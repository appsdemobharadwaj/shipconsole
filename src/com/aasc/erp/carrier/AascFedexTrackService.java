package com.aasc.erp.carrier;

/*
 * @(#)AascFedexTrackingInfo.java
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
import com.aasc.erp.util.AascLogger;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.logging.Logger;

/**
 Creating track resquest and pod request to service.
 @author Y Pradeep
 @version 1
 @since
 * 20/01/2015  Y Pradeep     Modified auther and version, also removed commented code.
 * 02/02/2015  Eshwari M     Modified code by changing the track service in the URL sent to Fedex tracking request
 */
public class AascFedexTrackService {
    private static Logger logger = 
        AascLogger.getLogger("AascFedexTrackService");

    public String processTrackRequest(String key, String password, 
                                      String acctNo, String meterNo, 
                                      String trackNo, String actionType, 
                                      String labelPath, String ipAddress, 
                                      int portNo) {
        // String ipAddress="wsbeta.fedex.com";
        // String portNo="443";

        StringBuffer trackRequest = new StringBuffer();
        String soapReply = "";

        BufferedWriter out = null;
        FileWriter fstream = null;
        String timeStampStr = "";

        try {
            trackRequest.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://fedex.com/ws/track/v4\">");
            trackRequest.append("<SOAP-ENV:Body>");
            trackRequest.append("<TrackRequest>");
            trackRequest.append("<WebAuthenticationDetail>");
            trackRequest.append("<UserCredential>");
            trackRequest.append("<Key>" + key + "</Key>");
            trackRequest.append("<Password>" + password + "</Password>");
            trackRequest.append("</UserCredential>");
            trackRequest.append("</WebAuthenticationDetail>");
            trackRequest.append("<ClientDetail>");
            trackRequest.append("<AccountNumber>" + acctNo + 
                                "</AccountNumber>");
            trackRequest.append("<MeterNumber>" + meterNo + "</MeterNumber>");
            trackRequest.append("<Localization>");
            trackRequest.append("<LanguageCode>EN</LanguageCode>");
            trackRequest.append("</Localization>");
            trackRequest.append("</ClientDetail>");
            trackRequest.append("<TransactionDetail>");
            trackRequest.append("<CustomerTransactionId>Tracking trackRequestuest</CustomerTransactionId>");
            trackRequest.append("<Localization>");
            trackRequest.append("<LanguageCode>EN</LanguageCode>");
            trackRequest.append("</Localization>");
            trackRequest.append("</TransactionDetail>");
            trackRequest.append("<Version>");
            trackRequest.append("<ServiceId>trck</ServiceId>");
            trackRequest.append("<Major>4</Major>");
            trackRequest.append("<Intermediate>1</Intermediate>");
            trackRequest.append("<Minor>0</Minor>");
            trackRequest.append("</Version>");
            trackRequest.append("<PackageIdentifier>");
            trackRequest.append("<Value>" + trackNo + "</Value>");
            trackRequest.append("<Type>TRACKING_NUMBER_OR_DOORTAG</Type>");
            trackRequest.append("</PackageIdentifier>");

            if (actionType.equalsIgnoreCase("WAYBILL")) {
                trackRequest.append("<IncludeDetailedScans>false</IncludeDetailedScans>");
                logger.info("waybill");
            } else {
                trackRequest.append("<IncludeDetailedScans>true</IncludeDetailedScans>");
                logger.info("track");
            }
            trackRequest.append("</TrackRequest>");
            trackRequest.append("</SOAP-ENV:Body>");
            trackRequest.append("</SOAP-ENV:Envelope>");


            try {
                timeStampStr = 
                        (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                                          "_");
                fstream = 
                        new FileWriter(labelPath + trackNo + "_" + timeStampStr + 
                                       "_" + actionType + "_soapRequest.xml");
                out = new BufferedWriter(fstream);
                out.write(nullStrToSpc(trackRequest));
            } catch (IOException ioe) {
                logger.severe("IOException while creating the request file for tracking: " + 
                              ioe.getMessage());
            } catch (Exception exe) {
                logger.severe("Exeception while creating the request file for tracking: " + 
                              exe.getMessage());
            } finally {
                try {
                    out.close();
                } catch (Exception ex) {
                    logger.severe("Exeception while closing the request file for tracking: " + 
                                  ex.getMessage());
                }
            }

            soapReply = 
            processXML(nullStrToSpc(trackRequest), true, ipAddress + 
                                           ":" + portNo + "/web-services/track",
                                           labelPath, trackNo, actionType);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return soapReply;
        }
    }

