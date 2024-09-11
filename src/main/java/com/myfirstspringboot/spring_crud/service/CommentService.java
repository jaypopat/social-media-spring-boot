package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.CommentDTO;
import com.myfirstspringboot.spring_crud.dto.LikeDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.Comment;
import com.myfirstspringboot.spring_crud.model.Like;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.CommentRepository;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeService likeService;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, LikeService likeService) {
        this.commentRepository = commentRepository;
        this.likeService = likeService;
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentsByCommentId(Long id) {
        return commentRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public CommentDTO createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CommentDTO updateComment(Long id, Comment comment) {
        if (commentRepository.existsById(id)) {
            comment.setId(id);
            Comment updatedComment = commentRepository.save(comment);
            return mapToDTO(updatedComment);
        }
        return null;
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());

        if (comment.getUser() != null) {
            User user = comment.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            dto.setUser(userDTO);
        }
        if (comment.getPost() != null) {
            Post post = comment.getPost();
            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setContent(post.getContent());
            postDTO.setCreatedAt(post.getCreatedAt());
            dto.setPost(postDTO);
        }

        return dto;
    }

    public List<CommentDTO> getCommentsByPostId(Long id) {
        return commentRepository.findByPostId(id).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByUserId(long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }
}
