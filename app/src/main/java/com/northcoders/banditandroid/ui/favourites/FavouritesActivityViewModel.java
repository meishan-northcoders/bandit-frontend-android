package com.northcoders.banditandroid.ui.favourites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.model.Favourite;
import com.northcoders.banditandroid.model.FavouriteRepository;
import com.northcoders.banditandroid.model.Profile;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivityViewModel extends AndroidViewModel {

    FavouriteRepository favouriteRepository;
    public FavouritesActivityViewModel(@NonNull Application application) {
        super(application);
        favouriteRepository = new FavouriteRepository(application);
    }

    List<Profile> getUserFavourites(){
        MutableLiveData<List<Profile>> mutableLiveData = favouriteRepository.getFavouriteProfiles();
        return mutableLiveData.getValue();
    }
}
