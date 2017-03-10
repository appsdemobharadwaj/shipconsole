/*
 * @(#)AascFSCarrierServiceLevels.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;
/**
 * AascFSCarrierServiceLevels is a class which is used as a bean for service levels in freight shop response.
 * 
 * @author Suman Gunda
 * 
 * @version 1.0
 * 
 * @since 24/02/2015
 * 
 */

public class AascFSCarrierServiceLevels {
    public AascFSCarrierServiceLevels() {
    }
    
    private String carrierServiceCode = "";
    private String carrierServiceName = "";
    private String shippingRate="";
    private String shippingTransitTime="";
    private String sortOrder="";


    public void setShippingRate(String shippingRate) {
        this.shippingRate = shippingRate;
    }

    public String getShippingRate() {
        return shippingRate;
    }

    public void setShippingTransitTime(String shippingTransitTime) {
        this.shippingTransitTime = shippingTransitTime;
    }

    public String getShippingTransitTime() {
        return shippingTransitTime;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setCarrierServiceCode(String carrierServiceCode) {
        this.carrierServiceCode = carrierServiceCode;
    }

    public String getCarrierServiceCode() {
        return carrierServiceCode;
    }

    public void setCarrierServiceName(String carrierServiceName) {
        this.carrierServiceName = carrierServiceName;
    }

    public String getCarrierServiceName() {
        return carrierServiceName;
    }
}
