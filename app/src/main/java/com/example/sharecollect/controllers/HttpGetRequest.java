package com.example.sharecollect.controllers;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.sharecollect.models.Item;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * That allows to perform an asynchronous HTTP GET request
 * Create URL with different elements given
 * Send the request and retrieve the result
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpGetRequest {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Allows to create a new user
     * using an asynchronous HTTP GET request
     * @param pseudo : user's pseudo
     * @param email : user's email
     * @param password : user's password
     * @return true if the creation is a success, false otherwise
     */
    public static HashMap<String, Object> createUser(String pseudo, String email, String password) {
        String urlString = "http://34.22.199.112/user/create?username=" +
                pseudo +
                "&mail=" +
                email +
                "&password=" +
                password;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {

            response.putAll(httpRequestThreadGet.getRequestResult());

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
    public static HashMap<String, Object> connectUser(String pseudo, String password) {
        String urlString = "http://34.22.199.112/user/login?username=" +
                pseudo +
                "&password=" +
                password;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if (Objects.equals(response.get("error"), "")) {

            response.putAll(httpRequestThreadGet.getRequestResult());

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
    public static HashMap<String, Object> getUser(String id, String token) {
        String urlString = "http://34.22.199.112/user/profil/" +
                id +
                "?token=" +
                token;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");


        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());


            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User not found");
        }

        return response;
    }

    public static HashMap<String, Object> getIdByUsername(String username) {
        String urlString = "http://34.22.199.112/user/getbyusername?username=" +
                username;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User not found");
        }

        return response;
    }

    public static HashMap<String, Object> getAllCollection() {
        String urlString = "http://34.22.199.112/collection";

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString, "json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());
        }

        return response;
    }

    /**
     * Allows to get a user's collection list
     * using an asynchronous HTTP GET request
     * @param idUser : user's id
     * @return String containing the user's collection list or an error message
     */
    public static HashMap<String, Object> getCollectionList(String idUser) {
        String urlString = "http://34.22.199.112/user/" +
                idUser +
                "/getcollections";

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "User not found");

        }

        return response;
    }

    /**
     * Allows to get a collection's information
     * using an asynchronous HTTP GET request
     * @param id : collection's id
     * @return String containing the collection's information or an error message
     */
    public static HashMap<String, Object> getCollectionInformation(String id) {
        String urlString = "http://34.22.199.112/collection/" +
                id;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");


        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString,"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());


            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "Collection not found");
        }

        return response;
    }


    /**
     * Allows to create a collection
     * using an asynchronous HTTP GET request
     * @param idUser : user's id
     * @param token : user's token
     * @param title : collection's title
     * @param description : collection's description
     * @param items : collection's items
     * @return String containing the collection's information or an error message
     */
    public static HashMap<String, Object> createCollection(String idUser, String token, String title, String description, List<Item> items) {
        StringBuilder urlString = new StringBuilder("http://34.22.199.112/collection/create?userid=" +
                idUser +
                "&token=" +
                token +
                "&title=" +
                title +
                "&desc=" +
                description +
                "&");

        for (int i = 0; i < items.size(); i++) {
            urlString.append("items[").append(i).append("][name]=").append(items.get(i).getTitle()).append("&");
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString.toString(),"json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if(Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "error");
        }

        return response;
    }

    public static HashMap<String, Object> addNotifToken(String id, String token) {
        String urlString = "http://34.22.199.112/user/" +
                id +
                "/addnotiftoken?token=" +
                token;

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        HttpRequestThreadGet httpRequestThread = new HttpRequestThreadGet(urlString,"json");

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

    public static HashMap<String, Object> sendNotif(String id) {
        String urlString = "http://34.22.199.112/user/" +
                id +
                "/sendnotif";

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        HttpRequestThreadGet httpRequestThread = new HttpRequestThreadGet(urlString,"json");

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

    /**
     * Allows to get a collection's items
     * using an asynchronous HTTP GET request
     * @param idCollection : collection's id
     * @return String containing the collection's items or an error message
     */
    public static HashMap<String, Object> getItems(String idCollection) {
        String urlString = "http://34.22.199.112/collection/"
                + idCollection
                + "/items";

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");

        // Thread creation
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString, "json");

        // Thread execution
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
            response.put("error", "Network error");
        }

        if (Objects.equals(response.get("error"), "")) {
            response.putAll(httpRequestThreadGet.getRequestResult());

            if (Objects.equals(response.get("valid"), "false"))
                response.put("error", "Collection not found");
        }

        return response;
    }

    public static void sendProfilePicture(String idUser, File image) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", image.getName(), requestBody);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.22.199.112/user/" + idUser + "/")
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.uploadImage(imagePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.println(Log.INFO, "USER PP", "Réponse : " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.ERROR, "USER PP", "Error when trying to send new pictures");
            }
        });
    }

    public static void sendItemPicture(String idUser, File image) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", image.getName(), requestBody);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.22.199.112/user/" + idUser + "/")
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.uploadImage(imagePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.println(Log.INFO, "USER PP", "Réponse : " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.ERROR, "USER PP", "Error when trying to send new pictures");
            }
        });
    }
    
    public static Bitmap getProfilePicture(String idUser) {

        String urlString = "http://34.22.199.112/user/" + idUser + "/pp";

        Log.println(Log.DEBUG, "GET PP", "URL : " + urlString);
        HttpRequestThreadGet httpRequestThreadGet = new HttpRequestThreadGet(urlString, "image");
        Future<?> future = executorService.submit(httpRequestThreadGet);

        try {
            future.get(); // Waiting for the thread to end
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(HttpGetRequest.class.getName()).log(Level.SEVERE, "Waiting thread error : ", e);
        }

        return (Bitmap) httpRequestThreadGet.getRequestResult().get("image");

    }
}