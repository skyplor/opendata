package com.algomized.android.testopendata.api;

import android.content.Context;

import com.algomized.android.testopendata.BuildConfig;
import com.algomized.android.testopendata.R;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Sky on 14/7/2015.
 */
public interface OpenDataLTAService {

    // Synchronized
    @GET("/BusArrival")
    OpenDataLTAResponse busArrivals(@Query(value = "$skip") int skip, @Query(value = "BusStopID") String busStopID, @Query(value = "ServiceNo") String serviceNo) throws OpenDataLTAException;

    //Non-blocking, asynchronous
    @GET("/BusArrival")
    void busArrivals(@Query(value = "$skip") int skip, @Query(value = "BusStopID") String busStopID, @Query(value = "ServiceNo") String serviceNo, Callback<OpenDataLTAResponse> callback);


    class Implementation {
        static Context mContext;

        public static OpenDataLTAService get(Context context) {
            mContext = context;
            return getBuilder()
                    .build()
                    .create(OpenDataLTAService.class);
        }

        static RestAdapter.Builder getBuilder() {
            // Allows GSON with Realm to go hand-in-hand
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            return new RestAdapter.Builder()
                    .setEndpoint(mContext.getResources().getString(R.string.lta_opendata_baseendpoint2))
                    .setRequestInterceptor(new OpenDataLTARequestInterceptor())
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setErrorHandler(new OpenDataLTAErrorHandler());
        }
    }
}
