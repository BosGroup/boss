package com.imooc.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.base.Courier;

/**
 * ClassName:CourierRepository <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午8:09:10 <br/>
 */

//条件查询需要实现JpaSpecificationExecutor接口
//JpaSpecificationExecutor接口不能单独使用,一般都是和JpaRepository接口一起使用
public interface CourierRepository
        extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

    // 自定义方法
    // 根据ID更改删除的标志位
    @Modifying
    @Query("update Courier set deltag = 1 where id = ?")
    void updateDelTagById(long id);
    
    //查询在职快递员
    List<Courier> findByDeltagIsNull();
    
    @Modifying
    @Query("update Courier set deltag = 0 where id = ?")
	void updateRestoreById(long parseLong);

}
