package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends GeneralPage {

    private final int CART_TAB = 0, PROCESSING_TAB = 1, COMPLETED_TAB = 2;
    TabLayout tabLayout;
    ListView cartListView;
    List<Map<String, String>> cart_info_list;
    ArrayList<Order> order_list;
    private ArrayList<String> existing_product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_cart);
        verifying_order_product_exists();
    }

    @Override
    public void initializeOther() {
       // verifying_order_product_exists();
        cart_info_list = new ArrayList<Map<String, String>>();
        order_list = new ArrayList<Order>();
        existing_product_id = new ArrayList<String>();

        cartListView = findViewById(R.id.cartList);
        tabLayout = findViewById(R.id.cartTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changing_tab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        changing_tab(0);

    }

    private void verifying_order_product_exists() {

        ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user order data", task.getException());
                } else {
                    if(task.getResult().getChildren() == null) return;
                    order_list.clear();
                    for(DataSnapshot orderSnap : task.getResult().getChildren()) {
                        Order order = orderSnap.getValue(Order.class);
                        order.setId(orderSnap.getKey());
                        //get store products id:
                            ref.child(DBConstants.STORES_PATH).child(order.getOrder_store_id()).child("products").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot each_product: snapshot.getChildren()){
                                        existing_product_id.add(each_product.getKey());
                                    }
                                    //loop through order and then check if it exists in the store products.
                                    for(HashMap.Entry<String,Integer> entry:order.getItems_ids().entrySet()) {
                                        Log.i("demo","You are cart "+existing_product_id );
                                        Log.i("demo","You are cart "+entry.getKey() );
                                        if(!existing_product_id.contains(entry.getKey())){

                                            ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).child(order.getId()).child("items_ids").child(entry.getKey()).removeValue();

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).child(order.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(!snapshot.child("items_ids").exists()){
                                    ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).child(order.getId()).removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
        });

    }

    private void changing_tab(int tabPos){
        ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user order data", task.getException());
                } else {

                    if(task.getResult().getChildren() == null) return;
                    order_list.clear();
                    for(DataSnapshot orderSnap : task.getResult().getChildren()) {
                        Order order = orderSnap.getValue(Order.class);
                        order.setId(orderSnap.getKey());
                        //Log.println(Log.DEBUG, "demo", order.getOrder_store_id());

                        if(tabPos == CART_TAB) {
                            cart_info_list.clear();

                            updateCartList(tabPos);
                            if(order.isIs_processing() == false && order.isIs_complete() == false){
                                order_list.add(order);

                                Map<String, String> single_product = new HashMap<String, String>(2);
                                //order.getOrder_store_id();
                                ref.child(DBConstants.STORES_PATH).child(order.getOrder_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Store current_store = task.getResult().getValue(Store.class);
                                        // Log.i("demo","You are tab 1 "+current_store.getName());
                                        single_product.put("First Line", current_store.getName());
                                        single_product.put("Second Line",orderSnap.getKey());
                                        cart_info_list.add(single_product);
                                       // Log.i("demo","your"+cart_info_list);
                                        updateCartList(tabPos);

                                    }
                                });

                            }
                        } else if(tabPos == PROCESSING_TAB) {
                            cart_info_list.clear();
                            updateCartList(tabPos);
                            if(order.isIs_processing() == true && order.isIs_complete() == false){
                                order_list.add(order);

                                Map<String, String> single_product = new HashMap<String, String>(2);
                                //order.getOrder_store_id();
                                ref.child(DBConstants.STORES_PATH).child(order.getOrder_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Store current_store = task.getResult().getValue(Store.class);
                                        //Log.i("demo","You are tab 1 "+current_store.getName());
                                        single_product.put("First Line", current_store.getName());
                                        single_product.put("Second Line",orderSnap.getKey());
                                        cart_info_list.add(single_product);
                                        updateCartList(tabPos);
                                    }
                                });

                            }
                        } else if(tabPos == COMPLETED_TAB) {
                            cart_info_list.clear();
                            updateCartList(tabPos);

                            if(order.isIs_processing() == false && order.isIs_complete() == true){
                                order_list.add(order);

                                Map<String, String> single_order = new HashMap<String, String>(2);
                                //order.getOrder_store_id();
                                ref.child(DBConstants.STORES_PATH).child(order.getOrder_store_id()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Store current_store = task.getResult().getValue(Store.class);
                                        //Log.i("demo","You are tab 1 "+current_store.getName());
                                        single_order.put("First Line", current_store.getName());
                                        single_order.put("Second Line",orderSnap.getKey());
                                        cart_info_list.add(single_order);
                                        updateCartList(tabPos);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        //set click listener:

        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                if(tabPos == 0){
                    Intent intent = new Intent();
                    intent.setClass(CartActivity.this, CartOrderEditActivity.class);
                    //send the information of the store clicked to next activity
                    intent.putExtra("order", order_list.get(index));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(CartActivity.this, CartOrderDetailsActivity.class);
                    //send the information of the store clicked to next activity
                    intent.putExtra("order", order_list.get(index));
                    startActivity(intent);
                }

            }
        });


    }

    private void updateCartList(int tabPos) {
        SimpleAdapter adapter = new SimpleAdapter(this, cart_info_list,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });
        cartListView.setAdapter(adapter);

    }


}