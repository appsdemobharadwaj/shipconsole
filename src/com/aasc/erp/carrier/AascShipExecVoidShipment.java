package com.aasc.erp.carrier;


import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.Package;
import com.aasc.erp.carrier.shipexec.proxy.WcfShipClient;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.VoidPackageByGlobalMsnResponse;
import com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.VoidPackageByTrackingNumberResponse;

import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

import java.io.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * AascVoidShipment Class contains createShipmentRequest()
 * which is used for creating xml shipment void request which is sent 
 * to ShipExec server of UPS through AascXmlTransmitter Class.
 * @author 	joseph
 * @version 	1

 * @since 09/10/2015
 
 */
 
public class AascShipExecVoidShipment {

    static Logger logger = AascLogger.getLogger("AascShipExecVoidShipment");
    String error = ""; // for storing errors from ShipExec
    String responseStatus = "";
    String outputFilePath = "";
    String trackingNumber="";
    String msnStr="";
    Long   globalMSN;
    String returnTrackingNumber="";
    String returnGlobalMSNStr="";
    Long   returnGlobalMSN;
    String protocol = "";
    String hostName = "";
    String prefix = "";
    String userName = "";
    String password = "";
    String endPointURL="";
    String port="";
    private String timeStampStr = (new Date().toString().replaceAll(" ", "_")).replaceAll(":", "_");
    public AascShipExecVoidShipment() {
    }
    
    /**
        * This method sets error string.
        *@param error  String.
    **/
    public void setError(String error) 
    {
        this.error = error;
    }
    
    /**
     *This method gets error string.
     *@return String error.
     */
    public String getError() 
    {
        return error;
    }
    
