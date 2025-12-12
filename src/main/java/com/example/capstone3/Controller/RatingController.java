package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Rating;
import com.example.capstone3.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;


    @GetMapping("/get")
    public ResponseEntity<?> getRatings () {
        return ResponseEntity.status(200).body(ratingService.getRatings());
    }


    @PostMapping("/add/{campaignId}/{kitchenId}")
    public ResponseEntity<?> addRating (@PathVariable Integer campaignId, @PathVariable Integer kitchenId, @RequestBody @Valid Rating rating) {
        ratingService.addRating(campaignId, kitchenId, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating added successfully"));
    }


    @PutMapping("/update/{ratingId}")
    public ResponseEntity<?> updateRating (@PathVariable Integer ratingId, @RequestBody @Valid Rating rating) {
        ratingService.updateRating(ratingId, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully"));
    }


    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<?> deleteRating (@PathVariable Integer ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully"));
    }

    @GetMapping("/get-rating-by-kitchenId/{kitchenId}")
    public ResponseEntity<?> getRatingByKitchen (@PathVariable Integer kitchenId) {
        return ResponseEntity.status(200).body(ratingService.getRatingByKitchen(kitchenId));
    }







}
