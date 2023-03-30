package com.example.sharecollect;

import android.support.v4.os.IResultReceiver;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Thread that allows to perform an asynchronous HTTP GET request
 * and to retrieve the result of the request
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-07
 */
public class HttpRequestThreadPost implements Runnable {
    private final String urlString;

    private HashMap<String, Object> requestResult;

    private File file;

    public HttpRequestThreadPost(String urlString, File file) {
        this.urlString = urlString;
        this.file = file;
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
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            String boundary = "------------------------" + System.currentTimeMillis();
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream outputStream = urlConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getName() + "\"").append("\r\n");
            writer.append("Content-Type: image/png").append("\r\n");
            writer.append("Content-Transfer-Encoding: binary").append("\r\n");
            writer.append("\r\n");

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            fileInputStream.close();

            writer.append("\r\n").flush();
            writer.append("--" + boundary + "--").append("\r\n");
            writer.close();



            System.out.println("Response code: " + urlConnection.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            System.out.println("Response: " + in.readLine());

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap<String, Object> responseMap = gson.fromJson(in, type);

            hashMapValues2String(responseMap);

            in.close();

            requestResult = responseMap;

        } catch (Exception e) {
            Logger.getLogger(HttpRequestThreadPost.class.getName()).log(Level.SEVERE, "HTTP request error : ", e);
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
