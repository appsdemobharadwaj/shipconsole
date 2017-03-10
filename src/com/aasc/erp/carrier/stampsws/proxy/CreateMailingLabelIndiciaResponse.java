// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class CreateMailingLabelIndiciaResponse implements java.io.Serializable {
    protected java.lang.String authenticator;
    protected java.lang.String stampsTxId;
    protected java.lang.String url;
    protected java.lang.String[] confirmationNumbers;
    protected com.aasc.erp.carrier.stampsws.proxy.PostageBalance postageBalance;
    protected int issuedLabelCount;
    protected java.lang.String errorReason;
    protected java.lang.String mac;
    
    public CreateMailingLabelIndiciaResponse() {
    }
    
    public java.lang.String getAuthenticator() {
        return authenticator;
    }
    
    public void setAuthenticator(java.lang.String authenticator) {
        this.authenticator = authenticator;
    }
    
    public java.lang.String getStampsTxId() {
        return stampsTxId;
    }
    
    public void setStampsTxId(java.lang.String stampsTxId) {
        this.stampsTxId = stampsTxId;
    }
    
    public java.lang.String getUrl() {
        return url;
    }
    
    public void setUrl(java.lang.String url) {
        this.url = url;
    }
    
    public java.lang.String[] getConfirmationNumbers() {
        return confirmationNumbers;
    }
    
    public void setConfirmationNumbers(java.lang.String[] confirmationNumbers) {
        this.confirmationNumbers = confirmationNumbers;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.PostageBalance getPostageBalance() {
        return postageBalance;
    }
    
    public void setPostageBalance(com.aasc.erp.carrier.stampsws.proxy.PostageBalance postageBalance) {
        this.postageBalance = postageBalance;
    }
    
    public int getIssuedLabelCount() {
        return issuedLabelCount;
    }
    
    public void setIssuedLabelCount(int issuedLabelCount) {
        this.issuedLabelCount = issuedLabelCount;
    }
    
    public java.lang.String getErrorReason() {
        return errorReason;
    }
    
    public void setErrorReason(java.lang.String errorReason) {
        this.errorReason = errorReason;
    }
    
    public java.lang.String getMac() {
        return mac;
    }
    
    public void setMac(java.lang.String mac) {
        this.mac = mac;
    }
}