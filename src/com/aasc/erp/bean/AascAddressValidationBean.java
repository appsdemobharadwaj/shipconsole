package com.aasc.erp.bean;
/*
 * @(#)AascAddressValidationBean.java     24/02/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */
 
 /**
  * AascAddressValidationBean class is action class for Address Validation.
  * @version   1
  * @author    Y Pradeep
  * History
  * 
  * 24-Feb-2015  Y Pradeep       Added this file for Address Validation.
  * 26-Feb-2015  Y Pradeep       Added fields required for parsing reponse.
  */

public class AascAddressValidationBean {
    String fedExKey = "";
    String fedExPassword = "";
    String upsUserName = "";
    String upsPassword = "";
    String accessLicenseNumber = "";
    String meterNumber = "";
    String carrierAccountNumber = "";
    String addressValidationEnable = "";
    String addressValidationUserName = "";
    String addressValidationPassword = "";
    String addressValidationAccNumber = "";
    String customerName = "";
    //String shipToLocationName = "";
    String attentionName = "";
    String address1 = "";
    String address2 = "";
    String city = "";
    String state = "";
    String postalCode = "";
    String countryCode = "";
    int carrierCode ;
    String orderNumber = "";
    
    String responseAddress = "";
    String responseAddress2 = "";
    String responseCity = "";
    String responseState = "";
    String responsePostalCode = "";
    String responseCountryCode = "";
    String responseAddressType = "";
    String responseAddressClassification = "";
    
    
    public AascAddressValidationBean() {
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAttentionName(String attentionName) {
        this.attentionName = attentionName;
    }

    public String getAttentionName() {
        return attentionName;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setFedExKey(String fedExKey) {
        this.fedExKey = fedExKey;
    }

    public String getFedExKey() {
        return fedExKey;
    }

    public void setFedExPassword(String fedExPassword) {
        this.fedExPassword = fedExPassword;
    }

    public String getFedExPassword() {
        return fedExPassword;
    }

    public void setUpsUserName(String upsUserName) {
        this.upsUserName = upsUserName;
    }

    public String getUpsUserName() {
        return upsUserName;
    }

    public void setUpsPassword(String upsPassword) {
        this.upsPassword = upsPassword;
    }

    public String getUpsPassword() {
        return upsPassword;
    }

    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }

    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setCarrierAccountNumber(String carrierAccountNumber) {
        this.carrierAccountNumber = carrierAccountNumber;
    }

    public String getCarrierAccountNumber() {
        return carrierAccountNumber;
    }

    public void setAddressValidationEnable(String addressValidationEnable) {
        this.addressValidationEnable = addressValidationEnable;
    }

    public String getAddressValidationEnable() {
        return addressValidationEnable;
    }

    public void setAddressValidationUserName(String addressValidationUserName) {
        this.addressValidationUserName = addressValidationUserName;
    }

    public String getAddressValidationUserName() {
        return addressValidationUserName;
    }

    public void setAddressValidationPassword(String addressValidationPassword) {
        this.addressValidationPassword = addressValidationPassword;
    }

    public String getAddressValidationPassword() {
        return addressValidationPassword;
    }

    public void setAddressValidationAccNumber(String addressValidationAccNumber) {
        this.addressValidationAccNumber = addressValidationAccNumber;
    }

    public String getAddressValidationAccNumber() {
        return addressValidationAccNumber;
    }

    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setResponseAddress(String responseAddress) {
        this.responseAddress = responseAddress;
    }

    public String getResponseAddress() {
        return responseAddress;
    }

    public void setResponseAddress2(String responseAddress2) {
        this.responseAddress2 = responseAddress2;
    }

    public String getResponseAddress2() {
        return responseAddress2;
    }

    public void setResponseCity(String responseCity) {
        this.responseCity = responseCity;
    }

    public String getResponseCity() {
        return responseCity;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponsePostalCode(String responsePostalCode) {
        this.responsePostalCode = responsePostalCode;
    }

    public String getResponsePostalCode() {
        return responsePostalCode;
    }

    public void setResponseCountryCode(String responseCountryCode) {
        this.responseCountryCode = responseCountryCode;
    }

    public String getResponseCountryCode() {
        return responseCountryCode;
    }

    public void setResponseAddressType(String responseAddressType) {
        this.responseAddressType = responseAddressType;
    }

    public String getResponseAddressType() {
        return responseAddressType;
    }

    public void setResponseAddressClassification(String responseAddressClassification) {
        this.responseAddressClassification = responseAddressClassification;
    }

    public String getResponseAddressClassification() {
        return responseAddressClassification;
    }
}
