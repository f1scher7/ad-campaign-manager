package com.futurum.adcampaignmanager.services.user.exceptions;

public class InvalidUsernameException extends Exception {

    public InvalidUsernameException() {
        super("Username must be between 4 and 12 characters long and cannot contain special symbols");
    }

}
