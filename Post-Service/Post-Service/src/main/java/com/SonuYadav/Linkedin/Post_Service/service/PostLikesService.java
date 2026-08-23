package com.SonuYadav.Linkedin.Post_Service.service;

import com.SonuYadav.Linkedin.Post_Service.auth.UserContextHolder;
import com.SonuYadav.Linkedin.Post_Service.entity.PostLike;
import com.SonuYadav.Linkedin.Post_Service.event.PostCreatedEvent;
import com.SonuYadav.Linkedin.Post_Service.event.PostLikedEvent;
import com.SonuYadav.Linkedin.Post_Service.exception.BadRequestException;
import com.SonuYadav.Linkedin.Post_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.Post_Service.repository.PostLikesRepository;
import com.SonuYadav.Linkedin.Post_Service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikesService {
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;
    @Value("${kafka.topic.like-post}")
    private String likePostTopic;
    public void likePost(Long postId) {
        Long userId = Long.parseLong(UserContextHolder.getUserId());
        log.info("attempting to like post with id: {} by user with id: {}", postId, userId);
        Long postCreatedUserId = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId)).getUserId();
        boolean exists = postRepository.existsById(postId);
        if (!exists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikesRepository.existsByPostIdAndUserId(postId, userId);
        if(alreadyLiked) throw  new BadRequestException("Post already liked by user with id: " + userId);
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikesRepository.save(postLike);
        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .postLikedUserId(userId)
                .postCreatedUserId(postCreatedUserId)
                .build();
        kafkaTemplate.send(likePostTopic, postLikedEvent);
        log.info("post with id: {} liked by user with id: {} successfully", postId, userId);


    }

    public void unlikePost(Long postId) {
        Long userId = Long.parseLong(UserContextHolder.getUserId());
        log.info("attempting to unlike post with id: {} by user with id: {}", postId, userId);
        boolean exists = postRepository.existsById(postId);
        if (!exists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikesRepository.existsByPostIdAndUserId(postId, userId);
        if(!alreadyLiked) throw  new BadRequestException("Post not  liked by user with id: " + userId +" so cannot unlike");
        postLikesRepository.deleteByPostIdAndUserId(postId,userId);
        log.info("post with id: {} unliked by user with id: {} successfully", postId, userId);


    }
}
