/*

 * @(#)AascShipmentOrderInfo.java        

 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

package com.aasc.erp.bean;

import java.util.HashMap;
import java.util.LinkedList;


/**

 AascShipmentOrderInfo class is used to set Header and Package class objects

 into setXXX Methods and to get the header and package objects from getXXX Methods.

 */
public class

/*

 * @author 

 * @version 1.0

 * @since Modified by Suman Gunda

 */

/* ========================================================================================

 Date        Resource       Change history

 ------------------------------------------------------------------------------------------

 19/01/2015     Suman G       Removed unncessary code 
 02/04/2015     Y Pradeep     Added intlFlag variable.
 22/04/2015     Suman G       Added fsRates variable to fix #2730
 23/06/2015     Y Pradeep     Added aascPrinterInfoList variable for accessing list of enabled printers from Printer Setup table based on Ship from location after Shipping Successfully.
 13/07/2015     Y Pradeep     Removed aascPrinterInfoList variable and its related code.

 ========================================================================================*/

AascShipmentOrderInfo {

    private AascShipmentHeaderInfo headerInfo; // holds Object of AascShipmentHeaderInfo class

    private LinkedList packageInfo; // holds the LinkedList object in which  AascShipmentPackageInfo class objects are there

    private int returnStatus; // holds the value of the  returnsStatus from the data base        
    
    private String intlFlag;
    
    private String fsRates; //  holds rates which we got from Freight Shopping.
    

    /**

     * Default constructor.

     **/
    public

    AascShipmentOrderInfo() {

        headerInfo = new AascShipmentHeaderInfo();

        packageInfo = new LinkedList();

        packageInfo.add(new AascShipmentPackageInfo());

    }

    /**
     * 
     * Method setShipmentHeaderInfo to set aascShipmentHeaderInfo object for header information.
     * 
     * @param aascShipmentHeaderInfo of type AascShipmentHeaderInfo the input value.
     * 
     */
    public

    void setShipmentHeaderInfo(AascShipmentHeaderInfo aascShipmentHeaderInfo) {

        this.headerInfo = aascShipmentHeaderInfo;

    }

    /**

     * Method setShipmentPackageInfo to set LinkedList object for package information.

     * @param packageList of type LinkedList the input value.

     */
    public

    void setShipmentPackageInfo(LinkedList packageList) {

        this.packageInfo = packageList;

    }

    /**

     * Method setReturnStatus to set the returnStatus. 

     * @param returnStatus of type int the input value.

     */
    public

    void setReturnStatus(int returnStatus) {

        this.returnStatus = returnStatus;

    }

    /**
     * 
     * Method getShipmentHeaderInfo returns headerInfo object for header information.
     * 
     * @return headerInfo of type AascShipmentHeaderInfo.
     * 
     */
    public

    AascShipmentHeaderInfo getShipmentHeaderInfo() {

        return headerInfo;

    }

    /**

     * Method getShipmentPackageInfo returns packageInfo object for package information.

     * @return packageInfo of type LinkedList.

     */
    public

    LinkedList getShipmentPackageInfo() {

        return packageInfo;

    }

    /**

     * Method getReturnStatus returns returnStatus of type int.

     * @return returnStatus of type int.

     */
    public

    int getReturnStatus() {

        return returnStatus;

    }


    public void setIntlFlag(String intlFlag) {
        this.intlFlag = intlFlag;
    }

    public String getIntlFlag() {
        return intlFlag;
    }


    public void setFsRates(String fsRates) {
        this.fsRates = fsRates;
    }

    public String getFsRates() {
        return fsRates;
    }

} // End of AascShipmentOrderInfo class
