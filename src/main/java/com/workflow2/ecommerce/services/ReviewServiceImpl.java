package com.workflow2.ecommerce.services;

import com.workflow2.ecommerce.dto.ReviewDTO;
import com.workflow2.ecommerce.entity.Review;
import com.workflow2.ecommerce.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workflow2.ecommerce.repository.ProductDao;
import com.workflow2.ecommerce.repository.ReviewDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mayur_jadhav
 * @version v0.0.1
 */
@Service
public class ReviewServiceImpl {
    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;


    public String addReview(UUID userId, ReviewDTO reviewDTO,UUID productId) {
        System.out.println("Product" + reviewDao.findByProductIdAndUserId(productId, userId) );
        if(reviewDao.findByProductIdAndUserId(productId,userId) == null){

        reviewDao.save(new Review(UUID.randomUUID(),userId, reviewDTO.getDescription(), reviewDTO.getSmileRating(), productId, reviewDTO.getThumb()));
        return "Review created";
        }
        else {
            return "Already Created";
        }
    }
    public List<ReviewDTO> getReviewsOfProduct(UUID productId) {
        List<Review> reviews = reviewDao.findByProductId(productId);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review review : reviews){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setProductId(review.getProductId());
            reviewDTO.setDescription(review.getDescription());
            reviewDTO.setThumb(review.getThumb());
            reviewDTO.setUserId(review.getUserId());
            reviewDTO.setUserName(userDao.findById(review.getUserId()).get().getName());
            reviewDTO.setSmileRating(review.getSmileRating());
            reviewDTO.setReviewId(review.getReviewId());
            reviewDTOS.add(reviewDTO);
        }
        return reviewDTOS;
    }
}