package com.myfirstspringboot.spring_crud.controllers;

import com.myfirstspringboot.spring_crud.dto.LikeContent;
import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.model.Like;
import com.myfirstspringboot.spring_crud.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<LikeDTO>> getAllLikes() {
        List<LikeDTO> likes = likeService.getAllLikes();
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LikeDTO> createLike(@RequestBody LikeContent like) {
        LikeDTO createdLike = likeService.createLike(like);

        if (likeService.hasUserLikedPost(like.getUserId(), like.getPostId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        boolean deleted = likeService.deleteLike(id);
        return deleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LikeDTO> updateLike(@PathVariable Long id, @RequestBody Like like) {
        LikeDTO updatedLike = likeService.updateLike(id, like);
        return new ResponseEntity<>(updatedLike, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeDTO> getLikeById(@PathVariable Long id) {
        LikeDTO like = likeService.getLikeById(id);
        return new ResponseEntity<>(like, HttpStatus.OK) ;
    }
}
