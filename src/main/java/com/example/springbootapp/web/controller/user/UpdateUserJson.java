package com.example.springbootapp.web.controller.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUserJson {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private Long organizationId;
}
