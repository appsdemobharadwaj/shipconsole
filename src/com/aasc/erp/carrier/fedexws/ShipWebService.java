package com.aasc.erp.carrier.fedexws;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;

import java.util.HashMap;

import com.aasc.erp.util.AascLogger;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.security.cert.X509Certificate;

/**
 * This class is used to frame the soap request and sending it to fedex webservice
 * also save the soap request and response file to server.
 
   26/03/2015   Y Pradeep       Added code related to ship to email, ship from email and residential flag for FedEx Shipping.
   06/11/2015     Mahesh V        Added code to fix #3884 declaration statement to print on commrcial invoice
 10/11/2015       Mahesh V       Added code for FedEx Recepient Development
 27/11/2015       Mahesh V      Added code for FedEx Third Party and Recepient development
 */
public class ShipWebService {

    private static Logger logger = AascLogger.getLogger("ShipWebService");

    /**
     * This method build the request and send the request file to fedex webservice.
     * This method returns the soap response
     * @param hm It is a HashMap,which holds all the webservice requested values which we sent to fedex webservice.
     * @return It returns the fedex webservice response 
     */
    public String getShipInfo(HashMap hm, String ipAddress, int portNo) {
        String response = "";
        StringBuffer request = buildRequest(hm);
        String outputFile = nullStringToSpace(hm.get("outputFile"));
        //  outputFile = "E:\\Labels\\Nov-21-2011\\";

        String packageSequenceNum = 
            nullStringToSpace(hm.get("packageSequence"));
        String timeStampStr = 
            nullStringToSpace(hm.get("timeStampStr"));
        String appendStr = nullStringToSpace(hm.get("appendStr"));
        String customerTransactionIdentifier = 
            nullStringToSpace(hm.get("customerTransactionIdentifier"));
            
        String requestFilePath = 
            outputFile + appendStr + customerTransactionIdentifier + "_" + 
            packageSequenceNum + "_" + timeStampStr;
        try {
            response = 
                    sendSOAPRequest(request.toString(), true, ipAddress + ":" + 
                                    portNo + "/web-services/ship", 
                                    requestFilePath);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return response;
        }
    }

    private StringBuffer buildRequest(HashMap hm) {
        logger.info("****************************************************************************************");
        logger.info("HashMap: " + hm);
        logger.info("****************************************************************************************");
        String halPhone = "";
        String halLine1 = "";
        String halLine2 = "";
        String halCity = "";
        String halState = "";
        String halZip = "";
        String intSenderTINOrDUNS = "";
        String intSenderTINOrDUNSType = "";
        String senderEmail = "";
        String shipAlertNotification = "";
        String exceptionNotification = "";
        String deliveryNotification = "";
        String format = "";
        String optionalMessage = "";
        String recipientEmailAddress1 = "";
        String recipientEmailAddress2 = "";
        String recipientEmailAddress3 = "";
        String recipientEmailAddress4 = "";
        String recipientEmailAddress5 = "";

        String hazMatFlag = "";

        String accessibility = "";
        String properShippingName = "";
        String hazMatIdentificationNo = "";
        String hazardousMaterialPkgGroup = "";
        String hazMatDOTLabelType = "";
        double hazMatQty = 0.0;
        String hazMatUnit = "";
        String hazMatEmergencyContactNo = "";
        String hazMatEmergencyContactName = "";
        String hazardClass = "";

        // Added on Jul-05-2011
        String hazmatPkgingCnt = "";
        String hazmatPkgingUnits = "";
        String hazmatTechnicalName = "";
        //Edd on Jul-05-2011
         String hazmatSignatureName = "";

        String fedExWSChkReturnlabelstr = "";

        String rtnACNumber = "";
        String rtnMeterNumber = "";
        String rtnDropOfType = "";
        String rtnShipMethod = "";
        String rtnPackageList = "";

        String rtnShipFromContact = "";
        String rtnShipFromCompany = "";
        String rtnShipFromPhone = "";
        String rtnShipFromLine1 = "";
        String rtnShipFromLine2 = "";

        String rtnShipFromCity = "";
        String rtnShipFromSate = "";
        String rtnShipFromZip = "";
        String rtnShipToContact = "";

        String rtnShipToCompany = "";
        String rtnShipToPhone = "";
        String rtnShipToLine1 = "";
        String rtnShipToLine2 = "";
        String rtnShipToCity = "";
        String rtnShipToState = "";
        String rtnShipToZip = "";
        String rtnPayMethod = "";
        String rtnRMA = "";
        String fedExWsTimeStr = "";
        String labelFormat = "";
        String labelStockOrientation = "";
        String docTabLocation = "";
        
        String op900LabelFormat = "";
        String intlDocSubType = "";  //added by Jagadish
         String dutiesAndTaxesFlag = ""; //added by Jagadish

        double codAmtStr = 0.0;
        Date shipDate;
        double dryIceWeight = 0.0;
        // int count = 0;
        //  int rtnnum = 0;
        // int pkgSpecialCnt = 0;
 
        String packingListEnclosed = "false";
        int shippersLoadAndCount = 0;
        String bookingConfirmationNumber = "";
 
        String packageCount = 
            nullStringToSpace(hm.get("packageCount"));
        int sequenceNumberWS = 
            Integer.parseInt(nullStringToSpace(hm.get("sequenceNumber")));
        String carrierCode = 
            nullStringToSpace(hm.get("carrierCode"));
            
//        String shipMethodName = 
//            nullStringToSpace(hm.get("shipMethodName"));  
        
        String hubid = 
            nullStringToSpace(hm.get("hubid"));  
        String indicia = 
            nullStringToSpace(hm.get("indicia"));  
        String ancillary = 
            nullStringToSpace(hm.get("ancillary"));      
            
            
        String key = nullStringToSpace(hm.get("key"));
        String password = nullStringToSpace(hm.get("password"));
        String senderAccountNumber = 
            nullStringToSpace(hm.get("senderAccountNumber"));
        String senderMeterNumber = 
            nullStringToSpace(hm.get("fedExTestMeterNumber"));
        String customerCarrierAccountNumber = 
            nullStringToSpace(hm.get("customerCarrierAccountNumber"));
        String payorCountryCodeWS = 
            nullStringToSpace(hm.get("payorCountryCodeWS"));
        String shipToAddressCity = 
            nullStringToSpace(hm.get("shipToAddressCity"));
        String shipToAddressState = 
            nullStringToSpace(hm.get("shipToAddressState"));
        String shipToAddressPostalCode = 
            nullStringToSpace(hm.get("shipToAddressPostalCode"));
        String shipToAddressLine1 = 
            nullStringToSpace(hm.get("shipToAddressLine1"));
        String shipToAddressLine2 = 
            nullStringToSpace(hm.get("shipToAddressLine2"));
        String shipFromAddressCity = 
            nullStringToSpace(hm.get("shipFromAddressCity"));
        String shipFromAddressState = 
            nullStringToSpace(hm.get("shipFromAddressState"));
        String shipFromAddressPostalCode = 
            nullStringToSpace(hm.get("shipFromAddressPostalCode"));
        String shipFromCountry = 
            nullStringToSpace(hm.get("shipFromCountry"));
        
        String shipToCountry = 
            nullStringToSpace(hm.get("shipToCountry"));
        
        String shipToEMailAddress = nullStringToSpace(hm.get("shipToEMailAddress"));
        
        String shipFromCompanyName = 
            nullStringToSpace(hm.get("shipFromCompanyName"));
        String shipFromPersonName = 
            nullStringToSpace(hm.get("shipFromPersonName"));
        String shipFromAddressLine1 = 
            nullStringToSpace(hm.get("shipFromAddressLine1"));
        String shipFromAddressLine2 = 
            nullStringToSpace(hm.get("shipFromAddressLine2"));
            
        String shipFromPhoneNumber1 = 
            nullStringToSpace(hm.get("shipFromPhoneNumber1"));
            
        String shipFromEMailAddress = nullStringToSpace(hm.get("shipFromEMailAddress"));
        
        String receipientPartyName = 
            nullStringToSpace(hm.get("receipientPartyName"));
        String recipientPostalCode = 
            nullStringToSpace(hm.get("recipientPostalCode"));
  //Mahesh added below code for Third Party development          
        String tpCompanyName = 
                   nullStringToSpace(hm.get("tpCompanyName"));
        String tpAddress = 
                   nullStringToSpace(hm.get("tpAddress"));
        String tpCity = 
                   nullStringToSpace(hm.get("tpCity"));
        String tpState = 
                   nullStringToSpace(hm.get("tpState"));
        String tpPostalCode = 
                   nullStringToSpace(hm.get("tpPostalCode"));
        String tpCountrySymbol = 
                   nullStringToSpace(hm.get("tpCountrySymbol"));
   
        String shipToContactPhoneNumber = 
            nullStringToSpace(hm.get("shipToContactPhoneNumber"));
        String packageLength = 
            nullStringToSpace(hm.get("packageLength"));
        String packageWidth = 
            nullStringToSpace(hm.get("packageWidth"));
        String packageHeight = 
            nullStringToSpace(hm.get("packageHeight"));
        double pkgWtVal = ((Double)hm.get("pkgWtVal")).doubleValue();
        String dropoffType = 
            nullStringToSpace(hm.get("dropoffType"));
        String service = nullStringToSpace(hm.get("service"));
        String packaging = nullStringToSpace(hm.get("packaging"));
        String customerTransactionIdentifier = 
            nullStringToSpace(hm.get("customerTransactionIdentifier"));
        String OrderNumber = 
            nullStringToSpace(hm.get("orderName"));      
            
        String pkgWtUom = nullStringToSpace(hm.get("pkgWtUom"));
        String dimunits = nullStringToSpace(hm.get("dimunits"));
        String carrierPayMethod = 
            nullStringToSpace(hm.get("carrierPayMethod"));
        String reference2 = nullStringToSpace(hm.get("reference2"));
        String reference1 = nullStringToSpace(hm.get("reference1"));
        String referenceValue1 = 
            nullStringToSpace(hm.get("referenceValue1"));
        String referenceValue2 = 
            nullStringToSpace(hm.get("referenceValue2"));
        String lpnNumber = 
            nullStringToSpace(hm.get("lpnNumber"));    // added by Suman Gunda            
        String shipFromDepartment = 
            nullStringToSpace(hm.get("shipFromDepartment"));
        String residentialAddrFlag = 
            nullStringToSpace(hm.get("residentialAddrFlag"));
        String signatureOptions = 
            nullStringToSpace(hm.get("signatureOptions"));
        //SC_6.7.1_Merge
        String rtnIntlSedNumber = nullStringToSpace(hm.get("recIntlSedNumber"));
        logger.info("rtnIntlSedNumber"+rtnIntlSedNumber);
        String rtnIntlSedType = nullStringToSpace(hm.get("recIntlSedType"));
        logger.info("rtnIntlSedType"+rtnIntlSedType);
        //end SC_6.7.1_Merge
        
         String shipToContactPersonName = "";
             
         
        String shipToCompanyName = "";
            
//      Commented by Suman Gunda      
//         if(service.equalsIgnoreCase("SmartPost") && (lpnNumber != null && !"".equalsIgnoreCase(lpnNumber))){
//             shipToCompanyName = 
//                 OrderNumber+"/"+lpnNumber;
//             shipToContactPersonName = 
//                nullStringToSpace(hm.get("shipToCompanyName"));
//         }else{
             shipToCompanyName = 
                nullStringToSpace(hm.get("shipToCompanyName"));
             shipToContactPersonName = 
                nullStringToSpace(hm.get("shipToContactPersonName")); 
//         }
         

        //NavyList Issue - Fix for Signature Option Tag
        if(signatureOptions == null || signatureOptions.length() < 1 ){
            signatureOptions="NONE";   
        }
        
        /* if (!signatureOptions.equalsIgnoreCase("NONE")) {
            pkgSpecialCnt++;
        }*/
        String intFlag = (String)hm.get("intFlag");
        String generateCI = nullStringToSpace(hm.get("generateCI")); //moved up in the code by jagadish
        String halFlag = nullStringToSpace(hm.get("halFlag"));
        String codFlag = nullStringToSpace(hm.get("codFlag"));
        String emailFlag = nullStringToSpace(hm.get("emailFlag"));
        String chDryIce = nullStringToSpace(hm.get("chDryIce"));
        String satShipFlag = 
            nullStringToSpace(hm.get("satShipFlag"));
        if (chDryIce.equalsIgnoreCase("Y")) {
            //  pkgSpecialCnt++;
            dryIceWeight = ((Double)hm.get("dryIceWeight")).doubleValue();
        }
        /*  if (satShipFlag.equalsIgnoreCase("Y") ||
            satShipFlag.equalsIgnoreCase("1")) {
          //  count++;
        }*/
        if (codFlag.equalsIgnoreCase("Y")) {
            /*  if (carrierCode.equalsIgnoreCase("FDXG")) {
             //   pkgSpecialCnt++;
            } else {
           //     count++;
            }*/

            codAmtStr = ((Double)hm.get("codAmtStr")).doubleValue();
        }
        if (halFlag.equalsIgnoreCase("Y")) {
            // count++;
            halPhone = nullStringToSpace(hm.get("halPhone"));
            halLine1 = nullStringToSpace(hm.get("halLine1"));
            halLine2 = nullStringToSpace(hm.get("halLine2"));
            halCity = nullStringToSpace(hm.get("halCity"));
            halState = nullStringToSpace(hm.get("halState"));
            halZip = nullStringToSpace(hm.get("halZip"));
        }
        if (emailFlag.equalsIgnoreCase("Y")) {
            //  count++;
            //  rtnnum = count;
            senderEmail = (String)hm.get("SenderEmail");

            shipAlertNotification = 
                    nullStringToSpace(hm.get("ShipAlertNotification"));
            exceptionNotification = 
                    nullStringToSpace(hm.get("ExceptionNotification"));
            deliveryNotification = 
                    nullStringToSpace(hm.get("DeliveryNotification"));
            format = nullStringToSpace(hm.get("Format"));
            optionalMessage = nullStringToSpace(hm.get("message"));
            recipientEmailAddress1 = 
                    nullStringToSpace(hm.get("recipientEmailAddress1"));
            recipientEmailAddress2 = 
                    nullStringToSpace(hm.get("recipientEmailAddress2"));
            recipientEmailAddress3 = 
                    nullStringToSpace(hm.get("recipientEmailAddress3"));
            recipientEmailAddress4 = 
                    nullStringToSpace(hm.get("recipientEmailAddress4"));
            recipientEmailAddress5 = 
                    nullStringToSpace(hm.get("recipientEmailAddress5"));
        }

        hazMatFlag = nullStringToSpace(hm.get("HazMatFlag"));
        if (hazMatFlag.equalsIgnoreCase("Y")) {
            // pkgSpecialCnt++;
            accessibility = 
                    nullStringToSpace(hm.get("Accessibility"));
            logger.info("Accessibility  :: " + accessibility);
            properShippingName = 
                    nullStringToSpace(hm.get("ProperShippingName"));
            hazMatIdentificationNo = 
                    nullStringToSpace(hm.get("HazMatIdentificationNo"));
            hazardousMaterialPkgGroup = 
                    nullStringToSpace(hm.get("HazardousMaterialPkgGroup"));
            hazMatDOTLabelType = 
                    nullStringToSpace(hm.get("HazMatDOTLabelType"));
            hazMatQty = ((Double)hm.get("HazMatQty")).doubleValue();
            hazMatUnit = nullStringToSpace(hm.get("HazMatUnit"));
            hazMatEmergencyContactNo = 
                    nullStringToSpace(hm.get("HazMatEmergencyContactNo"));
            hazMatEmergencyContactName = 
                    nullStringToSpace(hm.get("HazMatEmergencyContactName"));
            hazardClass = 
                    nullStringToSpace((String)hm.get("HazardClass"));

            //Added on Jul-05-2011
            try {
                double pkgCnt = ((Double)hm.get("hazmatPkgingCnt")).intValue();
                int cnt = (int)pkgCnt;
                hazmatPkgingCnt = Integer.toString(cnt);
                logger.info("hazmatPkgingCnt  :: " + hazmatPkgingCnt);
            } catch (Exception e) {
                hazmatPkgingCnt = "0";
                logger.info("hazmatPkgingCnt in Exception block  :: " + 
                            hazmatPkgingCnt);
            }

            hazmatPkgingUnits = 
                    nullStringToSpace((String)hm.get("hazmatPkgingUnits"));
            logger.info("hazmatPkgingUnits  :: " + hazmatPkgingUnits);

            hazmatTechnicalName = 
                    nullStringToSpace((String)hm.get("hazmatTechnicalName"));
            logger.info("hazmatTechnicalName  :: " + hazmatTechnicalName);

            //End on Jul-05-2011
            
             hazmatSignatureName = 
                     nullStringToSpace((String)hm.get("hazmatSignatureName"));
             logger.info("hazmatSignatureName  :: " + hazmatSignatureName);
        }

        //Return Shipment
        fedExWSChkReturnlabelstr = 
                nullStringToSpace(hm.get("fedExWSChkReturnlabelstr"));
                logger.info("fedExWSChkReturnlabelstr"+fedExWSChkReturnlabelstr);

        if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
            //  rtnnum = rtnnum + 1;
            //  count++;
            rtnACNumber = nullStringToSpace(hm.get("rtnACNumber"));
            rtnMeterNumber = 
                    nullStringToSpace(hm.get("rtnMeterNumber"));
            rtnDropOfType = 
                    nullStringToSpace(hm.get("rtnDropOfType"));
            rtnShipMethod = 
                    nullStringToSpace(hm.get("rtnShipMethod"));
            rtnPackageList = 
                    nullStringToSpace(hm.get("rtnPackageList"));
            rtnShipFromContact = 
                    nullStringToSpace(hm.get("rtnShipFromContact"));
            rtnShipFromCompany = 
                    nullStringToSpace(hm.get("rtnShipFromCompany"));
            rtnShipFromPhone = 
                    nullStringToSpace(hm.get("rtnShipFromPhone"));
            rtnShipFromLine1 = 
                    nullStringToSpace(hm.get("rtnShipFromLine1"));
            rtnShipFromLine2 = 
                    nullStringToSpace(hm.get("rtnShipFromLine2"));
            rtnShipFromCity = 
                    nullStringToSpace(hm.get("rtnShipFromCity"));
            rtnShipFromSate = 
                    nullStringToSpace(hm.get("rtnShipFromSate"));
            rtnShipFromZip = 
                    nullStringToSpace(hm.get("rtnShipFromZip"));
            shipFromCountry = 
                    nullStringToSpace(hm.get("shipFromCountry"));
            rtnShipToContact = 
                    nullStringToSpace(hm.get("rtnShipToContact"));
            rtnShipToCompany = 
                    nullStringToSpace(hm.get("rtnShipToCompany"));
            rtnShipToPhone = 
                    nullStringToSpace(hm.get("rtnShipToPhone"));
            rtnShipToLine1 = 
                    nullStringToSpace(hm.get("rtnShipToLine1"));
            rtnShipToLine2 = 
                    nullStringToSpace(hm.get("rtnShipToLine2"));
            rtnShipToCity = 
                    nullStringToSpace(hm.get("rtnShipToCity"));
            rtnShipToState = 
                    nullStringToSpace(hm.get("rtnShipToState"));
            rtnShipToZip = nullStringToSpace(hm.get("rtnShipToZip"));
            shipToCountry = 
                    nullStringToSpace(hm.get("shipToCountry"));
            rtnPayMethod = nullStringToSpace(hm.get("rtnPayMethod"));
            rtnRMA = nullStringToSpace(hm.get("rtnRMA"));
        }

