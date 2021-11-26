package com.example.groceryapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class Order {
    private String customer_id;
    private ArrayList<OrderItem> items;
    private boolean is_complete = false;
    private String date;

    public boolean is_complete() {
        return is_complete;
    }

    public void complete_order() {
        this.is_complete = true;
    }

    public Order(String customer_id){
        this.customer_id = customer_id;
        items = new ArrayList<OrderItem>();

        // Get current time

        date = DBConstants.dateFormat.format(Calendar.getInstance().getTime());
    }

    //adding item to the Hashtable:
    public void add_item(Product product, int quantity){
        items.add(new OrderItem(product,quantity));
    }

    public void set_quantity(Product product, int quantity) {
        for(OrderItem item : items) {
            if(product.equals(item.getProduct())) {
                item.setQuantity(quantity);
            }
        }
    }

    //remove item from the Hashtable:
    //if the order quantity is ZERO:
    public void remove_item(Product target_product){
        items.remove(target_product);
    }

}
