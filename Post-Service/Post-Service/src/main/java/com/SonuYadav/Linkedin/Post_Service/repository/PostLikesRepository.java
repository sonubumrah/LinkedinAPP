package com.SonuYadav.Linkedin.Post_Service.repository;

import com.SonuYadav.Linkedin.Post_Service.entity.PostLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    @Transactional
    void deleteByPostIdAndUserId(Long postId, long userId);
}
