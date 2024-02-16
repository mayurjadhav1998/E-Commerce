package com.workflow2.ecommerce.repository;

import com.workflow2.ecommerce.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
public interface WishLsitDao extends JpaRepository<WishList, UUID> {
    @Query(value = "select * from wishlist w where w.user_id= :userId and w.product_id = :productId",nativeQuery = true)
    public WishList getUserByGroup(@Param(value = "userId") UUID userId, @Param(value = "productId") UUID productId);

    @Query(value = "select * from wishlist w where w.user_id = :userId",nativeQuery = true)
    public List<WishList> getAllWishListOfUser(UUID userId);
}
