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
    public static final String EXTRA_USER_NAME = "com.example.groceryapp.USER_NAME";

    Button shopButton, myStoreButton, cartButton, accountButton;
    DatabaseReference ref;
    String current_user_id;
    User current_user;


    public void initializePage(int activityXML) {
        setContentView(activityXML);
        shopButton = findViewById(R.id.shop_button);
        myStoreButton = findViewById(R.id.my_store_button);
        cartButton = findViewById(R.id.cart_button);
        accountButton = findViewById(R.id.account_button);

        accountButton.setText(getIntent().getStringExtra(EXTRA_USER_NAME));

        // Get database and user data
        ref = FirebaseDatabase.getInstance().getReference();
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child(DBConstants.USERS_PATH).child(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user data", task.getException());
                } else {
                    current_user = task.getResult().getValue(User.class);
                    if(current_user != null) {
                        initializeNavigation();
                    } else {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        Log.e("GroceryApp", "Error getting user data", task.getException());
                        //startActivity(new Intent(getApplicationContext(), Register.class));
                    }
                }
            }
        });

    }

    private void initializeNavigation() {

        accountButton.setText(current_user.getName());

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToShop();
            }
        });
        myStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyStore();
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCart();
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();
            }
        });

        initializeOther();
    }

    public void initializeOther() {};

    public void goToShop() {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra(EXTRA_USER_NAME, current_user.getName());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToMyStore() {
//        ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_OWNEDSTORE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(!task.isSuccessful()) {
//                    Log.e("GroceryApp", "Error getting user data", task.getException());
//                } else {
//                    String store_id = task.getResult().getValue().toString();
                    if(current_user.getOwned_store_id().equals(DBConstants.NULL))
                        goToCreateStore();
                    else
                        goToMyStorePage();
//                }
//            }
//        });
    }

    public void goToMyStorePage() {
        Intent intent = new Intent(this, MyStoreActivity.class);
        intent.putExtra(EXTRA_USER_NAME, current_user.getName());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToCreateStore() {
        Intent intent = new Intent(this, CreateStoreActivity.class);
        intent.putExtra(EXTRA_USER_NAME, current_user.getName());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra(EXTRA_USER_NAME, current_user.getName());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToAccount() {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra(EXTRA_USER_NAME, current_user.getName());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
