package com.example.groceryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateStoreActivity extends GeneralPage {

    Button createButton;
    EditText storeNameField, addressField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUserData(R.layout.activity_create_store);


    }

    @Override
    public void initializeNavigation(int activityXML) {
        super.initializeNavigation(activityXML);

        storeNameField = findViewById(R.id.editStoreName);
        addressField = findViewById(R.id.editAddress);

        createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear error messages
                storeNameField.setError(null);
                addressField.setError(null);

                // Validate input
                if(storeNameField.getText().length() < 3) {
                    storeNameField.setError("Store name must be at least 3 characters long.");
                    return;
                }
                if(addressField.getText().length() < 3) {
                    addressField.setError("Invalid address");
                    return;
                }

                // Create the store and add to database
                Store createdStore = new Store(current_user_id,
                        storeNameField.getText().toString(),
                        addressField.getText().toString());
                String store_id = IDGenerator.generateID(IDGenerator.STORE_PREFIX);
                ref.child(DBConstants.STORES_PATH).child(store_id).setValue(createdStore);

                // Set user store_id
                ref.child(DBConstants.USERS_PATH).child(current_user_id).child(DBConstants.USER_OWNEDSTORE).setValue(store_id);

                // Launch view store page
                goToMyStorePage();
            }
        });
    }
}