/*
 * @(#)AascFedexVoidInfo.java        27/01/2015
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.util.AascLogger;

import java.io.StringReader;

import java.util.logging.Logger;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import org.xml.sax.InputSource;


/**
 * AascFedexVoidInfo Class is used to parse the fedex 
 * void response received from fedex atom server.
 * @author 	Sunanda Kondapalli
 * @version - 1
 * History
 * 23/03/2015   Sunanda K    Added method level documentation for code Review fix
 */
public class AascFedexVoidInfo {
    private static Logger logger = 
        AascLogger.getLogger("AascFedexVoidInfo"); // Logger object for issuing logging requests
    private String displayMessage = 
        ""; // String containing status of parsing the response    
    private StringReader reader = 
        null; // String reader object which takes String as input and converts it into character stream 
    private InputSource input = 
        null; // This object is used to encapsulate character stream into single object
    private DOMParser parser = 
        null; // This object is used to parse xml document 
    private XMLDocument xmlDocument = null; // This encapsulates xml document
    private XMLElement root = null; // This encapsulates xml element 
    private String errorCode = 
        ""; // holds error code that is generated if the response is failed from fedex server   
    private String errorMessage = 
        ""; // hold the error message that is generated if the response is failed from fedex server
    private String customerTransactionId = 
        ""; // customer transaction id which is retreived from response

    /** default constructor.*/
    public AascFedexVoidInfo() {
    }

    /** parseResponse method is used to parse the xml fedex void response and 
     * set the parsed data to corresponding headers and package bean.
     * @param voidResponse String containing voidResponse which needs to be parsed     
     * @return responseStatus String which contains status of parsing
     */
    String parseResponse(String voidResponse) { // String voidResponse, AascDeliveryInfo aascDeliveryInfo) {
        logger.info("Entered parseResponse()");
        try {
            // Get an instance of the parser
            parser = new DOMParser();

            // create InputSource from string containing xml response
            reader = new StringReader(voidResponse);

            input = new InputSource(reader);

            // Set various parser options: validation off, warnings shown
            parser.showWarnings(true);

            // Parse the document.
            logger.info("before passing the xml document to the parser");
            parser.parse(input);
            logger.info("after passing the xml document to the parser");

            // Obtain the document.
            xmlDocument = parser.getDocument();

            // Print or extract document fields(root element)
            root = (XMLElement)xmlDocument.getDocumentElement();
            errorCode = AascXmlParser.getValue(root, "Code");

            if (errorCode.equals("")) { // parsing the reponse if there are no errors                  
                logger.info("VOID SUCCESSFULLY DONE!");
                customerTransactionId = 
                        AascXmlParser.getValue(root, "CustomerTransactionIdentifier");
                logger.info("customer transaction identifier: " + 
                            customerTransactionId);
                displayMessage = "success";
            } else {
                if (errorCode != null && !errorCode.equals("")) {
                    customerTransactionId = 
                            AascXmlParser.getValue(root, "CustomerTransactionIdentifier");
                    errorMessage = AascXmlParser.getValue(root, "Message");
                    logger.severe("Got an Exception when voiding");
                    logger.severe("error Message: " + errorMessage + 
                                  "\n error code: " + errorCode + 
                                  "\n customer transaction identifier: " + 
                                  customerTransactionId);

                    displayMessage = errorCode + " " + errorMessage;
                }

            } // end of else of if((errorCode==null||errorCode=="")||(errorCodeWR==null||errorCodeWR=="")) 

        } // end of try
        catch (Exception exception) {
            displayMessage = "Error in Fedex void info " + exception;
            logger.severe("Error in Fedex void info " + 
                          exception.getMessage());
        }
        logger.info("Exit from parseResponse()");
        return displayMessage;
    } // end of method

    /** This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj of type Object
     */
    String nullStrToSpc(String obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    } // end o method
    
    /** parseSoapResponse method is used to parse the xml fedex void response 
    * @param voidResponse String containing voidResponse which needs to be parsed     
    * @return displayMessage String which contains status of parsing
    */

