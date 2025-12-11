package com.example.capstone3.DTO_in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckMealAllowedDTO {
    private Integer pilgrimId;
    private Integer mealId;
}
