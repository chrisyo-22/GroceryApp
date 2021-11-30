package com.example.groceryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyStoreOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStoreOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "com.example.groceryapp.user_store_id";

    // TODO: Rename and change types of parameters
    private String user_store_id;

    public MyStoreOrdersFragment() {
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user_store_id = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_store_orders, container, false);
        ListView listView = (ListView) view.findViewById(R.id.orders_list);

        ArrayList<String> ordersList = new ArrayList<>();
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ordersList);
        listView.setAdapter(listViewAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH).child(user_store_id).child(DBConstants.STORE_ORDERS);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ordersList.add(snapshot.getValue().toString());
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}