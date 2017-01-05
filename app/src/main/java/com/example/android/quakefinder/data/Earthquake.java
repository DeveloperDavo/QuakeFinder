package com.example.android.quakefinder.data;

/**
 * Created by David on 04/01/2017.
 * <p>
 * Contains the necessary data, which is displayed in the main activity. This could be replaced with
 * a database helper class in the future.
 */

public final class Earthquake {

    private double mag;
    private String place;
    private String url;

    public Earthquake(double mag, String place, String url) {
        this.mag = mag;
        this.place = place;
        this.url = url;
    }

    public double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

    public String getUrl() {
        return url;
    }

}
