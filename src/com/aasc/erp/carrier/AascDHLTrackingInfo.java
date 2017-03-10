/*
 * @(#)AascDHLTrackingInfo.java
 * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascTrackingOrderInfo;

import com.aasc.erp.util.AascLogger;

import java.io.StringReader;

import java.util.LinkedList;

import java.util.logging.Logger;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.w3c.dom.Element;

/**
 Parses xml track response and places the parse data in tracking information bean. 
 @author G S Shekar	
 @version 1
 @since
 *
 */
 
public class AascDHLTrackingInfo {
    
    
    
    
    
    
    public AascDHLTrackingInfo() {
    }

    private static Logger logger = AascLogger.getLogger("AascDHLTrackingInfo"); // Logger object for issuing logging requests
    private DOMParser parser = null;
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private Node node = null; // It represents single node in dom tree
    private StringReader reader = null;
    private InputSource input = null;
    
    /** Parses xml response depending on whether actionType is Tracking,
     and sets the parsed data to FedEx tracking bean and returns that bean.    
     @param soapResponse String contains the SOAP response for tracking.   
     @return aascFedexTrackingInfo AascFedexTrackingInfo contains parsed response data.
     */
    public AascDHLTrackingInfo parseWebServiceTrackResponse(String soapResponse, 
                                                              AascDHLTrackingInfo aascDHLTrackingInfo) {
        
         aascDHLTrackingInfo = new AascDHLTrackingInfo();
        String responseStatus = "";
        String date = "";
        String time = "";
        String signatory = "";
        String seviceEventDesc = "";
        String serviceAreaDesc = "";
        String furtherDetails = "";
        String nextSteps = "";
        
        
        soapResponse = soapResponse.replaceAll("<req:","<");
        soapResponse = soapResponse.replaceAll("</req:","</");
        
        try {
        parser = new DOMParser();
        
        // create InputSource from string containing xml response
        reader = new StringReader(soapResponse);
        input = new InputSource(reader);
        
        // Set various parser options: validation off, warnings shown
        parser.setValidationMode(false);
        parser.showWarnings(true);
        
        // Parse the document.
        logger.info("before passing the xml document to the parser");
        parser.parse(input);
        logger.info("after passing the xml document to the parser");
        
        // Obtain the document.
        xmlDocument = parser.getDocument();

        // Print or extract document fields
        root = (XMLElement)xmlDocument.getDocumentElement();
        
        responseStatus = (AascXmlParser.getValue(root, "ActionStatus"));
        
        logger.info("parsed xml responseStatus:" + responseStatus);    
        if (responseStatus != null && responseStatus != "" && 
            "success".equalsIgnoreCase(responseStatus)) {
            
            aascDHLTrackingInfo.setResponseStatus(responseStatus);
            
            LinkedList trackLinkedList = new LinkedList();
            
            NodeList shipmentEventList = root.selectNodes("/TrackingResponse/AWBInfo/ShipmentInfo/ShipmentEvent");
            logger.info("shipmentEventList length= " + shipmentEventList.getLength());
            if(shipmentEventList.getLength()!=0){
                for(int i=0;i<shipmentEventList.getLength();i++) {
                
                    AascDHLTrackingInfo aascDHLTrackingInfoTemp = new AascDHLTrackingInfo();
                    date = (String)(AascXmlParser.getValue((Element)shipmentEventList.item(i), "Date"));
                    aascDHLTrackingInfoTemp.setDate(date);
                                   
                    time = (AascXmlParser.getValue((Element)shipmentEventList.item(i), "Time"));
                    aascDHLTrackingInfoTemp.setTimeStamp(time);
                                        
                    signatory = (AascXmlParser.getValue((Element)shipmentEventList.item(i), "Signatory"));
                    aascDHLTrackingInfoTemp.setSignatory(signatory);
                                        
                    NodeList shipmentEvent = (shipmentEventList.item(i).getChildNodes());
                    for(int j=0;j<shipmentEvent.getLength();j++){
                        if("ServiceEvent".equalsIgnoreCase(shipmentEvent.item(j).getNodeName())){
                            seviceEventDesc = (AascXmlParser.getValue((Element)shipmentEvent.item(j), "Description"));
                            aascDHLTrackingInfoTemp.setServiceEventDesc(seviceEventDesc);
                            
                        }
                        
                        if("ServiceArea".equalsIgnoreCase(shipmentEvent.item(j).getNodeName())){
                            serviceAreaDesc = (AascXmlParser.getValue((Element)shipmentEvent.item(j), "Description"));
                            aascDHLTrackingInfoTemp.setServiceAreaDesc(serviceAreaDesc);
                            
                        }
                        
                        if("EventRemarks".equalsIgnoreCase(shipmentEvent.item(j).getNodeName())){
                            furtherDetails = (AascXmlParser.getValue((Element)shipmentEvent.item(j), "FurtherDetails"));
                            nextSteps = (AascXmlParser.getValue((Element)shipmentEvent.item(j), "NextSteps"));
                            aascDHLTrackingInfoTemp.setFurtherDetails(furtherDetails);
                            aascDHLTrackingInfoTemp.setNextSteps(nextSteps);
                            
                        }
                    }
            
                    trackLinkedList.add(aascDHLTrackingInfoTemp);
                }
            }
            aascDHLTrackingInfo.setTrackingInfoList(trackLinkedList);
        }
        else {
            responseStatus = (AascXmlParser.getValue(root, "ConditionData"));
            aascDHLTrackingInfo.setResponseStatus(responseStatus);
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return aascDHLTrackingInfo;
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
    String ResponseStatus="";
    LinkedList trackingInfoList;
    
    private String date = "";
    private String timeStamp = "";
    private String serviceEventDesc = "";
    private String serviceAreaDesc = "";
    private String furtherDetails = "";
    private String nextSteps = "";
    private String signatory = "";

    public void setResponseStatus(String responseStatus) {
        this.ResponseStatus = responseStatus;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setTrackingInfoList(LinkedList trackingInfoList) {
        this.trackingInfoList = trackingInfoList;
    }

    public LinkedList getTrackingInfoList() {
        return trackingInfoList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setServiceEventDesc(String serviceEventDesc) {
        this.serviceEventDesc = serviceEventDesc;
    }

    public String getServiceEventDesc() {
        return serviceEventDesc;
    }

    public void setServiceAreaDesc(String serviceAreaDesc) {
        this.serviceAreaDesc = serviceAreaDesc;
    }

    public String getServiceAreaDesc() {
        return serviceAreaDesc;
    }

    public void setFurtherDetails(String furtherDetails) {
        this.furtherDetails = furtherDetails;
    }

    public String getFurtherDetails() {
        return furtherDetails;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setSignatory(String signatory) {
        this.signatory = signatory;
    }

    public String getSignatory() {
        return signatory;
    }
}
