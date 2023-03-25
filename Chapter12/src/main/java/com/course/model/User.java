package com.course.model;

import lombok.Data;

@Data
public class User {
    private  int id;
    private  String name;
    private  String password;

    private  String age;

    private  String sex;
    private  String permission;
    private  String isDelete;

    @Override
    public  String toString(){
        return (
                "{id:"+id+","+
                       "userName:"+name+","+
                       "password:"+password+","+
                       "age:"+age+","+
                       "sex:"+sex+","+
                       "permission:"+permission+","+
                       "isDelete:"+isDelete+"}"
                );

    }



}