    String parseSoapResponse(String voidResponse) { // String voidResponse, AascDeliveryInfo aascDeliveryInfo) {
        String severity = "";
        logger.info("Entered parseSoapResponse()");
        try {
            // Get an instance of the parser
            parser = new DOMParser();

            // create InputSource from string containing xml response
            reader = new StringReader(voidResponse);

            input = new InputSource(reader);

            // Set various parser options: validation off, warnings shown
            parser.showWarnings(true);

            // Parse the document.
            logger.info("before passing the xml document to the parser");
            parser.parse(input);
            logger.info("after passing the xml document to the parser");

            // Obtain the document.
            xmlDocument = parser.getDocument();

            // Print or extract document fields(root element)
            root = (XMLElement)xmlDocument.getDocumentElement();
            errorCode = AascXmlParser.getValue(root, "Code");
            logger.info("errorCode  ::" + errorCode);
            severity = AascXmlParser.getValue(root, "Severity");
            logger.info("Severity " + 
                        AascXmlParser.getValue(root, "Severity"));
            if (severity.equalsIgnoreCase("SUCCESS")) { // parsing the reponse if there are no errors                  
                logger.info("VOID SUCCESSFULLY DONE!");
                customerTransactionId = 
                        AascXmlParser.getValue(root, "CustomerTransactionId");
                logger.info("customer transaction identifier: " + 
                            customerTransactionId);
                displayMessage = "success";
            } else {
                if (errorCode != null && !errorCode.equals("")) {
                    customerTransactionId = 
                            AascXmlParser.getValue(root, "CustomerTransactionId");
                    errorMessage = AascXmlParser.getValue(root, "Message");
                    logger.severe("Got an Exception when voiding");
                    logger.severe("error Message: " + errorMessage + 
                                  "\n error code: " + errorCode + 
                                  "\n customer transaction identifier: " + 
                                  customerTransactionId);

                    displayMessage = errorCode + " " + errorMessage;
                }

            } // end of else of if((errorCode==null||errorCode=="")||(errorCodeWR==null||errorCodeWR=="")) 

        } // end of try
        catch (Exception exception) {
            displayMessage = "Error in Fedex void info " + exception;
            logger.severe("Error in Fedex void info " + 
                          exception.getMessage());
        }
        logger.info("Exit from parseResponse()");
        return displayMessage;
    } // end of method

    /*  public static void main(String[] a) {
        String str =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><soapenv:Body><v9:ShipmentReply xmlns:v9=\"http://fedex.com/ws/ship/v9\"><v9:HighestSeverity xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">SUCCESS</v9:HighestSeverity><v9:Notifications xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:Severity>SUCCESS</v9:Severity><v9:Source>ship</v9:Source><v9:Code>0000</v9:Code><v9:Message>Success</v9:Message><v9:LocalizedMessage>Success</v9:LocalizedMessage></v9:Notifications><q0:TransactionDetail xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:q0=\"http://fedex.com/ws/ship/v9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><q0:CustomerTransactionId>183190</q0:CustomerTransactionId></q0:TransactionDetail><q0:Version xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:q0=\"http://fedex.com/ws/ship/v9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><q0:ServiceId>ship</q0:ServiceId><q0:Major>9</q0:Major><q0:Intermediate>0</q0:Intermediate><q0:Minor>0</q0:Minor></q0:Version></v9:ShipmentReply></soapenv:Body></soapenv:Envelope>";
        AascFedexVoidInfo obj = new AascFedexVoidInfo();
        str =
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><soapenv:Body><v9:ShipmentReply xmlns:v9=\"http://fedex.com/ws/ship/v9\"><v9:HighestSeverity xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">NOTE</v9:HighestSeverity><v9:Notifications xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><v9:Severity>NOTE</v9:Severity><v9:Source>ship</v9:Source><v9:Code>8159</v9:Code><v9:Message>Shipment Delete was requested for a tracking number already in a deleted state.</v9:Message><v9:LocalizedMessage>Shipment Delete was requested for a tracking number already in a deleted state.</v9:LocalizedMessage></v9:Notifications><q0:TransactionDetail xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:q0=\"http://fedex.com/ws/ship/v9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><q0:CustomerTransactionId>183190</q0:CustomerTransactionId></q0:TransactionDetail><q0:Version xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:q0=\"http://fedex.com/ws/ship/v9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><q0:ServiceId>ship</q0:ServiceId><q0:Major>9</q0:Major><q0:Intermediate>0</q0:Intermediate><q0:Minor>0</q0:Minor></q0:Version></v9:ShipmentReply></soapenv:Body></soapenv:Envelope>";
        String res = obj.parseSoapResponse(str);
        //logger.info("res :: " + res);
    }*/

}// end of class
