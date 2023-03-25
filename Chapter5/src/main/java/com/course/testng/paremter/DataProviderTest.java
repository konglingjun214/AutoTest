package com.course.testng.paremter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    @Test(dataProvider = "data")
    public  void testDataProvide(String name,int age){
        System.out.println("name="+name+";age="+age);
    }
    @Test(dataProvider = "methoddata")
    public  void test1(String name,int age){
        System.out.println("test1方法name="+name+";age="+age);
    }
    @Test(dataProvider = "methoddata")
    public  void test2(String name,int age){
        System.out.println("test2方法name="+name+";age="+age);
    }
    @DataProvider(name="data")
    public Object[][] providerData(){
        Object[][] o=new Object[][]{
                {"zhangsan",10},
                {"lisi",20},
                {"zhaoliu",30}
        };
        return  o;
    }
    @DataProvider(name="methoddata")
    public Object[][] methoddataTest(Method method){
        Object[][] result=null;
        if(method.getName().equals("test1")) {
            result = new Object[][]{
                    {"zhangsan", 20}
            };
        }else if (method.getName().equals("test2")){
                result=new Object[][]{
                        {"zhaosi",30}
                };
            }
        return  result;
        }



}
