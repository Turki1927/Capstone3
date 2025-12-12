package com.example.capstone3.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectorPerformanceEvaluationDTO {


    private String performanceLevel;


    private String justification;


    private List<String> managementNotes;
}
