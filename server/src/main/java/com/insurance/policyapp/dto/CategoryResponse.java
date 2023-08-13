package com.insurance.policyapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.insurance.policyapp.models.Policycategory;

public class CategoryResponse {
    private boolean success;
    private String error;
    private List<Policycategory> list = new ArrayList<Policycategory>();
    private Policycategory category;
    
    public CategoryResponse() {

    }

    public CategoryResponse(boolean success, String error, List<Policycategory> list, Policycategory category) {
        this.success = success;
        this.error = error;
        this.list = list;
        this.category = category;
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

    public List<Policycategory> getList() {
        return list;
    }

    public void setList(List<Policycategory> list) {
        this.list = list;
    }

    public Policycategory getCategory() {
        return category;
    }

    public void setCategory(Policycategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryResponse [success=" + success + ", error=" + error + ", list=" + list + ", category=" + category
                + "]";
    }
    
}
