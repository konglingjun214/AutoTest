package com.course.model;

import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private  int id;

    private  String userName;
    private  String userId;

    private  String age;

    private  String sex;
    private  String permission;

    private  String isDelete;
    private  String expected;
}
