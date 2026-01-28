package com.example.springbootapp.web.controller.oranization;

import com.example.springbootapp.dao.organization.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrganizationJson {

    private Long organizationId;
    private String name;
    private String address;

    public static OrganizationJson from(OrganizationEntity e) {
        return OrganizationJson.builder()
                .organizationId(e.getOrganizationId())
                .name(e.getName())
                .address(e.getAddress())
                .build();
    }
}
