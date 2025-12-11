package com.example.capstone3.Service;

import com.example.capstone3.DTO_out.MealValidationResultDTO;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Model.Meal;
import com.example.capstone3.Repository.HealthRecordRepository;
import com.example.capstone3.Repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIMealValidationService {

    private final OpenAiChatModel openAiChatModel;
    private final MealRepository mealRepository;
    private final HealthRecordRepository healthRecordRepository;

    public MealValidationResultDTO validateMealForPilgrim(Integer mealId, Integer pilgrimId) {

        Meal meal = mealRepository.findMealById(mealId);
        HealthRecord record = healthRecordRepository.findHealthRecordById(pilgrimId);

        if (meal == null || record == null) {
            return new MealValidationResultDTO(false, "Unknown", "Meal or Health Record not found");
        }

        String promptText =
                "You are a medical nutrition expert.\n" +
                        "Decide if the meal is safe for the pilgrim based ONLY on the explicit ingredients listed below.\n" +
                        "Do NOT assume cross-contamination.\n" +
                        "Do NOT assume hidden ingredients.\n" +
                        "Do NOT make precautionary assumptions.\n" +
                        "Base your decision strictly on direct medical impact.\n" +
                        "If an allergen is NOT explicitly mentioned in the ingredients, then it is NOT present.\n\n" +

                        "Return ONLY in this exact JSON format:\n" +
                        "{\n" +
                        "  \"allowed\": true/false,\n" +
                        "  \"riskLevel\": \"Low / Medium / High\",\n" +
                        "  \"reason\": \"Short clear medical reason based only on actual ingredients\"\n" +
                        "}\n\n" +

                        "Meal ingredients: " + meal.getIngredients() + "\n\n" +

                        "Pilgrim health record:\n" +
                        "Diabetic: " + record.isDiabetic() + "\n" +
                        "High Blood Pressure: " + record.isHighBloodPressure() + "\n" +
                        "Food Allergy: " + record.isFoodAllergy() + "\n" +
                        "Allergy Details: " + record.getAllergyDetails() + "\n" +
                        "Heart Disease: " + record.isHeartDisease() + "\n" +
                        "Asthma: " + record.isAsthma() + "\n\n" +

                        "Analyze and return the decision now.";

        OpenAiChatOptions options = OpenAiChatOptions.builder().build();
        Prompt prompt = new Prompt(promptText, options);

        ChatResponse response = openAiChatModel.call(prompt);
        String aiResult = response.getResult().getOutput().getText();

        boolean allowed = aiResult.contains("\"allowed\": true");

        String riskLevel =
                aiResult.contains("High") ? "High" :
                        aiResult.contains("Medium") ? "Medium" : "Low";

        String reason = aiResult
                .replaceAll("(?s).*\"reason\":\\s*\"", "")
                .replaceAll("\".*", "")
                .replaceAll("[\\n{}]", "")
                .trim();

        return new MealValidationResultDTO(allowed, riskLevel, reason);
    }
}
