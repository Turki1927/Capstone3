package com.example.capstone3.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRiskDTO {
    private String riskLevel;
    private String reason;
    private List<String> recommendations;
}
