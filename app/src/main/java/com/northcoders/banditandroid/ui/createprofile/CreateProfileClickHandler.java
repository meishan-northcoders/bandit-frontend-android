package com.northcoders.banditandroid.ui.createprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.helper.LogoutHandler;
import com.northcoders.banditandroid.model.Profile;

public class CreateProfileClickHandler {
    Profile profile;

    Context context;

    CreateProfileViewModel viewModel;


    public CreateProfileClickHandler() {
    }

    public CreateProfileClickHandler(Profile profile, Context context, CreateProfileViewModel viewModel) {
        this.profile = profile;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void submitBtnClick(View view){

        System.out.println("submit button clicked!");

        viewModel.createUserProfile(profile);

        //TODO intent to move to matching activity
//        Intent intent = new Intent();
//        context.startActivity(intent);

    }

    public void onLogoutBtnClick(View view){
        LogoutHandler.getInstance().logout((CreateProfileActivity) context);
    }



    public void onSelectProfileTypeSpinnerChange(){



    }
}
