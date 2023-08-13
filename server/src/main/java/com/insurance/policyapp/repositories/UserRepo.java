package com.insurance.policyapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.insurance.policyapp.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public User findByMobile(String mobile);
}