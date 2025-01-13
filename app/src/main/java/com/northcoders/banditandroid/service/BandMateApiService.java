package com.northcoders.banditandroid.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BandMateApiService {
    @GET("greeting")
    public Call<ResponseBody>  getGreeting();
}
