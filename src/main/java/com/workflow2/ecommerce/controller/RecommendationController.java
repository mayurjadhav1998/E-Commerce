package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.ProductDTO;
import com.workflow2.ecommerce.services.ProductRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class RecommendationController {

    @Autowired
    private ProductRecommendationService recommendationService;

    @GetMapping("/recommend/{productId}")
    public ResponseEntity<?> recommendedProducts(@PathVariable UUID productId){
        List<ProductDTO> productDTOList;
        try {
            productDTOList = recommendationService.recommendProduct(productId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("There is issue while decompressing images");
        }
        return ResponseEntity.ok(productDTOList);
    }
}
