package com.happysmile.myapplication.Model;

import java.io.Serializable;

public class LoginResponse implements  Serializable {
    private String access_token;
    private  User user;


    public LoginResponse(String access_token, User user) {
        this.access_token = access_token;
        this.user = user;
    }


    public String getAccess_token() {
        return access_token;
    }

    public User getUser() {
        return user;
    }
}
