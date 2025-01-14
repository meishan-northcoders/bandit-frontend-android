package com.northcoders.banditandroid.ui.createprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;
import com.northcoders.banditandroid.service.BandMateApiService;

public class CreateProfileViewModel extends AndroidViewModel {

    ProfileRepository profileRepository;
    public CreateProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Profile> getUserProfile(){
        return profileRepository.getMutableLiveDataProfile();
    }

    public void createUserProfile(Profile profile){
        profileRepository.createUserProfile(profile);
    }
}
