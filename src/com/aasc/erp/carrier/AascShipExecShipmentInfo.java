package com.aasc.erp.carrier;
/*
 * @(#)AascShipExecShipmentInfo.java        11/01/2011
 * Copyright (c) 2014 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */



import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.carrier.AascBase64;
import com.aasc.erp.util.AascLogger;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;



import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.PackageResponse;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Package;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipResponse;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ShipmentResponse;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDocument;


/**
 * AascShipExecShipmentInfo Class is used to parse the response and
 * set the parsed data to header bean and package bean.
 * @author    
 * @version - 
 * 
 *
 * 
 */
  
public class AascShipExecShipmentInfo {

    public AascShipExecShipmentInfo() {
    }
    private static Logger logger = AascLogger.getLogger("AascShipExecShipmentInfo");
    private String displayMessage = "";
    
    private AascShipmentHeaderInfo aascHeaderInfo = null;
    private AascShipmentPackageInfo aascPackageInfo = null;
    private LinkedList packageList = null;
    private ListIterator packageListIterator = null;
    private int packageSize = 0;
    private String labelPath = "";
    private String responseStatus = "";
    private String unescapedTotalShipmentCharges = "";
    private String baseChargeStr = "";
    private String specialCharges="";
    private String specialChargesStr="";
    private String pkgBaseChargeStr = "";
    private String pkgSpecialChargesStr = "";
    Double packageBaseCharges=0.0;
    private Double listRates=0.0;
    private String wayBillNumber = "";
    private AascProfileOptionsBean aascProfileOptionsInfo = null;
    private String label = "";
    private byte[] parsedLabel;
    //For International document Commvercial Invoice saving
    private String CIintlDoc="";
    private String deliveryName;
    private FileOutputStream fout = null;
    private byte[] parsedIntlDoc;
    
  // NAFTA certificate variables
    private String NAFTAintlDoc="";
    private byte[] parsedIntlNAFTADoc;
    
  // US certificate of origin variables  
   private String USCOintlDoc="";
   private byte[] parsedIntlUSCODoc;
  // For Return Shipment
  
