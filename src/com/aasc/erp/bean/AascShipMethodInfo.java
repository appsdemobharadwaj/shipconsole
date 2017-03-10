/*
 * @(#)AascShipMethodInfo.java        
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

import com.aasc.erp.util.AascLogger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;


/**
 Bean class that contains getXXX() and setXX() methods for shipmethod details,country details,
 carrier pay method details,carrier configuration details,dropOffType and packaging details.
 @author 
 @version 1.0
 */
public class

/* @since                                                                                                                  
 * 07/01/2015   Y Paradeep         Added proper java doc.
 * 19/01/2015   Suman G            Removed unncessary comments in history
 * 24/02/2015   Y Pradeep          Modified code for Address Validation to get Carrier account number, meter number, access licence number, username and password based on carrier code.
 * 05/11/2015   Suman G            Added getCodeIntegrationId() for Stamps FS.
 * */ 


AascShipMethodInfo {
    private int carrierId = 0; // indicates the carrier id
    
    private String carrierName = 
        ""; // indicates the carrier name which is used for shipment
    private String shipMethodMeaning = 
        ""; // indicates the ship method used for shipment
    private String shipMethod = ""; // indicates the ship method meaning
     private String shipExecScsTag = ""; // indicates ShipExec Scs Tag
    private String connectShipScsTag = ""; // indicates ConnectShip Scs Tag
    private String carrierServiceLevel = 
        ""; // indicates the level at which service is provided    
    private String carrierPaymentTerms = ""; // indicates carrier payment terms
    private String connectShipFreightPayTerms = 
        ""; // indicates connect ship freight pay terms
    private String saturdayShipFlag = 
        ""; // indicates true if delivery is to be shipped on saturday   
    private String countryCode = ""; // indicates country code
    private String countryName = ""; // indicates country name       
    
    private String currencyCode = ""; //SC_UPS Shipping - indicates currency code to pass in request
    
      private String currencyName = "";//SC_UPS Shipping - indicates currency name to dispaly
    private String error;
    
    private LinkedList shipMethodDetailList = 
        null; // linked list containing ship method details
    private LinkedList shipMethodList = 
        null; // linked list containing ship method names
    private LinkedList countryDetailList = 
        null; // linked list containing country details
    private LinkedList carrierDetailList = 
        null; // linked list containing carrier details
    private LinkedList carrierPayDetails = 
        null; // linked list containing carrier pay details
    private LinkedList carrierConfigList = 
        null; // linked list containing carrier configuration details
    private LinkedList dropOffTypeList = 
        null; // linked list containing carrier codes and droppOffTypes
    private LinkedList packagingList = 
        null; // linked list containing carrier codes and packaging
    private LinkedList shipMethodDomesticList = 
        null; // linked list containing domestic shipment details
    private LinkedList intlDetailList = 
        null; // linked list containing international shipment details   
    private String carrierPayCode = ""; // indicates carrier pay code
    private String carrierPayTerm = ""; // indicates carrier pay term
    private String csCarrierPayTerm = ""; // indicates cs carrier pay term
    private ListIterator listIterator = 
        null; // list iterator object to traverse through linked list 
    private String errorStatus = ""; // for holding error messages
    private int carrierCode = 
        0; // 3 digit carrier code to uniquely identify each carrier
    private String shipmentValidationCode = 
        ""; // code that indicates different validations that are required to be made before shipment
private int shipmethodIndex;
    // variables that hold carrier configuration settings details
    private String carrierPort = "";
    private int clientid;
    private String carrierServerIPAddress = "";
    private String carrierUserName = "";
    private String carrierPassword = "";
    private String carrierPrefix = "";
    private String carrierAccountNumber = "";
    private String printerPort = "";
    private String printerModelSymbol = "";
    private String printerStockSymbol = "";
    private String printerEODPort = "";
    private String meterNumber = "";
    private String protocol = "";
    private String dropOffType = "";
    private String packaging = "";
    private String carrierHubId = ""; //for SmartPost
    // variables holding ship method mapping details
    private String enabledFlag = "";
    private String alternateShipMethod = "";
    private String carrierNameSelected = "";
    private String intlShipFlag = "";

    private String shipFromPhoneNumber = "";
    private int opStatus = 0;
    private String statusDesc = "";
    private String accessLicenseNumber; // added on 23/05/07
    private String labelStock;
    private String dhlRegionCode="";
    private String docTab;
    private String nonDiscountedCost;
    private String acctNegotiatedCertified;
    private int locationid;
    private int userid;

    private String validation;

    private LinkedList carrierServiceList = null;

    private String meterNumberMain = "";
    private String carrierAccountNumberMain = "";
    private String accessLicenseNumberMain = "";

    private String carrierMode = ""; // added by rajesh
    private String domain = ""; //added by rajesh
//SC_7.0_SKP
     private String eodFormat;// Added this field for Elkay enhancement.
     
     private String eodDocPrinterSymbol;
     
     private String eodDocStockSymbol;
     
     private String eodDocPort;
     
     private String intlLabelFormat;
     
     private String op900LabelFormat;
     
    private String supportHazmatShipping;
    private String intlDocSubType;   // added by Jagadish
    
    private String userdefinedShipmethod;
    //Mahesh Start
     private String integrationId = "";
    private String stampsPaperSize = "";
    private String intlLabelStock = "";
    private String nonDeliveryOption = "";
    private String intlPrintLayOut = "";
   //Mahesh End
     
    private static Logger logger = 
        AascLogger.getLogger("AascShipMethodInfo"); // logger object used for issuing logging requests

    /** Method to retreive ship method linked list containing
     ship method names.
     @return shipMethodList LinkedList containing ship method names.
     */
    public LinkedList getShipMethodList() {
        return shipMethodList;
    }

    /** Method to set ship method linked list into instance variable.
     @param shipMethodList is a linked list containing ship method names.
     */
    public void setShipMethodList(LinkedList shipMethodList) {
        this.shipMethodList = shipMethodList;
    }

    /** Method to set country detail linked list into instance variable.
     @param countryDetailList is a linked list containing country details
     -country name,country code.
     */
    public void setCountryDetailList(LinkedList countryDetailList) {
        this.countryDetailList = countryDetailList;
    }

    /** Method to retreive linked list containing country details(country code and country name).
     @return countryDetailList linked list containing country details.
     */
    public LinkedList getCountryDetailList() {
        return countryDetailList;
    }

    /** Method to retreive linked list containing ship method details
     (ship method name,carrier id,carrier name,scs code etc).
     @return shipMethodDetailList linked list containing ship method
     details.
     */
    public LinkedList getShipMethodDetailList() {
        return shipMethodDetailList;
    }

    /** Method to set ship method details linked list.
     @param shipMethodDetailList is a linked list containing ship method
     details. 
     */
    public void setShipMethodDetailList(LinkedList shipMethodDetailList) {
        this.shipMethodDetailList = shipMethodDetailList;
    }

    /** Method to retreive linked list containing carrier details.
     @return carrierDetailList LinkedList
     */
    public LinkedList getCarrierDetailList() {
        return carrierDetailList;
    }

    /** Method to set carrier detail linked list into instance variable.
     @param  carrierDetailList LinkedList containing carrier details.
     */
    public void setCarrierDetailList(LinkedList carrierDetailList) {
        this.carrierDetailList = carrierDetailList;
    }

    /** Method to retreive linked list containing carrier details.
     (carrierId,printer port,eod port,meter number,account number etc). 
     @return carrierConfigList LinkedList containing carrier config details
     */
    public LinkedList getCarrierConfigList() {
        return carrierConfigList;
    }

    /** Method to set carrier detail linked list into instance variable.
     @param carrierConfigList  LinkedList containing carrier config details
     */
    public void setCarrierConfigList(LinkedList carrierConfigList) {
        this.carrierConfigList = carrierConfigList;
    }

    /** Method to reteive lookup linked list containing set of
     carrier codes and droppOffTypes.
     @return lookupList LinkedList containing carrier code,droppOffType and packaging.
     */
    public LinkedList getDropOffTypeList() {
        return dropOffTypeList;
    }

    /** Method to set lookup linked list containing set of
     carrier codes and packagings.
     @param dropOffTypeList LinkedList
     */
    public void setDropOffTypeList(LinkedList dropOffTypeList) {
        this.dropOffTypeList = dropOffTypeList;
    }

    /** Method to retrieve lookup linked list containing set of
     carrier codes,droppOffTypes and packagings.
     @return lookupList LinkedList containing carrier code and packaging.
     */
    public LinkedList getPackagingList() {
        return packagingList;
    }

    /** Method to set lookup linked list containing set of
     carrier codes,droppOffTypes and packagings.
     @param packagingList LinkedList
     */
    public void setPackagingList(LinkedList packagingList) {
        this.packagingList = packagingList;
    }

    /** Method to retrieve linked list containing ship method details.
     @return shipMethodDetailList linked list containing international ship method
     details.
     */
    public LinkedList getIntlDetailList() {
        return intlDetailList;
    }

    /** Method to set ship method details linked list.
     @param intlDetailList is a linked list containing international ship method
     details. 
     */
    public void setIntlDetailList(LinkedList intlDetailList) {
        this.intlDetailList = intlDetailList;
    }

    /** Method to retrieve ship method linked list containing
     domestic ship method details.
     @return shipMethodDomesticList LinkedList containing domestic ship method details.
     */
    public LinkedList getShipMethodDomesticList() {
        return shipMethodDomesticList;
    }

    /** Method to set ship method domestic linked list into instance variable.
     @param shipMethodDomesticList is a linked list containing domestic ship method details.
     */
    public void setShipMethodDomesticList(LinkedList shipMethodDomesticList) {
        this.shipMethodDomesticList = shipMethodDomesticList;
    }

    /** Given the country name this method returns country code.
     @param countryCode String indicating country code.
     @return countryName String indicating country name.
     */
    public String getCountryName(String countryCode) {
        String countryNameTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        listIterator = getCountryDetailList().listIterator();
        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCountryCode().equalsIgnoreCase(countryCode)) {
                countryNameTemp = aascShipMethodBeanTemp.getCountryName();
                return countryNameTemp;
            }
        }
        return countryNameTemp;
    }

    /** Returns ship method bean object 
     which matches given ship method meaning.
     @param shipMethodMeaning String indicating shipMethodMeaning.
     @return result AascShipMethodInfo ship method 
     info object which contains ship method details(bean).
     */
    public AascShipMethodInfo getShipMethodInfoBean(String shipMethodMeaning) {
        AascShipMethodInfo tempAascShipMethodInfo = null;
        AascShipMethodInfo tempAascShipMethodBean = null;
        
        listIterator = getShipMethodDetailList().listIterator();

        while (listIterator.hasNext()) {
            tempAascShipMethodBean = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodBean.getShipMethodMeaning().equalsIgnoreCase(shipMethodMeaning)) {
                tempAascShipMethodInfo = tempAascShipMethodBean;
                return tempAascShipMethodInfo;
            }
        }
        return tempAascShipMethodInfo;
    }

    /** Returns carrierId given the
     ship method which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  carrierId int.
     */
    public int getCarrierId(String shipMethodMeaning) {
        int carrierIdTemp = 0;
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            carrierIdTemp = aascShipMethodBeanTemp.getCarrierId();
        }
        return carrierIdTemp;
    }

    /** Returns carrierName, given the
     ship method, which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  carrierName String.
     */
    public String getCarrierName(String shipMethodMeaning) {
        String carrierNameTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            carrierNameTemp = aascShipMethodBeanTemp.getCarrierName();
        }
        return carrierNameTemp;
    }

    /** Returns carrierName, given the
     ship method, which is retrieved from bean.
     @param  carrierId String.
     @return  carrierName String.
     */
    public String getCName(int carrierId) {
        String carrierNameTemp = "";
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getShipMethodDetailList().listIterator();
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                carrierNameTemp = tempAascShipMethodInfo.getCarrierName();
            }
        }

        return carrierNameTemp;
    }

    /** Returns shipMethodMeaning ,given the 
     ship method ,which is retrieved from bean.
     @param  alternateShipMethod String.
     @return  shipMethodMeaning String.
     */
    public String getShipMethodFromAlt(String alternateShipMethod) {
        String shipMethodFromAlt = "";
        AascShipMethodInfo tempAascShipMethodInfo = null;
        //  //System.out.println("alternateShipMethod :"+alternateShipMethod);
        int count = 0;
        listIterator = getShipMethodDetailList().listIterator();

        while (listIterator.hasNext()) {
            count = count + 1;
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getAlternateShipMethod().equalsIgnoreCase(alternateShipMethod)) {
//                logger.info("************ alternateShipMethod matched **************");    
                shipMethodFromAlt = 
                        tempAascShipMethodInfo.getShipMethodMeaning();
                return shipMethodFromAlt;
            }
        }
