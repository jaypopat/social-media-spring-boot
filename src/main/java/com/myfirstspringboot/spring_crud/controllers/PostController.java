package com.myfirstspringboot.spring_crud.controllers;

import com.myfirstspringboot.spring_crud.dto.CommentDTO;
import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostContent;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.service.CommentService;
import com.myfirstspringboot.spring_crud.service.LikeService;
import com.myfirstspringboot.spring_crud.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

    public PostController(PostService postService, LikeService likeService, CommentService commentService) {
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostContent postContent) {
        PostDTO createdPost = postService.createPost(postContent);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        return deleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostContent postContent) {
        PostDTO updatedPost = postService.updatePost(id, postContent);
        return updatedPost != null ?
                new ResponseEntity<>(updatedPost, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return post != null ?
                new ResponseEntity<>(post, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeDTO>> getLikesByPostId(@PathVariable Long id) {
        List<LikeDTO> likes = likeService.getLikesByPostId(id);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long id) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
