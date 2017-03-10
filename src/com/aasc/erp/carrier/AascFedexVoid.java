/*
  * @(#)AascFedexVoid.java        27/01/2015
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.util.AascLogger;

import com.fedex.api.FedExAPI;
import com.fedex.api.FedExAPIException;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;
import com.aasc.erp.carrier.fedexws.VoidShipment;


/**
 * AascFedexVoid Class is used to create fedex xml void(delete/cancel) request 
 * and also Sends an API request through ATOM and returns the response. 
 * if the response is successfully returned and parsed then the responseStatus is
 * set to statusCode 130 else it is set to statusCode 131
 * Then the received response is sent to AascFedexTrackingInfo for parsing
 * the response
 * @author Sunanda Kondapalli
 * @version - 1
 * 
 * 17/06/2015   Y Pradeep           Commented below code to set carrier account number as prepaid account number even order is shipped with Receipent or the pay methods. Bug #2998.
 * 
 */
public class AascFedexVoid {
    private byte[] replyOut = 
        new byte[500]; // byte array to hold reply from fedex atom server which returns the response in byte
    private static Logger logger = AascLogger.getLogger("AascFedexVoid"); // logger object used in issuing logging requests
    private int iUTI = 2521; // Universal Transaction Identifier of request
    private int port = 0; // Integer.parseInt(PortInput);//the port on which that ATOM will be listening
    private String portString = "";
    private String host = ""; // the hostname or address of the instance of ATOM to which request is to be sent
    private int timeOut = 125; // Integer.parseInt(ReadTimeoutInput);//the time in seconds after which the API will timeout when attempting to read the response from ATOM  
    private String fedexVoidRequest =""; // to hold fedex void xml request string
    private String fedexVoidResponse = ""; // to hold fedex void xml response string
    private String customerTransactionId = ""; // customer transaction identifier that maps request to response
    private String carrierCode = ""; // carrier code  that is sent in response
    private String shipMethodName =""; // ship method name used to retreive carrier id from shipMethodInfo bean class
    private String customerCarrierAccountNumber = ""; // customer carrier account number is required to access fedex atom server
    private String fedExTestMeterNumber =""; // fedex meter number is required to access fedex atom server
    private String trackingNumber = ""; // waybill number that is retreived from header that is sent in void request 
    private String parseStatus =""; // status that is returned after parsing the response from parseResponse method of AascVoidInfo class
    private int responseStatus = 0; // integer variable to hold status code of 130 when response is successfully  returned from server and also parsed successfully
    private String parseError = ""; // holds the error that occured in parsing
    private int carrierId = 0; // carrier id used to retreive meter number from ship method information class
    private String rtnTrackingNumber = "";
    private LinkedList packageInfo = null;
    private AascShipmentHeaderInfo aascShipmentHeaderInfo = null; // holds order header information
    // private AascShipmentHeaderInfo aascShipmentHeaderInfo = 
    //   null; // holds the objcet of the AascShipmentHeaderInfo class
    //28/08/07(start)
    private String outputFile = "";
    private String timeStampStr;
    //28/08/07(end)


    /** default constructor.*/
    public AascFedexVoid() {
    }

  
  

    /** method used to set parse error.
     * @param parseError String
     */
    public void setError(String parseError) {
        this.parseError = parseError;
    }

    /** method used to get parse error.
     * @return parseError String
     */
    public String getError() {
        return parseError;
    }

    // this method is added by swapna soni. Date 29/03/2006
     /**
      * Method to create void request and sends the request to fedex atom server
      * and receives response from the server and sends it to AascFedexVoidInfo for parsing
      * if response is returned successfully and reponse is parsed successfully then 
      * responseStatus is  set to statusCode 130 else it is set to 131.
      * @param aascShipmentOrderInfo  AascShipmentOrderInfo that contains information of order
      * @param aascShipMethodInfo AascShipMethodInfo that contains ship method information
      * @return responseStatus integer that  holds statusCode of 130 if response returned 
      *  is successful and also parsed correctly 
      */

