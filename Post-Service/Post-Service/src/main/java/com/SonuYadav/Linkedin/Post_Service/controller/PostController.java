package com.SonuYadav.Linkedin.Post_Service.controller;

import com.SonuYadav.Linkedin.Post_Service.auth.UserContextHolder;
import com.SonuYadav.Linkedin.Post_Service.client.ConnectionsClient;
import com.SonuYadav.Linkedin.Post_Service.dto.PersonDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostCreateRequestDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostDto;
import com.SonuYadav.Linkedin.Post_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.Post_Service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/core")
public class PostController {
    private  final PostService postService;
    private final ConnectionsClient connectionsClient;
    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto){
        PostDto createdPost=postService.createPost(postDto,1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping({"/{postId}"})
    ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
       String userId =UserContextHolder.getUserId();
        List<PersonDto> firstDegreeConnections = connectionsClient.getFirstDegreeConnections(Long.parseLong(userId));
       log.info("Get post by id endpoint called for postId: {} and userId: {}", postId, userId);
        PostDto postDto=postService.getPostById(postId);
        return  ResponseEntity.ok(postDto);
    }
    @GetMapping("/users/{userId}/allPosts")
    ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId){
        List<PostDto> posts=postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }


}
