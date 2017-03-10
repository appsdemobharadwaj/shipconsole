package com.aasc.erp.bean;

import com.aasc.erp.util.AascLogger;

import java.util.LinkedList;
import java.util.logging.Logger;


public class AascShipFromLocationsInfo {

    private int clientId;
    private int userId;

    private int locationId;
    private String shipFromLocationName = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String state = "";
    private String postalCode;
    private String countryCode = "";
    private String phoneNumber = "";
    private int errorStatus;
    private String errorMessage = "";
    private String shipFromContactName = "";
    // private String actionType;

    LinkedList shipFromLocationsList;

    private static Logger logger = 
        AascLogger.getLogger("AascShipFromLocationsInfo");

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }


    //
    //    public void setActionType(String actionType) {
    //        this.actionType = actionType;
    //    }
    //
    //    public String getActionType() {
    //        return actionType;
    //    }

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

    public void setShipFromLocationsList(LinkedList shipFromLocationsList) {
        this.shipFromLocationsList = shipFromLocationsList;
    }

    public LinkedList getShipFromLocationsList() {
        return shipFromLocationsList;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setShipFromLocationName(String shipFromLocationName) {
        this.shipFromLocationName = shipFromLocationName;
    }

    public String getShipFromLocationName() {
        return shipFromLocationName;
    }


    public void setShipFromContactName(String shipFromContactName) {
        this.shipFromContactName = shipFromContactName;
    }

    public String getShipFromContactName() {
        return shipFromContactName;
    }

}
