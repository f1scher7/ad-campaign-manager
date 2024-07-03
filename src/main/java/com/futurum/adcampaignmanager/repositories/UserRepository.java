package com.futurum.adcampaignmanager.repositories;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.futurum.adcampaignmanager.enums.Role.USER;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole(Role role);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'USER'")
    long countUsersWithRoleUser();



}
