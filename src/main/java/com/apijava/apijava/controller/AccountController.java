package com.apijava.apijava.controller;

import com.apijava.apijava.domain.APIResult;
import com.apijava.apijava.domain.TestResultSummary;
import com.apijava.apijava.service.AccountService;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/test")
    public ModelAndView entitlement() {
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger total = new AtomicInteger();
        List<List<String>> resultList = new ArrayList<>();
        List<String> result = Arrays.asList(accountService.getBookmarks().split(",,,"));
        resultList.add(result);
        result = Arrays.asList(accountService.entitlementOne().split(",,,"));
        resultList.add(result);
        result = Arrays.asList(accountService.addUser().split(",,,"));
        resultList.add(result);
        result = Arrays.asList(accountService.getBookmarks().split(",,,"));
        resultList.add(result);
        result = Arrays.asList(accountService.getBookmarks().split(",,,"));
        resultList.add(result);
        result = Arrays.asList(accountService.getBookmarks().split(",,,"));
        resultList.add(result);
        List<APIResult> apiResults = new ArrayList<>();
        List<TestResultSummary> testResultSummaries = new ArrayList<>();

        resultList.forEach(list -> {
            if (list.get(0).startsWith("2")) {
                success.addAndGet(1);
                total.addAndGet(1);
            } else {
                failed.addAndGet(1);
                total.addAndGet(1);
            }
            APIResult APIResult = new APIResult(String.valueOf(total.get()),list.get(2), list.get(1), list.get(0));
            apiResults.add(APIResult);
        });

        TestResultSummary testResultSummary = new TestResultSummary(total, success, failed);
        testResultSummaries.add(testResultSummary);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("apiResults", apiResults);
        modelAndView.addObject("testResultSummaries", testResultSummaries);
        return modelAndView;
    }


}
