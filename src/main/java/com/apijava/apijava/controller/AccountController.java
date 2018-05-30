package com.apijava.apijava.controller;

import com.apijava.apijava.domain.LearnResouce;
import com.apijava.apijava.domain.ScanResult;
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
        result = Arrays.asList(accountService.getBookmarks().split(",,,"));
        resultList.add(result);
        List<LearnResouce> learnList = new ArrayList<>();
        List<ScanResult> scanResults = new ArrayList<>();

        resultList.forEach(list -> {
            if (list.get(0).startsWith("2")) {
                success.addAndGet(1);
                total.addAndGet(1);
            } else {
                failed.addAndGet(1);
                total.addAndGet(1);
            }
            LearnResouce learnResouce = new LearnResouce(String.valueOf(total.get()),list.get(2), list.get(1), list.get(0));
            learnList.add(learnResouce);
        });

        ScanResult scanResult = new ScanResult(total, success, failed);
        scanResults.add(scanResult);
        ModelAndView modelAndView = new ModelAndView("/template");
        modelAndView.addObject("learnList", learnList);
        modelAndView.addObject("scanResults", scanResults);
        return modelAndView;
    }


}
