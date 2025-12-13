package com.example.capstone3.DTO_in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectionDecisionDTO {

    @Size(max = 200, message = "Response must be less than 200 characters")
    private String response;
}
