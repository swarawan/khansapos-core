package com.khansapos.core.utils;

import java.util.regex.Pattern;

public class ValidatorUtils {

    private static final Pattern NUMERIC = Pattern.compile(".*\\d+.*");
    private static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isEmailValid(String email) {
        return EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isNumeric(String plain) {
        return NUMERIC.matcher(plain).matches();
    }
}
