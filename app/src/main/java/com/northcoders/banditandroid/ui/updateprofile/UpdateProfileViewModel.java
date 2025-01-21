package com.northcoders.banditandroid.ui.updateprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;

import java.util.ArrayList;

public class UpdateProfileViewModel extends AndroidViewModel {

    ProfileRepository profileRepository;
    public UpdateProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
    }

    public LiveData<Profile> getUserProfile(){
        return profileRepository.getUserProfile();
    }

    public void putUserProfile(Profile profile){
        System.out.println("profile id = " + profile.getProfile_id());
        profileRepository.putProfile(profile);
    }
}
