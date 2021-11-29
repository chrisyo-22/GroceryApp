package com.example.groceryapp;

import android.app.Activity;

public interface RegisterContract {
    public interface Model{
        void connectFirebaseRegister( String email, String password,String name);
    }
    public interface Presenter{
        void onHandleRegister(String email, String password, String name);
    }
    public interface View{
        void onSuccess();
        void onFailure(String message);
    }

    public interface RegisterListener{
        void RegisterSuccessful();
        void RegisterFailure(String message);
    }
}
