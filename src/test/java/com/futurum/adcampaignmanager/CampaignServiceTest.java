package com.futurum.adcampaignmanager;

import com.futurum.adcampaignmanager.entity.Campaign;
import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.repositories.CampaignRepository;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.campaign.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCampaignSuccessfully() {
        String username = "validUser";
        String keywordsString = "keyword1, keyword2";
        Campaign campaignData = new Campaign();
        campaignData.setCampaignName("ValidCampaign");

        User user = new User();
        user.setUsername(username);

        when(this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())).thenReturn(false);
        when(this.userRepository.findByUsername(username)).thenReturn(user);
        when(this.campaignRepository.save(any(Campaign.class))).thenReturn(campaignData);

        Campaign savedCampaign = this.campaignService.saveCampaign(campaignData, username, keywordsString);

        assertNotNull(savedCampaign);
        verify(this.campaignRepository, times(1)).save(any(Campaign.class));
    }

    @Test
    void shouldThrowExceptionWhenCampaignNameExists() {
        String username = "validUser";
        String keywordsString = "keyword1, keyword2";
        Campaign campaignData = new Campaign();
        campaignData.setCampaignName("ExistingCampaign");

        when(this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            this.campaignService.saveCampaign(campaignData, username, keywordsString);
        });

        verify(this.campaignRepository, never()).save(any(Campaign.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        String username = "invalidUser";
        String keywordsString = "keyword1, keyword2";
        Campaign campaignData = new Campaign();
        campaignData.setCampaignName("ValidCampaign");

        when(this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())).thenReturn(false);
        when(this.userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            this.campaignService.saveCampaign(campaignData, username, keywordsString);
        });

        verify(this.campaignRepository, never()).save(any(Campaign.class));
    }

    @Test
    void shouldUpdateCampaignSuccessfully() {
        String keywordsString = "keyword1, keyword2";
        Campaign campaignData = new Campaign();
        campaignData.setCampaignName("ExistingCampaign");

        Campaign existingCampaign = new Campaign();
        existingCampaign.setCampaignName("ExistingCampaign");

        when(this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())).thenReturn(true);
        when(this.campaignRepository.findByCampaignName(campaignData.getCampaignName())).thenReturn(existingCampaign);
        when(this.campaignRepository.save(existingCampaign)).thenReturn(existingCampaign);

        Campaign updatedCampaign = this.campaignService.updateCampaign(campaignData, keywordsString);

        assertNotNull(updatedCampaign);
        verify(this.campaignRepository, times(1)).save(existingCampaign);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCampaign() {
        String keywordsString = "keyword1, keyword2";
        Campaign campaignData = new Campaign();
        campaignData.setCampaignName("NonExistentCampaign");

        when(this.campaignRepository.existsByCampaignName(campaignData.getCampaignName())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            this.campaignService.updateCampaign(campaignData, keywordsString);
        });

        verify(this.campaignRepository, never()).save(any(Campaign.class));
    }

}