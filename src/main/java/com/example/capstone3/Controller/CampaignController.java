package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(campaignService.getAll());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCampaign(@RequestBody @Valid Campaign campaign) {
        campaignService.addCampaign(campaign);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign added successfully"));
    }


    @PutMapping("/update/{campaignId}")
    public ResponseEntity<?> updateCampaign(@PathVariable Integer campaignId, @RequestBody @Valid Campaign campaign) {
        campaignService.updateCampaign(campaignId, campaign);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign updated successfully"));
    }


    @DeleteMapping("/delete/{campaignId}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Integer campaignId) {
        campaignService.deleteCampaign(campaignId);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign deleted successfully"));
    }

    @PutMapping("/assign-to-kitchen/{campaignId}/{kitchenId}")
    public ResponseEntity<?> assignCampaignToKitchen(@PathVariable Integer campaignId, @PathVariable Integer kitchenId) {
        campaignService.assignCampaignToKitchen(campaignId, kitchenId);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign assigned to kitchen successfully"));
    }

    @GetMapping("/pilgrims/{campaignId}")
    public ResponseEntity<?> getPilgrimsInCampaign(@PathVariable Integer campaignId) {
        return ResponseEntity.status(200).body(campaignService.getPilgrimsInCampaign(campaignId));
    }

    @PutMapping("/add-pilgrim/{pilgrimId}/{campaignId}")
    public ResponseEntity<?> addPilgrimToCampaign(@PathVariable Integer pilgrimId, @PathVariable Integer campaignId) {
        campaignService.addPilgrimToCampaign(pilgrimId, campaignId);
        return ResponseEntity.status(200).body(new ApiResponse("Pilgrim added to campaign successfully"));
    }



    @PutMapping("/close-registration/{campaignId}")
    public ResponseEntity<?> closeCampaignRegistration(@PathVariable Integer campaignId) {

        campaignService.closeCampaignRegistration(campaignId);

        return ResponseEntity.status(200).body(new ApiResponse("Campaign registration closed successfully"));
    }




}