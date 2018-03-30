package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 06/12/17.
 */

public class Menu {

    private int drawables;
    private String titles;

    public Menu(){}

    public Menu(int drawables, String titles){
        this.drawables=drawables;
        this.titles=titles;
    }

    public int getDrawables() {
        return drawables;
    }

    public void setDrawables(int drawables) {
        this.drawables = drawables;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }
}
