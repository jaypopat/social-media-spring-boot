package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.Like;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.LikeRepository;
import com.myfirstspringboot.spring_crud.repository.PostRepository;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
    }

    public List<LikeDTO> getAllLikes() {
        return likeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LikeDTO getLikeById(Long id) {
        return likeRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public LikeDTO createLike(Like like) {
        Like savedLike = likeRepository.save(like);
        return mapToDTO(savedLike);
    }

    public boolean deleteLike(Long id) {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public LikeDTO updateLike(Long id, Like like) {
        if (likeRepository.existsById(id)) {
            like.setId(id);
            Like updatedLike = likeRepository.save(like);
            return mapToDTO(updatedLike);
        }
        return null;
    }
    public List<LikeDTO> getLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LikeDTO mapToDTO(Like like) {
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

    private UserDTO mapToUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;


    }

    public List<LikeDTO> getLikesByUserId(long userId) {
        return likeRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
