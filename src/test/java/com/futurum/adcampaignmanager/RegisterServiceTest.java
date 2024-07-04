package com.futurum.adcampaignmanager;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.user.RegisterService;
import com.futurum.adcampaignmanager.services.user.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws InvalidUsernameException, InvalidPasswordException, InvalidEmailException, UserAlreadyExistsException, EmailAlreadyExistsException {
        String username = "validUser";
        String password = "validPassword123";
        String email = "validemail@example.com";

        when(this.passwordEncoder.encode(password)).thenReturn("encodedPassword");

        this.registerService.registerUser(Role.USER, username, password, email);

        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowInvalidPasswordException() {
        String username = "validUser";
        String password = "inv";
        String email = "validemail@example.com";

        assertThrows(InvalidPasswordException.class, () -> {
            this.registerService.registerUser(Role.USER, username, password, email);
        });

        verify(this.userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowInvalidEmailException() {
        String username = "validUser";
        String password = "validPassword123";
        String email = "invalid-email";

        assertThrows(InvalidEmailException.class, () -> {
            this.registerService.registerUser(Role.USER, username, password, email);
        });

        verify(this.userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        String username = "validUser";
        String password = "validPassword123";
        String email = "validemail@example.com";

        when(this.passwordEncoder.encode(password)).thenReturn("encodedPassword");
        doThrow(new DataIntegrityViolationException("Duplicate entry 'validUser' for key 'username'"))
                .when(this.userRepository).save(any(User.class));

        assertThrows(UserAlreadyExistsException.class, () -> {
            this.registerService.registerUser(Role.USER, username, password, email);
        });

        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        String username = "validUser";
        String password = "validPassword123";
        String email = "validemail@example.com";

        when(this.passwordEncoder.encode(password)).thenReturn("encodedPassword");
        doThrow(new DataIntegrityViolationException("Duplicate entry 'validemail@example.com' for key 'email'"))
                .when(this.userRepository).save(any(User.class));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            this.registerService.registerUser(Role.USER, username, password, email);
        });

        verify(this.userRepository, times(1)).save(any(User.class));
    }

}
