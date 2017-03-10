/*
 * @(#)AascCarrierProfileOptionsInfo.java        14/05/2008
 * Copyright (c) 2005-2008 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.bean;

import java.sql.Timestamp;


/**
 AascCarrierProfileOptionsInfo Bean Class for Carrier Profile Options Information with setXxx() and getXxx() methods.
 These methods are used to get and set the Carrier Profile Options Information.
 */
public class

/*
 *@author Madhavi Latha
 *@version 1.2
 */

/* ========================================================================================
 Date         Resource                 Change history
 ---------------------------------------------------------------------------------------------
 23/06/2008   Sambit           Added setXXX() and getXXX() for carrier account number and value.
 15/08/2009   Madhavi          Added setXXX() and getXXX() for hazmatFlag.
 26/12/2012   Khaja            Added setXXX() and getXXX() for DefaultShipmentDate.
 ------------------------------------------------------------------------------------------*/


AascCarrierProfileOptionsInfo {
    private String shipMethodMeaning = 
        ""; // ship method used by the customer to ship the delivery
    private String packaging = ""; // indicates packaging for fedex deliveries
    private String carrierPaymentTerms = ""; // contains the payment terms
    private String dropOffType = 
        ""; // indicates droppofftype used for fedex deliveries
    private String codFlag = 
        ""; // charge on delivery flag which has 'Y' or 'N' 
    private String codType = ""; // contains COD Type for UPS deliveries
    private String codFundsCode = 
        ""; // contains COD Funds Code for UPS deliveries
    private String codCurrCode = 
        ""; // contains COD Currency Code for UPS deliveries
    private String signatureOptions = 
        ""; //indicates signature options for fedex deliveries
    private String returnShipmentFlag = 
        ""; //contains return shipment flag for fedex deliveries
    private String halFlag = 
        ""; //indicates hold at location flag for fedex deliveries
    private String dimensionsFlag = 
        ""; // indicates dimensions flag of the package to be shipped 
    private String declaredAmtFlag = 
        ""; // indicates declared Amount flag of the package to be shipped 

    private String carrierAccountNum = "";
    private String carrierShipmethodValue = "";
    private String dryIceFlag = "";
    private String hazmatFlag = "";
    private String insideDelivery = "";
    private String largePackageFlag = "";
    private String additionalHandlingFlag = "";
    private String shipFromLocation = "";
    private Timestamp shipTimeStampSession;
    
    /** method to retreive shipmethod used by customer to ship the delivery.
     * @return shipMethodMeaning String
     */
    public String getShipMethodMeaning() {
        return shipMethodMeaning;
    }

    /** method to set shipmethod used by customer to ship the delivery.
     * @param shipMethodMeaning String
     */
    public void setShipMethodMeaning(String shipMethodMeaning) {
        this.shipMethodMeaning = shipMethodMeaning;
    }

    /** method to get dropOffType.
     * @return dropOffType String
     */
    public String getDropOffType() {
        return dropOffType;
    }

    /** method to set dropOffType.
     * @param dropOffType String
     */
    public void setDropOffType(String dropOffType) {
        this.dropOffType = dropOffType;
    }

    /** method to get packaging.
     * @return packaging String
     */
    public String getPackaging() {
        return packaging;
    }

    /** method to set packaging.
     * @param packaging String
     */
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    /** method to retreive carrier pay terms.
     * @return carrierPaymentTerms String
     */
    public String getCarrierPaymentTerms() {
        return carrierPaymentTerms;
    }

    /** method to set carrier pay terms.
     * @param carrierPaymentTerms String
     */
    public void setCarrierPaymentTerms(String carrierPaymentTerms) {
        this.carrierPaymentTerms = carrierPaymentTerms;
    }

    /**
     Method setCodFlag to set flag to 'Y' or 'N'. 
     @param codFlag String.
     */
    public void setCodFlag(String codFlag) {
        this.codFlag = codFlag;
    }

    /**
     Method getCodFlag gets the codFlag. 
     @return codFlag String. 
     */
    public String getCodFlag() {
        return codFlag;
    }

    /**
     Method setCodType is used to set codType.
     @param codType String.
     */
    public void setCodType(String codType) {
        this.codType = codType;
    }

    /**
     Method getCodType gets the codType. 
     @return codType String. 
     */
    public String getCodType() {
        return codType;
    }

    /**
     Method getCodFundsCode gets the codFundsCode. 
     @return codFundsCode String. 
     */
    public String getCodFundsCode() {
        return codFundsCode;
    }

    /**
     Method setCodFundsCode is used to set codFundsCode.
     @param codFundsCode String.
     */
    public void setCodFundsCode(String codFundsCode) {
        this.codFundsCode = codFundsCode;
    }

    /**
     Method getCodCurrCode gets the codCurrCode. 
     @return codCurrCode String. 
     */
    public String getCodCurrCode() {
        return codCurrCode;
    }

    /**
     Method setCodCurrCode is used to set codCurrCode.
     @param codCurrCode String.
     */
    public void setCodCurrCode(String codCurrCode) {
        this.codCurrCode = codCurrCode;
    }

    /**
     Method getSignatureOptions gets value for signatureOptions. 
     @return String.
     */
    public String getSignatureOptions() {
        return signatureOptions;
    }

    /**
     Method setSignatureOptions to set value for signatureOptions. 
     @param signatureOptions String.
     */
    public void setSignatureOptions(String signatureOptions) {
        this.signatureOptions = signatureOptions;
    }

    /**
     Method getReturnShipmentFlag gets value for returnShipment Flag. 
     @return String.
     */
    public String getReturnShipmentFlag() {
        return returnShipmentFlag;
    }

    /**
     Method setReturnShipmentFlag to set value for returnShipment Flag. 
     @param returnShipmentFlag String.
     */
    public void setReturnShipmentFlag(String returnShipmentFlag) {
        this.returnShipmentFlag = returnShipmentFlag;
    }

    /**
     Method setHalFlag to set flag to 'Y' or 'N'. 
     @param halFlag String.
     */
    public void setHalFlag(String halFlag) {
        this.halFlag = halFlag;
    }

    /**
     Method setHalFlag to get flag to 'Y' or 'N'. 
     @return halFlag String.
     */
    public String getHalFlag() {
        return halFlag;
    }

    /**
     Method setDimensionsFlag to set Flag for dimensions of the individual packages to be shipped. 
     @param dimensionsFlag String.
     */
    public void setDimensionsFlag(String dimensionsFlag) {
        this.dimensionsFlag = dimensionsFlag;
    }

    /**
     Method getDimensionsFlag gets the flag for dimensions of individual package. 
     @return dimensionsFlag String.
     */
    public String getDimensionsFlag() {
        return dimensionsFlag;
    }

    /**
     Method setDeclaredAmtFlag to set Flag for declared value of the individual packages to be shipped. 
     @param declaredAmtFlag String.
     */
    public void setDeclaredAmtFlag(String declaredAmtFlag) {
        this.declaredAmtFlag = declaredAmtFlag;
    }

    /**
     Method getDeclaredAmtFlag gets the flag for declared value of individual package. 
     @return declaredAmtFlag String.
     */
    public String getDeclaredAmtFlag() {
        return declaredAmtFlag;
    }

    /** method to retreive carrier account number.
     * @return carrierAccountNum String
     */
    public String getCarrierAccountNum() {
        return carrierAccountNum;
    }

    /** method to set carrier account number.
     * @param carrierAccountNum String
     */
    public void setCarrierAccountNum(String carrierAccountNum) {
        this.carrierAccountNum = carrierAccountNum;
    }

    /** method to retreive carrier ship method value.
     * @return carrierAccountNum String
     */
    public String getCarrierShipmethodValue() {
        return carrierShipmethodValue;
    }

    /** method to set carrier ship method value.
     * @param carrierShipmethodValue String
     */
    public void setCarrierShipmethodValue(String carrierShipmethodValue) {
        this.carrierShipmethodValue = carrierShipmethodValue;
    }

    /** method to retreive dryIceFlag value.
     * @return dryIceFlag String
     */
    public String getDryIceFlag() {
        return dryIceFlag;
    }

    /** method to set dryIceFlag value.
     * @param dryIceFlag String
     */
    public void setDryIceFlag(String dryIceFlag) {
        this.dryIceFlag = dryIceFlag;
    }

    /** method to set hazmatFlag value.
     * @param hazmatFlag String
     */
    public void setHazmatFlag(String hazmatFlag) {
        this.hazmatFlag = hazmatFlag;
    }

    /** method to retreive hazmatFlag value.
     * @return hazmatFlag String
     */
    public String getHazmatFlag() {
        return hazmatFlag;
    }


    public void setLargePackageFlag(String largePackageFlag) {
        this.largePackageFlag = largePackageFlag;
    }

    public String getLargePackageFlag() {
        return largePackageFlag;
    }

    public void setAdditionalHandlingFlag(String additionalHandlingFlag) {
        this.additionalHandlingFlag = additionalHandlingFlag;
    }

    public String getAdditionalHandlingFlag() {
        return additionalHandlingFlag;
    }

    public void setShipFromLocation(String shipFromLocation) {
        this.shipFromLocation = shipFromLocation;
    }

    public String getShipFromLocation() {
        return shipFromLocation;
    }

    public void setInsideDelivery(String insideDelivery) {
        this.insideDelivery = insideDelivery;
    }

    public String getInsideDelivery() {
        return insideDelivery;
    }

  public void setShipTimeStampSession(Timestamp shipTimeStampSession)
  {
    this.shipTimeStampSession = shipTimeStampSession;
  }

  public Timestamp getShipTimeStampSession()
  {
    return shipTimeStampSession;
  }
}
