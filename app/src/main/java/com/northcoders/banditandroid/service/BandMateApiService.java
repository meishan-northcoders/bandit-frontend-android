package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.Favourite;
import com.northcoders.banditandroid.model.Profile;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BandMateApiService {
    @GET("api/p1/greeting")
    public Call<ResponseBody>  getGreeting(@Header("Authorization") String authToken);

    @GET("profile")
    public Call<Profile> getProfile();

    @GET("api/v1/favourites")
    public Call<List<Favourite>> getUserFavourites();
}
