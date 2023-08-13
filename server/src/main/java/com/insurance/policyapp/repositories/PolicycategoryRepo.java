package com.insurance.policyapp.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.insurance.policyapp.models.Policycategory;

	
	@Repository
	public interface  PolicycategoryRepo extends CrudRepository<Policycategory, Long> {

		public Policycategory findById(long id);
	

	}
