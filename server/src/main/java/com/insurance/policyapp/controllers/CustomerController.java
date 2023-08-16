package com.insurance.policyapp.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.dto.LoginRequest;
import com.insurance.policyapp.dto.RegisterRequest;
import com.insurance.policyapp.dto.UpdatePasswordRequest;
import com.insurance.policyapp.dto.UserResponse;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;
import com.insurance.policyapp.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    public CustomerController(CustomUserDetailsService customUserDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response)
            throws Exception {
        try {

            if (request.getUsername().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Username cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (request.getEmail().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Email cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(request.getEmail());

            if (!emailMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid email");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (request.getMobile().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Mobile cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String mobileRegex = "^[0-9]{10}$";
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            Matcher mobileMatcher = mobilePattern.matcher(request.getMobile());

            if (!mobileMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid mobile");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (request.getPassword().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Password cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            Pattern passwordPattern = Pattern.compile(passwordRegex);
            Matcher passwordMatcher = passwordPattern.matcher(request.getPassword());

            if (!passwordMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Enter a strong password");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            User isUser = this.customUserDetailsService.findOne(request.getEmail());
            if (isUser != null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("This email is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }
            isUser = this.customUserDetailsService.findByMobile(request.getMobile());
            if (isUser != null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("This mobile is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }
            User newuser = new User();
            newuser.setUsername(request.getUsername());
            newuser.setEmail(request.getEmail());
            newuser.setMobile(request.getMobile());
            newuser.setPassword(passwordEncoder.encode(request.getPassword()));
            newuser.setRole("customer");
            this.customUserDetailsService.createUser(newuser);

            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest jwtRequest, HttpServletResponse response)
            throws Exception {
        try {
            if (jwtRequest.getEmail().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Email cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(jwtRequest.getEmail());

            if (!emailMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid email");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (jwtRequest.getPassword().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Password cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
            } catch (DisabledException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is disabled");
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
            final String token = jwtUtil.generateToken(userDetails);

            Cookie jwtCookie = new Cookie("authorization", token);
            jwtCookie.setMaxAge(86400000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            JwtResponse myresponse = new JwtResponse();
            User user = customUserDetailsService.findOne(jwtRequest.getEmail());
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setMobile(user.getMobile());
            userResponse.setRole(user.getRole());
            userResponse.setAddress(user.getAddress());
            userResponse.setCreatedAt(user.getCreatedAt());
            userResponse.setUpdatedAt(user.getUpdatedAt());

            myresponse.setSuccess(true);
            myresponse.setUser(userResponse);
            myresponse.setToken(token);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest jwtRequest, HttpServletResponse response)
            throws Exception {
        try {

            if (jwtRequest.getOldPassword().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Old password cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (jwtRequest.getNewPassword().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("New password cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            Pattern passwordPattern = Pattern.compile(passwordRegex);
            Matcher passwordMatcher = passwordPattern.matcher(jwtRequest.getNewPassword());

            if (!passwordMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Enter a strong password");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (!jwtRequest.getNewPassword().equals(jwtRequest.getConfirmPassword())) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Password mismatch");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = this.customUserDetailsService.findOne(email);
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            boolean passwordMatch = this.customUserDetailsService.checkPassword(userDetails,
                    jwtRequest.getOldPassword());
            if (passwordMatch == false) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Old password is incorrect");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            user.setPassword(passwordEncoder.encode(jwtRequest.getNewPassword()));
            user = this.customUserDetailsService.update(user);

            JwtResponse myresponse = new JwtResponse();
            myresponse.setSuccess(true);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User request, HttpServletRequest request1,
            HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = this.customUserDetailsService.findOne(email);

            if (request.getUsername().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Username cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (request.getEmail().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Email cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(request.getEmail());

            if (!emailMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid email");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            if (request.getMobile().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Mobile cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            String mobileRegex = "^[0-9]{10}$";
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            Matcher mobileMatcher = mobilePattern.matcher(request.getMobile());

            if (!mobileMatcher.matches()) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Invalid mobile");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            User u1 = this.customUserDetailsService.findOne(request.getEmail());
            if (u1 != null && user.getUserId() != u1.getUserId()) {
                JwtResponse myresponse = new JwtResponse();
                myresponse.setSuccess(false);
                myresponse.setError("This email is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myresponse);
            }

            u1 = this.customUserDetailsService.findByMobile(request.getMobile());
            if (u1 != null && user.getUserId() != u1.getUserId()) {
                JwtResponse myresponse = new JwtResponse();
                myresponse.setSuccess(false);
                myresponse.setError("This mobile is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myresponse);
            }

            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setMobile(request.getMobile());
            user.setAddress(request.getAddress());

            user = customUserDetailsService.update(user);

            Cookie[] jwtCookies = request1.getCookies();
            if (jwtCookies != null) {
                for (Cookie cookie : jwtCookies) {
                    if (cookie.getName().equals("authorization")) {
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }

            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            final String token = jwtUtil.generateToken(userDetails);

            Cookie jwtCookie = new Cookie("authorization", token);
            jwtCookie.setMaxAge(86400000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(true);
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            userResponse.setMobile(user.getMobile());
            userResponse.setAddress(user.getAddress());
            userResponse.setCreatedAt(user.getCreatedAt());
            userResponse.setUpdatedAt(user.getUpdatedAt());
            myResponse.setUser(userResponse);
            myResponse.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(myResponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }
}
