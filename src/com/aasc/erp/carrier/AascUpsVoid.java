/*
 * @(#)AascUpsVoid.java       27/012/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.carrier;

import com.aasc.erp.carrier.AascXmlTransmitter;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.util.AascLogger;
import com.aasc.erp.bean.AascShipmentOrderInfo;
//import com.aasc.model.AascDeliveryInfo;
//import com.aasc.erp.bean.AascHeaderInfo;
//import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascShipMethodInfo;

import com.aasc.erp.bean.AascShipmentPackageInfo;

import java.io.FileOutputStream;

import java.net.HttpURLConnection;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * AascUpsVoid Class contains createShipmentRequest()
 * which is used for creating xml shipment void request which is sent
 * to UPS server  through AascXmlTransmitter Class.
 * @author 	Sunanda Kondapalli
 * @version 	1
 * History 
 * 
 *  16/02/2015   Eshwari M    Added the missed out code for Void functionality
 *  23/03/2015   Sunanda K    Removed the unnecessary comments and also removed the unnecessary conversion to linkedlist for code Review fix
 *  02/06/2015   Y Pradeep    Modified code to avoide null pointer exception at if conditions.
 *  
 */
public class AascUpsVoid {
    static Logger logger = 
        AascLogger.getLogger("AascUpsVoid"); // logger object 
    protected AascXmlTransmitter aascXmlTransmitter = 
        null; // for transmiting xml request object of AascXmlTransmitter
    protected AascVoidShipmentInfo aascVoidShipmentInfo = 
        null; // object of AascVoidShipmentInfo
    AascConnectShipConnection aascConnectShipConnection = 
        null; // object to create connection to UPS
    HttpURLConnection connection; // url connection with support for http specific features
    String error = ""; // for storing errors from connectship
    String protocol = ""; // protocol used to communicate with UPS
    String hostName = ""; // host name of system which receives the request 
    String prefix = ""; // page which receives processes request
    String userName = ""; // username to obtain authorised access to UPS
    String password = ""; // password to obtain authorised access to UPS
    String responseStatus = "";
    String outputFilePath = "";
    AascUpsVoidInfo aascUpsVoidInfo = null;
    String voidType = "";
    String trackingNum = "";
    String trackingTag = "";
    String accessLicenseNumber = "";

    /**
     *  default contructor.
     */
    public AascUpsVoid() {
    }

    /**
     * This method sets error string.
     *@param error  String.
     **/
    public void setError(String error) {
        this.error = error;
    }

    /**
     *This method gets error string.
     *@return String error.
     */
    public String getError() {
        return error;
    }

    /* private StringBuffer getVoidRequest() {

     StringBuffer voidRequest = new StringBuffer();
     String voidRequestHdr ="<?xml version=\"1.0\"?>"
     +"<VoidShipmentRequest>"
     +"	<Request>"
     +"<TransactionReference>"
     +"<CustomerContext>"+ aascShipmentHeaderInfo.getOrderNumber()+" </CustomerContext>"
     +"<XpciVersion>1.0001</XpciVersion>"
     +"</TransactionReference>"
     +"<RequestAction>1</RequestAction>"
     +"</Request>"
     +"<ShipmentIdentificationNumber >"+aascShipmentHeaderInfo.getWayBill()+"</ShipmentIdentificationNumber>"
     +"</VoidShipmentRequest>";

     voidRequest.append(voidRequestHdr);
     /*  while (packageIterator.hasNext()) {

     AascPackageInfo aascPackageBean = (AascPackageInfo) packageIterator.next();
     int msn = aascPackageBean.getMsn();
     String msnVal = String.valueOf(msn);

     voidRequest.append(getMSNStr(msnVal));
     }
     String voidRequestFtr = "</MSNLIST>" + "</VOIDREQUEST>";	

     voidRequest.append(voidRequestFtr);*/

    /* return voidRequest;
     }*/ // end of getVoidRequest()


