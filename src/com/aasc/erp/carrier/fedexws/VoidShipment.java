package com.aasc.erp.carrier.fedexws;
/*
  * @(#)VoidShipment.java        06/02/2015
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  */
import java.net.*;

import java.io.*;

import com.aasc.erp.util.AascLogger;

import java.util.logging.Logger;

/**
 * This class is used to sent void request to fedex webservice.
 * also write the request and response file to server
 *@author K Sunanda
 * History
 * 24/03/2015  Sunanda K  Added missed out method level documentation for code Review fix
 */
public class VoidShipment {
    /**
     */
    private static Logger logger = AascLogger.getLogger("VoidShipment");
    /*\\  public static void main(String[] args)
     {
         String accountNo = "510087046";
             String meterNo = "100001615";
             String key = "Y5kjHP92lLHTgLey";
             String password = "T691AxPSqhYT1Ob5qJIOLBlMw";
             String trackingNumber = "794794003277" ;  // "794791668580";  //"794791662424";
             String carrierCode = "FDXE";
             String customerTransactionId = "101410";  // DeliveryId;
         VoidShipment obj=new VoidShipment();
        String response = obj.voidShipment(customerTransactionId,trackingNumber,carrierCode,accountNo,meterNo,key,password);
     logger.info("Response from Main is ::"+response);
     }*/

    /**
     * This method will send request ot fedex webservice. 
     * @param customerTransactionId it is the delivery name 
     * @param trackingNumber it is tracking number which we void
     * @param carrierCode it is carrier code value.
     * @param accountNo it is the account number which we need to sent in soap request
     * @param meterNo it is meter number which we need to sent in soap request
     * @param key it is key value which we need to sent in soap request for web athentication
     * @param password key it is password value which we need to sent in soap request for web athentication
     * @param requestFilePath it is the path where we save the request and response files
     * @return it will returns void response
     */
    public String voidShipment(String customerTransactionId, 
                               String trackingNumber, String carrierCode, 
                               String accountNo, String meterNo, String key, 
                               String password, String requestFilePath, 
                               String ipAddress, int portNo) {
        String response = "";
        // String ipAddress = "wsbeta.fedex.com";
        // String portNo = "443";
        try {
            StringBuffer voidRequest = new StringBuffer("");
            voidRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://fedex.com/ws/ship/v9\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
            voidRequest.append("<soapenv:Body>");
            voidRequest.append("<q0:DeleteShipmentRequest>");
            voidRequest.append(createWebAuthenticationDetail(key, password));
            voidRequest.append(createClientDetail(accountNo, meterNo));
            voidRequest.append(createTransactionDetail(customerTransactionId));
            voidRequest.append(createVersion());
            voidRequest.append(createTrackingId(carrierCode, trackingNumber));
            voidRequest.append("<q0:DeletionControl>DELETE_ONE_PACKAGE</q0:DeletionControl>");
            voidRequest.append("</q0:DeleteShipmentRequest>");
            voidRequest.append("</soapenv:Body>");
            voidRequest.append("</soapenv:Envelope>");
            response = 
                    sendXML(voidRequest.toString(), true, ipAddress + ":" + portNo + 
                            "/web-services/ship", requestFilePath);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return response;
        }

    }
     /**
     * createTrackingId method is used to append the tracking number based on the carrier mode
     * @param carrierCode it is carrier code value
     * @param trackingNumber it is the tarcking number of the shipment
     * @return trackingId String Buffer containing the Tacking number and tracking Id
     */

    private StringBuffer createTrackingId(String carrierCode, 
                                          String trackingNumber) {
        StringBuffer trackingId = new StringBuffer("");
        trackingId.append("<q0:TrackingId>");
        trackingId.append(createTrackingIdType(carrierCode));
        trackingId.append("<q0:TrackingNumber>" + trackingNumber + 
                          "</q0:TrackingNumber>");
        trackingId.append("</q0:TrackingId>");
        return trackingId;
    }
    /**
     * createTrackingIdType method is used to append the trackingId type based on the carrier code
     * @param carrierCode it is carrier code value
     * @return trackingIdType String Buffer containing tracking  Id type
     */
     
     
    private StringBuffer createTrackingIdType(String carrierCode) {
        StringBuffer trackingIdType = new StringBuffer("");
        if (carrierCode.equalsIgnoreCase("FDXE")) {
            trackingIdType.append("<q0:TrackingIdType>EXPRESS</q0:TrackingIdType>");
        } else {
            trackingIdType.append("<q0:TrackingIdType>GROUND</q0:TrackingIdType>");
        }
        return trackingIdType;
    }
    /**
     * createVersion method is used to add the version type and service type of the shipment
     * @return version StringBuffer containing the service Id version
     */

    private StringBuffer createVersion() {
        StringBuffer version = new StringBuffer("");
        version.append("<q0:Version>");
        version.append("<q0:ServiceId>ship</q0:ServiceId>");
        version.append("<q0:Major>9</q0:Major>");
        version.append("<q0:Intermediate>0</q0:Intermediate>");
        version.append("<q0:Minor>0</q0:Minor>");
        version.append("</q0:Version>");

        return version;
    }
     /**
     * createClientDetail method is used to add the clientDetail like account number,meter number and clientDetail
     * @param accountNumber String it is the account number of the client
     * @param meterNumber String it is the meternumber 
     * @return clientDetail StringBuffer conatining the clientDetail
     */

