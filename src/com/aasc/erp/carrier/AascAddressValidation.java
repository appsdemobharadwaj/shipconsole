package com.aasc.erp.carrier;
/*
 * @(#)AascAddressValidation.java     24/02/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */
 
import com.aasc.erp.bean.AascAddressValidationBean;
import com.aasc.erp.util.AascLogger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 /**
  * AascAddressValidation class is action class for Address Validation.
  * @version   1
  * @author    Y Pradeep
  * History
  * 
  * 24-Feb-2015  Y Pradeep       Added this file for Address Validation.
  * 26-Feb-2015  Y Pradeep       Modified code to display details accordingly.
  * 27-Feb-2015  Y Pradeep       Replace printstacktrace with getmessage in error handling.
  * 14-Mar-2015  Y Pradeep       Hot coded Username, Password and Access License number for UPS Address Validation.
  * 23-Mar-2015  Y Pradeep       Modified code to save request and response based on date and time.
  * 17-Apr-2015  Y Pradeep       Added proper naming convention for generated xml files.
  * 14-Jul-2015  Suman G         Modified protocol from https to http.
  * 12-Dec-2015  Y Pradeep       Added encode for required field while framing request. Bug #4085.
  */
  
public class AascAddressValidation {

    private String appsAccountNumber = "";
    private String appsUserName = "";
    private String appsPassword = "";
    private String isFedExNeeded = "";
    private String isUPSNeeded = "";
    private String upsUserName = "";
    private String upsPassword = "";
    private String upsAccessLicenseNumber = "";
    private String fedExKey = "";
    private String fedExPassword = "";
    private String accountNumber = "";
    private String meterNumber = "";
    private String referenceId = "";
    private String requestTimestamp = "";
    private String requestOption = "3";  //Identifies the optional processing to be performed. 3 - Address Validation and Address Classification.
    private String maximumCandidateListSize = "15";  // The maximum number of Candidates to return for this request.
    private String comapanyName = "";
    private String attentionName = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String state = "";
    private String postcodePrimaryLow = "";
    private String postcodeExtendedLow = "";
    private String region = "";
    private String countryCode = "";
    private byte[] replyOut = new byte[500]; // Byte array for catching reply from 
    private String avResponse = "";
    private String addValidationResponse = "";
    
    
    private static Logger logger = AascLogger.getLogger("AddressValidation"); // logger object used for issuing logging requests
    
    public AascAddressValidation() {
    }

