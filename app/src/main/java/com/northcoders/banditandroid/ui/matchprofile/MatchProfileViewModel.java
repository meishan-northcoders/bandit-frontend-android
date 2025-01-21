package com.northcoders.banditandroid.ui.matchprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.model.Favourite;
import com.northcoders.banditandroid.model.MatchesRepository;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class MatchProfileViewModel extends AndroidViewModel {
    ProfileRepository profileRepository;
    MatchesRepository matchesRepository;
    public MatchProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        matchesRepository = new MatchesRepository(application);
    }

    public LiveData<List<Profile>> getFilteredProfiles(){
        String token = SharedPreferenceHelper.getInstance(getApplication().getApplicationContext()).getString("token", null);
        System.out.printf("printing token"+ token);
        return profileRepository.getFilteredProfiles(token);
    }

    public void addFavouriteProfile(Favourite favourite){
         matchesRepository.postFavourite(favourite);
    }

}
