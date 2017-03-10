  /**
   * AascDHLShipment.java  is a java class for DHL carrier where in you fetch all data.
   * @version   1
   * @author   G S Shekar
   * @History
   *
   * Date          Resource            Changes
   *-----------------------------------------------------------------------------------------------------------------------------------
   * 17/11/2015    Shiva G            Modified code to use Global Schema for DHL.
   * 18/12/2015    Y Pradeep          Modified code to send system date TimeStamp as shipmentdate for shipping. Bug #4095.
   */
package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.carrier.dhlws.DHLGetQuoteWS;
import com.aasc.erp.util.AascLogger;

import java.io.FileOutputStream;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class


AascDHLShipment {
    private AascShipmentHeaderInfo aascShipmentHeaderInfo = 
        null; // Delivery Header Information Object
    private LinkedList shipPackageInfo = 
        null; // Delivery Package Information Object  
    private String quoteResponse = ""; //quote response xml
    private String shipmentResponse = 
        ""; // String Containg the shipment response xml file
    private String shipmentRequest = 
        ""; // String containing Shipment Request In XML format  
    private static Logger logger = 
        AascLogger.getLogger("AascDHLShipment"); // logger object used for issuing logging requests
    private LinkedList adhocPackageInfo = 
        null; // Delivery Package Information Object
    private String dhlExTestMeterNumber = 
        ""; // dhlEx Test meter number of the client   
    private String shipToCompanyName = 
        ""; // indicates ship to company name(these details are required to be sent to connectship for calculating shipment cost)
        
    private String shipToCountryFullName = "";//holds ship to country full name
    private String shipFromCountryFullName = ""; // holds ship from company full name.
    private String transactionalCurrCode = ""; // holds currency type to be traded.
    private String dhlContent = ""; //holds content value from ebs
    
    private String shipToAddressLine1 = ""; // indicates ship to address   
    private String shipToAddressLine2 = ""; // indicates ship to address
    private String shipToAddressLine3 = ""; // indicates ship to address   
    private String shipToAddressCity = ""; // indicates ship to address city
    private String shipToAddressPostalCode = 
        ""; // indicates ship to postal code
    private String shipToCountry = ""; // indicates ship to country
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
    private String SenderEmail = "";
    private String recipientEmailAddress ="";
    private String shipFromPhoneNumber1 = ""; // indicates sender phone number
    private String shipFromAddressLine1 = ""; // indicates ship From address   
    private String shipFromAddressLine2 = ""; // indicates ship From address   
    private String shipFromAddressCity = 
        ""; // indicates ship From address city    
    private String shipFromCode = "";
    private String shipToCode = "";
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
    private String deliveryId = "";
    private byte[] replyOut = 
        new byte[500]; // Byte array for catching reply from 
    private int iUTI = 
        2517; // Universal transaction identifier for ShipRequest 2517
    private String portString = "";
    private int port = 0; // Default dhlAPI service (ATOM) port
    private String host = 
        ""; // host address of system where dhlAPI service is running
    private String protocol = ""; // timeOut period 
    private int responseStatus = 
        0; // holds 150 if response is retreived successfully and parsed successfully else holds 151                     
    private String parseStatus = 
        ""; // holds the status of parsing the response        
    private int customerTransactionIdentifier2 = 
        0; // Customer transaction identifier
    private static HashMap carrierPayMethodCodeMap = new HashMap();

    private String outputFile = 
        ""; // holds folder name to which request and response are written
    private String senderAccountNumber = ""; // sender's carrier account number
    private String receiverAccountNumber = ""; // sender's carrier account number
    private String shipperAccountNumber = ""; // shipper account number from profile options
    
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
    private String intlFlag = "";
    private String option = "";

    HashMap hashMap = null;

    private double surCharges = 0.0;
    private double totalSurcharge = 0.0;

    // Added for dimensions

    private double packageLength = 0;
    private double packageWidth = 0;
    private double packageHeight = 0;
    private double totalVolume = 0.0;
    private double totalInscuredValue = 0.0;
    private String totalGoodsValueStr = "";


    private String reference1 = "";
    private String reference1Tag = "";
    private String reference2 = "";
    private String ref1n2 = "";
    

    private String labelFormat = "PDF"; // default label format
    private String labelFormatTag = "";
    private int pkgCount = 0;
    


    private String labelStockOrientation = "";
    private String docTabLocation = "";
    private String dhlRegionCode = "";
    private String satShipFlag = 
        ""; // flag which indicates whether the delivery is shipped on saturday or not
    private String satShipFlagTag = "";
    private String orderName ;

    private String ShipDate = "";

    private String companyNameFromPO = "";
    private String shipToContactPersonName = "";
    private String tagshipToContactPersonName = "";
    private String chkReturnlabel = "";


    
    double hazmatPkgingCnt = 0.0;
    String hazmatPkgingUnits = "";
    String hazmatTechnicalName = "";
  

    private AascIntlHeaderInfo aascIntlHeaderInfo = null;
    private AascIntlCommodityInfo aascIntlCommodityInfo = null;

    private String timeStampStr;
    private String messageReference = "";
    private String messgageTime="";

    private String referenceValue1 = "";
    private String referenceValue2 = "";

    private String shiptoInfoOnLabel = "";

    private String ajaxcarrierservicelevel="";
    private String dhlPassword = "";
    private String siteId = "";
    
    private AascShipMethodInfo dhlAascShipMethodInfo = null;
    //private int dhlWsCount = 0;
    private String dhlWsTimeStr;
    private String applURL = "";
    private String payorCountryCodeWS = "";

    //Added for gaylord Enhancement
    private String updateFlg = "";
    private String separateShipFlag = "";

    private boolean errorFlag = 
        false; // Added this field for GayLord separate labels enhancement.
    
    private String package1;
    private Double totalWeight;
    private String reply;
    private String packageType;
    //private String dhlPackaging;
    private String intlAccountNumberStr = "";
    private String intlPayerType = "";
    private String termsOfTrade = "";

    static {
        carrierPayMethodCodeMap.put("PREPAID", "S");
        carrierPayMethodCodeMap.put("RECIPIENT", "R");
        carrierPayMethodCodeMap.put("CONSIGNEE", "R");
        carrierPayMethodCodeMap.put("THIRD PARTY BILLING", "T");
        carrierPayMethodCodeMap.put("THIRDPARTY", "T");
        carrierPayMethodCodeMap.put("SENDER", "S");
    }

    /** default constructor.*/
    public AascDHLShipment() {
    }

    public int processShipment(AascShipmentOrderInfo aascShipmentOrderInfo, 
                               AascShipMethodInfo aascShipMethodInfo,  
                               AascIntlInfo aascIntlInfo, 
                               AascProfileOptionsBean aascProfileOptionsInfo, 
                               String intlFlag,
                               String ajaxcarrierservicelevel,
                               String shipMethodActualName,
                               String cloudLabelPath) {
        try {
            logger.info("Entered DHL processShipment() method");
            this.intlFlag = intlFlag;
            this.ajaxcarrierservicelevel = ajaxcarrierservicelevel;
            
            logger.info("------------aascShipMethodInfo ------"+aascShipMethodInfo.getShipMethod());
            
            outputFile = cloudLabelPath;
            logger.info("out put location : "+outputFile);
                     
            companyNameFromPO = aascProfileOptionsInfo.getCompanyName();
            logger.info("------------> after output");
            aascShipmentHeaderInfo = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo(); // returns header info bean object
            shipPackageInfo = 
                    aascShipmentOrderInfo.getShipmentPackageInfo(); // returns the linkedlist contains the package info bean objects 

         deliveryId = aascShipmentHeaderInfo.getOrderNumber();
           shipMethodName = 
                    nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning()); // retreiving ship method meaning from header bean                        
            String shipMethodMeaning = aascShipmentHeaderInfo.getShipMethodMeaning();
            
            carrierId = aascShipMethodInfo.getCarrierId(shipMethodMeaning);
            
            dhlPassword = aascShipMethodInfo.getCarrierPassword(carrierId);
            siteId = aascShipMethodInfo.getCarrierUserName(carrierId);
            
            logger.info("dhlPassword "+dhlPassword);
            logger.info("siteId "+siteId);
            
            shipmentRequest = ""; // String that holds shipmentRequest  
            
            customerTransactionIdentifier = 
                    aascShipmentHeaderInfo.getDeliveryItemNumber();
            orderName= aascShipmentHeaderInfo.getOrderNumber();


            try {
                String companyNameFromPOTxt = 
                    companyNameFromPO.substring(0, 1);
            } catch (Exception e) {
                companyNameFromPO = "";
            }
            logger.info("Ship From CompanyName :" + companyNameFromPO);
            calendar = Calendar.getInstance();

            if (aascShipmentHeaderInfo != null && 
                shipPackageInfo != null && aascShipMethodInfo != null) {

                carrierId = aascShipmentHeaderInfo.getCarrierId();
                labelFormat = 
                        aascShipMethodInfo.getPrinterPort(carrierId); // retreiving label format from shipMethod bean          
                labelStockOrientation = 
                        aascShipMethodInfo.getLabelStockOrientation(carrierId);
                docTabLocation = 
                        aascShipMethodInfo.getDocTabLocation(carrierId);
                        
                dhlRegionCode = aascShipMethodInfo.getdhlRegionCode(carrierId);
                
                logger.info("labelFormat:" + labelFormat + 
                            "labelStockOrientation:" + labelStockOrientation + 
                            "docTabLocation:" + docTabLocation+
                            "dhlRegionCode::"+dhlRegionCode);

                logger.info("carrierId :" + carrierId);
                
                shipToContactPersonName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getContactName());
                shipToContactPersonName = 
                        encode(shipToContactPersonName);
            System.out.println("shipToContactPersonName::"+shipToContactPersonName);
                totalWeight = aascShipmentHeaderInfo.getPackageWeight();
                System.out.println("totalWeight:::"+totalWeight);
                shipToCompanyName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCustomerName()); // retreiving ship to company name from header bean 
                System.out.println("shipToCompanyName::::"+shipToCompanyName);        
                shipToCountryFullName = nullStrToSpc(aascShipmentHeaderInfo.getShipToCountryFullName());
                System.out.println("shipToCountryFullName::::"+shipToCountryFullName);
                shipFromCountryFullName =  nullStrToSpc(aascShipmentHeaderInfo.getShipFromCountryFullName());
                System.out.println("shipFromCountryFullName::::"+shipFromCountryFullName);
                dhlContent = nullStrToSpc(aascShipmentHeaderInfo.getShipAddInfo()); 
//                shipToCompanyName = encode(shipToCompanyName);
                shipToAddressLine1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getAddress()); // retreiving ship to address from header bean            
                shipToAddressLine1 = 
                        encode(shipToAddressLine1); //Added by dedeepya on 28/05/08

                shipToAddressLine2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine2()); // retreiving ship to address from header bean 
                shipToAddressLine2 = 
                        encode(shipToAddressLine2); //Added by dedeepya on 28/05/08


                shipToAddressLine3 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine3()); // retreiving ship to address from header bean            
                shipToAddressLine3 = 
                        encode(shipToAddressLine3); //Added by dedeepya on 28/05/08
                shipToAddressCity = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCity()); // retreiving ship to city from header bean           
