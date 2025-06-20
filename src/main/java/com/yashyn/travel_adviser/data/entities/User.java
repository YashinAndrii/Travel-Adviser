package com.yashyn.travel_adviser.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    @JsonIgnore
    private Long id;
    private String login;
    @JsonIgnore
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    private List<Trip> trips;

}
