package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Meal;
import com.example.capstone3.Service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meal")
public class MealController {

    private final MealService mealService;


    @GetMapping("/get")
    public ResponseEntity<?> getMeals () {
        return ResponseEntity.status(200).body(mealService.getMeals());
    }


    @PostMapping("/add/{kitchenId}")
    public ResponseEntity<?> addMeal (@PathVariable Integer kitchenId, @RequestBody @Valid Meal meal) {
        mealService.addMeal(kitchenId, meal);
        return ResponseEntity.status(200).body(new ApiResponse("Meal added successfully"));
    }


    @PutMapping("/updated/{mealId}")
    public ResponseEntity<?> updateMeal (@PathVariable Integer mealId, @RequestBody @Valid Meal meal) {
        mealService.updateMeal(mealId, meal);
        return ResponseEntity.status(200).body(new ApiResponse("Meal updated successfully"));
    }


    @DeleteMapping("/delete/{mealId}")
    public ResponseEntity<?> deleteMeal (@PathVariable Integer mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.status(200).body(new ApiResponse("Meal deleted successfully"));
    }


    @GetMapping("/get-meal-by-kitchenId/{kitchenId}")
    public ResponseEntity<?> getMealsByKitchenId (@PathVariable Integer kitchenId) {
        return ResponseEntity.status(200).body(mealService.getMealsByKitchenId(kitchenId));
    }
}
