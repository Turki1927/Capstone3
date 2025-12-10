package com.example.capstone3.DTO_in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecordDTO {

    @NotNull(message = "Pilgrim ID is required")
    private Integer pilgrimId;

    @NotNull(message = "Diabetic status is required")
    private Boolean diabetic;

    @NotNull(message = "High blood pressure status is required")
    private Boolean highBloodPressure;

    @NotNull(message = "Food allergy status is required")
    private Boolean foodAllergy;

    @Size(max = 255, message = "Allergy details must not exceed 255 characters")
    private String allergyDetails;

    @NotNull(message = "Heart disease status is required")
    private Boolean heartDisease;

    @NotNull(message = "Asthma status is required")
    private Boolean asthma;
}

