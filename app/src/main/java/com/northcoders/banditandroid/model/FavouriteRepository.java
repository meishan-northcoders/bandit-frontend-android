package com.northcoders.banditandroid.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepository {


    Application application;
    BandMateApiService bandMateApiService;

    private MutableLiveData<List<Favourite>> mutableLiveData = new MutableLiveData<>();

    public FavouriteRepository(Application application) {
        this.application = application;
        bandMateApiService = RetrofitInstance.getService();
    }

    public MutableLiveData<List<Favourite>> getMutableLiveData() {
        Call<List<Favourite>> favouriteCall = bandMateApiService.getUserFavourites();
        favouriteCall.enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                List<Favourite> favourites = response.body();
                mutableLiveData.setValue(favourites);
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }


}
