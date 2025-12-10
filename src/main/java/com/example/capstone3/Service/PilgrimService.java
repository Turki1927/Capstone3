package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.HealthRecord;
import com.example.capstone3.Model.Pilgrim;
import com.example.capstone3.Repository.CampaignRepository;
import com.example.capstone3.Repository.PilgrimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PilgrimService {

    private final PilgrimRepository pilgrimRepository;
    private final CampaignRepository campaignRepository;

    public List<Pilgrim> getAll(){
        return pilgrimRepository.findAll();
    }

    public void addPilgrim(Pilgrim pilgrim){
        pilgrimRepository.save(pilgrim);
    }

    public void updatePilgrim(Integer id, Pilgrim pilgrim){
        Pilgrim oldPilgrim = pilgrimRepository.findPilgrimById(id);
        if (oldPilgrim == null){
            throw new ApiException("The pilgrim id is not exists");
        }
        oldPilgrim.setName(pilgrim.getName());
        oldPilgrim.setEmail(pilgrim.getEmail());
        oldPilgrim.setPhoneNumber(pilgrim.getPhoneNumber());
        oldPilgrim.setGender(pilgrim.getGender());
        pilgrimRepository.save(oldPilgrim);
    }

    public void deletePilgrim(Integer id){
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(id);
        if (pilgrim == null){
            throw new ApiException("The pilgrim id is not exists");
        }
        pilgrimRepository.delete(pilgrim);
    }


//1
    public void assignPilgrimToCampaign(Integer pilgrimId, Integer campaignId){
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);
        Campaign campaign = campaignRepository.findCampaignById(campaignId);

        if (pilgrim == null || campaign == null){
            throw new ApiException("The pilgrim id or campaign id is not exists");
        }

        if (pilgrim.getCampaign() != null){
            throw new ApiException("This pilgrim already assigned to campaign");
        }
        if (!campaign.getRegistrationOpen()){
            throw new ApiException("The campaign is closed");
        }

        pilgrim.setCampaign(campaign);
        pilgrimRepository.save(pilgrim);
    }
//2
    public void transferPilgrim(Integer pilgrimId, Integer newCampaignId){
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);
        Campaign newCampaign = campaignRepository.findCampaignById(newCampaignId);

        if (pilgrim == null || newCampaign == null){
            throw new ApiException("The pilgrim id or campaign id is not exists");
        }

        if (pilgrim.getCampaign() == null){
            throw new ApiException("This pilgrim does not belong to any campaign");
        }

        if (pilgrim.getCampaign().getId().equals(newCampaignId)){
            throw new ApiException("The pilgrim already in the same campaign");
        }
        if (!newCampaign.getRegistrationOpen()){
            throw new ApiException("The campaign is closed");
        }

        pilgrim.setCampaign(newCampaign);
        pilgrimRepository.save(pilgrim);
    }
//3
    public HealthRecord getHealthRecord(Integer pilgrimId){
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);
        if (pilgrim == null){
            throw new ApiException("The pilgrim id is not exists");
        }
        if (pilgrim.getHealthRecord() == null){
            throw new ApiException("This pilgrim does not have health record");
        }
        return pilgrim.getHealthRecord();
    }


}