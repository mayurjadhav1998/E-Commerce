package com.workflow2.ecommerce.repository;

import com.workflow2.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * This repository help us to perform all database operation on category table
 * @author Mayur_Jadhav & tejas_badjate
 * @version v0.0.1
 */
public interface CategoryDao extends JpaRepository<Category, UUID>{

    @Query(value = "select * from product_category p where p.category_name = :name",nativeQuery = true)
    Category getCategoryByName(String name);
}
