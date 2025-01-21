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
import com.northcoders.banditandroid.model.Genre;
import com.northcoders.banditandroid.model.Instrument;
import com.northcoders.banditandroid.model.Profile;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;


public class ProfileMatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Profile> profileList;
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    // Constructor for the adapter
    public ProfileMatchAdapter(List<Profile> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout using DataBindingUtil
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_no_more_items, parent, false);
            return new FooterViewHolder(view);
        }
        ActivityMatchingProfileDetailsBinding bindedView = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_matching_profile_details,
                parent,
                false
        );
        return new ProfileMatchDetailViewHolder(bindedView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Bind data to views using the binding object
        if(position == profileList.size()){
            return;
        }
        ProfileMatchDetailViewHolder viewHolder = (ProfileMatchDetailViewHolder) holder;
        Profile profile = profileList.get(position);
        viewHolder.activityMatchingProfileDetailsBinding.cardTitle.setText(profile.getProfile_name());
        viewHolder.activityMatchingProfileDetailsBinding.cardDescription.setText(profile.getDescription());
        //viewHolder.activityMatchingProfileDetailsBinding.cardImage.setImageResource(profile.getImg_url());
        String generesJoinedByComma = profile.getGenres().stream().map(Genre::getGenre).collect(Collectors.joining(","));
        String instrumentsJoinedByComma = profile.getInstruments().stream().map(Instrument::getInstrument).collect(Collectors.joining(","));
        viewHolder.activityMatchingProfileDetailsBinding.cardGenre.setText(generesJoinedByComma);
        viewHolder.activityMatchingProfileDetailsBinding.cardInstrument.setText(instrumentsJoinedByComma);
        viewHolder.activityMatchingProfileDetailsBinding.cardDescription.setText(profile.getDescription());
        viewHolder.activityMatchingProfileDetailsBinding.cardCity.setText(profile.getCity());
        viewHolder.activityMatchingProfileDetailsBinding.cardCountry.setText(profile.getCountry());

        Glide.with(context)
                .load(profile.getImg_url())
                .into(viewHolder.activityMatchingProfileDetailsBinding.cardImage);
    }

    @Override
    public int getItemCount() {
        if(profileList != null){
            return profileList.size()+1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == profileList.size()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    // ViewHolder class
    public static class ProfileMatchDetailViewHolder extends RecyclerView.ViewHolder {
        private final ActivityMatchingProfileDetailsBinding activityMatchingProfileDetailsBinding;
        public ProfileMatchDetailViewHolder(@NonNull ActivityMatchingProfileDetailsBinding binding) {
            super(binding.getRoot());
            this.activityMatchingProfileDetailsBinding = binding; // Save the binding instance
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}






