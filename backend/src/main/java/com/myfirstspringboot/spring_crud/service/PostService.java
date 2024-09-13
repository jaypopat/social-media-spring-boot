package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostContent;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.Like;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.PostRepository;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public PostDTO createPost(PostContent postContent) {
        Post post = new Post();
        post.setCreatedAt(LocalDateTime.now());
        post.setContent(postContent.getContent());
        User user = userRepository.getReferenceById(postContent.getUserId());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PostDTO updatePost(Long id, PostContent postContent) {
        if (postRepository.existsById(id)) {

            Post post = new Post();
            post.setContent(postContent.getContent());
            post.setCreatedAt(LocalDateTime.now());
            User user = userRepository.getReferenceById(postContent.getUserId());
            post.setUser(user);
            Post updatedPost = postRepository.save(post);
            return mapToDTO(updatedPost);
        }
        return null;
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO dto = PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();


        if (post.getUser() != null) {
            User user = post.getUser();
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
            dto.setUser(userDTO);
        }

        if (post.getLikes() != null) {
            List<LikeDTO> likeDTOs = post.getLikes().stream()
                    .map(this::mapToLikeDTO)
                    .collect(Collectors.toList());
            dto.setLikes(likeDTOs);
        }

        return dto;
    }

    private LikeDTO mapToLikeDTO(Like like) {
        LikeDTO dto = LikeDTO.builder()
                .id(like.getId())
                .build();

        if (like.getUser() != null) {
            User user = like.getUser();
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
            dto.setUser(userDTO);
        }

        if (like.getPost() != null) {
            Post post = like.getPost();
            PostDTO postDTO = PostDTO.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .build();
            dto.setPost(postDTO);
        }

        return dto;
    }
    public List<PostDTO> feedForUsers(List<User> users) {
        return postRepository.findByUserIn(users).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getPostsByUserId(long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
