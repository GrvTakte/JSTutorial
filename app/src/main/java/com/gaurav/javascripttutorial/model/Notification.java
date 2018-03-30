package com.gaurav.javascripttutorial.model;

/**
 * Created by Gaurav on 2/16/2018.
 */

public class Notification {

    private String image;
    private String text;
    private String url;

    public Notification(){
    }

    public Notification(String image, String text, String url){
        this.image = image;
        this.text = text;
        this.url = url;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
