/*
 * @(#)AascPrinterInfo.java        23/06/2015
 *
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

import java.util.LinkedList;

/**
 * AascPrinterInfo is a bean class which contains getXXX() and setXXX() methods for printer fields
 * and contains getXXX() and setXXX() methods for linked lists-printerDetailList and printerList
 * it also contains a method which retrieves the printer name
 * 
 * @version - 1
 * @author 	Y Pradeep
 * @history 
 * 
 * 23/06/2015   Y Pradeep       Added code to access multiple Printers based on Lable Format lables in Profile Options page.
 * 13/07/2015   Y Pradeep       Added Print Server IP Address field and related code.
 * 11/03/2016   Y Pradeep       Removed Printer IP Address related code as it is not required.
 */
public class AascPrinterInfo {
    private int printerIndex; 
    private String printer = ""; 
    private String labelFormat = "";
    private String printerName = "";
    private String enabledFlag="";
    private int locationId = 0;
    private int clientId = 0;
    private int userid;
    private String errorStatus = "";
    private int opStatus = 0;   
    private LinkedList labelFormatList;

    public void setPrinterIndex(int printerIndex) {
        this.printerIndex = printerIndex;
    }

    public int getPrinterIndex() {
        return printerIndex;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getPrinter() {
        return printer;
    }

    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    public String getLabelFormat() {
        return labelFormat;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public void setLabelFormatList(LinkedList labelFormatList) {
        this.labelFormatList = labelFormatList;
    }

    public LinkedList getLabelFormatList() {
        return labelFormatList;
    }

}

