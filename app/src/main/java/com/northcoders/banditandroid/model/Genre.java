package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;

public class Genre extends BaseObservable {

    String genre;


    public Genre() {
    }

    public Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
