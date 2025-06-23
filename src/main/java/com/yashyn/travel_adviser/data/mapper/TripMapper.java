package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.CreateTripCommand;
import com.yashyn.travel_adviser.data.dto.TripDto;
import com.yashyn.travel_adviser.data.entities.Trip;
import com.yashyn.travel_adviser.data.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    TripDto toDto(Trip entity);

    List<TripDto> toDto(List<Trip> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "imagePath", ignore = true),
            @Mapping(target = "completed", constant = "false"),
            @Mapping(target = "advice", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Trip toEntity(CreateTripCommand cmd, @Context User user);
}
