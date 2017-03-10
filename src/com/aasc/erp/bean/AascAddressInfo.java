/*
 * @(#)AascAddressInfo.java        14/11/2014
 * Copyright (c) 2005-2008 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

/**
 * Bean Class for storing and retrieving addresses.
 * Module with getXXX() and setXXX() methods. 
 * @author 	Eshwari
 * @version - 4
 ========================================================================================
 Date         Resource                 Change history
 ------------------------------------------------------------------------------------------
 * 14/11/2014   Eshwari          Created from Cloud Application
 *
 **/
public class AascAddressInfo {
    private String companyName = "";
    private String addressLine1 = "";
    private String city = "";
    private String stateProvinceCode = "";
    private String postalCode = "";
    private String countryCode = "";
    private int addressId = 0;
    private String addressLine2 = "";
    private String taxId;

    public AascAddressInfo() {
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvinceCode() {
        return stateProvinceCode;
    }

    public void setStateProvinceCode(String stateProvinceCode) {
        this.stateProvinceCode = stateProvinceCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxId() {
        return taxId;
    }
}
