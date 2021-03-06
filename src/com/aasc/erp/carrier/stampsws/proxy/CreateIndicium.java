// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class CreateIndicium implements java.io.Serializable {
    protected java.lang.String authenticator;
    protected com.aasc.erp.carrier.stampsws.proxy.Credentials credentials;
    protected java.lang.String integratorTxID;
    protected java.lang.String trackingNumber;
    protected com.aasc.erp.carrier.stampsws.proxy.RateV14 rate;
    protected com.aasc.erp.carrier.stampsws.proxy.Address from;
    protected com.aasc.erp.carrier.stampsws.proxy.Address to;
    protected java.lang.String customerID;
    protected com.aasc.erp.carrier.stampsws.proxy.CustomsV2 customs;
    protected java.lang.Boolean sampleOnly;
    protected com.aasc.erp.carrier.stampsws.proxy.ImageType imageType;
    protected com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType eltronPrinterDPIType;
    protected java.lang.String memo;
    protected java.lang.Integer cost_code_id;
    protected java.lang.Boolean deliveryNotification;
    protected com.aasc.erp.carrier.stampsws.proxy.ShipmentNotification shipmentNotification;
    protected java.lang.Integer rotationDegrees;
    protected java.lang.Integer horizontalOffset;
    protected java.lang.Integer verticalOffset;
    protected java.lang.Integer printDensity;
    protected java.lang.Boolean printMemo;
    protected java.lang.Boolean printInstructions;
    protected java.lang.Boolean requestPostageHash;
    protected com.aasc.erp.carrier.stampsws.proxy.NonDeliveryOption nonDeliveryOption;
    protected com.aasc.erp.carrier.stampsws.proxy.Address redirectTo;
    protected java.lang.String originalPostageHash;
    protected java.lang.Boolean returnImageData;
    protected java.lang.String internalTransactionNumber;
    protected com.aasc.erp.carrier.stampsws.proxy.PaperSizeV1 paperSize;
    protected com.aasc.erp.carrier.stampsws.proxy.LabelRecipientInfo emailLabelTo;
    
    public CreateIndicium() {
    }
    
    public java.lang.String getAuthenticator() {
        return authenticator;
    }
    
    public void setAuthenticator(java.lang.String authenticator) {
        this.authenticator = authenticator;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Credentials getCredentials() {
        return credentials;
    }
    
    public void setCredentials(com.aasc.erp.carrier.stampsws.proxy.Credentials credentials) {
        this.credentials = credentials;
    }
    
    public java.lang.String getIntegratorTxID() {
        return integratorTxID;
    }
    
    public void setIntegratorTxID(java.lang.String integratorTxID) {
        this.integratorTxID = integratorTxID;
    }
    
    public java.lang.String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(java.lang.String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.RateV14 getRate() {
        return rate;
    }
    
    public void setRate(com.aasc.erp.carrier.stampsws.proxy.RateV14 rate) {
        this.rate = rate;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Address getFrom() {
        return from;
    }
    
    public void setFrom(com.aasc.erp.carrier.stampsws.proxy.Address from) {
        this.from = from;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Address getTo() {
        return to;
    }
    
    public void setTo(com.aasc.erp.carrier.stampsws.proxy.Address to) {
        this.to = to;
    }
    
    public java.lang.String getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(java.lang.String customerID) {
        this.customerID = customerID;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.CustomsV2 getCustoms() {
        return customs;
    }
    
    public void setCustoms(com.aasc.erp.carrier.stampsws.proxy.CustomsV2 customs) {
        this.customs = customs;
    }
    
    public java.lang.Boolean getSampleOnly() {
        return sampleOnly;
    }
    
    public void setSampleOnly(java.lang.Boolean sampleOnly) {
        this.sampleOnly = sampleOnly;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.ImageType getImageType() {
        return imageType;
    }
    
    public void setImageType(com.aasc.erp.carrier.stampsws.proxy.ImageType imageType) {
        this.imageType = imageType;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType getEltronPrinterDPIType() {
        return eltronPrinterDPIType;
    }
    
    public void setEltronPrinterDPIType(com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType eltronPrinterDPIType) {
        this.eltronPrinterDPIType = eltronPrinterDPIType;
    }
    
    public java.lang.String getMemo() {
        return memo;
    }
    
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }
    
    public java.lang.Integer getCost_code_id() {
        return cost_code_id;
    }
    
    public void setCost_code_id(java.lang.Integer cost_code_id) {
        this.cost_code_id = cost_code_id;
    }
    
    public java.lang.Boolean getDeliveryNotification() {
        return deliveryNotification;
    }
    
    public void setDeliveryNotification(java.lang.Boolean deliveryNotification) {
        this.deliveryNotification = deliveryNotification;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.ShipmentNotification getShipmentNotification() {
        return shipmentNotification;
    }
    
    public void setShipmentNotification(com.aasc.erp.carrier.stampsws.proxy.ShipmentNotification shipmentNotification) {
        this.shipmentNotification = shipmentNotification;
    }
    
    public java.lang.Integer getRotationDegrees() {
        return rotationDegrees;
    }
    
    public void setRotationDegrees(java.lang.Integer rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }
    
    public java.lang.Integer getHorizontalOffset() {
        return horizontalOffset;
    }
    
    public void setHorizontalOffset(java.lang.Integer horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }
    
    public java.lang.Integer getVerticalOffset() {
        return verticalOffset;
    }
    
    public void setVerticalOffset(java.lang.Integer verticalOffset) {
        this.verticalOffset = verticalOffset;
    }
    
    public java.lang.Integer getPrintDensity() {
        return printDensity;
    }
    
    public void setPrintDensity(java.lang.Integer printDensity) {
        this.printDensity = printDensity;
    }
    
    public java.lang.Boolean getPrintMemo() {
        return printMemo;
    }
    
    public void setPrintMemo(java.lang.Boolean printMemo) {
        this.printMemo = printMemo;
    }
    
    public java.lang.Boolean getPrintInstructions() {
        return printInstructions;
    }
    
    public void setPrintInstructions(java.lang.Boolean printInstructions) {
        this.printInstructions = printInstructions;
    }
    
    public java.lang.Boolean getRequestPostageHash() {
        return requestPostageHash;
    }
    
    public void setRequestPostageHash(java.lang.Boolean requestPostageHash) {
        this.requestPostageHash = requestPostageHash;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.NonDeliveryOption getNonDeliveryOption() {
        return nonDeliveryOption;
    }
    
    public void setNonDeliveryOption(com.aasc.erp.carrier.stampsws.proxy.NonDeliveryOption nonDeliveryOption) {
        this.nonDeliveryOption = nonDeliveryOption;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.Address getRedirectTo() {
        return redirectTo;
    }
    
    public void setRedirectTo(com.aasc.erp.carrier.stampsws.proxy.Address redirectTo) {
        this.redirectTo = redirectTo;
    }
    
    public java.lang.String getOriginalPostageHash() {
        return originalPostageHash;
    }
    
    public void setOriginalPostageHash(java.lang.String originalPostageHash) {
        this.originalPostageHash = originalPostageHash;
    }
    
    public java.lang.Boolean getReturnImageData() {
        return returnImageData;
    }
    
    public void setReturnImageData(java.lang.Boolean returnImageData) {
        this.returnImageData = returnImageData;
    }
    
    public java.lang.String getInternalTransactionNumber() {
        return internalTransactionNumber;
    }
    
    public void setInternalTransactionNumber(java.lang.String internalTransactionNumber) {
        this.internalTransactionNumber = internalTransactionNumber;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.PaperSizeV1 getPaperSize() {
        return paperSize;
    }
    
    public void setPaperSize(com.aasc.erp.carrier.stampsws.proxy.PaperSizeV1 paperSize) {
        this.paperSize = paperSize;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.LabelRecipientInfo getEmailLabelTo() {
        return emailLabelTo;
    }
    
    public void setEmailLabelTo(com.aasc.erp.carrier.stampsws.proxy.LabelRecipientInfo emailLabelTo) {
        this.emailLabelTo = emailLabelTo;
    }
}
