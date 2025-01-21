package com.northcoders.banditandroid.ui.favourites;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.model.Profile;

import java.util.List;

public class activity_favourites_page extends AppCompatActivity {

    List<Profile> profiles;
    FavouritesActivityViewModel viewModel;
    FavouritesClickHandler clickHandler;
    FavouriteProfilesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourites_page);
        clickHandler = new FavouritesClickHandler(this);
        viewModel = new FavouritesActivityViewModel(getApplication());
        profiles = viewModel.getUserFavourites();
        profiles.forEach(System.out::println);
        adapter = new FavouriteProfilesAdapter(this, profiles, clickHandler);
        recyclerView = new RecyclerView(this);
    }

    public void displayInRecyclerView(){
        if (recyclerView == null){
            recyclerView = findViewById(R.id.favRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }
}