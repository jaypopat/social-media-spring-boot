package com.myfirstspringboot.spring_crud.controllers;

import com.myfirstspringboot.spring_crud.dto.CommentDTO;
import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.service.CommentService;
import com.myfirstspringboot.spring_crud.service.LikeService;
import com.myfirstspringboot.spring_crud.service.PostService;
import com.myfirstspringboot.spring_crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    public UserController(UserService userService, PostService postService, CommentService commentService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        UserDTO user = userService.getUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String keyword) {
        List<UserDTO> users = userService.searchUsers(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable long userId) {
        var posts = postService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable long userId) {
        var comments = commentService.getCommentsByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    // get all user likes
    @GetMapping("/{userId}/likes")
    public ResponseEntity<List<LikeDTO>> getLikes(@PathVariable long userId) {
        var likes = likeService.getLikesByUserId(userId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
}
