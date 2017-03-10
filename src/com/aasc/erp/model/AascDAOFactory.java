package com.aasc.erp.model;

/*========================================================================+
@(#)AascDAOFactory.java 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

 /**
  * AascDAOFactory is an abstract class having abstract method which is extended by the  AascOracleDAOFactory class.
  * Abstract Class returns the DAO factory for back end Systems
  * @author      N Srisha
  * @version 1.0
  * @since
  * 
  * @History
  * 
  * 17/12/2014   Eshwari M    Renamed adhoc to shipment for SC Lite
  * 06/02/2014   Sunanda K    Added getAascVoidShipmentDAO() method
  * 23/06/2015   Y Pradeep    Added code related to AascPrinterSetupDAO call for Printer Setup in Options page.
  * 26/02/2016   Suman G      Added getAascSubscriptionDetailsDAO() method to get Object.
  **/

public abstract class AascDAOFactory {
    public AascDAOFactory() {
    }
    

   /**
     * 
     * @param whichFactory
     * @return the DAO factory depending upon the input , 
     *    by default returns OracleDAO Factory
     */
     
    public static AascDAOFactory getAascDAOFactory(int whichFactory) {

            return new AascOracleDAOFactory();

        }
    public abstract AascAccountNumbersDAO getAccountNumbersDAO();
    
    public abstract AascReportsInfoDAO getAascReportsInfoDAO();
    
    public abstract AascProfileOptionsDAO getAascProfileOptionsDAO();
    
    public abstract AascShipMethodDAO getAascShipMethodDAO();

    public abstract AascGetLocDAO getLocationDAO();  
    

    public abstract AascPrinterSetupDAO getAascPrinterSetupDAO();
    public abstract AascTrackingOrderInfoDAO getAascTrackingOrderInfoDAO() ;
    public abstract AascPackageDimensionDAO getPackageDimensionDAO();
    public abstract AascShipmentOrderInfoDAO getAascShipmentOrderInfoDAO();
    public abstract AascSetupLocationDAO getAascSetupLocationDAO();
    public abstract AascShipSaveDAO getAascShipSaveDAO();
    public abstract AascIntlShipmentsDAO getAascIntlShipmentsDAO();
    
    public abstract AascShipMethodAjaxDAO getAascShipMethodAjaxDAO();
    // Add void shipment DAO
    public abstract AascVoidShipmentDAO getAascVoidShipmentDAO();
    
    public abstract AascSubscriptionDetailsDAO getAascSubscriptionDetailsDAO();
}  
