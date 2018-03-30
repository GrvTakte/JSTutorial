package com.gaurav.javascripttutorial.model;

import android.support.annotation.NonNull;

/**
 * Created by gaurav on 13/11/17.
 */

public class LeaderBoard implements Comparable<LeaderBoard> {

    private String id;
    private String imageUrl;
    private String name;
    private int score;

    public LeaderBoard(){
    }

    public LeaderBoard(String id){
        this.id=id;
    }

    public LeaderBoard(String id, String name, int score, String imageUrl){
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.score = score;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(@NonNull LeaderBoard o) {
        return o.score-this.score;
    }
}
