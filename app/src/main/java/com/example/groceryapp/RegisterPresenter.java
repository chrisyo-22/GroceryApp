package com.example.groceryapp;

import android.app.Activity;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.RegisterListener{
    private RegisterModel register_Model;
    private RegisterContract.View register_View;

    public RegisterPresenter(RegisterContract.View register_View) {
        this.register_View = register_View;
        register_Model = new RegisterModel(this);
    }

    @Override
    public void onHandleRegister(String email, String password, String name) {
        register_Model.connectFirebaseRegister(email,password,name);
    }

    @Override
    public void RegisterSuccessful() {
        register_View.onSuccess();
    }

    @Override
    public void RegisterFailure(String message) {
        register_View.onFailure(message);
    }
}
