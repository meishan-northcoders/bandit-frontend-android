package com.northcoders.banditandroid.ui.matchprofile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityMatchingProfilesBinding;
import com.northcoders.banditandroid.model.Favourite;
import com.northcoders.banditandroid.model.Genre;
import com.northcoders.banditandroid.model.Instrument;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileType;
import com.northcoders.banditandroid.ui.ProfileMatchAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MatchingProfilesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfileMatchAdapter profileMatchAdapter;
    private List<Profile> profileList;
    private ActivityMatchingProfilesBinding activityMatchingProfilesBinding;
    private MatchProfileViewModel matchProfileViewModel;
    ImageView animationOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_matching_profiles);
//        recyclerView = findViewById(R.id.matches_recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMatchingProfilesBinding = DataBindingUtil.setContentView(this, R.layout.activity_matching_profiles);
        matchProfileViewModel = new ViewModelProvider(this).get(MatchProfileViewModel.class);
        getFilteredMatches();
    }

    private void getFilteredMatches() {
        matchProfileViewModel.getFilteredProfiles().observe(this, profiles -> {
            profileList = profiles;
            displayInRecyclerView();
        });
    }

    private void displayInRecyclerView(){
        recyclerView = activityMatchingProfilesBinding.matchesRecyclerView;
        profileMatchAdapter = new ProfileMatchAdapter(profileList, this);//class is implementing recyclerViewInterface
        recyclerView.setAdapter(profileMatchAdapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        profileMatchAdapter.notifyDataSetChanged();
        // Add swipe gestures
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                animationOverlay = activityMatchingProfilesBinding.animationOverlay;
                animationOverlay.setVisibility(View.VISIBLE);
                int position = viewHolder.getAdapterPosition();
                Profile profile = profileList.get(position);
                profileList.remove(position);

                profileMatchAdapter.notifyItemRemoved(position);
                if (direction == ItemTouchHelper.LEFT) {
                    animationOverlay.setImageResource(R.drawable.sad);
                    // Handle left swipe (e.g., Dislike)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Handle right swipe (e.g., Like)

                    Favourite favourite = new Favourite();
                    favourite.setYrFavProfileId(profile.getProfile_id());
                    matchProfileViewModel.addFavouriteProfile(favourite);
                    System.out.println(profile.getProfile_id()+ " " + profile.getProfile_name());
                    animationOverlay.setImageResource(R.drawable.fireworks);
                }
                animateOverlay(animationOverlay);
            }
        };
        // Attach the ItemTouchHelper to the RecyclerView
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void animateOverlay(ImageView overlay) {
        overlay.animate()
                .alpha(1f)
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(500)
                .withEndAction(() -> overlay.setVisibility(View.GONE))
                .start();
    }
}
