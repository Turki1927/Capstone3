package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Model.Rating;
import com.example.capstone3.Repository.CampaignRepository;
import com.example.capstone3.Repository.KitchenRepository;
import com.example.capstone3.Repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final KitchenRepository kitchenRepository;
    private final CampaignRepository campaignRepository;


    public List<Rating> getRatings () {
        if (ratingRepository.findAll().isEmpty()) {
            throw new ApiException("No ratings yet");
        }
        return ratingRepository.findAll();
    }

    public void addRating(Integer campaignId, Integer kitchenId, Rating rating) {

        Campaign campaign = campaignRepository.findCampaignById(campaignId);
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);

        if (campaign == null || kitchen == null) {
            throw new ApiException("Campaign or Kitchen not found");
        }

        if (!kitchen.getCampaigns().contains(campaign)) {
            throw new ApiException("This campaign is not assigned to this kitchen");
        }

        if (ratingRepository.existsByCampaignAndKitchen(campaign, kitchen)) {
            throw new ApiException("This campaign already rated this kitchen");
        }

        rating.setCampaign(campaign);
        rating.setKitchen(kitchen);

        ratingRepository.save(rating);
    }



    public void updateRating (Integer ratingId, Rating rating) {
        Rating oldRating = ratingRepository.findRatingById(ratingId);
        if (oldRating == null) {
            throw new ApiException("Ratings not found");
        }

        oldRating.setComment(rating.getComment());
        oldRating.setStars(rating.getStars());
        ratingRepository.save(oldRating);
    }

    public void deleteRating (Integer ratingId) {
        Rating rating = ratingRepository.findRatingById(ratingId);
        if (rating == null) {
            throw new ApiException("Ratings not found");
        }
        ratingRepository.delete(rating);
    }


    public List<Rating> getRatingByKitchen (Integer kitchenId) {
        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        List<Rating> ratings = ratingRepository.findRatingByKitchen(kitchen);
        if (ratings == null) {
            throw new ApiException("Not ratings found for this kitchen yet");
        }
        return ratings;
    }
}
