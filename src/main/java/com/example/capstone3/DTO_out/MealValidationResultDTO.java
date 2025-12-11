package com.example.capstone3.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealValidationResultDTO {

    private Boolean allowed;
    private String riskLevel;
    private String reason;
}
