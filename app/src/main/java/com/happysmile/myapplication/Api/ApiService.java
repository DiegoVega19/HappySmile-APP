package com.happysmile.myapplication.Api;


import com.google.gson.JsonArray;
import com.happysmile.myapplication.Model.LoginRequest;
import com.happysmile.myapplication.Model.LoginResponse;
import com.happysmile.myapplication.Model.Municipio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @GET("getMuni")
    Call<List<Municipio>> getMuni();

    //Para el login
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest",
    })
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
