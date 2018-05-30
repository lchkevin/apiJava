package com.apijava.apijava.service;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Slf4j
@Service
public class AccountService {

    private RestTemplate template;
    private Environment evm;
    private HttpHeaders requestHeaders = new HttpHeaders();
    private String token = "510f6b8f-2458-4bfc-a334-929a7b386b75";
    private JSONObject hashMap = new JSONObject();

    private AccountService(Environment evm) {
        template = new RestTemplate();
        this.evm = evm;
    }

    public String getBookmarks() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/profile/getBookmarks?access_token=" + token;
        requestHeaders.add("Accept", "application/json");
        log.info(url);
        ResponseEntity<String> response;
        try {
            response = template.getForEntity(url, String.class);
        } catch (RestClientException e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = ((HttpClientErrorException) e).getResponseBodyAsString();
            return status + ",,," + body + ",,," + url;
        }
        String str = response.getBody();
        return response.getStatusCodeValue() + ",,," + str + ",,," + url;
    }


    public String entitlementOne() {
        String url = evm.getProperty("account_url");
        url = url + "/entitlement/resources/byusername?username=test120701";
        requestHeaders.add("Accept", "application/json");
        log.info(url);
        ResponseEntity<String> response;
        try {
            response = template.getForEntity(url, String.class);
        } catch (Exception e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = ((HttpClientErrorException) e).getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        String str = response.getBody();
        return response.getStatusCodeValue() + ",,," + str + ",,," + url;
    }

    public String addUser() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/users?access_token=" + token;
        requestHeaders.add("Content-Type", " application/json");
        requestHeaders.add("Accept", "application/json");
        hashMap.put("username", getRandomString(10));
        hashMap.put("password", "123456");
        hashMap.put("memberType", "AGENT");
        hashMap.put("prizeGroup", "1950");
        hashMap.put("realName", "autotest");
        hashMap.put("phoneNumber", "13996323363");
        hashMap.put("qq", "258845215");
        hashMap.put("email", "258845215@qq.com");
        log.info(url);
        ResponseEntity<String> response;
        try {
            response = template.postForEntity(url, hashMap, String.class);
        }catch (Exception e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = ((HttpClientErrorException) e).getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + url;
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
