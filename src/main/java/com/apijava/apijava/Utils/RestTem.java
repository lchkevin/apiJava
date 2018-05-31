package com.apijava.apijava.Utils;

import com.apijava.apijava.domain.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class RestTem {
    private RestTemplate template;
    private Tools exchange;
    private HttpHeaders requestHeaders = new HttpHeaders();

    @Autowired
    public RestTem(RestTemplate template, Tools exchange) {
        this.template = template;
        this.exchange = exchange;
    }

    public String excute(ApiInfo apiInfo) {
        if (apiInfo.getSetBody().isEmpty()) apiInfo.setSetBody(null);
        requestHeaders.clear();
        requestHeaders = exchange.toHttpHeaders(apiInfo.getHeader());
        HttpEntity<String> requestEntity = new HttpEntity<>(apiInfo.getSetBody(), requestHeaders);

        ResponseEntity<String> response;
        try {
            response = template.exchange(apiInfo.getUrl(), exchange.toHttpMethod(apiInfo.getHttpMethod()), requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = e.getResponseBodyAsString();
            return status + ",,," + body + ",,," + apiInfo.getUrl();
        }
        return response.getStatusCodeValue() + ",,," + response.getBody() + ",,," + apiInfo.getUrl();
    }

}
