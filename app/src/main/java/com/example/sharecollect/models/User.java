package com.example.sharecollect.models;

public class User {

    private int id;
    private String username;
    private String email;
    private String token;

    public User(int id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    @Override
    public String toString(){
        return "User : id: " + id + " username: " + username + " email: " + email + " token: " + token;
    }

    /* GETTERS */

    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getToken() { return token; }

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

    public void setToken(String token) { this.token = token; }

}
