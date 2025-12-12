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
    private final WhatsAppService whatsAppService;

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
        sendViolationToKitchenWhatsApp(violation.getId());


        int violationsCount = violationRepository.countAllByKitchen(kitchen);
        if (violationsCount > 3 ) {
            kitchen.setStatus("Suspended");
            kitchenRepository.save(kitchen);
        }

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


    public void sendViolationToKitchenWhatsApp(Integer violationId) {

        Violation violation = violationRepository.findViolationById(violationId);

        if (violation == null) {
            throw new ApiException("Violation not found");
        }

        Kitchen kitchen = violation.getKitchen();
        Inspector inspector = violation.getInspector();

        String to = "966544593236";

        StringBuilder msg = new StringBuilder();
        msg.append("⚠️ *تم تسجيل بلاغ على مطبخكم*\n\n");

        msg.append("🏠 *اسم المطبخ:* ").append(kitchen.getName()).append("\n");
        msg.append("👤 *اسم المالك:* ").append(kitchen.getOwnerName()).append("\n\n");

        msg.append("📌 *تفاصيل البلاغ:*\n");
        msg.append("🆔 رقم البلاغ: ").append(violation.getId()).append("\n");
        msg.append("📅 تاريخ البلاغ: ").append(violation.getDate()).append("\n");
        msg.append("⚠️ نوع المخالفة: ").append(violation.getType()).append("\n");
        msg.append("⚠️ درجة الخطورة: ").append(violation.getSeverity()).append("\n");
        msg.append("📝 الملاحظات: ").append(violation.getNotes()).append("\n\n");

        msg.append("👮‍♂️ *المراقب:* ").append(inspector.getName()).append("\n\n");

        msg.append("يرجى مراجعة البلاغ واتخاذ الإجراء اللازم.\n");
        msg.append("— إدارة نظام تغذية الحجاج");

        whatsAppService.sendMessage(to, msg.toString());
    }






}