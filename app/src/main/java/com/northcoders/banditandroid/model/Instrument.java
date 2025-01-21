package com.northcoders.banditandroid.model;

import java.util.Set;

public class Instrument {
    private String instrument;

    private Set<ProfileAccurate> profiles;

    public Instrument(String instrument, Set<ProfileAccurate> profiles) {
        this.instrument = instrument;
        this.profiles = profiles;
    }

    public Instrument() {
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Set<ProfileAccurate> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<ProfileAccurate> profiles) {
        this.profiles = profiles;
    }
}
