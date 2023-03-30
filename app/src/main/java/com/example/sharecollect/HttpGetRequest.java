package com.example.sharecollect;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * That allows to perform an asynchronous HTTP GET request
 * Create URL with different elements given
 * Send the request and retrieve the result
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpGetRequest {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Allows to create a new user
     * using an asynchronous HTTP GET request
     * @param pseudo : user's pseudo
     * @param email : user's email
     * @param password : user's password
     * @return true if the creation is a success, false otherwise
     */
    public HashMap<String, Object> createUser(String pseudo, String email, String password) {
        String urlString = "http://34.22.199.112/user/create?username=" +
                pseudo +
                "&mail=" +
                email +
                "&password=" +
                password;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(response.get("error").equals("")) {

            response.putAll(httpRequestThread.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User already exists");

            response.put("User", "User created");
        }

        return response;
    }

    /**
     * Allows to connect to a user account
     * using an asynchronous HTTP GET request
     * @param pseudo : user's pseudo
     * @param password : user's password
     * @return true if the connection is a success, false otherwise
     */
    public HashMap<String, Object> connectUser(String pseudo, String password) {
        String urlString = "http://34.22.199.112/user/login?username=" +
                pseudo +
                "&password=" +
                password;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if (response.get("error").equals("")) {

            response.putAll(httpRequestThread.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User not found");
        }

        return response;
    }

    /**
     * Allows to get a user's information
     * using an asynchronous HTTP GET request
     * @param id : user's id
     * @param token : user's token
     * @return String containing the user's information or an error message
     */
    public HashMap<String, Object> getUser(String id, String token) {
        String urlString = "http://34.22.199.112/user/profil/" +
                id +
                "?token=" +
                token;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");


        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(response.get("error").equals("")) {
            response.putAll(httpRequestThread.getRequestResult());


            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User not found");
        }

        return response;
    }

    public HashMap<String, Object> addNotifToken(String id, String token) {
        String urlString = "http://34.22.199.112/user/" +
                id +
                "/addnotiftoken?token=" +
                token;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if (response.get("error").equals("")) {
            response.putAll(httpRequestThread.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "Token not added");
        }

        return response;
    }

}
