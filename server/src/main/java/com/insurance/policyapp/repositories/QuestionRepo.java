package com.insurance.policyapp.repositories;

import com.insurance.policyapp.models.Question;
import com.insurance.policyapp.models.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<Question, Long>{
	List<Question> findByUser(User user);

}





