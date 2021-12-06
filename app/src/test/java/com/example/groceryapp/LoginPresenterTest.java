package com.example.groceryapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    LoginContract.View view;

    @Mock
    LoginModel model;

    @Test
    public void testLoginPresenter() {
        String email = "testing@gmail.com";
        String password = "test123";

        LoginPresenter presenter = new LoginPresenter(view,model);

        presenter.onHandleLogin(email,password);
        verify(model).connectFirebaseLogin(email,password,presenter);
        presenter.onSuccess();
        verify(view).viewLoginSuccess();
        presenter.onFailure("failed");
        verify(view).viewLoginFailed("failed");
    }
}