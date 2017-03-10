/*
   * @(#)AascFedExShipment.java
   * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
   * All rights reserved.
   */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.util.AascLogger;

import com.fedex.api.FedExAPI;
import com.fedex.api.FedExAPIException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import java.text.DecimalFormat;

import java.util.Iterator;


/**
 * AascFedExShipment Class contains createShipmentRequest()
 * which is used for creating xml shipment request for FedEx which is sent 
 * to FedEx Atom Server.
 * @author     Suman Gunda
 * @version     1.0  
 * @since 
 * 
 * 19/01/2015     Suman Gunda     Removed unncessary comments in history.
 * 28/01/2015     Y Pradeep       Added code required for International Shipping.
 * 12/02/2015     Eshwari M       Modified code by removing the unnecessary code
 * 16/02/2015     Suman G         Commented code to fix #2583.
 * 10/03/2015     Y Pradeep       Removed reference1value and reference2value filed.
 * 09/03/2015     Suman G         Modified getDimensionUnit() to getDimensionUnits() for removing duplicate variables in package bean.
 * 26/03/2015     Y Pradeep       Added code related to ship to email, ship from email and residential flag for FedEx Shipping.
 * 03/06/2015     Suman G         Added code to fix #2955
 * 20/07/2015     Suman G         Added code for implement Email Notification.
 * 04/08/2015     Suman G         Modified code for issue #3294
 * 04/08/2015     Y Pradeep       Added concating substring value to fedExWsShippingName1 variable to get complete ProperShippingName value for HazMat.
 * 04/11/2015     Suman G         Added code to fix #3888 and showing wrong message issue.
 * 06/11/2015     Mahesh V        Added code to fix #3884 declaration statement to print on commrcial invoice
 * 10/11/2015     Mahesh V        Added code for FedEx Recepient Development.
 * 18/11/2015     Y Pradeep       Added encode to return shipfrom and shipto address details. Bug #3961.
 * 27/11/2015     Mahesh V        Added code for FedEx Recepient and Third Party development
 * 18/12/2015     Y Pradeep       Modified code to send system date TimeStamp as shipmentdate for shipping. Bug #4095.
 */
public class


