/*

 * @(#)AascShipmentOrderInfoDAO.java        15/12/2014

 ** Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipmentOrderInfo;


/**
 * 
 * AascShipmentOrderInfoDAO is an interface has an abstract method 
 * 
 * which is implemented by the  AascOracleShipmentOrderInfoDAO class.
 * 
 * AascOracleShipmentOrderInfoDAO is a class which interacts with the database and 
 * 
 * corresponding bean file for retriving the Shipment related information.
 * 
 */
public interface

/*

 * @author Y Pradeep
 * @version 1.0
 * 
 * History
 * 
 * 08/05/2015  Y Pradeep      Added deletePackage method for deleting or clearing package details in database. Bug #2910.
 * 13/07/2015  Y Pradeep      Removed getDefaultPrinter method call.
 */

AascShipmentOrderInfoDAO {

    /**
     * 
     * This Method implements by the  AascOracleShipmentOrderInfoDAO class.
     * 
     * @param orderNumber int
     * 
     * @return AascShipmentOrderInfo Object
     * 
     * 19/12/2014   Y Pradeep   Modified getOrderNumber method data type to String
     * 
     */
    public

    AascShipmentOrderInfo getShipmentOrderInfo(String orderNumber,int clientID);

    /**
     * 
     * This Method implements by the  AascOracleShipmentOrderInfoDAO class.
     * 
     * @param clientId int
     * 
     * @return String Object
     * 
     */
    public String getOrderNumber(int clientId);
    
    /**
     * 
     * This Method implements by the  AascOracleShipmentOrderInfoDAO class.
     * 
     * @param clientId int
     * @param orderNumber String
     * @param packNumber int
     * 
     * @return String Object
     * 
     */
    public String deletePackage(int clientId, String orderNumber, int packNumber);
    
    public int verifyCustomerName(int clientId, int userId, String customerName);
}// End of interface AascShipmentOrderInfoDAO
