package com.example.groceryapp;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    private String brand;
    private float price;
    private String name;

    public Product() {
        this.brand = "null-brand";
        this.price = -1.0f;
        this.name = "null-name";
    }

    public Product(String brand, float price, String name) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
