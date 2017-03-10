/*
 * @(#)AascCarrierShipment.java        05/01/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.carrier.AascCarrierShipmentInfo;
import com.aasc.erp.carrier.AascXmlTransmitter;
import com.aasc.erp.util.AascLogger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.net.HttpURLConnection;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;


/* TO DO:
 * -----
 * hard coded intl description
 */

/**
 * AascCarrierShipment Class contains createShipmentRequest()
 * which is used for creating xml shipment request which is sent 
 * to ConnectShip server of UPS through AascXmlTransmitter Class. 
 * @author 	Vandana Gangisetty
 * @version - 2	
 * @since 
 *          -07/03/2006 Vandana Gangisetty got the protocol value from getProtocol()
 *                                         instead of getCarrierPort()
 *          -15/03/2006 Vandana Gangisetty removed hardcoding of shipperTerm   
 *          -21/03/2006 Vandana Gangisetty added code to write request and response to a file
 *          -07/04/2006 Vandana Gangisetty added code for international shipments
 *                                        (added instance variables to hold intl package details,
 *                                         added intl tags in pkgStr method and getShipmentPkgStr
 *                                         and changed pkgStr and getShipmentPkgStr method signature 
 *                                         to include intl parameters) 
 *         -10/04/2006 Vandana Gangisetty added code to send contact name and phone number in international 
 *                                        shipment request 
 *         -19/04/2006 Vandana Gangisetty added code to send contact name and phone number in international 
 *                                        shipment request when freight payment term is "freight collect"                                
 *         -24/04/2006 Vandana Gangisetty added code to handle file not found exception.Added profile options 
 *                                        object in shipment createShipmentRequest method signature.  
 *         -03/05/2006 Vandana Gangisetty added code to retreive printer details(printer port,stock symbol
 *                                        and model symbol) from header bean.and if they are not available in 
 *                                        header bean they are retreived from ship method bean.
 *         -18/12/2014 Eshwari M          Renamed adhoc names with shipment for SC Lite                               
 */
public class

