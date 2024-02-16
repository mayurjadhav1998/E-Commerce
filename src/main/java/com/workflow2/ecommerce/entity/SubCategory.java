package com.workflow2.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="sub_category")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sub_category_id")
    private UUID subCategoryId;

    @Column(name="sub_category_name",nullable = false,unique = true)
    private String subCategoryName;

    @Column(name = "sub_category_image")
    private byte[] subCategoryImage;

    @Column(name="category")
    private String categoryName;

    @Column(name="category_id")
    private UUID categoryId;
}
