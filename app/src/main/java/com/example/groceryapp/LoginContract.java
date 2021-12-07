package com.example.groceryapp;

import android.app.Activity;

import java.util.ArrayList;

public interface LoginContract {
    public interface Model{
        void connectFirebaseLogin(String email, String password, LoginPresenter presenter);
    }

    public interface View{
        void viewLoginSuccess();
        void viewLoginFailed(String message);
    }

    public interface Presenter{
        void onHandleLogin(String email, String password);
        void onSuccess();
        void onFailure(String message);
    }
}