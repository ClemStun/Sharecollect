package com.example.sharecollect.models;

public class User {

    private int id;
    private String username;
    private String email;
    //ajouter l'image de profil

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    /* GETTERS */

    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    /* SETTERS */

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
