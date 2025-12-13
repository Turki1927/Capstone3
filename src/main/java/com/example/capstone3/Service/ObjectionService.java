package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO_in.ObjectionDecisionDTO;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.DTO_in.AddObjectionDTO;
import com.example.capstone3.DTO_out.ObjectionReportDTO;


import com.example.capstone3.Model.Objection;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.ObjectionRepository;
import com.example.capstone3.Repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ObjectionService {

    private final ObjectionRepository objectionRepository;
    private final ViolationRepository violationRepository;
    private final KitchenRepository kitchenRepository;


    public List<Objection> getAll() {
        return objectionRepository.findAll();
    }

    public void addObjection(Integer violationId, Integer kitchenId, AddObjectionDTO addObjectionDTO) {

        Violation violation = violationRepository.findViolationById(violationId);
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);

        if (violation == null || kitchen == null) {
            throw new ApiException("Violation id or kitchen id is not exists");
        }

        if (violation.getKitchen() == null || !violation.getKitchen().getId().equals(kitchen.getId())) {
            throw new ApiException("This violation does not belong to this kitchen");
        }

        Set<Objection> violationObjections = violation.getObjections();
        if (violationObjections != null && !violationObjections.isEmpty()) {
            throw new ApiException("This violation already has an objection");
        }

        Objection objection = new Objection();
        objection.setReason(addObjectionDTO.getReason());
        objection.setStatus("OPEN");
        objection.setResponse(null);
        objection.setViolation(violation);
        objection.setKitchen(kitchen);

        objectionRepository.save(objection);
    }

    public void updateObjection(Integer id, Objection objection) {
        Objection oldObjection = objectionRepository.findObjectionById(id);
        if (oldObjection == null) {
            throw new ApiException("Objection id not found");
        }

        oldObjection.setReason(objection.getReason());

        objectionRepository.save(oldObjection);
    }

    public void deleteObjection(Integer id) {
        Objection objection = objectionRepository.findObjectionById(id);
        if (objection == null) {
            throw new ApiException("Objection id not found");
        }

        objectionRepository.delete(objection);
    }

    public void approveObjection(Integer objectionId, ObjectionDecisionDTO dto) {

        Objection objection = objectionRepository.findObjectionById(objectionId);
        if (objection == null) {
            throw new ApiException("Objection not found");
        }

        if (!objection.getStatus().equals("OPEN")) {
            throw new ApiException("Objection already processed");
        }

        objection.setStatus("APPROVED");

        if (dto.getResponse() != null && !dto.getResponse().isBlank()) {
            objection.setResponse(dto.getResponse());
        }

        objectionRepository.save(objection);
    }


    public void rejectObjection(Integer objectionId, ObjectionDecisionDTO dto) {

        Objection objection = objectionRepository.findObjectionById(objectionId);
        if (objection == null) {
            throw new ApiException("Objection not found");
        }

        if (!objection.getStatus().equals("OPEN")) {
            throw new ApiException("Objection already processed");
        }

        objection.setStatus("REJECTED");

        if (dto.getResponse() != null && !dto.getResponse().isBlank()) {
            objection.setResponse(dto.getResponse());
        }

        objectionRepository.save(objection);
    }




    public ObjectionReportDTO getObjectionApprovalReport() {

        long total = objectionRepository.getTotalObjections();
        long approved = objectionRepository.getApprovedObjections();
        long rejected = objectionRepository.getRejectedObjections();

        double rate;

        if (total == 0) {
            rate = 0.0;
        } else {
            rate = ((double) approved / total) * 100;
            rate = Math.round(rate * 100.0) / 100.0; // round to 2 decimals
        }

        return new ObjectionReportDTO(total, approved, rejected, rate);
    }







}