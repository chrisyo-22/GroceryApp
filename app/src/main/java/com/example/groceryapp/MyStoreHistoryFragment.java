package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyStoreHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStoreHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "com.example.groceryapp.user_store_id";

    // TODO: Rename and change types of parameters
    private String user_store_id;

    public MyStoreHistoryFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyStoreOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyStoreOrdersFragment newInstance(String param_store_id) {
        MyStoreOrdersFragment fragment = new MyStoreOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param_store_id);
        //method is not call in here, dont know how or why:
        //Log.i("demo","what is this: "+ param_store_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_store_history, container, false);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.orders_list_history);

        ArrayList<String> ordersTitleList = new ArrayList<>();
        ArrayList<HashMap<String,String>> order_to_user = new ArrayList<HashMap<String,String>>();
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ordersTitleList);
        listView.setAdapter(listViewAdapter);


        //get the user_id, and then use it to get the store orders:
        FirebaseDatabase f_auth = FirebaseDatabase.getInstance();
        FirebaseUser f_user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = f_user.getUid();

        f_auth.getReference("Users").child(UID).child("owned_store_id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task){
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    user_store_id = task.getResult().getValue().toString();
                    //Log.i("demo","my userid is "+user_store_id);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(user_store_id).child(DBConstants.STORE_ORDERS);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ordersTitleList.clear();
                            if(!dataSnapshot.exists()){
                                //do something here if the user have no order in his/her store
                                Log.i("demo", "hey u have no order");
                            }

                            else{
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String user_id = snapshot.getValue().toString();
                                    String order_id = snapshot.getKey();

                                    HashMap<String, String> user_order_ids = new HashMap<String,String>();
                                    user_order_ids.put(order_id, user_id);



                                    DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference(DBConstants.USERS_PATH).child(user_id);

                                    reference_2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String state = snapshot.child(DBConstants.STORE_ORDERS).child(order_id).child(DBConstants.ORDER_COMPLETE).getValue().toString();

                                            if(state.equals("true")){
                                                order_to_user.add(user_order_ids);
                                                ordersTitleList.add(snapshot.child("name").getValue().toString()+ "  " + order_id);
                                                listViewAdapter.notifyDataSetChanged();
                                            }

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

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), MystoreOrderSummaryHistory.class);

                            intent.putExtra("order_to_user",  order_to_user.get(index));

                            startActivity(intent);
                        }
                    });
                }
            }
        });
        //Log.i("demo","my new user is "+user_store_id);
    }

}