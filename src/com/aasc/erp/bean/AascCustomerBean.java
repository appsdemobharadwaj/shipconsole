/*
 * @(#)AascCustomerInfo.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

/**
 * AascCustomerBean is a class which is used as a bean for create customer page.
 * 
 * @author Suman Gunda
 * 
 * @version 1.0
 * 
 * @since 24/07/2014
 * History
 * 10/03/2015  Sunanda removed Profile Options related code
 * 24/08/2015  Jagadish Jain added code for pricing details.
 * 21/10/2015  Suman G      Removed unused fields.
 * 28/10/2015  Suman G      Changed Transaction_Balance to Current_Balance to fix issue.
 * 24/02/2015  Suman G      Changed bean for new Transaction Management design.
 * 02/03/2016  Suman G      Added subscriptionExpiryFlag field for disabling Transaction based radio button whever validity is expired
 */
public class AascCustomerBean {

    public AascCustomerBean() {
    }
    
    private String firstName = "";
    private String lastName = "";
    private String status="";
    private String cloudLabelPath;
    
    private String companyName = "";
    private String contactName = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String state = "";
    private String postalCode;
    private String countryCode=""; 
    private String phoneNumber = "";
    private String emailAddress;
    
    private String makePayment="";
    private String customerType = "";
    private String invoiceType = "";
    private String monthlyEstimatedTransactionRange = "";
    private String pricingDuration = "";
    private String customerStartDate = "";
    private String subscriptionEndDate = "";
    private double totalFee = 0.0;
    private int cumulativePackageCount = 0;
    private int currentPackageBalance = 0;
    private String subscriptionExpiryFlag = "";
    
    private int transactionCount = 0;
    
    private int clientID;
    private String userName = "";
    private String password="";


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCloudLabelPath(String cloudLabelPath) {
        this.cloudLabelPath = cloudLabelPath;
    }

    public String getCloudLabelPath() {
        return cloudLabelPath;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setSubscriptionExpiryFlag(String subscriptionExpiryFlag) {
        this.subscriptionExpiryFlag = subscriptionExpiryFlag;
    }

    public String getSubscriptionExpiryFlag() {
        return subscriptionExpiryFlag;
    }
}
