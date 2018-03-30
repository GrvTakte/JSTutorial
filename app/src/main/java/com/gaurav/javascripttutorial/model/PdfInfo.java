package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 19/09/17.
 */

public class PdfInfo {

    private int cover;
    private String bookUrl;

    public PdfInfo(){}

    public PdfInfo(int cover, String bookUrl) {
        this.cover = cover;
        this.bookUrl = bookUrl;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
