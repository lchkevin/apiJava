package com.apijava.apijava.controller;

import com.alibaba.fastjson.JSONObject;
import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "addapi",method = RequestMethod.POST)
@Slf4j
public class InsertDataController {

    private final ApiInfoService apiInfoService;

    public InsertDataController(ApiInfoService apiInfoService) {
        this.apiInfoService = apiInfoService;
    }

    @RequestMapping(value = "/apiInfo", method = RequestMethod.POST)
    public JSONObject add(@RequestBody()ApiInfo apiInfo) {
        addPressFlag(apiInfo);
        JSONObject response = new JSONObject();
        String errorStr = null;
        if (!checkValidity(apiInfo, errorStr)) {
            response.put("success", "false");
            response.put("msg", errorStr);
            return response;
        }

        if (apiInfo.getSetBody().equals("")) apiInfo.setSetBody(null);
        apiInfoService.insert(apiInfo);
        response.put("success", "true");

        return response;
    }
    private void addPressFlag(ApiInfo apiInfo) {
        if (apiInfo.getGatlingTestName() == null) {
            apiInfo.setPressFlag(false);
        }else {
            apiInfo.setPressFlag(true);
        }

    }

    private boolean checkValidity(ApiInfo apiInfo, String errorStr) {
        boolean baseUrl_flag = !(null == apiInfo.getBaseUrl());
        if (!baseUrl_flag) {
            errorStr += "BaseUrl 为空\n";
        }
        boolean evm_flag = !(null == apiInfo.getEvm());
        if (!evm_flag) {
            errorStr += "测试环境不能为空\n";
        }
        boolean expect_result_flag = !(null == apiInfo.getExpectResult());
        boolean gatlingName_flag = !(null == apiInfo.getGatlingTestName());
        boolean header_flag = !(null == apiInfo.getHeader());
        boolean httpMethod_flag = !(null == apiInfo.getHttpMethod());
        boolean systemName_flag = !(null == apiInfo.getSystemName());
        boolean uri_flag = !(null == apiInfo.getUri());

        return baseUrl_flag & evm_flag & expect_result_flag & gatlingName_flag & header_flag & httpMethod_flag & systemName_flag & uri_flag;
    }
}
