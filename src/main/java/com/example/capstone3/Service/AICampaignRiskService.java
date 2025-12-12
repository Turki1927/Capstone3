package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Repository.CampaignRepository;
import com.example.capstone3.Repository.HealthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AICampaignRiskService {

    private final CampaignRepository campaignRepository;
    private final HealthRecordRepository healthRecordRepository;
    private final OpenAiChatModel openAiChatModel;

    public CampaignRiskDTO getCampaignRisk(Integer campaignId) {

        // تأكيد وجود الحملة
        var campaign = campaignRepository.findCampaignById(campaignId);
        if (campaign == null) {
            throw new RuntimeException("Campaign not found");
        }

        // جلب السجلات الصحية للحملة
        var records = healthRecordRepository.findRecordsByCampaignId(campaignId);

        int diabetic = 0, pressure = 0, allergy = 0, heart = 0, asthma = 0;

        for (var r : records) {
            if (r.isDiabetic()) diabetic++;
            if (r.isHighBloodPressure()) pressure++;
            if (r.isFoodAllergy()) allergy++;
            if (r.isHeartDisease()) heart++;
            if (r.isAsthma()) asthma++;
        }

        // --------- تحديد مستوى الخطورة ---------
        int totalRiskCases = diabetic + pressure + allergy + heart + asthma;

        String riskLevel;
        if (totalRiskCases >= 5) riskLevel = "High";
        else if (totalRiskCases >= 2) riskLevel = "Medium";
        else riskLevel = "Low";

        // --------- طلب توصيات من الـ AI ---------
        String prompt = """
                You are a Hajj campaign health advisor.
                Based on the health summary below, give 3 short recommendations
                for the campaign supervisor.

                Diabetic cases: %d
                High blood pressure: %d
                Allergies: %d
                Heart disease: %d
                Asthma: %d

                Return ONLY a JSON array of recommendations.
                """.formatted(diabetic, pressure, allergy, heart, asthma);

        var response = openAiChatModel.call(new Prompt(prompt));
        var aiText = response.getResult().getOutput().getText();

        List<String> recs = List.of(
                aiText.replace("[", "")
                        .replace("]", "")
                        .replace("\"", "")
                        .split(",")
        );

        return new CampaignRiskDTO(riskLevel, recs);
    }
}
