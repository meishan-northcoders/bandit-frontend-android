package com.northcoders.banditandroid.ui.updateprofile;

import android.content.Context;
import android.view.View;

import com.northcoders.banditandroid.model.Profile;

public class UpdateProfileClickHandler {
    Profile profile;

    Context context;

    UpdateProfileViewModel viewModel;


    public UpdateProfileClickHandler() {
    }

    public UpdateProfileClickHandler(Profile profile, Context context, UpdateProfileViewModel viewModel) {
        this.profile = profile;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void submitBtnClick(View view){

        System.out.println("submit button clicked!");

        viewModel.putUserProfile(profile);

        //TODO intent to move to previous screen or matching activity
//        Intent intent = new Intent();
//        context.startActivity(intent);

    }
}
