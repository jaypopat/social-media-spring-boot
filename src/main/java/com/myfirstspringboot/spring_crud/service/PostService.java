package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.Like;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.PostRepository;
import com.myfirstspringboot.spring_crud.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
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

    public PostDTO createPost(Post post) {
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

    public PostDTO updatePost(Long id, Post post) {
        if (postRepository.existsById(id)) {
            post.setId(id);
            Post updatedPost = postRepository.save(post);
            return mapToDTO(updatedPost);
        }
        return null;
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null) {
            User user = post.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
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
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());

        if (like.getUser() != null) {
            User user = like.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            dto.setUser(userDTO);
        }

        if (like.getPost() != null) {
            Post post = like.getPost();
            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setContent(post.getContent());
            postDTO.setCreatedAt(post.getCreatedAt());
            dto.setPost(postDTO);
        }

        return dto;
    }

    public List<PostDTO> getPostsByUserId(long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
