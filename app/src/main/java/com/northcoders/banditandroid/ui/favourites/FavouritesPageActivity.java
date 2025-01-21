package com.northcoders.banditandroid.ui.favourites;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.ui.messsage.MessageActivity;

import java.util.List;

public class FavouritesPageActivity extends AppCompatActivity implements RecyclerViewInterface {

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

        getProfiles();



    }

    public void displayInRecyclerView(){
        if (recyclerView == null){
            recyclerView = findViewById(R.id.favRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            adapter = new FavouriteProfilesAdapter(this, this,
                    profiles, clickHandler);
            recyclerView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {

        System.out.println("item clicked, position is:" + position);

        Intent intent = new Intent(this, MessageActivity.class);

        Gson converter = new Gson();

        String profileStr =  converter.toJson(profiles.get(position));
        intent.putExtra("PROFILE", profileStr);
        this.startActivity(intent);

    }

    private void getProfiles(){

        viewModel.getUserFavourites().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {

                FavouritesPageActivity.this.profiles = profiles;

                displayInRecyclerView();
            }
        });
    }


}