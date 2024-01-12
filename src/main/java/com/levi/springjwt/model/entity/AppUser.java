package com.levi.springjwt.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private List<String> roleName;

}
