package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText l_username, l_password;
    Button l_login_btn;
    FirebaseAuth f_auth;
    TextView jump_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_username = findViewById(R.id.signin_username);
        l_password = findViewById(R.id.signin_password);
        l_login_btn = findViewById(R.id.signin_button);
        jump_register = findViewById(R.id.Swap_to_register);
        f_auth = FirebaseAuth.getInstance();

        //if user already login in
        if(f_auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        jump_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


    }
}