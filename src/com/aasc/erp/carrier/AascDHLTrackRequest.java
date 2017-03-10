/*
 * @(#)AascDHLTrackRequest.java  08/02/2016
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.

History


 */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;

import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.util.AascLogger;

import com.fedex.api.FedExAPIException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.logging.Logger;


/**
 Creates xml track request and receives the response from the server(bean containing 
 the response data).   
 @author   G S Shekar
 @version 1.0
 @since     
 * 
 */
public class AascDHLTrackRequest {
    public AascDHLTrackRequest() {
    }
    
    private static Logger logger =  AascLogger.getLogger("AascDHLTrackRequest"); // logger object used in issuing logging requests
    private int iUTI = 2532; // Universal Transaction Identifier of request
    private int port = 0; // the port on which that ATOM will be listening
    private String host = ""; // the hostname or address of the instance of ATOM to which request is to be sent
    private String httpOrhttps = "";
    private String prefix = "";
    private StringBuffer trackRequest = null; // to hold DHL track xml request string
    
    private String trackingNumber = ""; // indicates tracking number of package in the delivery
    private String carrierCode = ""; // indicates carrierCode
    private Date shipDate = null; // indicates shipment date
    private String shipMethodName = ""; // indicates ship method used in the delivery
    private int carrierId = 0; // indicates carrier Id 
    private String date = ""; // holds the shipment date converted into string
    private String actionType = ""; // actionType holds the value of tracking or pod depending on user request
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // represents the date format to which shipment date is converted and sent in the request

