package com.northcoders.banditandroid.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    BandMateApiService apiService;

    private Application application;

    private MutableLiveData<Profile> mutableLiveDataProfile = new MutableLiveData<>();


    public ProfileRepository(Application application) {
        this.application = application;
        apiService = RetrofitInstance.getService();
    }


    //get's users own profile
    public MutableLiveData<Profile> getMutableLiveDataProfile(){
        Call<Profile> call = apiService.getUserProfile();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                Profile profile = response.body();

                mutableLiveDataProfile.setValue(profile);
                System.out.println("Successfully retrieved user's profile");
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println("Failed to retrieve user's profile") ;

            }
        });

        return mutableLiveDataProfile;
    }

    public void createUserProfile(Profile profile){
        Call<Profile> profileCall = apiService.postProfile(profile);

        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                System.out.println("successfully created profile");
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println("failed to create profile");

            }
        });
    }
}
