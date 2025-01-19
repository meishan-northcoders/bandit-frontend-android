package com.northcoders.banditandroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityMatchingProfileDetailsBinding;
import com.northcoders.banditandroid.model.Profile;

import java.util.List;


public class ProfileMatchAdapter extends RecyclerView.Adapter<ProfileMatchAdapter.ProfileMatchDetailViewHolder> {
    private List<Profile> profileList;
    private Context context;

    // Constructor for the adapter
    public ProfileMatchAdapter(List<Profile> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileMatchDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout using DataBindingUtil
        ActivityMatchingProfileDetailsBinding bindedView = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_matching_profile_details,
                parent,
                false
        );
        return new ProfileMatchDetailViewHolder(bindedView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMatchDetailViewHolder holder, int position) {
        // Bind data to views using the binding object
        Profile profile = profileList.get(position);
        holder.activityMatchingProfileDetailsBinding.cardTitle.setText("test name");
        holder.activityMatchingProfileDetailsBinding.cardDescription.setText(profile.getDescription());
        // Example: Loading image using Glide or similar library
        Glide.with(context)
                .load(profile.getImg_url())
                .into(holder.activityMatchingProfileDetailsBinding.cardImage);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    // ViewHolder class
    public static class ProfileMatchDetailViewHolder extends RecyclerView.ViewHolder {
        private final ActivityMatchingProfileDetailsBinding activityMatchingProfileDetailsBinding;
        public ProfileMatchDetailViewHolder(@NonNull ActivityMatchingProfileDetailsBinding binding) {
            super(binding.getRoot());
            this.activityMatchingProfileDetailsBinding = binding; // Save the binding instance
        }
    }
}






