package com.workflow2.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This class is the DTO class for order
 * @author tejas_badjate
 * @version v0.0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    String  orderId;
    String userName;
    String email;
    String contactNo;
    int quantity;
    String deliveryAddress;
    Integer status;
    UUID productId;
    String productName;
    byte [] productImage;
    double discountedPrice;
    double shippingCharges;
    String size;
    String  color;
    String deliveryDate;
    String orderedDate;
    double orderTotal;
    String description;

}
