package com.futurum.adcampaignmanager.repositories;

import com.futurum.adcampaignmanager.entity.Campaign;
import com.futurum.adcampaignmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Campaign findByCampaignName(String campaignName);

    Campaign findByUser(User user);

    boolean existsByCampaignName(String campaignName);

}
