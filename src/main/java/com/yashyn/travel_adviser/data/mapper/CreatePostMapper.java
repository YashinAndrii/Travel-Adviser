package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import com.yashyn.travel_adviser.data.entities.Post;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.entities.User;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import com.yashyn.travel_adviser.services.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public abstract class CreatePostMapper {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "user", source = "userId", qualifiedByName = "resolveUser")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapTripType")
    @Mapping(target = "photos", source = "photos", qualifiedByName = "mapPhotoKeys")
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

    @Named("mapPhotoKey")
    protected String mapPhotoKey(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            String[] segments = path.split("/");
            return segments[segments.length - 1];
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid photo URL: " + url, e);
        }
    }

    @Named("mapPhotoKeys")
    protected Set<String> mapPhotoKeys(Set<String> urls) {
        return urls.stream()
                .map(this::mapPhotoKey)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    protected void afterMapping(@MappingTarget Post post) {
        post.setLikes(Collections.emptySet());
        post.setComments(Collections.emptySet());
    }
}
