/*
 * @(#)AascFedexTrackingInfo.java
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.util.AascLogger;

import java.io.FileOutputStream;
import java.io.StringReader;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;


/**
 Parses xml track response and pod response and places the parse data in tracking information bean. 
 @author Eshwari M	
 @version 1
 @since
 * 17/12/2014 Eshwari M     Removed Adhoc and Delivery words for SC Lite while testing Tracking feature
 * 20/01/2015 Y Pradeep     Modified auther and version, also removed commented code.
 */
public class AascFedexTrackingInfo {
    // for parsing ups xml response    
    private StringReader reader = null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = null; // This object is used to encapsulate character stream into single object
    private DOMParser parser = null; // This object is used to parse xml document 
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private static Logger logger = AascLogger.getLogger("AascFedexTrackingInfo"); // logger object used for logging requests
    private AascFedexTrackingInfo aascFedexTrackingInfo = null; // bean that contains total parsed tracking information    
    private AascFedexTrackingInfo aascFedexTrackingScanInfo = null; // bean that contains parsed tracking package scan information
    private LinkedList scanNodeLinkedList = null; // scan node linked list containing package scan detail nodes   
    private Node node = null; // It represents single node in dom tree
    private NodeList scanNodeList = null; // It represents ordered collection of nodes
    private NodeList nodeList = null;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Date format
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm"); // time format
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
    private Date sqlDate;

    // following variables are used to hold scan details of the package
    private String date = "";
    private String time = "";
    private String location = "";
    private String city = "";
    private String state = "";
    private String country = "";

    private String countryCode = ""; // holds ship to country code 
    private String carrierCode = ""; // holds carrier code 
    private String trackingNumber = ""; // holds package tracking number
    private String signedForBy = ""; // holds the name of the person who received the delivery           
    private String dateTime = ""; // holds delivered date and time or holds package scan date and time   
    private String serviceType = ""; // holds the shipment service type    
    private String weightUnits = ""; // holds package weight units weight units
    private String weightStr = ""; // holds package weight value and 
    private String scanDescription = ""; // holds package scan description
    private String statusExceptionCode = ""; // holds package scan status exception code
    private String statusExceptionDesc = ""; // holds package scan status exception description    
    private String errorCode = ""; // holds error code contained in response
    private String errorMessage = ""; // holds error message contained in response
    private String status = ""; // contains status of the package whether it is in transit or delivered
    private LinkedList aascPackagelist = null; // linked list containing package info beans of the delivery
    private int packageCount = 0; // holds number of packages in the delivery
    private String packageCountString = ""; // string holding package count integer value
    private String softErrorType = null; // holds warning or information message type contained in response
    private String softErrorCode = null; // holds warning or information code type contained in response
    private String softErrorMsg = null; // holds warning or information message type contained in response
    private String responseStatus = null; // holds information of status in response
    private String actionType = null; // holds one of the values of  WAYBILL,TRACKING or POD button depending on user click

    private String letter = ""; // holds pod image retreived from response
    private byte[] decodedLetter; // holds decodes pod image in byte array
    private String urlPath = ""; // holds pod output file path
    private FileOutputStream fileOutputStream = null; // file output stream to write pod image to a file 

    /** Default Constructor.*/
    public AascFedexTrackingInfo() {
    }

