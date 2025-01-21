package com.northcoders.banditandroid.ui.favourites;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.ui.updateprofile.UpdateProfileActivity;

public class FavouritesClickHandler {

    Context context;

    public FavouritesClickHandler(Context context) {
        this.context = context;
    }

    public void onCardClick(Profile profile){

        //replace with Sam's class
        Intent intent = new Intent(context, UpdateProfileActivity.class);
        Gson converter = new Gson();
        String profileString = converter.toJson(profile);
        intent.putExtra("PROFILE", profileString);
        context.startActivity(intent);
    }
}
