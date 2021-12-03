package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CartOrderDetailsActivity extends GeneralPage {
    //testing
    private ArrayList<HashMap<String,Integer>> product_list;
    private HashMap<String,Integer> product_id_list;
    private RecyclerView recyclerView;
    private String order_id;
    TextView display_total;
    //private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_cart_order_details);
        product_list = new ArrayList<HashMap<String,Integer>>();
        product_id_list = new HashMap<String,Integer>();
        recyclerView = findViewById(R.id.Order_product_detail_view);
        display_total  = findViewById(R.id.detail_Total);
        Intent intent = this.getIntent();
        //get the store information
        Order order =  (Order) intent.getSerializableExtra("order");

        //store store_id:
        order_id = order.getId();

        setProductInfo(order);
        setAdapter();
    }

    private void setAdapter() {
        OrderProductDetailsAdapter adapter = new OrderProductDetailsAdapter(product_list,order_id,display_total);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setProductInfo(Order order) {
        product_id_list = order.getItems_ids();

        for(HashMap.Entry<String,Integer> entry:product_id_list.entrySet()){
            HashMap<String,Integer> new_hashmap_product = new HashMap<String,Integer>();
            new_hashmap_product.put(entry.getKey(),entry.getValue());
            product_list.add(new_hashmap_product);
            //Log.i("demo","Stuck here"+product_list);
        }

    }
}