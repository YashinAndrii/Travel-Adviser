package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.data.mapper.UserMapper;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.InvalidCredentials;
import com.yashyn.travel_adviser.exceptions.LoginAlreadyExistsException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void register(String login, String rawPassword) {
        userRepository.findUserByLogin(login).ifPresent(u -> {
            throw new LoginAlreadyExistsException(login);
        });

        String encoded = passwordEncoder.encode(rawPassword);
        userRepository.save(new User(login, encoded));
    }

    @Transactional(readOnly = true)
    public UserDto login(String login, String rawPassword) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new InvalidCredentials("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentials("Invalid credentials");
        }

        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));

        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(String login) {
        userRepository.deleteUserByLogin(login);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }
}