    /**
     Replaces the null values with nullString.
     @return String that is ""
     @param obj object 
     */
    private String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /** Parses xml response depending on whether actionType is Tracking,
     and sets the parsed data to FedEx tracking bean and returns that bean.    
     @param soapResponse String contains the SOAP response for tracking.   
     @return aascFedexTrackingInfo AascFedexTrackingInfo contains parsed response data.
     */
    public AascFedexTrackingInfo parseWebServiceTrackResponse(String soapResponse, 
                                                              AascTrackingOrderInfo aascTrackingDeliveryInfo) {
        String severity = "";

        String shipTimestamp = "";
        String deliveryTimeStamp = "";
        String eventTimestamp = "";

        int shipDateIndex = 0;
        int shipTimeIndex = 0;
        String shipDate = "";
        String shipTime = "";
        Date shipSqlDate = null;

        int destDateIndex = 0;
        int destTimeIndex = 0;
        String destDate = "";
        String destTime = "";
        Date destSqlDate = null;


        int eventDateIndex = 0;
        int eventTimeIndex = 0;
        String evtDate = "";
        String evtTime = "";
        Date evtSqlDate = null;

        try {
            aascFedexTrackingInfo = new AascFedexTrackingInfo();

            aascPackagelist = aascTrackingDeliveryInfo.getPackageInfo();
            packageCount = aascPackagelist.size();
            aascFedexTrackingInfo.setPackageCount(packageCount);

            actionType = aascTrackingDeliveryInfo.getActionType();
            aascFedexTrackingInfo.setActionType(actionType);

            logger.info("\n package count: " + packageCount + "\n actionType: " + actionType);


            parser = new DOMParser();
            reader = new StringReader(soapResponse);
            input = new InputSource(reader);
            parser.setValidationMode(false);
            parser.showWarnings(true);
            parser.parse(input);
            xmlDocument = parser.getDocument();
            root = (XMLElement)xmlDocument.getDocumentElement();

            if (actionType.equals("TRACKING") && root != null) {
                logger.info("actionType is TRACKING");

                node = root.selectSingleNode("/Envelope/Body/TrackReply/HighestSeverity/text()");
                if (node != null) {
                    severity = nullStrToSpc(node.getNodeValue());
                }


                if ("SUCCESS".equalsIgnoreCase(severity)) {
                    logger.info("responseStatus is  success");
                    aascFedexTrackingInfo.setResponseStatus("success");

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/TrackingNumber/text()");
                    if (node != null) {
                        trackingNumber = nullStrToSpc(node.getNodeValue());
                        logger.info("trackingNumber==" + trackingNumber);
                        aascFedexTrackingInfo.setTrackingNumber(trackingNumber);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/ShipTimestamp/text()");
                    if (node != null) {
                        shipTimestamp = nullStrToSpc(node.getNodeValue());
                        logger.info("shipTimestamp==" + shipTimestamp);

                        if (!"".equalsIgnoreCase(shipTimestamp)) {
                            try {
                                shipDateIndex = shipTimestamp.indexOf("T");

                                shipDate = 
                                        shipTimestamp.substring(0, shipDateIndex);
                                shipSqlDate = 
                                        new Date(simpleDateFormat.parse(shipDate).getTime());
                                shipDate = dateFormatter.format(shipSqlDate);
                            } catch (Exception e) {
                                logger.severe("Exception while retrieving ship date" + 
                                              e.getMessage());
                            }

                            try {
                                shipTimeIndex = shipTimestamp.lastIndexOf("-");
                                if (shipTimestamp.length() - shipTimeIndex == 
                                    6) {
                                    shipTime = 
                                            shipTimestamp.substring(shipDateIndex + 
                                                                    1, 
                                                                    shipTimeIndex);
                                } else {
                                    shipTime = 
                                            shipTimestamp.substring(shipDateIndex + 
                                                                    1, 
                                                                    shipTimestamp.length());
                                }


                                shipSqlDate = 
                                        new Date(simpleTimeFormat.parse(shipTime).getTime());
                                shipTime = timeFormatter.format(shipSqlDate);
                                logger.info("ship date==" + date + " " + time);

                                aascFedexTrackingInfo.setShipDate(shipDate + 
                                                                  " " + 
                                                                  shipTime);

                            } catch (Exception e) {
                                logger.severe("Exception while retrieving ship time: " + 
                                              e.getMessage());
                            }
                        }
                    }


                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/DestinationAddress/City/text()");
                    if (node != null) {
                        city = nullStrToSpc(node.getNodeValue());
                        logger.info("Dest city==" + city);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/DestinationAddress/StateOrProvinceCode/text()");
                    if (node != null) {
                        state = nullStrToSpc(node.getNodeValue());
                        logger.info("Dest state==" + state);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/DestinationAddress/CountryCode/text()");
                    if (node != null) {
                        countryCode = nullStrToSpc(node.getNodeValue());
                        logger.info("Dest countryCode==" + countryCode);
                    }


                    if (city != "" && state != "" && countryCode != "") {
                        location = city + ", " + state + ", " + countryCode;
                    } else {
                        if (countryCode != "") {
                            location = city + ", " + countryCode;
                        }
                    }

                    aascFedexTrackingInfo.setLocation(location);

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/ActualDeliveryTimestamp/text()");
                    if (node != null) {
                        deliveryTimeStamp = nullStrToSpc(node.getNodeValue());
                        logger.info("deliveryTimeStamp==" + deliveryTimeStamp);

                        if (!"".equalsIgnoreCase(deliveryTimeStamp)) {
                            try {
                                destDateIndex = deliveryTimeStamp.indexOf("T");

                                destDate = 
                                        deliveryTimeStamp.substring(0, destDateIndex);
                                destSqlDate = 
                                        new Date(simpleDateFormat.parse(destDate).getTime());
                                destDate = dateFormatter.format(destSqlDate);
                            } catch (Exception e) {
                                logger.severe("Exception while retrieving dest date: " + 
                                              e.getMessage());
                            }

                            try {

                                destTimeIndex = 
                                        deliveryTimeStamp.lastIndexOf("-");
                                if (deliveryTimeStamp.length() - 
                                    destTimeIndex == 6) {
                                    destTime = 
                                            deliveryTimeStamp.substring(destDateIndex + 
                                                                        1, 
                                                                        destTimeIndex);
                                } else {
                                    destTime = 
                                            deliveryTimeStamp.substring(destDateIndex + 
                                                                        1, 
                                                                        deliveryTimeStamp.length());
                                }

                                destSqlDate = 
                                        new Date(simpleTimeFormat.parse(destTime).getTime());
                                destTime = timeFormatter.format(destSqlDate);
                                logger.info("dest date==" + destDate + " " + 
                                            destTime);

                                aascFedexTrackingInfo.setDateTime(destDate + 
                                                                  " " + 
                                                                  destTime);

                            } catch (Exception e) {
                                logger.severe("Exception while retrieving dest time stamp: " + 
                                              e.getMessage());
                            }
                        }
                    }


                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/DeliverySignatureName/text()");
                    if (node != null) {
                        signedForBy = nullStrToSpc(node.getNodeValue());
                        logger.info("signedForby==" + signedForBy);
                        aascFedexTrackingInfo.setSignedForBy(signedForBy);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/ServiceInfo/text()");
                    if (node != null) {
                        serviceType = nullStrToSpc(node.getNodeValue());
                        logger.info("serviceType==" + serviceType);
                        aascFedexTrackingInfo.setServiceType(serviceType);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/PackageWeight/Value/text()");
                    if (node != null) {
                        weightStr = nullStrToSpc(node.getNodeValue());
                        logger.info("weightStr==" + weightStr);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/PackageWeight/Units/text()");
                    if (node != null) {
                        weightUnits = nullStrToSpc(node.getNodeValue());
                        logger.info("weightUnits==" + weightUnits);
                        aascFedexTrackingInfo.setWeightStr(weightStr + " " + 
                                                           weightUnits);
                    }

                    scanNodeLinkedList = new LinkedList();
                    scanNodeList = root.selectNodes("/Envelope/Body/TrackReply/TrackDetails/Events");

                    if (scanNodeList != null) {
                        for (int nodeCount = 0; 
                             nodeCount < scanNodeList.getLength(); 
                             nodeCount++) {
                            aascFedexTrackingScanInfo = 
                                    new AascFedexTrackingInfo();

                            eventTimestamp = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "Timestamp"));
                            logger.info("EventTimestamp==" + eventTimestamp);

                            if (!"".equalsIgnoreCase(eventTimestamp)) {
                                try {
                                    eventDateIndex = 
                                            eventTimestamp.indexOf("T");

                                    evtDate = 
                                            eventTimestamp.substring(0, eventDateIndex);
                                    evtSqlDate = 
                                            new Date(simpleDateFormat.parse(evtDate).getTime());
                                    evtDate = dateFormatter.format(evtSqlDate);
                                    logger.info("Event Date==" + evtDate);
                                    aascFedexTrackingScanInfo.setDate(evtDate);
                                } catch (Exception exc) {
                                    logger.severe("Exception while retrieving event date: " + 
                                                  exc.getMessage());
                                }

                                try {

                                    eventTimeIndex = 
                                            eventTimestamp.lastIndexOf("-");
                                    if (eventTimestamp.length() - 
                                        eventTimeIndex == 6) {
                                        evtTime = 
                                                eventTimestamp.substring(eventDateIndex + 
                                                                         1, 
                                                                         eventTimeIndex);
                                    } else {
                                        evtTime = 
                                                eventTimestamp.substring(eventDateIndex + 
                                                                         1, 
                                                                         eventTimestamp.length());
                                    }
                                    evtSqlDate = 
                                            new Date(simpleTimeFormat.parse(evtTime).getTime());
                                    evtTime = timeFormatter.format(evtSqlDate);
                                    logger.info("Event Time==" + evtTime);
                                    aascFedexTrackingScanInfo.setTime(evtTime);

                                } catch (Exception ex) {
                                    logger.severe("Exception while retrieving event time stamp: " + 
                                                  ex.getMessage());
                                }
                            }


                            city = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "City"));

                            state = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StateOrProvinceCode"));

                            country = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "CountryCode"));

                            location = city + "," + state + "," + country;
                            aascFedexTrackingScanInfo.setLocation(location);

                            scanDescription = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "EventDescription"));
                            aascFedexTrackingScanInfo.setScanDescription(scanDescription);

                            if (nodeCount == 0) {
                                aascFedexTrackingInfo.setStatus(AascXmlParser.getValue((Element)scanNodeList.item(0), "EventDescription"));
                            }

                            statusExceptionCode = AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StatusExceptionCode");
                            if (statusExceptionCode != "") {
                                logger.info("scan status exception code :" + 
                                            statusExceptionCode);

                                statusExceptionDesc = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StatusExceptionDescription"));

                                aascFedexTrackingScanInfo.setStatusExceptionDesc(nullStrToSpc(statusExceptionDesc));

                                logger.info("scan status exception description:" + 
                                            statusExceptionDesc);
                            } // end of if(statusExCode!="") 

                            scanNodeLinkedList.add(aascFedexTrackingScanInfo);
                        } // end of for(int nodeCount = 0; nodeCount < scanNodeList.getLength(); nodeCount++) loop 

                    } // end of if(scanNodeList != null)                                
                    aascFedexTrackingInfo.setScanNodeLinkedList(scanNodeLinkedList);
                    logger.info("after setting header fedex track info and scan node list");
                } // end of if(deliveredLocationCode!=null&& deliveredLocationCode!="")
                else {
                    node = root.selectSingleNode("/Envelope/Body/TrackReply/Notifications/Message/text()");
                    if (node != null) {
                        errorMessage = nullStrToSpc(node.getNodeValue());
                        aascFedexTrackingInfo.setResponseStatus(errorMessage);
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return aascFedexTrackingInfo;
        }
    }


    /** Parses xml response depending on whether actionType is Waybill,
     and sets the parsed data to FedEx tracking bean and returns that bean.    
     @param soapResponse String contains the SOAP response for tracking.   
     @return aascFedexTrackingInfo AascFedexTrackingInfo contains parsed response data.
     */
    public AascFedexTrackingInfo parseWebServiceWaybillResponse(String soapResponse, 
                                                         AascTrackingOrderInfo aascTrackingDeliveryInfo) {
        String severity = "";
        String eventTimestamp = "";
        String pkgCnt = "";

        int eventDateIndex = 0;
        int eventTimeIndex = 0;
        String evtDate = "";
        String evtTime = "";
        Date evtSqlDate = null;

        try {
            aascFedexTrackingInfo = new AascFedexTrackingInfo();

            aascPackagelist = aascTrackingDeliveryInfo.getPackageInfo();

            actionType = aascTrackingDeliveryInfo.getActionType();
            aascFedexTrackingInfo.setActionType(actionType);

            logger.info("\n package count: " + packageCount + 
                        "\n actionType: " + actionType);


            parser = new DOMParser();
            reader = new StringReader(soapResponse);
            input = new InputSource(reader);
            parser.setValidationMode(false);
            parser.showWarnings(true);
            parser.parse(input);
            xmlDocument = parser.getDocument();
            root = (XMLElement)xmlDocument.getDocumentElement();

            if (actionType.equals("WAYBILL") && root != null) {
                logger.info("actionType is WAYBILL");

                node = root.selectSingleNode("/Envelope/Body/TrackReply/HighestSeverity/text()");
                if (node != null) {
                    severity = nullStrToSpc(node.getNodeValue());
                }


                if ("SUCCESS".equalsIgnoreCase(severity)) {
                    logger.info("responseStatus is  success");
                    aascFedexTrackingInfo.setResponseStatus("success");

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/PackageCount/text()");
                    if (node != null) {
                        pkgCnt = nullStrToSpc(node.getNodeValue());
                        logger.info("trackingNumber==" + trackingNumber);
                        if (!"".equalsIgnoreCase(pkgCnt)) {
                            try {
                                aascFedexTrackingInfo.setPackageCount(Integer.parseInt(pkgCnt));
                            } catch (Exception e) {
                                logger.severe("Error while parsing the package count value: " + 
                                              e.getMessage());
                            }
                        }
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/StatusDescription/text()");
                    if (node != null) {
                        status = nullStrToSpc(node.getNodeValue());
                        aascFedexTrackingInfo.setStatus(status);
                    }

                    node = root.selectSingleNode("/Envelope/Body/TrackReply/TrackDetails/ServiceInfo/text()");
                    if (node != null) {
                        serviceType = nullStrToSpc(node.getNodeValue());
                        logger.info("serviceType==" + serviceType);
                        aascFedexTrackingInfo.setServiceType(serviceType);
                    }

                    scanNodeLinkedList = new LinkedList();
                    nodeList = root.selectNodes("/Envelope/Body/TrackReply/TrackDetails");


                    if (nodeList != null) {
                        for (int nodeCountTemp = 0; 
                             nodeCountTemp < nodeList.getLength(); 
                             nodeCountTemp++) {
                            aascFedexTrackingScanInfo = 
                                    new AascFedexTrackingInfo();

                            trackingNumber = 
                                    AascXmlParser.getValue((Element)nodeList.item(nodeCountTemp), 
                                                           "TrackingNumber");
                            aascFedexTrackingScanInfo.setTrackingNumber(trackingNumber);

                            signedForBy = 
                                    AascXmlParser.getValue((Element)nodeList.item(nodeCountTemp), 
                                                           "DeliverySignatureName");
                            aascFedexTrackingScanInfo.setSignedForBy(signedForBy);

                            scanNodeList = 
                                    root.selectNodes("/Envelope/Body/TrackReply/TrackDetails/Events");

                            if (scanNodeList != null) {
                                for (int nodeCount = 0; 
                                     nodeCount < scanNodeList.getLength(); 
                                     nodeCount++) {
                                    eventTimestamp = 
                                            nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), 
                                                                                "Timestamp"));
                                    logger.info("EventTimestamp==" + 
                                                eventTimestamp);

                                    if (!"".equalsIgnoreCase(eventTimestamp)) {
                                        try {
                                            eventDateIndex = 
                                                    eventTimestamp.indexOf("T");

                                            evtDate = 
                                                    eventTimestamp.substring(0, 
                                                                             eventDateIndex);
                                            evtSqlDate = 
                                                    new Date(simpleDateFormat.parse(evtDate).getTime());
                                            evtDate = 
                                                    dateFormatter.format(evtSqlDate);
                                            logger.info("Event Date==" + 
                                                        evtDate);
                                            aascFedexTrackingScanInfo.setDate(evtDate);
                                        } catch (Exception exc) {
                                            logger.severe("Exception while retrieving event date: " + 
                                                          exc.getMessage());
                                        }

                                        try {

                                            eventTimeIndex = 
                                                    eventTimestamp.lastIndexOf("-");
                                            if (eventTimestamp.length() - 
                                                eventTimeIndex == 6) {
                                                evtTime = 
                                                        eventTimestamp.substring(eventDateIndex + 
                                                                                 1, 
                                                                                 eventTimeIndex);
                                            } else {
                                                evtTime = 
                                                        eventTimestamp.substring(eventDateIndex + 
                                                                                 1, 
                                                                                 eventTimestamp.length());
                                            }
                                            evtSqlDate = 
                                                    new Date(simpleTimeFormat.parse(evtTime).getTime());
                                            evtTime = 
                                                    timeFormatter.format(evtSqlDate);
                                            logger.info("Event Time==" + 
                                                        evtTime);
                                            aascFedexTrackingScanInfo.setTime(evtTime);

                                        } catch (Exception ex) {
                                            logger.severe("Exception while retrieving event time stamp" + 
                                                          ex.getMessage());
                                        }
                                    }


                                    city = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "City"));

                                    state = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StateOrProvinceCode"));

                                    country = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "CountryCode"));

                                    location = city + "," + state + "," + country;
                                    aascFedexTrackingScanInfo.setLocation(location);

                                    scanDescription = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "EventDescription"));
                                    aascFedexTrackingScanInfo.setScanDescription(scanDescription);


                                    scanNodeLinkedList.add(aascFedexTrackingScanInfo);
                                } // end of for(int nodeCount = 0; nodeCount < scanNodeList.getLength(); nodeCount++) loop 

                            } // end of if(scanNodeList != null)                                
                            aascFedexTrackingInfo.setScanNodeLinkedList(scanNodeLinkedList);
                        }
                    }
                    logger.info("after setting header fedex track info and scan node list");
                } // end of if(deliveredLocationCode!=null&& deliveredLocationCode!="")
                else {
                    node = root.selectSingleNode("/Envelope/Body/TrackReply/Notifications/Message/text()");
                    if (node != null) {
                        errorMessage = nullStrToSpc(node.getNodeValue());
                        aascFedexTrackingInfo.setResponseStatus(errorMessage);
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return aascFedexTrackingInfo;
        }
    }

    public AascFedexTrackingInfo parseWebServiceSpodResponse(String soapResponse, 
                                                             AascTrackingOrderInfo aascTrackingDeliveryInfo, 
                                                             AascProfileOptionsBean aascProfileOptionsInfo) {
        String severity = "";
        String letter = "";
        String urlPath = "";
        String msg = "";

        try {
            aascFedexTrackingInfo = new AascFedexTrackingInfo();

            actionType = aascTrackingDeliveryInfo.getActionType();
            aascFedexTrackingInfo.setActionType(actionType);

            parser = new DOMParser();
            reader = new StringReader(soapResponse);
            input = new InputSource(reader);
            parser.setValidationMode(false);
            parser.showWarnings(true);
            parser.parse(input);
            xmlDocument = parser.getDocument();
            root = (XMLElement)xmlDocument.getDocumentElement();

            if (actionType.equals("POD") && root != null) {
                logger.info("actionType is POD");

                node = root.selectSingleNode("/Envelope/Body/SignatureProofOfDeliveryLetterReply/HighestSeverity/text()");
                if (node != null) {
                    severity = nullStrToSpc(node.getNodeValue());
                }


                if ("SUCCESS".equalsIgnoreCase(severity)) {
                    logger.info("responseStatus is  success");
                    aascFedexTrackingInfo.setResponseStatus("success");

                    node = root.selectSingleNode("/Envelope/Body/SignatureProofOfDeliveryLetterReply/Letter/text()");
                    logger.info("node==" + node);
                    if (node != null) {
                        letter = nullStrToSpc(node.getNodeValue());
                        logger.info("Letter==" + letter);
                    }

                    if (!"".equalsIgnoreCase(letter)) {
                        decodedLetter = AascBase64.decode(letter);
                        logger.info("decodedLetter==" + decodedLetter);

                        //urlPath = "E:\\Labels\\SPODImage\\";  // Comment it while deploying
                        urlPath = aascProfileOptionsInfo.getUrlPath();
                        try {
                            fileOutputStream = 
                                    new FileOutputStream(urlPath + "fedexpod.png");

                            fileOutputStream.write(decodedLetter);
                            fileOutputStream.close();
                        } catch (Exception exception) {
                            logger.severe("file not found or error in closing the file:" + 
                                          exception.getMessage());
                            aascFedexTrackingInfo.setResponseStatus("file not found or error in closing the file!!!");
                        }
                    } else {
                        aascFedexTrackingInfo.setResponseStatus("Letter Value is null");
                    }
                } else {
                    node = root.selectSingleNode("/Envelope/Body/SignatureProofOfDeliveryLetterReply/Notifications/Message/text()");
                    if (node != null) {
                        msg = nullStrToSpc(node.getNodeValue());
                        aascFedexTrackingInfo.setResponseStatus(msg);
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return aascFedexTrackingInfo;
        }
    }


    /** Parses xml response depending on whether actionType is waybill,
     tracking or pod sets the parsed data to FedEx tracking bean and returns that bean.    
     @param xmlResponse String containing either track or pod response.     
     @param aascTrackingDeliveryInfo AascTrackingDeliveryInfo delivery information.
     object from which header and package information is retreived.
     @return aascFedexTrackingInfo AascFedexTrackingInfo contains parsed response data.
     */
    public AascFedexTrackingInfo parseResponse(String xmlResponse, 
                                               AascTrackingOrderInfo aascTrackingDeliveryInfo, 
                                               AascProfileOptionsBean aascProfileOptionsInfo, 
                                               String carrierMode) {
        try {
            logger.info("Entered into parseResponse method");
            aascFedexTrackingInfo = new AascFedexTrackingInfo();
            aascPackagelist = aascTrackingDeliveryInfo.getPackageInfo();
            packageCount = aascPackagelist.size();
            aascFedexTrackingInfo.setPackageCount(packageCount);
            actionType = aascTrackingDeliveryInfo.getActionType();
            aascFedexTrackingInfo.setActionType(actionType);

            logger.info("\n package count: " + packageCount + 
                        "\n actionType: " + actionType);

            if (actionType != null && !actionType.equals("")) {
                parser = new DOMParser();

                // create InputSource from string containing xml response
                reader = new StringReader(xmlResponse);

                input = new InputSource(reader);


                // Set various parser options: validation off,
                // warnings shown, error stream set to stderr.
                parser.setValidationMode(false);
                parser.showWarnings(true);

                // Parse the document.
                logger.info("before passing input source to parser");
                parser.parse(input);
                logger.info("after passing input source to parser");

                // Obtain the document.
                xmlDocument = parser.getDocument();
                root = (XMLElement)xmlDocument.getDocumentElement();

                if (actionType.equals("POD")) {
                    logger.info("actionType is POD" + carrierMode);

                    letter = AascXmlParser.getValue(root, "Letter");
                    if (letter != null && letter != "") {
                        logger.info("POD letter is not null");
                        aascFedexTrackingInfo.setResponseStatus("success");
                        decodedLetter = 
                                AascBase64.decode(AascXmlParser.getValue(root, 
                                                                         "Letter"));
                        logger.info("decodedLetter==" + decodedLetter);

                        urlPath = aascProfileOptionsInfo.getUrlPath();
                        try {
                            fileOutputStream = 
                                    new FileOutputStream(urlPath + "fedexpod.png");

                            fileOutputStream.write(decodedLetter);
                            fileOutputStream.close();
                        } catch (Exception exception) {
                            logger.severe("file not found or error in closing the file:" + 
                                          exception.getMessage());
                            aascFedexTrackingInfo.setResponseStatus("file not found or error in closing the file!!!");
                        }
                        logger.info("\n pod letter: " + letter + 
                                    "\n decoded pod letter: " + decodedLetter + 
                                    "\n url path from profile options: " + 
                                    urlPath);
                        logger.info("POD is successfully created");
                    } else {
                        logger.info("POD letter is null");
                        errorCode = AascXmlParser.getValue(root, "Code");
                        errorMessage = AascXmlParser.getValue(root, "Message");
                        logger.severe("pod error code:" + errorCode + 
                                      "\n error message:" + errorMessage);
                        aascFedexTrackingInfo.setResponseStatus(errorMessage);
                    }
                    //}
                } // end of if(actionType.equals("POD")) 
                else if (actionType.equals("WAYBILL")) {
                    logger.info("actionType is WAYBILL");

                    responseStatus = 
                            AascXmlParser.getValue(root, "CarrierCode");
                    if (responseStatus != null && !responseStatus.equals("")) {
                        logger.info("responseStatus is not null");
                        aascFedexTrackingInfo.setResponseStatus("success");
                        packageCountString = 
                                AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), 
                                                       "PackageCount");
                        if (packageCountString != null && 
                            !packageCountString.equals("")) {
                            aascFedexTrackingInfo.setPackageCount(Integer.parseInt(packageCountString));
                        }
                        status = 
                                AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), 
                                                       "StatusDescription");
                        aascFedexTrackingInfo.setStatus(status);
                        serviceType = 
                                AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), 
                                                       "Service");
                        aascFedexTrackingInfo.setServiceType(serviceType);

                        logger.info("after successfully retreiving waybill header information" + 
                                    "\n status:" + status + 
                                    "\n service type:" + serviceType + 
                                    "\n packageCount:" + packageCount);
                        scanNodeLinkedList = new LinkedList();
                        nodeList = root.selectNodes("/FDXTrack2Reply/Package");

                        if (nodeList != null) {
                            for (int nodeCountTemp = 0; 
                                 nodeCountTemp < nodeList.getLength(); 
                                 nodeCountTemp++) {
                                aascFedexTrackingScanInfo = 
                                        new AascFedexTrackingInfo();

                                trackingNumber = 
                                        AascXmlParser.getValue((Element)nodeList.item(nodeCountTemp), 
                                                               "TrackingNumber");
                                aascFedexTrackingScanInfo.setTrackingNumber(trackingNumber);
                                signedForBy = 
                                        AascXmlParser.getValue((Element)nodeList.item(nodeCountTemp), 
                                                               "SignedForBy");
                                aascFedexTrackingScanInfo.setSignedForBy(signedForBy);

                                scanNodeList = 
                                        root.selectNodes("/FDXTrack2Reply/Package/Event");

                                if (scanNodeList != null) {
                                    for (int nodeCount = 0; 
                                         nodeCount < scanNodeList.getLength(); 
                                         nodeCount++) {
                                        scanDescription = 
                                                AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), 
                                                                       "Description");
                                        aascFedexTrackingScanInfo.setScanDescription(scanDescription);

                                        date = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "Date"));
                                        sqlDate = 
                                                new Date(simpleDateFormat.parse(date).getTime());
                                        date = dateFormatter.format(sqlDate);
                                        aascFedexTrackingScanInfo.setDate(date);

                                        city = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "City"));

                                        state = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StateOrProvinceCode"));

                                        country = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "CountryCode"));

                                        location = city + "," + state + "," + country;
                                        aascFedexTrackingScanInfo.setLocation(location);

                                        scanNodeLinkedList.add(aascFedexTrackingScanInfo);
                                    } // end of for
                                    aascFedexTrackingInfo.setScanNodeLinkedList(scanNodeLinkedList);
                                } // end of  if (scanNodeList != null)                                     
                            } // end of for                                                        
                        } // end of if (nodeList != null)   
                        logger.info("after successfully retreiving waybill footer information" + 
                                    "\n scanDescription:" + scanDescription + 
                                    "\n date:" + date + "\n city:" + city + 
                                    "\n state:" + state + "\n country:" + 
                                    country + "\n location:" + location);
                    } // end of if(responseStatus != null && !responseStatus.equals(""))   
                    else {
                        logger.info("responseStatus is null");
                        errorCode = AascXmlParser.getValue(root, "Code");
                        if (errorCode != null && errorCode != "") {
                            errorMessage = 
                                    AascXmlParser.getValue(root, "Message");
                            logger.severe("Error!!! :" + errorCode + "---->" + 
                                          errorMessage);
                            aascFedexTrackingInfo.setResponseStatus(errorMessage);
                        } // end of if(errorCode!=null && errorCode!="")                                                  
                    } // end of else
                    logger.info("end of else if(actionType.equals('WAYBILL'))");
                } // end of else if wayBill
                else {
                    if (actionType.equals("TRACKING")) {
                        logger.info("actionType is TRACKING");
                        responseStatus = 
                                AascXmlParser.getValue(root, "CarrierCode");

                        if (responseStatus != null && 
                            !responseStatus.equals("")) {
                            logger.info("responseStatus is not null");
                            aascFedexTrackingInfo.setResponseStatus("success");
                            trackingNumber = 
                                    AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), 
                                                           "TrackingNumber"); // node.getNodeValue();
                            aascFedexTrackingInfo.setTrackingNumber(trackingNumber);

                            if (softErrorType != null && softErrorType != "") {
                                softErrorCode = 
                                        AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package/SoftError"), 
                                                               "Code");
                                softErrorMsg = 
                                        AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package/SoftError"), 
                                                               "Message");
                                logger.info("soft error type :" + 
                                            softErrorType + 
                                            "soft error code :" + 
                                            softErrorCode + 
                                            "soft error message :" + 
                                            softErrorMsg);
                            }

                            carrierCode = AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "CarrierCode"); // node.getNodeValue();                                                                      
                            date = AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "ShipDate"); // node.getNodeValue();
                            sqlDate = new Date(simpleDateFormat.parse(date).getTime());
                            date = dateFormatter.format(sqlDate);
                            aascFedexTrackingInfo.setShipDate(date);

                            city = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package/DestinationAddress"), "City")); // nullStrToSpc(node.getNodeValue());                                                  
                            state = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package/DestinationAddress"), "StateOrProvinceCode")); // nullStrToSpc(node.getNodeValue());                                                    
                            countryCode = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package/DestinationAddress"), "CountryCode")); // nullStrToSpc(node.getNodeValue());                                                    

                            if (city != "" && state != "" && 
                                countryCode != "") {
                                location = 
                                        city + ", " + state + ", " + countryCode;
                            } else {
                                if (countryCode != "") {
                                    location = city + ", " + countryCode;
                                }
                            }

                            aascFedexTrackingInfo.setLocation(location);

                            date = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "DeliveredDate")); // nullStrToSpc(node.getNodeValue());                                                    
                            sqlDate = new Date(simpleDateFormat.parse(date).getTime());
                            date = dateFormatter.format(sqlDate);

                            time = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "DeliveredTime")); // nullStrToSpc(node.getNodeValue());                                                    
                            try {
                                sqlDate = 
                                        new Date(simpleTimeFormat.parse(time).getTime());
                                time = timeFormatter.format(sqlDate);
                            } catch (Exception e) {
                                time = "";
                            }

                            dateTime = date + " " + time;
                            aascFedexTrackingInfo.setDateTime(dateTime);

                            signedForBy = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "SignedForBy")); // nullStrToSpc(node.getNodeValue());                                                    
                            aascFedexTrackingInfo.setSignedForBy(signedForBy);

                            serviceType = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "Service"));
                            aascFedexTrackingInfo.setServiceType(serviceType);
                            weightStr = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "Weight"));
                            weightUnits = nullStrToSpc(AascXmlParser.getValue((XMLElement)root.selectSingleNode("/FDXTrack2Reply/Package"), "WeightUnits"));
                            aascFedexTrackingInfo.setWeightStr(weightStr + " " + weightUnits);

                            logger.info("\n Tracking Number : " + 
                                        trackingNumber + "\t carrier code : " + 
                                        carrierCode + "\t ship date : " + 
                                        date + "\n city : " + city + 
                                        "\t state : " + state + 
                                        "\t country code :" + countryCode + 
                                        "\t destinationAddress : " + location + 
                                        "\n signed by :" + signedForBy + 
                                        "\t serviceType :" + serviceType + 
                                        "\t weight :" + weightStr + 
                                        "\t weight units :" + weightUnits + 
                                        "\t delivered date :" + date + 
                                        "\t delivered time :" + time);

                            scanNodeLinkedList = new LinkedList();
                            scanNodeList = 
                                    root.selectNodes("/FDXTrack2Reply/Package/Event");

                            if (scanNodeList != null) {
                                for (int nodeCount = 0; 
                                     nodeCount < scanNodeList.getLength(); 
                                     nodeCount++) {

                                    aascFedexTrackingScanInfo = 
                                            new AascFedexTrackingInfo();

                                    date = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "Date"));
                                    sqlDate = new Date(simpleDateFormat.parse(date).getTime());
                                    date = dateFormatter.format(sqlDate);
                                    aascFedexTrackingScanInfo.setDate(date);

                                    time = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "Time"));
                                    try {
                                        sqlDate = 
                                                new Date(simpleTimeFormat.parse(time).getTime());

                                        time = timeFormatter.format(sqlDate);
                                    } catch (Exception e) {
                                        time = "";
                                    }
                                    aascFedexTrackingScanInfo.setTime(time);

                                    city = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "City"));

                                    state = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StateOrProvinceCode"));

                                    country = nullStrToSpc(AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "CountryCode"));

                                    location = city + "," + state + "," + country;
                                    aascFedexTrackingScanInfo.setLocation(location);

                                    scanDescription = AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "Description");
                                    aascFedexTrackingScanInfo.setScanDescription(scanDescription);
                                    if (nodeCount == 0) {
                                        aascFedexTrackingInfo.setStatus(AascXmlParser.getValue((Element)scanNodeList.item(0), "Description"));
                                    }
                                    statusExceptionCode = AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StatusExceptionCode");
                                    if (statusExceptionCode != "") {
                                        logger.info("scan status exception code :" + 
                                                    statusExceptionCode);

                                        statusExceptionDesc = AascXmlParser.getValue((Element)scanNodeList.item(nodeCount), "StatusExceptionDescription");

                                        aascFedexTrackingScanInfo.setStatusExceptionDesc(nullStrToSpc(statusExceptionDesc));

                                        logger.info("scan status exception description:" + 
                                                    statusExceptionDesc);
                                    } // end of if(statusExCode!="") 

                                    scanNodeLinkedList.add(aascFedexTrackingScanInfo);
                                } // end of for(int nodeCount = 0; nodeCount < scanNodeList.getLength(); nodeCount++) loop 

                            } // end of if(scanNodeList != null)                                
                            aascFedexTrackingInfo.setScanNodeLinkedList(scanNodeLinkedList);
                            logger.info("after setting header fedex track info and scan node list");
                        } // end of if(deliveredLocationCode!=null&& deliveredLocationCode!="")
                        else {
                            errorCode = AascXmlParser.getValue(root, "Code");
                            if (errorCode != null && errorCode != "") {
                                errorMessage = 
                                        AascXmlParser.getValue(root, "Message");
                                logger.severe("Error!!! :" + errorCode + 
                                              "---->" + errorMessage);
                                aascFedexTrackingInfo.setResponseStatus(errorMessage);
                            } // end of if(errorCode!=null && errorCode!="")                                                  
                        }


                    } // end of if(actionType.equals("WAYBILL")||actionType.equals("TRACKING")) 
                } // end of else of if(actionType.equals("POD"))
            } // end of if(actionType!=null && actionType!="")
            else {
                logger.severe("actionType is NULL");
            } // end of else if(actionType!=null && actionType!="")
        } // end of try
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } // end of catch
        logger.info("Exit from parseResponse()");
        return aascFedexTrackingInfo;
    } // end of method   

    /** Method to get package tracking number.
     @return trackingNumber String package tracking number.
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /** Method to set package tracking number.
     @param trackingNumber String package tracking number.
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /** Method to get signedForBy person name who received the delivery.
     @return signedForBy String signedForBy person name who received the delivery.
     */
    public String getSignedForBy() {
        return signedForBy;
    }

    /** Method to set signedForBy person name who received the delivery.
     @param signedForBy StringsignedForBy person name who received the delivery.
     */
    public void setSignedForBy(String signedForBy) {
        this.signedForBy = signedForBy;
    }

    /** Method to get shipDate of delivery.
     @return date String-shipDate of delivery.
     */
    public String getShipDate() {
        return date;
    }

    /** Method to set shipDate of delivery.
     @param shipDate String-shipDate of delivery.
     */
    public void setShipDate(String shipDate) {
        this.date = shipDate;
    }

    /** Method to get dateTime of delivery.
     @return dateTime String-dateTime of delivery or
     package scan date and time.
     */
    public String getDateTime() {
        return dateTime;
    }

    /** Method to set dateTime of delivery.
     @param dateTime String-dateTime of delivery or 
     package scan date and time.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /** Method to get service type of the delivery.
     @return serviceType String.
     */
    public String getServiceType() {
        return serviceType;
    }

    /** Method to set service type of the delivery.
     @param serviceType String.
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /** Method to get weight string containing weight value and weight units.
     @return weightStr String containing weight value and weight units.
     */
    public String getWeightStr() {
        return weightStr;
    }

    /** Method to set weight string containing weight value and weight units.
     @param weightStr String containing weight value and weight units.
     */
    public void setWeightStr(String weightStr) {
        this.weightStr = weightStr;
    }

    /** Method to get scanDescription of the package.
     @return scanDescription String scanDescription of the package.
     */
    public String getScanDescription() {
        return scanDescription;
    }

    /** Method to set scanDescription of the package.
     @param scanDescription String scanDescription of the package.
     */
    public void setScanDescription(String scanDescription) {
        this.scanDescription = scanDescription;
    }

    /** Method to get location.
     @return location String containing package scan location.
     */
    public String getLocation() {
        return location;
    }

    /** Method to set location.
     @param location String containing package scan location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Method to get scanNodeLinkedList.
     @return scanNodeLinkedList LinkedList linked list containing package scan nodes.
     */
    public LinkedList getScanNodeLinkedList() {
        return scanNodeLinkedList;
    }

    /** Method to set scanNodeLinkedList.
     @param scanNodeLinkedList LinkedList linked list containing package scan nodes.
     */
    public void setScanNodeLinkedList(LinkedList scanNodeLinkedList) {
        this.scanNodeLinkedList = scanNodeLinkedList;
    }

    /** Method to get status of package whether it is delivered to customer or in transit. 
     @return status String indicates package status.
     */
    public String getStatus() {
        return status;
    }

    /** Method to set status of package whether it is delivered to customer or in transit.
     @param status String indicates package status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** Method to get date of package in transit.
     @return date String date of package in transit. 
     */
    public String getDate() {
        return date;
    }

    /** Method to set date of package in transit.
     @param date String date of package in transit. 
     */
    public void setDate(String date) {
        this.date = date;
    }

    /** Method to get time of package in transit.
     @return time String time of package in transit. 
     */
    public String getTime() {
        return time;
    }

    /** Method to set time of package in transit.
     @param time String time of package in transit.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /** Method to get packageCount.
     @return packageCount int indicates total number of packages present in delivery.
     */
    public int getPackageCount() {
        return packageCount;
    }

    /** Method to set packageCount.
     @param packageCount int indicates total number of packages present in delivery. 
     */
    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    /** Method to get ActionType.
     @return actionType String indicates user click on POD TRACKING or WAYBILL button. 
     */
    public String getActionType() {
        return actionType;
    }

    /** Method to set ActionType.
     @param actionType String indicates user click on POD TRACKING or WAYBILL button. 
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /** Method to get package status exception details.
     @return statusExceptionDetails String package status exception details.
     */
    public String getStatusExceptionDesc() {
        return statusExceptionDesc;
    }

    /** Method to set package status exception details.
     @param statusExceptionDetails String package status exception details.
     */
    public void setStatusExceptionDesc(String statusExceptionDetails) {
        this.statusExceptionDesc = statusExceptionDetails;
    }

    /** Method to get responseStatus.
     @return responseStatus String returned in response from FedEx.
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /** Method to set responseStatus.
     @param responseStatus String returned in response from FedEx.
     */
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

}// end of class
