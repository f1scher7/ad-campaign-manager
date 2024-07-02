package com.futurum.adcampaignmanager.controllers.auth;

import com.futurum.adcampaignmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.futurum.adcampaignmanager.utils.JwtUtil.generateToken;
import static com.futurum.adcampaignmanager.utils.ResponseUtil.createErrorResponse;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;


    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwtToken = generateToken(authentication);

            Map<String, String> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("role", String.valueOf(user.getRole()));
            response.put("jwtToken", jwtToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, "loginError", "Invalid username or password");
        }
    }

}
