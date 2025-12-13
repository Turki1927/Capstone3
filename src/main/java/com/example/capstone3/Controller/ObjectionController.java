package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.DTO_in.AddObjectionDTO;
import com.example.capstone3.DTO_in.ObjectionDecisionDTO;
import com.example.capstone3.DTO_out.ObjectionReportDTO;
import com.example.capstone3.Model.Objection;
import com.example.capstone3.Service.ObjectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/objection")
@RequiredArgsConstructor
public class ObjectionController {

    private final ObjectionService objectionService;


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(objectionService.getAll());
    }


    @PostMapping("/add/{violationId}/{kitchenId}")
    public ResponseEntity<?> addObjection(@PathVariable Integer violationId, @PathVariable Integer kitchenId, @RequestBody @Valid AddObjectionDTO addObjectionDTO) {

        objectionService.addObjection(violationId, kitchenId, addObjectionDTO);

        return ResponseEntity.status(200).body(new ApiResponse("Objection added successfully"));
    }


    @PutMapping("/update/{objectionId}")
    public ResponseEntity<?> updateObjection(@PathVariable Integer objectionId, @RequestBody @Valid Objection objection) {
        objectionService.updateObjection(objectionId, objection);
        return ResponseEntity.status(200).body(new ApiResponse("Objection updated successfully"));
    }


    @DeleteMapping("/delete/{objectionId}")
    public ResponseEntity<?> deleteObjection(@PathVariable Integer objectionId) {
        objectionService.deleteObjection(objectionId);
        return ResponseEntity.status(200).body(new ApiResponse("Objection deleted successfully"));
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveObjection(@PathVariable Integer id, @RequestBody @Valid ObjectionDecisionDTO dto) {

        objectionService.approveObjection(id, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Objection approved and violation removed"));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectObjection(@PathVariable Integer id, @RequestBody @Valid ObjectionDecisionDTO dto) {

        objectionService.rejectObjection(id, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Objection rejected"));
    }

    @GetMapping("/approval-report")
    public ResponseEntity getApprovalReport() {

        ObjectionReportDTO report = objectionService.getObjectionApprovalReport();

        return ResponseEntity.status(200).body(report);
    }



}