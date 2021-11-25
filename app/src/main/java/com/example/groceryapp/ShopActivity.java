package com.example.groceryapp;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopActivity extends GeneralPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigataion(R.layout.activity_shop);

        //create sample stores in here:
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref_temp = FirebaseDatabase.getInstance().getReference();
        for(int i =0; i<30;i++){
            Store new_store = new Store(UID,"Walmart_"+i);
            ref_temp.child("Stores").child("Walmart"+i).setValue(new_store);
        }





    //reading all the stores!
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    Store store = child.getValue(Store.class);
                    Log.i("demo", store.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);

    }



}