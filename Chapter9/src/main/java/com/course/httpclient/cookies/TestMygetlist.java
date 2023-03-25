package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.testng.annotations.Test;

@Test
public class TestMygetlist {
    HttpGetWithEntity get= new HttpGetWithEntity("http://localhost:8888/get/with/param");
    JSONObject params=new JSONObject();







}
