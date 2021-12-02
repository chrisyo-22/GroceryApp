package com.example.groceryapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends GeneralPage {

    private final int CART_TAB = 0, PROCESSING_TAB = 1, COMPLETED_TAB = 2;
    TabLayout tabLayout;
    ListView cartListView;
    List<Map<String, String>> cart_info_list;
    ArrayList<Order> order_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_cart);
    }

    @Override
    public void initializeOther() {
        cart_info_list = new ArrayList<Map<String, String>>();
        order_list = new ArrayList<Order>();


        cartListView = findViewById(R.id.cartList);
        tabLayout = findViewById(R.id.cartTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                updateCartList(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void updateCartList(int tabPos) {
        SimpleAdapter adapter = new SimpleAdapter(this, cart_info_list,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });
        cartListView.setAdapter(adapter);

        ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    Log.e("GroceryApp", "Error getting user order data", task.getException());
                } else {
                    if(task.getResult().getChildren() == null) return;
                    for(DataSnapshot orderSnap : task.getResult().getChildren()) {
                        Order order = orderSnap.getValue(Order.class);
                        Log.println(Log.DEBUG, "demo", order.getOrder_store_id());

                        if(tabPos == CART_TAB) {

                        } else if(tabPos == PROCESSING_TAB) {

                        } else if(tabPos == COMPLETED_TAB) {

                        }
                    }
                }
            }
        });
    }
}