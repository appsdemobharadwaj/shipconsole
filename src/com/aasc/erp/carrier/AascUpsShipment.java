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


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.net.HttpURLConnection;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;



/**
 * Creates xml shipment request and retreives xml shipment response and responseStatus from UPS server.
 * Ups Shipment consists of 2 phases ship confirm phase followed by the ship accept phase.An XML 
 * request/response pair is exchanged between the client and server in each phase.
 * 
 * After the ShipmentConfirmRequest message is received by the server, it is preprocessed and validated. 
 * If the ShipmentConfirmRequest message passes all validation tests, a ShipmentConfirmResponse message is
 * returned containing basic rate information, the Shipment Id, and the shipment digest. If the 
 * ShipmentConfirmRequest message fails validation, a ShipmentConfirmResponse message is returned 
 * containing error information. The majority of the validation is performed in this phase.
 * 
 * The actual shipment has not been created at this point. In order to create a shipment and receive the
 * shipping label(s), a ShipAcceptRequest message must be created and sent to the ups server.
 *  The ShipAcceptRequest message must contain the shipment digest returned in the ShipmentConfirmResponse message. 
 * After the ShipmentAcceptRequest message is received in the server, additional processing validation is performed. 
 * If the ShipmentAcceptRequest message passes all validation tests, a ShipmentAcceptResponse message is returned 
 * containing detailed rate information, the Shipment Id, package tracking numbers and the shipping label(s). If the
 *  ShipmentAcceptRequest message fails validation, a ShipmentAcceptResponse message is returned containing
 *  error information.

 * AascUpsShipment class is used to create ShipmentConfirmRequest and ShipAcceptRequest.Sends the request to
 * the ups server and retreives the response.Response is sent to AascUpsShipmentInfo class for parsing and 
 * retreives the parsed status.
 
 *@author 
 *@version 1	 
 *@since
 * 
 * 18/12/2014   Eshwari M      Renamed adhoc to shipment for SC Lite 
 * 08/01/2015   Y Pradeep      Modified code to account number from shipment page.
 * 19/01/2015   Suman G        Removed unused comments in history and code.
 * 16/02/2015   Eshwari M      Modified void related code
 * 05/03/2015    Sanjay & Khaja Added code for new UI changes.
 * 13/03/2015   Y Pradeep      Added code to send Address Line 3 and EmailId for both ShipTo and ShipFrom, Residential Flag check.
 * 06/05/2015   Suman G         Added code to fix #2900 getting shipper number for paymethods other than prepaid.
 * 02/06/2015   Y Pradeep      Modified code to create request tags for packages only if Void Flag is not equals to Y.
 * 20/07/2015   Suman G         Added code for implement Email Notification.
 * 05/08/2015   Suman G         Modified code for issue #3294
 */
public class

