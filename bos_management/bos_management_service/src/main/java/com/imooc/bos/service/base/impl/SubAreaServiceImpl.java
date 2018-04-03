package com.imooc.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.SubAreaRepository;
import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:42:52 <br/>       
 */

@Transactional
@Service
public class SubAreaServiceImpl implements SubAreaService {
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public void save(SubArea model) {
        subAreaRepository.save(model);
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
        return subAreaRepository.findAll(pageable);
    }

    @Override
    public List<SubArea> findUnAssociatedsubAreas() {
        return subAreaRepository.findByFixedAreaIsNull();
    }

    @Override
    public List<SubArea> findAssociatedsubAreas(Long fixedAreaId) {
        //使用SpringDataJPA的命名规范进行查询时,如果字段是对象，必须是单一对象，不能是集合,传入的参数必须指定id属性
        FixedArea fixedArea = new FixedArea();
        fixedArea.setId(fixedAreaId);
        return subAreaRepository.findByFixedArea(fixedArea);
    }

    //导出图表
    @Override
    public List<Object[]> exportCharts() {
          
        return subAreaRepository.exportCharts();
    }

    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // 判断数据是否为空 null " "
        if (StringUtils.isNotEmpty(ids)) {
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                subAreaRepository.delete(Long.parseLong(id));
            }
        }
    }

}
  
