/*
 * @(#)AascFreightShopInfo.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 package com.aasc.erp.bean;

/**
 * AascFreightShopInfo is a class which is used as a bean for response of freight shop.
 * 
 * @author Suman Gunda
 * 
 * @version 1.0
 * 
 * @since 24/02/2015
 * 
 */

import java.util.LinkedList;

public class AascFreightShopInfo {
    public AascFreightShopInfo() {
    }
    
    private LinkedList carrierServiceLevels;
    private LinkedList carriersIncluded;
    private AascFSResponseStatus responseStatus;


    public void setCarrierServiceLevels(LinkedList carrierServiceLevels) {
        this.carrierServiceLevels = carrierServiceLevels;
    }

    public LinkedList getCarrierServiceLevels() {
        return carrierServiceLevels;
    }

    public void setCarriersIncluded(LinkedList carriersIncluded) {
        this.carriersIncluded = carriersIncluded;
    }

    public LinkedList getCarriersIncluded() {
        return carriersIncluded;
    }

    public void setResponseStatus(AascFSResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public AascFSResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
