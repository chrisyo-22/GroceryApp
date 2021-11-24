package com.example.groceryapp;

import java.util.Hashtable;

public class Order {
    private Hashtable<Product, Integer> items;
    private boolean is_complete = false;
    public boolean is_complete() {
        return is_complete;
    }

    public void complete_order() {
        this.is_complete = true;
    }

    public Order(){
        items = new Hashtable<Product, Integer>();
    }

    //adding item to the Hashtable:
    public void set_item_quantity(Product product, Integer quantity){
        items.put(product,quantity);
    }

    //remove item from the Hashtable:
    //if the order quantity is ZERO:
    public void remove_item(Product target_product){
        items.remove(target_product);
    }

}
