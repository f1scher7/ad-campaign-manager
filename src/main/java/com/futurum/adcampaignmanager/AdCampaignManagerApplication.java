package com.futurum.adcampaignmanager;

import com.futurum.adcampaignmanager.entity.Campaign;
import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.repositories.CampaignRepository;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.campaign.CampaignService;
import com.futurum.adcampaignmanager.services.user.RegisterService;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class AdCampaignManagerApplication implements CommandLineRunner {

    private final RegisterService registerService;
    private final CampaignService campaignService;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;



    @Autowired
    public AdCampaignManagerApplication(RegisterService registerService, CampaignService campaignService, UserRepository userRepository, CampaignRepository campaignRepository) {
        this.registerService = registerService;
        this.campaignService = campaignService;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(AdCampaignManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws InvalidPasswordException, EmailAlreadyExistsException, InvalidUsernameException, UserAlreadyExistsException, InvalidEmailException {
        if (!userRepository.existsByUsername("admin")) {
            this.registerService.registerUser(Role.ADMIN, "admin", "Admin123", "admin@gmail.com");
        }
        if (!userRepository.existsByUsername("testuser1")) {
            this.registerService.registerUser(Role.USER, "testuser1", "testUser1", "testuser1@gmail.com");
        }
        if (!userRepository.existsByUsername("testuser2")) {
            this.registerService.registerUser(Role.USER, "testuser2", "testUser2", "testuser2@gmail.com");
        }
        if (!userRepository.existsByUsername("testuser3")) {
            this.registerService.registerUser(Role.USER, "testuser3", "testUser3", "testuser3@gmail.com");
        }

        if (!campaignRepository.existsByCampaignName("Back to School")) {
            Campaign backToSchool = new Campaign();
            backToSchool.setUser(this.userRepository.findByUsername("testuser1"));
            backToSchool.setCampaignName("Back to School");
            backToSchool.setKeywords(Arrays.asList("school supplies", "backpacks", "notebooks"));
            backToSchool.setBidAmount(new BigDecimal("1.50"));
            backToSchool.setCampaignFund(new BigDecimal("5000.00"));
            backToSchool.setCampaignStatus(true);
            backToSchool.setTown("Warszawa");
            backToSchool.setRadius(15);
            campaignRepository.save(backToSchool);
        }

        if (!campaignRepository.existsByCampaignName("Summer Sale")) {
            Campaign summerSale = new Campaign();
            summerSale.setUser(this.userRepository.findByUsername("testuser2"));
            summerSale.setCampaignName("Summer Sale");
            summerSale.setKeywords(Arrays.asList("summer clothing", "sunglasses", "swimwear"));
            summerSale.setBidAmount(new BigDecimal("2.00"));
            summerSale.setCampaignFund(new BigDecimal("8000.00"));
            summerSale.setCampaignStatus(true);
            summerSale.setTown("Kraków");
            summerSale.setRadius(20);
            campaignRepository.save(summerSale);
        }

        if (!campaignRepository.existsByCampaignName("Holiday Specials")) {
            Campaign holidaySpecials = new Campaign();
            holidaySpecials.setUser(this.userRepository.findByUsername("testuser3"));
            holidaySpecials.setCampaignName("Holiday Specials");
            holidaySpecials.setKeywords(Arrays.asList("holiday gifts", "christmas decorations", "new year sales"));
            holidaySpecials.setBidAmount(new BigDecimal("1.80"));
            holidaySpecials.setCampaignFund(new BigDecimal("10000.00"));
            holidaySpecials.setCampaignStatus(true);
            holidaySpecials.setTown("Łuck");
            holidaySpecials.setRadius(25);
            campaignRepository.save(holidaySpecials);
        }
    }

}
