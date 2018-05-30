package com.apijava.apijava.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserLogin {
    private RestTemplate template;
    private Environment evm;
    private JSONObject setBody;

    private UserLogin() {
        this.template = new RestTemplate();
    }


    public List<String> login() {
//        String url  = evm.getProperty("account_url");
//        url = url + "/webapi/account/users/login";
        List<String> list = new ArrayList<>();
        String url = "http://192.168.1.93:8010/api/v1/account/webapi/account/users/login";
        setBody = new JSONObject();
        JSONObject responseBody;
        setBody.put("appVersion", "string");
        setBody.put("hash", "string");
        setBody.put("password", "123456");
        setBody.put("username", "053001a");
        setBody.put("wap", true);
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = template.postForEntity(url, setBody, String.class);
            responseBody = JSON.parseObject(responseEntity.getBody());
            list.add((String) responseBody.get("username"));
            JSONObject oauthToken = responseBody.getJSONObject("oauthToken");
            list.add(oauthToken.getString("access_token"));
            return list;
        } catch (Exception e) {
            return null;
        }
    }
 /*   public static void main(String[] args) {
        UserLogin userLogin = new UserLogin();
        userLogin.login();
    }*/
}

