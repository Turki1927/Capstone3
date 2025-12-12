package com.example.capstone3.Controller;

import com.example.capstone3.DTO_out.InspectorPerformanceEvaluationDTO;
import com.example.capstone3.Service.AIInspectorPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/inspectors")
@RequiredArgsConstructor
public class AIInspectorPerformanceController {

    private final AIInspectorPerformanceService aiInspectorPerformanceService;

    @PostMapping("/evaluate-performance/{inspectorId}")
    public ResponseEntity<InspectorPerformanceEvaluationDTO> evaluateInspectorPerformance(@PathVariable Integer inspectorId) {

        InspectorPerformanceEvaluationDTO result =
                aiInspectorPerformanceService.evaluateInspectorPerformance(inspectorId);

        return ResponseEntity.ok(result);
    }
}