package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.List;

public class Favourite extends BaseObservable {

    Long favId;
    String favProfileId;
    String yrFavProfileId;
    String isLikedOrDisliked;
    Profile profile;


    public Favourite() {
    }

    public Favourite(Long favId, String favProfileId, String yrFavProfileId, String isLikedOrDisliked, Profile profile) {
        this.favId = favId;
        this.favProfileId = favProfileId;
        this.yrFavProfileId = yrFavProfileId;
        this.isLikedOrDisliked = isLikedOrDisliked;
        this.profile = profile;
    }


    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
    }

    public String getFavProfileId() {
        return favProfileId;
    }

    public void setFavProfileId(String favProfileId) {
        this.favProfileId = favProfileId;
    }


    public String getYrFavProfileId() {
        return yrFavProfileId;
    }

    public void setYrFavProfileId(String yrFavProfileId) {
        this.yrFavProfileId = yrFavProfileId;
    }

    public String getIsLikedOrDisliked() {
        return isLikedOrDisliked;
    }

    public void setIsLikedOrDisliked(String isLikedOrDisliked) {
        this.isLikedOrDisliked = isLikedOrDisliked;
    }

    @Bindable
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "favId=" + favId +
                ", favProfileId='" + favProfileId + '\'' +
                ", yrFavProfileId='" + yrFavProfileId + '\'' +
                ", isLikedOrDisliked='" + isLikedOrDisliked + '\'' +
                ", profile=" + profile +
                '}';
    }
}
