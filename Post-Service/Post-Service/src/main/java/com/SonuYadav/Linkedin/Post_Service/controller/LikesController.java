package com.SonuYadav.Linkedin.Post_Service.controller;

import com.SonuYadav.Linkedin.Post_Service.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        log.info("DisLike post endpoint called");
        postLikesService.unlikePost(postId,1L);
        return ResponseEntity.noContent().build();
    }

}
