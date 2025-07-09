package com.yashyn.travel_adviser.data.mapper;

import com.yashyn.travel_adviser.data.dto.CommentDto;
import com.yashyn.travel_adviser.data.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.avatarUrl", target = "avatarUrl")
    CommentDto toDto(Comment comment);
}
