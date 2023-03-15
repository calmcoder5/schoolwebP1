package com.school.service;

import java.util.Set;

import com.school.model.User;
import com.school.model.UserRole;

public interface UserService {
    
    //creating User
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get user by username
    public User getUser(String username);

    //delete user by id
    public void deleteUser(Long userId);
}
