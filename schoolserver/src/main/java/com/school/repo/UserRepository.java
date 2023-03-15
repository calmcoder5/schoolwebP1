package com.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    public User findByUsername(String username);
    
}
