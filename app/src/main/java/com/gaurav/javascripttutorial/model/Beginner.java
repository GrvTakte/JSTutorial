package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 18/09/17.
 */

public class Beginner {

    private String name;
    private String initial;
    private int color;

    public Beginner(){}

    public Beginner(String name, String initial, int color){
        this.name = name;
        this.initial = initial;
        this.color=color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
