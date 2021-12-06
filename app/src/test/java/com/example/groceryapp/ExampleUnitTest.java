package com.example.groceryapp;

import org.junit.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Test
    public void testPresenter(){
        /*** stubbing ***/


        /*** verifying a method call with a specific value ***/
        //verify(view).displayMessage("user found");

        /*** verifying a method call with any value ***/
        //verify(view).displayMessage(anyString());

        /*** verifying a certain number of method calls ***/
        //verify(view, times(2)).displayMessage("user found");

        /*** Argument captors ***/
        //ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //verify(view).displayMessage(captor.capture());
        //assertEquals(captor.getValue(), "user found");

        /*** Verifying order ***/
        //InOrder order = inOrder(model, view);
        //order.verify(model).userExists("abc");
        //order.verify(view).displayMessage("user found");
    }
}