package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.ReviewDTO;
import com.workflow2.ecommerce.entity.User;
import com.workflow2.ecommerce.services.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.workflow2.ecommerce.entity.Review;
import com.workflow2.ecommerce.services.ReviewServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

    @Autowired
    CartServiceImpl cartService;
    @PostMapping("/addreview/{productId}")
    public String addReview(HttpServletRequest httpServletRequest, @RequestBody ReviewDTO reviewDTO,@PathVariable UUID productId){
        User user = cartService.getUser(httpServletRequest);
        return reviewServiceImpl.addReview(user.getId(), reviewDTO,productId);
    }

    @GetMapping("/review/{productId}")
    public List<ReviewDTO> getReview(@PathVariable UUID productId){
        return reviewServiceImpl.getReviewsOfProduct(productId);
    }

}
