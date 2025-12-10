package com.example.capstone3.Repository;

import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    Meal findMealById(Integer id);

    List<Meal> findMealByKitchen(Kitchen kitchen);

    @Query("select count(m) from Meal m where m.kitchen.id = ?1")
    int countByKitchenId (Integer kitchenId);
}