//        logger.info("shipMethodFromAlt :"+shipMethodFromAlt);    
        return shipMethodFromAlt;
    }

    /** Returns shipMethodMeaning ,given the 
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String containing shipMethodMeaning.
     @return  shipMethod String.
     */
    public String getShipMethod(String shipMethodMeaning) {
        String shipMethodTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            shipMethodTemp = aascShipMethodBeanTemp.getShipMethod();
        }
        return shipMethodTemp;
    }

    /** Returns carrierServiceLevel ,given the
     ship method, which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  carrierServiceLevel String.
     */
    public String getCarrierServiceLevel(String shipMethodMeaning) {
        String carrierServiceLevelTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            carrierServiceLevelTemp = 
                    aascShipMethodBeanTemp.getCarrierServiceLevel();
        }
        return carrierServiceLevelTemp;
    }

    /** Returns carrierPaymentTerms ,given the
     ship method, which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  carrierPayTerms String.
     */
    public String getCarrierPaymentTerms(String shipMethodMeaning) {
        String carrierPaymentTermsTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        carrierPaymentTermsTemp = 
                aascShipMethodBeanTemp.getCarrierPaymentTerms();
        return carrierPaymentTermsTemp;
    }

    /** Returns connectshipFreightPayTerms ,given the
     ship method, which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  connectShipFreightPayTerms String.
     */
    public String getConnectShipFreightPayTerms(String shipMethodMeaning) {
        String freightPayTermsTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            freightPayTermsTemp = 
                    aascShipMethodBeanTemp.getConnectShipFreightPayTerms();
        }
        return freightPayTermsTemp;
    }

    /** Returns ConnectShipScsTag ,given the
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  connectShipScsTag String.
     */
    public String getConnectShipScsTag(String shipMethodMeaning) {
        String connectShipScsTagTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            connectShipScsTagTemp = 
                    aascShipMethodBeanTemp.getConnectShipScsTag();
        }
        return connectShipScsTagTemp;
    }
    /** Returns ShipExecScsTag ,given the
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  ShipExecScsTag String.
     */
    public String getShipExecScsTag(String shipMethodMeaning) {
        String shipExecScsTagTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;
        LinkedList new1 = getShipMethodDetailList();
        Iterator it = new1.iterator();
        while(it.hasNext()){
        aascShipMethodBeanTemp = (AascShipMethodInfo)it.next();
        if (aascShipMethodBeanTemp != null) {
            if(aascShipMethodBeanTemp.getUserdefinedShipmethod().equalsIgnoreCase(shipMethodMeaning))
            shipExecScsTagTemp = 
                    aascShipMethodBeanTemp.getConnectShipScsTag();
        }}
        return shipExecScsTagTemp;
    }

    /** Returns SaturdayShipFlag ,given the  
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  saturdayShipFlag String.
     */
    public String getSaturdayShipFlag(String shipMethodMeaning) {
        String saturdayShipFlagTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            saturdayShipFlagTemp = 
                    aascShipMethodBeanTemp.getSaturdayShipFlag();
        }
        return saturdayShipFlagTemp;
    }

    /** Returns shipmentValidationCode ,given the  
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  shipmentValidationCode String.
     */
    public String getShipmentValidationCode(String shipMethodMeaning) {
        String shipmentValidationCodeTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            shipmentValidationCodeTemp = 
                    aascShipMethodBeanTemp.getShipmentValidationCode();
        }
        return shipmentValidationCodeTemp;
    }

    /** Returns carrierCode ,given the  
     ship method ,which is retrieved from bean.
     @param  shipMethodMeaning String.
     @return  carrierCode String.
     */
    public int getCarrierCodeFromShipMethod(String shipMethodMeaning) {
        int carrierCodeTemp = 0;
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getShipMethodInfoBean(shipMethodMeaning);
        if (aascShipMethodBeanTemp != null) {
            carrierCodeTemp = aascShipMethodBeanTemp.getCarrierCode();
        }
        return carrierCodeTemp;
    }

    /** Method to set carrierId.
     @param carrierId int.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    /** Method to get carrierId.
     @return carrierId int
     */
    public int getCarrierId() {
        return carrierId;
    }

    /** Method to set carrierName.
     @param carrierName String. 
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /** Method to get carrierName.
     @return  carrierName String.
     */
    public String getCarrierName() {
        return carrierName;
    }

    /** Method to set shipMethod.
     @param  shipMethod String.
     */
    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    /** Method to get shipMethod.
     @return  shipMethod String.
     */
    public String getShipMethod() {
        return shipMethod;
    }

    /** Method to set shipMethodMeaning.
     @param shipMethodMeaning String.
     */
    public void setShipMethodMeaning(String shipMethodMeaning) {
        this.shipMethodMeaning = shipMethodMeaning;
    }

    /** Method to get shipMethodMeaning.
     @return shipMethodMeaning String.
     */
    public String getShipMethodMeaning() {
        return shipMethodMeaning;
    }

    /** Method to set connectShipScsTag.
     @param  connectShipScsTag String.
     */
    public void setConnectShipScsTag(String connectShipScsTag) {
        this.connectShipScsTag = connectShipScsTag;
    }
    /** Method to set shipExecScsTag.
     @param  shipExecScsTag String.
     */
    public void setShipExecScsTag(String connectShipScsTag) {
        this.shipExecScsTag = shipExecScsTag;
    }
    /** Method to get connectShipScsTag.
     @return  connectShipScsTag String.
     */
    public String getConnectShipScsTag() {
        return connectShipScsTag;
    }
    /** Method to get shipExecScsTag.
     @return  shipExecScsTag String.
     */
    public String getShipExecScsTag() {
        return shipExecScsTag;
    }
    /** Method to get carrier service level.
     @return  carrierServiceLevel String.
     */
    public String getCarrierServiceLevel() {
        return carrierServiceLevel;
    }

    /** Method to set carrier service level.
     @param carrierServiceLevel String.
     */
    public void setCarrierServiceLevel(String carrierServiceLevel) {
        this.carrierServiceLevel = carrierServiceLevel;
    }

    /** Method to get carrier payment terms.
     @return  carrierPaymentTerms String.
     */
    public String getCarrierPaymentTerms() {
        return carrierPaymentTerms;
    }

    /** Method to set carrier payment terms.
     @param  carrierPaymentTerms String.
     */
    public void setCarrierPaymentTerms(String carrierPaymentTerms) {
        this.carrierPaymentTerms = carrierPaymentTerms;
    }

    /** Method to get connect ship freight pay terms.
     @return  connectShipFreightPayTerms String.
     */
    public String getConnectShipFreightPayTerms() {
        return connectShipFreightPayTerms;
    }

    /** Method to set connect ship freight pay terms.
     @param  connectShipFreightPayTerms String.
     */
    public void setConnectShipFreightPayTerms(String connectShipFreightPayTerms) {
        this.connectShipFreightPayTerms = connectShipFreightPayTerms;
    }

    /** Method to get saturday ship flag.
     @return  saturdayShipFlag String.
     */
    public String getSaturdayShipFlag() {
        return saturdayShipFlag;
    }

    /** Method to set saturday ship flag.
     @param  saturdayShipFlag String.
     */
    public void setSaturdayShipFlag(String saturdayShipFlag) {
        this.saturdayShipFlag = saturdayShipFlag;
    }

    /** Method to get country code.
     @return  countryCode String.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /** Method to set countryCode.
     @param  countryCode String.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /** Method to get countryName.
     @return  countryName String.
     */
    public String getCountryName() {
        return countryName;
    }

    /** Method to set CountryName.
     @param  countryName String.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    //SC_UPS Shipping
    
    /** Method to get countryName.
     @return  countryName String.
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /** Method to set CountryName.
     @param  currencyCode String.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    /** Method to get countryName.
     @return  countryName String.
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /** Method to set CountryName.
     @param  currencyName String.
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /** Method to get carrierPayCode.
     @return  carrierPayCode String.
     */
    public String getCarrierPayCode() {
        return carrierPayCode;
    }

    /** Method to set carrierPayCode.
     @param  carrierPayCode String.
     */
    public void setCarrierPayCode(String carrierPayCode) {
        this.carrierPayCode = carrierPayCode;
    }

    /** Method to get carrierPayTerm.
     @return  carrierPayTerm String.
     */
    public String getCarrierPayTerm() {
        return carrierPayTerm;
    }

    /** Method to set carrierPayTerm.
     @param  carrierPayTerm String.
     */
    public void setCarrierPayTerm(String carrierPayTerm) {
        this.carrierPayTerm = carrierPayTerm;
    }

    /** Method to get csCarrierPayTerm.
     @return  csCarrierPayTerm String.
     */
    public String getCsCarrierPayTerm() {
        return csCarrierPayTerm;
    }

    /** Method to set csCarrierPayTerm.
     @param  csCarrierPayTerm String.
     */
    public void setCsCarrierPayTerm(String csCarrierPayTerm) {
        this.csCarrierPayTerm = csCarrierPayTerm;
    }

    /** Method to get linked list containing carrier pay details.
     @param carrierPayDetails linked list containing carrier pay details.
     */
    public void setCarrierPayDetails(LinkedList carrierPayDetails) {
        this.carrierPayDetails = carrierPayDetails;
    }

    /** This method gets linked list containing carrier pay details.
     @return carrierPayDetails linked list containing carrier pay details.
     */
    public LinkedList getCarrierPayDetails() {
        return carrierPayDetails;
    }

    /**
     Given the carrierPayTerm this method returns carrierPayCode.
     @param carrierPaymentTerm String.
     @return carrierPayCode String.         
     */
    public String getCarrierPayCode(String carrierPaymentTerm) {
        AascShipMethodInfo aascShipMethodBeanTemp = null;
        String carrierPayCodeTemp = "";

        listIterator = getCarrierPayDetails().listIterator();
        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCarrierPaymentTerms().equalsIgnoreCase(carrierPaymentTerm)) {
                carrierPayCodeTemp = 
                        aascShipMethodBeanTemp.getCarrierPayCode();
            }
        }
        return carrierPayCodeTemp;
    }

    /** Given the carrier pay code this method returns carrier payment term.
     @param carrierPayCode String. 
     @return carrierPayTerm String. 
     */
    public String getCarrierPaymentTerm(String carrierPayCode) {
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        listIterator = getCarrierPayDetails().listIterator();
        String carrierPaymentTermTemp = "";

        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCarrierPayCode().equalsIgnoreCase(carrierPayCode)) {
                carrierPaymentTermTemp = 
                        aascShipMethodBeanTemp.getCarrierPaymentTerms();
            }
        }
        return carrierPaymentTermTemp;
    }

    /** Given the carrier pay code this method returns cs carrier payment term.
     @param carrierPayCode String. 
     @return csCarrierPayTerm String. 
     */
    public String getCsCarrierPayTerm(String carrierPayCode) {
        AascShipMethodInfo aascShipMethodBeanTemp = null;
        String csCarrierPayTermTemp = "";

        listIterator = getCarrierPayDetails().listIterator();
        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCarrierPayCode().equalsIgnoreCase(carrierPayCode)) {
                csCarrierPayTermTemp = 
                        aascShipMethodBeanTemp.getCsCarrierPayTerm();
            }
        }
        return csCarrierPayTermTemp;
    }

    /** Given the carrier name this method returns carrier Id.
     @param carrierId int 
     @return carrierName String 
     */
    public String getCarrierName(int carrierId) {
        AascShipMethodInfo aascShipMethodBeanTemp = null;
        String carrierNameTemp = "";

        listIterator = getCarrierDetailList().listIterator();
        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCarrierId() == carrierId) {
                carrierNameTemp = aascShipMethodBeanTemp.getCarrierName();
            }
        }
        return carrierNameTemp;
    }

    /** Given the carrier name this method returns carrier Id.
     @param carrierName String  
     @return carrierId int
     */
    public int getCarrierID(String carrierName) {
        int carrierIDTemp = 0;
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        listIterator = getCarrierDetailList().listIterator();
        while (listIterator.hasNext()) {
            aascShipMethodBeanTemp = (AascShipMethodInfo)listIterator.next();
            if (aascShipMethodBeanTemp.getCarrierName().equalsIgnoreCase(carrierName)) {
                carrierIDTemp = aascShipMethodBeanTemp.getCarrierId();
            }
        }
        return carrierIDTemp;
    }

    /** Method to get carrier port.               
     @return carrierPort String
     */
    public String getCarrierPort() {
        return carrierPort;
    }

    /** Method to set carrier port.               
     @param carrierPort String
     */
    public void setCarrierPort(String carrierPort) {
        this.carrierPort = carrierPort;
    }

    /** Method to get carrier server ip address.               
     @return carrierServerIPAddress String
     */
    public String getCarrierServerIPAddress() {
        return carrierServerIPAddress;
    }

    /** Method to set carrier server ip address.                
     @param carrierServerIPAddress String
     */
    public void setCarrierServerIPAddress(String carrierServerIPAddress) {
        this.carrierServerIPAddress = carrierServerIPAddress;
    }

    /** Method to get carrier user name.                
     @return carrierUserName String
     */
    public String getCarrierUserName() {
        return carrierUserName;
    }

    /** Method to set carrier user name.                
     @param carrierUserName String
     */
    public void setCarrierUserName(String carrierUserName) {
        this.carrierUserName = carrierUserName;
    }

    /** Method to get carrier password.                
     @return carrierPassword String
     */
    public String getCarrierPassword() {
        return carrierPassword;
    }

    /** Method to set carrier password.                
     @param carrierPassword String
     */
    public void setCarrierPassword(String carrierPassword) {
        this.carrierPassword = carrierPassword;
    }

    /** Method to get carrier prefix.               
     @return carrierPrefix String
     */
    public String getCarrierPrefix() {
        return carrierPrefix;
    }

    /** Method to set carrier prefix.                
     @param carrierPrefix String
     */
    public void setCarrierPrefix(String carrierPrefix) {
        this.carrierPrefix = carrierPrefix;
    }

    /** Method to get carrier account number.                
     @return carrierAccountNumber String
     */
    public String getCarrierAccountNumber() {
        return carrierAccountNumber;
    }

    /** Method to set carrier account number.                
     @param carrierAccountNumber String
     */
    public void setCarrierAccountNumber(String carrierAccountNumber) {
        this.carrierAccountNumber = carrierAccountNumber;
    }

    /** Method to get printer port.                
     @return printerPort String
     */
    public String getPrinterPort() {
        return printerPort;
    }

    /** Method to set printer port.               
     @param printerPort String
     */
    public void setPrinterPort(String printerPort) {
        this.printerPort = printerPort;
    }

    /** Method to get printer model symbol.
     @return printerModelSymbol String
     */
    public String getPrinterModelSymbol() {
        return printerModelSymbol;
    }

    /** Method to set printer model symbol.
     @param printerModelSymbol String
     */
    public void setPrinterModelSymbol(String printerModelSymbol) {
        this.printerModelSymbol = printerModelSymbol;
    }

    /** Method to get printer stock symbol.                
     @return printerStockSymbol String
     */
    public String getPrinterStockSymbol() {
        return printerStockSymbol;
    }

    /** Method to set printer stock symbol.
     @param printerStockSymbol String
     */
    public void setPrinterStockSymbol(String printerStockSymbol) {
        this.printerStockSymbol = printerStockSymbol;
    }

    /** Method to get printer eod port.                
     @return printerEODPort String
     */
    public String getPrinterEODPort() {
        return printerEODPort;
    }

    /** Method to set printer eod port.                
     @param printerEODPort String
     */
    public void setPrinterEODPort(String printerEODPort) {
        this.printerEODPort = printerEODPort;
    }

    /** Returns carrier config bean object 
     which matches given carrierId.
     @param carrierId int.
     @return result AascShipMethodInfo carrier config 
     info object which contains carrier config details(bean).
     */
    public AascShipMethodInfo getCarrierConfigInfoBean(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;
        AascShipMethodInfo result = null;

        listIterator = getCarrierConfigList().listIterator();

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                result = tempAascShipMethodInfo;
            }
        }
        return result;
    }

    /** Returns carrierPort, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierPort String.
     */
    public String getCarrierPort(int carrierId) {
        String carrierPortTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            carrierPortTemp = aascShipMethodBeanTemp.getCarrierPort();
        }
        return carrierPortTemp;
    }

    /** Returns protocol, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  protocol String.
     */
    public String getProtocol(int carrierId) {
        String protocolTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            protocolTemp = aascShipMethodBeanTemp.getProtocol();
        }
        return protocolTemp;
    }

    /** Returns carrier server ip address, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierServerIPAddress String.
     */
    public String getCarrierServerIPAddress(int carrierId) {
        String carrierServerIPAddressTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            carrierServerIPAddressTemp = 
                    aascShipMethodBeanTemp.getCarrierServerIPAddress();
        }
        return carrierServerIPAddressTemp;
    }

    /** Returns carrier user name, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierUserName String.
     */
    public String getCarrierUserName(int carrierId) {
        String userNameTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            userNameTemp = aascShipMethodBeanTemp.getCarrierUserName();
        }
        return userNameTemp;
    }
    
    
    /** Returns carrier user name, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierUserName String.
     */
    public String getCarrierHubId(int carrierId) {
        String hubIdTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            hubIdTemp = aascShipMethodBeanTemp.getCarrierHubId();
        }
        return hubIdTemp;
    }


    /** Returns carrier password, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierPassword String.
     */
    public String getCarrierPassword(int carrierId) {
        String passwordTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            passwordTemp = aascShipMethodBeanTemp.getCarrierPassword();
        }
        return passwordTemp;
    }

    /** Returns carrier prefix, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierPrefix String.
     */
    public String getCarrierPrefix(int carrierId) {
        String carrierPrefixTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            carrierPrefixTemp = aascShipMethodBeanTemp.getCarrierPrefix();
        }
        return carrierPrefixTemp;
    }

    /** Returns carrier account number, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  carrierAccountNumber String.
     */
    public String getCarrierAccountNumber(int carrierId) {
        String carrierAccountNumberTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            carrierAccountNumberTemp = 
                    aascShipMethodBeanTemp.getCarrierAccountNumber();
        }
        return carrierAccountNumberTemp;
    }

    /** Returns printer port, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  printerPort String.
     */
    public String getPrinterPort(int carrierId) {
        String printerPortTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            printerPortTemp = aascShipMethodBeanTemp.getPrinterPort();
        }
        return printerPortTemp;
    }

    /** Returns printer model symbol, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  printerModelSymbol String.
     */
    public String getPrinterModelSymbol(int carrierId) {
        String printerModelSymbolTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            printerModelSymbolTemp = 
                    aascShipMethodBeanTemp.getPrinterModelSymbol();
        }
        return printerModelSymbolTemp;
    }

    /** Returns printer stock symbol, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  printerStockSymbol String.
     */
    public String getPrinterStockSymbol(int carrierId) {
        String printerStockSymbolTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            printerStockSymbolTemp = 
                    aascShipMethodBeanTemp.getPrinterStockSymbol();
        }
        return printerStockSymbolTemp;
    }

    /** Returns EOD port, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  printerEODPort String.
     */
    public String getPrinterEODPort(int carrierId) {
        String printerEODPortTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            printerEODPortTemp = aascShipMethodBeanTemp.getPrinterEODPort();
        }
        return printerEODPortTemp;
    }

    /** Returns EOD port, given the
     carrierId, which is retrieved from bean.
     @param  carrierId int.
     @return  printerEODPort String.
     */
    public String getMeterNumber(int carrierId) {
        String meterNumberTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            meterNumberTemp = aascShipMethodBeanTemp.getMeterNumber();
        }
        return meterNumberTemp;
    }

    /** Returns carrierCode ,given the  
     carrier id ,which is retrieved from bean.
     @param  carrierId int.
     @return  carrierCode int.
     */
    public int getCarrierCode(int carrierId) {
        int carrierCodeTemp = 0;
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            carrierCodeTemp = aascShipMethodBeanTemp.getCarrierCode();
        }
        return carrierCodeTemp;
    }

    /** Returns carrierId given the
     ship method which is retrieved from bean.
     @param  carrierCode int
     @return carrierIdTemp int
     */
    public int getCarrierIdFromCarrierCode(int carrierCode) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        int carrierIdTemp = 0;

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierCode() == carrierCode) {
                carrierIdTemp = tempAascShipMethodInfo.getCarrierId();
                break;
            }
        }
        return carrierIdTemp;
    }

    /** Method to get fedex meter number.     
     @return  meterNumber String.
     */
    public String getMeterNumber() {
        return meterNumber;
    }

    /** Method to set fedex meter number.     
     @param  meterNumber String.
     */
    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    /** Method to get error.   
     @return  error String.
     */
    public String getError() {
        return error;
    }

    /** Method to set error.    
     @param  error String.
     */
    public void setError(String error) {
        this.error = error;
    }

    /** Method to get protocol.     
     @return  protocol String.
     */
    public String getProtocol() {
        return protocol;
    }

    /** Method to set protocol.     
     @param  protocol String.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /** Method to get carrier code.
     @return carrierCode int-3 digit carrier code uniquely identifying each carrier.
     */
    public int getCarrierCode() {
        return carrierCode;
    }

    /** Method to set carrier code.
     @param carrierCode int-3 digit carrier code uniquely identifying each carrier.
     */
    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    /** Method to get shipmentValidationCode code.
     @return shipmentValidationCode int.
     */
    public String getShipmentValidationCode() {
        return shipmentValidationCode;
    }

    /** Method to set shipmentValidationCode code.
     @param shipmentValidationCode int.
     */
    public void setShipmentValidationCode(String shipmentValidationCode) {
        this.shipmentValidationCode = shipmentValidationCode;
    }

    /** Method to get droppOffType.
     @return dropOffType String.
     */
    public String getDropOffType() {
        return dropOffType;
    }

    /** Method to set droppOffType.
     @param dropOffType String.
     */
    public void setDropOffType(String dropOffType) {
        this.dropOffType = dropOffType;
    }

    /** Method to get packaging.
     @return packaging String.
     */
    public String getPackaging() {
        return packaging;
    }

    /** Method to set packaging.
     @param packaging String.
     */
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    /** Method to get enabled flag value.
     @return enabledFlag String.
     */
    public String getEnabledFlag() {
        return enabledFlag;
    }

    /** Method to set enabledFlag.
     @param enabledFlag String.
     */
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /** Method to get alternateShipMethod.
     @return alternateShipMethod String.
     */
    public String getAlternateShipMethod() {
        return alternateShipMethod;
    }

    /** Method to set alternateShipMethod.
     @param alternateShipMethod String.
     */
    public void setAlternateShipMethod(String alternateShipMethod) {
        this.alternateShipMethod = alternateShipMethod;
    }

    /** Method to get carrierNameSelected.
     @return carrierNameSelected String.
     */
    public String getCarrierNameSelected() {
        return carrierNameSelected;
    }

    /** Method to set carrierNameSelected.
     @param carrierNameSelected String.
     */
    public void setCarrierNameSelected(String carrierNameSelected) {
        this.carrierNameSelected = carrierNameSelected;
    }

    /** Method to get international shipment flag.
     @return intlShipFlag String.
     */
    public String getIntlShipFlag() {
        return intlShipFlag;
    }

    /** Method to set international shipment flag.
     @param intlShipFlag String.
     */
    public void setIntlShipFlag(String intlShipFlag) {
        this.intlShipFlag = intlShipFlag;
    }

    /** Method to get shipFromPhoneNumber.
     @return shipFromPhoneNumber String.
     */
    public String getShipFromPhoneNumber() {
        return shipFromPhoneNumber;
    }

    /** Method to set shipFromPhoneNumber.
     @param shipFromPhoneNumber String.
     */
    public void setShipFromPhoneNumber(String shipFromPhoneNumber) {
        this.shipFromPhoneNumber = shipFromPhoneNumber;
    }

    /** Method to get opStatus.
     @return opStatus int.
     */
    public int getOpStatus() {
        return opStatus;
    }

    /** Method to set opStatus.
     @param opStatus int.
     */
    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    /** Method to get statusDescription.
     @return statusDesc String.
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /** Method to set status Description.
     @param statusDesc String.
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * Method to get accessLicenseNumber.
     * @return accessLicenseNumber String.
     */
    public String getAccessLicenseNumber() {
        return accessLicenseNumber;
    }

    /** Method to set accessLicenseNumber.
     @param accessLicenseNumber String.
     */
    public void setAccessLicenseNumber(String accessLicenseNumber) {
        this.accessLicenseNumber = accessLicenseNumber;
    }

    /** Returns accessLicenseNumber given carrierCode
     which is retrieved from bean.
     @param  carrierCode int.
     @return  accessLicenseNumber String.
     */
    public String getAccessLicenseNumber(int carrierCode) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getShipMethodDetailList().listIterator();
        String accessLicenseNumber = "";

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierCode() == carrierCode) {
                accessLicenseNumber = 
                        tempAascShipMethodInfo.getAccessLicenseNumber();
                break;
            }
        }
        return accessLicenseNumber;
    }

    /** Returns accessLicenseNumber given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  accessLicenseNumber String.
     */
    public String getAccessLicenseNumberFromCarrierId(int carrierId) {
        logger.info("input carrierId :" + carrierId);
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String accessLicenseNumber = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();

            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                accessLicenseNumber = 
                        tempAascShipMethodInfo.getAccessLicenseNumber();
                logger.info("accessLicenseNumber :" + accessLicenseNumber);
                // found=true;
                break;
            }

        }
        return accessLicenseNumber;
    }

    public String getLabelStock() {
        return labelStock;
    }

    public void setLabelStock(String labelStock) {
        this.labelStock = labelStock;
    }

    public String getDocTab() {
        return docTab;
    }

    public void setDocTab(String docTab) {
        this.docTab = docTab;
    }

    /** Returns labelStockOrientation given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  labelStock String.
     */
    public String getLabelStockOrientation(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String labelStock = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                labelStock = tempAascShipMethodInfo.getLabelStock();
                // found=true;
                break;
            }

        }
        return labelStock;
    }

    /** Returns docTab given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  docTab String.
     */
    public String getDocTabLocation(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String docTab = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                docTab = tempAascShipMethodInfo.getDocTab();
                // found=true;
                break;
            }

        }
        return docTab;
    }

    public String getNonDiscountedCost() {
        return nonDiscountedCost;
    }

    public void setNonDiscountedCost(String nonDiscountedCost) {
        this.nonDiscountedCost = nonDiscountedCost;
    }


    public String getNonDiscountedCost(int carrierId) {
        String nonDiscountedCost = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            nonDiscountedCost = aascShipMethodBeanTemp.getNonDiscountedCost();
        }
        logger.info("NonDiscountedCost in AascShipMethodinfo=" + 
                    nonDiscountedCost);
        return nonDiscountedCost;
    }

    public String getAcctNegotiatedCertified() {
        return acctNegotiatedCertified;
    }

    public void setAcctNegotiatedCertified(String acctNegotiatedCertified) {
        this.acctNegotiatedCertified = acctNegotiatedCertified;
    }


    public String getAcctNegotiatedCertified(int carrierId) {
        String acctNegotiatedCertified = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            acctNegotiatedCertified = 
                    aascShipMethodBeanTemp.getAcctNegotiatedCertified();
        }
        logger.info("AcctNegotiatedCertified in AascShipMethodinfo=" + 
                    acctNegotiatedCertified);
        return acctNegotiatedCertified;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getValidation() {
        return validation;
    }

    public void setCarrierServiceList(LinkedList carrierServiceList) {
        this.carrierServiceList = carrierServiceList;
    }

    public LinkedList getCarrierServiceList() {
        return carrierServiceList;
    }

    public String getMeterNumberMain() {
        return meterNumberMain;
    }

    public void setMeterNumberMain(String meterNumberMain) {
        this.meterNumberMain = meterNumberMain;
    }

    public String getCarrierAccountNumberMain() {
        return carrierAccountNumberMain;
    }

    public void setCarrierAccountNumberMain(String carrierAccountNumberMain) {
        this.carrierAccountNumberMain = carrierAccountNumberMain;
    }

    public String getAccessLicenseNumberMain() {
        return accessLicenseNumberMain;
    }

    public void setAccessLicenseNumberMain(String accessLicenseNumberMain) {
        this.accessLicenseNumberMain = accessLicenseNumberMain;
    }

    // added by rajesh

    public String getCarrierMode(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String carrierMode = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();

            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                carrierMode = tempAascShipMethodInfo.getCarrierMode();
                // found=true;
                break;
            }

        }
        return carrierMode;
    }

    public void setCarrierMode(String carrierMode) {
        this.carrierMode = carrierMode;
    }

    public String getCarrierMode() {
        return carrierMode;
    }
    // added by rajesh

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public String getDomain(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String domain = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();

            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                domain = tempAascShipMethodInfo.getDomain();
                // found=true;
                break;
            }

        }
        return domain;
    }

