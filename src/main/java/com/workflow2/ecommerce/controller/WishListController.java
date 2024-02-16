package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.WishListDTO;
import com.workflow2.ecommerce.entity.User;
import com.workflow2.ecommerce.services.CartServiceImpl;
import com.workflow2.ecommerce.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@RequestMapping("/api/wishlist/")
@RestController
public class WishListController {
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    WishListService wishListService;
    @PostMapping("/{productId}")
    public ResponseEntity<String> addItemToWishlist(HttpServletRequest httpServletRequest, @PathVariable UUID productId){
        User user = cartService.getUser(httpServletRequest);
        return wishListService.addItemToWishList(productId,user.getId());
    }

    @GetMapping("/")
    public ResponseEntity<List<WishListDTO>> getWishlist(HttpServletRequest httpServletRequest) throws Exception {
        User user = cartService.getUser(httpServletRequest);
        return wishListService.getWishlist(user.getId());
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable UUID wishlistId){
        return wishListService.deleteFromWishList(wishlistId);
    }

    @DeleteMapping("/")
    public  ResponseEntity<String > deleteAllWishlist(HttpServletRequest httpServletRequest){
        User user = cartService.getUser(httpServletRequest);
        return wishListService.deleteAllWishlist(user.getId());
    }
}
