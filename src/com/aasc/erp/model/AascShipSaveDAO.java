/*

 * @(#)AascShipSaveDAO.java        15/12/2014

 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipmentOrderInfo;

/**

 AascShipSaveDAO is an interface has an abstract method which is implemented 

 by the AascOracleShipSaveDAO class.AascOracleShipSaveDAO is a class which interacts with the database and 

 corresponding bean file for saving the Adhoc related information.

 */
 
 /*

  * @author Y Pradeep
  * @version 1.0
  */

 /* ========================================================================================

  Date        Resource       Change history

  ------------------------------------------------------------------------------------------
  17/12/2014  Eshwari M      Renamed Adhoc to Shipment for SC Lite
  16/02/2015  Y Pradeep      Modified code to generate order number on click og Ship button in Shipping page.

  ========================================================================================*/

  public interface AascShipSaveDAO {

     /**

      * This method  implements by the AascOracleShipSaveDAO class.

      * @param userId  int

      * @param aascShipmentOrderInfo Object 

      * @return returnStatus int

      */
     public  int getShipSaveDAO(int userId, AascShipmentOrderInfo aascShipmentOrderInfo,int clientID, int carrierSuccessStatus);

 }
