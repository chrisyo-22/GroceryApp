package com.example.groceryapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterModel implements RegisterContract.Model{

    private RegisterContract.RegisterListener register_Listener;

    public RegisterModel(RegisterContract.RegisterListener register_Listener){
        this.register_Listener = register_Listener;
    }

    @Override
    public void connectFirebaseRegister(String email, String password, String name) {
        FirebaseAuth f_auth = FirebaseAuth.getInstance();
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
                    register_Listener.RegisterSuccessful();
                    //Toast.makeText(Register.this,"User created", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                }
                else{
                    register_Listener.RegisterFailure(task.getException().getMessage());
                    //Toast.makeText(Register.this, "Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

