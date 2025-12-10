package com.example.capstone3.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenReportDTO {

    private Integer kitchenId;
    private Integer totalMeal;
    private Integer violationCount;
    private Double averageRating;
}
