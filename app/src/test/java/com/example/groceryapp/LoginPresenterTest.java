package com.example.groceryapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

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
    public void test_onHandleLogin() {
        String email = "testing@gmail.com";
        String password = "test123";
        LoginPresenter presenter = new LoginPresenter(view,model);
        presenter.onHandleLogin(email,password);
        verify(model).connectFirebaseLogin(email,password,presenter);
    }

    @Test
    public void testLoginSuccess() {
        LoginPresenter presenter = new LoginPresenter(view,model);
        presenter.onSuccess();
        verify(view).viewLoginSuccess();
    }

    @Test
    public void testLoginFailed() {
        LoginPresenter presenter = new LoginPresenter(view,model);
        presenter.onFailure("failed");
        verify(view).viewLoginFailed("failed");
    }
}