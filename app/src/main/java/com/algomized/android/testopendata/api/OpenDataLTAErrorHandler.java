package com.algomized.android.testopendata.api;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Sky on 14/7/2015.
 */
public class OpenDataLTAErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause.getResponse() != null && cause.getSuccessType() == OpenDataLTAResponse.class) {
            OpenDataLTAResponse response = (OpenDataLTAResponse) cause.getBody();
            if (response.getMeta().getErrorDetail() != null) {
                return new OpenDataLTAException(response.getMeta().getErrorDetail(), cause);
            }
        }
        return cause;
    }
}
