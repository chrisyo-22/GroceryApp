package com.example.groceryapp;

import android.app.Activity;

public class LoginPresenter implements LoginContract.Presenter{
    private LoginModel login_Model;
    private LoginContract.View login_View;


    public LoginPresenter(LoginContract.View login_View, LoginModel login_Model){
        this.login_View = login_View;
        //this.login_Model = new LoginModel();
        this.login_Model = login_Model;
    }

    @Override
    public void onHandleLogin(String email, String password) {
        login_Model.connectFirebaseLogin(email,password,this);
    }


    public void onSuccess() {
        login_View.viewLoginSuccess();
    }

    public void onFailure(String message) {
        login_View.viewLoginFailed(message);
    }
}