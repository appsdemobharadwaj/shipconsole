package com.aasc.erp.bean;
/*========================================================================+
@(#)AascSetupLocationBean.java 27/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/



import java.util.LinkedList;



/**
 * AascSetupLocationBean is a bean class. 
 * @author      N Srisha
 * @version 1.0
 * @since
 * 
 * History:
 * 23/03/2015  Sunanda  Added code for newly created fields email address and addressline 3
 * 
 **/
public class

AascSetupLocationBean {
    private int clientId;
    private int userId;

    private int locationId;
    private String locationName = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String state = "";
    private String postalCode;
    private String countryCode = "";
    private String phoneNumber = "";
    private String locationStatus = "";
    private int errorStatus;
    private String errorMessage = "";
    private String contactName = "";
    private int opStatus;
    private String emailAddress;
    private String addressLine3 = "";
    LinkedList locationDetailsList;



    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

        public String getAddressLine3() {
            return addressLine3;
        }
        
        public void setAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
        }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setLocationDetailsList(LinkedList locationDetailsList) {
        this.locationDetailsList = locationDetailsList;
    }

    public LinkedList getLocationDetailsList() {
        return locationDetailsList;
    }


    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getLocationStatus() {
        return locationStatus;
    }


}
