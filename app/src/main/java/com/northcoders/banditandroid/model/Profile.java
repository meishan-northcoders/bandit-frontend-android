package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.northcoders.banditandroid.BR;

import java.util.HashSet;
import java.util.Set;

public class Profile extends BaseObservable {

    String profile_id;
    String profile_name;

    private String img_url;

    private ProfileType profile_type;

    private String description;

    private String city;

    private String country;

    private String searchQuery; //analysed by openAi in backend

    private float lat;

    private float lon;

    private float max_distance;

    private Set<Genre> genres;

    private Set<Instrument> instruments;


    public Profile() {
    }

    public Profile(String profile_id, String profile_name,String img_url, ProfileType profile_type, String description, String city, String country, String searchQuery, float lat, float lon, float max_distance, Set<Genre> genres, Set<Instrument> instruments) {
        this.profile_id = profile_id;
        this.profile_name = profile_name;
        this.img_url = img_url;
        this.profile_type = profile_type;
        this.description = description;
        this.city = city;
        this.country = country;
        this.searchQuery = searchQuery;
        this.lat = lat;
        this.lon = lon;
        this.max_distance = max_distance;
        this.genres = genres;
        this.instruments = instruments;
    }


    @Bindable
    public String getProfile_id() {
        return profile_id;
    }

    @Bindable
    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
        this.notifyPropertyChanged(BR.profile_id);
    }

    @Bindable
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
        this.notifyPropertyChanged(BR.img_url);
    }

    @Bindable
    public ProfileType getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(ProfileType profile_type) {
        this.profile_type = profile_type;
        this.notifyPropertyChanged(BR.profile_type);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
    }

    @Bindable
    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
        this.notifyPropertyChanged(BR.lat);
    }

    @Bindable
    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
        this.notifyPropertyChanged(BR.lon);
    }

    @Bindable
    public float getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(float max_distance) {
        this.max_distance = max_distance;
        this.notifyPropertyChanged(BR.max_distance);
    }

    @Bindable
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
        this.notifyPropertyChanged(BR.genres);
    }

    public void addGenre(String genre){

        if(genres!= null){
            genres.add(new Genre(genre)); //creates new genre
        }
        else{
            genres = new HashSet<>();
            genres.add(new Genre(genre));
        }


    }

    @Bindable
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
        this.notifyPropertyChanged(BR.instruments);
    }

    public void addInstrument(String instrument){

        if(instruments!=null){
            instruments.add(new Instrument(instrument));
        }
        else{
            instruments = new HashSet<>();
            instruments.add(new Instrument(instrument));
        }


    }

    @Bindable
    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        this.notifyPropertyChanged(BR.searchQuery);
    }

    public boolean checkAttributesNotNull(){
        boolean result = true;

        if(profile_type == null){
            result = false;
        }
        if(description == null || description.isEmpty()){
            result = false;
        }
        if(genres == null || genres.isEmpty()){
            result = false;
        }
        if(instruments == null || instruments.isEmpty()){
            result = false;
        }
        if(city == null){
            result = false;
        }
        if(country == null){
            result = false;
        }
        return result;

    }

    @Override
    public String toString() {
        return "Profile{" +
                "profile_id='" + profile_id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", profile_type=" + profile_type +
                ", description='" + description + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", max_distance=" + max_distance +
                ", genres=" + genres +
                ", instruments=" + instruments +
                '}';
    }

    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.notifyPropertyChanged(BR.city);
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        this.notifyPropertyChanged(BR.country);
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
        this.notifyPropertyChanged(BR.profile_name);
    }

}
