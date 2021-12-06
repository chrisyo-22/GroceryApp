package com.example.groceryapp;

import android.app.Activity;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.LoginListener{
    private LoginModel login_Model;
    private LoginContract.View login_View;


    public LoginPresenter(LoginContract.View login_View){
        this.login_View = login_View;
        this.login_Model = new LoginModel();

    }

    @Override
    public void onHandleLogin(String email, String password) {
        login_Model.connectFirebaseLogin(email,password,(LoginContract.LoginListener)this);
    }


    @Override
    public void onSuccess() {
        login_View.viewLoginSuccess();
    }

    @Override
    public void onFailure(String message) {
        login_View.viewLoginFailed(message);
    }
}
