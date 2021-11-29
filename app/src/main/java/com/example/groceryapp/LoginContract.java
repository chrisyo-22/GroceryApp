package com.example.groceryapp;

import android.app.Activity;

import java.util.ArrayList;

public interface LoginContract {
    public interface Model{
//        public void validate_user(String email, String password);
//        //user info:
//        public String get_User_name();
//        public String get_User_email();
//        public String get_User_store_id();
//        public User get_specific_user(String user_id);
//        public ArrayList<Order> get_user_orders(String user_id);
//        public Order current_user_order(String user_id);
//
//        //Store info:
//        public ArrayList<Store> get_all_stores();
//        public Store get_User_Store();
//
//        //two different ways to get order from a store, implement whatever one is easier.
//        public ArrayList<Order> get_store_orders_by_store(Store target_store);
//        public ArrayList<Order> get_store_orders_by_id(String store_id);
        void connectFirebaseLogin(String email, String password);
    }

    public interface View{
        void viewLoginSuccess();
        void viewLoginFailed(String message);
    }

    public interface Presenter{
        void onHandleLogin(String email, String password);
    }

    public interface LoginListener {
        void onSuccess();
        void onFailure(String message);
    }
}
