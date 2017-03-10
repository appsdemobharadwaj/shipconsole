package com.aasc.erp.carrier;
/*
 * @(#)AascAddressValidationInfo.java     24/02/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */
import com.aasc.erp.bean.AascAddressValidationBean;
import com.aasc.erp.util.AascLogger;

import java.io.IOException;
import java.io.StringReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import oracle.xml.parser.v2.DOMParser;
 import org.w3c.dom.Element;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import oracle.xml.parser.v2.XSLException;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * AascAddressValidationInfo class is action class for Address Validation.
 * @version   1
 * @author    Y Pradeep
 * History
 * 
 * 24-Feb-2015  Y Pradeep       Added this file for Address Validation.
 * 26-Feb-2015  Y Pradeep       Added code to parse response accordingly.
 * 27-Feb-2015  Y Pradeep       Replace printstacktrace with getmessage in error handling.
 * 16-Mar-2015  Y Pradeep       Added a condition while parsing response.
 * 23-Mar-2015  Y Pradeep       Modified code to display proper mesage. Bug #2702
 */

public class AascAddressValidationInfo {

    private static Logger logger = AascLogger.getLogger("AascAddressValidationInfo"); // Logger object for issuing logging requests
    private DOMParser parser = null; // This object is used to parse xml document 
    private StringReader reader = null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = null; // This object is used to encapsulate character stream into single object
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private String addressTypeStr = "";
    private String addressClassificationStr = "";
    private String descriptionStatusStr = "" ;

    
    public AascAddressValidationInfo() {
    }

