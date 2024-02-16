package com.workflow2.ecommerce.repository;

import com.workflow2.ecommerce.entity.Category;
import com.workflow2.ecommerce.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
public interface SubCategoryDao extends JpaRepository<SubCategory, UUID> {
    @Query(value = "select * from sub_category s where s.category_id =:categoryId",nativeQuery = true)
    List<SubCategory> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query(value = "select * from sub_category s where s.sub_category_name = :name",nativeQuery = true)
    SubCategory getSubCategoryByName(String name);
}
