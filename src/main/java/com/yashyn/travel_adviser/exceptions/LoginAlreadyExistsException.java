package com.yashyn.travel_adviser.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login already exists: " + login);
    }
}
