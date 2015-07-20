package com.algomized.android.testopendata.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sky on 14/7/2015.
 */
public class Meta {
    public static final String FIELD_CODE = "code";
    public static final String FIELD_MESSAGE = "message";

    @SerializedName(FIELD_CODE)
    private int code;
    @SerializedName(FIELD_MESSAGE)
    @Nullable
    private ErrorMessage message;

    public int getCode() {
        return code;
    }

    @Nullable
    public ErrorMessage getMessage() {
        return message;
    }
}
