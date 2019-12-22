package com.example.findproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSpinner(); //Creating and Populating Spinner
    }

    /*Check whether username valid or not*/
    public boolean isUsernameValid(String username){
        if(username.length() == 0){
            return false;
        }else{
            return true;
        }
    }

    /*Check whether name and surname valid or not*/
    public boolean isNameSurnameValid(String nameSurname){
        if(nameSurname.length() == 0){
            return false;
        }else{
            return true;
        }
    }

    /*Check whether mail valid or not*/
    public boolean isMailValid(String mail){
        if(mail.length() != 0){
            return Pattern.matches(EMAIL_REGEX, mail);
        }else {
            return false;
        }
    }

    /*Check whether phone valid or not*/
    public boolean isPhoneValid(String phone){
        if(phone.length() != 0){
            return Pattern.matches(PHONE_REGEX, phone);
        }else{
            return false;
        }
    }

    public boolean isFieldsValid(String input,String selectedItem)
    {
        if(selectedItem == "Username"){
            return isUsernameValid(input);
        }else if(selectedItem == "Name&Surname"){
            return isNameSurnameValid(input);
        }else if(selectedItem == "Mail"){
            return isMailValid(input);
        }else if(selectedItem == "Phone"){
            return isPhoneValid(input);
        }else{
            return false;
        }
    }

    /*Change input type of input(EditText) field*/
    public void changeInputType(String inputType){
        final EditText input = findViewById(R.id.txtInput);
        if(inputType == "Username"){
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            input.setHint("Username");
        }else if(inputType == "Name&Surname"){
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            input.setHint("Name Surname");
        }else if(inputType == "Mail"){
            input.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            input.setHint("Mail");
        }else if(inputType == "Phone"){
            input.setInputType(InputType.TYPE_CLASS_PHONE);
            input.setHint("Phone (5XX XXX XX XX)");
        }
    }

    /*Use for populate spinner*/
    public void createSpinner(){
        final String[] spinnerParameters = { "Username", "Name&Surname", "Mail", "Phone"};
        final Spinner selectionSpinner = findViewById(R.id.selectionSpinner);
        final EditText input = findViewById(R.id.txtInput);

        /*Setting adapter for spinner*/
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinnerParameters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectionSpinner.setAdapter(adapter);

        /*Spinner OnItemSelectListener*/
        selectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String inputType = selectionSpinner.getSelectedItem().toString();
                changeInputType(inputType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }

        });
    }

    public void clickSearch(View view){
        final EditText txtInput = findViewById(R.id.txtInput);
        final Spinner selectionSpinner = findViewById(R.id.selectionSpinner);

        String input = txtInput.getText().toString();
        String selectedItem = selectionSpinner.getSelectedItem().toString();

        boolean isValid = isFieldsValid(input,selectedItem);

        if(isValid == true){
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("INPUT_VALUE", input);
            intent.putExtra("INPUT_TYPE", selectedItem);
            startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(), "Wrong !" , Toast.LENGTH_LONG).show();
        }
    }

}