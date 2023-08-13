package com.insurance.policyapp.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurance.policyapp.models.User;
import com.insurance.policyapp.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }
        else {
        	return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
    }

    public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		this.userRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(long id) {
		this.userRepo.deleteById(id);
	}

	public User findOne(String email) {
		return this.userRepo.findByEmail(email);
	}
	
	public User findByMobile(String mobile) {
		return this.userRepo.findByMobile(mobile);
	}

	public User findById(long id) {
		Optional<User> optionalUser = this.userRepo.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    public User update(User userDto) {
    	User user = findById(userDto.getUserId());
        if(user != null) {
        	user.setUsername(userDto.getUsername());
        	user.setEmail(userDto.getEmail());
        	user.setMobile(userDto.getMobile());
        	user.setAddress(userDto.getAddress());
        	user = this.userRepo.save(user);
        }
        return user;
    }

    public User createUser(User user) {
		return this.userRepo.save(user); 
    }
    
    public boolean checkPassword(UserDetails userDetails, String password) {
        return passwordEncoder.matches(password, userDetails.getPassword());
    }
    
}
