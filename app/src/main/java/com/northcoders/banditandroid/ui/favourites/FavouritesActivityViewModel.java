package com.northcoders.banditandroid.ui.favourites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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

    MutableLiveData<List<Profile>> getUserFavourites(){
        return favouriteRepository.getFavouriteProfiles();
    }
}
