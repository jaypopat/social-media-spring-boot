package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.CommentContent;
import com.myfirstspringboot.spring_crud.dto.CommentDTO;
import com.myfirstspringboot.spring_crud.dto.PostDTO;
import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.exception.NotFoundException;
import com.myfirstspringboot.spring_crud.model.Comment;
import com.myfirstspringboot.spring_crud.model.Post;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.CommentRepository;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public CommentDTO getCommentsByCommentId(Long id) {
        return commentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }


    public CommentDTO createComment(CommentContent commentContent) {
        Comment savedComment = new Comment();
        savedComment.setText(commentContent.getText());
        savedComment.setUser(userRepository.getReferenceById(commentContent.getUserId()));
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
        throw new NotFoundException("Comment not found with id: " + id);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByUserId(long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .text(comment.getText())
                .user(mapToUserDTO(comment.getUser()))
                .post(mapToPostDTO(comment.getPost()))
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
