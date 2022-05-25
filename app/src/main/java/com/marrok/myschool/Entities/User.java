package com.marrok.myschool.Entities;

public class User {
    public String email,username;

    public User() {
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