AascUpsShipment {
     
    private LinkedList linkedList = null; // linked list containing delivery or order packages information objects
    private ListIterator listIterator = null; // list iterator used to traverse through packages linked list
    private String shipmentResponse = ""; // String Containg the shipment response xml file
    private StringBuffer shipmentRequest = null; // String containing Shipment Request In XML format  
    private String parsedStatus = ""; // indicates the status of parsing
    private static Logger logger = AascLogger.getLogger("AascUpsShipment"); // logger object used for issuing logging requests
    private AascUpsShipmentInfo aascUpsShipmentInfo = null; // carrier shipment bean class object for parsing the response
    private String errorMessage = ""; // holds the error in failed response  
    private int responseStatus = 0; // holds value of 150 if valid response is returned and is successfully parsed else it holds 151
    private String outputFilePath = ""; // holds folder name to which request and response are written 
    
    private AascXmlTransmitter aascXmlTransmitter = null; // creates AascXmlTransmitter Object to which the request is sent for processing 
    private AascConnectShipConnection aascConnectShipConnection = null; // object to create connection to ups server
    private HttpURLConnection connection = null; // url connection with support for http specific features
    private String protocol = ""; // ="http";                                        //protocol used to communicate with ups server
    private String hostName = ""; // ="63.77.35.115";                                //host name of system which receives the request 
    private String prefix = ""; // ="Progistics/XML_Processor/Server/XMLProcDLL.asp";//page which receives processes request
    private String userName = ""; // ="";                                            //username to obtain authorised access to ups server
    private String password = ""; // ="";   

    private int numPackages = 0; // indicates number of packages for which we require tracking numbers
    private String reference1 = ""; 
     private String reference2 = "";
    private String shipToCompanyName = ""; // indicates ship to company name(these details are required to be sent to ups server for calculating shipment cost)
    private String shipToAddressLine1 = ""; // indicates ship to address   
    private String shipToAddressLine2 = ""; // indicates ship to address
    private String shipToAddressLine3 = ""; // indicates ship to address  
     private String shipToEmailId = ""; 
    private String shipToAddressCity = ""; // indicates ship to address city
    private String shipToAddressPostalCode = ""; // indicates ship to postal code
    private String shipToCountry = ""; // indicates ship to country
    private String shipToAddressState = ""; // indicates ship to state address
    private String residentialStr = "";
    private String shipFromCompanyName = ""; // indicates the company name from which delivery is being shipped
    private String shipFromPersonName = "";
    private String shipFromPhoneNumber = ""; // indicates sender phone number
    private String shipFromAddressLine1 = ""; // indicates ship From address   
    private String shipFromAddressLine2 = ""; // indicates ship From address 
    private String shipFromAddressLine3 = ""; // indicates ship From address 
     private String shipFromEmailId = "";
    private String shipFromAddressCity = ""; // indicates ship From address city    
    private String shipFromAddressPostalCode = ""; // indicates ship From postal code
    private String shipFromCountry = ""; // indicates ship From country
    private String shipFromAddressState = ""; // indicates ship From state address

    private java.sql.Date shipDate = null; // indicates ship date

    private String shipperName = ""; // indicates clients location name

    private String shipMethodName = ""; // indicates ship method name
    private String shipperTerm = ""; // indicates the terms on which the shipping is prioritised
    private String connectshipSCS = ""; // indicates connectship scs tag
    private String customerAccountNumber = ""; // indicates customer account number
    private String contactName = ""; // indicates the company name to which delivery should be shipped
    private String phoneNumber = ""; // indicates customer phone number
    private String carrierPayMethod = ""; // indicates carrier payment terms
    private String carrierPayMethodCode = ""; // indicates code of carrier payment terms
    private String satShipFlag = ""; // flag which indicates whether the delivery is shipped on saturday or not
    private String satShipFlagTag = "";
    private String nonDiscountCostFlag = ""; // flag which indicates non discounted cost
    private int carrierId = 0; // indicates carrier id
    private String acctNegotiatedCertified = "";
    private String shipmentRequestHdr = ""; // string that holds xml shipment request header
    //private String shipmentRequestFooter =""; // holds request footer information 
    private float pkgWtVal; // holds package weight value
    private String pkgWtUom = ""; // holds package unit of measure
    private String cod = ""; // charge on delivery flag 
    private float codAmount = 0; // holds the cost for charge on delivery     
    
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
    private String pkgStrCOD = "";
    private String pkgStrFooter = "";

    private HashMap parsedDataHashMap = null;


    private String paymentInfoTag = "";
    private String rateInfoTag = "";

    private String packageStrTag = "";
    private String shipAcceptRequestStr = "";
    private StringBuffer shipAcceptRequest = null;
    private String shipmentDigest = "";
    private String shipAcceptResponse = "";
    String FormTypeTag1 = "";
    String ProductTags1 = "";
    String otherTags1 = "";
    String FormTypeTag2 = "";
    String ProductTags2 = "";
    String otherTags2 = "";
    String FormTypeTag3 = "";
    String ProductTags3 = "";
    String otherTags3 = "";
    String FormTypeTag4 = "";
    String ProductTags4 = "";
    String otherTags4 = "";
    String FormTypeTagCI = "";
    String ProductTagsCI = "";
    String otherTagsCI = "";
    String FormTypeTagNAFTA = "";
    String ProductTagsNAFTA = "";
    String otherTagsNAFTA = "";
    String FormTypeTagUSCO = "";
    String ProductTagsUSCO = "";
    String otherTagsUSCO = "";
    String FormTypeTagSED = "";
    String ProductTagsSED = "";
    String otherTagsSED = "";
    String ProductTags = "";
    String mainProductTags = "";
    String mainPTag = "";
    String otherTags = "";
    String producerTags = "";
    String UltimateConsigneeTags = "";
    String otherTagsnafta = "";
    String otherTagssed = "";
    String eachProducerTags = "";
    private String labelFormat = "";
    private String docTabLocation = "";
    private String labelFormatTag = "";
    private String senderAccountNumber = "";
    private String upsServiceLevelCode = "";
    private String packaging = "";
    private String codType = "";
    private String codFundsCode = "";
    private String dcisType = "";
    private double declaredVal = 0.0;
    private String codCurrCode = "";
    private String declaredCurrCode = "";
    private String declaredTag = "";
    private String dcisTag = "";

    private String accessLicenseNumber = "";
    private String acctPostalCode = "";
    
    private String timeStampStr = 
        (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");

    private AascIntlHeaderInfo aascIntlHeaderInfo = null;
    
    private String soldCompanyName = "";
    private String soldAddressLine1 = "";
    private String soldAddressLine2 = "";
    private String soldCity = "";
    private String soldStProvinceCd = "";
    private String soldPostalCd = "";
    private String soldCountryCd = "";
    private String soldTaxId = "";

    private String shipToTaxId = "";
    private String shipFromTaxId = "";

    private String ciNumber = "";
    private String ciUOM = "";
    private String ciDesc = "";
    private String prdTarrifCode = "";
    private String prdCountry = "";
    private String ciPricePerUnit = "";
    private String ciPurchaseOrderNumber = "";
    private String ciInvoiceNumber = "";
    private String ciTermsOfShipment = "";
    private String ciReasonForExport = "";
    private String ciComments = "";
    private double ciDiscount = 0.0;
    private double ciFreightCharges = 0.0;
    private double ciInsuranceCharges = 0.0;
    private double ciOtherCharges = 0.0;
    private String ciDeclarationStatement = "";
    private String ciInvoiceDate = "";
    private String ciCurrencyCode = "";

    private String ciInvoiceNumberTag = "";
    private String ciPurchaseOrderNumberTag = "";
    private String ciTermsOfShipmentTag = "";
    private String ciCommentsTag = "";
    private String ciDeclarationStatementTag = "";
    private String ciDiscountTag = "";
    private String ciFreightChargesTag = "";
    private String ciInsuranceChargesTag = "";
    private String ciOtherChargesTag = "";

    private String naftaNetCostCode = "";
    private String naftaNetCostCodeTags = "";
    private String naftaNetCostBeginDate = "";
    private String naftaNetCostEndDate = "";
    private String naftaPreferenceCriteria = "";
    private String naftaProducerInfo = "";
    private String naftaCompanyName = "";
    private String naftaAddressLine1 = "";
    private String naftaCity = "";
    private String naftaStateProvinceCode = "";
    private String naftaPostalCode = "";
    private String naftaCountryCode = "";
    private String naftaTaxId = "";
    private String BeginDate = "";
    private String EndDate = "";

    private int uscoNumPkgPerCommodity = 0;
    private String productUOM = "";
    private String productWeight = "";
    private String uscoExportDate = "";
    private String uscoExportingCarrier = "";

    private String sedSchBNumber = "";
    private String sedSchQuantity = "";
    private String sedSchUnitOfMeasurement = "";
    private String sedExportType = "";
    private double sedTotalVal = 0.0;
    private String sedCompanyName = "";
    private String sedAddressLine1 = "";
    private String sedCity = "";
    private String sedStateProvinceCode = "";
    private String sedPostalCode = "";
    private String sedCountryCode = "";
    private String sedExportDate = "";
    private String sedExportingCarrier = "";
    private String sedInBondCode = "";
    private String sedContainerized = "";
    private String sedRoutedExportTransaction = "";
    private String sedPointOfOrigin = "";
    private String sedModeOfTransport = "";
    private String sedPartiesToTransaction = "";
    private String sedLicNumber = "";
    private String sedLicDate = "";
    private String sedCarrierID = "";
    private String sedPortOfUnloading = "";
    private String sedLoadingPier = "";
    private String sedPortOfExport = "";
    private String sedEntryNumber = "";

    private String faCompanyName = "";
    private String faAddressLine1 = "";
    private String faCity = "";
    private String faStateProvinceCode = "";
    private String faPostalCode = "";
    private String faCountryCode = "";
    private String fTaxId = "";
    private String faTag = "";
    private String icCompanyName = "";
    private String icAddressLine1 = "";
    private String icCity = "";
    private String icStateProvinceCode = "";
    private String icPostalCode = "";
    private String icCountryCode = "";
    private String icTag = "";

    private String shipFromAttention = "";
    private String shipFromPhone = "";
    private String soldToAttention = "";
    private String soldToPhone = "";
    private String invCurrencyCode = "";
    private int invValue = 0;

    private String sedLicTag = "";
    private String sedCarrierIDTag = "";
    private String sedPortOfUnloadingTag = "";
    private String sedLoadingPierTag = "";
    private String sedPortOfExportTag = "";
    private String sedEntryNumberTag = "";
    private String sedContainerizationTag = "";
    private String sedRoutedExportTransactionTag = "";
    private String invTag = "";
    private String solToTag = "";
    private String intlTag = "";

    private String ciFlag = "";
    private String naftaFlag = "";
    private String uscoFlag = "";
    private String sedFlag = "";

    private double packageLength = 0;
    private double packageWidth = 0;
    private double packageHeight = 0;
    private String packageUnits = "";

    private String companyNameFromPO = "";

    private String orderNumber;

    private String sedExceptionCode = "";
    private String sedEccnNumber = "";
    private String sedEccnNumberTag = "";
    
    // Email Notification details
     private String SenderEmail = "";
     private String ShipAlertNotification = "";
     private String ExceptionNotification = "";
     private String DeliveryNotification = "";
     private String NotificationVal = "";
     private String Format = "";
     private String message = "";
     private String OptionalMessage = "";
     private String emailReference1 = "";
     private String emailReference2 = "";
     private String emailReferenceVal = "";
     private String emailReferenceVal1 = "";
     private String emailCustomerName = "";
     private String RecipientEmailAddress1 = "";
     private String RecipientEmailAddress2 = "";
     
    
    
    /**
     Default Constructor.
     */
    public AascUpsShipment() {
    }

     /**
     For writing a string to specified file.
     @param str String containing data that is to be written to a file.
     @param file String containing complete filePath including fileName.
     */
    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);

        fout.write(str.getBytes());
        fout.close();
    }

    /** Replaces the null values with nullString.
     @return spcStr String that is "".
     @param obj object of type Object.
     */
    private String nullStrToSpc(Object obj) {
        String spcStr = "";

        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

    /**
     Method used to set errorMessage.
     @param errorMessage String.
     */
    /*private void setError(String errorMessage) {
        this.errorMessage = errorMessage;
    }*/

    /**
     Method used to get errorMessage.
     @return errorMessage String.
     */
    public String getError() {
        return errorMessage;
    }

    AascShipmentHeaderInfo aascShipmentHeaderInfo;
   // private String reference123;
    private String printerPort;
    private String stockSymbol;
    private String printerModelSymbol;
    AascShipmentPackageInfo aascShipmentPackageBean;


    public int createShipmentRequest(AascShipmentOrderInfo aascShipmentOrderInfo, 
                                     AascShipMethodInfo aascShipMethodInfo, 
                                     AascProfileOptionsBean aascProfileOptionsInfo, 
                                     AascIntlInfo aascIntlInfo,String cloudLabelPath ) {
        logger.info("Entered createShipmentRequest() for Shipment");
        try {
            shipmentRequest = 
                    new StringBuffer(); // StringBuffer that holds shipmentRequest  
            aascShipmentHeaderInfo = 
                    aascShipmentOrderInfo.getShipmentHeaderInfo(); // returns header info bean object
            linkedList = 
                    aascShipmentOrderInfo.getShipmentPackageInfo(); // returns the linkedlist contains the package info bean objects        

            orderNumber = aascShipmentHeaderInfo.getOrderNumber();

            
            outputFilePath = cloudLabelPath; //gururaj
            if (aascShipmentOrderInfo != null && aascShipmentHeaderInfo != null && 
                linkedList != null && aascShipMethodInfo != null) {
                carrierId = 
                        aascShipmentHeaderInfo.getCarrierId(); //carrier id retreived from shipment header bean            
                numPackages = 
                        linkedList.size(); // indicates total number of packages for which we require tracking numbers
                reference1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference1()); // retreiving customer purchase order number from header bean
              //  System.out.println("Reference1::::::::"+reference1);
                reference2 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getReference2()); //retreiving reference2 from shipment header bean
              //  System.out.println("Reference2::::::::"+reference2);
                shipToCompanyName = 
                       encode(nullStrToSpc(aascShipmentHeaderInfo.getCustomerName())); // retreiving ship to company name from header bean        
                shipToAddressLine1 = 
                        nullStrToSpc(aascShipmentHeaderInfo.getAddress()); // retreiving ship to address from header bean            
                shipToAddressLine1 =encode(shipToAddressLine1); //Added by Jagadish 
                shipToAddressLine2 =nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine2()); // retreiving ship to address from header bean
                 shipToAddressLine2 =encode(shipToAddressLine2); //Added by Jagadish 
                shipToAddressLine3 = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine3())); 
                 shipToEmailId = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipToEmailId())); 
                shipToAddressCity = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCity()); // retreiving ship to city from header bean            
                shipToAddressPostalCode = 
                        nullStrToSpc(String.valueOf(aascShipmentHeaderInfo.getPostalCode())); // retreiving ship to postal code from header bean            
                shipToCountry = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCountrySymbol()).toUpperCase(); // retreiving ship to country name from header bean
                shipToAddressState = 
                        nullStrToSpc(aascShipmentHeaderInfo.getState()).toUpperCase(); // retreiving ship to state from header bean
                
                if("Y".equalsIgnoreCase(aascShipmentHeaderInfo.getResidentialFlag()))
                    residentialStr = "<ResidentialAddress/>";   // Residential Flag
                
                shipDate = 
                        aascShipmentHeaderInfo.getShipmentDate(); // retreiving ship date from header bean
                shipperName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipperName()); // retreiving shipperName name                                                       
                printerPort = 
                        aascShipMethodInfo.getPrinterPort(carrierId); //retreiving printer port from shipmethod info bean 
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
                        
                // Email Notification Details
                if("Y".equalsIgnoreCase(aascShipmentHeaderInfo.getEmailNotificationFlag())){
                     SenderEmail = encode(aascShipmentHeaderInfo.getShipFromEmailId());
                    emailReference1 = encode(nullStrToSpc(aascShipmentHeaderInfo.getReference1Flag()));
                    emailReference2 = encode(nullStrToSpc(aascShipmentHeaderInfo.getReference2Flag()));
                    ShipAlertNotification = 
                            encode(nullStrToSpc(aascShipmentHeaderInfo.getShipNotificationFlag()));
                    
                    ExceptionNotification = 
                            encode(nullStrToSpc(aascShipmentHeaderInfo.getExceptionNotification()));
                    logger.info("ExceptionNotification==" + ExceptionNotification);
                    DeliveryNotification = 
                            encode(nullStrToSpc(aascShipmentHeaderInfo.getDeliveryNotification()));
                    logger.info("DeliveryNotification==" + DeliveryNotification);
                    Format = nullStrToSpc(aascShipmentHeaderInfo.getFormatType());
                    emailCustomerName = nullStrToSpc(aascShipmentHeaderInfo.getEmailCustomerName());
                    
                            
                    if(emailCustomerName.equalsIgnoreCase("Y")){
                        message = "/ Customer : "+nullStrToSpc(encode(aascShipmentHeaderInfo.getCustomerName()));
                    }
        
                    if (emailReference1.equalsIgnoreCase("Y")) {
                        emailReferenceVal = "<SubjectCode>03</SubjectCode>";
                        message = message + "/ Ref 1: "+nullStrToSpc(aascShipmentHeaderInfo.getReference1());
                    }
        
                    if (emailReference2.equalsIgnoreCase("Y")) {
                        emailReferenceVal1 = "<SubjectCode>04</SubjectCode>";
                        message = message + "/ Ref 2:" + nullStrToSpc(aascShipmentHeaderInfo.getReference2());
                    }
        
                    if (ShipAlertNotification.equalsIgnoreCase("Y")) {
                        NotificationVal = "6";
                    }
                    if (ExceptionNotification.equalsIgnoreCase("Y")) {
                        NotificationVal = "7";
                    }
                    if (DeliveryNotification.equalsIgnoreCase("Y")) {
                        NotificationVal = "8";
                    }
        
                    if (!message.equalsIgnoreCase("")) {
                        OptionalMessage = "<Memo>" + message + "</Memo>";
        
                    } else {
                        OptionalMessage = "";
                    }

                
                if (!shipToEmailId.equalsIgnoreCase("")) {
                    RecipientEmailAddress1 = 
                            "<Notification>" + "<NotificationCode>" + 
                            NotificationVal + "</NotificationCode>" + 
                            "<EMailMessage> " + "<EMailAddress>" + 
                            shipToEmailId + "</EMailAddress>" + 
                            OptionalMessage + emailReferenceVal + 
                            emailReferenceVal1 + "</EMailMessage>" + 
                            "</Notification>";
    
                } else {
                    RecipientEmailAddress1 = "";
                }
                if (!SenderEmail.equalsIgnoreCase("")) {
                    RecipientEmailAddress2 = 
                            "<Notification>" + "<NotificationCode>" + 
                            NotificationVal + "</NotificationCode>" + 
                            "<EMailMessage> " + "<EMailAddress>" + 
                            SenderEmail + "</EMailAddress>" + 
                            OptionalMessage + emailReferenceVal + 
                            emailReferenceVal1 + "</EMailMessage>" + 
                            "</Notification>";
    
                } else {
                    RecipientEmailAddress2 = "";
                }
                    
                    
                }    
            // Email Notification code ended    
            
           String shipperNumber = aascShipMethodInfo.getCarrierAccountNumber(aascShipmentHeaderInfo.getCarrierId());
                if("RECIPIENT".equalsIgnoreCase(carrierPayMethod)){    
                    carrierPayMethodCode="FC";   
                }
                if("CONSIGNEE".equalsIgnoreCase(carrierPayMethod)){    
                    carrierPayMethodCode="CG";
                }
                satShipFlag = 
                        nullStrToSpc(aascShipmentHeaderInfo.getSaturdayShipFlag()); // retreiving saturday ship flag from header bean        
                senderAccountNumber = 
                        nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber()); // retreiving Carrier Account Number from header bean        

                companyNameFromPO = aascShipmentHeaderInfo.getShipFromCountry();; //aascProfileOptionsInfo.getCompanyName();
                try {
                    companyNameFromPO.substring(0, 1);

                } catch (Exception e) {
                    companyNameFromPO = "";
                }                       
                String intFlag = aascShipmentHeaderInfo.getInternationalFlag();
                LinkedList coList = null;
                try {
                    aascIntlHeaderInfo = aascIntlInfo.getIntlHeaderInfo();
                    coList = aascIntlInfo.getIntlCommodityInfo();
                } catch (Exception e) {
                    aascIntlHeaderInfo = new AascIntlHeaderInfo();
                    coList = new LinkedList();
                }
                try{
                  if (intFlag.equalsIgnoreCase("Y")) {
                    shipFromAttention = 
                            nullStrToSpc(aascIntlHeaderInfo.getShipFromAttention());
                    shipFromPhone = 
                            nullStrToSpc(aascIntlHeaderInfo.getShipFromPhone());
                    soldToAttention = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAttention());
                    soldToPhone = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToPhone());
                    invCurrencyCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getInvoiceCurrencyCode());
                    invValue = (aascIntlHeaderInfo.getInvoiceValue());
                    soldTaxId = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToTaxId());
                    shipToTaxId = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlShipToTaxId());
                    shipFromTaxId = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlShipFromTaxId());
                    ciPurchaseOrderNumber = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlPurchaseOrderNumber());
                    ciInvoiceNumber = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlCustomerInvoiceNumber());
                    ciTermsOfShipment = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlTermsOfSale());
                    ciReasonForExport = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlPurpose()); //need to chk
                    ciComments = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlComments());
                    ciDiscount = aascIntlHeaderInfo.getIntlDiscount();
                    ciFreightCharges = 
                            aascIntlHeaderInfo.getIntlFreightCharge();
                    ciInsuranceCharges = 
                            aascIntlHeaderInfo.getIntlInsuranceCharge();
                    ciOtherCharges = 
                            aascIntlHeaderInfo.getIntlTaxMiscellaneousCharge(); //need to chk
                    ciDeclarationStatement = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlDeclarationStmt());
                    ciInvoiceDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getIntlInvoiceDate()));
                    ciCurrencyCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntlCurrencyCode());
                    BeginDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getIntlBlanketPeriodBeginDate()));
                    EndDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getIntlBlanketPeriodEndDate()));
                    uscoExportDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getIntlExportDate()));
                    uscoExportingCarrier = 
                            aascIntlHeaderInfo.getIntlExportCarrier();
                    soldCompanyName = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getCompanyName());
                    soldAddressLine1 = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getAddressLine1());
                    soldAddressLine2 = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getAddressLine2());
                    soldCity = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getCity());
                    soldStProvinceCd = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getStateProvinceCode());
                    soldPostalCd = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getPostalCode());
                    soldCountryCd = 
                            nullStrToSpc(aascIntlHeaderInfo.getSoldToAddressInfo().getCountryCode());
                    //}
                    sedCompanyName = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getCompanyName());
                    sedAddressLine1 = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getAddressLine1());
                    sedCity = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getCity());
                    sedStateProvinceCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getStateProvinceCode());
                    sedPostalCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getPostalCode());
                    sedCountryCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedAddressInfo().getCountryCode());
                    sedExportDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getSedExportDate()));
                    sedExportingCarrier = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedExportingCarrier());
                    sedInBondCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedInBondCode());
                    sedContainerized = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedContainerized());
                    sedRoutedExportTransaction = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedRoutedExportTransaction());
                    sedPointOfOrigin = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedPointOfOrigin());
                    sedModeOfTransport = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedModeOfTransport());
                    sedPartiesToTransaction = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedPartiesToTran()); //need to chk
                    sedLicNumber = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedLicenceNumber());
                    sedLicDate = 
                            convertDate(nullStrToSpc(aascIntlHeaderInfo.getSedLicenceDate()));
                    sedCarrierID = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedCarrierIdentificationCode());
                    sedPortOfUnloading = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedPortOfUnloading());
                    sedLoadingPier = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedLoadingPier());
                    sedPortOfExport = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedPortOfExport());
                    sedEntryNumber = aascIntlHeaderInfo.getSedEntryNumber();

                    faCompanyName = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getCompanyName()));
                    faAddressLine1 = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getAddressLine1()));
                    faCity = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getCity()));
                    faStateProvinceCode = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getStateProvinceCode()));
                    faPostalCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getPostalCode());
                    faCountryCode = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getCountryCode()));
                    fTaxId = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getForwardAgentInfo().getTaxId()));
                    icCompanyName = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getCompanyName()));
                    icAddressLine1 = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getAddressLine1()));
                    icCity = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getCity()));
                    icStateProvinceCode = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getStateProvinceCode()));
                    icPostalCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getPostalCode());
                    icCountryCode = 
                            encode(nullStrToSpc(aascIntlHeaderInfo.getIntermediateConsigneeInfo().getCountryCode()));

                    sedExceptionCode = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedLicenceExceptionCode());
                    sedEccnNumber = 
                            nullStrToSpc(aascIntlHeaderInfo.getSedECCN());

                    if (sedExceptionCode.equalsIgnoreCase("CIV") || 
                        sedExceptionCode.equalsIgnoreCase("CTP") || 
                        sedExceptionCode.equalsIgnoreCase("ENC") || 
                        sedExceptionCode.equalsIgnoreCase("KMI") || 
                        sedExceptionCode.equalsIgnoreCase("LVS")) {
                        sedEccnNumberTag = 
                                "<ECCNNumber>" + sedEccnNumber + "</ECCNNumber>";
                    } else {
                        sedEccnNumberTag = "";
                    }
                    ListIterator CoInfoIterator = coList.listIterator();
                    ciFlag = nullStrToSpc(aascIntlHeaderInfo.getCommercialInv());
                    naftaFlag = nullStrToSpc(aascIntlHeaderInfo.getNaftaCertOrigin());
                    uscoFlag = nullStrToSpc(aascIntlHeaderInfo.getUsCertOrigin());
                    sedFlag = nullStrToSpc(aascIntlHeaderInfo.getShippersExportDecl());
                    AascIntlCommodityInfo aascIntlCommodityInfo = null ;
                    while (CoInfoIterator.hasNext()) {
                        aascIntlCommodityInfo = 
                            (AascIntlCommodityInfo)CoInfoIterator.next();

                        ciNumber = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getQuantity()));
                        ciUOM = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getQuantityUnits()));
                        ciDesc = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getDescription()));
                        ciPricePerUnit = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getUnitPrice()));
                        prdTarrifCode = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getHarmonizedCode()));
                        prdCountry = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getCountryOfManufacture()));

                        naftaNetCostCode = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getRvcCalculationMethod())); ///need to chk
                        naftaNetCostBeginDate = 
                                convertDate(encode(nullStrToSpc(aascIntlCommodityInfo.getNetCostPeriodBeginDate())));
                        naftaNetCostEndDate = 
                                convertDate(encode(nullStrToSpc(aascIntlCommodityInfo.getNetCostPeriodEndDate())));
                        naftaPreferenceCriteria = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getPreferenceCriteria()));
                        naftaProducerInfo = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getProducer()));
                        naftaCompanyName = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getCompanyName()));
                        naftaAddressLine1 = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getAddressLine1()));
                        naftaCity = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getCity()));
                        naftaStateProvinceCode = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getStateProvinceCode()));
                        naftaPostalCode = 
                                nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getPostalCode());
                        naftaCountryCode = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getCountryCode()));
                        naftaTaxId = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getAascAddressInfo().getTaxId()));

                        uscoNumPkgPerCommodity = 
                                aascIntlCommodityInfo.getNumberOfPieces();
                        productUOM = 
                                aascIntlCommodityInfo.getPackageWeightUom(); //need to chk
                        productWeight = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getWeight()));

                        sedSchBNumber = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getScheduleBNumber()));
                        sedSchQuantity = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getScheduleBQty()));
                        sedSchUnitOfMeasurement = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getScheduleBUOM()));
                        sedExportType = 
                                encode(nullStrToSpc(aascIntlCommodityInfo.getExportType()));
                        sedTotalVal = aascIntlCommodityInfo.getSedTotalValue();
                        if (ciFlag.equalsIgnoreCase("Y")) {
                                ProductTags1 = 
                                    "<Unit>" + "<Number>" + ciNumber + 
                                    "</Number>" + "<UnitOfMeasurement>" + 
                                    "<Code>" + ciUOM + "</Code>" + 
                                    "</UnitOfMeasurement>" + "<Value>" + 
                                    ciPricePerUnit + "</Value>" + "</Unit>";
                            ProductTagsCI = ProductTags1;
                            if (naftaFlag.equals("Y")) {
                                ProductTagsNAFTA = ProductTags2;
                                if (uscoFlag.equalsIgnoreCase("Y")) {
                                    ProductTagsUSCO = ProductTags3;
                                    if (sedFlag.equalsIgnoreCase("Y")) {
                                        ProductTagsSED = ProductTags4;
                                    }
                                } else if (sedFlag.equalsIgnoreCase("Y")) {
                                    ProductTagsSED = ProductTags4;
                                }
                            } else if (uscoFlag.equalsIgnoreCase("Y")) {
                                ProductTagsUSCO = ProductTags3;
                                if (sedFlag.equalsIgnoreCase("Y")) {
                                    ProductTagsSED = ProductTags4;
                                }
                            } else if (sedFlag.equalsIgnoreCase("Y")) {
                                ProductTagsSED = ProductTags4;
                            }
                                                     
                        } //end of if(ciFlag.equalsIgnoreCase("Y"))
                        if (naftaFlag.equalsIgnoreCase("Y")) {
                            logger.info("naftaNetCostCode==" + 
                                        naftaNetCostCode);
                            if (naftaNetCostCode.equalsIgnoreCase("NC")) {
                                naftaNetCostCodeTags = 
                                        "<NetCostCode>" + naftaNetCostCode + 
                                        "</NetCostCode>" + 
                                        "<NetCostDateRange>" + "<BeginDate>" + 
                                        naftaNetCostBeginDate + 
                                        "</BeginDate>" + "<EndDate>" + 
                                        naftaNetCostEndDate + "</EndDate>" + 
                                        "</NetCostDateRange>";
                            } else {
                                naftaNetCostCodeTags = 
                                        "<NetCostCode>" + naftaNetCostCode + 
                                        "</NetCostCode>";
                            }
                            ProductTags2 = 
                                    naftaNetCostCodeTags + "<PreferenceCriteria>" + 
                                    naftaPreferenceCriteria + 
                                    "</PreferenceCriteria>" + 
                                    "<ProducerInfo>" + naftaProducerInfo + 
                                    "</ProducerInfo>";

                                eachProducerTags = 
                                    "<Producer>" + "<Option>01</Option>" + 
                                    "<CompanyName>" + naftaCompanyName + 
                                    "</CompanyName>" + 
                                    "<TaxIdentificationNumber>" + naftaTaxId + 
                                    "</TaxIdentificationNumber>" + 
                                    "<Address>" + "<AddressLine1>" + 
                                    naftaAddressLine1 + "</AddressLine1>" + 
                                    "<City>" + naftaCity + "</City>" + 
                                    "<StateProvinceCode>" + 
                                    naftaStateProvinceCode + 
                                    "</StateProvinceCode>" + "<PostalCode>" + 
                                    naftaPostalCode + "</PostalCode>" + 
                                    "<CountryCode>" + naftaCountryCode + 
                                    "</CountryCode>" + "</Address>" + 
                                    "</Producer>";

                            producerTags = producerTags + eachProducerTags;

                            ProductTagsNAFTA = ProductTags2;
                            if (uscoFlag.equalsIgnoreCase("Y")) {
                                ProductTagsUSCO = ProductTags3;
                                if (sedFlag.equalsIgnoreCase("Y")) {
                                    ProductTagsSED = ProductTags4;
                                }
                            } else if (sedFlag.equalsIgnoreCase("Y")) {
                                ProductTagsSED = ProductTags4;
                            }
                        } //end of if(naftaFlag.equalsIgnoreCase("Y"))
                        if (uscoFlag.equalsIgnoreCase("Y")) {
                            ProductTags3 = 
                                    "<NumberOfPackagesPerCommodity>" + uscoNumPkgPerCommodity + 
                                    "</NumberOfPackagesPerCommodity>" + 
                                    "<ProductWeight>" + 
                                    "<UnitOfMeasurement><Code>" + productUOM + 
                                    "</Code></UnitOfMeasurement>" + 
                                    "<Weight>" + productWeight + "</Weight>" + 
                                    "</ProductWeight>";

                            ProductTagsUSCO = ProductTags3;
                            if (sedFlag.equalsIgnoreCase("Y")) {
                                ProductTagsSED = ProductTags4;
                            }
                        } //end of if(uscoFlag.equalsIgnoreCase("Y"))
                        if (sedFlag.equalsIgnoreCase("Y")) {
                            ProductTags4 = 
                                    "<ProductWeight>" + "<UnitOfMeasurement><Code>" + 
                                    productUOM + 
                                    "</Code></UnitOfMeasurement>" + 
                                    "<Weight>" + productWeight + "</Weight>" + 
                                    "</ProductWeight>" + 
                                    "<ScheduleB><Number>" + sedSchBNumber + 
                                    "</Number>" + "<Quantity>" + 
                                    sedSchQuantity + "</Quantity>" + 
                                    "<UnitOfMeasurement><Code>" + 
                                    sedSchUnitOfMeasurement + 
                                    "</Code></UnitOfMeasurement>" + 
                                    "</ScheduleB>" + "<ExportType>" + 
                                    sedExportType + "</ExportType>" + 
                                    "<SEDTotalValue>" + sedTotalVal + 
                                    "</SEDTotalValue>";
                            logger.info("*************sedFlag equal to y******************");
                            ProductTagsSED = ProductTags4;
                        } //end of if(sedFlag.equalsIgnoreCase("Y"))
                        if ((uscoFlag.equalsIgnoreCase("Y")) && 
                            (sedFlag.equalsIgnoreCase("Y"))) {
                            ProductTags = 
                                    ProductTagsCI + ProductTagsNAFTA + "<NumberOfPackagesPerCommodity>" + 
                                    uscoNumPkgPerCommodity + 
                                    "</NumberOfPackagesPerCommodity>" + 
                                    ProductTagsSED;
                        } else {
                            ProductTags = 
                                    ProductTagsCI + ProductTagsNAFTA + ProductTagsUSCO + 
                                    ProductTagsSED;
                        }
                         mainPTag = 
                                "<Product>" + "<CommodityCode>" + prdTarrifCode + 
                                "</CommodityCode>" + "<OriginCountryCode>" + 
                                prdCountry + "</OriginCountryCode>" + 
                                "<Description>" + ciDesc + "</Description>" + 
                                ProductTags + "</Product>";

                        mainProductTags = mainProductTags + mainPTag;
                    } //end of while
                    ciFlag = nullStrToSpc(aascIntlHeaderInfo.getCommercialInv());    
                    naftaFlag = nullStrToSpc(aascIntlHeaderInfo.getNaftaCertOrigin());
                    uscoFlag = nullStrToSpc(aascIntlHeaderInfo.getUsCertOrigin());
                    sedFlag = nullStrToSpc(aascIntlHeaderInfo.getShippersExportDecl());
                    String FormTypeTag1 = "";
                    String otherTags1 = "";
                    String FormTypeTag2 = "";
                    String otherTags2 = "";
                    String FormTypeTag3 = "";
                    String otherTags3 = "";
                    String FormTypeTag4 = "";
                    String otherTags4 = "";
                    String FormTypeTagCI = "";
                    String otherTagsCI = "";
                    String FormTypeTagNAFTA = "";
                    String otherTagsNAFTA = "";
                    String FormTypeTagUSCO = "";
                    String otherTagsUSCO = "";
                    String FormTypeTagSED = "";
                    String otherTagsSED = "";
                    String otherTags = "";
                    String producerTags = "";
                    String UltimateConsigneeTags = "";
                    String otherTagsnafta = "";
                    String otherTagssed = "";
                    if (ciFlag.equalsIgnoreCase("Y")) {
                        FormTypeTag1 = "<FormType>01</FormType>";
                        
                        if (ciInvoiceNumber != "") {
                            ciInvoiceNumberTag = 
                                    "<InvoiceNumber>" + ciInvoiceNumber + 
                                    "</InvoiceNumber>";
    
                        } else {
                            ciInvoiceNumberTag = "";
                        }
                        if (ciPurchaseOrderNumber != "") {
                            ciPurchaseOrderNumberTag = 
                                    "<PurchaseOrderNumber>" + ciPurchaseOrderNumber + 
                                    "</PurchaseOrderNumber>";
                        } else {
                            ciPurchaseOrderNumberTag = "";
                        }
                        if (ciTermsOfShipment != "") {
                            ciTermsOfShipmentTag = 
                                    "<TermsOfShipment>" + ciTermsOfShipment + 
                                    "</TermsOfShipment>";    
                        } else {
                            ciTermsOfShipmentTag = "";
                        }
                        if (ciComments != "") {
                            ciCommentsTag = 
                                    "<Comments>" + ciComments + "</Comments>";
                        } else {
                            ciCommentsTag = "";
                        }
                        if (ciDeclarationStatement != "") {
                            ciDeclarationStatementTag = 
                                    "<DeclarationStatement>" + ciDeclarationStatement + 
                                    "</DeclarationStatement>";
                        } else {
                            ciDeclarationStatementTag = "";
                        }
                        if (ciDiscount != 0.0) {
                            ciDiscountTag = 
                                    "<Discount>" + "<MonetaryValue>" + ciDiscount + 
                                    "</MonetaryValue>" + "</Discount>";
                        } else {
                            ciDiscountTag = "";
                        }
                        if (ciFreightCharges != 0.0) {
                            ciFreightChargesTag = 
                                    "<FreightCharges>" + "<MonetaryValue>" + 
                                    ciFreightCharges + "</MonetaryValue>" + 
                                    "</FreightCharges>";
                        } else {
                            ciFreightChargesTag = "";
                        }
                        if (ciInsuranceCharges != 0.0) {
                            ciInsuranceChargesTag = 
                                    "<InsuranceCharges>" + "<MonetaryValue>" + 
                                    ciInsuranceCharges + "</MonetaryValue>" + 
                                    "</InsuranceCharges>";
                        } else {
                            ciInsuranceChargesTag = "";
                        }
                        if (ciOtherCharges != 0.0) {
                            ciOtherChargesTag = 
                                    "<OtherCharges>" + "<MonetaryValue>" + 
                                    ciOtherCharges + "</MonetaryValue>" + 
                                    "<Description>Others</Description>" + 
                                    "</OtherCharges>";
                        } else {
                            ciOtherChargesTag = "";
                        }
                        otherTags1 = 
                                ciInvoiceNumberTag + ciPurchaseOrderNumberTag + 
                                ciTermsOfShipmentTag + "<ReasonForExport>" + 
                                ciReasonForExport + "</ReasonForExport>" + 
                                ciCommentsTag + ciDeclarationStatementTag + 
                                ciDiscountTag + ciFreightChargesTag + 
                                ciInsuranceChargesTag + ciOtherChargesTag + 
                                "<InvoiceDate>" + ciInvoiceDate + 
                                "</InvoiceDate>" + "<CurrencyCode>" + 
                                ciCurrencyCode + "</CurrencyCode>";

                    }
                if (naftaFlag.equalsIgnoreCase("Y")) {
                    FormTypeTag2 = "<FormType>04</FormType>";
                    if (naftaNetCostCode.equalsIgnoreCase("NC")) {
                        naftaNetCostCodeTags = 
                                "<NetCostCode>" + naftaNetCostCode + 
                                "</NetCostCode>" + "<NetCostDateRange>" + 
                                "<BeginDate>" + naftaNetCostBeginDate + 
                                "</BeginDate>" + "<EndDate>" + 
                                naftaNetCostEndDate + "</EndDate>" + 
                                "</NetCostDateRange>";
                    } else {
                        naftaNetCostCodeTags = 
                                "<NetCostCode>" + naftaNetCostCode + 
                                "</NetCostCode>";
                    }
                    otherTagsnafta = 
                            "<BlanketPeriod>" + "<BeginDate>" + BeginDate + 
                            "</BeginDate>" + "<EndDate>" + EndDate + 
                            "</EndDate>" + "</BlanketPeriod>";

                    otherTags2 = 
                            "<Contacts>" + producerTags + "</Contacts>" + otherTagsnafta;
                }
                if (uscoFlag.equalsIgnoreCase("Y")) {
                    FormTypeTag3 = "<FormType>03</FormType>";
                    
                    otherTags3 = 
                            "<ExportDate>" + uscoExportDate + "</ExportDate>" + 
                            "<ExportingCarrier>" + uscoExportingCarrier + 
                            "</ExportingCarrier>";
                }
                if (sedFlag.equalsIgnoreCase("Y")) {
                    if (sedContainerized.equalsIgnoreCase("Y")) {
                        sedContainerizationTag = "<ContainerizedIndicator/>";

                    } else {
                        sedContainerizationTag = "";
                    }
                    
                    if (sedRoutedExportTransaction.equalsIgnoreCase("Y")) {
                        sedRoutedExportTransactionTag = 
                                "<RoutedExportTransactionIndicator/>";

                    } else {
                        sedRoutedExportTransactionTag = "";
                    }
                    if (sedLicNumber != "" && sedLicDate != "") {
                            sedLicTag = 
                                "<License>" + "<Number>" + sedLicNumber + 
                                "</Number>" + "<Date>" + sedLicDate + 
                                "</Date>" + "</License>";

                    } else if (sedExceptionCode != "" && 
                               sedExceptionCode != "") {
                        sedLicTag = 
                                "<License>" + "<ExceptionCode>" + sedExceptionCode + 
                                "</ExceptionCode>" + "</License>";
                    } else {
                        sedLicTag = "";
                    }

                    if (sedCarrierID != "") {
                        sedCarrierIDTag = 
                                "<CarrierID>" + sedCarrierID + "</CarrierID>";

                    } else {
                        sedCarrierIDTag = "";
                    }

                    if (sedPortOfUnloading != "") {
                        sedPortOfUnloadingTag = 
                                "<PortOfUnloading>" + sedPortOfUnloading + 
                                "</PortOfUnloading>";

                    } else {
                        sedPortOfUnloadingTag = "";
                    }
                    if (sedLoadingPier != "") {
                        sedLoadingPierTag = 
                                "<LoadingPier>" + sedLoadingPier + "</LoadingPier>";

                    } else {
                        sedLoadingPierTag = "";
                    }

                    if (sedPortOfExport != "") {
                        sedPortOfExportTag = 
                                "<PortOfExport>" + sedPortOfExport + 
                                "</PortOfExport>";

                    } else {
                        sedPortOfExportTag = "";
                    }

                    if (sedEntryNumber != "") {
                        sedEntryNumberTag = 
                                "<EntryNumber>" + sedEntryNumber + "</EntryNumber>";

                    } else {
                        sedEntryNumberTag = "";
                    }
                    if (faCompanyName != "") {
                        faTag = 
                                "<ForwardAgent>" + "<CompanyName>" + faCompanyName + 
                                "</CompanyName>" + 
                                "<TaxIdentificationNumber>" + fTaxId + 
                                "</TaxIdentificationNumber>" + "<Address>" + 
                                "<AddressLine1>" + faAddressLine1 + 
                                "</AddressLine1>" + "<City>" + faCity + 
                                "</City>" + "<StateProvinceCode>" + 
                                faStateProvinceCode + "</StateProvinceCode>" + 
                                "<PostalCode>" + faPostalCode + 
                                "</PostalCode>" + "<CountryCode>" + 
                                faCountryCode + "</CountryCode>" + 
                                "</Address>" + "</ForwardAgent>";
                    } else {
                        faTag = "";
                    }
                    if (icCompanyName != "") {
                        icTag = 
                                "<IntermediateConsignee>" + "<CompanyName>" + icCompanyName + 
                                "</CompanyName>" + "<Address>" + 
                                "<AddressLine1>" + icAddressLine1 + 
                                "</AddressLine1>" + "<City>" + icCity + 
                                "</City>" + "<StateProvinceCode>" + 
                                icStateProvinceCode + "</StateProvinceCode>" + 
                                "<PostalCode>" + icPostalCode + 
                                "</PostalCode>" + "<CountryCode>" + 
                                icCountryCode + "</CountryCode>" + 
                                "</Address>" + "</IntermediateConsignee>";
                    } else {
                        icTag = "";
                    }
                    FormTypeTag4 = "<FormType>02</FormType>";                    
                    otherTags4 = 
                        "<Contacts>" + faTag + "<UltimateConsignee>" + 
                        "<CompanyName>" + sedCompanyName + 
                        "</CompanyName>" + "<Address>" + "<AddressLine1>" + 
                        sedAddressLine1 + "</AddressLine1>" + "<City>" + 
                        sedCity + "</City>" + "<StateProvinceCode>" + 
                        sedStateProvinceCode + "</StateProvinceCode>" + 
                        "<PostalCode>" + sedPostalCode + "</PostalCode>" + 
                        "<CountryCode>" + sedCountryCode + 
                        "</CountryCode>" + "</Address>" + 
                        "</UltimateConsignee>" + icTag + "</Contacts>" + 
                        "<SEDFilingOption>01</SEDFilingOption>" + 
                        "<ExportDate>" + sedExportDate + "</ExportDate>" + 
                        "<ExportingCarrier>" + sedExportingCarrier + 
                        "</ExportingCarrier>" + "<InBondCode>" + 
                        sedInBondCode + "</InBondCode>" + 
                        "<PointOfOrigin>" + sedPointOfOrigin + 
                        "</PointOfOrigin>" + "<ModeOfTransport>" + 
                        sedModeOfTransport + "</ModeOfTransport>" + 
                        "<PartiesToTransaction>" + 
                        sedPartiesToTransaction + 
                        "</PartiesToTransaction>" + 
                        sedContainerizationTag + 
                        sedRoutedExportTransactionTag + sedLicTag + 
                        sedCarrierIDTag + sedPortOfUnloadingTag + 
                        sedLoadingPierTag + sedPortOfExportTag + 
                        sedEntryNumberTag;
    
                    UltimateConsigneeTags = 
                        "<UltimateConsignee>" + "<CompanyName>" + 
                        sedCompanyName + "</CompanyName>" + "<Address>" + 
                        "<AddressLine1>" + sedAddressLine1 + 
                        "</AddressLine1>" + "<City>" + sedCity + 
                        "</City>" + "<StateProvinceCode>" + 
                        sedStateProvinceCode + "</StateProvinceCode>" + 
                        "<PostalCode>" + sedPostalCode + "</PostalCode>" + 
                        "<CountryCode>" + sedCountryCode + 
                        "</CountryCode>" + "</Address>" + 
                        "</UltimateConsignee>";
                    otherTagssed = 
                            "<SEDFilingOption>01</SEDFilingOption>" + "<ExportDate>" + 
                            sedExportDate + "</ExportDate>" + 
                            "<ExportingCarrier>" + sedExportingCarrier + 
                            "</ExportingCarrier>" + "<InBondCode>" + 
                            sedInBondCode + "</InBondCode>" + 
                            "<PointOfOrigin>" + sedPointOfOrigin + 
                            "</PointOfOrigin>" + "<ModeOfTransport>" + 
                            sedModeOfTransport + "</ModeOfTransport>" + 
                            "<PartiesToTransaction>" + 
                            sedPartiesToTransaction + 
                            "</PartiesToTransaction>" + 
                            sedContainerizationTag + 
                            sedRoutedExportTransactionTag + sedLicTag + 
                            sedCarrierIDTag + sedPortOfUnloadingTag + 
                            sedLoadingPierTag + sedPortOfExportTag + 
                            sedEntryNumberTag;
                }
                if (ciFlag.equalsIgnoreCase("Y")) {
                    FormTypeTagCI = FormTypeTag1;
                    otherTagsCI = otherTags1;
                }
                if (naftaFlag.equalsIgnoreCase("Y")) {
                    FormTypeTagNAFTA = FormTypeTag2;
                    otherTagsNAFTA = otherTags2;
                }
                if (uscoFlag.equalsIgnoreCase("Y")) {
                    FormTypeTagUSCO = FormTypeTag3;
                    otherTagsUSCO = otherTags3;
                }
                if (sedFlag.equalsIgnoreCase("Y")) {
                    FormTypeTagSED = FormTypeTag4;
                    otherTagsSED = otherTags4;
                }
                if ((uscoFlag.equalsIgnoreCase("Y")) && 
                    (sedFlag.equalsIgnoreCase("Y"))) {
                    otherTags = otherTagsCI + otherTagsNAFTA + otherTagsSED;
                } else {
                    otherTags = 
                            otherTagsCI + otherTagsNAFTA + otherTagsUSCO + otherTagsSED;
                }
                if ((naftaFlag.equalsIgnoreCase("Y")) && 
                    (sedFlag.equalsIgnoreCase("Y"))) {
                    if (uscoFlag.equalsIgnoreCase("Y")) {
                        otherTags = 
                                otherTagsCI + "<Contacts>" + producerTags + faTag + 
                                UltimateConsigneeTags + icTag + "</Contacts>" + 
                                otherTagsnafta + otherTagssed;
                    } else {
                        otherTags = 
                                otherTagsCI + otherTagsUSCO + "<Contacts>" + 
                                producerTags + faTag + UltimateConsigneeTags + 
                                icTag + "</Contacts>" + otherTagsnafta + 
                                otherTagssed;
                    }
                }
                if (intFlag.equalsIgnoreCase("Y")) {
                    if(ciFlag.equalsIgnoreCase("Y") || naftaFlag.equalsIgnoreCase("Y") || uscoFlag.equalsIgnoreCase("Y") || sedFlag.equalsIgnoreCase("Y")){
                        intlTag = 
                            "<InternationalForms>" + FormTypeTagCI + FormTypeTagNAFTA + 
                            FormTypeTagUSCO + FormTypeTagSED + 
                            mainProductTags + otherTags + sedEccnNumberTag + 
                            "</InternationalForms>";
                    }
                    else{
                        intlTag="";   
                    }
                    if ((invCurrencyCode != "") && (invValue != 0)) {
                        invTag = 
                                "<InvoiceLineTotal>" + "<CurrencyCode>" + invCurrencyCode + 
                                "</CurrencyCode>" + "<MonetaryValue>" + 
                                invValue + "</MonetaryValue>" + 
                                "</InvoiceLineTotal>";
                    } else {
                        invTag = "";
                    }
                    solToTag = 
                        "<SoldTo>" + "<Option/>" + "<CompanyName>" + soldCompanyName + 
                        "</CompanyName>" + "<AttentionName>" + 
                        soldToAttention + "</AttentionName>" + 
                        "<TaxIdentificationNumber>" + soldTaxId + 
                        "</TaxIdentificationNumber>" + "<PhoneNumber>" + 
                        soldToPhone + "</PhoneNumber>" + "<Address>" + 
                        "<AddressLine1>" + soldAddressLine1 + 
                        "</AddressLine1>" + "<City>" + soldCity + 
                        "</City>" + "<StateProvinceCode>" + 
                        soldStProvinceCd + "</StateProvinceCode>" + 
                        "<PostalCode>" + soldPostalCd + "</PostalCode>" + 
                        "<CountryCode>" + soldCountryCd + 
                        "</CountryCode>" + "</Address>" + "</SoldTo>";
                }
            }
          }catch(Exception e){
                    logger.info("Data is not entered in the International Shipments window");
          }

          shipFromCompanyName = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipFromCompanyName())); 
          shipFromAddressLine1 = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine1());
            shipFromAddressLine1=encode(shipFromAddressLine1);       
                  
          shipFromAddressLine2 = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine2());
            shipFromAddressLine2=encode(shipFromAddressLine2);
            shipFromAddressLine3 = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine3()));
            
            shipFromEmailId = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipFromEmailId())); 
          shipFromAddressCity = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromCity());
          shipFromAddressState = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromState()).toUpperCase();
          shipFromAddressPostalCode = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromPostalCode());
          shipFromCountry = 
                  nullStrToSpc(aascShipmentHeaderInfo.getShipFromCountry()).toUpperCase();
          shipFromPhoneNumber = 
                  aascShipmentHeaderInfo.getShipFromPhoneNumber1();
          shipFromPersonName = encode(nullStrToSpc(aascShipmentHeaderInfo.getShipFromContactName()));                   
          upsServiceLevelCode = 
                    aascShipmentHeaderInfo.getupsServiceLevelCode();
          nonDiscountCostFlag = 
                    nullStrToSpc(aascProfileOptionsInfo.getNonDiscountedCost());
          acctNegotiatedCertified = 
                    nullStrToSpc(aascProfileOptionsInfo.getAcctNumNegotiatedFlag());
          if (upsServiceLevelCode.length() == 1) {    
                upsServiceLevelCode = ("0").concat(upsServiceLevelCode);
          }
          if (satShipFlag.equalsIgnoreCase("N")) {
               satShipFlag = "FALSE";
          } else {
               satShipFlag = "TRUE";
          }
          labelFormat = 
                    aascShipMethodInfo.getPrinterPort(carrierId); //retreiving label format from shipMethod bean          
          docTabLocation = 
                    aascShipMethodInfo.getDocTabLocation(carrierId);

          shipmentRequestHdr = "";
          if (acctNegotiatedCertified.equalsIgnoreCase("Y")) {
             rateInfoTag = "<RateInformation><NegotiatedRatesIndicator>1</NegotiatedRatesIndicator></RateInformation>";
          } else {
              rateInfoTag = "";
          }
          acctPostalCode = nullStrToSpc(aascShipmentHeaderInfo.getRecPostalCode());
          if("".equalsIgnoreCase(acctPostalCode)){
              acctPostalCode = nullStrToSpc(aascShipmentHeaderInfo.getAcctPostalCode());
          }
          if (carrierPayMethodCode.equalsIgnoreCase("PP")) { //<!--Must be the same UPS account number as the one provided in Shipper/ShipperNumber. -->
             paymentInfoTag = 
                    "<Prepaid>" + "<BillShipper>" + "<AccountNumber>" + 
                    senderAccountNumber + "</AccountNumber>" + 
                    "</BillShipper>" + "</Prepaid>";
          } else if (carrierPayMethodCode.equalsIgnoreCase("FC")) {
                paymentInfoTag = 
                        "<FreightCollect>" + "<BillReceiver>" + "<AccountNumber>" + 
                        customerAccountNumber + "</AccountNumber>" + 
                        "<Address><PostalCode>" + acctPostalCode + 
                        "</PostalCode></Address>" + "</BillReceiver>" + 
                        "</FreightCollect>";
          } else if (carrierPayMethodCode.equalsIgnoreCase("CG")) {
                paymentInfoTag = "<ConsigneeBilled/>";
          }
          else if (carrierPayMethodCode.equalsIgnoreCase("TP")) {
                tpCompanyName = 
                        nullStrToSpc(aascShipmentHeaderInfo.getTpCompanyName());
                tpAddress = 
                        nullStrToSpc(aascShipmentHeaderInfo.getTpAddress());
                tpCity = nullStrToSpc(aascShipmentHeaderInfo.getTpCity());
                tpState = nullStrToSpc(aascShipmentHeaderInfo.getTpState());
                tpPostalCode = 
                        nullStrToSpc(aascShipmentHeaderInfo.getTpPostalCode());
                tpCountrySymbol = 
                        nullStrToSpc(aascShipmentHeaderInfo.getTpCountrySymbol());

                paymentInfoTag = 
                        "<BillThirdParty>" + "<BillThirdPartyShipper>" + 
                        "<AccountNumber>" + customerAccountNumber + 
                        "</AccountNumber>" + "<ThirdParty>" + "<Address>" + 
                        "<PostalCode>" + acctPostalCode + "</PostalCode>" + 
                        "<CountryCode>" + tpCountrySymbol + 
                        "</CountryCode>" + "</Address>" + "</ThirdParty>" + 
                        "</BillThirdPartyShipper>" + "</BillThirdParty>";
          }
          if (labelFormat.equalsIgnoreCase("EPL") || 
                labelFormat.equalsIgnoreCase("SPL") || 
                labelFormat.equalsIgnoreCase("ZPL")) {
                labelFormatTag = 
                        "<LabelPrintMethod>" + "<Code>" + labelFormat + 
                        "</Code>" + "</LabelPrintMethod>" + 
                        "<LabelStockSize>" + "<Height>4</Height>" + 
                        "<Width>6</Width>" + "</LabelStockSize>";
          } else if (labelFormat.equalsIgnoreCase("GIF")) {
                labelFormatTag = 
                        "<LabelPrintMethod>" + "<Code>GIF</Code>" + 
                        "</LabelPrintMethod>" + "<LabelImageFormat>" + 
                        "<Code>GIF</Code>" + "</LabelImageFormat>";
          }
          contactName = aascShipmentHeaderInfo.getContactName();
          // retreiving package info from package bean which is needed to be sent as part of request
          listIterator = linkedList.listIterator();
          while (listIterator.hasNext()) {
            aascShipmentPackageBean = 
                (AascShipmentPackageInfo)listIterator.next();
            if (aascShipmentPackageBean == null) {
            } else {
                pkgWtVal = aascShipmentPackageBean.getWeight();
                pkgWtUom = nullStrToSpc(aascShipmentPackageBean.getUom());
                cod = nullStrToSpc(aascShipmentPackageBean.getCodFlag());
                codAmount = aascShipmentPackageBean.getCodAmt();
                packaging = 
                        nullStrToSpc(aascShipmentPackageBean.getPackaging());
                dcisType = 
                       nullStrToSpc(aascShipmentPackageBean.getSignatureOptions());
                codType = 
                        nullStrToSpc(aascShipmentPackageBean.getCodCode());
                codFundsCode = 
                        nullStrToSpc(aascShipmentPackageBean.getCodFundsCode());
                codCurrCode = 
                        nullStrToSpc(aascShipmentPackageBean.getCodCurrCode());
                declaredVal = 
                        aascShipmentPackageBean.getPackageDeclaredValue();
                declaredCurrCode = 
                        aascShipmentPackageBean.getDeclaredCurrCode();

                if (declaredVal != 0) {
                    declaredTag = 
                            "<InsuredValue>" + "<CurrencyCode>" + declaredCurrCode + 
                            "</CurrencyCode>" + "<MonetaryValue>" + 
                            declaredVal + "</MonetaryValue>" + 
                            "</InsuredValue>";
                } else {

                    declaredTag = "";
                }
                if (pkgWtUom.equalsIgnoreCase("LB")) {
                    pkgWtUom = "LBS";
                }
                if (pkgWtUom.equalsIgnoreCase("KG")) {
                    pkgWtUom = "KGS";
                }
                packageLength = aascShipmentPackageBean.getPackageLength();
                packageWidth = aascShipmentPackageBean.getPackageWidth();
                packageHeight = aascShipmentPackageBean.getPackageHeight();
                packageUnits = nullStrToSpc(aascShipmentPackageBean.getDimensionUnits());
                // concatenate string for each package to shipmentRequest
                String voidFlag = nullStrToSpc(aascShipmentPackageBean.getVoidFlag());
                String headerVoidFlag = nullStrToSpc(aascShipmentHeaderInfo.getVoidFlag());

                if(!"Y".equalsIgnoreCase(voidFlag) && !"Y".equalsIgnoreCase(headerVoidFlag)){
                    packageStrTag = 
                        packageStrTag + getPkgStr(pkgWtVal, 
                                                    packageLength, 
                                                    packageWidth, 
                                                    packageHeight, 
                                                    packageUnits,
                                                       pkgWtUom, cod, 
                                                       codAmount, 
                                                       dcisType); //,countryCode
                }
            }
          } // end of while          
          String personNameTag = "";
          shipFromPersonName=aascShipmentHeaderInfo.getShipFromContactName();
          if (!shipFromPersonName.equalsIgnoreCase("")) {
             personNameTag =  "<AttentionName>" + shipFromPersonName + "</AttentionName>";
          }
          if (satShipFlag.equalsIgnoreCase("TRUE")) {
               satShipFlagTag = 
                       "<ShipmentServiceOptions><SaturdayDelivery/></ShipmentServiceOptions>"; // empty tag indicating saturday shipment
          } else {
               satShipFlagTag = "";
          }
            String intFlag2 = aascShipmentHeaderInfo.getInternationalFlag();
            
            String shipmentReferenceTag = "";
                          if ((!("US").equalsIgnoreCase(shipFromCountry) && !("PR").equalsIgnoreCase(shipFromCountry)) || ("Y".equalsIgnoreCase(intFlag2) && (("US").equalsIgnoreCase(shipFromCountry) || ("PR").equalsIgnoreCase(shipFromCountry)))) {
                               String shipmentRefTag1 = "";
                               String shipmentRefTag2 = "";
                              // System.out.println("Inside Intl:::::::::::::");
                              if (!"".equals(reference1)) {
                                   shipmentRefTag1 = 
                                           "<ReferenceNumber>" + "<Code>" + "TN" + 
                                           "</Code>" + "<Value>" + reference1 + 
                                           "</Value>" + "</ReferenceNumber>";
                                System.out.println("shipmentRefTag1::::::"+shipmentRefTag1);
                               }

                               if (!"".equals(reference2)) {
                                   shipmentRefTag2 = 
                                           "<ReferenceNumber>" + "<Code>" + "TN" + 
                                           "</Code>" + "<Value>" + reference2 + 
                                           "</Value>" + "</ReferenceNumber>";
                              //  System.out.println("shipmentRefTag2:::::::"+shipmentRefTag2);
                               }
                               shipmentReferenceTag = shipmentRefTag1 + shipmentRefTag2;
                             //  System.out.println("shipmentReferenceTag:::::::"+shipmentReferenceTag);
                           }
          shipmentRequestHdr = 
                "<?xml version=\"1.0\"?><ShipmentConfirmRequest xml:lang=\"en-US\">" + 
                "<Request>" + "<TransactionReference>" + 
                "<CustomerContext>ShipConfirm123</CustomerContext>" + 
                "<XpciVersion>1.0001</XpciVersion>" + 
                "</TransactionReference>" + 
                "<RequestAction>ShipConfirm</RequestAction>" + 
                "<RequestOption>nonvalidate</RequestOption>" + 
                "</Request>" + "<LabelSpecification>" + 
                labelFormatTag + "</LabelSpecification>" + 
                "<Shipment>";
                
                if("Y".equalsIgnoreCase(intFlag2))
                shipmentRequestHdr = shipmentRequestHdr + "<Description>International Shipment</Description>";
                
                shipmentRequestHdr = shipmentRequestHdr +
                "<Shipper>" + "<EMailAddress>" +shipFromEmailId+"</EMailAddress>"+ "<Name>" + shipFromCompanyName + 
                "</Name>" + personNameTag + "<PhoneNumber>" + 
                shipFromPhoneNumber + "</PhoneNumber>" + 
                "<ShipperNumber>" + shipperNumber + 
                "</ShipperNumber>" + "<Address>" + "<AddressLine1>" + 
                shipFromAddressLine1 + "</AddressLine1>" + "<AddressLine2>" + shipFromAddressLine2 + 
                "</AddressLine2>" + "<AddressLine3>" + shipFromAddressLine3 + 
                "</AddressLine3>"  + "<City>" + 
                shipFromAddressCity + "</City>" + 
                "<StateProvinceCode>" + shipFromAddressState + 
                "</StateProvinceCode>" + "<PostalCode>" + 
                shipFromAddressPostalCode + "</PostalCode>" + 
                "<CountryCode>"+shipFromCountry+"</CountryCode>" + "</Address>" + 
                "</Shipper>" + "<ShipTo>" + "<EMailAddress>" + shipToEmailId + 
                "</EMailAddress>" + "<CompanyName>" + 
                shipToCompanyName + "</CompanyName>" + 
                "<AttentionName>" + contactName + "</AttentionName>" + 
                "<TaxIdentificationNumber>456789</TaxIdentificationNumber>" + 
                "<PhoneNumber>" + phoneNumber + "</PhoneNumber>" + 
                "<Address>" + "<AddressLine1>" + shipToAddressLine1 + 
                "</AddressLine1>"+"<AddressLine2>" + shipToAddressLine2 + 
                "</AddressLine2>" + "<AddressLine3>" + shipToAddressLine3 + 
                "</AddressLine3>" +"<City>" + shipToAddressCity + 
                "</City>" + "<StateProvinceCode>" + 
                shipToAddressState + "</StateProvinceCode>" + 
                "<PostalCode>" + shipToAddressPostalCode + 
                "</PostalCode>" + "<CountryCode>" + shipToCountry + 
                "</CountryCode>" + residentialStr +"</Address>" + "</ShipTo>" + 
                "<ShipFrom>" + "<CompanyName>" + shipFromCompanyName + 
                "</CompanyName>" + personNameTag + "<PhoneNumber>" + 
                shipFromPhoneNumber + "</PhoneNumber>" + "<Address>" + 
                "<AddressLine1>" + shipFromAddressLine1 + 
                "</AddressLine1>" + "<AddressLine2>" + shipFromAddressLine2 + 
                "</AddressLine2>" + "<AddressLine3>" + shipFromAddressLine3 + 
                "</AddressLine3>" + "<City>" + shipFromAddressCity + 
                "</City>" + "<StateProvinceCode>" + 
                shipFromAddressState + "</StateProvinceCode>" + 
                "<PostalCode>" + shipFromAddressPostalCode + 
                "</PostalCode>" + "<CountryCode>" + shipFromCountry + 
                "</CountryCode>" + "</Address>" + "</ShipFrom>" + 
                solToTag + invTag + "<PaymentInformation>" + 
                paymentInfoTag + "</PaymentInformation>" + 
                rateInfoTag + "<Service>" + "<Code>" + 
                upsServiceLevelCode + "</Code>" + "</Service>" + satShipFlagTag+
               shipmentReferenceTag + packageStrTag + declaredTag + 
                "<ShipmentServiceOptions>" + intlTag + 
                RecipientEmailAddress1 + RecipientEmailAddress2 + 
                "</ShipmentServiceOptions>" + "</Shipment>" + 
                "</ShipmentConfirmRequest>";
