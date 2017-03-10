/*
 * @(#)AascPackageInfo.java        
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.bean;

import java.sql.Date;

import java.util.LinkedList;


/**
 * AascPackageInfo Bean Class for Package Information with setXxx() and getXxx() methods.
 * These methods are used to get and set the Package Information.
 * @version   1.1
 * @author    Eshwari M
 History:
  
 * 28/11/2014   Eshwari M     Added this file from cloud 1.2 version
 * 18/12/2014   Eshwari M     Renamed Adhoc to Shipment
 * 19/01/2015   Suman G       Removed unnecessary comments in history
 * 02/02/2015   Eshwari M     Added transitTime and deliveryDate 
 * 05/03/2015   Suman G       Commented dimensionUnit field for eliminate duplicate variables.
 * 03/06/2015   Suman G       Added dry ice related fields to fix #2955
 */
 
public class AascShipmentPackageInfo {
    private String orderNumber; // holds the value of sequence number generated from the header level

        private int packageNumber; // holds the value of  sequence number generated for each package for a particular order

        private String itemNumber; // holds the value of inventory item Number with respect to item

        private String itemDescription; // holds the value of the description of the item

        private float shippedQuantity; // holds the value of the Quantity that needs to be shipped 

        private String uom; // holds the value of the Unit of measure of the package to be shipped        

        private float weight; // holds the value of the  weight of the package which is to be shipped 

        private String codFlag; // change on delivery flag which has 'Y' or 'N' 

        private float codAmt; // holds the value of the codAmt for the package 

        private int createdBy; // holds the userId who insert the data for Shipment

        private String trackingNumber; // holds the value of the tracking number

        private int msn; // holds the value of the msn

        private String dimension = 
            ""; // holds the value of the dimension of the package

//        private String dimensionUnit = 
//            ""; // holds the value of the dimensionUnit of the package

    private String LargePackage = "";
    private String AdditionalHandling = "";
        private String packaging = "";
        private String codCode = "";
        private String codFundsCode = "";
        private String codCurrCode = "";
        private String declaredCurrCode = "";
        private int packageLength;
        private int packageWidth;
        private int packageHeight;
        private String returnShipment = "";
        private String signatureOptions = "";
        private String futureDayShipment;
        private double packageDeclaredValue = 0;
        private double surCharges = 0;
        private double pkgCost = 0;
        private double pkgDiscountNetCharge;
        private double pkgListNetCharge;
        private String codTrackingNum;
        private String packageCount = "";
        private String packageSequence = "";
        private String shipmentWeight = "";
        private String masterTrackingNumber = "";
        private String masterFormID = "";
        private String deliveryConfirmation = "";

        private String lineNo = ""; // lineNo of the package  

        private String dimensions = 
            ""; // dimensions of the package to be shipped       

        private String dimensionUnits = ""; // sets the units for the dimensions

        int dimensionId;
        String dimensionName;
        String halLine2;

        private String voidFlag;
    private String rtnCountrySymbol;
        private String rtnDesc;
        private String rtnShipFromCompany;
        private String rtnShipToCompany;
        private String rtnShipFromContact;
        private String rtnShipToContact;
        private String rtnShipFromLine1;
        private String rtnShipToLine1;
        private String rtnShipFromLine2;
        private String rtnShipToLine2;
        private String rtnShipFromCity;
        private String rtnShipFromSate;
        private String rtnShipFromZip;
        private String rtnShipToCity;
        private String rtnShipToState;
        private String rtnShipToZip;
        private String rtnShipFromPhone;
        private String rtnShipToPhone;
        private String rtnShipMethod;
        private String rtnDropOfType;
        private String rtnPackageList;
        private String rtnPayMethod;
        private String rtnACNumber;
        private String rtnRMA;
        private String rtnTrackingNumber;
        private double rtnShipmentCost;
        private String rtnPayMethodCode;
        private double rtnDeclaredValue;
        private String reqVoidFlagHidden;
        double baseCharge;
        double totalDiscount;
        double rtnBaseChrge;
        double rtnTotalDiscount;

