package com.example.groceryapp;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginModel implements LoginContract.Model{
    ArrayList<Store> all_stores;
    String user_Name;
    String user_Email;
    String user_store_id;
    Store user_store;

    private LoginContract.LoginListener login_Listener;

    public LoginModel(LoginContract.LoginListener login_Listener){
        this.login_Listener = login_Listener;
    }


    @Override
    public void connectFirebaseLogin(String email, String password) {
        FirebaseAuth f_auth = FirebaseAuth.getInstance();
        f_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    login_Listener.onSuccess();
                    //Toast.makeText(activity,"Login successful", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                }
                else{
                    //Toast.makeText(Login.this, "Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    login_Listener.onFailure(task.getException().getMessage());
                }
            }
        });
    }

}
