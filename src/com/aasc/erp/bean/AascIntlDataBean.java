package com.aasc.erp.bean;
/*
 * @(#)AascIntlShipDelegate.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.ol
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * AascIntlDataBean class.
 * @author 	Y Pradeep
 * @version 1
 * @since
 * 28/01/2015   Y Pradeep    Added this file for Internationa Shipping which is used to get data from database on load of FedEx and UPS International Pages.
 * 20/03/2015   Y Pradeep    Modified data type for countryList, currencyCodeList from HashMap to Map.
 */

public class AascIntlDataBean {
    public AascIntlDataBean() {
    }
    Map countryList;
    Map currencyCodeList;
    HashMap sbUOM;
    HashMap exportType;
    HashMap rvcCalMthd;
    HashMap producer;
    HashMap preferenceCriteria;
    HashMap UOM;
    HashMap TOM;
    HashMap reasonExport;
    HashMap licExpCode;
    HashMap bondCode;
    HashMap modeTransport;
    AascIntlCommodityInfo aascCommodityBean;
    AascIntlHeaderInfo intlHeaderBean;
    AascIntlInfo intlInfoBean;
    String commercialInvChecked = "";
    String usCertOrigin = "";
    String naftaCertOrigin = "";
    String shippersExportDecl = "";
    HashMap impNames;
    HashMap brokerNames;
    String generateCI = "";
    double unitprice = 0.0;
    String payerType = "";

    public void setCountryList(Map countryList) {
        this.countryList = countryList;
    }

    public Map getCountryList() {
        return countryList;
    }

    public void setCurrencyCodeList(Map currencyCodeList) {
        this.currencyCodeList = currencyCodeList;
    }

    public Map getCurrencyCodeList() {
        return currencyCodeList;
    }

    public void setSbUOM(HashMap sbUOM) {
        this.sbUOM = sbUOM;
    }

    public HashMap getSbUOM() {
        return sbUOM;
    }

    public void setExportType(HashMap exportType) {
        this.exportType = exportType;
    }

    public HashMap getExportType() {
        return exportType;
    }

    public void setRvcCalMthd(HashMap rvcCalMthd) {
        this.rvcCalMthd = rvcCalMthd;
    }

    public HashMap getRvcCalMthd() {
        return rvcCalMthd;
    }

    public void setProducer(HashMap producer) {
        this.producer = producer;
    }

    public HashMap getProducer() {
        return producer;
    }

    public void setPreferenceCriteria(HashMap preferenceCriteria) {
        this.preferenceCriteria = preferenceCriteria;
    }

    public HashMap getPreferenceCriteria() {
        return preferenceCriteria;
    }

    public void setUOM(HashMap uOM) {
        this.UOM = uOM;
    }

    public HashMap getUOM() {
        return UOM;
    }

    public void setTOM(HashMap tOM) {
        this.TOM = tOM;
    }

    public HashMap getTOM() {
        return TOM;
    }

    public void setReasonExport(HashMap reasonExport) {
        this.reasonExport = reasonExport;
    }

    public HashMap getReasonExport() {
        return reasonExport;
    }

    public void setLicExpCode(HashMap licExpCode) {
        this.licExpCode = licExpCode;
    }

    public HashMap getLicExpCode() {
        return licExpCode;
    }

    public void setBondCode(HashMap bondCode) {
        this.bondCode = bondCode;
    }

    public HashMap getBondCode() {
        return bondCode;
    }

    public void setModeTransport(HashMap modeTransport) {
        this.modeTransport = modeTransport;
    }

    public HashMap getModeTransport() {
        return modeTransport;
    }

    public void setAascCommodityBean(AascIntlCommodityInfo aascCommodityBean) {
        this.aascCommodityBean = aascCommodityBean;
    }

    public AascIntlCommodityInfo getAascCommodityBean() {
        return aascCommodityBean;
    }

    public void setIntlHeaderBean(AascIntlHeaderInfo intlHeaderBean) {
        this.intlHeaderBean = intlHeaderBean;
    }

    public AascIntlHeaderInfo getIntlHeaderBean() {
        return intlHeaderBean;
    }

    public void setCommercialInvChecked(String commercialInvChecked) {
        this.commercialInvChecked = commercialInvChecked;
    }

    public String getCommercialInvChecked() {
        return commercialInvChecked;
    }

    public void setUsCertOrigin(String usCertOrigin) {
        this.usCertOrigin = usCertOrigin;
    }

    public String getUsCertOrigin() {
        return usCertOrigin;
    }

    public void setNaftaCertOrigin(String naftaCertOrigin) {
        this.naftaCertOrigin = naftaCertOrigin;
    }

    public String getNaftaCertOrigin() {
        return naftaCertOrigin;
    }

    public void setShippersExportDecl(String shippersExportDecl) {
        this.shippersExportDecl = shippersExportDecl;
    }

    public String getShippersExportDecl() {
        return shippersExportDecl;
    }

    public void setImpNames(HashMap impNames) {
        this.impNames = impNames;
    }

    public HashMap getImpNames() {
        return impNames;
    }

    public void setBrokerNames(HashMap brokerNames) {
        this.brokerNames = brokerNames;
    }

    public HashMap getBrokerNames() {
        return brokerNames;
    }

    public void setGenerateCI(String generateCI) {
        this.generateCI = generateCI;
    }

    public String getGenerateCI() {
        return generateCI;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setPayerType(String payerType) {
        this.payerType = payerType;
    }

    public String getPayerType() {
        return payerType;
    }

    public void setIntlInfoBean(AascIntlInfo intlInfoBean) {
        this.intlInfoBean = intlInfoBean;
    }

    public AascIntlInfo getIntlInfoBean() {
        return intlInfoBean;
    }
}
