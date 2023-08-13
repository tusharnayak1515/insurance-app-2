package com.insurance.policyapp.dto;

public class AskQuestionRequest {
	private String questionText;

	public AskQuestionRequest() {
		super();
	}

	public AskQuestionRequest(String questionText) {
		super();
		this.questionText = questionText;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
}
