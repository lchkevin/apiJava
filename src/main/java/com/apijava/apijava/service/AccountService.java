package com.apijava.apijava.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public String entitlementAll() {
        String url = evm.getProperty("account_url");
        url = url + "/entitlement/resources/all";
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
        } catch (Exception ignore) {}

        String str = response.getBody();
        return response.getStatusCodeValue() + ",,," + str + ",,," + url;
    }
}
