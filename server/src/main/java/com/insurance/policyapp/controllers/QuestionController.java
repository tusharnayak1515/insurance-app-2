package com.insurance.policyapp.controllers;

import com.insurance.policyapp.dto.AnswerQuestionRequest;
import com.insurance.policyapp.dto.AskQuestionRequest;
import com.insurance.policyapp.dto.JwtResponse;
import com.insurance.policyapp.dto.QuestionResponse;
import com.insurance.policyapp.models.Question;
import com.insurance.policyapp.models.User;
import com.insurance.policyapp.services.CustomUserDetailsService;
import com.insurance.policyapp.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/")
	public ResponseEntity<?> viewAllQuestions() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("admin")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}

			List<Question> questions = questionService.getAllQuestions();
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			myResponse.setList(questions);
			return ResponseEntity.status(HttpStatus.OK).body(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@GetMapping("/myquestions")
	public ResponseEntity<?> viewMyQuestions() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("customer")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}

			List<Question> questions = questionService.getMyQuestions(user);
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			myResponse.setList(questions);
			return ResponseEntity.status(HttpStatus.OK).body(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
			myResponse.setSuccess(false);
			myResponse.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewQuestion(@PathVariable Long id) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("admin")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}
	
			Question question = questionService.getQuestionById(id);
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			myResponse.setQuestion(question);
			return ResponseEntity.status(HttpStatus.OK).body(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> askQuestion(@RequestBody AskQuestionRequest question) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("customer")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}
	
			if (question.getQuestionText().replaceAll("\\s+", "").length() == 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Question name cannot be empty");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
			}
	
			Question newQuestion = new Question();
			newQuestion.setQuestionText(question.getQuestionText());
			newQuestion.setUser(user);
	
			questionService.createQuestion(newQuestion);
			List<Question> questions = questionService.getMyQuestions(user);
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			myResponse.setList(questions);
			return ResponseEntity.status(HttpStatus.OK).body(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> answerQuestion(@PathVariable Long id, @RequestBody AnswerQuestionRequest request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			User user = this.customUserDetailsService.findOne(email);
			if (!user.getRole().equalsIgnoreCase("admin")) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Not allowed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
			}
	
			Question question = questionService.getQuestionById(id);
			if (question == null) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Question not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
			}
			
			if(request.getAnswerText() == null || request.getAnswerText() != null && request.getAnswerText().replaceAll("\\s+", "").length() == 0) {
				JwtResponse myResponse = new JwtResponse();
				myResponse.setSuccess(false);
				myResponse.setError("Answer cannot be blank");
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(myResponse);
			}
	
			question.setAnswerText(request.getAnswerText());
			questionService.updateQuestion(question);
	
			List<Question> questions = questionService.getAllQuestions();
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			myResponse.setList(questions);
			return ResponseEntity.status(HttpStatus.OK).body(myResponse);
		} catch (Exception e) {
			JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
		}
	}

}
