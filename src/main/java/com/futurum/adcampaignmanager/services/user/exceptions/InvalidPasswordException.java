package com.futurum.adcampaignmanager.services.user.exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super("Hasło musi mieć co najmniej 8 znaków, w tym jedną małą literę, jedną dużą literę i jedną cyfrę");
    }

}
