package com.apijava.apijava.controller;

import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.domain.APIResult;
import com.apijava.apijava.domain.TestResultSummary;
import com.apijava.apijava.service.AccountService;
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

    public AccountController(AccountService accountService, ApiTestResultService apiTestResultService) {
        this.accountService = accountService;
        this.apiTestResultService = apiTestResultService;
    }

    @RequestMapping("/test")
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
        apiTestResultService.getByCreateTime(time, "account-user").forEach(e ->
                resultList.add(Arrays.asList(e.getUrl(), e.getResponseBody(), String.valueOf(e.getStatus_code()), e.getVerification())));
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

        resultList.forEach(list -> {
            if (list.get(3).startsWith("Success")) {
                success.addAndGet(1);
            } else {
                failed.addAndGet(1);
            }
            total.addAndGet(1);
            APIResult apiResult = new APIResult(String.valueOf(total.get()), list.get(0), list.get(1), list.get(2), list.get(3));
            apiResults.add(apiResult);
        });

        TestResultSummary testResultSummary = new TestResultSummary(total, success, failed);
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }


}
