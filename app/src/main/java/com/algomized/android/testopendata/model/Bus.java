package com.algomized.android.testopendata.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sky on 20/7/2015.
 */
public class Bus {
    public static final String FIELD_ESIMATEDARRIVAL = "EstimatedArrival";
    public static final String FIELD_LOAD = "Load";
    public static final String FIELD_FEATURE = "Feature";

    @NonNull
    @SerializedName(FIELD_ESIMATEDARRIVAL)
    private String EstimatedArrival;
    @NonNull
    @SerializedName(FIELD_LOAD)
    private String Load;

    @NonNull
    @SerializedName(FIELD_FEATURE)
    private String Feature;

    @NonNull
    public String getFeature() {
        return Feature;
    }

    public void setFeature(@NonNull String feature) {
        Feature = feature;
    }

    @NonNull
    public String getLoad() {
        return Load;
    }

    public void setLoad(@NonNull String load) {
        Load = load;
    }

    @NonNull
    public String getEstimatedArrival() {
        return EstimatedArrival;
    }

    public void setEstimatedArrival(@NonNull String estimatedArrival) {
        EstimatedArrival = estimatedArrival;
    }

    public String toString() {
        return "Estimated Arrival: " + EstimatedArrival + "\n"
                + "Load: " + Load + "\n"
                + "Feature: " + Feature;
    }
}
