package com.aasc.erp.delegate;

/* @ AascShipmentOrderDelegate.java        07/08/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

import com.aasc.erp.carrier.AascShipExecVoidShipment;
import com.aasc.erp.carrier.AascStampsVoid;
import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascPackageDimensionInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.carrier.AascFedexVoid;
import com.aasc.erp.carrier.AascShipExecShipment;
import com.aasc.erp.carrier.AascUpsShipment;
import com.aasc.erp.carrier.AascUpsVoid;
import com.aasc.erp.model.AascAccountNumbersDAO;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascIntlShipmentsDAO;
import com.aasc.erp.model.AascPackageDimensionDAO;
import com.aasc.erp.model.AascShipMethodAjaxDAO;
import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.model.AascShipSaveDAO;
import com.aasc.erp.model.AascShipmentOrderInfoDAO;

import com.aasc.erp.model.AascVoidShipmentDAO;
import com.aasc.erp.util.AascLogger;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.ListIterator;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import oracle.jsp.net.http.HttpRequest;

/**

 * AascShipmentOrderDelegate class.

 * @author Eshwari
   creation 07/08/2014
  
   History
 * 29-12-2014      Eshwari M    Modified getOrderInfo() to get the shipmethods based on locationId
 * 07-01-2014      Y Pradeep    Removed unused loggers and added comments.
 * 19/01/2015      Suman G      Added nullStrToSpc(), voidShipment() and printLabel() methods.
 * 28/01/2015      Y Pradeep    Added code required for International Shipping.
 * 05/02/2015      K Sunanda    Added code for void functionality voidShipment() method
 * 12/02/2015      Eshwari M    Modified code to pull dimension details also at the time of order detail pulling to get correct dimensions according to the location associated with order
 * 12/02/2015      Eshwari M    Modified code to fix the package level void for fedex shipments by getting voidStatusFlagVal
 * 16/02/2015      Y Pradeep    Added method code to generate order number on click og Ship button in Shipping page.
 * 23/03/2015      K Sunanda    Removed unused variable testClientId for code review fix.
 * 14/05/2015      Y Pradeep    Modified code at if conditions to avoid null pointer exception while getting voidStatusFlagVal in voidShipment method.
 * 27/05/2015      K Sunanda    Modified code for bug fix #2938
 * 02/06/2015      Y Pradeep    Modified code accordingly to for package level voiding for UPS.
 * 03/06/2015      Suman G      Added code to fix #2956
 * 01/11/2015      Mahesh V     Added code for Stamps.com Integration

 */
public class AascShipmentOrderDelegate {

    AascDAOFactory aascDAOFactory;
    AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO;

    int returnStatus; // holds return status
    HttpSession session; // holds the object of the HttpSession
    private String headerTrackingNumber = 
        ""; // holds the value of headerTrackingNumber
    String fedExCarrierMode; //  holds the fedEx carrier mode
    String fedExKey; //  holds the fedEx key
    String fedExPassword; //  holds the fedEx password
    private String carrierACNumber; // holds the value of the carrier account number
    String voidStatusFlagVal = ""; //  holds the void status flag
    AascIntlInfo aascIntlInfo;
    AascIntlHeaderInfo intlHeaderBean;
    int oldPackcount = 0;
    String packageTrackingNumber = "";
    String intlFlagWS = "";
    String error = 
        ""; // holds the errorStaus value get from the procedures called by the  model class method

    AascShipmentHeaderInfo aascShipmentHeaderInfo; // holds the object of the AascShipmentHeaderInfo class
    private static Logger logger = 
        AascLogger.getLogger("AascShipmentOrderDelegate"); // this method returns the object of the logger class

    public AascShipmentOrderDelegate() {
        aascDAOFactory = 
                AascDAOFactory.getAascDAOFactory(1); // this method returns the object of the AascDAOFactory class
        aascShipmentOrderInfoDAO = 
                aascDAOFactory.getAascShipmentOrderInfoDAO(); // this method returns the object of the AascShipmentOrderInfoDAO class

    }
    
    public String generateOrderNumber(int clientID){
    
        String orderNumber = aascShipmentOrderInfoDAO.getOrderNumber(clientID);
        orderNumber = "SC"+orderNumber;
        return orderNumber;        
    }

