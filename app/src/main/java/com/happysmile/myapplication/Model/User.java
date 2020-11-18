package com.happysmile.myapplication.Model;

import java.io.Serializable;

public class User {
    public int id;
    public  String name;
    public  String email;
    public  String email_verified_at;
    public String created_at;
    public String updated_at;


    public User(int id, String name, String email, String email_verified_at, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
