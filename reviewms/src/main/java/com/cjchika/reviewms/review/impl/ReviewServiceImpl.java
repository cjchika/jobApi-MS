package com.cjchika.reviewms.review.impl;

import com.cjchika.reviewms.review.Review;
import com.cjchika.reviewms.review.ReviewRepository;
import com.cjchika.reviewms.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if(companyId != null){
            review.setCompanyId(companyId);
            reviewRepo.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepo.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepo.findById(reviewId).orElse(null);
        if(review != null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepo.save(updatedReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReviewById(Long reviewId) {
        Review review = reviewRepo.findById(reviewId).orElse(null);

        if(review != null && reviewRepo.existsById(reviewId)){
            reviewRepo.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
