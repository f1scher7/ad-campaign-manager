package com.futurum.adcampaignmanager.controllers;

import com.futurum.adcampaignmanager.entity.Campaign;
import com.futurum.adcampaignmanager.repositories.CampaignRepository;
import com.futurum.adcampaignmanager.services.campaign.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.futurum.adcampaignmanager.utils.ResponseUtil.createErrorResponse;

@RestController
@RequestMapping("/api")
public class CampaignController {

    private final CampaignService campaignService;
    private final CampaignRepository campaignRepository;


    @Autowired
    public CampaignController(CampaignService campaignService, CampaignRepository campaignRepository) {
        this.campaignService = campaignService;
        this.campaignRepository = campaignRepository;
    }


    @GetMapping("/campaigns/count")
    public long getCampaignCount() {
        return this.campaignRepository.count();
    }

    @GetMapping("/campaigns/get-by-id/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        return this.campaignRepository.findById(id)
                .map(campaign -> ResponseEntity.ok().body(campaign))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/campaigns/all")
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        try {
            List<Campaign> campaigns = this.campaignRepository.findAll();
            return ResponseEntity.ok().body(campaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/campaigns/create-campaign")
    public ResponseEntity<?> createCampaign(@RequestBody Campaign campaign, @RequestParam String username, @RequestParam String keywords) {
        try {
            Campaign savedCampaign = campaignService.saveCampaign(campaign, username, keywords);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("user")) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "usernameError", e.getMessage());
            } else {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "campaignNameError", e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/campaigns/update-campaign")
    public ResponseEntity<?> updateCampaign(@RequestBody Campaign campaign, @RequestParam String keywords) {
        try {
            System.out.println(campaign);
            System.out.println(keywords);
            Campaign updatedCampaign = campaignService.updateCampaign(campaign, keywords);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCampaign);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("user")) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "usernameError", e.getMessage());
            } else {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "campaignNameError", e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/campaigns/delete-by-id/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) {
        return this.campaignRepository.findById(id)
                .map(campaign -> {
                    this.campaignRepository.delete(campaign);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
