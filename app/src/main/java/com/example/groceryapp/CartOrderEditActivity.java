package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class CartOrderEditActivity extends GeneralPage {
    //testing
    private ArrayList<HashMap<String,Integer>> product_list;
    private HashMap<String,Integer> product_id_list;
    private RecyclerView recyclerView;
    private String order_id;
    TextView display_total;
    Button sent_order;
    private ArrayList<String> existing_product_id;
    //private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_cart_order_edit);
        product_list = new ArrayList<HashMap<String,Integer>>();
        product_id_list = new HashMap<String,Integer>();
        recyclerView = findViewById(R.id.Order_product_edit_view);
        display_total  = findViewById(R.id.edit_Total);
        sent_order = findViewById(R.id.order_Comfirm);
        existing_product_id = new ArrayList<String>();
        Intent intent = this.getIntent();
        //get the store information
        Order order =  (Order) intent.getSerializableExtra("order");
        //

        //
        //store store_id:
        order_id = order.getId();

        setProductInfo(order);

    }

    private void setAdapter() {
        OrderProductAdapter adapter = new OrderProductAdapter(product_list,order_id,display_total,sent_order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setProductInfo(Order order) {
        product_id_list = order.getItems_ids();
        FirebaseDatabase ref = FirebaseDatabase.getInstance();
        existing_product_id.clear();
        ref.getReference(DBConstants.STORES_PATH).child(order.getOrder_store_id()).child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot each_product: snapshot.getChildren()){
                    existing_product_id.add(each_product.getKey());
                }
                for(HashMap.Entry<String,Integer> entry:product_id_list.entrySet()){
                    HashMap<String,Integer> new_hashmap_product = new HashMap<String,Integer>();

                    if(existing_product_id.contains(entry.getKey())){
                       // Log.i("demo", "hello "+existing_product_id);
                        new_hashmap_product.put(entry.getKey(),entry.getValue());
                        product_list.add(new_hashmap_product);
                        //Log.i("demo", "hello "+product_list);
                    }
                    //Log.i("demo","Stuck here"+product_list);
                }
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}