package com.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