        double totalWeight = (Double)hm.get("totalWeight");
        shipDate = (Date)hm.get("shipDate");
        double packageDeclaredValue = (Double)hm.get("packageDeclaredValue");
        fedExWsTimeStr = nullStringToSpace(hm.get("fedExWsTimeStr"));
        labelFormat = nullStringToSpace(hm.get("labelFormat"));
        labelStockOrientation = 
                nullStringToSpace(hm.get("labelStockOrientation"));
        docTabLocation = nullStringToSpace(hm.get("docTabLocation"));
        
        op900LabelFormat = nullStringToSpace(hm.get("op900LabelFormat"));
        intlDocSubType = nullStringToSpace(hm.get("intlDocSubType"));
  //      System.out.println("Here intldocsubtype 477"+intlDocSubType);
        dutiesAndTaxesFlag = nullStringToSpace(hm.get("dutiesAndTaxesFlag"));
        
        StringBuffer shipMentRequest = new StringBuffer("");
        try {

            shipMentRequest.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://fedex.com/ws/ship/v12\">\n"); // Jagadish changed WSDL version from 9 to 12
            shipMentRequest.append("<SOAP-ENV:Body>\n");
            shipMentRequest.append("<ProcessShipmentRequest>\n");
            shipMentRequest.append(createWebAuthenticationDetail(key, 
                                                                 password));
            shipMentRequest.append(createClientDetail(senderAccountNumber, 
                                                      senderMeterNumber));
            shipMentRequest.append(createTransactionDetail(customerTransactionIdentifier));
            shipMentRequest.append(createVersion());
            shipMentRequest.append("<RequestedShipment>\n");
            shipMentRequest.append(createShipTimestamp(shipDate, 
                                                       fedExWsTimeStr));
            if (intFlag.equalsIgnoreCase("Y")) {
                intSenderTINOrDUNS = 
                        nullStringToSpace(hm.get("intSenderTINOrDUNS"));
                intSenderTINOrDUNSType = 
                        nullStringToSpace(hm.get("intSenderTINOrDUNSType"));
                shipMentRequest.append(createIntlDropOffServicePackagingTypes(fedExWSChkReturnlabelstr, 
                                                                              rtnDropOfType, 
                                                                              rtnShipMethod, 
                                                                              rtnPackageList, 
                                                                              dropoffType, 
                                                                              service, 
                                                                              packaging));
            } else {
                shipMentRequest.append(createDropOffServicePackagingTypes(fedExWSChkReturnlabelstr, 
                                                                          rtnDropOfType, 
                                                                          rtnShipMethod, 
                                                                          rtnPackageList, 
                                                                          dropoffType, 
                                                                          service, 
                                                                          packaging));
            }
            
            if(service.equalsIgnoreCase("SmartPost")){
                shipMentRequest.append(createTotalWeight(pkgWtUom, pkgWtVal));
            }
            else
            {
            shipMentRequest.append(createTotalWeight(pkgWtUom, totalWeight));//pkgWtVal
            }
            shipMentRequest.append(createShipperRecipientAddress(fedExWSChkReturnlabelstr, 
                                                                 service, 
                                                                 residentialAddrFlag, 
                                                                 intFlag, 
                                                                 intSenderTINOrDUNS, 
                                                                 intSenderTINOrDUNSType,
                                                                 shipFromPersonName, 
                                                                 shipFromCompanyName, 
                                                                 shipFromPhoneNumber1, 
                                                                 shipFromEMailAddress,
                                                                 shipFromAddressLine1, 
                                                                 shipFromAddressLine2, 
                                                                 shipFromAddressCity, 
                                                                 shipFromAddressState, 
                                                                 shipFromAddressPostalCode, 
                                                                 shipFromCountry, 
                                                                 shipToContactPersonName, 
                                                                 shipToCompanyName, 
                                                                 shipToContactPhoneNumber, 
                                                                 shipToAddressLine1, 
                                                                 shipToAddressLine2, 
                                                                 shipToAddressCity, 
                                                                 shipToAddressState, 
                                                                 shipToAddressPostalCode, 
                                                                 shipToCountry, 
                                                                 shipToEMailAddress,
                                                                 rtnShipFromContact, 
                                                                 rtnShipFromCompany, 
                                                                 rtnShipFromPhone, 
                                                                 rtnShipFromLine1, 
                                                                 rtnShipFromLine2, 
                                                                 rtnShipFromCity, 
                                                                 rtnShipFromSate, 
                                                                 rtnShipFromZip, 
                                                                 rtnShipToContact, 
                                                                 rtnShipToCompany, 
                                                                 rtnShipToPhone, 
                                                                 rtnShipToLine1, 
                                                                 rtnShipToLine2, 
                                                                 rtnShipToCity, 
                                                                 rtnShipToState, 
                                                                 rtnShipToZip,
                                                                 rtnIntlSedNumber,
                                                                 rtnIntlSedType));
            shipMentRequest.append(createShippingChargesPayment(fedExWSChkReturnlabelstr, 
                                                                rtnPayMethod, 
                                                                shipFromCountry, 
                                                                payorCountryCodeWS, 
                                                                customerCarrierAccountNumber, 
                                                                carrierPayMethod, receipientPartyName,recipientPostalCode,
                                                                tpCompanyName,                                               //Mahesh added code for Third Party development 
                                                                tpAddress,
                                                                tpCity,
                                                                tpState,
                                                                tpPostalCode,
                                                                tpCountrySymbol));
                                                                                                           
            
            
             if(service.equalsIgnoreCase("SmartPost") && (fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
             
              shipMentRequest.append(createSmartPostDetailsNotRtn(indicia,ancillary,hubid));    
                
            }
            
            //  shipMentRequest.append("<PackageRateDetails>\n");
            //  shipMentRequest.append("<RateType>PAYOR_ACCOUNT</RateType>\n");
            //  shipMentRequest.append("</PackageRateDetails>\n");
            
           
             if(service.equalsIgnoreCase("SmartPost"))
             {         
                    if(!(fedExWSChkReturnlabelstr.equals("NONRETURN")))
                    {
                     shipMentRequest.append("<SpecialServicesRequested>\n");
                    }
             }
             else{
                 shipMentRequest.append("<SpecialServicesRequested>\n");
             }
            
            //***************************************************************************
            //SC_6.7.1_Merge
            String brokerNameStr =nullStringToSpace(hm.get("brokerName"));
             logger.info("brokerNameStr"+brokerNameStr);
             if(brokerNameStr.length() > 1 ){
                 shipMentRequest.append("<SpecialServiceTypes>BROKER_SELECT_OPTION</SpecialServiceTypes>");
             }//end SC_6.7.1_Merge

            if ((codFlag.equalsIgnoreCase("Y"))) {
                if (!carrierCode.equalsIgnoreCase("FDXG")) {
                    shipMentRequest.append("<SpecialServiceTypes>COD</SpecialServiceTypes>");
                }
            }
            if ((halFlag.equalsIgnoreCase("Y"))) {
                shipMentRequest.append("<SpecialServiceTypes>HOLD_AT_LOCATION</SpecialServiceTypes>");
            }
            if ((emailFlag.equalsIgnoreCase("Y"))) {
                    if(!service.equalsIgnoreCase("SmartPost")){
                    shipMentRequest.append("<SpecialServiceTypes>EMAIL_NOTIFICATION</SpecialServiceTypes>");
                    }
            }
            if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
                shipMentRequest.append("<SpecialServiceTypes>RETURN_SHIPMENT</SpecialServiceTypes>");
            }
            if (satShipFlag.equalsIgnoreCase("Y") || 
                satShipFlag.equalsIgnoreCase("1")) {
                shipMentRequest.append("<SpecialServiceTypes>SATURDAY_DELIVERY</SpecialServiceTypes>\n");
            }
            //added by Jagadish
            if((intlDocSubType.equalsIgnoreCase("Electronic") || intlDocSubType.equalsIgnoreCase("Electronic And Paper")) && intFlag.equalsIgnoreCase("Y") && generateCI.equalsIgnoreCase("Y")) {
   //             System.out.println("Here intldocsubtype 627"+intlDocSubType);
                shipMentRequest.append("<SpecialServiceTypes>ELECTRONIC_TRADE_DOCUMENTS</SpecialServiceTypes>\n");
            }
            //***************************************************************************
            if ((codFlag.equalsIgnoreCase("Y"))) {
                if (!carrierCode.equalsIgnoreCase("FDXG")) {
                    shipMentRequest.append(createCod(codAmtStr));
                }
            }
            if ((halFlag.equalsIgnoreCase("Y"))) {
                shipMentRequest.append(createHoldAtLocation(halPhone, halLine1, 
                                                            halLine2, halCity, 
                                                            halState, halZip, 
                                                            shipToCountry));
            }
            if ((emailFlag.equalsIgnoreCase("Y"))) {
                if(!service.equalsIgnoreCase("SmartPost")){
                shipMentRequest.append(createEmailNotification(shipAlertNotification, 
                                                               exceptionNotification, 
                                                               deliveryNotification, 
                                                               "EN", 
                                                               recipientEmailAddress1, 
                                                               recipientEmailAddress2, 
                                                               recipientEmailAddress3, 
                                                               recipientEmailAddress4, 
                                                               recipientEmailAddress5, 
                                                               senderEmail, 
                                                               optionalMessage));
                }
            }
            if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
                shipMentRequest.append(createReturnShipment(rtnRMA));
            }

            //added by Jagadish
            if(intlDocSubType.equalsIgnoreCase("Electronic And Paper") && intFlag.equalsIgnoreCase("Y") && generateCI.equalsIgnoreCase("Y")) {
        //        System.out.println("Here intldocsubtype 664"+intlDocSubType);
                shipMentRequest.append("<EtdDetail>" +
                "<RequestedDocumentCopies>COMMERCIAL_INVOICE</RequestedDocumentCopies>" +
                "</EtdDetail>");
            }
           
            if(service.equalsIgnoreCase("SmartPost"))
            {         
                   if(!(fedExWSChkReturnlabelstr.equals("NONRETURN")))
                   {
                    shipMentRequest.append("</SpecialServicesRequested>\n");
                   }
            }
            else{
                shipMentRequest.append("</SpecialServicesRequested>\n");
            }
            
            
            if (service.equalsIgnoreCase("SmartPost") && !(fedExWSChkReturnlabelstr.equals("NONRETURN")) ) {
                if(rtnShipMethod.equalsIgnoreCase("SmartPost"))
                {
            
                    shipMentRequest.append(createSmartPostDetailsRtn(indicia,ancillary,hubid)); 
            
                }
            }
            
            
            if(service.equals("INTERNATIONALECONOMYFREIGHT")|| service.equals("INTERNATIONALECONOMY FREIGHT") ||
               service.equals("INTERNATIONALPRIORITYFREIGHT") || service.equals("INTERNATIONALPRIORITY FREIGHT"))
            {
                logger.info("hm.get(\"packingListEnclosed\") : "+hm.get("packingListEnclosed"));
                logger.info("hm.get(\"shippersLoadAndCount\") : "+hm.get("shippersLoadAndCount"));
                logger.info("hm.get(\"bookingConfirmationNumber\") : "+hm.get("bookingConfirmationNumber"));
                packingListEnclosed = nullStringToSpace(hm.get("packingListEnclosed"));  
                shippersLoadAndCount = Integer.parseInt(nullStringToSpace(hm.get("shippersLoadAndCount")));
                bookingConfirmationNumber = nullStringToSpace(hm.get("bookingConfirmationNumber"));
            
                shipMentRequest.append("<ExpressFreightDetail>\n");
                
                shipMentRequest.append("<PackingListEnclosed>");
                shipMentRequest.append(packingListEnclosed);  // 1 for true and 0 for false
                shipMentRequest.append("</PackingListEnclosed>\n"); 
                shipMentRequest.append("<ShippersLoadAndCount>");
                shipMentRequest.append(shippersLoadAndCount); // 2
                shipMentRequest.append("</ShippersLoadAndCount>\n");
                shipMentRequest.append("<BookingConfirmationNumber>");
                shipMentRequest.append(bookingConfirmationNumber);  //123asd789
                shipMentRequest.append("</BookingConfirmationNumber>\n");
                shipMentRequest.append("</ExpressFreightDetail>");
            
            }
            
            //International Details
            if (intFlag.equalsIgnoreCase("Y")) {
                String tPintAccNumber = 
                    nullStringToSpace(hm.get("TPintAccNumber"));
                String tPintcountryCode = 
                    nullStringToSpace(hm.get("TPintcountryCode"));
                String intPayerType = 
                    nullStringToSpace(hm.get("intPayerType"));
                double intFreightCharge = (Double)hm.get("intFreightCharge");
                double intInsuranceCharge = 
                    (Double)hm.get("intInsuranceCharge");
                double intTaxesOrMiscellaneousCharge = 
                    (Double)hm.get("intTaxesOrMiscellaneousCharge");
                String intPurpose = 
                    nullStringToSpace(hm.get("intPurpose"));
                String exemptionNumber = 
                    nullStringToSpace(hm.get("exemptionNumber"));
                String intTermsOfSale = 
                    nullStringToSpace(hm.get("intTermsOfSale"));
                String intTotalCustomsValue = 
                    nullStringToSpace(hm.get("intTotalCustomsValue"));
                HashMap[] intlCommodityHM = 
                    (HashMap[])hm.get("intlCommodityHM");

//SC_6.7.1_Merge

//String generateCI = nullStringToSpace(hm.get("generateCI"));
                logger.info("generateCI"+generateCI);
                
                String declarationStmt = nullStringToSpace(hm.get("declarationStmt"));
                logger.info("declarationStmt"+declarationStmt);
                String importerName=nullStringToSpace(hm.get("importerName"));
                logger.info("importerName"+importerName);
                String importerCompName = nullStringToSpace(hm.get("importerCompName"));
                String importerPhoneNum = nullStringToSpace(hm.get("importerPhoneNum"));
                String importerAddress1 = nullStringToSpace(hm.get("importerAddress1"));
                String importerAddress2 = nullStringToSpace(hm.get("importerAddress2"));
                String importerCity = nullStringToSpace(hm.get("importerCity"));
                String importerState = nullStringToSpace(hm.get("importerState"));
                String importerPostalCode = nullStringToSpace(hm.get("importerPostalCode"));
                String importerCountryCode = nullStringToSpace(hm.get("importerCountryCode"));
                String impIntlSedNumber = nullStringToSpace(hm.get("impIntlSedNumber"));
                String impIntlSedType = nullStringToSpace(hm.get("impIntlSedType"));
                logger.info("impIntlSedType"+impIntlSedType);
                
                String brokerName=nullStringToSpace(hm.get("brokerName"));
                logger.info("brokerName"+brokerName);
                String brokerCompName = nullStringToSpace(hm.get("brokerCompName"));
                String brokerPhoneNum = nullStringToSpace(hm.get("brokerPhoneNum"));
                String brokerAddress1 = nullStringToSpace(hm.get("brokerAddress1"));
                String brokerAddress2 = nullStringToSpace(hm.get("brokerAddress2"));
                String brokerCity = nullStringToSpace(hm.get("brokerCity"));
                String brokerState = nullStringToSpace(hm.get("brokerState"));
                String brokerPostalCode = nullStringToSpace(hm.get("brokerPostalCode"));
                String brokerCountryCode = nullStringToSpace(hm.get("brokerCountryCode"));

//end of SC_6.7.1_Merge

                shipMentRequest.append(createCustomsClearanceDetail(
                                                                    intPayerType, 
                                                                    shipFromCountry, 
                                                                    tPintcountryCode, 
                                                                    tPintAccNumber, 
                                                                    customerCarrierAccountNumber, 
                                                                    intTotalCustomsValue, 
                                                                    intInsuranceCharge, 
                                                                    intFreightCharge, 
                                                                    intTaxesOrMiscellaneousCharge, 
                                                                    intPurpose, 
                                                                    intTermsOfSale, 
                                                                    exemptionNumber, 
                                                                    intlCommodityHM,
                                                                    importerName,
                                                                    importerCompName,
                                                                    importerPhoneNum,
                                                                    importerAddress1,
                                                                    importerAddress2,
                                                                    importerCity,
                                                                    importerState,
                                                                    importerPostalCode,
                                                                    importerCountryCode,
                                                                    impIntlSedNumber,
                                                                    impIntlSedType,
                                                                    declarationStmt,
                                                                    generateCI,
                                                                   brokerName,
                                                                   brokerCompName,
                                                                   brokerPhoneNum,
                                                                   brokerAddress1,
                                                                   brokerAddress2,
                                                                   brokerCity,
                                                                   brokerState,
                                                                   brokerPostalCode,
                                                                   brokerCountryCode));
            }
            shipMentRequest.append(createLabelSpecification(labelFormat, 
                                                            labelStockOrientation, 
                                                            docTabLocation,customerTransactionIdentifier,OrderNumber,reference1,reference2,referenceValue1,referenceValue2));
            