        // ended on 11/05/2007
        // Added by Sambit on 15/11/07
        String halFlag;
        String halPhone;
        String halLine1;
        String halCity;
        String halState;
        String halPostalCode;

        String HalStateOrProvince;
        
        String dryIceChk;
        double dryIceWeight;
        String dryIceUnits;
        
        String HazMatFlag;
        String HazMatType;
        String HazMatClass;
        double HazMatCharges;
        double RtnHazMatCharges;
        //end 11/07/2008


        double HazMatQty;
        String HazMatUnit;

        //Added by pavan on 09/03/2009  
        String HazMatIdNo;
        String HazMatPkgGroup;
        String HazMatDOTLabel;
        String HazMatEmerContactNo;
    String HazMatEmerContactName;
    String HazmatPackInstructions;
        String HazMatId;

        // Added on Jun-03-2011
        double hazmatPkgingCnt;
        String hazmatPkgingUnits;
        // End on Jun-03-2011

        //Added on Jul-05-2011
        String hazmatTechnicalName;
        //Edd on Jul-05-2011
         String hazmatSignatureName;

        private double intlQuantity; // quantity for International Shipment
        private String intlQUOM = ""; // Unit of measure for International Shipment
        private double intlUnitWeight; // Unit weight for International Shipment
        private double intlUnitValue; // Unit value for International Shipment
        private String intlProductDescription = 
            ""; // Description of the product for International Shipment

        private String HazMatReq = "";

        private String HazMatLabelType = "";

        private String temporaryFlag;
 
        private String transitTime="";
  
        private Date deliveryDate;
        
       private String stampsTaxId = "";

    private LinkedList hazmatPackageInfoList;
    
    AascHazmatPackageInfo aascHazmatPackageInfo;
        /**

         * Default constructor.

         * */
        public

        AascShipmentPackageInfo() {
        }

        /**

         * Method setOrderNumber to set orderNumber.

         * @param  orderNumber  int.

         */
        public

        void setOrderNumber(String orderNumber) {

            this.orderNumber = orderNumber;

        }

        /**

         * Method setPackageNumber to set packageNumber.

         * @param  packageNumber  int.

         */
        public

        void setPackageNumber(int packageNumber) {

            this.packageNumber = packageNumber;

        }

        /**

         * Method setItemNumber to set itemNumber.

         * @param  itemNumber String.

         */
        public

        void setItemNumber(String itemNumber) {

            this.itemNumber = itemNumber;

        }

        /**

         * Method setItemDescription to set itemDescription.

         * @param  itemDescription String .

         */
        public

        void setItemDescription(String itemDescription) {

            this.itemDescription = itemDescription;

        }

        /**

         * Method setShippedQuantity to set shippedQuantity.

         * @param  shippedQuantity float.

         */
        public

        void setShippedQuantity(float shippedQuantity) {

            this.shippedQuantity = shippedQuantity;

        }

        /**

         * Method setUnitOfMeasure to set unitOfMeasure.

         * @param  uom String.

         */
        public

        void setUom(String uom) {

            this.uom = uom;

        }

        /**

         * Method setWeight to set weight.

         * @param  weight float.

         */
        public

        void setWeight(float weight) {

            this.weight = weight;

        }

        /**

         * Method setCreatedy to set createdBy number- user id who insert the data for Shipment.

         * @param  createdBy int.

         */
        public

        void setCreatedBy(int createdBy) {

            this.createdBy = createdBy;

        }

        /**

         * Method getItemDescription returns itemDescription for the item.

         * @return itemDescription  String.

         */
        public

        String getItemDescription() {

            return itemDescription;

        }

        /**

         * Method getItemNumber returns itemNumber for the item.

         * @return itemNumber  String.

         */
        public

        String getItemNumber() {

            return itemNumber;

        }

        /**

         * Method getOrderNumber returns orderNumber. 

         * @return orderNumber  int.

         */
        public

        String getOrderNumber() {

            return orderNumber;

        }

        /**

         * Method getPackageNumber returns packageNumber for the item.

         * @return packageNumber int.

         */
        public

        int getPackageNumber() {

            return packageNumber;

        }

