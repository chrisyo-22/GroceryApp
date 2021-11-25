package com.example.groceryapp;

import java.util.ArrayList;
import java.util.HashSet;

public class Store {
    private String owner_id;
    private String name;
    ArrayList<Order> orders;
    ArrayList<Product> products;


    public Store(String owner_id, String name){
        this.name = name;
        this.owner_id = owner_id;
        //orders = new HashSet<Order>();
        //products = new HashSet<Product>();
        orders = new ArrayList<Order>();
    }
    public Store(){

    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
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