    private AascShipmentHeaderInfo aascHeaderInfo = null; // delivery header info bean
    private AascDHLTrackingInfo aascDHLTrackInfo =  null; // DHL tracking info bean
    private AascDHLTrackingInfo aascDHLTrackingInfo = null; // DHL tracking info bean
    private String outputFile = ""; // holds folder name to which request and response are written
    private String timeStampStr;
    private String messageTime = "";
    private String messageReference = "";
    private
    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }
    
    /**
     Creates tracking request depending
     on action type(TRACKING,WAYBILL)
     and the request is sent to the DHL server -Atom
     and response is retreived and sent to AascTrackingInfo. 
     for parsing and the parsed response is returned as DHL tracking bean.  
     @param aascTrackingDeliveryInfo AascTrackingDeliveryInfo tracking delivery info bean.
     @param aascShipMethodInfo AascShipMethodInfo ship method info  bean.
     @return aascDHLTrackingInfo AascDHLTrackingInfo tracking info bean containing
     parsed response data.
     */
    
     public AascDHLTrackingInfo processRequest(AascTrackingOrderInfo aascTrackingDeliveryInfo, 
                                                 AascShipMethodInfo aascShipMethodInfo, 
                                                 AascProfileOptionsBean aascProfileOptionsInfo, 
                                                 String carrierMode, 
                                                 String userName, 
                                                 String password,String cloudLabelPath) {
        
        logger.info("Entered processRequest method");
        try{
            outputFile=cloudLabelPath;
            
            if (aascTrackingDeliveryInfo != null && 
                aascShipMethodInfo != null) {
                    aascHeaderInfo = aascTrackingDeliveryInfo.getHeaderInfo();
                    shipMethodName = aascHeaderInfo.getShipMethodMeaning();
                    trackingNumber = aascTrackingDeliveryInfo.getHeaderInfo().getWayBill();
                    carrierId = aascShipMethodInfo.getCarrierId(shipMethodName);
                    carrierCode = aascShipMethodInfo.getCarrierName(carrierId);
                    actionType = aascTrackingDeliveryInfo.getActionType();
                    
                    timeStampStr =  (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");
                    logger.info("timeStampStr==" + timeStampStr);
                    httpOrhttps = aascShipMethodInfo.getProtocol(carrierId);
                    host = aascShipMethodInfo.getCarrierServerIPAddress(carrierId);
                    
                    
                    timeStampStr = 
                            (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");
                    messageTime=((new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()))+"T"+(new SimpleDateFormat("HH:mm:ssZ").format(new java.util.Date())));
                    String messageTimeTmp=messageTime.substring(0,22);
                    String messageTimeTmp2=messageTime.substring(22,24);
                    messageTime = messageTimeTmp+":"+messageTimeTmp2;

                    messageReference = timeStampStr.substring(3);
                    messageReference =  trackingNumber+messageReference;
                    if(messageReference.length() <28)
                       messageReference = "0000"+messageReference;
                    if(messageReference.length()>31)
                       messageReference = messageReference.substring(0,31);
                    
                    logger.info("\n carrierCode: " + 
                                carrierCode + "\n date: " + date + 
                                "\n trackingNumber: " + trackingNumber + 
                                "\n shipMethodName: " + shipMethodName + 
                                "\n date: " + date + "\n actionType: " + 
                                actionType + "\n" + httpOrhttps+
                                "host:" + host );
                                
                    aascDHLTrackInfo = new AascDHLTrackingInfo();
                    if (actionType != null && !"".equals(actionType)) {
                        try{
                            if ("WAYBILL".equals(actionType) ||"TRACKING".equals(actionType)) {
                                iUTI = 2532;
                                logger.info("creating DHL track request");
                                trackRequest = new StringBuffer("");
                                trackRequest = trackRequest.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
                                "<req:KnownTrackingRequest xmlns:req=\"http://www.dhl.com\" \n" + 
                                "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" + 
                                "      xsi:schemaLocation=\"http://www.dhl.com\n" + 
                                "      TrackingRequestKnown.xsd\">\n" + 
                                "	<Request>\n" + 
                                "		<ServiceHeader>\n" + 
                                "			<MessageTime>"+messageTime+"</MessageTime>\n" + 
                                "			<MessageReference>"+messageReference+"</MessageReference>\n" + 
                                "			<SiteID>"+userName+"</SiteID>\n" + 
                                "			<Password>"+password+"</Password>\n" + 
                                "		</ServiceHeader>\n" + 
                                "	</Request>\n" + 
                                "	<LanguageCode>en</LanguageCode>\n");
                               
                                if("WAYBILL".equals(actionType)){
                                    logger.info("actionType is WAYBILL");
                                    trackRequest.append("<AWBNumber>"+trackingNumber+"</AWBNumber>\n");
                                }
                                else if("TRACKING".equals(actionType)){
                                    logger.info("actionType is TRACKING");
                                    trackRequest.append("<LPNumber>"+trackingNumber+"</LPNumber>");
                                }
                                
                                trackRequest.append("<LevelOfDetails>ALL_CHECK_POINTS</LevelOfDetails>\n" + 
                                "	<PiecesEnabled>B</PiecesEnabled>\n" + 
//                                "	<CountryCode>US</CountryCode>\n" + 
                                "</req:KnownTrackingRequest>");

                                
                                try {
                                    writeOutputFile(trackRequest.toString(), 
                                                    outputFile + trackingNumber + 
                                                    "_" + timeStampStr + 
                                                    "_track_request.xml");
                                    logger.info("after writing request into file:" + 
                                                outputFile + trackingNumber + 
                                                "_request.xml");
                                } catch (Exception fileNotFoundException) {
                                    logger.severe("file path to which DHL xml request needs to be written is not found:" + 
                                                  fileNotFoundException.getMessage() + 
                                                  "\n file name:" + outputFile);

                                }
                                
                                if (host != null && !("".equals(host))) {
//                                    http://xmlpi-ea.dhl.com/XMLShippingServlet
                                    String trackReply = processXML(nullStrToSpc(trackRequest), httpOrhttps, host,prefix ,cloudLabelPath,trackingNumber);
                                                                                      
                                    if (!"".equalsIgnoreCase(trackReply)) {
                                        try {
                                            writeOutputFile(trackReply, 
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
                                        
                                        aascDHLTrackingInfo = 
                                                aascDHLTrackInfo.parseWebServiceTrackResponse(trackReply,aascDHLTrackingInfo);
                                    }

                                    else {
                                        aascDHLTrackingInfo = 
                                                new AascDHLTrackingInfo();
                                        aascDHLTrackingInfo.setResponseStatus("track response is null");
                                        return aascDHLTrackingInfo;
                                    }                                                                                      
                                
                                }
                                else{
                                    logger.severe("either port or host is null:" + 
                                                  "\n host:" + host + "\n port:" + 
                                                  port);
                                    aascDHLTrackingInfo = 
                                            new AascDHLTrackingInfo();
                                    aascDHLTrackingInfo.setResponseStatus("either port or host is null");
                                    return aascDHLTrackingInfo;
                                }
                                
                                
                            }
                        
                        }catch(UnsupportedOperationException e){
                        logger.severe("UnsupportedEncodingException: " + 
                                      e.getMessage());
                        aascDHLTrackingInfo = new AascDHLTrackingInfo();
                        aascDHLTrackingInfo.setResponseStatus(e.getMessage());
                        return aascDHLTrackingInfo;
                    }
                        catch (Exception e) {
                        logger.severe("DHL Expception= " + e.getMessage());
                        aascDHLTrackingInfo = new AascDHLTrackingInfo();
                        aascDHLTrackingInfo.setResponseStatus(e.getMessage());
                        return aascDHLTrackingInfo;
                    }
                    
                    }
                else {
                    logger.severe("action type is NULL");
                    aascDHLTrackingInfo = new AascDHLTrackingInfo();
                    aascDHLTrackingInfo.setResponseStatus("action type is NULL");
                    return aascDHLTrackingInfo;
                    }
                }
            else {
                logger.severe("aascTrackingDeliveryInfo is NULL OR aascShipMethodInfo is NULL");
                aascDHLTrackingInfo = new AascDHLTrackingInfo();
                aascDHLTrackingInfo.setResponseStatus("aascTrackingDeliveryInfo is NULL OR aascShipMethodInfo is NULL");
                return aascDHLTrackingInfo;

            }
            
        }catch(Exception e){
            logger.severe("Exception::"+e.getMessage());
            aascDHLTrackingInfo = new AascDHLTrackingInfo();
            aascDHLTrackingInfo.setResponseStatus("null values or empty strings passed in request");
        }
        logger.info("Exit from processRequest method");
        return aascDHLTrackingInfo;                              
    }

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

    public String processXML(String request, String httpOrHttps, String hName,String prefix, String labelPath,String trackNum) {
        
        String response = "";
        ByteArrayOutputStream baos = null;
        FileOutputStream fout = null;
        String filename = "";
        String timeStampStr = "";

//        logger.info("SOAP Request: " + request);

        try {
        

            logger.info("Posting to URL: " + httpOrHttps + ":" + hName);
            String serverURL = httpOrHttps + ":" + hName;
            /* Preparing the URL and opening connection to the server*/
            URL servletURL = null;
            servletURL = new URL(serverURL);
            
            HttpURLConnection connection2 =  (HttpURLConnection)servletURL.openConnection();
            connection2.setDoOutput(true);
            connection2.setDoInput(true);
            connection2.setUseCaches(false);
            connection2.setRequestMethod("POST");
            connection2.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String len = Integer.toString(request.getBytes().length);
            connection2.setRequestProperty("Content-Length", len);
            
            /*Code for sending data to the server*/
            
            DataOutputStream dataOutputStream;
            dataOutputStream = new DataOutputStream(connection2.getOutputStream());
            
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                                            connection2.setReadTimeout(10000);
                                            connection2.connect();
                                            OutputStreamWriter wr = new OutputStreamWriter(connection2.getOutputStream());
                                            wr.write(request);
                                            wr.flush();
                                            wr.close();
            DataOutputStream toServer = 
                new DataOutputStream(connection2.getOutputStream());
            byte[] dataStream = request.getBytes();
            dataOutputStream.write(dataStream);  //Writing data to the servlet
            dataOutputStream.flush();
            dataOutputStream.close();
            
            /*Code for getting and processing response from DHL's servlet*/
            InputStream inputStream = null;
            inputStream = connection2.getInputStream();
            StringBuffer response1 = new StringBuffer();
            int printResponse;
                
            //Reading the response into StringBuffer
             while ((printResponse=inputStream.read())!=-1) 
             {
                 response1.append((char) printResponse);
             }
             inputStream.close();

             //System.out.println("response 1--> "+response1.toString());
             response = response1.toString();
             }
             catch(MalformedURLException mfURLex)
             {
                 logger.severe("MalformedURLException "+mfURLex.getMessage());
             }
             catch(IOException e)
             {
                 logger.severe("IOException "+e.getMessage());
             //e.printStackTrace();
             }
             
             return response;
        }
    }    
    
