package com.futurum.adcampaignmanager.services.user.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("User with this name already exists.");
    }


}
