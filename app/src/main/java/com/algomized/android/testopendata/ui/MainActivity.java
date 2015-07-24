package com.algomized.android.testopendata.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.algomized.android.testopendata.R;
import com.algomized.android.testopendata.api.OpenDataLTAResponse;
import com.algomized.android.testopendata.api.OpenDataLTAService;
import com.algomized.android.testopendata.model.HSAProduct;
import com.algomized.android.testopendata.model.HealthProduct;
import com.algomized.android.testopendata.model.LTAService;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
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
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    Set<String> healthcare_links;

    Set<String> productdetail_links;
    //    Realm realm;
    ProgressDialog progressDialog;
    Firebase mFirebaseRef;

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
        initialiseFirebase();
        getHealthcare_links();
//        setupRealmDB();
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

    private void initialiseFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        mFirebaseRef = new Firebase("https://sgopendata.firebaseio.com/");
//        mFirebaseRef.keepSynced(true);
    }

//    @UiThread
//    public void setupRealmDB() {
//        RealmConfiguration config1 = new RealmConfiguration.Builder(this)
//                .build();
//
//        realm = Realm.getInstance(config1);
//
//        RealmBrowser.getInstance().addRealmModel(HealthProduct.class);
//    }

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
//        RealmBrowser.startRealmFilesActivity(this);
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
                showProgressDialog("Loading", "Searching the DB...", true);
                // Search the DB for the number and present the result back to UI.
                searchDB(result.getContents());
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @UiThread
    public void showProgressDialog(String title, String message, boolean cancelable) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Snackbar.make(snackBar, "Search is cancelled", Snackbar.LENGTH_LONG).show();
            }

        });
        progressDialog.show();
    }

    @Background
    public void goGrabLinks() {
        Document doc;
        String currentLink = "";
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
                currentLink = healthcareLink;
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
            currentLink = "";
            Timber.i("ProductDetails size: " + getProductdetail_links().size());
            final List<HealthProduct> healthProducts = new ArrayList<>();
            HealthProduct healthProduct;
            for (String productDetailLink : getProductdetail_links()) {
                currentLink = productDetailLink;
//            doc = Jsoup.connect(getProductdetail_links().iterator().next()).timeout(5 * 60 * 1000).get();
                doc = Jsoup.connect(productDetailLink).timeout(5 * 60 * 1000).get();
                healthProduct = new HealthProduct();
                Elements titles = doc.select("div.content-details h1.content-title");
                if (titles != null && !titles.isEmpty()) {
                    String name = titles.get(0).text().split(" - ")[0];
                    healthProduct.setProductName(name);
                }
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
            System.out.println("Error at link: " + currentLink);
            e.printStackTrace();
        }
    }

    @Background
    public void insertIntoDB(List<HealthProduct> healthProducts) {
        Firebase healthproductsref = mFirebaseRef.child("healthproducts");

        final List<HealthProduct> temp = new ArrayList<>();
        for (HealthProduct healthProduct : healthProducts) {
//            HealthProduct healthProductTemp = new HealthProduct(healthProduct);
//            temp.add(healthProductTemp);
            healthproductsref.push().setValue(healthProduct);
        }
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(temp);
//            }
//        });
        Timber.i("Done Scrapping");

        Snackbar.make(snackBar, "Done scrapping", Snackbar.LENGTH_LONG).show();
//        realm.close();
    }

    @Background
    protected void searchDB(final String barcode) {
        // search the db and if no result, dem we proceed to add into db
        // if user cant find the result,we proceed to add into the db
        // we will first search our phone db
//        Snackbar.make(snackBar, "Searching for " + barcode, Snackbar.LENGTH_LONG).show();
        Firebase healthproductsref = mFirebaseRef.child("healthproducts");
        Query queryRef = healthproductsref.orderByChild("ean").equalTo(barcode);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Map<String, Object> healthProductMap = (Map<String, Object>) dataSnapshot.getValue();
                HealthProduct healthProduct = new HealthProduct();
                healthProduct.setProductName(String.valueOf(healthProductMap.get("productname")));
                healthProduct.setEpc(String.valueOf(healthProductMap.get("epc")));
                healthProduct.setEan(String.valueOf(healthProductMap.get("ean")));
                healthProduct.setManufacturer(String.valueOf(healthProductMap.get("manufacturer")));
                healthProduct.setImageSrc(String.valueOf(healthProductMap.get("imageSrc")));
                healthProduct.setDescription(String.valueOf(healthProductMap.get("description")));
                display(healthProduct);
                searchHSAdb(healthProduct);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Snackbar.make(snackBar, "Something went wrong. It's probably our fault but please try again later. =)", Snackbar.LENGTH_LONG).show();
            }
        });
        // on successful callback, we dismiss the progressdialog and go display result on ui thread
//        RealmResults results = realm.ge
    }

    @UiThread
    public void display(HealthProduct healthProduct) {
        progressDialog.dismiss();

        Snackbar.make(snackBar, healthProduct.toString(), Snackbar.LENGTH_LONG).show();
        System.out.println(healthProduct.toString());
    }

    @UiThread
    public void display(HSAProduct hsaProduct) {
        progressDialog.dismiss();

        Snackbar.make(snackBar, hsaProduct.toString(), Snackbar.LENGTH_LONG).show();
        System.out.println(hsaProduct.toString());
    }

    @Background
    public void searchHSAdb(HealthProduct healthProduct) {

        String name = healthProduct.getProductName().toLowerCase();
        String ean = healthProduct.getEan();
        String manufacturer = healthProduct.getManufacturer();
        String imageSrc = healthProduct.getImageSrc();
        String description = healthProduct.getDescription();

        Firebase hsacertifiedref = mFirebaseRef.child("hsacertified");
        Query queryRef = hsacertifiedref.orderByChild("case_folded_productname").startAt(name).endAt(name + "~");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                Map<String, Object> hsaProductMap = (Map<String, Object>) dataSnapshot.getValue();
                HSAProduct hsaProduct = new HSAProduct();
                hsaProduct.setProductname(String.valueOf(hsaProductMap.get("productname")));
                hsaProduct.setManufacturer(String.valueOf(hsaProductMap.get("manufacturer")));
                hsaProduct.setActiveingredients(String.valueOf(hsaProductMap.get("activeingredients")));
                hsaProduct.setApprovaldate(String.valueOf(hsaProductMap.get("approvaldate")));
                hsaProduct.setAtccode(String.valueOf(hsaProductMap.get("atccode")));
                hsaProduct.setCountryofmanufacturer(String.valueOf(hsaProductMap.get("countryofmanufacturer")));
                hsaProduct.setDosageform(String.valueOf(hsaProductMap.get("dosageform")));
                hsaProduct.setForensicclassification(String.valueOf(hsaProductMap.get("forensicclassification")));
                hsaProduct.setLicenceno(String.valueOf(hsaProductMap.get("licenceno")));
                hsaProduct.setLicenseholder(String.valueOf(hsaProductMap.get("licenseholder")));
                hsaProduct.setRouteofadministration(String.valueOf(hsaProductMap.get("routeofadministration")));
                hsaProduct.setStrength(String.valueOf(hsaProductMap.get("strength")));
                display(hsaProduct);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
