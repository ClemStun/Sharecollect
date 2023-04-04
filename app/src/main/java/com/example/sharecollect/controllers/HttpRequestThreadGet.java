package com.example.sharecollect.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread that allows to perform an asynchronous HTTP GET request
 * and to retrieve the result of the request
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpRequestThreadGet implements Runnable {
    private final String urlString;
    private final String responseType;
    private HashMap<String, Object> requestResult;

    public HttpRequestThreadGet(String urlString, String responseType) {

        this.urlString = urlString;
        this.responseType = responseType;

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

            if(responseType.equals("json")) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));

                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, Object>>() {
                }.getType();
                HashMap<String, Object> responseMap = gson.fromJson(in, type);

                hashMapValues2String(responseMap);

                in.close();

                requestResult = responseMap;
            } else if (responseType.equals("image")) {

                Bitmap bitmap = null;

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }

                requestResult = new HashMap<>();
                requestResult.put("image", bitmap);

            }

        } catch (Exception e) {
            Logger.getLogger(HttpRequestThreadGet.class.getName()).log(Level.SEVERE, "HTTP request error : ", e);
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

            } else if (value instanceof ArrayList) {
                ArrayList<LinkedTreeMap<String, Object>> v = (ArrayList<LinkedTreeMap<String, Object>>) value;
                HashMap<String, Object> newHashMap = ArrayList2HashMap(v);
                entry.setValue(newHashMap);
            } else if (value instanceof Boolean) {
                Boolean v = (Boolean) value;
                entry.setValue(v.toString());
            }
        }
    }

    /**
     * Allows to convert an ArrayList of LinkedTreeMap
     * to a HashMap
     * @param arrayList : ArrayList to convert
     * @return the converted HashMap
     */
    private HashMap<String, Object> ArrayList2HashMap(ArrayList<LinkedTreeMap<String, Object>> arrayList) {
        HashMap<String, Object> newHashMap = new HashMap<>();
        for (int i = 0; i < arrayList.size(); i++) {
            LinkedTreeMap<String, Object> childLinkedTreeMap = (LinkedTreeMap<String, Object>) arrayList.get(i);
            HashMap<String, Object> childHashMap = new HashMap<>(childLinkedTreeMap);

            hashMapValues2String(childHashMap);

            newHashMap.put(Integer.toString(i), childHashMap);
        }

        return newHashMap;
    }
}
