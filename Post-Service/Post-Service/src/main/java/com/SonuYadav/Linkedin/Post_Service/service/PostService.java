package com.SonuYadav.Linkedin.Post_Service.service;

import com.SonuYadav.Linkedin.Post_Service.controller.PostController;
import com.SonuYadav.Linkedin.Post_Service.dto.PostCreateRequestDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostDto;
import com.SonuYadav.Linkedin.Post_Service.entity.Post;
import com.SonuYadav.Linkedin.Post_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.Post_Service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

        private final PostRepository postRepository;
        private final ModelMapper modelMapper;

        public PostDto createPost(PostCreateRequestDto postDto,Long id){
                Post post=modelMapper.map(postDto,Post.class);
                post.setUserId(id);
                postRepository.save(post);
                return modelMapper.map(post,PostDto.class);
        }

        public PostDto getPostById(Long postId) {
                Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
                return modelMapper.map(post,PostDto.class);
        }

        public List<PostDto> getPostsByUserId(Long userId) {
                List<Post> posts=postRepository.findByUserId(userId);
                return posts.
                        stream().
                        map(post -> modelMapper.map(post,PostDto.class)).
                        collect(Collectors.toList());

        }
}
