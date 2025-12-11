package com.example.capstone3.Controller;

import com.example.capstone3.DTO_out.MealValidationResultDTO;
import com.example.capstone3.Service.AIMealValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/meal")
@RequiredArgsConstructor
public class AIMealController {

    private final AIMealValidationService aiMealValidationService;


    @PostMapping("/validate-for-pilgrim")
    public ResponseEntity<MealValidationResultDTO> validateMealForPilgrim(
            @RequestParam Integer mealId,
            @RequestParam Integer pilgrimId) {

        MealValidationResultDTO result =
                aiMealValidationService.validateMealForPilgrim(mealId, pilgrimId);

        return ResponseEntity.status(200).body(result);
    }
}
