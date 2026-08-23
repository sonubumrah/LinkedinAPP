package com.SonuYadav.Linkedin.Post_Service.service;

import com.SonuYadav.Linkedin.Post_Service.auth.UserContextHolder;
import com.SonuYadav.Linkedin.Post_Service.controller.PostController;
import com.SonuYadav.Linkedin.Post_Service.dto.PostCreateRequestDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostDto;
import com.SonuYadav.Linkedin.Post_Service.entity.Post;
import com.SonuYadav.Linkedin.Post_Service.event.PostCreatedEvent;
import com.SonuYadav.Linkedin.Post_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.Post_Service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

        private final PostRepository postRepository;
        private final ModelMapper modelMapper;
        private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;
        @Value("${kafka.topic.create-post}")
        private String createPostTopic;


        public PostDto createPost(PostCreateRequestDto postDto){
                Long userID= Long.parseLong(UserContextHolder.getUserId());
                Post post=modelMapper.map(postDto,Post.class);
                post.setUserId(userID);
                postRepository.save(post);
                PostCreatedEvent postCreatedEvent=PostCreatedEvent.builder()
                        .postId(post.getId())
                        .postCreatedUserId(post.getUserId())
                        .postContent(post.getContent())
                        .build();
                kafkaTemplate.send(createPostTopic, postCreatedEvent);
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
