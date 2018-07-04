package com.example.mctuan.restaurantmanagement.Object;

/**
 * Created by mctuan on 7/4/18.
 */

public class User {
    private String fullName;
    private boolean isAdmin;

    public User(String fullName, boolean isAdmin) {
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getFullName() {
        return fullName;
    }
}
