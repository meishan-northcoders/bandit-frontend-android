package com.northcoders.banditandroid.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

public class Instrument extends BaseObservable implements Parcelable {

    String instrument;


    public Instrument() {
    }

    public Instrument(String instrument) {
        this.instrument = instrument;
    }

    protected Instrument(Parcel in) {
        instrument = in.readString();
    }

    public static final Creator<Instrument> CREATOR = new Creator<Instrument>() {
        @Override
        public Instrument createFromParcel(Parcel in) {
            return new Instrument(in);
        }

        @Override
        public Instrument[] newArray(int size) {
            return new Instrument[size];
        }
    };


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(instrument);

    }
}
