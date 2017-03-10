package com.aasc.erp.bean;
/*
 * @(#)AascIntlInfo.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 
import java.util.LinkedList;

/**
 * AascIntlCommodityInfo Bean Class is used to set HeaderInformation,CommodityInformation 
 * of particular delivery for International shipments.
 * Module with getXXX() and setXXX() methods. 
 * @author Y Pradeep
 * @version - 1
 ========================================================================================
 Date           Resource                 Change history
 ------------------------------------------------------------------------------------------
 * 28/01/2015   Y Pradeep        Added for International Shipping.
 * 11/02/2015   Y Pradeep        Code alignment.
 **/
public class AascIntlInfo {
    AascIntlHeaderInfo aascIntlHeaderInfo;
    LinkedList commodityList;
    int returnStatus;

    public AascIntlInfo() {
        aascIntlHeaderInfo = new AascIntlHeaderInfo();
        commodityList = new LinkedList();
        commodityList.add(new AascIntlCommodityInfo());
    }

    /** This method can sets the object of class AascIntlHeaderInfo.
     *    @param aascIntlHeaderInfo of type AascIntlHeaderInfo
     *    
     */
    public void setIntlHeaderInfo(AascIntlHeaderInfo aascIntlHeaderInfo) {
        this.aascIntlHeaderInfo = aascIntlHeaderInfo;
    }

    /** This method can returns the object of AascIntlHeaderInfo.
     *    @return object of type AascIntlHeaderInfo
     */
    public AascIntlHeaderInfo getIntlHeaderInfo() {
        return aascIntlHeaderInfo;
    }

    /** This method can sets the object of LinkedList.
     * @param commodityList of type LinkedList
     *    
     */
    public void setIntlCommodityInfo(LinkedList commodityList) {
        this.commodityList = commodityList;
    }

    /** This method can returns the object of LinkedList.
     *    @return commodityList of type LinkedList
     */
    public LinkedList getIntlCommodityInfo() {
        return commodityList;
    }

    /** This method can sets the returnStatus.
     *    @param returnStatus variable of type int 
     *    
     */
    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    /** This method can returns returnStatus.
     *@return returnStatus variable of type int
     */
    public int getReturnStatus() {
        return returnStatus;
    }


}
