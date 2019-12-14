package com.example.findproject;

import android.widget.Button;

public class Site {
    private String siteName;
    private String url;
    Button resultButton;

    public Site(String siteName, String url){
        this.siteName = siteName;
        this.url = url;
    }

    public void setSiteName(String siteName){
        this.siteName = siteName;
    }
    public String getSiteName(){
        return this.siteName;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }


    public void setResultButton(Button resultButton){
        this.resultButton = resultButton;
    }
    public Button getResultButton(){
        return this.resultButton;
    }
}
