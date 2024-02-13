package com.levi.springjwt.controller;

import com.levi.springjwt.model.dto.response.ApiResponse;
import com.levi.springjwt.model.dto.response.AppUserDTO;
import com.levi.springjwt.service.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController

@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private AppUserService appUserService;
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(){
        List<AppUserDTO> userDTOS =  appUserService.getAllUser();
        ApiResponse<List<AppUserDTO>> response =  ApiResponse.<List<AppUserDTO>>builder()
                .message("Successfully fetched all users")
                .status(HttpStatus.OK)
                .code(200)
                .payload(userDTOS).build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/admin")
    public String accessAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/user")
    public String accessUser(){
        return "Hello User";
    }

    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        System.out.println(username);
        return username;
    }
    @GetMapping("/getEmail")
    @SecurityRequirement(name = "bearerAuth")
    private String getHello() {
        return getUsernameOfCurrentUser();
    }

}
