package com.northcoders.banditandroid.ui.navigation;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.helper.LogoutHandler;
import com.northcoders.banditandroid.ui.createprofile.CreateProfileActivity;
import com.northcoders.banditandroid.ui.favourites.FavouritesActivityViewModel;
import com.northcoders.banditandroid.ui.favourites.FavouritesPageActivity;
import com.northcoders.banditandroid.ui.matchprofile.MatchingProfilesActivity;
import com.northcoders.banditandroid.ui.updateprofile.UpdateProfileActivity;

public class NavigationButtonsFragment extends Fragment {
    private static final String TAG = "NavigationButtonsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_buttons, container, false);

        ImageButton btnProfile = view.findViewById(R.id.btn_profile);
        ImageButton btnLike = view.findViewById(R.id.btn_like);
        ImageButton btnFavorite = view.findViewById(R.id.btn_favorite);
        ImageButton btnLogout = view.findViewById(R.id.bt_logout);

        btnProfile.setOnClickListener(v -> onProfileClick());
        btnLike.setOnClickListener(v -> onLikeClick());
        btnFavorite.setOnClickListener(v -> onFavoriteClick());
        btnLogout.setOnClickListener(v -> onLogoutClick());

        return view;
    }

    private void onProfileClick() {
        // Handle Profile button click
        Log.d(TAG, "onProfileClick: Profile button clicked");
        Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
        startActivity(intent);
    }

    private void onLikeClick() {
        // Handle Like button click

        Intent intent = new Intent(getActivity(), MatchingProfilesActivity.class);
        startActivity(intent);

        Log.d(TAG, "onLikeClick: Profile button clicked");

    }

    private void onFavoriteClick() {
        // Handle Favorite button click
        Intent intent = new Intent(getActivity(), FavouritesPageActivity.class);
        startActivity(intent);


        Log.d(TAG, "onFavoriteClick: Profile button clicked");

    }

    private void onLogoutClick() {
        // Handle Logout button click
        Log.d(TAG, "onLogoutClick: Profile button clicked");
        LogoutHandler.getInstance().logout(getActivity());


    }
}
