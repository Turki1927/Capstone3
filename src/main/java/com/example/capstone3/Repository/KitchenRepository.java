package com.example.capstone3.Repository;

import com.example.capstone3.Model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Integer> {
    Kitchen findKitchenById(Integer id);

    @Query("select k from Kitchen k where k.status = ?1")
    List<Kitchen> getActiveKitchen (String status);
}
