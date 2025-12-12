package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Pilgrim;
import com.example.capstone3.Service.PilgrimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pilgrim")
@RequiredArgsConstructor
public class PilgrimController {

    private final PilgrimService pilgrimService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(200).body(pilgrimService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPilgrim(@RequestBody @Valid Pilgrim pilgrim){
        pilgrimService.addPilgrim(pilgrim);
        return ResponseEntity.status(200).body(new ApiResponse("Pilgrim added successfully"));
    }


    @PutMapping("/update/{pilgrimId}")
    public ResponseEntity<?> updatePilgrim(@PathVariable Integer pilgrimId,
                                           @RequestBody @Valid Pilgrim pilgrim){
        pilgrimService.updatePilgrim(pilgrimId, pilgrim);
        return ResponseEntity.status(200).body(new ApiResponse("Pilgrim updated successfully"));
    }

    @DeleteMapping("/delete/{pilgrimId}")
    public ResponseEntity<?> deletePilgrim(@PathVariable Integer pilgrimId){
        pilgrimService.deletePilgrim(pilgrimId);
        return ResponseEntity.status(200).body(new ApiResponse("Pilgrim deleted successfully"));
    }
    @GetMapping("/health-record/{pilgrimId}")
    public ResponseEntity<?> getHealthRecord(@PathVariable Integer pilgrimId){
        return ResponseEntity.status(200).body(pilgrimService.getHealthRecord(pilgrimId));
    }
}