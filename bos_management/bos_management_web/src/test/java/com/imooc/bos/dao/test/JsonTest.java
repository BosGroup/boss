package com.imooc.bos.dao.test;

import org.junit.Test;

import net.sf.json.JSONObject;

/**
 * ClassName:JsonTest <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午5:13:28 <br/>
 */
public class JsonTest {

    @Test
    public void fun01() {
        Person person = new Person(11, "zhangsan");

        // json库将对象转化成json的时候不是看对象类的字段,而是根据get方法
        String json = JSONObject.fromObject(person).toString();
        System.out.println(json); // {"age":11,"name":"zhangsan","province":"广东"}
    }

    @Test
    public void fun02() {
        Person person = new Person(11, "zhangsan");

        // json库将对象转化成json的时候不是看对象类的字段,而是根据get方法
        // bean类中多加一个get方法,在返回json数据到前端时就会多一个{info:age+name}
        String json = JSONObject.fromObject(person).toString();
        System.out.println(json); // {"age":11,"info":"11zhangsan","name":"zhangsan"}
    }
}
