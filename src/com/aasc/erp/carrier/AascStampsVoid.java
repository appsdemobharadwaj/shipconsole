 /*
  * @(#)AascStampsVoid.java        01/11/2015
  * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
  * All rights reserved.
  @History
  01/11/2015  Mahesh V    Added files for Stamps.com Integration


  */


 package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.carrier.stampsws.proxy.Credentials;
import com.aasc.erp.carrier.stampsws.proxy.SwsimV37SoapClient;


import com.aasc.erp.util.AascLogger;

import java.util.Date;
import java.util.LinkedList;
 import java.util.ListIterator;
 import java.util.logging.Logger;


 public class AascStampsVoid {


     private static Logger logger = 
         AascLogger.getLogger("AascStampsVoid"); // logger object used in issuing logging requests

     private String customerTransactionId = 
         ""; // customer transaction identifier that maps request to response
     private String carrierCode = ""; // carrier code  that is sent in response
     private String shipMethodName = 
         ""; // ship method name used to retreive carrier id from shipMethodInfo bean class

     private int carrierId = 
         0; // carrier id used to retreive meter number from ship method information class
     private LinkedList packageInfo = null;
     private AascShipmentHeaderInfo aascHeaderInfo = 
         null; // holds delivery header information
     AascShipmentOrderInfo aascDeliveryInfo = null;
     AascShipMethodInfo aascShipMethodInfo = null;
     private String stampsTxId = "";
     String authenticatorResponse = "";
     String shipResponse = "";
     String protocol = "";
     String hostName = "";
     String integrationId = "";
     String userName = "";
     String password = "";
     String domain = "";
     String prefix = "";
//     private AascStampsXMLRequestWrapper request = null;
     String outputFilePath = ""; // holds folder name to which request and response are written 

      String deliveryName = ""; // holds name of the delivery that is shipped
      private String timeStampStr = null;
     String error = ""; // for storing errors from connectship

     public AascStampsVoid() {
     }

    /**
     * Method to create void request and sends the request to Stamps.com Server
     * and receives response from the server
     *  if response is returned successfully and reponse is parsed successfully then 
     * responseStatus is  set to statusCode 130 else it is set to 131.
     * @param aascDeliveryInfo  AascDeliveryInfo that contains information of delivery
     * @param aascShipMethodInfo AascShipMethodInfo that contains ship method information
     * @return responseStatus integer that  holds statusCode of 130 if response returned 
     *  is successful and also parsed correctly 
     * if we get success from server  We will get only Authenticator in the response
     */

     public String voidShipment(AascShipmentOrderInfo aascDeliveryInfo, 
                                AascShipMethodInfo aascShipMethodInfo,String cloudLabelPath) {
         logger.info("Entered voidShipment()");

         try {

             if (aascDeliveryInfo != null && aascShipMethodInfo != null) {
                 aascHeaderInfo = 
                         aascDeliveryInfo.getShipmentHeaderInfo(); // delivery header info bean is retreived from delivery info bean
                 customerTransactionId = 
                         aascDeliveryInfo.getShipmentHeaderInfo().getOrderNumber(); // delivery name used as customerTransactionId is retreived from delivery info bean
                 shipMethodName = 
                         aascHeaderInfo.getShipMethodMeaning(); // shipMethodName retreived from header bean
                 carrierId = 
                         aascShipMethodInfo.getCarrierId(shipMethodName); // carrierId retreived from shipMethod info bean
                 carrierCode = 
                         aascShipMethodInfo.getCarrierName(carrierId); // carrier code retreived from shipMethod info bean
                         
                 deliveryName = nullStrToSpc(aascHeaderInfo.getOrderNumber());

                 packageInfo = aascDeliveryInfo.getShipmentPackageInfo();
                 ListIterator packageInfoIterator = packageInfo.listIterator();
                 timeStampStr = (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");   

                 while (packageInfoIterator.hasNext()) {

                     AascShipmentPackageInfo aascPackageBean = 
                         (AascShipmentPackageInfo)packageInfoIterator.next();
                     stampsTxId = aascPackageBean.getStampsTaxId();
                     AascStampsShipment aascStamsShipment = 
                         new AascStampsShipment();

                     authenticatorResponse = 
                             aascStamsShipment.authenticator(aascDeliveryInfo, 
                                                             aascShipMethodInfo);

                     SwsimV37SoapClient stampsShipClient = 
                         new SwsimV37SoapClient();
                         
                     carrierId = aascDeliveryInfo.getShipmentHeaderInfo().getCarrierId();
                     
                      integrationId   = nullStrToSpc(aascShipMethodInfo.getIntegrationId(carrierId)); 
                      userName = nullStrToSpc(aascShipMethodInfo.getCarrierUserName(carrierId));            
                      password = nullStrToSpc(aascShipMethodInfo.getCarrierPassword(carrierId));  
                      protocol = nullStrToSpc(aascShipMethodInfo.getProtocol(carrierId));
                      hostName = nullStrToSpc(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));
                      prefix   = nullStrToSpc(aascShipMethodInfo.getCarrierPrefix(carrierId));
                      logger.info("integrationId void::"+integrationId);
                      logger.info("userName void::"+userName);
                      logger.info("password void::"+password);

                     logger.info("calling befoore void ::" + stampsShipClient.getEndpoint());
                     
                      Credentials credentials = new Credentials();
                      credentials.setIntegrationID(integrationId);
                      credentials.setUsername(userName);
                      credentials.setPassword(password);

                      stampsShipClient.setEndpoint(protocol +"://" + hostName + "/" + prefix);
                      logger.info("calling void" + stampsShipClient.getEndpoint());

                     try {

                         String cancelIndiciumResponse = 
                             stampsShipClient.cancelIndicium(null, 
                                                             authenticatorResponse, 
                                                              null, stampsTxId);           //Mahesh Modified as per wsdl 37 version
                   logger.info("cancelIndiciumResponse response:::"+cancelIndiciumResponse);
                                            }

                     catch (Exception e) {
                         String status = "131@@" + e.getMessage();
                         return status;
                     }

                 }
             }
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
             aascHeaderInfo.setMainError("null values or empty strings are passed in request");
             return "131@@"; //
         } // end of catch
         logger.info("Exit from voidShipment()");
         return "130";

     }
     
     /** This method can replace the null values with nullString
      * @return String that is ""
      * @return obj-object of type Object
      */
     String nullStrToSpc(Object obj) {
         String spcStr = "";

         if (obj == null) {
             return spcStr;
         } else {
             return obj.toString();
         }
     }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
