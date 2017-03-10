/*
 * @(#)AascTrackingDeliveryInfoDAO.java
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.model;

import com.aasc.erp.bean.AascTrackingOrderInfo;

import java.util.LinkedList;


/**
 AascTrackingOrderInfoDAO interface class is implemented by 
 AascOracleTrackingOrderInfoDAO class.
 This class has 3 abstract methods which gets Tracking Information.
 */
/*
 *@author Eshwari M
 *@version 1.0
 */

/* ========================================================================================
 Date         Resource    Change history
 ------------------------------------------------------------------------------------------
 17/12/2014   Eshwari M   Renamed this file name from AascTrackingDelvieryInfoDAO to AascTrackingOrderInfoDAO for SC Lite
 31/12/2014   Eshwari M   Moved getTrackingDetails, saveTrackingDetails methods from AascReportsInfoDAO class to this class
 20/01/2015   Y Pradeep   Modified auther and version, also removed commented code.
 02/02/2015   Eshwari M   Moved getTrackingDetails, saveTrackingDetails methods from this class to AascOracleReportsInfoDAO class as these methods are related to Report
 14/03/2015   Eshwari M   Removed queryTimeOut
 ========================================================================================*/

 public interface AascTrackingOrderInfoDAO {

    /**
     Method getDeliveryInfo() gets header and package information of given deliveryName.
     @param orderNumber String.
     @return aascTrackingDeliveryInfo object of AascTrackingDeliveryInfo class.
     */
    public abstract AascTrackingOrderInfo getOrderInfo(String orderNumber, 
                                                             int clientId, 
                                                             int locationId);  

    /**
     Method getTrackingOrderInfo() gets header and package information of given trackingNumber.
     @param trackingNumber String.
     @return aascTrackingDeliveryInfo object of AascTrackingDeliveryInfo class.
     */
    public abstract AascTrackingOrderInfo getTrackingOrderInfo(String trackingNumber, 
                                                                     int clientId, 
                                                                     int locationId);

    /**
     Method getDeliveryNames() gets list of deliveryNames.
     @param orderNumber int.
     @return AascTrackingDeliveryInfo object of AascTrackingDeliveryInfo class.
     */
    public abstract AascTrackingOrderInfo getOrderNames(String orderNumber, 
                                                              int clientId, 
                                                              int locationId);
                                                              
   
}

   
