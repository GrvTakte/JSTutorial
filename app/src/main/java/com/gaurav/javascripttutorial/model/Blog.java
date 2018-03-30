package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 02/11/17.
 */

public class Blog {

    private String id;
    private String name;
    private String imageurl;
    private String blog;
    private String time;
    private String date;
    private int likeCounter;

    public Blog(){
    }

    public Blog(String name){
        this.name = name;
    }


    public Blog (String id, String name, String imageUrl, String blog, String time, String date, int likeCounter){
        this.id = id;
        this.name = name;
        this.imageurl = imageUrl;
        this.blog = blog;
        this.time = time;
        this.date = date;
       this.likeCounter = likeCounter;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }
}
