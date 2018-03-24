package com.imooc.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.AreaRepository;
import com.imooc.bos.dao.FixedAreaRepository;
import com.imooc.bos.dao.take_delivery.OrderRepository;
import com.imooc.bos.dao.take_delivery.WorkBillRepository;
import com.imooc.bos.domain.base.Area;
import com.imooc.bos.domain.base.Courier;
import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.domain.take_delivery.Order;
import com.imooc.bos.domain.take_delivery.WorkBill;
import com.imooc.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:52:57 <br/>    
 * 根据发件地址完全匹配(发件地址数据来自CRM系统中customer表,而且这个客户一定要和某一个定区关联)
 * 根据发件地址模糊匹配(发件地址数据来自后台系统中subArea表,而且这个分区一定要和某一个定区关联,这个分区所属的区域数据一定要对得上号)   
 */

@Transactional
@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private WorkBillRepository workBillRepository;
    
    @Override
    public void saveOrder(Order order) {
        //收件人和寄件人的Area的id为null,需要把瞬时态的Area转换为持久态的Area
        Area sendArea = order.getSendArea();
        if(sendArea != null){
            //查找数据库中此省市区的Area
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(),
                    sendArea.getCity(),sendArea.getDistrict());
            //将瞬时态转换成持久态
            order.setSendArea(sendAreaDB);
        }
        
        Area recArea = order.getRecArea();
        if(recArea != null){
            //根据省市区查找持久态,并重新赋值给Order对象
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        //保存订单
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderTime(new Date());
        orderRepository.save(order);
        
        
        //自动分单
        //若寄件人详细地址与客户的地址完全匹配,根据该客户找到该客户的定区id,继而找到定区和该定区下的快递员,再指派快递员
        //根据发件地址完全匹配,让crm系统根据发件地址查询定区ID
        //做下面的测试之前,必须在定区中关联一个客户,下单的页面填写的地址,必须和这个客户的地址一致
        String sendAddress = order.getSendAddress();
        if(StringUtils.isNotEmpty(sendAddress)){
            String fixedAreaId = WebClient
                    .create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAdddress")
                    .type(MediaType.APPLICATION_JSON)
                    .query("address", sendAddress)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            
            if(StringUtils.isNotEmpty(fixedAreaId)){
                //根据定区ID查询定区
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                if(fixedArea != null){
                    //获取定区关联的快递员
                    Set<Courier> couriers = fixedArea.getCouriers();
                    if(!couriers.isEmpty()){
                        //指派快递员
                        findCourior(order, couriers);
                        //发送短信,推送一个通知
                        //中断代码的执行
                        order.setOrderType("自动分单");
                        return;
                    }
                }
            }else{
                //若寄件人详细地址与客户的地址不匹配,根据省市区(区域)找到分区集合,遍历找到详细地址包含关键字和辅助关键字的分区,再找到定区,快递员
                //持久态
                //定区关联分区,在页面上填写的发件人地址,必须是对应的分区的关键字或者辅助关键字
                Area sendArea2 = order.getSendArea();
                if(sendArea2 != null) {
                    //获得该寄件人区域的分区集合
                    Set<SubArea> subareas = sendArea2.getSubareas();
                    for(SubArea subArea : subareas) {
                        String keyWords = subArea.getKeyWords();
                        String assistKeyWords = subArea.getAssistKeyWords();
                        //判断是否包含关键字和辅助关键字
                        if(sendAddress.contains(keyWords)||sendAddress.contains(assistKeyWords)){
                            FixedArea fixedArea2 = subArea.getFixedArea();
                            if(fixedArea2 != null) {
                                //查询快递员
                                Set<Courier> couriers =fixedArea2.getCouriers();
                                if(!couriers.isEmpty()) {
                                    //指派快递员
                                    findCourior(order, couriers);
                                    //发送短信,推送一个通知
                                    //中断代码的执行
                                    order.setOrderType("自动分单");
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        //若寄件人详细地址与客户的地址不匹配,详细地址不包含分区中关键字和辅助关键字,则实现人工分单,打电话给寄件人询问具体信息
        //根据发件地址模糊匹配
        order.setOrderType("人工分单");
    }
    
    
    //找到定区关联的快递员,生成工单,并发送短信通知
    private void findCourior(Order order, Set<Courier> couriers) {
        //根据快递员的上班时间,收派能力,忙闲程度分单,此处直接指派第一个
        Iterator<Courier> iterator = couriers.iterator();
        Courier courier = iterator.next();
        //指派快递员
        order.setCourier(courier);
        //生成工单
        WorkBill workBill = new WorkBill();
        workBill.setAttachbilltimes(0);
        workBill.setBuildtime(new Date());
        workBill.setCourier(courier);
        workBill.setOrder(order);
        workBill.setPickstate("新单");
        workBill.setRemark(order.getRemark());
        workBill.setSmsNumber("123");
        workBill.setType("新");
        workBillRepository.save(workBill);
        //发送工单信息给快递员,此处打印日志进行模拟
        System.out.println("工单信息:请到" + order.getSendAddress() + "取件,客户电话:" + order.getSendMobile());
    }

}
  
