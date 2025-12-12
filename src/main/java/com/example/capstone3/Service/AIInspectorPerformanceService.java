package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO_out.InspectorPerformanceEvaluationDTO;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Repository.InspectorRepository;
import com.example.capstone3.Repository.ViolationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIInspectorPerformanceService {

    private final InspectorRepository inspectorRepository;
    private final ViolationRepository violationRepository;
    private final org.springframework.ai.openai.OpenAiChatModel openAiChatModel;

    public InspectorPerformanceEvaluationDTO evaluateInspectorPerformance(Integer inspectorId) {


        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        if (inspector == null) {
           throw new ApiException("Inspector not found");
        }


        List<Violation> violations = violationRepository.findViolationsByInspectorId(inspectorId);


        StringBuilder violationText = new StringBuilder();

        if (violations.isEmpty()) {
            violationText.append("No violations have been reported by this inspector.");
        } else {
            int index = 1;
            for (Violation v : violations) {
                violationText.append("- Violation ").append(index++).append(":\n")
                        .append("  Type: ").append(v.getType()).append("\n")
                        .append("  Severity: ").append(v.getSeverity()).append("\n")
                        .append("  Notes: ").append(v.getNotes()).append("\n\n");
            }
        }


        String prompt = """
                You are an AI assistant specialized in administrative performance evaluation
                for food safety inspectors in a Hajj monitoring system.

                Your task is to evaluate the PERFORMANCE of a single inspector based on
                the violations they have reported.

                IMPORTANT RULES:
                1) Do NOT evaluate performance based only on the number of violations.
                2) Analyze BOTH quantity and quality.
                3) Consider the following factors carefully:
                   - Number of violations reported.
                   - Severity of violations (minor, moderate, critical).
                   - Clarity, professionalism, and usefulness of the written notes.
                   - Whether the inspector identifies real issues or writes vague reports.
                   - Diversity of violation types (not repeating the same issue blindly).
                4) Inspectors should NOT be penalized for reporting many violations
                   if the reports are detailed, justified, and relevant.
                5) Inspectors SHOULD be rated lower if reports are:
                   - Repetitive
                   - Poorly written
                   - Missing meaningful details
                   - Overly generic or careless

                Your output MUST classify the inspector as EXACTLY ONE of:
                - Excellent
                - Average
                - Weak

                Then provide:
                - A short professional justification for the rating.
                - 2 administrative notes for management.

                Return ONLY the following STRICT JSON format:

                {
                  "performanceLevel": "Excellent | Average | Weak",
                  "justification": "concise professional explanation",
                  "managementNotes": [
                    "note 1",
                    "note 2"
                  ]
                }

                Inspector ID: %d
                Total reported violations: %d

                Violation details:
                %s

                Begin the evaluation now.
                """.formatted(
                inspectorId,
                violations.size(),
                violationText.toString()
        );


        ChatResponse response = openAiChatModel.call(new Prompt(prompt));
        String aiText = response.getResult().getOutput().getText().trim();


        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(aiText);

            String performanceLevel =
                    root.path("performanceLevel").asText("Average");

            String justification =
                    root.path("justification").asText("");

            List<String> managementNotes =
                    mapper.convertValue(
                            root.path("managementNotes"),
                            mapper.getTypeFactory()
                                    .constructCollectionType(List.class, String.class)
                    );

            return new InspectorPerformanceEvaluationDTO(
                    performanceLevel,
                    justification,
                    managementNotes
            );

        } catch (Exception e) {
            return new InspectorPerformanceEvaluationDTO(
                    "Average",
                    "Unable to evaluate inspector performance due to invalid AI response.",
                    List.of("Review inspector data manually.")
            );
        }
    }
}
