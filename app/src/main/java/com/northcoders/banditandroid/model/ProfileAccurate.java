package com.northcoders.banditandroid.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.util.Set;

public class ProfileAccurate {
        private String profile_id;
        private String username;
        private String img_url;
        private ProfileType profile_type;
        private String description;
        private float lat;
        private float lon;
        private float max_distance;
        private Set<Genre> genres;
        private Set<Instrument> instruments;


    public ProfileAccurate(String profile_id, String username, String img_url, ProfileType profile_type, String description, float lat, float lon, float max_distance, Set<Genre> genres, Set<Instrument> instruments) {
        this.profile_id = profile_id;
        this.username = username;
        this.img_url = img_url;
        this.profile_type = profile_type;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.max_distance = max_distance;
        this.genres = genres;
        this.instruments = instruments;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public ProfileType getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(ProfileType profile_type) {
        this.profile_type = profile_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(float max_distance) {
        this.max_distance = max_distance;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    //this is essential for getting correspondent image into MessagesActivity - NB will need to change MessageActivity layout Binding (data variable type) and Binding in the OnCreate method to reflect new Profile class name i.e. it probably wont still be ProfileAccurate...
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
