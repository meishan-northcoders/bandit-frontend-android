package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.Profile;

import java.util.ArrayList;
import com.northcoders.banditandroid.model.Profile;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;

public interface BandMateApiService {
    @GET("greeting")
    public Call<ResponseBody>  getGreeting();

    @GET("profiles")
    public Call<ArrayList<Profile>> getAllProfiles();

    @GET("profiles/user")
    public Call<Profile> getUserProfile(); //gets current logged in user profile

    @POST("profiles")
    public Call<Profile> postProfile(@Body Profile profile);

    @GET("api/p1/greeting")
    public Call<ResponseBody>  getGreeting(@Header("Authorization") String authToken);

}
