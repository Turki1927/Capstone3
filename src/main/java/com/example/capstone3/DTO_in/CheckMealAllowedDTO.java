package com.example.capstone3.DTO_in;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckMealAllowedDTO {
    private Integer pilgrimId;
    private Integer mealId;
}
