package com.example.springbootapp.dao.user;

import com.example.springbootapp.dao.organization.OrganizationEntity;
import com.example.springbootapp.web.controller.user.NewUserJson;
import com.github.javafaker.Faker;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "exampleUser")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @ManyToOne
    @JoinColumn(name = "organizationId")
    private OrganizationEntity organization;

    public static UserEntity from(String name, String email) {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .build();
    }

    public static UserEntity from(NewUserJson u) {
        return UserEntity.builder()
                .name(u.getName())
                .email(u.getEmail())
                .build();
    }

    public static UserEntity generateRandom() {
        Faker faker = new Faker();
        String name = faker.name().lastName();
        return UserEntity.builder()
                .name(name)
                .email(name.toLowerCase() + "@example.com")
                .build();
    }
}