        /**

         * Method getShippedQuantity returns shippedQuantity for the item.

         * @return shippedQuantity  float.

         */
        public

        float getShippedQuantity() {

            return shippedQuantity;

        }

        /**

         * Method getUnitOfMeasure returns unitOfMeasure for the item.

         * @return unitOfMeasure  String.

         */
        public

        String getUom() {

            return uom;

        }

        /**

         * Method getWeight returns weight for the item.

         * @return weight  float.

         */
        public

        float getWeight() {

            return weight;

        }

        /**

         * Method getCreatedBy returns user id who insert the data for Shipment.

         * @return createdBy  int.

         */
        public

        int getCreatedBy() {

            return createdBy;

        }

        /**

         * Method getCodAmt gets the amount of the package. 

         * @return codAmt  float. 

         */
        public

        float getCodAmt() {

            return codAmt;

        }

        /**

         * Method setCodAmt to set amount for the package. 

         * @param  codAmt float.

         */
        public

        void setCodAmt(float codAmt) {

            this.codAmt = codAmt;

        }

        /**

         * Method setCodFlag to set flag to 'Y' or 'N'. 

         * @param  codFlag String.

         */
        public

        void setCodFlag(String codFlag) {

            this.codFlag = codFlag;

        }

        /**

         * Method getCodFlag gets the codFlag. 

         * @return codFlag  String. 

         */
        public

        String getCodFlag() {

            return codFlag;

        }

        /**

         * Method setMsn sets the msn value given by the carrier. 

         * @param  msn int.

         */
        public

        void setMsn(int msn) {

            this.msn = msn;

        }

        /**

         * Method getMsn return the msn value given by the carrier. 

         * @return msn  int.

         */
        public

        int getMsn() {

            return msn;

        }

        /**

         * Method setTrackingNumber to set the trackingNumber value.

         * @param  trackingNumber String.

         */
        public

        void setTrackingNumber(String trackingNumber) {

            this.trackingNumber = trackingNumber;

        }

        /**

         * Method getTrackingNumber gets the trackingNumber.

         * @return trackingNumber  String.

         */
        public

        String getTrackingNumber() {

            return trackingNumber;

        }

        /**

         * Method setDimension to set the dimension  value.

         * @param  dimension String.

         */
        public

        void setDimension(String dimension) {

            this.dimension = dimension;

        }

        /**

         * Method getDimension gets the dimension.

         * @return dimension  String.

         */
        public

        String getDimension() {

            return dimension;

        }

        /**

         * Method setDimensionUnit to set the dimensionUnit  value.

         * @param  dimensionUnit String.

         */
//        public
//
//        void setDimensionUnit(String dimensionUnit) {
//
//            this.dimensionUnit = dimensionUnit;
//
//        }

        /**

         * Method getDimensionUnit gets the dimensionUnit.

         * @return dimensionUnit  String.

         */
//        public
//
//        String getDimensionUnit() {
//
//            return dimensionUnit;
//
//        }

        // added on 15/05/2007

        public String getPackaging() {
            return packaging;
        }

        public void setPackaging(String packaging) {
            this.packaging = packaging;
        }

        public String getCodCode() {
            return codCode;
        }

        public void setCodCode(String codCode) {
            this.codCode = codCode;
        }


        public String getCodFundsCode() {
            return codFundsCode;
        }

        public void setCodFundsCode(String codFundsCode) {
            this.codFundsCode = codFundsCode;
        }

        public String getCodCurrCode() {
            return codCurrCode;
        }

        public void setCodCurrCode(String codCurrCode) {
            this.codCurrCode = codCurrCode;
        }

        public String getDeclaredCurrCode() {
            return declaredCurrCode;
        }

        public void setDeclaredCurrCode(String declaredCurrCode) {
            this.declaredCurrCode = declaredCurrCode;
        }

        /**
         *Method getPackageLength returns packageLength.
         *@return packageLength int. 
         */
        public int getPackageLength() {
            return packageLength;
        }

        /**
         *Method setPackageLength to set packageLength.
         *@param packageLength int.
         */
        public void setPackageLength(int packageLength) {
            this.packageLength = packageLength;
        }

