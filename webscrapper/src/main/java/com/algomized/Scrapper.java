package com.algomized;

import com.algomized.model.HealthProduct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            System.out.println("Health Care links size: " + getHealthcare_links().size());
            for (String healthcareLink : getHealthcare_links()) {
                doc = Jsoup.connect(healthcareLink + "?page=all").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1) Gecko/20100101 Firefox/8.0.1").timeout(5 * 60 * 1000).get();
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
            System.out.println("ProductDetails size: " + getProductdetail_links().size());
            final List<HealthProduct> healthProducts = new ArrayList<>();
            HealthProduct healthProduct;
            for (String productDetailLink : getProductdetail_links()) {
                System.out.println("Current link: " + productDetailLink);
//            doc = Jsoup.connect(getProductdetail_links().iterator().next()).timeout(5 * 60 * 1000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1) Gecko/20100101 Firefox/8.0.1").get();
                doc = Jsoup.connect(productDetailLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1) Gecko/20100101 Firefox/8.0.1").timeout(1 * 60 * 1000).get();
//            System.out.println("Document:\n" + doc.toString());
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
            System.out.println("Health Products size: " + healthProducts.size());
            writeToCSV(healthProducts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToCSV(List<HealthProduct> healthProducts) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("writeWithCsvBeanWriter.csv"), "UTF-8"))) {
            writer.write("name,epc,ean,manufacturer,imageSrc,description");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter("writeWithCsvBeanWriter.csv"),
                    CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the bean values to each column (names must match)
            final String[] header = new String[]{"name", "epc", "ean", "manufacturer", "imageSrc", "description"};
            final CellProcessor[] processors = getProcessors();

            // write the header
            beanWriter.writeHeader(header);

            // write the beans
            for (final HealthProduct customer : healthProducts) {
                beanWriter.write(customer, header, processors);
            }

        } catch (IOException e) {
            System.out.println("Exception in writecsv:\n" + e);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException e) {
                    System.out.println("Unable to close beanwriter:\n" + e);
                }
            }
        }
    }

    /**
     * Sets up the processors used for the examples. There are 10 CSV columns, so 10 processors are defined. All values
     * are converted to Strings before writing (there's no need to convert them), and null values will be written as
     * empty columns (no need to convert them to "").
     *
     * @return the cell processors
     */
    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
                new UniqueHashCode(), // name (must be unique)
                new NotNull(), // epc
                new NotNull(), // ean
                new Optional(), // manufacturer
                new Optional(), // imageSrc
                new Optional(), // description
        };

        return processors;
    }
}
