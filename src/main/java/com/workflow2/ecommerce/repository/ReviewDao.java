package com.workflow2.ecommerce.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workflow2.ecommerce.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
public interface ReviewDao extends JpaRepository<Review, UUID>{
    public List<Review> findByProductId(UUID productId);

    @Query(value = "select * from review r where r.product_id = :productId AND r.user_id = :userId",nativeQuery = true)
    public Review findByProductIdAndUserId(@Param("productId") UUID productId,@Param("userId") UUID userId);
}