AascFedexShipment {
    private LinkedList packageInfo = 
        null; // Delivery Package Information Object    
    private String shipmentResponse = 
        ""; // String Containg the shipment response xml file
    private String shipmentRequest = 
        ""; // String containing Shipment Request In XML format  
    private static Logger logger = 
        AascLogger.getLogger("AascFedexShipment"); // logger object used for issuing logging requests
    private String shipmentRequestHdr = "";
    private AascShipmentHeaderInfo aascShipmentHeaderInfo = 
        null; // Delivery Header Information Object
    private LinkedList shipPackageInfo = 
        null; // Delivery Package Information Object
    private String fedExTestMeterNumber = 
        ""; // FedEx Test meter number of the client   
    private String shipToCompanyName = 
        ""; // indicates ship to company name(these details are required to be sent to connectship for calculating shipment cost)
    private String shipToAddressLine1 = ""; // indicates ship to address   
    private String shipToAddressLine2 = ""; // indicates ship to address

    private String shipToAddressCity = ""; // indicates ship to address city
    private String shipToAddressPostalCode = 
        ""; // indicates ship to postal code
    private String shipToCountry = ""; // indicates ship to country
    private String shipToEMailAddress = "";
    private String shipToAddressState = ""; // indicates ship to state address
    private java.sql.Date shipDate = null; // indicates ship date   
    private SimpleDateFormat stf = 
        new SimpleDateFormat("HH:mm:ss"); // time format
    private Date currentDate = null; // indicates current date 
    private String time = null; // indicates current time string
    private Calendar calendar = 
        null; // calender object to retreive current dat and time
    private String shipMethodName = ""; // indicates ship method name
    private String carrierCode = ""; // indicates CarrierCode 
    private String customerCarrierAccountNumber = 
        ""; // indicates customer account number
    private String customerTransactionIdentifier = 
        ""; // Customer transaction identifier
    private String dropoffType = ""; // indicates drop off type
    private String service = 
        ""; // "STANDARDOVERNIGHT"; // indicates service type
    private String packaging = "";
    private String shipToContactPhoneNumber = 
        ""; // indicates customer phone number
    private String shipFromCompanyName = 
        ""; // indicates the company name from which delivery is being shipped
    private String shipFromPersonName = "";
    private String shipFromDepartment = 
        ""; // indicates the department name in the company from which delivery is being shipped
    private String shipFromPhoneNumber1 = ""; // indicates sender phone number
    private String shipFromEMailAddress = "";
    private String shipFromAddressLine1 = ""; // indicates ship From address   
    private String shipFromAddressLine2 = ""; // indicates ship From address   
    private String shipFromAddressCity = 
        ""; // indicates ship From address city    
    private String shipFromAddressPostalCode = 
        ""; // indicates ship From postal code
    private String shipFromCountry = ""; // indicates ship From country
    private String shipFromAddressState = 
        ""; // indicates ship From state address
    private String carrierPayMethod; // indicates code of carrier payment terms
    private String carrierPayMethodCode = 
        ""; // indicates carrier payment terms
    private int carrierId = 0; // indicates carrier Id
    private float pkgWtVal = 0; // indicates package weight in the delivery
    private String pkgWtUom = ""; // indicates package unit of measure 
    private String currencyCode = "USD"; // Hard coded for now
    private byte[] replyOut = 
        new byte[500]; // Byte array for catching reply from 
    private int iUTI = 
        2517; // Universal transaction identifier for ShipRequest 2517
    private String portString = "";
    private int port = 0; // Default FedExAPI service (ATOM) port
    private String host = 
        ""; // host address of system where FedExAPI service is running
    private int timeOut = 125; // timeOut period 
    private int responseStatus = 
        0; // holds 150 if response is retreived successfully and parsed successfully else holds 151                     
    private String parseStatus = 
        ""; // holds the status of parsing the response        

    private static HashMap carrierPayMethodCodeMap = new HashMap();

    private String outputFile = 
        ""; // holds folder name to which request and response are written
    private String senderAccountNumber = ""; // sender's carrier account number
    private String dimensions = "";
    private String width = "0";
    private String height = "0";
    private String length = "0";
    private int index = 0;
    private String units = "";

    private String packageCount = "";
    private String packageSequence = "";
    private String packageSequenceTest = "";
    private String packageFlag = "";
    private String packageTrackinNumber = "";
    private String shipFlag = "";
    private String shipmentWeight = "";
    private String masterTrackingNumber = "";
    private String masterFormID = "";


    private String header2 = "";
    private String header3 = "";
    private String header4 = "";
    private String listTag = "";
    private String rntHeader1 = "";
    private String rntHeader5 = "";
    private String rntHeader6 = "";

    private String part1 = "";
    private String part2 = "";
    private String shipWtTag = "";
    private double totalShipmentCost = 0.0;
    private double totalFreightCost = 0.0;

    HashMap hashMap = null;

    private double surCharges = 0.0;

    /* added for Fedex Package Options */
    private String returnShipment = "";
    private String signatureOptions = "";
    private String signatureOptionString = "";
    private String futureDayShipment = "";
    // private String nonStandardContainer;
    private double packageDeclaredValue;
    private String packageDeclaredValueStr;

    private double rtnPackageDeclaredValue;
    private String rtnPackageDeclaredValueStr;

    private String header7 = ""; // added for package options
    private String header8 = ""; // added for department.
    private double shipmentDeclaredValue = 0.0;
    private String shipmentDeclaredValueStr = "";


    // Added for dimensions

    private double packageLength = 0;
    private double packageWidth = 0;
    private double packageHeight = 0;
    private String header9 = "";

    private String reference1 = "";
    private String reference2 = "";

    private String hubid = ""; //added for smartpost
    private String indicia = ""; //added for smartpost
    private String ancillary = ""; //added for smartpost

    private String labelFormat = ""; // default label format
    private String labelFormatTag = "";

    private String codFlag = "";
    private double codAmt = 0.0;
    private String codAmtStr = "";
    private String codTag = "";
    private String codReturnTrackingTag = "";

    private String codTrackingNum = "";

    private String residentialTag = "";
    private String labelStockOrientation = "";
    private String docTabLocation = "";
    private String satShipFlag = 
        ""; // flag which indicates whether the delivery is shipped on saturday or not
    private String satShipFlagTag = "";
    private int orderName;
    //Added by Dedeepya on 22/06/07(start)
    private String orderNumber = "";
    private String Shipment = "";
    private String Dept = "";
    private String poNumber = "";
    private String ShipDate = "";
    private String Weight = "";
    private String COD = "";
    private String DV = "";
    private String Shipping = "";
    private String Special = "";
    private String Handling = "";
    private String Total = "";

    private String shipToContactPersonName = "";
    private String tagshipToContactPersonName = "";
    private String chkReturnlabel = "";

    private String rtnShipFromCompany = "";
    private String rtnShipToCompany = "";
    private String rtnShipFromContact = "";
    private String rtnShipToContact = "";
    private String rtnShipFromLine1 = "";
    private String rtnShipToLine1 = "";
    private String rtnShipFromLine2 = "";
    private String rtnShipToLine2 = "";
    private String rtnShipFromCity = "";
    private String rtnShipFromSate = "";
    private String rtnShipFromZip = "";
    private String rtnShipToCity = "";
    private String rtnShipToState = "";
    private String rtnShipToZip = "";
    private String rtnShipFromPhone = "";
    private String rtnShipToPhone = "";
    private String rtnShipMethod = "";
    private String rtnDropOfType = "";
    private String rtnPackageList = "";
    private String rtnPayMethod = "";
    private String rtnPayMethodCode = "";
    private String rtnACNumber = "";
    private String rtnMeterNumber = "";
    private String rtnRMA = "";

    private String rtnTagshipToContactPersonName = "";
    private int size;

    private String halPhone = "";
    private String halLine1 = "";
    private String halLine2 = "";
    private String halCity = "";
    private String halState = "";
    private String halZip = "";
    private String hal = "";
    private String halFlag = "";
    private String HazMat = "";
    private String HazMatFlag = "";
    private String HazMatType = "";
    private String HazMatClass = "";

    private double HazMatQty = 0.0;
    private String HazMatUnit = "";

    private String HazMatIdentificationNo = "";
    private String HazMatEmergencyContactNo = "";
    private String HazMatEmergencyContactName = "";
    private String HazardousMaterialPkgGroup = "";
    private String HazMatDOTLabelType = "";
    private String HazardousMaterialId = "";
    double hazmatPkgingCnt = 0.0;
    String hazmatPkgingUnits = "";
    String hazmatTechnicalName = "";
    String hazmatSignatureName = "";

    private String internationalTags = "";
    private String intHeader1 = "";
    private String intHeader2 = "";
    private String intHeader3 = "";

    private String intHeader5 = "";

    private String intHeader6 = "";

    private String intPayerType = "";
    private String intAccNumber = "";
    private String intMaskAccNumber = "";
    
    private String intcountryCode = "";
    private String intTermsOfSale = "";
    private String intTotalCustomsValue = "";
    private double intFreightCharge = 0.0;
    private double intInsuranceCharge = 0.0;
    private double intTaxesOrMiscellaneousCharge = 0.0;
    private String intPurpose = "";
    private String intSenderTINOrDUNS = "";
    private String intSenderTINOrDUNSType = "";

    private String packingListEnclosed;
    private int shippersLoadAndCount;
    private String bookingConfirmationNumber = "";

    private int numberOfPieces; //indicates number of pieces for each commodity item
    private String description = ""; //indicates description of item
    private String countryOfManufacture = 
        ""; //indicates country of manufacture of item
    private String harmonizedCode = ""; //indicates harmonized code of item
    private double weight; //indicates weight of item
    private String quantity = ""; //indicates quantity of item
    private String quantityUnits = ""; //indicates units of quantity
    private String unitPrice = ""; //indicates unit price of item
    private String customsValue = ""; //indicates customs value of item
    private String exportLicenseNumber = 
        ""; //indicates export license number of the shipper
    private String exportLicenseExpiryDate; //indicates export license Expiry Date

    private AascIntlHeaderInfo aascIntlHeaderInfo = null;


    private String shipmentHeader5 = "";

    private int orderNumberShip;


    private String rmaTag = "";

    private String timeStampStr;


    private String SenderEmail = "";
    private String recipientEmailAddress1 = "";
    private String recipientEmailAddress2 = "";
    private String recipientEmailAddress3 = "";
    private String recipientEmailAddress4 = "";
    private String recipientEmailAddress5 = "";

    private String ShipAlertNotification = "";
    private String ExceptionNotification = "";
    private String DeliveryNotification = "";

    private String Format = "";
    private String message = "";

    private String emailFlag = "";

    private String dryIceUnits = "";
    private String chDryIce = "";
    private double dryIceWeight = 0.0;    
    private String dryIceTag = "";

    private int shipMultiPieceFlag = 0;
    private String residentialAddrFlag = "";


    private String fedExCarrierMode;
    private String fedExKey;
    private String fedExPassword;

    private String fedExWsTimeStr;
    //Hazardous Material 
    private String fedExWsShippingName = "";
    private String fedExWsShippingName1 = "";
    private String fedExWsShippingName2 = "";
    private String fedExWsShippingName3 = "";
    private String fedExWsClass = "";
    private String fedExWSChkReturnlabelstr = "";
    private int pkgCntWs = 0;
    private String payorCountryCodeWS = "";

    private String updateFlg = "";
    private String separateShipFlag = "";


    
    private String declarationStmt;
    private String generateCI;
    private String importerName;
    private String importerCompName;
    private String importerPhoneNum;
    private String importerAddress1;
    private String importerAddress2;
    private String importerCity;
    private String importerState;
    private String importerPostalCode;
    private String importerCountryCode;
    private String impIntlSedNumber;
    private String impIntlSedType;

    private String recIntlSedNumber;
    private String recIntlSedType;

    private String brokerName;
    private String brokerCompName;
    private String brokerPhoneNum;
    private String brokerAddress1;
    private String brokerAddress2;
    private String brokerCity;
    private String brokerState;
    private String brokerPostalCode;
    private String brokerCountryCode;
    private String dutiesAndTaxesFlag; 
    private String intlDocSubType; 

    private String op900LabelFormat;

    private String lpnNumber = ""; // added by Suman Gunda
    
     private String receipientPartyName = "";
     private String recipientPostalCode = "";
     
    private String tpCompanyName = "";
    private String tpAddress = "";
    private String tpCity = "";
    private String tpState = "";
    private String tpPostalCode = "";
    private String tpCountrySymbol = "";
  
    static {
        carrierPayMethodCodeMap.put("PREPAID", "SENDER");
        carrierPayMethodCodeMap.put("RECIPIENT", "RECIPIENT");
        carrierPayMethodCodeMap.put("THIRD PARTY BILLING", "THIRDPARTY");
        carrierPayMethodCodeMap.put("COLLECT", "COLLECT");

    }

    /** default constructor.*/
    public AascFedexShipment() {
    }

    /** This method can be used to create references tags.
     * @return String 
     * @param refVal1 String, refVal2 String
     */

    private String getReferenceValue(String refVal1, String refVal2) {
        logger.info("Entered getReferenceValue() method");
        String refTag = "";


        if ((refVal1.equalsIgnoreCase("")) && (refVal2.equalsIgnoreCase(""))) {
            refTag = 
                    "<CustomerReference>" + "Ref1# " + reference1 + "  Ref2# " + 
                    reference2 + "</CustomerReference>";
        } else if (refVal1.equalsIgnoreCase("")) {
            refTag = getRef2Val(refVal2);
        } else if (refVal2.equalsIgnoreCase("")) {
            refTag = getRef1Val(refVal1);
        } else {
            
            if (refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                refTag = 
                        "<PONumber>" + reference1 + "</PONumber>" + "<CustomerReference>" + 
                        reference2 + "</CustomerReference>";
            } else if (refVal2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                refTag = 
                        "<PONumber>" + reference2 + "</PONumber>" + "<CustomerReference>" + 
                        reference1 + "</CustomerReference>";
            } else if ((refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) && 
                       (refVal2.equalsIgnoreCase("DELIVERY NAME"))) {
                refTag = 
                        "<CustomerReference>" + "SO# " + reference1 + "  Del# " + 
                        reference2 + "</CustomerReference>";
            } else {
                refTag = 
                        "<CustomerReference>" + "Del# " + reference1 + "  SO# " + 
                        reference2 + "</CustomerReference>";
            }
        }
        return refTag;

    }

    /** This method can be used to create references2 tag.
     * @return String 
     * @param  refVal2 String
     */
    private String getRef2Val(String refVal2) {
        logger.info("Ref 1 from profile Options is null");
        String refTag = "";
        if (refVal2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
            refTag = 
                    "<PONumber>" + reference2 + "</PONumber>" + "<CustomerReference>" + 
                    reference1 + "</CustomerReference>";
        } else if (refVal2.equalsIgnoreCase("SALES ORDER NUMBER")) {
            refTag = 
                    "<CustomerReference>" + "Ref# " + reference1 + "  SO# " + reference2 + 
                    "</CustomerReference>";
        } else {
            refTag = 
                    "<CustomerReference>" + "Ref# " + reference1 + "  Del# " + reference2 + 
                    "</CustomerReference>";
        }
        return refTag;
    }

    /** This method can be used to create references1 tag.
     * @return String 
     * @param  refVal1 String
     */
    private String getRef1Val(String refVal1) {
        logger.info("Ref 2 from profile Options is null");
        String refTag = "";
        if (refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
            refTag = 
                    "<PONumber>" + reference1 + "</PONumber>" + "<CustomerReference>" + 
                    reference2 + "</CustomerReference>";
        } else if (refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) {
            refTag = 
                    "<CustomerReference>" + "Ref# " + reference2 + "  SO# " + reference1 + 
                    "</CustomerReference>";
        } else {
            refTag = 
                    "<CustomerReference>" + "Ref# " + reference2 + "  Del# " + reference1 + 
                    "</CustomerReference>";
        }
        return refTag;
    }


    /** This method can be used to send fedex request.
     * @param  aascShipmentOrderInfo object, 
     *          aascShipMethodInfo Object,
     *          chkReturnlabelstr String,
     *          aascProfileOptionsInfo Object,
     *          aascIntlInfo Object,
     *          cloudLabelPath String
     */

    public void sendfedexRequest(AascShipmentOrderInfo aascShipmentOrderInfo, 
                                 AascShipMethodInfo aascShipMethodInfo, 
                                 String chkReturnlabelstr, 
                                 AascProfileOptionsBean aascProfileOptionsInfo, 
                                 AascIntlInfo aascIntlInfo, 
                                 String cloudLabelPath) throws Exception {
        logger.info("Entered sendfedexRequest method");
        int pkgCount = aascShipmentOrderInfo.getShipmentPackageInfo().size();
        HashMap tempMap = null;

        String appendStr = "";

        if (chkReturnlabelstr.equals("NONRETURN")) {
            fedExWSChkReturnlabelstr = chkReturnlabelstr;
            shipmentRequestHdr = "";
            appendStr = "";
            
            // "<PersonName>"+ Contact Name +"</PersonName>"+
            shipmentRequestHdr = 
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
                    "<FDXShipRequest xmlns:api=\"http://www.fedex.com/fsmapi\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXShipRequest.xsd\">" + 
                    "<RequestHeader>" + "<AccountNumber>" + 
                    senderAccountNumber + "</AccountNumber>" + 
                    "<MeterNumber>" + fedExTestMeterNumber + "</MeterNumber>" + 
                    "<CarrierCode>" + carrierCode + "</CarrierCode>" + 
                    "</RequestHeader>" + "<ShipDate>" + shipDate + 
                    "</ShipDate>" + "<ShipTime>" + time + "</ShipTime>" + 
                    "<DropoffType>" + dropoffType + "</DropoffType>" + 
                    "<Service>" + service + "</Service>" + "<Packaging>" + 
                    packaging + "</Packaging>" + "<WeightUnits>" + pkgWtUom + 
                    "</WeightUnits>" + "<Weight>" + pkgWtVal + "</Weight>" + 
                    "<CurrencyCode>" + currencyCode + "</CurrencyCode>" + 
                    "<ListRate>true</ListRate>" + "<Origin>" + "<Contact>" + 
                    "<CompanyName>" + shipFromCompanyName + "</CompanyName>" + 
                    "<PhoneNumber>" + shipFromPhoneNumber1 + "</PhoneNumber>" + 
                    "</Contact>" + "<Address>" + "<Line1>" + 
                    shipFromAddressLine1 + "</Line1>" + "<Line2>" + 
                    shipFromAddressLine2 + "</Line2>" + "<City>" + 
                    shipFromAddressCity + "</City>" + "<StateOrProvinceCode>" + 
                    shipFromAddressState + "</StateOrProvinceCode>" + 
                    "<PostalCode>" + shipFromAddressPostalCode + 
                    "</PostalCode>" + "<CountryCode>" + shipFromCountry + 
                    "</CountryCode>" + "</Address>" + "</Origin>" + 
                    "<Destination>" + "<Contact>" + 
                    tagshipToContactPersonName + "<CompanyName>" + 
                    shipToCompanyName + "</CompanyName>" + "<PhoneNumber>" + 
                    shipToContactPhoneNumber + "</PhoneNumber>" + 
                    "</Contact>" + "<Address>" + "<Line1>" + 
                    shipToAddressLine1 + "</Line1>" + "<Line2>" + 
                    shipToAddressLine2 + "</Line2>" + "<City>" + 
                    shipToAddressCity + "</City>" + "<StateOrProvinceCode>" + 
                    shipToAddressState + "</StateOrProvinceCode>" + 
                    "<PostalCode>" + shipToAddressPostalCode + 
                    "</PostalCode>" + "<CountryCode>" + shipToCountry + 
                    "</CountryCode>" + "</Address>" + "</Destination>" + 
                    "<Payment>" + "<PayorType>" + carrierPayMethod + 
                    "</PayorType>";
            

        } else {
            fedExWSChkReturnlabelstr = chkReturnlabelstr;
            
            
            shipmentRequestHdr = 
                    rntHeader1 + header2 + header3 + listTag + rntHeader5 + 
                    rntHeader6;
            appendStr = "Return_";

        }

        tempMap = new HashMap();

        

        //Added by dedeepya on 22/06/07(start)

        if (carrierCode.equalsIgnoreCase("FDXE")) {
            logger.info("carrierCode is FDXE");
            orderNumber = 
                    "<Value>FDXShipRequest/ReferenceInfo/CustomerReference</Value>";
            Shipment = 
                    "<Value>FDXShipReply/ReplyHeader/CustomerTransactionIdentifier</Value>";
            Dept = "<Value>FDXShipRequest/Origin/Contact/Department</Value>";
            poNumber = "<Value>FDXShipRequest/ReferenceInfo/PONumber</Value>";
            ShipDate = "<Value>FDXShipRequest/ShipDate</Value>";
            Weight = "<Value>FDXShipRequest/Weight</Value>";
            COD = 
"<Value>FDXShipRequest/SpecialServices/COD/CollectionAmount</Value>";
            DV = "<Value>FDXShipRequest/DeclaredValue</Value>";
            Shipping = 
                    "<Value>FDXShipReply/EstimatedCharges/DiscountedCharges/BaseCharge</Value>";
            Special = 
                    "<Value>FDXShipReply/EstimatedCharges/DiscountedCharges/TotalSurcharge</Value>";
            Handling = 
                    "<Value>FDXShipReply/EstimatedCharges/DiscountedCharges/TotalDiscount</Value>";
            Total = 
                    "<Value>FDXShipReply/EstimatedCharges/DiscountedCharges/NetCharge</Value>";


        } else {
            logger.info("carrierCode is FDXG");
            orderNumber = 
                    "<Value>FDXShipRequestReferenceInfoCustomerReference</Value>";
            Shipment = 
                    "<Value>FDXShipReplyReplyHeaderCustomerTransactionIdentifier</Value>";
            Dept = "<Value>FDXShipRequestOriginContactDepartment</Value>";
            poNumber = "<Value>FDXShipRequestReferenceInfoPONumber</Value>";
            ShipDate = "<Value>FDXShipRequestShipDate</Value>";
            Weight = "<Value>FDXShipRequestWeight</Value>";
            COD = 
"<Value>FDXShipRequestSpecialServicesCODCollectionAmount</Value>";
            DV = "<Value>FDXShipRequestDeclaredValue</Value>";
            Shipping = 
                    "<Value>FDXShipReplyEstimatedChargesDiscountedChargesBaseCharge</Value>";
            Special = 
                    "<Value>FDXShipReplyEstimatedChargesDiscountedChargesTotalSurcharge</Value>";
            Handling = 
                    "<Value>FDXShipReplyEstimatedChargesDiscountedChargesTotalDiscount</Value>";
            Total = 
                    "<Value>FDXShipReplyEstimatedChargesDiscountedChargesNetCharge</Value>";
        }

        //Added by dedeepya on 22/06/07(start)


        // If carrier code is FedExExpress and pay method is RECIPIENT or THIRDPARTY Or
        // carrier code is FedExGround and pay method is THIRDPARTY payer carrier account number is must.
        if (((carrierCode.equalsIgnoreCase("FDXE")) && 
             ((carrierPayMethodCode.equalsIgnoreCase("TP")) || 
              (carrierPayMethodCode.equalsIgnoreCase("CG")))) || 
            ((carrierCode.equalsIgnoreCase("FDXG")) && 
             (carrierPayMethodCode.equalsIgnoreCase("TP")) || 
             (carrierPayMethodCode.equalsIgnoreCase("CG")))) {
            if (customerCarrierAccountNumber.length() < 9 || 
                customerCarrierAccountNumber.length() > 12) {
                aascShipmentHeaderInfo.setMainError("third party or consignee's account number should not be less than 9 digits and greater than 12 digits ");
                responseStatus = 151;
                tempMap = new HashMap();
                tempMap.put("ResponseStatus", String.valueOf(responseStatus));
            }
            
            shipmentRequestHdr = 
                    shipmentRequestHdr + "<Payor><AccountNumber>" + 
                    customerCarrierAccountNumber + "</AccountNumber>";
            if (carrierPayMethodCode.equalsIgnoreCase("TP")) {

                String tpCountrySymbl = 
                    aascShipmentHeaderInfo.getTpCountrySymbol();
                shipmentRequestHdr = 
                        shipmentRequestHdr + "<CountryCode>" + tpCountrySymbl + 
                        "</CountryCode></Payor>";
                payorCountryCodeWS = tpCountrySymbl;
            } else {
                shipmentRequestHdr = 
                        shipmentRequestHdr + "<CountryCode>" + shipToCountry + 
                        "</CountryCode></Payor>";
                payorCountryCodeWS = shipToCountry;
            }
        }
        shipmentRequestHdr = shipmentRequestHdr + "</Payment>";

        /*if (carrierCode.equalsIgnoreCase("FDXE")
                           && (service.equalsIgnoreCase("FEDEX1DAYFREIGHT")
                                   || service.equalsIgnoreCase("FEDEX2DAYFREIGHT")
                                   || service.equalsIgnoreCase("FEDEX3DAYFREIGHT"))) {*/
        shipmentRequestHdr = shipmentRequestHdr + header9;

        /*"<Dimensions>"
                               + "<Length>" + length + "</Length>" + "<Width>"
                               + width + "</Width>" + "<Height>" + height
                               + "</Height>" + "<Units>" + units + "</Units>"
                               + "</Dimensions>"
                               */
        ;
        //}


        if (carrierCode.equalsIgnoreCase("FDXG") && 
            service.equalsIgnoreCase("GROUNDHOMEDELIVERY")) {

            residentialTag = "<ResidentialDelivery>true</ResidentialDelivery>";

        } else {
            residentialTag = "";
        }

        if (!signatureOptions.equalsIgnoreCase("NONE")) {
            signatureOptionString = 
                    "<SignatureOption>" + signatureOptions + "</SignatureOption>";
        } else {
            signatureOptionString = "";
        }

        shipmentHeader5 = 
                "<SpecialServices>" + signatureOptionString + residentialTag + 
                codTag + HazMat + hal + "</SpecialServices>";


        labelFormat = aascShipMethodInfo.getPrinterPort(carrierId);
        labelStockOrientation = 
                aascShipMethodInfo.getLabelStockOrientation(carrierId);
        docTabLocation = aascShipMethodInfo.getDocTabLocation(carrierId);
        
        if (labelFormat.equalsIgnoreCase("ZEBRA") || 
            labelFormat.equalsIgnoreCase("ELTRON") || 
            labelFormat.equalsIgnoreCase("UNIMARK")) { // LEADING
            // BOTTOM
            //Added by dedeepya on 22/06/07(start)
            labelFormatTag = 
                    "<ImageType>" + labelFormat + "</ImageType>" + "<LabelStockOrientation>" + 
                    labelStockOrientation + "</LabelStockOrientation>" + 
                    "<DocTabLocation>" + docTabLocation + "</DocTabLocation>" + 
                    "<DocTabContent>" + "<Type>ZONE001</Type>" + "<Zone001>" + 
                    "<HeaderValuePair>" + "<ZoneNumber>01</ZoneNumber>" + 
                    "<Header>Order#</Header>" + orderNumber + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>02</ZoneNumber>" + 
                    "<Header>Delivery</Header>" + Shipment + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>03</ZoneNumber>" + "<Header>Dept</Header>" + 
                    Dept + "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>04</ZoneNumber>" + "<Header>Ref</Header>" + 
                    poNumber + "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>05</ZoneNumber>" + 
                    "<Header>ShipDate</Header>" + ShipDate + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>06</ZoneNumber>" + "<Header>Weight</Header>" + 
                    Weight + "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>07</ZoneNumber>" + "<Header>COD</Header>" + 
                    COD + "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>08</ZoneNumber>" + "<Header>DV</Header>" + 
                    DV + "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>09</ZoneNumber>" + 
                    "<Header>Shipping</Header>" + Shipping + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>10</ZoneNumber>" + 
                    "<Header>Special</Header>" + Special + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>11</ZoneNumber>" + 
                    "<Header>Discount</Header>" + Handling + 
                    "</HeaderValuePair>" + "<HeaderValuePair>" + 
                    "<ZoneNumber>12</ZoneNumber>" + "<Header>Total</Header>" + 
                    Total + "</HeaderValuePair>" + "</Zone001>" + 
                    "</DocTabContent>" + 
                    "<MaskAccountNumber>true</MaskAccountNumber>";
            //Added by dedeepya on 22/06/07(start)

        } else if (labelFormat.equalsIgnoreCase("PNG")) {

            labelFormatTag = "<ImageType>" + labelFormat + "</ImageType>";
        } else {
            labelFormatTag = 
                    "<ImageType>" + "PDF" + "</ImageType>"; //labelFormat + "</ImageType>";      // remove pdf by labelFormat
        }

        pkgCount = aascShipmentOrderInfo.getShipmentPackageInfo().size();

        
        if (pkgCount == 1) {
            header4 = "";
        }
        shipmentRequestHdr = 
                shipmentRequestHdr + internationalTags + shipmentHeader5 + 
                "<Label>" + "<Type>2DCOMMON</Type>" + labelFormatTag + 
                "</Label>" + header4 + rmaTag + "</FDXShipRequest>";
        //End


        /* shipmentRequestHdr = shipmentRequestHdr + internationalTags +shipmentHeader5+ "<Label>"
                           + "<Type>2DCOMMON</Type>" + "<ImageType>PNG</ImageType>"
                           + "</Label>" +rmaTag+ "</FDXShipRequest>"; */

        timeStampStr = 
                (new Date().toString().replaceAll(" ", "_")).replaceAll(":", 
                                                                        "_");

        
        tempMap = null;
        tempMap = new HashMap();

        if (port != 0 && host != null && !(host.equals(""))) {
            try {

                
//                try {   logger.info("Before call");
//                    writeOutputFile(shipmentRequestHdr, 
//                                    outputFile + orderNumberShip + "_" + 
//                                    packageSequence + "_" + carrierCode + "_" + 
//                                    appendStr + timeStampStr + "_request.xml");
//                    
//                } catch (FileNotFoundException fileNotFoundException) {
//                    logger.severe("file to which the request and response to be written is not found:" + 
//                                  fileNotFoundException.getMessage() + 
//                                  "\n file name:" + outputFile);
//
//                }
                shipmentRequest = shipmentRequestHdr;
                

                fedExCarrierMode = nullStrToSpc(fedExCarrierMode);
                
                if (fedExCarrierMode.equalsIgnoreCase("WEBSERVICES") || 
                    fedExCarrierMode.equalsIgnoreCase("FedexWebServices")) {
                    
                    String intFlagLocal = 
                        nullStrToSpc(aascShipmentHeaderInfo.getInternationalFlag());
                    Double totalWeight = 
                        aascShipmentHeaderInfo.getPackageWeight();

                    replyOut = 
                            callFedexWS(fedExKey, fedExPassword, intFlagLocal, 
                                        totalWeight, aascIntlInfo).getBytes();
                } else {
                    replyOut = 
                            FedExAPI.transact(iUTI, shipmentRequest.getBytes(), 
                                              host, port, timeOut);
                }


                
                shipmentResponse = new String(replyOut, "ISO-8859-1");
                
                if (shipmentResponse != null && !shipmentResponse.equals("")) {
                    
                    {
                        
//                        try {
//                            writeOutputFile(shipmentResponse, 
//                                            outputFile + orderNumberShip + 
//                                            "_" + packageSequence + "_" + 
//                                            carrierCode + "_" + appendStr + 
//                                            timeStampStr + "_response.xml");
//                            
//                        } catch (Exception fileNotFoundException) {
//                            logger.severe("file path to which the fedex xml response to be written is not found:" + 
//                                          fileNotFoundException.getMessage() + 
//                                          "\n file name:" + outputFile);
//                        }
                        
                        String nonDiscountedCost = 
                            aascProfileOptionsInfo.getNonDiscountedCost();
                        

                        AascFedexShipmentInfo aascFedexShipmentInfo = 
                            new AascFedexShipmentInfo();

                        if (fedExCarrierMode.equalsIgnoreCase("WEBSERVICES") || 
                            fedExCarrierMode.equalsIgnoreCase("FedexWebServices")) {
                            tempMap = aascFedexShipmentInfo.parseWebServiceResponse(shipmentResponse, 
                                                                                  aascShipmentOrderInfo, 
                                                                                  aascShipMethodInfo, 
                                                                                  aascProfileOptionsInfo, 
                                                                                  packageSequence, 
                                                                                  chkReturnlabelstr, 
                                                                                  cloudLabelPath);
                        } else {
                            tempMap = 
                                    aascFedexShipmentInfo.parseResponse(shipmentResponse, 
                                                                        aascShipmentOrderInfo, 
                                                                        aascShipMethodInfo, 
                                                                        aascProfileOptionsInfo, 
                                                                        packageSequence, 
                                                                        chkReturnlabelstr, 
                                                                        cloudLabelPath);
                        }


                        // }


                        hashMap = tempMap;
                        parseStatus = (String)tempMap.get("status");

                        if ("success".equalsIgnoreCase(parseStatus) || "WARNING".equalsIgnoreCase(parseStatus) || "NOTE".equalsIgnoreCase(parseStatus)) {
                            
                            responseStatus = 150;
                            tempMap.put("ResponseStatus", 
                                        String.valueOf(responseStatus));
                            hashMap = tempMap;
                            logger.info("response status:" + responseStatus);
                            aascShipmentHeaderInfo.setMainError("");
                            if("WARNING".equalsIgnoreCase(parseStatus)){
                                logger.info("Warning Message "+(String)tempMap.get("warningMsg"));
                                aascShipmentHeaderInfo.setMainError((String)tempMap.get("warningMsg"));
                            }
                            
                        } else {
                            
                            aascShipmentHeaderInfo.setMainError(parseStatus);
                            responseStatus = 151;
                            tempMap.put("ResponseStatus", 
                                        String.valueOf(responseStatus));
                            hashMap = tempMap;
                            
                        }
                    }
                }
            } catch (FedExAPIException e) {
                responseStatus = 151;
                aascShipmentHeaderInfo.setMainError(e.getMessage());
                logger.severe("FedExAPIException: " + e.getMessage());
            } catch (UnsupportedEncodingException e) {
                responseStatus = 151;
                aascShipmentHeaderInfo.setMainError(e.getMessage());
                logger.severe("UnsupportedEncodingException: " + 
                              e.getMessage());
            }
        } else {
            logger.severe("either port or host is null:" + "\n host:" + host + 
                          "\n port:" + port);
            aascShipmentHeaderInfo.setMainError("either port or host is null:" + 
                                                "\n host:" + host + 
                                                "\n port:" + port);
            responseStatus = 151;
            tempMap.put("ResponseStatus", String.valueOf(responseStatus));
            hashMap = tempMap;
        }
        logger.info("Exit from sendfedexRequest method");
    } // end of the method

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

    /** This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj of type Object
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /** method used to set parse error.
     * @param parseStatus String
     */
    public void setError(String parseStatus) {
        this.parseStatus = parseStatus;
    }

    /** method used to get parse error.
     * @return parseStatus String
     */
    public String getError() {
        return parseStatus;
    }

    /** method used to covert the request string to contain require format.
     * @param  str String 
     * @return returns String with required format
     * */
    private String escape(String str) {
        String result = null; // replace(str, "&", "&amp;");

        while (str.indexOf("&") != -1) {
            str = replace(str, "&", "&amp;");
        }
        result = str;
        while (result.indexOf("-") != -1) {
            result = replace(result, "-", "");
        }
        return result;
    }

    /** method used to replace a substring of a string with another substring.
     * @param str String containing from substring which is replaced by to substring
     * @return returns String with replaced values
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
     * Method to process the Return Shipments. To generate the Return Shipment labels
     */
    public void processReturnShipment() {
        
    }

    
     /** This method can be used to replace speccial characters in xml's.
      * @param  src String, 
      * @param  oldPattern String,
      * @param  newPattern String
      * @return String
      */
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

    /**
     * @param src String
     * @return src String
     */
    private String encode(String src) {
        src = replaceStr(src, "&", "&amp;");
        src = replaceStr(src, "\"", "&quot;");
        src = replaceStr(src, "'", "&apos;");
        src = replaceStr(src, "<", "&lt;");
        src = replaceStr(src, ">", "&gt;");
        return src;
    }



    /** creates an xml ShipRequest based on the PayorType
     * If the PayMethod is THIRDPARTY,RECIPIENT or COLLECT the request 
     * should contain their corresponding accountNumber and countryCode     
     * Information and so on  and passes the request to fedex atom server
     * and receives the Response from server.
     * @param aascShipmentOrderInfo AascShipmentOrderInfo Object to get the Order Information
     * @param aascShipMethodInfo AascShipMethodInfo Object to get the ShipMethod Information
     * @return Returns the int value , if 150 the response is success otherwise 
     * if value is 151 then there is an error in the response file.
     * */
    public int processShipment(AascShipmentOrderInfo aascShipmentOrderInfo, 
                               AascShipMethodInfo aascShipMethodInfo, 
                               AascIntlInfo aascIntlInfo, 
                               AascProfileOptionsBean aascProfileOptionsInfo, 
                               String fedExCarrierMode, String fedExKey, 
                               String fedExPassword, 
                               String cloudLabelPath) { 
        logger.info("Entered processShipment()" + fedExCarrierMode);
        String intFlag = "";
        if (returnShipment.equalsIgnoreCase("PRINTRETURNLABEL")) {
            fedExWSChkReturnlabelstr = "PRINTRETURNLABEL";
        } else {
            fedExWSChkReturnlabelstr = "NONRETURN";
        }


        this.fedExCarrierMode = fedExCarrierMode;
        this.fedExKey = fedExKey;
        this.fedExPassword = fedExPassword;

        try {
            
            outputFile = cloudLabelPath;
            try {
                intFlag = aascShipmentHeaderInfo.getInternationalFlag();
            } catch (Exception e) {
                intFlag = "";
            }

            shipmentRequest = ""; // String that holds shipmentRequest  
            aascShipmentHeaderInfo = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo(); // returns header info bean object
            shipPackageInfo = 
                    aascShipmentOrderInfo.getShipmentPackageInfo(); // returns the linkedlist contains the package info bean objects 

            int size = shipPackageInfo.size();

            Iterator packageIterator = shipPackageInfo.iterator();

            while (packageIterator.hasNext()) {
                AascShipmentPackageInfo shipPackageInfo = 
                    (AascShipmentPackageInfo)packageIterator.next();
                if ("PRINTRETURNLABEL".equalsIgnoreCase(shipPackageInfo.getReturnShipment())) {
                    size++;
                }

                if ("Y".equalsIgnoreCase(shipPackageInfo.getHazMatFlag())) {
                    size++;
                }
            }


            calendar = Calendar.getInstance();
            time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
            currentDate = new Date(stf.parse(time).getTime());
            shipFlag = aascShipmentHeaderInfo.getShipFlag();
            time = stf.format(currentDate);
            
            Timestamp time1 = aascShipmentHeaderInfo.getShipTimeStamp();
            logger.info("TimeStamp =========> ::" + time1);

            String timeStr = time1.toString();

            timeStr = timeStr.substring(11, timeStr.length() - 2);
            fedExWsTimeStr = nullStrToSpc(timeStr);
    

            //fedExWsTimeStr = "00:00:00";


            orderNumber = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo().getOrderNumber();

            customerTransactionIdentifier = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo().getOrderNumber();

            ListIterator packageInfoIterator = shipPackageInfo.listIterator();
            if (aascShipmentOrderInfo != null && 
                aascShipmentHeaderInfo != null && shipPackageInfo != null && 
                aascShipMethodInfo != null) {
                carrierId = aascShipmentHeaderInfo.getCarrierId();

                if (carrierPayMethodCode.equalsIgnoreCase("PP")) {

                    senderAccountNumber = 
                            nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber()); // FedEx Account number for prepaid from shipment page
                    
                    if (senderAccountNumber.length() < 9 || 
                        senderAccountNumber.length() > 12) {
                        aascShipmentHeaderInfo.setMainError("shipper's account number should not be less than 9 digits and greater than 12 digits ");
                        responseStatus = 151;
                        return responseStatus;
                    }
                } else {
                    senderAccountNumber = 
                            nullStrToSpc(aascShipMethodInfo.getCarrierAccountNumber(carrierId));
                    

                    

                    if (senderAccountNumber.length() < 9 || 
                        senderAccountNumber.length() > 12) {
                        aascShipmentHeaderInfo.setMainError("shipper's account number should not be less than 9 digits and greater than 12 digits ");
                        responseStatus = 151;
                        return responseStatus;
                    }

                }


                fedExTestMeterNumber = 
                        nullStrToSpc(aascShipMethodInfo.getMeterNumber(carrierId));
                
                shipToCompanyName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCustomerName()); // retreiving ship to company name from header bean  
                
                shipToCompanyName = encode(shipToCompanyName);

                shipToAddressLine1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getAddress()); // retreiving ship to address from header bean                              
                         shipToAddressLine1 = 
                                                  encode(shipToAddressLine1);  //added by Jagadish
                shipToAddressCity = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCity()); // retreiving ship to city from header bean                
                shipToAddressPostalCode = 
                        nullStrToSpc(nullStrToSpc(aascShipmentHeaderInfo.getPostalCode())); // retreiving ship to postal code from header bean               

                shipToAddressPostalCode = escape(shipToAddressPostalCode);

                shipToCountry = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCountrySymbol()).toUpperCase(); // retreiving ship to country name from header bean                
                        
                shipToEMailAddress = nullStrToSpc(aascShipmentHeaderInfo.getShipToEmailId());
                        
                shipToAddressState = 
                        nullStrToSpc(aascShipmentHeaderInfo.getState()).toUpperCase(); // retreiving ship to state from header bean                
                        
                residentialAddrFlag = aascShipmentHeaderInfo.getResidentialFlag();                         
                
                shipDate = 
                        aascShipmentHeaderInfo.getShipmentDate(); // retreiving ship date from header bean                
                shipFromAddressLine1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine1()); // indicates ship From address                   
                         shipFromAddressLine1 = 
                                                  encode(shipFromAddressLine1);  //added by Jagadish
                shipFromAddressLine2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine2()); // indicates ship From address                   
                         shipFromAddressLine2 = 
                                                  encode(shipFromAddressLine2);  //added by Jagadish                
                shipFromAddressCity = 
                        aascShipmentHeaderInfo.getShipFromCity(); // indicates ship From address city                    
                shipFromAddressPostalCode = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipFromPostalCode()); // indicates ship From postal code                
                shipFromPersonName = 
                        aascShipmentHeaderInfo.getShipFromContactName();
                //   System.out.println(":::::::::::::::::::::::::::::::::::::: shipFromPersonName :::::::::::::::::::::: -- > in Fedex shipment "+shipFromPersonName);
                shipFromAddressPostalCode = escape(shipFromAddressPostalCode);

                shipFromCountry = 
                        aascShipmentHeaderInfo.getShipFromCountry().toUpperCase(); // indicates ship From country                
                shipFromAddressState = 
                        aascShipmentHeaderInfo.getShipFromState().toUpperCase();
                shipFromDepartment = 
                        nullStrToSpc(aascShipmentHeaderInfo.getDepartment()); // Retrieving the shipfrom department        
                shipFromDepartment = encode(shipFromDepartment);
                shipMethodName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning()); // retreiving ship method meaning from header bean                           
                carrierCode = 
                        aascShipMethodInfo.getCarrierName(shipMethodName); // retreiving carrier code from ship method bean                                                      

                reference1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference1());
                reference1 = encode(reference1);
                reference2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference2());
                reference2 = encode(reference2);
                
                receipientPartyName = encode(aascShipmentHeaderInfo.getRecCompanyName());
                recipientPostalCode= encode(aascShipmentHeaderInfo.getRecPostalCode());
                //Mahesh added below code for Third Party development   
                tpCompanyName = encode(aascShipmentHeaderInfo.getTpCompanyName());
                tpAddress= encode(aascShipmentHeaderInfo.getTpAddress());
                tpCity= encode(aascShipmentHeaderInfo.getTpCity());
                tpState= encode(aascShipmentHeaderInfo.getTpState());
                tpPostalCode= encode(aascShipmentHeaderInfo.getTpPostalCode());
                tpCountrySymbol= encode(aascShipmentHeaderInfo.getTpCountrySymbol());
               
                // khaja added code 
                satShipFlag = 
                        nullStrToSpc(aascShipmentHeaderInfo.getSaturdayShipFlag()); // retreiving saturday ship flag from header bean         
                //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ satShipFlag :"+satShipFlag);
                // khaja added code end
                size = shipPackageInfo.size();

                while (packageInfoIterator.hasNext()) {
                    AascShipmentPackageInfo aascPackageBean = 
                        (AascShipmentPackageInfo)packageInfoIterator.next();

                    pkgWtUom = nullStrToSpc(aascPackageBean.getUom());
                    if (pkgWtUom.equalsIgnoreCase("LB") || 
                        pkgWtUom.equalsIgnoreCase("KG")) {
                        pkgWtUom = (pkgWtUom + "S").toUpperCase();
                    }
                    pkgWtVal = aascPackageBean.getWeight();
                    //pkgWtVal BY MADHAVI

                    DecimalFormat fmt = new DecimalFormat();
                    fmt.setMaximumFractionDigits(1);
                    String str = fmt.format(pkgWtVal);
                    if (carrierCode.equalsIgnoreCase("FDXG")) {
                        pkgWtVal = Float.parseFloat(str);
                        
                    } else {
                        
                    }

                    dimensions = nullStrToSpc(aascPackageBean.getDimension());
                    units = nullStrToSpc(aascPackageBean.getDimensionUnits());


                    if (dimensions != null && !dimensions.equals("")) {

                        index = dimensions.indexOf("*");
                        if (index != -1) {
                            length = dimensions.substring(0, index);
                        }
                        dimensions = dimensions.substring(index + 1);
                        index = dimensions.indexOf("*");
                        if (index != -1) {
                            width = dimensions.substring(0, index);
                        }
                        dimensions = dimensions.substring(index + 1);
                        height = dimensions;
                        
                    }
                    // Email Notification details
                     emailFlag = aascShipmentHeaderInfo.getEmailNotificationFlag();
                    SenderEmail = aascShipmentHeaderInfo.getShipFromEmailId();
                    ShipAlertNotification = aascShipmentHeaderInfo.getShipNotificationFlag();
                    ExceptionNotification = aascShipmentHeaderInfo.getExceptionNotification();
                    DeliveryNotification = aascShipmentHeaderInfo.getDeliveryNotification();
                    Format = aascShipmentHeaderInfo.getFormatType();
                    recipientEmailAddress1 = aascShipmentHeaderInfo.getShipToEmailId();
                    if(aascShipmentHeaderInfo.getEmailCustomerName().equalsIgnoreCase("Y")){
                        message = "/ Customer : "+nullStrToSpc(encode(aascShipmentHeaderInfo.getCustomerName()));
                    }
                    
                    if (aascShipmentHeaderInfo.getReference1Flag().equalsIgnoreCase("Y")) {
                        message = message + "/ Ref 1: "+nullStrToSpc(aascShipmentHeaderInfo.getReference1());
                    }
                    
                    if (aascShipmentHeaderInfo.getReference2Flag().equalsIgnoreCase("Y")) {
                        message = message + "/ Ref 2:" + nullStrToSpc(aascShipmentHeaderInfo.getReference2());
                    }
                    
                     // Email Notification details

                    codFlag = nullStrToSpc(aascPackageBean.getCodFlag());

                    if (codFlag.equalsIgnoreCase("Y")) {
                        
                        codAmt = aascPackageBean.getCodAmt();
                        
                        codAmtStr = String.valueOf(codAmt);

                        int index = codAmtStr.indexOf(".");

                        

                        if (index < 1) {
                            codAmtStr = codAmtStr + ".00";
                            
                        } else if ((codAmtStr.length() - index) > 2) {
                            codAmtStr = codAmtStr.substring(0, index + 3);
                            
                        } else {
                            while (codAmtStr.length() != (index + 3)) {
                                codAmtStr = codAmtStr + "0";
                                
                            }
                        }
                        codTag = 
                                "<COD>" + "<CollectionAmount>" + codAmtStr + "</CollectionAmount>" + 
                                "<CollectionType>ANY</CollectionType>" + 
                                "</COD>";
                    } else {
                        
                        codTag = "";
                    }

                    halPhone = aascPackageBean.getHalPhone();
                    halCity = aascPackageBean.getHalCity();
                    halState = aascPackageBean.getHalStateOrProvince();
                    halLine1 = aascPackageBean.getHalLine1();
                    halLine2 = aascPackageBean.getHalLine2();
                    halZip = aascPackageBean.getHalPostalCode();
                    halFlag = aascPackageBean.getHalFlag();

                    dryIceUnits = aascPackageBean.getDryIceUnits();
                    chDryIce = aascPackageBean.getDryIceChk();
                    dryIceWeight = aascPackageBean.getDryIceWeight();
                    
                    logger.info("DryIce Flag=" + chDryIce);
                    if (chDryIce.equalsIgnoreCase("Y")) {
                     logger.info("DryIce Flag is Y");
                     dryIceTag = 
                             "<DryIce><WeightUnits>" + dryIceUnits + "</WeightUnits><Weight>" + 
                             dryIceWeight + "</Weight></DryIce>";
                    } else {
                     logger.info("DryIce Flag is N");
                     dryIceTag = "";
                    }



                    String halLine2Tag = "";

                    if (halLine2.equalsIgnoreCase("") || halLine2 == null) {
                        halLine2Tag = "";
                    } else {
                        halLine2Tag = "<Line2>" + halLine2 + "</Line2>";
                    }


                    if (halFlag.equalsIgnoreCase("Y")) {
                        
                        hal = 
"<HoldAtLocation><PhoneNumber>" + halPhone + "</PhoneNumber>" + 
  "<Address><Line1>" + halLine1 + "</Line1>" + halLine2Tag + "<City>" + 
  halCity + "</City>" + "<StateOrProvinceCode>" + halState + 
  "</StateOrProvinceCode>" + "<PostalCode>" + halZip + 
  "</PostalCode></Address></HoldAtLocation>";

                    } else {
                        
                        hal = "";
                    }

                    HazMatFlag = aascPackageBean.getHazMatFlag();
                    HazMatType = aascPackageBean.getHazMatType();
                    HazMatClass = aascPackageBean.getHazMatClass();
                    

                    String HazMatCertData = "";
                    String ShippingName = "";
                    String ShippingName1 = "";
                    String ShippingName2 = "";
                    String ShippingName3 = "";
                    String Class = "";

                    if (HazMatFlag.equalsIgnoreCase("Y")) {
                        
                        if (!HazMatClass.equalsIgnoreCase("")) {
                            
                            int classIndex = HazMatClass.indexOf("Class", 1);
                            if (classIndex == -1) {
                                classIndex = HazMatClass.indexOf("CLASS", 1);
                                
                            }
                            
                            int firstIndex = 0;
                            

                            firstIndex = HazMatClass.indexOf("-");
                            
                            String HazMatClassStr = 
                                HazMatClass.substring(0, firstIndex);
                            
                            if (classIndex == -1) {
                                
                                ShippingName = "";
                                
                                
                                try {
                                    Class = 
                                            trim(HazMatClassStr.substring(HazMatClassStr.indexOf(" "), 
                                                                          HazMatClassStr.length()));
                                } catch (Exception e) {
                                    Class = "";
                                    firstIndex = -1;
                                }

                            } else {
                                
                                ShippingName = 
                                        HazMatClass.substring(0, classIndex - 
                                                              1);
                                
                                Class = 
                                        trim(HazMatClassStr.substring(HazMatClassStr.lastIndexOf(" "), 
                                                                      HazMatClassStr.length()));
                            }
                            
                            if (HazMatClass.length() > firstIndex + 1 + 100) {
                                ShippingName1 = 
                                        HazMatClass.substring(firstIndex + 1, 
                                                              firstIndex + 1 + 
                                                              50);
                                ShippingName2 = 
                                        HazMatClass.substring(firstIndex + 1 + 
                                                              50, 
                                                              firstIndex + 1 + 
                                                              100);
                                ShippingName3 = 
                                        HazMatClass.substring(firstIndex + 1 + 
                                                              100, 
                                                              HazMatClass.length());
                            } else if (HazMatClass.length() > 
                                       firstIndex + 1 + 50) {
                                ShippingName1 = 
                                        HazMatClass.substring(firstIndex + 1, 
                                                              firstIndex + 1 + 
                                                              50);
                                ShippingName2 = 
                                        HazMatClass.substring(firstIndex + 1 + 
                                                              50, 
                                                              HazMatClass.length());
                            } else if (HazMatClass.length() <= 
                                       firstIndex + 1 + 50) {
                                ShippingName1 = 
                                        HazMatClass.substring(firstIndex + 1, 
                                                              HazMatClass.length());
                            }
                            
                            fedExWsShippingName = ShippingName;
                            fedExWsShippingName1 = ShippingName1+ShippingName2+ShippingName3;
                            fedExWsShippingName2 = ShippingName2;
                            fedExWsShippingName3 = ShippingName3;
                            fedExWsClass = Class;
                            HazMatCertData = 
                                    "<HazMatCertificateData>" + "<DOTProperShippingName>" + 
                                    ShippingName + "</DOTProperShippingName>" + 
                                    "<DOTProperShippingName>" + ShippingName1 + 
                                    "</DOTProperShippingName>" + 
                                    "<DOTProperShippingName>" + ShippingName2 + 
                                    "</DOTProperShippingName>" + 
                                    "<DOTProperShippingName>" + ShippingName3 + 
                                    "</DOTProperShippingName>" + 
                                    "<DOTHazardClassOrDivision>" + Class + 
                                    "</DOTHazardClassOrDivision>";
                            //  "</HazMatCertificateData>";

                        }


                        HazMatQty = aascPackageBean.getHazMatQty();
                        HazMatUnit = aascPackageBean.getHazMatUnit();

                        HazMatIdentificationNo = 
                                aascPackageBean.getHazMatIdNo();
                        HazMatEmergencyContactNo = 
                                aascPackageBean.getHazMatEmerContactNo();
                        HazMatEmergencyContactName = 
                                aascPackageBean.getHazMatEmerContactName();
                        HazardousMaterialPkgGroup = 
                                nullStrToSpc(aascPackageBean.getHazMatPkgGroup());

                        // Added on Jul-05-2011
                        try {
                            hazmatPkgingCnt = 
                                    aascPackageBean.getHazmatPkgingCnt();
                        } catch (Exception e) {
                            hazmatPkgingCnt = 0.0;
                        }
                        hazmatPkgingUnits = 
                                nullStrToSpc(aascPackageBean.getHazmatPkgingUnits());
                        hazmatTechnicalName = 
                                nullStrToSpc(aascPackageBean.getHazmatTechnicalName());
                        //End on Jul-05-2011
                        hazmatSignatureName = 
                                nullStrToSpc(aascPackageBean.getHazmatSignatureName());

                        /* if(HazardousMaterialPkgGroup.equalsIgnoreCase(""))
                          {
                          HazardousMaterialPkgGroup = aascPackageBean.getHazMatPkgGroup();
                          }
                          else {
                              HazardousMaterialPkgGroup="";
                          }*/
                        HazMatDOTLabelType = 
                                aascPackageBean.getHazMatDOTLabel();
                        HazardousMaterialId = aascPackageBean.getHazMatId();

                        String AccessibilityTag = 
                            "<Accessibility>" + HazMatType + 
                            "</Accessibility>";
                        String additionalTag = "";
                        String additionalTag1 = "";

                        if (carrierCode.equalsIgnoreCase("FDXG")) {
                            
                            AccessibilityTag = "";
                            additionalTag = 
                                    "<Quantity>" + HazMatQty + "</Quantity><Units>" + 
                                    HazMatUnit + 
                                    "</Units></HazMatCertificateData>";
                            additionalTag1 = 
                                    "<DOTIDNumber>" + HazMatIdentificationNo + 
                                    "</DOTIDNumber>" + "<PackingGroup>" + 
                                    HazardousMaterialPkgGroup + 
                                    "</PackingGroup>" + "<DOTLabelType>" + 
                                    HazMatDOTLabelType + "</DOTLabelType>" + 
                                    "<TwentyFourHourEmergencyResponseContactName>" + 
                                    HazMatEmergencyContactName + 
                                    "</TwentyFourHourEmergencyResponseContactName>" + 
                                    "<TwentyFourHourEmergencyResponseContactNumber>" + 
                                    HazMatEmergencyContactNo + 
                                    " </TwentyFourHourEmergencyResponseContactNumber>";

                            additionalTag = 
                                    additionalTag1 + "<Quantity>" + HazMatQty + 
                                    "</Quantity><Units>" + HazMatUnit + 
                                    "</Units></HazMatCertificateData>";

                        } else {
                            
                            additionalTag = "" + "</HazMatCertificateData>";
                        }

                        HazMat = 
                                "<DangerousGoods>" + AccessibilityTag + HazMatCertData + 
                                additionalTag + "</DangerousGoods>";

                    } else {
                        
                        HazMat = "";
                    }

                    // Added code for dimensions 
                    packageLength = aascPackageBean.getPackageLength();
                    packageWidth = aascPackageBean.getPackageWidth();
                    packageHeight = aascPackageBean.getPackageHeight();
                    units = nullStrToSpc(aascPackageBean.getDimensionUnits());

                    //System.out.println("packageLength----------------------------in fedex shipment::"+packageLength);
                    // Added for dimensions 
                    if (packageLength != 0 && packageWidth != 0 && 
                        packageHeight != 0 && packageLength != 0.0 && 
                        packageWidth != 0.0 && packageHeight != 0.0) {
                        header9 = 
                                "<Dimensions>" + "<Length>" + (int)packageLength + 
                                "</Length>" + "<Width>" + (int)packageWidth + 
                                "</Width>" + "<Height>" + (int)packageHeight + 
                                "</Height>" + "<Units>" + units + "</Units>" + 
                                "</Dimensions>";
                    } else {
                        header9 = "";
                    }

                    signatureOptions = 
                            nullStrToSpc(aascPackageBean.getSignatureOptions());


                    returnShipment = 
                            nullStrToSpc(aascPackageBean.getReturnShipment());
                    if (returnShipment.equalsIgnoreCase("PRINTRETURNLABEL")) {
                        rtnShipFromCompany = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromCompany().trim()));
                        
                        rtnShipToCompany = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToCompany().trim()));
                        rtnShipFromContact = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromContact().trim()));
                        rtnShipToContact = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToContact().trim()));

                        if (rtnShipToContact.equalsIgnoreCase("") || 
                            rtnShipToContact == null) {
                            rtnTagshipToContactPersonName = "";
                        } else {
                            rtnTagshipToContactPersonName = 
                                    "<PersonName>" + rtnShipToContact + 
                                    "</PersonName>";
                        }

                        rtnShipFromLine1 = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromLine1().trim()));

                        rtnShipToLine1 = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToLine1().trim()));
                        rtnShipFromLine2 = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromLine2().trim()));

                        rtnShipToLine2 = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToLine2().trim()));
                        rtnShipFromCity = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromCity().trim()));
                        rtnShipFromSate = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromSate().trim()));
                        rtnShipFromZip = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromZip()));
                        rtnShipToCity = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToCity().trim()));
                        rtnShipToState = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToState().trim()));
                        rtnShipToZip = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToZip()));
                        rtnShipFromPhone = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipFromPhone().trim()));

                        rtnShipFromPhone = rtnShipFromPhone.replace("(", "");
                        rtnShipFromPhone = rtnShipFromPhone.replace(")", "");
                        rtnShipFromPhone = rtnShipFromPhone.replace("-", "");

                        rtnShipToPhone = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipToPhone().trim()));

                        rtnShipToPhone = rtnShipToPhone.replace("(", "");
                        rtnShipToPhone = rtnShipToPhone.replace(")", "");
                        rtnShipToPhone = rtnShipToPhone.replace("-", "");

                        rtnShipMethod = 
                                encode(nullStrToSpc(aascPackageBean.getRtnShipMethod()));
                        rtnDropOfType = 
                                encode(nullStrToSpc(aascPackageBean.getRtnDropOfType()));
                        rtnPackageList = 
                                encode(nullStrToSpc(aascPackageBean.getRtnPackageList()));
                        //rtnPayMethod=nullStrToSpc(aascPackageBean.getRtnPayMethod());
                        rtnPayMethod = 
                                encode((String)carrierPayMethodCodeMap.get(nullStrToSpc(aascPackageBean.getRtnPayMethod())));
                        rtnPayMethodCode = 
                                encode(nullStrToSpc(aascPackageBean.getRtnPayMethodCode()));
                        rtnACNumber = 
                                encode(nullStrToSpc(aascPackageBean.getRtnACNumber().trim()));
                        rtnRMA = 
                                encode(nullStrToSpc(aascPackageBean.getRtnRMA().trim()));
                    }
                    // rtnTrackingNumber=nullStrToSpc(aascPackageBean.getRtnTrackingNumber().trim());
                    //18/07/07(end)
                    //25/07/07(start)
                    String rtnShipTagToContact = "";
                    String rtnshipToContactPersonName = "";
                    String rtnShipTagFromContact = "";
                    String rtnshipFromContactPersonName = "";
                    if (rtnShipToCompany.equalsIgnoreCase("") || 
                        rtnShipToCompany == null) {

                        if (rtnShipToContact.equalsIgnoreCase("") || 
                            rtnShipToContact == null) {
                            rtnShipTagToContact = "";
                        } else {
                            rtnShipTagToContact = 
                                    "<PersonName>" + rtnShipToContact + 
                                    "</PersonName>";
                        }


                    } else {

                        if (rtnShipToContact.equalsIgnoreCase("") || 
                            rtnShipToContact == null) {
                            rtnshipToContactPersonName = "";
                        } else {
                            rtnshipToContactPersonName = 
                                    "<PersonName>" + rtnShipToContact + 
                                    "</PersonName>";

                        }
                        rtnShipTagToContact = 
                                rtnshipToContactPersonName + "<CompanyName>" + 
                                rtnShipToCompany + "</CompanyName>";

                    }


                    if (rtnShipFromCompany.equalsIgnoreCase("") || 
                        rtnShipFromCompany == null) {

                        if (rtnShipFromContact.equalsIgnoreCase("") || 
                            rtnShipFromContact == null) {
                            rtnShipTagFromContact = "";
                        } else {
                            rtnShipTagFromContact = 
                                    "<PersonName>" + rtnShipFromContact + 
                                    "</PersonName>";
                        }


                    } else {

                        if (rtnShipFromContact.equalsIgnoreCase("") || 
                            rtnShipFromContact == null) {
                            rtnshipFromContactPersonName = "";
                        } else {
                            rtnshipFromContactPersonName = 
                                    "<PersonName>" + rtnShipFromContact + 
                                    "</PersonName>";

                        }
                        rtnShipTagFromContact = 
                                rtnshipFromContactPersonName + "<CompanyName>" + 
                                rtnShipFromCompany + "</CompanyName>";

                    }
                    //25/07/07(end)


                    //24/07/07(start)
                    String toLine2Tag = "";

                    if (rtnShipToLine2.equalsIgnoreCase("") || 
                        rtnShipToLine2 == null) {
                        toLine2Tag = "";
                    } else {
                        toLine2Tag = "<Line2>" + rtnShipToLine2 + "</Line2>";
                    }

                    String fromLine2Tag = "";

                    if (rtnShipFromLine2.equalsIgnoreCase("") || 
                        rtnShipFromLine2 == null) {
                        fromLine2Tag = "";
                    } else {
                        fromLine2Tag = 
                                "<Line2>" + rtnShipFromLine2 + "</Line2>";
                    }
                    //24/07/07(end)
                    if (rtnRMA != null && !(rtnRMA.equalsIgnoreCase(""))) {
                        rmaTag = 
                                "<RMA>" + "<Number>" + rtnRMA + "</Number>" + "</RMA>";
                    } else {
                        rmaTag = "";
                    }
                    packageDeclaredValue = 
                            aascPackageBean.getPackageDeclaredValue();
                    
                    rtnPackageDeclaredValue = 
                            aascPackageBean.getRtnDeclaredValue();
                    
                    rtnPackageDeclaredValueStr = 
                            String.valueOf(rtnPackageDeclaredValue);

                    int indexRtr = rtnPackageDeclaredValueStr.indexOf(".");

                    if (indexRtr < 1) {
                        rtnPackageDeclaredValueStr = 
                                rtnPackageDeclaredValueStr + ".00";

                    } else if ((rtnPackageDeclaredValueStr.length() - 
                                indexRtr) > 2) {
                        rtnPackageDeclaredValueStr = 
                                rtnPackageDeclaredValueStr.substring(0, 
                                                                     index + 3);

                    } else {
                        while (rtnPackageDeclaredValueStr.length() != 
                               (indexRtr + 3)) {
                            rtnPackageDeclaredValueStr = 
                                    rtnPackageDeclaredValueStr + "0";

                        }
                    }
                    //31/07/07(end)

                    packageDeclaredValueStr = 
                            String.valueOf(packageDeclaredValue);
                    int index = packageDeclaredValueStr.indexOf(".");


                    if (index < 1) {
                        packageDeclaredValueStr = 
                                packageDeclaredValueStr + ".00";

                    } else if ((packageDeclaredValueStr.length() - index) > 
                               2) {
                        packageDeclaredValueStr = 
                                packageDeclaredValueStr.substring(0, 
                                                                  index + 3);

                    } else {
                        while (packageDeclaredValueStr.length() != 
                               (index + 3)) {
                            packageDeclaredValueStr = 
                                    packageDeclaredValueStr + "0";

                        }
                    }
                    /*End 15-04-09   */


                    packageSequence = 
                            nullStrToSpc(aascPackageBean.getPackageSequence());
                    
                    packageFlag = nullStrToSpc(aascPackageBean.getVoidFlag());
                    
                    packageTrackinNumber = 
                            nullStrToSpc(aascPackageBean.getTrackingNumber());

                    packageCount = 
                            nullStrToSpc(aascPackageBean.getPackageCount());

                    

                    if (!packageCount.equalsIgnoreCase("1")) {

                        shipmentWeight = 
                                nullStrToSpc(String.valueOf(aascPackageBean.getWeight()));

                        

                        if (packageSequence.equalsIgnoreCase("1")) {
                            if (carrierCode.equalsIgnoreCase("FDXG") || 
                                (carrierCode.equalsIgnoreCase("FDXE") && 
                                 codFlag.equalsIgnoreCase("Y")) || 
                                (carrierCode.equalsIgnoreCase("FDXE") && 
                                 intFlag.equalsIgnoreCase("Y"))) {
                                shipMultiPieceFlag = 1;
                            }
                            masterTrackingNumber = "";
                            masterFormID = "";
                            /*shipWtTag = "<ShipmentWeight>"
                                    + aascHeaderInfo.getPackageWeight()
                                    + "</ShipmentWeight>"; */
                        } else {
                            if (shipFlag.equalsIgnoreCase("Y")) {
                                masterTrackingNumber = 
                                        nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getWayBill()));
                                masterFormID = 
                                        nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getMasterFormId()));
                                
                            } else {
                                masterTrackingNumber = 
                                        nullStrToSpc((String)hashMap.get("masterTrkNum"));

                                
                                if (masterTrackingNumber == "" || 
                                    masterTrackingNumber.equalsIgnoreCase("")) {
                                    masterTrackingNumber = 
                                            nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getWayBill()));

                                    //aascShipmentHeaderInfo.setWayBill(masterTrackingNumber);

                                }
                                //26/07/07(end)
                                masterFormID = 
                                        nullStrToSpc((String)hashMap.get("masterFormId"));
                                //shipWtTag = "";
                            }

                        }

                        if (shipMultiPieceFlag == 
                            1) { //shipWtTag + // "<ShipmentWeight>"+aascHeaderInfo.getPackageWeight()+"</ShipmentWeight>"+
                            part1 = 
                                    "<MultiPiece>" + "<PackageCount>" + packageCount + 
                                    "</PackageCount>" + 
                                    "<PackageSequenceNumber>" + 
                                    packageSequence + 
                                    "</PackageSequenceNumber>" + 
                                    "<MasterTrackingNumber>" + 
                                    masterTrackingNumber + 
                                    "</MasterTrackingNumber>";
                            part2 = "";
                            if (carrierCode.equalsIgnoreCase("FDXE")) {
                                part2 = 
                                        "<MasterFormID>" + masterFormID + "</MasterFormID></MultiPiece>";
                            } else {
                                part2 = "</MultiPiece>";
                            }

                            header4 = part1 + part2;
                        }

                        else {
                            header4 = "";
                        }
                    }


                    chkReturnlabel = "NONRETURN";

                    responseStatus = 
                            setCarrierLevelInfo1(aascShipmentHeaderInfo, 
                                                 aascPackageBean, 
                                                 aascShipMethodInfo, 
                                                 chkReturnlabel); //calling local method
                    int ctr = 0;
                    if (responseStatus == 151 && 
                        !(shipFlag.equalsIgnoreCase("Y"))) {
                        //    aascHeaderInfo.setWayBill("");     
                        packageInfoIterator = shipPackageInfo.listIterator();

                        while (packageInfoIterator.hasNext()) {
                            ctr = ctr + 1;
                            aascPackageBean = 
                                    (AascShipmentPackageInfo)packageInfoIterator.next();
                            aascPackageBean.setMasterTrackingNumber("");
                            aascPackageBean.setMasterFormID("");
                            // aascPackageBean.setTrackingNumber("");
                            aascPackageBean.setPkgCost(0.0);
                            aascPackageBean.setRtnShipmentCost(0.0);
                            aascPackageBean.setRtnTrackingNumber("");
                            aascPackageBean.setSurCharges(0.0);
                        }
                        return responseStatus;
                    }
                    if (responseStatus == 151 && 
                        shipFlag.equalsIgnoreCase("Y")) {

                        packageInfoIterator = shipPackageInfo.listIterator();
                        while (packageInfoIterator.hasNext()) {
                            ctr = ctr + 1;
                            aascPackageBean = 
                                    (AascShipmentPackageInfo)packageInfoIterator.next();

                        }
                        return responseStatus;
                    }

                    // }//End of Iterator        

                    customerCarrierAccountNumber = 
                            aascShipmentHeaderInfo.getCarrierAccountNumber(); // FedEx Account number of the client                
                    // Modified to retrieve the Ship From company name from Profile Options instead of from Header Info
                    // shipFromCompanyName =  nullStrToSpc(aascProfileOptionsInfo.getCompanyName());
                    shipFromCompanyName = 
                            aascShipmentHeaderInfo.getShipFromCompanyName();
                    logger.info("ship from company name::::" + 
                                       shipFromCompanyName);
                    shipFromCompanyName = encode(shipFromCompanyName);
                    
                    shipFromPhoneNumber1 = 
                            nullStrToSpc(aascShipmentHeaderInfo.getShipFromPhoneNumber1());
                    //start
                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace("(", "");
                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace(")", "");
                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace("-", "");

                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace("(", "");
                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace(")", "");
                    shipFromPhoneNumber1 = 
                            shipFromPhoneNumber1.replace("-", "");

                    shipFromPhoneNumber1 = escape(shipFromPhoneNumber1);

                    shipToContactPhoneNumber = 
                            nullStrToSpc(aascShipmentHeaderInfo.getPhoneNumber()); // retreiving phone number from header bean               

                    shipToContactPhoneNumber = 
                            escape(shipToContactPhoneNumber);
                    
                    if (shipToContactPhoneNumber.equalsIgnoreCase("")) {
                        shipToContactPhoneNumber = shipFromPhoneNumber1;
                    }

                    shipToContactPhoneNumber = 
                            shipToContactPhoneNumber.replace("(", "");
                    shipToContactPhoneNumber = 
                            shipToContactPhoneNumber.replace(")", "");
                    shipToContactPhoneNumber = 
                            shipToContactPhoneNumber.replace("-", "");
                    
                    shipFromEMailAddress = nullStrToSpc(aascShipmentHeaderInfo.getShipFromEmailId());
                    
                    carrierPayMethod = 
                            (String)carrierPayMethodCodeMap.get(nullStrToSpc(aascShipmentHeaderInfo.getCarrierPaymentMethod()));
                    carrierPayMethodCode = 
                            nullStrToSpc(aascShipMethodInfo.getCarrierPayCode(aascShipmentHeaderInfo.getCarrierPaymentMethod()));
                    if ("RECIPIENT".equalsIgnoreCase(carrierPayMethod) && 
                        "FC".equalsIgnoreCase(carrierPayMethodCode)) {
                        carrierPayMethodCode = "CG";
                    }
                    dropoffType = 
                            nullStrToSpc(aascShipmentHeaderInfo.getDropOfType());
                    service = 
                            aascShipMethodInfo.getConnectShipScsTag(shipMethodName);
                    packaging = 
                            nullStrToSpc(aascShipmentHeaderInfo.getPackaging());
                    portString = aascShipMethodInfo.getCarrierPort(carrierId);
                    if (portString != null && !(portString.equals(""))) {
                        port = Integer.parseInt(portString);
                    } else {
                        logger.severe("portString is null " + portString);
                    }
                    host = 
