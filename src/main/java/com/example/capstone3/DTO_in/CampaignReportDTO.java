package com.example.capstone3.DTO_in;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignReportDTO {

    @NotNull
    private Integer campaignId;

    private String campaignName;

    private String supervisorName;

    private String issueType;

    private String severity;

    private String details;

    private String reportedAt;
}