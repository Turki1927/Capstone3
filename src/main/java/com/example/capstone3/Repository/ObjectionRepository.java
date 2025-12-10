package com.example.capstone3.Repository;

import com.example.capstone3.Model.Objection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectionRepository extends JpaRepository<Objection, Integer> {

    Objection findObjectionById(Integer id);


    @Query("SELECT COUNT(o) FROM Objection o")
    long getTotalObjections();

    @Query("SELECT COUNT(o) FROM Objection o WHERE o.status = 'APPROVED'")
    long getApprovedObjections();

    @Query("SELECT COUNT(o) FROM Objection o WHERE o.status = 'REJECTED'")
    long getRejectedObjections();



}