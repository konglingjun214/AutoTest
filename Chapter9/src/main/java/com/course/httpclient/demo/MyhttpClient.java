package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;


import java.io.IOException;

public class MyhttpClient{
    @Test
    public  void  test1() throws IOException {
        //用来存放我们的结果
        String result;
        //创建一个get请求
        HttpGet get=new HttpGet("http://baidu.com");
        //这个是用来执行get方法的
        HttpClient client=new DefaultHttpClient();
        //接收请求返回的数据
        HttpResponse response=client.execute(get);
        //或者全体内容
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);




    }
}
