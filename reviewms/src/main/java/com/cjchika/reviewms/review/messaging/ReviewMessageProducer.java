package com.cjchika.reviewms.review.messaging;

import com.cjchika.reviewms.review.Review;
import com.cjchika.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    @Autowired
    private final RabbitTemplate template;

    public ReviewMessageProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(Review review){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(review.getId());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setCompanyId(review.getCompanyId());
        template.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
