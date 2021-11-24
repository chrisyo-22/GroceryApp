package com.example.groceryapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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
