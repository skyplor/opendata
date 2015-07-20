package com.algomized.android.testopendata.api;


import com.algomized.android.testopendata.R;
import com.algomized.android.testopendata.TestOpenDataApplication_;

import retrofit.RequestInterceptor;

/**
 * Created by Sky on 14/7/2015.
 */
public class OpenDataLTARequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("AccountKey", TestOpenDataApplication_.getContext().getResources().getString(R.string.accountkey));
        request.addHeader("UniqueUserId", TestOpenDataApplication_.getContext().getResources().getString(R.string.uniqueuserid));
        request.addHeader("accept", "application/json");
    }
}