    public String processXML(String request, boolean httpsFlag, String hName, String labelPath, String trackNum, 
                             String actionType) {
        URL aspPage;
        InputStream isXMLResponse;
        String response = "";
        String httpOrHttps = "https";
        int charRead;
        ByteArrayOutputStream baos = null;
        FileOutputStream fout = null;
        String filename = "";
        String timeStampStr = "";

        logger.info("SOAP Request: " + request);

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

            timeStampStr = 
                    (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                                      "_");
            try {
                filename = 
                        labelPath + trackNum + "_" + timeStampStr + "_" + actionType + 
                        "_soapReply.xml";
                fout = new FileOutputStream(filename);
            } catch (Exception e) {
                logger.severe("Exception while forming the file name:" + 
                              e.getMessage());
            }
            baos = new ByteArrayOutputStream();


            while (true) {
                charRead = isXMLResponse.read();
                if (charRead == -1)
                    break;

                baos.write(charRead);
                try {
                    fout.write(charRead);
                } catch (Exception ex) {
                    logger.severe("exception while writing soap response to file: " + 
                                  ex.getMessage());
                }
            }

            response = nullStrToSpc(baos);
            response = response.replaceAll("<SOAP-ENV:", "<");
            response = response.replaceAll("<ns:", "<");
            response = response.replaceAll("</ns:", "</");
            response = response.replaceAll("</SOAP-ENV:", "</");
            response = response.replaceAll("xmlns=\"http://fedex.com/ws/track/v4\"", "");
            response = response.replaceAll("<soapenv:", "<");
            response = response.replaceAll("<env:", "<");
            response = response.replaceAll("<v4:", "<");
            response = response.replaceAll("</v4:", "</");
            response = response.replaceAll("</env:", "</");
            response = response.replaceAll("</soapenv:", "</");


            isXMLResponse.close();
        } catch (MalformedURLException mue) {
            logger.severe("MalformedURLException: " + mue.getMessage());
        } catch (IOException ioe) {
            logger.severe("IOException: " + ioe.getMessage());
        } catch (Exception exe) {
            logger.severe("Exeception: " + exe.getMessage());
        } finally {
            try {
                fout.close();

            } catch (Exception ex) {
                logger.severe("Exception while closing the file: " + 
                              ex.getMessage());
            }
            return response;
        }
    }

    public String processSpod(String key, String password, String acctNo, 
                              String meterNo, String trackNo, 
                              String carrierCode, String shipDate, 
                              String labelPath, String actionType, 
                              String ipAddress, int portNo) {

        // String ipAddress="wsbeta.fedex.com";
        // String portNo="443";

        StringBuffer spodRequest = new StringBuffer();
        String soapReply = "";

        BufferedWriter out = null;
        FileWriter fstream = null;
        String timeStampStr = "";

        try {
            spodRequest.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://fedex.com/ws/track/v4\">");
            spodRequest.append("<SOAP-ENV:Body>");
            spodRequest.append("<SignatureProofOfDeliveryLetterRequest>");
            spodRequest.append("<WebAuthenticationDetail>");
            spodRequest.append("<UserCredential>");
            spodRequest.append("<Key>" + key + "</Key>");
            spodRequest.append("<Password>" + password + "</Password>");
            spodRequest.append("</UserCredential>");
            spodRequest.append("</WebAuthenticationDetail>");
            spodRequest.append("<ClientDetail>");
            spodRequest.append("<AccountNumber>" + acctNo + 
                               "</AccountNumber>");
            spodRequest.append("<MeterNumber>" + meterNo + "</MeterNumber>");
            spodRequest.append("</ClientDetail>");
            spodRequest.append("<TransactionDetail>");
            spodRequest.append("<CustomerTransactionId>SPOD Request</CustomerTransactionId>");
            spodRequest.append("<Localization>");
            spodRequest.append("<LanguageCode>EN</LanguageCode>");
            spodRequest.append("</Localization>");
            spodRequest.append("</TransactionDetail>");
            spodRequest.append("<Version>");
            spodRequest.append("<ServiceId>trck</ServiceId>");
            spodRequest.append("<Major>4</Major>");
            spodRequest.append("<Intermediate>0</Intermediate>");
            spodRequest.append("<Minor>0</Minor>");
            spodRequest.append("</Version>");
            spodRequest.append("<QualifiedTrackingNumber>");
            spodRequest.append("<TrackingNumber>" + trackNo + 
                               "</TrackingNumber>");
            spodRequest.append("<ShipDate>" + shipDate + "</ShipDate>");
            spodRequest.append("<Carrier>" + carrierCode + "</Carrier>");
            spodRequest.append("</QualifiedTrackingNumber>");
            spodRequest.append("<LetterFormat>PNG</LetterFormat>");
            spodRequest.append("</SignatureProofOfDeliveryLetterRequest>");
            spodRequest.append("</SOAP-ENV:Body>");
            spodRequest.append("</SOAP-ENV:Envelope>");

            try {
                timeStampStr = 
                        (new java.util.Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                                          "_");
                fstream = 
                        new FileWriter(labelPath + trackNo + "_" + timeStampStr + 
                                       "_" + actionType + "_soapRequest.xml");
                out = new BufferedWriter(fstream);
                out.write(nullStrToSpc(spodRequest));
            } catch (IOException ioe) {
                logger.severe("IOException while creating the request file for spod: " + 
                              ioe.getMessage());
            } catch (Exception exe) {
                logger.severe("Exeception while creating the request file for spod: " + 
                              exe.getMessage());
            } finally {
                try {
                    out.close();
                } catch (Exception ex) {
                    logger.severe("Exeception while closing the request file for spod: " + 
                                  ex.getMessage());
                }
            }

            soapReply = 
            processXML(nullStrToSpc(spodRequest), true, ipAddress + ":" + 
                                           portNo + "/web-services/track",  labelPath, 
                                           trackNo, actionType);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return soapReply;
        }
    }

    private String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

}
