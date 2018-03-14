package com.imooc.bos.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imooc.bos.dao.StandardRepository;
import com.imooc.bos.domain.base.Standard;


/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午9:41:57 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    
    @Autowired
    private StandardRepository standardRepository;
    
    //################# 基本增删改查操作 ########################
    
    //查询所有
    @Test
    public void test1() {
        //此处standardRepository为StandardRepository接口的具体实现类SimpleJpaRepository,调用实现类的方法
        List<Standard> list = standardRepository.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    //保存
    @Test
    public void test2() {
        Standard standard = new Standard();
        standard.setName("张三");
        standard.setMaxWeight(100);
        standardRepository.save(standard);
    }
    
    //修改
    @Test
    public void test3() {
        Standard standard = new Standard();
        standard.setId(1L);
        standard.setName("张三三");
        standard.setMaxWeight(9999);

        // save兼具保存和修改的功能,
        // 修改的话,必须传入id
        standardRepository.save(standard);
    }
    
    //查询一个
    @Test
    public void test4() {
        Standard standard = standardRepository.findOne(1L);
        System.out.println(standard);
    }
    
    @Test
    public void test5() {
        standardRepository.delete(2L);
    }
    
    
   //################# 自定义增删改查操作(遵循jpa命名规范) ########################
    //jpa没有的方法可以通过自定义实现,SpringDataJPA提供了一套命名规范,遵循这一套规范定义**查询类**方法
    // 必须以findBy开头,后面跟属性的名字,首字母必须大写
    // 如果有多个条件,使用对应的SQL关键字
    
    //根据名字进行查找
    @Test
    public void test6() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }
        
    }
    
    //根据名字进行模糊查找
    @Test
    public void test7() {
        // 根据名字进行查找
        List<Standard> list = standardRepository.findByNameLike("%张三%");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    //多条件查询
    @Test
    public void test8() {
        List<Standard> list = standardRepository.findByNameAndMaxWeight("张三", 100);
        for (Standard standard : list) {
            System.out.println(standard);
        }

    }
    
    
    //################# 自定义增删改查操作(不遵循jpa命名规范,不推荐使用) ########################
    //则必须自定义查询语句,否则会报错
    
    //不遵循jpa规范,查询语句:JPQL
    @Test
    public void test9() {
        List<Standard> list = standardRepository.findByNameAndMaxWeight123("张三", 100);
        for (Standard standard : list) {
            System.out.println(standard);
        }

    }
    
    //通过在?后面追加数字的方式,改变匹配参数的顺序
    @Test
    public void test10() {
        List<Standard> list = standardRepository.findByNameAndMaxWeight123(100, "张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }

    }
    
    //通过原生SQL查询
    @Test
    public void test11() {
        List<Standard> list = standardRepository.findByNameAndMaxWeightabcdef("张三", 100);
        for (Standard standard : list) {
            System.out.println(standard);
        }

    }
    
    // 自定义修改语句
    // 若在测试用例中使用事务注解,方法执行完成以后,事务回滚了
    @Test
    public void test12() {
        standardRepository.updateWeightByName(200, "张三");

    }
    
    // 自定义删除语句
    @Test
    public void test13() {
        standardRepository.deleteByName("张三");

    }
}
  
