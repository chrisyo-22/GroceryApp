package com.example.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Product> productList;
    private String store_id;
    private String current_user_id;

    public recyclerAdapter(ArrayList<Product> productList, String store_id){
        this.store_id = store_id;
        this.productList = productList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView product_name, product_price,product_brand;
        private ImageView add_to_order;


        public MyViewHolder(final View view){
            super(view);
            product_name = view.findViewById(R.id.product_name);
            product_brand = view.findViewById(R.id.product_brand);
            product_price = view.findViewById(R.id.product_price);

            //define image action btn
            add_to_order = view.findViewById(R.id.add_to_cart_btn);

        }
    }
    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View product_View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_product,parent,false);
        return new MyViewHolder(product_View);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String product_name = productList.get(position).getName();
        String product_brand = productList.get(position).getBrand();
        String product_price = productList.get(position).getPriceAsString(true);

        String product_id = productList.get(position).getId();

        //Log.i("demo", "my product id is "+product_id);
        holder.product_name.setText(product_name);
        holder.product_brand.setText(product_brand);
        holder.product_price.setText(product_price);



        //clicking each add_order_btn action to be added here:
        holder.add_to_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the add button is clicked, add the item to order:
                /*
                1. Read the data check if there is order
                2. if there is no order exists then add item
                3. if there is order exists within user, then compare the store_id if it's the same
                4. if same:
                    5: add it
                    6: if not, reject the product, and remind the user he/she has items from another store.

                 */
                current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase ref = FirebaseDatabase.getInstance();
                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //if the user has no order within local:
                        if(!snapshot.exists()){
                            String order_id = IDGenerator.generateID(IDGenerator.ORDER_PREFIX);
                            //Order(String customer_id,boolean order_sent, String order_store_id, HashMap<String, Integer> items_ids)
                            HashMap<String, Integer> id_to_product = new HashMap<String, Integer>();
                            id_to_product.put(product_id,1);
                            Order new_order = new Order(current_user_id, false,false,store_id,id_to_product);
                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child(order_id).setValue(new_order);
                        }
                        //if the user has local orders, loop through all of them. And check the user has order in this store
                        else if (snapshot.exists()) {
                            boolean is_order_new_store = true;
                            for (DataSnapshot order : snapshot.getChildren()) {
                                String current_order_id = order.getKey();
                                    Order current_order = order.getValue(Order.class);
                                if (current_order.getOrder_store_id().equals(store_id) && current_order.isIs_complete()==false&&current_order.isIs_processing()==false) {
                                    is_order_new_store = false;
                                    if (current_order.getItems_ids().get(product_id)!= null) {
                                        int current_quantity = current_order.getItems_ids().get(product_id);
                                        current_quantity++;
                                        current_order.getItems_ids().put(product_id,current_quantity);
                                        ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child(current_order_id).setValue(current_order);


                                    } else {
                                        current_order.getItems_ids().put(product_id,1);
                                        ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child(current_order_id).setValue(current_order);
                                    }
                                }
                            }
                            //if the user have not been order this store, simply create one.
                            if(is_order_new_store == true){
                                String order_id = IDGenerator.generateID(IDGenerator.ORDER_PREFIX);
                                //Order(String customer_id,boolean order_sent, String order_store_id, HashMap<String, Integer> items_ids)
                                HashMap<String, Integer> id_to_product = new HashMap<String, Integer>();
                                id_to_product.put(product_id,1);
                                Order new_order = new Order(current_user_id, false,false,store_id,id_to_product);
                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child(order_id).setValue(new_order);
                            }
                        }
//                        if (!snapshot.child("localOrders").exists()) {
//                            String order_id = IDGenerator.generateID(IDGenerator.ORDER_PREFIX);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("customer_id").setValue(current_user_id);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("products").child(product_id).setValue(1);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("ordered_store_id").setValue(store_id);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("order_sent").setValue(false);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("is_complete").setValue(false);
//                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("date").setValue(DBConstants.dateFormat.format(Calendar.getInstance().getTime()));
//
//                        } else if (snapshot.child("localOrders").exists()) {
//                            boolean is_order_new_store = true;
//                            for (DataSnapshot order : snapshot.child("localOrders").getChildren()) {
//                                String current_order_id = order.getKey();
//                                if (order.child("ordered_store_id").getValue(String.class).equals(store_id)) {
//                                    is_order_new_store = false;
//                                    if (order.child("products").child((product_id)).exists()) {
//                                        ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(current_order_id).child("products").child(product_id).setValue(ServerValue.increment(1));
//
//                                    } else {
//                                        ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(current_order_id).child("products").child(product_id).setValue(1);
//                                    }
//                                }
//                            }
//                            if(is_order_new_store == true){
//                                String order_id = IDGenerator.generateID(IDGenerator.ORDER_PREFIX);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("customer_id").setValue(current_user_id);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("products").child(product_id).setValue(1);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("ordered_store_id").setValue(store_id);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("order_sent").setValue(false);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("is_complete").setValue(false);
//                                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("orders").child("localOrders").child(order_id).child("date").setValue(DBConstants.dateFormat.format(Calendar.getInstance().getTime()));
//                            }
//                        }
                    }
                        @Override
                        public void onCancelled (@NonNull DatabaseError error){

                        }

                });

                Toast.makeText(v.getContext(), product_name +" added to Cart", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
