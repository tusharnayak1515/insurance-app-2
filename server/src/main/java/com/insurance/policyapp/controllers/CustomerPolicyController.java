package com.insurance.policyapp.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.policyapp.dto.CustomerPolicyResponse;
import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.models.Customerpolicy;
import com.insurance.policyapp.models.Policy;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;
import com.insurance.policyapp.services.PolicyService;
import com.insurance.policyapp.services.customerpolicydetailsservice;

@RestController
@RequestMapping("/api/customer-policy")
public class CustomerPolicyController {
	@Autowired
	private customerpolicydetailsservice customerPolicyService;

	@Autowired
	private PolicyService policiesService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/")
	public ResponseEntity<?> getAllCustomerPolicies() {
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

			List<Customerpolicy> list = customerPolicyService.getAllCustomerPolicies();
			CustomerPolicyResponse myresponse = new CustomerPolicyResponse();
			myresponse.setSuccess(true);
			myresponse.setCustomerPolicies(list);
			return ResponseEntity.ok(myresponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@GetMapping("/mypolicies")
	public ResponseEntity<?> getMyPolicies() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("customer")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}
	
			List<Customerpolicy> list = customerPolicyService.getMyPolicies(user);
			list.forEach(item -> item.getUser().setPassword(null));
			CustomerPolicyResponse myresponse = new CustomerPolicyResponse();
			myresponse.setSuccess(true);
			myresponse.setCustomerPolicies(list);
			return ResponseEntity.ok(myresponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<?> applyPolicy(@PathVariable int id) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("customer")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}

			Policy policy = policiesService.findById(id);
			if (policy == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}

			Customerpolicy customerpolicy = new Customerpolicy();
			customerpolicy.setPolicy(policy);
			customerpolicy.setStatus("pending");
			customerpolicy.setUser(user);
			customerpolicy = this.customerPolicyService.save(customerpolicy);
			System.out.println("customerpolicy: "+customerpolicy);
			
			List<Customerpolicy> list = customerPolicyService.getMyPolicies(user);
			System.out.println("list: "+list);
			list.forEach(item -> item.getUser().setPassword(null));
			CustomerPolicyResponse myresponse = new CustomerPolicyResponse();
			myresponse.setSuccess(true);
			myresponse.setCustomerPolicies(list);
			return ResponseEntity.ok(myresponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PutMapping("/approve/{id}")
	public ResponseEntity<?> approvePolicy(@PathVariable("id") long policyId) {
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

			Customerpolicy policy = customerPolicyService.findById(policyId);
			if (policy == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}

			policy.setStatus("approved");
			policy = customerPolicyService.save(policy);

			List<Customerpolicy> list = customerPolicyService.getAllCustomerPolicies();
			CustomerPolicyResponse myresponse = new CustomerPolicyResponse();
			myresponse.setSuccess(true);
			myresponse.setCustomerPolicies(list);
			return ResponseEntity.ok(myresponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PutMapping("/reject/{id}")
	public ResponseEntity<?> rejectPolicy(@PathVariable("id") long policyId) {
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

			Customerpolicy policy = customerPolicyService.findById(policyId);
			if (policy == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Policy not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}

			policy.setStatus("rejected");
			customerPolicyService.save(policy);

			List<Customerpolicy> list = customerPolicyService.getAllCustomerPolicies();
			CustomerPolicyResponse myresponse = new CustomerPolicyResponse();
			myresponse.setSuccess(true);
			myresponse.setCustomerPolicies(list);
			return ResponseEntity.ok(myresponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

}
