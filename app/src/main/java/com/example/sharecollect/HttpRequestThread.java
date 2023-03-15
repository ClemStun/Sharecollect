package com.example.sharecollect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Thread that allows to perform an asynchronous HTTP GET request
 * and to retrieve the result of the request
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpRequestThread implements Runnable {
    private final String urlString;
    private HashMap<String, Object> requestResult;

    public HttpRequestThread(String urlString) {
        this.urlString = urlString;
    }

    public HashMap<String, Object> getRequestResult() {
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

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap<String, Object> responseMap = gson.fromJson(in, type);

            hashMapValues2String(responseMap);

            in.close();

            requestResult = responseMap;

        } catch (Exception e) {
            Logger.getLogger(HttpRequestThread.class.getName()).log(Level.SEVERE, "HTTP request error : ", e);
        }
    }

    /**
     * Allows to convert the values of a HashMap
     * from all types to String
     * @param hashMap : HashMap to convert
     */
    private void hashMapValues2String(HashMap<String, Object> hashMap){

        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {

            Object value = entry.getValue();

            if (value instanceof Double) {
                Double v = (Double) value;
                entry.setValue(Integer.toString(v.intValue()));

            } else if (value instanceof HashMap) {
                HashMap<String, Object> childHashMap = (HashMap<String, Object>) value;
                hashMapValues2String(childHashMap);
                entry.setValue(childHashMap);

            } else if (value instanceof Boolean) {
                Boolean v = (Boolean) value;
                entry.setValue(v.toString());
            }
        }

    }
}
