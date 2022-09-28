package com.oracle.interview.evote.domain;

public class Voter {
    int id;
    String preference;

    public Voter(int id, String preference) {
        this.id = id;
        this.preference = preference;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