System.out.println("shipToAddressCity:::"+shipToAddressCity);                        
                shipToAddressPostalCode = 
                        nullStrToSpc(aascShipmentHeaderInfo.getPostalCode()); // retreiving ship to postal code from header bean                                    
System.out.println("shipToAddressPostalCode:::"+shipToAddressPostalCode);                        

                shipToAddressPostalCode = escape(shipToAddressPostalCode);

                shipToCountry = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCountrySymbol()).toUpperCase(); // retreiving ship to country name from header bean            
                shipToAddressState = 
                        encode(nullStrToSpc(aascShipmentHeaderInfo.getState()).toUpperCase()); // retreiving ship to state from header bean           
                shipDate = 
                        aascShipmentHeaderInfo.getShipmentDate(); // retreiving ship date from header bean                                                     
                shipFlag = aascShipmentHeaderInfo.getShipFlag();
                
                
                carrierPayMethodCode = 
                        nullStrToSpc(aascShipMethodInfo.getCarrierPayCode(aascShipmentHeaderInfo.getCarrierPaymentMethod()));
                logger.info("carrierPayMethodCode "+carrierPayMethodCode);
                
                if (carrierPayMethodCode.equalsIgnoreCase("PP")) {
                    senderAccountNumber = 
                            nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber());
                    logger.info("senderAccountNumber "+senderAccountNumber);
                }
                else {
                    senderAccountNumber = nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber());
                    logger.info("senderAccountNumber for not PP "+senderAccountNumber);
                }
                
                shipperAccountNumber = aascShipMethodInfo.getCarrierAccountNumber(carrierId);
                if(shipperAccountNumber == null)
                    shipperAccountNumber = "";
                logger.info("shipperAccountNumber : "+shipperAccountNumber);
                
                if(receiverAccountNumber == null)
                    receiverAccountNumber = "";
                
                ref1n2 = nullStrToSpc(aascShipmentHeaderInfo.getReference1()+" "+aascShipmentHeaderInfo.getReference2());


                shipFromAddressLine1 = 
                        encode(aascShipmentHeaderInfo.getShipFromAddressLine1()); // indicates ship From address    
                shipFromAddressLine2 = 
                        encode(aascShipmentHeaderInfo.getShipFromAddressLine2());
                shipFromAddressCity = 
                        encode(aascShipmentHeaderInfo.getShipFromCity()); // indicates ship From address city                

                shipFromAddressPostalCode = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipFromPostalCode()); // indicates ship From postal code                                   

                shipFromAddressPostalCode = escape(shipFromAddressPostalCode);

                shipFromCountry = 
                        encode(aascShipmentHeaderInfo.getShipFromCountry().toUpperCase()); // indicates ship From country            
                shipFromAddressState = 
                        encode(aascShipmentHeaderInfo.getShipFromState().toUpperCase());
                SenderEmail = aascShipmentHeaderInfo.getShipFromEmailId();
                recipientEmailAddress = 
                        aascShipmentHeaderInfo.getShipToEmailId();

                totalSurcharge = aascShipmentHeaderInfo.getTotalSurcharge();

                shipMethodName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning()); // retreiving ship method meaning from header bean                        
                carrierCode = 
                        aascShipMethodInfo.getCarrierName(shipMethodName);
                    

              
                shipFromCompanyName = companyNameFromPO;
                shipFromCompanyName = encode(shipFromCompanyName);

                shipFromPersonName = 
                        encode(nullStrToSpc(aascShipmentHeaderInfo.getShipFromContactName()));

                shipFromDepartment = 
                        nullStrToSpc(aascShipmentHeaderInfo.getDepartment()); // Retrieving the shipfrom department        
                shipFromDepartment = 
                        encode(shipFromDepartment); 
                shipFromPhoneNumber1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipFromPhoneNumber1());
                //Shiva commented below 3 lines for the issue #4026
