package com.insurance.policyapp.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.dto.UserResponse;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AdminController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addAdmin(@RequestBody User user) {
        try {
            if (user.getUsername().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Username cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (user.getEmail().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Email cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(user.getEmail());

            if (!emailMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid email");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (user.getMobile().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Mobile cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String mobileRegex = "^[0-9]{10}$";
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            Matcher mobileMatcher = mobilePattern.matcher(user.getMobile());

            if (!mobileMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid mobile");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (user.getPassword().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Password cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            Pattern passwordPattern = Pattern.compile(passwordRegex);
            Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

            if (!passwordMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Enter a strong password");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User myuser = this.userDetailsService.findOne(email);
            if (!myuser.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }
            
            User isUser = this.userDetailsService.findOne(user.getEmail());
            if (isUser != null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("This email is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }
            isUser = this.userDetailsService.findByMobile(user.getMobile());
            if (isUser != null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("This mobile is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            user.setRole("admin");
            userDetailsService.createUser(user);

            List<User> customers = userDetailsService.findAll();
            Iterator<User> iterator = customers.iterator();
            while (iterator.hasNext()) {
                User element = iterator.next();
                if (element.getRole().equals("customer")) {
                    iterator.remove();
                }
                else {
                	element.setPassword(null);
                }
            }
            customers.forEach(customer -> customer.setPassword(null));

            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            myresponse.setUsers(customers);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @GetMapping("/view-customers")
    public ResponseEntity<?> viewAllCustomers() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User myuser = this.userDetailsService.findOne(email);
            if (!myuser.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            List<User> customers = userDetailsService.findAll();
            Iterator<User> iterator = customers.iterator();
            while (iterator.hasNext()) {
                User element = iterator.next();
                if (element.getRole().equals("admin")) {
                    iterator.remove();
                }
                else {
                	element.setPassword(null);
                }
            }
            customers.forEach(customer -> customer.setPassword(null));
            System.out.println("customers: "+ customers);
            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            myresponse.setUsers(customers);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @GetMapping("/view-customers/{id}")
    public ResponseEntity<?> viewCustomer(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User myuser = this.userDetailsService.findOne(email);
            if (!myuser.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            User customer = userDetailsService.findById(id);
            if(customer == null) {
            	JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
            }
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(customer.getUserId());
            userResponse.setUsername(customer.getUsername());
            userResponse.setEmail(customer.getEmail());
            userResponse.setMobile(customer.getMobile());
            userResponse.setRole(customer.getRole());
            userResponse.setCreatedAt(customer.getCreatedAt());
            userResponse.setUpdatedAt(customer.getUpdatedAt());
            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            myresponse.setUser(userResponse);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User myuser = this.userDetailsService.findOne(email);
            if (!myuser.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            if(id == myuser.getUserId()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("You cannot delete your own account");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            User user = userDetailsService.findById(id);
            if (user == null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
            }
            userDetailsService.delete(id);
            List<User> customers = userDetailsService.findAll();
            Iterator<User> iterator = customers.iterator();
            while (iterator.hasNext()) {
                User element = iterator.next();
                if (element.getRole().equals("admin")) {
                    iterator.remove();
                }
                else {
                	element.setPassword(null);
                }
            }
            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            myresponse.setUsers(customers);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User u) {
        try {
        	System.out.println("request user: "+u);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User myuser = this.userDetailsService.findOne(email);
            if (!myuser.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            if (u.getUsername() != null && u.getUsername().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Username cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (u.getEmail() != null && u.getEmail().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Email cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(u.getEmail());

            if (!emailMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid email");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (u.getMobile() != null && u.getMobile().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Mobile cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String mobileRegex = "^[0-9]{10}$";
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            Matcher mobileMatcher = mobilePattern.matcher(u.getMobile());

            if (!mobileMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid mobile");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (u.getAddress() != null && u.getAddress().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Address cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            User u1 = userDetailsService.findById(u.getUserId());
            if (u1 == null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
            }

            User u2 = this.userDetailsService.findOne(u.getEmail());
            if (u2 != null && u2.getUserId() != u1.getUserId()) {
                JwtResponse myresponse = new JwtResponse();
                myresponse.setSuccess(false);
                myresponse.setError("This email is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myresponse);
            }

            u2 = this.userDetailsService.findByMobile(u.getMobile());
            if (u2 != null && u2.getUserId() != u1.getUserId()) {
                JwtResponse myresponse = new JwtResponse();
                myresponse.setSuccess(false);
                myresponse.setError("This mobile is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myresponse);
            }

            userDetailsService.update(u);
            List<User> customers = userDetailsService.findAll();
            Iterator<User> iterator = customers.iterator();
            while (iterator.hasNext()) {
                User element = iterator.next();
                if (element.getRole().equals("admin")) {
                    iterator.remove();
                }
                else {
                	element.setPassword(null);
                }
            }
            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            myresponse.setUsers(customers);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }
}