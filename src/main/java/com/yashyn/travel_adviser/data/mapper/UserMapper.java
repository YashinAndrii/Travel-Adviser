package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.UserDto;
import com.yashyn.travel_adviser.data.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}