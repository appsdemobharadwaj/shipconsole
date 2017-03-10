 /*
  * @(#)AascStampsShipment.java        01/11/2015
  * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
  * All rights reserved.
  @History
  01/11/2015  Mahesh V    Added files for Stamps.com Integration

  */

 package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.carrier.stampsws.proxy.Address;
import com.aasc.erp.carrier.stampsws.proxy.AuthenticateUserResponse;
import com.aasc.erp.carrier.stampsws.proxy.ContentTypeV2;
import com.aasc.erp.carrier.stampsws.proxy.CreateIndiciumResponse;
import com.aasc.erp.carrier.stampsws.proxy.Credentials;
import com.aasc.erp.carrier.stampsws.proxy.CustomsLine;
import com.aasc.erp.carrier.stampsws.proxy.CustomsV2;
import com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType;
import com.aasc.erp.carrier.stampsws.proxy.ImageType;
import com.aasc.erp.carrier.stampsws.proxy.LabelRecipientInfo;
import com.aasc.erp.carrier.stampsws.proxy.NonDeliveryOption;
import com.aasc.erp.carrier.stampsws.proxy.PackageTypeV6;
import com.aasc.erp.carrier.stampsws.proxy.PaperSizeV1;
import com.aasc.erp.carrier.stampsws.proxy.RateV14;
import com.aasc.erp.carrier.stampsws.proxy.ServiceType;
import com.aasc.erp.carrier.stampsws.proxy.ShipmentNotification;

import com.aasc.erp.carrier.stampsws.proxy.SwsimV37SoapClient;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.xml.ws.soap.SOAPFaultException;

//import javax.xml.rpc.soap.SOAPFaultException;


public class AascStampsShipment {

          AascShipmentHeaderInfo aascHeaderInfo = null;
         LinkedList linkedList = null; // linked list containing delivery or order packages information objects
         ListIterator listIterator = null; // list iterator used to traverse through packages linked list
         String service = "";
         AascShipmentPackageInfo aascPackageBean = null; // delivery package information bean
         String shipmentResponse = ""; // String Containg the shipment response xml file
         String parsedStatus = ""; // indicates the status of parsing
         static Logger logger = AascLogger.getLogger("AascStampsShipment");
         int responseStatus =  0; // holds value of 150 if valid response is returned and is successfully parsed else it holds 151
         String deliveryName = ""; // holds name of the delivery that is shipped
         String outputFilePath = ""; // holds folder name to which request and response are written 
         String protocol = "";
         String hostName = "";
         String integrationId = "";
         String userName = "";
         String password = "";
         String domain = "";
         String prefix = "";
         String shipToCompanyName = "";
         String shipToAddressLine1 = "";
         String shipToAddressLine2 = "";
         String shipToAddressLine3 = "";
         String shipToAddressCity = "";
         String shipToAddressPostalCode = "";
         String shipToCountry = "";
         String shipToAddressState = "";
         java.sql.Date shipDate = null;
         String shipMethodName = "";
         String connectshipSCS = "";
         String contactName = "";
         String phoneNumber = "";
         String carrierPayMethod = "";
         String carrierPayMethodCode = "";
         int carrierId = 0;
         String intlShipFlag = "";
         String countryCode = "";
         String labelFormat = ""; 
         String paperSize = "";
         String nonDelOption = "";
         String intlPrintlLayout = "";
         String normalPrintLayout = "";
         int carrierCode = 0;
         private String timeStampStr = null;
         String authenticatorResponse = "";
         String rateAuthenticator = "";
         String shipFromAuthenticator = "";
         String shipToAuthenticator = "";
         private AascIntlHeaderInfo aascIntlHeaderInfo = null;
         private String description = ""; //indicates description of item
         private String countryOfManufacture =  ""; //indicates country of manufacture of item
         private String harmonizedCode = ""; //indicates harmonized code of item
         private double weight; //indicates weight of item
         private String quantity ; //indicates quantity of item
         private String unitPrice = ""; //indicates unit price of item
         private String pkgWtUom = ""; //indicates export license number of the shipper
         RateV14 r1[] = null;
         CreateIndiciumResponse responseIndicium = null;
         CreateIndiciumResponse returnResponseIndicium = null;
         private String integratorTxId = "";
         String intFlag = "";
         String shipmentNotifyEmail = "";
         String labelReceiptEmail = "";
         LinkedList coList = null;
         LinkedList adonList = null;
         String packageType = "";
         String intlContentType = "";
         private String shipFromPhoneNumber1 = ""; 
         private String shipToPhoneNumber = "";
        String cloudLabelPath = "";
        private AascStampsXMLRequestWrapper request = null;
        
