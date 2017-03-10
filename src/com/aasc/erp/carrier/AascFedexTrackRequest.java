/*
 * @(#)AascFedexTrackRequest.java  01/02/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.

History

17-Dec-2014   Eshwari M   Removed Adhoc and Delivery words for SC Lite while testing Tracking feature
15-Mar-2014   Eshwari M   Removed unused variables
 */

package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipMethodInfo;

import com.aasc.erp.bean.AascShipmentHeaderInfo;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.logging.Logger;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.util.AascLogger;

import com.fedex.api.FedExAPI;
import com.fedex.api.FedExAPIException;

/**
 Creates xml track request and receives the response from the server(bean containing 
 the response data).   
 @author 
 @version 1.0
 @since     
 * 
 */
  
public class AascFedexTrackRequest {
    private byte[] replyOut = 
        new byte[500]; // byte array for holding reply from fedex server
    private static Logger logger = 
        AascLogger.getLogger("AascFedexTrackRequest"); // logger object used in issuing logging requests
    private int iUTI = 2532; // Universal Transaction Identifier of request
    private int port = 0; // the port on which that ATOM will be listening
    private String portString = "";
    private String host = 
        ""; // the hostname or address of the instance of ATOM to which request is to be sent
    private int timeOut = 
        125; // Integer.parseInt(ReadTimeoutInput);//the time in seconds after which the API will timeout when attempting to read the response from ATOM    
    private String trackRequest = ""; // to hold fedex track xml request string
    private String trackResponse = 
        ""; // to hold fedex track xml request string
    private String podRequest = ""; // to hold fedex pod xml request string
    private String podResponse = ""; // to hold fedex pod xml request string
    private String customerTransactionId = 
        ""; // customer transaction identifier that maps request to response
    private String customerCarrierAccountNumber = 
        ""; // customer carrier account number is required to access fedex atom server
    private String fedExTestMeterNumber = 
        ""; // fedex meter number of the client that is required to access fedex atom server
    private String trackingNumber = 
        ""; // indicates tracking number of package in the delivery
    private String carrierCode = ""; // indicates carrierCode
    private String destinationCountryCode = 
        ""; // indicates destination country code
    private Date shipDate = null; // indicates shipment date
    private String shipMethodName = 
        ""; // indicates ship method used in the delivery
    private int carrierId = 0; // indicates carrier Id 
    private String date = ""; // holds the shipment date converted into string
    private String actionType = 
        ""; // actionType holds the value of tracking or pod depending on user request
    private SimpleDateFormat sdf = 
        new SimpleDateFormat("yyyy-MM-dd"); // represents the date format to which shipment date is converted and sent in the request

    private AascShipmentHeaderInfo aascHeaderInfo = null; // delivery header info bean
    private AascFedexTrackingInfo aascFedexTrackInfo = 
        null; // fedex tracking info bean
    private AascFedexTrackingInfo aascFedexTrackingInfo = 
        null; // fedex tracking info bean
    private String outputFile = 
        ""; // holds folder name to which request and response are written
    private String timeStampStr;

