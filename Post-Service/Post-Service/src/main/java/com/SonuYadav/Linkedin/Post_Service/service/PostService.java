package com.SonuYadav.Linkedin.Post_Service.service;

import com.SonuYadav.Linkedin.Post_Service.controller.PostController;
import com.SonuYadav.Linkedin.Post_Service.dto.PostCreateRequestDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostDto;
import com.SonuYadav.Linkedin.Post_Service.entity.Post;
import com.SonuYadav.Linkedin.Post_Service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
