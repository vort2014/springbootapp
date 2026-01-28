package com.example.springbootapp.web.controller.oranization;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Organization without id for @Post request
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewOrganizationJson {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
