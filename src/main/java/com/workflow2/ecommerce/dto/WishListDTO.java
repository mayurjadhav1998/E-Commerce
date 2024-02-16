package com.workflow2.ecommerce.dto;

import com.workflow2.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WishListDTO {
    private Product product;
    private UUID wishlistId;
}
