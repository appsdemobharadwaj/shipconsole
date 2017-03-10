/*
 * @(#)AascTrackingOrderInfo.java        11/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.bean;

import java.util.LinkedList;


/**
 AascTrackingOrderInfo class with getXxx() and setXxx() methods for Tracking information.
 *
 *@author Eshwari M
 *@version 1.0
 * History
 * 17/12/2014   Eshwari M       Renamed this file name from AascTrackingDelvieryInfo to AascTrackingOrderInfo for SC Lite
 * 16-Jan-2015  Y Pradeep       Modified code to remove unused variables and commented code as suggested in Code Review document and ran self audit.
 * 20-Jan-2015  Y Pradeep       Modified auther and version, also removed commented code.
 */

public class AascTrackingOrderInfo {
    private int returnStatus; // returns status from the data base package 
    private AascShipmentHeaderInfo headerInfo = null; // object of AascHeaderInfo class
    private LinkedList packageInfo = null; // LinkedList variable for Package information
    private LinkedList orderInfo = null; // LinkedList object for OrderNames
    private String actionType = null; // gets the type of action
    // Tracking connection configuration information
    private String userName = ""; // used to connect to connectShip for retreiving tracking information
    private String password = ""; // used to connect to connectShip for retreiving tracking information
    private String service = ""; // name of the service to be used
    private String prefix = ""; // prefix gives the file name to which we should send the request on the server,
    // file name which processes tracking request and gives response.
    private String protocol = ""; // Type of the protocol used
    private String hostName = ""; // host name gives the connecShip address where the connectShip instance is located
    private String accessLicenseNumber;
    private int locationId; 

    /**
     Method setHeaderInfo() sets aascHeaderInfo object of type AascHeaderInfo class.
     @param aascHeaderInfo AascHeaderInfo class.
     */
    public void setHeaderInfo(AascShipmentHeaderInfo aascHeaderInfo) {
        this.headerInfo = aascHeaderInfo;
    }

    /**
     Method setPackageInfo() sets aascPackageInfo object of type LinkedList.
     @param aascPackageInfo LinkedList.
     */
    public void setPackageInfo(LinkedList aascPackageInfo) {
        this.packageInfo = aascPackageInfo;
    }

    /**
     Method getHeaderInfo() gets the header information.
     @return headerInfo AascHeaderInfo class.
     */
    public AascShipmentHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     Method getPackageInfo gets the package information.
     @return packageInfo LinkedList.
     */
    public LinkedList getPackageInfo() {
        return packageInfo;
    }

    /**
     Method setReturnStatus sets the status.
     @param returnStatus int.
     */
    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    /**
     Method getReturnStatus is used to get the status.
     @return returnStatus int.
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     Method setOrderList() sets aascTrackingOrderInfo object of type LinkedList.
     @param aascTrackingOrderInfo LinkedList.
     */
    public void setOrderList(LinkedList aascTrackingOrderInfo) {
        this.orderInfo = aascTrackingOrderInfo;
    }

    /**
     Method getOrderList gets the list of Order names.
     @return OrderInfo LinkedList.
     */
    public LinkedList getOrderList() {
        return orderInfo;
    }

    /**
     Method getActionType gets the actionType .
     @return actionType String.
     */
    public String getActionType() {
        return actionType;
    }

    /**
     Method setActionType() sets actionType.
     @param actionType String.
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     Method getUserName() gets the userName.
     @return userName String.
     */
    public String getUserName() {
        return userName;
    }

    /**
     Method setUserName() sets userName.
     @param userName String.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     Method getPassword() gets the password.
     @return password String.
     */
    public String getPassword() {
        return password;
    }

    /**
     Method setPassword() sets password.
     @param password String.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     Method getService() gets the service type.
     @return service String.
     */
    public String getService() {
        return service;
    }

    /**
     Method setService() sets service.
     @param service String.
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     Method getPrefix() gets the prefix.
     @return prefix String.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     Method setPrefix() sets prefix.
     @param prefix String.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     Method getProtocol() gets the protocol.
     @return protocol String.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     Method setProtocol() sets protocol.
     @param protocol String.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     Method getHostName() gets the hostName.
     @return hostName String.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     Method setHostName() sets hostName.
     @param hostName String.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     Method getAccessLicenseNumber() gets the accessLicenseNumber.
     @return accessLicenseNumber String.
     */
    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    /**
     Method setAccessLicenseNumber() sets accessLicenseNumber.
     @param accessLicenseNumber String.
     */
    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }


    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }
} // end of AascTrackingOrderInfo class