    /** Default Constructor.*/
    public AascFedexTrackRequest() {
    }


    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /**
     Creates tracking or pod request depending
     on action type(TRACKING,WAYBILL or POD)
     and the request is sent to the FedEx server -Atom
     and response is retreived and sent to AascTrackingInfo. 
     for parsing and the parsed response is returned as fedex tracking bean.  
     @param aascTrackingDeliveryInfo AascTrackingDeliveryInfo tracking delivery info bean.
     @param aascShipMethodInfo AascShipMethodInfo ship method info  bean.
     @return aascFedexTrackingInfo AascFedexTrackingInfofedex tracking info bean containing
     parsed response data.
     */
    public AascFedexTrackingInfo processRequest(AascTrackingOrderInfo aascTrackingDeliveryInfo, 
                                                AascShipMethodInfo aascShipMethodInfo, 
                                                AascProfileOptionsBean aascProfileOptionsInfo, 
                                                String carrierMode, 
                                                String fedExKey, 
                                                String fedExPassword,String cloudLabelPath) {
        logger.info("Entered processRequest method");
        try {
            
            //Gururaj modified code to get the cloud label path from customer creation page
         //   outputFile = aascProfileOptionsInfo.getLabelPath();
            // outputFile = "E:\\MADHAVI\\SHIPCONSOLE\\Labels\\";
            
             outputFile=cloudLabelPath;
    //          System.out.println("Cloud Label Path::  "+cloudLabelPath);
             //Gururaj code end
            if (aascTrackingDeliveryInfo != null && 
                aascShipMethodInfo != null) {
                aascHeaderInfo = aascTrackingDeliveryInfo.getHeaderInfo();
                // retreiving data from tracking header bean and ship method bean to build xml request for tracking or pod
                customerTransactionId = aascTrackingDeliveryInfo.getHeaderInfo().getOrderNumber();
                shipMethodName = aascHeaderInfo.getShipMethodMeaning();
                trackingNumber = 
                        aascTrackingDeliveryInfo.getHeaderInfo().getWayBill();
                shipDate = 
                        aascTrackingDeliveryInfo.getHeaderInfo().getShipmentDate();
                // customerCarrierAccountNumber = aascTrackingDeliveryInfo.getHeaderInfo().getCarrierAccountNumber();          
                carrierId = aascShipMethodInfo.getCarrierId(shipMethodName);
                carrierCode = aascShipMethodInfo.getCarrierName(carrierId);
                // getting customerCarrierAccountNumber from aascShipMethodInfo 
                customerCarrierAccountNumber = 
                        aascShipMethodInfo.getCarrierAccountNumber(carrierId);
                fedExTestMeterNumber = 
                        aascShipMethodInfo.getMeterNumber(carrierId);
                date = sdf.format(shipDate);
                fedExTestMeterNumber = 
                        aascShipMethodInfo.getMeterNumber(carrierId);
                destinationCountryCode = aascHeaderInfo.getShipToCountry();
                actionType = aascTrackingDeliveryInfo.getActionType();
                portString = aascShipMethodInfo.getCarrierPort(carrierId);
                if (portString != null && !(portString.equals(""))) {
                    port = Integer.parseInt(portString);
                } else {
                    logger.severe("portString is null " + portString);
                    aascFedexTrackingInfo = new AascFedexTrackingInfo();
                    aascFedexTrackingInfo.setResponseStatus("portString is null");
                    return aascFedexTrackingInfo;
                }

                timeStampStr = 
                        (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                                          "_");

                logger.info("timeStampStr==" + timeStampStr);

                host = aascShipMethodInfo.getCarrierServerIPAddress(carrierId);
                logger.info("\n customerTransactionId: " + 
                            customerTransactionId + 
                            "\n customerCarrierAccountNumber: " + 
                            customerCarrierAccountNumber + 
                            "\n fedExTestMeterNumber: " + 
                            fedExTestMeterNumber + "\n carrierCode: " + 
                            carrierCode + "\n date: " + date + 
                            "\n trackingNumber: " + trackingNumber + 
                            "\n shipMethodName: " + shipMethodName + 
                            "\n date: " + date + "\n actionType: " + 
                            actionType + "\n host:" + host + "\n port: " + 
                            port);

                aascFedexTrackInfo = new AascFedexTrackingInfo();
                if (!(actionType.equals("")) && actionType != null) {
                    try {
                        if (actionType.equals("WAYBILL") || 
                            actionType.equals("TRACKING")) {

                            iUTI = 2532;
                            logger.info("creating fedex track request");
                            trackRequest = 
                                    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
                                    "<FDXTrack2Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXTrack2Request.xsd\">" + 
                                    "<RequestHeader>" + 
                                    "<CustomerTransactionIdentifier>" + 
                                    customerTransactionId + 
                                    "</CustomerTransactionIdentifier>" + 
                                    "<AccountNumber>" + 
                                    customerCarrierAccountNumber + 
                                    "</AccountNumber>" + "<MeterNumber>" + 
                                    fedExTestMeterNumber + "</MeterNumber>" + 
                                    "<CarrierCode>" + carrierCode + 
                                    "</CarrierCode>" + "</RequestHeader>" + 
                                    "<PackageIdentifier>" + "<Value>" + 
                                    trackingNumber + "</Value>" + 
                                    "<Type>TRACKING_NUMBER_OR_DOORTAG</Type>" + 
                                    "</PackageIdentifier>";
                            if (actionType.equals("WAYBILL")) {
                                logger.info("actionType is WAYBILL");
                                trackRequest = 
                                        trackRequest + "<DetailScans>false</DetailScans>";
                            } else {
                                if (actionType.equals("TRACKING")) {
                                    logger.info("actionType is TRACKING");
                                    trackRequest = 
                                            trackRequest + "<DetailScans>true</DetailScans>";
                                }
                            }
                            trackRequest = 
                                    trackRequest + "<DestinationCountryCode>" + 
                                    destinationCountryCode + 
                                    "</DestinationCountryCode>" + 
                                    "</FDXTrack2Request>";

                            logger.info("track request:" + trackRequest);

                            try {
                                writeOutputFile(trackRequest, 
                                                outputFile + trackingNumber + 
                                                "_" + timeStampStr + 
                                                "_track_request.xml");
                                logger.info("after writing request into file:" + 
                                            outputFile + trackingNumber + 
                                            "_request.xml");
                            } catch (Exception fileNotFoundException) {
                                logger.severe("file path to which fedex xml request needs to be written is not found:" + 
                                              fileNotFoundException.getMessage() + 
                                              "\n file name:" + outputFile);

                            }

                            if (port != 0 && host != null && 
                                !(host.equals(""))) {
                                carrierMode = nullStrToSpc(carrierMode);

                                if (carrierMode.equalsIgnoreCase("FedexWebServices") || 
                                    carrierMode.equalsIgnoreCase("WEBSERVICES")) {

                                    AascFedexTrackService trackService = 
                                        new AascFedexTrackService();

                                    if (actionType.equals("TRACKING")) {
                                        String trackReply = 
                                            nullStrToSpc(trackService.processTrackRequest(fedExKey, 
                                                                                          fedExPassword, 
                                                                                          customerCarrierAccountNumber, 
                                                                                          fedExTestMeterNumber, 
                                                                                          trackingNumber, 
                                                                                          "TRACKING", 
                                                                                          outputFile, 
                                                                                          host, 
                                                                                          port));

                                        if (!"".equalsIgnoreCase(trackReply)) {
                                            aascFedexTrackingInfo = 
                                                    aascFedexTrackInfo.parseWebServiceTrackResponse(trackReply, 
                                                                                                    aascTrackingDeliveryInfo);
                                        }

                                        else {
                                            aascFedexTrackingInfo = 
                                                    new AascFedexTrackingInfo();
                                            aascFedexTrackingInfo.setResponseStatus("track response is null");
                                            return aascFedexTrackingInfo;
                                        }
                                    }
                                    if (actionType.equals("WAYBILL")) {
                                        String waybillReply = 
                                            nullStrToSpc(trackService.processTrackRequest(fedExKey, 
                                                                                          fedExPassword, 
                                                                                          customerCarrierAccountNumber, 
                                                                                          fedExTestMeterNumber, 
                                                                                          trackingNumber, 
                                                                                          "WAYBILL", 
                                                                                          outputFile, 
                                                                                          host, 
                                                                                          port));

                                        if (!"".equalsIgnoreCase(waybillReply)) {
                                            aascFedexTrackingInfo = 
                                                    aascFedexTrackInfo.parseWebServiceWaybillResponse(waybillReply, 
                                                                                                      aascTrackingDeliveryInfo);
                                        }

                                        else {
                                            aascFedexTrackingInfo = 
                                                    new AascFedexTrackingInfo();
                                            aascFedexTrackingInfo.setResponseStatus("waybill response is null");
                                            return aascFedexTrackingInfo;
                                        }
                                    }

                                } else {

                                    replyOut = 
                                            FedExAPI.transact(iUTI, trackRequest.getBytes(), 
                                                              host, port, 
                                                              timeOut);

                                    // initial closing of this else block is at this point

                                    trackResponse = 
                                            new String(replyOut, "ISO-8859-1");


                                    logger.info("trackResponse:" + 
                                                trackResponse);
                                    if (trackResponse != null && 
                                        !(trackResponse.equals(""))) {

                                        try {
                                            writeOutputFile(trackResponse, 
                                                            outputFile + 
                                                            trackingNumber + 
                                                            "_" + 
                                                            timeStampStr + 
                                                            "_track_response.xml");
                                            logger.info("after writing response into file:" + 
                                                        outputFile + 
                                                        trackingNumber + 
                                                        "_response.xml");
                                        } catch (Exception fileNotFoundException) {
                                            logger.severe("file path to which fedex xml response needs to be written is not found:" + 
                                                          fileNotFoundException.getMessage() + 
                                                          "\n file name:" + 
                                                          outputFile);

                                        }
                                        aascFedexTrackingInfo = 
                                                aascFedexTrackInfo.parseResponse(trackResponse, 
                                                                                 aascTrackingDeliveryInfo, 
                                                                                 aascProfileOptionsInfo, 
                                                                                 carrierMode);
                                    } else {
                                        logger.severe("track response is null");
                                        aascFedexTrackingInfo = 
                                                new AascFedexTrackingInfo();
                                        aascFedexTrackingInfo.setResponseStatus("track response is null");
                                        return aascFedexTrackingInfo;
                                    }
                                } // changed for  web services     
                            } // end of if(port!=0 && host!=null && !(host.equals("")))                         
                            else {
                                logger.severe("either port or host is null:" + 
                                              "\n host:" + host + "\n port:" + 
                                              port);
                                aascFedexTrackingInfo = 
                                        new AascFedexTrackingInfo();
                                aascFedexTrackingInfo.setResponseStatus("either port or host is null");
                                return aascFedexTrackingInfo;
                            } // end of else of if(port!=0 && host!=null && !(host.equals(""))) 
                        } // end of if(actionType.equals("WAYBILL")||actionType.equals("TRACKING"))                     
                        else {
                            if (actionType.equals("POD")) {
                                logger.info("actionType is POD");
                                iUTI = 2529;
                                podRequest = 
                                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
                                        "<FDXSPODRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXSPODRequest.xsd\">" + 
                                        "<RequestHeader>" + 
                                        "<CustomerTransactionIdentifier>" + 
                                        customerTransactionId + 
                                        "</CustomerTransactionIdentifier>" + 
                                        "<AccountNumber>" + 
                                        customerCarrierAccountNumber + 
                                        "</AccountNumber>" + "<MeterNumber>" + 
                                        fedExTestMeterNumber + 
                                        "</MeterNumber>" + "<CarrierCode>" + 
                                        carrierCode + "</CarrierCode>" + 
                                        "</RequestHeader>" + "<ShipDate>" + 
                                        date + "</ShipDate>" + 
                                        "<TrackingNumber>" + trackingNumber + 
                                        "</TrackingNumber>" + 
                                        "<LanguageCode>EN</LanguageCode>" + 
                                        "<ReturnLetterFormat>PNG</ReturnLetterFormat>" + 
                                        "</FDXSPODRequest>";

                                logger.info("pod request:" + podRequest);

                                try {
                                    writeOutputFile(podRequest, 
                                                    outputFile + trackingNumber + 
                                                    "_" + timeStampStr + 
                                                    "_POD_request.xml");
                                    logger.info("after writing request into file:" + 
                                                outputFile + trackingNumber + 
                                                "_request.xml");
                                } catch (Exception fileNotFoundException) {
                                    logger.severe("file path to which fedex xml request needs to be written is not found:" + 
                                                  fileNotFoundException.getMessage() + 
                                                  "\n file name:" + 
                                                  outputFile);

                                }

                                if (port != 0 && host != null && 
                                    !(host.equals(""))) {


                                    if ((carrierMode.equalsIgnoreCase("WEBSERVICES")) || 
                                        (carrierMode.equalsIgnoreCase("FedexWebServices"))) {

                                        AascFedexTrackService trackService = 
                                            new AascFedexTrackService();

                                        String spodResponse = 
                                            nullStrToSpc(trackService.processSpod(fedExKey, 
                                                                                  fedExPassword, 
                                                                                  customerCarrierAccountNumber, 
                                                                                  fedExTestMeterNumber, 
                                                                                  trackingNumber, 
                                                                                  carrierCode, 
                                                                                  date, 
                                                                                  cloudLabelPath, 
                                                                                  "POD", 
                                                                                  host, 
                                                                                  port));
                                        if (!"".equalsIgnoreCase(spodResponse)) {
                                            aascFedexTrackingInfo = 
                                                    aascFedexTrackInfo.parseWebServiceSpodResponse(spodResponse, 
                                                                                                   aascTrackingDeliveryInfo, 
                                                                                                   aascProfileOptionsInfo);

                                        } else {
                                            aascFedexTrackingInfo = 
                                                    new AascFedexTrackingInfo();
                                            aascFedexTrackingInfo.setResponseStatus("pod response is null");
                                            return aascFedexTrackingInfo;
                                        }

                                    } else {

                                        replyOut = 
                                                FedExAPI.transact(iUTI, podRequest.getBytes(), 
                                                                  host, port, 
                                                                  timeOut);

                                        // }  // Initial loop till here
                                        /*replyOut = FedExAPI.transact(iUTI,
                                            podRequest.getBytes(), host, port,
                                            timeOut);       */
                                        logger.info("Transaction done ");
                                        podResponse = 
                                                new String(replyOut, "ISO-8859-1");
                                        logger.info("podResponse:" + 
                                                    podResponse);
                                        if (podResponse != null && 
                                            !(podResponse.equals(""))) {

                                            try {
                                                writeOutputFile(podResponse, 
                                                                outputFile + 
                                                                trackingNumber + 
                                                                "_" + 
                                                                timeStampStr + 
                                                                "_POD_response.xml");
                                                logger.info("after writing response into file:" + 
                                                            outputFile + 
                                                            trackingNumber + 
                                                            "_response.xml");
                                            } catch (Exception fileNotFoundException) {
                                                logger.severe("file path to which fedex xml response needs to be written is not found:" + 
                                                              fileNotFoundException.getMessage() + 
                                                              "\n file name:" + 
                                                              outputFile);

                                            }
                                            aascFedexTrackingInfo = 
                                                    aascFedexTrackInfo.parseResponse(podResponse, 
                                                                                     aascTrackingDeliveryInfo, 
                                                                                     aascProfileOptionsInfo, 
                                                                                     carrierMode);
                                        } // end of if (podResponse != null && !(podResponse.equals("")))
                                        else {
                                            logger.severe("pod response is null");
                                            aascFedexTrackingInfo = 
                                                    new AascFedexTrackingInfo();
                                            aascFedexTrackingInfo.setResponseStatus("pod response is null");
                                            return aascFedexTrackingInfo;
                                        } // end of else of if (podResponse != null && !(podResponse.equals("")))
                                    } // changed for web service  

                                } // end of if(port!=0 && host!=null && !(host.equals("")))
                                else {
                                    logger.severe("either port or host is null:" + 
                                                  "\n host:" + host + 
                                                  "\n port:" + port);
                                    aascFedexTrackingInfo = 
                                            new AascFedexTrackingInfo();
                                    aascFedexTrackingInfo.setResponseStatus("either port or host is null");
                                    return aascFedexTrackingInfo;
                                } // end of else of if(port!=0 && host!=null && !(host.equals(""))) 
                            } // end of if(actionType.equals("POD"))              
                        } // end of else of if(actionType.equals("WAYBILL")||actionType.equals("TRACKING"))
                    } // end of inner try
                    catch (UnsupportedEncodingException e) {
                        logger.severe("UnsupportedEncodingException: " + 
                                      e.getMessage());
                        aascFedexTrackingInfo = new AascFedexTrackingInfo();
                        aascFedexTrackingInfo.setResponseStatus(e.getMessage());
                        return aascFedexTrackingInfo;
                    } catch (FedExAPIException e) {
                        logger.severe("FedExAPIException= " + e.getMessage());
                        aascFedexTrackingInfo = new AascFedexTrackingInfo();
                        aascFedexTrackingInfo.setResponseStatus(e.getMessage());
                        return aascFedexTrackingInfo;
                    }
                } else {
                    logger.severe("action type is NULL");
                    aascFedexTrackingInfo = new AascFedexTrackingInfo();
                    aascFedexTrackingInfo.setResponseStatus("action type is NULL");
                    return aascFedexTrackingInfo;
                }
            } // end of if(aascTrackingDeliveryInfo!=null && aascShipMethodInfo!=null)
            else {
                logger.severe("aascTrackingDeliveryInfo is NULL OR aascShipMethodInfo is NULL");
                aascFedexTrackingInfo = new AascFedexTrackingInfo();
                aascFedexTrackingInfo.setResponseStatus("aascTrackingDeliveryInfo is NULL OR aascShipMethodInfo is NULL");
                return aascFedexTrackingInfo;

            }
        } // end of outer try
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            aascFedexTrackingInfo = new AascFedexTrackingInfo();
            aascFedexTrackingInfo.setResponseStatus("null values or empty strings passed in request");
        } // end of outer catch             
        logger.info("Exit from processRequest method");
        return aascFedexTrackingInfo;
    } // end of method

    /**
     * For writing a string to specified file.
     * @param str java.lang.String
     * @param file java.lang.String
     */
    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);

        fout.write(str.getBytes());
        fout.close();
    }
}
