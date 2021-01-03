package com.example.androidui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidui.Profile.Profile;
import com.example.androidui.R;
import com.example.androidui.Register.Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private TextView tvRegister;
    private EditText edtPhoneNumber, edtPassword;
    private Button btnSubmit, btnLogin;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(submitLogin);


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
                (Login.this, Register.class));
    }




    private View.OnClickListener submitLogin= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String phone = edtPhoneNumber.getText().toString();
            String password = edtPassword.getText().toString();

            Login(phone, password);
        }
    };

    //Service Login - Call API
    public void Login(String phone, String password){

        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        //API link
        String ServerName = "https://whatfoods.herokuapp.com/signin";

        //Setting Post Method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerName,

                //Login Successful
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //Nhận trả về theo kiểu Json
                            JSONObject obj = new JSONObject(response);
                            System.out.println(obj);
                            System.out.println(obj.get("token"));

                            //Lưu Token vào SharePrefs
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("token", obj.get("token").toString());
                            editor.commit();
                            System.out.println("test prefs-------------------------------------------------------------------------");
                            System.out.println(sharedpreferences.getString("token",""));

                            //Show Toast
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            //Chuyển trang
                            startActivity(new Intent
                                    (Login.this, Profile.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                //Login Fail
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Show Toast
                        Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                })

                //Truyền dữ liệu theo params
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("phone", phone);
                        params.put("password", password);

                        return params;
                    }
                };

        //Tạo request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Thêm request vào Call API
        requestQueue.add(stringRequest);
    }
}