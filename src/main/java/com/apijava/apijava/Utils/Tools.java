package com.apijava.apijava.Utils;

import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.domain.ApiTestResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Component
public class Tools {

    private final static String spliteStr = "&&";

    public HttpMethod toHttpMethod(String httpMethodStr) {
        switch (httpMethodStr) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "DELETE":
                return HttpMethod.DELETE;
        }
        return null;
    }

    HttpHeaders toHttpHeaders(String headerStr) {
        if (headerStr.isEmpty()) return null;
        List<String> listKV = Arrays.asList(headerStr.split(spliteStr));
        HttpHeaders requestHeaders = new HttpHeaders();
        listKV.forEach(e -> requestHeaders.set(Arrays.asList(e.split(":")).get(0).trim(), Arrays.asList(e.split(":")).get(1).trim()));
        return requestHeaders;
    }


    public ApiTestResult toApiTestResult(ApiInfo apiInfo) {
        ApiTestResult apiTestResult = new ApiTestResult();
        apiTestResult.setExpectResult(apiInfo.getExpectResult());
        apiTestResult.setHeader(apiInfo.getHeader());
        apiTestResult.setHttpMethod(apiInfo.getHttpMethod());
        apiTestResult.setSetBody(apiInfo.getSetBody());
        apiTestResult.setSystemName(apiInfo.getSystemName());
        apiTestResult.setUrl(apiInfo.getUrl());
        return apiTestResult;
    }

    public void responseBodyToApiTestResult(ApiTestResult apiTestResult, ResponseEntity<String> response) {
        apiTestResult.setStatus_code(response.getStatusCodeValue());
        apiTestResult.setResponseBody(response.getBody());
        if (apiTestResult.getExpectResult().equals("Success") && response.getStatusCode().is2xxSuccessful()) {
            apiTestResult.setVerification("Success");
        } else if (apiTestResult.getExpectResult().equals("Failed") && (response.getStatusCode().is4xxClientError())) {
            apiTestResult.setVerification("Success");
        } else apiTestResult.setVerification("Failed");
    }

    public String getTimeName() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        String stime = String.format("%04d-%02d-%02d %02d-%02d-%02d %03d", year, month, day, hour, minute, second, millisecond);
//        String stime = String.format("%04d%02d%02d%02d%02d%02d%03d", year, month, day, hour, minute, second, millisecond);
        return stime;
    }
}


   /* public static void main(String[] args) {
        String str = "{" +
                "  \"mode\": \"PASSWORD\"," +
                "  \"newPassword\": \"string\"," +
                "  \"password\": \"string\"" +
                "}";

        System.out.println(str);
        System.out.println(JSONObject.parse(str));


        Arrays.asList(str.split(",")).forEach(e ->System.out.println(e.trim()));

    }*/
