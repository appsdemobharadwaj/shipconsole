/*
 * @(#)AascFSResponseStatus.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 package com.aasc.erp.bean;

/**
 * AascFSResponseStatus is a class which is used as a bean for response of freight shop.
 * 
 * @author Suman Gunda
 * 
 * @version 1.0
 * 
 * @since 24/02/2015
 * 
 */
public class AascFSResponseStatus {
    public AascFSResponseStatus() {
    }
    
    private String code="";
    private String description = "";


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
