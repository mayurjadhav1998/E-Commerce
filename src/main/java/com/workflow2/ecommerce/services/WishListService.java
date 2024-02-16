package com.workflow2.ecommerce.services;

import com.workflow2.ecommerce.dto.WishListDTO;
import com.workflow2.ecommerce.entity.Product;
import com.workflow2.ecommerce.entity.WishList;
import com.workflow2.ecommerce.repository.ProductDao;
import com.workflow2.ecommerce.repository.WishLsitDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Service
public class WishListService {
    @Autowired
    WishLsitDao wishLsitDao;
    @Autowired
    ProductDao productDao;

    public ResponseEntity<String> addItemToWishList(UUID productId, UUID userId){
        if (wishLsitDao.getUserByGroup(userId,productId) == null){
            wishLsitDao.save(new WishList(UUID.randomUUID(),userId,productId));
            return ResponseEntity.status(HttpStatus.CREATED).body("Added to Wishlist"); }
        return ResponseEntity.status(HttpStatus.OK).body("This product already added to Wishlist");
    }

    public ResponseEntity<List<WishListDTO>> getWishlist(UUID userId) throws Exception {
        List<WishList> wishListList = wishLsitDao.getAllWishListOfUser(userId);
        List<WishListDTO>wishListDTOS = new ArrayList<>();
        for(WishList list: wishListList){
            Product product = productDao.findById(list.getProductId()).get();
            product.setImage(ImageUtility.decompressImage(product.getImage()));
            wishListDTOS.add(new WishListDTO(product ,list.getWishListId()));
        }
        return ResponseEntity.ok().body(wishListDTOS);
    }

    public ResponseEntity<String> deleteFromWishList(UUID wishlistId){

        wishLsitDao.deleteById(wishlistId);
        return ResponseEntity.status(HttpStatus.OK).body("Removed");
    }


    public ResponseEntity<String> deleteAllWishlist(UUID userId) {
        List<WishList> wishListList = wishLsitDao.getAllWishListOfUser(userId);
        for(WishList wishList:wishListList){
            wishLsitDao.deleteById(wishList.getWishListId());
        }
        return ResponseEntity.ok().body("Removed All");
    }
}
