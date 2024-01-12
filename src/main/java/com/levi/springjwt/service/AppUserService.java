package com.levi.springjwt.service;

import com.levi.springjwt.model.dto.request.AppUserRequest;
import com.levi.springjwt.model.dto.response.AppUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AppUserService extends UserDetailsService {

    AppUserDTO createUser(AppUserRequest appUserRequest);

    List<AppUserDTO> getAllUser();
}
