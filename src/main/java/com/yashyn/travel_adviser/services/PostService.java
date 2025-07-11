package com.yashyn.travel_adviser.services;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.data.entities.TripType;
import com.yashyn.travel_adviser.data.mapper.CreatePostMapper;
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
    private final CreatePostMapper createPostMapper;

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
    public PostDto createPost(CreatePostDto dto) { //TODO take userId from auth
        var post = createPostMapper.toEntity(dto);
        return postMapper.toDto(postRepository.save(post));
    }

    @Transactional
    public PostDto updatePost(UUID id, PostDto dto) {
        var existing = postRepository.findById(id).orElseThrow();
        existing.setPhotos(dto.getPhotos());
        existing.setCountries(dto.getCountries());
        existing.setCities(dto.getCities());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
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