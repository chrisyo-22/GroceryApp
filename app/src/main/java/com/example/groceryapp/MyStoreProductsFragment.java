package com.example.groceryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
 * Use the {@link MyStoreProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStoreProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "com.example.groceryapp.user_store_id";

    // TODO: Rename and change types of parameters
    private String user_store_id;

    private DatabaseReference ref_to_store_products;

    ListView productListView;
    List<Map<String, String>> product_info_list;
    ArrayList<Product> product_list;

    Button addProductButton;

    boolean dialogBoxCancelled = false;
    String currentAddProductName;

    public MyStoreProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyStoreProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyStoreProductsFragment newInstance(String param_store_id) {
        MyStoreProductsFragment fragment = new MyStoreProductsFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_store_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        productListView = (ListView) getView().findViewById(R.id.products_list_view);
        product_info_list = new ArrayList<Map<String, String>>();
        product_list = new ArrayList<Product>();

        productListView.setFocusableInTouchMode(true);

        ref_to_store_products = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH)
                .child(user_store_id)
                .child(DBConstants.STORE_PRODUCTS);

        addProductButton = (Button) getView().findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxCancelled = false;
                displayNameDialog();

            }
        });
        attachListeners();
    }

    private void displayNameDialog() {
        AlertDialog.Builder nameDialogBuilder = new AlertDialog.Builder(getActivity());
        nameDialogBuilder.setTitle("Enter product name");

        EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        nameDialogBuilder.setView(input);

        nameDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentAddProductName = input.getText().toString();



                Product new_product = new Product("brand", 69, currentAddProductName);
                String prod_id = IDGenerator.generateID(IDGenerator.PRODUCT_PREFIX);

                ref_to_store_products.child(prod_id).setValue(new_product);

            }
        });
        nameDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialogBoxCancelled = true;
            }
        });
        nameDialogBuilder.show();
    }

    private void attachListeners() {
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), product_info_list,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });
        productListView.setAdapter(adapter);

        //reading all the stores!

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                for (DataSnapshot productSnap : dataSnapshot.getChildren()) {

                    //Log.i("demo", "How many times");
                    Product product = productSnap.getValue(Product.class);

                    if(product_list.contains(product)) continue;

                    //add products into the store
                    //the products node within each store object in firebase is a map of products
                    //and not a list of products, so add each into the product list using a for loop

                    product_list.add(product);

                    //for each item on listview: display store name and address
                    Map<String, String> single_store = new HashMap<String, String>(2);
                    single_store.put("First Line", product.getName() + " | " + product.getBrand());
                    single_store.put("Second Line",Float.toString(product.getPrice()));
                    product_info_list.add(single_store);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref_to_store_products.addValueEventListener(listener);
    }
}