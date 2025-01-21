package com.northcoders.banditandroid.ui.createprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.northcoders.banditandroid.helper.LogoutHandler;
import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.ui.matchprofile.MatchingProfilesActivity;

public class CreateProfileClickHandler {

    private static final String TAG = "CreateProfileClickHandler";


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
        Toast.makeText(view.getContext(), "Profile created successfully", Toast.LENGTH_LONG).show();
        // intent to move to matching activity

    }

    public void onHomBtnClick(View view){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Call the moveToMatchesPage method
            currentUser.getIdToken(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Token retrieved successfully");
                    Intent matchProfile = new Intent(view.getContext(), MatchingProfilesActivity.class);
                    matchProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    view.getContext().startActivity(matchProfile);

                    SharedPreferenceHelper.getInstance(view.getContext()).putString("token", task.getResult().getToken());
                }
                else {
                    Log.e(TAG, "Token retrieval failed", task.getException());
                }
            });
        }
        else {
            Log.e(TAG, "No authenticated user found.");
        }
        Intent intent = new Intent(view.getContext(), MatchingProfilesActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onLogoutBtnClick(View view){
        LogoutHandler.getInstance().logout((CreateProfileActivity) context);
    }

    public void onSelectProfileTypeSpinnerChange(){



    }
}