//                 shipFromPhoneNumber1 = shipFromPhoneNumber1.replace("(", "");
//                 shipFromPhoneNumber1 = shipFromPhoneNumber1.replace(")", "###");
//                 shipFromPhoneNumber1 = shipFromPhoneNumber1.replace("-", "");
                 

                logger.info("shipFromPhoneNumber in carrier:" + 
                            shipFromPhoneNumber1);

                shipFromPhoneNumber1 = encode(shipFromPhoneNumber1);
                shipToContactPhoneNumber = 
                        nullStrToSpc(aascShipmentHeaderInfo.getPhoneNumber()); // retreiving phone number from header bean  
                

                if (shipToContactPhoneNumber.equalsIgnoreCase("")) {
                    shipToContactPhoneNumber = shipFromPhoneNumber1;
                }
                
                
                carrierPayMethod = 
                        (String)carrierPayMethodCodeMap.get(nullStrToSpc(aascShipmentHeaderInfo.getCarrierPaymentMethod()));
                        
                logger.info("carrierPayMethod : "+carrierPayMethod);
                        
                if(carrierPayMethodCode.equalsIgnoreCase("CG"))
                    carrierPayMethod = "R";
                
                logger.info("got the information from header and package bean and setting it to instance variables");


                dropoffType = 
                        aascShipmentHeaderInfo.getDropOfType(); // retreiving dropp off type from header bean 
                logger.info("dropoffType  :" + dropoffType);
                packaging = 
                        aascShipmentHeaderInfo.getPackaging(); // retreiving packaging from header bean    
                logger.info("packaging  :" + packaging);
                logger.info("shipMethodName  :" + shipMethodName);
                service = 
                        aascShipMethodInfo.getConnectShipScsTag(shipMethodName);
                portString = aascShipMethodInfo.getCarrierPort(carrierId);
                if (portString != null && !(portString.equals(""))) {
                    port = Integer.parseInt(portString);
                } else {
                    logger.severe("portString is null " + portString);
                }
                String host1 = aascShipMethodInfo.getCarrierServerIPAddress(carrierId);
                protocol = aascShipMethodInfo.getProtocol(carrierId);
                host = protocol+":"+host1;
                reference1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference1()); // retreiving purchase order number from header bean.
                reference1 = 
                        encode(reference1); 
                reference2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference2()); // retreiving purchase order number from header bean.
                reference2 = 
                        encode(reference2); 

                
                    if (shipToCompanyName.length() > 35) {
                        shipToCompanyName = 
                                encode(shipToCompanyName.substring(0, 35));
                    } else {
                        shipToCompanyName = 
                                encode(shipToCompanyName);
                    }
               

                logger.info("shipToCompanyName in DHL shipment::" + 
                            shipToCompanyName);
               
                referenceValue1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference1());
                referenceValue2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference2());
                if(aascIntlInfo ==  null)
                    aascIntlInfo = new AascIntlInfo();
                
                aascIntlHeaderInfo = aascIntlInfo.getIntlHeaderInfo();
                intlAccountNumberStr = aascIntlHeaderInfo.getIntlAccountNumber();
                intlPayerType = (String)carrierPayMethodCodeMap.get(nullStrToSpc(aascIntlHeaderInfo.getIntlPayerType()));
                
                
                termsOfTrade =aascIntlHeaderInfo.getTermsOfTrade(); //Shiva added 
                 if("".equalsIgnoreCase(termsOfTrade) || termsOfTrade == null)//default value if user does not specify.
                     termsOfTrade = "DDP";
                 
               // System.out.println("terms of trade in AascDHLShipment.java :::::::"+termsOfTrade);
                //if intlAccountNumberStr is null we sending sender account number
                if("".equalsIgnoreCase(intlAccountNumberStr) || intlAccountNumberStr == null)
                    intlAccountNumberStr = senderAccountNumber;
                    
                    
                    
                if("".equalsIgnoreCase(intlPayerType) || intlPayerType == null)
                    intlPayerType = carrierPayMethod;
                
                
