package com.futurum.adcampaignmanager.services.user.exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super("Password must have at least 8 characters, including one lowercase letter, one uppercase letter, and one digit");
    }

}
