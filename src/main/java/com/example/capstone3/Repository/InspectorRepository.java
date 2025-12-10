package com.example.capstone3.Repository;

import com.example.capstone3.Model.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector,Integer> {
    Inspector findInspectorById(Integer id);
}
