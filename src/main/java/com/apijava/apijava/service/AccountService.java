package com.apijava.apijava.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AccountService {

    private RestTemplate template;
    private Environment evm;
    private HttpHeaders requestHeaders = new HttpHeaders();

    private AccountService(Environment evm) {
        template = new RestTemplate();
        this.evm = evm;
    }

    public String getBookmarks() {
        String url = evm.getProperty("account_url");
        url = url + "/webapi/account/profile/getBookmarks?access_token=510f6b8f-2458-4bfc-a334-929a7b386b75";
        requestHeaders.add("Accept", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        log.info(url);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String str = response.getBody();
        return response.getStatusCodeValue() + ",,," + str + ",,," + url;
    }


    public String entitlementOne() {
        String url = evm.getProperty("account_url");
        url = url + "/entitlement/resources/byusername?username=test120701";
        requestHeaders.add("Accept", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        log.info(url);
        ResponseEntity<String> response = null;
        try {
            response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            String status = e.getMessage().trim().substring(0, 3);
            String body = ((HttpClientErrorException) e).getResponseBodyAsString();
            return status+ ",,," + body + ",,," + url;
        }
        String str = response.getBody();
        return response.getStatusCodeValue() + ",,," + str + ",,," + url;
    }
}
