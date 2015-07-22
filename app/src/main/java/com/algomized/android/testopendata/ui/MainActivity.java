package com.algomized.android.testopendata.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.algomized.android.testopendata.R;
import com.algomized.android.testopendata.api.OpenDataLTAResponse;
import com.algomized.android.testopendata.api.OpenDataLTAService;
import com.algomized.android.testopendata.model.HealthProduct;
import com.algomized.android.testopendata.model.LTAService;
import com.dd.realmbrowser.RealmBrowser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    Set<String> healthcare_links;

    Set<String> productdetail_links;
    Realm realm;
    ProgressDialog progressDialog;

    @ViewById
    View snackBar;

    public Set<String> getProductdetail_links() {
        if (productdetail_links == null) productdetail_links = new HashSet<>();
        return productdetail_links;
    }

    public Set<String> getHealthcare_links() {
        if (healthcare_links == null) healthcare_links = new HashSet<>();
        return healthcare_links;
    }

    @AfterViews
    void init() {
        getHealthcare_links();
        setupRealmDB();
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/allergies-sinus.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/antifungal-treatments.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/antiseptics-cleaning-supplies.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/bandages-tapes.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-beauty/health-care/contraceptives.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/corn-callus-care.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/cough-cold-and-flu.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/diet-pills.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/ear-drops.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/eye-drops-eye-lubricants.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/eye-wash-supplies.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/gastrointestinal-treatments.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/heat-rubs.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/massage-oils.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/medical-thermometers.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/pain-relievers.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-beauty/health-care/pregnancy-tests.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/rash-anti-itch-treatments.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/sleeping-medication.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/traditional-chinese-tonics.html");
        healthcare_links.add("http://www.minshenghe.com.sg/health-care/vitamins-supplements.html");
    }

    @Background
    public void setupRealmDB() {
        RealmConfiguration config1 = new RealmConfiguration.Builder(this)
                .build();

        realm = Realm.getInstance(config1);

        RealmBrowser.getInstance().addRealmModel(HealthProduct.class);
    }

    @Click
    void getBusArrivals() {
        getOpenDataLTAService(0, "14141", null);
    }

    @Click
    void scrapScreen() {
        goGrabLinks();
    }

    @Click
    void scanBarcodeScreen() {
        new IntentIntegrator(this).setCaptureActivity(BarcodeScannerActivity_.class).initiateScan();
    }

    @Click
    void openRealmBrowser() {
        RealmBrowser.startRealmFilesActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Snackbar.make(snackBar, "Cancelled", Snackbar.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Snackbar.make(snackBar, "Scanned: " + result.getContents(), Snackbar.LENGTH_LONG).show();
                showProgressDialog("Loading", "Searching the DB...", false);
                // Search the DB for the number and present the result back to UI.
                searchDB(result.getContents());
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showProgressDialog(String title, String message, boolean cancelable) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    @Background
    public void searchDB(String contents) {
        // on successful callback, we dismiss the progressdialog and go display result on ui thread
//        RealmResults results = realm.ge
        progressDialog.dismiss();
        Snackbar.make(snackBar, "Searching for " + contents, Snackbar.LENGTH_LONG);
    }

    @Background
    public void goGrabLinks() {
        Document doc;
        try {
//            doc = Jsoup.connect("http://www.minshenghe.com.sg/health-care.html").get();
//            Elements contents = doc.getElementsByClass("nav-pills");
//            for (Element content : contents) {
//                Elements links = content.getElementsByTag("a");
//                for (Element link : links) {
//                    String linkHref = link.attr("href");
////                    String linkText = link.text();
//                    Timber.i(linkHref);
//                    getHealthcare_links().add(linkHref);
//                }
//            }
            for (String healthcareLink : getHealthcare_links()) {
                doc = Jsoup.connect(healthcareLink + "?page=all").timeout(5 * 60 * 1000).get();
                Elements productdetails = doc.select("div.product-title a");
                if (productdetails != null)
                    for (Element productdetail : productdetails) {
                        Elements links = productdetail.getElementsByTag("a");
                        for (Element link : links) {
                            String productLink = link.attr("href");
                            getProductdetail_links().add(productLink);
                        }
                    }
            }
            Timber.i("ProductDetails size: " + getProductdetail_links().size());
            final List<HealthProduct> healthProducts = new ArrayList<>();
            HealthProduct healthProduct;
            for (String productDetailLink : getProductdetail_links()) {
                doc = Jsoup.connect(productDetailLink).timeout(5 * 60 * 1000).get();//getProductdetail_links().iterator().next()).timeout(5 * 60 * 1000).get();
                healthProduct = new HealthProduct();
                Elements titles = doc.select("div.content-details h1.content-title");
                if (titles != null && !titles.isEmpty())
                    healthProduct.setName(titles.get(0).text());
                Elements epc = doc.select("li.product-code span.product-detail-value");
                if (epc != null && !epc.isEmpty())
                    healthProduct.setEpc(epc.get(0).text());
                Elements ean = doc.select("li.product-ean span.product-detail-value");
                if (ean != null && !ean.isEmpty())
                    healthProduct.setEan(ean.get(0).text());
                Elements manufacturer = doc.select("li.product-manufacturer span.product-detail-value");
                if (manufacturer != null && !manufacturer.isEmpty())
                    healthProduct.setManufacturer(manufacturer.get(0).text());
                Elements description = doc.select("div.tab-pane.active");
                if (description != null && !description.isEmpty())
                    healthProduct.setDescription(description.get(0).text());
                Elements image_src = doc.select("a.MagicZoomPlus img");
                if (image_src != null && !image_src.isEmpty())
                    healthProduct.setImageSrc(image_src.get(0).text());
                healthProducts.add(healthProduct);
            }
            Timber.i("Health Products size: " + healthProducts.size());
            insertIntoDB(healthProducts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void insertIntoDB(List<HealthProduct> healthProducts) {
        final List<HealthProduct> temp = new ArrayList<>(healthProducts);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(temp);
            }
        });
        Timber.i("Done Scrapping");

        Snackbar.make(snackBar, "Done scrapping", Snackbar.LENGTH_LONG).show();
        realm.close();
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
