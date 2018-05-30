package com.apijava.apijava.service;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class AccountService {

    private RestTemplate template;
    private UserLogin userLogin;
    private Environment evm;
    private HttpHeaders requestHeaders = new HttpHeaders();
    private JSONObject setBody = new JSONObject();
    private ResponseEntity<String> response;

    private AccountService(Environment evm, UserLogin userLogin) {
        template = new RestTemplate();
        this.userLogin = userLogin;
        this.evm = evm;
    }

    private List<String> login() {
        return userLogin.login();
    }

    private String getToken() {
        return login().get(1);
    }
    private String getUserName() {
        return login().get(0);
    }


    /**
     * user.1.彩种收藏保存接口
     * */
    public String addBookmarks() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/profile/addBookmarks?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "*/*");
        setBody.clear();
        setBody.put("gameId", getGameID());
        log.info(url);
        return getPostResponse(url);
    }

    /**
     * user.2.用户彩种收藏删除接口
     * */
    public String delBookmarks() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/profile/delBookmarks?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "*/*");
        setBody.clear();
        setBody.put("gameId", getGameID());
        log.info(url);
        return getDeleteResponse(HttpMethod.DELETE, url);
    }

    /**
     * user.3.用户彩种收藏获取接口
     * */
    public String getBookmarks() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/profile/getBookmarks?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Accept", "application/json");
        log.info(url);
        return getGetResponse(url);
    }

    /**
     * user.4.代理创建下层用户
     * */
    public String addAgentUser() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/users?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", " application/json");
        requestHeaders.add("Accept", "application/json");
        setBody.clear();
        setBody.put("username", getRandomString());
        setBody.put("password", "123456");
        setBody.put("memberType", "AGENT");
        setBody.put("prizeGroup", "1950");
        setBody.put("realName", "autotest");
        setBody.put("phoneNumber", "13996323363");
        setBody.put("qq", "258845215");
        setBody.put("email", "258845215@qq.com");
        log.info(url);
        return getPostResponse(url);
    }

    /**
     * user.5.用户更新基本信息
     * */
    public String updateUserInfo() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/users/?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "application/json");
        setBody.clear();
        setBody.put("email", "321232132@qq.com");
        setBody.put("identityNumber", "110110190005210101");
        setBody.put("memberType", "AGENT");
        setBody.put("nickname", "updateName");
        setBody.put("phoneNumber", "13636363256");
        setBody.put("prizeGroup", 1978);
        setBody.put("qq", "321232132");
        setBody.put("username", getUserName());
        return getDeleteResponse(HttpMethod.PUT, url);
    }

    /**
     * user.6.修改用户取款密码
     * */
    public String encryptSecurityPassword() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/users/change/encryptPassword?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "application/json");
        setBody.clear();
        setBody.put("mode", "SECURITY_PASSWORD");
        String newpassword = "NmFmMDQ4ZGE3MDc5NzFjNWFmM2RkZWFmYzhlMDkyZDM4Mzc4ZGEwOWFlZTVhMjdjNzYyMzNlNWQ5YWZjYjRhNGY3YjdjYjJmZGYyNjQ4YTBmY2VhMTYyZGMzY2E3ZDc4NWVjZWIzZDQwMDYxOTI2YWE1YzQ4YTA5ODI1ODg1OWExYTFjZGRkZDliOGE1MjA4MjNhMTk2NDkzZWE3MTMwNjM4OTY4MjMwMzJlMmQ1ZTAyYTI2MDczMjdmMmM0M2I0ZTdiZWI2ZDE3OWJkN2NiZjQ0NTIyZDA0ZWEwMmExOTU3MDYxMDFkNmFkNjQ1MjcxNzM1OWI3NTZlYzQyMWRmOA==";
        setBody.put("newPassword", newpassword);
        String password = "NTIwMmY1MDIyZjcwNGU3MmM2NmU4MGY4NjM0MGFlYzdlZTVkZGNmM2Q0NDcwYWYyMDM3MThjMzVkN2U1NDQ5YjhmYTcxNzc2ODAyYmRkMmQ3MDU1YjQwNGQ2Y2RkMTIwZTNiYTk4ZDdjMjdiNWE3YzlhOWJlMjEzNWYxNjk1YmExOTVkMjY5NmNmOGRjN2M3ZjVhZTkxNjc3NmVmMTczOThmMDJhMDc4YjQ2N2Y4ZTBlMzkyNTI1OTAwNzA2NmEyYTU2NGViNzliNzZiZjg2YTFkNmYzOTc4ZGYxMjUwYTJlYmRkZjQzODRhNTliNmZiNThlNjYzMmIwMDAxMGQwYQ==";
        setBody.put("password", password);
        log.info(url);
        return getPostResponse(url);
    }

    /**
     * user.7.修改用户登录密码
     * */
    public String encryptLoginPassword() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/users/change/encryptPassword?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "application/json");
        setBody.clear();
        setBody.put("mode", "PASSWORD");
        String newpassword = "MDFlYTE1NzU2ZTNiMGE5Y2RjZDZiMDc3NDJkZjljNDBkMTdkNjFhZDM0NWUxODRkNTQ1YzM4ZmI5Mzc4MjA0NWQxZjA0YjA5OTUyOWUxMTVmMGIyNDNmNjMzODJhOWFlZmE0ODkzMjU1YjFiMDEyNzJkMzkwMWI4YzE1OGFmZmZlNDk5YWQ0N2U4MDYzY2IxN2E4NzQ0MDZjODRmZWZhNTc3ZmUwYzVmOGNhY2M5ODQ3M2YwM2NiNGI2Y2FkOTcxZmQ1NWJhMjYyYjliYzQ0NWQ3Y2I4MmQwZTIwNDJiNDUxNzA5MDI2MWE3YjhhZGRiNjQ4M2RkZmQ3MDk5OWE1Yg==";
        setBody.put("newPassword", newpassword);
        String password = "MTQ5MjMzOTg0Njc4Mzg5YjM1Mzg5NzQxYmZhOWY1MGIzNGRlMTcxOTY5NTA0Mzc3NWYyMGNlNjNjZjNmN2I1NjMyOWMxMzA1MzJmYzhkN2QwNTQ5YTMyYzQ1NjBlODZkYWRlY2Y2M2QzZDhjMDUxYjA4YzhkODkzYjY3YzI0ODk4ZjU3ZTM2NmZiODZkYmQwMmYwMTNmOGE4MWJlZGVhZjNmZjNmNWJkMzg1ZGM0ZWRlZjJiZjAxZGRlODIzNTkyNjg4OGMxZDM5OTNmMDM2Y2ZkNDg2YmQzNjY2MGRjZDZjNDBhMWI1M2VlOTMxZDBlYmY4YjE3NDU1ZDk3ZGJiMg==";
        setBody.put("password", password);
        log.info(url);
        return getPostResponse(url);
    }

    /**
     * 用户签到
     * */
    public String signIn() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/operate/users/signIn?access_token=" + getToken();
        requestHeaders.clear();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Accept", "application/json");
        setBody.clear();
        log.info(url);
        return getPostResponse(url);
    }


    /**
     * http-Post请求结果处理
     * */
    private String getPostResponse(String url) {
        try {
            response = template.postForEntity(url, setBody, String.class);
        }catch (HttpClientErrorException e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = e.getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + url;
    }

    /**
     * http-Get请求结果处理
     * */
    private String getGetResponse(String url) {
        try {
            response = template.getForEntity(url, String.class);
        }catch (HttpClientErrorException e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = e.getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + url;
    }

    /**
     * http-Delete请求结果处理
     * */
    private String getDeleteResponse(HttpMethod httpMethod, String url) {
        HttpEntity<String> requestEntity = new HttpEntity<>(setBody.toString(), requestHeaders);
        log.info(url);
        ResponseEntity<String> response;
        try {
            response = template.exchange(url, httpMethod, requestEntity, String.class);
        }catch (HttpClientErrorException e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = e.getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + url;
    }

    /**
     * 获取随机创建的username
     * */
    private static String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i = 0; i< 10; i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取彩种ID
     * */
    private static String getGameID() {
        ArrayList<String> gameIDList = new ArrayList<>();
        gameIDList.add("HF_AHD11");
        gameIDList.add("HF_AHK3");
        gameIDList.add("HF_BJ28");
        gameIDList.add("HF_BJ5FC");
        gameIDList.add("HF_BJK3");
        gameIDList.add("HF_BJPK10");
        gameIDList.add("HF_CQKL10F");
        gameIDList.add("HF_CQSSC");
        gameIDList.add("HF_FFK3");
        gameIDList.add("HF_FFPK10");
        gameIDList.add("HF_FFSSC");
        gameIDList.add("HF_FJD11");
        gameIDList.add("HF_GDD11");
        gameIDList.add("HF_GDKL10F");
        gameIDList.add("HF_GXK3");
        Random index = new Random();
        return gameIDList.get(index.nextInt(2));
    }

/*    public static void main(String[] args) {
        log.info(getGameID());
    }*/

}
