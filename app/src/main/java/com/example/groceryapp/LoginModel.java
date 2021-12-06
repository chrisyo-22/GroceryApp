package com.example.groceryapp;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginModel implements LoginContract.Model{


    public LoginModel(){

    }


    @Override
    public void connectFirebaseLogin(String email, String password, LoginPresenter presenter) {
        FirebaseAuth f_auth = FirebaseAuth.getInstance();
        f_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    presenter.onSuccess();
                    //Toast.makeText(activity,"Login successful", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                }
                else{
                    //Toast.makeText(Login.this, "Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    presenter.onFailure(task.getException().getMessage());
                }
            }
        });
    }

}