        /**
         *Method getPackageWidth returns packageWidth.
         *@return packageWidth int. 
         */
        public int getPackageWidth() {
            return packageWidth;
        }

        /**
         *Method setPackageWidth to set packageWidth.
         *@param packageWidth int.
         */
        public void setPackageWidth(int packageWidth) {
            this.packageWidth = packageWidth;
        }

        /**
         *Method getPackageHeight returns packageHeight.
         *@return packageHeight int. 
         */
        public int getPackageHeight() {
            return packageHeight;
        }

        /**
         *Method setPackageHeight to set packageHeight.
         *@param packageHeight int.
         */
        public void setPackageHeight(int packageHeight) {
            this.packageHeight = packageHeight;
        }

        /**
          Method getReturnShipment gets value for returnShipment. 
          @return Shipment String.
         */
        public String getReturnShipment() {
            return returnShipment;
        }

        /**
          Method setReturnShipment to set value for returnShipment. 
          @param returnShipment String.
         */
        public void setReturnShipment(String returnShipment) {
            this.returnShipment = returnShipment;
        }


        /**
          Method getFutureDayShipment gets value for futureDayShipment. 
          @return futureDayShipment String.
         */
        public String getFutureDayShipment() {
            return futureDayShipment;
        }

        /**
          Method setfutureDayShipment to set value for futureDayShipment. 
          @param futureDayShipment String.
         */
        public void setFutureDayShipment(String futureDayShipment) {
            this.futureDayShipment = futureDayShipment;
        }


        /**
          Method getSignatureOptions gets value for signatureOptions. 
          @return signatureOptions String.
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

        public double getPackageDeclaredValue() {
            return packageDeclaredValue;
        }

        public void setPackageDeclaredValue(double packageDeclaredValue) {
            this.packageDeclaredValue = packageDeclaredValue;
        }

        /**
          Method getSurCharges gets the value for surCharges. 
          @return surCharges double. 
         */
        public double getSurCharges() {
            return surCharges;
        }

        /**
          Method setSurCharges to set value for surCharges. 
          @param surCharges double.
         */
        public

        void setSurCharges(double surCharges) {
            this.surCharges = surCharges;
        }

        /**
          Method getPkgCost gets the value for pkgCost. 
          @return pkgCost double. 
         */
        public double getPkgCost() {
            return pkgCost;
        }

        /**
          Method setPkgCost to set value for pkgCost. 
          @param pkgCost double.
         */
        public void setPkgCost(double pkgCost) {
            this.pkgCost = pkgCost;
        }

        /**
             Method getPkgListNetCharge gets the value for pkgListNetCharge. 
             @return pkgListNetCharge double. 
         */
        public double getPkgListNetCharge() {
            return pkgListNetCharge;
        }

        /**
             Method setPkgListNetCharge to set value for pkgListNetCharge. 
             @param pkgListNetCharge double.
         */
        public void setPkgListNetCharge(double pkgListNetCharge) {
            this.pkgListNetCharge = pkgListNetCharge;
        }

        /**
             Method getPkgDiscountNetCharge gets the value for pkgDiscountNetCharge. 
             @return pkgDiscountNetCharge double. 
         */
        public double getPkgDiscountNetCharge() {
            return pkgDiscountNetCharge;
        }

        /**
             Method setPkgDiscountNetCharge to set value for pkgDiscountNetCharge. 
             @param pkgDiscountNetCharge double.
         */
        public void setPkgDiscountNetCharge(double pkgDiscountNetCharge) {
            this.pkgDiscountNetCharge = pkgDiscountNetCharge;
        }

        public void setPackageCount(String packageCount) {
            this.packageCount = packageCount;
        }

        public String getPackageCount() {
            return packageCount;
        }

        public void setPackageSequence(String packageSequence) {
            this.packageSequence = packageSequence;
        }

        public String getPackageSequence() {
            return packageSequence;
        }

        public void setShipmentWeight(String shipmentWeight) {
            this.shipmentWeight = shipmentWeight;
        }

        public String getShipmentWeight() {
            return shipmentWeight;
        }


