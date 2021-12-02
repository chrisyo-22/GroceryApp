package com.example.groceryapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

public class Product implements Serializable {

    private String brand;
    private int price; // in cents
    private String name;
    @Exclude
    private String product_id;
    public String getId() {
        return product_id;
    }

    public void setId(String product_id) {
        this.product_id = product_id;
    }



    private final DecimalFormat df = new DecimalFormat("#.00");

    public Product() {
        this.brand = "null-brand";
        this.price = 0;
        this.name = "null-name";
    }

    public Product(String brand, int price, String name) {
        this.brand = brand;
        this.price = price;
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public String getPriceAsString(boolean withDollarSign) {
        return (withDollarSign ? "$" : "") + df.format(((double)price)/100.0);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.price, price) == 0 && brand.equals(product.brand) && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, price, name);
    }
}
