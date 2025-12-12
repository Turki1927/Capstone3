package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Repository.InspectorRepository;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InspectorService {

    private final InspectorRepository inspectorRepository;
    private final KitchenRepository kitchenRepository;
    private final ViolationRepository violationRepository;

    public List<Inspector> getAll() {
        return inspectorRepository.findAll();
    }

    public void addInspector(Inspector inspector) {
        inspectorRepository.save(inspector);
    }

    public void updateInspector(Integer id, Inspector inspector) {
        Inspector oldInspector = inspectorRepository.findInspectorById(id);
        if (oldInspector == null) {
            throw new ApiException("Inspector id not found");
        }

        oldInspector.setName(inspector.getName());
        oldInspector.setPhoneNumber(inspector.getPhoneNumber());
        inspectorRepository.save(oldInspector);
    }

    public void deleteInspector(Integer id) {
        Inspector inspector = inspectorRepository.findInspectorById(id);
        if (inspector == null) {
            throw new ApiException("Inspector id not found");
        }
        inspectorRepository.delete(inspector);
    }
    public Set<Violation> getViolationsByInspector(Integer inspectorId) {
        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        if (inspector == null) {
            throw new ApiException("Inspector id not found");
        }

        return inspector.getViolations();
    }
    public Set<Violation> getOpenViolationsByInspector(Integer inspectorId) {
        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        if (inspector == null) {
            throw new ApiException("Inspector id not found");
        }

        Set<Violation> allViolations = inspector.getViolations();
        Set<Violation> openViolations = new HashSet<>();

        for (Violation violation : allViolations) { //query
            if (violation.getStatus().equalsIgnoreCase("OPEN")) {
                openViolations.add(violation);
            }
        }

        return openViolations;
    }
}