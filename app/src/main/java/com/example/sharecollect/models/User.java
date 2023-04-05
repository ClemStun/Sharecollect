package com.example.sharecollect.models;

/**
 * The type User.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String token;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     * @param email    the email
     * @param token    the token
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return id; }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() { return email; }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() { return token; }

    /* SETTERS */

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) { this.token = token; }

}
