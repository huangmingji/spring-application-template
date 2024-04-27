package com.stargazer.springapplicationtemplate.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestApiCaller {
    private RestTemplate restTemplate = new RestTemplate();

    public <T> T callApiGet(String url, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody();
    }

    public <T, R> T callApiPost(String url, R request, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<R> entity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType).getBody();
    }

    public <T, R> T callApiPut(String url, R request, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<R> entity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType).getBody();
    }

    public <T> T callApiDelete(String url, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType).getBody();
    }

    public <T> T callApiPatch(String url, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType).getBody();
    }
}
