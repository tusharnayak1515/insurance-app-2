package com.insurance.policyapp.dto;

public class CreatePolicyRequest {
	private String name;

	public CreatePolicyRequest() {
		super();
	}

	public CreatePolicyRequest(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
