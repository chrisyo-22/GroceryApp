package com.example.groceryapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBConstants {
    public static final int MAX_DISPLAY_NAME_LENGTH = 17;

    public static final String USERS_PATH = "Users";
    public static final String USER_NAME = "name";
    public static final String USER_OWNEDSTORE = "owned_store_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_ORDERS = "orders";

    public static final String STORES_PATH = "Stores";
    public static final String STORE_NAME = "name";
    public static final String STORE_OWNERID = "owner_id";
    public static final String STORE_ADDRESS = "address";
    public static final String STORE_PRODUCTS = "products";
    public static final String STORE_ORDERS = "orders";

    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_BRAND = "brand";
    public static final String PRODUCT_PRICE = "price";

    public static final String ORDER_CUSTOMERID = "customer_id";
    public static final String ORDER_ITEMS = "items";
    public static final String ORDER_COMPLETE = "is_complete";

    public static final String NULL = "null";

    public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public static String restrictNameLength(String name) {
        if(name == null) return NULL;
        if(name.length() < MAX_DISPLAY_NAME_LENGTH) return name;

        return name.substring(0, MAX_DISPLAY_NAME_LENGTH) + "...";
    }
}