    /**
     * This method is used for get order information.
     * @return AascShipmentOrderInfo
     * @param orderNumber String 
     * @param clientID Integer
     * @param session HttpSession
     */
    public AascShipmentOrderInfo getOrderInfo(String orderNumber, 
                                              Integer clientID, 
                                              //Integer locationId, 
                                              HttpSession session) {
        logger.info("Entered getOrderInfo()");
        LinkedList sessionList = new LinkedList();
        AascShipmentOrderInfo aascShipmentOrderInfoSaved = 
            aascShipmentOrderInfoDAO.getShipmentOrderInfo(orderNumber,clientID.intValue());
        //  Eshwari added below code to get shipmenthods based on location selection in shipments page   ***/
        AascShipMethodDAO aascShipMethodDAO = 
            aascDAOFactory.getAascShipMethodDAO();
        int locationId = 0 ;
        if(aascShipmentOrderInfoSaved.getShipmentHeaderInfo() != null && 
           (aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getOrderNumber() != null) && aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getOrderNumber() != "" )  
        {    
            locationId = aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getLocationId();    
        }    
        else         
        {
            Integer locId = (Integer)session.getAttribute("locationId") ;
            if(locId != null)
               locationId = locId.intValue() ;
        }
        logger.info("locationId : "+locationId);
        sessionList.add(locationId);
        sessionList.add(clientID);
        
        int roleId = ((Integer)session.getAttribute("role_id")).intValue() ;

        AascShipMethodInfo aascShipMethodInfo = 
            aascShipMethodDAO.getShipMethodInfo(sessionList);
        session.setAttribute("ShipMethodInfo", aascShipMethodInfo);
        //Sunanda added the below code for bug fix #2938
        String payMethod1= aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getCarrierPaymentMethod();
        String shipMethod1= aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getShippingMethod();
        String accountNum= aascShipmentOrderInfoSaved.getShipmentHeaderInfo().getCarrierAccountNumber();
        if(payMethod1.equalsIgnoreCase("PREPAID")&&( "".equalsIgnoreCase(accountNum)||accountNum==null)) {
        logger.info("inside id ##");
        int carrierCode1; 
       
        if(shipMethod1.substring(0,3).equalsIgnoreCase( "UPS")) {
           carrierCode1=100;
        }
        else if(shipMethod1.substring(0,4).equalsIgnoreCase("FDXE")){
           carrierCode1=110;
        }
        else if(shipMethod1.substring(0,4).equalsIgnoreCase("FDXG")){
            carrierCode1=111;
        }
        else{
            carrierCode1=999;
        }
        AascAccountNumbersDAO aascAccountNumbersDAO = 
            aascDAOFactory.getAccountNumbersDAO();
        AascAccountNumbersBean aascAccountNumbersInfo = 
        aascAccountNumbersDAO.getAccountNumbersInfo( carrierCode1,clientID.intValue(),locationId,60);
        accountNum=aascAccountNumbersInfo.getAccountNumber();
        AascShipmentHeaderInfo shipmentHeaderInfo=aascShipmentOrderInfoSaved.getShipmentHeaderInfo();
        shipmentHeaderInfo.setCarrierAccountNumber(accountNum);
        aascShipmentOrderInfoSaved.setShipmentHeaderInfo(shipmentHeaderInfo);
        //Sunanda code ends here
        }
        AascPackageDimensionDAO aascPackageDimensionDAO;
        aascPackageDimensionDAO =  aascDAOFactory.getPackageDimensionDAO();
        AascPackageDimensionInfo aascPackageDimensionInfo = null;
        aascPackageDimensionInfo =aascPackageDimensionDAO.getPackageDimensionInfo(locationId, roleId, clientID);
        aascPackageDimensionInfo.setLocationId(locationId);
        session.setAttribute("aascPackageDimensionInfo",  aascPackageDimensionInfo);
        session.setAttribute("unitList", (Object)aascPackageDimensionInfo.getUnitDetails());
        //   eshwari code end *******/

        logger.info("Exit getOrderInfo()");
        return aascShipmentOrderInfoSaved;
    }

    /**
     * This method is used for get Account number information.
     * @return AascAccountNumbersBean
     * @param locationIdint int 
     * @param carrierCode int
     * @param clientIDMain int
     * @param queryTimeOutInt int
     */
    public AascAccountNumbersBean getAccountNumbersInfo(int locationIdint, 
                                                        int carrierCode, 
                                                        int clientIDMain, 
                                                        int queryTimeOutInt) {
        AascAccountNumbersDAO aascAccountNumbersDAO = 
            aascDAOFactory.getAccountNumbersDAO();
        AascAccountNumbersBean aascAccountNumbersInfo = 
            aascAccountNumbersDAO.getAccountNumbersInfo(carrierCode, 
                                                        clientIDMain, 
                                                        locationIdint, 
                                                        queryTimeOutInt);
        return aascAccountNumbersInfo;
    }

    /**
     * This method is used for save shipment.
     * @return int
     * @param orderNo int 
     * @param aascShipmentOrderInfo Object
     * @param clientID int
     */
    public int saveShipment(int orderNo, 
                            AascShipmentOrderInfo aascShipmentOrderInfo, 
                            int clientID, int carrierSuccessStatus) {
        AascShipSaveDAO aascShipSaveDAO = 
            aascDAOFactory.getAascShipSaveDAO(); // this method returns the AascShipSaveDAO class object
        int returnStatus = 
            aascShipSaveDAO.getShipSaveDAO(orderNo, aascShipmentOrderInfo, 
                                           clientID, carrierSuccessStatus); //callling the aascShipSaveDAO.getShipSaveDAO method which save the header and packag information to the database
        return returnStatus;
    }

    /**
     * This method is used for get shipment information.
     * @return AascShipmentOrderInfo
     * @param orderNumber String 
     * @param clientID int
     */
    public AascShipmentOrderInfo getShipmentInfo(String orderNumber, 
                                                 int clientID) {
        AascShipmentOrderInfo aascOrderSaved = 
            aascShipmentOrderInfoDAO.getShipmentOrderInfo(orderNumber, 
                                                          clientID);
        return aascOrderSaved;
    }
    
    
    public AascIntlInfo getIntlShipments(String orderNumber, 
                                                 int clientId, int locationId) {
                                                 
        AascIntlShipmentsDAO aascIntlDAO = aascDAOFactory.getAascIntlShipmentsDAO();
        aascIntlInfo = 
                aascIntlDAO.getIntlCommodityLineDetails(orderNumber,clientId, locationId);
        
        intlHeaderBean = 
                aascIntlDAO.getIntlHeaderDetails(orderNumber,clientId, locationId);
                
        aascIntlInfo.setIntlHeaderInfo(intlHeaderBean);
        return aascIntlInfo;
    }

    /**
     * This method is used for retreieve ups service level codes.
     * @return String
     * @param carrierCode int 
     * @param upsScacCode String
     * @param originRegionCode String
     */
    public String retrieveUpsServiceLevelCode(int carrierCode, 
                                              String upsScacCode, 
                                              String originRegionCode) {
        AascShipMethodAjaxDAO aascShipMethodAjaxDAO = 
            aascDAOFactory.getAascShipMethodAjaxDAO();
        String upsServiceLevelCode = 
            aascShipMethodAjaxDAO.retrieveUpsServiceLevelCode(carrierCode, 
                                                              upsScacCode, 
                                                              originRegionCode);
        return upsServiceLevelCode;
    }

