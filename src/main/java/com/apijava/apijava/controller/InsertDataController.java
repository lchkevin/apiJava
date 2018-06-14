package com.apijava.apijava.controller;

import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "addapi",method = RequestMethod.POST)
@Slf4j
public class InsertDataController {

    private final ApiInfoService apiInfoService;

    public InsertDataController(ApiInfoService apiInfoService) {
        this.apiInfoService = apiInfoService;
    }

    @RequestMapping(value = "/apiInfo", method = RequestMethod.POST)
    @ResponseStatus
    public ResponseEntity<ApiInfo> add(@RequestBody()ApiInfo apiInfo) {
        addPressFlag(apiInfo);
        HttpStatus status;
        if (!checkValidity(apiInfo)) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            return new ResponseEntity<>(apiInfo, status);
        }

        if (apiInfo.getSetBody().equals("")) apiInfo.setSetBody(null);
        apiInfoService.insert(apiInfo);
        status = HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(apiInfo, status);
    }
    private void addPressFlag(ApiInfo apiInfo) {
        if (apiInfo.getGatlingTestName() == null) {
            apiInfo.setPressFlag(false);
        }else {
            apiInfo.setPressFlag(true);
        }

    }

    private boolean checkValidity(ApiInfo apiInfo) {
        String errorStr = null;
        boolean baseUrl_flag = !apiInfo.getBaseUrl().equals("");
        if (!baseUrl_flag) {
            errorStr += "BaseUrl 为空\n";
        }
        boolean evm_flag = !apiInfo.getEvm().equals("");
        if (!evm_flag) {
            errorStr += "测试环境不能为空\n";
        }
        boolean expect_result_flag = !apiInfo.getExpectResult().equals("");
        boolean gatlingName_flag = !apiInfo.getGatlingTestName().equals("");
        boolean header_flag = !apiInfo.getHeader().equals("");
        boolean httpMethod_flag = !apiInfo.getHttpMethod().equals("");
        boolean systemName_flag = !apiInfo.getSystemName().equals("");
        boolean uri_flag = !apiInfo.getUri().equals("");

        return baseUrl_flag & evm_flag & expect_result_flag & gatlingName_flag & header_flag & httpMethod_flag & systemName_flag & uri_flag;
    }
}
