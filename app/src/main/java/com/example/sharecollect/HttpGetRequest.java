package com.example.sharecollect;

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
    public String createUser(String pseudo, String email, String password) {
        String urlString = "http://34.22.199.112/user/create?username=" +
                pseudo +
                "&mail=" +
                email +
                "&password=" +
                password;

        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            return "Network error";
        }

        if (httpRequestThread.getRequestResult().contains("valid\":true"))
            return "User created";
        else
            return "User already exists";
    }

    /**
     * Allows to connect to a user account
     * using an asynchronous HTTP GET request
     * @param pseudo : user's pseudo
     * @param password : user's password
     * @return true if the connection is a success, false otherwise
     */
    public String connectUser(String pseudo, String password) {
        String urlString = "http://34.22.199.112/user/login?username=" +
                pseudo +
                "&password=" +
                password;

        // Thread creation
        HttpRequestThread httpRequestThread = new HttpRequestThread(urlString);

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThread);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            return "Network error";
        }

        String[] result = httpRequestThread.getRequestResult().split(",");
        if (result[0].contains("valid\":true"))
            return result[2] + "," + result[3];
        else
            return "User not found";
    }
}
