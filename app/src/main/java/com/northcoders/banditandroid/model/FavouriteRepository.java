package com.northcoders.banditandroid.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepository {


    Application application;
    BandMateApiService bandMateApiService;

    private MutableLiveData<List<Profile>> mutableLiveData = new MutableLiveData<>();

    public FavouriteRepository(Application application) {
        this.application = application;
        bandMateApiService = RetrofitInstance.getService();
    }

    public MutableLiveData<List<Profile>> getFavouriteProfiles() {

        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);


        Call<List<Profile>> favouriteCall = bandMateApiService.getUserFavourites(token);

        favouriteCall.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                List<Profile> profiles = response.body();
                profiles.forEach(System.out::println);
                mutableLiveData.postValue(profiles);
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }


}
