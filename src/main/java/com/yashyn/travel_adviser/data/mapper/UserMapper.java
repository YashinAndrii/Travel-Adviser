package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.data.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TripMapper.class)
public interface UserMapper {

    UserDto toDto(User entity);

    List<UserDto> toDto(List<User> entities);
}