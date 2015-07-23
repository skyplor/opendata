package com.algomized.android.testopendata.model;

/**
 * Created by Sky on 22/7/2015.
 */
public class HealthProduct {//extends RealmObject {
    //    private String id;
//    @PrimaryKey
    private String name;
    private String epc;
    private String ean;
    private String manufacturer;
    private String imageSrc;
    private String description;

//    public HealthProduct() {
//        id = UUID.randomUUID().toString();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public HealthProduct() {
    }

    public HealthProduct(HealthProduct healthProduct) {
        this(healthProduct.getName(), healthProduct.getEpc(), healthProduct.getEan(), healthProduct.getManufacturer(), healthProduct.getImageSrc(), healthProduct.getDescription());
    }

    public HealthProduct(String name, String epc, String ean, String manufacturer, String imageSrc, String description) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
