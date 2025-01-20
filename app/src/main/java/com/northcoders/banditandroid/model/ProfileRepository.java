package com.northcoders.banditandroid.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    BandMateApiService apiService;

    private Application application;

    private MutableLiveData<ArrayList<Profile>> mutableAllProfiles = new MutableLiveData<>();
    private MutableLiveData<List<Profile>> mutableFilteredProfiles = new MutableLiveData<>();


    private MutableLiveData<Profile> mutableUserProfile = new MutableLiveData<>();


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

    public MutableLiveData<Profile> getUserProfile(){

        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        System.out.println("token for get user is : " + token);

        Call<Profile> call = apiService.getUserProfile(token);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                System.out.println("successfully retrieved user's profile" + response.body());

                Profile userProfile = response.body();

                mutableUserProfile.setValue(userProfile);

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println("failed to retrieve user profile" + t.getMessage());
            }
        });

        return mutableUserProfile;

    }

    public void createUserProfile(Profile profile){

        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        Call<Profile> call = apiService.postProfile(token, profile);

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

    public void putProfile(Profile profile){
        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        Call<Profile> call = apiService.putProfile(token, profile);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                System.out.println("successfully put profile");
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println("failed to put profile" + t.getMessage());
            }
        });

    }

    public MutableLiveData<List<Profile>> getFilteredProfiles(String authToken){
        Call<List<Profile>> listOfProfiles = apiService.getFilteredProfiles(authToken);

        listOfProfiles.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                System.out.println("successfully retrieved filtered profiles");
                List<Profile> body = response.body();
                mutableFilteredProfiles.setValue(body);
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                System.out.println("failed to retrieve filtered profiles");
                Log.i("ProfileRepository", t.getMessage());
            }
        });
        return mutableFilteredProfiles;
    }
}
