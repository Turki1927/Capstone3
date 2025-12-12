package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Service.KitchenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    @GetMapping("/get")
    public ResponseEntity<?> getKitchens () {
        return  ResponseEntity.status(200).body(kitchenService.getKitchens());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addKitchen (@RequestBody @Valid Kitchen kitchen) {
        kitchenService.addKitchen(kitchen);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen added successfully"));
    }


    @PutMapping("/update/{kitchenId}")
    public ResponseEntity<?> updateKitchen (@PathVariable Integer kitchenId, @RequestBody @Valid Kitchen kitchen) {
        kitchenService.updateKitchen(kitchenId, kitchen);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen updated successfully"));
    }


    @DeleteMapping("/delete/{kitchenId}")
    public ResponseEntity<?> deleteKitchen (@PathVariable Integer kitchenId) {
        kitchenService.deleteKitchen(kitchenId);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen deleted successfully"));
    }

    @GetMapping("/get-active-kitchen")
    public ResponseEntity<?> getActiveKitchen () {
        return ResponseEntity.status(200).body(kitchenService.getActiveKitchen());
    }

    @GetMapping("/get-kitchen-violation/{kitchenId}")
    public ResponseEntity<?> getKitchenViolation (@PathVariable Integer kitchenId) {
        return ResponseEntity.status(200).body(kitchenService.getKitchenViolation(kitchenId));
    }


    @GetMapping(("/get-report/{kitchenId}"))
    public ResponseEntity<?> getReport (@PathVariable Integer kitchenId) {
        return ResponseEntity.status(200).body(kitchenService.getReport(kitchenId));
    }


}
