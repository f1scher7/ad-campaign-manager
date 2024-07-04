package com.futurum.adcampaignmanager.services.campaign;

import com.futurum.adcampaignmanager.entity.Campaign;
import com.futurum.adcampaignmanager.repositories.CampaignRepository;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;


    @Autowired
    public CampaignService(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }


    public Campaign saveCampaign(Campaign campaignData, String username, String keywordsString) throws IllegalArgumentException {
        if (this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())) {
            throw new IllegalArgumentException("Campaign name already exists");
        }

        if (this.userRepository.findByUsername(username) == null) {
            throw new IllegalArgumentException("Assigned user does not exist");
        }

        List<String> keywords = List.of(keywordsString.split(", "));

        Campaign campaign = new Campaign();
        campaign.setUser(this.userRepository.findByUsername(username.toLowerCase()));

        populateCampaignData(campaign, campaignData, keywords);

        return this.campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Campaign campaignData, String keywordsString) throws IllegalArgumentException {
        System.out.println(campaignData);

        if (!this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())) {
            throw new IllegalArgumentException("Campaign name doesn't exist");
        }


        List<String> keywords = List.of(keywordsString.split(", "));

        Campaign campaign = this.campaignRepository.findByCampaignName(campaignData.getCampaignName());
        populateCampaignData(campaign, campaignData, keywords);
        return this.campaignRepository.save(campaign);
    }

    private void populateCampaignData(Campaign campaign, Campaign campaignData, List<String> keywords) {
        campaign.setCampaignName(campaignData.getCampaignName());
        campaign.setKeywords(keywords);
        campaign.setBidAmount(campaignData.getBidAmount());
        campaign.setCampaignFund(campaignData.getCampaignFund());
        campaign.setCampaignStatus(campaignData.isCampaignStatus());
        campaign.setTown(campaignData.getTown());
        campaign.setRadius(campaignData.getRadius());

    }

}
