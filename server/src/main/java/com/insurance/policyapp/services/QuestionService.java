
package com.insurance.policyapp.services;

import com.insurance.policyapp.models.Question;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.repositories.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepository;

    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<Question>();
        this.questionRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public List<Question> getMyQuestions(User user) {
        return questionRepository.findByUser(user);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> optionalQuestion = this.questionRepository.findById(id);
		return optionalQuestion.isPresent() ? optionalQuestion.get() : null;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question updatedQuestion) {
        Optional<Question> existingQuestionOptional = questionRepository.findById(updatedQuestion.getQuestionId());

        if (existingQuestionOptional.isPresent()) {
            return questionRepository.save(updatedQuestion);
        } else {
            throw new IllegalArgumentException("Question with ID " + updatedQuestion.getQuestionId() + " not found.");
        }
    }

    public void deleteQuestion(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if (questionOptional.isPresent()) {
            questionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Question with ID " + id + " not found.");
        }
    }
}