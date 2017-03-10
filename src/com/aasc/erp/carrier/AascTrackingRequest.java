package com.aasc.erp.carrier;

/*
 * @(#)AascTrackingRequest.java        12/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */


import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.net.HttpURLConnection;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;


/**
 AascTrackingRequest class is used to create xml request 
 based on actionType(button click)-WayBill or Package Tracking Number 
 or POD if user clicks on WayBill then request for each package is generated
 else if user clicks on certain package tracking number then the request 
 is generated only for that package.
 It also creates connection that encapsulates connection to ups
 and it passes the connection and request to XmlTransmitter
 which sends back the response. 
 Then the received response is sent to AascTrackingInfo for parsing
 and setting the parsed values to bean. 
 @author	Y Pradeep
 @version 1.0 
  History
 *
 * 16-Jan-2015  Y Pradeep       Modified code to remove unused variables and commented code as suggested in Code Review document and ran self audit.
 * 20-Jan-2015  Y Pradeep       Modified auther and version, also removed commented code.
 * 17-Mar-2015  Eshwari M       Modified code to get label path that is placed in session at the time of user login and removed Profile option object
 */
public class AascTrackingRequest {
    private static Logger logger = 
        AascLogger.getLogger("AascTrackingRequest"); // Logger object which is used in issuing logging requests   
    private LinkedList packageList = null; // linked list to hold packages and thier corresponding details
    private LinkedList upsResponseList = null; // linked list to hold list of responses when actionType is waybill
    private ListIterator aascPackageIterator = null;
    private String requestOption = ""; // sets the option value for the shipment request 
    private String upsResponse = ""; // String to hold ups xml response   
    private AascTrackingInfo aascTrackingBean = null; // object that holds parsed xml response data
    private int packageListSize = 0; // total number of packages in delivery    
    // the following details are required to create connection to ups
    private AascConnectShipConnection aascConnectShipConnection = null; // object to create connection to ups
    private HttpURLConnection connection = null; // url connection with support for http specific features
    private String protocol = ""; // protocol used to communicate with ups
    private String hostName = ""; // host name of system which receives the request 
    private String prefix = ""; // page which receives processes request
    private String userName = ""; // username to obtain authorised access to ups
    private String password = ""; // password to obtain authorised access to ups
    private String service = ""; //
    private String packageTrackingNumber = "";
    private String accessLicenseNumber = ""; // accessLicenseNumber used to communicate with ups(added on 30/05/2007)
    private AascShipmentPackageInfo aascPackageInfo = null;
    private String shipTrackServiceRequest = "";
    // this object is used to process request and send the response back   
    private AascXmlTransmitter aascXmlTransmitter = null;

    // object used to call parse method
    private AascTrackingInfo aascTrackingInfo = new AascTrackingInfo();

    /** Default Constructor.*/
    public AascTrackingRequest() {
    }

