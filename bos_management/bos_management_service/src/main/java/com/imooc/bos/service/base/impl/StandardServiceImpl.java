package com.imooc.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.StandardRepository;
import com.imooc.bos.domain.base.Standard;
import com.imooc.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:26:07 <br/>       
 */

@Transactional
@Service
public class StandardServiceImpl implements StandardService{
    
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard standard) {
         standardRepository.save(standard); 
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
      
        return standardRepository.findAll(pageable);
    }

    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // 判断数据是否为空 null " "
        if (StringUtils.isNotEmpty(ids)) {
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                standardRepository.delete(Long.parseLong(id));
            }
        }
    }

}
  
