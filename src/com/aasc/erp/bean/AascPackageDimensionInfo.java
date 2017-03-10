/*
  * @(#)AascOraclePackageDimensionInfo.java
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 * ====================================================================
 * This class is used to retrive the package dimensions Information
 @History
 17/11/2014    Sunanda.k    added the following code from ShipConsoleCloud version 1.2 
 */
package com.aasc.erp.bean;

import java.util.LinkedList;

public class AascPackageDimensionInfo {
    private String dimensionName;
    private int dimensionLength;
    private int dimensionWidth;
    private int dimensionHeight;
    private String dimensionUnits;
    private String dimensionActive;
    private String dimensionDefault;
    private int dimensionId;
    private int locationId;
    private String actionType;
    private int errorStatus;
    private LinkedList unitDetails;
    private String errorMessage;

    LinkedList packageDimensionList;

    private LinkedList hazardousUnitDetails;

    private LinkedList hazardousMatIdDetails;

    private LinkedList packageUOMDetails;

    public AascPackageDimensionInfo() {
        packageDimensionList = new LinkedList();
        
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public int getDimensionLength() {
        return dimensionLength;
    }

    public void setDimensionLength(int dimensionLength) {
        this.dimensionLength = dimensionLength;
    }

    public int getDimensionWidth() {
        return dimensionWidth;
    }

    public void setDimensionWidth(int dimensionWidth) {
        this.dimensionWidth = dimensionWidth;
    }

    public int getDimensionHeight() {
        return dimensionHeight;
    }

    public void setDimensionHeight(int dimensionHeight) {
        this.dimensionHeight = dimensionHeight;
    }

    public String getDimensionUnits() {
        return dimensionUnits;
    }

    public void setDimensionUnits(String dimensionUnits) {
        this.dimensionUnits = dimensionUnits;
    }

    public String getDimensionActive() {
        return dimensionActive;
    }

    public void setDimensionActive(String dimensionActive) {
        this.dimensionActive = dimensionActive;
    }

    public String getDimensionDefault() {
        return dimensionDefault;
    }

    public void setDimensionDefault(String dimensionDefault) {
        this.dimensionDefault = dimensionDefault;
    }

    public LinkedList getPackageDimensionList() {
        return packageDimensionList;
    }

    public void setPackageDimensionList(LinkedList packageDimensionList) {
        this.packageDimensionList = packageDimensionList;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public LinkedList getUnitDetails() {
        return unitDetails;
    }

    public void setUnitDetails(LinkedList unitDetails) {
        this.unitDetails = unitDetails;
    }


    public void setHazardousUnitDetails(LinkedList hazardousUnitDetails) {
        this.hazardousUnitDetails = hazardousUnitDetails;
    }

    public LinkedList getHazardousUnitDetails() {
        return hazardousUnitDetails;
    }


    public void setHazardousMatIdDetails(LinkedList hazardousMatIdDetails) {
        this.hazardousMatIdDetails = hazardousMatIdDetails;
    }

    public LinkedList getHazardousMatIdDetails() {
        return hazardousMatIdDetails;
    }

    public LinkedList getPackageUOMDetails() {
        return packageUOMDetails;
    }

    public void setPackageUOMDetails(LinkedList packageUOMDetails) {
        this.packageUOMDetails = packageUOMDetails;
    }

}
