package com.aasc.erp.bean;
/*
 * @(#)AascIntlCommodityInfo.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
/**
 * AascIntlCommodityInfo Bean Class for Commodity information of International shipments.
 * Module with getXXX() and setXXX() methods. 
 * @author 	Y Pradeep
 * @version - 1
 ========================================================================================
 Date         Resource                 Change history
 ------------------------------------------------------------------------------------------
 * 18/12/2014   Y Pradeep        Modified orderNumber from int to String.
 * 28/01/2015   Y Pradeep        Added for International Shipping.
 * 11/02/2015   Y Pradeep        Code alignment.
 **/
public class AascIntlCommodityInfo {
    private int commodityNumber; //indicates commodity number of international shipments
    private String orderNumber; // contains (unique)order number which is generated while placing of order
    private int shipmentId; //indicates shipment Id for international shipments
    private String voidFlag = ""; // indicates cancellation of a delivery for international shipments
    private int numberOfPieces = 1; //indicates number of pieces for each commodity item
    private String description = ""; //indicates description of item
    private String countryOfManufacture = ""; //indicates country of manufacture of item
    private String harmonizedCode = ""; //indicates harmonized code of item
    private double weight = 0.0; //indicates weight of item
    private String quantity = ""; //indicates quantity of item
    private String quantityUnits = ""; //indicates units of quantity
    private String unitPrice = ""; //indicates unit price of item
    private String customsValue = ""; //indicates customs value of item
    private String exportLicenseNumber = ""; //indicates export license number of the shipper
    private String exportLicenseExpiryDate = ""; //indicates export license Expiry Date
    private String preferenceCriteria = ""; //indicates Preference Criteria 
    private String producer = ""; //indicates Producer of the item
    private String rvcCalculationMethod = ""; //indicates RVC Calculation Method
    private String tariffCode = ""; //indicates Tariff Code
    private String netCostPeriodBeginDate = ""; // NetCostPeriod Begin Date
    private String netCostPeriodEndDate = ""; // NetCostPeriod End Date
    private double sedTotalValue = 0.0; //indicates sedTotalValue
    private String scheduleBNumber = ""; //indicates scheduleBNumber
    private String scheduleBQty = ""; //indicates scheduleBQty
    private String scheduleBUOM = ""; //indicates scheduleBUOM
    private String exportType = ""; //indicates exportType
    private int addressId = 0;
    AascAddressInfo aascAddressInfo;
    private String packageWeightUom = "";
    private int carrierCode = 0;

    public AascIntlCommodityInfo() {
    }

    /** method to retreive commodity Number.
     * @return commodityNumber int
     */
    public int getCommodityNumber() {
        return commodityNumber;
    }

