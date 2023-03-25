package com.course.server;

import com.course.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class MyGetMethod {
    @RequestMapping(value = "/getCookies",method = RequestMethod.GET )
    public String getCookies(HttpServletResponse response){

        Cookie Cookie=new Cookie("login","ture");
        response.addCookie(Cookie);


        return  "恭喜你获得cookies成功";
    }
    /**
     * 要求客户端携带cookies访问
     */
    @RequestMapping(value = "/get/withCookies",method = RequestMethod.GET)
    public  String getWithCookies(HttpServletRequest request){
        //获得cookies
        Cookie[] cookies = request.getCookies();
        //判断cookies是否为空
        if(Objects.isNull(cookies)){
            return "你必须携带cookies信息来";
        }
        //不为空接着遍历cookies，判断内容是否正确
        for( Cookie cookie:cookies){
            if(cookie.getValue().equals("ture")&&cookie.getName().equals("login")){
                return "访问成功";
            }
        }
        //不为空，但是内容不正确
        return "你必须携带cookies信息来";

    }
    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种方式：URL：key=value&key=value
     * 我们来模拟获取商品列表
     */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    public Map<String,Integer> getlist(@RequestParam(value = "start",required = true)  String start,@RequestParam(value = "end",required = true)   String end){
        Map<String,Integer> mylist=new HashMap<>();
        mylist.put("鞋",400);
        mylist.put("干脆面",10);
        mylist.put("衬衫",300);
        return mylist;

    }
/**
 * 开发一个需要携带参数才能访问的get请求
 * 第二种方式：URL：ip:port/get/with/param/10/20
 * 我们来模拟获取商品列表
 */
@RequestMapping(value = "/get/with/param/{start}/{end}",method = RequestMethod.GET)

public Map<String,Integer> mygetlist(@PathVariable Integer start, @PathVariable Integer end) {

    Map<String,Integer> mylist=new HashMap<>();
    mylist.put("鞋",400);
    mylist.put("干脆面",10);
    mylist.put("衬衫",300);
    return mylist;

}
}
