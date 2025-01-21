package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.CorrespondentRequestDTO;
import com.northcoders.banditandroid.model.MessageRequestDTO;
import com.northcoders.banditandroid.model.MessageResponseDTO;

import com.northcoders.banditandroid.model.Favourite;
import com.northcoders.banditandroid.model.Profile;

import java.util.ArrayList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BandMateApiService {
    @GET("api/v1/greeting")
    public Call<ResponseBody>  getGreeting();

    @GET("api/v1/profiles")
    public Call<ArrayList<Profile>> getAllProfiles();

    @GET("api/v1/profiles/user")
    public Call<Profile> getUserProfile(@Header("Authorization") String authToken); //gets current logged in user profile

    @PUT("api/v1/profiles")
    public Call<Profile> putProfile(@Header("Authorization") String authToken, @Body Profile profile);

    @POST("api/v1/profiles")
    public Call<Profile> postProfile(@Header("Authorization") String authToken, @Body Profile profile);

    @GET("api/p1/greeting")
    public Call<ResponseBody>  getGreeting(@Header("Authorization") String authToken);

    @GET("api/v1/profiles/filtered")
    public Call<List<Profile>> getFilteredProfiles(@Header("Authorization") String authToken);

    @GET("profile")
    public Call<Profile> getProfile();

    @POST("api/v1/messages")
    public Call<MessageResponseDTO> saveMessage(@Header("Authorization") String authHeader, @Body MessageRequestDTO messageRequestDTO);

    @POST("api/v1/messages/user")
    public Call<List<MessageResponseDTO>> getMessagesBetweenUsers(@Header("Authorization") String authHeader, @Body CorrespondentRequestDTO correspondentRequestDTO);

    @GET("api/v1/favourites/user")
    public Call<List<Profile>> getUserFavourites(@Header("Authorization") String authHeader);

    @POST("api/v1/favourites")
    public Call<Favourite> createFavourite(@Header("Authorization") String authHeader,@Body Favourite favourite);

}
