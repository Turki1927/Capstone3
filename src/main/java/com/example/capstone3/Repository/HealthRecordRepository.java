package com.example.capstone3.Repository;

import com.example.capstone3.Model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord,Integer> {
    HealthRecord findHealthRecordById(Integer id);
    @Query("SELECT h FROM HealthRecord h WHERE h.pilgrim.campaign.id = :campaignId")
    List<HealthRecord> findRecordsByCampaignId(Integer campaignId);


}
