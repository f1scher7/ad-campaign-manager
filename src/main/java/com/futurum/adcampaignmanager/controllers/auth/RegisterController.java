package com.futurum.adcampaignmanager.controllers.auth;

import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.services.user.RegisterService;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.futurum.adcampaignmanager.utils.ResponseUtil.createErrorResponse;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterController {

    private final RegisterService registerService;


    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        try {
            String username = registerRequest.get("username");
            String password = registerRequest.get("password");
            String email = registerRequest.get("email");

            this.registerService.registerUser(Role.USER, username, password, email);

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

}