    public String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    } 
    /** creates request for shipment void request 
     * @param aascShipMethodInfo AascShipMethodInfo object 
     * @param aascProfileOptionsInfo AascProfileOptionsInfo object 
     * @return returnstatus Returns the int value , if 130 the response is success otherwise 
     * if int value is 131 then there is an error in the response file.
     * */
     public int voidShipment(AascShipmentOrderInfo aascShipmentOrderInfo,
                            AascProfileOptionsBean aascProfileOptionsInfo,
                            AascShipMethodInfo aascShipMethodInfo, 
                            String cloudLabelPath,String packTrackinNumber,String tempVoid) // Parameters for future use for Seperate shipment
    {
        logger.info(" In voidShipment in AascShipExecVoidShipment");
        int returnstatus = 130;
        try 
        {
            AascShipmentHeaderInfo headerbean = aascShipmentOrderInfo.getShipmentHeaderInfo();
            String shipExecShipper;
            LinkedList packageLinkedList = aascShipmentOrderInfo.getShipmentPackageInfo();
            logger.info("packageLinkedList.size() :: "+packageLinkedList.size());
            Iterator packageIterator = packageLinkedList.iterator();
            String shipMethod = headerbean.getShipMethodMeaning();
            String  connectshipSCS = nullStringToSpace(aascShipMethodInfo.getConnectShipScsTag(shipMethod));
            logger.info("connectshipSCS ::::::::::::"+connectshipSCS+"::::::::::::");
            String connectshipSC = connectshipSCS;
           
            if (connectshipSCS.lastIndexOf(".") != -1) 
            {
                connectshipSC = connectshipSCS.substring(0, connectshipSCS.lastIndexOf("."));
//                connectshipSC=connectshipSC.replaceAll("TANDATA","CONNECTSHIP");
            }
            logger.info("connectshipSC after substring ::::::::::::"+connectshipSC+"::::::::::::");
            int carrierId = aascShipMethodInfo.getCarrierId(shipMethod);
            int carrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
            logger.info("carrier Id :::: "+carrierId);
            shipExecShipper = aascProfileOptionsInfo.getShipperName();
            logger.info("connectshipShipper::"+shipExecShipper);
//            voidRequest = getVoidRequest(packageIterator, connectshipShipper, connectshipSC, 
//                                               packOrShipVoid,msn,returnMSN);
                
             WcfShipClient shipExecClient=new WcfShipClient();
//-----------------------------------------------------------------------------------------------------------------------             
              protocol = nullStringToSpace(aascShipMethodInfo.getProtocol(carrierId));
              logger.info("protocol::"+protocol+":::::");
              hostName = nullStringToSpace(aascShipMethodInfo.getCarrierServerIPAddress(carrierId));            
              logger.info("hostName::"+hostName+":::::");
              prefix   = nullStringToSpace(aascShipMethodInfo.getCarrierPrefix(carrierId));            
              logger.info("prefix::"+prefix+":::::");
              port = nullStringToSpace(aascShipMethodInfo.getCarrierPort(carrierId));
              logger.info("port::"+port+":::::");
//              endPointURL=protocol+"://"+hostName+"/"+prefix;
              endPointURL=protocol+"://"+hostName+":"+port+"/"+prefix;
              logger.info("endPointURL::"+endPointURL+":::");
              shipExecClient.setEndpoint(endPointURL);
//-------------------------------------------------------------------------------------------------              
             logger.info("calling " + shipExecClient.getEndpoint());
             SoxDictionaryItem sox[]=new SoxDictionaryItem[1];
             SoxDictionaryItem soxDictionaryItem =new SoxDictionaryItem();
             soxDictionaryItem.setKey(shipExecShipper);
             soxDictionaryItem.setValue(shipExecShipper);
             sox[0]=soxDictionaryItem;
            while (packageIterator.hasNext()) 
            {
                AascShipmentPackageInfo aascPackageBean = (AascShipmentPackageInfo)packageIterator.next();
                logger.info("ReqVoidFlagHidden Values==" +  nullStringToSpace(aascPackageBean.getReqVoidFlagHidden()));
                String ReqVoidFlagHidden = aascPackageBean.getReqVoidFlagHidden();
                logger.info("ReqVoidFlagHidden::"+ReqVoidFlagHidden);
                logger.info("void flag::::::"+aascPackageBean.getVoidFlag());
                logger.info("tempVoid::"+tempVoid);
                    if("ShipmentVoid".equalsIgnoreCase(tempVoid)){
                    if(!"Y".equalsIgnoreCase(ReqVoidFlagHidden)){
                    try 
                    {
                        trackingNumber=nullStringToSpace(aascPackageBean.getTrackingNumber());
                        logger.info("tracking number:::::::::"+trackingNumber);
                        returnTrackingNumber=nullStringToSpace(aascPackageBean.getRtnTrackingNumber());  
                    //                    returnGlobalMSNStr= nullStringToSpace(aascPackageBean.getReturnMSN());
                        msnStr = nullStringToSpace(aascPackageBean.getMsn()); 
                        if(!"".equalsIgnoreCase(returnGlobalMSNStr))
                        {
                          returnGlobalMSN = Long.parseLong(returnGlobalMSNStr);
                        }
                        if(!"".equalsIgnoreCase(msnStr))
                        {
                          globalMSN = Long.parseLong(msnStr);
                        }
                    //                    logger.info("returnTrackingNumber ::: "+returnTrackingNumber+
                    //                    "\n aascPackageBean.getReturnMSN() ::: \n"+aascPackageBean.getReturnMSN() );
                        if(!"".equalsIgnoreCase(returnTrackingNumber))
                        {
                          logger.info("Return Tracking number from aascPackageBean ::::"+returnTrackingNumber+"::::");
                          logger.info("Request details .................." +
                                      "\n returnTrackingNumber :: "+returnTrackingNumber+
                                      "\n connectshipSC :: "+connectshipSC+
                                      "\n connectshipShipper ::"+shipExecShipper);
                          // Calling the client in the carrier package and storing the reponse in VoidPackageByTrackingNumberResponse object
                           Package packageVoidOjbect=null;
                           
                          if(carrierCode==114 || carrierCode==999)
                          {
                            VoidPackageByGlobalMsnResponse voidPackageByGlobalMsnResponse=
                                                          shipExecClient.voidPackageByGlobalMsn(returnGlobalMSN,connectshipSC,shipExecShipper,sox);
                            packageVoidOjbect=voidPackageByGlobalMsnResponse.getVoidPackageByGlobalMsnResult();
                             try
                             {
                                 String deliveryName = headerbean.getOrderNumber();
                                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 XMLEncoder xmlEncoder = new XMLEncoder(baos);
                                 PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                                 xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                                 xmlEncoder.writeObject(voidPackageByGlobalMsnResponse);
                                 xmlEncoder.close();
                                 String xml = baos.toString();
                                 //                logger.info("--------------------------------------------------------\n"+xml);
                                 OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                                                         "_ShipExec_" + timeStampStr + 
                                                         "_void_return_response.xml"); 
                                 baos.writeTo(outputStream);
                             }
                             catch(Exception e) {
                                 e.printStackTrace();
                             }
                             //Gururaj code end
                            
                          }
                          else
                          {
                            VoidPackageByTrackingNumberResponse voidPackageByTrackingNumberResponse=
                                                                  shipExecClient.voidPackageByTrackingNumber(returnTrackingNumber,connectshipSC,shipExecShipper,sox);
                            packageVoidOjbect=voidPackageByTrackingNumberResponse.getVoidPackageByTrackingNumberResult();
                           
                             try
                             {
                                 String deliveryName = headerbean.getOrderNumber();
                                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 XMLEncoder xmlEncoder = new XMLEncoder(baos);
                                 PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                                 xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                                 xmlEncoder.writeObject(voidPackageByTrackingNumberResponse);
                                 xmlEncoder.close();
                                 String xml = baos.toString();
                                 //                logger.info("--------------------------------------------------------\n"+xml);
                                 OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                                                         "_ShipExec_" + timeStampStr + 
                                                         "_void_return_response.xml"); 
                                 baos.writeTo(outputStream);
                             }
                             catch(Exception e) {
                                 e.printStackTrace();
                             }
                             //Gururaj code end
                          }
                          //Parsing the response                                                    
                           
                          int errorCode=packageVoidOjbect.getError_code();
                          error=packageVoidOjbect.getError_message();
                          boolean VoidedFlag=packageVoidOjbect.getVoided();
                          logger.info("errorCode for return package void:: "+errorCode +
                                      "\n error for return package void:: "+error+
                                      "\n VoidedFlag return package void:: "+VoidedFlag);
                          if(errorCode==0&&VoidedFlag==true) 
                          {
                              logger.info("Voided Successfully :: ");
                              error = "";
                              returnstatus = 130;
                              logger.info(error + returnstatus);
                               
                          }
                          else 
                          {
                              error = responseStatus;
                              returnstatus = 131;
                          }            
                          
                        }
                        if(!"".equalsIgnoreCase(trackingNumber))
                        {
                          logger.info("Tracking number from aascPackageBean::::"+trackingNumber+"::::");
                          logger.info("Request details .................." +
                                      "\n trackingNumber :: "+trackingNumber+
                                      "\n connectshipSC :: "+connectshipSC+
                                      "\n connectshipShipper ::"+shipExecShipper+
                                      "\n Msn ::"+aascPackageBean.getMsn());
                          Package packageVoidOjbect=null;
                          if(carrierCode==114 || carrierCode==999)
                          {
                            VoidPackageByGlobalMsnResponse voidPackageByGlobalMsnResponse=
                                                          shipExecClient.voidPackageByGlobalMsn(globalMSN,connectshipSC,shipExecShipper,sox);
                            packageVoidOjbect=voidPackageByGlobalMsnResponse.getVoidPackageByGlobalMsnResult();
                            try
                            {
                                String deliveryName = headerbean.getOrderNumber();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                XMLEncoder xmlEncoder = new XMLEncoder(baos);
                                PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                                xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                                xmlEncoder.writeObject(voidPackageByGlobalMsnResponse);
                                xmlEncoder.close();
                                String xml = baos.toString();
                                //                logger.info("--------------------------------------------------------\n"+xml);
                                OutputStream outputStream = new FileOutputStream (cloudLabelPath +deliveryName + 
                                                        "_ShipExec_" + timeStampStr + 
                                                        "_void_response.xml"); 
                                baos.writeTo(outputStream);
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                          }
                          else
                          {
                            VoidPackageByTrackingNumberResponse voidPackageByTrackingNumberResponse=
                                                                  shipExecClient.voidPackageByTrackingNumber(trackingNumber,connectshipSC,shipExecShipper,sox);
                            packageVoidOjbect=voidPackageByTrackingNumberResponse.getVoidPackageByTrackingNumberResult();
                             try
                             {
                                 String deliveryName = headerbean.getOrderNumber();
                                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 XMLEncoder xmlEncoder = new XMLEncoder(baos);
                                 PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                                 xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                                 xmlEncoder.writeObject(voidPackageByTrackingNumberResponse);
                                 xmlEncoder.close();
                                 String xml = baos.toString();
                                 //                logger.info("--------------------------------------------------------\n"+xml);
                                 OutputStream outputStream = new FileOutputStream (cloudLabelPath +deliveryName + 
                                                         "_ShipExec_" + timeStampStr + 
                                                         "_void_response.xml"); 
                                 baos.writeTo(outputStream);
                             }
                             catch(Exception e) {
                                 e.printStackTrace();
                             }
                          }
                    //                      Parsing the response
                         
                          int errorCode=packageVoidOjbect.getError_code();
                          error=packageVoidOjbect.getError_message();
                          boolean VoidedFlag=packageVoidOjbect.getVoided();
                          logger.info("errorCode :: "+errorCode +
                                      "\n error :: "+error+
                                      "\n VoidedFlag:: "+VoidedFlag);
                          if(errorCode==0&&VoidedFlag==true) 
                          {
                              logger.info("Voided Successfully :: ");
                              error = "";
                              returnstatus = 130;
                              logger.info(error + returnstatus);
                               
                          }
                          else 
                          {
                    //                          error = responseStatus;
                              returnstatus = 131;
                              headerbean.setMainError(error);
                          }
                    //                      if(carrierCode==999 )
                    //                      {
                    //                        if(errorCode==0&&VoidedFlag==true)
                    //                        {
                    //                          break;
                    //                        }
                    //                      }
                        }
                       
                    }
                    catch(Exception e) 
                    {
                        e.printStackTrace();
                       returnstatus = 131;
                    }
                    }
                 }
                    
          else{
                try 
                {
                    trackingNumber=nullStringToSpace(aascPackageBean.getTrackingNumber());
                    logger.info("tracking number:::::::::"+trackingNumber);
                    returnTrackingNumber=nullStringToSpace(aascPackageBean.getRtnTrackingNumber());  
//                    returnGlobalMSNStr= nullStringToSpace(aascPackageBean.getReturnMSN());
                    msnStr = nullStringToSpace(aascPackageBean.getMsn()); 
                    if(!"".equalsIgnoreCase(returnGlobalMSNStr))
                    {
                      returnGlobalMSN = Long.parseLong(returnGlobalMSNStr);
                    }
                    if(!"".equalsIgnoreCase(msnStr))
                    {
                      globalMSN = Long.parseLong(msnStr);
                    }
//                    logger.info("returnTrackingNumber ::: "+returnTrackingNumber+
//                    "\n aascPackageBean.getReturnMSN() ::: \n"+aascPackageBean.getReturnMSN() );
                    if(!"".equalsIgnoreCase(returnTrackingNumber))
                    {
                      logger.info("Return Tracking number from aascPackageBean ::::"+returnTrackingNumber+"::::");
                      logger.info("Request details .................." +
                                  "\n returnTrackingNumber :: "+returnTrackingNumber+
                                  "\n connectshipSC :: "+connectshipSC+
                                  "\n connectshipShipper ::"+shipExecShipper);
                      // Calling the client in the carrier package and storing the reponse in VoidPackageByTrackingNumberResponse object
                       Package packageVoidOjbect=null;
                       
                      if(carrierCode==114 || carrierCode==999)
                      {
                        VoidPackageByGlobalMsnResponse voidPackageByGlobalMsnResponse=
                                                      shipExecClient.voidPackageByGlobalMsn(returnGlobalMSN,connectshipSC,shipExecShipper,sox);
                        packageVoidOjbect=voidPackageByGlobalMsnResponse.getVoidPackageByGlobalMsnResult();
                        //Gururaj added code saving of java xmls for ShipExec
                         try
                         {
                             String deliveryName = headerbean.getOrderNumber();
                             ByteArrayOutputStream baos = new ByteArrayOutputStream();
                             XMLEncoder xmlEncoder = new XMLEncoder(baos);
                             PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                             xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                             xmlEncoder.writeObject(voidPackageByGlobalMsnResponse);
                             xmlEncoder.close();
                             String xml = baos.toString();
                             //                logger.info("--------------------------------------------------------\n"+xml);
                             OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                                                     "_ShipExec_" + timeStampStr + 
                                                     "_void_return_response.xml"); 
                             baos.writeTo(outputStream);
                         }
                         catch(Exception e) {
                             e.printStackTrace();
                         }
                        
                      }
                      else
                      {
                        VoidPackageByTrackingNumberResponse voidPackageByTrackingNumberResponse=
                                                              shipExecClient.voidPackageByTrackingNumber(returnTrackingNumber,connectshipSC,shipExecShipper,sox);
                        packageVoidOjbect=voidPackageByTrackingNumberResponse.getVoidPackageByTrackingNumberResult();
                        //Gururaj added code saving of java xmls for ShipExec
                         try
                         {
                             String deliveryName = headerbean.getOrderNumber();
                             ByteArrayOutputStream baos = new ByteArrayOutputStream();
                             XMLEncoder xmlEncoder = new XMLEncoder(baos);
                             PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                             xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                             xmlEncoder.writeObject(voidPackageByTrackingNumberResponse);
                             xmlEncoder.close();
                             String xml = baos.toString();
                             //                logger.info("--------------------------------------------------------\n"+xml);
                             OutputStream outputStream = new FileOutputStream (cloudLabelPath + deliveryName + 
                                                     "_ShipExec_" + timeStampStr + 
                                                     "_void_return_response.xml"); 
                             baos.writeTo(outputStream);
                         }
                         catch(Exception e) {
                             e.printStackTrace();
                         }
                       
                      }
                      //Parsing the response                                                    
                       
                      int errorCode=packageVoidOjbect.getError_code();
                      error=packageVoidOjbect.getError_message();
                      boolean VoidedFlag=packageVoidOjbect.getVoided();
                      logger.info("errorCode for return package void:: "+errorCode +
                                  "\n error for return package void:: "+error+
                                  "\n VoidedFlag return package void:: "+VoidedFlag);
                      if(errorCode==0&&VoidedFlag==true) 
                      {
                          logger.info("Voided Successfully :: ");
                          error = "";
                          returnstatus = 130;
                          logger.info(error + returnstatus);
                           
                      }
                      else 
                      {
                          error = responseStatus;
                          returnstatus = 131;
                      }            
                      
                    }
                    if(!"".equalsIgnoreCase(trackingNumber))
                    {
                      logger.info("Tracking number from aascPackageBean::::"+trackingNumber+"::::");
                      logger.info("Request details .................." +
                                  "\n trackingNumber :: "+trackingNumber+
                                  "\n connectshipSC :: "+connectshipSC+
                                  "\n connectshipShipper ::"+shipExecShipper+
                                  "\n Msn ::"+aascPackageBean.getMsn());
                      Package packageVoidOjbect=null;
                      if(carrierCode==114 || carrierCode==999)
                      {
                        VoidPackageByGlobalMsnResponse voidPackageByGlobalMsnResponse=
                                                      shipExecClient.voidPackageByGlobalMsn(globalMSN,connectshipSC,shipExecShipper,sox);
                        packageVoidOjbect=voidPackageByGlobalMsnResponse.getVoidPackageByGlobalMsnResult();
                        try
                        {
                            String deliveryName = headerbean.getOrderNumber();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            XMLEncoder xmlEncoder = new XMLEncoder(baos);
                            PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                            xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                            xmlEncoder.writeObject(voidPackageByGlobalMsnResponse);
                            xmlEncoder.close();
                            String xml = baos.toString();
                            //                logger.info("--------------------------------------------------------\n"+xml);
                            OutputStream outputStream = new FileOutputStream (cloudLabelPath +deliveryName + 
                                                    "_ShipExec_" + timeStampStr + 
                                                    "_void_response.xml"); 
                            baos.writeTo(outputStream);
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                      }
                      else
                      {
                        VoidPackageByTrackingNumberResponse voidPackageByTrackingNumberResponse=
                                                              shipExecClient.voidPackageByTrackingNumber(trackingNumber,connectshipSC,shipExecShipper,sox);
                        packageVoidOjbect=voidPackageByTrackingNumberResponse.getVoidPackageByTrackingNumberResult();
                         try
                         {
                             String deliveryName = headerbean.getOrderNumber();
                             ByteArrayOutputStream baos = new ByteArrayOutputStream();
                             XMLEncoder xmlEncoder = new XMLEncoder(baos);
                             PersistenceDelegate pd = xmlEncoder.getPersistenceDelegate(Integer.class); 
                             xmlEncoder.setPersistenceDelegate(BigDecimal.class, pd);
                             xmlEncoder.writeObject(voidPackageByTrackingNumberResponse);
                             xmlEncoder.close();
                             String xml = baos.toString();
                             //                logger.info("--------------------------------------------------------\n"+xml);
                             OutputStream outputStream = new FileOutputStream (cloudLabelPath +deliveryName + 
                                                     "_ShipExec_" + timeStampStr + 
                                                     "_void_response.xml"); 
                             baos.writeTo(outputStream);
                         }
                         catch(Exception e) {
                             e.printStackTrace();
                         }
                      }
//                      Parsing the response                                                    
                     
                      int errorCode=packageVoidOjbect.getError_code();
                      error=packageVoidOjbect.getError_message();
                      boolean VoidedFlag=packageVoidOjbect.getVoided();
                      logger.info("errorCode :: "+errorCode +
                                  "\n error :: "+error+
                                  "\n VoidedFlag:: "+VoidedFlag);
                      if(errorCode==0&&VoidedFlag==true) 
                      {
                          logger.info("Voided Successfully :: ");
                          error = "";
                          returnstatus = 130;
                          logger.info(error + returnstatus);
                           
                      }
                      else 
                      {
//                          error = responseStatus;
                          returnstatus = 131;
                          headerbean.setMainError(error);
                      }
//                      if(carrierCode==999 )
//                      {
//                        if(errorCode==0&&VoidedFlag==true)
//                        {
//                          break;
//                        }
//                      }
                    }
                   
                }
                catch(Exception e) 
                {
                    e.printStackTrace();
                   returnstatus = 131;
                }
                }
            }


            
        }
        catch (Exception e) 
        {
            error = "Error: " + e.getMessage();
            returnstatus = 131;
            e.printStackTrace();
        }
        return returnstatus;
    }//end of method

        
}
