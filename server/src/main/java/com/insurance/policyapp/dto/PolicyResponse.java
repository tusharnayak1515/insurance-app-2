package com.insurance.policyapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.insurance.policyapp.models.Policy;

public class PolicyResponse {
    private boolean success;
    private String error;
    private List<Policy> list = new ArrayList<Policy>();
    private Policy policy;

    public PolicyResponse() {

    }

    public PolicyResponse(boolean success, String error, List<Policy> list, Policy policy) {
        this.success = success;
        this.error = error;
        this.list = list;
        this.policy = policy;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Policy> getList() {
        return list;
    }

    public void setList(List<Policy> list) {
        this.list = list;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
    
}
