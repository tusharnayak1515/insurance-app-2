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

import com.insurance.policyapp.dto.AddPolicyRequest;
import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.dto.PolicyResponse;
import com.insurance.policyapp.models.Policy;
import com.insurance.policyapp.models.Policycategory;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;
import com.insurance.policyapp.services.PolicyService;
import com.insurance.policyapp.services.PolicycategoryService;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

	@Autowired
	private PolicyService policyService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private PolicycategoryService policycategoryService;

	@GetMapping("/")
	public ResponseEntity<?> ViewAllPolicies() {
		try {
			List<Policy> list = policyService.fetchPolicies();
			PolicyResponse myResponse = new PolicyResponse();
			myResponse.setSuccess(true);
			myResponse.setList(list);
			return ResponseEntity.ok(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> addpolicy(@RequestBody AddPolicyRequest request) {
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

			if (request.getPolicyName().replaceAll("\\s+", "").length() == 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy name cannot be empty");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
			}

			if (request.getPolicydesc().replaceAll("\\s+", "").length() == 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy description cannot be empty");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
			}

			Policycategory category = policycategoryService.findById(request.getCategoryId());
			if (category == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Category not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}
			
			if(request.getPremiumAmount() <= 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Premium amount should be greater than 0");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}
			
			if(request.getSumAssured() <= 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Sum assured should be greater than 0");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}
			
			if(request.getTenure() <= 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Tenure should be greater than 0");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}

			Policy policy = new Policy();
			
			policy.setPolicyName(request.getPolicyName());
			policy.setPolicydesc(request.getPolicydesc());
			policy.setPolicycategory(category);
			policy.setPremiumAmount(request.getPremiumAmount());
			policy.setSumAssured(request.getSumAssured());
			policy.setTenure(request.getTenure());

			policyService.savePolicy(policy);
			
			List<Policy> list = policyService.fetchPolicies();
			PolicyResponse myResponse = new PolicyResponse();
			myResponse.setSuccess(true);
			myResponse.setList(list);
			return ResponseEntity.ok(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePolicy(@PathVariable("id") int policyId,
			@RequestBody AddPolicyRequest request) {
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
			
			Policy policy = policyService.getPolicy(policyId);
			policy.setPolicyName(request.getPolicyName());
			Policycategory category = this.policycategoryService.findById(request.getCategoryId());
			policy.setPolicycategory(category);
			policy.setPolicydesc(request.getPolicydesc());

			policyService.updatePolicy(policy);
			List<Policy> list = policyService.fetchPolicies();
			PolicyResponse myResponse = new PolicyResponse();
			myResponse.setSuccess(true);
			myResponse.setList(list);
			return ResponseEntity.ok(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@DeleteMapping("{policyId}")
	public ResponseEntity<Object> deletePolicy(@PathVariable("policyId") int policyId) {
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
			
			Policy policy = this.policyService.findById(policyId);
			if(policy == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}
			
			policyService.deletePolicy(policyId);
			List<Policy> list = policyService.fetchPolicies();
			PolicyResponse myResponse = new PolicyResponse();
			myResponse.setSuccess(true);
			myResponse.setList(list);
			return ResponseEntity.ok(myResponse);			
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}


}