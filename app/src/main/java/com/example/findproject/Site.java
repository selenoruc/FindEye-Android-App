package com.example.findproject;

import android.widget.Button;

public class Site {
    private String siteName;
    private String url;
    private String searchQuery;
    private String usernameSearchUrl;
    private String nameSurnameSearchUrl;
    Button resultButton;

    /*Constructors*/
    public Site(String siteName, String url){
        this.siteName = siteName;
        this.url = url;
    }

    /*Getter & Setter methods*/
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

    public void setSearchQuery(String searchQuery){
        this.searchQuery = searchQuery;
    }
    public String getSearchQuery(){
        return this.searchQuery;
    }

    public void setUsernameSearchUrl(String usernameSearchUrl){
        this.usernameSearchUrl = usernameSearchUrl;
    }
    public String getUsernameSearchUrl(){
        return this.usernameSearchUrl;
    }

    public void setNameSurnameSearchUrl(String nameSurnameSearchUrl){
        this.nameSurnameSearchUrl = nameSurnameSearchUrl;
    }
    public String getNameSurnameSearchUrl(){
        return this.nameSurnameSearchUrl;
    }

}
