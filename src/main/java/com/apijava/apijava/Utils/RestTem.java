package com.apijava.apijava.Utils;

import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.domain.ApiTestResult;
import com.apijava.apijava.service.ApiTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTem {
    private final RestTemplate template = new RestTemplate();
    private Tools tools;
    private final ApiTestResultService apiTestResultService;
    private HttpHeaders requestHeaders = new HttpHeaders();

    @Autowired
    public RestTem( Tools tools, ApiTestResultService apiTestResultService) {
        this.tools = tools;
        this.apiTestResultService = apiTestResultService;
    }

    public void excute(ApiInfo apiInfo) {
        ApiTestResult apiTestResult;
        apiTestResult = tools.toApiTestResult(apiInfo);
        requestHeaders.clear();
        requestHeaders = tools.toHttpHeaders(tools.toJSONObject(apiInfo.getHeader()));
        HttpEntity<String> requestEntity = new HttpEntity<>(apiInfo.getSetBody(), requestHeaders);

        ResponseEntity<String> response;
        try {
            response = template.exchange(apiInfo.getUrl(), tools.toHttpMethod(apiInfo.getHttpMethod()), requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            getExceptionResponse(apiInfo, apiTestResult, e);
            return;
        }
        tools.responseBodyToApiTestResult(apiTestResult, response);
        apiTestResultService.insert(apiTestResult);
    }

    public void getExceptionResponse(ApiInfo apiInfo, ApiTestResult apiTestResult, HttpClientErrorException e) {
        String status = e.getMessage().trim().substring(0, 3);
        String body = e.getResponseBodyAsString();
        apiTestResult.setStatus_code(Integer.parseInt(status));
        apiTestResult.setResponseBody(body);
        if (apiInfo.getExpectResult().equals("Failed")) {
            apiTestResult.setVerification("Success");
        } else apiTestResult.setVerification("Failed");
        apiTestResultService.insert(apiTestResult);
    }

}
