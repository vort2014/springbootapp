package com.example.springbootapp.web.controller.user;

import com.example.springbootapp.dao.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserJson {

    private Long id;
    private String name;
    private String email;
    private Long organizationId;
    private String organizationName;

    public static UserJson from(UserEntity e) {
        var builder = UserJson.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail());
        var organization = e.getOrganization();
        if (organization != null) {
            builder.organizationId(organization.getOrganizationId())
                    .organizationName(organization.getName());
        }
        return builder.build();
    }
}
