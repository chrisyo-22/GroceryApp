package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements LoginContract.View,View.OnClickListener{
    EditText l_email, l_password;
    Button l_login_btn;
    FirebaseAuth f_auth;
    TextView jump_register;

    private LoginPresenter login_Presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }
    public void initView(){
        l_email = findViewById(R.id.signin_username);
        l_password = findViewById(R.id.signin_password);


        //jump_register.setOnClickListener(this);

        f_auth = FirebaseAuth.getInstance();
        login_Presenter = new LoginPresenter(this, new LoginModel());
        //if user already login in
        if (f_auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            finish();
        }
        l_login_btn = findViewById(R.id.signin_button);
//        l_login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkInputText();
//            }
//        });
        jump_register = findViewById(R.id.Swap_to_register);
        l_login_btn.setOnClickListener(this);
        jump_register.setOnClickListener(this);
//        jump_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jump_register();
//            }
//        });

    }

    //handles two buttons action:
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.signin_button:
                checkInputText();
                break;
            case R.id.Swap_to_register:
                jump_register();
                break;
        }
    }

    private void jump_register() {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }


    public void checkInputText() {
        String email = l_email.getText().toString().trim();
        String password = l_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            l_email.setError("Email is Required");
        }
        if (TextUtils.isEmpty(password)) {
            l_password.setError("Password is Required");
        }
        if(!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)){
            startLogin(email, password);
        }
    }

    private void startLogin(String email, String password) {
        login_Presenter.onHandleLogin(email,password);
    }

    @Override
    public void viewLoginSuccess() {
        Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ShopActivity.class));

    }

    @Override
    public void viewLoginFailed(String message) {
        //Log.i("demo","You are here");
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }
}