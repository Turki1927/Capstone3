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
    private  final  EmailService emailService;


    public List<Campaign> getAll() {
        return campaignRepository.findAll();
    }

    public void addCampaign(Campaign campaign) {
        campaign.setRegistrationOpen(true);
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

    public Set<Pilgrim> getPilgrimsInCampaign(Integer campaignId) {
        Campaign campaign = campaignRepository.findCampaignById(campaignId);
        if (campaign == null) {
            throw new ApiException("Campaign id not found");
        }

        return campaign.getPilgrims();
    }


public void addPilgrimToCampaign(Integer pilgrimId, Integer campaignId) {

    Pilgrim pilgrim = pilgrimRepository.findPilgrimById(pilgrimId);
    Campaign campaign = campaignRepository.findCampaignById(campaignId);

    if (pilgrim == null || campaign == null) {
        throw new ApiException("Pilgrim id or Campaign id not found");
    }

    if (pilgrim.getCampaign() != null) {
        throw new ApiException("Pilgrim already belongs to another campaign");
    }
    if (campaign.getRegistrationOpen() == null || !campaign.getRegistrationOpen()) {
        throw new ApiException("Campaign registration is closed");
    }


    pilgrim.setCampaign(campaign);
    pilgrimRepository.save(pilgrim);


    String html = String.format("""
<html>
<head>
<style>
    .container {
        background-color: #f7f7f7;
        padding: 20px;
        border-radius: 10px;
        font-family: Arial, sans-serif;
        color: #333;
        max-width: 600px;
        margin: auto;
        border: 1px solid #ddd;
    }
    .title {
        font-size: 22px;
        font-weight: bold;
        color: #0A6EBD;
        margin-bottom: 15px;
        text-align: center;
    }
    .info {
        font-size: 16px;
        margin-bottom: 12px;
    }
    .footer {
        margin-top: 25px;
        font-size: 14px;
        color: #777;
        text-align: center;
    }
    .highlight {
        background-color: #e8f4ff;
        padding: 5px 8px;
        border-radius: 6px;
        display: inline-block;
        font-weight: bold;
        color: #0A6EBD;
    }
</style>
</head>
<body>

<div class="container">
    <div class="title">🟢 تم تسجيل حاج جديد في الحملة</div>

    <div class="info">اسم الحاج:
        <span class="highlight">%s</span>
    </div>

    <div class="info">رقم الحاج:
        <span class="highlight">%s</span>
    </div>

    <div class="info">اسم الحملة:
        <span class="highlight">%s</span>
    </div>

    <div class="footer">
        نظام تغذية الحجاج © 2025
    </div>
</div>

</body>
</html>
""",
            pilgrim.getName(),
            pilgrim.getId(),
            campaign.getName());


    emailService.sendEmail("turkialhrbi5923@gmail.com", "🟢 تسجيل حاج جديد", html);
}



    public void closeCampaignRegistration(Integer campaignId) {

        Campaign campaign = campaignRepository.findCampaignById(campaignId);

        if (campaign == null) {
            throw new ApiException("Campaign not found");
        }

        if (!Boolean.TRUE.equals(campaign.getRegistrationOpen())) {
            throw new ApiException("Campaign registration is already closed");
        }

        campaign.setRegistrationOpen(false);
        campaignRepository.save(campaign);
    }




}