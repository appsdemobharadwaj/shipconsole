/*
 * @(#)AascGetLocInfo.java        05/09/2007
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.bean;


import java.util.LinkedList;

/**
 * Bean Class to hold organization values.
 * Module with getXXX() and setXXX() methods. 
 * @author 	Dedeepya Boppudi
 * @version - 2
 * */
public class AascGetLocInfo {
    private int locationId;
    private String locationName;
    private LinkedList locationDetails;

    public int getlocationId() {
        return locationId;
    }

    public void setlocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setlocationName(String locationName) {
        this.locationName = locationName;
    }

    public LinkedList getlocationDetails() {
        return locationDetails;
    }

    public void setlocationDetails(LinkedList locationDetails) {
        this.locationDetails = locationDetails;
    }

}

