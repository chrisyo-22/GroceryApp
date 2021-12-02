package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ShopProductActivity extends GeneralPage {
    //testing
    private ArrayList<Product> product_list;
    private ArrayList<String> product_id_list;
    private RecyclerView recyclerView;
    private String store_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_shop_product);
        product_list = new ArrayList<Product>();
        product_id_list = new ArrayList<String>();
        recyclerView = findViewById(R.id.recycle_products);
        Intent intent = this.getIntent();
        //get the store information
        Store store = (Store) intent.getSerializableExtra("store");

        //store store_id:
        store_id = store.getId();

        setProductInfo(store);
        setAdapter();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(product_list,store_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setProductInfo(Store store) {

        product_list = store.products;
    }
}