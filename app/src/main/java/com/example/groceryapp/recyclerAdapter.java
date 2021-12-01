package com.example.groceryapp;

import android.util.Log;
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

        //String product_id = productList.get(position).getName();

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
               // ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("order").add
                ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("order").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String,Integer> single_item = new HashMap<String,Integer>();
                        //user order is empty:
                        if(!snapshot.exists()){
                            //single_item.put(product_id,1);
                            //ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("order").child(String.valueOf(productList.get(position))).setValue(productList.get(holder.getAdapterPosition()));
                            ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child("order").child("order_store_id").setValue(store_id);
                        }
                        else if(snapshot.child("order_store_id").getValue().toString() == store_id){
                            //if(snapshot)
                        }
                        //user order is non-empty but store id is the same:
                        Log.i("demo","the value is "+snapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
