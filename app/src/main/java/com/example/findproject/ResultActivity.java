package com.example.findproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ResultActivity extends AppCompatActivity {
    ArrayList<Site> siteList = new ArrayList<Site>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        /* User Values */ /*Ilk activite den gelen veriler*/
        String input = getIntent().getStringExtra("INPUT_VALUE");
        String inputType = getIntent().getStringExtra("INPUT_TYPE");

        Log.i("input",input);
        Log.i("type",inputType);

        setSites(); //Generating sites
        //createButtons(); //Creating buttons

        switch (inputType)
        {
            case "Username":
                checkUsernameOnSites(input);
                break;
            case "Name&Surname":
                checkNameSurnameOnsites(input);
                break;
        }

    }

    public void checkUsernameOnSites(String username)
    {
        for (int i=0;i<siteList.size();i++)
        {
            Site site = siteList.get(i);

            String searchUsernameUrl = site.getUsernameSearchUrl();
            searchUsernameUrl = searchUsernameUrl.replace("_USERNAME_",username);
            site.setUsernameSearchUrl(searchUsernameUrl);

            site.setSearchQuery(searchUsernameUrl);

            makeRequest(site);
        }
    }

    protected void checkNameSurnameOnsites(String nameAndSurname) {
        String replaceableItems[] = {"",".","-","_"};
        for(int k=0;k<replaceableItems.length;k++)
        {
            String nameSurname = nameAndSurname.replace(" ",replaceableItems[k]);
            Log.i("nameSurname",nameSurname);
            for (int i=0;i<siteList.size();i++)
            {
                Site site = siteList.get(i);

                String nameSurnameSearchUrl = site.getNameSurnameSearchUrl();
                nameSurnameSearchUrl = nameSurnameSearchUrl.replace("_NAMESURNAME_",nameSurname);

                site.setSearchQuery(nameSurnameSearchUrl);

                makeRequest(site);
            }
        }
    }

    public void makeRequest(final Site site){
        OkHttpClient client = new OkHttpClient();

        final String url = site.getSearchQuery();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseCode = String.valueOf(response.code());
                if(response.isSuccessful()){
                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createButton(site.getSiteName(),url); //If exist creating button
                            Log.i("SuccessUrl",url);
                        }
                    });
                }else{
                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // do nothing
                        }
                    });
                }
            }
        });

    }

    /* Adding list of sites to search */
    public void setSites(){
        /* Sites declared */
        Site twitter    = new Site("twitter","https://twitter.com/");
        Site instagram  = new Site("instagram","https://www.instagram.com/");
        Site github     = new Site("github","https://github.com/");
        Site facebook   = new Site("facebook","https://facebook.com/");
        Site reddit     = new Site("reddit","https://reddit.com/");
        Site tumblr     = new Site("tumblr","https://www.tumblr.com/");
        Site linkedin   = new Site("linkedin","https://www.linkedin.com/");
        Site spotify    = new Site("spotify","https://www.spotify.com/");
        Site pinterest  = new Site("pinterest","https://tr.pinterest.com/");
        Site quora      = new Site("quora","https://www.quora.com/profile/");
        Site domainCom  = new Site("domainCom","https://www.google.com/");
        Site domainNet  = new Site("domainNet","https://www.google.com/");
        Site domainOrg  = new Site("domainOrg","https://www.google.com/");

        /* Sites added to search by username */
        twitter.setUsernameSearchUrl("https://twitter.com/_USERNAME_");
        instagram.setUsernameSearchUrl("https://www.instagram.com/_USERNAME_");
        github.setUsernameSearchUrl("https://github.com/_USERNAME_");
        facebook.setUsernameSearchUrl("https://facebook.com/_USERNAME_");
        reddit.setUsernameSearchUrl("https://reddit.com/user/_USERNAME_");
        tumblr.setUsernameSearchUrl("https://_USERNAME_.tumblr.com/");
        linkedin.setUsernameSearchUrl("https://tr.linkedin.com/in/_USERNAME_");
        spotify.setUsernameSearchUrl("https://open.spotify.com/user/_USERNAME_");
        pinterest.setUsernameSearchUrl("https://tr.pinterest.com/_USERNAME_");
        quora.setUsernameSearchUrl("https://www.quora.com/profile/_USERNAME_");
        domainCom.setUsernameSearchUrl("https://_USERNAME_.com");
        domainNet.setUsernameSearchUrl("https://_USERNAME_.net");
        domainOrg.setUsernameSearchUrl("https://_USERNAME_.org");

        /* Sites added to search by name and surname */
        twitter.setNameSurnameSearchUrl("https://twitter.com/_NAMESURNAME_");
        instagram.setNameSurnameSearchUrl("https://www.instagram.com/_NAMESURNAME_");
        github.setNameSurnameSearchUrl("https://github.com/_NAMESURNAME_");
        facebook.setNameSurnameSearchUrl("https://facebook.com/_NAMESURNAME_");
        reddit.setNameSurnameSearchUrl("https://reddit.com/user/_NAMESURNAME_");
        tumblr.setNameSurnameSearchUrl("https://_NAMESURNAME_.tumblr.com/");
        linkedin.setNameSurnameSearchUrl("https://tr.linkedin.com/in/_NAMESURNAME_");
        spotify.setNameSurnameSearchUrl("https://open.spotify.com/user/_NAMESURNAME_");
        pinterest.setNameSurnameSearchUrl("https://tr.pinterest.com/_NAMESURNAME_");
        quora.setNameSurnameSearchUrl("https://www.quora.com/profile/_NAMESURRNAME_");
        domainCom.setNameSurnameSearchUrl("https://_NAMESURNAME_.com");
        domainNet.setNameSurnameSearchUrl("https://_NAMESURNAME_.net");
        domainOrg.setNameSurnameSearchUrl("https://_NAMESURNAME_.org");

        /* Sites adding on the list */
        siteList.add(twitter);
        siteList.add(instagram);
        siteList.add(github);
        siteList.add(facebook);
        siteList.add(reddit);
        siteList.add(tumblr);
        siteList.add(linkedin);
        siteList.add(pinterest);
        siteList.add(quora);
        siteList.add(domainCom);
        siteList.add(domainNet);
        siteList.add(domainOrg);
    }

    public void createButton(String siteName, final String url)
    {
        Button myButton = new Button(this); //Creating new button
        myButton.setText(siteName);  //Setting button text
        myButton.setTextColor(Color.GREEN);

        LinearLayout layout = (LinearLayout) findViewById(R.id.resultLinearLayout);
        layout.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                Intent goUrlIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(goUrlIntent);
            }
        });
    }

}