        public void setLineNo(String lineNo) {
            this.lineNo = lineNo;
        }

        public String getLineNo() {
            return lineNo;
        }


        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensionUnits(String dimensionUnits) {
            this.dimensionUnits = dimensionUnits;
        }

        public String getDimensionUnits() {
            return dimensionUnits;
        }


        public void setDimensionId(int dimensionId) {
            this.dimensionId = dimensionId;
        }

        public int getDimensionId() {
            return dimensionId;
        }

        public void setDimensionName(String dimensionName) {
            this.dimensionName = dimensionName;
        }

        public String getDimensionName() {
            return dimensionName;
        }

        public void setHalLine2(String halLine2) {
            this.halLine2 = halLine2;
        }

        public String getHalLine2() {
            return halLine2;
        }


        public void setVoidFlag(String voidFlag) {
            this.voidFlag = voidFlag;
        }

        public String getVoidFlag() {
            return voidFlag;
        }


        public void setRtnShipFromCompany(String rtnShipFromCompany) {
            this.rtnShipFromCompany = rtnShipFromCompany;
        }

        public String getRtnShipFromCompany() {
            return rtnShipFromCompany;
        }

        public void setRtnShipToCompany(String rtnShipToCompany) {
            this.rtnShipToCompany = rtnShipToCompany;
        }

        public String getRtnShipToCompany() {
            return rtnShipToCompany;
        }

        public void setRtnShipFromContact(String rtnShipFromContact) {
            this.rtnShipFromContact = rtnShipFromContact;
        }

        public String getRtnShipFromContact() {
            return rtnShipFromContact;
        }

        public void setRtnShipToContact(String rtnShipToContact) {
            this.rtnShipToContact = rtnShipToContact;
        }

        public String getRtnShipToContact() {
            return rtnShipToContact;
        }

        public void setRtnShipFromLine1(String rtnShipFromLine1) {
            this.rtnShipFromLine1 = rtnShipFromLine1;
        }

        public String getRtnShipFromLine1() {
            return rtnShipFromLine1;
        }

        public void setRtnShipToLine1(String rtnShipToLine1) {
            this.rtnShipToLine1 = rtnShipToLine1;
        }

        public String getRtnShipToLine1() {
            return rtnShipToLine1;
        }

        public void setRtnShipFromLine2(String rtnShipFromLine2) {
            this.rtnShipFromLine2 = rtnShipFromLine2;
        }

        public String getRtnShipFromLine2() {
            return rtnShipFromLine2;
        }

        public void setRtnShipToLine2(String rtnShipToLine2) {
            this.rtnShipToLine2 = rtnShipToLine2;
        }

        public String getRtnShipToLine2() {
            return rtnShipToLine2;
        }

        public void setRtnShipFromCity(String rtnShipFromCity) {
            this.rtnShipFromCity = rtnShipFromCity;
        }

        public String getRtnShipFromCity() {
            return rtnShipFromCity;
        }

        public void setRtnShipFromSate(String rtnShipFromSate) {
            this.rtnShipFromSate = rtnShipFromSate;
        }

        public String getRtnShipFromSate() {
            return rtnShipFromSate;
        }

        public void setRtnShipFromZip(String rtnShipFromZip) {
            this.rtnShipFromZip = rtnShipFromZip;
        }

        public String getRtnShipFromZip() {
            return rtnShipFromZip;
        }

        public void setRtnShipToCity(String rtnShipToCity) {
            this.rtnShipToCity = rtnShipToCity;
        }

        public String getRtnShipToCity() {
            return rtnShipToCity;
        }

        public void setRtnShipToState(String rtnShipToState) {
            this.rtnShipToState = rtnShipToState;
        }

        public String getRtnShipToState() {
            return rtnShipToState;
        }

        public void setRtnShipToZip(String rtnShipToZip) {
            this.rtnShipToZip = rtnShipToZip;
        }

        public String getRtnShipToZip() {
            return rtnShipToZip;
        }

        public void setRtnShipFromPhone(String rtnShipFromPhone) {
            this.rtnShipFromPhone = rtnShipFromPhone;
        }

