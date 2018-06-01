package com.apijava.apijava.controller;

import com.apijava.apijava.Utils.RestTem;
import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.domain.APIResult;
import com.apijava.apijava.domain.TestResultSummary;
import com.apijava.apijava.service.AccountService;
import com.apijava.apijava.service.ApiInfoService;
import com.apijava.apijava.service.ApiTestResultService;
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

    private AccountService accountService;
    private final ApiTestResultService apiTestResultService;
    private final ApiInfoService apiInfoService;
    private final RestTem restTem;

    public AccountController(AccountService accountService, ApiTestResultService apiTestResultService, ApiInfoService apiInfoService, RestTem restTem) {
        this.accountService = accountService;
        this.apiTestResultService = apiTestResultService;
        this.apiInfoService = apiInfoService;
        this.restTem = restTem;
    }

    @RequestMapping("sqlite")
    public ModelAndView modelAndView() {

        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        List<List<String>> resultList = new ArrayList<>();
        String token = "425c735b-788f-4b94-81ad-300f510f8292";

        resultList.clear();
        //TODO 从sqlite中获取account的接口进行测试
        String time = (new Tools()).getTimeName();
        apiInfoService.findAllBySystemName("account-user").forEach(e -> {
            if (e.getUrl().contains("XXXX")) e.setUrl(e.getUrl().replaceAll("XXXX", token));
            restTem.excute(e);
        });
        //TODO 获取测试结果
        apiTestResultService.getByCreateTimeAndSystemName(time, "account-user").forEach(e ->
                resultList.add(Arrays.asList(e.getUrl(), e.getRemark(), e.getResponseBody() == null ? null: e.getResponseBody().toString(), String.valueOf(e.getStatus_code()), e.getVerification())));
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

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

        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(total), String.valueOf(success), String.valueOf(failed));
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }

    @RequestMapping("/test111")
    public ModelAndView entitlement() {
        String time = (new Tools()).getTimeName();
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        List<List<String>> resultList = new ArrayList<>();
        accountService.addBookmarks();
        accountService.getBookmarks();
        accountService.delBookmarks();
        accountService.addAgentUser();
        accountService.updateUserInfo();
        accountService.encryptSecurityPassword();
        accountService.encryptLoginPassword();
        accountService.signIn();
        resultList.clear();
        apiTestResultService.getByCreateTimeAndSystemName(time, "account-user").forEach(e ->
                resultList.add(Arrays.asList(e.getUrl(), e.getRemark(), e.getResponseBody() == null ? null: e.getResponseBody().toString(), String.valueOf(e.getStatus_code()), e.getVerification())));
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

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

        TestResultSummary testResultSummary = new TestResultSummary(String.valueOf(total), String.valueOf(success), String.valueOf(failed));
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }

    public static void main(String[] args) {
        String url = "http://192.168.1.93:8010/api/v1/account/webapi/operate/users/signIn?access_token=XXXX";
        String token = "MMMM";
        String body = "{\"qq\":\"258845215\",\"realName\":\"autotest\",\"password\":\"123456\",\"phoneNumber\":\"13996323363\",\"memberType\":\"AGENT\", \"username\":${username}, \"prizeGroup\":\"1950\",\"email\":\"258845215@qq.com\"}";
        String username = "testName";

        if (url.contains("XXXX")) {
            url = url.replace("XXXX", token);
        }
        log.info(url);

        if (body.contains("${")) {
            body.replace("${*}", username);
        }
        log.info(body);
    }

}
