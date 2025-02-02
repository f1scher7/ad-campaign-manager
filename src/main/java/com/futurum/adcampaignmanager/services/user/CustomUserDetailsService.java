package com.futurum.adcampaignmanager.services.user;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username.toLowerCase());

        if (user == null) {
            user = this.userRepository.findByEmail(username.toLowerCase());
        }

        if (user == null) {
            throw new UsernameNotFoundException("Użytkownik o nazwie '" + username.toLowerCase() + "' nie istnieje");
        }

        return user;
    }

}
