package com.aasc.erp.bean;

import java.util.HashMap;

/**
 * AascCarrierConfigurationBean class is action class for Carrier COnfiguration.
 * @version   2
 * @author    Venkateswaramma Malluru
 * @History
 *
 * 07/01/2015   Y Pradeep   Added History section and code alignment.
 * 17/11/2015   Shiva G     Added dhlRegionCode for DHL
 * 
 * */
public class AascCarrierConfigurationBean {
    public AascCarrierConfigurationBean() {
    }
    private String carrierName;
    private int clientId;
    private int userId;
    private int locationId;
    private int carrierCodeValue;
    private String enableFlag = ""; //holds the value of enableFlag
    private String protocol = 
        ""; // holds the value set by the setProtocol(String protocol) method
    private int port; // holds the value set by the setPort(int port) method
    private String carrierMode = ""; //holds the value of carrierMode
    private String serverIpAddress = 
        ""; // holds the value set by the setServerIpAddress(String serverIpAddress) method
    private String userName = 
        ""; // holds the value set by the setUserName(String userName) method
    private String password = 
        ""; // holds the value set by the setPassword(String password) method
    private String prefix = 
        ""; // holds the value set by the setPrefix(String prefix) method
    private String accountNo = 
        ""; // holds the value set by the setAccountNo(String accountNo) method
    private String domain = 
        ""; // holds the value set by the setDomain(String domain) method
    private String supportHazmatShipping = "";

    private String accessLicenseNumber = 
        ""; // holds value for accessLicenseNumber which is used to send to ups server
    private String meterNumber;
    private String nonDiscountedCost;
    private String acctNumNegotiatedFlag = "";

    private String pdPort = 
        ""; // holds the value set by the setPdPort(String pdPort) method  
    private String modelSymbol = 
        ""; // holds the value set by the setModelSymbol(String modelSymbol) method
    private String stockSymbol = 
        ""; // holds the value set by the setStockSymbol(String stockSymbol) method

    private String labelStock;
    private String docTab;
    private int carrierId;
       
    //For Email Notification
    private String emailNotificationFlag = "";
    private String senderEmailAddress = "";
    private String recepientEmailAddress1 = "";
    private String recepientEmailAddress2 = "";
    private String recepientEmailAddress3 = "";
    private String recepientEmailAddress4 = "";
    private String recepientEmailAddress5 = "";
    private String referenceFlag1 = "";
    private String referenceFlag2 = "";
    private String shipNotificationFlag = "";
    private String deliveryNotificationFlag = "";
    private String exceptionNotificationFlag = "";
    private String formatType = "";

    //SC_EMail_SJ
     private String salesOrderNumber = "";
     private String customerName = "";
     private String deliveryItemNumbers = "";
     private String op900Format = "";  // holds op900 label format
     
      private String integrationId = "";
//      private String intlLabelFormat = "";
      private String paperSize = "";
      private String intlPrintLayout = "";
      private String nonDelivery = "";

      private String dhlRegionCode="";        



    public void setCarrierCodeValue(int carrierCodeValue) {
        this.carrierCodeValue = carrierCodeValue;
    }

