package com.example.springbootapp.web.controller.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// user without id for @Post request
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewUserJson {

    @NotBlank
    private String name;
    @Email(regexp = ".+@.+\\..+")
    private String email;
    private Long organizationId;
}
