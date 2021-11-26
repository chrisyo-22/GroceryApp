package com.example.groceryapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeneralPage extends AppCompatActivity {
    Button shopButton, myStoreButton, cartButton, accountButton;

    public void initNavigataion(int activityXML) {
        setContentView(activityXML);
        shopButton = findViewById(R.id.shop_button);
        accountButton = findViewById(R.id.account_button);
        myStoreButton = findViewById(R.id.my_store_button);
        cartButton = findViewById(R.id.cart_button);

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToShop(v);
                overridePendingTransition(0, 0);
            }
        });
        myStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyStore(v);
                overridePendingTransition(0, 0);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCart(v);
                overridePendingTransition(0, 0);
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount(v);
                overridePendingTransition(0, 0);
            }
        });

        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Users");
        dref.child(current_user_id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user data", task.getException());
                } else {
                    //
                    accountButton.setText(task.getResult().getValue().toString());
                    //Log.i("demo", String.valueOf(task.getResult()));
                }
            }
        });

    }

    public void goToShop(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void goToMyStore(View view) {
        Intent intent = new Intent(this, MyStoreActivity.class);
        startActivity(intent);
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void goToAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
