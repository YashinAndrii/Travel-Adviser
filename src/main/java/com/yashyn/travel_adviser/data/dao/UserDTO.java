package com.yashyn.travel_adviser.data.dao;

import jakarta.persistence.Id;

import java.util.Set;

public class UserDTO {
    @Id
    private String login;
    private Set<String> preferences;
}
