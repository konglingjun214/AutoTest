package com.course.httpclient.cookies;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MycookiesForPost {
    private  String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public  void beforeTest(){
        //读取application.propertities文件
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        //获取请求服务器IP链接
        url=bundle.getString("test.url");
    }
    @Test
    public  void  testGetCookies() throws IOException {
        String result;
        //获取测试接口链接
        String uri=bundle.getString("getCookies.uri");
        //拼接测试接口完整链接
        String testUrl=url+uri;
        //创建get请求
        HttpGet get=new HttpGet(testUrl);
        //创建客户端
        DefaultHttpClient client=new DefaultHttpClient();
        //执行get请求，创建接收对象
        HttpResponse response=client.execute(get);
        //获取对象实体，并转成string类型
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //client执行完后，cookie会存储在client中，再获取cookies信息
        this.store=client.getCookieStore();





        //获取cookies
        List<Cookie> cookies=store.getCookies();
        //遍历cookies
        for(Cookie cookie:cookies){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("name="+name+";value="+value);

        }
    }
    @Test(dependsOnMethods = {"testGetCookies"},enabled = true)
    private  void testPostMethod() throws IOException {
        String result;
        //获取测试接口链接
        String uri=bundle.getString("test.post.with.cookies");
        //拼接测试接口完整链接
        String testUrl=url+uri;
        //创建post请求
        HttpPost post = new HttpPost(testUrl);
        //创建客户端
        DefaultHttpClient client=new DefaultHttpClient();
        //添加cookies
        client.setCookieStore(this.store);
        //创建json类型参数
//        JSONObject params= new JSONObject();
//        params.put("name","huhansan");
//        params.put("age","18");
//        params.toString();
//        //传入json类型参数
//        StringEntity entity=new StringEntity(params.toString(),"utf-8");
//        post.setEntity(entity);
        //创建form类型参数
        List<NameValuePair> pairs= new ArrayList<>();
        pairs.add(new BasicNameValuePair("name","huhansan"));
        pairs.add(new BasicNameValuePair("age","18"));

        //把参数放入实体
        StringEntity entity=new UrlEncodedFormEntity(pairs,"UTF-8");
        System.err.println(JSON.toJSONString(entity));

        post.setEntity(entity);
        //设置头信息
//       post.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        //执行post请求，创建接收响应的对象
        HttpResponse response=client.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");

        //判断结果值与预期是否一致
        JSONObject resultJson=new JSONObject(result);
        String success= (String) resultJson.get("huhansan");
        String status= (String) resultJson.get("status");

        Assert.assertEquals(success,"success");
        Assert.assertEquals("1",status);



    }


}