//SC_7.0_SKP (START BLOCK)
 public void setEodFormat(String eodFormat) {
        this.eodFormat = eodFormat;
    }

    public String getEodFormat(int carrierId) {
        String eodFormat = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            eodFormat = aascShipMethodBeanTemp.getEodFormat();
        }
        return eodFormat;
    }

    private String getEodFormat() {
      return eodFormat;
    }

    public void setEodDocPrinterSymbol(String eodDocPrinterSymbol) {
        this.eodDocPrinterSymbol = eodDocPrinterSymbol;
    }

    public String getEodDocPrinterSymbol() {
        return eodDocPrinterSymbol;
    }
    
    public String getEodDocPrinterSymbol(int carrierId) {
        String eodDocPrinterSymbol = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            eodDocPrinterSymbol = aascShipMethodBeanTemp.getEodDocPrinterSymbol();
        }
        return eodDocPrinterSymbol;
    }

    public void setEodDocStockSymbol(String eodDocStockSymbol) {
        this.eodDocStockSymbol = eodDocStockSymbol;
    }

    public String getEodDocStockSymbol() {
        return eodDocStockSymbol;
    }

  public String getEodDocStockSymbol(int carrierId) {
        String eodDocStockSymbol = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            eodDocStockSymbol = aascShipMethodBeanTemp.getEodDocStockSymbol();
        }
        return eodDocStockSymbol;
    }

    public void setEodDocPort(String eodDocPort) {
        this.eodDocPort = eodDocPort;
    }

    public String getEodDocPort() {
        return eodDocPort;
    }
  public String getEodDocPort(int carrierId) {
        String eodDocPort = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            eodDocPort = aascShipMethodBeanTemp.getEodDocPort();
        }
        return eodDocPort;
    }

 public void setIntlLabelFormat(String intlLabelFormat) {
        this.intlLabelFormat = intlLabelFormat;
    }

    public String getIntlLabelFormat() {
        return intlLabelFormat;
    }
    public String getIntlLabelFormat(int carrierId) {
        String intlLabelFormat = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            intlLabelFormat = aascShipMethodBeanTemp.getIntlLabelFormat();
        }
        return intlLabelFormat;
    }
    //SC_7.0_SKP (END BLOCK)
    public void setCarrierHubId(String carrierHubId) {
        this.carrierHubId = carrierHubId;
    }

    public String getCarrierHubId() {
        return carrierHubId;
    }
    
    
    public String getHazmatOp900LabelFormat(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String op900LabelFormat = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                op900LabelFormat = tempAascShipMethodInfo.getOp900LabelFormat();
                break;
            }

        }
        return op900LabelFormat;
    }

    public void setOp900LabelFormat(String op900LabelFormat) {
        this.op900LabelFormat = op900LabelFormat;
    }

    public String getOp900LabelFormat() {
        return op900LabelFormat;
    }

    public void setSupportHazmatShipping(String supportHazmatShipping) {
        this.supportHazmatShipping = supportHazmatShipping;
    }

    public String getSupportHazmatShipping() {
        return supportHazmatShipping;
    }
    public void setIntlDocSubType(String intlDocSubType) {
        this.intlDocSubType = intlDocSubType;
    }

    public String getIntlDocSubType() {
        return intlDocSubType;
    }
    
    public String getIntlDocSubTypeValue(int carrierId) {
    
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String intlDocSubTypeVal = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                intlDocSubTypeVal = tempAascShipMethodInfo.getIntlDocSubType();
                break;
            }else{
           
                intlDocSubTypeVal = tempAascShipMethodInfo.getIntlDocSubType();
              //  System.out.println("In else--"+intlDocSubTypeVal);
            }

        }
        
        return intlDocSubTypeVal;
    }

    public void setUserdefinedShipmethod(String userdefinedShipmethod) {
        this.userdefinedShipmethod = userdefinedShipmethod;
    }

    public String getUserdefinedShipmethod() {
        return userdefinedShipmethod;
    }

    public void setShipmethodIndex(int shipmethodIndex) {
        this.shipmethodIndex = shipmethodIndex;
    }

    public int getShipmethodIndex() {
        return shipmethodIndex;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getClientid() {
        return clientid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public int getLocationid() {
        return locationid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorStatus() {
        return errorStatus;
    }
    
    /** Returns carrier user name, given the
     carrierId, which is retrieved from bean.
     @param  carrierCode int.
     @return  carrierUserName String.
     */
    public String getCarrierCodeUserName(int carrierCode) {
        String userNameTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
        if (aascShipMethodBeanTemp != null) {
            userNameTemp = aascShipMethodBeanTemp.getCarrierUserName();
        }
        return userNameTemp;
    }
    
    /** Returns carrier password, given the
     carrierId, which is retrieved from bean.
     @param  carrierCode int.
     @return  carrierPassword String.
     */
    public String getCarrierCodePassword(int carrierCode) {
        String passwordTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
        if (aascShipMethodBeanTemp != null) {
            passwordTemp = aascShipMethodBeanTemp.getCarrierPassword();
        }
        return passwordTemp;
    }
    
    /** Returns carrier config bean object 
     which matches given carrierId.
     @param carrierCode int.
     @return result AascShipMethodInfo carrier config 
     info object which contains carrier config details(bean).
     */
    public AascShipMethodInfo getCarrierCodeInfoBean(int carrierCode) {
        AascShipMethodInfo tempAascShipMethodInfo = null;
        AascShipMethodInfo result = null;

        listIterator = getCarrierConfigList().listIterator();

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierCode() == carrierCode) {
                result = tempAascShipMethodInfo;
            }
        }
        return result;
    }
    
    /** Returns EOD port, given the
     carrierId, which is retrieved from bean.
     @param  carrierCode int.
     @return  printerEODPort String.
     */
    public String getCodeMeterNumber(int carrierCode) {
        String meterNumberTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
        if (aascShipMethodBeanTemp != null) {
            meterNumberTemp = aascShipMethodBeanTemp.getMeterNumber();
        }
        return meterNumberTemp;
    }



    /** Returns carrier account number, given the
     carrierId, which is retrieved from bean.
     @param  carrierCode int.
     @return  carrierAccountNumber String.
     */
    public String getCarrierCodeAccountNumber(int carrierCode) {
        String carrierAccountNumberTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
        if (aascShipMethodBeanTemp != null) {
            carrierAccountNumberTemp = 
                    aascShipMethodBeanTemp.getCarrierAccountNumber();
        }
        return carrierAccountNumberTemp;
    }
    
    /** Returns accessLicenseNumber given carrierCode
     which is retrieved from bean.
     @param  carrierCode int.
     @return  accessLicenseNumber String.
     */
    public String getCodeAccessLicenseNumber(int carrierCode) {
        String upsAccessLicenseNumber = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

            aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
            if (aascShipMethodBeanTemp != null) {
                upsAccessLicenseNumber = 
                        aascShipMethodBeanTemp.getAccessLicenseNumber();
            }
        return upsAccessLicenseNumber;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    public String getIntegrationId() {
        return integrationId;
    }
    
    /** Returns integrationId given the
    Carrier ID which is retrieved from bean.
    @param  carrierId Int.
    @return  integrationId String.
     */

    public String getIntegrationId(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String integrationId = "";

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();

            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                integrationId = tempAascShipMethodInfo.getIntegrationId();
                break;
            }

        }
        return integrationId;
    }

    public void setStampsPaperSize(String stampsPaperSize) {
        this.stampsPaperSize = stampsPaperSize;
    }

    public String getStampsPaperSize() {
        return stampsPaperSize;
    }
    
    /** Returns stampsPaperSize given the
        Carrier ID which is retrieved from bean.
        @param  carrierId Int.
        @return  stampsPaperSize String.
        */
        public String getStampsPaperSizeFormat(int carrierId) {
        String stampsPaperSize = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierConfigInfoBean(carrierId);
        if (aascShipMethodBeanTemp != null) {
            stampsPaperSize = aascShipMethodBeanTemp.getStampsPaperSize();
        }
        return stampsPaperSize;
    }

    public void setIntlLabelStock(String intlLabelStock) {
        this.intlLabelStock = intlLabelStock;
    }

    public String getIntlLabelStock() {
        return intlLabelStock;
    }
    
    /** Returns IntlLabelStockOrientation given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  IntlLabelStockOrientation String.
     */
    public String getIntlLabelStockOrientation(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String intlLabelStock = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                intlLabelStock = tempAascShipMethodInfo.getIntlLabelStock();
                // found=true;
                break;
            }

        }
        return intlLabelStock;
    }


    public void setNonDeliveryOption(String nonDeliveryOption) {
        this.nonDeliveryOption = nonDeliveryOption;
    }

    public String getNonDeliveryOption() {
        return nonDeliveryOption;
    }
    
    /** Returns nonDeliveryOption given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  nonDeliveryOption String.
     */
    public String getNonDeliveryOptionOrientation(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String nonDeliveryOption = "";

        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                nonDeliveryOption = tempAascShipMethodInfo.getNonDeliveryOption();
                break;
            }

        }
        return nonDeliveryOption;
    }

    public void setIntlPrintLayOut(String intlPrintLayOut) {
        this.intlPrintLayOut = intlPrintLayOut;
    }

    public String getIntlPrintLayOut() {
        return intlPrintLayOut;
    }
    
    public String getIntlPrintLayOut(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String intlPrintLayOutTemp = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                intlPrintLayOutTemp = tempAascShipMethodInfo.getIntlPrintLayOut();
                // found=true;
                break;
            }

        }
        return intlPrintLayOutTemp;
    }
    
    /** Returns Integration id which is retrieved from bean.
     @param  carrierCode int.
     @return  IntegrationId String.
     */
    public String getCodeIntegrationId(int carrierCode) {
        String integrationIdTemp = "";
        AascShipMethodInfo aascShipMethodBeanTemp = null;

        aascShipMethodBeanTemp = getCarrierCodeInfoBean(carrierCode);
        if (aascShipMethodBeanTemp != null) {
            integrationIdTemp = aascShipMethodBeanTemp.getIntegrationId();
        }
        return integrationIdTemp;
    }
    
	//Shiva added below code for DHL
    /** Returns dhlRegionCode given carrierId
     which is retrieved from bean.
     @param  carrierId int.
     @return  dhlRegionCode String.
     */
    public String getdhlRegionCode(int carrierId) {
        AascShipMethodInfo tempAascShipMethodInfo = null;

        listIterator = getCarrierConfigList().listIterator();
        String dhlRegionCode = "";

        // boolean found=false;
        while (listIterator.hasNext()) {
            tempAascShipMethodInfo = (AascShipMethodInfo)listIterator.next();
            if (tempAascShipMethodInfo.getCarrierId() == carrierId) {
                dhlRegionCode = tempAascShipMethodInfo.getDhlRegionCode();
                // found=true;
                break;
            }

        }
        return dhlRegionCode;
    }

    public void setDhlRegionCode(String dhlRegionCode) {
        this.dhlRegionCode = dhlRegionCode;
    }

    public String getDhlRegionCode() {
        return dhlRegionCode;
    }
}
