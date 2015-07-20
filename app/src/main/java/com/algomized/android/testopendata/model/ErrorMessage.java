package com.algomized.android.testopendata.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sky on 21/7/2015.
 */
public class ErrorMessage {

    public static final String FIELD_LANG = "Lang";
    public static final String FIELD_VALUE = "Value";


    @NonNull
    @SerializedName(FIELD_LANG)
    private String lang;

    @NonNull
    @SerializedName(FIELD_VALUE)
    private String value;

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getLang() {
        return lang;
    }

    public void setLang(@NonNull String lang) {
        this.lang = lang;
    }
}
