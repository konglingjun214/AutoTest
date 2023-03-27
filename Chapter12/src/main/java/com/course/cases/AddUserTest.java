package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")

    public void addUser() throws IOException, InterruptedException, SQLException {
        SqlSession session= DatabaseUtil.getSqlSession();
        //在session第一次执行前，设置事务隔离级别
        session.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //session第一次执行，获取添加用户请求的测试数据
        AddUserCase addUserCase=session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);



        // 第一步发送请求，获取请求返回结果
        String result=getResult(addUserCase);
        Thread.sleep(5000);
        //查看数据库被添加成功的用户数据
        //session第二次执行
        List<User> users=session.selectList("addUserTest");
        log.info("添加用户的数量是"+users.size());
        //判断预期结果与请求返回结果是否一致
       Assert.assertEquals(result,addUserCase.getExpected());


    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        JSONObject param=new JSONObject();
        param.put("name",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        log.info("添加的用户是："+param.toString());


        //设置头信息
        post.setHeader("content-type","application/json");

        log.info("2-xk.TestConfig.store值:{}",TestConfig.store);
        //设置cookies值
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response= TestConfig.defaultHttpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity(),"utf-8");
        log.info("3-execute结果:{}",result);
        return result;

    }


}