            //SC_6.7.1_Merge
             if("FDXG".equalsIgnoreCase(carrierCode)){
                        shipMentRequest.append(createHazmatOp900(hazmatSignatureName,op900LabelFormat));
            }

            generateCI = nullStringToSpace(hm.get("generateCI"));
            if (intFlag.equalsIgnoreCase("Y") && "Y".equalsIgnoreCase(generateCI)) {
//             if (intFlag.equalsIgnoreCase("Y") ) {
            shipMentRequest.append(setCommercialInvoice());
            }//end of SC_6.7.1_Merge
            
            
            shipMentRequest.append(createRateRequestTypes());
            // added by jagadish for intldocsubtype and dutiesAndTaxesFlag
            if(intFlag.equalsIgnoreCase("Y") &&  ( "Y".equalsIgnoreCase(dutiesAndTaxesFlag) || (generateCI.equalsIgnoreCase("Y") && (intlDocSubType.equalsIgnoreCase("Electronic") || intlDocSubType.equalsIgnoreCase("Electronic And Paper")) ))) {
       //         System.out.println("Here intldocsubtype 830"+intlDocSubType);
                shipMentRequest.append(createEtdRequestType());
            }
            logger.info("sequenceNumberWS  :: " + sequenceNumberWS + 
                        "   packageCount  ::" + packageCount);
           
            if(!service.equalsIgnoreCase("SmartPost") && fedExWSChkReturnlabelstr.equalsIgnoreCase("NONRETURN"))
            {
            if (!packageCount.equalsIgnoreCase("1")) {
                if (sequenceNumberWS > 1) {
                    String masterTrackingNumber = 
                        (String)hm.get("masterTrackingNumber");
                    String masterFormID = (String)hm.get("masterFormID");
                   
                    shipMentRequest.append(createMultiPieceShipment(carrierCode, 
                                                                    masterTrackingNumber, 
                                                                  masterFormID));
                }
            }
                shipMentRequest.append(createPackageCount(packageCount, 
                                                          sequenceNumberWS, 
                                                          fedExWSChkReturnlabelstr));
            }
            
            else 
            {
            
            shipMentRequest.append(createPackageCount("1",1,fedExWSChkReturnlabelstr));
            }                    
           
            shipMentRequest.append(createPackageDetail());
            shipMentRequest.append("<RequestedPackageLineItems>\n");
            if(!service.equalsIgnoreCase("SmartPost") && (fedExWSChkReturnlabelstr.equals("NONRETURN")))
            {
            shipMentRequest.append(createSequenceNumber(sequenceNumberWS));
            }
            else {
                shipMentRequest.append(createSequenceNumber(1));
            }
            shipMentRequest.append(createInsuredValue(packageDeclaredValue));
            shipMentRequest.append(createWeight(pkgWtUom, pkgWtVal));
            if (!(packageLength.equalsIgnoreCase("0") && 
                  packageWidth.equalsIgnoreCase("0") && 
                  packageHeight.equalsIgnoreCase("0")) && !(packageLength.equalsIgnoreCase("0.0") && 
                  packageWidth.equalsIgnoreCase("0.0") && 
                  packageHeight.equalsIgnoreCase("0.0"))) {
                  
                //  System.out.println("in shipwebservice if condition entered::::::::");
                  
                shipMentRequest.append(createDimensions(packageLength, 
                                                        packageWidth, 
                                                        packageHeight, 
                                                        dimunits));
            }
            shipMentRequest.append(createCustomerReferences(referenceValue1, 
                                                            reference1, 
                                                            referenceValue2, 
                                                            reference2, 
                                                            shipFromDepartment,lpnNumber,OrderNumber));
            shipMentRequest.append("<SpecialServicesRequested>\n");
            //***************************************************************************************
            if ((codFlag.equalsIgnoreCase("Y")) && 
                (carrierCode.equalsIgnoreCase("FDXG"))) {
                shipMentRequest.append("<SpecialServiceTypes>COD</SpecialServiceTypes>");
            }

            if ((hazMatFlag.equalsIgnoreCase("Y"))) {
                shipMentRequest.append("<SpecialServiceTypes>DANGEROUS_GOODS</SpecialServiceTypes>");
            }

            if ((chDryIce.equalsIgnoreCase("Y"))) {
                shipMentRequest.append("<SpecialServiceTypes>DRY_ICE</SpecialServiceTypes>");
            }

            if ((!signatureOptions.equalsIgnoreCase("NONE"))) {
                shipMentRequest.append("<SpecialServiceTypes>SIGNATURE_OPTION</SpecialServiceTypes>");
            }
            //***************************************************************************************
            if ((codFlag.equalsIgnoreCase("Y")) && 
                (carrierCode.equalsIgnoreCase("FDXG"))) {
                shipMentRequest.append(createCod(codAmtStr));
            }

            if ((hazMatFlag.equalsIgnoreCase("Y"))) {
                shipMentRequest.append(createDangerousGoodsDetail(carrierCode, 
                                                                  accessibility, 
                                                                  hazMatIdentificationNo, 
                                                                  hazardousMaterialPkgGroup, 
                                                                  properShippingName, 
                                                                  hazMatEmergencyContactName, 
                                                                  hazardClass, 
                                                                  hazMatDOTLabelType, 
                                                                  hazMatQty, 
                                                                  hazMatUnit, 
                                                                  hazmatPkgingCnt, 
                                                                  hazmatPkgingUnits, 
                                                                  hazMatEmergencyContactNo, 
                                                                  "Offeror", 
                                                                  hazmatTechnicalName));
            }

            if ((chDryIce.equalsIgnoreCase("Y"))) {
                shipMentRequest.append(createDryIce(dryIceWeight));
            }