  private String returnTrackingNumber="";
  private String returnLabel = "";
  private byte[] parsedReturnLabel;
    private String timeStampStr = 
        (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");
  int carrierId = 0;
  int carrierCode=0;
  private String labelFormat="";
    private String bolFormat="";
  String documentFormat="";
  private String shipExecNegotiatedRates = "";
  
  // BOL document variables
    private String bolDoc="";
    private byte[] parsedBOLDoc;
    /**
      * parseResponse method is used to parse the xml response and 
      * set the parsed data to corresponding headers and package bean.
      * @param shipmentResponse String containing shipmentResponse which needs to be parsed
      * @param aascDeliveryInfo AascDeliveryInfo bean object to retreive header and package bean 
      * to which parsed data is set
      * @return displayMessage String which contains response status
      *
    */
    String parseResponse(ShipResponse shipmentResponse, AascShipmentOrderInfo aascShipmentOrderInfo,AascShipMethodInfo aascShipMethodInfo, String cloudLabelPath)
    {
        logger.info("parsing the response in AascShipExecShipmentInfo");
        aascProfileOptionsInfo = new AascProfileOptionsBean();
//        String cloudLabelPath=(String)session.getAttribute("cloudLabelPath");
//                    logger.info("cloudLabelPath in aascIntlDocViewPrint "+cloudLabelPath);
                    labelPath=cloudLabelPath;
//        labelPath = nullStrToSpc(aascProfileOptionsInfo.getLabelPath());
        logger.info("label path : "+labelPath);
      carrierId = aascShipmentOrderInfo.getShipmentHeaderInfo().getCarrierId();
      labelFormat=aascShipMethodInfo.getPrinterPort(carrierId);
      logger.info("labelFormat :: "+labelFormat);
//        bolFormat=nullStrToSpc(aascShipMethodInfo.getBolFormat(carrierId));
        logger.info("bolFormat :: "+bolFormat);
//      shipExecNegotiatedRates = aascShipMethodInfo.getShipExecNegotiatedRates(carrierId);
//      logger.info("shipExecNegotiatedRates :: "+shipExecNegotiatedRates);
        try 
        {
            aascHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo();
          int carrierId = aascHeaderInfo.getCarrierId();
          carrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
          String intlDocSubType = nullStrToSpc(aascShipMethodInfo.getIntlDocSubTypeValue(carrierId));
          logger.info("intlDocSubType::::::"+intlDocSubType);
            packageList = aascShipmentOrderInfo.getShipmentPackageInfo();
            packageSize = packageList.size();
            logger.info("packageSize :: "+packageSize);
            
            /* Parsing the response according to the response status*/
            //Gururaj added code saving of java xmls for ShipExec
             try
             {
                 deliveryName = aascHeaderInfo.getOrderNumber();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             XMLEncoder xmlEncoder = new XMLEncoder(baos);
             PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
             xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
             xmlEncoder.writeObject(shipmentResponse);
             xmlEncoder.close();
             String xml = baos.toString();
                
             //                System.out.println("--------------------------------------------------------\n"+xml);
             OutputStream outputStream = new FileOutputStream (cloudLabelPath +deliveryName + 
                                     "_ShipExec_" + timeStampStr + 
                                     "_response.xml"); 
             baos.writeTo(outputStream);
             }
             catch(Exception e) {
                 e.printStackTrace();
             }
             //Gururaj code end
            ShipmentResponse parseShipmentResponse=shipmentResponse.getShipResult();
            logger.info("shipmentResponse.getShipResult()::::::::"+shipmentResponse.getShipResult());
            Package packageResponseObject=parseShipmentResponse.getDef_attr();
            responseStatus=packageResponseObject.getError_message();
            logger.info("responseStatus of the transaction "+responseStatus);
            packageListIterator = packageList.listIterator();
            if ("No error".equalsIgnoreCase(responseStatus))
            {
                // vikas added code to fix issue #3446
                    aascHeaderInfo.setErrorChk("");
                    aascHeaderInfo.setError("");
                    aascHeaderInfo.setMainError("");
                    // vika code ended
                logger.info("Shipment is success and contiue to parse the response");
                try
                {
                    unescapedTotalShipmentCharges=packageResponseObject.getTotal().toString();
                    logger.info("Negotiated rates :: Total :: "+unescapedTotalShipmentCharges);
                    baseChargeStr = packageResponseObject.getBase_charge().toString();
                    logger.info("baseChargeStr :: "+baseChargeStr);
                    try
                    {
                      specialChargesStr = packageResponseObject.getSpecial().toString();
                      logger.info("specialChargesStr :: "+specialChargesStr);
                      
                     
                    }
                    catch(Exception e)
                    {
                      specialChargesStr= "0.0";
                      logger.info("In catch as shipment special charges are not retunred specialChargesStr :: "+specialChargesStr);
                    }
                  listRates = Double.parseDouble(baseChargeStr) + Double.parseDouble(specialChargesStr);
                  logger.info("listRates :: "+listRates);
                    
                    //Setting the total shipment cost to header bean to display in the shipment page
                  aascHeaderInfo.setShipmentCost(Double.parseDouble(unescapedTotalShipmentCharges));
                    //If the check box is checked in carrier configuration then we are saving the negotiated rates in shipment page
                    if("Y".equalsIgnoreCase(shipExecNegotiatedRates))
                    {
                      aascHeaderInfo.setShipmentCost(Double.parseDouble(unescapedTotalShipmentCharges));
                      aascHeaderInfo.setFreightCost(Double.parseDouble(unescapedTotalShipmentCharges));
                    }
                    else
                    {
                      aascHeaderInfo.setShipmentCost(listRates);
                      aascHeaderInfo.setFreightCost(listRates);
                    }
                }
                catch(Exception e) {
                    aascHeaderInfo.setShipmentCost(0.0);
                    aascHeaderInfo.setFreightCost(0.0);
                }
                //Getting the package reponse object in reponse 
                PackageResponse packageResponse[]=parseShipmentResponse.getPackages();
                for(int i=0;i<parseShipmentResponse.getPackages().length;i++) 
                {
                    //Getting the tracking number of each package
                    wayBillNumber = packageResponse[i].getTracking_number();
                    logger.info("tracking_number ::"+packageResponse[i].getTracking_number());
                    if(i==0)
                    {
                        aascHeaderInfo.setWayBill(wayBillNumber);
                    }    
                    aascPackageInfo = (AascShipmentPackageInfo) packageListIterator.next();
                    aascPackageInfo.setTrackingNumber(nullStrToSpc(wayBillNumber));
                    Long msnNodeList= packageResponse[i].getGlobal_msn();
                    aascPackageInfo.setMsn(msnNodeList.intValue());
                    try
                    {
                      pkgBaseChargeStr  = packageResponse[i].getApportioned_base().toString();
                      logger.info("pkgBaseChargeStr :: "+pkgBaseChargeStr);
                      try
                      {
                        pkgSpecialChargesStr =packageResponse[i].getApportioned_special().toString();
                        logger.info("pkgSpecialChargesStr :: "+pkgSpecialChargesStr);
                      }
                      catch(Exception e )
                      {
                        pkgSpecialChargesStr="0.0";
                        logger.info("In catch as package special charges are not retunred pkgSpecialChargesStr :: "+pkgSpecialChargesStr);
                      }
                      packageBaseCharges = Double.parseDouble(pkgBaseChargeStr)+Double.parseDouble(pkgSpecialChargesStr);
                      logger.info("packageBaseCharges :: "+packageBaseCharges);
                      
//                       AascShipmentPackageInfo.setPkgCost(Double.valueOf(packageResponse[i].getApportioned_total().toString()));
//                       logger.info("After shipping package "+i+" The negotiated charge in package "+aascPackageInfo.getPkgCost());
                      aascPackageInfo.setBaseCharge(packageBaseCharges);
                      logger.info("After shipping package "+i+" The Base charge in package "+aascPackageInfo.getBaseCharge());
                      //If the check box is checked in carrier configuration then we are saving the negotiated rates in package table
                      if("Y".equalsIgnoreCase(shipExecNegotiatedRates))
                      {
                        aascPackageInfo.setBaseCharge(Double.valueOf(packageResponse[i].getApportioned_total().toString()));
                      }
                      else
                      {
                        aascPackageInfo.setBaseCharge(packageBaseCharges);
                      }
                      
                    }
                    catch (Exception ex)
                    {
                       aascPackageInfo.setPkgCost(0.0);
                    }
                    try
                    {
                      aascPackageInfo.setHazMatCharges(Double.parseDouble(packageResponse[i].getHazmat_fee().toString()));
                      logger.info("After shipping package "+i+" The Special charge in package "+ aascPackageInfo.getHazMatCharges());
                    }
                    catch(Exception ex)
                    {
                      aascPackageInfo.setHazMatCharges(0.0);
                    }
                    logger.info("aascPackageInfo.gettrackingno:" + aascPackageInfo.getTrackingNumber());
                    logger.info("aascPackageInfo.getmsnno:" +aascPackageInfo.getMsn());
                    logger.info("aascPackageInfo.getPackageCost:" + aascPackageInfo.getPkgCost());
                    //Getting the labels from the response
                    SoxDocument soxDocument[]=new  SoxDocument[packageResponse[i].getDocuments().length];
                    logger.info("soxDocument.length ::"+soxDocument.length);
                    for(int j=0;j<soxDocument.length;j++) 
                    {
                       soxDocument[j]=packageResponse[i].getDocuments()[j];
                      String DocFormat_FriendlyName=soxDocument[j].getDocFormat_FriendlyName();
                      logger.info("DocFormat_FriendlyName ::"+DocFormat_FriendlyName);
                      
                      String local_printer_port=soxDocument[j].getLocal_printer_port();
                      logger.info("local_printer_port :: "+local_printer_port);
                      
                      documentFormat=soxDocument[j].getDocumentFormat();
                      logger.info("documentFormat :: "+documentFormat);
//                    vikas commented code to fix issue #3310
//                         String error = soxDocument[j].getError_message();
//                        logger.info(error);
//                        if(error!=null){
//                            aascHeaderInfo.setMainError(documentFormat+"  "+error); 
//                        }
//                      vikas comments ended
                        
                       try
                       {
                         if("Commercial Invoice - Standard (Enhanced)".equalsIgnoreCase(DocFormat_FriendlyName)
                          || "DHL Commercial Invoice - Standard".equalsIgnoreCase(DocFormat_FriendlyName)
                          || "TANDATA_COMMERCIAL_INVOICE.STANDARD_1".equalsIgnoreCase(documentFormat) // dover server
                          || "CONNECTSHIP_DHL_COMMERCIAL_INVOICE.STANDARD".equalsIgnoreCase(documentFormat) //DEMO server
                          || "CUSTOM_CI_ALL.STANDARD".equalsIgnoreCase(documentFormat)
                          )   
                         {
                            try {
                                CIintlDoc=soxDocument[j].getPDFs()[0];
                            } catch ( Exception e) {
                                logger.info("Commercial Invoice not returned as RAW Printer Language");
                                CIintlDoc=soxDocument[j].getImages()[0];
                            }
                           //logger.info(intlDoc);
                         }
                       }
                      catch(Exception e) 
                      {
                          //e.printStackTrace();
                          logger.info(e.getMessage());
                      }
                       try
                       {
                         if("Custom NAFTA Certificate of Origin - Standard".equalsIgnoreCase(DocFormat_FriendlyName)
                         || "NAFTA Certificate of Origin - Standard".equalsIgnoreCase(DocFormat_FriendlyName)
                         || "CUSTOM_NAFTA_CO.STANDARD".equalsIgnoreCase(documentFormat) // dover
                         || "TANDATA_NAFTA.STANDARD".equalsIgnoreCase(documentFormat))
                         {
                            try {
                                NAFTAintlDoc=soxDocument[j].getPDFs()[0];
                            } catch (Exception e) {
                                logger.info("NAFTA Certificate of Origin is not returned as RAW Printer Language");
                           NAFTAintlDoc=soxDocument[j].getImages()[0];
                            }                                
                           //logger.info(NAFTAintlDoc);
                         }
                       }
                      catch(Exception e) 
                      {
                          //e.printStackTrace();
                          logger.info(e.getMessage());
                      }
                      // IF the response contains US cerificate of origin then getting it and storing them in variables
                      try
                      {
                        if("Certificate of Origin - Standard (Enhanced - Num. Boxes Column)".equalsIgnoreCase(DocFormat_FriendlyName)
                        || "TANDATA_CERTIFICATE_OF_ORIGIN.STANDARD1".equalsIgnoreCase(documentFormat) // dover
                       ) 
                        {
                            try {
                                USCOintlDoc=soxDocument[j].getPDFs()[0];
                            } catch (Exception e) {
                                logger.info("Certificate of Origin is not returned as RAW Printer Language");
                          USCOintlDoc=soxDocument[j].getImages()[0];
                            }
                          
//                          logger.info(USCOintlDoc);
                        }
                      }
                      catch(Exception e)
                      {
                         //e.printStackTrace();
                         logger.info(e.getMessage());
                      }
                      
                      
                      
                      
                      
                      if("ZPL".equalsIgnoreCase(labelFormat) || "EPL".equalsIgnoreCase(labelFormat))
                      {
                          
                if("CONNECTSHIP_UPS_MAXICODE_SHIPMENT_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
                || "CONNECTSHIP_UPS_MAXICODE_PACKAGE_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
                || "TANDATA_STDLABEL.STANDARD".equalsIgnoreCase(documentFormat)
                || "CONNECTSHIP_LTL_LABEL_PACKAGE.STANDARD".equalsIgnoreCase(documentFormat)
                || "CONNECTSHIP_DHL_LABEL.STANDARD".equalsIgnoreCase(documentFormat)) {
                
                
                       try
                       {  
                        logger.info("For ZPL Code");
                           logger.info("Label :"+label);
                           label=soxDocument[j].getRawPrinterLanguage()[0];
                           logger.info(" label :\\n"+label);
                       }
                       catch(Exception e) 
                       {
                            try {
                            label=soxDocument[j].getImages()[0];
                            } catch (Exception e1) {
                                e.printStackTrace();
                            }
                       }
                       }
                       else
                       {
//                         try
//                         {  
//                          logger.info("For ZPL in else for LTL");
//                             logger.info("Label :"+label);
//                             label=soxDocument[j].getRawPrinterLanguage()[0];
//                             logger.info(" label :\\n"+label);
//                         }
//                         catch(Exception e) 
//                         {
//                              try {
//                              label=soxDocument[j].getImages()[0];
//                              } catch (Exception e1) {
//                                  //e.printStackTrace();
//                              }
//                         }
                       }
                       if(carrierCode==999)
                       {
                        if(bolFormat.equalsIgnoreCase(documentFormat))
                        {
                          try
                          {
                           
                            try
                            {
                              bolDoc=soxDocument[j].getPDFs()[0];
                            }
                            catch(Exception e)
                            {
                              logger.info("BOL document is not returned as PDF");
                              bolDoc=soxDocument[j].getImages()[0]; 
                            }
                          }
                          catch(Exception e)
                          {
                              // vikas added code to fix issue #3310
//                                  String error = soxDocument[j].getError_message();
//                                  logger.info(error);
//                                  if(error!=null){
//                                      aascHeaderInfo.setMainError(documentFormat+"  "+error); 
//                                  }
                                  // vikas code ended
                            logger.info(e.getMessage());
                          }
                        }
                       }
                      }
                      else
                      {
                        if(carrierCode!=999)
                        {
                              
                          if("CONNECTSHIP_UPS_MAXICODE_SHIPMENT_LABEL.STANDARD".equalsIgnoreCase(documentFormat) || 
                          "CONNECTSHIP_UPS_MAXICODE_PACKAGE_LABEL.STANDARD".equalsIgnoreCase(documentFormat) ||
                          "CONNECTSHIP_DHL_LABEL.STANDARD".equalsIgnoreCase(documentFormat)
                          )
                          {
                           try
                           { 
                               label=soxDocument[j].getImages()[0];
                           }
                           catch(Exception e) 
                           {
                            try {
                                label=soxDocument[j].getRawPrinterLanguage()[0];
                            } catch (Exception e1) {
                                logger.info(e1.getMessage());
                            } 
                           }
                          } 
                        }
                        else
                        {
//                          if(carrierCode==999)
//                          {
//                            if("CONNECTSHIP_LTL_LABEL_PACKAGE.STANDARD".equalsIgnoreCase(documentFormat) ||
//                                "TANDATA_STDLABEL.STANDARD".equalsIgnoreCase(documentFormat))
//                            {
//                              try
//                              { 
//                                  label=soxDocument[j].getImages()[0];
//                              }
//                              catch(Exception e) 
//                              {
//                                  //e.printStackTrace();
//                                   logger.info(e.getMessage());
//                              }
//                            }
//                            if(bolFormat.equalsIgnoreCase(documentFormat))
//                            {
//                              try
//                              {
//                                try
//                                {
//                                  bolDoc=soxDocument[j].getPDFs()[0];
//                                }
//                                catch(Exception e)
//                                {
//                                  logger.info("BOL document is not returned as PDF");
//                                  bolDoc=soxDocument[j].getImages()[0]; 
//                                }
//                              }
//                              catch(Exception e)
//                              {
//                              // vikas added code to fix issue #3310
////                                  String error = soxDocument[j].getError_message();
////                                  logger.info(error);
////                                  if(error!=null){
////                                      aascHeaderInfo.setMainError(documentFormat+"  "+error); 
////                                  }
//                                  // vikas code ended
//                                logger.info(e.getMessage());
//                              }
//                            }
//                          }
                        }
                      }
                      
                    }
                    // Saving the normal label for shipment
                    try
                    {
                        if (!(label.equals("")) && label != null)
                        {
                            parsedLabel = AascBase64.decode(label);
                            logger.info("labelPath ::: "+labelPath);
                            String utf16LeLabel = null;
                            try {
                              if("ZPL".equalsIgnoreCase(labelFormat) )
                              {
//                                utf16LeLabel = new String (parsedLabel,"UTF-16LE"); //UTF
                                logger.info("in zpl if");
                                  logger.info("carrierCode::"+carrierCode);

                                 utf16LeLabel=label;
                              }  
                            
                            } catch (Exception le) {
                                logger.info("Exception Generated while parsing the ShipExec Label from UTF 16 LE to UTF 8");
                            }
                            if (utf16LeLabel != null) {
                              if(carrierCode==114 || carrierCode==999)
                              {
//                                writeOutputFile(utf16LeLabel.getBytes("UTF-8"), labelPath + aascPackageInfo.getMsn());
                                  writeOutputFile(parsedLabel, labelPath + aascPackageInfo.getMsn());
                              }
                              else
                              {
//                                writeOutputFile(utf16LeLabel.getBytes("UTF-8"), labelPath + aascPackageInfo.getTrackingNumber());
                                  writeOutputFile(parsedLabel, labelPath +aascPackageInfo.getTrackingNumber());
                              }
                                logger.info("In the Label saving where the label is converted from UTF-16 LE to UTF-8");
                            } else {
                              if(carrierCode==114 || carrierCode==999)
                              {
                                writeOutputFile(parsedLabel, labelPath + aascPackageInfo.getMsn());
                              }
                              else
                              {
                                writeOutputFile(parsedLabel, labelPath +aascPackageInfo.getTrackingNumber());
                              }
                                logger.info("Normal Label Saving");
                            }
                           
                        }
                    }
                    catch (FileNotFoundException fileNotFoundException)
                    {
                      logger.severe("file path to which  label needs to be written is not found" + 
                                    "\n file name:" + labelPath + 
                                    aascPackageInfo.getTrackingNumber());             
                        fileNotFoundException.printStackTrace();
                    }
                    if (!(CIintlDoc.equals("")) && CIintlDoc != null)
                    {
                      parsedIntlDoc = AascBase64.decode(CIintlDoc);
                      deliveryName = aascHeaderInfo.getOrderNumber();
                      logger.info("delivery name :: "+deliveryName);
                      Date shippedDate = aascHeaderInfo.getShipmentDate();
                      logger.info("shippedDate ::"+shippedDate);
                      String shippedDateStr = shippedDate.toString().replaceAll("-", "_");
                      logger.info("shippedDateStr :: "+shippedDateStr);
                      String labelPathIntlDoc = "";
                      logger.info("shippedDate==" + shippedDate);
                      logger.info("deliveryName==" + deliveryName);
                      logger.info("timeStampStr==" + timeStampStr);
                      labelPathIntlDoc = labelPath + "intlDocs/";
                      logger.info("labelPathIntlDoc==" + labelPathIntlDoc);
                      String upsIntlDoc = deliveryName + "_" + shippedDateStr + "_CI";
                      logger.info("upsIntlDoc==" + upsIntlDoc);
                      
                      try
                      {
                        fout = new FileOutputStream(labelPathIntlDoc + upsIntlDoc);
                        fout.write(parsedIntlDoc);
                        fout.close();
                        logger.info("after writing label to file:" + upsIntlDoc);
                        
                      }
                      catch(FileNotFoundException fileNotFoundException)
                      {
                        logger.severe("file path to which label needs to be written is not found" + "\n file name:" + upsIntlDoc);  
                      }
                      
                      
                    }
                    //Saving the NAFTA certificate of if it is not null
                    if (!(NAFTAintlDoc.equals("")) && NAFTAintlDoc != null)
                    {
                      logger.info("Inside saveing of NAFTA certificate");
                      parsedIntlNAFTADoc = AascBase64.decode(NAFTAintlDoc);  
                      deliveryName = aascHeaderInfo.getOrderNumber();
                      logger.info("delivery name :: "+deliveryName);
                      Date shippedDate = aascHeaderInfo.getShipmentDate();
                      logger.info("shippedDate ::"+shippedDate);
                      String shippedDateStr = shippedDate.toString().replaceAll("-", "_");
                      logger.info("shippedDateStr :: "+shippedDateStr);
                      String labelPathIntlDoc = "";
                      logger.info("shippedDate==" + shippedDate);
                      logger.info("deliveryName==" + deliveryName);
                      logger.info("timeStampStr==" + timeStampStr);
                      labelPathIntlDoc = labelPath + "intlDocs/";
                      logger.info("labelPathIntlDoc==" + labelPathIntlDoc);
                      String upsIntlDoc = deliveryName + "_" + shippedDateStr + "_NAFTA";
                      logger.info("upsIntlDoc==" + upsIntlDoc);
                                                
                      try
                      {
                        fout = new FileOutputStream(labelPathIntlDoc + upsIntlDoc);
                        fout.write(parsedIntlNAFTADoc);
                        fout.close();
                        logger.info("after writing label to file:" + upsIntlDoc);
                      }
                      catch(FileNotFoundException fileNotFoundException)
                      {
                        logger.severe("file path to which label needs to be written is not found" + "\n file name:" + upsIntlDoc);  
                      }
                                            
                    }
                    //Saving the US certificate of if it is not null
                    if (!(USCOintlDoc.equals("")) && USCOintlDoc != null)
                    {
                      logger.info("Inside saveing of US certificate of origin");
                      parsedIntlUSCODoc = AascBase64.decode(USCOintlDoc);  
                      deliveryName = aascHeaderInfo.getOrderNumber();
                      logger.info("delivery name :: "+deliveryName);
                      Date shippedDate = aascHeaderInfo.getShipmentDate();
                      logger.info("shippedDate ::"+shippedDate);
                      String shippedDateStr = shippedDate.toString().replaceAll("-", "_");
                      logger.info("shippedDateStr :: "+shippedDateStr);
                      String labelPathIntlDoc = "";
                      logger.info("shippedDate==" + shippedDate);
                      logger.info("deliveryName==" + deliveryName);
                      labelPathIntlDoc = labelPath + "intlDocs/";
                      logger.info("labelPathIntlDoc==" + labelPathIntlDoc);
                      String upsIntlDoc = deliveryName + "_" + shippedDateStr + "_USCO";
                      logger.info("upsIntlDoc==" + upsIntlDoc); 

                      try
                      {
                        fout = new FileOutputStream(labelPathIntlDoc + upsIntlDoc);
                        fout.write(parsedIntlUSCODoc);
                        fout.close();
                        logger.info("after writing label to file:" + upsIntlDoc);
                      }
                      catch(FileNotFoundException fileNotFoundException)
                      {
                        logger.severe("file path to which label needs to be written is not found" + "\n file name:" + upsIntlDoc);  
                      }
                    }
                  //Saving the BOL document if it is not null 
                  if (!(bolDoc.equals("")) && bolDoc != null)
                  {
                    logger.info("Inside saving of BOL document");
                    parsedBOLDoc = AascBase64.decode(bolDoc);  
                    deliveryName = aascHeaderInfo.getOrderNumber();
                    logger.info("delivery name :: "+deliveryName);
                    Date shippedDate = aascHeaderInfo.getShipmentDate();
                    logger.info("shippedDate ::"+shippedDate);
                    String shippedDateStr = shippedDate.toString().replaceAll("-", "_");
                    logger.info("shippedDateStr :: "+shippedDateStr);
                    String labelPathIntlDoc = "";
                    logger.info("shippedDate==" + shippedDate);
                    logger.info("deliveryName==" + deliveryName);
                    logger.info("labelPath ==" + labelPath);
                    String bolDoc = deliveryName + "_" + shippedDateStr + "_BOL";
                    logger.info("bolDoc ==" + bolDoc);
                    try
                    {
                          fout = new FileOutputStream(labelPath + bolDoc);
                          fout.write(parsedBOLDoc);
                          fout.close();
                          logger.info("after writing label to file:" + bolDoc);
                    }
                    catch(FileNotFoundException fileNotFoundException)
                    {
                          logger.severe("file path to which label needs to be written is not found" + "\n file name:" + bolDoc);  
                    }
                  }
                    
                  
                    
                }
                displayMessage = "success";  
            }
            else 
            {
                logger.info("Shipment has failed");
                logger.info("responseStatus in error case :: "+responseStatus);
                displayMessage = "Error in Shipping..."+responseStatus;  
                aascHeaderInfo.setErrorChk(displayMessage);
                aascHeaderInfo.setError(displayMessage);
                aascHeaderInfo.setMainError(displayMessage);
            }
    
        }
        catch (Exception exp)
        {
            logger.severe("Exception::"+exp.getMessage());
            displayMessage = "Error in parsing or setting the values to bean !";
        }
        return displayMessage;
    }
  /**
   * parseResponse method is used to parse the xml response and 
   * set the parsed data to corresponding headers and package bean.
   * @param shipmentResponse String containing shipmentResponse which needs to be parsed
   * @param aascDeliveryInfo AascDeliveryInfo bean object to retreive header and package bean 
   * to which parsed data is set
   * @return displayMessage String which contains response status
   */
  String parseReturnShipmentResponse(ShipResponse shipmentResponse, 
                                     AascShipmentPackageInfo packageInfo, AascShipmentOrderInfo aascShipmentOrderInfo,AascShipMethodInfo aascShipMethodInfo, String cloudLabelPath)
   {
     logger.info("parsing the return shipment response");
     
    aascHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo();
     aascProfileOptionsInfo = new AascProfileOptionsBean();
     labelPath = cloudLabelPath;//nullStrToSpc(aascProfileOptionsInfo.getLabelPath());
     System.out.println("labelPath:rtn::"+labelPath);
    //Gururaj added code saving of java xmls for ShipExec return shipment
     try
     {
         deliveryName = aascHeaderInfo.getOrderNumber();
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     XMLEncoder xmlEncoder = new XMLEncoder(baos);
     PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
     xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
     xmlEncoder.writeObject(shipmentResponse);
     xmlEncoder.close();
//     String xml = baos.toString();
     //                System.out.println("--------------------------------------------------------\n"+xml);
     OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                             "_ShipExec_" + timeStampStr + "_Return"+
                             "_response.xml"); 
     baos.writeTo(outputStream);
     }
     catch(Exception e) {
         e.printStackTrace();
     }
     //Gururaj code end
    carrierId = aascShipmentOrderInfo.getShipmentHeaderInfo().getCarrierId();
    logger.info("carrierId :: " + carrierId);
    labelFormat = aascShipMethodInfo.getPrinterPort(carrierId);
    logger.info("labelFormat :: " + labelFormat);
//    domesticDocumentFormat = 
//        nullStrToSpc(aascShipMethodInfo.getDomesticDocumentFormat(carrierId));
//    IntlDocumentFormat = 
//        nullStrToSpc(aascShipMethodInfo.getIntlDocumentFormat(carrierId));
//    logger.info("In return Shipment \n domesticDocumentFormat :: " + 
//                domesticDocumentFormat + "\nIntlDocumentFormat :: " + 
//                IntlDocumentFormat);
     try
     {
        ShipmentResponse parseShipmentResponse=shipmentResponse.getShipResult();
        Package packageResponseObject=parseShipmentResponse.getDef_attr();
        responseStatus=packageResponseObject.getError_message();
        logger.info("responseStatus of the return transaction "+responseStatus);
        if ("No error".equalsIgnoreCase(responseStatus))
        {
        // vikas added code to fix issue #3446
//            aascHeaderInfo.setErrorChk("");
//            aascHeaderInfo.setError("");
//            aascHeaderInfo.setMainError("");
            // vika code ended
            logger.info("Return  Shipment is success and contiue to parse the response");
            try
            {
                unescapedTotalShipmentCharges=packageResponseObject.getTotal().toString();
                logger.info("Return unescapedTotalShipmentCharges :: "+unescapedTotalShipmentCharges);
                packageInfo.setRtnShipmentCost(Double.parseDouble(unescapedTotalShipmentCharges));
                logger.info("packageInfo.getRtnShipmentCost() :: "+ packageInfo.getRtnShipmentCost());
            }
            catch(Exception e)
            {
              e.printStackTrace();
              packageInfo.setRtnShipmentCost(0.0);
            }
            PackageResponse packageResponse[]=parseShipmentResponse.getPackages();
            for(int i=0;i<parseShipmentResponse.getPackages().length;i++) 
            {
                returnTrackingNumber = packageResponse[i].getTracking_number();
                logger.info("returnTrackingNumber ::"+returnTrackingNumber);
                packageInfo.setRtnTrackingNumber(nullStrToSpc(returnTrackingNumber));
                
//                Long returnMSN= packageResponse[i].getGlobal_msn();
//                String returnMsn = String.valueOf(returnMSN);
//                packageInfo.setReturnMSN(returnMsn);
//                logger.info("Return MSn ::"+packageInfo.getReturnMSN());
                
                SoxDocument soxDocument[]=new  SoxDocument[packageResponse[i].getDocuments().length];
                logger.info("soxDocument.length ::"+soxDocument.length);
                for(int j=0;j<soxDocument.length;j++) 
                {
                  soxDocument[j]=packageResponse[i].getDocuments()[j];
            String DocFormat_FriendlyName = 
              soxDocument[j].getDocFormat_FriendlyName();
            logger.info("DocFormat_FriendlyName ::" + 
                        DocFormat_FriendlyName);
            String local_printer_port = 
              soxDocument[j].getLocal_printer_port();

                  logger.info("local_printer_port :: "+local_printer_port);
            String documentFormat = soxDocument[j].getDocumentFormat();
            if ("ZPL".equalsIgnoreCase(labelFormat) || 
                "EPL".equalsIgnoreCase(labelFormat)||  "ZEBRA".equalsIgnoreCase(labelFormat))
            {
//              logger.info("Loop :: " + j + "  documentFormat :: " + 
//                          documentFormat + 
//                          "\t domesticDocumentFormat :: " + 
//                          domesticDocumentFormat + 
//                          "\t IntlDocumentFormat :: " + 
//                          IntlDocumentFormat);
//              if (domesticDocumentFormat.equalsIgnoreCase(documentFormat) || 
//                  IntlDocumentFormat.equalsIgnoreCase(documentFormat))
//              {
 if("CONNECTSHIP_UPS_MAXICODE_SHIPMENT_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
 || "CONNECTSHIP_UPS_MAXICODE_PACKAGE_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
 || "TANDATA_STDLABEL.STANDARD".equalsIgnoreCase(documentFormat)
 || "CONNECTSHIP_LTL_LABEL_PACKAGE.STANDARD".equalsIgnoreCase(documentFormat)
 || "CONNECTSHIP_DHL_LABEL.STANDARD".equalsIgnoreCase(documentFormat)) {
 
                  try
                  {
                  logger.info("For ZPL Code");
                  logger.info("Label :" + label);
                      returnLabel=soxDocument[j].getRawPrinterLanguage()[0];
                  logger.info(" label : \n" + returnLabel);
                  continue;
                  }
                  catch(Exception e)
                  {
                  try
                  {
                    if(!("ZPL".equalsIgnoreCase(labelFormat) ||  "EPL".equalsIgnoreCase(labelFormat)||  "ZEBRA".equalsIgnoreCase(labelFormat)))
                    {
                      returnLabel = soxDocument[j].getImages()[0];
                      continue;
                    }
                  }
                  catch (Exception e1)
                  {
                      //e.printStackTrace();
                      logger.info(e.getMessage());
                  }
                }
              }
            }
            else
            {
//              logger.info("Loop :: " + j + "  documentFormat :: " + 
//                          documentFormat + 
//                          "\t domesticDocumentFormat :: " + 
//                          domesticDocumentFormat + 
//                          "\t IntlDocumentFormat :: " + 
//                          IntlDocumentFormat);
//              if (domesticDocumentFormat.equalsIgnoreCase(documentFormat) || 
//                  IntlDocumentFormat.equalsIgnoreCase(documentFormat))
//              {
 if("CONNECTSHIP_UPS_MAXICODE_SHIPMENT_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
 || "CONNECTSHIP_UPS_MAXICODE_PACKAGE_LABEL.STANDARD".equalsIgnoreCase(documentFormat) 
 || "TANDATA_STDLABEL.STANDARD".equalsIgnoreCase(documentFormat)
 || "CONNECTSHIP_LTL_LABEL_PACKAGE.STANDARD".equalsIgnoreCase(documentFormat)
 || "CONNECTSHIP_DHL_LABEL.STANDARD".equalsIgnoreCase(documentFormat)) {
 
                  try
                  { 
                      returnLabel=soxDocument[j].getImages()[0];
                  }
                  catch(Exception e) 
                  {
                  try
                  {
                    returnLabel = 
                        soxDocument[j].getRawPrinterLanguage()[0];
                  }
                  catch (Exception e1)
                  {
                    logger.info(e1.getMessage());
                  }
                }
              }
                  }
                  // Saving the normal label for shipment
                  try
                  {
                      if (!(returnLabel.equals("")) && returnLabel != null)
                      {
                          parsedReturnLabel = AascBase64.decode(returnLabel);
                          logger.info("labelPath ::: "+labelPath);
                String utf16LeLabel = null;
                try
                {
                  if ("ZPL".equalsIgnoreCase(labelFormat)||  "EPL".equalsIgnoreCase(labelFormat)||  "ZEBRA".equalsIgnoreCase(labelFormat))
                  {
                    utf16LeLabel = 
                        new String(parsedReturnLabel, "UTF-16LE"); //UTF
                  }
                  

                }
                catch (Exception le)
                {
                  logger.info("Exception Generated while parsing the ShipExec Label from UTF 16 LE to UTF 8");
                }
                logger.info("file name :: " + 
                            packageInfo.getRtnTrackingNumber() + 
                            "_ReturnLabel");

                if (utf16LeLabel != null)
                {
                  if (carrierCode == 114 || carrierCode == 999)
                  {
//                    writeOutputFile(utf16LeLabel.getBytes("UTF-8"), 
//                                    labelPath + 
//                                    packageInfo.getReturnMSN() + 
//                                    "_ReturnLabel");
                  }
                  else
                  {
                    writeOutputFile(utf16LeLabel.getBytes("UTF-8"), 
                                    labelPath + 
                                    packageInfo.getRtnTrackingNumber() + 
                                    "_ReturnLabel");
                  }
                  logger.info("In the Label saving where the label is converted from UTF-16 LE to UTF-8");
                }
                else
                {
                  if (carrierCode == 114 || carrierCode == 999)
                  {
//                    writeOutputFile(parsedReturnLabel, 
//                                    labelPath + packageInfo.getReturnMSN() + 
//                                    "_ReturnLabel");
                  }
                  else
                  {
                    writeOutputFile(parsedReturnLabel, 
                                    labelPath + packageInfo.getRtnTrackingNumber() + 
                                    "_ReturnLabel");
                      }
                  logger.info("Normal Label Saving");
                }

                logger.info("after writing  CONTENT to file:" + labelPath + 
                            packageInfo.getRtnTrackingNumber() + 
                            "_ReturnLabel");
              }
                  }
                  catch(FileNotFoundException fileNotFoundException)
                  {
                    logger.severe("file path to which  label needs to be written is not found" + 
                                                        "\n file name:" + labelPath + 
                            packageInfo.getRtnTrackingNumber());
                    fileNotFoundException.printStackTrace();
                  }
                }
                
            }
          displayMessage = "success";  
        }
        else 
        {
          logger.info("Shipment has failed");
          displayMessage = "Error in Shipping... "+responseStatus;  
//          aascHeaderInfo.setErrorChk(displayMessage);
//          aascHeaderInfo.setError(displayMessage);
//          aascHeaderInfo.setMainError(displayMessage);
        }
   }
   catch(Exception exp)
   {
      logger.severe("Exception::"+exp.getMessage());
      displayMessage = "Error in parsing or setting the values to bean !";
   }
    return displayMessage;  
  }   
   
    /** This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj-object of type Object
     */
    String nullStrToSpc(Object obj)
    {
      String spcStr = "";
      if (obj == null)
      {
        return spcStr;
      }
      else
      {
        return obj.toString();
      }
    }   
    /**
     * For writing a string to specified file.
     * @param str java.lang.String
     * @param file java.lang.String
     */
    private void writeOutputFile(byte[] str, String file)
      throws Exception
    {
      FileOutputStream fout = new FileOutputStream(file);

      fout.write(str);
      fout.close();
    }  
  
    
    
}
