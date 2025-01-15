package com.northcoders.banditandroid.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    BandMateApiService apiService;

    private Application application;

    private MutableLiveData<ArrayList<Profile>> mutableAllProfiles = new MutableLiveData<>();


    public ProfileRepository(Application application) {
        this.application = application;
        apiService = RetrofitInstance.getService();
    }


    //gets all profiles
    public MutableLiveData<ArrayList<Profile>> getMutableAllProfiles(){
        Call<ArrayList<Profile>> call = apiService.getAllProfiles();

        call.enqueue(new Callback<ArrayList<Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Profile>> call, Response<ArrayList<Profile>> response) {

                ArrayList<Profile> profiles = response.body();

                mutableAllProfiles.setValue(profiles);
                System.out.println("Successfully retrieved all profiles");
            }

            @Override
            public void onFailure(Call<ArrayList<Profile>> call, Throwable t) {
                System.out.println("Failed to retrieve all profiles, reason: " + t) ;

            }
        });

        return mutableAllProfiles;
    }

    public void getUserProfile(){
        Call<Profile> call = apiService.getUserProfile();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                System.out.println("successfully retrieved user's profile");
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println("failed to retrieve user profile");
            }
        });


    }

    public void createUserProfile(Profile profile){
        Call<Profile> call = apiService.postProfile(profile);

        call.enqueue(new Callback<Profile>() {
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
