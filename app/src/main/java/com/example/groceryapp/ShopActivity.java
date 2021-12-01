package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends GeneralPage {

    private ListView stores_lv;
    List<Map<String, String>> stores_to_display;
    ArrayList<Store> store_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_shop);
        stores_lv = (ListView) findViewById(R.id.shop_store_list);
        stores_to_display = new ArrayList<Map<String, String>>();
        store_list = new ArrayList<Store>();
        ListingStores();
    }

    //get data from Firebase and set data to storeArrayList
    private void ListingStores() {
        //array adapter for the list view
        SimpleAdapter adapter = new SimpleAdapter(this, stores_to_display,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });
        stores_lv.setAdapter(adapter);

        //reading all the stores!
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("demo", "data changed");
                for (DataSnapshot EachStore : dataSnapshot.getChildren()) {
                    //Log.i("demo", "How many times");
                    Store store = EachStore.getValue(Store.class);

                    //add products into the store
                    //the products node within each store object in firebase is a map of products
                    //and not a list of products, so add each into the product list using a for loop
                    DataSnapshot Products = EachStore.child(DBConstants.STORE_PRODUCTS);
                    for (DataSnapshot EachProduct : Products.getChildren()){
                        store.add_store_product(EachProduct.getValue(Product.class));
                    }

                    store_list.add(store);

                    //for each item on listview: display store name and address
                    Map<String, String> single_store = new HashMap<String, String>(2);
                    single_store.put("First Line", store.getName());
                    single_store.put("Second Line",store.getAddress());
                    stores_to_display.add(single_store);
                   // Log.i("demo", store.getName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);

        //saving product id:


        stores_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent intent = new Intent();
                intent.setClass(ShopActivity.this, ShopProductActivity.class);
                //send the information of the store clicked to next activity
                intent.putExtra("store",store_list.get(index));

                //store product_id into hashmap:
                HashMap<String,Product> id_to_product = new HashMap<String,Product>();
                ref.child(""/*this replace with store_id*/).child("products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child:snapshot.getChildren()) {
                            Product product = child.getValue(Product.class);
                            id_to_product.put(child.getKey(),product);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                intent.putExtra("id_to_product",id_to_product);
                startActivity(intent);
            }
        });
    }



}