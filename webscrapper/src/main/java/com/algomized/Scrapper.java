package com.algomized;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Scrapper {


    static Set<String> healthcare_links;

    static Set<String> productdetail_links;

    public static Set<String> getProductdetail_links() {
        if (productdetail_links == null) productdetail_links = new HashSet<>();
        return productdetail_links;
    }

    public static Set<String> getHealthcare_links() {
        if (healthcare_links == null) healthcare_links = new HashSet<>();
        return healthcare_links;
    }

    public static void main(String[] args) {
        init();
        goGrabLinks();
    }

    static void init() {
        getHealthcare_links();
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

    public static void goGrabLinks() {
        Document doc = null;
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
            System.out.println("Size: " + getHealthcare_links().size());
            for (String healthcareLink : getHealthcare_links()) {
                doc = Jsoup.connect(healthcareLink).timeout(5 * 60 * 1000).get();
                Elements productdetails = doc.select("div.product-title a");
                if (productdetails != null)
                    System.out.println("Product details not null, size: " + productdetails.size());
                for (Element productdetail : productdetails) {
                    System.out.println(productdetail.toString());
                    Elements links = productdetail.getElementsByTag("a");
                    for (Element link : links) {
                        String productLink = link.attr("href");
                        String product = link.text();
                        System.out.println(productLink);
                        getProductdetail_links().add(productLink);
                    }
                }
//                Elements productdetails = doc.getElementsByClass("product-image");
//                for (Element productdetail : productdetails) {
//                    Elements links = productdetail.getElementsByTag("a");
//                    for (Element link : links) {
//                        String productLink = link.attr("href");
//                        String product = link.text();
//                        Timber.i("ProductDetail: %s (Link: %s)", product, productLink);
//                        getProductdetail_links().add(productLink);
//                    }
//                }
            }
            for (String s : getProductdetail_links()) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