//                currencyCode  = aascShipmentHeaderInfo.getTransactionalCurrCode();
                System.out.println("-------currencyCode----->"+currencyCode);
                
        
                logger.info("----------calling sentDHLRequest()");

                sendDHLRequest(aascShipmentHeaderInfo,
                                shipPackageInfo,
                                 aascProfileOptionsInfo, 
                                 aascIntlInfo,
                                 shipMethodActualName,
                                 cloudLabelPath);
//                System.out.println("after sending sendDHLRequest");
                responseStatus = 
                        Integer.parseInt((String)hashMap.get("ResponseStatus"));
                
            
            } // end of If
            else {
                responseStatus = 151;
                logger.info("aascDeliveryInfo is null Or aascShipmentHeaderInfo is null" + 
                            "Or packageInfo is null Or aascShipMethodInfo is null ");
                aascShipmentHeaderInfo.setMainError("aascDeliveryInfo is null Or aascShipmentHeaderInfo is null" + 
                                            "Or packageInfo is null Or aascShipMethodInfo is null ");
            }
        } // End of Main Try Block
        catch (Exception e) {
            responseStatus = 151;

            aascShipmentHeaderInfo.setMainError("null values or empty strings passed in request");
            e.printStackTrace();
            logger.severe("Exception::"+e.getMessage());
        }
        logger.info("Exit from processShipment method");
        
     if (errorFlag) {
            responseStatus = 151;
        }
        return responseStatus;
    } // end of getShipmentRequest


    private String getReferenceValue(String refVal1, String refVal2) {
        logger.info("Entered getReferenceValue() method");
        String refTag = "";


        
        return refTag;

    }

   

    public void sendDHLRequest(AascShipmentHeaderInfo aascShipmentHeaderInfo, 
                                 LinkedList shipPackageInfo, 
                                 AascProfileOptionsBean aascProfileOptionsInfo, 
                                 AascIntlInfo aascIntlInfo,
                                 String shipMethodActualName,
                                 String cloudLabelPath) {
                    logger.info("Entered sendDHLRequest method");
                    
                    
                    pkgCount = shipPackageInfo.size();
                    HashMap tempMap = null;
                    String appendStr = "";
            
                    tempMap = new HashMap();
                    String dhlResponse = "";    
                    timeStampStr = (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");
                    
                    //messgageTime=((new SimpleDateFormat("yyyy-MM-dd").format(new Date()))+"T"+(new SimpleDateFormat("HH:mm:ssZ").format(new Date())));
                    //String messageTimeTmp=messgageTime.substring(0,22);
                    //String messageTimeTmp2=messgageTime.substring(22,24);
                    
                     Timestamp time1 = aascShipmentHeaderInfo.getShipTimeStamp();
                     logger.info("TimeStamp =========> ::" + time1);

                     String timeStr = time1.toString();
                     timeStr = nullStrToSpc(timeStr.substring(11, timeStr.length() - 2));
                     
                     String hr = timeStr.substring(0, 2);
                     String min = timeStr.substring(3, 5);
                     String sec = timeStr.substring(6, 8);
                    
                    messgageTime = aascShipmentHeaderInfo.getShipmentDate() + "T" + hr + ":" + min + ":" + sec;//messageTimeTmp+":"+messageTimeTmp2;
                    logger.info("messgageTime ===== "+messgageTime);
                    messageReference = timeStampStr.substring(3);
                    messageReference =  deliveryId+messageReference;
                    if(messageReference.length() <28)
                        messageReference = "0000"+messageReference;
                    if(messageReference.length()>31)
                        messageReference = messageReference.substring(0,31);

                if (host != null && !(host.equals("")))
            //if(true)
            {

                try 
                {
               
                    logger.info("Calling DHL Webservice services code...");
                    String intFlag = nullStrToSpc(aascShipmentHeaderInfo.getIntlShipFlag());
                    totalWeight = aascShipmentHeaderInfo.getPackageWeight();
                    
                    // added flag for quote request...
                    String quoteFlag = "true";
                    logger.info("calling quote request");
                    
                    quoteResponse = callDHLWS( intFlag, 
                                                quoteFlag,  totalWeight, shipPackageInfo, aascIntlInfo,"","");
                    
                    AascDHLShipmentInfo aascDHLShipmentInfo = new AascDHLShipmentInfo();
                    
                    String quoteParsing =  aascDHLShipmentInfo.parseQuoteSeriveResponse(quoteResponse,
                                                                                         intlFlag,
                                                                                         shipMethodActualName,
                                                                                         cloudLabelPath);                     
                        
                    if(quoteParsing.contains("success"))//parse quote request
                    {
                        String[] localParts = quoteParsing.split("###");
                        
//                        String quoteParseFlag = localParts[0];
                        String productName = localParts[1];
//                        String quoteShipmentCost = localParts[2];
                        //System.out.println("quoteLocalProductName "+productName);
                        
                        String globalProductName = "";
                        String localProductName = "";
                        
                        try{
                            localProductName = productName.substring(0,1);
                            globalProductName = productName.substring(1,2);
                        }
                        catch(Exception e){
                            logger.info("Error in getting product name");
                        }
                        
                        if("@".equalsIgnoreCase(globalProductName))
                            globalProductName = " ";
                        if("@".equalsIgnoreCase(localProductName))
                            localProductName = " ";
                        logger.info("calling shipment request after getting succes response from quote parsing");
                        quoteFlag = "false";
                        
                        
                        dhlResponse = callDHLWS( intFlag, quoteFlag,  totalWeight,  
                                                        shipPackageInfo, aascIntlInfo,globalProductName,localProductName);
                                                        
                        logger.info("calling parse DHL responce ");
                        tempMap = aascDHLShipmentInfo.parseWebServiceResponse(dhlResponse,
                                                                             aascShipmentHeaderInfo,
                                                                             shipPackageInfo,
                                                                             intlFlag,
                                                                             ajaxcarrierservicelevel,
                                                                             quoteParsing,
                                                                             carrierPayMethod,
                                                                             cloudLabelPath);
                        
                        
                    }
                    else 
                    {
                        // call parsing and update bean for error msg 
                        tempMap = aascDHLShipmentInfo.parseWebServiceResponse(dhlResponse,
                                                                            aascShipmentHeaderInfo,
                                                                            shipPackageInfo,
                                                                            intlFlag,
                                                                            ajaxcarrierservicelevel,
                                                                            quoteParsing,
                                                                            carrierPayMethod,
                                                                            cloudLabelPath);
                      
                    }
                    
                    hashMap = tempMap;
                    parseStatus = (String)tempMap.get("status");
                    
                    if (dhlResponse != null && !dhlResponse.equals("")) {
                        logger.info("--------------->DHL responce<--------------------");
                
                    
                    if ("success".equalsIgnoreCase(parseStatus)) 
                    {
                        logger.info(" if(parseStatus.equalsIgnoreCase('success'))" + 
                                    parseStatus);
                        responseStatus = 150;
                        tempMap.put("ResponseStatus", 
                                    String.valueOf(responseStatus));
                        hashMap = tempMap;
                        logger.info("response status:" + responseStatus);
                        String wayBil = (String)tempMap.get("masterTrkNum");
                        
                        logger.info("getting waybill no for label name");
                        
                        aascShipmentHeaderInfo.setMainError("");
                        
                        
                    }
                    else 
                    {
                        logger.info("inside else of if(parseStatus.equalsIgnoreCase('success'))" + 
                                    parseStatus);
                        aascShipmentHeaderInfo.setMainError(parseStatus);
                        responseStatus = 151;
                        tempMap.put("ResponseStatus", 
                                    String.valueOf(responseStatus));
                        hashMap = tempMap;
                        logger.info("response Status:" + responseStatus);
                    }
                } // end of if (shipmentResponse != null && !shipmentResponse.equals(""))
                else 
                {
                //fail to get response
                logger.info("when dhlResponse is null not been called "+responseStatus);
                aascShipmentHeaderInfo.setMainError(parseStatus);
                responseStatus = 151;
                tempMap.put("ResponseStatus", 
                            String.valueOf(responseStatus));
                hashMap = tempMap;
                logger.info("response Status:" + responseStatus);
                }
                
                } // end of try  
                catch (Exception e) 
                {
                    responseStatus = 151;
                    tempMap.put("ResponseStatus", String.valueOf(responseStatus));
                    hashMap = tempMap;
                    aascShipmentHeaderInfo.setMainError(e.getMessage());
                    logger.severe( e.getMessage());
                } // end of catch 
            } // end of if
            else 
            {
                logger.severe("either port or host is null:" + "\n host:" + host + 
                              "\n port:" + port);
                aascShipmentHeaderInfo.setMainError("either port or host is null:" + 
                                            "\n host:" + host + "\n port:" + port);
                responseStatus = 151;
                tempMap.put("ResponseStatus", String.valueOf(responseStatus));
                hashMap = tempMap;
            }
            logger.info("Exit from sendDHLRequest method");
            
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
     * @param obj-object of type Object
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
     * @param  String as input 
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
     * @param takes String containing from substring which is replaced by to substring
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
     * Information and so on  and passes the request to DHL atom server
     * and receives the Response from server.
     * @param aascAdhocOrderInfo AascAdhocOrderInfo Object to get the Order Information
     * @param aascShipMethodInfo AascShipMethodInfo Object to get the ShipMethod Information
     * @return Returns the int value , if 150 the response is success otherwise 
     * if value is 151 then there is an error in the response file.
     * */

    public String trim(String str) {

        return str.replaceFirst(" ", "");

    }



    public String callDHLWS(String intFlag, String quoteFlag,
                              Double totalWeight, LinkedList shipPackageInfo, 
                              AascIntlInfo aascIntlInfo,
                              String globalProductName,
                              String localProductName) {
        logger.info("----------enter call DHL ws-----------");
        //HttpSession session = request.getSession();                    
        String reply = "";
        String appendStr = "";
        try{
         HashMap hm = new HashMap();
         hm.put("customerCarrierAccountNumber", 
                customerCarrierAccountNumber);
         hm.put("orderName", 
                orderName);  
        hm.put("shipDate", shipDate);
        logger.info("shipDate  ====>" + shipDate);
        
        //Ship From Location Details
        hm.put("shipFromDepartment", shipFromDepartment);
        logger.info("======>shipFromDepartment  ::" + shipFromDepartment);
        hm.put("shipFromCompanyName", shipFromCompanyName);
        logger.info("=======>shipFromCompanyName" + shipFromCompanyName);
        hm.put("shipFromPersonName", shipFromPersonName);
        logger.info("======>shipFromPersonName" + shipFromPersonName);
        hm.put("shipFromPhoneNumber1", shipFromPhoneNumber1);
        logger.info("======>shipFromPhoneNumber1  ::" + 
                    shipFromPhoneNumber1);
        hm.put("shipFromCode", shipFromCode);
        logger.info("======>shipFromCode  ::" + 
                    shipFromCode); 
                    
        hm.put("shipToContactPhoneNumber", shipToContactPhoneNumber);
        logger.info("======>shipToContactPhoneNumber  ::" + 
                    shipToContactPhoneNumber);
        hm.put("shipToCode", shipToCode);
        logger.info("======>shipToCode  ::" + 
                    shipToCode);
                   
        hm.put("shipFromAddressLine1", shipFromAddressLine1);
        logger.info("======>shipFromAddressLine1  ::" + 
                    shipFromAddressLine1);
        hm.put("shipFromAddressLine2", shipFromAddressLine2);
        logger.info("======>shipFromAddressLine2  ::" + 
                    shipFromAddressLine2);
        hm.put("shipFromAddressCity", shipFromAddressCity);
        System.out.println("shipFromAddressCity::967::"+shipFromAddressCity);
        hm.put("shipFromAddressState", shipFromAddressState);
        System.out.println("shipFromAddressState::::"+shipFromAddressState);
        hm.put("shipFromAddressPostalCode", shipFromAddressPostalCode);
        System.out.println("shipFromAddressPostalCode::::"+shipFromAddressPostalCode);
        hm.put("shipFromCountry", shipFromCountry);
     System.out.println("shipFromCountry::::"+shipFromCountry);
        //Ship To Location Details
        hm.put("termsOfTrade", termsOfTrade);
        logger.info("======>termsOfTrade  ::" + termsOfTrade);
        
        hm.put("currencyCode", currencyCode);
        logger.info("======>currencyCode  ::" + currencyCode);
        
        hm.put("shipToCompanyName", shipToCompanyName);
        logger.info("======>shipToCompanyName  ::" + shipToCompanyName);
        hm.put("shipToContactPersonName", shipToContactPersonName);
        logger.info("======>shipToContactPersonName  ::" + 
                    shipToContactPersonName);
        hm.put("shipToAddressLine1", shipToAddressLine1);
        logger.info("======>shipToAddressLine1  ::" + shipToAddressLine1);
        hm.put("shipToAddressLine2", shipToAddressLine2);
        logger.info("======>shipToAddressLine2  ::" + shipToAddressLine2);
        hm.put("shipToAddressLine3", shipToAddressLine3);
        logger.info("======>shipToAddressLine3  ::" + shipToAddressLine3);
        hm.put("shipToAddressCity", shipToAddressCity);
        logger.info("=====>shipToAddressCity ::" + shipToAddressCity);
        hm.put("shipToAddressState", shipToAddressState);
        logger.info("=====>shipToAddressState ::" + shipToAddressState);
        hm.put("shipToAddressPostalCode", shipToAddressPostalCode);
        logger.info("=====>shipToAddressPostalCode ::" + shipToAddressPostalCode);
        
        hm.put("shipToCountryFullName", shipToCountryFullName);
        logger.info("=====>shipToCountryFullName ::" + shipToCountryFullName);
        hm.put("shipFromCountryFullName", shipFromCountryFullName);
        logger.info("=====>shipFromCountryFullName ::" + shipFromCountryFullName);
//        hm.put("transactionalCurrCode", transactionalCurrCode);
//        logger.info("=====>transactionalCurrCode ::" + transactionalCurrCode);
        hm.put("dhlContent", dhlContent);
        logger.info("=====>dhlContent ::" + dhlContent);
        
        hm.put("shipToCountry", shipToCountry);
        logger.info("=====>shipToCountry ::" + shipToCountry);
        hm.put("SenderEmail", SenderEmail);
        logger.info("========>SenderEmail ::" + SenderEmail);
        hm.put("recipientEmailAddress1", recipientEmailAddress);
        logger.info("========>recipientEmailAddress1 ::" + recipientEmailAddress);
        
        hm.put("referenceValue1", referenceValue1);
        logger.info("========>referenceValue1 ::" + referenceValue1);
        hm.put("carrierPayMethod", carrierPayMethod);
        logger.info("========>carrierPayMethod ::" + carrierPayMethod);
  
        hm.put("pkgCount", pkgCount);
        logger.info("========>pkgCount ::" + pkgCount);
        hm.put("outputFile", outputFile);
        logger.info("========>outputFile ::" + outputFile);
        hm.put("timeStampStr", timeStampStr);
        logger.info("========>timeStampStr ::" + timeStampStr);
        
        hm.put("messgageTime", messgageTime);
        logger.info("========>messgageTime ::" + messgageTime);
        
        hm.put("messageReference", messageReference);
        logger.info("========>messageReference ::" + messageReference);
        
        hm.put("deliveryId", deliveryId);
        logger.info("========>deliveryId ::" + deliveryId);
        hm.put("totalVolume", totalVolume);
        logger.info("========>totalVolume ::" + totalVolume);
            //ajaxcarrierservicelevel = "D";
        hm.put("globalProductName", globalProductName);
        logger.info("========>globalProductName ::" + globalProductName);
        
        hm.put("localProductName", localProductName);
        logger.info("========>localProductName ::" + localProductName);
            
        hm.put("labelFormat", labelFormat);
        logger.info("========>labelFormat ::" + labelFormat); 
        
        hm.put("labelStockOrientation", labelStockOrientation);
        logger.info("========>labelStockOrientation ::" + labelStockOrientation);
        
        hm.put("dhlRegionCode", dhlRegionCode);
        logger.info("========>dhlRegionCode ::" + dhlRegionCode);
            
        hm.put("option", option);
        logger.info("========>option ::" + option);
        
        if(totalInscuredValue > 0)
        {
        hm.put("totalInscuredValue", totalInscuredValue);
        logger.info("========>totalInscuredValue ::" + totalInscuredValue);
        }
        else {
        hm.put("totalInscuredValue", totalGoodsValueStr);
        logger.info("========>totalInscuredValue ::" + totalGoodsValueStr);    
        }
         
        hm.put("totalWeight", totalWeight);
        
        logger.info("senderAccountNumber" + senderAccountNumber);
        hm.put("senderAccountNumber", senderAccountNumber);
        
        logger.info("receiverAccountNumber" + receiverAccountNumber);
        hm.put("receiverAccountNumber", receiverAccountNumber);
        
        logger.info("shipperAccountNumber" + shipperAccountNumber);
        hm.put("shipperAccountNumber", shipperAccountNumber);
        
        logger.info("ref1n2" + ref1n2);
        hm.put("ref1n2", ref1n2);
        
        logger.info("dhlPassword" + dhlPassword);
        hm.put("dhlPassword", dhlPassword); 
        
        logger.info("siteId" + siteId);
        hm.put("siteId", siteId);
        
        logger.info("aascIntlInfo "+aascIntlInfo);
        hm.put("aascIntlInfo", aascIntlInfo);
            
        logger.info("intlAccountNumberStr "+intlAccountNumberStr);
        hm.put("intlAccountNumberStr", intlAccountNumberStr);
        
        logger.info("intlPayerType "+intlPayerType);
        hm.put("intlPayerType", intlPayerType);
        com.aasc.erp.carrier.dhlws.DHLShipWebService dhlShipWebService = 
                new com.aasc.erp.carrier.dhlws.DHLShipWebService();
                
        DHLGetQuoteWS dhlQuoteService = 
             new DHLGetQuoteWS();
             
        if("true".equalsIgnoreCase(quoteFlag))//for quote
        {
            reply = dhlQuoteService.getQuoteInfo(hm, host, port, shipPackageInfo);
            
        }
        else 
        { // for shipment
             reply = dhlShipWebService.getShipInfo(hm, host, port, shipPackageInfo,intlFlag);
        }
            //logger.info("--------------->reply  :: " + reply);
            
            
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
            replySB.append("Error Sending request to DHL. ");
            if (e.getMessage().contains("java.net.ConnectException")) {
                replySB.append("Please check the connection details in Carrier Configuration.");
            } else {
                replySB.append(e.getMessage());
            }
            replySB.append("</Message>");
            replySB.append("<Type>Ship</Type>");
            replySB.append("</Error>");
            replySB.append("</DHLShip2Reply>");
            reply = replySB.toString();
        } finally {
            return reply;
        }
    }

}
