package com.example.groceryapp;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class MyStoreActivity extends GeneralPage {

    TextView storeNameTextView, addressTextView;
    Store current_user_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUserData(R.layout.activity_my_store);

//        if(current_user == null) return;

    }

    @Override
    public void initializeNavigation(int activityXML) {
        super.initializeNavigation(activityXML);

        storeNameTextView = findViewById(R.id.storeNameText);
        addressTextView = findViewById(R.id.addressText);

        // Get store of user
        ref.child(DBConstants.STORES_PATH).child(current_user.getOwned_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user's store data", task.getException());
                } else {
                    current_user_store = task.getResult().getValue(Store.class);
                    if(current_user_store != null) {
                        storeNameTextView.setText(current_user_store.getName());
                        addressTextView.setText(current_user_store.getAddress());
                    }
                }
            }
        });
    }
}