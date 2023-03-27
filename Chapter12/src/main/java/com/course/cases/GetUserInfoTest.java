package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息接口测试")

    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        System.out.printf(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        //第一步发送请求，获取请求结果
        JSONArray resultJson=getResult(getUserInfoCase);
        JSONArray resultJson0=new JSONArray(resultJson.getString(0));
        //从数据库查询用户信息
        List users2=session.selectList(getUserInfoCase.getExpected(),getUserInfoCase);
        JSONArray jsonArray2=new JSONArray(users2);
        System.out.println(jsonArray2.toString());
        System.out.println(resultJson.toString());

        //验证结果
        Assert.assertEquals(resultJson0.toString(),jsonArray2.toString());



    }

    private JSONArray getResult(GetUserInfoCase getUserInfoCase) throws IOException {

        HttpPost post=new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param=new JSONObject();
        param.put("id",getUserInfoCase.getId());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        log.info("请求获取用户信息的ID是："+param.toString());


        //设置头信息
        post.setHeader("content-type","application/json");


        //设置cookies值
        log.info("请求获取用户信息的cookies是",TestConfig.store);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response= TestConfig.defaultHttpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity(),"utf-8");
        List users1=Arrays.asList(result);
        JSONArray jsonArray1=new JSONArray(users1);

        return jsonArray1;

    }
}
