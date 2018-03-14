package com.imooc.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.domain.base.Standard;


/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午9:29:48 <br/>   
 * 测试用例见web工程测试包内    
 */

//要想使用jpa实现自动创建接口的代理对象,需要在配置文件中配置<jpa:repositories base-package="com.imooc.bos.dao" />
//泛型1: 封装数据的对象的类型
//泛型2: 对象的主键的类型
public interface StandardRepository extends JpaRepository<Standard, Long> {
    
    // jpa提供基本的增删改查操作
    // jpa没有的方法可以通过自定义实现,SpringDataJPA提供了一套命名规范,遵循这一套规范定义**查询类**方法
    // 必须以findBy开头,后面跟属性的名字,首字母必须大写
    // 如果有多个条件,使用对应的SQL关键字
    List<Standard> findByName(String name);
    
    List<Standard> findByNameLike(String name);
    
    List<Standard> findByNameAndMaxWeight(String name, Integer maxWeight);
    
    // 若不遵循命名规范,则必须自定义查询语句,否则会报错
    // 查询语句:JPQL ,语法同 HQL
    @Query("from Standard where name = ? and maxWeight = ?")
    List<Standard> findByNameAndMaxWeight123(String name, Integer maxWeight);
    
    // 在?后面追加数字的方式,改变匹配参数的顺序
    @Query("from Standard where name = ?2 and maxWeight = ?1")
    List<Standard> findByNameAndMaxWeight123(Integer maxWeight, String name);
    
    
    // 原生SQL,必须加nativeQuery属性
    @Query(value = "select * from T_STANDARD where C_NAME = ? and C_MAX_WEIGHT = ?", nativeQuery = true)
    List<Standard> findByNameAndMaxWeightabcdef(String name,Integer maxWeight);
    
    
    // 自定义修改语句
    @Transactional
    @Modifying
    @Query("update Standard set maxWeight = ? where name = ?")
    void updateWeightByName(Integer maxWeight, String name);
    
    // 自定义删除语句
    @Transactional
    @Modifying
    @Query("delete from Standard where name = ?")
    void deleteByName(String name);
    
 }
  
