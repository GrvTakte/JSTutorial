package com.gaurav.javascripttutorial.model;

/**
 * Created by gaurav on 20/09/17.
 */

public class ReferenceSiteInfo {

    private String websiteName;
    private String websiteUrl;

    public ReferenceSiteInfo(){

    }

    public ReferenceSiteInfo(String websiteName, String websiteUrl){
        this.websiteName = websiteName;
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
