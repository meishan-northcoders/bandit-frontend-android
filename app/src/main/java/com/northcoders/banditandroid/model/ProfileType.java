package com.northcoders.banditandroid.model;

import androidx.annotation.NonNull;

public enum ProfileType {
    BAND("Band"),
    MUSICIAN("Musician");

    private String prettyName;

    private ProfileType(String prettyName){
        this.prettyName = prettyName;
    }

    @NonNull
    @Override
    public String toString(){
        return prettyName;
    }
}

