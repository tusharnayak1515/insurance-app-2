package com.insurance.policyapp.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insurance.policyapp.models.Policy;
import com.insurance.policyapp.models.Policycategory;
import com.insurance.policyapp.repositories.PolicyRepository;
import jakarta.transaction.Transactional;

@Service
public class PolicyService {

	@Autowired
	PolicyRepository policiesRepository;

	public List<Policy> findByCategoryId(Policycategory category) {
		List<Policy> list = policiesRepository.findByPolicycategory(category);
		return list;
	}

	public Policy findById(int id) {
		Optional<Policy> optionalPolicy = this.policiesRepository.findById(id);
		return optionalPolicy.isPresent() ? optionalPolicy.get() : null;
	}

	public List<Policy> fetchPolicies() {
		List<Policy> list = policiesRepository.findAll();
		return list;
	}

	@Transactional
	public Policy savePolicy(Policy policies) {

		return policiesRepository.save(policies);

	}

	@Transactional
	public Policy updatePolicy(Policy policies) {
		Policy policy = findById(policies.getPolicyId());
        if(policy != null) {
        	policy.setPolicyName(policies.getPolicyName());
        	policy.setPolicydesc(policies.getPolicydesc());
        	policy.setPolicycategory(policies.getPolicycategory());
        	policy = this.policiesRepository.save(policy);
        }
        return policy;
	}

	@Transactional
	public void deletePolicy(int policyId) {
		policiesRepository.deleteById(policyId);
	}

	@Transactional
	public Policy getPolicy(int policyId) {

		Optional<Policy> optional = policiesRepository.findById(policyId);
		return optional.isPresent() ? optional.get() : null;
	}
}