aascShipMethodInfo.getCarrierServerIPAddress(carrierId);

                    if (carrierCode.equalsIgnoreCase("FDXE") && 
                        carrierPayMethodCode.equalsIgnoreCase("FC")) {
                        aascShipmentHeaderInfo.setMainError("bill to type of collect is for ground services only ");
                        responseStatus = 151;
                        return responseStatus;
                    }
                    //By Madhavi
                    String line2Tag = "";
                    shipToAddressLine2 = 
                            nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine2());
                    shipToAddressLine2 = shipToAddressLine2.trim();
                    shipToAddressLine2=encode(shipToAddressLine2); //added by Jagadish
                    
                    if (shipToAddressLine2.equalsIgnoreCase("") || 
                        shipToAddressLine2 == null) {
                        line2Tag = "";
                    } else {
                        line2Tag = "<Line2>" + shipToAddressLine2 + "</Line2>";
                    }
                    op900LabelFormat = 
                            nullStrToSpc(aascShipMethodInfo.getHazmatOp900LabelFormat(carrierId));
                    
                    intFlag = aascShipmentHeaderInfo.getInternationalFlag();
                    LinkedList coList = null;
                    try {
                        aascIntlHeaderInfo = aascIntlInfo.getIntlHeaderInfo();
                        coList = aascIntlInfo.getIntlCommodityInfo();
                    } catch (Exception e) {
                        aascIntlHeaderInfo = new AascIntlHeaderInfo();
                        coList = new LinkedList();
                    }
                    if (intFlag.equalsIgnoreCase("Y")) {
                        
                        intPayerType = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlPayerType());
                        
                        intAccNumber = nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber());
