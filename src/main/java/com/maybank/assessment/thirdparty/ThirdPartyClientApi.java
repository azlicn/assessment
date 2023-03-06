package com.maybank.assessment.thirdparty;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ThirdPartyClientApi {

    private final RestTemplate restTemplate;
    private final String thirdPartyApiUrl;
    private final String thirdPartyApiAcceptHeader;

    public ThirdPartyClientApi(RestTemplateBuilder restTemplateBuilder,
                               @Value("${thirdPartyApiUrl}") String thirdPartyApiUrl,
                               @Value("${thirdPartyApiAcceptHeader}") String thirdPartyApiAcceptHeader) {
        this.restTemplate = restTemplateBuilder.build();
        this.thirdPartyApiUrl = thirdPartyApiUrl;
        this.thirdPartyApiAcceptHeader = thirdPartyApiAcceptHeader;
    }

    public String callThirdPartyApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", thirdPartyApiAcceptHeader);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> response = restTemplate.exchange(thirdPartyApiUrl, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject= new JSONObject(response.getBody());
        Object data = jsonObject.get("data");
        JSONObject jsonData= new JSONObject(data.toString());
        Object opr = jsonData.get("new_opr_level");

        return opr.toString();
    }
}

