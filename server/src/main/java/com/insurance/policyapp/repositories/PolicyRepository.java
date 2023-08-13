package com.insurance.policyapp.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.policyapp.models.Policy;
import com.insurance.policyapp.models.Policycategory;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
	
    List<Policy> findByPolicyName(String policyName);

    List<Policy> findByPolicycategory(Policycategory policycategory);

    List<Policy> findByPolicydesc(String keyword);
}