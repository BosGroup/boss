package com.imooc.bos.dao.test;  
/**  
 * ClassName:Person <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午5:10:07 <br/>       
 */
public class Person {
    
    private int age;
    private String name;

    public Person() {

    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    //在bean类中多加一个get方法
   /* public String getProvince() {
        return "广东";
    }*/
    
    public String getinfo(){
        return age+name;
    }

    @Override
    public String toString() {
        return "Person [age=" + age + ", name=" + name + "]";
    }
   
}
  
