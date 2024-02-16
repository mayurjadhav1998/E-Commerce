package com.workflow2.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import javax.persistence.*;

/**
 * This is the Entity class for Product table
 * @author krishna_rawat & nikhitha_sripada
 * @version v0.0.1
 */
@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @Column(name = "product_id")
    private UUID id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name="product_category")
    private String categoryName;

    @Column(name="product_sub_category")
    private String subCategoryName;

    @Column(name="sub_category_id")
    private UUID subCategoryId;



    @Column(name = "product_brand")
    private String brand;

    @Column(name = "product_price")
    private double price;

    @Column(name = "product_color")
    private String[] color;

    @Column(name = "product_size")
    private String size;

    @Column(name = "product_ratings")
    private double ratings;

    @Column(name = "product_image", unique = false, nullable = true, length = 16777215)
    private byte[] image = null;
    
    @Column(name="product_description")
    private String description;

    @Column(name="product_totalStock")
    private int totalStock;

    @Column(name="discount_percentage")
    private double discountPercent;

    @Column(name="discounted_price")
    private double discountedPrice;


}
