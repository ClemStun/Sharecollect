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

        HashMap<String, String> response = response2HashMap(httpRequestThread.getRequestResult());

        if (Objects.equals(response.get("valid"), "true"))
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

        HashMap<String, String> response = response2HashMap(httpRequestThread.getRequestResult());

        if (Objects.equals(response.get("valid"), "true"))
            return "id:" + response.get("id") + ",token:" + response.get("token");
        else
            return "User not found";
    }

    /**
     * Allows to get a user's information
     * using an asynchronous HTTP GET request
     * @param id : user's id
     * @param token : user's token
     * @return String containing the user's information or an error message
     */
    public String getUser(String id, String token) {
        String urlString = "http://34.22.199.112/user/profil/" +
                id +
                "?" +
                token;

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

        HashMap<String, String> response = response2HashMap(httpRequestThread.getRequestResult());

        if (Objects.equals(response.get("valid"), "true"))
            return response.get("username");
        else
            return "User doesn't exist";
    }

    /**
     * Allows to convert a response from a HTTP GET request
     * to a HashMap
     * @param response : response from a HTTP GET request
     * @return HashMap containing the response elements
     */
    private HashMap<String, String> response2HashMap(String response) {
        response = response.replaceAll("\"", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "");

        String[] result = response.split(",");

        HashMap<String, String> hashMap = new HashMap<>();
        for (String s : result) {
            String[] keyValue = s.split(":");
            hashMap.put(keyValue[0], keyValue[1]);
        }

        return hashMap;
    }
}
