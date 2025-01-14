package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;

public class Instrument extends BaseObservable {

    String instrument;


    public Instrument() {
    }

    public Instrument(String instrument) {
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
