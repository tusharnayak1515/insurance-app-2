package com.insurance.policyapp.repositories;
import com.insurance.policyapp.models.Customerpolicy;
import com.insurance.policyapp.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface customerpolicyrepo extends JpaRepository<Customerpolicy, Long> {
    List<Customerpolicy> findByUser(User user);
}