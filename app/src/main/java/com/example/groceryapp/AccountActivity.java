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
import com.google.firebase.database.DataSnapshot;

public class AccountActivity extends GeneralPage {
    Button logout_btn;
    TextView email_tag,owned_store_tag;
    EditText name_tag;
    public static final String EXTRA_USER_NAME = "com.example.groceryapp.USER_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_account);
        email_tag = findViewById(R.id.email_tag);
        owned_store_tag = findViewById(R.id.owner_store_tag);
        name_tag = findViewById(R.id.name_tag);




        ref.child(DBConstants.USERS_PATH).child(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user data", task.getException());
                } else {
                    User current_user = task.getResult().getValue(User.class);
                    email_tag.setText(current_user.getEmail());
                    name_tag.setText(current_user.getName());
                    ref.child(DBConstants.STORES_PATH).child(current_user.getOwned_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()) {
                                Store owner_store = task.getResult().getValue(Store.class);
                                owned_store_tag.setText(owner_store.getName());
                            }
                            else{
                                Log.e("GroceryApp", "Error getting user data", task.getException());
                            }

                        }
                    });
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

    }

    @Override
    public void initializeOther() {
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