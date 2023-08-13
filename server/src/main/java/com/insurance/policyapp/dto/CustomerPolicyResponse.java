package com.insurance.policyapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.insurance.policyapp.models.Customerpolicy;

public class CustomerPolicyResponse {
    private boolean success;
    private String error;
    private List<Customerpolicy> customerPolicies = new ArrayList<Customerpolicy>();

    public CustomerPolicyResponse() {
        
    }

    public CustomerPolicyResponse(boolean success, String error, List<Customerpolicy> customerPolicies) {
        this.success = success;
        this.error = error;
        this.customerPolicies = customerPolicies;
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

    public List<Customerpolicy> getCustomerPolicies() {
        return customerPolicies;
    }

    public void setCustomerPolicies(List<Customerpolicy> customerPolicies) {
        this.customerPolicies = customerPolicies;
    }

    @Override
    public String toString() {
        return "CustomerPolicyResponse [success=" + success + ", error=" + error + ", customerPolicies="
                + customerPolicies + "]";
    }

}
