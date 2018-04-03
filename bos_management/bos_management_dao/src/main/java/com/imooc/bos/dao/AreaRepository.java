package com.imooc.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:24:08 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area, Long>{
    
    //自定义条件查询
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1")
    List<Area> findQ(String q);
    
    //根据省市区查询区域
    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
    
    // SELECT a.C_PROVINCE,COUNT(*) FROM T_AREA a GROUP BY a.C_PROVINCE;
    @Query("select a.province,count(*) from Area a group by a.province")
    List<Object[]> exportCharts();

}
  
