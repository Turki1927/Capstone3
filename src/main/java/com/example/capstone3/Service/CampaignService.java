package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Pilgrim;
import com.example.capstone3.Repository.CampaignRepository;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.PilgrimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final KitchenRepository kitchenRepository;
    private final PilgrimRepository pilgrimRepository;


    public List<Campaign> getAll() {
        return campaignRepository.findAll();
    }

    public void addCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
    }

    public void updateCampaign(Integer id, Campaign newCampaign) {
        Campaign oldCampaign = campaignRepository.findCampaignById(id);
        if (oldCampaign == null) {
            throw new ApiException("Campaign id not found");
        }

        oldCampaign.setName(newCampaign.getName());
        oldCampaign.setCountry(newCampaign.getCountry());
        oldCampaign.setSupervisorName(newCampaign.getSupervisorName());
        oldCampaign.setSupervisorPhone(newCampaign.getSupervisorPhone());

        campaignRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer id) {
        Campaign campaign = campaignRepository.findCampaignById(id);
        if (campaign == null) {
            throw new ApiException("Campaign id not found");
        }

        campaignRepository.delete(campaign);
    }



//4
    public void assignCampaignToKitchen(Integer campaignId, Integer kitchenId) {
        Campaign campaign = campaignRepository.findCampaignById(campaignId);
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);

        if (campaign == null || kitchen == null) {
            throw new ApiException("Campaign id or Kitchen id not found");
        }


        if (!"Active".equalsIgnoreCase(kitchen.getStatus())) {
            throw new ApiException("Kitchen is not active");
        }


        if (campaign.getKitchen() != null) {
            throw new ApiException("Campaign already assigned to kitchen");
        }
        campaign.setKitchen(kitchen);
        campaignRepository.save(campaign);
    }
//5
    public Set<Pilgrim> getPilgrimsInCampaign(Integer campaignId) {
        Campaign campaign = campaignRepository.findCampaignById(campaignId);
        if (campaign == null) {
            throw new ApiException("Campaign id not found");
        }

        return campaign.getPilgrims();
    }

//6
    public void addPilgrimToCampaign(Integer pilgrimId, Integer campaignId) {
        Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);
        Campaign campaign = campaignRepository.findCampaignById(campaignId);

        if (pilgrim == null || campaign == null) {
            throw new ApiException("Pilgrim id or Campaign id not found");
        }


        if (pilgrim.getCampaign() != null) {
            throw new ApiException("Pilgrim already belongs to another campaign");
        }

        pilgrim.setCampaign(campaign);
        pilgrimRepository.save(pilgrim);
    }


}