package com.example.groceryapp;

import java.util.HashSet;

public class Store {
    String owner_id;
    String name;
    HashSet<Order> orders;
    HashSet<Product> products;

    public void set_owner(String id){
        owner_id=id;
    }

    public void set_name(String name){
        this.name=name;
    }

    //adding item to the HashSet:
    public void adding_store_products(Product product){
        products.add(product);
    }

    /*public void removing_store_products(Product product){
        products.remove(product);
    }*/

    public void add_order(Order order){
        orders.add(order);
    }

    public void set_complete(Order completed_order){
        completed_order.is_complete();
    }
}
