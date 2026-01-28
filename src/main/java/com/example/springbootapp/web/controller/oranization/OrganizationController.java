package com.example.springbootapp.web.controller.oranization;

import com.example.springbootapp.service.organization.OrganizationService;
import com.example.springbootapp.service.user.UserService;
import com.example.springbootapp.web.controller.user.UserJson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final UserService userService;

    /*
    Return all Organization (1. Display the Organizations)
    */
    @GetMapping("/organizations")
    ResponseEntity<Collection<OrganizationJson>> findAll() {
        return ResponseEntity.ok().body(organizationService.findAll());
    }

    /*
    Find organization by organizationId
    */
    @GetMapping("/organizations/{organizationId}")
    ResponseEntity<OrganizationJson> findOrganizationById(@PathVariable @Valid @NotNull Long organizationId) {
        return ResponseEntity.ok().body(organizationService.findById(organizationId));
    }

    /*
    Update organization parameters (2. Allow editing Organizations)
    */
    @PutMapping("/organizations/{organizationId}")
    ResponseEntity<OrganizationJson> update(@PathVariable Long organizationId, @RequestBody @Valid OrganizationJson json) {
        return ResponseEntity.ok().body(organizationService.update(organizationId, json));
    }

    /*
    Return the Users that belong to an organization with organizationId (4. Display the Users that belong to an organization (e.g. within table))
     */
    @GetMapping("/organizations/{organizationId}/users")
    ResponseEntity<Collection<UserJson>> findUsersByOrganizationId(@PathVariable @Valid @NotNull Long organizationId) {
        return ResponseEntity.ok().body(userService.findByOrganizationId(organizationId));
    }

    /*
    Update users in organization with organizationId (3. Allow to assign/de-assign users to an organization --> Org - User (1:n))
     */
    @PutMapping("/organizations/{organizationId}/users")
    ResponseEntity<Collection<UserJson>> updateUsersInOrganization(@PathVariable @Valid @NotNull Long organizationId, @RequestBody Collection<Long> userIds) {
        return ResponseEntity.ok().body(userService.updateUsersInOrganization(organizationId, userIds));
    }

    /*
    Create new organization
    */
    @PostMapping("/organizations")
    ResponseEntity<OrganizationJson> save(@RequestBody @Valid NewOrganizationJson json) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(json));
    }

    /*
    Delete all organization with ids
    */
    @DeleteMapping("/organizations")
    ResponseEntity<Void> deleteAllById(@RequestBody @Valid @NotEmpty Collection<Long> ids) {
        organizationService.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }
}
