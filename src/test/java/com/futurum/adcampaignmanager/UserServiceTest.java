package com.futurum.adcampaignmanager;

import com.futurum.adcampaignmanager.entity.User;
import com.futurum.adcampaignmanager.enums.Role;
import com.futurum.adcampaignmanager.repositories.UserRepository;
import com.futurum.adcampaignmanager.services.user.UserService;
import com.futurum.adcampaignmanager.services.user.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(this.securityContext);
    }

    @Test
    void shouldReturnAllUsersExceptCurrent() {
        String currentUsername = "currentUser";
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        User currentUser = new User();
        currentUser.setUsername(currentUsername);

        when(this.authentication.getName()).thenReturn(currentUsername);
        when(this.securityContext.getAuthentication()).thenReturn(this.authentication);
        when(this.userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, currentUser));

        List<User> users = this.userService.getAllUsersExceptCurrent();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertFalse(users.contains(currentUser));
    }

    @Test
    void shouldUpdateUserSuccessfully() throws UserAlreadyExistsException {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");

        User userDetails = new User();
        userDetails.setUsername("newUser");
        userDetails.setEmail("new@example.com");
        userDetails.setRole(Role.USER);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(this.userRepository.existsByUsernameAndIdNot(userDetails.getUsername().toLowerCase(), userId)).thenReturn(false);
        when(this.userRepository.existsByEmailAndIdNot(userDetails.getEmail().toLowerCase(), userId)).thenReturn(false);
        when(this.userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = this.userService.updateUser(userId, userDetails);

        assertEquals("newUser", updatedUser.getUsername());
        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals(Role.USER, updatedUser.getRole());
    }

    @Test
    void shouldThrowUsernameNotFoundException() {
        Long userId = 1L;
        User userDetails = new User();
        userDetails.setUsername("newUser");
        userDetails.setEmail("new@example.com");
        userDetails.setRole(Role.USER);

        when(this.userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            this.userService.updateUser(userId, userDetails);
        });

        verify(this.userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenUsernameExists() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");

        User userDetails = new User();
        userDetails.setUsername("newUser");
        userDetails.setEmail("new@example.com");
        userDetails.setRole(Role.USER);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(this.userRepository.existsByUsernameAndIdNot(userDetails.getUsername().toLowerCase(), userId)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            this.userService.updateUser(userId, userDetails);
        });

        verify(this.userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenEmailExists() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");

        User userDetails = new User();
        userDetails.setUsername("newUser");
        userDetails.setEmail("new@example.com");
        userDetails.setRole(Role.USER);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(this.userRepository.existsByUsernameAndIdNot(userDetails.getUsername().toLowerCase(), userId)).thenReturn(false);
        when(this.userRepository.existsByEmailAndIdNot(userDetails.getEmail().toLowerCase(), userId)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            this.userService.updateUser(userId, userDetails);
        });

        verify(this.userRepository, never()).save(any(User.class));
    }

}
