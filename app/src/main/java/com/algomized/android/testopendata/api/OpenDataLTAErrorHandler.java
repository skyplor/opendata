package com.algomized.android.testopendata.api;

import com.algomized.android.testopendata.model.ErrorMessage;
import com.algomized.android.testopendata.model.Meta;

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
            if (response != null) {
                Meta meta = response.getError();
                if (meta != null) {
                    ErrorMessage errorMessage = meta.getMessage();
                    if (errorMessage.getValue() != null)
                        return new OpenDataLTAException(errorMessage.getValue(), cause);
                }
            }
        }
        return cause;
    }
}
