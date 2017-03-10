/*
 * @(#)AascFSCarriersIncluded.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 package com.aasc.erp.bean;

/**
 * AascFSCarriersIncluded is a class which is used as a bean for carriers in freight shop response.
 * 
 * @author Suman Gunda
 * 
 * @version 1.0
 * 
 * @since 24/02/2015
 * 
 */

public class AascFSCarriersIncluded {
    public AascFSCarriersIncluded() {
    }
    
    private String carrier="";
    private String carrierStatus="";


    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrierStatus(String carrierStatus) {
        this.carrierStatus = carrierStatus;
    }

    public String getCarrierStatus() {
        return carrierStatus;
    }
}
