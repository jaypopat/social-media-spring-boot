package com.myfirstspringboot.spring_crud.controllers;

import com.myfirstspringboot.spring_crud.dto.CommentDTO;
import com.myfirstspringboot.spring_crud.model.Comment;
import com.myfirstspringboot.spring_crud.service.CommentService;
import com.myfirstspringboot.spring_crud.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService, LikeService likeService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment) {
        CommentDTO createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        return deleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        CommentDTO updatedComment = commentService.updateComment(id, comment);
        return updatedComment != null ?
                new ResponseEntity<>(updatedComment, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        CommentDTO comment = commentService.getCommentsByCommentId(id);
        return comment != null ?
                new ResponseEntity<>(comment, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
