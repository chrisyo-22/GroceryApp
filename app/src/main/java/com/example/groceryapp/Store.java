package com.example.groceryapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private String owner_id;
    private String name;
    private String address;

    ArrayList<Order> orders;
    ArrayList<Product> products;

    public Store() {
        this.owner_id = "null-id";
        this.name = "null-name";
        this.address = "null-address";
        orders = new ArrayList<Order>();
        products = new ArrayList<Product>();
    }

    public Store(String owner_id, String name, String address) {
        this.owner_id = owner_id;
        this.name = name;
        this.address = address;
        orders = new ArrayList<Order>();
        products = new ArrayList<Product>();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void adding_store_products(Product product){
        products.add(product);
    }

    public void removing_store_products(Product product){
        products.remove(product);
    }

    public void add_order(Order order){
        orders.add(order);
    }

    public void set_complete(Order completed_order){
        completed_order.complete_order();
    }

}
