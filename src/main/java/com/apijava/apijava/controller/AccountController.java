package com.apijava.apijava.controller;

import com.alibaba.fastjson.JSONObject;
import com.apijava.apijava.Utils.RestTem;
import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.domain.APIResult;
import com.apijava.apijava.domain.TestResultSummary;
import com.apijava.apijava.service.ApiInfoService;
import com.apijava.apijava.service.ApiTestResultService;
import com.apijava.apijava.service.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final ApiTestResultService apiTestResultService;
    private final ApiInfoService apiInfoService;
    private final RestTem restTem;
    private UserLogin userLogin;

    public AccountController( ApiTestResultService apiTestResultService, ApiInfoService apiInfoService, RestTem restTem, UserLogin userLogin) {
        this.apiTestResultService = apiTestResultService;
        this.apiInfoService = apiInfoService;
        this.restTem = restTem;
        this.userLogin = userLogin;
    }

    private List<String> login() {
        return userLogin.login();
    }

    private String getToken() {
        return login().get(1);
    }

    @RequestMapping("sqlite")
    public ModelAndView modelAndView() {
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        List<List<String>> resultList = new ArrayList<>();

        resultList.clear();
        //TODO 从sqlite中获取account的接口进行测试
        String testBatch = (new Tools()).getTimeName();
        String systemName = "user_account";
        apiInfoService.findAllBySystemName(systemName).forEach(e -> {
            e.setUri(changeUrlToken(e.getUri()));
            e.setSetBody(changeParams(e.getSetBody()));
            restTem.excute(e,testBatch);
        });
        //TODO 获取测试结果
        apiTestResultService.getByTestBatch(testBatch).forEach(e ->
                resultList.add(Arrays.asList(e.getUri(), e.getRemark(), e.getResponseBody(), String.valueOf(e.getStatus_code()), e.getVerification())));
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

        processTestResultSummary(success, failed, total, resultList, apiResults);

        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(total), String.valueOf(success), String.valueOf(failed));
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        modelAndView.addObject("systemName", systemName);
        return modelAndView;
    }

    private void processTestResultSummary(AtomicInteger success, AtomicInteger failed, AtomicInteger total, List<List<String>> resultList, List<APIResult> apiResults) {
        resultList.forEach(list -> {
            if (list.get(4).startsWith("Success")) {
                success.addAndGet(1);
            } else {
                failed.addAndGet(1);
            }
            total.addAndGet(1);
            APIResult apiResult = new APIResult(String.valueOf(total.get()), list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
            apiResults.add(apiResult);
        });
    }

    @RequestMapping("/test111")
    public ModelAndView entitlement() {
        String time = (new Tools()).getTimeName();
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        List<List<String>> resultList = new ArrayList<>();
//        accountService.addBookmarks();
//        accountService.getBookmarks();
//        accountService.delBookmarks();
//        accountService.addAgentUser();
//        accountService.updateUserInfo();
//        accountService.encryptSecurityPassword();
//        accountService.encryptLoginPassword();
//        accountService.signIn();
//        accountService.adminGetCMSMessage();
//        accountService.adminSendCMSMessage();
//        accountService.adminUpdateCMSMessage();
//        accountService.userGetCMSMessage();
//        accountService.userGetNewCMSMessage();
        resultList.clear();
        apiTestResultService.getByCreateTimeAndSystemName(time, "user_account").forEach(e ->
                resultList.add(Arrays.asList(e.getUri(), e.getRemark(), e.getResponseBody(), String.valueOf(e.getStatus_code()), e.getVerification())));
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

        processTestResultSummary(success, failed, total, resultList, apiResults);

        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(total), String.valueOf(success), String.valueOf(failed));
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }

    private String  changeUrlToken(String url) {
        if (url.contains("XXXX")) url = url.replaceAll("XXXX", getToken());
        return url;

    }

    private String changeParams(String bodyStr) {
        if (null == bodyStr) return null;
        JSONObject object = (JSONObject) JSONObject.parse(bodyStr);
            object.keySet().forEach(e -> {
                if (object.get(e).toString().contains("{")) {
                    log.info(e);
                    if (e.contains("username"))
                        object.put(e, Tools.getRandomString());
                }
            });
            return String.valueOf(object);
    }

    /*public static void main(String[] args) {
        String url = "http://192.168.1.93:8010/api/v1/account/webapi/operate/users/signIn?access_token=XXXX";
        String token = "MMMM";
        String body = "{\"qq\":\"258845215\",\"realName\":\"autotest\",\"password\":\"123456\",\"phoneNumber\":\"13996323363\",\"memberType\":\"AGENT\", \"username\":\"${username}\", \"prizeGroup\":\"1950\",\"email\":\"258845215@qq.com\"}";
        String username = "testName";

        if (url.contains("XXXX")) {
            url = url.replace("XXXX", token);
        }
        log.info(url);
    }*/
}