//                                nullStrToSpc(aascIntlHeaderInfo.getIntlAccountNumber());
//logger.info("intAccNumber:2167::"+intAccNumber);
                      //  logger.info("nullStrToSpc(aascIntlHeaderInfo.getIntlAccountNumber()):2168::"+nullStrToSpc(aascIntlHeaderInfo.getIntlAccountNumber()));

                        intMaskAccNumber = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlMaskAccountNumber());
                        intcountryCode = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlCountryCode());
                        
                        intTermsOfSale = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlTermsOfSale());
                        intTotalCustomsValue = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlTotalCustomsValue());
                        intFreightCharge = 
                                aascIntlHeaderInfo.getIntlFreightCharge();
                        intInsuranceCharge = 
                                aascIntlHeaderInfo.getIntlInsuranceCharge();
                        intTaxesOrMiscellaneousCharge = 
                                aascIntlHeaderInfo.getIntlTaxMiscellaneousCharge();
                        intPurpose = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlPurpose());
                        intSenderTINOrDUNS = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlSedNumber());
                        intSenderTINOrDUNSType = 
                                nullStrToSpc(aascIntlHeaderInfo.getIntlSedType());
                        packingListEnclosed = 
                                aascIntlHeaderInfo.getPackingListEnclosed();
                        shippersLoadAndCount = 
                                aascIntlHeaderInfo.getShippersLoadAndCount();
                        bookingConfirmationNumber = 
                                aascIntlHeaderInfo.getBookingConfirmationNumber();
                        generateCI = aascIntlHeaderInfo.getGenerateCI();
                        declarationStmt = 
                                aascIntlHeaderInfo.getIntlDeclarationStmt();
                                
                        importerName = aascIntlHeaderInfo.getImporterName();
                        importerCompName = 
                                aascIntlHeaderInfo.getImporterCompName();
                        importerAddress1 = 
                                aascIntlHeaderInfo.getImporterAddress1();
                        importerAddress2 = 
                                aascIntlHeaderInfo.getImporterAddress2();
                        importerCity = aascIntlHeaderInfo.getImporterCity();
                        importerCountryCode = 
                                aascIntlHeaderInfo.getImporterCountryCode();
                        
                        importerPhoneNum = 
                                aascIntlHeaderInfo.getImporterPhoneNum();
                        
                        importerPostalCode = 
                                aascIntlHeaderInfo.getImporterPostalCode();
                        importerState = aascIntlHeaderInfo.getImporterState();
                        
                        impIntlSedNumber = 
                                aascIntlHeaderInfo.getImpIntlSedNumber();
                        
                        impIntlSedType = 
                                aascIntlHeaderInfo.getImpIntlSedType();
                        
                        recIntlSedNumber = 
                                aascIntlHeaderInfo.getRecIntlSedNumber();
                        recIntlSedType = 
                                aascIntlHeaderInfo.getRecIntlSedType();
                        
                        brokerName = aascIntlHeaderInfo.getBrokerName();
                        brokerCompName = 
                                aascIntlHeaderInfo.getBrokerCompName();
                        
                        brokerAddress1 = 
                                aascIntlHeaderInfo.getBrokerAddress1();
                        
                        brokerAddress2 = 
                                aascIntlHeaderInfo.getBrokerAddress2();
                        
                        brokerCity = aascIntlHeaderInfo.getBrokerCity();
                        
                        brokerCountryCode = 
                                aascIntlHeaderInfo.getBrokerCountryCode();
                        
                        brokerPhoneNum = 
                                aascIntlHeaderInfo.getBrokerPhoneNum();
                        
                        brokerPostalCode = 
                                aascIntlHeaderInfo.getBrokerPostalCode();
                        
                        brokerState = aascIntlHeaderInfo.getBrokerState();
                        

                        recIntlSedType = 
                                aascIntlHeaderInfo.getRecIntlSedType();
                        

                        ListIterator CoInfoIterator = coList.listIterator();

                        intHeader6 = "";
                        String harCode = "";
                        String uPrice = "";
                        String exportLicense = "";

                        while (CoInfoIterator.hasNext()) {
                            AascIntlCommodityInfo aascIntlCommodityInfo = 
                                (AascIntlCommodityInfo)CoInfoIterator.next();

                            numberOfPieces = 
                                    aascIntlCommodityInfo.getNumberOfPieces();
                            description = 
                                    encode(aascIntlCommodityInfo.getDescription());
                            countryOfManufacture = 
                                    aascIntlCommodityInfo.getCountryOfManufacture();
                            harmonizedCode = 
                                    aascIntlCommodityInfo.getHarmonizedCode();
                            weight = aascIntlCommodityInfo.getWeight();
                            quantity = aascIntlCommodityInfo.getQuantity();
                            quantityUnits = 
                                    aascIntlCommodityInfo.getQuantityUnits();
                            unitPrice = aascIntlCommodityInfo.getUnitPrice();
                            customsValue = 
                                    aascIntlCommodityInfo.getCustomsValue();
                            exportLicenseNumber = 
                                    aascIntlCommodityInfo.getExportLicenseNumber();
                            exportLicenseExpiryDate = 
                                    aascIntlCommodityInfo.getExportLicenseExpiryDate();
                            String rdate = "";

                            try {
                                //String mon[]={"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
                                //                                 String exportLicenseExpiryDateStr = 
                                //                                     exportLicenseExpiryDate.substring(0, 1);
                                String convertDate = exportLicenseExpiryDate;

                                // 18-FEB-08  2008-02-18
                                int len = convertDate.length();
                                int indexs = convertDate.indexOf('-');
                                int index1 = convertDate.lastIndexOf('-');

                                String syear = 
                                    convertDate.substring(index1 + 1, 
                                                          len).trim();
                                String sdate = 
                                    convertDate.substring(0, indexs).trim();
                                String smon = 
                                    convertDate.substring(indexs + 1, index1).trim();
                                String intMonth = "";
                                if (smon.equalsIgnoreCase("JAN"))
                                    intMonth = "01";
                                else if (smon.equalsIgnoreCase("FEB"))
                                    intMonth = "02";
                                else if (smon.equalsIgnoreCase("MAR"))
                                    intMonth = "03";
                                else if (smon.equalsIgnoreCase("APR"))
                                    intMonth = "04";
                                else if (smon.equalsIgnoreCase("MAY"))
                                    intMonth = "05";
                                else if (smon.equalsIgnoreCase("JUN"))
                                    intMonth = "06";
                                else if (smon.equalsIgnoreCase("JUL"))
                                    intMonth = "07";
                                else if (smon.equalsIgnoreCase("AUG"))
                                    intMonth = "08";
                                else if (smon.equalsIgnoreCase("SEP"))
                                    intMonth = "09";
                                else if (smon.equalsIgnoreCase("OCT"))
                                    intMonth = "10";
                                else if (smon.equalsIgnoreCase("NOV"))
                                    intMonth = "11";
                                else if (smon.equalsIgnoreCase("DEC"))
                                    intMonth = "12";

                                rdate = 
                                        "20" + syear + '-' + intMonth + '-' + sdate;
                                rdate = exportLicenseExpiryDate;

                            } catch (Exception e) {
                                exportLicenseExpiryDate = "";
                                rdate = "";
                            }

                            try {
                                //                                 String harmonizedCodeStr = 
                                //                                     harmonizedCode.substring(0, 1);
                            } catch (Exception e) {
                                harmonizedCode = "";
                            }
                            if (harmonizedCode.equalsIgnoreCase("")) {
                                harCode = "";
                            } else {
                                harCode = 
                                        "<HarmonizedCode>" + harmonizedCode + "</HarmonizedCode>";
                            }
                            if (unitPrice.equalsIgnoreCase("")) {
                                uPrice = "";
                            } else {
                                uPrice = 
                                        "<UnitPrice>" + unitPrice + "</UnitPrice>";
                            }
                            try {
                                //                                 String expNoStr = 
                                //                                     exportLicenseNumber.substring(0, 1);

                            } catch (Exception e) {
                                exportLicenseNumber = "";
                            }
                            if (exportLicenseNumber.equalsIgnoreCase("")) {
                                exportLicense = "";
                            } else {
                                exportLicense = 
                                        "<ExportLicenseNumber>" + exportLicenseNumber + 
                                        "</ExportLicenseNumber>" + 
                                        "<ExportLicenseExpirationDate>" + 
                                        rdate + 
                                        "</ExportLicenseExpirationDate>";
                            }


                            intHeader6 = 
                                    intHeader6 + "<Commodity>" + "<NumberOfPieces>" + 
                                    numberOfPieces + "</NumberOfPieces>" + 
                                    "<Description>" + description + 
                                    "</Description>" + 
                                    "<CountryOfManufacture>" + 
                                    countryOfManufacture + 
                                    "</CountryOfManufacture>" + harCode + 
                                    "<Weight>" + weight + "</Weight>" + 
                                    "<Quantity>" + quantity + "</Quantity> " + 
                                    "<QuantityUnits>" + quantityUnits + 
                                    "</QuantityUnits>" + uPrice + 
                                    "<CustomsValue>" + customsValue + 
                                    "</CustomsValue>" + exportLicense + 
                                    "</Commodity>";


                        }

                        if (intPayerType.equalsIgnoreCase("THIRDPARTY")) {
                            intHeader1 = 
                                    "<DutiesPayor><AccountNumber>" + intAccNumber + 
                                    "</AccountNumber>" + "<CountryCode>" + 
                                    intcountryCode + "</CountryCode>" + 
                                    "</DutiesPayor>";
                            intHeader2 = 
                                    "<DutiesPayment>" + intHeader1 + "<PayorType>" + 
                                    intPayerType + "</PayorType>" + 
                                    "</DutiesPayment>";
                            payorCountryCodeWS = intcountryCode;
                        } else {
                            intHeader2 = 
                                    "<DutiesPayment>" + "<PayorType>" + intPayerType + 
                                    "</PayorType>" + "</DutiesPayment>";
                        }

                        /*
          try{
          String ifc = intFreightCharge.substring(0,1);
          }catch(Exception e)
          {
            intFreightCharge = "0.0";
          }
          try{
          String iic = intInsuranceCharge.substring(0,1);
          }catch(Exception e)
          {
            intInsuranceCharge = "0.0";
          }
          try{
          String itmc = intTaxesOrMiscellaneousCharge.substring(0,1);
          }catch(Exception e)
          {
            intTaxesOrMiscellaneousCharge = "0.0";
          }
          */

                        if (intPurpose.equalsIgnoreCase("")) { // +"<Comments>dd</Comments>"+
                            intHeader3 = 
                                    "<CommercialInvoice>" + "<FreightCharge>" + 
                                    intFreightCharge + "</FreightCharge>" + 
                                    "<InsuranceCharge>" + intInsuranceCharge + 
                                    "</InsuranceCharge>" + 
                                    "<TaxesOrMiscellaneousCharge>" + 
                                    intTaxesOrMiscellaneousCharge + 
                                    "</TaxesOrMiscellaneousCharge>" + 
                                    "</CommercialInvoice>";
                        } else { // +"<Comments>dd</Comments>"+
                            intHeader3 = 
                                    "<CommercialInvoice>" + "<FreightCharge>" + 
                                    intFreightCharge + "</FreightCharge>" + 
                                    "<InsuranceCharge>" + intInsuranceCharge + 
                                    "</InsuranceCharge>" + 
                                    "<TaxesOrMiscellaneousCharge>" + 
                                    intTaxesOrMiscellaneousCharge + 
                                    "</TaxesOrMiscellaneousCharge>" + 
                                    "<Purpose>" + intPurpose + "</Purpose>" + 
                                    "</CommercialInvoice>";
                        }
                        /*
          intHeader4 = "<Commodity>"+
                              "<NumberOfPieces>1</NumberOfPieces> "+
                              "<Description>Computer Keyboards</Description> "+
                              "<CountryOfManufacture>US</CountryOfManufacture> "+
                              "<HarmonizedCode>00</HarmonizedCode> "+
                              "<Weight>5.0</Weight> "+
                              "<Quantity>1</Quantity> "+
                              "<QuantityUnits>PCS</QuantityUnits> "+
                              "<UnitPrice>25.000000</UnitPrice> "+
                              "<CustomsValue>25.000000</CustomsValue> "+
                              "<ExportLicenseNumber>25</ExportLicenseNumber> "+
                              "<ExportLicenseExpirationDate>25</ExportLicenseExpirationDate> "+
                              "</Commodity>";

          intHeader4 = "";  */

                        if (!intSenderTINOrDUNS.equalsIgnoreCase("")) {
                            intHeader5 = 
                                    "<SED>" + "<SenderTINOrDUNS>" + intSenderTINOrDUNS + 
                                    "</SenderTINOrDUNS>" + 
                                    "<SenderTINOrDUNSType>" + 
                                    intSenderTINOrDUNSType + 
                                    "</SenderTINOrDUNSType>" + "</SED>";
                        } else {
                            intHeader5 = "";
                        }

                        internationalTags = 
                                "<International>" + intHeader2 + "<TermsOfSale>" + 
                                intTermsOfSale + "</TermsOfSale>" + 
                                "<TotalCustomsValue>" + intTotalCustomsValue + 
                                "</TotalCustomsValue>" + intHeader3 + 
                                intHeader6 + intHeader5 + "</International>";


                    } else {
                        internationalTags = "";
                    }


                    // end of addition on 09/06/08

                    // end of addition on 09/06/08

                    // Start on Aug-01-2011
                    try {
                        shipToContactPersonName = 
                                nullStrToSpc(aascShipmentHeaderInfo.getContactName());
                        shipToContactPersonName = 
                                encode(shipToContactPersonName); //Added by dedeepya on 28/05/08
                        if (shipToContactPersonName.equalsIgnoreCase("")) {
                            tagshipToContactPersonName = "";
                        } else {
                            tagshipToContactPersonName = 
                                    "<PersonName>" + shipToContactPersonName + 
                                    "</PersonName>";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        tagshipToContactPersonName = "";
                    }
                    // End on Aug-01-2011


                    
                    chkReturnlabel = "NONRETURN";
                    
                    responseStatus = 
                            setCarrierLevelInfo1(aascShipmentHeaderInfo, 
                                                 aascPackageBean, 
                                                 aascShipMethodInfo, 
                                                 chkReturnlabel);
                    sendfedexRequest(aascShipmentOrderInfo, aascShipMethodInfo, 
                                     chkReturnlabel, aascProfileOptionsInfo, 
                                     aascIntlInfo, cloudLabelPath);
                    responseStatus = 
                            Integer.parseInt((String)hashMap.get("ResponseStatus"));
                  

                    if (returnShipment.equals("PRINTRETURNLABEL") && 
                        responseStatus == 150) {
                        
                        
                        shipmentRequestHdr = "";


                        

                        chkReturnlabel = "PRINTRETURNLABEL";

                        responseStatus = 
                                setCarrierLevelInfo1(aascShipmentHeaderInfo, 
                                                     aascPackageBean, 
                                                     aascShipMethodInfo, 
                                                     chkReturnlabel); //calling local method

                        //processReturnShipment();
                        //Shiva modified code for  FedEx Return Shipment
                        
                        rtnShipMethod = 
                                rtnShipMethod.substring(0, rtnShipMethod.indexOf("@@"));
                        //   System.out.println("rtnShipMethod.indexOf(\"@@\")"+rtnShipMethod.indexOf("@@")+"rtnShipMethod:::"+rtnShipMethod);                             
                        rtnShipMethod = 
                                aascShipMethodInfo.getShipMethodFromAlt(rtnShipMethod);
                        
                        carrierCode = 
                                aascShipMethodInfo.getCarrierName(rtnShipMethod);
                        
                        rtnShipMethod = 
                                aascShipMethodInfo.getConnectShipScsTag(rtnShipMethod);
                        rtnACNumber = 
                                nullStrToSpc(aascPackageBean.getRtnACNumber().trim());

                        
                        rntHeader1 = 
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
                                "<FDXShipRequest xmlns:api=\"http://www.fedex.com/fsmapi\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXShipRequest.xsd\">" + 
                                "<RequestHeader>" + 
                                "<CustomerTransactionIdentifier>" + 
                                customerTransactionIdentifier + 
                                "</CustomerTransactionIdentifier>" + 
                                "<AccountNumber>" + senderAccountNumber + 
                                "</AccountNumber>" + "<MeterNumber>" + 
                                fedExTestMeterNumber + "</MeterNumber>" + 
                                "<CarrierCode>" + carrierCode + 
                                "</CarrierCode>" + "</RequestHeader>" + 
                                "<ShipDate>" + shipDate + "</ShipDate>" + 
                                "<ShipTime>" + time + "</ShipTime>" + 
                                "<DropoffType>" + rtnDropOfType + 
                                "</DropoffType>" + "<Service>" + 
                                rtnShipMethod + "</Service>" + "<Packaging>" + 
                                rtnPackageList + 
                                "</Packaging>"; // added for package options
                        rntHeader6 = 
                                "<Origin>" + "<Contact>" + rtnShipTagFromContact + 
                                header8 + "<PhoneNumber>" + rtnShipFromPhone + 
                                "</PhoneNumber>" + "</Contact>" + "<Address>" + 
                                "<Line1>" + rtnShipFromLine1 + "</Line1>" + 
                                fromLine2Tag + "<City>" + rtnShipFromCity + 
                                "</City>" + "<StateOrProvinceCode>" + 
                                rtnShipFromSate + "</StateOrProvinceCode>" + 
                                "<PostalCode>" + rtnShipFromZip + 
                                "</PostalCode>" + "<CountryCode>" + 
                                shipFromCountry + "</CountryCode>" + 
                                "</Address>" + "</Origin>" + "<Destination>" + 
                                "<Contact>" + rtnShipTagToContact + 
                                "<PhoneNumber>" + rtnShipToPhone + 
                                "</PhoneNumber>" + "</Contact>" + "<Address>" + 
                                "<Line1>" + rtnShipToLine1 + "</Line1>" + 
                                toLine2Tag + "<City>" + rtnShipToCity + 
                                "</City>" + "<StateOrProvinceCode>" + 
                                rtnShipToState + "</StateOrProvinceCode>" + 
                                "<PostalCode>" + rtnShipToZip + 
                                "</PostalCode>" + "<CountryCode>" + 
                                shipToCountry + "</CountryCode>" + 
                                "</Address>" + "</Destination>" + "<Payment>" + 
                                "<PayorType>" + rtnPayMethod + "</PayorType>";

                        header4 = "";
                        rntHeader5 = 
                                "<ReturnShipmentIndicator>PRINTRETURNLABEL</ReturnShipmentIndicator>"; // added for package options

                        header2 = 
                                "<WeightUnits>" + pkgWtUom + "</WeightUnits>" + 
                                "<Weight>" + pkgWtVal + "</Weight>";

                        listTag = "<ListRate>true</ListRate>";

                        // added for package options
                        header3 = 
                                "<CurrencyCode>" + currencyCode + "</CurrencyCode>";
                        //Shiva modified code for  FedEx Return Shipment
                        sendfedexRequest(aascShipmentOrderInfo, 
                                         aascShipMethodInfo, chkReturnlabel, 
                                         aascProfileOptionsInfo, aascIntlInfo, 
                                         cloudLabelPath);
                    }


                    
                    shipmentDeclaredValueStr = 
                            String.valueOf(shipmentDeclaredValue);
                    
                    int i = shipmentDeclaredValueStr.indexOf(".");

                    if (i < 1) {
                        shipmentDeclaredValueStr = 
                                shipmentDeclaredValueStr + ".00";
                        
                    } else if ((shipmentDeclaredValueStr.length() - index) > 
                               2) {
                        shipmentDeclaredValueStr = 
                                shipmentDeclaredValueStr.substring(0, 
                                                                   index + 3);
                        
                    } else {
                        while (shipmentDeclaredValueStr.length() != (i + 3)) {
                            shipmentDeclaredValueStr = 
                                    shipmentDeclaredValueStr + "0";

                        }
                    }
                   


                } //iterator


                packageInfoIterator = shipPackageInfo.listIterator();

                int ctr1 = 0;

                while (packageInfoIterator.hasNext()) {
                    ctr1 = ctr1 + 1;

                    AascShipmentPackageInfo aascPackageBean1 = 
                        (AascShipmentPackageInfo)packageInfoIterator.next();

                    totalShipmentCost = 
                            totalShipmentCost + aascPackageBean1.getPkgCost();

                    totalFreightCost = 
                            totalFreightCost + aascPackageBean1.getTotalDiscount();

                    surCharges = surCharges + aascPackageBean1.getSurCharges();
                    
                }
                

                if (carrierCode.equalsIgnoreCase("FDXE")) {


                    totalShipmentCost = 
                            (Math.floor(totalShipmentCost * 100)) / 100;
                    totalFreightCost = 
                            (Math.floor(totalFreightCost * 100)) / 100;
                    
                }

                if (carrierCode.equalsIgnoreCase("FDXG")) {
                    totalShipmentCost = 
                            (Math.floor(totalShipmentCost * 100)) / 100;

                    totalFreightCost = 
                            (Math.floor(totalFreightCost * 100)) / 100;
                    
                }
                surCharges = (Math.floor(surCharges * 100)) / 100;
                aascShipmentHeaderInfo.setTotalSurcharge(surCharges);

            } else {
                responseStatus = 151;
                aascShipmentHeaderInfo.setMainError("aascShipmentOrderInfo is null OR aascShipmentHeaderInfo is null" + 
                                                    "OR aascpackageInfo is null OR aascShipMethodInfo is null ");
                logger.info("aascShipmentOrderInfo is null OR aascShipmentHeaderInfo is null" + 
                            "OR aascpackageInfo is null OR aascShipMethodInfo is null ");
            }
        } catch (Exception exception) {
            aascShipmentHeaderInfo.setMainError("null values or empty strings passed in request");
            logger.severe("Exception::"+exception.getMessage());
        }
        logger.info("Exit from processShipment()");
        return responseStatus;
    } // end of getShipmentRequest

     /** This method can be used to trim the string.
      * @param  str String, 
      * @return String
      */
    public String trim(String str) {

        return str.replaceFirst(" ", "");

    }


    /** This method can be used to set carrier level information.
     * @param  aascShipmentHeaderInfo object, 
     *          aascPackageBean Object,
     *          aascShipMethodInfo Object,
     *          chkReturnlabel String
     * @return int         
     */
    public int setCarrierLevelInfo1(AascShipmentHeaderInfo aascShipmentHeaderInfo, 
                                    AascShipmentPackageInfo aascPackageBean, 
                                    AascShipMethodInfo aascShipMethodInfo, 
                                    String chkReturnlabel) {

        logger.info("Entered setCarrierLevelInfo() method");
        String intFlag = 
            nullStrToSpc(aascShipmentHeaderInfo.getInternationalFlag());
        //         AascShipmentPackageInfo aascPackageInfo = aascPackageBean;
        String shipMethod = "";
        String declaredValueInternal = "";
        if (chkReturnlabel.equalsIgnoreCase("NONRETURN")) {
            shipMethodName = 
                    nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning()); // retreiving ship method meaning from header bean

            carrierCode = aascShipMethodInfo.getCarrierName(shipMethodName);

            customerCarrierAccountNumber = 
                    aascShipmentHeaderInfo.getCarrierAccountNumber(); // FedEx Account number of the client


            shipMethod = service;
            declaredValueInternal = packageDeclaredValueStr;
        } else {

            String shipMethodNameRtn = 
                nullStrToSpc(aascPackageBean.getRtnShipMethod()); // retreiving ship method meaning from header bean

            carrierCode = aascShipMethodInfo.getCarrierName(shipMethodNameRtn);

            customerCarrierAccountNumber = 
                    rtnACNumber; // FedEx Account number of the client
            carrierPayMethod = rtnPayMethod;

            String carrierPaymentTermsStr = 
                aascShipMethodInfo.getCarrierPaymentTerm(rtnPayMethodCode);

            carrierPayMethodCode = 
                    aascShipMethodInfo.getCarrierPayCode(carrierPaymentTermsStr);


            shipMethod = rtnShipMethod;
            declaredValueInternal = rtnPackageDeclaredValueStr;
        }

        if (carrierCode.equalsIgnoreCase("FDXE") && 
            carrierPayMethodCode.equalsIgnoreCase("FC")) {
            aascShipmentHeaderInfo.setMainError("bill to type of collect is for ground services only ");
            responseStatus = 151;
            return responseStatus;
        }
        //*********************
        if (carrierCode.equalsIgnoreCase("FDXG") && 
            shipMethod.equalsIgnoreCase("GROUNDHOMEDELIVERY")) {

            residentialTag = "<ResidentialDelivery>true</ResidentialDelivery>";
        } else {
            residentialTag = "";
        }

        //*********************
        if (chkReturnlabel.equalsIgnoreCase("NONRETURN")) {
            if (codFlag.equalsIgnoreCase("Y")) {
                codAmt = aascPackageBean.getCodAmt();

                codAmtStr = String.valueOf(codAmt);

                index = codAmtStr.indexOf(".");


                if (index < 1) {
                    codAmtStr = codAmtStr + ".00";

                } else if ((codAmtStr.length() - index) > 2) {
                    codAmtStr = codAmtStr.substring(0, index + 3);

                } else {
                    while (codAmtStr.length() != (index + 3)) {
                        codAmtStr = codAmtStr + "0";

                    }
                }
                packageSequenceTest = 
                        nullStrToSpc(aascPackageBean.getPackageSequence());
                if (carrierCode.equalsIgnoreCase("FDXE") && size != 1 && 
                    !(packageSequenceTest.equalsIgnoreCase("1"))) {

                    if (shipFlag.equalsIgnoreCase("Y")) {

                        codTrackingNum = 
                                aascShipmentHeaderInfo.getCodTrackingNumber();
                    } else {

                        codTrackingNum = 
                                nullStrToSpc((String)hashMap.get("codTrackingNum"));
                    }


                    
                    codReturnTrackingTag = 
                            "<CODReturn><TrackingNumber>" + codTrackingNum + 
                            "</TrackingNumber></CODReturn>";
                }

                codTag = 
                        "<COD>" + "<CollectionAmount>" + codAmtStr + "</CollectionAmount>" + 
                        "<CollectionType>ANY</CollectionType>" + 
                        codReturnTrackingTag + "</COD>";
            } else {
                codTag = "";
            }
            //******************

            if (!packageCount.equalsIgnoreCase("1")) {

                shipmentWeight = 
                        nullStrToSpc(String.valueOf(aascPackageBean.getWeight()));

                

                if (packageSequence.equalsIgnoreCase("1")) {
                    if (carrierCode.equalsIgnoreCase("FDXG") || 
                        (carrierCode.equalsIgnoreCase("FDXE") && 
                         codFlag.equalsIgnoreCase("Y")) || 
                        (carrierCode.equalsIgnoreCase("FDXE") && 
                         intFlag.equalsIgnoreCase("Y"))) {
                        shipMultiPieceFlag = 1;
                    }

                    masterTrackingNumber = "";
                    masterFormID = "";
                    /*shipWtTag = "<ShipmentWeight>"
                           + aascShipmentHeaderInfo.getPackaging()
                           + "</ShipmentWeight>";*/
                } else {
                    if (shipFlag.equalsIgnoreCase("Y")) {
                        masterTrackingNumber = 
                                nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getWayBill()));
                        masterFormID = 
                                nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getMasterFormId()));
                        
                        shipWtTag = "";
                    } else {
                        masterTrackingNumber = 
                                nullStrToSpc((String)hashMap.get("masterTrkNum"));
                        //26/07/07(start)
                        if (masterTrackingNumber == "" || 
                            masterTrackingNumber.equalsIgnoreCase("")) {
                            masterTrackingNumber = 
                                    nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getWayBill()));
                        }
                        //26/07/07(end)
                        masterFormID = 
                                nullStrToSpc((String)hashMap.get("masterFormId"));
                        shipWtTag = "";
                    }
                }

                if (shipMultiPieceFlag == 
                    1) { // "<ShipmentWeight>"+aascShipmentHeaderInfo.getPackageWeight()+"</ShipmentWeight>"+
                    part1 = 
                            "<MultiPiece>" + "<PackageCount>" + packageCount + "</PackageCount>" + 
                            "<PackageSequenceNumber>" + packageSequence + 
                            "</PackageSequenceNumber>" + shipWtTag + 
                            "<MasterTrackingNumber>" + masterTrackingNumber + 
                            "</MasterTrackingNumber>";
                    part2 = "";
                    if (carrierCode.equalsIgnoreCase("FDXE")) {
                        part2 = 
                                "<MasterFormID>" + masterFormID + "</MasterFormID></MultiPiece>";
                    } else {
                        part2 = "</MultiPiece>";
                    }

                    header4 = part1 + part2;
                }

                else {
                    header4 = "";
                }
            }
        } else {
            codTag = "";
            header4 = "";
        }
        //**********************

        if (!signatureOptions.equalsIgnoreCase("NONE")) {
            signatureOptionString = 
                    "<SignatureOption>" + signatureOptions + "</SignatureOption>";
        } else {
            signatureOptionString = "";
        }

        // added for package options
        // added for package options
        // added for package options
        header7 = 
                "<DeclaredValue>" + declaredValueInternal + "</DeclaredValue>" + 
                "<SpecialServices>" + signatureOptionString + dryIceTag +
                "<FutureDayShipment>" + futureDayShipment + 
                "</FutureDayShipment>" + codTag + residentialTag + 
                satShipFlagTag + hal + HazMat + 
                "</SpecialServices>"; // added for package options


        //********************


        if (carrierCode.equalsIgnoreCase("FDXG") && 
            shipMethod.equalsIgnoreCase("GROUNDHOMEDELIVERY")) {

            residentialTag = "<ResidentialDelivery>true</ResidentialDelivery>";
        } else {
            residentialTag = "";
        }
        //********************

        if (carrierCode.equalsIgnoreCase("FDXE") && 
            satShipFlag.equalsIgnoreCase("Y")) {
            satShipFlag = "1";

            satShipFlagTag = 
                    "<SaturdayDelivery>" + satShipFlag + "</SaturdayDelivery>";
        } else {
            satShipFlagTag = "";
        }
        logger.info("Exit from setCarrierLevelInfo() method");
        return responseStatus;

    } //  End Of setCarrierLevelInfo().

     /** This method can be used to call fedex web service.
      * @param  key String, 
      * @param  password String,
      * @param  intFlag String,
      * @param  totalWeight Double,
      * @param aascIntlInfo Object
      * @return String
      */
    public String callFedexWS(String key, String password, String intFlag, 
                              Double totalWeight, AascIntlInfo aascIntlInfo) {
        String reply = "";
        String appendStr = "";
        try {
            HashMap hm = new HashMap();
            hm.put("key", key);
            
            hm.put("password", password);
            
            hm.put("customerCarrierAccountNumber", 
                   customerCarrierAccountNumber);
            
            hm.put("customerTransactionIdentifier", 
                   customerTransactionIdentifier);
            hm.put("orderName", orderName);
            hm.put("senderAccountNumber", senderAccountNumber);
            
            hm.put("fedExTestMeterNumber", fedExTestMeterNumber);
            
            hm.put("carrierCode", carrierCode);

            hm.put("shipMethodName", shipMethodName); //added for SmartPost
            hm.put("hubid", hubid); //added for SmartPost
            hm.put("indicia", indicia); //added for SmartPost   
            hm.put("ancillary", ancillary); //added for SmartPost

            hm.put("shipDate", shipDate);
            
            hm.put("fedExWsTimeStr", fedExWsTimeStr);
            
            hm.put("dropoffType", dropoffType);
            
            hm.put("service", service);
            
            hm.put("packaging", packaging);
            
            hm.put("pkgWtUom", pkgWtUom);
            

            hm.put("packageDeclaredValue", packageDeclaredValue);
            
            hm.put("pkgWtVal", Double.parseDouble(Float.toString(pkgWtVal)));
            
            
            hm.put("codFlag", codFlag);
            
            if (codFlag.equalsIgnoreCase("Y")) {
                hm.put("codAmtStr", Double.parseDouble(codAmtStr));
                
            }

            //Hold At Location
            hm.put("halFlag", halFlag);
            
            hm.put("halPhone", halPhone);
            
            hm.put("halCity", halCity);
            
            hm.put("halState", halState);
            
            hm.put("halLine1", halLine1);
            
            hm.put("halLine2", halLine2);
            
            hm.put("halZip", halZip);
            

            //Ship From Location Details
            hm.put("shipFromDepartment", shipFromDepartment);
            
            hm.put("shipFromCompanyName", shipFromCompanyName);
            
            hm.put("shipFromPersonName", shipFromPersonName);
            
            hm.put("shipFromPhoneNumber1", shipFromPhoneNumber1);
            
            hm.put("shipFromEMailAddress", shipFromEMailAddress);
                        
            hm.put("shipToContactPhoneNumber", shipToContactPhoneNumber);
            
            hm.put("shipFromAddressLine1", shipFromAddressLine1);
            
            hm.put("shipFromAddressLine2", shipFromAddressLine2);

            hm.put("shipFromAddressCity", shipFromAddressCity);
            hm.put("shipFromAddressState", shipFromAddressState);
            hm.put("shipFromAddressPostalCode", shipFromAddressPostalCode);
            hm.put("shipFromCountry", shipFromCountry);

            //Ship To Location Details
            hm.put("currencyCode", currencyCode);
            hm.put("shipToCompanyName", shipToCompanyName);
            
            hm.put("shipToContactPersonName", shipToContactPersonName);
            
            hm.put("shipToAddressLine1", shipToAddressLine1);
            hm.put("shipToAddressLine2", shipToAddressLine2);

            
            hm.put("shipToAddressCity", shipToAddressCity);
            
            hm.put("shipToAddressState", shipToAddressState);
            
            hm.put("shipToAddressPostalCode", shipToAddressPostalCode);
            
            hm.put("shipToCountry", shipToCountry);
            
            hm.put("shipToEMailAddress", shipToEMailAddress);            

            hm.put("carrierPayMethod", carrierPayMethod);
            

            hm.put("reference1", reference1);
            
            hm.put("reference2", reference2);
            
            hm.put("lpnNumber", lpnNumber);


            String pl1 = Double.toString(packageLength);
            String pw1 = Double.toString(packageWidth);
            String ph1 = Double.toString(packageHeight);
            hm.put("packageLength", pl1);
            
            hm.put("packageWidth", pw1);
            
            hm.put("packageHeight", ph1);
            
            hm.put("dimunits", units);
            
            hm.put("SenderEmail", SenderEmail);
            

            hm.put("ShipAlertNotification", ShipAlertNotification);
            

            hm.put("ExceptionNotification", ExceptionNotification);
            

            hm.put("DeliveryNotification", DeliveryNotification);
            

            hm.put("Format", Format);
            
            hm.put("emailFlag", emailFlag);
            
            hm.put("message", message);
            
            hm.put("recipientEmailAddress1", recipientEmailAddress1);
            
            hm.put("recipientEmailAddress2", recipientEmailAddress2);
            
            hm.put("recipientEmailAddress3", recipientEmailAddress3);
            
            hm.put("recipientEmailAddress5", recipientEmailAddress5);
            
            hm.put("recipientEmailAddress4", recipientEmailAddress4);
            

            hm.put("satShipFlag", satShipFlag);
            
            //Dry Ice
            hm.put("chDryIce", chDryIce);
            
            if (chDryIce.equalsIgnoreCase("Y")) {
                hm.put("dryIceUnits", dryIceUnits);
                
                if (dryIceUnits.equalsIgnoreCase("LBS")) {
                    dryIceWeight = dryIceWeight * 0.453;
                }
                hm.put("dryIceWeight", dryIceWeight);
                
            }

            //signatureOptions
            hm.put("signatureOptions", signatureOptions);
            
            //Return Shipment

            hm.put("fedExWSChkReturnlabelstr", fedExWSChkReturnlabelstr);
            // hm.put("NONRETURN","returnlabel");
            

            hm.put("outputFile", outputFile);
            

            hm.put("packageSequence", packageSequence);
            

            hm.put("timeStampStr", timeStampStr);
            

            hm.put("residentialAddrFlag", residentialAddrFlag);
            


            if (fedExWSChkReturnlabelstr.equals("NONRETURN")) {
                appendStr = "";

            } else {
                appendStr = "Return_";

            }

            hm.put("appendStr", appendStr);
            

            if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {

                
                

                hm.put("rtnACNumber", rtnACNumber);
                

                hm.put("rtnMeterNumber", rtnMeterNumber);
                

                hm.put("rtnDropOfType", rtnDropOfType);
                

                hm.put("rtnShipMethod", rtnShipMethod);
                

                hm.put("rtnPackageList", rtnPackageList);
                


                hm.put("rtnShipFromContact", rtnShipFromContact);
                hm.put("rtnShipFromCompany", rtnShipFromCompany);

                hm.put("rtnShipFromPhone", rtnShipFromPhone);
                

                hm.put("rtnShipFromLine1", rtnShipFromLine1);
                

                hm.put("rtnShipFromLine2", rtnShipFromLine2);
                

                hm.put("rtnShipFromCity", rtnShipFromCity);
                

                hm.put("rtnShipFromSate", rtnShipFromSate);
                

                hm.put("rtnShipFromZip", rtnShipFromZip);
                

                hm.put("shipFromCountry", shipFromCountry);
                

                hm.put("rtnShipToContact", rtnShipToContact);
                

                hm.put("rtnShipToCompany", rtnShipToCompany);
                


                hm.put("rtnShipToPhone", rtnShipToPhone);
                

                hm.put("rtnShipToLine1", rtnShipToLine1);
                

                hm.put("rtnShipToLine2", rtnShipToLine2);
                

                hm.put("rtnShipToCity", rtnShipToCity);
                

                hm.put("rtnShipToState", rtnShipToState);
                

                hm.put("rtnShipToZip", rtnShipToZip);
                

                hm.put("shipToCountry", shipToCountry);
                

                hm.put("rtnPayMethod", rtnPayMethod);
                

                
                hm.put("rtnRMA", rtnRMA);
                
                

            }

            //Hazardous Material
            
            hm.put("HazMatFlag", HazMatFlag);
            
            hm.put("Accessibility", HazMatType);
            
            hm.put("ProperShippingName", fedExWsShippingName1);
            hm.put("HazardClass", fedExWsClass);
            
            hm.put("HazMatIdentificationNo", HazMatIdentificationNo);
            
            hm.put("HazardousMaterialPkgGroup", HazardousMaterialPkgGroup);
            
            hm.put("HazMatDOTLabelType", HazMatDOTLabelType);
            
            hm.put("HazMatEmergencyContactName", HazMatEmergencyContactName);
            
            hm.put("HazMatEmergencyContactNo", HazMatEmergencyContactNo);
            
            hm.put("HazMatQty", HazMatQty);
            
            hm.put("HazMatUnit", HazMatUnit);
            
            hm.put("payorCountryCodeWS", payorCountryCodeWS);
            //Mahesh added below code for Third Party and Recepient development 
            
            hm.put("receipientPartyName", receipientPartyName);
            hm.put("recipientPostalCode", recipientPostalCode);
            
            hm.put("tpCompanyName", tpCompanyName);
            logger.info("tpCompanyName :" + tpCompanyName);
            hm.put("tpAddress", tpAddress);
            logger.info("tpAddress :" + tpAddress);
            hm.put("tpCity", tpCity);
            logger.info("tpCity :" + tpCity);
            hm.put("tpState", tpState);
            logger.info("tpState :" + tpState);
            hm.put("tpPostalCode", tpPostalCode);
            logger.info("tpPostalCode :" + tpPostalCode);
            hm.put("tpCountrySymbol", tpCountrySymbol);
            logger.info("tpCountrySymbol :" + 
                        tpCountrySymbol); 


            //Added on Jul-05-2011
            hm.put("hazmatPkgingCnt", hazmatPkgingCnt);
            

            hm.put("hazmatPkgingUnits", hazmatPkgingUnits);
            

            hm.put("hazmatTechnicalName", hazmatTechnicalName);
            

            //End on Jul-05-2011

            hm.put("hazmatSignatureName", hazmatSignatureName);

            //  label/doctab values
            hm.put("labelFormat", labelFormat);
            hm.put("labelStockOrientation", labelStockOrientation);
            hm.put("docTabLocation", docTabLocation);
            //

            hm.put("op900LabelFormat", op900LabelFormat);
            hm.put("intlDocSubType", intlDocSubType);
            hm.put("dutiesAndTaxesFlag", dutiesAndTaxesFlag);

            //International Details
            //String intFlag = nullStrToSpc(aascHeaderInfo.getIntlShipFlag());     commented by rajesh,  
            //passing the parameter as method variable
            LinkedList coList = null;
            try {
                aascIntlHeaderInfo = aascIntlInfo.getIntlHeaderInfo();
                coList = aascIntlInfo.getIntlCommodityInfo();
            } catch (Exception e) {
                aascIntlHeaderInfo = new AascIntlHeaderInfo();
                coList = new LinkedList();
            }
            hm.put("intFlag", intFlag);
            if (intFlag.equalsIgnoreCase("Y")) {
                String exemptionNumber = "";
                exemptionNumber = 
                        nullStrToSpc(aascIntlHeaderInfo.getIntlExemptionNumber());
                hm.put("exemptionNumber", exemptionNumber);
                hm.put("TPintAccNumber", intAccNumber);
                hm.put("TPintcountryCode", intcountryCode);
                hm.put("intPayerType", intPayerType);
                hm.put("intFreightCharge", intFreightCharge);
                hm.put("intInsuranceCharge", intInsuranceCharge);
                hm.put("intTaxesOrMiscellaneousCharge", 
                       intTaxesOrMiscellaneousCharge);
                hm.put("intPurpose", intPurpose);
                hm.put("intSenderTINOrDUNS", intSenderTINOrDUNS);
                hm.put("intSenderTINOrDUNSType", intSenderTINOrDUNSType);

                hm.put("packingListEnclosed", packingListEnclosed);
                hm.put("shippersLoadAndCount", shippersLoadAndCount);
                hm.put("bookingConfirmationNumber", bookingConfirmationNumber);

                hm.put("intTermsOfSale", intTermsOfSale);
                
                hm.put("intTotalCustomsValue", intTotalCustomsValue);
                
                hm.put("declarationStmt", declarationStmt);
                hm.put("generateCI", generateCI);
                hm.put("importerName", importerName);
                hm.put("importerCompName", importerCompName);
                hm.put("importerPhoneNum", importerPhoneNum);
                hm.put("importerAddress1", importerAddress1);
                hm.put("importerAddress2", importerAddress2);
                hm.put("importerCity", importerCity);
                hm.put("importerState", importerState);
                hm.put("importerPostalCode", importerPostalCode);
                hm.put("importerCountryCode", importerCountryCode);
                hm.put("impIntlSedNumber", impIntlSedNumber);
                hm.put("impIntlSedType", impIntlSedType);
                hm.put("recIntlSedNumber", recIntlSedNumber);
                hm.put("recIntlSedType", recIntlSedType);

                hm.put("brokerName", brokerName);
                hm.put("brokerCompName", brokerCompName);
                hm.put("brokerPhoneNum", brokerPhoneNum);
                hm.put("brokerAddress1", brokerAddress1);
                hm.put("brokerAddress2", brokerAddress2);
                hm.put("brokerCity", brokerCity);
                hm.put("brokerState", brokerState);
                hm.put("brokerPostalCode", brokerPostalCode);
                hm.put("brokerCountryCode", brokerCountryCode);

                //sc_skp_7.0 end
                ListIterator CoInfoIterator = coList.listIterator();


                HashMap intlCommodityHM[] = new HashMap[coList.size()];

                int index = 0;
                while (CoInfoIterator.hasNext()) {
                    AascIntlCommodityInfo aascIntlCommodityInfo = 
                        (AascIntlCommodityInfo)CoInfoIterator.next();

                    numberOfPieces = aascIntlCommodityInfo.getNumberOfPieces();
                    description = 
                            encode(aascIntlCommodityInfo.getDescription());
                    countryOfManufacture = 
                            aascIntlCommodityInfo.getCountryOfManufacture();
                    harmonizedCode = aascIntlCommodityInfo.getHarmonizedCode();
                    weight = aascIntlCommodityInfo.getWeight();
                    quantity = aascIntlCommodityInfo.getQuantity();
                    quantityUnits = aascIntlCommodityInfo.getQuantityUnits();
                    unitPrice = aascIntlCommodityInfo.getUnitPrice();
                    customsValue = aascIntlCommodityInfo.getCustomsValue();
                    exportLicenseNumber = 
                            aascIntlCommodityInfo.getExportLicenseNumber();
                    exportLicenseExpiryDate = 
                            aascIntlCommodityInfo.getExportLicenseExpiryDate();
                    //                     String rdate = "";
                    intlCommodityHM[index] = new HashMap();
                    intlCommodityHM[index].put("numberOfPieces", 
                                               numberOfPieces);
                    
                    intlCommodityHM[index].put("description", description);
                    
                    intlCommodityHM[index].put("countryOfManufacture", 
                                               countryOfManufacture);
                    
                    intlCommodityHM[index].put("weight", weight);
                    
                    intlCommodityHM[index].put("quantity", quantity);
                    
                    intlCommodityHM[index].put("quantityUnits", quantityUnits);
                    
                    intlCommodityHM[index].put("harmonizedCode", 
                                               harmonizedCode);
                    
                    intlCommodityHM[index].put("unitPrice", unitPrice);
                    
                    intlCommodityHM[index].put("exportLicenseNumber", 
                                               exportLicenseNumber);
                    
                    intlCommodityHM[index].put("exportLicenseExpiryDate", 
                                               exportLicenseExpiryDate);
                    
                    intlCommodityHM[index].put("customsValue", customsValue);
                    
                    index++;
                }
                hm.put("intlCommodityHM", intlCommodityHM);
            }

            

            if (separateShipFlag.equalsIgnoreCase("Y")) {
                packageCount = "1";
            }
            hm.put("packageCount", packageCount);
            if ((fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
                if (updateFlg.equalsIgnoreCase("SQ")) {
                    pkgCntWs = Integer.parseInt(packageSequence);
                } else if (separateShipFlag.equalsIgnoreCase("Y")) {
                    pkgCntWs = 1;
                } else {
                    pkgCntWs = pkgCntWs + 1;
                }
            }
            
            hm.put("sequenceNumber", pkgCntWs);
            hm.put("totalWeight", totalWeight);

            //Added code for multiple shipment
            if (!packageCount.equalsIgnoreCase("1")) {
                if (pkgCntWs > 0) {
                    
                    hm.put("masterTrackingNumber", masterTrackingNumber);
                    
                    hm.put("masterFormID", masterFormID);
                }
            }
            com.aasc.erp.carrier.fedexws.ShipWebService shipWebService = 
                new com.aasc.erp.carrier.fedexws.ShipWebService();
            reply = shipWebService.getShipInfo(hm, host, port);
            
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            StringBuffer replySB = new StringBuffer();
            replySB.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            replySB.append("<FDXShip2Reply xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"FDXShip2Reply.xsd\">");
            replySB.append("<ReplyHeader>");
            replySB.append("<CustomerTransactionIdentifier>126972</CustomerTransactionIdentifier>");
            replySB.append("</ReplyHeader>");
            replySB.append("<Error>");
            replySB.append("<Code>9040</Code>");
            replySB.append("<Message>");
            replySB.append("Error Sending request to FedEx. ");
            if (e.getMessage().contains("java.net.ConnectException")) {
                replySB.append("Please check the connection details in Carrier Configuration.");
            } else {
                replySB.append(e.getMessage());
            }
            replySB.append("</Message>");
            replySB.append("<Type>Ship</Type>");
            replySB.append("</Error>");
            replySB.append("</FDXShip2Reply>");
            reply = replySB.toString();
        } finally {
            return reply;
        }
    }

}
