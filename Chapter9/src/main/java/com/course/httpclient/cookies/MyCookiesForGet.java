package com.course.httpclient.cookies;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private  String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeTest
    public  void beforeTest(){
        //读取application.propertities文件
        bundle=ResourceBundle.getBundle("application",Locale.CHINA);
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
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException, URISyntaxException {
        String result;
        //获取测试接口链接
        String uri=bundle.getString("test.get.with.cookies");
        //拼接测试接口完整链接
        String testUrl=url+uri;
        //创建get请求
//        URIBuilder uriBuilder=new URIBuilder(testUrl);
//        uriBuilder.addParameter("name","huhansan");
//        uriBuilder.addParameter("age","18");
//        HttpGet get=new HttpGet(uriBuilder.build());
        HttpGetWithEntity get= new HttpGetWithEntity(testUrl);
        JSONObject params= new JSONObject();
        params.put("name","huhansan");
        params.put("age","18");
        params.toString();
        //传入json类型参数
        StringEntity entity=new StringEntity(params.toString(),"utf-8");
        get.setEntity(entity);






        //创建客户端
        DefaultHttpClient client=new DefaultHttpClient();
        //添加cookies
        client.setCookieStore(this.store);





        //执行get请求，创建接收对象
        HttpResponse response=client.execute(get);

        //获取响应状态码
        int statusCode=response.getStatusLine().getStatusCode();
        System.out.println("statusCode="+statusCode);
        //如果状态码是200，返回实体
        if(statusCode==200){
            //获取对象实体，并转成string类型
            result= EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }


    }
}
