package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {


    @BeforeTest(groups = "loginTrue",description = "测试准备工作,获取HttpClient对象")
    public void beforeTest(){
        //声明客户端
        TestConfig.defaultHttpClient = new DefaultHttpClient();
        //获取接口的url
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);

    }




    @Test(groups = "loginTrue",description = "用户登陆成功接口测试")
    public void loginTrue() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

     //第一步发送请求，获取请求结果
       String result=getResult(loginCase);

      //验证结果
        Assert.assertEquals(result,loginCase.getExpected());
    }



    @Test(groups = "loginFalse",description = "用户登陆失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


//第一步发送请求，获取请求结果
        String result=getResult(loginCase);

        //验证结果
        Assert.assertEquals(result,loginCase.getExpected());


    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.loginUrl);
        JSONObject param=new JSONObject();
        param.put("name",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result=EntityUtils.toString(response.getEntity());

        TestConfig.store=TestConfig.defaultHttpClient.getCookieStore();
        return result;



    }




}
