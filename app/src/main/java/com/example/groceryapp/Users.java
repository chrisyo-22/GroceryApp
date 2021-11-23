package com.example.groceryapp;

public class Users {
    int owned_store_id;
    private String username, email, name;

    //constructor:
    public Users(String username,  String email, String name){
        this.username = username;
        this.email = email;
        this.name = name;
        owned_store_id = -1;
    }

    //we need to override Hashcode, and equals method in Users too!


    public int getOwned_store_id() {
        return owned_store_id;
    }

    public void setOwned_store_id(int owned_store_id) {
        this.owned_store_id = owned_store_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
