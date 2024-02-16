package com.workflow2.ecommerce.dto;

import java.util.UUID;

import com.workflow2.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {
   private UUID reviewId;
   private String description;
   private int smileRating;
   private UUID productId;
   private int thumb;
   private UUID userId;
   private String userName;
}
