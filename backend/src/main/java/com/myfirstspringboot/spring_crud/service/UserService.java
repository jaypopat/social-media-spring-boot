package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserContent;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.exception.NotFoundException;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PostService postService;

    public UserService(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public User createUser(UserContent userContent) {
        User user = new User();
        user.setName(userContent.getName());
        user.setPassword(userContent.getPassword());
        user.setEmail(userContent.getEmail());
        return userRepository.save(user);
    }

    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? convertToDTO(user) : null;
    }


    public User updateUser(long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userDetails.getPassword());
        }

        return userRepository.save(user);
    }

    public boolean deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
    public List<PostDTO> getUserFeed(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<User> following = user.getFollowing();

        return postService.feedForUsers(following);
    }
    public void followUser(long followerId, long followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new NotFoundException("Follower user not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new NotFoundException("Followed user not found"));

        follower.getFollowing().add(followed);

        userRepository.save(follower);
    }

    public void unfollowUser(long followerId, long followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new NotFoundException("Follower user not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new NotFoundException("Followed user not found"));

        follower.getFollowing().remove(followed);

        userRepository.save(follower);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder().email(user.getEmail()).id(user.getId()).name(user.getName()).build();
    }
    public List<UserDTO> searchUsers(String keyword) {
        List<User> users = userRepository.searchUsers(keyword);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getFollowing(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return user.getFollowing().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}