    /** This method is called from AascAddressValidationAction.java action class to  frame request for validating address from Web Services.
     * @param aascAddressValidationBean
     * @param ipAddress
     * @param portNo
     * @param cloudLabelPath
     * @return object of type HashMap.
     */
    public HashMap sendAddressValidationRequest(AascAddressValidationBean aascAddressValidationBean, String ipAddress, int portNo, String cloudLabelPath){
        
        logger.info("Entered sendAddressValidationRequest method");
        
        if(aascAddressValidationBean.getCarrierCode() == 110 || aascAddressValidationBean.getCarrierCode() == 111){
            isFedExNeeded = "true";
            isUPSNeeded = "false";
        }else {
            isFedExNeeded = "false";
            isUPSNeeded = "true";
        }
        
        upsUserName = aascAddressValidationBean.getUpsUserName();                       //"jeffhenson1";
        upsPassword = aascAddressValidationBean.getUpsPassword();                       //"Court1313";
        upsAccessLicenseNumber = aascAddressValidationBean.getAccessLicenseNumber();    //"4CE60A956404D495";
        fedExKey =  aascAddressValidationBean.getFedExKey();
        fedExPassword = aascAddressValidationBean.getFedExPassword();
        accountNumber = aascAddressValidationBean.getCarrierAccountNumber();
        meterNumber = aascAddressValidationBean.getMeterNumber();
        referenceId = aascAddressValidationBean.getOrderNumber();
        comapanyName = aascAddressValidationBean.getCustomerName();
        attentionName = aascAddressValidationBean.getAttentionName();
        addressLine1 = aascAddressValidationBean.getAddress1();
        addressLine2 = aascAddressValidationBean.getAddress2();
        city = aascAddressValidationBean.getCity();
        state = aascAddressValidationBean.getState();
        postcodePrimaryLow = aascAddressValidationBean.getPostalCode();
        countryCode = aascAddressValidationBean.getCountryCode();
        appsAccountNumber = aascAddressValidationBean.getAddressValidationAccNumber();
        appsUserName = aascAddressValidationBean.getAddressValidationUserName();
        appsPassword = aascAddressValidationBean.getAddressValidationPassword();

        requestTimestamp = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
//        logger.info("timestamp = " + requestTimestamp);
        
        StringBuffer avHeaderRequest = new StringBuffer("");
        HashMap addressHashMap = null;
        try{
            avHeaderRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.addressvalidation.apps.com/\">\n");
            avHeaderRequest.append("<soapenv:Header>\n");
            avHeaderRequest.append(createAppsAuthenticationDetail(appsAccountNumber, appsUserName, appsPassword));
            avHeaderRequest.append("</soapenv:Header>\n");
            avHeaderRequest.append("<soapenv:Body>\n");
            avHeaderRequest.append("<ws:validateAddress>\n");
            avHeaderRequest.append(createAddressDetails(isFedExNeeded,
                                                        isUPSNeeded,
                                                        upsUserName,
                                                        upsPassword,
                                                        upsAccessLicenseNumber,
                                                        fedExKey,
                                                        fedExPassword,
                                                        accountNumber,
                                                        meterNumber,
                                                        referenceId,
                                                        requestTimestamp,
                                                        requestOption,
                                                        maximumCandidateListSize,
                                                        comapanyName,
                                                        attentionName,
                                                        addressLine1,
                                                        addressLine2,
                                                        city,
                                                        state,
                                                        postcodePrimaryLow,
                                                        postcodeExtendedLow,
                                                        region,
                                                        countryCode));
            avHeaderRequest.append("</ws:validateAddress>\n");
            avHeaderRequest.append("</soapenv:Body>\n");
            avHeaderRequest.append("</soapenv:Envelope>");

            avResponse = sendSOAPRequest(avHeaderRequest.toString(), false, ipAddress + ":" + 
                            portNo + "/AddressValidation/Validate", 
                            cloudLabelPath);
            
            replyOut = avResponse.getBytes();
            
            addValidationResponse = new String(replyOut, "ISO-8859-1");
                if (addValidationResponse != null && !addValidationResponse.equals("")) {

                    AascAddressValidationInfo reponseParse = new AascAddressValidationInfo();
                    addressHashMap = reponseParse.parseWebServiceResponse(addValidationResponse);
                }
            
        } catch(Exception e) {
            logger.info("Got exception in sendAddressValidationRequest method address validation carrier class = "+e.getMessage());
        }
        return addressHashMap;
    }

    /** This method is used to frame xml request for Apps Associates Authentication details with Apps Associates User Name, Password and Account Number details.
     * @param accountNumber
     * @param userName
     * @param password
     * @return object of type StringBuffer.
     */
    private StringBuffer createAppsAuthenticationDetail(String accountNumber, String userName, String password){
    
        StringBuffer appsAuthenticationDetail = new StringBuffer();
        
        appsAuthenticationDetail.append("<ws:AppsAssociatesUserName>"+userName+"</ws:AppsAssociatesUserName>");
        appsAuthenticationDetail.append("<ws:AppsAssociatesPassword>"+password+"</ws:AppsAssociatesPassword>");
        appsAuthenticationDetail.append("<ws:AppsAssociatesAccountNo>"+accountNumber+"</ws:AppsAssociatesAccountNo>\n");
        
        return appsAuthenticationDetail;
    }

    /** This method is used to frame xml request for address details with Ship to Address details from Shipment page.
     * @param isFedExNeeded
     * @param isUPSNeeded
     * @param upsUserName
     * @param upsPassword
     * @param upsAccessLicenseNumber
     * @param fedExKey
     * @param fedExPassword
     * @param accountNumber
     * @param meterNumber
     * @param referenceId
     * @param requestTimestamp
     * @param requestOption
     * @param maximumCandidateListSize
     * @param comapanyName
     * @param attentionName
     * @param addressLine1
     * @param addressLine2
     * @param city
     * @param state
     * @param postcodePrimaryLow
     * @param postcodeExtendedLow
     * @param region
     * @param countryCode
     * @return object of type StringBuffer.
     */
    private StringBuffer createAddressDetails(String isFedExNeeded,
                                              String isUPSNeeded,
                                              String upsUserName,
                                              String upsPassword,
                                              String upsAccessLicenseNumber,
                                              String fedExKey,
                                              String fedExPassword,
                                              String accountNumber,
                                              String meterNumber,
                                              String referenceId,
                                              String requestTimestamp,
                                              String requestOption,
                                              String maximumCandidateListSize,
                                              String comapanyName,
                                              String attentionName,
                                              String addressLine1,
                                              String addressLine2,
                                              String city,
                                              String state,
                                              String postcodePrimaryLow,
                                              String postcodeExtendedLow,
                                              String region,
                                              String countryCode){
        StringBuffer addressDetail = new StringBuffer();
        
        addressDetail.append("<isFedExNeeded>"+isFedExNeeded+"</isFedExNeeded>");
        addressDetail.append("<isUPSNeeded>"+isUPSNeeded+"</isUPSNeeded>");
        addressDetail.append("<UPSUserName>"+upsUserName+"</UPSUserName>");
        addressDetail.append("<UPSPassword>"+upsPassword+"</UPSPassword>");
        addressDetail.append("<UPSAccessLicenseNumber>"+upsAccessLicenseNumber+"</UPSAccessLicenseNumber>");
        addressDetail.append("<fedExKey>"+fedExKey+"</fedExKey>");
        addressDetail.append("<fedExPassword>"+fedExPassword+"</fedExPassword>");
        addressDetail.append("<accountNumber>"+accountNumber+"</accountNumber>");
        addressDetail.append("<meterNumber>"+meterNumber+"</meterNumber>");
        addressDetail.append("<referenceId>"+encode(referenceId)+"</referenceId>");
        addressDetail.append("<requestTimestamp>"+requestTimestamp+"</requestTimestamp>");
        addressDetail.append("<RequestOption>"+requestOption+"</RequestOption>");
        addressDetail.append("<MaximumCandidateListSize>"+maximumCandidateListSize+"</MaximumCandidateListSize>");
        addressDetail.append("<ComapanyName>"+encode(comapanyName)+"</ComapanyName>");
        addressDetail.append("<AttentionName>"+encode(attentionName)+"</AttentionName>");
        addressDetail.append("<AddressLine>"+encode(addressLine1)+"</AddressLine>");
        addressDetail.append("<AddressLine>"+encode(addressLine2)+"</AddressLine>");
        addressDetail.append("<City>"+encode(city)+"</City>");
        addressDetail.append("<State>"+encode(state)+"</State>");
        addressDetail.append("<PostcodePrimaryLow>"+encode(postcodePrimaryLow)+"</PostcodePrimaryLow>");
        addressDetail.append("<PostcodeExtendedLow>"+encode(postcodeExtendedLow)+"</PostcodeExtendedLow>");
        addressDetail.append("<Region>"+region+"</Region>");
        addressDetail.append("<CountryCode>"+countryCode+"</CountryCode>");
        
        return addressDetail;
    }
    
    /** 
     * This is the method where we establish the connection with Address Validation webservice.
     * here we sent soap request to Address Validation webservice.
     * @param request it is the soap request which we sent to Address Validation webservice
     * @param httpsFlag it is type of conncection
     * @param hName it is the host name
     * @param requestFilePath it is path where we save the request and response.
     * @return it returns response after sending the request to fedex webservice.
     */
    public String sendSOAPRequest(String request, boolean httpsFlag, 
                                  String hName, String requestFilePath) {
        URL aspPage;
        InputStream isXMLResponse;
        String response = "";
        String httpOrHttps = "https";
        String timeStampStr;
        timeStampStr = 
                (new Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                        "_");
//        logger.info("XML Request: " + request);
        try {
            writeOutputFile(request, requestFilePath + referenceId+"_"+timeStampStr+"_address_validation_request.xml");
        } catch (Exception fileNotFoundException) {
            logger.severe("Exception::"+fileNotFoundException.getMessage());
        }
        try {
//            if (httpsFlag) {
//                httpOrHttps = "https";
                disableSslVerification();
//            } else {
//                httpOrHttps = "http";
//            }

            logger.info("Posting to URL: " + hName);
            // Added below code to take protocal from properties file
//            aspPage = new URL(httpOrHttps + "://" + hName);
            aspPage = new URL(hName);
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
//            logger.info("reponse === "+response);
            isXMLResponse.close();
        } catch (MalformedURLException mue) {
            logger.severe("Exception::"+mue.getMessage());
        } catch (IOException ioe) {
            logger.severe("Exception::"+ioe.getMessage());
        } catch (Exception exe) {
            logger.severe("Exception::"+exe.getMessage());
        } finally {
            try {
                writeOutputFile(response, requestFilePath + referenceId+"_"+timeStampStr+"_address_validation_response.xml");
            } catch (Exception fileNotFoundException) {
                logger.severe("Exception::"+fileNotFoundException.getMessage());
            }
            return response;
        }
    }

    /** This method is used for saving framed request and response in a specified path for feature reference.
     * @param str
     * @param file
     * @throws Exception
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

    /** This method is used to disable SSL certificate if it is in invalid state at server side.
     */
    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] 
            {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
              public javax.net.ssl.X509KeyManager getX509KeyManager()
              {
                return null;
              }
              public void checkClientTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
              public void checkServerTrusted(java.security.cert.X509Certificate[] certs, 
                                             String authType)
              {
              }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (Exception e1) 
            {
                logger.info("Got exception in disbale ssl method address validation carrier class = "+e1.getMessage());
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException ne) {
            logger.info("Got exception in disbale ssl method address validation carrier class = "+ne.getMessage());
        } catch (Exception e) {
            logger.info("Got exception in disbale ssl method address validation carrier class = "+e.getMessage());
        }
    }
    
    private String encode(String src) {
        src = replaceStr(src, "&", "&amp;");
        src = replaceStr(src, "\"", "&quot;");
        src = replaceStr(src, "'", "&apos;");
        src = replaceStr(src, "<", "&lt;");
        src = replaceStr(src, ">", "&gt;");
        return src;
    }
    
    private String replaceStr(String src, String oldPattern, 
                              String newPattern) {

        String dst = ""; // the new bult up string based on src
        int i; // index of found token
        int last = 0; // last valid non token string data for concat  
        boolean done = false; // determines if we're done.

        if (src != null) {
            // while we'er not done, try finding and replacing
            while (!done) {
                // search for the pattern...
                i = src.indexOf(oldPattern, last);
                // if it's not found from our last point in the src string....
                if (i == -1) {
                    // we're done.
                    done = true;
                    // if our last point, happens to be before the end of the string
                    if (last < src.length()) {
                        // concat the rest of the string to our dst string
                        dst = dst.concat(src.substring(last, (src.length())));
                    }
                } else {
                    // we found the pattern
                    if (i != last) {
                        // if the pattern's not at the very first char of our searching point....
                        // we need to concat the text up to that point..
                        dst = dst.concat(src.substring(last, i));
                    }
                    // update our last var to our current found pattern, plus the lenght of the pattern
                    last = i + oldPattern.length();
                    // concat the new pattern to the dst string
                    dst = dst.concat(newPattern);
                }
            }
        } else {
            dst = src;
        }
        // finally, return the new string
        return dst;
    }    
}
