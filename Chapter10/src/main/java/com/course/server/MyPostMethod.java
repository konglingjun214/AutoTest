package com.course.server;

import com.course.bean.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
public class MyPostMethod {
    /**
     * 开发一个需要携带参数才能访问的post请求
     * 第一种方式：bodyDate:name=value&name=value
     * 我们来模拟登陆成功获返回cookies
     */
    private static Cookie cookie;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public  String login(HttpServletResponse response,
                         @RequestParam(value = "username",required = true) String  username,
                         @RequestParam(value = "password",required = true)String password){
            if(username.equals("huhansan")&&password.equals("123456")){
                cookie=new Cookie("login","true");

                response.addCookie(cookie);
                return "登陆成功";
            }else{
                return "登陆失败";
            }
    }
    /**
     * 开发一个需要验证cookie返回用户列表请求
     * 第二种方式：bodyDate:json
     */
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)

    public String  login(HttpServletRequest request,@RequestBody User u) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") &&
                    cookie.getValue().equals("true") &&
                    u.getName().equals("huhansan") &&
                    u.getPassword().equals("123456")) {
                User user = new User();
                user.setAge("18");
                user.setSex("男");
                return user.toString();
            }

        }
        return "访问失败";
    }
}
