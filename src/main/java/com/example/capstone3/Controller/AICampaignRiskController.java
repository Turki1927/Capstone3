package com.example.capstone3.Controller;

import com.example.capstone3.Service.AICampaignRiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/campaign")
@RequiredArgsConstructor
public class AICampaignRiskController {

    private final AICampaignRiskService riskService;

    @GetMapping("/risk/{campaignId}")
    public ResponseEntity<?> getRisk(@PathVariable Integer campaignId) {
        return ResponseEntity.ok(riskService.getCampaignRisk(campaignId));
    }
}