    /**
    
     * This method is used for printing the labels which inturns calls other classes methods.
     * @return int
     * @param aascShipmentOrderInfo object 
     * @param aascShipMethodInfo object
     * @param wshPrinterName String
     * @param intlFlag String
     * @param op900PrinterName String
     * @param ciFlag String
     */
    public int printlabel(AascShipmentOrderInfo aascShipmentOrderInfo, 
                          AascProfileOptionsBean aascProfileOptionsInfo, 
                          AascShipMethodInfo aascShipMethodInfo, 
                          String wshPrinterName, String intlFlag, 
                          String op900PrinterName, String ciFlag) {
        AascShipmentHeaderInfo aascShipmentHeaderInfo = 
            aascShipmentOrderInfo.getShipmentHeaderInfo(); // this method returns the object of the AascShipmentHeaderInfo class
        try {

            if (aascShipMethodInfo.getCarrierCode(aascShipMethodInfo.getCarrierId(aascShipmentHeaderInfo.getShipMethodMeaning())) == 
                999) {
            } else {

                try {
                    LinkedList packageList = 
                        aascShipmentOrderInfo.getShipmentPackageInfo();

                    String intlDocName = "";
                    String orderNumberIntl = 
                        aascShipmentHeaderInfo.getOrderNumber();
                    Date shippedDateIntl = 
                        aascShipmentHeaderInfo.getShipmentDate();
                    String shippedDateIntlStr = 
                        shippedDateIntl.toString().replaceAll("-", "_");
                    int carrierCode = 
                        aascShipMethodInfo.getCarrierCode(aascShipMethodInfo.getCarrierId(aascShipmentHeaderInfo.getShipMethodMeaning()));
                    if (carrierCode == 110 || carrierCode == 111) {
                        intlDocName = 
                                orderNumberIntl + "_Shipment_" + shippedDateIntlStr + 
                                "_FDXECI.pdf";
                    } else if (carrierCode == 100) {
                        intlDocName = 
                                orderNumberIntl + "_Shipment_" + shippedDateIntlStr + 
                                "_UPS.pdf";
                    }
                    try {

                        Iterator packageIterator = packageList.iterator();

                        while (packageIterator.hasNext()) {
                            AascShipmentPackageInfo packageInfo = 
                                (AascShipmentPackageInfo)packageIterator.next();

                            if (!"Y".equalsIgnoreCase(packageInfo.getVoidFlag())) {

                            }
                        }
                    }

                    catch (Exception e) {
                        logger.severe("Exception :" + e.getMessage());
                    }
                } catch (Exception e) {
                    logger.severe("Exception :" + e.getMessage());
                }
            }
            if (returnStatus == 125) {

            } //closing the if block
            if (returnStatus == 125) {
                return 125;
            } //closign the if block
        } //closign the try block
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());

        } //closing the catch block
        return 126;
    } //closig the method printlabel

    /**
     * This method is used for voiding the order
     * @return returnStatus int
     * @param aascShipMethodInfo object 
     * @param aascShipmentOrderInfo object OLD
     * @param aascProfileOptionsInfo ProfileOption Bean Object
     */

    public int voidShipment(AascProfileOptionsBean aascProfileOptionsInfo, 
                            AascShipmentOrderInfo aascShipmentOrderInfo, 
                            AascShipMethodInfo aascShipMethodInfo, 
                            HttpSession session,
                            HttpServletRequest request) {
        logger.info("Entered voidShipment() of Shipment Order Delegate class");
        AascShipmentHeaderInfo headerBean = 
            aascShipmentOrderInfo.getShipmentHeaderInfo();
        LinkedList pkgList = aascShipmentOrderInfo.getShipmentPackageInfo();
        ListIterator pkgListIterator = pkgList.listIterator();
        int pkgSize = pkgList.size();
        int pkgCtr = 1;
        boolean voidComplete = true;
        boolean voidCompleteFedex = true;
        LinkedList accountNumbersList = null;
        ListIterator accountNumbersIterator = null;
        int clientID = 
            Integer.parseInt((session.getAttribute("client_id")).toString());
        String clientId = "" + clientID;

        try {
           clientId.substring(0,1);
        } catch (Exception e) {
            clientID = 
                    Integer.parseInt((session.getAttribute("client_id")).toString());
            //e.printStackTrace();
        }
        while (pkgListIterator.hasNext()) {
            AascShipmentPackageInfo aascPkgBean = 
                (AascShipmentPackageInfo)pkgListIterator.next();

            aascPkgBean.setPackageSequence(String.valueOf(pkgCtr));
            pkgCtr = pkgCtr + 1;
        }

        try {
            int returnStatus = 131;
            int returnStatus1 = 0;
            String orderNumber = headerBean.getOrderNumber();

            String shipMethodMeaning = headerBean.getShipMethodMeaning();
            headerTrackingNumber = headerBean.getWayBill();
            int carrierId = aascShipMethodInfo.getCarrierId(shipMethodMeaning);
            fedExCarrierMode = aascShipMethodInfo.getCarrierMode(carrierId);
            int carrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
            fedExKey = aascShipMethodInfo.getCarrierUserName(carrierId);
            fedExPassword = aascShipMethodInfo.getCarrierPassword(carrierId);

            AascAccountNumbersBean aascAccountNumbersInfo = null;
            String queryTimeOut = (String)session.getAttribute("queryTimeOut");
            int queryTimeOutInt = Integer.parseInt(queryTimeOut);
            int locationId = headerBean.getLocationId() ;
            AascShipmentOrderDelegate aascShipmentOrderDelegate = 
                new AascShipmentOrderDelegate();
            aascAccountNumbersInfo = 
                    aascShipmentOrderDelegate.getAccountNumbersInfo(locationId, carrierCode, 
                                                                    clientID, 
                                                                    queryTimeOutInt);
            carrierACNumber = headerBean.getCarrierAccountNumber();
            if (aascAccountNumbersInfo != null) {
                accountNumbersList = 
                        aascAccountNumbersInfo.getAccountNumbersList();
                if (accountNumbersList != null) {
                    accountNumbersIterator = accountNumbersList.listIterator();
                }
            }
            if (accountNumbersIterator != null) {
                while (accountNumbersIterator.hasNext()) {
                    AascAccountNumbersBean aascAccountNumbersInfo1 = 
                        (AascAccountNumbersBean)accountNumbersIterator.next();
                    String accountNumberMain = 
                        aascAccountNumbersInfo1.getAccountNumber();
                    if (carrierACNumber.equalsIgnoreCase(accountNumberMain)) {
                        String fedexKeyFromPopup = 
                            nullStrToSpc(aascAccountNumbersInfo1.getAccountUserName());
                        String fedexPasswordFrompopup = 
                            nullStrToSpc(aascAccountNumbersInfo1.getAccountPassword());

                        if (fedexKeyFromPopup != "" && 
                            fedexPasswordFrompopup != "") {

                            fedExKey = fedexKeyFromPopup;
                            fedExPassword = fedexPasswordFrompopup;
                        }
                    }
                }
            }
            AascShipMethodInfo aascShipMethodInfoUPS = 
                (AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
            int carrierIdUPS = 
                aascShipMethodInfoUPS.getCarrierIdFromCarrierCode(100);
            String connectionModeUPS = 
                aascShipMethodInfoUPS.getCarrierMode(carrierIdUPS);
            if (carrierCode == 100 && 
                !(headerBean.getManualTrackingFlag().equalsIgnoreCase("Y"))) {
                if (connectionModeUPS.equalsIgnoreCase("Connectship")) {
                    if (returnStatus == 130) {
                        return returnStatus1;
                    }
                }
                // vikas added code
                
                 else if (connectionModeUPS.equalsIgnoreCase("ShipExec")){  // && !(headerBean.getManualTrackingFlag().equalsIgnoreCase("Y"))) {
                    logger.info("headerBean.getManualTrackingFlag()::"+headerBean.getManualTrackingFlag());
                     String rtnPkgTrackingNumber = "";
                     String voidDetailsFedex = "Could not Void The Following Packages ";
                    
                     AascShipExecVoidShipment aascShipExecVoid = new AascShipExecVoidShipment();
                     AascShipmentPackageInfo aascPackageInfo;
                     AascShipmentOrderInfo aascShipmentOrderInfoTmp=aascShipmentOrderInfo;
                     LinkedList packageList=null;
                     LinkedList aascPackageBean = aascShipmentOrderInfo.getShipmentPackageInfo();
                     Iterator packageIterator = aascPackageBean.iterator();
                     voidStatusFlagVal = aascShipmentOrderInfo.getShipmentHeaderInfo().getVoidType();
                     logger.info("voidStatusFlagVal::: "+voidStatusFlagVal);
                     if ("PackageVoid".equalsIgnoreCase(voidStatusFlagVal)) {

                         int size = aascPackageBean.size();
                         int[] arrayReturnStaus = new int[size];
                         int[] arrayPackageSequence = new int[size];

                         int count = 1;
                         int arraycount = 0;

                         while (packageIterator.hasNext()) {

                             aascPackageInfo = 
                                     (AascShipmentPackageInfo)packageIterator.next();
                             String chkVoidFlag = aascPackageInfo.getVoidFlag();
                             logger.info("chkVoidFlag::"+chkVoidFlag);
                             int pkgSeqNum;
                             if (chkVoidFlag.equalsIgnoreCase("Y")) {
                                 logger.info("Pkg Seq ::: "+aascPackageInfo.getPackageSequence());
                                 pkgSeqNum = 
                                         Integer.parseInt(aascPackageInfo.getPackageSequence());
                                 try {
                                     rtnPkgTrackingNumber = aascPackageInfo.getRtnTrackingNumber();
                                     logger.info("rtnPkgTrackingNumber::"+rtnPkgTrackingNumber);
                                     if (!(rtnPkgTrackingNumber.equalsIgnoreCase("")) || 
                                         rtnPkgTrackingNumber != null) {
                                         rtnPkgTrackingNumber = 
                                                 aascPackageInfo.getRtnTrackingNumber();
                                     }
                                     else
                                         rtnPkgTrackingNumber = "";
                                 } catch (Exception e) {
                                     rtnPkgTrackingNumber = "";
                                 }

                                 if (count == pkgSeqNum) {

                                     packageTrackingNumber = 
                                             aascPackageInfo.getTrackingNumber();
                                     logger.info("Package packageTrackingNumber in loop:" + 
                                                 packageTrackingNumber);
                                     String cloudLabelPath = 
                                         (String)session.getAttribute("cloudLabelPath");
                                     packageList=new LinkedList();
                                     packageList.add(aascPackageInfo);
                                     aascShipmentOrderInfoTmp.setShipmentPackageInfo(packageList);
                                     returnStatus = 
                                             aascShipExecVoid.voidShipment(aascShipmentOrderInfoTmp, 
                                                                          aascProfileOptionsInfo,
                                                                      aascShipMethodInfo, 
                                                                      cloudLabelPath,
                                                                      packageTrackingNumber,"PackageVoid");
                                     logger.info("the returnstatus in AascShipmentOrderDelegate at line 652" + 
                                                 returnStatus);

                                     arrayReturnStaus[arraycount] = returnStatus;
                                     arrayPackageSequence[arraycount] = pkgSeqNum;

                                     error = aascShipExecVoid.getError();

                                 }

                             }
                             arraycount = arraycount + 1;
                             count = count + 1;
                             packageList=null;
                         }

                         int flag = 0;

                         for (int i = 0; i < aascPackageBean.size(); i++) {
                             flag = flag + 1;
                             if (arrayReturnStaus[i] == 131) {
                                 voidCompleteFedex = false;
                                 voidDetailsFedex = 
                                         voidDetailsFedex + " " + flag + ",";
                             }

                         }
                         logger.info("voidCompleteFedex : "+voidCompleteFedex); 
                         if (voidCompleteFedex) {
                             logger.info("inside void  in line 698************" + 
                                         voidCompleteFedex);
                             AascVoidShipmentDAO aascVoidShipmentDAO = 
                                 aascDAOFactory.getAascVoidShipmentDAO();
                            logger.info("aascPackageBean.size()::"+aascPackageBean.size());
                             for (int i = 0; i < aascPackageBean.size(); i++) {
                                 returnStatus1 = 
                                         aascVoidShipmentDAO.voidShipmentPackage(orderNumber, 
                                                                                 arrayPackageSequence[i], 
                                                                                 locationId, 
                                                                                 clientID);
                             }
                             return returnStatus1;
                         } else {
                             error = aascShipExecVoid.getError() + voidDetailsFedex;
                             returnStatus = 131;
                             return returnStatus;
                         }

                     } else if("ShipmentVoid".equalsIgnoreCase(voidStatusFlagVal)) {
                         int returnStatusCount = 0;
                         String rtnTrackinNumberVoid = "";
                         int successStatus = 0;
                         int packStatusCount = 0;
                         String packTrackinNumberVoid = "";
                         while (packageIterator.hasNext()) {
                             aascPackageInfo = 
                                     (AascShipmentPackageInfo)packageIterator.next();
                             try {
                                 if (aascPackageInfo.getRtnTrackingNumber().equalsIgnoreCase("") || 
                                     aascPackageInfo.getRtnTrackingNumber() == 
                                     null) {
                                     rtnTrackinNumberVoid = "";
                                 } else {
                                     rtnTrackinNumberVoid = 
                                             aascPackageInfo.getRtnTrackingNumber();
                                 }
                             } catch (Exception e) {
                                 rtnTrackinNumberVoid = "";
                             }

                             try {
                                 if (aascPackageInfo.getTrackingNumber().equalsIgnoreCase("") || 
                                     aascPackageInfo.getTrackingNumber() == null) {
                                     packTrackinNumberVoid = "";
                                 } else {
                                     packTrackinNumberVoid = 
                                             aascPackageInfo.getTrackingNumber();
                                 }
                             } catch (Exception e) {
                                 packTrackinNumberVoid = "";
                             }
                             logger.info(" in shipment void ");
                             logger.info("aascPackageInfo.getReqVoidFlagHidden()::"+aascPackageInfo.getReqVoidFlagHidden());
                             if (!(packTrackinNumberVoid.equalsIgnoreCase("")) && 
                                 ((aascPackageInfo.getReqVoidFlagHidden()).equalsIgnoreCase("N"))) {

                                 String cloudLabelPath = 
                                     (String)session.getAttribute("cloudLabelPath");
                                 returnStatus = 
                                         aascShipExecVoid.voidShipment(aascShipmentOrderInfo, 
                                                                      aascProfileOptionsInfo,
                                                                  aascShipMethodInfo, 
                                                                  cloudLabelPath,
                                                                  packTrackinNumberVoid,"ShipmentVoid");
                                 
                                 if (returnStatus != 130) {
                                     packStatusCount = packStatusCount + 1;
                                 } else {
                                     successStatus = 1;
                                 }
                             } else {
                                 returnStatus = 130;
                             }
                             logger.info("fedExCarrierMode::"+fedExCarrierMode);
                             if (carrierCode == 100 && fedExCarrierMode.equalsIgnoreCase("ShipExec")){
                                 break;
                             }
                             if (((carrierCode == 100)) && intlFlagWS.equalsIgnoreCase("Y")) {

                                 if (fedExCarrierMode.equalsIgnoreCase("ShipExec")) {

                                     break;
                                 }
                             }
                         }
                         if (returnStatus == 130 && packStatusCount == 0 && 
                             returnStatusCount == 0) {

                             AascVoidShipmentDAO aascVoidShipmentDAO = 
                                 aascDAOFactory.getAascVoidShipmentDAO();

                             returnStatus1 = 
                                     aascVoidShipmentDAO.voidShipment(orderNumber, 
                                                                      clientID, "", 
                                                                      locationId);

                             
                             return returnStatus1;
                             
                             
                         }

                     }

                     error = aascShipExecVoid.getError();
                 } // end of  if (aascProfileOptionsInfo.getUpsMode().equalsIgnoreCase("ShipExec")) 
                                // vikas code ended
                else if (connectionModeUPS.equalsIgnoreCase("UPS Direct")) {
                    voidStatusFlagVal = 
                            aascShipmentOrderInfo.getShipmentHeaderInfo().getVoidType();
                    //logger.info("voidStatusFlagVal : "+voidStatusFlagVal);        
                    AascUpsVoid aascUpsVoid = new AascUpsVoid();
                    logger.severe("Calling voidShipment(), if carrier code is UPSDirect mode of the Shipment Ship Save Action Class");
                    String cloudLabelPath = 
                        (String)session.getAttribute("cloudLabelPath");
                    returnStatus = 
                            aascUpsVoid.voidShipment(aascShipmentOrderInfo, 
                                                     aascShipMethodInfo, 
                                                     cloudLabelPath);
                    error = aascUpsVoid.getError();
                    if (returnStatus == 130) {
                        AascVoidShipmentDAO aascVoidShipmentDAO = 
                            aascDAOFactory.getAascVoidShipmentDAO();
                        int statusArray[] = new int[pkgSize];
                        int arrayIndex = 0;
                        String voidDetails = 
                            "Could not Void The Following Packages ";
                        int returnStatusNum = 0;
                        pkgListIterator = pkgList.listIterator();
                        if ("PackageVoid".equalsIgnoreCase(voidStatusFlagVal)) {
                            while (pkgListIterator.hasNext()) {
                                AascShipmentPackageInfo packageBean = 
                                    (AascShipmentPackageInfo)pkgListIterator.next();
                                if ("Y".equalsIgnoreCase(packageBean.getVoidFlag())) {
                                    returnStatusNum = 
                                            aascVoidShipmentDAO.voidShipmentPackage(orderNumber, 
                                                                                    Integer.parseInt(packageBean.getPackageSequence()), 
                                                                                    locationId, 
                                                                                    clientID);

                                } else {
                                    returnStatusNum = 0;
                                }
                                statusArray[arrayIndex] = returnStatusNum;
                            }
                            for (int index = 0; index < statusArray.length; 
                                 index++) {
                                if (statusArray[index] == 131) {
                                    voidComplete = false;
                                    voidDetails = voidDetails + " " + index;
                                }
                            }
                            int updateCostStatus = 0;
                            //logger.info("voidComplete : "+voidComplete);
                            if (voidComplete) {
                                AascShipMethodAjaxDAO aascShipMethodAjaxDAO = aascDAOFactory.getAascShipMethodAjaxDAO();
                                shipMethodMeaning = 
                                        nullStrToSpc(headerBean.getShipMethodMeaning());
                                carrierId = 
                                        aascShipMethodInfo.getCarrierId(shipMethodMeaning);
                                carrierCode = 
                                        aascShipMethodInfo.getCarrierCode(carrierId);
                                        
                                String upsScacCode =
                                     aascShipMethodInfo.getConnectShipScsTag(shipMethodMeaning);
                                String originRegionCode =
                                     nullStrToSpc(headerBean.getShipFromCountry());
                                String upsServiceLevelCode =
                                     aascShipMethodAjaxDAO.retrieveUpsServiceLevelCode(carrierCode,
                                                                                       upsScacCode,
                                                                                       originRegionCode);

                                 headerBean.setCarrierId(carrierId);
                                 headerBean.setUpsServiceLevelCode(upsServiceLevelCode);
        
                                AascUpsShipment aascUpsShipment = 
                                    new AascUpsShipment();
                                cloudLabelPath = 
                                        (String)session.getAttribute("cloudLabelPath");
                                int shipmentCostReturnStatus = 
                                    aascUpsShipment.createShipmentRequest(aascShipmentOrderInfo, 
                                                                          aascShipMethodInfo, 
                                                                          aascProfileOptionsInfo, 
                                                                          aascIntlInfo, 
                                                                          cloudLabelPath);

                                AascShipmentHeaderInfo aascHeaderInfoBean = 
                                    aascShipmentOrderInfo.getShipmentHeaderInfo();

                                logger.info("shipmentCostReturnStatus : "+shipmentCostReturnStatus);
                                if (shipmentCostReturnStatus == 150) {
                                    updateCostStatus = 
                                            aascVoidShipmentDAO.updateShipmentCost(orderNumber, 
                                                                                   aascHeaderInfoBean.getShipmentCost(), 
                                                                                   clientID, 
                                                                                   locationId);
                                    if (updateCostStatus == 0) {
                                        returnStatus = 130;
                                        return returnStatus;
                                    } else {
                                        returnStatus = 161;
                                        return returnStatus;
                                    }

                                } else {
                                    returnStatus = 161;
                                    return returnStatus;
                                }

                            } else {
                                error = error + voidDetails;
                                returnStatus = 131;
                                return returnStatus;
                            }
                        } else if ("ShipmentVoid".equalsIgnoreCase(voidStatusFlagVal)) {
                            oldPackcount = 0;
                            aascVoidShipmentDAO = 
                                    aascDAOFactory.getAascVoidShipmentDAO();
                            returnStatus1 = 
                                    aascVoidShipmentDAO.voidShipment(orderNumber, 
                                                                     clientID, 
                                                                     "", 
                                                                     locationId);

                            return returnStatus1;

                        } // end of  if(voidStatusFlagVal.equalsIgnoreCase("ShipmentVoid"))
                    } // end of if(returnStatus==130)
                    else {
                        returnStatus = 131;
                        return returnStatus;
                    }
                } // end of  if (aascProfileOptionsInfo.getUpsMode().equalsIgnoreCase("UPS Direct"))                                
            } // end of if (carrierCode == 100)
            else if ((carrierCode == 110 || carrierCode == 111) && 
                     !(headerBean.getManualTrackingFlag().equalsIgnoreCase("Y"))) {
                String rtnPkgTrackingNumber = "";
                String voidDetailsFedex = 
                    "Could not Void The Following Packages ";

                logger.info("In FedEx Void of voidShipment() of the Shipment Order Delegate Class");
                AascFedexVoid aascFedexVoid = new AascFedexVoid();

                AascShipmentPackageInfo aascPackageInfo;
                LinkedList aascPackageBean = 
                    aascShipmentOrderInfo.getShipmentPackageInfo();
                Iterator packageIterator = aascPackageBean.iterator();
                voidStatusFlagVal = aascShipmentOrderInfo.getShipmentHeaderInfo().getVoidType();
                //logger.info("voidStatusFlagVal : "+voidStatusFlagVal);
                if ("PackageVoid".equalsIgnoreCase(voidStatusFlagVal)) {

                    int size = aascPackageBean.size();
                    int[] arrayReturnStaus = new int[size];
                    int[] arrayPackageSequence = new int[size];

                    int count = 1;
                    int arraycount = 0;

                    while (packageIterator.hasNext()) {

                        aascPackageInfo = 
                                (AascShipmentPackageInfo)packageIterator.next();
                        String chkVoidFlag = aascPackageInfo.getVoidFlag();
                        int pkgSeqNum;
                        if (chkVoidFlag.equalsIgnoreCase("Y")) {
                            //logger.info("Pkg Seq : "+aascPackageInfo.getPackageSequence());
                            pkgSeqNum = 
                                    Integer.parseInt(aascPackageInfo.getPackageSequence());
                            try {
                                rtnPkgTrackingNumber = aascPackageInfo.getRtnTrackingNumber();
                                if (!(rtnPkgTrackingNumber.equalsIgnoreCase("")) || 
                                    rtnPkgTrackingNumber != null) {
                                    rtnPkgTrackingNumber = 
                                            aascPackageInfo.getRtnTrackingNumber();
                                }
                                else
                                    rtnPkgTrackingNumber = "";
                            } catch (Exception e) {
                                rtnPkgTrackingNumber = "";
                            }

                            if (count == pkgSeqNum) {

                                packageTrackingNumber = 
                                        aascPackageInfo.getTrackingNumber();
                                logger.info("Package packageTrackingNumber in loop:" + 
                                            packageTrackingNumber);
                                String cloudLabelPath = 
                                    (String)session.getAttribute("cloudLabelPath");
                                returnStatus = 
                                        aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                                   aascShipMethodInfo, 
                                                                   packageTrackingNumber, 
                                                                   aascProfileOptionsInfo, 
                                                                   fedExCarrierMode, 
                                                                   fedExKey, 
                                                                   fedExPassword, 
                                                                   cloudLabelPath);
                                logger.info("the returnstatus in AascShipmentOrderDelegate at line 652" + 
                                            returnStatus);


                                if (rtnPkgTrackingNumber == null)
                                    rtnPkgTrackingNumber = "";

                                if (returnStatus == 130 && 
                                    !(rtnPkgTrackingNumber.equalsIgnoreCase(""))) {

                                    returnStatus = 
                                            aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                                       aascShipMethodInfo, 
                                                                       rtnPkgTrackingNumber, 
                                                                       aascProfileOptionsInfo, 
                                                                       fedExCarrierMode, 
                                                                       fedExKey, 
                                                                       fedExPassword, 
                                                                       cloudLabelPath);
                                    logger.info("the returnstatus in AascShipmentOrderDelegate at line 669*********" + 
                                                returnStatus);


                                }
                                arrayReturnStaus[arraycount] = returnStatus;
                                arrayPackageSequence[arraycount] = pkgSeqNum;

                                error = aascFedexVoid.getError();

                            }

                        }
                        arraycount = arraycount + 1;
                        count = count + 1;
                    }

                    int flag = 0;

                    for (int i = 0; i < aascPackageBean.size(); i++) {
                        flag = flag + 1;
                        if (arrayReturnStaus[i] == 131) {
                            voidCompleteFedex = false;
                            voidDetailsFedex = 
                                    voidDetailsFedex + " " + flag + ",";
                        }

                    }
                    logger.info("voidCompleteFedex : "+voidCompleteFedex); 
                    if (voidCompleteFedex) {
                        logger.info("inside void compleet fedex in line 698************" + 
                                    voidCompleteFedex);
                        AascVoidShipmentDAO aascVoidShipmentDAO = 
                            aascDAOFactory.getAascVoidShipmentDAO();

                        for (int i = 0; i < aascPackageBean.size(); i++) {
                            returnStatus1 = 
                                    aascVoidShipmentDAO.voidShipmentPackage(orderNumber, 
                                                                            arrayPackageSequence[i], 
                                                                            locationId, 
                                                                            clientID);
                        }
                        return returnStatus1;
                    } else {
                        error = aascFedexVoid.getError() + voidDetailsFedex;
                        returnStatus = 131;
                        return returnStatus;
                    }

                } else if("ShipmentVoid".equalsIgnoreCase(voidStatusFlagVal)) {
                    int returnStatusCount = 0;
                    String rtnTrackinNumberVoid = "";
                    int successStatus = 0;
                    int packStatusCount = 0;
                    String packTrackinNumberVoid = "";
                    while (packageIterator.hasNext()) {
                        aascPackageInfo = 
                                (AascShipmentPackageInfo)packageIterator.next();
                        try {
                            if (aascPackageInfo.getRtnTrackingNumber().equalsIgnoreCase("") || 
                                aascPackageInfo.getRtnTrackingNumber() == 
                                null) {
                                rtnTrackinNumberVoid = "";
                            } else {
                                rtnTrackinNumberVoid = 
                                        aascPackageInfo.getRtnTrackingNumber();
                            }
                        } catch (Exception e) {
                            rtnTrackinNumberVoid = "";
                        }

                        try {
                            if (aascPackageInfo.getTrackingNumber().equalsIgnoreCase("") || 
                                aascPackageInfo.getTrackingNumber() == null) {
                                packTrackinNumberVoid = "";
                            } else {
                                packTrackinNumberVoid = 
                                        aascPackageInfo.getTrackingNumber();
                            }
                        } catch (Exception e) {
                            packTrackinNumberVoid = "";
                        }
                        if (!(packTrackinNumberVoid.equalsIgnoreCase("")) && 
                            ((aascPackageInfo.getReqVoidFlagHidden()).equalsIgnoreCase("N"))) {

                            String cloudLabelPath = 
                                (String)session.getAttribute("cloudLabelPath");
                            returnStatus = 
                                    aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                               aascShipMethodInfo, 
                                                               packTrackinNumberVoid, 
                                                               aascProfileOptionsInfo, 
                                                               fedExCarrierMode, 
                                                               fedExKey, 
                                                               fedExPassword, 
                                                               cloudLabelPath);
                            if (returnStatus != 130) {
                                packStatusCount = packStatusCount + 1;
                            } else {
                                successStatus = 1;
                            }
                        } else {
                            returnStatus = 130;
                        }
                        if (returnStatus == 130 && packStatusCount == 0) {

                            if (!(rtnTrackinNumberVoid.equalsIgnoreCase(""))) {

                                String cloudLabelPath = 
                                    (String)session.getAttribute("cloudLabelPath");
                                returnStatus = 
                                        aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                                   aascShipMethodInfo, 
                                                                   rtnTrackinNumberVoid, 
                                                                   aascProfileOptionsInfo, 
                                                                   fedExCarrierMode, 
                                                                   fedExKey, 
                                                                   fedExPassword, 
                                                                   cloudLabelPath);
                                if (returnStatus != 130) {
                                    returnStatusCount = returnStatusCount + 1;
                                }
                            } else {
                                returnStatus = 130;
                            }
                        }
                        if (carrierCode == 110 && 
                            (fedExCarrierMode.equalsIgnoreCase("FedexWebServices") || 
                             fedExCarrierMode.equalsIgnoreCase("WEBSERVICES")) && 
                            "Y".equalsIgnoreCase(aascPackageInfo.getCodFlag()) && 
                            "1".equalsIgnoreCase(aascPackageInfo.getPackageSequence())) {

                            break;
                        }
                        if (((carrierCode == 110) || (carrierCode == 111)) && 
                            intlFlagWS.equalsIgnoreCase("Y")) {

                            if (fedExCarrierMode.equalsIgnoreCase("FedexWebServices") || 
                                fedExCarrierMode.equalsIgnoreCase("WEBSERVICES")) {

                                break;
                            }
                        }
                    }
                    if (returnStatus == 130 && returnStatusCount == 0 && 
                        successStatus == 0) {

                        String cloudLabelPath = 
                            (String)session.getAttribute("cloudLabelPath");
                        returnStatus = 
                                aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                           aascShipMethodInfo, 
                                                           headerTrackingNumber, 
                                                           aascProfileOptionsInfo, 
                                                           fedExCarrierMode, 
                                                           fedExKey, 
                                                           fedExPassword, 
                                                           cloudLabelPath);
                    }


                    if (returnStatus == 130 && packStatusCount == 0 && 
                        returnStatusCount == 0) {

                        AascVoidShipmentDAO aascVoidShipmentDAO = 
                            aascDAOFactory.getAascVoidShipmentDAO();

                        returnStatus1 = 
                                aascVoidShipmentDAO.voidShipment(orderNumber, 
                                                                 clientID, "", 
                                                                 locationId);

                        return returnStatus1;
                    }

                }

                error = aascFedexVoid.getError();
            }
                                                //Mahesh Start
                                                else if (carrierCode == 115) {
                                                AascStampsVoid aascStampsVoid = new AascStampsVoid();
                                                String cloudLabelPath = 
                                                (String)session.getAttribute("cloudLabelPath");
                                                String shipError = "";
                                                String warningChk = "";
                                                String errorDesc = "";
                                                String carrierStatusTemp1 =  aascStampsVoid.voidShipment(aascShipmentOrderInfo, aascShipMethodInfo, cloudLabelPath);
                                                
                                                String subStatus1 = carrierStatusTemp1.substring(0,3);
                                                
                                                int carrierstatus = Integer.parseInt(subStatus1);
                                                
                                                String stampStatus = carrierStatusTemp1.substring(carrierStatusTemp1.indexOf("@@")+2,carrierStatusTemp1.length());
                                                aascShipmentOrderInfo.getShipmentHeaderInfo().setErrorChk(stampStatus);
                                                aascShipmentOrderInfo.getShipmentHeaderInfo().setError(stampStatus);
                                                aascShipmentOrderInfo.getShipmentHeaderInfo().setMainError(stampStatus);
                                                if("131".equalsIgnoreCase(subStatus1)){
                                                shipError =aascShipmentOrderInfo.getShipmentHeaderInfo().getErrorChk();
                                                logger.info("shipError===="+shipError);
                                                request.setAttribute("shipError", shipError);           // Setting them to request instead of session.
                                                errorDesc = aascShipmentOrderInfo.getShipmentHeaderInfo().getError();
                                                request.setAttribute("errorDesc", errorDesc);
                                                request.setAttribute("error",stampStatus);
                                                
                                                }else if ("130".equalsIgnoreCase(subStatus1)){
                                                returnStatus = 130;
                                                
                                                if (returnStatus == 130 ) {
                                                
                                                AascVoidShipmentDAO aascVoidShipmentDAO = 
                                                aascDAOFactory.getAascVoidShipmentDAO();
                                                
                                                returnStatus1 = 
                                                aascVoidShipmentDAO.voidShipment(orderNumber, 
                                                clientID, "", 
                                                locationId);
                                                
                                                return returnStatus1;
                                                }
                                                
                                                }
                                                return returnStatus;
                                                }//Else if 115 end
                                                
                                                //Mahesh End
                                                
                                                
                                                

            else {

                logger.info("In else part");
                AascVoidShipmentDAO aascVoidShipmentDAO = 
                    aascDAOFactory.getAascVoidShipmentDAO();
                returnStatus1 = 
                        aascVoidShipmentDAO.voidShipment(orderNumber, clientID, 
                                                         "", locationId);
                return returnStatus1;
            }

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }

        return returnStatus;

    }
    
    
    public int verifyCustomerName(HttpServletRequest request){
        HttpSession session = request.getSession();
        
        String addressBookLevel = (String)session.getAttribute("addressBookLevel");
        logger.info("address book level:::"+addressBookLevel);
        int status = 100;
        if("UL".equalsIgnoreCase(addressBookLevel)){
            int userId = (Integer)session.getAttribute("user_id");
            int clientId = (Integer)session.getAttribute("client_id");
            logger.info("user id ::::"+userId);
            String customerName = request.getParameter("customerName");
            status = aascShipmentOrderInfoDAO.verifyCustomerName(clientId,userId,customerName);
        }
        return status;
        
    }

    /**
     * This method can replace the null values with nullString
     * @return String that is ""
     * @param obj object of type Object
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } //closing the if block
        else {
            return obj.toString();
        } //closing the else block
    } //closig the method nullStrToSpc

}
