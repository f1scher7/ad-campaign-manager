package com.futurum.adcampaignmanager.services.user.exceptions;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException() {
        super("Email address already exists");
    }

}
