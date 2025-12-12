package com.example.capstone3.Repository;

import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolationRepository extends JpaRepository<Violation,Integer> {
    Violation findViolationById(Integer id);

    List<Violation> findAllByKitchen (Kitchen kitchen);
    List<Violation> findViolationsByInspectorId(Integer inspectorId);

    int countAllByKitchen(Kitchen kitchen);

}
