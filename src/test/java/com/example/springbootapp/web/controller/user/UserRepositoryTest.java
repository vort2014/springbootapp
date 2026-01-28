package com.example.springbootapp.web.controller.user;

import com.example.springbootapp.ApplicationTest;
import com.example.springbootapp.dao.user.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends ApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Disabled
    void clearOrganizationsTest() {

        // Given
        var user1 = userRepository.getReferenceById(1L);
        var organizationId1 = user1.getOrganization().getOrganizationId();
        assertThat(organizationId1).isNotNull();
        var user2 = userRepository.getReferenceById(2L);
        var organizationId2 = user2.getOrganization().getOrganizationId();
        assertThat(organizationId2).isNotNull();

        // When
        userRepository.clearOrganizations(List.of(organizationId1, organizationId2));

        // Then
        assertThat(userRepository.getReferenceById(1L).getOrganization()).isNotNull();
        assertThat(userRepository.getReferenceById(2L).getOrganization()).isNotNull();
    }

    @Test
    @Disabled
    void findAllByOrganizationOrganizationIdTest() {
        var organizationId = userRepository.getReferenceById(1L)
                .getOrganization()
                .getOrganizationId();
        assertThat(userRepository.findAllByOrganizationOrganizationId(organizationId)).isNotEmpty();
    }
}