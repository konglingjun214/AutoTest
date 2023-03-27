package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男用户信息接口测试")

    public void getUserList() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase=session.selectOne("getUserListCase",1);
        System.out.printf(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);




    }
}
