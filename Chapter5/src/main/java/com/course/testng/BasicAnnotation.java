package com.course.testng;


import org.testng.annotations.*;

public class BasicAnnotation {
    @Test
    public  void testCase1 (){
        System.out.println("test 这是测试用例1");
    }

    @Test
    public  void testCase2 (){
        System.out.println("test 这是测试用例2");
    }
    @BeforeMethod
    public  void beforeMethod(){
        System.out.println("beforeMethod这是在测试方法之前运行的");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("AfterMethod这是在测试方法之后运行的");
    }
    @AfterClass
    public void AfterClass(){
        System.out.println("AfterClass这是在类运行之后运行的");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass这是在类运行之前运行的");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("beforeSuite这是在类运行之前运行的");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("AfterSuite这是在类运行之前运行的");
    }
}