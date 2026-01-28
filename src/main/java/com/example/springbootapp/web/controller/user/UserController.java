package com.example.springbootapp.web.controller.user;

import com.example.springbootapp.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    /*
    Return all users from database
    (5. Optional: Display the User-Org relationship in a simple graphical Chart (use a Chart/Angular library of your choice))
    */
    @GetMapping("/users")
    ResponseEntity<Collection<UserJson>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    /*
    Find user by id
    */
    @GetMapping("/users/{id}")
    ResponseEntity<UserJson> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    /*
    Create new user
    */
    @PostMapping("/users")
    ResponseEntity<UserJson> save(@RequestBody @Valid NewUserJson newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(newUser));
    }

    /*
    Update user information including organization
     */
    @PutMapping("/users/{userId}")
    ResponseEntity<UserJson> update(@PathVariable Long userId, @RequestBody @Valid UpdateUserJson json) {
        return ResponseEntity.ok().body(userService.update(userId, json));
    }

    /*
    Delete users with the ids
    */
    @DeleteMapping("/users")
    ResponseEntity<Void> deleteAllById(@RequestBody @Valid @NotEmpty Collection<Long> ids) {
        userService.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }
}