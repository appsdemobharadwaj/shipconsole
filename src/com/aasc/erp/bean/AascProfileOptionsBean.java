/*

 * @ AascProfileOptionsDelegate.java        07/08/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */
 
 /**

  * AascProfileOptionsDelegate class.

  * @author Jagadish

  * @creation 07/08/2014
  
  * @History
  * 24-12-2014      Jagadish Jain   Added code required for client id, address validationa nd FreightShopping fields in profile options. 
  * 20-02-2015      Y Pradeep       Modified code for Address Validationa dn Freight Shopping. Removed Average pallet weight and lable printing check box.
  * 26-02-2015      Y Pradeep       Modified code to change field names of username, password and account number common for Address Validation and Freight Shopping.
  * 10-03-2015      Y Pradeep       Removed reference1Value and reference2Value fileds.
  * 17-03-2015      Y Pradeep       Removed label path field from Profile Options.
  * 03-06-2015      Y Pradeep       Added weighingScaleName and weighingScale variables for configuring Weighing Scale base on Location.
  * 13-07-2015      Y Pradeep       Added aascPrinterInfoHashMap variables for getting printers details for different label formats.
  */


package com.aasc.erp.bean;

import java.util.HashMap;

public class AascProfileOptionsBean {
    private int locationId;
    private int clientId;
    private String defaultPayMethod;
    private String enableSaturdayFlag;
    private String editShipToAddress;
    private String addresValidation;
    private String maskAccount; // vikas added maskAccount for masking the carrier account number
    private String freightShopping;
    private String shipperName;
    private String wsUserName;
    private String wsPassword;
    private String wsAccountNumber;
    private String companyName;
    private String customerNameFlag;
    private String addrLinesFlag;
    private String cityFlag;
    private String stateFlag;
    private String postalCodeFlag;
    private String countryCodeFlag;
    private String reference1 = ""; // indicates customer purchase order number
    private String reference2 = ""; // indicates order number
    private String weighingScaleName = ""; // indicates weighing scale name.
    private String weighingScale = "N"; // indicates weighingScale checkbox

    // declaring the variables for carrier server details

    private static String urlPath = "" ;
    private HashMap clientDetailsHashMap ;
    private String nonDiscountedCost ;
    private String acctNumNegotiatedFlag;
    private HashMap hazardousPkgGroupDetails; // holds hazardousPkgGroupDetails hashmap
     private HashMap aascPrinterInfoHashMap;


    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setDefaultPayMethod(String defaultPayMethod) {
        this.defaultPayMethod = defaultPayMethod;
    }

    public String getDefaultPayMethod() {
        return defaultPayMethod;
    }

    public void setEnableSaturdayFlag(String enableSaturdayFlag) {
        this.enableSaturdayFlag = enableSaturdayFlag;
    }

    public String getEnableSaturdayFlag() {
        return enableSaturdayFlag;
    }

    public void setEditShipToAddress(String editShipToAddress) {
        this.editShipToAddress = editShipToAddress;
    }

    public String getEditShipToAddress() {
        return editShipToAddress;
    }

    public void setAddresValidation(String addresValidation) {
        this.addresValidation = addresValidation;
    }

    public String getAddresValidation() {
        return addresValidation;
    }
    // vikas added code for masking the carrier account number
    public void setMaskAccount(String maskAccount) {
        this.maskAccount = maskAccount;
    }
    public String getMaskAccount() {
        return maskAccount;
    }
    // vikas code ended

    public void setFreightShopping(String freightShopping) {
        this.freightShopping = freightShopping;
    }

    public String getFreightShopping() {
        return freightShopping;
    }
    
    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setCustomerNameFlag(String customerNameFlag) {
        this.customerNameFlag = customerNameFlag;
    }

    public String getCustomerNameFlag() {
        return customerNameFlag;
    }

    public void setAddrLinesFlag(String addrLinesFlag) {
        this.addrLinesFlag = addrLinesFlag;
    }

    public String getAddrLinesFlag() {
        return addrLinesFlag;
    }

    public void setCityFlag(String cityFlag) {
        this.cityFlag = cityFlag;
    }

    public String getCityFlag() {
        return cityFlag;
    }

    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
    }

    public String getStateFlag() {
        return stateFlag;
    }

    public void setPostalCodeFlag(String postalCodeFlag) {
        this.postalCodeFlag = postalCodeFlag;
    }

    public String getPostalCodeFlag() {
        return postalCodeFlag;
    }

    public void setCountryCodeFlag(String countryCodeFlag) {
        this.countryCodeFlag = countryCodeFlag;
    }

    public String getCountryCodeFlag() {
        return countryCodeFlag;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }

    public String getReference2() {
        return reference2;
    }

    public String getUrlPath() {
        return urlPath;
    }
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public void setClientDetailsHashMap(HashMap clientDetailsHashMap) {
        this.clientDetailsHashMap = clientDetailsHashMap;
    }

    public HashMap getClientDetailsHashMap() {
        return clientDetailsHashMap;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
    public void setNonDiscountedCost(String nonDiscountedCost) {
        this.nonDiscountedCost = nonDiscountedCost;
    }

    public String getNonDiscountedCost() {
        return nonDiscountedCost;
    }

    public void setAcctNumNegotiatedFlag(String acctNumNegotiatedFlag) {
        this.acctNumNegotiatedFlag = acctNumNegotiatedFlag;
    }

    public String getAcctNumNegotiatedFlag() {
        return acctNumNegotiatedFlag;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
    
    public void setHazardousPkgGroupDetails(HashMap hazardousPkgGroupDetails) {
        this.hazardousPkgGroupDetails = hazardousPkgGroupDetails;
    }

    public HashMap getHazardousPkgGroupDetails() {
        return hazardousPkgGroupDetails;
    }

    public void setWsUserName(String wsUserName) {
        this.wsUserName = wsUserName;
    }

    public String getWsUserName() {
        return wsUserName;
    }

    public void setWsPassword(String wsPassword) {
        this.wsPassword = wsPassword;
    }

    public String getWsPassword() {
        return wsPassword;
    }

    public void setWsAccountNumber(String wsAccountNumber) {
        this.wsAccountNumber = wsAccountNumber;
    }

    public String getWsAccountNumber() {
        return wsAccountNumber;
    }

    public void setWeighingScaleName(String weighingScaleName) {
        this.weighingScaleName = weighingScaleName;
    }

    public String getWeighingScaleName() {
        return weighingScaleName;
    }

    public void setWeighingScale(String weighingScale) {
        this.weighingScale = weighingScale;
    }

    public String getWeighingScale() {
        return weighingScale;
    }

    public void setAascPrinterInfoHashMap(HashMap aascPrinterInfoHashMap) {
        this.aascPrinterInfoHashMap = aascPrinterInfoHashMap;
    }

    public HashMap getAascPrinterInfoHashMap() {
        return aascPrinterInfoHashMap;
    }
}
