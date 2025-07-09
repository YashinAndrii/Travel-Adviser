package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import com.yashyn.travel_adviser.data.entities.Post;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import com.yashyn.travel_adviser.services.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public abstract class CreatePostMapper {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "user", source = "userId", qualifiedByName = "resolveUser")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapTripType")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract Post toEntity(CreatePostDto dto);

    @Named("resolveUser")
    protected User resolveUser(UUID userId) {
        return userService.getUserById(userId)
                .map(userMapper::toEntity)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }

    @Named("mapTripType")
    protected TripType mapTripType(String type) {
        return type != null ? TripType.valueOf(type.toUpperCase()) : null;
    }

    @AfterMapping
    protected void afterMapping(@MappingTarget Post post) {
        post.setLikes(Collections.emptySet());
        post.setComments(Collections.emptySet());
    }
}