logger.info("Shipping Request :::::::::"+shipmentRequestHdr);

          shipmentRequest.append(shipmentRequestHdr);
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
          accessLicenseNumber = 
                    nullStrToSpc(aascShipMethodInfo.getAccessLicenseNumberFromCarrierId(carrierId));
          
          aascConnectShipConnection = 
                  new AascConnectShipConnection(protocol, hostName, 
                                                  prefix + "/ShipConfirm", 
                                                  userName, password);
          connection = aascConnectShipConnection.createConnection();
          String warningChk = aascShipmentHeaderInfo.getWarningChk();
            
          if (connection == null) {
                aascShipmentHeaderInfo.setMainError("ERROR IN CONNECTION ESTABLISHMENT!");
                responseStatus = 151;
                return responseStatus;
          } else {
              aascXmlTransmitter = 
                        new AascXmlTransmitter(userName, password, 
                                               accessLicenseNumber);
              if(!"continue".equalsIgnoreCase(warningChk)){                    
                try {
                    writeOutputFile(shipmentRequest.toString(), 
                                    outputFilePath + orderNumber + 
                                    "_shipconfirm_" + timeStampStr + 
                                    "_request.xml");
                    
                } catch (FileNotFoundException fileNotFoundException) {
                    logger.severe("file to which the request and response to be written is not found:" + 
                                  fileNotFoundException.getMessage());    
                }
                shipmentResponse = 
                        aascXmlTransmitter.processRequest(shipmentRequest, 
                                                          connection, 
                                                          "ups"); // sending the request for processing and receiving the response                                     
                connection.disconnect();
                logger.info("shipmentResponse : "+shipmentResponse);
                if (shipmentResponse != null && 
                    !shipmentResponse.equals("")) {
                    try {
                        writeOutputFile(shipmentResponse, 
                                        outputFilePath + orderNumber + 
                                        "_shipconfirm_" + 
                                        timeStampStr + "_response.xml");
                    } catch (FileNotFoundException fileNotFoundException) {
                        logger.severe("file to which the request and response to be written is not found:" + 
                                      fileNotFoundException.getMessage() );
                    }
                    aascUpsShipmentInfo = new AascUpsShipmentInfo();
                    parsedDataHashMap = 
                            aascUpsShipmentInfo.parseResponse(aascShipmentHeaderInfo, 
                                                              aascShipmentOrderInfo, 
                                                              labelFormat, 
                                                              shipmentResponse, 
                                                              "ShipConfirm", 
                                                              aascProfileOptionsInfo, 
                                                              docTabLocation,cloudLabelPath);

                    parsedStatus = 
                            (String)parsedDataHashMap.get("ResponseStatusDescription");
                            
                    try{
                    shipmentDigest=(String)parsedDataHashMap.get("ShipmentDigest");
                        aascShipmentHeaderInfo.setShipmentDigest(shipmentDigest);
                    }
                    catch(Exception e){
                        logger.info("shipmentDigest: " + e.getMessage());
                    }
                    if (!parsedStatus.equals("success")) {
                        aascShipmentHeaderInfo.setMainError((String)parsedDataHashMap.get("ErrorDetails"));
                        responseStatus = 151;    
                        return responseStatus;
                    }
                } else {
                    aascShipmentHeaderInfo.setMainError("shipment response is null: " + 
                                                         (String)parsedDataHashMap.get("ErrorDetails"));
                    responseStatus = 151;                    
                    return responseStatus;
                }
              }
              logger.info("parsedStatus:" + parsedStatus);
          }
          if ("success".equals(parsedStatus) || "continue".equalsIgnoreCase(warningChk)) {
            shipmentDigest = aascShipmentHeaderInfo.getShipmentDigest();                           

            if (shipmentDigest != null && !shipmentDigest.equals("")) {
                shipAcceptRequest = new StringBuffer();
                shipAcceptRequestStr = 
                        "<?xml version=\"1.0\"?><ShipmentAcceptRequest>" + 
                        "<Request>" + "<TransactionReference>" + 
                        "<CustomerContext>ShipConfirm123</CustomerContext>" + 
                        "<XpciVersion>1.0001</XpciVersion>" + 
                        "</TransactionReference>" + 
                        "<RequestAction>ShipAccept</RequestAction>" + 
                        "<RequestOption>01</RequestOption>" + 
                        "</Request>" + "<ShipmentDigest>" + 
                        shipmentDigest + "</ShipmentDigest>" + 
                        "</ShipmentAcceptRequest>";
                shipAcceptRequest.append(shipAcceptRequestStr);
                aascConnectShipConnection = 
                        new AascConnectShipConnection(protocol, 
                                                      hostName, 
                                                      prefix + 
                                                      "/ShipAccept", 
                                                      userName, 
                                                      password);
                connection = 
                        aascConnectShipConnection.createConnection();
                try {
                    writeOutputFile(shipAcceptRequest.toString(), 
                                    outputFilePath + orderNumber + 
                                    "_shipaccept_" + 
                                    timeStampStr + "_request.xml");
                } catch (FileNotFoundException fileNotFoundException) {
                    logger.severe("file path to which ups shipaccept xml request need to be written is not found!!!" + 
                                  fileNotFoundException.getMessage());
                }
                shipAcceptResponse = 
                        aascXmlTransmitter.processRequest(shipAcceptRequest, 
                                                          connection, 
                                                          "ShipAccept"); // sending the request for processing and receiving the response                                    
                logger.info("shipAcceptResponse : "+shipAcceptResponse);                                          
                connection.disconnect();
                if (shipAcceptResponse != null && 
                    !shipAcceptResponse.equals("")) {
                    try {
                        writeOutputFile(shipAcceptResponse.toString(), 
                                        outputFilePath + orderNumber + 
                                        "_shipaccept_" + 
                                        timeStampStr + 
                                        "_response.xml");
                    } catch (FileNotFoundException fileNotFoundException) {
                        logger.severe("file path to which ups xml response need to be written is not found!!!" + 
                                      fileNotFoundException.getMessage());
                    }                            
                    aascUpsShipmentInfo = 
                            new AascUpsShipmentInfo(); // sending the response to AascCarrierShipmentInfo for parsing Response
                    parsedDataHashMap = 
                            aascUpsShipmentInfo.parseResponse(aascShipmentHeaderInfo, 
                                                              aascShipmentOrderInfo, 
                                                              labelFormat, 
                                                              shipAcceptResponse, 
                                                              "ShipAccept", 
                                                              aascProfileOptionsInfo, 
                                                              docTabLocation,cloudLabelPath); // returns String indicating status of parsing response
                    parsedStatus = 
                            (String)parsedDataHashMap.get("ResponseStatusDescription");
                    if (parsedStatus.equalsIgnoreCase("success")) {
                        responseStatus = 150;
                        return responseStatus;
                    } else {
                        responseStatus = 151;
                        aascShipmentHeaderInfo.setMainError((String)parsedDataHashMap.get("ErrorDetails"));
                        return responseStatus;
                    }
                } //end of if(shipAcceptResponse!= null && !shipAcceptResponse.equals("") )
            } //end of if(shipmentDigest!=null && !shipmentDigest.equals(""))
          } else {
            logger.severe("shipment digest is null");
            aascShipmentHeaderInfo.setMainError("shipment digest is null");
            responseStatus = 151;
            return responseStatus;
          }
        } // end of if (aascShipmentOrderInfo != null && aascShipmentHeaderInfo != null && linkedList != null && aascShipMethodInfo != null)
        else {
            aascShipmentHeaderInfo.setMainError((String)parsedDataHashMap.get("ErrorDetails"));
            responseStatus = 151;
            
            aascShipmentHeaderInfo.setMainError("\n aascShipmentOrderInfo is null OR" + 
                                             "\n aascShipmentHeaderInfo is null OR" + 
                                             "\n linkedList is null OR" + 
                                             "\n aascShipMethodInfo is null");
        }
      } //end of try
      catch (Exception e) {
          responseStatus = 151;
          aascShipmentHeaderInfo.setMainError("Error! null values or empty strings passed in request");

      } //end of catch 
      return responseStatus;
    }


    private String getPkgStr(float pkgWtVal, Double packageLength, Double packageWidth, Double packageHeight, String packageUnits, String pkgWtUom, String cod, 
                                  float codAmount, String dcisType) {

        logger.info("Entered getPkgStr()");
//        String intlShipFlag = aascShipmentHeaderInfo.getIntlShipFlag();
        String intlShipFlag = aascShipmentHeaderInfo.getInternationalFlag();
     //   System.out.println("InternationalShipFlag::::::::::::"+aascShipmentHeaderInfo.getInternationalFlag());
        String referencetag = "";
               String refTag1 = "";
               String refTag2 = "";
               

               if ("Y".equalsIgnoreCase(intlShipFlag)) {
                   referencetag = "";
               } else if (!("Y").equalsIgnoreCase(intlShipFlag) && (("US").equalsIgnoreCase(shipFromCountry) || ("PR").equalsIgnoreCase(shipFromCountry)))  {
                //   System.out.println("inside Domestic:::::::::");
                   if (!reference1.equalsIgnoreCase("")) {
                       refTag1 = 
                               "<ReferenceNumber>" + "<Code>" + "TN" + 
                               "</Code>" + "<Value>"  + reference1 + 
                               "</Value>" + "</ReferenceNumber>";
                           //    System.out.println("refTag1::::::::::"+refTag1);
                   }  if (!reference2.equalsIgnoreCase("")) {
                       refTag2 = 
                               "<ReferenceNumber>" + "<Code>" + "TN" + 
                               "</Code>" + "<Value>" + reference2 + "</Value>" + 
                               "</ReferenceNumber>";
                           //    System.out.println("refTag2::::::::::"+refTag2);
                   }
                   referencetag = refTag1 + refTag2;
                //   System.out.println("referencetag::::::::::::::::"+referencetag);
               }
        pkgStrHeader = 
                "<Package>" + referencetag + "<PackagingType>" + "<Code>" + packaging + "</Code>" + 
                "</PackagingType>" + "<Dimensions> " + 
                "<UnitOfMeasurement>" + "<Code>" + packageUnits + "</Code>" + 
                "</UnitOfMeasurement>" + " <Length>" + (int)packageLength.doubleValue() + 
                "</Length>" + "<Width>" + (int)packageWidth.doubleValue() + "</Width> " + 
                "<Height>" + (int)packageHeight.doubleValue() + "</Height> " + "</Dimensions>" + 
                "<PackageWeight>" + "<UnitOfMeasurement>" + "<Code>" + pkgWtUom + "</Code>" + 
                "</UnitOfMeasurement>" + "<Weight>" + pkgWtVal + "</Weight>" + 
                "</PackageWeight>"  + "<PackageServiceOptions>";      //Mahesh removed <AdditionalHandling/> tag sending in the request

        if (cod.equals("Y")) {
            pkgStrCOD = 
                    "<COD>" + "<CODFundsCode>" + codFundsCode + "</CODFundsCode>" + 
                    "<CODCode>" + codType + "</CODCode>" + "<CODAmount>" + 
                    "<CurrencyCode>" + codCurrCode + "</CurrencyCode>" + 
                    "<MonetaryValue>" + codAmount + "</MonetaryValue>" + 
                    "</CODAmount>" + "</COD>";
        } else {
            pkgStrCOD = "";
        }
        if ((dcisType != null && !"".equalsIgnoreCase(dcisType)) && 
            !dcisType.equalsIgnoreCase("NA") && !dcisType.equalsIgnoreCase("NONE")) { //Shiva added code for bug #1933
            dcisTag = 
                    "<DeliveryConfirmation>" + "<DCISType>" + dcisType + "</DCISType>" + 
                    "</DeliveryConfirmation>";
        } else {
            dcisTag = "";
        }
        pkgStrFooter = "</PackageServiceOptions>" + "</Package>";
        pkgStr = pkgStrHeader + dcisTag + pkgStrCOD + pkgStrFooter;
        logger.info("Exit from getPkgStr()");
        return pkgStr;
    } // end of getPkgStr       

    public String convertDate(String convertDate) {
        if (convertDate != "") {
            String rdate = "";
            int len = convertDate.length();
            int index = convertDate.indexOf('-');
            int index1 = convertDate.lastIndexOf('-');

            String syear = convertDate.substring(index1 + 1, len).trim();

            String sdate = convertDate.substring(0, index).trim();

            String smon = convertDate.substring(index + 1, index1).trim();

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

            rdate = sdate + smon + syear;
            return rdate;
        } else {
            return "";
        }
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

    public String formattingPhoneNumber(String phoneNo) {
        String var1 = null;
        String var2 = null;
        String var3 = null;

        try {

            if (nullStrToSpc(phoneNo) != "") {
                phoneNo = phoneNo.trim();
                int len = phoneNo.length();

                var1 = phoneNo.substring(0, 3);
                

                var2 = phoneNo.substring(3, 6);

                var3 = phoneNo.substring(6, len);

                phoneNo = "(" + var1 + ")" + var2 + "-" + var3;
            } else {
                phoneNo = "";
            }
        } catch (Exception e) {
            logger.severe("The Exception message in formatting phone no is: " + 
                          e.getMessage());
        }
        return phoneNo;
    }

}// end of the class


