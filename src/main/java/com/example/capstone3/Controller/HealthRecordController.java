package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.DTO_in.CheckMealAllowedDTO;
import com.example.capstone3.DTO_in.HealthRecordDTO;
import com.example.capstone3.DTO_out.HealthRiskResponseDTO;
import com.example.capstone3.DTO_out.MealValidationResponseDTO;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Service.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/health-record")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllHealthRecords() {
        List<HealthRecord> records = healthRecordService.getAllHealthRecords();
        return ResponseEntity.status(200).body(records);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHealthRecordById(@PathVariable Integer id) {
        HealthRecord record = healthRecordService.getHealthRecordById(id);
        return ResponseEntity.status(200).body(record);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHealthRecord(@RequestBody @Valid HealthRecordDTO healthRecordDTO) {
        healthRecordService.addHealthRecord(healthRecordDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Health record added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHealthRecord(@PathVariable Integer id,
                                             @RequestBody HealthRecordDTO healthRecordDTO) {
        healthRecordService.updateHealthRecord(id, healthRecordDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Health record updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHealthRecord(@PathVariable Integer id) {
        healthRecordService.deleteHealthRecord(id);
        return ResponseEntity.status(200).body(new ApiResponse("Health record deleted successfully"));
    }



    @PostMapping("/check-meal-allowed")
    public ResponseEntity<?> checkMealAllowed(@RequestBody CheckMealAllowedDTO dto) {

        MealValidationResponseDTO response = healthRecordService.checkMealAllowed(dto);

        return ResponseEntity.status(200).body(response);
    }



    @GetMapping("/classify-risk/{pilgrimId}")
    public ResponseEntity<?> classifyRisk(@PathVariable Integer pilgrimId) {

        HealthRiskResponseDTO response =
                healthRecordService.classifyHealthRisk(pilgrimId);

        return ResponseEntity.status(200).body(response);
    }



}
