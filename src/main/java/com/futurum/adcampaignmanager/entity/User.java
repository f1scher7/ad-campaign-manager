package com.futurum.adcampaignmanager.entity;

import com.futurum.adcampaignmanager.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean isAccountNonLocked = true;


    public User () {}

    public User(Role role, String username, String password, String email) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public void lockAccount() {
        this.isAccountNonLocked = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }


    public Long getId() {
        return this.id;
    }

    public Role getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", role=" + this.role +
                ", username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", email='" + this.email + '\'' +
                ", isAccountNonLocked=" + this.isAccountNonLocked +
                '}';
    }

}
