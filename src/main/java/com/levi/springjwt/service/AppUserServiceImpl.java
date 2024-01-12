package com.levi.springjwt.service;

import com.levi.springjwt.exception.DataBadRequestException;
import com.levi.springjwt.exception.EmailDuplicatedException;
import com.levi.springjwt.exception.NotFoundException;
import com.levi.springjwt.model.dto.request.AppUserRequest;
import com.levi.springjwt.model.dto.response.AppUserDTO;
import com.levi.springjwt.model.entity.AppUser;
import com.levi.springjwt.model.entity.CustomUserDetails;
import com.levi.springjwt.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    public AppUserServiceImpl(AppUserRepository appUserRepository, ModelMapper mapper, BCryptPasswordEncoder encoder) {
        this.appUserRepository = appUserRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email);
        if(user == null){
            throw new NotFoundException("could not found user..!!");
        }
        return new CustomUserDetails(user);
    }
    @Override
    public AppUserDTO createUser(AppUserRequest appUserRequest) {
        String email = appUserRepository.findEmail(appUserRequest.getEmail());
        if (appUserRequest.getEmail().equalsIgnoreCase(email)){
            throw new EmailDuplicatedException("Email already exist");
        }
       appUserRequest.setPassword(encoder.encode(appUserRequest.getPassword()));
       Integer userId =  appUserRepository.saveUser(appUserRequest);
        for (String roleName : appUserRequest.getRoleName() ) {
            if (roleName.equalsIgnoreCase("ROLE_ADMIN")){
                appUserRepository.saveUserRole(userId, 1);
            }
            if (roleName.equalsIgnoreCase("ROLE_USER")){
                appUserRepository.saveUserRole(userId, 2);
            }
            if (!roleName.equalsIgnoreCase("ROLE_ADMIN") && !roleName.equalsIgnoreCase("ROLE_USER")){
                appUserRepository.deleteUserById(userId);
                throw new DataBadRequestException("Role field must be 'ROLE_ADMIN', 'ROLE_USER'");
            }
        }
        AppUser appUser = appUserRepository.findUserById(userId);
        return mapper.map(appUser, AppUserDTO.class);
    }

    @Override
    public List<AppUserDTO> getAllUser() {
        List<AppUser> appUsers = appUserRepository.findAllUser();
        return appUsers.stream()
                .map(appUser -> mapper.map(appUser, AppUserDTO.class))
                .collect(Collectors.toList());
    }
}
