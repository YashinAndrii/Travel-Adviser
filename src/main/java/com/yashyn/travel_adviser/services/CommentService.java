package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.CommentDto;
import com.yashyn.travel_adviser.data.entities.Comment;
import com.yashyn.travel_adviser.data.mapper.CommentMapper;
import com.yashyn.travel_adviser.data.repositories.CommentRepository;
import com.yashyn.travel_adviser.data.repositories.PostRepository;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CommentDto> getCommentsByPostId(UUID postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Transactional
    public CommentDto addComment(CommentDto dto) {
        var user = userRepository.findById(dto.getUserId()).orElseThrow();
        var post = postRepository.findById(dto.getPostId()).orElseThrow();

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(dto.getContent());
        comment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
