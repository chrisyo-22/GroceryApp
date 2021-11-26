package com.example.groceryapp;

import java.nio.charset.Charset;
import java.util.Random;

public class IDGenerator {
    public static final String STORE_PREFIX = "sto_";
    public static final String ORDER_PREFIX = "ord_";
    public static final String PRODUCT_PREFIX = "prod_";

    private static final char[] validChars = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                                            'u', 'v', 'w', 'x', 'y', 'z',
                                            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                                            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                                            'U', 'V', 'W', 'X', 'Y', 'Z',
                                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    //Returns a random, 28 character, alphanumeric string.
    public static String generateID(String pre) {
        char[] arr = new char[28];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = validChars[(int)(Math.random() * validChars.length)];
        }
        return pre + new String(arr);
    }
}
