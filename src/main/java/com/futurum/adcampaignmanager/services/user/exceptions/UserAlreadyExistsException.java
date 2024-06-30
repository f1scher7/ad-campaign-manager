package com.futurum.adcampaignmanager.services.user.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("Nazwa użytkownika już istnieje.");
    }


}
