package com.example.findproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
        String username = getIntent().getStringExtra("USERNAME");
        String firstName = getIntent().getStringExtra("FIRSTNAME");
        String lastName = getIntent().getStringExtra("LASTNAME");
        String mail = getIntent().getStringExtra("MAIL");
        String phone = getIntent().getStringExtra("PHONE");

        setSites(); //Generating sites
        createButtons(); //Creating buttons

        if(username.length() != 0)
        {
            checkUsernameOnSites(username);
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

            makeRequest(site);
        }
    }

    public void makeRequest(final Site site){
        OkHttpClient client = new OkHttpClient();

        final String url = site.getUsernameSearchUrl();
        Log.i("usernamesearchurl",url);

        final Button btn = site.getResultButton();

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
                            btn.setTextColor(Color.GREEN);
                        }
                    });
                }else{
                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn.setTextColor(Color.RED);
                            btn.setEnabled(false);
                        }
                    });
                }
            }
        });

    }

    /* Listeye eklenecek siteler burada ekleniyor */
    public void setSites(){
        /* Sites declared */
        Site twitter = new Site("twitter","https://twitter.com/");
        Site github = new Site("github","https://github.com/");
        Site facebook = new Site("facebook","https://facebook.com/");
        Site reddit = new Site("reddit","https://reddit.com/");
        Site tumblr = new Site("tumblr","https://www.tumblr.com/");
        Site linkedin = new Site("linkedin","https://www.linkedin.com/");
        Site spotify = new Site("spotify","https://www.spotify.com/");

        twitter.setUsernameSearchUrl("https://twitter.com/_USERNAME_");
        github.setUsernameSearchUrl("https://github.com/_USERNAME_");
        facebook.setUsernameSearchUrl("https://facebook.com/_USERNAME_");
        reddit.setUsernameSearchUrl("https://reddit.com/user/_USERNAME_");
        tumblr.setUsernameSearchUrl("https://_USERNAME_.tumblr.com/");
        linkedin.setUsernameSearchUrl("https://tr.linkedin.com/in/_USERNAME_");
        spotify.setUsernameSearchUrl("https://open.spotify.com/user/_USERNAME_");

        siteList.add(twitter);
        siteList.add(github);
        siteList.add(facebook);
        siteList.add(reddit);
        siteList.add(tumblr);
        siteList.add(linkedin);
    }

    /*Creating buttons*/
    public void createButtons()
    {
        for (int i = 0; i < siteList.size(); i++) {
            final Site site = siteList.get(i); //Getting site
            Button myButton = new Button(this); //Creating new button
            myButton.setText(site.getSiteName());  //Setting button text
            myButton.setId(i);  //Setting button id
            final int id_ = myButton.getId();
            site.setResultButton(myButton);

            LinearLayout layout = (LinearLayout) findViewById(R.id.resultLinearLayout);
            layout.addView(myButton);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Uri uri = Uri.parse(site.getSearchQuery());
                    Intent goUrlIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goUrlIntent);
                }
            });
        }
    }

}
