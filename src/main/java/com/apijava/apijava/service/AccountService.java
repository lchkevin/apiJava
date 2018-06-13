//package com.apijava.apijava.service;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.apijava.apijava.Utils.RestTem;
//import com.apijava.apijava.Utils.Tools;
//import com.apijava.apijava.domain.ApiInfo;
//import com.apijava.apijava.domain.ApiTestResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//
//import static com.apijava.apijava.Utils.Tools.getGameID;
//
//@Slf4j
//@Service
//public class AccountService {
//    private final ApiInfoService apiInfoService;
//    private final ApiTestResultService apiTestResultService;
//    private final Tools tools;
//
//    private RestTemplate template;
//    private UserLogin userLogin;
//    private Environment evm;
//    private HttpHeaders requestHeaders = new HttpHeaders();
//    private JSONObject setBody = new JSONObject();
//    private ResponseEntity<String> response;
//
//    private AccountService(ApiInfoService apiInfoService, ApiTestResultService apiTestResultService, Tools exchangeTools, Environment evm, UserLogin userLogin) {
//        this.apiInfoService = apiInfoService;
//        this.apiTestResultService = apiTestResultService;
//        this.tools = exchangeTools;
//        template = new RestTemplate();
//        this.userLogin = userLogin;
//        this.evm = evm;
//    }
//
//    private List<String> login() {
//        return userLogin.login();
//    }
//
//    private String getToken() {
//        return login().get(1);
//    }
//
//    private String getUserName() {
//        return login().get(0);
//    }
//
//
//    /**
//     * user.1.彩种收藏保存接口
//     */
//    public void addBookmarks() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/account/profile/addBookmarks?access_token=XXXX";
//        requestHeaders.clear();
//        String headerStr = "Content-Type: application/json,Accept: */*";
//        setBody.clear();
//        setBody.put("gameId", getGameID());
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User01.1001彩种收藏保存接口-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("userAddBookmarks");
//        apiInfoService.insert(apiInfo);
//        getPostResponse(apiInfo);
//    }
//
//    /**
//     * user.2.用户彩种收藏删除接口
//     */
//    public void delBookmarks() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/webapi/account/profile/delBookmarks?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "*/*");
//        String headerStr = "Content-Type: application/json,Accept: */*";
//
//        setBody.clear();
//        setBody.put("gameId", getGameID());
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("delete");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User02.1001用户彩种收藏删除接口-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("userDelBookmarks");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.DELETE, apiInfo);
//    }
//
//    /**
//     * user.3.用户彩种收藏获取接口
//     */
//    public void getBookmarks() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/account/profile/getBookmarks?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Accept: application/json";
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("get");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User03.1001用户彩种收藏获取接口-正向用例");
//        apiInfo.setStatus_code(200);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("userGetBookmarks");
//        apiInfoService.insert(apiInfo);
//        getGetResponse(apiInfo);
//    }
//
//    /**
//     * user.4.代理创建下层用户
//     */
//    public void addAgentUser() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri =  "/api/v1/account/webapi/account/users?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", " application/json");
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Content-Type: application/json,Accept: application/json";
//        setBody.clear();
//        setBody.put("username", getRandomString());
//        setBody.put("password", "123456");
//        setBody.put("memberType", "AGENT");
//        setBody.put("prizeGroup", "1950");
//        setBody.put("realName", "autotest");
//        setBody.put("phoneNumber", "13996323363");
//        setBody.put("qq", "258845215");
//        setBody.put("email", "258845215@qq.com");
//        log.info(baseUrl +  uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User04.1001代理创建下层用户-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("agentCreateLowUserAccount");
//        apiInfoService.insert(apiInfo);
//        getPostResponse(apiInfo);
//    }
//
//    /**
//     * user.5.用户更新基本信息
//     */
//    public void updateUserInfo() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/account/users/?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Content-Type: application/json,Accept: application/json";
//        setBody.clear();
//        setBody.put("email", "321232132@qq.com");
//        setBody.put("identityNumber", "110110190005210101");
//        setBody.put("memberType", "AGENT");
//        setBody.put("nickname", "updateName");
//        setBody.put("phoneNumber", "13636363256");
//        setBody.put("prizeGroup", 1978);
//        setBody.put("qq", "321232132");
//        setBody.put("username", getUserName());
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("put");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User05.1001用户更新基本信息-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("agentCreateLowUserAccount");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * user.6.修改用户取款密码
//     */
//    public void encryptSecurityPassword() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/account/users/change/encryptPassword?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Content-Type: application/json,Accept: application/json";
//        setBody.clear();
//        setBody.put("mode", "SECURITY_PASSWORD");
//        String newpassword = "NmFmMDQ4ZGE3MDc5NzFjNWFmM2RkZWFmYzhlMDkyZDM4Mzc4ZGEwOWFlZTVhMjdjNzYyMzNlNWQ5YWZjYjRhNGY3YjdjYjJmZGYyNjQ4YTBmY2VhMTYyZGMzY2E3ZDc4NWVjZWIzZDQwMDYxOTI2YWE1YzQ4YTA5ODI1ODg1OWExYTFjZGRkZDliOGE1MjA4MjNhMTk2NDkzZWE3MTMwNjM4OTY4MjMwMzJlMmQ1ZTAyYTI2MDczMjdmMmM0M2I0ZTdiZWI2ZDE3OWJkN2NiZjQ0NTIyZDA0ZWEwMmExOTU3MDYxMDFkNmFkNjQ1MjcxNzM1OWI3NTZlYzQyMWRmOA==";
//        setBody.put("newPassword", newpassword);
//        String password = "NTIwMmY1MDIyZjcwNGU3MmM2NmU4MGY4NjM0MGFlYzdlZTVkZGNmM2Q0NDcwYWYyMDM3MThjMzVkN2U1NDQ5YjhmYTcxNzc2ODAyYmRkMmQ3MDU1YjQwNGQ2Y2RkMTIwZTNiYTk4ZDdjMjdiNWE3YzlhOWJlMjEzNWYxNjk1YmExOTVkMjY5NmNmOGRjN2M3ZjVhZTkxNjc3NmVmMTczOThmMDJhMDc4YjQ2N2Y4ZTBlMzkyNTI1OTAwNzA2NmEyYTU2NGViNzliNzZiZjg2YTFkNmYzOTc4ZGYxMjUwYTJlYmRkZjQzODRhNTliNmZiNThlNjYzMmIwMDAxMGQwYQ==";
//        setBody.put("password", password);
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User06.1001修改用户取款密码-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("changeEncryptSecurityPassword");
//        apiInfoService.insert(apiInfo);
//        getPostResponse(apiInfo);
//    }
//
//    /**
//     * user.7.修改用户登录密码
//     */
//    public void encryptLoginPassword() {
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/account/users/change/encryptPassword?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Content-Type: application/json,Accept: application/json";
//        setBody.clear();
//        setBody.put("mode", "PASSWORD");
//        String newpassword = "MDFlYTE1NzU2ZTNiMGE5Y2RjZDZiMDc3NDJkZjljNDBkMTdkNjFhZDM0NWUxODRkNTQ1YzM4ZmI5Mzc4MjA0NWQxZjA0YjA5OTUyOWUxMTVmMGIyNDNmNjMzODJhOWFlZmE0ODkzMjU1YjFiMDEyNzJkMzkwMWI4YzE1OGFmZmZlNDk5YWQ0N2U4MDYzY2IxN2E4NzQ0MDZjODRmZWZhNTc3ZmUwYzVmOGNhY2M5ODQ3M2YwM2NiNGI2Y2FkOTcxZmQ1NWJhMjYyYjliYzQ0NWQ3Y2I4MmQwZTIwNDJiNDUxNzA5MDI2MWE3YjhhZGRiNjQ4M2RkZmQ3MDk5OWE1Yg==";
//        setBody.put("newPassword", newpassword);
//        String password = "MTQ5MjMzOTg0Njc4Mzg5YjM1Mzg5NzQxYmZhOWY1MGIzNGRlMTcxOTY5NTA0Mzc3NWYyMGNlNjNjZjNmN2I1NjMyOWMxMzA1MzJmYzhkN2QwNTQ5YTMyYzQ1NjBlODZkYWRlY2Y2M2QzZDhjMDUxYjA4YzhkODkzYjY3YzI0ODk4ZjU3ZTM2NmZiODZkYmQwMmYwMTNmOGE4MWJlZGVhZjNmZjNmNWJkMzg1ZGM0ZWRlZjJiZjAxZGRlODIzNTkyNjg4OGMxZDM5OTNmMDM2Y2ZkNDg2YmQzNjY2MGRjZDZjNDBhMWI1M2VlOTMxZDBlYmY4YjE3NDU1ZDk3ZGJiMg==";
//        setBody.put("password", password);
//        log.info(baseUrl + uri);
//        ApiInfo apiInfo = new ApiInfo();
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("User07.1001修改用户登录密码-正向用例");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("changeEncryptLoginPassword");
//        apiInfoService.insert(apiInfo);
//        getPostResponse(apiInfo);
//    }
//
//    /**
//     * 用户签到
//     */
//    public void signIn() {
//        String baseUrl = evm.getProperty("account_url");
//        String uri = "/api/v1/account/webapi/operate/users/signIn?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Content-Type: application/json,Accept: application/json";
//        setBody.clear();
//        log.info(baseUrl + uri);
//        ApiInfo apiInfo = new ApiInfo();
//        apiInfo.setUri(uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("user_account");
//        apiInfo.setExpectResult("Failed");
//        apiInfo.setRemark("User08.2001用户签到-签到失败");
//        apiInfo.setStatus_code(204);
//        apiInfo.setPressFlag(false);
//        apiInfo.setGatlingTestName("changeEncryptLoginPassword");
//        apiInfoService.insert(apiInfo);
//        getPostResponse(apiInfo);
//    }
//
//
//    /**
//     * adminCMS.1.厅主或子账号发送消息
//     */
//    public void adminSendCMSMessage() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("admin_cms");
//        String uri = "/api/v1/cms/Message?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "*/*");
//        String headerStr = "Content-Type: application/json, Accept: */*";
//        setBody.clear();
//        setBody.put("content", "接口测试消息发送");
//        setBody.put("displayTime", 0);
//        setBody.put("groupType", "1");
//        setBody.put("title", "autoTest");
//        setBody.put("type", "NORMAL");
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("admin_cms");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setStatus_code(204);
//        apiInfo.setRemark("AdminCMS01.1001厅主或子账号发送消息-正向用例");
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("adminSendMessage");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * adminCMS.2.厅主或子账号查询消息
//     */
//    public void adminGetCMSMessage() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("admin_cms");
//        String uri = "/api/v1/cms/Message/?start=0&pageSize=20&access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Accept: application/json";
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("get");
//        apiInfo.setSystemName("admin_cms");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("AdminCMS02.1001厅主或子账号查询消息-正向用例");
//        apiInfo.setStatus_code(200);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("adminGetMessage");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * adminCMS.3.厅主或子账号发送消息
//     */
//    public void adminUpdateCMSMessage() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("admin_cms");
//        String uri = "/api/v1/cms/Message/update?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Content-Type", "application/json");
//        requestHeaders.add("Accept", "*/*");
//        String headerStr = "Content-Type: application/json, Accept: */*";
//        setBody.clear();
//        setBody.put("content", "接口测试消息发送");
//        setBody.put("displayTime", 0);
//        setBody.put("groupType", "1");
//        setBody.put("title", "autoTest");
//        setBody.put("type", "NORMAL");
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("post");
//        apiInfo.setSystemName("admin_cms");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setStatus_code(204);
//        apiInfo.setRemark("AdminCMS03.1001厅主或子账号更新消息-正向用例");
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("adminUpdateMessage");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * userCMS.1.会员查询消息
//     */
//    public void userGetCMSMessage() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("admin_cms");
//        String uri = "/api/v1/cms/Message/playerMessages?start=0&pageSize=20&access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Accept: application/json";
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("get");
//        apiInfo.setSystemName("user_cms");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("UserCMS01.1001会员查询消息-正向用例");
//        apiInfo.setStatus_code(200);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("userGetMessage");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * userCMS.2.会员是否有新消息
//     */
//    public void userGetNewCMSMessage() {
//        ApiInfo apiInfo = new ApiInfo();
//        String baseUrl = evm.getProperty("admin_cms");
//        String uri = "/api/v1/cms/Message/playerMessagesStatus?access_token=XXXX";
//        requestHeaders.clear();
//        requestHeaders.add("Accept", "application/json");
//        String headerStr = "Accept: application/json";
//        log.info(baseUrl + uri);
//        apiInfo.setBaseUrl(baseUrl);
//        apiInfo.setUri(uri);
//        apiInfo.setSetBody(setBody.toString());
//        apiInfo.setHeader(headerStr);
//        apiInfo.setHttpMethod("get");
//        apiInfo.setSystemName("user_cms");
//        apiInfo.setExpectResult("Success");
//        apiInfo.setRemark("UserCMS02.1001会员查询是否有新消息-正向用例");
//        apiInfo.setStatus_code(200);
//        apiInfo.setPressFlag(true);
//        apiInfo.setGatlingTestName("userGetIfHaveMessage");
//        apiInfoService.insert(apiInfo);
//        getResponse(HttpMethod.PUT, apiInfo);
//    }
//
//    /**
//     * http-Post请求结果处理
//     */
//    private void getPostResponse(ApiInfo apiInfo) {
//        ApiTestResult apiTestResult;
//        apiTestResult = tools.toApiTestResult(apiInfo);
//        try {
//            response = template.postForEntity((apiInfo.getBaseUrl() + apiInfo.getUri()), setBody, String.class);
//        } catch (HttpClientErrorException e) {
//            getExceptionResponse(apiInfo, apiTestResult, e);
//            return;
//        }
//        tools.responseBodyToApiTestResult(apiTestResult, response);
//        apiTestResultService.insert(apiTestResult);
//    }
//
//    /**
//     * http-Get请求结果处理
//     */
//    private void getGetResponse(ApiInfo apiInfo) {
//        ApiTestResult apiTestResult;
//        apiTestResult = tools.toApiTestResult(apiInfo);
//        try {
//            response = template.getForEntity((apiInfo.getBaseUrl() + apiInfo.getUri()), String.class);
//        } catch (HttpClientErrorException e) {
//            ()getExceptionResponse(apiInfo, apiTestResult, e);
//            return;
//        }
//        tools.responseBodyToApiTestResult(apiTestResult, response);
//        apiTestResultService.insert(apiTestResult);
////        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + apiInfo.getUrl();
//    }
//
//    /**
//     * http-Delete请求结果处理
//     * http-put请求结果处理
//     */
//    private void getResponse(HttpMethod httpMethod, ApiInfo apiInfo) {
//        ApiTestResult apiTestResult;
//        apiTestResult = tools.toApiTestResult(apiInfo);
//        HttpEntity<String> requestEntity = new HttpEntity<>(setBody.toString(), requestHeaders);
//        log.info((apiInfo.getBaseUrl() + apiInfo.getUri()));
//        ResponseEntity<String> response;
//        try {
//            response = template.exchange((apiInfo.getBaseUrl() + apiInfo.getUri()), httpMethod, requestEntity, String.class);
//        } catch (HttpClientErrorException e) {
//            getExceptionResponse(apiInfo, apiTestResult, e);
//            return;
//        }
//        tools.responseBodyToApiTestResult(apiTestResult, response);
//        apiTestResultService.insert(apiTestResult);
////        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + apiInfo.getUrl();
//    }
//
//    /**
//     * 获取随机创建的username
//     */
//    private static String getRandomString() {
//        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            int number = random.nextInt(62);
//            sb.append(str.charAt(number));
//        }
//        return sb.toString();
//    }
//
//
//  /*  public static void main(String[] args) {
//        log.info(getGameID());
//    }*/
//
//}
