package com.insurance.policyapp;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.insurance.policyapp.models.User;
import com.insurance.policyapp.repositories.UserRepo;

import jakarta.transaction.Transactional;

@SpringBootTest
class PolicyAppApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Test
	public void getAllUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		this.userRepo.findAll().iterator().forEachRemaining(users::add);
		Assertions.assertTrue(users.size() > 0);
	}
	
	@Test
	@Transactional
	public void registerCustomer() throws Exception {
		User user = new User();
		user.setUsername("Test User");
		user.setEmail("testuser@gmail.com");
		user.setMobile("1599512365");
		user.setPassword(encoder.encode("Test123@"));
		user = this.userRepo.save(user);
		Assertions.assertNotNull(user);
	}
	
	@Test
	public void loginUser() throws Exception {
		String email = "admin@gmail.com";
		String password = "Test123@";
		User user = this.userRepo.findByEmail(email);
		boolean passwordMatch = encoder.matches(password, user.getPassword());
		Assertions.assertNotNull(user);
		Assertions.assertTrue(passwordMatch);
	}
	
	@Test
	public void checkRole() throws Exception {
		String email = "admin@gmail.com";
		User user = this.userRepo.findByEmail(email);
		Assertions.assertNotNull(user);
		Assertions.assertTrue(user.getRole().equals("admin"));
	}
	
	@Test
	@Transactional
	public void updateProfile() throws Exception {
		String email = "test1@gmail.com";
		User user = this.userRepo.findByEmail(email);
		Assertions.assertNotNull(user);
		user.setUsername("John Doe");
		user = this.userRepo.save(user);
		Assertions.assertTrue(user.getUsername().equals("John Doe"));
	}
}
