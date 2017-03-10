/*
 * @(#)AascXmlTransmitter.java        15/01/2015
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.util.AascLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.logging.Logger;


/**
 * AascXmlTransmitter Class is used to transmit the xml request to
 * connectship for processing and  receives the xml response.
 * @author 	Eshwari
 * @version - 1.0
 * @history
 *   
 *   15/02/15  Eshwari M   Cleaned up the code
 */
public class

AascXmlTransmitter {
    private StringBuffer xmlRequest = 
        null; // string buffer containing xml request
    private StringBuffer xmlResponse = 
        new StringBuffer(""); // string buffer containing xml response
    private URLConnection httpUrlConnection = 
        null; // returns connection object which encapsulates connection to a service
    private URL url = 
        null; // this object encapsulates url of server to which we need the connection
    private static Logger logger = 
        AascLogger.getLogger("AascXmlTransmitter"); // logger object for issuing logging requests
    // The following details are needed to access ups
    private String accessUserId = "";
    private String accessPassword = "";
    private String accessInfo = "";
    private String accessLicenseNumber = ""; // "4BC4C86C246114BC";

    /** default constructor.*/
    public AascXmlTransmitter() {
    }

    /**
     * Constructor to set username,password and accessLicenseNumber.
     */
    public AascXmlTransmitter(String accessUserId, String accessPassword, 
                              String accessLicenseNumber) {
        this.accessUserId = accessUserId;
        this.accessPassword = accessPassword;
        this.accessLicenseNumber = accessLicenseNumber;
    }

    /**
     * This method is used to send the XmlTransmitter information to a designated service.  
     * @throws java.lang.Exception
     */
    private void contactService() throws Exception {
        logger.info("Entered contactService() method");
        url = httpUrlConnection.getURL();
        logger.info("connected to : " + url);
        // Setup HTTP POST parameters
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setUseCaches(false);

        // Get POST data from input StringBuffer containing an XML document 
        String queryString = xmlRequest.toString();

        // POST data
        OutputStream out = httpUrlConnection.getOutputStream();

        out.write(queryString.getBytes());
        logger.info("writing the request to connection stream");

        // get response data from URL connection and save in a StringBuffer
        String data = "";

        try {
            data = readURLConnection(httpUrlConnection);
        } catch (Exception e) {
            logger.severe("exception in contact service method " + 
                          e.getMessage());
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (Exception exception) {
                logger.info("closing output stream" + exception);
            }
        }
        xmlResponse = new StringBuffer(data);
        logger.info("got response from UPS");
    } // method contactService


    /**
     *processRequest method takes UpsLicenseRequest and calls contactService method 
     * to connect to connectShip/ups which sends the request over that httpUrlConnection and
     * retreives the response.
     *@param shipmentRequest StringBuffer
     *@param httpUrlConnection URLConnection
     *@return UpsLicenseResponse String
     */
    public  String processRequest(StringBuffer shipmentRequest, 
                          URLConnection httpUrlConnection) {
        logger.info("entered processRequest() method");
        this.httpUrlConnection = httpUrlConnection;

        xmlRequest = (new StringBuffer()).append(shipmentRequest);
        try {

            if (httpUrlConnection != null) {
                contactService();

            } else {
                logger.severe("connection is not established");
            }

        } catch (Exception e) {
            logger.severe("Cannot contact the service: " + e.toString());
        }
        logger.info("Exit from processRequest() method");
        return xmlResponse.toString();
    }

    /**
     *processRequest method takes shipmentRequest and calls contactService method 
     * to connect to connectShip/ups which sends the request over that httpUrlConnection and
     * retreives the response.
     *@param shipmentRequest StringBuffer
     *@param httpUrlConnection URLConnection
     *@param serviceName String 
     *@return shipmentResponse String
     */
    public String processRequest(StringBuffer shipmentRequest, 
                          URLConnection httpUrlConnection, 
                          String serviceName) {
        logger.info("entered processRequest() method");
        this.httpUrlConnection = httpUrlConnection;
        if (!(serviceName.equals(""))) {

            accessInfo = 
                    "<?xml version=\"1.0\"?>" + "<AccessRequest xml:lang=\"en-US\">" + 
                    "<AccessLicenseNumber>" + accessLicenseNumber + 
                    "</AccessLicenseNumber>" + "<UserId>" + accessUserId + 
                    "</UserId>" + "<Password>" + accessPassword + 
                    "</Password>" + "</AccessRequest>";

            // create service request -- xml doc sent to UPS server
            xmlRequest = 
                    new StringBuffer().append(accessInfo).append(shipmentRequest);

            logger.info("prefixed access info to the xml ups request ");
        } // end of if(!(serviceName.equals("")))
        else {
            xmlRequest = (new StringBuffer()).append(shipmentRequest);
        }
        try {
            if (httpUrlConnection != null) {
                contactService();
            } else {
                logger.severe("connection is not established");
            }
        } catch (Exception e) {
            logger.severe("Cannot contact the service: " + e.toString());
        }
        logger.info("Exit from processRequest() method");
        return xmlResponse.toString();
    }

    /**
     * This method reads all of the data from a URL conection to a String.
     * @param urlConnection URLConnection object
     * @return String containg data read from that connection
     * @throws Exception raised when there is error in reading data from
     * connection or when there is error in closing the url reader        
     */
    public String readURLConnection(URLConnection urlConnection) throws Exception {
        logger.info("entered readURLConnection method");
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = 
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            int letter = 0;

            while ((letter = reader.read()) != -1) {
                buffer.append((char)letter);
            }
        } catch (Exception exception) {
            logger.severe("Cannot read from URL " + exception.getMessage());
            throw exception;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            } catch (IOException io) {
                logger.severe("Error closing URLReader" + io);
                throw io;
            }
        }
        logger.info("Exit from readURLConnection method");
        return buffer.toString();
    } // end of the method readURLConnection(URLConnection urlConnection)
}// end of the class
