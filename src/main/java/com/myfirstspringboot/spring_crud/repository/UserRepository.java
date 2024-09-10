package com.myfirstspringboot.spring_crud.repository;

import com.myfirstspringboot.spring_crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
