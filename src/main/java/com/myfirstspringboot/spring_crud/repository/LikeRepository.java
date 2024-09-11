package com.myfirstspringboot.spring_crud.repository;

import com.myfirstspringboot.spring_crud.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
}
