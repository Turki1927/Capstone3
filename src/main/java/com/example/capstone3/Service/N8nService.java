package com.example.capstone3.Service;


import com.example.capstone3.DTO_in.CampaignReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class N8nService {
    private static final String N8N_WEBHOOK_URL = "http://localhost:5678/webhook-test/report";

    public void sendCampaignReport(CampaignReportDTO dto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CampaignReportDTO> requestEntity = new HttpEntity<>(dto, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(N8N_WEBHOOK_URL, requestEntity, String.class);
    }
}