    public int voidShipment(AascShipmentOrderInfo aascShipmentOrderInfo, 
                            AascShipMethodInfo aascShipMethodInfo, 
                            String trackingNumberval, 
                            AascProfileOptionsBean aascProfileOptionsInfo, 
                            String fedExCarrierMode, String fedExKey, 
                            String fedExPassword,String cloudLabelPath) {
        logger.info("Entered voidShipment() for Shipment");
        try {
            if (aascShipmentOrderInfo != null && aascShipMethodInfo != null) {
                aascShipmentHeaderInfo = 
                        aascShipmentOrderInfo.getShipmentHeaderInfo(); // order header info bean is retreived from order info bean
                customerTransactionId = 
                        String.valueOf(aascShipmentHeaderInfo.getOrderNumber()); // order name used as customerTransactionId is retreived from order info bean
                shipMethodName = 
                        aascShipmentHeaderInfo.getShipMethodMeaning(); // shipMethodName retreived from header bean
                carrierId = 
                        aascShipMethodInfo.getCarrierId(shipMethodName); // carrierId retreived from shipMethod info bean
                carrierCode = 
                        aascShipMethodInfo.getCarrierName(carrierId); // carrier code retreived from shipMethod info bean
                //customerCarrierAccountNumber = aascShipmentHeaderInfo.getCarrierAccountNumber(); // customerCarrierAccountNumber retreived from header bean
                //if (customerCarrierAccountNumber.equalsIgnoreCase("")) {
                customerCarrierAccountNumber = aascShipMethodInfo.getCarrierAccountNumber(carrierId);
                //}
                trackingNumber = 
                        trackingNumberval; // aascShipmentHeaderInfo.getWayBill(); // tracking number retreived from header bean                                     
                packageInfo = aascShipmentOrderInfo.getShipmentPackageInfo();
                ListIterator packageInfoIterator = packageInfo.listIterator();

                // aascProfileOptionsInfo = new AascProfileOptionsInfo();
                //outputFile = aascProfileOptionsInfo.getLabelPath();//gururaj
                //Gururaj added code to get the label path from session, set in customer creation page
                outputFile=cloudLabelPath;
                logger.info("CloudLabelPath in AascFedexVoid "+cloudLabelPath);
                //Gururaj code end
                
                String rtnStr = "";
                // outputFile = "E:/Labels/";
                while (packageInfoIterator.hasNext()) {

                    AascShipmentPackageInfo aascPackageBean = 
                        (AascShipmentPackageInfo)packageInfoIterator.next();
                    try {
                        rtnTrackingNumber = 
                                aascPackageBean.getRtnTrackingNumber();
                        if (rtnTrackingNumber.equalsIgnoreCase("") || 
                            rtnTrackingNumber == null) {
                            rtnTrackingNumber = "";
                        } else {
                            //rtnTrackingNumber = rtnTrackingNumber;
                        }
                    } catch (Exception e) {
                        rtnTrackingNumber = "";
                    }

                    logger.info("rtnTrackingNumber:" + rtnTrackingNumber);
                    if (!(rtnTrackingNumber.equalsIgnoreCase("")) && 
                        rtnTrackingNumber != null) {

                        logger.info("trackingNumber::::::::" + trackingNumber);
                        if (rtnTrackingNumber.equalsIgnoreCase(trackingNumber)) {
                            rtnStr = "Return_";

                            String shipMethodNameRtn = 
                                aascPackageBean.getRtnShipMethod();
                            carrierCode = 
                                    aascShipMethodInfo.getCarrierName(shipMethodNameRtn);
                            break;
                        }

                    }

                }

                fedExTestMeterNumber = aascShipMethodInfo.getMeterNumber(carrierId); // fedex meter number retreived from ship method info bean
                /*fedExTestMeterNumber = aascShipMethodInfo.getMeterNumberMain(); */
                portString = aascShipMethodInfo.getCarrierPort(carrierId);
                if (portString != null && !(portString.equals(""))) {
                    port = Integer.parseInt(portString);
                } else {
                    logger.severe("portString is null " + portString);
                }
                host = aascShipMethodInfo.getCarrierServerIPAddress(carrierId);
                logger.info("\n customer transaction identifier : " + 
                            customerTransactionId + 
                            "\n customerCarrierAccountNumber : " + 
                            customerCarrierAccountNumber + 
                            "\n fedExTestMeterNumber : " + 
                            fedExTestMeterNumber + "\n carrierCode : " + 
                            carrierCode + "\n trackingNumber : " + 
                            trackingNumber + "\n shipMethodName : " + 
                            shipMethodName + "\n carrierId : " + carrierId + 
                            "\n host:" + host + "\n port: " + port);


                fedexVoidRequest = 
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
                        "<FDXShipDeleteRequest xmlns:api=\"http://www.fedex.com/fsmapi\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXShipDeleteRequest.xsd\">" + 
                        "<RequestHeader>" + "<CustomerTransactionIdentifier>" + 
                        customerTransactionId + 
                        "</CustomerTransactionIdentifier>" + 
                        "<AccountNumber>" + customerCarrierAccountNumber + 
                        "</AccountNumber>" + "<MeterNumber>" + 
                        fedExTestMeterNumber + "</MeterNumber>" + 
                        "<CarrierCode>" + carrierCode + "</CarrierCode>" + 
                        "</RequestHeader>" + "<TrackingNumber>" + 
                        trackingNumber + "</TrackingNumber>" + 
                        "</FDXShipDeleteRequest>";

                logger.info("Sending Transaction.");
                //28/08/07(start)
                timeStampStr = 
                        (new Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                                "_");
                if (fedExCarrierMode.equalsIgnoreCase("FedexWebServices") || 
                    fedExCarrierMode.equalsIgnoreCase("WEBSERVICES")) {
                    try {
                        String requestFilePath = 
                            outputFile + rtnStr + customerTransactionId + "_" + 
                            trackingNumber + "_" + timeStampStr;
                        replyOut = 
                                callFedexWS(fedExKey, fedExPassword, requestFilePath, 
                                            host, port).getBytes();
                        fedexVoidResponse = new String(replyOut, "ISO-8859-1");
                        if (fedexVoidResponse != null) {
                            AascFedexVoidInfo aascFedexVoidInfo = 
                                new AascFedexVoidInfo();
                            logger.info("Parasing the Webservice Soap Response");
                            parseStatus = 
                                    aascFedexVoidInfo.parseSoapResponse(fedexVoidResponse);
                        }
                        if (parseStatus.equalsIgnoreCase("success")) {
                            logger.info("parseStatus is success");

                            responseStatus = 130;

                        } else {
                            logger.info("parseStatus is not success");
                            aascShipmentHeaderInfo.setMainError(parseStatus);
                            responseStatus = 131;

                        }
                    } catch (UnsupportedEncodingException exception) {
                        responseStatus = 131;
                        aascShipmentHeaderInfo.setMainError(exception.getMessage());

                        logger.severe("exception in createRequest():" + 
                                      exception.getMessage());
                    } catch (Exception e) {
                        responseStatus = 131;
                        aascShipmentHeaderInfo.setMainError(e.getMessage());
                        logger.severe("FedExWebServiceException=" + 
                                      e.getMessage());
                    }

                } else {
                    if (port != 0 && host != null && !(host.equals(""))) {
                        try {

                            logger.info("Request val:" + fedexVoidRequest);


                            try {
                                writeOutputFile(fedexVoidRequest, 
                                                outputFile + rtnStr + 
                                                customerTransactionId + "_" + 
                                                trackingNumber + "_" + 
                                                timeStampStr + 
                                                "_void_request.xml");
                                /* logger.info(
                              "after writing request into file:" + outputFile
                              +customerTransactionIdentifier +  packageSequenceNum
                              +"_"+timeStampStr+"_request.xml"); */
                            } catch (Exception fileNotFoundException) {
                                logger.severe("file path to which fedex xml request needs to be written is not found:" + 
                                              fileNotFoundException.getMessage() + 
                                              "\n file name:" + outputFile);

                            }

                            // Sends an API request through ATOM and returns the response
                            replyOut = FedExAPI.transact(iUTI, fedexVoidRequest.getBytes(), 
                                                      host, port, timeOut);

                            // Constructs a response String by decoding the specified array of bytes using the specified charset
                            fedexVoidResponse = 
                                    new String(replyOut, "ISO-8859-1");
                            logger.info("Reply :" + fedexVoidResponse);
                            //28/08/07(start)
                            try {
                                writeOutputFile(fedexVoidResponse, 
                                                outputFile + rtnStr + 
                                                customerTransactionId + "_" + 
                                                trackingNumber + "_" + 
                                                timeStampStr + 
                                                "_void_response.xml");
                                /* logger.info(
                              "after writing request into file:" + outputFile
                              +customerTransactionIdentifier +  packageSequenceNum
                              +"_"+timeStampStr+"_request.xml"); */
                            } catch (Exception fileNotFoundException) {
                                logger.severe("file path to which fedex xml request needs to be written is not found:" + 
                                              fileNotFoundException.getMessage() + 
                                              "\n file name:" + outputFile);

                            }
                            //28/08/07(end)
                            if (fedexVoidResponse != null) {
                                AascFedexVoidInfo aascFedexVoidInfo = 
                                    new AascFedexVoidInfo();

                                parseStatus = 
                                        aascFedexVoidInfo.parseResponse(fedexVoidResponse);
                            }
                            if (parseStatus.equalsIgnoreCase("success")) {
                                logger.info(" if(parseStatus.equalsIgnoreCase('success'))");
                                responseStatus = 130;

                            } else {
                                logger.info("inside else of if(parseStatus.equalsIgnoreCase('success'))");
                                aascShipmentHeaderInfo.setMainError(parseStatus);
                                responseStatus = 131;

                            }
                        } // end of inner try
                        catch (UnsupportedEncodingException exception) {
                            responseStatus = 131;
                            aascShipmentHeaderInfo.setMainError(exception.getMessage());

                            logger.severe("exception in createRequest():" + 
                                          exception);
                        } catch (FedExAPIException e) {
                            responseStatus = 131;
                            aascShipmentHeaderInfo.setMainError(e.getMessage());
                            logger.severe("FedExAPIException: " + 
                                          e.getMessage());
                        }
                    } else {
                        logger.severe("either port or host is null:" + 
                                      "\n host:" + host + "\n port:" + port);
                        aascShipmentHeaderInfo.setMainError("either port or host is null:" + 
                                                         "\n host:" + host + 
                                                         "\n port:" + port);
                        responseStatus = 131;
                    }

                } //End of checking Webservice. 
            } // end of if(aascShipmentOrderInfo!=null)
            else {
                logger.severe("ORDER INFORMATION OBJECT IS NULL OR SHIP METHOD INFO BEAN IS NULL");
                aascShipmentHeaderInfo.setMainError("ORDER INFORMATION OBJECT IS NULL OR SHIP METHOD INFO BEAN IS NULL");
                responseStatus = 131;
            }
        } // end of outer try
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            aascShipmentHeaderInfo.setMainError("null values or empty strings are passed in request");
        } // end of catch
        logger.info("Exit from voidShipment() for Shipment");
        return responseStatus;
    } // end of the method voidShipment 


    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);

        fout.write(str.getBytes());
        fout.close();
    }
    //trackingNumber, customerCarrierAccountNumber, fedExTestMeterNumber, fedExKey, fedExPassword, customerTransactionId, carrierCode

    public String callFedexWS(String key, String password, 
                              String requestFilePath, String host, int port) {
        logger.info("customerTransactionId  ::" + customerTransactionId);
        logger.info("carrierCode  ::" + carrierCode);
        logger.info("trackingNumber ::" + trackingNumber);
        logger.info("customerCarrierAccountNumber  ::" + 
                    customerCarrierAccountNumber);
        logger.info("fedExTestMeterNumber" + fedExTestMeterNumber);

        logger.info("Inside callFedexWS");
        String reply = "";
        //  port = 8090;
        try {
            /*
           Service service1 = new Service();
           Call call = (Call)service1.createCall();
           //logger.info("endpoint :::"+applURL);
           call.setTargetEndpointAddress(new URL(applURL+"axis/VoidShipment.jws"));
           call.setOperationName(new QName("voidShipment"));
           //reply = (String)call.invoke(new Object [] {trackingNumber, customerCarrierAccountNumber, fedExTestMeterNumber, key, password, customerTransactionId, carrierCode});
           reply = (String)call.invoke(new Object [] {customerTransactionId,trackingNumber,carrierCode, customerCarrierAccountNumber, fedExTestMeterNumber, key, password});
           */

            com.aasc.erp.carrier.fedexws.VoidShipment voidShipment = 
                new VoidShipment();
            reply = voidShipment.voidShipment(customerTransactionId, trackingNumber, 
                                              carrierCode, 
                                              customerCarrierAccountNumber, 
                                              fedExTestMeterNumber, key, 
                                              password, requestFilePath, host, 
                                              port);
            logger.info("=========================> After calling the axis/VoidShipment.jws");
            logger.info("--------------->reply  :: " + reply);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }

        return reply;
    }

}// end of the class 
