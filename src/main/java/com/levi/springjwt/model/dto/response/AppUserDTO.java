package com.levi.springjwt.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Integer id;
    private String username;
    private String email;
    private List<String> roleName;
}
