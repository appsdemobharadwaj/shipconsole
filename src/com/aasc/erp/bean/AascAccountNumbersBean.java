package com.aasc.erp.bean;

/*
 * @(#)AascAccountNumbersBean.java     28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import java.util.LinkedList;

/**
 * AascAccountNumbersBean class is java bean class for Account Numbers.
 * @version   1
 * @author    Venkateswaramma Malluru
 * History
 * 
 * 30/01/2015   Y Pradeep           Ran self audit and added java doc for all methods.
 * 05/05/2015   Y Pradeep           Renamed registrationStatus to accountNumberRegistrationFlag.
 */
public class AascAccountNumbersBean {

    private int locationId;
    private int carrierCode;
    private int accountNumberId;
    private String accountNumber;
    private String meterNumber;
    private String accessLicenseNumber;
    private String actionType;
    private int errorStatus;
    private String errorMessage;
    private String accountNumberActive;
    private String accountNumberDefault;
    private String accountNumberDelete;
    private String accountNumberNegotiatedFlag;
    private String negotiatedRates;
    private String accountNumberRegistrationFlag;
    private String accountUserName;
    private String accountPassword;
    LinkedList accountNumbersList;
    private int clientId; 
    
    private String logoImagePath;
    private String signatureImagePath;

 

    public void setlocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getlocationId() {
        return locationId;
    }

    public void setAccountNumberId(int accountNumberId) {
        this.accountNumberId = accountNumberId;
    }

    public int getAccountNumberId() {
        return accountNumberId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }

    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setAccountNumbersList(LinkedList accountNumbersList) {
        this.accountNumbersList = accountNumbersList;
    }

    public LinkedList getAccountNumbersList() {
        return accountNumbersList;
    }

    public void setAccountNumberActive(String accountNumberActive) {
        this.accountNumberActive = accountNumberActive;
    }

    public String getAccountNumberActive() {
        return accountNumberActive;
    }

    public void setAccountNumberDefault(String accountNumberDefault) {
        this.accountNumberDefault = accountNumberDefault;
    }

    public String getAccountNumberDefault() {
        return accountNumberDefault;
    }

    public void setAccountNumberDelete(String accountNumberDelete) {
        this.accountNumberDelete = accountNumberDelete;
    }

    public String getAccountNumberDelete() {
        return accountNumberDelete;
    }

    public void setAccountNumberNegotiatedFlag(String accountNumberNegotiatedFlag) {
        this.accountNumberNegotiatedFlag = accountNumberNegotiatedFlag;
    }

    public String getAccountNumberNegotiatedFlag() {
        return accountNumberNegotiatedFlag;
    }

    public void setNegotiatedRates(String negotiatedRates) {
        this.negotiatedRates = negotiatedRates;
    }

    public String getNegotiatedRates() {
        return negotiatedRates;
    }

   public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setLogoImagePath(String logoImagePath) {
        this.logoImagePath = logoImagePath;
    }

    public String getLogoImagePath() {
        return logoImagePath;
    }

    public void setSignatureImagePath(String signatureImagePath) {
        this.signatureImagePath = signatureImagePath;
    }

    public String getSignatureImagePath() {
        return signatureImagePath;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setAccountNumberRegistrationFlag(String accountNumberRegistrationFlag) {
        this.accountNumberRegistrationFlag = accountNumberRegistrationFlag;
    }

    public String getAccountNumberRegistrationFlag() {
        return accountNumberRegistrationFlag;
    }
}
