package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO_out.KitchenReportDTO;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Violation;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.MealRepository;
import com.example.capstone3.Repository.RatingRepository;
import com.example.capstone3.Repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    private final ViolationRepository violationRepository;
    private final MealRepository mealRepository;
    private final RatingRepository ratingRepository;


    public List<Kitchen> getKitchens (){
        if (kitchenRepository.findAll().isEmpty()) {
            throw new ApiException("No Kitchens found");
        }
        return kitchenRepository.findAll();
    }


    public void addKitchen (Kitchen kitchen) {
        kitchen.setStatus("Pending");
        kitchenRepository.save(kitchen);
    }


    public void updateKitchen (Integer kitchenId, Kitchen kitchen) {
        Kitchen oldKitchen = kitchenRepository.findKitchenById(kitchenId);
        if (oldKitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        oldKitchen.setName(kitchen.getName());
        oldKitchen.setInformation(kitchen.getInformation());
        oldKitchen.setOwnerName(kitchen.getOwnerName());
        oldKitchen.setOwnerPhone(kitchen.getOwnerPhone());

        kitchenRepository.save(oldKitchen);
    }


    public void deleteKitchen (Integer kitchenId) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        kitchenRepository.delete(kitchen);
    }


    public List<Kitchen> getActiveKitchen () {
        List<Kitchen> kitchens = kitchenRepository.getActiveKitchen("Active");

        if (kitchens.isEmpty()) {
            throw new ApiException("No kitchens found");
        }
        return kitchens;
    }


    public List<Violation> getKitchenViolation (Integer kitchenId) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        List<Violation> violations = violationRepository.findAllByKitchen(kitchen);
        if (violations.isEmpty()) {
            throw new ApiException("No violations found");
        }
        return violations;
    }


    public KitchenReportDTO getReport (Integer kitchenId) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        int meals = mealRepository.countByKitchenId(kitchenId);
        double avg =  ratingRepository.getAvgByKitchenId(kitchenId);
        int violation = violationRepository.countAllByKitchen(kitchen);

        KitchenReportDTO report = new KitchenReportDTO();
        report.setKitchenId(kitchenId);
        report.setTotalMeal(meals);
        report.setViolationCount(violation);
        report.setAverageRating(avg);

        return report;
    }
}
