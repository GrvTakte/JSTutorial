package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 25/09/17.
 */

public class InterviewQA {

    private String question;
    private String answer;

    public InterviewQA(){}

    public InterviewQA(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
