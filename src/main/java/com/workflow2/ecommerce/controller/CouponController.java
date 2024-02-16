package com.workflow2.ecommerce.controller;


import org.springframework.web.bind.annotation.*;
import com.workflow2.ecommerce.entity.Coupon;
import com.workflow2.ecommerce.services.UserOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CouponController {
    @Autowired
    UserOrderServiceImpl userOrderService;
   List<Coupon> coupons = List.of(
           new Coupon(1,"FLAT70",70),
           new Coupon(2,"FLAT33",33),
           new Coupon(3,"FLAT55",55),
           new Coupon(4,"FLAT49",49),
           new Coupon(5,"FLAT35",35),
           new Coupon(6,"FLAT15",15),
           new Coupon(7,"FLAT25",25),
           new Coupon(8,"FLAT60",60),
           new Coupon(9,"FLAT10",10),
           new Coupon(10,"FLAT20",20),
           new Coupon(11,"FLAT30",30),
           new Coupon(12,"FLAT40",40),
           new Coupon(13,"FLAT50",50)
   );


    @GetMapping("/coupon")
    public Set<Coupon> coupon(){

        int i = coupons.size();
        Set<Coupon> couponList = new HashSet<>();
        while(couponList.size()<3)
        {
            int index = userOrderService.getRandomNumberUsingNextInt(0,i);
            couponList.add(coupons.get(index));
        }
        return couponList;
    }
}
