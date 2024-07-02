package com.futurum.adcampaignmanager.controllers;

import com.futurum.adcampaignmanager.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CampaignController {

    private final CampaignRepository campaignRepository;


    @Autowired
    public CampaignController(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }


    @GetMapping("/campaigns/count")
    public long getCampaignCount() {
        return this.campaignRepository.count();
    }

}