    /**
     This method is used to create xml request depending on whether actionType(user clicked) 
     waybill or tracking,if actionType is waybill ,a request is created for each package in the 
     delivery else only one request if actionType is tracking.
     Then connection is obtained for ups from AascConnectShipConnection class
     both connection and request are sent to AascXmlTransmitter for request processing
     then the response is sent to AascTrackingInfo for parsing and setting the parsed data
     to bean and that bean is returned. 
     @param actionType String.
     @param trackingNumber String.
     @param aascTrackingOrderInfo aascTrackingOrderInfo bean object used to retreive package details.   
     @return aascTrackingBean AascTrackingInfo bean object which contains parsed xml response data. 
     */
    public AascTrackingInfo createRequest(String actionType, 
                                          String trackingNumber, 
                                          AascTrackingOrderInfo aascTrackingOrderInfo, String labelsPath){

        logger.info("Entered createRequest()");
        if (aascTrackingOrderInfo != null && actionType != null && !(actionType.equals(""))) {
            protocol = aascTrackingOrderInfo.getProtocol();
            userName = aascTrackingOrderInfo.getUserName();
            password = aascTrackingOrderInfo.getPassword();
            hostName = aascTrackingOrderInfo.getHostName();
            service = aascTrackingOrderInfo.getService();
            prefix = aascTrackingOrderInfo.getPrefix();

            accessLicenseNumber = aascTrackingOrderInfo.getAccessLicenseNumber();
            logger.info(protocol + "\t" + userName + "\t" + password + "\t" + hostName + "\t" + service + "\t" + prefix + "\t" + protocol + "\t" + accessLicenseNumber);

            aascXmlTransmitter = new AascXmlTransmitter(userName, password, accessLicenseNumber);

            aascTrackingBean = null;

            // creating connection to ups
            aascConnectShipConnection = new AascConnectShipConnection(protocol, hostName, prefix + "/" + service, userName, password);
            connection = aascConnectShipConnection.createConnection();
            if (connection == null) {
                logger.info("connection is null");
                aascTrackingBean = new AascTrackingInfo();
                aascTrackingBean.setResponseStatus("connection is null");
                return aascTrackingBean;
            } else {
                logger.info("connection established to UPS:" + connection);
                // if actionType is proof of delivery then set request option accordingly
                if (actionType.equals("POD")) {
                    requestOption = "9";
                } else {
                    requestOption = "1";
                }

                if (actionType.equalsIgnoreCase("POD")) {
                    logger.info("actionType is POD");
                    upsResponse = "";
                    upsResponseList = new LinkedList();

                    shipTrackServiceRequest = 
                            "<?xml version=\"1.0\"?>" + "<TrackRequest xml:lang=\"en-US\">" + 
                            "<Request>" + "<TransactionReference>" + 
                            "<CustomerContext>Success, all information</CustomerContext>" + 
                            "<XpciVersion>1.0</XpciVersion>" + 
                            "</TransactionReference>" + 
                            "<RequestAction>Track</RequestAction>" + 
                            "<RequestOption>" + requestOption + 
                            "</RequestOption>" + "</Request>" + 
                            "<TrackingNumber>" + trackingNumber + 
                            "</TrackingNumber>" + "</TrackRequest>";
                    logger.info("Pod request" + shipTrackServiceRequest);

                    // send xml request to UPS
                    upsResponse = aascXmlTransmitter.processRequest(new StringBuffer().append(shipTrackServiceRequest), connection, service);
                    logger.info("pod upsResponse:" + upsResponse);

                    connection.disconnect();

                    upsResponseList.add(upsResponse);
                    if (upsResponse != null && !upsResponse.equals("")) {
                        aascTrackingBean = aascTrackingInfo.parseResponse(actionType, upsResponseList, aascTrackingOrderInfo, labelsPath); //aascProfileOptionsInfo);
                    } else {
                        logger.severe("ups pod response is null");
                        aascTrackingBean = new AascTrackingInfo();
                        aascTrackingBean.setResponseStatus("ups pod response is null");
                        return aascTrackingBean;
                    }

                } // end of if pod
                else if (actionType.equalsIgnoreCase("TRACKING")) {
                    logger.info("actionType is TRACKING");
                    upsResponse = "";
                    upsResponseList = new LinkedList();

                    // set ShipTrack xml service request to be sent to UPS//"1Z1204830384023479"
                    shipTrackServiceRequest = 
                            "<?xml version=\"1.0\"?>" + "<TrackRequest xml:lang=\"en-US\">" + 
                            "<Request>" + "<TransactionReference>" + 
                            "<CustomerContext>Success, all information</CustomerContext>" + 
                            "<XpciVersion>1.0</XpciVersion>" + 
                            "</TransactionReference>" + 
                            "<RequestAction>Track</RequestAction>" + 
                            "<RequestOption>" + requestOption + 
                            "</RequestOption>" + "</Request>" + 
                            "<TrackingNumber>" + trackingNumber + 
                            "</TrackingNumber>" + "</TrackRequest>";

                    logger.info("UPS Tracking request" + 
                                shipTrackServiceRequest);

                    // send xml request to UPS
                    upsResponse = 
                            aascXmlTransmitter.processRequest(new StringBuffer().append(shipTrackServiceRequest), 
                                                              connection, 
                                                              service);
                    logger.info("Tracking upsResponse:" + upsResponse);

                    connection.disconnect();

                    upsResponseList.add(upsResponse);
                    if (upsResponse != null && !upsResponse.equals("")) {
                        aascTrackingBean = 
                                aascTrackingInfo.parseResponse(actionType, 
                                                               upsResponseList, 
                                                               aascTrackingOrderInfo, 
                                                               labelsPath);
                    } else {
                        logger.severe("ups tracking response is null when actionType is " + 
                                      actionType);
                        aascTrackingBean = new AascTrackingInfo();
                        aascTrackingBean.setResponseStatus("ups tracking response is null ");
                        return aascTrackingBean;
                    }
                } // end of if(actionType.equals("TRACKING"))
                else {
                    if (actionType.equalsIgnoreCase("WAYBILL")) {
                        logger.info("actionType is WAYBILL");
                        upsResponse = "";
                        upsResponseList = new LinkedList();

                        packageList = 
                                aascTrackingOrderInfo.getPackageInfo();
                        if (packageList == null) {
                            logger.info("package list is null");
                            aascTrackingBean = new AascTrackingInfo();
                            aascTrackingBean.setResponseStatus("package list is null");
                            return aascTrackingBean;
                        } else {
                            packageListSize = packageList.size();
                            aascPackageIterator = packageList.listIterator();

                            while (aascPackageIterator.hasNext()) {
                                connection = 
                                        aascConnectShipConnection.createConnection();
                                aascPackageInfo = 
                                        (AascShipmentPackageInfo)aascPackageIterator.next();

                                packageTrackingNumber = 
                                        aascPackageInfo.getTrackingNumber(); // "1Z1204830384023479";//         

                                // set ShipTrack xml service request to be sent to UPS
                                shipTrackServiceRequest = 
                                        "<?xml version=\"1.0\"?>" + 
                                        "<TrackRequest xml:lang=\"en-US\">" + 
                                        "<Request>" + 
                                        "<TransactionReference>" + 
                                        "<CustomerContext>Success, all information</CustomerContext>" + 
                                        "<XpciVersion>1.0</XpciVersion>" + 
                                        "</TransactionReference>" + 
                                        "<RequestAction>Track</RequestAction>" + 
                                        "<RequestOption>1</RequestOption>" + 
                                        "</Request>" + "<TrackingNumber>" + 
                                        trackingNumber + "</TrackingNumber>" + 
                                        "</TrackRequest>";
                                // changing packageTrackingNumber to trackingNumber in the request above
                                logger.info("Package Tracking request" + 
                                            shipTrackServiceRequest + 
                                            "for tracking number" + 
                                            packageTrackingNumber);

                                // send ShipConfirm service request to UPS
                                upsResponse = 
                                        aascXmlTransmitter.processRequest(new StringBuffer().append(shipTrackServiceRequest), 
                                                                          connection, 
                                                                          service);
                                if (upsResponse != null && 
                                    !upsResponse.equals("")) {
                                    upsResponseList.add(upsResponse);
                                } else {
                                    logger.info("ups tracking response is null when actionType is " + 
                                                actionType);
                                    aascTrackingBean = new AascTrackingInfo();
                                    aascTrackingBean.setResponseStatus("ups tracking response is null ");
                                    return aascTrackingBean;
                                }
                                logger.info("Package Tracking response" + 
                                            upsResponse + 
                                            "for tracking number" + 
                                            packageTrackingNumber);
                            } // end of while  

                            connection.disconnect();

                            if (upsResponseList.size() > 0) {

                                aascTrackingBean = 
                                        aascTrackingInfo.parseResponse(actionType, 
                                                                       upsResponseList, 
                                                                       aascTrackingOrderInfo, 
                                                                       labelsPath); 
                            } else {
                                logger.info("ups tracking response is null when actionType is " + 
                                            actionType);
                                aascTrackingBean = new AascTrackingInfo();
                                aascTrackingBean.setResponseStatus("ups tracking response is null ");
                                return aascTrackingBean;
                            }
                        } // else of if(packageList==null)
                    } // end of if(actionType.equals("WAYBILL"))
                } // end of else
            } // else part of if(connection==null)          
        } // end of if(aascTrackingOrderInfo!=null && actionType!=null) 
        else {
            logger.severe("Order info bean is null or action type is null");
            aascTrackingBean = new AascTrackingInfo();
            aascTrackingBean.setResponseStatus("Order info bean is null or action type is null");
            return aascTrackingBean;
        }
        logger.info("Exit from createRequest()");
        return aascTrackingBean;
    } // end of method

    
    }
// end of class

