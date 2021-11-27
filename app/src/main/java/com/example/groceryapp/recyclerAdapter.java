package com.example.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Product> productList;

    public recyclerAdapter(ArrayList<Product> productList){
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
        float product_price = productList.get(position).getPrice();

        holder.product_name.setText(product_name);
        holder.product_brand.setText(product_brand);
        holder.product_price.setText(String.valueOf(product_price));

        //clicking each add_order_btn action to be added here:
        holder.add_to_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the add button is clicked, add the item to order:
                Toast.makeText(v.getContext(), product_name +" added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
