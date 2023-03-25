package com.course.Controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("/v1")
public class UserManager {
    @Autowired
    private SqlSessionTemplate template;
    @ApiOperation(value="登陆接口",httpMethod = "POST")
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public Boolean login(HttpServletResponse response, @RequestBody User user){
       int i= template.selectOne("login",user);
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if (i==1){
           log.info("登陆的用户是"+user.getName());
           return true;
        }
        return false;
    }
    @ApiOperation(value="添加用户接口",httpMethod = "POST")
    @RequestMapping(value="/addUser",method=RequestMethod.POST)
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean  x=verifyCookies(request);
        int  result=0;
        if(x!=null&&x==true){
            result=  template.insert("addUser",user);
            List<User> users=template.selectList("addUserTest");
            log.info("查询添加用户的是"+Arrays.toString(users.toArray()));
        }
        if (result>0){
            log.info("添加用户的数量是"+result);
            List<User> users=template.selectList("addUserTest");
            log.info("查询添加用户的是"+Arrays.toString(users.toArray()));
            return true;

        }
        return false;
    }

    @ApiOperation(value="获取用户信息/列表接口",httpMethod = "POST")
    @RequestMapping(value="/getUserInfo",method=RequestMethod.POST)
    public List<User> getUserINfo(HttpServletRequest request,@RequestBody User user){
        Boolean x=verifyCookies(request);
        List<User>  users=new ArrayList<>();
        log.info("1、开始判断cookies是否正确");

        if(x!=null&&x==true){
            users=  template.selectList("getUserInfo",user);
            log.info("2、cookies正确");
            log.info("3、getUserInfo获取的用户数量是"+users.size());
            return users;
        }else {
            return null;
        }
    }


    @ApiOperation(value="更新用户/删除用户接口",httpMethod = "POST")
    @RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x=verifyCookies(request);
        int result=0;
        log.info("1、开始判断cookies是否正确");

        if(x!=null&&x==true){
            result=  template.update("updateUserInfo",user);

        }
        log.info("2、更新的用户数量是"+result);

        return result;
        }








    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        log.info("1、开始判断cookies值");
        if (Objects.isNull(cookies)) {
            log.info("2、cookies为空");
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                log.info("3、cookies验证通过");
                return true;

            }
        }
        log.info("4、cookies值不为空但是没有验证通过");

        return false;

    }
}
