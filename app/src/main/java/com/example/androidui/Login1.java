package com.example.androidui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.regex.Pattern;

public class Login1 extends AppCompatActivity {

    private TextView tvRegister;
    private EditText edtPhoneNumber, edtPassword;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);


        String inputPhone = edtPhoneNumber.getText().toString().trim();

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
                }else if(edtPhoneNumber.length() == 0){
                    edtPhoneNumber.setError("Required field");
                } else {
//                    edtPhoneNumber.setError("2");
                }
            }
        });

    }

    // pattem phone number
    private boolean isValidPhone(String phone) {
        return Pattern.compile("^\\+?\\(?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?")
                .matcher(phone).matches();
    }

    // change to register active
    public void tvRegister(View view) {
        startActivity(new Intent
                (Login1.this, Register1.class));
    }
}