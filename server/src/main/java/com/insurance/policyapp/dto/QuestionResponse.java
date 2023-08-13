package com.insurance.policyapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.insurance.policyapp.models.Question;

public class QuestionResponse {
    private boolean success;
    private String error;
    private List<Question> list = new ArrayList<Question>();
    private Question question;
    
    public QuestionResponse() {

    }

    public QuestionResponse(boolean success, String error, List<Question> list, Question question) {
        this.success = success;
        this.error = error;
        this.list = list;
        this.question = question;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionResponse [success=" + success + ", error=" + error + ", list=" + list + ", question=" + question
                + "]";
    }
    
}
