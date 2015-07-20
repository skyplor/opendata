package com.algomized.android.testopendata.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sky on 20/7/2015.
 */
public class LTAService {
    public static final String FIELD_SERVICENO = "ServiceNo";
    public static final String FIELD_STATUS = "Status";
    public static final String FIELD_OPERATOR = "Operator";
    public static final String FIELD_NEXTBUS = "NextBus";
    public static final String FIELD_SUBSEQUENTBUS = "SubsequentBus";

    @NonNull
    @SerializedName(FIELD_SERVICENO)
    private String ServiceNo;
    @NonNull
    @SerializedName(FIELD_STATUS)
    private String Status;

    @NonNull
    @SerializedName(FIELD_OPERATOR)
    private String Operator;
    @NonNull
    @SerializedName(FIELD_NEXTBUS)
    private Bus NextBus;
    @NonNull
    @SerializedName(FIELD_SUBSEQUENTBUS)
    private Bus SubsequentBus;

    @NonNull
    public String getServiceNo() {
        return ServiceNo;
    }

    public void setServiceNo(@NonNull String serviceNo) {
        ServiceNo = serviceNo;
    }

    @NonNull
    public String getStatus() {
        return Status;
    }

    public void setStatus(@NonNull String status) {
        Status = status;
    }

    @NonNull
    public String getOperator() {
        return Operator;
    }

    public void setOperator(@NonNull String operator) {
        Operator = operator;
    }

    @NonNull
    public Bus getNextBus() {
        return NextBus;
    }

    public void setNextBus(@NonNull Bus nextBus) {
        NextBus = nextBus;
    }

    @NonNull
    public Bus getSubsequentBus() {
        return SubsequentBus;
    }

    public void setSubsequentBus(@NonNull Bus subsequentBus) {
        SubsequentBus = subsequentBus;
    }

    public String toString() {
        return "Service Number: " + ServiceNo + "\n"
                + "Status: " + Status + "\n"
                + "Operator: " + Operator + "\n"
                + "Next Bus: " + NextBus.toString() + "\n"
                + "Subsequent Bus: " + SubsequentBus.toString();
    }
}
