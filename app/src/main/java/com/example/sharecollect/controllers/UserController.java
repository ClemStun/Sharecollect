package com.example.sharecollect.controllers;

import com.example.sharecollect.models.User;

import java.io.Serializable;

public class UserController implements Serializable {

    private static UserController instance;

    private User user;

    private UserController() {
        user = null;
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    /* GETTERS */

    public User getUser() {
        return user;
    }

    /* SETTERS */

    public void setUser(User user) {
        this.user = user;
    }

}