    public int getCarrierCodeValue() {
        return carrierCodeValue;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setCarrierMode(String carrierMode) {
        this.carrierMode = carrierMode;
    }

    public String getCarrierMode() {
        return carrierMode;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }

    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setNonDiscountedCost(String nonDiscountedCost) {
        this.nonDiscountedCost = nonDiscountedCost;
    }

    public String getNonDiscountedCost() {
        return nonDiscountedCost;
    }

    public void setAcctNumNegotiatedFlag(String acctNumNegotiatedFlag) {
        this.acctNumNegotiatedFlag = acctNumNegotiatedFlag;
    }

    public String getAcctNumNegotiatedFlag() {
        return acctNumNegotiatedFlag;
    }

    public void setPdPort(String pdPort) {
        this.pdPort = pdPort;
    }

    public String getPdPort() {
        return pdPort;
    }

    public void setModelSymbol(String modelSymbol) {
        this.modelSymbol = modelSymbol;
    }

    public String getModelSymbol() {
        return modelSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setLabelStock(String labelStock) {
        this.labelStock = labelStock;
    }

    public String getLabelStock() {
        return labelStock;
    }

    public void setDocTab(String docTab) {
        this.docTab = docTab;
    }

    public String getDocTab() {
        return docTab;
    }

    public void setEmailNotificationFlag(String emailNotificationFlag) {
        this.emailNotificationFlag = emailNotificationFlag;
    }

    public String getEmailNotificationFlag() {
        return emailNotificationFlag;
    }

    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public void setRecepientEmailAddress1(String recepientEmailAddress1) {
        this.recepientEmailAddress1 = recepientEmailAddress1;
    }

    public String getRecepientEmailAddress1() {
        return recepientEmailAddress1;
    }

    public void setRecepientEmailAddress2(String recepientEmailAddress2) {
        this.recepientEmailAddress2 = recepientEmailAddress2;
    }

    public String getRecepientEmailAddress2() {
        return recepientEmailAddress2;
    }

    public void setRecepientEmailAddress3(String recepientEmailAddress3) {
        this.recepientEmailAddress3 = recepientEmailAddress3;
    }

    public String getRecepientEmailAddress3() {
        return recepientEmailAddress3;
    }

    public void setRecepientEmailAddress4(String recepientEmailAddress4) {
        this.recepientEmailAddress4 = recepientEmailAddress4;
    }

    public String getRecepientEmailAddress4() {
        return recepientEmailAddress4;
    }

    public void setRecepientEmailAddress5(String recepientEmailAddress5) {
        this.recepientEmailAddress5 = recepientEmailAddress5;
    }

    public String getRecepientEmailAddress5() {
        return recepientEmailAddress5;
    }

    public void setReferenceFlag1(String referenceFlag1) {
        this.referenceFlag1 = referenceFlag1;
    }

    public String getReferenceFlag1() {
        return referenceFlag1;
    }

    public void setReferenceFlag2(String referenceFlag2) {
        this.referenceFlag2 = referenceFlag2;
    }

    public String getReferenceFlag2() {
        return referenceFlag2;
    }

    public void setShipNotificationFlag(String shipNotificationFlag) {
        this.shipNotificationFlag = shipNotificationFlag;
    }

    public String getShipNotificationFlag() {
        return shipNotificationFlag;
    }

    public void setDeliveryNotificationFlag(String deliveryNotificationFlag) {
        this.deliveryNotificationFlag = deliveryNotificationFlag;
    }

    public String getDeliveryNotificationFlag() {
        return deliveryNotificationFlag;
    }

    public void setExceptionNotificationFlag(String exceptionNotificationFlag) {
        this.exceptionNotificationFlag = exceptionNotificationFlag;
    }

    public String getExceptionNotificationFlag() {
        return exceptionNotificationFlag;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setDeliveryItemNumbers(String deliveryItemNumbers) {
        this.deliveryItemNumbers = deliveryItemNumbers;
    }

    public String getDeliveryItemNumbers() {
        return deliveryItemNumbers;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setSupportHazmatShipping(String supportHazmatShipping) {
        this.supportHazmatShipping = supportHazmatShipping;
    }

    public String getSupportHazmatShipping() {
        return supportHazmatShipping;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setOp900Format(String op900Format) {
        this.op900Format = op900Format;
    }

    public String getOp900Format() {
        return op900Format;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    public String getIntegrationId() {
        return integrationId;
    }

//    public void setIntlLabelFormat(String intlLabelFormat) {
//        this.intlLabelFormat = intlLabelFormat;
//    }
//
//    public String getIntlLabelFormat() {
//        return intlLabelFormat;
//    }

    public void setPaperSize(String paperSize) {
        this.paperSize = paperSize;
    }

    public String getPaperSize() {
        return paperSize;
    }

    public void setIntlPrintLayout(String intlPrintLayout) {
        this.intlPrintLayout = intlPrintLayout;
    }

    public String getIntlPrintLayout() {
        return intlPrintLayout;
    }

    public void setNonDelivery(String nonDelivery) {
        this.nonDelivery = nonDelivery;
    }

    public String getNonDelivery() {
        return nonDelivery;
    }

    public void setDhlRegionCode(String dhlRegionCode) {
        this.dhlRegionCode = dhlRegionCode;
    }

    public String getDhlRegionCode() {
        return dhlRegionCode;
    }
}
