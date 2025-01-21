package com.northcoders.banditandroid.model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesRepository {
    private static final String TAG = "MatchesRepository";
    Application application;
    BandMateApiService bandMateApiService;
    private MutableLiveData<Favourite> mutableLiveData = new MutableLiveData<>();

    public MatchesRepository(Application application) {
        this.application = application;
        bandMateApiService = RetrofitInstance.getService();
    }

    public void postFavourite( Favourite favourite) {
        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        Call<Favourite> favouriteCreated = bandMateApiService.createFavourite(token,favourite);
        favouriteCreated.enqueue(new Callback<Favourite>() {

            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {
                Toast.makeText(application.getApplicationContext(), "Favourite added successfully", Toast.LENGTH_SHORT).show();
                Favourite favourite = response.body();
                mutableLiveData.setValue(favourite);
            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), "Failed to add favourite", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
