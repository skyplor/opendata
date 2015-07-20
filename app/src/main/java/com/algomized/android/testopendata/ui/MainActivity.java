package com.algomized.android.testopendata.ui;

import android.app.Activity;

import com.algomized.android.testopendata.R;
import com.algomized.android.testopendata.api.OpenDataLTAResponse;
import com.algomized.android.testopendata.api.OpenDataLTAService;
import com.algomized.android.testopendata.model.LTAService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Click
    void getBusArrivals() {
        getOpenDataLTAService(0, "14141", null);
    }

    @Background
    public void getOpenDataLTAService(int skip, String busStopID, String serviceNo) {
        OpenDataLTAService.Implementation.get(this).busArrivals(skip, busStopID, serviceNo, new Callback<OpenDataLTAResponse>() {
            @Override
            public void success(OpenDataLTAResponse openDataLTAResponse, Response response) {
                List<LTAService> ltaServices = openDataLTAResponse.getLTAServices();
                if (ltaServices != null) display(ltaServices);
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Failed to load bus arrivals: '%s'", error);
                try {
                    throw (error.getCause());
                } catch (Throwable e) {
                    Timber.e("Bus Arrivals request failed: '%s'", e);
                }
            }
        });
    }

    private void display(List<LTAService> ltaServices) {
        for (LTAService service : ltaServices) {
            Timber.i(service.toString());
        }
    }
}
