package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.InvalidCredentials;
import com.yashyn.travel_adviser.exceptions.LoginAlreadyExistsException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String login, String password) {
        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new LoginAlreadyExistsException(login);
        }
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(new User(login, encodedPassword));
    }

    public User login(String login, String password) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new InvalidCredentials("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentials("Invalid credentials");
        }

        return user;
    }

/*    public String createUser(User user) {
        try {
            User createdUser = userRepository.save(user);
            return createdUser.getLogin();
        } catch (DataAccessException | ConstraintViolationException e) {
            throw new RuntimeException("User can not be created", e);
        }
    }*/

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

/*    public String updateUser(User user) {
        try {
            User updatedUser = userRepository.save(user);
            return updatedUser.getLogin();
        } catch (DataAccessException | ConstraintViolationException e) {
            throw new RuntimeException("User can not be updated", e);
        }
    }*/

    public void deleteUser(String login) {
        userRepository.deleteUserByLogin(login);
    }

/*    public void addPreferencesToUser(String login, Set<String> preferences) {
        User user = findUserByLogin(login);
        Set<String> oldPreferences = user.getPreferences();
        if (oldPreferences == null) {
            user.setPreferences(preferences);
        } else user.getPreferences().addAll(preferences);
        userRepository.save(user);
    }*/

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
