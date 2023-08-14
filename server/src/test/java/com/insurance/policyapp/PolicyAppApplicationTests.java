package com.insurance.policyapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.insurance.policyapp.models.Policy;
import com.insurance.policyapp.models.Policycategory;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.repositories.PolicyRepository;
import com.insurance.policyapp.repositories.PolicycategoryRepo;
import com.insurance.policyapp.repositories.UserRepo;

import jakarta.transaction.Transactional;

@SpringBootTest
class PolicyAppApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PolicycategoryRepo policycategoryRepo;
	
	@Autowired
	private PolicyRepository policyRepository;
	
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
	
	//	Category
	
	@Test
	@Transactional
	public void addCategory() throws Exception {
		Policycategory category = new Policycategory();
        category.setName("Test Category");
        category = this.policycategoryRepo.save(category);
        Assertions.assertNotNull(category);
	}
	
	@Test
	@Transactional
	public void updateCategory() throws Exception {
		Policycategory category = this.policycategoryRepo.findById(8);
		Assertions.assertNotNull(category);
		category.setName("Cars");
		category = this.policycategoryRepo.save(category);
		Assertions.assertTrue(category.getName().equals("Cars"));
	}
	
	@Test
	@Transactional
	public void deleteCategory() throws Exception {
		this.policycategoryRepo.deleteById(8L);
		Policycategory category = this.policycategoryRepo.findById(8);
		Assertions.assertNull(category);
	}
	
	//	Policy
	
	@Test
	@Transactional
	public void addPolicy() throws Exception {
		Policy policy = new Policy();
		policy.setPolicyName("Test Policy");
		Policycategory category = this.policycategoryRepo.findById(8L);
		policy.setPolicydesc("Test policy description");
		policy.setPolicycategory(category);
		policy.setPremiumAmount(2000);
		policy.setSumAssured(100000);
		policy.setTenure(1);
		policy = this.policyRepository.save(policy);
		Assertions.assertNotNull(policy);
	}
	
	@Test
	@Transactional
	public void updatePolicy() throws Exception {
		Optional<Policy> optionalPolicy = this.policyRepository.findById(2);
		Policy policy = optionalPolicy.isPresent() ? optionalPolicy.get() : null;
		Assertions.assertNotNull(policy);
		policy.setPolicyName("Relicare Life Insurance");
		policy = this.policyRepository.save(policy);
		Assertions.assertTrue(policy.getPolicyName().equals("Relicare Life Insurance"));
	}
	
	@Test
	@Transactional
	public void deletePolicy() throws Exception {
		this.policyRepository.deleteById(2);
		Optional<Policy> optionalPolicy = this.policyRepository.findById(2);
		Policy policy = optionalPolicy.isPresent() ? optionalPolicy.get() : null;
		Assertions.assertNull(policy);
	}
}
