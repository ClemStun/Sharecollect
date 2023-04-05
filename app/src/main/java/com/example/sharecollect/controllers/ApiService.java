package com.example.sharecollect.controllers;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * The interface Api service.
 */
public interface ApiService {

    /**
     * Upload image call.
     *
     * @param image the image
     * @return the call
     */
    @Multipart
    @POST("newpp")
    Call<ResponseBody> uploadImage(
            @Part MultipartBody.Part image
    );

}
