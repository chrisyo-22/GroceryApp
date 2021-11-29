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

public class Register extends AppCompatActivity implements RegisterContract.View{
    EditText r_name, r_password, r_email;
    Button r_register_btn;
    FirebaseAuth f_auth;

    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    void initView(){
        r_name = findViewById(R.id.signup_name);
        r_password = findViewById(R.id.signup_password);
        r_email = findViewById(R.id.signup_email);
        r_register_btn = findViewById(R.id.signup_button);

        f_auth = FirebaseAuth.getInstance();
        registerPresenter = new RegisterPresenter(this);

        //register button action:
        r_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = r_name.getText().toString().trim();
                String password = r_password.getText().toString().trim();
                String email = r_email.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    r_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    r_password.setError("Password is Required");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    r_name.setError("Name is Required");
                    return;
                }

                startRegister(email, password, name);
            }
        });

}
    private void startRegister(String email, String password, String name) {
        registerPresenter.onHandleRegister(email,password,name);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(Register.this,"User created", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ShopActivity.class));
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(Register.this,message, Toast.LENGTH_SHORT).show();
    }
}