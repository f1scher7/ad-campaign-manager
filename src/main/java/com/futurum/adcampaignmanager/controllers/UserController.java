package com.futurum.adcampaignmanager.controllers;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.user.RegisterService;
import com.futurum.adcampaignmanager.services.user.UserService;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.futurum.adcampaignmanager.utils.ResponseUtil.createErrorResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RegisterService registerService;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, RegisterService registerService, UserRepository userRepository) {
        this.userService = userService;
        this.registerService = registerService;
        this.userRepository = userRepository;
    }


    @GetMapping("/users/count")
    public long getUserCount() {
        return this.userRepository.countUsersWithRoleUser();
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsersExceptCurrent();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users/get-by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return this.userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/create-user")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody User user) {
        try {
            this.registerService.registerUser(user.getRole(), user.getUsername(), user.getPassword(), user.getEmail());
            Map<String, String> registerResponse = new HashMap<>();
            registerResponse.put("message", "Registration successful");

            return ResponseEntity.ok(registerResponse);
        } catch (InvalidUsernameException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "invalidUsername", e.getMessage());
        } catch (UserAlreadyExistsException e) {
            return createErrorResponse(HttpStatus.CONFLICT, "userAlreadyExists", e.getMessage());
        } catch (InvalidPasswordException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "invalidPassword", e.getMessage());
        } catch (InvalidEmailException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "invalidEmail", e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            return createErrorResponse(HttpStatus.CONFLICT, "emailAlreadyExists", e.getMessage());
        }
    }

    @PutMapping("/users/update-user-by-id/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok().body(updatedUser);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/users/delete-user-by-id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return this.userRepository.findById(id)
                .map(user -> {
                    this.userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
