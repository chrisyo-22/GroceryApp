package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.Method;
import java.sql.Timestamp;

public class AccountActivity extends GeneralPage {
    Button logout_btn,reset_psw_btn;
    TextView email_tag,owned_store_tag, creation_date_tag;
    EditText name_tag;
    public static final String EXTRA_USER_NAME = "com.example.groceryapp.USER_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_account);
    }
    public void reset_psw_btn(){
        //String current_user_id = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email_tag.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AccountActivity.this, "Reset Password Request Sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initializeOther() {
        email_tag = findViewById(R.id.email_tag);
        owned_store_tag = findViewById(R.id.owner_store_tag);
        creation_date_tag = findViewById(R.id.creation_date_view);
        name_tag = findViewById(R.id.name_tag);
        reset_psw_btn = findViewById(R.id.change_pswd_btn);

        email_tag.setText(current_user.getEmail());
        name_tag.setText(current_user.getName());
        long timestamp = FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp();
        Timestamp ts = new Timestamp(timestamp);

        creation_date_tag.setText(DBConstants.dateFormat.format(ts));//ts.toString()


        ref.child(DBConstants.STORES_PATH).child(current_user.getOwned_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    Store owner_store = task.getResult().getValue(Store.class);
                    if(owner_store != null)
                        owned_store_tag.setText(owner_store.getName());
                    else
                        owned_store_tag.setText("N/a");

                }
                else{
                    Log.e("GroceryApp", "Error getting user data", task.getException());
                }

            }
        });
//
//        TextWatcher inputTextWatcher = new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                ref.child(DBConstants.USERS_PATH).child(current_user_id).child("name").setValue(name_tag.getText().toString());
//                goToAccount();
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after){
//            }
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        };

        name_tag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ( (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ref.child(DBConstants.USERS_PATH).child(current_user_id).child("name").setValue(name_tag.getText().toString());
                    Toast.makeText(AccountActivity.this, "Name Update successful!", Toast.LENGTH_SHORT).show();
                    goToAccount();
                    return true;
                }
                //Toast.makeText(AccountActivity.this, "Name NOT Updated", Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        reset_psw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_psw_btn();
            }
        });

        logout_btn = findViewById(R.id.sign_out_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}