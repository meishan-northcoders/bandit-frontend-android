package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.Profile;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BandMateApiService {
    @GET("greeting")
    public Call<ResponseBody>  getGreeting(@Header("Authorization") String authToken);

    @GET("profile")
    public Call<Profile> getProfile();
}
