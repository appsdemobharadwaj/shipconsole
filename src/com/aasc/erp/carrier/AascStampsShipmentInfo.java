 /*
  * @(#)AascStampsShipmentInfo.java        01/11/2015
  * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
  * All rights reserved.
  @History
  01/11/2015  Mahesh V    Added files for Stamps.com Integration


  */

 package com.aasc.erp.carrier;

import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.carrier.stampsws.proxy.CreateIndiciumResponse;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.Date;
 import java.util.LinkedList;
 import java.util.ListIterator;
 import java.util.logging.Logger;


     public class AascStampsShipmentInfo {
     public AascStampsShipmentInfo() {
     }
     
     private static Logger logger = AascLogger.getLogger("AascStampsShipmentInfo");
     
     private AascShipmentHeaderInfo aascHeaderInfo = null;
     private AascShipmentPackageInfo aascPackageInfo = null;
     private String labelPath = "";
      private FileOutputStream fout = 
          null;
     private byte[][] parsedLabel = null;
      private String deliveryName = "";
      private String timeStampStr = null;
     String returnTrackingNumber = "";
     String returnStampsTxId = "";
     BigDecimal codAmount ;
     Double doublevalue = 0.0;
     private Double freightCharge=0.0;

     private BigDecimal unescapedTotalShipmentCharges = new BigDecimal(0);


     /**
       * parseResponse method is used to parse the Web service response and 
       * set the parsed data to corresponding headers and package bean.
       * @param shipmentResponse String containing shipmentResponse which needs to be parsed
       * @param aascDeliveryInfo AascDeliveryInfo bean object to retreive header and package bean 
       * to which parsed data is set
       * @return displayMessage String which contains response status
       *
     */
    
       String parseResponse(CreateIndiciumResponse shipmentResponse, AascShipmentOrderInfo aascDeliveryInfo, AascShipMethodInfo aascShipMethodInfo, String cloudLabelPath)
      {
          logger.info("inside parsing the response");
          String displayMessage="";
          String wayBillNumber = "";

           labelPath=cloudLabelPath;
          logger.info("label path : "+labelPath);
          ListIterator packageListIterator = null;
              try
              {
               aascHeaderInfo = aascDeliveryInfo.getShipmentHeaderInfo();
               LinkedList packageList = aascDeliveryInfo.getShipmentPackageInfo();
               int packageSize = packageList.size();
               logger.info("packageSize---"+packageSize);
               packageListIterator = packageList.listIterator();
               while(packageListIterator.hasNext()){
               aascPackageInfo = (AascShipmentPackageInfo)packageListIterator.next();
              }
            deliveryName = nullStrToSpc(aascHeaderInfo.getOrderNumber());
            timeStampStr = (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");   
               String trackingNuber=shipmentResponse.getTrackingNumber();
               String stampsTxId = shipmentResponse.getStampsTxID();
               unescapedTotalShipmentCharges = shipmentResponse.getRate().getAmount();

               if(aascHeaderInfo.getShipmentCost()>0){
               freightCharge = (aascHeaderInfo.getShipmentCost() + unescapedTotalShipmentCharges.doubleValue());
               }
               else {
               freightCharge = unescapedTotalShipmentCharges.doubleValue();
               }
              logger.info("freightCharge:::"+freightCharge);
              
             //Mahesh Added below code to save response files             
            //------------------------------------------
               try
                        {
            
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        XMLEncoder xmlEncoder = new XMLEncoder(baos);
                        PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                        xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                        xmlEncoder.writeObject(shipmentResponse);
                        xmlEncoder.close();
                        String xml = baos.toString();
                        //                System.out.println("--------------------------------------------------------\n"+xml);
                        OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                                                "_Stamps_" + timeStampStr + 
                                                "_response.xml"); 
                        baos.writeTo(outputStream);
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }

              //-----------------------------------------
              try{
               wayBillNumber = trackingNuber;
               aascHeaderInfo.setWayBill(wayBillNumber);
               aascHeaderInfo.setShipmentCost(freightCharge);
               aascHeaderInfo.setFreightCost(freightCharge);
               aascPackageInfo.setStampsTaxId(stampsTxId);
               aascPackageInfo.setTrackingNumber(nullStrToSpc(wayBillNumber));
               aascPackageInfo.setPkgCost((unescapedTotalShipmentCharges.doubleValue()));
                logger.info("wayBillNumber::"+wayBillNumber);
                 

               logger.info("setting waybill number from parsed response to header bean:::::" + wayBillNumber); 
                     try{
                      parsedLabel = shipmentResponse.getImageData();
                     int carrierId = aascDeliveryInfo.getShipmentHeaderInfo().getCarrierId();
                     String intlLabelFormat= aascShipMethodInfo.getPrinterPort(carrierId); //"AZpl";
                     logger.info("intlLabelFormat::" + intlLabelFormat);
                      
                      if(!(parsedLabel.equals("")) && parsedLabel != null){
                           logger.info("labelPath ::: "+labelPath +"parsedLabel.length::"+parsedLabel.length);
                      writeOutputFile(parsedLabel[0], labelPath + trackingNuber);
                                               try{
                      writeOutputFile(parsedLabel[1], labelPath + trackingNuber + "_2");
                          } catch (Exception e) {
                              logger.severe("Exception::"+e.getMessage());
                          }try{
                      writeOutputFile(parsedLabel[2], labelPath + trackingNuber + "_3");
                      } catch (Exception e) {
                          logger.severe("Exception::"+e.getMessage());
                      }
                     //Mahesh Added below code for handling AZPL nad zpl format start
                     logger.info("intlLabelFormat in AAscShipment Info::"+intlLabelFormat);
                          if(intlLabelFormat.equalsIgnoreCase("AZpl") || intlLabelFormat.equalsIgnoreCase("Zpl")){   //Mahesh Added below code for handling AZPL nad zpl format
                          File file = 
                               new File(labelPath + trackingNuber);
                          fout = new FileOutputStream(file);
                          fout.write(parsedLabel[0]);
                          fout.close();
                              byte[] parsedLabelTemp = 
                                  new byte[(int)file.length()];
                                  
                              byte[] parsedLabelTemp1 = 
                                  new byte[(int)file.length()];
                              try {
                                  FileInputStream fileInputStream = 
                                      new FileInputStream(file);
                                  fileInputStream.read(parsedLabelTemp);
                                 
                                  try {
                                  int p2Index=0;
                                      for (int p1Index =0; p1Index < parsedLabelTemp.length; p1Index++) {
                                                  if ((parsedLabelTemp[p1Index] == 126) && (parsedLabelTemp[p1Index+1] == 83) && (parsedLabelTemp[p1Index+2] == 68) && (parsedLabelTemp[p1Index+3] == 48) && (parsedLabelTemp[p1Index+4] == 13) && (parsedLabelTemp[p1Index+5] == 10)) {
                                                      
                                                      logger.info("inside skipping text ~SD0");
                                                      p1Index++;
                                                      p1Index++;
                                                      p1Index++;
                                                      p1Index++;
                                                      p1Index++;
                                                      
                                                  }else{
                                                      parsedLabelTemp1[p2Index] = parsedLabelTemp[p1Index];
                                                      p2Index++;
                                                  }
                                              }
                                              
                                      FileOutputStream fout1 = 
                                          new FileOutputStream(labelPath + 
                                                               trackingNuber);
                                      fout1.write(parsedLabelTemp1);
                                      fout1.close();
                                  
                                  }catch (Exception e) {
                                    logger.severe("Exception::"+e.getMessage());
                                     }
                                     
                                     
                              }
                                 catch (Exception e) {
                                   logger.severe("Exception::"+e.getMessage());
                                    }
                                    
                                    //second AZpl Label Removing ~SD0 start-------------------
                                     logger.info("parsedLabel[1].length==="+parsedLabel[1].length);

                                    if(parsedLabel[1].length > 0){
                                     File file1 =                                           
                                          new File(labelPath + trackingNuber + "_2");
                                              
                                          
                                     fout = new FileOutputStream(file1);
                                     fout.write(parsedLabel[1]);
                                     fout.close();
                                         byte[] parsedLabelTemp11 =                        
                                             new byte[(int)file1.length()];
                                             
                                         byte[] parsedLabelTemp22 =                         
                                             new byte[(int)file1.length()];
                                        
                                         try {
                                             FileInputStream fileInputStream = 
                                                 new FileInputStream(file1);
                                             fileInputStream.read(parsedLabelTemp11);
                                             try {
                                             int p2Index=0;
                                                 for (int p1Index =0; p1Index < parsedLabelTemp11.length; p1Index++) {
                                                             if ((parsedLabelTemp11[p1Index] == 126) && (parsedLabelTemp11[p1Index+1] == 83) && (parsedLabelTemp11[p1Index+2] == 68) && (parsedLabelTemp11[p1Index+3] == 48) && (parsedLabelTemp11[p1Index+4] == 13) && (parsedLabelTemp11[p1Index+5] == 10)) {
                                                                 
                                                                 logger.info("the p1Index value is :: " + p1Index);
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 
                                                             }else{
                                                                 parsedLabelTemp22[p2Index] = parsedLabelTemp11[p1Index];
                                                                 p2Index++;
                                                             }

                                                         }
                                                         
                                                 FileOutputStream fout1 = new FileOutputStream(labelPath + trackingNuber + "_2");
                                                 fout1.write(parsedLabelTemp22);
                                             
                                                 fout1.close();
                                                 
                                             
                                             }catch (Exception e) {
                                               logger.severe("Exception::"+e.getMessage());
                                                }
                                         
                                         
                                         }
                                            catch (Exception e) {
                                              logger.severe("Exception::"+e.getMessage());
                                               }
                                    }
                                    //second AZpl Label Removing ~SD0 End--------------------
                                    
                                    
                                    //Third AZpl Label Removing ~SD0 Start-----------------------
                                    logger.info("parsedLabel[2].length==="+parsedLabel[2].length);
                                     if(parsedLabel[2].length > 0){
                                     File file2 =                                           
                                          new File(labelPath + trackingNuber + "_3");
                                     fout = new FileOutputStream(file2);
                                     fout.write(parsedLabel[2]);
                                     fout.close();
                                         byte[] parsedLabelTemp33 =                        
                                             new byte[(int)file2.length()];
                                             
                                         byte[] parsedLabelTemp44 =                         
                                             new byte[(int)file2.length()];
                                        
                                         try {
                                             FileInputStream fileInputStream = 
                                                 new FileInputStream(file2);
                                             fileInputStream.read(parsedLabelTemp33);
                                            
                                             try {
                                             int p2Index=0;
                                                 for (int p1Index =0; p1Index < parsedLabelTemp33.length; p1Index++) {
                                                             if ((parsedLabelTemp33[p1Index] == 126) && (parsedLabelTemp33[p1Index+1] == 83) && (parsedLabelTemp33[p1Index+2] == 68) && (parsedLabelTemp33[p1Index+3] == 48) && (parsedLabelTemp33[p1Index+4] == 13) && (parsedLabelTemp33[p1Index+5] == 10)) {
                                                                 
                                                                 logger.info("the p1Index value is :: " + p1Index);
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 p1Index++;
                                                                 
                                                             }else{
                                                                 parsedLabelTemp44[p2Index] = parsedLabelTemp33[p1Index];
                                                                 p2Index++;
                                                             }

                                                         }
                                                         
                                                 FileOutputStream fout1 = new FileOutputStream(labelPath + trackingNuber + "_3");
                                                 fout1.write(parsedLabelTemp44);
                                                 fout1.close();

                     } catch (Exception e) {
                         logger.severe("Exception::"+e.getMessage());
                     }
                                                
                                         }
                                            catch (Exception e) {
                                              logger.severe("Exception::"+e.getMessage());
                                               }
                                    
                                     }
                                    //Third AZPL label ~SD0 removing End---------------------
                                     //Mahesh Added below code for handling AZPL nad zpl format end
                                    
                          }

                     
                 }
             } catch (Exception e) {
                 logger.severe("Exception::"+e.getMessage());
             }
             if (trackingNuber != "" && trackingNuber != null) {
                 displayMessage = "success";
             } else {
                 displayMessage = "failure";
             }
              }catch(Exception e){
                  e.printStackTrace();
              }
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }
             return displayMessage;
      }
      
     /** This method can replace the null values with nullString.
      * @return String that is ""
      * @return obj-object of type Object
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

