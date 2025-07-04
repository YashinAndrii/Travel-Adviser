package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.data.entities.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.avatarUrl", target = "avatarUrl")
    @Mapping(source = "planned", target = "planned")
    @Mapping(target = "likeCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostDto toDto(Post post);

    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "id", ignore = true)
    Post toEntity(PostDto dto);
}

