package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.data.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)   // игнорируем posts/likes/comments
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}