AascCarrierShipment {
    private AascShipmentHeaderInfo aascShipmentHeaderInfo = 
        null; // order header information object 
    private AascShipmentPackageInfo aascShipmentPackageInfo = 
        null; // order package information bean
    
    private LinkedList linkedList = 
         null; // linked list containing delivery or order packages information objects
    private ListIterator listIterator = 
         null; // list iterator used to traverse through packages linked list
         
    /*private AascHeaderInfo aascShipmentHeaderInfo = null; // delivery header information object    
    private AascPackageInfo aascShipmentPackageInfo =null;  */// delivery package information bean
    
    private String shipmentResponse = 
        ""; // String Containg the shipment response xml file
    private StringBuffer shipmentRequest = 
        null; // String containing Shipment Request In XML format  
    private String parsedStatus = ""; // indicates the status of parsing
    private static Logger logger = 
        AascLogger.getLogger("AascCarrierShipment"); // logger object used for issuing logging requests
    private AascCarrierShipmentInfo aascCarrierShipmentInfo = 
        null; // carrier shipment bean class object for parsing the response
    private String errorMessage = ""; // holds the error in failed response  
    private int responseStatus = 
        0; // holds value of 150 if valid response is returned and is successfully parsed else it holds 151
    private String outputFilePath = 
        ""; // holds folder name to which request and response are written 
    private AascXmlTransmitter aascXmlTransmitter = 
        null; // creates AascXmlTransmitter Object to which the request is sent for processing 
    private AascConnectShipConnection aascConnectShipConnection = 
        null; // object to create connection to connectShip
    private HttpURLConnection connection = 
        null; // url connection with support for http specific features
    private String protocol = 
        ""; // ="http";                                        //protocol used to communicate with connectShip
    private String hostName = 
        ""; // ="63.77.35.115";                                //host name of system which receives the request 
    private String prefix = 
        ""; // ="Progistics/XML_Processor/Server/XMLProcDLL.asp";//page which receives processes request
    private String userName = 
        ""; // ="";                                            //username to obtain authorised access to connectShip
    private String password = 
        ""; // ="";                                            //password to obtain authorised access to connectShip

    private int numPackages; // indicates number of packages for which we require tracking numbers
    private String reference1 = ""; // indicates customer purchase order number
    private String shipToCompanyName = ""; // indicates ship to company name(these details are required to be sent to connectship for calculating shipment cost)
    private String shipToAddressLine1 = ""; // indicates ship to address   
    private String shipToAddressLine2 = ""; // indicates ship to address
    private String shipToAddressLine3 = ""; // indicates ship to address   
    private String shipToAddressCity = ""; // indicates ship to address city
    private String shipToAddressPostalCode = ""; // indicates ship to postal code
    private String shipToCountry = ""; // indicates ship to country
    private String shipToAddressState = ""; // indicates ship to state address
    private java.sql.Date shipDate = null; // indicates ship date
    private String shipperName = ""; // indicates clients location name
    //private String printerName = ""; // indicates printer name
    private String printerPort = ""; // indicates printer port
    private String stockSymbol = ""; // indicates stock symbol
    private String printerModelSymbol = ""; // indicates printer model symbol
    private String shipMethodName = ""; // indicates ship method name
    private String shipperTerm = ""; // indicates the terms on which the shipping is prioritised
    private String connectshipSCS = ""; // indicates connectship scs tag
    private String customerAccountNumber = ""; // indicates customer account number
    private String phoneNumber = ""; // indicates customer phone number
    private String carrierPayMethod = ""; // indicates carrier payment terms
    private String carrierPayMethodCode =  ""; // indicates code of carrier payment terms
    private String satShipFlag = ""; // flag which indicates whether the delivery is shipped on saturday or not
    private int carrierId = 0; // indicates carrier id
    private String reference123 = ""; // added by swapna soni    
    private String shipmentRequestHdr = ""; // string that holds xml shipment request header
    private String shipmentRequestFooter = ""; // holds request footer information 
    private float pkgWtVal; // holds package weight value
    private String pkgWtUom = ""; // holds package unit of measure
    private String cod = ""; // charge on delivery flag 
    private float codAmount = 0; // holds the cost for charge on delivery     
    private String intlShipFlag = ""; // international shipment flag containing Y/N
    private String intlShipString = ""; // international shipment request string

    // variables to hold third party details
    private String tpCompanyName = "";
    private String tpAddress = "";
    private String tpCity = "";
    private String tpState = "";
    private String tpPostalCode = "";
    private String tpCountrySymbol = "";

    // strings to hold xml package request which is part of xml shipment request
    private String pkgStr = "";
    private String pkgStrHeader = "";
    private String pkgStrThirdParty = "";
    private String pkgStrCOD = "";
    private String pkgStrFooter = "";
    // for international shipments package details  
    private double intlQuantity;
    private String intlQUOM = "";
    private double intlUnitWeight;
    private double intlUnitValue;
    private String intlProductDescription = "";
    private String countryCode = "";

    // private String propsPrinterName="";
    // private String printerEODPort="";

    /** default constructor.
     */
    public AascCarrierShipment() {
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

    /** This method can replace the null values with nullString
     * @return String that is ""
     * @param obj object of type Object
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /**
     * method used to set errorMessage.
     * @param errorMessage String
     */
    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * method used to get errorMessage.
     * @return errorMessage String
     */
    public String getError() {
        return errorMessage;
    }

    // this method is added by swapna soni

    /** creates an xml shipment request based on the Type of the PayMethod.
     * If the PayMethod is ThirdPary the request file should contain the Thrid Party
     * related Information. If the PayMethod is freight collect the request file should 
     * contain the customer AccountNumber and so on
     * and passes the request to AascXmlTransmitter.
     * and receives the Response from AascXmlTransmitter
     * @param aascShipmentOrderInfo AascShipmentOrderInfo Object to get the Order Information
     * @param aascShipMethodInfo AascShipMethodInfo Object to get the ShipMethod Information
     * @return Returns the int value , if 150 the response is success otherwise 
     * if int value is 151 then there is an error in the response file.
     * */
    public int createShipmentRequest(AascShipmentOrderInfo aascShipmentOrderInfo, 
                                     AascShipMethodInfo aascShipMethodInfo, 
                                     AascProfileOptionsBean aascProfileOptionsInfo,String cloudLabelPath ) {
        try {
            shipmentRequest = 
                    new StringBuffer(); // StringBuffer that holds shipmentRequest  
            aascShipmentHeaderInfo = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo(); // returns header info bean object
            linkedList = 
                    aascShipmentOrderInfo.getShipmentPackageInfo(); // returns the linkedlist contains the package info bean objects        

            // retreiving file path from profile options to which request and response are written        
            //outputFilePath = aascProfileOptionsInfo.getLabelPath();//gururaj
            // Gururaj added code to get the label path from session, set in customer creation page
             outputFilePath=cloudLabelPath;//gururaj
             //Gururaj code end
            if (aascShipmentOrderInfo != null && aascShipmentHeaderInfo != null && 
                linkedList != null && aascShipMethodInfo != null) {
                carrierId = 
                        aascShipmentHeaderInfo.getCarrierId(); // carrier id retreived from shipment header bean            
                numPackages = 
                        linkedList.size(); // indicates total number of packages fo which we require tracking numbers
                reference1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference1()); // retreiving customer purchase order number from header bean
                reference123 = 
                        aascShipmentHeaderInfo.getReference2(); // retreiving reference2 from shipment header bean               
                shipToCompanyName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCustomerName()); // retreiving ship to company name from header bean   
      //                  System.out.println("shipToCompanyName:: in aascCarrierShipment.java  ::"+shipToCompanyName+"::::");
                shipToAddressLine1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getAddress()); // retreiving ship to address from header bean            
                shipToAddressLine2 = 
                        ""; // retreiving ship to address from header bean
                shipToAddressLine3 = 
                        ""; // retreiving ship to address from header bean
                shipToAddressCity = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCity()); // retreiving ship to city from header bean            
                shipToAddressPostalCode = 
                        nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getPostalCode())); // retreiving ship to postal code from header bean            
                shipToCountry = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCountrySymbol()); // retreiving ship to country name from header bean
                shipToAddressState = 
                        nullStrToSpc(aascShipmentHeaderInfo.getState()); // retreiving ship to state from header bean
                shipDate = 
                        aascShipmentHeaderInfo.getShipmentDate(); // retreiving ship date from header bean
                shipperName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipperName()); // retreiving shipperName name                                                       
                printerPort = 
                        aascShipMethodInfo.getPrinterPort(carrierId); // retreiving printer port from shipmethod info bean 
                stockSymbol = 
                        aascShipMethodInfo.getPrinterStockSymbol(carrierId);
                printerModelSymbol = 
                        aascShipMethodInfo.getPrinterModelSymbol(carrierId);
                shipMethodName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning()); // retreiving ship method meaning from header bean                          
                connectshipSCS = 
                        nullStrToSpc(aascShipMethodInfo.getConnectShipScsTag(shipMethodName)); // retreiving connectShipScsTag from ship method bean                
                customerAccountNumber = 
                        aascShipmentHeaderInfo.getCarrierAccountNumber(); // "123456";                        
                phoneNumber = 
                        nullStrToSpc(aascShipmentHeaderInfo.getPhoneNumber()); // retreiving phone number from header bean                
                carrierPayMethod = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCarrierPaymentMethod()); // retreiving carrier payment terms from header bean   
                carrierPayMethodCode = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierPayCode(carrierPayMethod)); // retreiving carrier payment terms from shipMethod bean            
                shipperTerm = 
                        nullStrToSpc(aascShipMethodInfo.getCsCarrierPayTerm(carrierPayMethodCode));
                satShipFlag = 
                        nullStrToSpc(aascShipmentHeaderInfo.getSaturdayShipFlag()); // retreiving saturday ship flag from header bean        
                logger.info("saturday ship flag : " + satShipFlag);
                if (satShipFlag.equalsIgnoreCase("N")) {
                    satShipFlag = "FALSE";
                } else {
                    satShipFlag = "TRUE";
                }

                logger.info("got the information from header and package bean and setting it to instance variables");

                logger.info("\n carrierId : " + carrierId + 
                            "\n shipper name : " + shipperName + 
                            "\n printer port : " + printerPort + 
                            "\n stockSymbol : " + stockSymbol + 
                            "\n printer model symbol : " + printerModelSymbol + 
                            "\n ship method name :" + shipMethodName + 
                            "\n connectshipSCS : " + connectshipSCS + 
                            "\n cust acct number : " + customerAccountNumber + 
                            "\n carrierPayMethodCode : " + 
                            carrierPayMethodCode + "\n saturday ship flag : " + 
                            satShipFlag + "\n shipperTerm : " + shipperTerm + 
                            "\n shipToCompanyName : " + shipToCompanyName + 
                            "\n shipToAddressLine1 : " + shipToAddressLine1 + 
                            "\n shipToAddressLine2 : " + shipToAddressLine2 + 
                            "\n shipToAddressLine3 : " + shipToAddressLine3 + 
                            "\n shipToAddressCity : " + shipToAddressCity + 
                            "\n shipToAddressState : " + shipToAddressState + 
                            "\n shipToAddressPostalCode : " + 
                            shipToAddressPostalCode + "\n shipToCountry : " + 
                            shipToCountry + "\n shipDate : " + shipDate + 
                            "\n phone number:" + phoneNumber);

                shipmentRequestHdr = "";
                if (carrierPayMethodCode.equalsIgnoreCase("FC") && 
                    !connectshipSCS.equalsIgnoreCase("TANDATA_UPS.UPS.NAM")) {
                    logger.info("creating a request header if carrier pay method is freight collect");
                    shipmentRequestHdr = 
                            "<?xml version=\"1.0\"?>" + "<SHIPMENTREQUEST>" + 
                            "<LOGIN>" + "<LOGINID>TEST</LOGINID>" + 
                            "<PASSWORD>TEST</PASSWORD>" + "</LOGIN>" + 
                            "<DEFATTRIBUTES>" + "<CONSIGNEE>" + "<COMPANY>" + 
                            shipToCompanyName + "</COMPANY>" + "<ADDRESS1>" + 
                            shipToAddressLine1 + "</ADDRESS1>" + "<ADDRESS2>" + 
                            shipToAddressLine2 + "</ADDRESS2>" + "<ADDRESS3>" + 
                            shipToAddressLine3 + "</ADDRESS3>" + "<CITY>" + 
                            shipToAddressCity + "</CITY>" + "<STATEPROVINCE>" + 
                            shipToAddressState + "</STATEPROVINCE>" + 
                            "<POSTALCODE>" + shipToAddressPostalCode + 
                            "</POSTALCODE>" + "<COUNTRYSYMBOL>" + 
                            shipToCountry + "</COUNTRYSYMBOL>" + 
                            "<CONSIGNEEACCOUNT>" + customerAccountNumber + 
                            "</CONSIGNEEACCOUNT>" + "</CONSIGNEE>" + 
                            "<SHIPPERINFO><SHIPPER>" + shipperName + 
                            "</SHIPPER></SHIPPERINFO>" + "<TERMS>" + 
                            shipperTerm + "</TERMS>" + "<SHIPDATE>" + 
                            shipDate + "</SHIPDATE>" + 
                            "<SHIPMENTSERVICEOPTIONS>" + "<SATDELIVERY>" + 
                            satShipFlag + "</SATDELIVERY>" + 
                            "</SHIPMENTSERVICEOPTIONS>" + "</DEFATTRIBUTES>" + 
                            "<PACKAGES>";
                } // if
                else if (connectshipSCS.equals("TANDATA_UPS.UPS.NAM")) {
                    logger.info("creating request header when connectShipSCS is TANDATA_UPS.UPS.NAM");
                    shipmentRequestHdr = 
                            "<?xml version=\"1.0\"?>" + "<SHIPMENTREQUEST>" + 
                            "<LOGIN>" + "<LOGINID>TEST</LOGINID>" + 
                            "<PASSWORD>TEST</PASSWORD>" + "</LOGIN>" + 
                            "<DEFATTRIBUTES>" + "<CONSIGNEE>" + "<COMPANY>" + 
                            shipToCompanyName + "</COMPANY>" + "<CONTACT>" + 
                            shipToCompanyName + "</CONTACT>" + "<ADDRESS1>" + 
                            shipToAddressLine1 + "</ADDRESS1>" + "<ADDRESS2>" + 
                            shipToAddressLine2 + "</ADDRESS2>" + "<ADDRESS3>" + 
                            shipToAddressLine3 + "</ADDRESS3>" + "<CITY>" + 
                            shipToAddressCity + "</CITY>" + "<STATEPROVINCE>" + 
                            shipToAddressState + "</STATEPROVINCE>" + 
                            "<POSTALCODE>" + shipToAddressPostalCode + 
                            "</POSTALCODE>" + "<PHONE>" + phoneNumber + 
                            "</PHONE>" + "<COUNTRYSYMBOL>" + shipToCountry + 
                            "</COUNTRYSYMBOL>" + "</CONSIGNEE>" + 
                            "<SHIPPERINFO><SHIPPER>" + shipperName + 
                            "</SHIPPER></SHIPPERINFO>" + "<TERMS>" + 
                            shipperTerm + "</TERMS>" + "<SHIPDATE>" + 
                            shipDate + "</SHIPDATE>" + 
                            "<SHIPMENTSERVICEOPTIONS>" + "<SATDELIVERY>" + 
                            satShipFlag + "</SATDELIVERY>" + 
                            "</SHIPMENTSERVICEOPTIONS>" + "</DEFATTRIBUTES>" + 
                            "<PACKAGES>";
                } else {
                    logger.info("creating request header");
                    shipmentRequestHdr = 
                            "<?xml version=\"1.0\"?>" + "<SHIPMENTREQUEST>" + 
                            "<LOGIN>" + "<LOGINID>TEST</LOGINID>" + 
                            "<PASSWORD>TEST</PASSWORD>" + "</LOGIN>" + 
                            "<DEFATTRIBUTES>" + "<CONSIGNEE>" + "<COMPANY>" + 
                            shipToCompanyName + "</COMPANY>" + "<ADDRESS1>" + 
                            shipToAddressLine1 + "</ADDRESS1>" + "<ADDRESS2>" + 
                            shipToAddressLine2 + "</ADDRESS2>" + "<ADDRESS3>" + 
                            shipToAddressLine3 + "</ADDRESS3>" + "<CITY>" + 
                            shipToAddressCity + "</CITY>" + "<STATEPROVINCE>" + 
                            shipToAddressState + "</STATEPROVINCE>" + 
                            "<POSTALCODE>" + shipToAddressPostalCode + 
                            "</POSTALCODE>" + "<COUNTRYSYMBOL>" + 
                            shipToCountry + "</COUNTRYSYMBOL>" + 
                            "</CONSIGNEE>" + "<SHIPPERINFO><SHIPPER>" + 
                            shipperName + "</SHIPPER></SHIPPERINFO>" + 
                            "<TERMS>" + shipperTerm + "</TERMS>" + 
                            "<SHIPDATE>" + shipDate + "</SHIPDATE>" + 
                            "<SHIPMENTSERVICEOPTIONS>" + "<SATDELIVERY>" + 
                            satShipFlag + "</SATDELIVERY>" + 
                            "</SHIPMENTSERVICEOPTIONS>" + "</DEFATTRIBUTES>" + 
                            "<PACKAGES>";
                } // else

                shipmentRequestFooter = 
                        "</PACKAGES>" + "<SHIPMENTSERVICE>" + "<SCS>" + 
                        connectshipSCS + "</SCS>" + "</SHIPMENTSERVICE>" + 
                        "<CLOSEOUTMODE>1</CLOSEOUTMODE>" + 
                        "<PACKAGEDETAIL>TRUE</PACKAGEDETAIL>" + 
                        "</SHIPMENTREQUEST>";

                // initialize shipmentRequest to header
                shipmentRequest.append(shipmentRequestHdr);

                // retreiving package info from package bean which is needed to be sent as part of request
                logger.info("getting package info from package bean");
                listIterator = linkedList.listIterator();

                logger.info("pacakage size:" + linkedList.size());
                while (listIterator.hasNext()) {
                    logger.info("inside the while loop in carrier shipment");
                    aascShipmentPackageInfo = 
                            (AascShipmentPackageInfo)listIterator.next();

                    if (aascShipmentPackageInfo == null) {
                        logger.info("inside the if loop of carrier shipment");
                        logger.info("delivery package information bean is null(aascShipmentPackageInfo==null)");

                    } else {
                        logger.info("inside the else block of carrier shipment");
                        pkgWtVal = aascShipmentPackageInfo.getWeight();
                        pkgWtUom = nullStrToSpc(aascShipmentPackageInfo.getUom());
                        cod = nullStrToSpc(aascShipmentPackageInfo.getCodFlag());
                        codAmount = aascShipmentPackageInfo.getCodAmt();

                        // connectship requires WEIGHTUNITS to be either 'LB' or 'KG'
                        if (pkgWtUom.equalsIgnoreCase("LB")) {
                            pkgWtUom = "LB";
                        }
                        if (pkgWtUom.equalsIgnoreCase("KG")) {
                            pkgWtUom = "KG";
                        }
                        logger.info("after setting the uom");
                        // concatenate string for each package to shipmentRequest
                        shipmentRequest.append(getPkgStr(pkgWtVal, 
                                                              pkgWtUom, cod, 
                                                              codAmount)); // ,countryCode
                        logger.info("added pkg details to shipment header");
                    }
                } // end of while          
                logger.info("after appending package info to request header");
                // add footer to shipmentRequest
                shipmentRequest.append(shipmentRequestFooter);
                logger.info("after appending request footer to request header");
                protocol = 
                        nullStrToSpc(aascShipMethodInfo.getProtocol(carrierId));
                hostName = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));
                prefix = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierPrefix(carrierId));
                userName = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierUserName(carrierId));
                password = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierPassword(carrierId));

                logger.info("\n protocol: " + protocol + "\n hostName:" + 
                            hostName + "\n prefix:" + prefix + "\n userName:" + 
                            userName + "\n password:" + password);

                // creating connection to connectShip
                aascConnectShipConnection = 
                        new AascConnectShipConnection(protocol, hostName, 
                                                      prefix, userName, 
                                                      password);
                connection = aascConnectShipConnection.createConnection();
                logger.info("after creating connection to connectShip" + 
                            connection);
                if (connection == null) {
                    logger.info("ERROR IN CONNECTION ESTABLISHMENT!");
                    aascShipmentHeaderInfo.setMainError("ERROR IN CONNECTION ESTABLISHMENT!");
                    responseStatus = 151;
                    return responseStatus;
                } else {
                    aascXmlTransmitter = 
                            new AascXmlTransmitter(); // creates AascXmlTransmitter Object to which the request is sent for processing       

                    logger.info("shipmentRequest: " + 
                                shipmentRequest.toString());
                    try {
                        writeOutputFile(shipmentRequest.toString(), 
                                        outputFilePath + 
                                        "shipment_ups_request.xml");

                    } catch (FileNotFoundException fileNotFoundException) {
                        logger.severe("file to which the request and response to be written is not found:" + 
                                      fileNotFoundException.getMessage() + 
                                      "\n file name:" + outputFilePath);

                    }
                    logger.info("before sending the request to AascXmlTransmitter for processing");
                    shipmentResponse = 
                            aascXmlTransmitter.processRequest(shipmentRequest, 
                                                              connection, 
                                                              ""); // sending the request for processing and receiving the response                                     
                    connection.disconnect();
                    logger.info("after connection is closed");

                    logger.info("before sending the request to AascCarrierShipmentInfo for parsing");
                    if (shipmentResponse != null && 
                        !shipmentResponse.equals("")) {
                        logger.info("sending the request to connectship through xmltransmitter class and receiving response");
                        logger.info("shipmentResponse: " + shipmentResponse);
                        try {
                            writeOutputFile(shipmentResponse, 
                                            outputFilePath + "shipment_ups_response.xml");
                        } catch (FileNotFoundException fileNotFoundException) {
                            logger.severe("file to which the request and response to be written is not found:" + 
                                          fileNotFoundException.getMessage() + 
                                          "\n file name:" + outputFilePath);
                        }
                        logger.info("after writing response to file:" + 
                                    outputFilePath + "shipment_ups_response.xml");
                        aascCarrierShipmentInfo = 
                                new AascCarrierShipmentInfo(); // sending the response to AascCarrierShipmentInfo for parsing Response
                        logger.info(" before sending response to AascCarrierShipmentInfo for parsing");
                        parsedStatus = 
                                aascCarrierShipmentInfo.parseResponse(shipmentResponse, aascShipmentOrderInfo,cloudLabelPath); // returns String indicating status of parsing response
                        logger.info(" after sending response to AascCarrierShipmentInfo for parsing");
                    } else {
                        logger.info("shipment response is null");
                        aascShipmentHeaderInfo.setMainError("shipment response is null");
                        responseStatus = 151;
                        return responseStatus;
                    }
                }
                logger.info("parsedStatus:" + parsedStatus);
                if (parsedStatus.equals("success")) {
                    responseStatus = 150;
                } else {
                    aascShipmentHeaderInfo.setMainError(parsedStatus);
                    responseStatus = 151;
                }
            } // end of if (aascAdhocOrderInfo != null && aascShipmentHeaderInfo != null && linkedList != null && aascShipMethodInfo != null)
            else {
                logger.severe("\n aascShipmentOrderInfo is null OR" + 
                              "\n aascShipmentHeaderInfo is null OR" + 
                              "\n linkedList is null OR" + 
                              "\n aascShipMethodInfo is null");
                aascShipmentHeaderInfo.setMainError("\n aascShipmentOrderInfo is null OR" + 
                                                 "\n aascShipmentHeaderInfo is null OR" + 
                                                 "\n linkedList is null OR" + 
                                                 "\n aascShipMethodInfo is null");
                responseStatus = 151;
            } // end of else of if (aascAdhocOrderInfo != null && aascShipmentHeaderInfo != null && linkedList != null && aascShipMethodInfo != null)
        } // end of try
        catch (Exception e) {
            responseStatus = 151;
            aascShipmentHeaderInfo.setMainError("Error! null values or empty strings passed in request");
            logger.severe("Exception::"+e.getMessage());
        } // end of catch

        // if response is parsed successfully then this method returns 150 else 151
        return responseStatus;
    } // end of getShipmentRequest

    // this method is added by swapna soni

    /** creates shipment request string for each package in shipment.
     * @param pkgWtVal pacakage weight
     * @param pkgWtUom package unit of measure
     * @param cod charge on delivery flag
     * @param codAmount charge on delivery amount
     * @return pkgStr is returned which contains xml request containing package details
     * */
    private String getPkgStr(float pkgWtVal, String pkgWtUom, String cod, 
                                  float codAmount) {
        logger.info("inside the getShipmentPkgStr method in carrier");

        pkgStrHeader = 
                "<PKG>" + "<PKGWEIGHT>" + "<WEIGHTUNITS>" + pkgWtUom + "</WEIGHTUNITS>" + 
                "<WEIGHTVALUE>" + pkgWtVal + "</WEIGHTVALUE>" + 
                "</PKGWEIGHT>" + "<PACKAGING>CUSTOM</PACKAGING>" + 
                "<REFERENCE>" + "<CONSIGNEEREFERENCE>" + reference1 + 
                "</CONSIGNEEREFERENCE>" + "<SHIPPERREFERENCE>" + reference123 + 
                "</SHIPPERREFERENCE>" + "</REFERENCE>" + 
                "<PACKAGESERVICEOPTIONS>";
        logger.info("created pkg header");

        pkgStrFooter = 
                "</PACKAGESERVICEOPTIONS>" + "<LABELFORMAT>TANDATA_UPS_MAXICODE_US_DOMESTIC.STANDARD</LABELFORMAT>" + 
                "<LABELPRINT>" + "<PORT>" + printerPort + "</PORT>" + 
                "<STOCKSYMBOL>" + stockSymbol + "</STOCKSYMBOL>" + 
                "<PRINTERMODELSYMBOL>" + printerModelSymbol + 
                "</PRINTERMODELSYMBOL>" + "</LABELPRINT>" + "</PKG>";
        logger.info("created package footer");

        if (cod.equals("Y")) {
            logger.info("creating package cod  request when charge on delivery(COD) flag is true");
            pkgStrCOD = 
                    "<COD><CODAMOUNT>" + "<CURRENCYCODE>USD</CURRENCYCODE>" + 
                    "<MONETARYVALUE>" + codAmount + "</MONETARYVALUE>" + 
                    "</CODAMOUNT>" + 
                    "<CODPAYMENTMETHOD>30</CODPAYMENTMETHOD>" + 
                    "<CODADDFREIGHT>FALSE</CODADDFREIGHT>" + "</COD>";
        }

        if (carrierPayMethodCode.equalsIgnoreCase("TP")) {
            // retreiving third party details from header bean required to populate the request when carrier pay method is third party 
            logger.info("getting details from package bean for creating xml package request");
            tpCompanyName = aascShipmentHeaderInfo.getTpCompanyName();
            tpAddress = aascShipmentHeaderInfo.getTpAddress();
            tpCity = aascShipmentHeaderInfo.getTpCity();
            tpState = aascShipmentHeaderInfo.getTpState();
            tpPostalCode = aascShipmentHeaderInfo.getTpPostalCode();
            tpCountrySymbol = 
                    nullStrToSpc(aascShipmentHeaderInfo.getTpCountrySymbol());

            logger.info("\n tpCompanyName : " + tpCompanyName + 
                        "\n tpAddress : " + tpAddress + "\n tpCity : " + 
                        tpCity + "\n tpState : " + tpState + 
                        "\n tpPostalCode : " + tpPostalCode + 
                        "\n tpCountrySymbol : " + tpCountrySymbol + 
                        "\n cod :" + cod + "\n carrier pay method code:" + 
                        carrierPayMethodCode);

            logger.info("creating third party xml request if carrier pay method code is third party");

            pkgStrThirdParty = 
                    "<BILLTHIRDPARTY>" + "<BTPCOMPANY>" + tpCompanyName + 
                    "</BTPCOMPANY>" + "<BTPADDRESS1>" + tpAddress + 
                    "</BTPADDRESS1>" + "<BTPCITY>" + tpCity + "</BTPCITY>" + 
                    "<BTPSTATEPROVINCE>" + tpState + "</BTPSTATEPROVINCE>" + 
                    "<BTPPOSTALCODE>" + tpPostalCode + "</BTPPOSTALCODE>" + 
                    "<BTPCOUNTRYSYMBOL>" + tpCountrySymbol + 
                    "</BTPCOUNTRYSYMBOL>" + "<BTPPHONE>" + phoneNumber + 
                    "</BTPPHONE>" + "<BTPACCOUNT>" + customerAccountNumber + 
                    "</BTPACCOUNT>" + "</BILLTHIRDPARTY>";
        }

        pkgStr = pkgStrHeader + pkgStrThirdParty + pkgStrCOD + pkgStrFooter;
        logger.info("adding pkg footer and third party details to package footer");
        return pkgStr;
    } // end of getPkgStr                
}// end of the class                

