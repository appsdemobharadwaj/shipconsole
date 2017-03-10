/*

 * @(#)AascShipToLocationsInfo.java        05/11/2014

 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */
package com.aasc.erp.bean;

import java.util.LinkedList;

/*
 * @author Eshwari
 * @version 1.0
 * @since Modified by Eshwari
 */

/* ========================================================================================

 Date        Resource       Change history

 ------------------------------------------------------------------------------------------

 02/01/2015  Y Pradeep      Added customerId for retriving data from database.
 07/01/2015  Y Pradeep      Added proper java doc.
 20/01/2015  Sunanda.K      Modified the Documentationcreation Date
 20/01/2015  Suman G        Modified the copyrights years
 23/03/2015  Sunanda K      Added code for newly created fields email address and addressline 3
 ========================================================================================*/

public class AascShipToLocationsInfo {
    public AascShipToLocationsInfo() {
    }
    private int clientId;
    private int userId;
    private int locationId;
    private int customerLocationId;
    private int customerId;
    private String shipToCustomerName = "";
    private String shipToCustLocation="";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String addressLine3 = "";
    private String city = "";
    private String state = "";
    private String postalCode;
    private String countryCode="";
    private String phoneNumber = "";
    private String emailAddress = "";
    private int errorStatus;
    private String errorMessage="";
    private String shipToContactName="";
    private String enableFlag="";
    
    
       // private String actionType;
       
    LinkedList shipToCustomersList;

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
    
    public void setShipToCustomerName(String shipToCustomerName) {
        this.shipToCustomerName = shipToCustomerName;
    }

    public String getShipToCustomerName() {
        return shipToCustomerName;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
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

    public void setShipToContactName(String shipToContactName) {
        this.shipToContactName = shipToContactName;
    }

    public String getShipToContactName() {
        return shipToContactName;
    }

    public void setShipToCustomersList(LinkedList shipToCustomersList) {
        this.shipToCustomersList = shipToCustomersList;
    }

    public LinkedList getShipToCustomersList() {
        return shipToCustomersList;
    }

    public void setShipToCustLocation(String shipToCustLocation) {
        this.shipToCustLocation = shipToCustLocation;
    }

    public String getShipToCustLocation() {
        return shipToCustLocation;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setCustomerLocationId(int customerLocationId) {
        this.customerLocationId = customerLocationId;
    }

    public int getCustomerLocationId() {
        return customerLocationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
