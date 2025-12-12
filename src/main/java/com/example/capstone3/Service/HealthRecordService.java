package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO_in.CheckMealAllowedDTO;
import com.example.capstone3.DTO_in.HealthRecordDTO;
import com.example.capstone3.DTO_out.HealthRiskResponseDTO;
import com.example.capstone3.DTO_out.MealValidationResponseDTO;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Model.Meal;
import com.example.capstone3.Model.Pilgrim;
import com.example.capstone3.Repository.HealthRecordRepository;
import com.example.capstone3.Repository.MealRepository;
import com.example.capstone3.Repository.PilgrimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final PilgrimRepository pilgrimRepository;
    private  final MealRepository  mealRepository;


    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordRepository.findAll();
    }


    public HealthRecord getHealthRecordById(Integer id) {
        HealthRecord record = healthRecordRepository.findHealthRecordById(id);
        if (record == null) {
            throw new ApiException("Health record not found");
        }
        return record;
    }

    // Add new health record using DTO and link it to a pilgrim
    public void addHealthRecord(HealthRecordDTO healthRecordDTO) {

        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(healthRecordDTO.getPilgrimId());

        if (pilgrim == null) {
            throw new ApiException("Pilgrim not found");
        }

        HealthRecord healthRecord = new HealthRecord(null,
                healthRecordDTO.getDiabetic(),
                healthRecordDTO.getHighBloodPressure(),
                healthRecordDTO.getFoodAllergy(),
                healthRecordDTO.getAllergyDetails(),
                healthRecordDTO.getHeartDisease(),
                healthRecordDTO.getAsthma(),
                pilgrim
        );

        // Set the relationship from both sides
        pilgrim.setHealthRecord(healthRecord);
        healthRecordRepository.save(healthRecord);
    }

    // Update existing health record using DTO
    public void updateHealthRecord(Integer id, HealthRecordDTO healthRecordDTO) {

        HealthRecord oldRecord = healthRecordRepository.findHealthRecordById(id);

        if (oldRecord == null) {
            throw new ApiException("Health record not found");
        }

        oldRecord.setDiabetic(healthRecordDTO.getDiabetic());
        oldRecord.setHighBloodPressure(healthRecordDTO.getHighBloodPressure());
        oldRecord.setFoodAllergy(healthRecordDTO.getFoodAllergy());
        oldRecord.setAllergyDetails(healthRecordDTO.getAllergyDetails());
        oldRecord.setHeartDisease(healthRecordDTO.getHeartDisease());
        oldRecord.setAsthma(healthRecordDTO.getAsthma());

        healthRecordRepository.save(oldRecord);
    }

    public void deleteHealthRecord(Integer id) {

        HealthRecord record = healthRecordRepository.findHealthRecordById(id);

        if (record == null) {
            throw new ApiException("Health record not found");
        }

        healthRecordRepository.delete(record);
    }





    public MealValidationResponseDTO checkMealAllowed(CheckMealAllowedDTO dto) {

        //  Get pilgrim
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(dto.getPilgrimId());
        if (pilgrim == null) {
            throw new ApiException("Pilgrim not found");
        }

        //  Get health record
        HealthRecord record = pilgrim.getHealthRecord();
        if (record == null) {
            throw new ApiException("Health record not found for this pilgrim");
        }

        //  Get meal
        Meal meal = mealRepository.findMealById(dto.getMealId());
        if (meal == null) {
            throw new ApiException("Meal not found");
        }

        //  Convert ingredients list to one lowercase text
        String ingredientsText = String.join(" ", meal.getIngredients()).toLowerCase();


        if (record.isFoodAllergy()
                && record.getAllergyDetails() != null
                && ingredientsText.contains(record.getAllergyDetails().toLowerCase())) {

            return new MealValidationResponseDTO(
                    false,
                    "Meal is not allowed because it contains allergen: "
                            + record.getAllergyDetails()
            );
        }

        //  if no allergy conflict found
        return new MealValidationResponseDTO(
                true,
                "Meal is allowed for this pilgrim"
        );
    }



    public HealthRiskResponseDTO classifyHealthRisk(Integer pilgrimId) {

        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);

        if (pilgrim == null) {
            throw new ApiException("Pilgrim not found");
        }

        HealthRecord record = pilgrim.getHealthRecord();

        if (record == null) {
            throw new ApiException("Health record not found for this pilgrim");
        }

        boolean diabetic = record.isDiabetic();
        boolean highBloodPressure = record.isHighBloodPressure();
        boolean heartDisease = record.isHeartDisease();
        boolean asthma = record.isAsthma();

        //  high
        if ((heartDisease && asthma) || (diabetic && highBloodPressure)) {
            return new HealthRiskResponseDTO(
                    "High",
                    "Pilgrim has a high-risk combination of diseases");
        }

        // medium
        if (diabetic || highBloodPressure || heartDisease || asthma) {
            return new HealthRiskResponseDTO(
                    "Medium",
                    "Pilgrim has one chronic disease"
            );
        }

        //  low
        return new HealthRiskResponseDTO(
                "Low",
                "Pilgrim has no chronic diseases"
        );
    }







}
