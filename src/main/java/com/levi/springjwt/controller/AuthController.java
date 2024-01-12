package com.levi.springjwt.controller;

import com.levi.springjwt.exception.NotFoundException;
import com.levi.springjwt.model.dto.request.AppUserRequest;
import com.levi.springjwt.model.dto.request.AuthRequest;
import com.levi.springjwt.model.dto.response.ApiResponse;
import com.levi.springjwt.model.dto.response.AppUserDTO;
import com.levi.springjwt.model.dto.response.AuthResponse;
import com.levi.springjwt.security.JwtService;
import com.levi.springjwt.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthController(AppUserService appUserService, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AppUserRequest appUserRequest){
        AppUserDTO appUserDTO = appUserService.createUser(appUserRequest);
        ApiResponse<AppUserDTO> response = ApiResponse.<AppUserDTO>builder()
                .message("Successfully registered")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(appUserDTO).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());

        final UserDetails userDetails = appUserService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .message("Successfully login")
                .status(HttpStatus.OK)
                .code(200)
                .payload(authResponse).build();
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = appUserService.loadUserByUsername(username);
            if (userApp == null){
                throw new NotFoundException("Wrong Email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())){
                throw new NotFoundException("Wrong Password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
