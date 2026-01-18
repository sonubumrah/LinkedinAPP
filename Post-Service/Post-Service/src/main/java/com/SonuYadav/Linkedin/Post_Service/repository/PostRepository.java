package com.SonuYadav.Linkedin.Post_Service.repository;

import com.SonuYadav.Linkedin.Post_Service.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}

