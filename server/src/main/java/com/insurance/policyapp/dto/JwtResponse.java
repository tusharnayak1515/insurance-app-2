package com.insurance.policyapp.dto;

import java.util.List;

import com.insurance.policyapp.models.User;

public class JwtResponse {
    private boolean success;
    private String token;
    private String error;
    private UserResponse user;
    private List<User> users;

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}
    
    public JwtResponse(boolean success, String token, String error, UserResponse user, List<User> users) {
        this.success = success;
        this.token = token;
        this.error = error;
        this.user = user;
        this.users = users;
    }

    public JwtResponse() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "JwtResponse [success=" + success + ", token=" + token + ", error=" + error + ", user=" + user
                + ", users=" + users + "]";
    }
    
}