package com.example.findproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isFieldsValid(String username, String firstName, String lastName, String mail, String phone){
        if(username.length() == 0 && mail.length() == 0){
            return false;
        }
        if(mail.length() != 0){
            return Pattern.matches(EMAIL_REGEX, mail);
        }
        if(phone.length() != 0){
            return Pattern.matches(PHONE_REGEX, phone);
        }
        return true;
    }

    public void clickSearch(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        String username =  txtUsername.getText().toString();

        EditText txtFirstName = findViewById(R.id.txtFirstName);
        String firstName =  txtFirstName.getText().toString();

        EditText txtLastName = findViewById(R.id.txtLastName);
        String lastName =  txtLastName.getText().toString();

        EditText txtMail = findViewById(R.id.txtMail);
        String mail =  txtMail.getText().toString();

        EditText txtPhone = findViewById(R.id.txtPhone);
        String phone =  txtPhone.getText().toString();

        boolean isValid = isFieldsValid(username,firstName,lastName,mail,phone);

        if(isValid == true){
            Toast.makeText(getApplicationContext(), username , Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, ResultActivity.class));


        }else{
            Toast.makeText(getApplicationContext(), "Wrong" , Toast.LENGTH_LONG).show();
        }
    }

    private void readStream(InputStream in) {
    }

}
/*URL url = new URL("http://www.android.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            } finally {
                urlConnection.disconnect();
            }*/