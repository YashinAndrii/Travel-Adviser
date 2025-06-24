package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.CreateTripRequest;
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
            @Mapping(target = "user", expression = "java(user)"),
            @Mapping(target = "countries", source = "cmd.countries"),
            @Mapping(target = "cities", source = "cmd.cities"),
            @Mapping(target = "type", source = "cmd.type"),
            @Mapping(target = "startDate", source = "cmd.startDate"),
            @Mapping(target = "endDate", source = "cmd.endDate"),
            @Mapping(target = "budget", source = "cmd.budget"),
            @Mapping(target = "includeTransport", source = "cmd.includeTransport"),
            @Mapping(target = "hasChildren", source = "cmd.hasChildren"),
            @Mapping(target = "additionalInfo", source = "cmd.additionalInfo")
    })
    Trip toEntity(CreateTripRequest cmd, @Context User user);
}
