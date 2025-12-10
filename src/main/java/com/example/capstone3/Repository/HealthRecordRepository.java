package com.example.capstone3.Repository;

import com.example.capstone3.Model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord,Integer> {
    HealthRecord findHealthRecordById(Integer id);
}
