package com.apijava.apijava.Utils;

import com.alibaba.fastjson.JSONObject;
import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.domain.ApiTestResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Tools {

    private final static String spliteStr = "&&";

    HttpMethod toHttpMethod(String httpMethodStr) {
        switch (httpMethodStr) {
            case "get":
                return HttpMethod.GET;
            case "post":
                return HttpMethod.POST;
            case "put":
                return HttpMethod.PUT;
            case "delete":
                return HttpMethod.DELETE;
        }
        return null;
    }

    public HttpHeaders toHttpHeaders(JSONObject headerJson) {
        if (headerJson.isEmpty()) return null;
        HttpHeaders requestHeaders = new HttpHeaders();
        headerJson.keySet().forEach(e -> requestHeaders.set(e, String.valueOf(headerJson.get(e))));
        return requestHeaders;
    }

    public JSONObject toJSONObject(String headerStr) {
        if (headerStr.isEmpty()) return null;
        //TODO 添加输入字符串的格式校验
//        if (!headerStr.contains(":")) return null;
        JSONObject jsonObject = new JSONObject();
        Arrays.asList(headerStr.split(",")).forEach(e -> jsonObject.put(Arrays.asList(e.split(":")).get(0).trim(),Arrays.asList(e.split(":")).get(1).trim()));
        return jsonObject;
    }

    JSONObject toJSON(HttpHeaders httpHeaders) {
        if (httpHeaders.isEmpty()) return null;
        JSONObject object = new JSONObject();
        List<String> list = new ArrayList<>();
        return object;
    }


    public ApiTestResult toApiTestResult(ApiInfo apiInfo) {
        ApiTestResult apiTestResult = new ApiTestResult();
        apiTestResult.setBaseUrl(apiInfo.getBaseUrl());
        apiTestResult.setUri(apiInfo.getUri());
        apiTestResult.setRemark(apiInfo.getRemark());
        apiTestResult.setExpectResult(apiInfo.getExpectResult());
        apiTestResult.setHeader(apiInfo.getHeader());
        apiTestResult.setHttpMethod(apiInfo.getHttpMethod());
        apiTestResult.setSetBody(apiInfo.getSetBody());
        apiTestResult.setSystemName(apiInfo.getSystemName());
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

        //        String stime = String.format("%04d%02d%02d%02d%02d%02d%03d", year, month, day, hour, minute, second, millisecond);
        return String.format("%04d-%02d-%02d %02d-%02d-%02d %03d", year, month, day, hour, minute, second, millisecond);
    }

    /**
     * 获取彩种ID
     */
    public static String getGameID() {
        ArrayList<String> gameIDList = new ArrayList<>();
        gameIDList.add("HF_AHD11");
        gameIDList.add("HF_AHK3");
        gameIDList.add("HF_BJ28");
        gameIDList.add("HF_BJ5FC");
        gameIDList.add("HF_BJK3");
        gameIDList.add("HF_BJPK10");
        gameIDList.add("HF_CQKL10F");
        gameIDList.add("HF_CQSSC");
        gameIDList.add("HF_FFK3");
        gameIDList.add("HF_FFPK10");
        gameIDList.add("HF_FFSSC");
        gameIDList.add("HF_FJD11");
        gameIDList.add("HF_GDD11");
        gameIDList.add("HF_GDKL10F");
        gameIDList.add("HF_GXK3");
        Random index = new Random();
        return gameIDList.get(index.nextInt(2));
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
