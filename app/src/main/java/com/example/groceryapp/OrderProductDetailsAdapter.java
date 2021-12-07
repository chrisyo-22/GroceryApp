package com.example.groceryapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderProductDetailsAdapter extends RecyclerView.Adapter<OrderProductDetailsAdapter.MyViewHolder> {
    private ArrayList<HashMap<String,Integer>> productList;
    private String order_id;
    private String current_user_id;
    private String order_store_id;
    private TextView display_total;
    int total;
    //private int quantity;

    public OrderProductDetailsAdapter(ArrayList<HashMap<String, Integer>> productList, String order_id, TextView display_total){
        this.order_id = order_id;
        this.productList = productList;
        this.display_total = display_total;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView product_name, product_price,product_brand;
        private TextView quantity;


        public MyViewHolder(final View view){
            super(view);
            product_name = view.findViewById(R.id.dproduct_name);
            product_brand = view.findViewById(R.id.dproduct_brand);
            product_price = view.findViewById(R.id.dproduct_price);
            quantity = view.findViewById(R.id.dproduct_quantity);



        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Order_product_detail_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_details_product,parent,false);
        return new MyViewHolder(Order_product_detail_view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductDetailsAdapter.MyViewHolder holder, int position) {
        // String product_name = productList.get(position).getName();
//        String product_brand = productList.get(position).getBrand();
//        String product_price = productList.get(position).getPriceAsString(true);
//
//        String product_id = productList.get(position).getId();
//
//        //Log.i("demo", "my product id is "+product_id);
//        holder.product_name.setText(product_name);
//        holder.product_brand.setText(product_brand);
//        holder.product_price.setText(product_price);
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase ref = FirebaseDatabase.getInstance();
        ref.getReference(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_ORDERS).child(order_id).child("order_store_id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    order_store_id = task.getResult().getValue(String.class);
                    //
                    ref.getReference(DBConstants.STORES_PATH).child(order_store_id).child(DBConstants.STORE_PRODUCTS).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot each_product: snapshot.getChildren()) {
                                Product each_pro = each_product.getValue(Product.class);
                                each_pro.setId(each_product.getKey());
                                Log.i("demo","hey "+each_pro.getId());
                                for (HashMap.Entry<String, Integer> entry : productList.get(holder.getAdapterPosition()).entrySet()) {
                                    if(entry.getKey().equals(each_pro.getId())){

                                        holder.product_name.setText(each_pro.getName());
                                        holder.product_brand.setText(each_pro.getBrand());
                                        holder.product_price.setText(each_pro.getPriceAsString(true));
                                        holder.quantity.setText(entry.getValue().toString());
                                        total += entry.getValue()*each_pro.getPrice();
                                        Log.i("demo","hey "+DBConstants.getPriceAsString(total));
                                        display_total.setText("Total: "+DBConstants.getPriceAsString(total));
                                    }
                                    //entry.getValue());
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
        holder.product_name.setText("hello");

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
