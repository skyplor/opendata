package com.algomized.android.testopendata.ui;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.algomized.android.testopendata.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_barcode_scanner)
public class BarcodeScannerActivity extends Activity {
    @ViewById
    CompoundBarcodeView zxing_barcode_scanner;

    Bundle savedInstanceState;

    @ViewById
    Button switch_flashlight;

    CaptureManager captureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @AfterViews
    void init() {
        zxing_barcode_scanner.setTorchListener(new CompoundBarcodeView.TorchListener() {
            @Override
            public void onTorchOn() {

                switch_flashlight.setText(R.string.turn_off_flashlight);
            }

            @Override
            public void onTorchOff() {
                switch_flashlight.setText(R.string.turn_on_flashlight);

            }
        });

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...
        if (!hasFlash()) {
            switch_flashlight.setVisibility(View.GONE);
        }

        zxing_barcode_scanner = (CompoundBarcodeView) findViewById(R.id.zxing_barcode_scanner);
        captureManager = new CaptureManager(this, zxing_barcode_scanner);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }


    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return zxing_barcode_scanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {
        if (getString(R.string.turn_on_flashlight).equals(switch_flashlight.getText())) {
            zxing_barcode_scanner.setTorchOn();
        } else {
            zxing_barcode_scanner.setTorchOff();
        }
    }
}
