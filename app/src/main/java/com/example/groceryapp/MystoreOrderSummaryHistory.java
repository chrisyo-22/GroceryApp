package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MystoreOrderSummaryHistory extends GeneralPage {

    String order_id;
    String order_user_id;

    ArrayList<String> orderSummaryList;
    ArrayAdapter<String> listViewAdapter;
    TextView display_total;
    float totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initializePage(R.layout.activity_mystore_order_summary_history);



    }

    @Override
    public void initializeOther() {
        Intent intent = this.getIntent();
        //get the order information

        HashMap<String, String> order_to_user = (HashMap<String, String>) intent.getSerializableExtra("order_to_user");

        display_total = findViewById(R.id.total_price);


        ListView listView = (ListView) findViewById(R.id.OrderSummaryList_History);
        orderSummaryList = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(MystoreOrderSummaryHistory.this, android.R.layout.simple_list_item_1, orderSummaryList);
        listView.setAdapter(listViewAdapter);

        for (HashMap.Entry<String, String> entry : order_to_user.entrySet()) {
            order_id = entry.getKey();
            order_user_id = entry.getValue();
        }
        displayOrderDetails();
    }

    private void displayOrderDetails() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.USERS_PATH).child(order_user_id).child("orders").child(order_id);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String store_id = dataSnapshot.child("order_store_id").getValue().toString();


                for(DataSnapshot snapshot : dataSnapshot.child("items_ids").getChildren()) {

                    String product_id = snapshot.getKey();
                    String product_quantity  = snapshot.getValue().toString();

                    DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(store_id).child("products").child(product_id);

                    reference_2.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
//                            Log.i("demo","my price is ");

                            float price = Float.parseFloat(snapshot2.child("price").getValue().toString())/100;

                            orderSummaryList.add(snapshot2.child("name").getValue().toString() + "    " + "$"+price + "    "+ product_quantity);

                            totalPrice+=price*Integer.parseInt(product_quantity);
//                            Log.i("demo","my first price is "+ totalPrice);


                            listViewAdapter.notifyDataSetChanged();
                            display_total.setText("Total: $" + totalPrice);

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
//                Log.i("demo","my price is "+ totalPrice);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addValueEventListener(listener);
}
}