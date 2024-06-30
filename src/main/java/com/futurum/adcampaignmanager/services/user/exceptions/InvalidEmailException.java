package com.futurum.adcampaignmanager.services.user.exceptions;

public class InvalidEmailException extends Exception {

    public InvalidEmailException(String email) {
        super("Mail '" + email + "' nie jest poprawny");
    }

}
