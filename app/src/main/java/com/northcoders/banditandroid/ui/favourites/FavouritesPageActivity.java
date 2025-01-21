package com.northcoders.banditandroid.ui.favourites;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.model.Profile;

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

        profiles = viewModel.getUserFavourites();

        //profiles.forEach(System.out::println);

        adapter = new FavouriteProfilesAdapter(this, this, profiles, clickHandler);

        displayInRecyclerView();

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


    @Override
    public void onItemClick(int position) {

        System.out.println("item clicked, position is:" + position);
//        Intent intent = new Intent(this,)


/*
*     Intent intent = new Intent(this, UpdateExistingAlbum.class);
    if(filteredAlbums == null || filteredAlbums.isEmpty()){
        intent.putExtra(ALBUM_KEY,albums.get(position));
    }
    else {
        intent.putExtra(ALBUM_KEY,filteredAlbums.get(position));
    }
    this.startActivity(intent);
*/


    }
}