package com.example.androidui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;


public class Register1 extends AppCompatActivity {


    private EditText edtPhoneNumber, edtPassword, edtEmail, edtName;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        //convert editText to string
//        String inputPhone = edtPhoneNumber.getText().toString().trim();
//        String inputEmail = edtEmail.getText().toString().trim();


        //catch text changed event
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidName(edtName.getText().toString().trim())) {
                    edtName.setError("Invalid name");
                }else if(edtName.getText().toString().isEmpty()){
                    edtName.setError("Required field");
                } else {
//                    edtPhoneNumber.setError("2");
                }
            }
        });
        //catch text changed event
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidPhone(edtPhoneNumber.getText().toString().trim())) {
                    edtPhoneNumber.setError("Invalid phone number");
                }else if(edtPhoneNumber.getText().toString().isEmpty()){
                    edtPhoneNumber.setError("Required field");
                }
                else {
//                    edtPhoneNumber.setError("2");
                }
            }
        });

        //catch text changed event
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmailId(edtEmail.getText().toString().trim())) {
                    // email invalid
                    edtEmail.setError("Invalid email");
                }else if(edtEmail.getText().toString().isEmpty()){
                    edtEmail.setError("Required field");
                }
            }
        });

        //catch text changed event
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidPassword(edtPassword.getText().toString().trim())) {
                    // email invalid
                    edtPassword.setError("Password too short");
                }else if(edtPassword.getText().toString().isEmpty()){
                    edtPassword.setError("Required field");
                }
            }
        });

    }

    // pattem name
    private boolean isValidName(String name) {

        return Pattern.compile("^[a-zA-Z\\s]{2,50}")
                .matcher(name).matches();
    }

    // pattem email
    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
                .matcher(email).matches();

    }

    // pattem phone number
    private boolean isValidPhone(String phone) {

        return Pattern.compile("^\\+?\\(?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?")
                .matcher(phone).matches();
    }

    // pattem password
    private boolean isValidPassword(String password) {
        return password.length() >=6 ;
    }


    // back arrow event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}