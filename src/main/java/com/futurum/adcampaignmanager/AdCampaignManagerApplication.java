package com.futurum.adcampaignmanager;

import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.services.user.RegistrationService;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdCampaignManagerApplication implements CommandLineRunner {

    private final RegistrationService registrationService;


    @Autowired
    public AdCampaignManagerApplication(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    public static void main(String[] args) {
        SpringApplication.run(AdCampaignManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws InvalidPasswordException, EmailAlreadyExistsException, InvalidUsernameException, UserAlreadyExistsException, InvalidEmailException {
        //this.registrationService.registerUser(Role.ADMIN, "admin", "admin", "admin@gmail.com");
        //this.registrationService.registerUser(Role.ADMIN, "admin1", "admin", "admin1@gmail.com");
    }

}
