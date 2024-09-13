package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.LikeContent;
import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.exception.NotFoundException;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<LikeDTO> getAllLikes() {
        return likeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LikeDTO getLikeById(Long id) {
        return likeRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Like not found with id: " + id));
    }
    public boolean hasUserLikedPost(Long userId, Long postId) {
        // Check if the like already exists
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public LikeDTO createLike(LikeContent likeContent) {
        Like savedLike = new Like();
        savedLike.setUser(userRepository.getReferenceById(likeContent.getUserId()));
        savedLike.setPost(postRepository.getReferenceById(likeContent.getPostId()));
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
        throw new NotFoundException("Like not found with id: " + id);
    }

    public List<LikeDTO> getLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LikeDTO> getLikesByUserId(long userId) {
        return likeRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LikeDTO mapToDTO(Like like) {

        return LikeDTO.builder()
                .id(like.getId())
                .user(mapToUserDTO(like.getUser()))
                .post(mapToPostDTO(like.getPost()))
                .build();
    }

    private UserDTO mapToUserDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private PostDTO mapToPostDTO(Post post) {
        if (post == null) return null;

        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
