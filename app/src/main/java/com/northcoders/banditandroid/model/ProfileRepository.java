package com.northcoders.banditandroid.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private static final String TAG = "ProfileRepository";
    private MutableLiveData<Profile> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MutableLiveData<Profile> getProfile() {
        BandMateApiService bandMateApiService = RetrofitInstance.getService();
        Call<Profile> profile = bandMateApiService.getProfile();
        profile.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, Response<Profile> response) {
                Profile profile1 = response.body();
                mutableLiveData.setValue(profile1);
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
