package com.futurum.adcampaignmanager.utils;

import org.springframework.stereotype.Component;

import static java.lang.Character.isLetterOrDigit;

@Component
public final class UserUtil {

    public static boolean isUsernameValid(String username) {
        if (username == null || username.length() > 12 || username.length() < 3) {
            return false;
        }

        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);

            if (!isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*");
    }

    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

}
