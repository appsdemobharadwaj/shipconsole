package com.aasc.erp.bean;

/*
 * @(#)AascCustomerInfo.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 
 /**
  * AascSubscriptionDetailsBean is a class which is used as a bean for create customer page.
  * 
  * @author Suman Gunda
  * 
  * @version 1.0
  * 
  * @since 25/02/2016
  * History
  * 
*/
public class AascSubscriptionDetailsBean {

    public AascSubscriptionDetailsBean() {
    }
    
    private String makePayment="";
    private String customerType = "";
    private String invoiceType = "";
    private String monthlyEstimatedTransactionRange = "";
    private String pricingDuration = "";
    private String customerStartDate = "";
    private String subscriptionEndDate = "";
    private double totalFee = 0.0;
    private int cumulativePackageCount;
    private int currentPackageBalance;
    private String subscriptionExpiryFlag = "";
    
    private int transactionCount;
    
    private int clientID;


    public void setMakePayment(String makePayment) {
        this.makePayment = makePayment;
    }

    public String getMakePayment() {
        return makePayment;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setMonthlyEstimatedTransactionRange(String monthlyEstimatedTransactionRange) {
        this.monthlyEstimatedTransactionRange = monthlyEstimatedTransactionRange;
    }

    public String getMonthlyEstimatedTransactionRange() {
        return monthlyEstimatedTransactionRange;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setCustomerStartDate(String customerStartDate) {
        this.customerStartDate = customerStartDate;
    }

    public String getCustomerStartDate() {
        return customerStartDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setCumulativePackageCount(int cumulativePackageCount) {
        this.cumulativePackageCount = cumulativePackageCount;
    }

    public int getCumulativePackageCount() {
        return cumulativePackageCount;
    }

    public void setCurrentPackageBalance(int currentPackageBalance) {
        this.currentPackageBalance = currentPackageBalance;
    }

    public int getCurrentPackageBalance() {
        return currentPackageBalance;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setSubscriptionExpiryFlag(String subscriptionExpiryFlag) {
        this.subscriptionExpiryFlag = subscriptionExpiryFlag;
    }

    public String getSubscriptionExpiryFlag() {
        return subscriptionExpiryFlag;
    }
}
