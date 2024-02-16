package com.workflow2.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This class is used to return the response for product
 * @author krishna_rawat
 * @version v0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private UUID productId;
    private String productName;
    private String categoryName;
    private String subCategoryName;
    private UUID categoryId;
    private UUID subCategoryId;
    private String brand;
    private double price;
    private String[] color;
    private String size;
    private byte[] image = null;
    private String description;
    private int totalStock;
    private double ratings;
    private double discountedPrice;
    private double discountPercent;
}
