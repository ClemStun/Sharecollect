package com.example.sharecollect.controllers;

import com.example.sharecollect.models.User;

import java.io.Serializable;

/**
 * The type User controller.
 */
public class UserController implements Serializable {

    private static UserController instance;

    private User user;

    private UserController() {
        user = null;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    /* GETTERS */

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /* SETTERS */

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
