package com.myfirstspringboot.spring_crud.service;

import com.myfirstspringboot.spring_crud.dto.UserDTO;
import com.myfirstspringboot.spring_crud.model.User;
import com.myfirstspringboot.spring_crud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
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
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }


    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}