        public String getRtnShipFromPhone() {
            return rtnShipFromPhone;
        }

        public void setRtnShipToPhone(String rtnShipToPhone) {
            this.rtnShipToPhone = rtnShipToPhone;
        }

        public String getRtnShipToPhone() {
            return rtnShipToPhone;
        }

        public void setRtnShipMethod(String rtnShipMethod) {
            this.rtnShipMethod = rtnShipMethod;
        }

        public String getRtnShipMethod() {
            return rtnShipMethod;
        }

        public void setRtnDropOfType(String rtnDropOfType) {
            this.rtnDropOfType = rtnDropOfType;
        }

        public String getRtnDropOfType() {
            return rtnDropOfType;
        }

        public void setRtnPackageList(String rtnPackageList) {
            this.rtnPackageList = rtnPackageList;
        }

        public String getRtnPackageList() {
            return rtnPackageList;
        }

        public void setRtnPayMethod(String rtnPayMethod) {
            this.rtnPayMethod = rtnPayMethod;
        }

        public String getRtnPayMethod() {
            return rtnPayMethod;
        }

        public void setRtnACNumber(String rtnACNumber) {
            this.rtnACNumber = rtnACNumber;
        }

        public String getRtnACNumber() {
            return rtnACNumber;
        }

        public void setRtnRMA(String rtnRMA) {
            this.rtnRMA = rtnRMA;
        }

        public String getRtnRMA() {
            return rtnRMA;
        }

        public void setRtnTrackingNumber(String rtnTrackingNumber) {
            this.rtnTrackingNumber = rtnTrackingNumber;
        }

        public String getRtnTrackingNumber() {
            return rtnTrackingNumber;
        }

        public void setRtnShipmentCost(double rtnShipmentCost) {
            this.rtnShipmentCost = rtnShipmentCost;
        }

        public double getRtnShipmentCost() {
            return rtnShipmentCost;
        }

        public void setRtnPayMethodCode(String rtnPayMethodCode) {
            this.rtnPayMethodCode = rtnPayMethodCode;
        }

        public String getRtnPayMethodCode() {
            return rtnPayMethodCode;
        }

        public void setRtnDeclaredValue(double rtnDeclaredValue) {
            this.rtnDeclaredValue = rtnDeclaredValue;
        }

        public double getRtnDeclaredValue() {
            return rtnDeclaredValue;
        }

        public void setReqVoidFlagHidden(String reqVoidFlagHidden) {
            this.reqVoidFlagHidden = reqVoidFlagHidden;
        }

        public String getReqVoidFlagHidden() {
            return reqVoidFlagHidden;
        }

        public void setBaseCharge(double baseCharge) {
            this.baseCharge = baseCharge;
        }

        public double getBaseCharge() {
            return baseCharge;
        }

