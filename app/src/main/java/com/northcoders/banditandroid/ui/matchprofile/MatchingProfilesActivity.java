package com.northcoders.banditandroid.ui.matchprofile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matching_profiles);

        recyclerView = findViewById(R.id.matches_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mock data
        profileList = new ArrayList<>();

        HashSet<Genre> genres = new HashSet<>();
        genres.add(new Genre("Rock"));
        HashSet<Instrument> instruments = new HashSet<>();
        instruments.add(new Instrument("Guitar"));
        //(String profile_id, String img_url, ProfileType profile_type,
                //String description, float lat, float lon, float max_distance, Set<Genre> genres, Set<Instrument> instruments
        profileList.add(new Profile("344",
                "https://lh3.googleusercontent.com/a/ACg8ocKF4RuOneJ-H-LD3N4Y63PKapk1ReOf92qbz1Cbb9lglmI06g=s96-c",
                ProfileType.MUSICIAN, // <- Add this comma
                "Energetic and passionate guitarist looking to perform in high metal genre band",
                53.4f,
                -2.2f,
                50.0f,
                genres,
                instruments));
        profileList.add(new Profile("354",
                "https://lh3.googleusercontent.com/a/ACg8ocKF4RuOneJ-H-LD3N4Y63PKapk1ReOf92qbz1Cbb9lglmI06g=s96-c",
                ProfileType.MUSICIAN, // <- Add this comma
                "Dynamic and passionate Piano artist looking to perform in high metal genre band",
                53.4f,
                -2.2f,
                50.0f,
                genres,
                instruments));


        // Set up adapter
        profileMatchAdapter = new ProfileMatchAdapter(profileList, this);
        recyclerView.setAdapter(profileMatchAdapter);

        // Add swipe gestures
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                profileList.remove(position);
                profileMatchAdapter.notifyItemRemoved(position);
                if (direction == ItemTouchHelper.LEFT) {
                    // Handle left swipe (e.g., Dislike)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Handle right swipe (e.g., Like)
                }
            }
        };


        // Attach the ItemTouchHelper to the RecyclerView
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
}
