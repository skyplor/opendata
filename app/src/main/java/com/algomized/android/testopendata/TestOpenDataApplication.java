package com.algomized.android.testopendata;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EApplication;

import timber.log.Timber;

/**
 * Created by Sky on 20/7/2015.
 */
@EApplication
public class TestOpenDataApplication extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
