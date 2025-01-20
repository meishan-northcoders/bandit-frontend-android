package com.northcoders.banditandroid.service;

import com.northcoders.banditandroid.model.CorrespondentRequestDTO;
import com.northcoders.banditandroid.model.MessageRequestDTO;
import com.northcoders.banditandroid.model.MessageResponseDTO;
import com.northcoders.banditandroid.model.Profile;

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
    @GET("api/p1/greeting")
    public Call<ResponseBody>  getGreeting(@Header("Authorization") String authToken);

    @GET("profile")
    public Call<Profile> getProfile();

    //messages endpoints
    @POST("api/v1/messages")
    public Call<MessageResponseDTO> saveMessage(@Header("Authorization") String authHeader, @Body MessageRequestDTO messageRequestDTO);

    @POST("api/v1/messages/user")
    public Call<List<MessageResponseDTO>> getMessagesBetweenUsers(@Header("Authorization") String authHeader, @Body CorrespondentRequestDTO correspondentRequestDTO);
}
