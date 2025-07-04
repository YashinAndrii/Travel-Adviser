package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.data.mapper.UserMapper;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setCreatedAt(java.time.LocalDateTime.now());
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public Optional<UserDto> updateUser(UUID id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(userDto.getUsername());
                    existing.setAvatarUrl(userDto.getAvatarUrl());
                    existing.setBio(userDto.getBio());
                    return userMapper.toDto(userRepository.save(existing));
                });
    }

    @Transactional
    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
