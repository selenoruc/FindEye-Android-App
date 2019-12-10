package com.example.findproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
    ArrayList<String> siteList = new ArrayList<String>();//Creating site list

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

        /* Sites declared */ /* Listeye eklenecek siteler burada ekleniyor */
        siteList.add("https://twitter.com/");
        siteList.add("https://github.com/");
        siteList.add("https://facebook.com/");
        siteList.add("https://reddit.com/user/");

        if(username.length() != 0)
        {
            checkUsernameOnSites(username);
        }

    }

    public void checkUsernameOnSites(String username)
    {
        for (int i=0;i<siteList.size();i++)
        {
            String site = siteList.get(i);
            makeRequest(site,username);
        }
    }

    public void makeRequest(String site, final String username){
        final TextView resultView = findViewById(R.id.resultView);
        OkHttpClient client = new OkHttpClient();
        final String url = site+username;
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
                            resultView.append(url+" "+responseCode+"\n");
                        }
                    });
                }else{
                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultView.append(url+" "+responseCode+"\n");
                        }
                    });
                }
            }
        });

    }


    /*Username ile bütün listedeki siteler üzerinde döngüyle bakıyor ve bunları isUsernameExist ile kontrol ediyor*/
    /*protected void checkUsernameOnSites(String username){
        TextView resultView = findViewById(R.id.resultView);

        for(int i=0; i<siteList.size(); i++) {
            String site = siteList.get(i);
            String[] seperatedSite = site.split("/");
            String siteName = seperatedSite[2];
            String url = site + username; //github.com/username
            //Log.i("user sites", url);
            makeRequest(url);
            //Log.i("ret code ",returnCode);
            //resultView.append(returnCode);
        }

    }*/
}
