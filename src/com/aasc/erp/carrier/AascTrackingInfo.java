/*
 * @(#)AascTrackingInfo.java        12/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;


/**
 * AascTrackingInfo Class is used to parse xml response 
 * based on actionType-WayBill or Package Tracking Number
 * and sets the parsed data to respective beans.  
 * @author 	Eshwari M
 * @version - 1.0
 * @since 
 * 17-Dec-2014  Eshwari M       Removed Adhoc and Delivery words for SC Lite while testing Tracking feature
 * 16-Jan-2015  Y Pradeep       Modified code to remove unused variables and commented code as suggested in Code Review document and ran self audit.
 * 20-Jan-2015  Y Pradeep       Modified auther and version, also removed commented code.
 * 14-Mar-2015  Eshwari M       Removed unused urlPath
 * 17-Mar-2015  Eshwari M       Modified code to get label path that is placed in session at the time of user login and removed Profile option object
 * 22-Jun-2015  Y Pradeep       Added if conditions to avoid nullpointer exception when SignedForByName node is null.
 */
public class

AascTrackingInfo {
    // for parsing ups xml response    
    private StringReader reader = null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = null; // This object is used to encapsulate character stream into single object
    private DOMParser parser = null; // This object is used to parse xml document 
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private static Logger logger = 
        AascLogger.getLogger("AascTrackingInfo"); // logger object used for logging requests
    private Node node = null; // It represents single node in dom tree
    private NodeList nlActivities = null; // It represents ordered collection of nodes

    private String upsResponse = ""; // string to hold response 
    private String responseStatus = ""; // status that indicates whether the parsing is successful or not
    private String status = ""; // indicates the LastActivityStatus of package progress to the destination
    private String lastActivityStatusCode = ""; // code which represents status
    private String deliveredOn = ""; // indicates date on which delivery is shipped
    private String signedBy = ""; // indicates the customer name who received the delivery   
    private String deliveredTo = ""; // indicates city,state,country to which delivery is shipped
    private String serviceType = ""; // indicates the level at which service is provided   
    private String weightUOM = ""; // package unit of measure
    private String weight = ""; // contains package weight and unit of measure   
    private String actionType = ""; // actionType indicates whether user clicked on waybill or tracking button    
    private String activity; // indicates package activity status    
    private String packageTrackingNumber = ""; // package tracking number    
    private int packageListSize = 0; // indicates total number of packages in that delivery

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd"); // Date format
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hhmmss"); // time format
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
    private Date sqlDate = null; // date object

    private String podHtml = ""; // holds proof of delivery parsed and decode image that is received in pod response
    private String wayBill = ""; // holds waybill number of the delivery
    private String signatureImage = ""; // holds pod signature image
    private byte[] sigBytes; // holds decoded pod signature image
    private FileOutputStream fileOutputStream = null; // to write pod image into a file
    private String urlPath = ""; // holds pod output file path

    private AascTrackingInfo aascTrackingInfo = null; // tracking information bean to hold total tracking information of waybill or package
    private AascTrackingInfo tempTrackingInfo = null; // tracking information bean to hold package progress information or way bill package consolidated information
    private LinkedList linkedList = null; // linkedlist that holds (includes all the activities)total package progress information to destination      
    private ListIterator upsListIterator = null; // iterator used to traverse through the linked list containing list of ups track responses one for each package
    private static HashMap activityStatusMap = new HashMap(); // maps code -> description

    // following variables are used to hold activity details
    private String date = "";
    private String time = "";
    private String location = "";
    private String city = "";
    private String state = "";
    private String country = "";

    // for holding error information
    private String errorSeverity = "";
    private String errorDescription = "";
    private String errorCode = "";

    static {
        // maps code -> description
        activityStatusMap.put("I", "In Transit");
        activityStatusMap.put("D", "Delivered");
        activityStatusMap.put("X", "Exception");
        activityStatusMap.put("P", "Pickup");
        activityStatusMap.put("M", "Manifest Pickup");
    }

    /** default constructor.*/
    public AascTrackingInfo() {
    }

    /**
     * This method can replace the null values with nullString
     * @return String that is ""
     * @param obj object 
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /** method to parse xml response depending on whether response is waybill
     * response or tracking response and sets the parsed data to bean and returns that bean.
     * @param actionType String containing waybill or tracking depending on what information user wants(user clicked)
     * @param upsResponseList LinkedList containing list of responses for waybill packages
     * the list contains only one response when actionType is tracking
     * @param aascOrderInfo order information object from which header and package information is retreived
     * @return aascTrackingBean AascTrackingInfo contains tracking information
     */
    public AascTrackingInfo parseResponse(String actionType, 
                                          LinkedList upsResponseList, 
                                          AascTrackingOrderInfo aascOrderInfo, String labelsPath){
                                          //AascProfileOptionsBean aascProfileOptionsInfo) {
        logger.info("Entered into parseResponse method");
        aascTrackingInfo = new AascTrackingInfo(); // bean which holds total tracking information (i.e)waybillheaderinfo and footer info or package header info and package progress info   
        aascTrackingInfo.setActionType(nullStrToSpc(actionType));

        //aascProfileOptionsInfo = new AascProfileOptionsBean();
        urlPath = labelsPath;
        try {
            if (actionType.equals("POD")) {
                logger.info("actionType is POD");
                upsListIterator = upsResponseList.listIterator();
                upsResponse = (String)upsListIterator.next();
                wayBill = aascOrderInfo.getHeaderInfo().getWayBill();
                parser = new DOMParser();
                // create InputSource from string containing xml response
                reader = new StringReader(upsResponse);

                input = new InputSource(reader);


                // Set various parser options: validation off,
                // warnings shown, error stream set to stderr.
                parser.setValidationMode(false);
                parser.showWarnings(true);

                // Parse the document.

                parser.parse(input);


                // Obtain the document.
                xmlDocument = parser.getDocument();
                root = (XMLElement)xmlDocument.getDocumentElement();
                responseStatus = 
                        AascXmlParser.getValue(root, "ResponseStatusDescription").toLowerCase();
                logger.info("UPS response status:" + responseStatus);

                if (responseStatus != null && 
                    responseStatus.equalsIgnoreCase("success")) {
                    logger.info("responseStatus is not null");
                    aascTrackingInfo.setResponseStatus(responseStatus);
                    // extract POD from xml doc
                    podHtml = 
                            AascBase64.decodeToString(AascXmlParser.getValue(root, 
                                                                             "HTMLImage"));
                    if (podHtml.equals("")) {
                        responseStatus = "POD is not available";
                        aascTrackingInfo.setResponseStatus(responseStatus);
                        logger.info("POD is not available");
                    } // end of if(podHtml.equals(""))
                    else {
                        logger.info("label path:" + urlPath);
                        try {
                            fileOutputStream = 
                                    new FileOutputStream(urlPath + "pod.html");

                            fileOutputStream.write(podHtml.getBytes());
                            fileOutputStream.close();
                            signatureImage = 
                                    AascXmlParser.getValue(root, "GraphicImage");
                            sigBytes = 
                                    AascBase64.decode(AascXmlParser.getValue(root, 
                                                                             "GraphicImage"));
                            fileOutputStream = 
                                    new FileOutputStream(urlPath + "pod_" + 
                                                         wayBill + ".gif");

                            fileOutputStream.write(sigBytes);
                            fileOutputStream.close();
                            logger.info("POD generated successfully");
                        } catch (Exception exception) {
                            logger.severe("file not found exception or error in closing the file output stream" + 
                                          exception.getMessage());
                            aascTrackingInfo.setResponseStatus("File not found exception or error in closing the file output stream");
                        }
                    } // end of else of if(podHtml.equals("")) 
                } // end of  if (responseStatus != null && responseStatus.equalsIgnoreCase("success")) 
                else {
                    logger.info("responseStatus is null");
                    errorSeverity = 
                            AascXmlParser.getValue(root, "ErrorSeverity");
                    errorDescription = 
                            AascXmlParser.getValue(root, "ErrorDescription");
                    errorCode = AascXmlParser.getValue(root, "ErrorCode");
                    aascTrackingInfo.setResponseStatus("\n pod information is not available" + 
                                                       "\n errorDescription: " + 
                                                       errorDescription);
                    logger.info("\n pod information is not available" + 
                                "\n errorCode: " + errorCode + 
                                "\n errorDescription: " + errorDescription + 
                                "\n errorSeverity: " + errorSeverity + 
                                "\n responseStatus: " + responseStatus);
                } // end of else of if (responseStatus != null && responseStatus.equalsIgnoreCase("success"))
            } // end of if (actionType.equals("POD")) 
            else if (actionType.equals("TRACKING")) // TRACKING
            {
                logger.info("actionType is TRACKING");
                linkedList = new LinkedList();
                upsListIterator = upsResponseList.listIterator();
                upsResponse = (String)upsListIterator.next();

                parser = new DOMParser();

                // create InputSource from string containing xml response
                reader = new StringReader(upsResponse);

                input = new InputSource(reader);


                // Set various parser options: validation off,
                // warnings shown, error stream set to stderr.
                parser.setValidationMode(false);
                parser.showWarnings(true);

                // Parse the document.

                parser.parse(input);


                // Obtain the document.
                xmlDocument = parser.getDocument();
                root = (XMLElement)xmlDocument.getDocumentElement();
                responseStatus = 
                        AascXmlParser.getValue(root, "ResponseStatusDescription").toLowerCase();
                logger.info("UPS response status:" + responseStatus);

                if (responseStatus != null && 
                    responseStatus.equalsIgnoreCase("success")) {
                    aascTrackingInfo.setResponseStatus(responseStatus);
                    logger.info("responseStatus is not null");

                    // show details of package progress                     
                    node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/Status/StatusType/Code/text()");
                    lastActivityStatusCode = node.getNodeValue();
                    status = 
                            (String)activityStatusMap.get(lastActivityStatusCode);
                    logger.info("lastActivityStatus of package :" + status);

                    if (status.equalsIgnoreCase("DELIVERED")) {
                        logger.info("last activity status is 'DELIVERED'");
                        aascTrackingInfo.setStatus(nullStrToSpc(status));

                        node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/Date/text()");
                        deliveredOn = node.getNodeValue();

                        sqlDate = new Date(simpleDateFormat.parse(deliveredOn).getTime());
                        deliveredOn = dateFormatter.format(sqlDate);

                        aascTrackingInfo.setDeliveredOn(nullStrToSpc(deliveredOn));
                        aascTrackingInfo.setShippedOn(nullStrToSpc(deliveredOn));

                        node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/SignedForByName/text()");
                        if(node != null){
                            signedBy = node.getNodeValue();
                        } else {
                            signedBy = "";
                        }

                        aascTrackingInfo.setSignedBy(nullStrToSpc(signedBy));

                        node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/City/text()");
                        city = node.getNodeValue().trim();

                        node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/StateProvinceCode/text()");
                        state = node.getNodeValue().trim();

                        node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/CountryCode/text()");
                        country = node.getNodeValue().trim();

                        aascTrackingInfo.setDeliveredTo(nullStrToSpc(city + "," + state + "," + country));
                        logger.info("All activity values are set successfully to aascTrackingInfo bean");
                    } // if(status.equalsIgnoreCase("DELIVERED"))
                    else {
                        logger.info("last activity status is NOT 'DELIVERED'");
                        
                        aascTrackingInfo.setStatus(nullStrToSpc(status));
                        
                        node = root.selectSingleNode("/TrackResponse/Shipment/ShipTo/Address/City/text()");
                        city = node.getNodeValue().trim();
                        node = root.selectSingleNode("/TrackResponse/Shipment/ShipTo/Address/StateProvinceCode/text()");
                        state = node.getNodeValue().trim();
                        node = root.selectSingleNode("/TrackResponse/Shipment/ShipTo/Address/CountryCode/text()");
                        country = node.getNodeValue().trim();
                        aascTrackingInfo.setDeliveredTo(nullStrToSpc(city + 
                                                                     "," + 
                                                                     state + 
                                                                     "," + 
                                                                     country));
                    } // end of else of if(status.equalsIgnoreCase("DELIVERED"))

                    deliveredTo = city + ", " + state + ", " + country;

                    node = root.selectSingleNode("/TrackResponse/Shipment/Package/TrackingNumber/text()");
                    packageTrackingNumber = node.getNodeValue();
                    aascTrackingInfo.setPackageTrackingNumber(nullStrToSpc(packageTrackingNumber));

                    node = root.selectSingleNode("/TrackResponse/Shipment/Service/Description/text()");
                    serviceType = node.getNodeValue();
                    aascTrackingInfo.setServiceType(nullStrToSpc(serviceType));

                    node = root.selectSingleNode("/TrackResponse/Shipment/Package/PackageWeight/Weight/text()");
                    weight = node.getNodeValue().trim();
                    node = root.selectSingleNode("/TrackResponse/Shipment/Package/PackageWeight/UnitOfMeasurement/Code/text()");
                    weightUOM = node.getNodeValue().trim();

                    aascTrackingInfo.setWeight(nullStrToSpc(weight + " " + weightUOM));
                    nlActivities = root.selectNodes("/TrackResponse/Shipment/Package/Activity");

                    if (nlActivities != null) {
                        for (int nlCount = 0; 
                             nlCount < nlActivities.getLength(); nlCount++) {
                            date = nullStrToSpc(AascXmlParser.getValue((Element)nlActivities.item(nlCount),  "Date"));
                            time = nullStrToSpc(AascXmlParser.getValue((Element)nlActivities.item(nlCount), "Time"));
                            city = nullStrToSpc(AascXmlParser.getValue((Element)nlActivities.item(nlCount), "City"));
                            state = nullStrToSpc(AascXmlParser.getValue((Element)nlActivities.item(nlCount), "StateProvinceCode"));
                            country = nullStrToSpc(AascXmlParser.getValue((Element)nlActivities.item(nlCount), "CountryCode"));

                            location = null;

                            if (city != "" && state != "") {
                                location = 
                                        city + ", " + state + ", " + country;
                            } else {
                                location = country;
                            }

                            activity = 
                                    AascXmlParser.getValue((Element)nlActivities.item(nlCount), 
                                                           "Description");


                            sqlDate = 
                                    new Date(simpleDateFormat.parse(date).getTime());
                            date = dateFormatter.format(sqlDate);


                            sqlDate = 
                                    new Date(simpleTimeFormat.parse(time).getTime());
                            time = timeFormatter.format(sqlDate);

                            tempTrackingInfo = new AascTrackingInfo();
                            tempTrackingInfo.setDate(nullStrToSpc(date));
                            tempTrackingInfo.setTime(nullStrToSpc(time));
                            tempTrackingInfo.setLocation(nullStrToSpc(location));
                            tempTrackingInfo.setActivity(nullStrToSpc(activity));
                            linkedList.add(tempTrackingInfo);
                            logger.info("date=" + date + "\t time=" + time + 
                                        "\t location=" + location + 
                                        "\t activity=" + activity);

                        } // end of for(int nlCount=0;nlCount<nlActivities.getLength()-1;nlCount++) loop
                    } // if (nlActivities != null)
                    logger.info("after setting package progress data to bean and adding beans to linked list");
                    aascTrackingInfo.setPackageProgressList(linkedList);
                    logger.info("added the PackageProgressList linked list to bean");

                } // end of if(responseStatus!= null && responseStatus.equalsIgnoreCase("Success"))               
                else {
                    logger.info("responseStatus is null");
                    errorSeverity = 
                            AascXmlParser.getValue(root, "ErrorSeverity");
                    errorDescription = 
                            AascXmlParser.getValue(root, "ErrorDescription");
                    errorCode = AascXmlParser.getValue(root, "ErrorCode");
                    aascTrackingInfo.setResponseStatus("\n Tracking Information is not available" + 
                                                       "\n ErrorDescription: " + 
                                                       errorDescription);
                    logger.info("\n Tracking Information is not available" + 
                                "\n errorCode: " + errorCode + 
                                "\n errorDescription: " + errorDescription + 
                                "\n errorSeverity: " + errorSeverity + 
                                "\n responseStatus: " + responseStatus);
                } // end of else of if(responseStatus!= null && responseStatus.equalsIgnoreCase("Success")) 


            } // end of else if TRACKING
            else {
                if (actionType.equals("WAYBILL")) {
                    logger.info("actionType is WAYBILL");
                    linkedList = new LinkedList();
                    upsListIterator = upsResponseList.listIterator();

                    packageListSize = upsResponseList.size();

                    aascTrackingInfo.setPackageListSize(packageListSize);
                    int packageCount = 0;

                    while (upsListIterator.hasNext()) {
                        logger.info("packageCount==" + packageCount);
                        tempTrackingInfo = new AascTrackingInfo();

                        upsResponse = (String)upsListIterator.next();

                        parser = new DOMParser();

                        // create InputSource from string containing xml response
                        reader = new StringReader(upsResponse);

                        input = new InputSource(reader);


                        // Set various parser options: validation off,
                        // warnings shown, error stream set to stderr.
                        parser.setValidationMode(false);
                        parser.showWarnings(true);

                        // Parse the document.

                        parser.parse(input);


                        // Obtain the document.
                        xmlDocument = parser.getDocument();
                        root = (XMLElement)xmlDocument.getDocumentElement();
                        responseStatus = 
                                AascXmlParser.getValue(root, "ResponseStatusDescription").toLowerCase();
                        logger.info("UPS response status :" + responseStatus);

                        if (responseStatus != null && 
                            responseStatus.equalsIgnoreCase("success")) {
                            aascTrackingInfo.setResponseStatus(responseStatus);
                            logger.info("responseStatus is 'Success'");
                            if (packageCount == 0) {
                                node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/Status/StatusType/Code/text()");
                                lastActivityStatusCode = node.getNodeValue();
                                status = (String)activityStatusMap.get(lastActivityStatusCode);

                                aascTrackingInfo.setStatus(nullStrToSpc(status));

                            } // end of if(packageCount==0)

                            if (status.equalsIgnoreCase("DELIVERED")) {
                                logger.info("status is 'DELIVERED'");
                                tempTrackingInfo.setStatus(nullStrToSpc(status));

                                node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/Date/text()");
                                deliveredOn = node.getNodeValue();

                                sqlDate = new Date(simpleDateFormat.parse(deliveredOn).getTime());

                                deliveredOn = dateFormatter.format(sqlDate);

                                tempTrackingInfo.setDeliveredOn(nullStrToSpc(deliveredOn));

                                node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/SignedForByName/text()");
                                if(node != null){
                                    signedBy = node.getNodeValue();
                                } else{
                                    signedBy = "";
                                }
                                tempTrackingInfo.setSignedBy(nullStrToSpc(signedBy));
                                logger.info("---------deliveredOn-----------:" + deliveredOn + "\t ----signedBy=" + signedBy);

                            } // end of if(status.equalsIgnoreCase("DELIVERED"))---->last activity status

                            node = root.selectSingleNode("/TrackResponse/Shipment/Package/TrackingNumber/text()");
                            packageTrackingNumber = node.getNodeValue();

                            tempTrackingInfo.setPackageTrackingNumber(nullStrToSpc(packageTrackingNumber));

                            node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/City/text()");
                            city = node.getNodeValue().trim();

                            node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/StateProvinceCode/text()");
                            state = node.getNodeValue().trim();

                            node = root.selectSingleNode("/TrackResponse/Shipment/Package/Activity/ActivityLocation/Address/CountryCode/text()");
                            country = node.getNodeValue().trim();

                            deliveredTo = city + ", " + state + ", " + country;

                            tempTrackingInfo.setDeliveredTo(nullStrToSpc(deliveredTo));

                            if (packageCount == 0) {
                                node = root.selectSingleNode("/TrackResponse/Shipment/Service/Description/text()");
                                serviceType = node.getNodeValue();

                                aascTrackingInfo.setServiceType(nullStrToSpc(serviceType));

                            } // end of if(packageCount==0)

                            linkedList.add(tempTrackingInfo);


                        } // if(responseStatus != null && responseStatus.equalsIgnoreCase("Success"))
                        else {
                            logger.info("responseStatus is null");
                            errorSeverity = AascXmlParser.getValue(root, "ErrorSeverity");
                            errorDescription = AascXmlParser.getValue(root, "ErrorDescription");
                            errorCode = AascXmlParser.getValue(root, "ErrorCode");
                            aascTrackingInfo.setResponseStatus("\n WayBill Information is not available" + 
                                                               "\n ErrorDescription: " + 
                                                               errorDescription);
                            logger.info("\n WayBill Information is not available" + 
                                        "\n errorCode: " + errorCode + 
                                        "\n errorDescription: " + 
                                        errorDescription + 
                                        "\n errorSeverity: " + errorSeverity + 
                                        "\n responseStatus: " + 
                                        responseStatus);
                        } // end of else of if(responseStatus != null && responseStatus.equalsIgnoreCase("Success"))
                        packageCount++;
                    } // end of while(upsListIterator.hasNext())                    
                    logger.info("after adding the way bill header info to main bean(aascTrackingInfo)");
                    aascTrackingInfo.setWayBillList(linkedList);

                } // end of if (actionType.equals("WAYBILL"))
            } // end of else     
        } // end of try
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        logger.info("end of parse method");
        return aascTrackingInfo;
    } // end of method   

    /** method to get delivery date.
     * @return date String
     */
    public String getDate() {
        return date;
    }

    /** method to set delivery date. 
     * @param date String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /** method to get delivered time.
     * @return time String
     */
    public String getTime() {
        return time;
    }

    /** method to set delivered time.
     * @param time String
     */
    public void setTime(String time) {
        this.time = time;
    }

    /** method to get location to where the delivery is shipped.
     * @return location String
     */
    public String getLocation() {
        return location;
    }

    /** method to set shipment location. 
     * @param location String
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** method to get package activity status.
     * @return activity String
     */
    public String getActivity() {
        return activity;
    }

    /** method to set package activity status.
     * @param activity String
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /** method to get package progress list containing package progress information.
     * @return packageProgressList LinkedList
     */
    public LinkedList getPackageProgressList() {
        return linkedList;
    }

    /** method to set package progress list containing package progress information.
     * @param packageProgressList LinkedList
     */
    public void setPackageProgressList(LinkedList packageProgressList) {
        this.linkedList = packageProgressList;
    }

    /** method to get deliveredOn date.
     * @return deliveredOn String
     */
    public String getDeliveredOn() {
        return deliveredOn;
    }

    /** method to set date on which delivery is shipped. 
     * @param deliveredOn String
     */
    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    /** method to get signedBy information.
     * @return signedBy String
     */
    public String getSignedBy() {
        return signedBy;
    }

    /** method to set signedBy information .
     * @param signedBy String
     */
    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    /** method to get deliveredTo information.
     * @return deliveredTo String
     */
    public String getDeliveredTo() {
        return deliveredTo;
    }

    /** method to set deliveredTo information.
     * @param deliveredTo String
     */
    public void setDeliveredTo(String deliveredTo) {
        this.deliveredTo = deliveredTo;
    }

    /** method to get package weight. 
     * @return weight String
     */
    public String getWeight() {
        return weight;
    }

    /** method to set package weight.
     * @param weight String
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /** method to get serviceType of delivery. 
     * @return serviceType String
     */
    public String getServiceType() {
        return serviceType;
    }

    /** method to set serviceType of delivery. 
     * @param serviceType String
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /** method to get packageTrackingNumber of delivery. 
     * @return packageTrackingNumber String
     */
    public String getPackageTrackingNumber() {
        return packageTrackingNumber;
    }

    /** method to set packageTrackingNumber.  
     * @param packageTrackingNumber String
     */
    public void setPackageTrackingNumber(String packageTrackingNumber) {
        this.packageTrackingNumber = packageTrackingNumber;
    }

    /** method to get activity status of package. 
     * @return status String
     */
    public String getStatus() {
        return status;
    }

    /** method to set package activity status.  
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** method to get day on which delivery is shipped information.
     * @return deliveredOn String
     */
    public String getShippedOn() {
        return deliveredOn;
    }

    /** method to set shippedOn delivery information. 
     * @param shippedOn String
     */
    public void setShippedOn(String shippedOn) {
        this.deliveredOn = shippedOn;
    }

    /** method to set way bill linked list  to bean.
     * @param wayBillList LinkedList
     */
    public void setWayBillList(LinkedList wayBillList) {
        this.linkedList = wayBillList;
    }

    /** method to get wayBillList containing waybill package information.
     * @return wayBillList LinkedList
     */
    public LinkedList getWayBillList() {
        return linkedList;
    }

    /** method to get total number of packages in waybill.
     * @return packageListSize AascTrackingInfo
     */
    public int getPackageListSize() {
        return packageListSize;
    }

    /** method to set packageListSize.  
     * @param packageListSize int
     */
    public void setPackageListSize(int packageListSize) {
        this.packageListSize = packageListSize;
    }

    /** method to get actionType.
     * @return actionType String
     */
    public String getActionType() {
        return actionType;
    }

    /** method to set actionType to waybill,tracking or proof of delivery.
     * @param actionType String
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /** method to get responseStatus.
     * @return responseStatus String
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /** method to set responseStatus.
     * @param responseStatus String
     */
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}// end of class
