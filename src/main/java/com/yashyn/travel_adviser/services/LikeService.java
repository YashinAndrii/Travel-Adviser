package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.LikeDto;
import com.yashyn.travel_adviser.data.entities.Like;
import com.yashyn.travel_adviser.data.mapper.LikeMapper;
import com.yashyn.travel_adviser.data.repositories.LikeRepository;
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
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<LikeDto> getLikesByPostId(UUID postId) {
        return likeRepository.findByPostId(postId)
                .stream()
                .map(likeMapper::toDto)
                .toList();
    }

    @Transactional
    public LikeDto addLike(LikeDto dto) {
        var user = userRepository.findById(dto.getUserId()).orElseThrow();
        var post = postRepository.findById(dto.getPostId()).orElseThrow();

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(java.time.LocalDateTime.now());

        return likeMapper.toDto(likeRepository.save(like));
    }

    @Transactional
    public void removeLike(UUID userId, UUID postId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
