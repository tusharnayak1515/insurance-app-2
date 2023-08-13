package com.insurance.policyapp.services;
import com.insurance.policyapp.models.Customerpolicy;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.repositories.customerpolicyrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class customerpolicydetailsservice {
    @Autowired
    private customerpolicyrepo customerPolicyRepository;

    public List<Customerpolicy> getAllCustomerPolicies() {
        return customerPolicyRepository.findAll();
    }
    
    public List<Customerpolicy> getMyPolicies(User user) {
    	System.out.println("list: "+customerPolicyRepository.findByUser(user));
    	return customerPolicyRepository.findByUser(user);
    }
    
    public Customerpolicy save(Customerpolicy policy) {
        return customerPolicyRepository.save(policy);
    }
    public Customerpolicy findById(long id) {
    	Optional<Customerpolicy> optionalpolicy = this.customerPolicyRepository.findById(id);
		return optionalpolicy.isPresent() ? optionalpolicy.get() : null;
    }
    
    
}