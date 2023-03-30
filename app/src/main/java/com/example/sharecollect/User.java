package com.example.sharecollect;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String token;

    private String username;

    private String mail;

    private String picture;

    public User(String id, String token, String username, String mail) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }
}
