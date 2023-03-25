package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@Slf4j
public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息接口测试")
    public void updateUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);
        System.out.printf(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        //第一步发送请求，获取请求结果
        int result=getResult(updateUserInfoCase);
        User user=session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);

        //验证结果
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);




    }



    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息接口测试")
    public void deleteUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2);
        System.out.printf(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);



    }
    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {

        HttpPost post=new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param=new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());
        param.put("name",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        log.info("修改获取用户信息的是："+param.toString());


        //设置头信息
        post.setHeader("content-type","application/json");


        //设置cookies值
        log.info("请求获取用户信息的cookies是",TestConfig.store);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response= TestConfig.defaultHttpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity(),"utf-8");


        return Integer.parseInt(result);


    }
}
