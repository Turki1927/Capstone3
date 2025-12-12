package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO_out.CampaignRiskDTO;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Repository.CampaignRepository;
import com.example.capstone3.Repository.HealthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AICampaignRiskService {

    private final CampaignRepository campaignRepository;
    private final HealthRecordRepository healthRecordRepository;
    private final org.springframework.ai.openai.OpenAiChatModel openAiChatModel;

    public CampaignRiskDTO getCampaignRisk(Integer campaignId) {

        Campaign campaign = campaignRepository.findCampaignById(campaignId);

        if (campaign == null) {
           throw new ApiException("Campaign not found");
        }


        //  Collect Health Records
        List<HealthRecord> records = healthRecordRepository.findRecordsByCampaignId(campaignId);

        int total = records.size();
        int diabetic = 0, pressure = 0, allergy = 0, heart = 0, asthma = 0;

        for (HealthRecord r : records) {
            if (r.isDiabetic()) diabetic++;
            if (r.isHighBloodPressure()) pressure++;
            if (r.isFoodAllergy()) allergy++;
            if (r.isHeartDisease()) heart++;
            if (r.isAsthma()) asthma++;
        }


        String prompt = """
                You are an expert health risk analyst specializing in Hajj campaign safety.

                Your task is to evaluate the overall medical risk level for an entire campaign of pilgrims.
                You MUST follow these rules strictly:

                - Do NOT classify risk based only on raw counts.
                - ALWAYS consider percentage relative to total participants.
                - Weigh conditions by severity:
                    • Very High Severity: heart disease, severe food allergies
                    • High Severity: diabetes, high blood pressure
                    • Medium Severity: asthma
                - Consider combined conditions (multiple moderate issues may increase risk).
                - Consider the size of the campaign:
                    Example: 5 diabetic cases in a 20-person campaign is riskier than 5 diabetic cases in a 300-person campaign.

                Your goals:
                - Classify the campaign as EXACTLY one of: High, Medium, Low.
                - Provide a short medical explanation for the classification.
                - Provide 3 practical recommendations for the campaign supervisor.

                Return ONLY the following JSON format:

                {
                  "riskLevel": "High | Medium | Low",
                  "reason": "short explanation",
                  "recommendations": ["rec1","rec2","rec3"]
                }

                Data to analyze:
                Total participants: %d
                Diabetic cases: %d
                High blood pressure cases: %d
                Food allergy cases: %d
                Heart disease cases: %d
                Asthma cases: %d

                Begin your analysis now.
                """.formatted(total, diabetic, pressure, allergy, heart, asthma);


        ChatResponse response = openAiChatModel.call(new Prompt(prompt));
        String aiText = response.getResult().getOutput().getText().trim();

        // debug
        System.out.println("AI Response: " + aiText);



        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(aiText);

            String riskLevel = root.path("riskLevel").asText("");
            String reason = root.path("reason").asText("");

            List<String> recommendations = mapper.convertValue(
                    root.path("recommendations"),
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            return new CampaignRiskDTO(riskLevel, reason, recommendations);

        } catch (Exception e) {

            return new CampaignRiskDTO(
                    "",
                    "AI returned invalid JSON: " + aiText,
                    List.of()
            );
        }
    }
}
