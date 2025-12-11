package com.example.capstone3.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final String INSTANCE_URL = "https://api.ultramsg.com/instance153200/messages/chat";
    private final String TOKEN = "y25vvp34ii67wvo0";

    public void sendMessage(String to, String message) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("token", TOKEN);
        form.add("to", to);
        form.add("body", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(form, headers);

        String response =
                restTemplate.postForObject(INSTANCE_URL, request, String.class);

        System.out.println("WhatsApp Response: " + response);
    }
}
