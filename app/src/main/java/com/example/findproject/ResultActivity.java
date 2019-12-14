package com.example.findproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ResultActivity extends AppCompatActivity {
    ArrayList<Site> siteList = new ArrayList<Site>();
    //ArrayList<String> siteList = new ArrayList<String>();//Creating site list

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
            makeRequest(site,username);
        }
    }

    public void makeRequest(final Site site, final String username){
        OkHttpClient client = new OkHttpClient();
        final String url = site.getUrl()+username;
        final Button btn = site.getResultButton();
        Log.i("url",url);
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
        Site reddit = new Site("reddit","https://reddit.com/user/");

        siteList.add(twitter);
        siteList.add(github);
        siteList.add(facebook);
        siteList.add(reddit);
    }

    /*Creating buttons*/
    public void createButtons()
    {
        for (int i = 0; i < siteList.size(); i++) {
            Site site = siteList.get(i); //Getting site
            Button myButton = new Button(this); //Creating new button
            myButton.setText(site.getSiteName());  //Setting button text
            myButton.setId(i);  //Setting button id
            final int id_ = myButton.getId();
            site.setResultButton(myButton);

            LinearLayout layout = (LinearLayout) findViewById(R.id.resultLinearLayout);
            layout.addView(myButton);
        }
    }

}
