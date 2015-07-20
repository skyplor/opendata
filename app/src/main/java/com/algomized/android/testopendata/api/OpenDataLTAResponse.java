package com.algomized.android.testopendata.api;

import android.support.annotation.Nullable;

import com.algomized.android.testopendata.model.LTAService;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sky on 14/7/2015.
 */
public class OpenDataLTAResponse {
    public static final String FIELD_RESPONSE = "Services";
    public static final String FIELD_META = "meta";

    //    @SerializedName(FIELD_META)
//    @NonNull
//    private Meta meta;
    @SerializedName(FIELD_RESPONSE)
    @Nullable
    private List<LTAService> LTAServices;

//    @NonNull
//    public Meta getMeta() {
//        return meta;
//    }

    public List<LTAService> getLTAServices() {
        return LTAServices;
    }
}