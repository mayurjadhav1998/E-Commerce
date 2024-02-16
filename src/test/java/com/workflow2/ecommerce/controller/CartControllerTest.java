package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.CartItems;
import com.workflow2.ecommerce.dto.ProductDTO;
import com.workflow2.ecommerce.entity.Cart;
import com.workflow2.ecommerce.entity.CartDetails;
import com.workflow2.ecommerce.entity.User;
import com.workflow2.ecommerce.repository.CartDao;
import com.workflow2.ecommerce.repository.CartDetailDao;
import com.workflow2.ecommerce.repository.UserDao;
import com.workflow2.ecommerce.services.CartServiceImpl;
import com.workflow2.ecommerce.services.CustomUserDetailsService;
import com.workflow2.ecommerce.services.ProductServiceImpl;
import com.workflow2.ecommerce.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartServiceImpl cartService;

    @MockBean
    UserDao userDao;

    @MockBean
    ProductServiceImpl productService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    CartDetails cartDetails1;
    List<CartDetails> cartDetails;
    List<CartItems> cartItemsList;
    List<SimpleGrantedAuthority> roles;
    Cart cart;
    User user;
    CartItems cartItems;

    @BeforeEach
    void setUp() {
        cartDetails1  = CartDetails.builder().id(122).productId(UUID.fromString("8b379426-eafa-4285-ad9c-45deb68a05a9")).quantity(2).color("#17B383").size("M").build();
        cartItems =CartItems.builder().productId(UUID.fromString("8b379426-eafa-4285-ad9c-45deb68a05a9")).quantity(2).image(null).name("Product1").shippingCharges(100).price(1299).color("#17B383").size("M").build();
        cartDetails = new ArrayList<>();
        cartItemsList = new ArrayList<>();
        cartItemsList.add(cartItems);
        cartDetails.add(cartDetails1);
        cart = Cart.builder().userCartId(1).totalAmount(9008.300000000001).cartDetails(cartDetails).build();
        user = User.builder().id(UUID.randomUUID()).name("Test Name").email("testmail@gmail.com").password("Password123").phoneNo("0000000000").cart(cart).build();
        roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_Admin"));
        when(userDao.findByEmail(any())).thenReturn(user);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(new org.springframework.security.core.userdetails.User("testmail@gmail.com","Password123",roles));
    }

    @AfterEach
    void tearDown() {
        user=null;
        cart=null;
        cartDetails1=null;
        cartDetails=null;
        cartItemsList=null;
        roles=null;
        cartItems =null;
        reset(userDao);
    }

    @Test
    void addToCart() throws Exception {
        when(cartService.addToCart(any(),any())).thenReturn("Item Added to cart");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/addtocart")
                .content("{\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"color\":\"#17B383\",\"size\":\"M\"}")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Item Added to cart"))
                .andReturn();

        verify(cartService,times(1)).addToCart(any(),any());

    }

    @Test
    void getAllItems() throws Exception {
        when(cartService.getAllCart(any())).thenReturn(java.util.Optional.ofNullable(cart));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cart")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"userCartId\":1,\"totalAmount\":9008.300000000001,\"cartDetails\":[{\"id\":122,\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"color\":\"#17B383\",\"size\":\"M\"}]}"))
                .andReturn();

        verify(cartService,times(1)).getAllCart(any());
    }

    @Test
    void cartDetails() throws Exception {
        when(cartService.getAllCartDetails(any())).thenReturn(cartItemsList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cartDetails")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"image\":null,\"name\":\"Product1\",\"shippingCharges\":100, \"price\":1299,\"color\":\"#17B383\",\"size\":\"M\"}]"))
                .andReturn();

        verify(cartService,times(1)).getAllCartDetails(any());
    }

    @Test
    void cartDetailById() throws Exception {
        when(cartService.getCartDetailsById(any(),any())).thenReturn(cartItems);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cartDetails/8b379426-eafa-4285-ad9c-45deb68a05a9")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"image\":null,\"name\":\"Product1\",\"shippingCharges\":100, \"price\":1299,\"color\":\"#17B383\",\"size\":\"M\"}"))
                .andReturn();

        verify(cartService,times(1)).getCartDetailsById(any(),any());
    }

    @Test
    void updateCartDetails() throws Exception {
        when(cartService.updateCartDetails(any(),any())).thenReturn(cartItems);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/cartDetails")
                .content("{\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"color\":\"#17B383\",\"size\":\"M\"}")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":\"8b379426-eafa-4285-ad9c-45deb68a05a9\",\"quantity\":2,\"image\":null,\"name\":\"Product1\",\"shippingCharges\":100, \"price\":1299,\"color\":\"#17B383\",\"size\":\"M\"}"))
                .andReturn();

        verify(cartService,times(1)).updateCartDetails(any(),any());
    }

    @Test
    void cartDetailDeleteById() throws Exception {
        when(cartService.deleteCartDetailsById(any(),any())).thenReturn(new ArrayList<>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cartDetails/8b379426-eafa-4285-ad9c-45deb68a05a9")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();

        verify(cartService,times(1)).deleteCartDetailsById(any(),any());
    }

    @Test
    void deleteAllCart() throws Exception {
        when(cartService.deleteAllCartDetails(any())).thenReturn("Cart is clear");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cart")
                .header("Authorization","Bearer "+JwtUtil.generateToken("testmail@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Cart is clear"))
                .andReturn();

        verify(cartService,times(1)).deleteAllCartDetails(any());
    }
}