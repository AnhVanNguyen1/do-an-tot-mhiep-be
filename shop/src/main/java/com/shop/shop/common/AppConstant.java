package com.shop.shop.common;

public class AppConstant {
    //Secret
    public static final String SECRET = "MockProject";
    //EXPIRATION_TIME_MS
    public static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24 * 7*100; // 7 day

    // Replace with your email here: bvvvvv
    public static final String MY_EMAIL = "thuy.le2@ntq-solution.com.vn";
    // Replace password!!
    public static final String MY_PASSWORD = "thuy231078";
    // And receiver!
    public static final String FRIEND_EMAIL = "longnguyen99it@gmail.com";

    public static final String ROLE_WORKER = "ROLE_WORKER";

    // RanDom Password
    public static final String alpha = "abcdefghijklmnopqrstuvwxyz";
    public static final String alphaUpperCase = alpha.toUpperCase(); //a-z
    public static final String digits = "0123456789"; //A-Z
    public static final String specials = "~=+%^*/()[]{}/!@#$?|";     // 0-9
    public static final String ALL = alpha + alphaUpperCase + digits + specials;
    public static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;

    // HTTP Status
    //Success
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "OK";

    //Not found
    public static final String NOT_FOUND_CODE = "404";
    public static final String NOT_FOUND_MESSAGE = "NOT FOUND";

    public static final String INTERNAL_SERVER_ERROR_CODE = "500";
    public static final String INTERNAL_SERVER_ERROR_MESAGE = "INTERNAL_SERVER_ERROR ";
}
