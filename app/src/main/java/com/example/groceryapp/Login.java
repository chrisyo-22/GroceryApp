package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
//            f_auth.getCurrentUser().get
            startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            finish();
        }
        jump_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        l_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = l_username.getText().toString().trim();
                String password = l_password.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    l_username.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    l_password.setError("Password is Required");
                    return;
                }

                f_auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}