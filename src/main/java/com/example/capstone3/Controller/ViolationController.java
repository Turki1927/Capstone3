package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Service.ViolationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/violation")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationService violationService;


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(violationService.getAll());
    }

    @PostMapping("/add/{inspectorId}/{kitchenId}")
    public ResponseEntity<?> addViolation(@PathVariable Integer inspectorId, @PathVariable Integer kitchenId, @RequestBody @Valid Violation violation) {

        violationService.addViolation(inspectorId, kitchenId, violation);
        return ResponseEntity.status(200).body(new ApiResponse("Violation added successfully"));
    }


    @PutMapping("/update/{violationId}")
    public ResponseEntity<?> updateViolation(@PathVariable Integer violationId, @RequestBody @Valid Violation violation) {
        violationService.updateViolation(violationId, violation);
        return ResponseEntity.status(200).body(new ApiResponse("Violation updated successfully"));
    }


    @DeleteMapping("/delete/{violationId}")
    public ResponseEntity<?> deleteViolation(@PathVariable Integer violationId) {
        violationService.deleteViolation(violationId);
        return ResponseEntity.status(200).body(new ApiResponse("Violation deleted successfully"));
    }

    @PutMapping("/close/{violationId}")
    public ResponseEntity<?> closeViolation(@PathVariable Integer violationId) {
        violationService.closeViolation(violationId);
        return ResponseEntity.status(200).body(new ApiResponse("Violation closed successfully"));
    }

    @PutMapping("/reopen/{violationId}")
    public ResponseEntity<?> reopenViolation(@PathVariable Integer violationId) {
        violationService.reopenViolation(violationId);
        return ResponseEntity.status(200).body(new ApiResponse("Violation reopened successfully"));
    }
}