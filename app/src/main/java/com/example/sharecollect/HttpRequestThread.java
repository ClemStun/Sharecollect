package com.example.sharecollect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread that allows to perform an asynchronous HTTP GET request
 * and to retrieve the result of the request
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpRequestThread implements Runnable {
    private final String urlString;
    private String requestResult;

    public HttpRequestThread(String urlString) {
        this.urlString = urlString;
    }

    public String getRequestResult() {
        return requestResult;
    }

    /**
     * Execution of the thread
     * Performs an HTTP GET request and retrieves the result
     */
    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            requestResult = response.toString();

        } catch (Exception e) {
            Logger.getLogger(HttpRequestThread.class.getName()).log(Level.SEVERE, "HTTP request error : ", e);
        }
    }
}
