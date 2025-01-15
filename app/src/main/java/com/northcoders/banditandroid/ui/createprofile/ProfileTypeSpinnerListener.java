package com.northcoders.banditandroid.ui.createprofile;

import static java.security.AccessController.getContext;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.banditandroid.MainActivity;
import com.northcoders.banditandroid.model.ProfileType;

public class ProfileTypeSpinnerListener implements Spinner.OnItemSelectedListener {

    CreateProfileActivity activity;

    public ProfileTypeSpinnerListener(CreateProfileActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(position){
            case 0: //band
                activity.userProfile.setProfile_type(ProfileType.BAND);
                break;

            case 1: //musician
                activity.userProfile.setProfile_type(ProfileType.MUSICIAN);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
