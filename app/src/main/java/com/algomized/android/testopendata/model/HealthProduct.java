package com.algomized.android.testopendata.model;

/**
 * Created by Sky on 22/7/2015.
 */
public class HealthProduct {//extends RealmObject {
    //    private String id;
//    @PrimaryKey
    private String productname;
    private String case_folded_productname;
    private String epc;
    private String ean;
    private String manufacturer;
    private String imageSrc;
    private String description;

    public HealthProduct() {
    }

    public HealthProduct(HealthProduct healthProduct) {
        this(healthProduct.getProductName(), healthProduct.getEpc(), healthProduct.getEan(), healthProduct.getManufacturer(), healthProduct.getImageSrc(), healthProduct.getDescription());
    }

    public HealthProduct(String productname, String epc, String ean, String manufacturer, String imageSrc, String description) {
        this.productname = productname;
        this.case_folded_productname = productname.toLowerCase();
        this.epc = epc;
        this.ean = ean;
        this.manufacturer = manufacturer;
        this.imageSrc = imageSrc;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getProductName() {
        return productname;
    }

    public void setProductName(String productname) {
        this.productname = productname;
        this.case_folded_productname = productname.toLowerCase();
    }

    public String toString() {
        return "Product Name: " + productname + "\nEPC: " + epc + "\nEAN: " + ean + "\nManufacturer: " + manufacturer + "\nImage: " + imageSrc + "\nDescription: " + description;
    }

}
