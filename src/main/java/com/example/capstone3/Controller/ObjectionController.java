package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.DTO_in.AddObjectionDTO;
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
//12
    @PutMapping("/approve/{objectionId}")
    public ResponseEntity<?> approveObjection(@PathVariable Integer objectionId,
                                              @RequestBody Objection objection) {
        objectionService.approveObjection(objectionId, objection.getResponse());
        return ResponseEntity.status(200).body(new ApiResponse("Objection approved successfully"));
    }

//13
    @PutMapping("/reject/{objectionId}")
    public ResponseEntity<?> rejectObjection(@PathVariable Integer objectionId,
                                             @RequestBody Objection objection) {
        objectionService.rejectObjection(objectionId, objection.getResponse());
        return ResponseEntity.status(200).body(new ApiResponse("Objection rejected successfully"));
    }




    @GetMapping("/approval-report")
    public ResponseEntity getApprovalReport() {

        ObjectionReportDTO report = objectionService.getObjectionApprovalReport();

        return ResponseEntity.status(200).body(report);
    }



}