    /** This method takes response from Web Service of type String and parse this response to get required details to display in Shipping page.
     * @param avResponse
     * @return object of type HashMap
     */
    public HashMap parseWebServiceResponse(String avResponse){
        logger.info("Entered parseWebServiceResponse for AddressValidation");
        List addressList = new LinkedList();
        HashMap addressHashMap = new HashMap();
        try{
            avResponse = avResponse.replaceAll("<S:Envelope", "<Envelope");
            avResponse = avResponse.replaceAll("</S:Envelope", "</Envelope");
            avResponse = avResponse.replaceAll("<S:Body", "<Body");
            avResponse = avResponse.replaceAll("</S:Body", "</Body");
            avResponse = avResponse.replaceAll("<ns2:", "<");
            avResponse = avResponse.replaceAll("</ns2:", "</");
            // Get an instance of the parser
            parser = new DOMParser();
    
            // create InputSource from string containing xml response
            reader = new StringReader(avResponse);
    
            input = new InputSource(reader);
    
            // Set various parser options: validation off, warnings shown
            parser.setValidationMode(false);
            parser.showWarnings(true);
    
            // Parse the document.
            parser.parse(input);
    
            // Obtain the document.
            xmlDocument = parser.getDocument();
    
            // Print or extract document fields
            root = (XMLElement)xmlDocument.getDocumentElement();

            String severity = "";
            try {
                severity = AascXmlParser.getValue(root, "Severity");
            } catch (Exception ex) {
                logger.severe("error getting severity: " + ex.getMessage());
            }

            try {
                Node descriptionStatus = root.selectSingleNode("/Envelope/Body/validateAddressResponse/return/responseStatus/description/text()");
                if (descriptionStatus != null && !descriptionStatus.equals("")) {
                    descriptionStatusStr = (descriptionStatus.getNodeValue()).toUpperCase();
                }
                logger.info("descriptionStatusStr === "+descriptionStatusStr);
            } catch (XSLException e) {
                logger.info("Got exception in address validation info carrier class at descriptionStatus node  = "+e.getMessage());
            }
            addressHashMap.put("descriptionStatusStr", descriptionStatusStr);
            
            if("Success".equalsIgnoreCase(descriptionStatusStr)){
                try {
                    Node addressClassification = root.selectSingleNode("/Envelope/Body/validateAddressResponse/return/addressClassification/text()");
                    if (addressClassification != null && !addressClassification.equals("")) {
                        addressClassificationStr = (addressClassification.getNodeValue()).toUpperCase();
                    }
                    logger.info("addressClassificationStr === "+addressClassificationStr);
                } catch (XSLException e) {
                    logger.info("Got exception in address validation info carrier class at addressClassification node = "+e.getMessage());
                }
                addressHashMap.put("addressClassificationStr",addressClassificationStr);
                
                try {
                    Node addressType = root.selectSingleNode("/Envelope/Body/validateAddressResponse/return/addressType/text()");
                    if (addressType != null && !addressType.equals("")) {
                        addressTypeStr = (addressType.getNodeValue()).toUpperCase();
                        if("UNKNOWN".equalsIgnoreCase(addressTypeStr)) {
                            addressTypeStr = "UNKNOWN ADDRESS";
                        }
                        else if("NO_CHANGES".equalsIgnoreCase(addressTypeStr)) {
                            addressTypeStr = "NO CHANGES REQUIRED";
                        }
                    }
                    logger.info("addressTypeStr === "+addressTypeStr);
                } catch (XSLException e) {
                    logger.info("Got exception in address validation info carrier class at addressType node = "+e.getMessage());
                }
                addressHashMap.put("addressTypeStr", addressTypeStr);
                
                if(("UNKNOWN".equalsIgnoreCase(addressClassificationStr) || "UNDETERMINED".equalsIgnoreCase(addressClassificationStr) || "BUSINESS".equalsIgnoreCase(addressClassificationStr) 
                    || "RESIDENTIAL".equalsIgnoreCase(addressClassificationStr) || "COMMERCIAL".equalsIgnoreCase(addressClassificationStr)) 
                        && (!"UNKNOWN ADDRESS".equalsIgnoreCase(addressTypeStr) && !"INSUFFICIENT_DATA".equalsIgnoreCase(addressTypeStr) && !"NO CHANGES REQUIRED".equalsIgnoreCase(addressTypeStr) && !"VALID ADDRESS".equalsIgnoreCase(addressTypeStr))){
                    try {
                        Node proposedAddressesNode = root.selectSingleNode("/Envelope/Body/validateAddressResponse/return/proposedAddresses");
                        String addressLine = "";
                        String city = "";
                        String state = "";
                        String postalCode = "";
                        String countryCode = "";
                        NodeList proposedAddressesList = proposedAddressesNode.getChildNodes();
                        for(int i = 0; i < proposedAddressesList.getLength(); i++){
                            AascAddressValidationBean aascAddressValidationBean = null;
                            Node addressChildNode = proposedAddressesList.item(i);
                            if ("address".equalsIgnoreCase(addressChildNode.getNodeName())) {
                                aascAddressValidationBean = new AascAddressValidationBean();
                                addressLine = AascXmlParser.getValue((Element)proposedAddressesList.item(i), "addressLine");
                                city = AascXmlParser.getValue((Element)proposedAddressesList.item(i), "city");
                                state = AascXmlParser.getValue((Element)proposedAddressesList.item(i), "state");
                                postalCode = AascXmlParser.getValue((Element)proposedAddressesList.item(i), "postalCode");
                                countryCode = AascXmlParser.getValue((Element)proposedAddressesList.item(i), "countryCode");
                                
                                aascAddressValidationBean.setResponseAddress(addressLine);
                                aascAddressValidationBean.setResponseCity(city);
                                aascAddressValidationBean.setResponseState(state);
                                aascAddressValidationBean.setResponsePostalCode(postalCode);
                                aascAddressValidationBean.setResponseCountryCode(countryCode);
                                
                                addressList.add(aascAddressValidationBean);
                            }
                        }
                        addressHashMap.put("addressList", (Object)addressList);
                    } catch (XSLException e) {
                        logger.info("Got exception in address validation info carrier class at proposedAddressesNode node = "+e.getMessage());
                    }
                }
            }            
        } catch (SAXException e) {
            logger.info("Got exception in address validation info carrier class at SAXException = "+e.getMessage());
        } catch (IOException e) {
            logger.info("Got exception in address validation info carrier class at IOException = "+e.getMessage());
        } catch (Exception e) {
            logger.info("Got exception in address validation info carrier class at XSLException = "+e.getMessage());
        }
        
        return addressHashMap;  
    }
}
