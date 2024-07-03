package com.futurum.adcampaignmanager.services.user;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.user.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsersExceptCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUsername))
                .collect(Collectors.toList());
    }

    public User updateUser(Long id, User userDetails) throws UserAlreadyExistsException {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }

        User user = optionalUser.get();

        if ((!user.getUsername().equals(userDetails.getUsername()) && this.userRepository.existsByUsername(userDetails.getUsername().toLowerCase())) || this.userRepository.existsByEmail(userDetails.getEmail().toLowerCase())) {
            throw new UserAlreadyExistsException();
        }

        user.setUsername(userDetails.getUsername());
        user.setRole(userDetails.getRole());
        user.setEmail(userDetails.getEmail());

        return this.userRepository.save(user);
    }


}
