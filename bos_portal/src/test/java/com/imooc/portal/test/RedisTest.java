package com.imooc.portal.test;

import java.util.concurrent.TimeUnit;

import org.apache.xml.resolver.helpers.PublicId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.sym.Name;

/**  
 * ClassName:RedisTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:14:09 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
    
    @Autowired
    //指定key和value的类型
    private RedisTemplate<String, String> redisTemplate;
    
    @Test
    public void test01(){
        //设置数据类型opsForValue(),opsForList(),opsForHash(),opsForSet()...
        redisTemplate.opsForValue().set("name", "zsf");  
    }
    
    @Test
    public void test02(){
        String name = redisTemplate.opsForValue().get("name"); 
        System.out.println(name);
    }
    
    @Test
    public void test03(){
        redisTemplate.delete("name");  
    }
    
    @Test
    public void test04(){
        //设置数据的有效期,过期后数据会被自动删除
        //参数3:时长; 参数4:时间单位
        redisTemplate.opsForValue().set("tel", "18086506769",10,TimeUnit.SECONDS);  
    }
}
  
