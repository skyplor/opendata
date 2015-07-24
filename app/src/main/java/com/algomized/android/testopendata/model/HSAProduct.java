package com.algomized.android.testopendata.model;

/**
 * Created by Sky on 24/7/2015.
 */
public class HSAProduct {
    private String activeingredients;
    private String approvaldate;
    private String atccode;
    private String countryofmanufacturer;
    private String dosageform;
    private String forensicclassification;
    private String licenceno;
    private String licenseholder;
    private String manufacturer;
    private String productname;
    private String case_folded_productname;
    private String routeofadministration;
    private String strength;

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getRouteofadministration() {
        return routeofadministration;
    }

    public void setRouteofadministration(String routeofadministration) {
        this.routeofadministration = routeofadministration;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicenseholder() {
        return licenseholder;
    }

    public void setLicenseholder(String licenseholder) {
        this.licenseholder = licenseholder;
    }

    public String getLicenceno() {
        return licenceno;
    }

    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno;
    }

    public String getForensicclassification() {
        return forensicclassification;
    }

    public void setForensicclassification(String forensicclassification) {
        this.forensicclassification = forensicclassification;
    }

    public String getDosageform() {
        return dosageform;
    }

    public void setDosageform(String dosageform) {
        this.dosageform = dosageform;
    }

    public String getCountryofmanufacturer() {
        return countryofmanufacturer;
    }

    public void setCountryofmanufacturer(String countryofmanufacturer) {
        this.countryofmanufacturer = countryofmanufacturer;
    }

    public String getAtccode() {
        return atccode;
    }

    public void setAtccode(String atccode) {
        this.atccode = atccode;
    }

    public String getApprovaldate() {
        return approvaldate;
    }

    public void setApprovaldate(String approvaldate) {
        this.approvaldate = approvaldate;
    }

    public String getActiveingredients() {
        return activeingredients;
    }

    public void setActiveingredients(String activeingredients) {
        this.activeingredients = activeingredients;
    }

    public String getCase_folded_productname() {
        return case_folded_productname;
    }

    public void setCase_folded_productname(String case_folded_productname) {
        this.case_folded_productname = case_folded_productname;
    }

    public String toString() {
        return "Licence No: " + licenceno
                + "\nProduct Name: " + productname
                + "\nLicense Holder: " + licenseholder
                + "\nApproval Date: " + approvaldate
                + "\nForensic Classification: " + forensicclassification
                + "\nATC Code: " + atccode
                + "\nDosage Form: " + dosageform
                + "\nRoute of Administration: " + routeofadministration
                + "\nManufacturer: " + manufacturer
                + "\nCountry of Manufacturer: " + countryofmanufacturer
                + "\nActive ingredients: " + activeingredients
                + "\nStrength: " + strength;
    }
}
