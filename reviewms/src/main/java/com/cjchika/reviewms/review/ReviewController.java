package com.cjchika.reviewms.review;


import com.cjchika.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    @GetMapping("reviews")
    public ResponseEntity<List<Review>> getAllCompanyReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping("reviews")
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review){
       boolean isReviewSaved =  reviewService.addReview(companyId, review);

       if(isReviewSaved){
           reviewMessageProducer.sendMessage(review);
           return new ResponseEntity<>("Review Added Successfully!", HttpStatus.CREATED);
       } else{
           return new ResponseEntity<>("Review not saved!", HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
        Review review = reviewService.getReview(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody  Review review){
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);

        if(isReviewUpdated){
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReviewById(reviewId);

        if(isReviewDeleted){
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
