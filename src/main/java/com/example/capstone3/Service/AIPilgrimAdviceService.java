package com.example.capstone3.Service;

import com.example.capstone3.DTO_out.PilgrimAdviceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIPilgrimAdviceService {

    private final org.springframework.ai.openai.OpenAiChatModel openAiChatModel;

    public PilgrimAdviceDTO getGeneralPilgrimAdvice(String lang) {

        String prompt = """
                You are an AI assistant integrated into a Hajj guidance system.
                
                Your task is to generate GENERAL advice for pilgrims.
                This advice is NOT personalized and does NOT depend on any health record.
                
                Rules:
                - Generate exactly 5 useful recommendations for pilgrims.
                - Recommendations must be general and applicable to any pilgrim.
                - Consider common Hajj conditions such as crowding, heat, physical fatigue, long walking, and general safety.
                - Respond ONLY in the language specified.
                - Output must be STRICTLY valid JSON.
                - The JSON format must be an array of strings.
                - Do NOT add explanations, titles, markdown, or extra text.
                - Do NOT include medical diagnosis.
                
                IMPORTANT:
                - If you do NOT recognize or understand the requested language, return EXACTLY this JSON instead:
                [
                  "The requested language is not supported or not recognized."
                ]
                
                Language: %s
                
                Return ONLY the JSON array now.
                
                """.formatted(lang);

        ChatResponse response = openAiChatModel.call(new Prompt(prompt));
        String aiText = response.getResult().getOutput().getText().trim();

        ObjectMapper mapper = new ObjectMapper();

        try {
            List<String> advice = mapper.readValue(
                    aiText,
                    mapper.getTypeFactory()
                            .constructCollectionType(List.class, String.class)
            );

            return new PilgrimAdviceDTO(lang, advice);

        } catch (Exception e) {
            return new PilgrimAdviceDTO(
                    lang,
                    List.of(
                            "Follow official Hajj instructions at all times.",
                            "Stay hydrated and take regular breaks.",
                            "Avoid overcrowded areas whenever possible.",
                            "Wear comfortable clothing and footwear.",
                            "Keep personal belongings secure."
                    )
            );
        }
    }
}