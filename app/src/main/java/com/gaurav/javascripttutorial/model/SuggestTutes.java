package com.gaurav.javascripttutorial.model;

/**
 * Created by Gaurav on 2/19/2018.
 */

public class SuggestTutes {

    private String tutesInit;
    private String topic;

    public SuggestTutes(){}

    public SuggestTutes(String tutesInit, String topic){
        this.tutesInit = tutesInit;
        this.topic = topic;
    }

    public SuggestTutes(String topic){
        this.topic = topic;
    }

    public void setTutesInit(String tutesInit){
        this.tutesInit = tutesInit;
    }

    public String getTutesInit(){
        return this.tutesInit;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public String getTopic(){
        return this.topic = topic;
    }

    @Override
    public String toString() {
        return this.tutesInit+this.topic;
    }
}