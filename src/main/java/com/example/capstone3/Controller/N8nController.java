package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.DTO_in.CampaignReportDTO;
import com.example.capstone3.Service.N8nService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/n8n")
@RequiredArgsConstructor
public class N8nController {

    private final N8nService n8nService;

    @PostMapping("/campaign-report")
    public ResponseEntity<?> sendCampaignReport(@RequestBody @Valid CampaignReportDTO dto) {
        n8nService.sendCampaignReport(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign report sent successfully"));
    }
}