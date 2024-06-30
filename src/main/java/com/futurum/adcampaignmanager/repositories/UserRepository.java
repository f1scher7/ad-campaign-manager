package com.futurum.adcampaignmanager.repositories;

import com.futurum.adcampaignmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String email);

}
