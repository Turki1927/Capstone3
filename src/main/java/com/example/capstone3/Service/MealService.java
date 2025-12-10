package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Meal;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final KitchenRepository kitchenRepository;

    public List<Meal> getMeals () {
        if (mealRepository.findAll().isEmpty()) {
            throw new ApiException("No meals found");
        }
        return mealRepository.findAll();
    }

    public void addMeal (Integer kitchenId, Meal meal) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        if (!kitchen.getStatus().equals("Active")) {
            throw new ApiException("Kitchen must be active to add a meal");
        }

        meal.setKitchen(kitchen);
        mealRepository.save(meal);
    }

    public void updateMeal (Integer mealId, Meal meal) {
        Meal oldMeal = mealRepository.findMealById(mealId);
        if (oldMeal == null) {
            throw new ApiException("Meal not found");
        }

        oldMeal.setName(meal.getName());
        oldMeal.setCategory(meal.getCategory());
        oldMeal.setIngredients(meal.getIngredients());
        mealRepository.save(oldMeal);
    }

    public void deleteMeal (Integer mealId) {
        Meal meal = mealRepository.findMealById(mealId);
        if (meal == null) {
            throw new ApiException("Meal not found");
        }
        mealRepository.delete(meal);
    }


    public List<Meal> getMealsByKitchenId (Integer kitchenId) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }
        List<Meal> meals = mealRepository.findMealByKitchen(kitchen);
        if (meals.isEmpty()) {
            throw new ApiException("No meals found for this kitchen");
        }
        return meals;
    }
}

