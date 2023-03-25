package com.course.controller;

import com.course.model.User;
import org.mapstruct.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class Demo {
    //首先获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;
    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    public  int getUserList(){
       return template.selectOne("getUserCount");
    }
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public  int addUser(@RequestBody User u){
        return template.insert("addUser",u);
    }

    @RequestMapping(value = "/upDateUser",method = RequestMethod.POST)
    public  int  upDateUser(@RequestBody User u){
        return template.update("upDateUser",u);
    }
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public  int deleteUser(@RequestParam(value = "id") int idd){
        return  template.delete("deleteUser",idd);
    }



}
