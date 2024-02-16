package com.workflow2.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Entity
@Table(name = "wishlist")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID wishListId;

    @Column(name = "user_id")
    private UUID userId;
    //Object of product class to store the product information
    @Column(name="product_id")
    private UUID productId;
}
