package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyStoreOrderSummary extends AppCompatActivity {
    private String user_store_id;
    int selectedItem = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_order_summary);

        ListView listView = (ListView) findViewById(R.id.OrderSummaryList);
        ArrayList<String> orderSummaryList = new ArrayList<>();
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(MyStoreOrderSummary.this, android.R.layout.simple_list_item_1, orderSummaryList);
        listView.setAdapter(listViewAdapter);

        //get the user_id, and then use it to get the store orders:
        FirebaseDatabase f_auth = FirebaseDatabase.getInstance();
        FirebaseUser f_user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = f_user.getUid();
        f_auth.getReference("Users").child(UID).child("owned_store_id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                } else {
                    user_store_id = task.getResult().getValue().toString();
                    //Log.i("demo","my userid is "+user_store_id);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(user_store_id).child(DBConstants.STORE_ORDERS);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            orderSummaryList.clear();
                            if (!dataSnapshot.exists()) {
                                //do something here if the user have no order in his/her store
                                Log.i("demo", "hey u have no order");
                            } else {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String user_id = snapshot.getValue().toString();
                                    String order_id = snapshot.getKey();

                                    DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference(DBConstants.USERS_PATH).child(user_id).child("orders").child(order_id);

                                    reference_2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {



                                                orderSummaryList.add(dataSnapshot2.getKey());
                                                listViewAdapter.notifyDataSetChanged();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        //Log.i("demo","my new user is "+user_store_id);
        Button removeProductButton = findViewById(R.id.complete_button);


        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem != -1)
                    displayDeleteDialog();
            }
        });
    }

    private void displayDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyStoreOrderSummary.this);
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        ref_to_store_products.child(product_list_ids.get(selectedItem)).removeValue();
//                        product_list_ids.remove(selectedItem);
//                        product_list.remove(selectedItem);
                        selectedItem--;
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}