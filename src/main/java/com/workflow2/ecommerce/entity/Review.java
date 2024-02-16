package com.workflow2.ecommerce.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Table(name="review")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reviewId;
    private UUID userId;
    private String description;
    private int smileRating;
    private UUID productId;
    private int thumb;


}
  

