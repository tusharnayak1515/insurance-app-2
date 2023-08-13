package com.insurance.policyapp.services;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insurance.policyapp.models.Policycategory;
import com.insurance.policyapp.repositories.PolicycategoryRepo;


@Service
public class PolicycategoryService {
	 
	@Autowired
	private PolicycategoryRepo policycategoryrepo;

	public List<Policycategory> getAllPolicyCategories() {
        List<Policycategory> list = new ArrayList<Policycategory>();
		this.policycategoryrepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
	public Policycategory findById(Long id) {	
		Optional<Policycategory> optionalPolicyCategory = this.policycategoryrepo.findById(id);
		return optionalPolicyCategory.isPresent() ? optionalPolicyCategory.get() : null;
	}

    public Policycategory createPolicycategory(Policycategory policycategory) {
        return policycategoryrepo.save(policycategory);
    }

    public Policycategory updatePolicycategory(Policycategory policycategory) {
    	System.out.println("name1: "+policycategory);
    	Policycategory existingCategory = findById(policycategory.getCategoryId());
         if(existingCategory != null) {
        	existingCategory.setName(policycategory.getName());
        	existingCategory = policycategoryrepo.save(existingCategory);
         }
        return existingCategory;
    }

    public void deletePolicycategory(Long CategoryId) {
        policycategoryrepo.deleteById(CategoryId);
    }
}




