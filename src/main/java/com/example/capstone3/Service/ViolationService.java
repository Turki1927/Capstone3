package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Repository.InspectorRepository;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRepository violationRepository;
    private final KitchenRepository kitchenRepository;
    private final InspectorRepository inspectorRepository;


    public List<Violation> getAll() {
        return violationRepository.findAll();
    }


    public void addViolation(Integer inspectorId, Integer kitchenId, Violation violation) {
        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (inspector == null || kitchen == null) {
            throw new ApiException("The inspector id or kitchen id is not exists");
        }
        violation.setDate(LocalDate.now());
        violation.setInspector(inspector);
        violation.setKitchen(kitchen);
        violation.setStatus("open");
        violationRepository.save(violation);
    }

    public void updateViolation(Integer id, Violation violation) {
        Violation oldViolation = violationRepository.findViolationById(id);
        if (oldViolation == null) {
            throw new ApiException("Violation id not found");
        }

        oldViolation.setType(violation.getType());
        oldViolation.setSeverity(violation.getSeverity());
        oldViolation.setNotes(violation.getNotes());

        violationRepository.save(oldViolation);
    }

    public void deleteViolation(Integer id) {
        Violation violation = violationRepository.findViolationById(id);
        if (violation == null) {
            throw new ApiException("Violation id not found");
        }
        violationRepository.delete(violation);
    }


    public void closeViolation(Integer id) {
        Violation violation = violationRepository.findViolationById(id);
        if (violation == null) {
            throw new ApiException("Violation id not found");
        }

        if (violation.getStatus().equalsIgnoreCase("CLOSED")) {
            throw new ApiException("Violation already closed");
        }

        violation.setStatus("CLOSED");
        violationRepository.save(violation);
    }

    public void reopenViolation(Integer id) {
        Violation violation = violationRepository.findViolationById(id);
        if (violation == null) {
            throw new ApiException("Violation id not found");
        }

        if (violation.getStatus().equalsIgnoreCase("open")) {
            throw new ApiException("Violation already open");
        }
        violation.setStatus("open");
        violationRepository.save(violation);
    }

}