    /** method used to covert the request string to contain require format
     * @param:  String as input 
     * @return: returns String with required format
     * */
    private String escape(String str) {
        String result = replace(str, "&", "&amp;");

        return result;
    }

    /** method used to replace a substring of a string with another substring
     * @param: takes String containing from substring which is replaced by to substring
     * @return: returns String with replaced values
     * */
    private String replace(String str, String from, String to) {
        int index = str.indexOf(from);

        if (index == -1) {
            return str;
        } else {
            int endIndex = index + from.length();

            return str.substring(0, index) + to + str.substring(endIndex);
        }
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

    /** creates an xml shipment void request 
     * and passes the request to AascXmlTransmitter.
     * and receives the Response from AascXmlTransmitter
     * @author Gayaz ahamed 
     * @param AascShipmentOrderInfo  AascShipmentOrderInfo Object to get the Shipment order header  Information
     * @param aascShipMethodInfo AascShipMethodInfo object 
     * @param aascProfileOptionsBean aascProfileOptionsBean object 
     * @return returnstatus Returns the int value , if 130 the response is success otherwise 
     * if int value is 131 then there is an error in the response file.
     * */
    AascShipmentHeaderInfo aascShipmentHeaderInfo;

    public int voidShipment(AascShipmentOrderInfo aascShipmentOrderInfo, 
                            AascShipMethodInfo aascShipMethodInfo, 
                            String cloudLabelPath) {
        logger.info(" Entered voidShipment() for Shipment");
        int returnstatus = 0;
        aascShipmentHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo();
        try {
            voidType = aascShipmentHeaderInfo.getVoidType();
            //String connectshipShipper; // to store connect shipper
            StringBuffer voidRequest = new StringBuffer(); // Request string
            String voidResponse = ""; // Response String
            LinkedList packageLinkedList =  aascShipmentOrderInfo.getShipmentPackageInfo();
            ListIterator packageIterator = packageLinkedList.listIterator();     
            String shipMethod = aascShipmentHeaderInfo.getShipMethodMeaning();

            logger.info("shipMethod" + shipMethod);
            int carrierId = aascShipMethodInfo.getCarrierId(shipMethod);

            logger.info(" carrierId" + carrierId);

            protocol = aascShipMethodInfo.getProtocol(carrierId);
            hostName = aascShipMethodInfo.getCarrierServerIPAddress(carrierId);
            prefix = aascShipMethodInfo.getCarrierPrefix(carrierId);
            userName = 
                    aascShipMethodInfo.getCarrierUserName(carrierId); // username to obtain authorised access to connectShip
            password = aascShipMethodInfo.getCarrierPassword(carrierId);
            accessLicenseNumber = 
                    aascShipMethodInfo.getAccessLicenseNumberFromCarrierId(carrierId);
            aascXmlTransmitter = 
                    new AascXmlTransmitter(userName, password, accessLicenseNumber);
            // creates AascXmlTransmitter Object to which the request is sent for processing
            aascVoidShipmentInfo = new AascVoidShipmentInfo();
            
            if ("ShipmentVoid".equalsIgnoreCase(voidType)) {
              voidRequest = voidRequest.append(
                               "<?xml version=\"1.0\"?>" + "<VoidShipmentRequest>" + 
                               "   <Request>" + "<TransactionReference>" + 
                               "<CustomerContext>"+aascShipmentHeaderInfo.getOrderNumber()+"</CustomerContext>" + 
                               "<XpciVersion>1.0001</XpciVersion>" + "</TransactionReference>" + 
                               "<RequestAction>1</RequestAction>" + "</Request>" + 
                               "<ShipmentIdentificationNumber >" + 
                               aascShipmentHeaderInfo.getWayBill() + 
                               "</ShipmentIdentificationNumber>" + "</VoidShipmentRequest>" );
 
            } 
            else if ("PackageVoid".equalsIgnoreCase(voidType)) {
                logger.info("In PackageVoid");
                int num = 0;
                AascShipmentPackageInfo aascPackageBean = null ;
                while (packageIterator.hasNext()) {
                    num++;      
                    aascPackageBean = (AascShipmentPackageInfo)packageIterator.next();
                    trackingNum = aascPackageBean.getTrackingNumber();
                    logger.info("void flag : " + num + " : " + 
                                aascPackageBean.getVoidFlag());
                    logger.info("aascPackageBean.getTemporaryFlag() : "+aascPackageBean.getTemporaryFlag());            
                    if (aascPackageBean.getTemporaryFlag().equalsIgnoreCase("Y")) {
                        trackingTag = 
                                trackingTag + "<TrackingNumber>" + trackingNum + 
                                "</TrackingNumber>";
                    }
                }

                voidRequest.append("<?xml version=\"1.0\"?>" + 
                                   "<VoidShipmentRequest>" + "  <Request>" + 
                                   "<TransactionReference>" + 
                                   "<CustomerContext>" + 
                                   aascShipmentHeaderInfo.getOrderNumber() + 
                                   " </CustomerContext>" + 
                                   "<XpciVersion>1.0001</XpciVersion>" + 
                                   "</TransactionReference>" + 
                                   "<RequestAction>1</RequestAction>" + 
                                   "</Request>" + "<ExpandedVoidShipment>" + 
                                   "<ShipmentIdentificationNumber >" + 
                                   aascShipmentHeaderInfo.getWayBill() + 
                                   "</ShipmentIdentificationNumber>" + 
                                   trackingTag + "</ExpandedVoidShipment>" + 
                                   "</VoidShipmentRequest>");
            
            }
            //connectshipShipper = aascShipmentHeaderInfo.getShipperName();

            //  outputFilePath=aascProfileOptionsBean.getLabelPath();
            //Gururaj modified code from cloud label Path 
            // outputFilePath = "E:/logfiles/";

            outputFilePath = cloudLabelPath;
            //Gururaj code end
            aascUpsVoidInfo = new AascUpsVoidInfo();
            if (outputFilePath != null) {
                try {
                    writeOutputFile(voidRequest.toString(), 
                                    outputFilePath + aascShipmentHeaderInfo.getOrderNumber() + 
                                    "_void_request.xml");
                } catch (Exception e) {
                    logger.severe("Got exception in writing voidResponse to outputfile" + 
                                  e.getMessage());  e.printStackTrace();
                }
            }
            logger.info("after writing request to file:" + outputFilePath + aascShipmentHeaderInfo.getOrderNumber()+
                        "_void_request.xml");
            aascConnectShipConnection = 
                    new AascConnectShipConnection(protocol, hostName, 
                                                  prefix + "/Void", userName, 
                                                  password);
            connection = aascConnectShipConnection.createConnection();
            if (connection != null) {
                logger.info("Void Request sent to UPS is " + 
                            voidRequest);
                voidResponse = 
                        escape(aascXmlTransmitter.processRequest(voidRequest, 
                                                                 connection, 
                                                                 "Void")); // sending the request for processing and receiving the response   
                if (outputFilePath != null) {
                    try {
                        writeOutputFile(voidResponse.toString(), 
                                        outputFilePath + 
                                        aascShipmentHeaderInfo.getOrderNumber() + 
                                        "_void_response.xml");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                logger.info("Void response from ups is " + 
                            voidResponse);
                responseStatus = aascUpsVoidInfo.parseResponse(voidResponse);
                logger.info(" the return status from aascVoidShipmentInfo.parseResponse is " + 
                            responseStatus);
                if (responseStatus.equalsIgnoreCase("success")) {
                    logger.info("if(responseStatus.equalsIgnoreCasesucess)");
                    error = "";
                    returnstatus = 130;
                    logger.info(error + returnstatus);
                } else {
                    error = responseStatus;
                    aascShipmentHeaderInfo.setMainError(error);
                    returnstatus = 131;
                }
            } else {
                returnstatus = 131;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "  ", e);
            returnstatus = 131;
        }
        logger.info(" Exit from voidShipment() for Shipment");
        return returnstatus;
    }

}

