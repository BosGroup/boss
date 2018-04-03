package com.imooc.bos.service.base.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.CourierRepository;
import com.imooc.bos.dao.FixedAreaRepository;
import com.imooc.bos.dao.SubAreaRepository;
import com.imooc.bos.dao.TakeTimeRepository;
import com.imooc.bos.domain.base.Courier;
import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.domain.base.TakeTime;
import com.imooc.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午10:03:58 <br/>       
 */

@Transactional
@Service       //spring的注解,业务层代码
public class FixedAreaServiceImpl implements FixedAreaService{
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    
    @Override
    public void save(FixedArea fixedArea) {
         fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
        
        //代码执行成功以后,快递员表发生update操作,快递员和定区中间表会发生insert操作  
        //持久态对象
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        //建立快递员和时间的关联
        courier.setTakeTime(takeTime);
        
        //建立快递员和定区的关联
        //快递员和定区的关系由定区维护,因为Courier实体类中fixedAreas字段的上方添加了mappedBy属性
        fixedArea.getCouriers().add(courier);
    }

    @Override
    public void assignsubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds) {
        //关联分区到指定的定区,关系是由分区在维护
        //先解绑，把当前定区绑定的所有分区全部解绑
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Set<SubArea> subareas = fixedArea.getSubareas();
        for (SubArea subArea : subareas) {
            subArea.setFixedArea(null);
        }
        
        //再绑定
        if(subAreaIds != null){
            for (Long subAreaId : subAreaIds) {
                SubArea subArea = subAreaRepository.findOne(subAreaId);
                subArea.setFixedArea(fixedArea);
            }
        }
    }

    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // 判断数据是否为空 null " "
        if (StringUtils.isNotEmpty(ids)) {
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                fixedAreaRepository.delete(Long.parseLong(id));
            }
        }
    }

    @Override
    public Page<FixedArea> findAll(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification, pageable);
    }
    
    
}
  
