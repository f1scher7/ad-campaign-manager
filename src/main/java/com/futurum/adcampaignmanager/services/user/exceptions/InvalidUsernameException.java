package com.futurum.adcampaignmanager.services.user.exceptions;

public class InvalidUsernameException extends Exception {

    public InvalidUsernameException() {
        super("Login musi mieć od 4 do 12 znaków i nie może zawierać symboli specjalnych");
    }

}
