package com.northcoders.banditandroid.ui.matchprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;

import java.io.Closeable;
import java.util.ArrayList;

public class MatchProfileViewModel extends ViewModel {
    ProfileRepository profileRepository;
    public MatchProfileViewModel(@NonNull Application application) {
        super((Closeable) application);
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
