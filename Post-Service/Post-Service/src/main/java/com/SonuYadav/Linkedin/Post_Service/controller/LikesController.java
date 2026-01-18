package com.SonuYadav.Linkedin.Post_Service.controller;

import com.SonuYadav.Linkedin.Post_Service.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/likes")
public class LikesController {

    private final PostLikesService postLikesService;
    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        log.info("Like post endpoint called");
        postLikesService.likePost(postId,1L);
        return ResponseEntity.noContent().build();
    }

}
