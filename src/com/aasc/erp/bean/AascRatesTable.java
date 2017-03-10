package com.aasc.erp.bean;

/*
 * @(#)AascCustomerInfo.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 
 /**
  * AascRatesTable is a class which is used to show rate details in Make Payment page.
  * 
  * @author Suman Gunda
  * 
  * @version 1.0
  * 
  * @since 01/03/2016
  * History
  * 
*/

public class AascRatesTable {
    
    public AascRatesTable() {
    }
    
    private String customerType = "";
    private String transactionRange = "";
    private String durationType = "";
    private String totalAmount = "";
    private String pricePerPkg = "";


    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setTransactionRange(String transactionRange) {
        this.transactionRange = transactionRange;
    }

    public String getTransactionRange() {
        return transactionRange;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setPricePerPkg(String pricePerPkg) {
        this.pricePerPkg = pricePerPkg;
    }

    public String getPricePerPkg() {
        return pricePerPkg;
    }
}
