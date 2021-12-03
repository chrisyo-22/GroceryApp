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
import android.widget.AdapterView;
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
    ArrayList<String> product_list_ids;

    Button addProductButton, editProductButton, removeProductButton;

    int selectedItem = -1;

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
        product_list_ids = new ArrayList<String>();

        productListView.setFocusableInTouchMode(true);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = position;
            }
        });

        ref_to_store_products = FirebaseDatabase.getInstance().getReference(DBConstants.STORES_PATH)
                .child(user_store_id)
                .child(DBConstants.STORE_PRODUCTS);

        addProductButton = (Button) getView().findViewById(R.id.add_product_button);
        editProductButton = (Button) getView().findViewById(R.id.edit_button);
        removeProductButton = (Button) getView().findViewById(R.id.delete_button);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNameDialog(null);
            }
        });
        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem != -1)
                    displayNameDialog(product_list.get(selectedItem));
            }
        });
        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem != -1)
                    displayDeleteDialog();
            }
        });
        attachListeners();
    }

    private void displayDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton(android.R.string.yes,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ref_to_store_products.child(product_list_ids.get(selectedItem)).removeValue();
                    product_list_ids.remove(selectedItem);
                    product_list.remove(selectedItem);
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

    private void displayNameDialog(Product defaultProduct) {
        AlertDialog.Builder nameDialogBuilder = new AlertDialog.Builder(getActivity());
        nameDialogBuilder.setTitle("Enter product name");

        EditText input = new EditText(getActivity());


        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        if(defaultProduct != null) input.setText(defaultProduct.getName());
        nameDialogBuilder.setView(input);

        nameDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentAddProductName = input.getText().toString();

                displayBrandDialog(defaultProduct, currentAddProductName);

            }
        });
        nameDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        nameDialogBuilder.show();
    }

    private void displayBrandDialog(Product defaultProduct, String currentAddProductName) {
        AlertDialog.Builder brandDialogBuilder = new AlertDialog.Builder(getActivity());
        brandDialogBuilder.setTitle("Enter brand name");

        EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        if(defaultProduct != null) input.setText(defaultProduct.getBrand());
        brandDialogBuilder.setView(input);

        brandDialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentAddProductBrand = input.getText().toString();
                displayPriceDialog(defaultProduct, currentAddProductName, currentAddProductBrand);

            }
        });
        brandDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        brandDialogBuilder.show();
    }

    private void displayPriceDialog(Product defaultProduct, String currentAddProductName, String currentAddProductBrand) {
        AlertDialog.Builder priceDialogBuilder = new AlertDialog.Builder(getActivity());
        priceDialogBuilder.setTitle("Enter price");

        EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if(defaultProduct != null) input.setText(defaultProduct.getPriceAsString(false));
        priceDialogBuilder.setView(input);

        priceDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int currentPrice = Math.round(Float.parseFloat(input.getText().toString())*100);
                String prod_id = defaultProduct == null ? IDGenerator.generateID(IDGenerator.PRODUCT_PREFIX) : product_list_ids.get(selectedItem);
                Product new_product = new Product(currentAddProductBrand, currentPrice, currentAddProductName);


                ref_to_store_products.child(prod_id).setValue(new_product);
            }
        });
        priceDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        priceDialogBuilder.show();
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
                product_info_list.clear();
                product_list.clear();
                product_list_ids.clear();
                for (DataSnapshot productSnap : dataSnapshot.getChildren()) {

                    String product_id = productSnap.getKey();
                    Product product = productSnap.getValue(Product.class);

//                    if(product_list_ids.contains(product_id)) continue;


                    product_list.add(product);
                    product_list_ids.add(productSnap.getKey());

                    Map<String, String> single_product = new HashMap<String, String>(2);
                    single_product.put("First Line", product.getName() + " (" + product.getBrand() + ")");
                    single_product.put("Second Line", product.getPriceAsString(true));
                    product_info_list.add(single_product);
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