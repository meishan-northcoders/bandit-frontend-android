package com.northcoders.banditandroid.model;

import java.util.Set;

public class Genre {
    private String genre;
    private Set<ProfileAccurate> profiles;

    public Genre(String genre, Set<ProfileAccurate> profiles) {
        this.genre = genre;
        this.profiles = profiles;
    }

    public Genre() {
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<ProfileAccurate> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<ProfileAccurate> profiles) {
        this.profiles = profiles;
    }
}
