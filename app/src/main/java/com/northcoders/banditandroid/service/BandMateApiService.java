package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.Profile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BandMateApiService {
    @GET("greeting")
    public Call<ResponseBody>  getGreeting();

    @GET("profiles")
    public Call<Profile> getUserProfile();

    @POST("profiles")
    public Call<Profile> postProfile(Profile profile);
}
