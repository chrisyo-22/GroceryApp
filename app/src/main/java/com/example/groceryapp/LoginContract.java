package com.example.groceryapp;

import android.app.Activity;

import java.util.ArrayList;

public interface LoginContract {
    public interface Model{

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
