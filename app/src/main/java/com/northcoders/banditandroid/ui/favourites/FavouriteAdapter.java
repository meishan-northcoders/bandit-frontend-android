package com.northcoders.banditandroid.ui.favourites;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.model.Favourite;

public class FavouriteAdapter extends RecyclerView.Adapter<Favourite.FavouriteViewHolder>{

    @NonNull
    @Override
    public Favourite.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Favourite.FavouriteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{
        FavouriteRecyclerViewBinding
    }
}
