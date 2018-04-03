package com.imooc.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.CourierRepository;
import com.imooc.bos.domain.base.Courier;
import com.imooc.bos.service.base.CourierService;

/**
 * ClassName:CourierServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午8:05:48 <br/>
 */

@Transactional
@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }

    /**
     * 权限控制的方式二:使用注解,在调用方法时,框架就会检查当前用户是否有对应的权限,如果有就放行,没有就抛异常
     * 使用注解方式进行权限控制,必须开启cglib代理,可以在applicationContext.xml中配置
     */
    // @RequiresPermissions("batchDel")
    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // 判断数据是否为空 null " "
        if (StringUtils.isNotEmpty(ids)) {
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                courierRepository.updateDelTagById(Long.parseLong(id));
            }
        }
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public List<Courier> findAvaible() {
        return courierRepository.findByDeltagIsNull();
    } 

    // 查看定区关联的快递员
    @Override 
    public List<Courier> findAssociatedCourier(Long fixedAreaId) { 
        return courierRepository.findByFixedArea(fixedAreaId);
    } 

    @Override
    public void doRestore(String ids) {
        // 判断是否为空
        if (StringUtils.isNotEmpty(ids)) {
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                courierRepository.updateRestoreById(Long.parseLong(id));
            }
        }
    }

}
