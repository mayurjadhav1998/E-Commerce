package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.AllOrderDto;
import com.workflow2.ecommerce.dto.OrderDto;
import com.workflow2.ecommerce.dto.PlaceOrderDTO;
import com.workflow2.ecommerce.entity.User;
import com.workflow2.ecommerce.services.CartServiceImpl;
import com.workflow2.ecommerce.services.UserOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * This Class have all the endpoints which help us to place, view and track the orders
 * @author tejas_badjate and Nikhitha_Sripada
 * @version v0.0.2
 */
@RestController
@RequestMapping("/api")
public class UserOrderController{
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    UserOrderServiceImpl userOrderService;

    /**
     * This controller method have endpoint to place a order
     * @param httpServletRequest It is the request header
     * @param placeOrderDTO it consists of totalAmount and address required while placing order
     * @return It returns success message for order
     */
    @PostMapping("/placeorder")
    @ResponseBody
    public ResponseEntity<?> placeOrder(HttpServletRequest httpServletRequest, @RequestBody PlaceOrderDTO placeOrderDTO){
        User user = cartService.getUser(httpServletRequest);
        String success = userOrderService.placeOrder(user, placeOrderDTO.getTotalAmount(), placeOrderDTO.getAddress(), placeOrderDTO.getOrderId(),placeOrderDTO.getCouponCode(),placeOrderDTO.getPercentage());
        if(success.startsWith("Success")){
            return ResponseEntity.ok(success);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(success);
    }

    /**
     * This method have endpoint for view orders
     * @param httpServletRequest It is the request header
     * @return It returns the list of all orders for a perticular order
     */
    @GetMapping("/vieworders")
    public List<AllOrderDto> viewOrders(HttpServletRequest httpServletRequest)
    {
        User user = cartService.getUser(httpServletRequest);
        return userOrderService.viewAllOrders(user);
    }

    /**
     * This method is the endpoint for tracking a order by trackingId and orderId
     * @param httpServletRequest It is the Request header
     * @param orderId It is the orderId of the order we wanted to track
     * @param trackingId It is the trackingId of order we wanted to track
     * @return It returns order whose orderId and trackingId is provided
     */
    @GetMapping("/trackorder/{orderId}/{trackingId}")
    public OrderDto trackOrder(HttpServletRequest httpServletRequest,@PathVariable String orderId,@PathVariable UUID trackingId){
        User user = cartService.getUser(httpServletRequest);
        return userOrderService.trackOrder(user,orderId,trackingId);
    }
}
