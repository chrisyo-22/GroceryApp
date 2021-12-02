package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class trytest extends AppCompatActivity {
    private String user_store_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trytest);


        ListView listView = (ListView) findViewById(R.id.OrderSummaryList);
        ArrayList<String> ordersList = new ArrayList<>();
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(trytest.this, android.R.layout.simple_list_item_1, ordersList);
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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(user_store_id).child(DBConstants.STORE_ORDERS).child("order1");


                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ordersList.clear();
                            if (!dataSnapshot.exists()) {
                                //do something here if the user have no order in his/her store
                                Log.i("demo", "hey u have no order");
                            } else {


                                for (DataSnapshot snapshot : dataSnapshot.child("items").getChildren()) {


                                    ordersList.add(snapshot.getKey() + "    " + snapshot.getValue().toString());
                                }
                                listViewAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    };

                    reference.addValueEventListener(listener);

                }
            }
        });
        //Log.i("demo","my new user is "+user_store_id);
    }


//    private String getProductName(String product_id) {
//
//        final String[] name = new String[1];
//        FirebaseDatabase f_auth = FirebaseDatabase.getInstance();
//        FirebaseUser f_user = FirebaseAuth.getInstance().getCurrentUser();
//        String UID = f_user.getUid();
//
//
//        f_auth.getReference("Users").child(UID).child("owned_store_id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                user_store_id = task.getResult().getValue().toString();
//                //Log.i("demo","my userid is "+user_store_id);
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(user_store_id).child(DBConstants.STORE_PRODUCTS);
//
//
//                ValueEventListener listener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if (!dataSnapshot.exists()) {
//                            //do something here if the user have no order in his/her store
//                            Log.i("demo", "hey u have no order");
//                        } else {
//
//
//                            for (DataSnapshot snapshot : dataSnapshot.child("items").getChildren()) {
//
//
//                                name[0] = snapshot.child("")getKey();
//
//                            }
//
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                };
//            }
//        });
//
//        return name[0];
//    }

}





