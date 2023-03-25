package com.course.testng.paremter;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParemterTest {
    @Test
    @Parameters({"name","age"})
    public void paraTest1(String name,int age){
        System.out.println("name="+name+"；age="+age);

    }
}