         public AascStampsShipment() {
         }
     /**   Method authenticator returns the authenticatorResponse response.
           @param  aascDeliveryInfo Bean to get the delivery details.
            @param aascShipMethodInfo Bean to get the shipmethod details.
            @return  authenticatorResponse String.
            We need to send this authenticatorResponse in the ship request.
            For every request we need to send the authenticatorResponse in the ship request.
            */
     public String authenticator(AascShipmentOrderInfo aascShipmentOrderInfo,AascShipMethodInfo aascShipMethodInfo){
        try{
         SwsimV37SoapClient stampsClient = new SwsimV37SoapClient();
         
         carrierId = aascShipmentOrderInfo.getShipmentHeaderInfo().getCarrierId();
     
         
          integrationId   = nullStrToSpc(aascShipMethodInfo.getIntegrationId(carrierId)); //"8af1d930-5101-49da-ac2b-ffcbc63a06f6";//
          userName = nullStrToSpc(aascShipMethodInfo.getCarrierUserName(carrierId));            
          password = nullStrToSpc(aascShipMethodInfo.getCarrierPassword(carrierId));  
          protocol = nullStrToSpc(aascShipMethodInfo.getProtocol(carrierId));
          hostName = nullStrToSpc(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));
          prefix   = nullStrToSpc(aascShipMethodInfo.getCarrierPrefix(carrierId));
          
          logger.info("integrationId in authenticator method==="+integrationId);
          logger.info("userName in authenticator method==="+userName);
          logger.info("password in authenticator method==="+password);

          logger.info("calling " + stampsClient.getEndpoint());
          Credentials credentials = new Credentials();
          credentials.setIntegrationID(integrationId);
          credentials.setUsername(userName);
          credentials.setPassword(password);

          stampsClient.setEndpoint(protocol +"://" + hostName + "/" + prefix);
          AuthenticateUserResponse res= new AuthenticateUserResponse();
          res = stampsClient.authenticateUser(credentials);
          authenticatorResponse = res.getAuthenticator();
         
     }catch(Exception e){
         logger.severe("Exception::"+e.getMessage());
     }
     return authenticatorResponse;
     }
     /**   Method processShipment to the ship request.
            @param  aascDeliveryInfo Bean to get the delivery details.
            @param aascShipMethodInfo Bean to get the ship method details.
            @param aascIntlInfo Bean to get the international details.
            @param aascProfileOptionsInfo Bean to get the profile option sinformation.
            @return  String response from the carrier.
            We need to send all the required fields to call the WSDL of stamps.com
            along with the authenticatorResponse from the authenticator method.
            */
     public String processShipment(AascShipmentOrderInfo aascDeliveryInfo, 
                                AascShipMethodInfo aascShipMethodInfo, 
                                AascIntlInfo aascIntlInfo, 
                                AascProfileOptionsBean aascProfileOptionsInfo, String labelpath) {

                                
         String printerPort = "";
         String residentialAddrFlag = "";
         String labelFormat = "";
         String shiptoInfoOnLabel="";
         String authenticatorResponse = "";
         authenticatorResponse = authenticator(aascDeliveryInfo, aascShipMethodInfo);
         timeStampStr = (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");   
         String intergationId = "";
         String userName = "";
         String password = "";
         String  status = "";
         
          try{
         aascHeaderInfo = aascDeliveryInfo.getShipmentHeaderInfo();
         linkedList = aascDeliveryInfo.getShipmentPackageInfo();
         outputFilePath = labelpath; // path from profile options to which request and response are written        
          listIterator  = linkedList.listIterator();
          while(listIterator.hasNext()){
              aascPackageBean = (AascShipmentPackageInfo)listIterator.next(); 
          }

             intFlag = 
                        nullStrToSpc(aascHeaderInfo.getInternationalFlag());
             
              if (aascDeliveryInfo != null && aascHeaderInfo != null && linkedList != null && aascShipMethodInfo != null) 
                {
                    intlShipFlag = nullStrToSpc(aascHeaderInfo.getIntlShipFlag());
                    intlShipFlag = nullStrToSpc(aascHeaderInfo.getInternationalFlag());
                    logger.info("intlShipFlag :: "+intlShipFlag);
                    deliveryName = nullStrToSpc(aascHeaderInfo.getOrderNumber());
                    carrierId = aascHeaderInfo.getCarrierId();
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getCustomerName()));
                    shiptoInfoOnLabel = nullStrToSpc(aascHeaderInfo.getShipToLocationName());
                    logger.info("shiptoInfoOnLabel in Stamps shipment::" + shiptoInfoOnLabel);
                    if (shiptoInfoOnLabel.equalsIgnoreCase("CUSTOMER NAME")) {
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()));
                    
                    } else if (shiptoInfoOnLabel.equalsIgnoreCase("SHIP TO LOCATION")) {
                    if (aascHeaderInfo.getShipToLocationName().length() > 32) {
                    shipToCompanyName = 
                    encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()).substring(0, 
                    32));
                    } else {
                    shipToCompanyName = 
                    encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()));
                    }
                    } else  if (shiptoInfoOnLabel.equalsIgnoreCase("CUSTOM")) {
                    if(("".equalsIgnoreCase(aascHeaderInfo.getShipToLocationName())) || aascHeaderInfo.getShipToLocationName().equalsIgnoreCase(null)){
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()));
                    }
                    else{
                    if(aascHeaderInfo.getShipToLocationName().length()>32){
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()).substring(0,32));
                    }
                    else {
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()));
                    }
                    }
                    } else {
                    
                    shipToCompanyName = encode(nullStrToSpc(aascHeaderInfo.getShipToLocationName()));
                    }
                    
                    logger.info("shipToCompanyName in Stamps shipment::" + 
                    shipToCompanyName);
              
              shipToAddressLine1 = encode(nullStrToSpc(aascHeaderInfo.getAddress()));
              shipToAddressLine2 = encode(nullStrToSpc(aascHeaderInfo.getShipToAddrLine2()));
              shipToAddressLine3 = encode(nullStrToSpc(aascHeaderInfo.getShipToAddrLine3()));
              shipToAddressCity = encode(nullStrToSpc(aascHeaderInfo.getShipToAddressCity()));
              shipToAddressPostalCode = nullStrToSpc(aascHeaderInfo.getPostalCode());
              String shipFromPostal = nullStrToSpc(aascHeaderInfo.getShipFromAddressPostalCode());
              shipToCountry = encode(nullStrToSpc(aascHeaderInfo.getShipToCountry()));
              shipToAddressState = encode(nullStrToSpc(aascHeaderInfo.getShipToAddressState()));
              shipDate = aascHeaderInfo.getShipmentDate();
              carrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
              labelFormat = aascShipMethodInfo.getPrinterPort(carrierId); // retreiving label format from shipMethod bean 
              printerPort = aascShipMethodInfo.getPrinterEODPort(carrierId);
              paperSize = aascShipMethodInfo.getStampsPaperSizeFormat(carrierId);
              System.out.println("paperSize---"+paperSize);
              nonDelOption = aascShipMethodInfo.getNonDeliveryOptionOrientation(carrierId);
              intlPrintlLayout = aascShipMethodInfo.getIntlPrintLayOut(carrierId);
              normalPrintLayout = aascShipMethodInfo.getLabelStockOrientation(carrierId);

              shipMethodName = nullStrToSpc(aascHeaderInfo.getShipMethodMeaning());
              connectshipSCS = nullStrToSpc(aascShipMethodInfo.getConnectShipScsTag(shipMethodName));
             
              contactName = encode(nullStrToSpc(aascHeaderInfo.getContactName()));
              phoneNumber = nullStrToSpc(aascHeaderInfo.getPhoneNumber());
              carrierPayMethod = nullStrToSpc(aascHeaderInfo.getCarrierPaymentMethod());
              carrierPayMethodCode = nullStrToSpc(aascShipMethodInfo.getCarrierPayCode(carrierPayMethod));
              residentialAddrFlag = nullStrToSpc(aascHeaderInfo.getResidentialFlag());
              protocol = nullStrToSpc(aascShipMethodInfo.getProtocol(carrierId));
              hostName = nullStrToSpc(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));            
              intergationId   = nullStrToSpc(aascShipMethodInfo.getIntegrationId(carrierId));            
              userName = nullStrToSpc(aascShipMethodInfo.getCarrierUserName(carrierId));            
              password = nullStrToSpc(aascShipMethodInfo.getCarrierPassword(carrierId));
              shipFromPhoneNumber1 = nullStrToSpc(aascHeaderInfo.getShipFromPhoneNumber1());
              shipToPhoneNumber = nullStrToSpc(aascHeaderInfo.getPhoneNumber());
 
             logger.info("shipMethodName ::: "+shipMethodName);
             String shipmentValidationCode= nullStrToSpc(aascShipMethodInfo.getShipmentValidationCode(shipMethodName));
             logger.info("shipmentValidationCode ::: "+shipmentValidationCode);

              SwsimV37SoapClient stampsShipClient = new SwsimV37SoapClient();
              RateV14 rate = new RateV14();
              rate.setAmount(new BigDecimal(0.0));
              service =aascShipMethodInfo.getConnectShipScsTag(shipMethodName);
              rate.setServiceType(new ServiceType(service));
              if(shipToAddressPostalCode.contains("-")){
              int index = shipToAddressPostalCode.indexOf("-");
              shipToAddressPostalCode = shipToAddressPostalCode.substring(0,index);
              }
              BigDecimal bd = new BigDecimal(aascPackageBean.getPackageDeclaredValue());
              if(("Y").equalsIgnoreCase(aascHeaderInfo.getInternationalFlag()) ){
              rate.setFromZIPCode(aascHeaderInfo.getShipFromAddressPostalCode());
              rate.setToZIPCode(aascHeaderInfo.getPostalCode());
              rate.setToCountry(aascHeaderInfo.getCountrySymbol());

              rate.setDeclaredValue(bd.setScale(2,BigDecimal.ROUND_UP));
              }
              
              if("LB".equalsIgnoreCase(aascPackageBean.getUom())){
              rate.setWeightLb(new Double(aascPackageBean.getWeight()));
              }else if("OZ".equalsIgnoreCase(aascPackageBean.getUom())){
              rate.setWeightOz(new Double(aascPackageBean.getWeight()));
              }
              
              if (aascPackageBean.getPackageLength() > 0.0 && aascPackageBean.getPackageWidth() > 0.0 && aascPackageBean.getPackageHeight() > 0.0){
                  packageType = "Package";
                  }
              else{
                  packageType = aascHeaderInfo.getPackaging();
                  }
              rate.setPackageType(new PackageTypeV6(packageType));
              
              rate.setLength(aascPackageBean.getPackageLength());
              rate.setWidth(aascPackageBean.getPackageWidth());
              rate.setHeight(aascPackageBean.getPackageHeight());

              Calendar cal = Calendar.getInstance();
              cal.setTime(aascHeaderInfo.getShipmentDate());
              rate.setShipDate(cal);
            
              Address shipFrom = new Address();
              
              shipFrom.setFullName(aascHeaderInfo.getShipFromContactName());
              shipFrom.setCompany(aascHeaderInfo.getShipFromCompanyName());

              shipFrom.setAddress1(aascHeaderInfo.getShipFromAddressLine1());
              
              shipFrom.setAddress2(aascHeaderInfo.getShipFromAddressLine2());
              shipFrom.setCity(aascHeaderInfo.getShipFromAddressCity());
              shipFrom.setState(aascHeaderInfo.getShipFromState());
              shipFrom.setZIPCode(aascHeaderInfo.getShipFromAddressPostalCode());
              shipFrom.setCountry(aascHeaderInfo.getShipFromCountry());
              shipFrom.setPhoneNumber(shipFromPhoneNumber1);
              
              Address shipTo = new Address();
 
              shipTo.setFullName(aascHeaderInfo.getContactName());
              shipTo.setCompany(aascHeaderInfo.getCustomerName());

              shipTo.setAddress1(aascHeaderInfo.getAddress());
              shipTo.setAddress2(aascHeaderInfo.getShipToAddrLine2());
              shipTo.setCity(aascHeaderInfo.getCity());
              if("Y".equalsIgnoreCase(aascHeaderInfo.getInternationalFlag())){
              shipTo.setProvince(aascHeaderInfo.getState());
               shipTo.setPostalCode(aascHeaderInfo.getPostalCode());
              }else{
              shipTo.setState(aascHeaderInfo.getState());
               shipTo.setZIPCode(aascHeaderInfo.getPostalCode());
              }
              shipTo.setCountry(aascHeaderInfo.getCountrySymbol());
              shipTo.setPhoneNumber(aascHeaderInfo.getPhoneNumber());

               intFlag = 
                  nullStrToSpc(aascHeaderInfo.getIntlShipFlag());
              CustomsV2 customs = new CustomsV2();
              if (("Y").equalsIgnoreCase(aascHeaderInfo.getInternationalFlag())) {
              try {
                  aascIntlHeaderInfo = aascIntlInfo.getIntlHeaderInfo();
                  coList = aascIntlInfo.getIntlCommodityInfo();
               } catch (Exception e) {
                  aascIntlHeaderInfo = new AascIntlHeaderInfo();
                  coList = new LinkedList();
               }
              
              
               ListIterator CoInfoIterator = coList.listIterator();
               int i=0;
                  LinkedList bean = new LinkedList(); 

                  while (CoInfoIterator.hasNext()) {
                  
                      AascIntlCommodityInfo aascIntlCommodityInfo = 
                          (AascIntlCommodityInfo)CoInfoIterator.next();
                          
                      description = encode(aascIntlCommodityInfo.getDescription()); 
                      quantity = aascIntlCommodityInfo.getQuantity();
                      logger.info("quantity:Shipment.java:"+quantity);

                      unitPrice = aascIntlCommodityInfo.getUnitPrice();
                      logger.info("unitPrice:Shipment.java:"+unitPrice);


                      weight = aascIntlCommodityInfo.getWeight();
                      logger.info("weight:Shipment.java:"+weight);


                      harmonizedCode = 
                             encode(aascIntlCommodityInfo.getHarmonizedCode());
                      logger.info("harmonizedCode:Shipment.java:"+harmonizedCode);


                      countryOfManufacture = 
                              aascIntlCommodityInfo.getCountryOfManufacture();
                      logger.info("countryOfManufacture:Shipment.java:"+countryOfManufacture);

                              
                      pkgWtUom = 
                              aascIntlCommodityInfo.getPackageWeightUom();
                      logger.info("pkgWtUom:Shipment.java:"+pkgWtUom);

                              
                              

                      CustomsLine cl = new CustomsLine();     
                      cl.setDescription(description);
                      cl.setQuantity(new Double(quantity));
                      cl.setValue(new BigDecimal(unitPrice));
                      if(pkgWtUom.equals("LBS")){
                      cl.setWeightLb(new Double(weight));
                      }
                      else if(pkgWtUom.equals("OZS")){
                          cl.setWeightOz(new Double(weight));
                      }
                      cl.setHSTariffNumber(harmonizedCode);
                      if(!"Select".equalsIgnoreCase(countryOfManufacture)){
                      cl.setCountryOfOrigin(countryOfManufacture);
                      }
                      try{
                      bean.add(cl);

                      i++;
                      }catch(Exception e){
                      logger.severe("Exception::"+e.getMessage());
                  }
                      
                  
                  }
             ListIterator iterator=bean.listIterator();
             CustomsLine c[] = new CustomsLine[i];
             int j=0;
             while(iterator.hasNext()){
                 c[j]=(CustomsLine)iterator.next();
                 j++;
             }
             intlContentType = aascIntlHeaderInfo.getIntlPurpose();
              
   
              customs.setContentType(new ContentTypeV2(intlContentType));
              
              customs.setComments(aascIntlHeaderInfo.getIntlComments());
              customs.setLicenseNumber(aascIntlHeaderInfo.getStampsLicenseNumber());
              customs.setCertificateNumber(aascIntlHeaderInfo.getStampsCertificateNumber());
              customs.setInvoiceNumber(aascIntlHeaderInfo.getIntlCustomerInvoiceNumber());
              customs.setOtherDescribe(aascIntlHeaderInfo.getIntlComments());
              customs.setCustomsLines(c);
              
              }
              ImageType imageType = null;
              if(!"Y".equalsIgnoreCase(aascHeaderInfo.getInternationalFlag())){
               imageType = new ImageType(labelFormat);
              }
              EltronPrinterDPIType eltronPrinterDPIType = new EltronPrinterDPIType("Default");

              if("".equalsIgnoreCase(shipmentNotifyEmail)){
                  shipmentNotifyEmail = "abc.xyz@gmail.com";
              }
              
                logger.info("shipment Notify Email::" + shipmentNotifyEmail);
              
              ShipmentNotification shipmentNotification = new ShipmentNotification();
              shipmentNotification.setEmail(shipmentNotifyEmail);
              shipmentNotification.setCCToAccountHolder(false);
              shipmentNotification.setCCToMain(false);
              shipmentNotification.setUseCompanyNameInFromLine(false);
              shipmentNotification.setUseCompanyNameInSubject(false);
              
              NonDeliveryOption nonDeliveryOption = new NonDeliveryOption(nonDelOption);
              PaperSizeV1 paperSizeV1 = new PaperSizeV1(paperSize);

              if("".equalsIgnoreCase(labelReceiptEmail)){
                  labelReceiptEmail = "xyz.abc@gmail.com";
                  
              }
                
                  logger.info("label Receipt Email::" + labelReceiptEmail);
                  
              LabelRecipientInfo LabelRecipientInfo = new LabelRecipientInfo();
              LabelRecipientInfo.setEmailAddress(labelReceiptEmail);
              LabelRecipientInfo.setName(aascHeaderInfo.getContactName());
              intergationId   = nullStrToSpc(aascShipMethodInfo.getIntegrationId(carrierId));  //"8af1d930-5101-49da-ac2b-ffcbc63a06f6";

             userName = nullStrToSpc(aascShipMethodInfo.getCarrierUserName(carrierId));            
             password = nullStrToSpc(aascShipMethodInfo.getCarrierPassword(carrierId));  
             protocol = nullStrToSpc(aascShipMethodInfo.getProtocol(carrierId));
             hostName = nullStrToSpc(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));
             prefix   = nullStrToSpc(aascShipMethodInfo.getCarrierPrefix(carrierId));
                     
             logger.info("calling " + stampsShipClient.getEndpoint());
             Credentials credentials = new Credentials();
             credentials.setIntegrationID(intergationId);
             credentials.setUsername(userName);
             credentials.setPassword(password);
             
              stampsShipClient.setEndpoint(protocol +"://" + hostName + "/" + prefix);
             
              
               responseIndicium = new CreateIndiciumResponse();
               returnResponseIndicium = new CreateIndiciumResponse();
              
               integratorTxId = deliveryName+"_"+timeStampStr;
                  request  = new AascStampsXMLRequestWrapper();
               if("Y".equalsIgnoreCase(aascHeaderInfo.getInternationalFlag())){
               try{
                imageType = new ImageType(labelFormat);
                rate.setPrintLayout(intlPrintlLayout);
                            //Mahesh Added Code to save the Request files
                            request.setCredentials(null);
                            request.setAuthenticator(authenticatorResponse);
                            request.setIntegratorTxID(integratorTxId);
                            request.setTrackingNumber("");
                            request.setRate(rate);
                            request.setFrom(shipFrom);
                            request.setTo(shipTo);
                            request.setCustomerID(null);
                            request.setCustoms(customs);
                            request.setSampleOnly(false);
                            request.setImageType(imageType);
                            request.setEltronPrinterDPIType(eltronPrinterDPIType);
                            request.setMemo("");
                            request.setCost_code_id(0);
                            request.setDeliveryNotification(false);
                            request.setShipmentNotification(shipmentNotification);
                            request.setRotationDegrees(0);
                            request.setHorizontalOffset(0);
                            request.setVerticalOffset(0);
                            request.setPrintDensity(0);
                            request.setPrintMemo(false);
                            request.setPrintInstructions(false);
                            request.setRequestPostageHash(false);
                            request.setNonDeliveryOption(nonDeliveryOption);
                            request.setRedirectTo(shipFrom);
                            request.setOriginalPostageHash("");
                            request.setReturnImageData(true);
                            request.setInternalTransactionNumber(null);
                            request.setPaperSize(paperSizeV1);
                            request.setEmailLabelTo(null);
                            
                            //------------------------------------------
                            
                            
                            try
                            {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            XMLEncoder xmlEncoder = new XMLEncoder(baos);
                            PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                            xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                            xmlEncoder.writeObject(request);
                            
                            xmlEncoder.close();
                            String xml = baos.toString();
                                            System.out.println("--------------------------------------------------------\n"+labelpath);
                            OutputStream outputStream = new FileOutputStream (labelpath +deliveryName + 
                                               "_Stamps_" + timeStampStr + 
                                               "_request.xml"); 
                            baos.writeTo(outputStream);
                            }
                            catch(Exception e) {
                            e.printStackTrace();
                            }
                            //------------------------------------------

              responseIndicium =   stampsShipClient.createIndicium(authenticatorResponse, null, integratorTxId, "", rate, shipFrom, shipTo, null, customs, false, imageType, eltronPrinterDPIType,
              "", 0, false, shipmentNotification, 0, 0, 0, 0, false, false, false, nonDeliveryOption, shipFrom, "", true, null, paperSizeV1, null);
              }catch(Exception e){
              status = "151@@"+e.getMessage();
               return status;
              }
           
              }
                           
             else{
             try{
     
             rate.setFromZIPCode(aascHeaderInfo.getShipFromAddressPostalCode());//(aascHeaderInfo.getShipFromAddressPostalCode());
             rate.setToZIPCode(shipToAddressPostalCode);
              rate.setPrintLayout(normalPrintLayout); //normalPrintLayout
              //Mahesh Added code to save the request files
                 request.setCredentials(null);
                 request.setAuthenticator(authenticatorResponse);
                 request.setIntegratorTxID(integratorTxId);
                 request.setTrackingNumber("");
                 request.setRate(rate);
                 request.setFrom(shipFrom);
                 request.setTo(shipTo);
                 request.setCustomerID(null);
                 request.setCustoms(customs);
                 request.setSampleOnly(false);
                 request.setImageType(imageType);
                 request.setEltronPrinterDPIType(eltronPrinterDPIType);
                 request.setMemo("");
                 request.setCost_code_id(0);
                 request.setDeliveryNotification(false);
                 request.setShipmentNotification(shipmentNotification);
                 request.setRotationDegrees(0);
                 request.setHorizontalOffset(0);
                 request.setVerticalOffset(0);
                 request.setPrintDensity(0);
                 request.setPrintMemo(false);
                 request.setPrintInstructions(false);
                 request.setRequestPostageHash(false);
                 request.setNonDeliveryOption(nonDeliveryOption);
                 request.setRedirectTo(shipFrom);
                 request.setOriginalPostageHash("");
                 request.setReturnImageData(true);
                 request.setInternalTransactionNumber(null);
                 request.setPaperSize(paperSizeV1);
                 request.setEmailLabelTo(null);

                 //------------------------------------------


                 try
                 {
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 XMLEncoder xmlEncoder = new XMLEncoder(baos);
                 PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                 xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                 xmlEncoder.writeObject(request);

                 xmlEncoder.close();
                 String xml = baos.toString();
                  logger.info("--------------------------------------cloudLabelPath------------------\n"+labelpath);
                 OutputStream outputStream = new FileOutputStream (labelpath +deliveryName + 
                                    "_Stamps_" + timeStampStr + 
                                    "_request.xml"); 
                 baos.writeTo(outputStream);
                 }
                 catch(Exception e) {
                 e.printStackTrace();
                 }
               //  ------------------------------------------              

            responseIndicium =   stampsShipClient.createIndicium(authenticatorResponse, null, integratorTxId, "", rate, shipFrom, shipTo, null, null, false, imageType, eltronPrinterDPIType,
              "", 0, false, shipmentNotification, 0, 0, 0, 0, false, false, false, nonDeliveryOption, shipFrom, "", true, null, paperSizeV1, null);
             }catch(SOAPFaultException e){
               status = "151@@"+e.getMessage();
               return status; 
            }

           }

              AascStampsShipmentInfo aascStampsShipmentInfo = new AascStampsShipmentInfo();
              parsedStatus = aascStampsShipmentInfo.parseResponse(responseIndicium, aascDeliveryInfo, aascShipMethodInfo, labelpath);


              }
             if (responseIndicium.getTrackingNumber() != "" && responseIndicium.getTrackingNumber() != null ) {
             responseStatus = 150;
             } else {
             responseStatus = 151;
             }               
         }
         catch(Exception e) {

             responseStatus = 151;
             aascHeaderInfo.setMainError("Exception: " + e.getMessage());
             logger.severe("Exception::"+e.getMessage());
         }
          if(responseStatus==150){
          return "150";
             }
          else{
          return "151";
          }
         
     }

     /** This method can replace the null values with nullString
      * @return String that is ""
      * @return obj-object of type Object
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
     * @param src String
     * @return src String
     */
     
      private String encode(String src) 
      {
            src = replaceStr(src, "&", "&amp;");
            src = replaceStr(src, "\"", "&quot;");
            src = replaceStr(src, "<", "&lt;");
            src = replaceStr(src, "\\", "&#092;");
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
 
 }
