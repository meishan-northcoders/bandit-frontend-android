package com.northcoders.banditandroid.ui.createprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;

import java.util.ArrayList;

public class CreateProfileViewModel extends AndroidViewModel {

    ProfileRepository profileRepository;
    public CreateProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
    }

    public LiveData<ArrayList<Profile>> getAllProfiles(){
        return profileRepository.getMutableAllProfiles();
    }

    public void createUserProfile(Profile profile){
        System.out.println("profile is currently: " + profile.toString());
        profileRepository.createUserProfile(profile);
    }
}
