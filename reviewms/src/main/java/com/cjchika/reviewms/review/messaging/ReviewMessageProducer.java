package com.cjchika.reviewms.review.messaging;

import com.cjchika.reviewms.review.Review;
import com.cjchika.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewMessageProducer {

    @Autowired
    private final RabbitTemplate template;

    public ReviewMessageProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(Review review){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(reviewMessage.getId());
        reviewMessage.setTitle(reviewMessage.getTitle());
        reviewMessage.setDescription(reviewMessage.getDescription());
        reviewMessage.setRating(reviewMessage.getRating());
        reviewMessage.setCompanyId(reviewMessage.getCompanyId());
        template.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
