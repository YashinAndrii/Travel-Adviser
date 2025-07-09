package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.data.entities.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LikeMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.avatarUrl", target = "avatarUrl")
    @Mapping(source = "planned", target = "planned")
    PostDto toDto(Post post);

    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Post toEntity(PostDto dto);
}
