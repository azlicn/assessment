package com.maybank.assessment.thirdparty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class CentralBankApiClient {

    private final RestTemplate restTemplate;
    private final String thirdPartyApiUrl;

    public CentralBankApiClient(RestTemplateBuilder restTemplateBuilder,
                                @Value("${thirdPartyApiUrl}") String thirdPartyApiUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.thirdPartyApiUrl = thirdPartyApiUrl;
    }

    public String callThirdPartyApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.BNM.API.v1+json");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> response = restTemplate.exchange(thirdPartyApiUrl, HttpMethod.GET, entity, String.class);
        //ResponseEntity<String> response = restTemplate.getForEntity(thirdPartyApiUrl, String.class);
        return response.getBody();
    }
}

