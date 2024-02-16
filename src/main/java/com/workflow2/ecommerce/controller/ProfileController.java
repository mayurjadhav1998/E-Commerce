package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.ProfileDTO;
import com.workflow2.ecommerce.entity.User;
import com.workflow2.ecommerce.repository.UserDao;
import com.workflow2.ecommerce.services.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    UserDao userDao;
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(HttpServletRequest httpServletRequest)
    {
        User user = cartService.getUser(httpServletRequest);
        ProfileDTO profile = new ProfileDTO();
        profile.setName(user.getName());
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setAddress(user.getAddress());
        profile.setPhoneNo(user.getPhoneNo());
        return ResponseEntity.ok().body(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileDTO> updateProfile(HttpServletRequest httpServletRequest, @RequestBody ProfileDTO profileDTO)
    {
        User user = cartService.getUser(httpServletRequest);
        try{

            user.setName(profileDTO.getName());
            user.setEmail(profileDTO.getEmail());
            user.setPhoneNo(profileDTO.getPhoneNo());
            user.setAddress(profileDTO.getAddress());
            userDao.save(user); }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }

        ProfileDTO profile = new ProfileDTO();
        profile.setName(user.getName());
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setAddress(user.getAddress());
        profile.setPhoneNo(user.getPhoneNo());
        return ResponseEntity.ok().body(profile);
    }
}