            if ((!signatureOptions.equalsIgnoreCase("NONE"))) {
                shipMentRequest.append(createSignatureOptionDetail(signatureOptions));
            }
            shipMentRequest.append("</SpecialServicesRequested>");
            shipMentRequest.append("</RequestedPackageLineItems>\n");
            shipMentRequest.append("</RequestedShipment>\n");
            shipMentRequest.append("</ProcessShipmentRequest>\n");
            shipMentRequest.append("</SOAP-ENV:Body>\n");
            shipMentRequest.append("</SOAP-ENV:Envelope>\n");


        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } finally {
            return shipMentRequest;
        }
    }


    private StringBuffer createClientDetail(String accountNumber, 
                                            String meterNumber) {
        StringBuffer clientDetail = new StringBuffer("");
        clientDetail.append("<ClientDetail>");
        clientDetail.append("<AccountNumber>" + accountNumber + 
                            "</AccountNumber>");
        clientDetail.append("<MeterNumber>" + meterNumber + "</MeterNumber>");
//        clientDetail.append("<ClientProductId>ShipConsole</ClientProductId>"); Commented by Jagadish. These are outdated tags which aren't supported in FedEx ship service wsdl v12 
//        clientDetail.append("<ClientProductVersion>5</ClientProductVersion>");
        clientDetail.append("<IntegratorId>7</IntegratorId>"); // By Jagadish. New tag inroduced in v12 
        clientDetail.append("</ClientDetail>\n");

        return clientDetail;
    }

    private StringBuffer createWebAuthenticationDetail(String key, 
                                                       String password) {
        StringBuffer webAuthenticationDetail = new StringBuffer("");
        webAuthenticationDetail.append("<WebAuthenticationDetail>");
        webAuthenticationDetail.append("<UserCredential>");
        webAuthenticationDetail.append("<Key>" + key + "</Key>");
        webAuthenticationDetail.append("<Password>" + password + 
                                       "</Password>");
        webAuthenticationDetail.append("</UserCredential>");
        webAuthenticationDetail.append("</WebAuthenticationDetail>\n");

        return webAuthenticationDetail;
    }

    private StringBuffer createTransactionDetail(String transactionDetailStr) {
        StringBuffer transactionDetail = new StringBuffer("");
        transactionDetail.append("<TransactionDetail>");
        transactionDetail.append("<CustomerTransactionId>" + 
                                 transactionDetailStr + 
                                 "</CustomerTransactionId>");
        transactionDetail.append("</TransactionDetail>\n");

        return transactionDetail;
    }

    private StringBuffer createVersion() {
        StringBuffer version = new StringBuffer("");
        version.append("<Version>");
        version.append("<ServiceId>ship</ServiceId>");
        version.append("<Major>12</Major>"); // Jagadish changed the version from 9 to 12.
        version.append("<Intermediate>0</Intermediate>");
        version.append("<Minor>0</Minor>");
        version.append("</Version>\n");

        return version;
    }

    private StringBuffer createShipTimestamp(Date shipDate, 
                                             String fedExWsTimeStr) {
        StringBuffer shipTimestamp = new StringBuffer();
        String hr = fedExWsTimeStr.substring(0, 2);
        String min = fedExWsTimeStr.substring(3, 5);
        String sec = fedExWsTimeStr.substring(6, 8);

        shipTimestamp.append("<ShipTimestamp>");
        shipTimestamp.append(shipDate + "T" + hr + ":" + min + ":" + 
                             sec); //ShipTimestamp.append("2011-05-16T09:30:47-05:00");
        shipTimestamp.append("</ShipTimestamp>\n");

        return shipTimestamp;
    }

    private StringBuffer createDropOffServicePackagingTypes(String fedExWSChkReturnlabelstr, 
                                                            String rtnDropOfType, 
                                                            String rtnShipMethod, 
                                                            String rtnPackageList, 
                                                            String dropoffType, 
                                                            String service, 
                                                            String packaging) {
        StringBuffer dropOffServicePackagingTypes = new StringBuffer("");

        if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
            dropOffServicePackagingTypes.append("<DropoffType>");
            if (rtnDropOfType.equals("REGULARPICKUP")) {
                dropOffServicePackagingTypes.append("REGULAR_PICKUP");
            } else if (rtnDropOfType.equals("DROPBOX")) {
                dropOffServicePackagingTypes.append("DROP_BOX");
            } else if (rtnDropOfType.equals("BUSINESSSERVICECENTER")) {
                dropOffServicePackagingTypes.append("BUSINESS_SERVICE_CENTER");
            } else if (rtnDropOfType.equals("STATION")) {
                dropOffServicePackagingTypes.append("STATION");
            } else if (rtnDropOfType.equals("REQUESTCOURIER")) {
                dropOffServicePackagingTypes.append("REQUEST_COURIER");
            }
            dropOffServicePackagingTypes.append("</DropoffType>");


            dropOffServicePackagingTypes.append("<ServiceType>");
            if (rtnShipMethod.equals("PRIORITYOVERNIGHT")) {
                dropOffServicePackagingTypes.append("PRIORITY_OVERNIGHT");
            } else if (rtnShipMethod.equals("STANDARDOVERNIGHT")) {
                dropOffServicePackagingTypes.append("STANDARD_OVERNIGHT");
            } else if (rtnShipMethod.equals("FEDEX1DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_1_DAY_FREIGHT");
            } else if (rtnShipMethod.equals("FEDEX2DAY")) {
                dropOffServicePackagingTypes.append("FEDEX_2_DAY");
            } else if (rtnShipMethod.equals("FEDEX2DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_2_DAY_FREIGHT");
            } else if (rtnShipMethod.equals("FEDEX3DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_3_DAY_FREIGHT");
            } else if (rtnShipMethod.equals("FEDEXEXPRESSSAVER")) {
                dropOffServicePackagingTypes.append("FEDEX_EXPRESS_SAVER");
            } else if (rtnShipMethod.equals("FEDEXGROUND")) {
                dropOffServicePackagingTypes.append("FEDEX_GROUND");
            } else if (rtnShipMethod.equals("FIRSTOVERNIGHT")) {
                dropOffServicePackagingTypes.append("FIRST_OVERNIGHT");
            } else if (rtnShipMethod.equals("GROUNDHOMEDELIVERY")) {
                dropOffServicePackagingTypes.append("GROUND_HOME_DELIVERY");
            }
            else if (rtnShipMethod.equals("SMARTPOST")) {
                            dropOffServicePackagingTypes.append("SMART_POST");
                        }
            else if (rtnShipMethod.equals("FEDEX_2_DAY_AM")) {
             dropOffServicePackagingTypes.append("FEDEX_2_DAY_AM");   // Added by Jagadish
            }
            else if (rtnShipMethod.equals("FEDEX_FIRST_FREIGHT")) {
            dropOffServicePackagingTypes.append("FEDEX_FIRST_FREIGHT");   // Added by Jagadish
            }
            dropOffServicePackagingTypes.append("</ServiceType>");


            dropOffServicePackagingTypes.append("<PackagingType>");
            if (rtnPackageList.equals("YOURPACKAGING")) {
                dropOffServicePackagingTypes.append("YOUR_PACKAGING");
            } else if (rtnPackageList.equals("FEDEXBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_BOX");
            } else if (rtnPackageList.equals("FEDEXENVELOPE")) {
                dropOffServicePackagingTypes.append("FEDEX_ENVELOPE");
            } else if (rtnPackageList.equals("FEDEXPAK")) {
                dropOffServicePackagingTypes.append("FEDEX_PAK");
            } else if (rtnPackageList.equals("FEDEXTUBE")) {
                dropOffServicePackagingTypes.append("FEDEX_TUBE");
            }
            dropOffServicePackagingTypes.append("</PackagingType>");

        } else {

            logger.info("dropoffType: " + dropoffType);
            dropOffServicePackagingTypes.append("<DropoffType>");
            if (dropoffType.equals("REGULARPICKUP")) {
                dropOffServicePackagingTypes.append("REGULAR_PICKUP");
            } else if (dropoffType.equals("DROPBOX")) {
                dropOffServicePackagingTypes.append("DROP_BOX");
            } else if (dropoffType.equals("BUSINESSSERVICECENTER")) {
                dropOffServicePackagingTypes.append("BUSINESS_SERVICE_CENTER");
            } else if (dropoffType.equals("STATION")) {
                dropOffServicePackagingTypes.append("STATION");
            } else if (dropoffType.equals("REQUESTCOURIER")) {
                dropOffServicePackagingTypes.append("REQUEST_COURIER");
            }
            dropOffServicePackagingTypes.append("</DropoffType>");


            dropOffServicePackagingTypes.append("<ServiceType>");
            if (service.equals("PRIORITYOVERNIGHT")) {
                dropOffServicePackagingTypes.append("PRIORITY_OVERNIGHT");
            } else if (service.equals("STANDARDOVERNIGHT")) {
                dropOffServicePackagingTypes.append("STANDARD_OVERNIGHT");
            } else if (service.equals("FEDEX1DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_1_DAY_FREIGHT");
            } else if (service.equals("FEDEX2DAY")) {
                dropOffServicePackagingTypes.append("FEDEX_2_DAY");
            } else if (service.equals("FEDEX2DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_2_DAY_FREIGHT");
            } else if (service.equals("FEDEX3DAYFREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_3_DAY_FREIGHT");
            } else if (service.equals("FEDEXEXPRESSSAVER")) {
                dropOffServicePackagingTypes.append("FEDEX_EXPRESS_SAVER");
            } else if (service.equals("FEDEXGROUND")) {
                dropOffServicePackagingTypes.append("FEDEX_GROUND");
            } else if (service.equals("FIRSTOVERNIGHT")) {
                dropOffServicePackagingTypes.append("FIRST_OVERNIGHT");
            } else if (service.equals("GROUNDHOMEDELIVERY")) {
                dropOffServicePackagingTypes.append("GROUND_HOME_DELIVERY");
            }
            else if (service.equals("SMARTPOST")) {
                            dropOffServicePackagingTypes.append("SMART_POST");
                        }
            else if (service.equals("FEDEX_2_DAY_AM")) {
                dropOffServicePackagingTypes.append("FEDEX_2_DAY_AM");   // Added by Jagadish
            }
            else if (service.equals("FEDEX_FIRST_FREIGHT")) {
                dropOffServicePackagingTypes.append("FEDEX_FIRST_FREIGHT");   // Added by Jagadish
            }
            dropOffServicePackagingTypes.append("</ServiceType>");


            dropOffServicePackagingTypes.append("<PackagingType>");
            if (packaging.equals("YOURPACKAGING")) {
                dropOffServicePackagingTypes.append("YOUR_PACKAGING");
            } else if (packaging.equals("FEDEXBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_BOX");
            } else if (packaging.equals("FEDEXENVELOPE")) {
                dropOffServicePackagingTypes.append("FEDEX_ENVELOPE");
            } else if (packaging.equals("FEDEXPAK")) {
                dropOffServicePackagingTypes.append("FEDEX_PAK");
            } else if (packaging.equals("FEDEXTUBE")) {
                dropOffServicePackagingTypes.append("FEDEX_TUBE");
            }
            dropOffServicePackagingTypes.append("</PackagingType>\n");

        }

        return dropOffServicePackagingTypes;
    }

    private StringBuffer createTotalWeight(String pkgWtUom, double pkgWtVal) {
        StringBuffer totalWeight = new StringBuffer("");
        totalWeight.append("<TotalWeight>");
        totalWeight.append("<Units>");
        if (pkgWtUom.equals("LBS")) {
            totalWeight.append("LB");
        } else if (pkgWtUom.equals("KGS")) {
            totalWeight.append("KG");
        }
        totalWeight.append("</Units>");
        totalWeight.append("<Value>");
        totalWeight.append(pkgWtVal);
        totalWeight.append("</Value>");
        totalWeight.append("</TotalWeight>\n");

        return totalWeight;
    }

    private StringBuffer createShipperRecipientAddress(String fedExWSChkReturnlabelstr, 
                                                       String service, 
                                                       String residentialAddrFlag, 
                                                       String intFlag, 
                                                       String intSenderTINOrDUNS, 
                                                       String intSenderTINOrDUNSType,
                                                       String shipFromPersonName, 
                                                       String shipFromCompanyName, 
                                                       String shipFromPhoneNumber1,
                                                       String shipFromEMailAddress,
                                                       String shipFromAddressLine1, 
                                                       String shipFromAddressLine2, 
                                                       String shipFromAddressCity, 
                                                       String shipFromAddressState, 
                                                       String shipFromAddressPostalCode, 
                                                       String shipFromCountry, 
                                                       String shipToContactPersonName, 
                                                       String shipToCompanyName, 
                                                       String shipToContactPhoneNumber, 
                                                       String shipToAddressLine1, 
                                                       String shipToAddressLine2, 
                                                       String shipToAddressCity, 
                                                       String shipToAddressState, 
                                                       String shipToAddressPostalCode, 
                                                       String shipToCountry, 
                                                       String shipToEMailAddress,
                                                       String rtnShipFromContact, 
                                                       String rtnShipFromCompany, 
                                                       String rtnShipFromPhone, 
                                                       String rtnShipFromLine1, 
                                                       String rtnShipFromLine2, 
                                                       String rtnShipFromCity, 
                                                       String rtnShipFromSate, 
                                                       String rtnShipFromZip, 
                                                       String rtnShipToContact, 
                                                       String rtnShipToCompany, 
                                                       String rtnShipToPhone, 
                                                       String rtnShipToLine1, 
                                                       String rtnShipToLine2, 
                                                       String rtnShipToCity, 
                                                       String rtnShipToState, 
                                                       String rtnShipToZip,
                                                       String rtnIntlSedNumber,
                                                       String rtnIntlSedType) {
        StringBuffer shipperRecipientAddress = new StringBuffer("");

        if ((fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
            shipperRecipientAddress.append("<Shipper>");
            shipperRecipientAddress.append("<AccountNumber></AccountNumber>");
            if (intFlag.equalsIgnoreCase("Y")) {
                if (!(intSenderTINOrDUNS.equalsIgnoreCase("") && 
                      intSenderTINOrDUNS.equalsIgnoreCase(null))) {
                    shipperRecipientAddress.append("<Tins>");
                    //SC_6.7.1_Merge
                   // shipperRecipientAddress.append("<TinType>BUSINESS_NATIONAL</TinType>");
                   shipperRecipientAddress.append("<TinType>"+intSenderTINOrDUNSType+"</TinType>");
                    shipperRecipientAddress.append("<Number>" + 
                                                   intSenderTINOrDUNS + 
                                                   "</Number>");
                    shipperRecipientAddress.append("</Tins>");
                }
            }
            shipperRecipientAddress.append("<Contact>");
            shipperRecipientAddress.append("<PersonName>" + 
                                           shipFromPersonName + 
                                           "</PersonName>");
            shipperRecipientAddress.append("<CompanyName>" + 
                                           shipFromCompanyName + 
                                           "</CompanyName>");
            shipperRecipientAddress.append("<PhoneNumber>" + 
                                           shipFromPhoneNumber1 + 
                                           "</PhoneNumber>");
            shipperRecipientAddress.append("<EMailAddress>" + shipFromEMailAddress + 
                                           "</EMailAddress>");
            shipperRecipientAddress.append("</Contact>");
            shipperRecipientAddress.append("<Address>");
            shipperRecipientAddress.append("<StreetLines>" + 
                                           shipFromAddressLine1 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<StreetLines>" + 
                                           shipFromAddressLine2 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<City>" + shipFromAddressCity + 
                                           "</City>");
            shipperRecipientAddress.append("<StateOrProvinceCode>" + 
                                           shipFromAddressState + 
                                           "</StateOrProvinceCode>");
            shipperRecipientAddress.append("<PostalCode>" + 
                                           shipFromAddressPostalCode + 
                                           "</PostalCode>");
            shipperRecipientAddress.append("<CountryCode>" + shipFromCountry + 
                                           "</CountryCode>");
            shipperRecipientAddress.append("</Address>");
            shipperRecipientAddress.append("</Shipper>");

            shipperRecipientAddress.append("<Recipient>");
            shipperRecipientAddress.append("<AccountNumber></AccountNumber>");
            //SC_6.7.1_Merge
             if (intFlag.equalsIgnoreCase("Y")) {
                if (!(rtnIntlSedNumber.equalsIgnoreCase("") && 
                      rtnIntlSedNumber.equalsIgnoreCase(null))) {
                    shipperRecipientAddress.append("<Tins>");
                    shipperRecipientAddress.append("<TinType>"+rtnIntlSedType+"</TinType>");
                    shipperRecipientAddress.append("<Number>" + 
                                                   rtnIntlSedNumber + 
                                                   "</Number>");
                    shipperRecipientAddress.append("</Tins>");
                }
            }
            //end SC_6.7.1_Merge
            shipperRecipientAddress.append("<Contact>");
            shipperRecipientAddress.append("<PersonName>" + 
                                           shipToContactPersonName + 
                                           "</PersonName>");
            shipperRecipientAddress.append("<CompanyName>" + 
                                           shipToCompanyName + 
                                           "</CompanyName>");
            shipperRecipientAddress.append("<PhoneNumber>" + 
                                           shipToContactPhoneNumber + 
                                           "</PhoneNumber>");
            shipperRecipientAddress.append("<EMailAddress>" + shipToEMailAddress + 
                                           "</EMailAddress>");
            shipperRecipientAddress.append("</Contact>");
            shipperRecipientAddress.append("<Address>");
            shipperRecipientAddress.append("<StreetLines>" + 
                                           shipToAddressLine1 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<StreetLines>" + 
                                           shipToAddressLine2 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<City>" + shipToAddressCity + 
                                           "</City>");
            shipperRecipientAddress.append("<StateOrProvinceCode>" + 
                                           shipToAddressState + 
                                           "</StateOrProvinceCode>");
            shipperRecipientAddress.append("<PostalCode>" + 
                                           shipToAddressPostalCode + 
                                           "</PostalCode>");
            shipperRecipientAddress.append("<CountryCode>" + shipToCountry + 
                                           "</CountryCode>");
            if ("Y".equalsIgnoreCase(residentialAddrFlag) || 
                (service.equals("GROUNDHOMEDELIVERY"))) {
                shipperRecipientAddress.append("<Residential>" + 
                                               Boolean.valueOf(true) + 
                                               "</Residential>");
            } else {
                shipperRecipientAddress.append("<Residential>" + 
                                               Boolean.valueOf(false) + 
                                               "</Residential>");
            }
            shipperRecipientAddress.append("</Address>");
            
            shipperRecipientAddress.append("</Recipient>");
        } else {

            shipperRecipientAddress.append("<Shipper>");
            shipperRecipientAddress.append("<AccountNumber></AccountNumber>");
            shipperRecipientAddress.append("<Contact>");
            shipperRecipientAddress.append("<PersonName>" + 
                                           rtnShipFromContact + 
                                           "</PersonName>");
            shipperRecipientAddress.append("<CompanyName>" + 
                                           rtnShipFromCompany + 
                                           "</CompanyName>");
            shipperRecipientAddress.append("<PhoneNumber>" + rtnShipFromPhone + 
                                           "</PhoneNumber>");
            shipperRecipientAddress.append("</Contact>");
            shipperRecipientAddress.append("<Address>");
            shipperRecipientAddress.append("<StreetLines>" + rtnShipFromLine1 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<StreetLines>" + rtnShipFromLine2 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<City>" + rtnShipFromCity + 
                                           "</City>");
            shipperRecipientAddress.append("<StateOrProvinceCode>" + 
                                           rtnShipFromSate + 
                                           "</StateOrProvinceCode>");
            shipperRecipientAddress.append("<PostalCode>" + rtnShipFromZip + 
                                           "</PostalCode>");
            shipperRecipientAddress.append("<CountryCode>" + shipToCountry + 
                                           "</CountryCode>");
            shipperRecipientAddress.append("</Address>");
            shipperRecipientAddress.append("</Shipper>");

            shipperRecipientAddress.append("<Recipient>");
            shipperRecipientAddress.append("<AccountNumber></AccountNumber>");
            shipperRecipientAddress.append("<Contact>");
            shipperRecipientAddress.append("<PersonName>" + rtnShipToContact + 
                                           "</PersonName>");
            shipperRecipientAddress.append("<CompanyName>" + rtnShipToCompany + 
                                           "</CompanyName>");
            shipperRecipientAddress.append("<PhoneNumber>" + rtnShipToPhone + 
                                           "</PhoneNumber>");
            shipperRecipientAddress.append("</Contact>");
            shipperRecipientAddress.append("<Address>");
            shipperRecipientAddress.append("<StreetLines>" + rtnShipToLine1 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<StreetLines>" + rtnShipToLine2 + 
                                           "</StreetLines>");
            shipperRecipientAddress.append("<City>" + rtnShipToCity + 
                                           "</City>");
            shipperRecipientAddress.append("<StateOrProvinceCode>" + 
                                           rtnShipToState + 
                                           "</StateOrProvinceCode>");
            shipperRecipientAddress.append("<PostalCode>" + rtnShipToZip + 
                                           "</PostalCode>");
            shipperRecipientAddress.append("<CountryCode>" + shipFromCountry + 
                                           "</CountryCode>");
            shipperRecipientAddress.append("</Address>");
            shipperRecipientAddress.append("</Recipient>");
        }
        shipperRecipientAddress.append("\n");

        return shipperRecipientAddress;
    }

    private StringBuffer createShippingChargesPayment(String fedExWSChkReturnlabelstr, 
                                                      String rtnPayMethod, 
                                                      String shipFromCountry, 
                                                      String payorCountryCodeWS, 
                                                      String customerCarrierAccountNumber, 
                                                      String carrierPayMethod,
                                                      String receipientPartyName,
                                                      String recipientPostalCode,
                                                      String tpCompanyName,                                             //Mahesh added code for Third Party development 
                                                      String tpAddress,
                                                      String tpCity,
                                                      String tpState,
                                                      String tpPostalCode,
                                                      String tpCountrySymbol) {
        StringBuffer shippingChargesPayment = new StringBuffer("");
        // //System.out.println("customerCarrierAccountNumber at line 938  :: "+customerCarrierAccountNumber);
        shippingChargesPayment.append("<ShippingChargesPayment>");
        if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
            if (rtnPayMethod.equals("SENDER")) {
                shippingChargesPayment.append("<PaymentType>SENDER</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
                shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12                                
                shippingChargesPayment.append("<CountryCode>" + 
                                              shipFromCountry + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>"); 
                shippingChargesPayment.append("</Payor>");
            } else if (rtnPayMethod.equals("RECIPIENT")) {
                shippingChargesPayment.append("<PaymentType>RECIPIENT</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
                shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12                                                              
                shippingChargesPayment.append("<CountryCode>" + 
                                              shipFromCountry + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>");                               
                shippingChargesPayment.append("</Payor>");
            } else if (rtnPayMethod.equals("COLLECT")) {
                shippingChargesPayment.append("<PaymentType>COLLECT</PaymentType>");
                if (!(customerCarrierAccountNumber.equalsIgnoreCase("") || 
                      customerCarrierAccountNumber.equalsIgnoreCase(null))) {
                    shippingChargesPayment.append("<Payor>");
                    shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                    shippingChargesPayment.append("<AccountNumber>" + 
                                                  customerCarrierAccountNumber + 
                                                  "</AccountNumber>");
                    shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                    shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12   
                    shippingChargesPayment.append("<CountryCode>" + 
                                                  shipFromCountry + 
                                                  "</CountryCode>");
                    shippingChargesPayment.append(" </Address>");                               
                    shippingChargesPayment.append("</ResponsibleParty>");    
                    shippingChargesPayment.append("</Payor>");
                }
            } else if (rtnPayMethod.equals("THIRDPARTY")) {
                shippingChargesPayment.append("<PaymentType>THIRD_PARTY</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
                shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12   
                shippingChargesPayment.append("<CountryCode>" + 
                                              payorCountryCodeWS + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>");    
                shippingChargesPayment.append("</Payor>");
            }

        } else {
            if (carrierPayMethod.equals("SENDER")) {
                shippingChargesPayment.append("<PaymentType>SENDER</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
                shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12   
                shippingChargesPayment.append("<CountryCode>" + 
                                              shipFromCountry + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>");   
                shippingChargesPayment.append("</Payor>");
            } else if (carrierPayMethod.equals("RECIPIENT")) {
                shippingChargesPayment.append("<PaymentType>RECIPIENT</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
                shippingChargesPayment.append("<Contact>");            // By Mahesh receipientPartyName.
                 shippingChargesPayment.append("<CompanyName>"+
                                         receipientPartyName+
                                         "</CompanyName>");  
                 shippingChargesPayment.append("</Contact>");    
                shippingChargesPayment.append(" <Address>");            // By Mahesh recipientPostalCode. 
                 shippingChargesPayment.append("<PostalCode>" + 
                                               recipientPostalCode + 
                                               "</PostalCode>");
                shippingChargesPayment.append("<CountryCode>" + 
                                              shipFromCountry + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>");
                shippingChargesPayment.append("</Payor>");
            } else if (carrierPayMethod.equals("THIRDPARTY")) {
                shippingChargesPayment.append("<PaymentType>THIRD_PARTY</PaymentType>");
                shippingChargesPayment.append("<Payor>");
                shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                shippingChargesPayment.append("<AccountNumber>" + 
                                              customerCarrierAccountNumber + 
                                              "</AccountNumber>");
//                shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
//                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12 
//Mahesh below added code for Third party development
                  shippingChargesPayment.append("<Contact>");
                  shippingChargesPayment.append("<CompanyName>"+
                                              tpCompanyName +
                                              "</CompanyName>");
                shippingChargesPayment.append("</Contact>");    
                shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12  
                shippingChargesPayment.append("<StreetLines>"+tpAddress+"</StreetLines>");
                shippingChargesPayment.append("<City>"+tpCity+"</City>");
                shippingChargesPayment.append("<StateOrProvinceCode>"+tpState+"</StateOrProvinceCode>");
                shippingChargesPayment.append("<PostalCode>"+tpPostalCode+"</PostalCode>");
                shippingChargesPayment.append("<CountryCode>" + 
                                               tpCountrySymbol +                              // payorCountryCodeWS + 
                                              "</CountryCode>");
                shippingChargesPayment.append(" </Address>");                               
                shippingChargesPayment.append("</ResponsibleParty>");
                shippingChargesPayment.append("</Payor>");
            } else if (carrierPayMethod.equals("COLLECT")) {
                shippingChargesPayment.append("<PaymentType>COLLECT</PaymentType>");
                //  //System.out.println("customerCarrierAccountNumber  :: "+customerCarrierAccountNumber);
                if (!(customerCarrierAccountNumber.equalsIgnoreCase("") || 
                      customerCarrierAccountNumber.equalsIgnoreCase(null))) {
                    shippingChargesPayment.append("<Payor>");
                    shippingChargesPayment.append("<ResponsibleParty>");    // By Jagadish. This is a mandatory tag for v12
                    shippingChargesPayment.append("<AccountNumber>" + 
                                                  customerCarrierAccountNumber + 
                                                  "</AccountNumber>");
                    shippingChargesPayment.append("<Contact/>");            // By Jagadish. This is a mandatory tag for v12                                
                    shippingChargesPayment.append(" <Address>");            // By Jagadish. This is a mandatory tag for v12  
                    shippingChargesPayment.append("<CountryCode>" + 
                                                  shipFromCountry + 
                                                  "</CountryCode>");
                    shippingChargesPayment.append(" </Address>");                               
                    shippingChargesPayment.append("</ResponsibleParty>");
                    shippingChargesPayment.append("</Payor>");
                }
            }
        }

        shippingChargesPayment.append("</ShippingChargesPayment>\n");

        return shippingChargesPayment;
    }


//added for SmartPost
 private StringBuffer createSmartPostDetailsNotRtn(String indicia, 
                                                   String ancillary, 
                                                   String hubid
                                                  ) {
            StringBuffer smartPostDetailsBuffer = new StringBuffer("");  
            if(indicia.length()==0 || indicia==null){
                                indicia="PARCEL_SELECT";                   
                               }
                          
                          
                          smartPostDetailsBuffer.append("<SmartPostDetail>");  
                          smartPostDetailsBuffer.append("<Indicia>"+indicia+"</Indicia>");
                          if(ancillary.length()!=0)
                          {
                          smartPostDetailsBuffer.append("<AncillaryEndorsement>"+ancillary+"</AncillaryEndorsement>"); 
                          }

                          try{
                          smartPostDetailsBuffer.append("<HubId>"+hubid.substring(0,4)+"</HubId>");
                          }catch(Exception e){
                              logger.info("Got Exception");
                          }
                          smartPostDetailsBuffer.append("</SmartPostDetail>");
            
            return smartPostDetailsBuffer;
                                                  
        }
        
    private StringBuffer createSmartPostDetailsRtn(String indicia, 
                                                      String ancillary, 
                                                      String hubid
                                                     ) {
               StringBuffer smartPostDetailsBuffer = new StringBuffer("");  
              

                           smartPostDetailsBuffer.append("<SmartPostDetail>");  
                           smartPostDetailsBuffer.append("<Indicia>PARCEL_RETURN</Indicia>");
                               if(ancillary.length()!=0)
                               {
                           smartPostDetailsBuffer.append("<AncillaryEndorsement>"+ancillary+"</AncillaryEndorsement>"); 
                               }
                           smartPostDetailsBuffer.append("<HubId>"+hubid.substring(0,4)+"</HubId>");
                           smartPostDetailsBuffer.append("</SmartPostDetail>");

               
               return smartPostDetailsBuffer;
                                                     
           }       
        
//end for SmartPost

    private StringBuffer createLabelSpecification(String labelFormat, 
                                                  String labelStockOrientation, 
                                                  String docTabLocation,String customerTransactionIdentifier,String OrderNumber,
                                                  String reference1,String reference2,String referenceValue1,String referenceValue2) {
        StringBuffer labelSpecification = new StringBuffer("");
        labelSpecification.append("<LabelSpecification>");
        labelSpecification.append("<LabelFormatType>COMMON2D</LabelFormatType>");
        if (labelFormat.equalsIgnoreCase("ZEBRA") || 
            labelFormat.equalsIgnoreCase("ELTRON") || 
            labelFormat.equalsIgnoreCase("UNIMARK")) {
            if (labelFormat.equalsIgnoreCase("ZEBRA")) {
                labelSpecification.append("<ImageType>ZPLII</ImageType>");
            } else if (labelFormat.equalsIgnoreCase("ELTRON")) {
                labelSpecification.append("<ImageType>EPL2</ImageType>");
            } else if (labelFormat.equalsIgnoreCase("UNIMARK")) {
                labelSpecification.append("<ImageType>DPL</ImageType>");
            }
            
            labelSpecification.append("<LabelStockType>");
            labelSpecification.append(labelStockOrientation);
            labelSpecification.append("</LabelStockType>");

//commented below code to send <LabelStockType> directly from Carrier Configuration

//            if (labelStockOrientation.equalsIgnoreCase("LEADING")) {
//                labelSpecification.append("<LabelStockType>STOCK_4X6.75_LEADING_DOC_TAB</LabelStockType>");
//            } else if (labelStockOrientation.equalsIgnoreCase("TRAILING")) {
//                labelSpecification.append("<LabelStockType>STOCK_4X6.75_TRAILING_DOC_TAB</LabelStockType>");
//            } else {
//                labelSpecification.append("<LabelStockType>STOCK_4X6</LabelStockType>");
//            }


            if (docTabLocation.equalsIgnoreCase("TOP")) {
                labelSpecification.append("<LabelPrintingOrientation>TOP_EDGE_OF_TEXT_FIRST</LabelPrintingOrientation>");
            } else if (docTabLocation.equalsIgnoreCase("BOTTOM")) {
                labelSpecification.append("<LabelPrintingOrientation>BOTTOM_EDGE_OF_TEXT_FIRST</LabelPrintingOrientation>");
            }


            labelSpecification.append("<CustomerSpecifiedDetail>");
            labelSpecification.append("<DocTabContent>");
            labelSpecification.append("<DocTabContentType>ZONE001</DocTabContentType>");
            labelSpecification.append("<Zone001>");
          //  String mytext="786786";
            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>1</ZoneNumber>");
            labelSpecification.append("<Header>Order#</Header>");
           // labelSpecification.append("<DataField>REQUEST/PACKAGE/CustomerReferences[2]/Value</DataField>");
            labelSpecification.append("<LiteralValue>");
            labelSpecification.append(OrderNumber);
            labelSpecification.append("</LiteralValue>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>2</ZoneNumber>");
            labelSpecification.append("<Header>Delivery</Header>");
            //labelSpecification.append("<DataField>REQUEST/PACKAGE/CustomerReferences[1]/Value</DataField>");
             labelSpecification.append("<LiteralValue>");
            labelSpecification.append(customerTransactionIdentifier);
            labelSpecification.append("</LiteralValue>");
            labelSpecification.append("</DocTabZoneSpecifications>");
            
            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>3</ZoneNumber>");
            labelSpecification.append("<Header>Dept</Header>");
            labelSpecification.append("<DataField>REQUEST/PACKAGE/CustomerReferences[1]/Value</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>4</ZoneNumber>");
            labelSpecification.append("<Header>Ref</Header>");
           // labelSpecification.append("<DataField>REQUEST/PACKAGE/CustomerReferences[2]/Value</DataField>");
             labelSpecification.append("<LiteralValue>");
            if (referenceValue1.equalsIgnoreCase("PURCHASE ORDER NUMBER")||referenceValue2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) 
            {
           if(referenceValue1.equalsIgnoreCase("PURCHASE ORDER NUMBER"))
           {
               labelSpecification.append(reference1);
           }
           else{
               labelSpecification.append(reference2);
           }
            }
            else{
            labelSpecification.append("");
            }
            
            labelSpecification.append("</LiteralValue>");
            labelSpecification.append("</DocTabZoneSpecifications>");


            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>5</ZoneNumber>");
            labelSpecification.append("<Header>Weight</Header>");
            labelSpecification.append("<DataField>REQUEST/PACKAGE/Weight/Value</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>6</ZoneNumber>");
            labelSpecification.append("<Header>Date</Header>");
            labelSpecification.append("<DataField>REQUEST/SHIPMENT/ShipTimestamp</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>7</ZoneNumber>");
            labelSpecification.append("<Header>Shipping</Header>");
            labelSpecification.append("<DataField>REPLY/PACKAGE/RATES/PAYOR_ACCOUNT_PACKAGE/BaseCharge/Amount</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>8</ZoneNumber>");
            labelSpecification.append("<Header>Special</Header>");
            labelSpecification.append("<DataField>REPLY/PACKAGE/RATES/PAYOR_ACCOUNT_PACKAGE/TotalSurcharges/Amount</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>9</ZoneNumber>");
            labelSpecification.append("<Header>Discount</Header>");
            labelSpecification.append("<DataField>REPLY/PACKAGE/RATES/PAYOR_ACCOUNT_PACKAGE/TotalFreightDiscounts/Amount</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("<DocTabZoneSpecifications>");
            labelSpecification.append("<ZoneNumber>10</ZoneNumber>");
            labelSpecification.append("<Header>Total</Header>");
            labelSpecification.append("<DataField>REPLY/PACKAGE/RATES/PAYOR_ACCOUNT_PACKAGE/NetCharge/Amount</DataField>");
            labelSpecification.append("</DocTabZoneSpecifications>");

            labelSpecification.append("</Zone001>");
            labelSpecification.append("</DocTabContent>");
            labelSpecification.append("</CustomerSpecifiedDetail>");

        } else if (labelFormat.equalsIgnoreCase("PDF")) {
            labelSpecification.append("<ImageType>PDF</ImageType>");
        } else if (labelFormat.equalsIgnoreCase("PNG")) {
            labelSpecification.append("<ImageType>PNG</ImageType>");
        }
        else
            labelSpecification.append("<ImageType>PDF</ImageType>");
        // Need to remove else from suman gunda

        labelSpecification.append("</LabelSpecification>\n");

        return labelSpecification;
    }


    private StringBuffer createRateRequestTypes() {
        StringBuffer rateRequestTypes = new StringBuffer();
        rateRequestTypes.append("<RateRequestTypes>LIST</RateRequestTypes>\n");
        return rateRequestTypes;
    }

    // added by Jagadish for intldocsubtype
    private StringBuffer createEtdRequestType(){
        StringBuffer EDTRequestTypes = new StringBuffer();
        EDTRequestTypes.append("<EdtRequestType>ALL</EdtRequestType>\n");

        return EDTRequestTypes;
    }
    private StringBuffer createPackageCount(String pkgCount, 
                                            int sequenceNumberWS, 
                                            String fedExWSChkReturnlabelstr) {
        StringBuffer packageCount = new StringBuffer("");
        packageCount.append("<PackageCount>");
        if (!fedExWSChkReturnlabelstr.equalsIgnoreCase("NONRETURN")) {
            packageCount.append(sequenceNumberWS);
        } else {
            packageCount.append(pkgCount);
        }
        packageCount.append("</PackageCount>\n");

        return packageCount;
    }

    private StringBuffer createPackageDetail() {
        StringBuffer packageDetail = new StringBuffer("");
       // packageDetail.append("<PackageDetail>INDIVIDUAL_PACKAGES</PackageDetail>\n"); // Commented by Jagadish. This tag is not supported in v12
        return packageDetail;
    }

    private StringBuffer createSequenceNumber(int sequenceNumberWS) {
        StringBuffer sequenceNumber = new StringBuffer("");
        sequenceNumber.append("<SequenceNumber>");
        sequenceNumber.append(sequenceNumberWS);
        sequenceNumber.append("</SequenceNumber>\n");

        return sequenceNumber;
    }

    private StringBuffer createWeight(String pkgWtUom, double pkgWtVal) {
        StringBuffer weight = new StringBuffer("");
        weight.append("<Weight>");
        weight.append("<Units>");
        if (pkgWtUom.equals("LBS")) {
            weight.append("LB");
        } else if (pkgWtUom.equals("KGS")) {
            weight.append("KG");
        }
        weight.append("</Units>");
        weight.append("<Value>");
        weight.append(pkgWtVal);
        weight.append("</Value>");
        weight.append("</Weight>\n");

        return weight;
    }

    private StringBuffer createDimensions(String packageLength, 
                                          String packageWidth, 
                                          String packageHeight, 
                                          String dimunits) {
        StringBuffer dimensions = new StringBuffer("");
        dimensions.append("<Dimensions>");
        dimensions.append("<Length>");
        dimensions.append((int)Double.parseDouble(packageLength));
        dimensions.append("</Length>");
        dimensions.append("<Width>");
        dimensions.append((int)Double.parseDouble(packageWidth));
        dimensions.append("</Width>");
        dimensions.append("<Height>");
        dimensions.append((int)Double.parseDouble(packageHeight));
        dimensions.append("</Height>");
        dimensions.append("<Units>");
        dimensions.append(dimunits);
        dimensions.append("</Units>");
        dimensions.append("</Dimensions>\n");

        return dimensions;
    }

    private StringBuffer createCod(double codAmtStr) {
        StringBuffer cod = new StringBuffer("");
        cod.append("<CodDetail>");
        cod.append("<CodCollectionAmount>");
        // cod.append("<Money>");
        cod.append("<Currency>");
        cod.append("USD");
        cod.append("</Currency>");
        cod.append("<Amount>");
        cod.append(codAmtStr);
        cod.append("</Amount>");
        // cod.append("</Money>");
        cod.append("</CodCollectionAmount>");
        cod.append("<CollectionType>ANY</CollectionType>");
        cod.append("</CodDetail>\n");
        return cod;
    }

    private StringBuffer createHoldAtLocation(String halPhone, String halLine1, 
                                              String halLine2, String halCity, 
                                              String halState, String halZip, 
                                              String shipToCountry) {
        StringBuffer holdAtLocation = new StringBuffer("");

        holdAtLocation.append("<HoldAtLocationDetail>");
        holdAtLocation.append("<PhoneNumber>");
        holdAtLocation.append(halPhone);
        holdAtLocation.append("</PhoneNumber>");
        holdAtLocation.append("<LocationContactAndAddress>");
        holdAtLocation.append("<Contact>");
        //holdAtLocation.append("<PersonName>Recipient Contact</PersonName>");
        holdAtLocation.append("<PhoneNumber>" + halPhone + "</PhoneNumber>");
        holdAtLocation.append("</Contact>");
        holdAtLocation.append("<Address>");
        holdAtLocation.append("<StreetLines>");
        holdAtLocation.append(halLine1);
        holdAtLocation.append("</StreetLines>");
        holdAtLocation.append("<StreetLines>");
        holdAtLocation.append(halLine2);
        holdAtLocation.append("</StreetLines>");
        holdAtLocation.append("<City>");
        holdAtLocation.append(halCity);
        holdAtLocation.append("</City>");
        holdAtLocation.append("<StateOrProvinceCode>");
        holdAtLocation.append(halState);
        holdAtLocation.append("</StateOrProvinceCode>");
        holdAtLocation.append("<PostalCode>");
        holdAtLocation.append(halZip);
        holdAtLocation.append("</PostalCode>");
        holdAtLocation.append("<CountryCode>");
        holdAtLocation.append(shipToCountry);
        holdAtLocation.append("</CountryCode>");
        holdAtLocation.append("</Address>");
        holdAtLocation.append("</LocationContactAndAddress>");
        holdAtLocation.append("</HoldAtLocationDetail>\n");
        return holdAtLocation;
    }

    private StringBuffer createEmailNotification(String shipAlertNotification, 
                                                 String exceptionNotification, 
                                                 String deliveryNotification, 
                                                 String languageCode, 
                                                 String recipientEmailAddress1, 
                                                 String recipientEmailAddress2, 
                                                 String recipientEmailAddress3, 
                                                 String recipientEmailAddress4, 
                                                 String recipientEmailAddress5, 
                                                 String senderEmail, 
                                                 String optionalMessage) {
        StringBuffer emailNotification = new StringBuffer("");
        emailNotification.append("<EMailNotificationDetail>");
        emailNotification.append("<PersonalMessage>");
        emailNotification.append(optionalMessage);
        emailNotification.append("</PersonalMessage>");
        if (!recipientEmailAddress1.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(recipientEmailAddress1, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "RECIPIENT", 
                                                     languageCode));
        }
        if (!recipientEmailAddress2.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(recipientEmailAddress2, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "RECIPIENT", 
                                                     languageCode));
        }
        if (!recipientEmailAddress3.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(recipientEmailAddress3, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "RECIPIENT", 
                                                     languageCode));
        }
        if (!recipientEmailAddress4.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(recipientEmailAddress4, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "RECIPIENT", 
                                                     languageCode));
        }
        if (!recipientEmailAddress5.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(recipientEmailAddress5, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "RECIPIENT", 
                                                     languageCode));
        }
        if (!senderEmail.equalsIgnoreCase("")) {
            emailNotification.append(createRecipient(senderEmail, 
                                                     shipAlertNotification, 
                                                     exceptionNotification, 
                                                     deliveryNotification, 
                                                     "SHIPPER", languageCode));
        }
        emailNotification.append("</EMailNotificationDetail>\n");
        return emailNotification;
    }

    private StringBuffer createRecipient(String recipientEmailAddress, 
                                         String shipAlertNotification, 
                                         String exceptionNotification, 
                                         String deliveryNotification, 
                                         String notificationRecipientType, 
                                         String languageCode) {
        StringBuffer recipient = new StringBuffer("");
        recipient.append("<Recipients>");
        recipient.append("<EMailNotificationRecipientType>");
        recipient.append(notificationRecipientType);
        recipient.append("</EMailNotificationRecipientType>");
        recipient.append("<EMailAddress>");
        recipient.append(recipientEmailAddress);
        recipient.append("</EMailAddress>");

//        recipient.append("<NotifyOnShipment>");                       //This block is commented by Jagadish. These tags are outdated that were present in v9. v12 dont support them.
//        if (shipAlertNotification.equalsIgnoreCase("Y")) {
//            recipient.append(Boolean.valueOf(true));
//        } else {
//            recipient.append(Boolean.valueOf(false));
//        }
//        recipient.append("</NotifyOnShipment>");
//
//        recipient.append("<NotifyOnException>");
//        if (exceptionNotification.equalsIgnoreCase("Y")) {
//            recipient.append("true");
//        } else {
//           recipient.append("false");
//       }
//       recipient.append("</NotifyOnException>");
//
//      recipient.append("<NotifyOnDelivery>");
//    if (deliveryNotification.equalsIgnoreCase("Y")) {
//            recipient.append("true");
//        } else {
//            recipient.append("false");
//      }
//        recipient.append("</NotifyOnDelivery>");
//Below code added by Jagadish. New tags that were introduced in v12.
 if (shipAlertNotification.equalsIgnoreCase("Y")) {  
recipient.append("<NotificationEventsRequested>");
recipient.append("ON_SHIPMENT");
recipient.append("</NotificationEventsRequested>");
 }
 if (exceptionNotification.equalsIgnoreCase("Y")) {
     recipient.append("<NotificationEventsRequested>");
     recipient.append("ON_EXCEPTION");
     recipient.append("</NotificationEventsRequested>");
 }
 if (deliveryNotification.equalsIgnoreCase("Y")) {
     recipient.append("<NotificationEventsRequested>");
     recipient.append("ON_DELIVERY");
     recipient.append("</NotificationEventsRequested>");
 }
//end
        recipient.append("<Format>TEXT</Format>");
        recipient.append("<Localization><LanguageCode>");
        recipient.append(languageCode);
        recipient.append("</LanguageCode></Localization>");
        recipient.append("</Recipients>");

        return recipient;
    }

    private StringBuffer createReturnShipment(String rtnRMA) {
        StringBuffer returnShipment = new StringBuffer("");
        returnShipment.append("<ReturnShipmentDetail>");
        returnShipment.append("<ReturnType>");
        returnShipment.append("PRINT_RETURN_LABEL");
        returnShipment.append("</ReturnType>");
        returnShipment.append("<Rma>");
       // returnShipment.append("<Number>"); // By Jagadish. This tag is not supported in v12
        returnShipment.append("<Reason>");    // By Jagadish. New tag supported in v12
        returnShipment.append(rtnRMA);
        //returnShipment.append("</Number>");
         returnShipment.append("</Reason>");
        returnShipment.append("</Rma>");
        returnShipment.append("</ReturnShipmentDetail>");
        // returnShipment.append("</SpecialServiceTypes>\n");
        return returnShipment;
    }

    private StringBuffer createCustomerReferences(String referenceValue1, 
                                                  String reference1, 
                                                  String referenceValue2, 
                                                  String reference2, 
                                                  String shipFromDepartment,String lpnNumber,String OrderNumber) {
        StringBuffer customerReferences = new StringBuffer("");
        if (shipFromDepartment.equalsIgnoreCase("") || 
            shipFromDepartment == null) {
            customerReferences.append("");
        } else {
            customerReferences.append("<CustomerReferences>");
            customerReferences.append("<CustomerReferenceType>");
            customerReferences.append("DEPARTMENT_NUMBER");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append(shipFromDepartment);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
        }
        logger.info("referenceValue1::::::::"+referenceValue1+"\n referenceValue2:::::::"+referenceValue2);
        logger.info("reference1:::::::"+reference1+"\n reference2:::::::::::"+reference2+"\n lpnNumber:::::::::::"+lpnNumber);
        if (reference1.equalsIgnoreCase("") && 
            (reference2.equalsIgnoreCase("") && lpnNumber.equalsIgnoreCase(""))) {
            customerReferences.append("");
        } else {

            //  customerReferences.append("<CustomerReferences>");
            if (!reference1.equalsIgnoreCase("") && 
                (reference2.equalsIgnoreCase(""))) {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                if (referenceValue1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                    customerReferences.append("P_O_NUMBER");
                
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
              
              // Added by Suman Gunda  
              if(!lpnNumber.equalsIgnoreCase("")){
                if(referenceValue2.equalsIgnoreCase("LPN NUMBER")) {
                    customerReferences.append("<CustomerReferences>");
                     customerReferences.append("<CustomerReferenceType>");
                     customerReferences.append("CUSTOMER_REFERENCE");
                     customerReferences.append("</CustomerReferenceType>");
                     customerReferences.append("<Value>");
                     customerReferences.append("lpn# "+lpnNumber);
                     customerReferences.append("</Value>");
                     customerReferences.append("</CustomerReferences>\n");
                 }
                else if(referenceValue2.equalsIgnoreCase("Order Number/LPN Number")) {
                    customerReferences.append("<CustomerReferences>");
                     customerReferences.append("<CustomerReferenceType>");
                     customerReferences.append("CUSTOMER_REFERENCE");
                     customerReferences.append("</CustomerReferenceType>");
                     customerReferences.append("<Value>");
                     customerReferences.append(OrderNumber+"/"+lpnNumber);
                     customerReferences.append("</Value>");
                     customerReferences.append("</CustomerReferences>\n");
                 }
              }
                } else {
                    customerReferences.append("CUSTOMER_REFERENCE");
                    customerReferences.append("</CustomerReferenceType>");
                    customerReferences.append("<Value>");
                    if(!lpnNumber.equalsIgnoreCase("")){
                      if(referenceValue2.equalsIgnoreCase("LPN NUMBER")) {
                           reference1=reference1+" "+"lpn# "+lpnNumber;
                       }
                      else if(referenceValue2.equalsIgnoreCase("Order Number/LPN Number")) {
                           reference1=reference1+" lpn#"+OrderNumber+"/"+lpnNumber;
                           
                       }
                    }
                    customerReferences.append(reference1);
                    customerReferences.append("</Value>");
                    customerReferences.append("</CustomerReferences>");
                
            // Added above by Suman Gunda
            } 
                }else if (reference1.equalsIgnoreCase("") && 
                       (!reference2.equalsIgnoreCase(""))) {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                if (referenceValue2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                    customerReferences.append("P_O_NUMBER");
                } else {
                    customerReferences.append("CUSTOMER_REFERENCE");
                }
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                if(referenceValue2.equalsIgnoreCase("Order Number/LPN Number")){
                    customerReferences.append(OrderNumber+"/"+lpnNumber);
                }
                else if(referenceValue2.equalsIgnoreCase("LPN NUMBER")){
                    customerReferences.append("lpn# "+lpnNumber);
                }
                else{
                customerReferences.append(reference2);
                }
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>\n");
            } else {

                customerReferences.append(getReferenceValue(referenceValue1, 
                                                            referenceValue2, 
                                                            reference1, 
                                                            reference2,lpnNumber,OrderNumber));
            }
        }
        return customerReferences;
    }


    private StringBuffer getReferenceValue(String refVal1, String refVal2, 
                                           String reference1, 
                                           String reference2,String lpnNumber,String OrderNumber) {
                                           
                                      
        StringBuffer customerReferences = new StringBuffer("");

        logger.info("Entered getReferenceValue() method");
        if ((refVal1.equalsIgnoreCase("")) && (refVal2.equalsIgnoreCase(""))) {
            customerReferences.append("<CustomerReferences>");
            customerReferences.append("<CustomerReferenceType>");
            customerReferences.append("CUSTOMER_REFERENCE");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append("Ref1# " + reference1 + "  Ref2# " + 
                                      reference2);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
            logger.info("Ref 1 & Ref2 from profile Options are null");
        } else if (refVal1.equalsIgnoreCase("")) {
            customerReferences.append(getRef2Val(refVal2, reference1, 
                                                 reference2,lpnNumber,OrderNumber));
        } else if (refVal2.equalsIgnoreCase("")) {
            customerReferences.append(getRef1Val(refVal1, reference1, 
                                                 reference2));
        } else {
            logger.info("Ref 1 & Ref2 from profile Options are not null");
            if ((refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) && 
                                   (refVal2.equalsIgnoreCase("Order Number/LPN Number"))) {
                                  
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("P_O_NUMBER");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(OrderNumber+"/"+lpnNumber);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else if ((refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) && 
                                   (refVal2.equalsIgnoreCase("LPN Number"))) {
                                  
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("P_O_NUMBER");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append("Lpn# "+lpnNumber);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            }else if ((refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) && 
                                                   (refVal2.equalsIgnoreCase("LPN NUMBER"))) {
                                                   
                                            customerReferences.append("<CustomerReferences>");
                                            customerReferences.append("<CustomerReferenceType>");
                                            customerReferences.append("CUSTOMER_REFERENCE");
                                            customerReferences.append("</CustomerReferenceType>");
                                            customerReferences.append("<Value>");
                                            customerReferences.append("SO# " + reference1 + " lpn#" + 
                                                                      lpnNumber);
                                            customerReferences.append("</Value>");
                                            customerReferences.append("</CustomerReferences>");
                                    
        }else if ((refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) && 
                                                   (refVal2.equalsIgnoreCase("Order Number/LPN Number"))) {
                                                   
                                            customerReferences.append("<CustomerReferences>");
                                            customerReferences.append("<CustomerReferenceType>");
                                            customerReferences.append("CUSTOMER_REFERENCE");
                                            customerReferences.append("</CustomerReferenceType>");
                                            customerReferences.append("<Value>");
                                            customerReferences.append("SO#" + reference1 + " lpn#" + 
                                                                      OrderNumber+"/"+lpnNumber);
                                            customerReferences.append("</Value>");
                                            customerReferences.append("</CustomerReferences>");
                                    
        }else if ((refVal1.equalsIgnoreCase("DELIVERY NAME")) && 
                                           (refVal2.equalsIgnoreCase("LPN NUMBER"))) {
                                           
                                    customerReferences.append("<CustomerReferences>");
                                    customerReferences.append("<CustomerReferenceType>");
                                    customerReferences.append("CUSTOMER_REFERENCE");
                                    customerReferences.append("</CustomerReferenceType>");
                                    customerReferences.append("<Value>");
                                    customerReferences.append("Del# " + reference1 + " lpn#" + 
                                                              lpnNumber);
                                    customerReferences.append("</Value>");
                                    customerReferences.append("</CustomerReferences>");
                            
            }else if ((refVal1.equalsIgnoreCase("DELIVERY NAME")) && 
                                           (refVal2.equalsIgnoreCase("Order Number/LPN Number"))) {
                                           
                                    customerReferences.append("<CustomerReferences>");
                                    customerReferences.append("<CustomerReferenceType>");
                                    customerReferences.append("CUSTOMER_REFERENCE");
                                    customerReferences.append("</CustomerReferenceType>");
                                    customerReferences.append("<Value>");
                                    customerReferences.append("Del# " + reference1 + " lpn#" + 
                                                              OrderNumber+"/"+lpnNumber);
                                    customerReferences.append("</Value>");
                                    customerReferences.append("</CustomerReferences>");
                            
            }  else if ((refVal1.equalsIgnoreCase("CUSTOM")) && 
                                           (refVal2.equalsIgnoreCase("LPN NUMBER"))) {
                                           
                                    customerReferences.append("<CustomerReferences>");
                                    customerReferences.append("<CustomerReferenceType>");
                                    customerReferences.append("CUSTOMER_REFERENCE");
                                    customerReferences.append("</CustomerReferenceType>");
                                    customerReferences.append("<Value>");
                                    if(lpnNumber != null && !"".equals(lpnNumber)){
                                    customerReferences.append(reference1 + " lpn#" + 
                                                              lpnNumber);
                                    }
                                    else{
                                        customerReferences.append(reference1);
                                    }
                                    customerReferences.append("</Value>");
                                    customerReferences.append("</CustomerReferences>");
                            
            }   else if ((refVal1.equalsIgnoreCase("CUSTOM")) && 
                                           (refVal2.equalsIgnoreCase("Order Number/LPN Number"))) {
                                           
                                    customerReferences.append("<CustomerReferences>");
                                    customerReferences.append("<CustomerReferenceType>");
                                    customerReferences.append("CUSTOMER_REFERENCE");
                                    customerReferences.append("</CustomerReferenceType>");
                                    customerReferences.append("<Value>");
                if(lpnNumber != null && !"".equals(lpnNumber)){
                                    customerReferences.append(reference1 + " lpn#" + 
                                                              OrderNumber+"/"+lpnNumber);
                }else{
                    customerReferences.append(reference1);
                }
                                    customerReferences.append("</Value>");
                                    customerReferences.append("</CustomerReferences>");
                            
            }
           else if (refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("P_O_NUMBER");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else if (refVal2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("P_O_NUMBER");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else if ((refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) && 
                       (refVal2.equalsIgnoreCase("DELIVERY NAME"))) {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append("SO# " + reference1 + "  Del# " + 
                                          reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else {
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                
                 customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            
              customerReferences.append("<CustomerReferences>");
              customerReferences.append("<CustomerReferenceType>");
              customerReferences.append("INVOICE_NUMBER");
              customerReferences.append("</CustomerReferenceType>");
              customerReferences.append("<Value>");
              customerReferences.append(reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");

            }
        }
        return customerReferences;

    }

    private StringBuffer getRef2Val(String refVal2, String reference1, 
                                        String reference2,String lpnNumber,String OrderNumber) {
                                        
            StringBuffer customerReferences = new StringBuffer("");
            customerReferences.append("<CustomerReferences>");
            customerReferences.append("<CustomerReferenceType>");
            if (refVal2.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
                customerReferences.append("P_O_NUMBER");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
                customerReferences.append("<CustomerReferences>");
                customerReferences.append("<CustomerReferenceType>");
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(reference1);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else if (refVal2.equalsIgnoreCase("SALES ORDER NUMBER")) {
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append("Ref# " + reference1 + "  SO# " + 
                                          reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            }else if (refVal2.equalsIgnoreCase("LPN NUMBER")) {                       
             
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append("Lpn# " + lpnNumber);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            }else if (refVal2.equalsIgnoreCase("Order Number/LPN Number")) {                       
             
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append(OrderNumber+"/" + lpnNumber);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            } else {
                customerReferences.append("CUSTOMER_REFERENCE");
                customerReferences.append("</CustomerReferenceType>");
                customerReferences.append("<Value>");
                customerReferences.append("Ref# " + reference1 + "  Del# " + 
                                          reference2);
                customerReferences.append("</Value>");
                customerReferences.append("</CustomerReferences>");
            }
            return customerReferences;
        }

    private StringBuffer getRef1Val(String refVal1, String reference1, 
                                    String reference2) {
        StringBuffer customerReferences = new StringBuffer("");
        customerReferences.append("<CustomerReferences>");
        customerReferences.append("<CustomerReferenceType>");
        if (refVal1.equalsIgnoreCase("PURCHASE ORDER NUMBER")) {
            customerReferences.append("P_O_NUMBER");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append(reference1);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
            customerReferences.append("<CustomerReferences>");
            customerReferences.append("<CustomerReferenceType>");
            customerReferences.append("CUSTOMER_REFERENCE");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append(reference2);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
        } else if (refVal1.equalsIgnoreCase("SALES ORDER NUMBER")) {

            customerReferences.append("CUSTOMER_REFERENCE");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append("Ref# " + reference2 + "  SO# " + 
                                      reference1);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
        } else {
            customerReferences.append("CUSTOMER_REFERENCE");
            customerReferences.append("</CustomerReferenceType>");
            customerReferences.append("<Value>");
            customerReferences.append("Ref# " + reference2 + "  Del# " + 
                                      reference1);
            customerReferences.append("</Value>");
            customerReferences.append("</CustomerReferences>");
        }
        return customerReferences;
    }

    private StringBuffer createDangerousGoodsDetail(String carrierCode, 
                                                    String accessibility, 
                                                    String identificationNumber, 
                                                    String packingGroup, 
                                                    String properShippingName, 
                                                    String technicanName, 
                                                    String hazardClass, 
                                                    String labelText, 
                                                    double amount, 
                                                    String units, 
                                                    String packagingCount, 
                                                    String packagingUnits, 
                                                    String emergencyContactNumber, 
                                                    String offeror, 
                                                    String hazmatTechnicalName) {
        StringBuffer dangerousGoodsDetail = new StringBuffer("");
        dangerousGoodsDetail.append("<DangerousGoodsDetail>");

        if (carrierCode.equalsIgnoreCase("FDXE")) {
            dangerousGoodsDetail.append("<Accessibility>");
            dangerousGoodsDetail.append(accessibility); //INACCESSIBLE  // ACCESSIBLE
            dangerousGoodsDetail.append("</Accessibility>");

            dangerousGoodsDetail.append("<Containers>");           // Added by Jagadish. This is a new tag that is added in v12
            dangerousGoodsDetail.append("<HazardousCommodities>");
            dangerousGoodsDetail.append("<Description>");
            dangerousGoodsDetail.append("<ProperShippingName>");
            dangerousGoodsDetail.append(properShippingName);
            dangerousGoodsDetail.append("</ProperShippingName>");
            dangerousGoodsDetail.append("<HazardClass>");
            dangerousGoodsDetail.append(hazardClass);
            dangerousGoodsDetail.append("</HazardClass>");
            dangerousGoodsDetail.append("</Description>");
            dangerousGoodsDetail.append("</HazardousCommodities>");
            dangerousGoodsDetail.append("</Containers>");           // Added by Jagadish. This is a new tag that is added in v12
        } else {
            dangerousGoodsDetail.append("<Options>HAZARDOUS_MATERIALS</Options>");
            dangerousGoodsDetail.append("<Containers>");           // Added by Jagadish. This is a new tag that is added in v12
            dangerousGoodsDetail.append("<HazardousCommodities>");
            dangerousGoodsDetail.append("<Description>");
            dangerousGoodsDetail.append("<Id>");
            dangerousGoodsDetail.append(identificationNumber); //UN1992 ...
            dangerousGoodsDetail.append("</Id>");
            
            ////System.out.println("******************** packingGroup :"+packingGroup+"**");
            if(packingGroup != null && ! ("".equalsIgnoreCase(packingGroup.trim())))
            {
                dangerousGoodsDetail.append("<PackingGroup>");
                dangerousGoodsDetail.append(packingGroup); //I II III
                dangerousGoodsDetail.append("</PackingGroup>");
            }    
            dangerousGoodsDetail.append("<ProperShippingName>");
            dangerousGoodsDetail.append(properShippingName);
            dangerousGoodsDetail.append("</ProperShippingName>");
            dangerousGoodsDetail.append("<TechnicalName>");
            dangerousGoodsDetail.append(hazmatTechnicalName);
            dangerousGoodsDetail.append("</TechnicalName>");
            
            ////System.out.println("hazardClass :"+hazardClass+"**");
            if(hazardClass.contains("("))
            {
                String hzClass = hazardClass.substring(0,hazardClass.indexOf('(')).trim();
                ////System.out.println("hzClass :"+hzClass+"**");
                dangerousGoodsDetail.append("<HazardClass>");
                dangerousGoodsDetail.append(hzClass);  //hazardClass);
                dangerousGoodsDetail.append("</HazardClass>\n");
                
                String subsidaryClasses = hazardClass.substring(hazardClass.indexOf('(')+1 , hazardClass.indexOf(')')).trim();
                ////System.out.println("subsidaryClasses :"+subsidaryClasses+"**");
                dangerousGoodsDetail.append("<SubsidiaryClasses>");
                dangerousGoodsDetail.append(subsidaryClasses);
                dangerousGoodsDetail.append("</SubsidiaryClasses>\n");
            }
            else
            {
                dangerousGoodsDetail.append("<HazardClass>");
                dangerousGoodsDetail.append(hazardClass);
                dangerousGoodsDetail.append("</HazardClass>\n");
            }
            dangerousGoodsDetail.append("<LabelText>");
            dangerousGoodsDetail.append(labelText); //FLAMMABLE LIQUID
            dangerousGoodsDetail.append("</LabelText>");
            dangerousGoodsDetail.append("</Description>");
            dangerousGoodsDetail.append("<Quantity>");
            dangerousGoodsDetail.append("<Amount>");
            dangerousGoodsDetail.append(amount);
            dangerousGoodsDetail.append("</Amount>");
            
            ////System.out.println("******************** units :"+units+"**");
            if(units != null && ! ("".equalsIgnoreCase(units.trim())))
            {
                dangerousGoodsDetail.append("<Units>");
                dangerousGoodsDetail.append(units);
                dangerousGoodsDetail.append("</Units>");
            }   
            dangerousGoodsDetail.append("</Quantity>");
            dangerousGoodsDetail.append("</HazardousCommodities>");
            dangerousGoodsDetail.append("</Containers>");           // Added by Jagadish. This is a new tag that is added in v12
            dangerousGoodsDetail.append("<Packaging>");
            dangerousGoodsDetail.append("<Count>");
            dangerousGoodsDetail.append(packagingCount);
            dangerousGoodsDetail.append("</Count>");
            dangerousGoodsDetail.append("<Units>");
            dangerousGoodsDetail.append(packagingUnits);
            dangerousGoodsDetail.append("</Units>");
            dangerousGoodsDetail.append("</Packaging>");
            dangerousGoodsDetail.append("<EmergencyContactNumber>");
            dangerousGoodsDetail.append(emergencyContactNumber);
            dangerousGoodsDetail.append("</EmergencyContactNumber>");
            dangerousGoodsDetail.append("<Offeror>");
            dangerousGoodsDetail.append(technicanName);
            dangerousGoodsDetail.append("</Offeror>");
        }
        dangerousGoodsDetail.append("</DangerousGoodsDetail>");
        return dangerousGoodsDetail;
    }

    private StringBuffer createDryIce(double dryIceWeight) {
        StringBuffer dryIce = new StringBuffer("");
        dryIce.append("<DryIceWeight>");
        dryIce.append("<Units>KG</Units>");
        dryIce.append("<Value>");
        dryIce.append(dryIceWeight);
        dryIce.append("</Value>");
        dryIce.append("</DryIceWeight>\n");

        return dryIce;
    }

    private StringBuffer createSignatureOptionDetail(String signatureOptions) {
        StringBuffer signatureOptionDetail = new StringBuffer("");
        signatureOptionDetail.append("<SignatureOptionDetail>");
        signatureOptionDetail.append("<OptionType>");
        if (signatureOptions.equalsIgnoreCase("DIRECT")) {
            signatureOptionDetail.append("DIRECT");
        }
        if (signatureOptions.equalsIgnoreCase("ADULT")) {
            signatureOptionDetail.append("ADULT");
        }
        if (signatureOptions.equalsIgnoreCase("DELIVERWITHOUTSIGNATURE")) {
            signatureOptionDetail.append("NO_SIGNATURE_REQUIRED");
        }
        if (signatureOptions.equalsIgnoreCase("INDIRECT")) {
            signatureOptionDetail.append("INDIRECT");
        }
        signatureOptionDetail.append("</OptionType>");
        signatureOptionDetail.append("</SignatureOptionDetail>\n");

        return signatureOptionDetail;
    }

    private StringBuffer createCommodities(HashMap[] intlCommodityHM) {
        int numberOfPieces = 0;
        String description = "";
        String countryOfManufacture = "";
        String harmonizedCode = "";
        String weight = "";
        String quantity = "";
        String quantityUnits = "";
        String unitPrice = "";
        String customsValue = "";
        String exportLicenseNumber = "";
        String exportLicenseExpiryDate = "";
        StringBuffer commodities = new StringBuffer("");
        for (int index = 0; index < intlCommodityHM.length; index++) {
            commodities.append("<Commodities>");
            numberOfPieces = 
                    (Integer)intlCommodityHM[index].get("numberOfPieces");
            description = 
                    nullStringToSpace(intlCommodityHM[index].get("description"));
            countryOfManufacture = 
                    nullStringToSpace(intlCommodityHM[index].get("countryOfManufacture"));
            weight = 
                    nullStringToSpace(intlCommodityHM[index].get("weight"));
            quantity = 
                    nullStringToSpace(intlCommodityHM[index].get("quantity"));
            quantityUnits = 
                    nullStringToSpace(intlCommodityHM[index].get("quantityUnits"));
            harmonizedCode = 
                    nullStringToSpace(intlCommodityHM[index].get("harmonizedCode"));
            unitPrice = 
                    nullStringToSpace(intlCommodityHM[index].get("unitPrice"));
            exportLicenseNumber = 
                    nullStringToSpace(intlCommodityHM[index].get("exportLicenseNumber"));
            ////System.out.println("exportLicenseNumber  :: "+exportLicenseNumber);
            exportLicenseExpiryDate = 
                    nullStringToSpace(intlCommodityHM[index].get("exportLicenseExpiryDate"));
            // //System.out.println("exportLicenseExpiryDate  :: "+exportLicenseExpiryDate);
            customsValue = 
                    nullStringToSpace(intlCommodityHM[index].get("customsValue"));
            commodities.append("<NumberOfPieces>" + numberOfPieces + 
                               "</NumberOfPieces>");
            commodities.append("<Description>" + description + 
                               "</Description>");
            commodities.append("<CountryOfManufacture>" + 
                               countryOfManufacture + 
                               "</CountryOfManufacture>");
            if ((!harmonizedCode.equalsIgnoreCase(""))) {
                commodities.append("<HarmonizedCode>" + harmonizedCode + 
                                   "</HarmonizedCode>");
            }
            commodities.append(createWeight("LBS", 
                                            Double.parseDouble(weight)));
            commodities.append("<Quantity>" + quantity + "</Quantity>");
            commodities.append("<QuantityUnits>" + quantityUnits + 
                               "</QuantityUnits>");
            commodities.append(createCharges("UnitPrice", 
                                             Double.parseDouble(unitPrice)));
            commodities.append(createCharges("CustomsValue", 
                                             Double.parseDouble(customsValue)));
            if ((!exportLicenseNumber.equalsIgnoreCase(""))) {
                commodities.append("<ExportLicenseNumber>" + 
                                   exportLicenseNumber + 
                                   "</ExportLicenseNumber>");
            }
            if ((!exportLicenseExpiryDate.equals(""))) {
                //  //System.out.println("Inside if :: "+exportLicenseExpiryDate);
                commodities.append("<ExportLicenseExpirationDate>" + 
                                   exportLicenseExpiryDate + 
                                   "</ExportLicenseExpirationDate>");
            }
            commodities.append("</Commodities>");
        }
        return commodities;
    }


    private StringBuffer createCustomsClearanceDetail(String intPayerType, 
                                                      String shipFromCountry, 
                                                      String tPintcountryCode, 
                                                      String tPintAccNumber, 
                                                      String customerCarrierAccountNumber, 
                                                      String intTotalCustomsValue, 
                                                      double insuranceCharges, 
                                                      double freightCharge, 
                                                      double taxesOrMiscellaneousCharge, 
                                                      String intPurpose, 
                                                      String intTermsOfSale, 
                                                      String exemptionNumber, 
                                                      HashMap[] intlCommodityHM,
                                                      String importerName,
                                                      String importerCompName,
                                                      String importerPhoneNum,
                                                      String importerAddress1,
                                                      String importerAddress2,
                                                      String importerCity,
                                                      String importerState,
                                                      String importerPostalCode,
                                                      String importerCountryCode,
                                                      String impIntlSedNumber,
                                                      String impIntlSedType,
                                                      String declarationStmt,
                                                      String generateCI,
                                                      String brokerName,
                                                    String brokerCompName,
                                                    String brokerPhoneNum,
                                                    String brokerAddress1,
                                                    String brokerAddress2,
                                                    String brokerCity,
                                                    String brokerState,
                                                    String brokerPostalCode,
                                                    String brokerCountryCode) {
        StringBuffer customsClearanceDetail = new StringBuffer("");
        customsClearanceDetail.append("<CustomsClearanceDetail>");
        //SC_6.7.1_Merge
         if(brokerName.length() > 1 ){
                 customsClearanceDetail.append(setBrokerDetail(  brokerName,
                                                                         brokerCompName,
                                                                         brokerPhoneNum,
                                                                         brokerAddress1,
                                                                         brokerAddress2,
                                                                         brokerCity,
                                                                         brokerState,
                                                                         brokerPostalCode,
                                                                         brokerCountryCode));
        }
        if(importerName.length() > 1 ){
            customsClearanceDetail.append(setImporterDetail(  importerName,
                                                                    importerCompName,
                                                                    importerPhoneNum,
                                                                    importerAddress1,
                                                                    importerAddress2,
                                                                    importerCity,
                                                                    importerState,
                                                                    importerPostalCode,
                                                                    importerCountryCode,
                                                                    impIntlSedNumber,
                                                                    impIntlSedType));
        }
        //end SC_6.7.1_Merge
        customsClearanceDetail.append("<DutiesPayment>");
        try {
            if (intPayerType.equals("SENDER")) {
                customsClearanceDetail.append("<PaymentType>SENDER</PaymentType>");
                customsClearanceDetail.append("<Payor>");
                customsClearanceDetail.append("<ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                if(tPintAccNumber == null || tPintAccNumber == "")
                {
                    customsClearanceDetail.append("<AccountNumber>" + 
                                                  customerCarrierAccountNumber + 
                                                  "</AccountNumber>");
                    customsClearanceDetail.append("<Contact/>");               // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<Address>");                 // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<CountryCode>" + 
                                                  shipFromCountry + 
                                                  "</CountryCode>");                              
                    customsClearanceDetail.append("</Address>");               // By Jagadish. This is a mandatory tag for v12                
                }                              
                else     
                {
                    customsClearanceDetail.append("<AccountNumber>" + 
                                                  tPintAccNumber + //customerCarrierAccountNumber + 
                                                  "</AccountNumber>");
                    customsClearanceDetail.append("<Contact/>");               // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<Address>");                 // By Jagadish. This is a mandatory tag for v12                              
                    customsClearanceDetail.append("<CountryCode>" + 
                                                  tPintcountryCode + //shipFromCountry + 
                                                  "</CountryCode>");
                    customsClearanceDetail.append("</Address>");               // By Jagadish. This is a mandatory tag for v12                                                  
                }                                  
                customsClearanceDetail.append("</ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("</Payor>");
            } else if (intPayerType.equals("RECIPIENT")) {
                customsClearanceDetail.append("<PaymentType>RECIPIENT</PaymentType>");
                if(tPintAccNumber != null && tPintAccNumber != "")
                {
                    customsClearanceDetail.append("<Payor>");
                    customsClearanceDetail.append("<ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<AccountNumber>" + 
                                                  tPintAccNumber + 
                                                  "</AccountNumber>");
                    customsClearanceDetail.append("<Contact/>");               // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<Address>");                 // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("<CountryCode>" + 
                                                  tPintcountryCode +
                                                  "</CountryCode>");
                    customsClearanceDetail.append("</Address>");               // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("</ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                    customsClearanceDetail.append("</Payor>");                              
                }                              
            } else if (intPayerType.equals("THIRDPARTY")) {
                customsClearanceDetail.append("<PaymentType>THIRD_PARTY</PaymentType>");
                customsClearanceDetail.append("<Payor>");
                customsClearanceDetail.append("<ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<AccountNumber>" + 
                                              tPintAccNumber + 
                                              "</AccountNumber>");
                customsClearanceDetail.append("<Contact/>");               // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<Address>");                 // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<CountryCode>" + 
                                              tPintcountryCode + 
                                              "</CountryCode>");
                customsClearanceDetail.append("</Address>");               // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("</ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("</Payor>");
            } else if (intPayerType.equals("COLLECT")) {
                customsClearanceDetail.append("<PaymentType>COLLECT</PaymentType>");
                customsClearanceDetail.append("<Payor>");
                customsClearanceDetail.append("<ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<AccountNumber>" + 
                                              tPintAccNumber + 
                                              "</AccountNumber>");
                customsClearanceDetail.append("<Contact/>");               // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<Address>");                 // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("<CountryCode>" + 
                                              tPintcountryCode +
                                              "</CountryCode>");
                customsClearanceDetail.append("</Address>");               // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("</ResponsibleParty>");     // By Jagadish. This is a mandatory tag for v12
                customsClearanceDetail.append("</Payor>");
            }
            customsClearanceDetail.append("</DutiesPayment>");
            customsClearanceDetail.append("<DocumentContent>DOCUMENTS_ONLY</DocumentContent>");
            customsClearanceDetail.append(createCharges("CustomsValue", 
                                                        (Double)Double.parseDouble(intTotalCustomsValue)));
            customsClearanceDetail.append(createCharges("InsuranceCharges", 
                                                        insuranceCharges));

//SC_6.7.1_Merge
if("Y".equalsIgnoreCase(generateCI)){
            customsClearanceDetail.append(createCommercialInvoice(freightCharge, 
                                                                  taxesOrMiscellaneousCharge, 
                                                                  declarationStmt,
                                                                  intPurpose, 
                                                                  intTermsOfSale));
            }
//end SC_6.7.1_Merge
            
            customsClearanceDetail.append(createCommodities(intlCommodityHM));
            if (!(exemptionNumber.equalsIgnoreCase("") || 
                  exemptionNumber.equalsIgnoreCase(null))) {
                customsClearanceDetail.append(createExportDetail(exemptionNumber));
            }
            customsClearanceDetail.append("</CustomsClearanceDetail>");
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        return customsClearanceDetail;
    }

    private StringBuffer createCharges(String typeOfCharge, double amount) {
        StringBuffer charges = new StringBuffer("");
        charges.append("<" + typeOfCharge + ">");
        charges.append("<Currency>USD</Currency>");
        charges.append("<Amount>" + amount + "</Amount>");
        charges.append("</" + typeOfCharge + ">");
        return charges;
    }

    private StringBuffer createExportDetail(String exemptionNumber) {
        StringBuffer exportDetail = new StringBuffer("");
        exportDetail.append("<ExportDetail>");
        exportDetail.append("<ExportComplianceStatement>" + exemptionNumber + 
                            "</ExportComplianceStatement>");
        exportDetail.append("</ExportDetail>");
        return exportDetail;
    }

    private StringBuffer createCommercialInvoice(double freightCharge, 
                                                 double taxesOrMiscellaneousCharge, 
                                                 String declarationStmt,
                                                 String intPurpose, 
                                                 String intTermsOfSale) {
        StringBuffer commercialInvoice = new StringBuffer("");
        commercialInvoice.append("<CommercialInvoice>");
        commercialInvoice.append(createCharges("FreightCharge", 
                                               freightCharge));
        commercialInvoice.append(createCharges("TaxesOrMiscellaneousCharge", 
                                               taxesOrMiscellaneousCharge));
        //SC_6.7.1_Merge
        commercialInvoice.append(setDeclarationStmt(declarationStmt));
        commercialInvoice.append(purposeOfShipmentType(intPurpose));
        commercialInvoice.append(setTermsOfSale(intTermsOfSale));
        commercialInvoice.append("</CommercialInvoice>");
        return commercialInvoice;

    }

    private StringBuffer purposeOfShipmentType(String intPurpose) {
        StringBuffer purpose = new StringBuffer("");
        if (intPurpose.equalsIgnoreCase("Not Sold")) {
            purpose.append("<Purpose>NOT_SOLD</Purpose>");
        } else if (intPurpose.equalsIgnoreCase("Sample")) {
            purpose.append("<Purpose>SAMPLE</Purpose>");
        } else if (intPurpose.equalsIgnoreCase("Repair and Return")) {
            purpose.append("<Purpose>REPAIR_AND_RETURN</Purpose>");
        } else if (intPurpose.equalsIgnoreCase("Personal Effects")) {
            purpose.append("<Purpose>PERSONAL_EFFECTS</Purpose>");
        } else if (intPurpose.equalsIgnoreCase("Sold")) {
            purpose.append("<Purpose>SOLD</Purpose>");
        } else if (intPurpose.equalsIgnoreCase("Gift")) {
            purpose.append("<Purpose>GIFT</Purpose>");
        }
        return purpose;
    }

    private StringBuffer setTermsOfSale(String intTermsOfSale) {
        StringBuffer termsOfSale = new StringBuffer("");
        if (intTermsOfSale.equalsIgnoreCase("CFR_OR_CPT")) {
            termsOfSale.append("<TermsOfSale>CFR_OR_CPT</TermsOfSale>");
        } else if (intTermsOfSale.equalsIgnoreCase("CIF_OR_CIP")) {
            termsOfSale.append("<TermsOfSale>CIF_OR_CIP</TermsOfSale>");
        } else if (intTermsOfSale.equalsIgnoreCase("DDP")) {
            termsOfSale.append("<TermsOfSale>DDP</TermsOfSale>");
        } else if (intTermsOfSale.equalsIgnoreCase("DDU")) {
            termsOfSale.append("<TermsOfSale>DDU</TermsOfSale>");
        } else if (intTermsOfSale.equalsIgnoreCase("EXW")) {
            termsOfSale.append("<TermsOfSale>EXW</TermsOfSale>");
        } else if (intTermsOfSale.equalsIgnoreCase("FOB_OR_FCA")) {
            termsOfSale.append("<TermsOfSale>FOB_OR_FCA</TermsOfSale>");
        }
        return termsOfSale;
    }

    private StringBuffer createIntlDropOffServicePackagingTypes(String fedExWSChkReturnlabelstr, 
                                                                String rtnDropOfType, 
                                                                String rtnShipMethod, 
                                                                String rtnPackageList, 
                                                                String dropoffType, 
                                                                String service, 
                                                                String packaging) {
        StringBuffer dropOffServicePackagingTypes = new StringBuffer("");

        if (!(fedExWSChkReturnlabelstr.equals("NONRETURN"))) {
            dropOffServicePackagingTypes.append("<DropoffType>");
            if (rtnDropOfType.equals("REGULARPICKUP")) {
                dropOffServicePackagingTypes.append("REGULAR_PICKUP");
            } else if (rtnDropOfType.equals("DROPBOX")) {
                dropOffServicePackagingTypes.append("DROP_BOX");
            } else if (rtnDropOfType.equals("BUSINESSSERVICECENTER")) {
                dropOffServicePackagingTypes.append("BUSINESS_SERVICE_CENTER");
            } else if (rtnDropOfType.equals("STATION")) {
                dropOffServicePackagingTypes.append("STATION");
            } else if (rtnDropOfType.equals("REQUESTCOURIER")) {
                dropOffServicePackagingTypes.append("REQUEST_COURIER");
            }
            dropOffServicePackagingTypes.append("</DropoffType>");

            //System.out.println("rtnShipMethod : "+rtnShipMethod); 
            dropOffServicePackagingTypes.append("<ServiceType>");
            if (rtnShipMethod.equals("INTERNATIONALPRIORITY") || rtnShipMethod.equals("INTERNATIONAL PRIORITY")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_PRIORITY");
            } else if (rtnShipMethod.equals("INTERNATIONALPRIORITYFREIGHT")||service.equals("INTERNATIONALPRIORITY FREIGHT")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_PRIORITY_FREIGHT");
            } else if (rtnShipMethod.equals("INTERNATIONALGROUND") || rtnShipMethod.equals("FEDEXGROUND")) {
                dropOffServicePackagingTypes.append("FEDEX_GROUND");
            } else if (rtnShipMethod.equals("INTERNATIONALFIRST") || rtnShipMethod.equals("INTERNATIONAL FIRST")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_FIRST");
            } else if (rtnShipMethod.equals("INTERNATIONALECONOMYFREIGHT") || rtnShipMethod.equals("INTERNATIONALECONOMY FREIGHT")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_ECONOMY_FREIGHT");
            } else if (rtnShipMethod.equals("INTERNATIONALECONOMY") || rtnShipMethod.equals("INTERNATIONAL ECONOMY")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_ECONOMY");
            } else if (rtnShipMethod.equals("EUROPEFIRSTINTERNATIONALPRIORITY") || rtnShipMethod.equals("EUROPEFIRSTINTERNATIONAL PRIORITY")) {
                dropOffServicePackagingTypes.append("EUROPE_FIRST_INTERNATIONAL_PRIORITY");
            }
            dropOffServicePackagingTypes.append("</ServiceType>");


            dropOffServicePackagingTypes.append("<PackagingType>");
            if (rtnPackageList.equals("YOURPACKAGING")) {
                dropOffServicePackagingTypes.append("YOUR_PACKAGING");
            } else if (rtnPackageList.equals("FEDEXBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_BOX");
            } else if (rtnPackageList.equals("FEDEXENVELOPE")) {
                dropOffServicePackagingTypes.append("FEDEX_ENVELOPE");
            } else if (rtnPackageList.equals("FEDEXPAK")) {
                dropOffServicePackagingTypes.append("FEDEX_PAK");
            } else if (rtnPackageList.equals("FEDEXTUBE")) {
                dropOffServicePackagingTypes.append("FEDEX_TUBE");
            } else if (rtnPackageList.equals("FEDEX25KGBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_25KG_BOX");
            } else if (rtnPackageList.equals("FEDEX10KGBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_10KG_BOX");
            }
            dropOffServicePackagingTypes.append("</PackagingType>");

        } else {
            dropOffServicePackagingTypes.append("<DropoffType>");
            if (dropoffType.equals("REGULARPICKUP")) {
                dropOffServicePackagingTypes.append("REGULAR_PICKUP");
            } else if (dropoffType.equals("DROPBOX")) {
                dropOffServicePackagingTypes.append("DROP_BOX");
            } else if (dropoffType.equals("BUSINESSSERVICECENTER")) {
                dropOffServicePackagingTypes.append("BUSINESS_SERVICE_CENTER");
            } else if (dropoffType.equals("STATION")) {
                dropOffServicePackagingTypes.append("STATION");
            } else if (dropoffType.equals("REQUESTCOURIER")) {
                dropOffServicePackagingTypes.append("REQUEST_COURIER");
            }
            dropOffServicePackagingTypes.append("</DropoffType>");

            //System.out.println("service : "+service);
            dropOffServicePackagingTypes.append("<ServiceType>");
            if (service.equals("INTERNATIONALPRIORITY") || service.equals("INTERNATIONAL PRIORITY")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_PRIORITY");
            } else if (service.equals("INTERNATIONALPRIORITYFREIGHT") || service.equals("INTERNATIONALPRIORITY FREIGHT")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_PRIORITY_FREIGHT");
            } else if (service.equals("INTERNATIONALGROUND") || service.equals("FEDEXGROUND")) {
                dropOffServicePackagingTypes.append("FEDEX_GROUND");
            } else if (service.equals("INTERNATIONALFIRST") || service.equals("INTERNATIONAL FIRST")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_FIRST");
            } else if (service.equals("INTERNATIONALECONOMYFREIGHT")|| service.equals("INTERNATIONALECONOMY FREIGHT")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_ECONOMY_FREIGHT");
            } else if (service.equals("INTERNATIONALECONOMY") || service.equals("INTERNATIONAL ECONOMY")) {
                dropOffServicePackagingTypes.append("INTERNATIONAL_ECONOMY");
            } else if (service.equals("EUROPEFIRSTINTERNATIONALPRIORITY") || service.equals("EUROPEFIRSTINTERNATIONAL PRIORITY")) {
                dropOffServicePackagingTypes.append("EUROPE_FIRST_INTERNATIONAL_PRIORITY");
            }
            dropOffServicePackagingTypes.append("</ServiceType>");


            dropOffServicePackagingTypes.append("<PackagingType>");
            if (packaging.equals("YOURPACKAGING")) {
                dropOffServicePackagingTypes.append("YOUR_PACKAGING");
            } else if (packaging.equals("FEDEXBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_BOX");
            } else if (packaging.equals("FEDEXENVELOPE")) {
                dropOffServicePackagingTypes.append("FEDEX_ENVELOPE");
            } else if (packaging.equals("FEDEXPAK")) {
                dropOffServicePackagingTypes.append("FEDEX_PAK");
            } else if (packaging.equals("FEDEXTUBE")) {
                dropOffServicePackagingTypes.append("FEDEX_TUBE");
            } else if (packaging.equals("FEDEX25KGBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_25KG_BOX");
            } else if (packaging.equals("FEDEX10KGBOX")) {
                dropOffServicePackagingTypes.append("FEDEX_10KG_BOX");
            }
            dropOffServicePackagingTypes.append("</PackagingType>");


        }

        return dropOffServicePackagingTypes;
    }


    /**
     * This is the method where we establish the connection with fedex webservice.
     * here we sent soap request to fedex webservice.
     * @param request it is the soap request which we sent to fedex webservice
     * @param httpsFlag it is type of conncection
     * @param hName it is the host name
     * @param requestFilePath it is path where we save the request and response.
     * @return it returns response after sending the request to fedex webservice.
     */
    public String sendSOAPRequest(String request, boolean httpsFlag, 
                                  String hName, String requestFilePath) {
        URL aspPage;
        InputStream isXMLResponse;
        String response = "";
        String httpOrHttps = "https";
        logger.info("XML Request: " + request);
        logger.info("requestFilePath  ::" + requestFilePath);
        try {
            writeOutputFile(request, requestFilePath + "_request.xml");
        } catch (Exception fileNotFoundException) {
            logger.severe("Exception::"+fileNotFoundException.getMessage());
        }
        try {
            if (httpsFlag) {
                httpOrHttps = "https";
                disableSslVerification();
            } else {
                httpOrHttps = "http";
            }

            logger.info("Posting to URL: " + httpOrHttps + "://" + hName);
            aspPage = new URL(httpOrHttps + "://" + hName);
            HttpURLConnection connection2 = 
                (HttpURLConnection)aspPage.openConnection();

            // setup connection
            connection2.setDoInput(true);
            connection2.setDoOutput(true);
            connection2.setRequestMethod("POST");
            connection2.setUseCaches(false);
            connection2.setRequestProperty("Content-type", 
                                           "text/xml; charset=utf-8");

            String xml_request = request;
            byte[] buffer = xml_request.getBytes("UTF-8");

            connection2.setRequestProperty("Content-Length", 
                                           String.valueOf(buffer.length));

            // POST the request to the connections OutputStream
            DataOutputStream toServer = 
                new DataOutputStream(connection2.getOutputStream());
            try {
                toServer.write(buffer, 0, buffer.length);
            } finally {
                toServer.close();
                toServer.flush();
            }

            // read back the repsonse to check if the post request was successful
            isXMLResponse = connection2.getInputStream();

            int charRead;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                charRead = isXMLResponse.read();
                if (charRead == -1)
                    break;

                baos.write(charRead);
            }

            response = baos.toString();
            isXMLResponse.close();
        } catch (MalformedURLException mue) {
            logger.severe("Exception::"+mue.getMessage());
        } catch (IOException ioe) {
            logger.severe("Exception::"+ioe.getMessage());
        } catch (Exception exe) {
            logger.severe("Exception::"+exe.getMessage());
        } finally {
            try {
                //    logger.info("inside finally requestFilePath  ::"+requestFilePath);
                writeOutputFile(response, requestFilePath + "_response.xml");
            } catch (Exception fileNotFoundException) {
                logger.severe("Exception::"+fileNotFoundException.getMessage());
            }
            return response;
        }
    }

    private StringBuffer createMultiPieceShipment(String carrierCode, 
                                                  String masterTrackingNumber, 
                                                  String masterFormID) {
        StringBuffer multiPieceShipment = new StringBuffer();
        multiPieceShipment.append("<MasterTrackingId>");
        if (carrierCode.equalsIgnoreCase("FDXG")) {
            multiPieceShipment.append("<TrackingIdType>GROUND</TrackingIdType>\n");
        } else {
            multiPieceShipment.append("<TrackingIdType>EXPRESS</TrackingIdType>\n");
        }
        multiPieceShipment.append("<FormId>" + masterFormID + "</FormId>\n");
        multiPieceShipment.append("<TrackingNumber>" + masterTrackingNumber + 
                                  "</TrackingNumber>\n");
        multiPieceShipment.append("</MasterTrackingId>");
        return multiPieceShipment;
    }

    private StringBuffer createInsuredValue(double insuredValue) {
        StringBuffer declaredValue = new StringBuffer();
        if (insuredValue != 0.0) {
            declaredValue.append("<InsuredValue>");
            declaredValue.append("<Currency>USD</Currency>");
            declaredValue.append("<Amount>" + insuredValue + "</Amount>");
            declaredValue.append("</InsuredValue>");
        }
        return declaredValue;
    }

    private void writeOutputFile(String str, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(str.getBytes());
        } catch (Exception ex) {
            logger.severe("Exception::"+ex.getMessage());
        } finally {
            try {
                fout.close();
            } catch (Exception ex) {
                logger.severe("Exception::"+ex.getMessage());
            }

        }


    }

//SC_6.7.1_Merge
private StringBuffer setCommercialInvoice() {
        StringBuffer commercialInvoice = new StringBuffer();
        
        commercialInvoice.append("<ShippingDocumentSpecification>");
        commercialInvoice.append("<ShippingDocumentTypes>COMMERCIAL_INVOICE</ShippingDocumentTypes>");
        commercialInvoice.append("<CommercialInvoiceDetail>");
        commercialInvoice.append("<Format>");
        commercialInvoice.append("<ImageType>PDF</ImageType>");
        commercialInvoice.append("<StockType>PAPER_LETTER</StockType>");
        commercialInvoice.append("</Format>");
        commercialInvoice.append("<CustomerImageUsages>");
        commercialInvoice.append("<Type>SIGNATURE</Type>");
        commercialInvoice.append("<Id>IMAGE_1</Id>");
        commercialInvoice.append("</CustomerImageUsages>");
        commercialInvoice.append("<CustomerImageUsages>");
        commercialInvoice.append("<Type>LETTER_HEAD</Type>");
        commercialInvoice.append("<Id>IMAGE_2</Id>");
        commercialInvoice.append("</CustomerImageUsages>");
        commercialInvoice.append("</CommercialInvoiceDetail>");
        commercialInvoice.append("</ShippingDocumentSpecification>");
        
        return commercialInvoice;
    }
    
    private StringBuffer setImporterDetail(String importerName,
                                    String importerCompName,
                                    String importerPhoneNum,
                                    String importerAddress1,
                                    String importerAddress2,
                                    String importerCity,
                                    String importerState,
                                    String importerPostalCode,
                                    String importerCountryCode,
                                    String impIntlSedNumber,
                                    String impIntlSedType)
        {
          StringBuffer importerDetail = new StringBuffer("");
          importerDetail.append("<ImporterOfRecord>");
            importerDetail.append("<AccountNumber></AccountNumber>");
          if (!(impIntlSedNumber.equalsIgnoreCase("") && impIntlSedNumber.equalsIgnoreCase(null))) {
              importerDetail.append("<Tins>");
              importerDetail.append("<TinType>"+impIntlSedType+"</TinType>");
              importerDetail.append("<Number>" + impIntlSedNumber + "</Number>");
              importerDetail.append("</Tins>");
          }
          importerDetail.append("<Contact>");
          importerDetail.append("<PersonName>" + 
                                         importerName + 
                                         "</PersonName>");
          importerDetail.append("<CompanyName>" + 
                                         importerCompName + 
                                         "</CompanyName>");
          importerDetail.append("<PhoneNumber>" + 
                                         importerPhoneNum + 
                                         "</PhoneNumber>");
          importerDetail.append("</Contact>");
          importerDetail.append("<Address>");
          importerDetail.append("<StreetLines>" + 
                                         importerAddress1 + 
                                         "</StreetLines>");
          importerDetail.append("<StreetLines>" + 
                                         importerAddress2 + 
                                         "</StreetLines>");
          importerDetail.append("<City>" + importerCity + 
                                         "</City>");
          importerDetail.append("<StateOrProvinceCode>" + 
                                         importerState + 
                                         "</StateOrProvinceCode>");
          importerDetail.append("<PostalCode>" + 
                                         importerPostalCode + 
                                         "</PostalCode>");
          importerDetail.append("<CountryCode>" + importerCountryCode + 
                                         "</CountryCode>");
          importerDetail.append("</Address>");
          importerDetail.append("</ImporterOfRecord>");     
            
        return importerDetail;
            
    }

    private StringBuffer setDeclarationStmt(String declarationStmt){
        StringBuffer declarationStmtDetail = new StringBuffer();
        if(declarationStmt.length() > 1){
        declarationStmtDetail.append("<DeclarationStatement>");    // By Jagadish for v12 from DeclarationStatment to DeclarationStatement ('e' in statement)
        declarationStmtDetail.append(declarationStmt);
        declarationStmtDetail.append("</DeclarationStatement>");   // By Jagadish for v12 from DeclarationStatment to DeclarationStatement ('e' in statement)
    }
    return declarationStmtDetail;
  }
  
    private StringBuffer setBrokerDetail(String brokerName,
                                        String brokerCompName,
                                        String brokerPhoneNum,
                                        String brokerAddress1,
                                        String brokerAddress2,
                                        String brokerCity,
                                        String brokerState,
                                        String brokerPostalCode,
                                        String brokerCountryCode)
        {
          StringBuffer brokerDetail = new StringBuffer("");
        brokerDetail.append("<Brokers>");           // By Jagadish. This is a mandatory tag for v12
          brokerDetail.append("<Type>IMPORT</Type>");           // By Jagadish. This is a mandatory tag for v12
          brokerDetail.append("<Broker>");       
         
          brokerDetail.append("<Contact>");
          brokerDetail.append("<PersonName>" + 
                                         brokerName + 
                                         "</PersonName>");
          brokerDetail.append("<CompanyName>" + 
                                         brokerCompName + 
                                         "</CompanyName>");
          brokerDetail.append("<PhoneNumber>" + 
                                         brokerPhoneNum + 
                                         "</PhoneNumber>");
          brokerDetail.append("</Contact>");
          brokerDetail.append("<Address>");
          brokerDetail.append("<StreetLines>" + 
                                         brokerAddress1 + 
                                         "</StreetLines>");
          brokerDetail.append("<StreetLines>" + 
                                         brokerAddress2 + 
                                         "</StreetLines>");
          brokerDetail.append("<City>" + brokerCity + 
                                         "</City>");
          brokerDetail.append("<StateOrProvinceCode>" + 
                                         brokerState + 
                                         "</StateOrProvinceCode>");
          brokerDetail.append("<PostalCode>" + 
                                         brokerPostalCode + 
                                         "</PostalCode>");
          brokerDetail.append("<CountryCode>" + brokerCountryCode + 
                                         "</CountryCode>");
          brokerDetail.append("</Address>");
          brokerDetail.append("</Broker>");     
        brokerDetail.append("</Brokers>");          // By Jagadish. This is a mandatory tag for v12
            
        return brokerDetail;
            
    }
    
    private StringBuffer createHazmatOp900(String hazmatSignatureName, String op900LabelFormat){
           StringBuffer hazmatOp900 = new StringBuffer();
           hazmatOp900.append("<ShippingDocumentSpecification>"+
             "<ShippingDocumentTypes>OP_900</ShippingDocumentTypes>"+
               "<Op900Detail>"+
                   "<Format>");
//                   if("PDF".equalsIgnoreCase(op900LabelFormat)){
                   hazmatOp900.append("<ImageType>PDF</ImageType>"+
                   "<StockType>OP_900_LL_B</StockType>");
//                   }else if("TEXT".equalsIgnoreCase(op900LabelFormat)){
//                       hazmatOp900.append("<ImageType>TEXT</ImageType>"+
//                       "<StockType>OP_900_LG_B</StockType>");
//                   }
        hazmatOp900.append(
                   "</Format>"+
                   "<SignatureName>"+hazmatSignatureName+"</SignatureName>\n" +
               "</Op900Detail>"+
             "</ShippingDocumentSpecification>");
           return hazmatOp900;
    }
    
    
  private static void disableSslVerification() {
      try
      {
          // Create a trust manager that does not validate certificate chains
          TrustManager[] trustAllCerts = new TrustManager[] 
          {new X509TrustManager() {
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                  return null;
              }
              public void checkClientTrusted(X509Certificate[] certs, String authType) {
              }
              public void checkServerTrusted(X509Certificate[] certs, String authType) {
              }
            public javax.net.ssl.X509KeyManager getX509KeyManager()
            {
              return null;
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, 
                                           String authType)
            {
            }
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, 
                                           String authType)
            {
            }
          }
          };

          // Install the all-trusting trust manager
          SSLContext sc = SSLContext.getInstance("SSL");
          try {
          sc.init(null, trustAllCerts, new java.security.SecureRandom());
          } catch (Exception e1) 
          {
            e1.printStackTrace();
          }
          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

          // Create all-trusting host name verifier
          HostnameVerifier allHostsValid = new HostnameVerifier() {
              public boolean verify(String hostname, SSLSession session) {
                  return true;
              }
          };

          // Install the all-trusting host verifier
          HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
      } catch (NoSuchAlgorithmException ne) {
          ne.printStackTrace();
      } //catch (KeyManagementException ke) {
       //   ke.printStackTrace();
     // } 
  catch (Exception e) {
          e.printStackTrace();
      }
  }
//Shiva added below method as formatter is removed
 /**Returns String value of the object or a space.
  * @param object Object of type Object class 
  * @return String equivalent of the object
  */
 public String nullStringToSpace(Object object) {
     String spcStr = "";
     if (object == null) {
         return spcStr;
     } else {
         return object.toString();
     }
 }


}
