package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.mapper.PostMapper;
import com.yashyn.travel_adviser.data.repositories.PostRepository;
import com.yashyn.travel_adviser.data.repositories.UserRepository;
import com.yashyn.travel_adviser.exceptions.PostNotFoundException;
import com.yashyn.travel_adviser.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostDto> getPostsByUsername(String username) {
        return postRepository.findByUserUsername(username)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostDto> getPostsByUserId(UUID userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto getPostById(UUID id) {
        return postRepository.findById(id)
                .map(postMapper::toDto)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Transactional
    public PostDto createPost(PostDto dto) {
        var user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        var post = postMapper.toEntity(dto);
        post.setUser(user);
        post.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now());
        return postMapper.toDto(postRepository.save(post));
    }

    @Transactional
    public PostDto updatePost(UUID id, PostDto dto) {
        var existing = postRepository.findById(id).orElseThrow();
        existing.setPhotos(dto.getPhotos());
        existing.setCountries(dto.getCountries());
        existing.setCities(dto.getCities());
        existing.setDates(dto.getDates());
        existing.setType(TripType.valueOf(dto.getType()));
        existing.setBudget(dto.getBudget());
        existing.setPlanned(dto.isPlanned());
        return postMapper.toDto(postRepository.save(existing));
    }

    @Transactional
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}