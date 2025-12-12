package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Service.InspectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inspector")
@RequiredArgsConstructor
public class InspectorController {

    private final InspectorService inspectorService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(inspectorService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInspector(@RequestBody @Valid Inspector inspector) {
        inspectorService.addInspector(inspector);
        return ResponseEntity.status(200).body(new ApiResponse("Inspector added successfully"));
    }

    @PutMapping("/update/{inspectorId}")
    public ResponseEntity<?> updateInspector(@PathVariable Integer inspectorId, @RequestBody @Valid Inspector inspector) {
        inspectorService.updateInspector(inspectorId, inspector);
        return ResponseEntity.status(200).body(new ApiResponse("Inspector updated successfully"));
    }

    @DeleteMapping("/delete/{inspectorId}")
    public ResponseEntity<?> deleteInspector(@PathVariable Integer inspectorId) {
        inspectorService.deleteInspector(inspectorId);
        return ResponseEntity.status(200).body(new ApiResponse("Inspector deleted successfully"));
    }


    @GetMapping("/violations/{inspectorId}")
    public ResponseEntity<?> getViolationsByInspector(@PathVariable Integer inspectorId) {
        return ResponseEntity.status(200).body(inspectorService.getViolationsByInspector(inspectorId));
    }
    @GetMapping("/violations/open/{inspectorId}")
    public ResponseEntity<?> getOpenViolationsByInspector(@PathVariable Integer inspectorId) {
        return ResponseEntity.status(200).body(inspectorService.getOpenViolationsByInspector(inspectorId));
    }
}