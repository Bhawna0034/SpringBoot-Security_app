package com.bhawna.SpringBoot.Security_app.services;

import com.bhawna.SpringBoot.Security_app.dtos.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto getPostById(Long postId);

    PostDto createNewPost(PostDto postDto);
}
