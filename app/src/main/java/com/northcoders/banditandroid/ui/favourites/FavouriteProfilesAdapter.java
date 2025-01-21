package com.northcoders.banditandroid.ui.favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.FavouriteRecyclerViewBinding;
import com.northcoders.banditandroid.model.Profile;

import java.util.List;

public class FavouriteProfilesAdapter extends RecyclerView.Adapter<FavouriteProfilesAdapter.ProfileViewHolder>{


    private final RecyclerViewInterface recyclerViewInterface;

    List<Profile> profiles;
    Context context;
    FavouritesClickHandler clickHandler;

    public FavouriteProfilesAdapter(RecyclerViewInterface recyclerViewInterface, Context context, List<Profile> profiles, FavouritesClickHandler clickHandler) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.profiles = profiles;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FavouriteRecyclerViewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.favourite_recycler_view,
                parent,
                false);

        return new ProfileViewHolder(binding.getRoot(), binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.binding.setProfile(profile);
//        holder.binding.setFavClickHandler(clickHandler);

        if(profile.getImg_url()!= null){
            Glide.with(context)
                    .load(profile.getImg_url())
                    .into(holder.binding.imageView);
        }

    }

    @Override
    public int getItemCount() {
        if (profiles != null) {
            return profiles.size();
        }
        return 0;
    }


    public static class ProfileViewHolder extends RecyclerView.ViewHolder{
        FavouriteRecyclerViewBinding binding;
        public ProfileViewHolder(@NonNull View itemView, FavouriteRecyclerViewBinding binding, RecyclerViewInterface recyclerView) {
            super(itemView);
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerView != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerView.onItemClick(position);
                            //intent
                        }
                    }
                }
            });
        }
    }
}
