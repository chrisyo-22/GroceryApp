package com.example.groceryapp;

import java.util.Objects;

public class User {
    private int owned_store_id;
    private String email, name;

    public User(String email, String name){
        this.email = email;
        this.name = name;
        owned_store_id = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return owned_store_id == user.owned_store_id && email.equals(user.email) && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owned_store_id, email, name);
    }

    public int getOwned_store_id() {
        return owned_store_id;
    }

    public void setOwned_store_id(int owned_store_id) {
        this.owned_store_id = owned_store_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
