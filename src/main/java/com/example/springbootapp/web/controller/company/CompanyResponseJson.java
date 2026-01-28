package com.example.springbootapp.web.controller.company;

import com.example.springbootapp.dao.company.CompanyEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponseJson {

    String id;
    String name;

    public static CompanyResponseJson from(CompanyEntity e) {
        return CompanyResponseJson.builder()
                .id(e.getId())
                .name(e.getName())
                .build();
    }
}