        public void setTotalDiscount(double totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public double getTotalDiscount() {
            return totalDiscount;
        }

        public void setRtnBaseChrge(double rtnBaseChrge) {
            this.rtnBaseChrge = rtnBaseChrge;
        }

        public double getRtnBaseChrge() {
            return rtnBaseChrge;
        }

        public void setRtnTotalDiscount(double rtnTotalDiscount) {
            this.rtnTotalDiscount = rtnTotalDiscount;
        }

        public double getRtnTotalDiscount() {
            return rtnTotalDiscount;
        }

        public void setHalFlag(String halFlag) {
            this.halFlag = halFlag;
        }

        public String getHalFlag() {
            return halFlag;
        }

        public void setHalPhone(String halPhone) {
            this.halPhone = halPhone;
        }

        public String getHalPhone() {
            return halPhone;
        }

        public void setHalLine1(String halLine1) {
            this.halLine1 = halLine1;
        }

        public String getHalLine1() {
            return halLine1;
        }

        public void setHalCity(String halCity) {
            this.halCity = halCity;
        }

        public String getHalCity() {
            return halCity;
        }

        public void setHalState(String halState) {
            this.halState = halState;
        }

        public String getHalState() {
            return halState;
        }

        public void setHalPostalCode(String halPostalCode) {
            this.halPostalCode = halPostalCode;
        }

        public String getHalPostalCode() {
            return halPostalCode;
        }

        public void setHazMatFlag(String hazMatFlag) {
            this.HazMatFlag = hazMatFlag;
        }

        public String getHazMatFlag() {
            return HazMatFlag;
        }

        public void setHazMatType(String hazMatType) {
            this.HazMatType = hazMatType;
        }

        public String getHazMatType() {
            return HazMatType;
        }

        public void setHazMatClass(String hazMatClass) {
            this.HazMatClass = hazMatClass;
        }

        public String getHazMatClass() {
            return HazMatClass;
        }

        public void setHazMatCharges(double hazMatCharges) {
            this.HazMatCharges = hazMatCharges;
        }

        public double getHazMatCharges() {
            return HazMatCharges;
        }

        public void setRtnHazMatCharges(double rtnHazMatCharges) {
            this.RtnHazMatCharges = rtnHazMatCharges;
        }

        public double getRtnHazMatCharges() {
            return RtnHazMatCharges;
        }

        public void setHazMatQty(double hazMatQty) {
            this.HazMatQty = hazMatQty;
        }

        public double getHazMatQty() {
            return HazMatQty;
        }

        public void setHazMatUnit(String hazMatUnit) {
            this.HazMatUnit = hazMatUnit;
        }

        public String getHazMatUnit() {
            return HazMatUnit;
        }

        public void setHazMatIdNo(String hazMatIdNo) {
            this.HazMatIdNo = hazMatIdNo;
        }

        public String getHazMatIdNo() {
            return HazMatIdNo;
        }

        public void setHazMatPkgGroup(String hazMatPkgGroup) {
            this.HazMatPkgGroup = hazMatPkgGroup;
        }

        public String getHazMatPkgGroup() {
            return HazMatPkgGroup;
        }

        public void setHazMatDOTLabel(String hazMatDOTLabel) {
            this.HazMatDOTLabel = hazMatDOTLabel;
        }

        public String getHazMatDOTLabel() {
            return HazMatDOTLabel;
        }

        public void setHazMatEmerContactNo(String hazMatEmerContactNo) {
            this.HazMatEmerContactNo = hazMatEmerContactNo;
        }

        public String getHazMatEmerContactNo() {
            return HazMatEmerContactNo;
        }

        public void setHazMatEmerContactName(String hazMatEmerContactName) {
            this.HazMatEmerContactName = hazMatEmerContactName;
        }

        public String getHazMatEmerContactName() {
            return HazMatEmerContactName;
        }

        public void setHazMatId(String hazMatId) {
            this.HazMatId = hazMatId;
        }

        public String getHazMatId() {
            return HazMatId;
        }


        public void setHalStateOrProvince(String halStateOrProvince) {
            this.HalStateOrProvince = halStateOrProvince;
        }

        public String getHalStateOrProvince() {
            return HalStateOrProvince;
        }

        public void setIntlQuantity(double intlQuantity) {
            this.intlQuantity = intlQuantity;
        }

        public double getIntlQuantity() {
            return intlQuantity;
        }

        public void setIntlQUOM(String intlQUOM) {
            this.intlQUOM = intlQUOM;
        }

        public String getIntlQUOM() {
            return intlQUOM;
        }

        public void setIntlUnitWeight(double intlUnitWeight) {
            this.intlUnitWeight = intlUnitWeight;
        }

        public double getIntlUnitWeight() {
            return intlUnitWeight;
        }

        public void setIntlUnitValue(double intlUnitValue) {
            this.intlUnitValue = intlUnitValue;
        }

        public double getIntlUnitValue() {
            return intlUnitValue;
        }

        public void setIntlProductDescription(String intlProductDescription) {
            this.intlProductDescription = intlProductDescription;
        }

        public String getIntlProductDescription() {
            return intlProductDescription;
        }

        public void setHazMatReq(String hazMatReq) {
            this.HazMatReq = hazMatReq;
        }

        public String getHazMatReq() {
            return HazMatReq;
        }

        public void setHazMatLabelType(String hazMatLabelType) {
            this.HazMatLabelType = hazMatLabelType;
        }

        public String getHazMatLabelType() {
            return HazMatLabelType;
        }


        public void setMasterTrackingNumber(String masterTrackingNumber) {
            this.masterTrackingNumber = masterTrackingNumber;
        }

        public String getMasterTrackingNumber() {
            return masterTrackingNumber;
        }

        public void setMasterFormID(String masterFormID) {
            this.masterFormID = masterFormID;
        }

        public String getMasterFormID() {
            return masterFormID;
        }

        public void setCodTrackingNum(String codTrackingNum) {
            this.codTrackingNum = codTrackingNum;
        }

        public String getCodTrackingNum() {
            return codTrackingNum;
        }

        public void setTemporaryFlag(String temporaryFlag) {
            this.temporaryFlag = temporaryFlag;
        }

        public String getTemporaryFlag() {
            return temporaryFlag;
        }

        public void setHazmatPkgingCnt(double hazmatPkgingCnt) {
            this.hazmatPkgingCnt = hazmatPkgingCnt;
        }

        public double getHazmatPkgingCnt() {
            return hazmatPkgingCnt;
        }

        public void setHazmatPkgingUnits(String hazmatPkgingUnits) {
            this.hazmatPkgingUnits = hazmatPkgingUnits;
        }

        public String getHazmatPkgingUnits() {
            return hazmatPkgingUnits;
        }

        public void setHazmatTechnicalName(String hazmatTechnicalName) {
            this.hazmatTechnicalName = hazmatTechnicalName;
        }

        public String getHazmatTechnicalName() {
            return hazmatTechnicalName;
        }

        public void setHazmatSignatureName(String hazmatSignatureName) {
            this.hazmatSignatureName = hazmatSignatureName;
        }

        public String getHazmatSignatureName() {
            return hazmatSignatureName;
        }


    public void setTransitTime(String transitTime) {
        this.transitTime = transitTime;
    }

    public String getTransitTime() {
        return transitTime;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDryIceChk(String dryIceChk) {
        this.dryIceChk = dryIceChk;
    }

    public String getDryIceChk() {
        return dryIceChk;
    }

    public void setDryIceWeight(double dryIceWeight) {
        this.dryIceWeight = dryIceWeight;
    }

    public double getDryIceWeight() {
        return dryIceWeight;
    }

    public void setDryIceUnits(String dryIceUnits) {
        this.dryIceUnits = dryIceUnits;
    }

    public String getDryIceUnits() {
        return dryIceUnits;
    }

    public void setStampsTaxId(String stampsTaxId) {
        this.stampsTaxId = stampsTaxId;
    }

    public String getStampsTaxId() {
        return stampsTaxId;
    }

    public void setHazmatPackageInfoList(LinkedList hazmatPackageInfoList) {
        this.hazmatPackageInfoList = hazmatPackageInfoList;
    }

    public LinkedList getHazmatPackageInfoList() {
        return hazmatPackageInfoList;
    }

    public void setDeliveryConfirmation(String deliveryConfirmation) {
        this.deliveryConfirmation = deliveryConfirmation;
    }

    public String getDeliveryConfirmation() {
        return deliveryConfirmation;
    }

    public void setRtnDesc(String rtnDesc) {
        this.rtnDesc = rtnDesc;
    }

    public String getRtnDesc() {
        return rtnDesc;
    }

    public void setRtnCountrySymbol(String rtnCountrySymbol) {
        this.rtnCountrySymbol = rtnCountrySymbol;
    }

    public String getRtnCountrySymbol() {
        return rtnCountrySymbol;
    }

    public void setHazmatPackInstructions(String hazmatPackInstructions) {
        this.HazmatPackInstructions = hazmatPackInstructions;
    }

    public String getHazmatPackInstructions() {
        return HazmatPackInstructions;
    }

    public void setLargePackage(String largePackage) {
        this.LargePackage = largePackage;
    }

    public String getLargePackage() {
        return LargePackage;
    }

    public void setAdditionalHandling(String additionalHandling) {
        this.AdditionalHandling = additionalHandling;
    }

    public String getAdditionalHandling() {
        return AdditionalHandling;
    }
} // end of class

