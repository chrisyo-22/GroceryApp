package com.example.groceryapp;

import android.os.Bundle;

public class CartActivity extends GeneralPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_cart);
    }
}