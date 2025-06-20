package com.yashyn.travel_adviser.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("User with login '" + login + "' not found");
    }
}

