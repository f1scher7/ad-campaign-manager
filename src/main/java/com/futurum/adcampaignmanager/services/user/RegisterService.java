package com.futurum.adcampaignmanager.services.user;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.futurum.adcampaignmanager.utils.UserUtil.*;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(Role role, String username, String password, String email) throws InvalidUsernameException, InvalidPasswordException, InvalidEmailException, UserAlreadyExistsException, EmailAlreadyExistsException {
        if (!isUsernameValid(username)) {
            throw new InvalidUsernameException();
        }

        if (!isPasswordValid(password)) {
            throw new InvalidPasswordException();
        }

        if (!isEmailValid(email)) {
            throw new InvalidEmailException(email);
        }

        String encodedPassword = this.passwordEncoder.encode(password);

        User user = new User(role, username, encodedPassword, email);

        try {
            this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String mess = e.getMessage();

            if (mess.contains(username)) {
                throw new UserAlreadyExistsException();
            } else if (mess.contains(email)) {
                throw new EmailAlreadyExistsException();
            } else {
                throw new IllegalStateException(e);
            }
        }
    }

}
