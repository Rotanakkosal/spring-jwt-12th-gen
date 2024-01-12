package com.levi.springjwt.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {

    @NotNull(message = "username shouldn't be null")
    @NotBlank(message = "username shouldn't be blank")
    private String username;
    @Email(message = "invalid email address format")
    @NotNull(message = "email shouldn't be null")
    @NotBlank(message = "email shouldn't be blank")
    private String email;
    @NotNull(message = "password shouldn't be null")
    @NotBlank(message = "password shouldn't be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers")
    private String password;

    @NotNull(message = "roleName shouldn't be null")
    @NotEmpty(message = "roleName shouldn't empty")
    private List<String> roleName;
}
