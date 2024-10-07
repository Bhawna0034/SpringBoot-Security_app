package com.bhawna.SpringBoot.Security_app.services;

import com.bhawna.SpringBoot.Security_app.dtos.PostDto;
import com.bhawna.SpringBoot.Security_app.entities.PostEntity;
import com.bhawna.SpringBoot.Security_app.exceptions.ResourceNotFoundException;
import com.bhawna.SpringBoot.Security_app.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id: " + postId));

        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
        PostEntity savedEntity = postRepository.save(postEntity);
        return modelMapper.map(savedEntity, PostDto.class);
    }
}
