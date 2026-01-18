package com.SonuYadav.Linkedin.Post_Service.controller;

import com.SonuYadav.Linkedin.Post_Service.dto.PostCreateRequestDto;
import com.SonuYadav.Linkedin.Post_Service.dto.PostDto;
import com.SonuYadav.Linkedin.Post_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.Post_Service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private  final PostService postService;
    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto){
        PostDto createdPost=postService.createPost(postDto,1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping({"/{postId}"})
    ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        PostDto postDto=postService.getPostById(postId);
        return  ResponseEntity.ok(postDto);
    }


}
