// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class CreditCard implements java.io.Serializable {
    protected com.aasc.erp.carrier.stampsws.proxy.CreditCardType creditCardType;
    protected java.lang.String accountNumber;
    protected java.lang.String CVN;
    protected java.util.Calendar expirationDate;
    protected com.aasc.erp.carrier.stampsws.proxy.Address billingAddress;
    
    public CreditCard() {
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.CreditCardType getCreditCardType() {
        return creditCardType;
    }
    
    public void setCreditCardType(com.aasc.erp.carrier.stampsws.proxy.CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }
    
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public java.lang.String getCVN() {
        return CVN;
    }
    
    public void setCVN(java.lang.String CVN) {
        this.CVN = CVN;
    }
    
    public java.util.Calendar getExpirationDate() {
        return expirationDate;
    }
    
    public void setExpirationDate(java.util.Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Address getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(com.aasc.erp.carrier.stampsws.proxy.Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}