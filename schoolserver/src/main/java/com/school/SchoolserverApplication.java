package com.school;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.school.model.User;
import com.school.model.UserRole;
import com.school.model.Role;
import com.school.service.UserService;

@SpringBootApplication
public class SchoolserverApplication implements CommandLineRunner{


	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SchoolserverApplication.class, args);
	}

	@Override
	public void run(String... args){
		System.out.println("Starting Code");

		User user = new User();

		user.setFirstname("Tejas");
		user.setLastname("Yelne");
		user.setUsername("tejas123");
		user.setPassword("asdf");
		user.setEmail("tejas@gmail.com");
		user.setProfile("Default.png");
		user.setPhone("745858353");

		Role role1= new Role();
		role1.setRoleID(42L);
		role1.setRoleName("NORMAL");

		Set<UserRole> userRoleSet = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setRole(role1);
		userRole.setUser(user);

		userRoleSet.add(userRole);
		User user1;
		try {
			user1 = this.userService.createUser(user, userRoleSet);
			System.out.println(user1.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
