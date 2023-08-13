package com.insurance.policyapp.dto;

import java.util.Date;

public class UserResponse {
	private long userId;
	private String username;
	private String email;
	private String mobile;
	private String address;
	private String role;
	private Date createdAt;
	private Date updatedAt;
	
	public UserResponse() {
		
	}

	public UserResponse(long userId, String username, String email, String mobile, String address, String role,
			Date createdAt, Date updatedAt) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "UserResponse [userId=" + userId + ", username=" + username + ", email=" + email + ", mobile=" + mobile
				+ ", address=" + address + ", role=" + role + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
	
}