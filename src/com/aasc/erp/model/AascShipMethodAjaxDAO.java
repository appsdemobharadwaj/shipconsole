/*
 * $Header:AascShipMethodAjaxDAO.java       
 *
 *  ====================================================================
 * @author  Eshwari M
 * Version 1.0
 *
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 * ====================================================================
 *
 */
package com.aasc.erp.model;


/**
 * AascShipMethodAjaxDAO .
 * @author   Eshwari M
 * @version  1.1
 * This interface is used to get Shipmethod details from data base
 * 
 * 11/02/2015   Suman G     Added retrieveDefaultAcNo() for getting TP and FC account numbers.
 * 20/07/2015   Suman G     Added code for implement Email Notification.
 */
public interface AascShipMethodAjaxDAO {

    /**
     * This method retrives Carrier Code and Carrier Service Level(Connect ship tag) from databse .
     * @param  useShipMethodMeaning  String  Ship Method for which the service levels has to be fetched
     * @param  locationID              int    locaton Id for which the shipmethod service levels has to be fetched
     * @param  clientId                int    clientId 
     * @return a String with Carrier Code, Carrier Service level
     */
    public String retrieveCcCsl(String useShipMethodMeaning, 
                                int locationID, int carrierId, int clientId);

     /**
      * This method retrives Drop Off Type.
      * @param carrierCode          int     carrier code for which dropoff type has to fetch
      * @param carrierServiceLevel  String  carrier service level for which dropoff type has to fetch
      * @return a String Drop Off Type
      */
    public String retrieveDot(int carrierCode, String carrierServiceLevel);

     /**
      * This method retrives Packaging.
      * @param carrierCode         carrier code for which packaging has to fetch
      * @param carrierServiceLevel carrier service level for which packaging has to fetch
      * @param dropOffType         Drop Off Type for which packaging has to fetch
      * @return a String Packaging
      */
    public String retrievePackaging(int carrierCode, 
                                    String carrierServiceLevel, 
                                    String dropOffType);

     /**
      * This method retrives Email Details.
      * @param carrierCode   carrier for which eamil details has to fetch
      * @param locationId    location for which eamil details has to fetch
      * @param orderNumber   order number for which eamil details has to fetch
      * @param clientId      clientId fo the user logged in
      * @return a String contianinf email details
      */
    public String getEmailDetails(int carrierCode, int locationId, int orderNumber, 
                                  int clientId); // ClientId Added by suman Gunda

     /**
      * This method retrives Carrier Pay Method.
      * @param carrierCode           carrier code for which carrier pay method has to fetch
      * @param carrierServiceLevel   carrier service level for which carrier pay method has to fetch
      * @param dropOffType           Drop Off Type for which carrier pay method has to fetch
      * @param packaging             Packaging for which carrier pay method has to fetch
      * @return a String Carrier Pay Method
      */
    public String retrieveCpt(int carrierCode, String carrierServiceLevel, 
                              String dropOffType, String packaging);

     /**
      * This method retrives Carrier Pay Method.
      * @param carrierCode            carrier code for which service level code has to fetch
      * @param carrierServiceLevel    carrier service level  for which service level code has to fetch
      * @param ajaxOriginRegionCode   region code  for which service level code has to fetch
      * @return a String Ups Service Level Code
      */
    public String retrieveUpsServiceLevelCode(int carrierCode, 
                                              String carrierServiceLevel, 
                                              String ajaxOriginRegionCode);

     /**
      * This method retrives Carrier Code Combination Values.
      * @param carrierCode           carrier code for which carrier code combination values has to fetch
      * @param carrierServiceLevel   carrier service level for which carrier code combination values has to fetch
      * @param dropOffType           Drop Off Type for which carrier code combination values has to fetch
      * @param packaging             Packaging for which carrier code combination values has to fetch
      * @param carrierPaymentTerms   Carrier payment terms for which carrier code combination values has to fetch
      * @return a String carrier code combination values
      */
    public String getCarrierCombValidValues(int carrierCode, 
                                            String carrierServiceLevel, 
                                            String dropOffType, 
                                            String packaging, 
                                            String carrierPaymentTerms);
    /**
     * This method retrives Default Account Numbers.
     * @param carrierCode           int
     * @param clientId              int
     * @param custLocationId        String
     * @param locationSelected      String
     * @return a String carrier code combination values
     */                                        
    public String retrieveDefaultAcNo(int clientId, String custLocationId, String locationSelected,int carrierCode);                                            
    
    /**
     * This method retrives Email Details.
     * @param carrierCode           int
     * @param clientId              int
     * @param locationId      String
     * @return a String carrier code combination values
     */   
    public String getEmailDetails(int carrierCode,int locationId, int clientId);
}

