package com.insurance.policyapp.controllers;

import java.util.List;

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

import com.insurance.policyapp.dto.CategoryResponse;
import com.insurance.policyapp.dto.CreatePolicyRequest;
import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.models.Policycategory;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;
import com.insurance.policyapp.services.PolicycategoryService;

@RestController
@RequestMapping("/api/policycategories")
public class PolicycategoryController {

    @Autowired
    private PolicycategoryService policyCategoryService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPolicyCategories() {
        try {
            List<Policycategory> policyCategories = policyCategoryService.getAllPolicyCategories();
            CategoryResponse myresponse = new CategoryResponse();
            myresponse.setSuccess(true);
            myresponse.setList(policyCategories);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getPolicyCategoryById(@PathVariable Long categoryId) {
        try {
            Policycategory policyCategory = policyCategoryService.findById(categoryId);
            CategoryResponse myresponse = new CategoryResponse();
            myresponse.setSuccess(true);
            myresponse.setCategory(policyCategory);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createPolicyCategory(@RequestBody CreatePolicyRequest policyCategory) {
        try {
            if (policyCategory.getName().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Category name cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = this.customUserDetailsService.findOne(email);
            if (!user.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            Policycategory category = new Policycategory();
            category.setName(policyCategory.getName());
            policyCategoryService.createPolicycategory(category);
            List<Policycategory> policyCategories = policyCategoryService.getAllPolicyCategories();
            CategoryResponse myresponse = new CategoryResponse();
            myresponse.setSuccess(true);
            myresponse.setList(policyCategories);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updatePolicyCategory(
            @PathVariable Long categoryId, @RequestBody Policycategory policyCategory) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = this.customUserDetailsService.findOne(email);
            if (!user.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            if (policyCategory.getName().replaceAll("\\s+", "").length() == 0) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Category name cannot be empty");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
            }

            Policycategory category = policyCategoryService.findById(categoryId);
            if (category == null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
            }
            
            category.setName(policyCategory.getName());

            policyCategoryService.updatePolicycategory(category);
            List<Policycategory> policyCategories = policyCategoryService.getAllPolicyCategories();
            CategoryResponse myresponse = new CategoryResponse();
            myresponse.setSuccess(true);
            myresponse.setList(policyCategories);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deletePolicyCategory(@PathVariable Long categoryId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = this.customUserDetailsService.findOne(email);
            if (!user.getRole().equalsIgnoreCase("admin")) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Not allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
            }

            Policycategory category = policyCategoryService.findById(categoryId);
            if (category == null) {
                JwtResponse myResponse = new JwtResponse();
                myResponse.setSuccess(false);
                myResponse.setError("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
            }

            policyCategoryService.deletePolicycategory(categoryId);
            List<Policycategory> policyCategories = policyCategoryService.getAllPolicyCategories();
            CategoryResponse myresponse = new CategoryResponse();
            myresponse.setSuccess(true);
            myresponse.setList(policyCategories);
            return ResponseEntity.ok(myresponse);
        } catch (Exception e) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }
}