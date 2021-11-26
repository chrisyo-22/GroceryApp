package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText r_name, r_password, r_email;
    Button r_register_btn;
    FirebaseAuth f_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_name = findViewById(R.id.signup_name);
        r_password = findViewById(R.id.signup_password);
        r_email = findViewById(R.id.signup_email);
        r_register_btn = findViewById(R.id.signup_button);

        f_auth = FirebaseAuth.getInstance();

        r_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = r_name.getText().toString().trim();
                String password = r_password.getText().toString().trim();
                String email = r_email.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    r_email.setError("Email is Required");
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


                f_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //reading database and create a new user
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            //get current userid:
                            FirebaseUser f_user = FirebaseAuth.getInstance().getCurrentUser();
                            String UID = f_user.getUid();

                            User new_user = new User(email,name);
                            ref.child(DBConstants.USERS_PATH).child(UID).setValue(new_user);
                            Toast.makeText(Register.this,"User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ShopActivity.class));
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