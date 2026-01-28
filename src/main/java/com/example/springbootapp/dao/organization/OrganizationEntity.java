package com.example.springbootapp.dao.organization;

import com.example.springbootapp.web.controller.oranization.NewOrganizationJson;
import com.example.springbootapp.web.controller.oranization.OrganizationJson;
import com.github.javafaker.Faker;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organization")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long organizationId;
    private String name;
    private String address;

    public static OrganizationEntity from(NewOrganizationJson j) {
        return OrganizationEntity.builder()
                .name(j.getName())
                .address(j.getAddress())
                .build();
    }

    public static OrganizationEntity generateRandom() {
        Faker faker = new Faker();
        return OrganizationEntity.builder()
                .name(faker.company().name())
                .address(faker.address().streetAddress())
                .build();
    }

    public OrganizationEntity updateFrom(OrganizationJson j) {
        setName(j.getName());
        setAddress(j.getAddress());
        return this;
    }
}
