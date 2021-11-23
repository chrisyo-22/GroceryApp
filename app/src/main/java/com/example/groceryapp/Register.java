package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText r_name, r_username, r_password, r_email;
    Button r_register_btn;
    FirebaseAuth f_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_name = findViewById(R.id.signup_name);
        r_username = findViewById(R.id.signup_username);
        r_password = findViewById(R.id.signup_password);
        r_email = findViewById(R.id.signup_email);
        r_register_btn = findViewById(R.id.signup_button);

        f_auth = FirebaseAuth.getInstance();

        r_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = r_name.getText().toString().trim();
                String username = r_username.getText().toString().trim();
                String password = r_password.getText().toString().trim();
                String email = r_email.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    r_username.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    r_password.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    r_name.setError("Name is Required");
                    return;
                }


                if(TextUtils.isEmpty(email)){
                    r_email.setError("Email is Required");
                    return;
                }

                f_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
}