    /** method to set commodity Number.
     * @param commodityNumber int
     */
    public void setCommodityNumber(int commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    /** method to retreive Shipment Id.
     * @return shipmentId int
     */
    public int getShipmentId() {
        return shipmentId;
    }

    /** method to set Shipment Id.
     * @param shipmentId int
     */
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    /** method to get the value of void flag. 
     * which indicates cancellation of shipment of that delivery
     * @return voidFlag String
     */
    public String getVoidFlag() {
        return voidFlag;
    }

    /** method to set the value of void flag.
     * which indicates cancellation of shipment of that delivery
     * @param voidFlag String
     */
    public void setVoidFlag(String voidFlag) {
        this.voidFlag = voidFlag;
    }

    /** method to retreive number of pieces for each commodity item.
     * @return numberOfPieces int
     */
    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    /** method to set number of pieces for each commodity item.
     * @param numberOfPieces int
     */
    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    /**
     Method description gets the Product Description for International Quantity. 
     @return description String. 
     */
    public String getDescription() {
        return description;
    }

    /**
     Method description to set Product Description for International Quantity. 
     @param description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     Method description gets the country of manufacture of item for International Quantity. 
     @return countryOfManufacture String. 
     */
    public String getCountryOfManufacture() {
        return countryOfManufacture;
    }

    /**
     Method description to set country of manufacture of item for International Quantity. 
     @param countryOfManufacture String.
     */
    public void setCountryOfManufacture(String countryOfManufacture) {
        this.countryOfManufacture = countryOfManufacture;
    }

    /**
     Method description gets the harmonized code of item for International Quantity. 
     @return harmonizedCode String. 
     */
    public String getHarmonizedCode() {
        return harmonizedCode;
    }

    /**
     Method description to set harmonized code of item for International Quantity. 
     @param harmonizedCode String.
     */
    public void setHarmonizedCode(String harmonizedCode) {
        this.harmonizedCode = harmonizedCode;
    }

    /**
     Method getIntlUnitWeight gets the Unit Weight for International Quantity.
     @return weight double. 
     */
    public double getWeight() {
        return weight;
    }

    /**
     Method setIntlUnitWeight to set Unit Weight for International Quantity.
     @param weight double.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     Method getQuantity gets the International Quantity. 
     @return quantity double. 
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     Method setQuantity to set International Quantity. 
     @param quantity double.
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     Method getQuantityUnits gets the Unit Of Measure for International Quantity. 
     @return quantityUnits String. 
     */
    public String getQuantityUnits() {
        return quantityUnits;
    }

    /**
     Method setQuantityUnits to set Unit Of Measure for International Quantity. 
     @param quantityUnits String.
     */
    public void setQuantityUnits(String quantityUnits) {
        this.quantityUnits = quantityUnits;
    }

    /**
     Method getUnitPrice gets the Unit price of International item. 
     @return unitPrice String. 
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     Method setUnitPrice to set the Unit price of International item. 
     @param unitPrice String.
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     Method getCustomsValue gets the customs value for International Item. 
     @return customsValue String. 
     */
    public String getCustomsValue() {
        return customsValue;
    }

    /**
     Method setCustomsValue to set the customs value for International Item. 
     @param customsValue String.
     */
    public void setCustomsValue(String customsValue) {
        this.customsValue = customsValue;
    }

    /**
     Method getExportLicenseNumber gets the Export License Number of the shipper. 
     @return exportLicenseNumber String. 
     */
    public String getExportLicenseNumber() {
        return exportLicenseNumber;
    }

    /**
     Method setExportLicenseNumber to set the Export License Number of the shipper. 
     @param exportLicenseNumber String.
     */
    public void setExportLicenseNumber(String exportLicenseNumber) {
        this.exportLicenseNumber = exportLicenseNumber;
    }

    /** method to retreive export license Expiry Date.
     * @return exportLicenseExpiryDate java.sql.Date
     */
    public String getExportLicenseExpiryDate() {
        return exportLicenseExpiryDate;
    }

    /** method to set export license Expiry Date.
     * @param exportLicenseExpiryDate java.sql.Date
     */
    public void setExportLicenseExpiryDate(String exportLicenseExpiryDate) {
        this.exportLicenseExpiryDate = exportLicenseExpiryDate;
    }

    /**
     Method preferenceCriteria gets the Product preferenceCriteria for International Quantity. 
     @return preferenceCriteria String. 
     */
    public String getPreferenceCriteria() {
        return preferenceCriteria;
    }

    /**
     Method preferenceCriteria to set Product preferenceCriteria for International Quantity. 
     @param preferenceCriteria String.
     */
    public void setPreferenceCriteria(String preferenceCriteria) {
        this.preferenceCriteria = preferenceCriteria;
    }

    /**
     Method producer gets the Product producer for International Quantity. 
     @return producer String. 
     */
    public String getProducer() {
        return producer;
    }

    /**
     Method description to set Product producer for International Quantity. 
     @param producer String.
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     Method rvcCalculationMethod gets the rvcCalculationMethod for International Quantity. 
     @return rvcCalculationMethod String. 
     */
    public String getRvcCalculationMethod() {
        return rvcCalculationMethod;
    }

    /**
     Method rvcCalculationMethod to set rvcCalculationMethod for International Quantity. 
     @param rvcCalculationMethod String.
     */
    public void setRvcCalculationMethod(String rvcCalculationMethod) {
        this.rvcCalculationMethod = rvcCalculationMethod;
    }

    /**
     Method tariffCode gets the Product tariffCode for International Quantity. 
     @return tariffCode String. 
     */
    public String getTariffCode() {
        return tariffCode;
    }

    /**
     Method tariffCode to set Product tariffCode for International Quantity. 
     @param tariffCode String.
     */
    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    /**
     Method netCostPeriodBeginDate to set the netCostPeriodBeginDate.
     @param netCostPeriodBeginDate String.
     */
    public void setNetCostPeriodBeginDate(String netCostPeriodBeginDate) {
        this.netCostPeriodBeginDate = netCostPeriodBeginDate;
    }

    /**
     Method netCostPeriodBeginDate returns netCostPeriodBeginDate.
     @return netCostPeriodBeginDate String.
     */
    public String getNetCostPeriodBeginDate() {
        return netCostPeriodBeginDate;
    }

    /**
     Method netCostPeriodEndDate to set netCostPeriodEndDate.
     @param netCostPeriodEndDate String.
     */
    public void setNetCostPeriodEndDate(String netCostPeriodEndDate) {
        this.netCostPeriodEndDate = netCostPeriodEndDate;
    }

    /**
     Method netCostPeriodEndDate returns netCostPeriodEndDate.
     @return netCostPeriodEndDate String.
     */
    public String getNetCostPeriodEndDate() {
        return netCostPeriodEndDate;
    }

    /** method to retreive sedTotalValue.
     * @return sedTotalValue double
     */
    public double getSedTotalValue() {
        return sedTotalValue;
    }

    /** method to set sedTotalValue.
     * @param sedTotalValue int
     */
    public void setSedTotalValue(double sedTotalValue) {
        this.sedTotalValue = sedTotalValue;
    }

    /**
     Method scheduleBNumber gets the Product scheduleBNumber for International Quantity. 
     @return scheduleBNumber String. 
     */
    public String getScheduleBNumber() {
        return scheduleBNumber;
    }

    /**
     Method scheduleBNumber to set Product scheduleBNumber for International Quantity. 
     @param scheduleBNumber String.
     */
    public void setScheduleBNumber(String scheduleBNumber) {
        this.scheduleBNumber = scheduleBNumber;
    }

    /**
     Method scheduleBQty gets the Product scheduleBQty for International Quantity. 
     @return scheduleBQty String. 
     */
    public String getScheduleBQty() {
        return scheduleBQty;
    }

    /**
     Method scheduleBQty to set Product scheduleBQty for International Quantity. 
     @param scheduleBQty String.
     */
    public void setScheduleBQty(String scheduleBQty) {
        this.scheduleBQty = scheduleBQty;
    }

    /**
     Method scheduleBUOM gets the Product scheduleBUOM for International Quantity. 
     @return scheduleBUOM String. 
     */
    public String getScheduleBUOM() {
        return scheduleBUOM;
    }

    /**
     Method scheduleBUOM to set Product scheduleBUOM for International Quantity. 
     @param scheduleBUOM String.
     */
    public void setScheduleBUOM(String scheduleBUOM) {
        this.scheduleBUOM = scheduleBUOM;
    }

    /**
     Method exportType gets the Product exportType for International Quantity. 
     @return exportType String. 
     */
    public String getExportType() {
        return exportType;
    }

    /**
     Method exportType to set Product exportType for International Quantity. 
     @param exportType String.
     */
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public AascAddressInfo getAascAddressInfo() {
        return aascAddressInfo;
    }

    public void setAascAddressInfo(AascAddressInfo aascAddressInfo) {
        this.aascAddressInfo = aascAddressInfo;
    }

    public String getPackageWeightUom() {
        return packageWeightUom;
    }

    public void setPackageWeightUom(String packageWeightUom) {
        this.packageWeightUom = packageWeightUom;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