    private StringBuffer createClientDetail(String accountNumber, 
                                            String meterNumber) {
        StringBuffer clientDetail = new StringBuffer("");
        clientDetail.append("<q0:ClientDetail>");
        clientDetail.append("<q0:AccountNumber>" + accountNumber + 
                            "</q0:AccountNumber>");
        clientDetail.append("<q0:MeterNumber>" + meterNumber + 
                            "</q0:MeterNumber>");
        clientDetail.append("</q0:ClientDetail>");

        return clientDetail;
    }
     /**
     * createWebAuthenticationDetail method is used to create WebAuthenticationDetail containing key and password
     * @param key is a string containing username of usercredentials
     * @param password is a string containing password of usercredentials
     * @return webAuthenticationDetail StringBuffer containing usercredentials
     */

    private StringBuffer createWebAuthenticationDetail(String key, 
                                                       String password) {
        StringBuffer webAuthenticationDetail = new StringBuffer("");
        webAuthenticationDetail.append("<q0:WebAuthenticationDetail>");
        webAuthenticationDetail.append("<q0:UserCredential>");
        webAuthenticationDetail.append("<q0:Key>" + key + "</q0:Key>");
        webAuthenticationDetail.append("<q0:Password>" + password + 
                                       "</q0:Password>");
        webAuthenticationDetail.append("</q0:UserCredential>");
        webAuthenticationDetail.append("</q0:WebAuthenticationDetail>");

        return webAuthenticationDetail;
    }
     /**
     * createTransactionDetail method is used to append the TransactionId and Transaction Details\
     * @param transactionDetailStr is string having the transaction details
     * @return transactionDetail StringBuffer containing the transaction details
     */

    private StringBuffer createTransactionDetail(String transactionDetailStr) {
        StringBuffer transactionDetail = new StringBuffer("");
        transactionDetail.append("<q0:TransactionDetail>");
        transactionDetail.append("<q0:CustomerTransactionId>" + 
                                 transactionDetailStr + 
                                 "</q0:CustomerTransactionId>");
        transactionDetail.append("</q0:TransactionDetail>");

        return transactionDetail;
    }

    /**
     * This is the method where we establish the connection with fedex webservice.
     * here we sent soap request to fedex webservice.
     * @param request it is the soap request which we sent to fedex webservice
     * @param httpsFlag it is type of conncection
     * @param hName it is the host name
     * @param requestFilePath it is path where we save the request and response.
     * @return it returns response after sending the request to fedex webservice.
     */
    public String sendXML(String request, boolean httpsFlag, String hName, 
                          String requestFilePath) {
        URL aspPage;
        InputStream isXMLResponse;
        String response = "";
        String httpOrHttps = "https";

        logger.info("XML Request: " + request);
        try {
            writeOutputFile(request, requestFilePath + "_void_request.xml");
        } catch (Exception fileNotFoundException) {
            logger.severe("Exception::"+fileNotFoundException.getMessage());
        }

        try {
            logger.info("In sendXML method...");
            if (httpsFlag) {
                httpOrHttps = "https";
            } else {
                httpOrHttps = "http";
            }

            logger.info("Posting to URL: " + httpOrHttps + "://" + hName);
            aspPage = new URL(httpOrHttps + "://" + hName);
            HttpURLConnection connection2 = 
                (HttpURLConnection)aspPage.openConnection();

            // setup connection
            connection2.setDoInput(true);
            connection2.setDoOutput(true);
            connection2.setRequestMethod("POST");
            connection2.setUseCaches(false);
            connection2.setRequestProperty("Content-type", 
                                           "text/xml; charset=utf-8");

            String xml_request = request;
            byte[] buffer = xml_request.getBytes("UTF-8");

            connection2.setRequestProperty("Content-Length", 
                                           String.valueOf(buffer.length));

            // POST the request to the connections OutputStream
            DataOutputStream toServer = 
                new DataOutputStream(connection2.getOutputStream());
            try {
                toServer.write(buffer, 0, buffer.length);
            } finally {
                toServer.close();
                toServer.flush();
            }

            // read back the repsonse to check if the post request was successful
            isXMLResponse = connection2.getInputStream();

            int charRead;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                charRead = isXMLResponse.read();
                if (charRead == -1)
                    break;

                baos.write(charRead);
            }

            response = baos.toString();
            isXMLResponse.close();
        } catch (MalformedURLException mue) {
            logger.severe("Exception::"+mue.getMessage());
        } catch (IOException ioe) {
            logger.severe("Exception::"+ioe.getMessage());
        } catch (Exception exe) {
            logger.severe("Exception::"+exe.getMessage());
        } finally {
            try {
                writeOutputFile(response, 
                                requestFilePath + "_void_response.xml");
            } catch (Exception fileNotFoundException) {
                logger.severe("Exception::"+fileNotFoundException.getMessage());
            }
            return response;
        }
    }
/**
     * For writing a string to specified file.
     * @param str java.lang.String
     * @param file java.lang.String
     */
     private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(str.getBytes());
        } catch (Exception ex) {
            logger.severe("Exception::"+ex.getMessage());
        } finally {
            try {
                fout.close();
            } catch (Exception ex) {
                logger.severe("Exception::"+ex.getMessage());
            }

        }


    }

}
