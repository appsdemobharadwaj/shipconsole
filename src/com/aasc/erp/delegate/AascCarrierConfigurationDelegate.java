package com.aasc.erp.delegate;

/**
 * AascCarrierConfigurationDelegate class is delegate class for Carrier COnfiguration.
 * @version   2
 * @author    Venkateswaramma Malluru
 * @History
 *
 * 10/12/2014   Y Pradeep   Fixed all issues relaed to UI, Roles, Validations. Addded code for missed fileds for retriving and saving. Arranged code in proper order.
 * 24/12/2014   Y Pradeep   Commited code after Self Audit.
 * 07/01/2015   Eshwari M   Merged Sunanda's code of calling getAllLocationDetails() method call as this method modiifed.
 * 07/01/2015   Y Pradeep   Rename key1 to key as per coding standerds. Removed and commented unused loggers.
 * 15/04/2015   Eshwari M   Modified code to fix bug # 2582
 * 17/11/2015   Shiva  G    Added dhlRegionCode for DHL
 */

import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.bean.AascCarrierConfigurationBean;
import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.model.AascCarrierConfigurationDAO;
import com.aasc.erp.model.AascOracleCarrierConfigurationDAO;
import com.aasc.erp.model.AascAccountNumbersDAO;


import com.aasc.erp.model.AascOracleAccountNumbersInfoDAO;
import com.aasc.erp.model.AascOracleSetupLocationDAO;
import com.aasc.erp.model.AascOracleShipMethodDAO;
import com.aasc.erp.model.AascSetupLocationDAO;
import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.util.AascLogger;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AascCarrierConfigurationDelegate {

    private String carrierName; // holds the carrierName value return by the get() method
    private String protocol; // holds the protocol value return by the get() method
    String port1;
    private String key;
    private int port; // holds the port value return by the get method()
    private String serverIpAddress; // holds the serverIpAddress value return by the get() method
    private String userName; // holds the userName value return by the get() method
    private String password; // holds the password value return by the get() method
    private String prefix; // holds the prefix value return by the get() method
    private String domain; // holds the domain value return by the get() method
    private String accountNo; // holds the accountNo value return by the get() method
    private String integrationId;
    // declaring the variables for the printer details

    private String pdPort; // holds the pdPort value return by the get() method
    private String modelSymbol; // holds the modelSymbol value return by the get() method
    private String stockSymbol; // holds the stockSymbol value return by the get() method
    private String labelStock = "";
    private String docTab = "";
    private String accessLicenseNumber = ""; // holds access license number from carrier configuration screen
    private HashMap labelFormats = null;
    private HashMap carrierMode = null;
    private HashMap labelStockOrientation = null;
    private HashMap docTabLocation = null;
    
    //Mahesh
//     private HashMap intlLabelFormat = null;
     private HashMap paperSize = null;
     private HashMap intlPrintLayout = null;
     private HashMap nonDelivery = null;
     
    private String intlLabel = null;
    private String stampsPaperSize = null;
    private String intlPrintLay = null;
    private String nonDeliveryOption = null;

    //Mahesh End
    
    HashMap intlLabelFormats = null;
//    LinkedList sessionList = null;
    private int userID; //to hold UserId
    private String meterNumber = ""; // holds value for meterNumber
    private String nonDiscountedCost = "";
    private String acctNumNegotiatedFlag = "";
    private String enableFlag = "";
    private int carrierCodeValue = 0;
    private String carrierModeStr = "";
    //For Email Notification
    private String emailNotificationFlag = ""; // Flag to check whether email notification is required or not
    private String senderEmailAddress = ""; // Email Address of the sender
    private String referenceFlag1 = ""; // Flag to check whether reference1 is included in email notification or not
    private String referenceFlag2 = ""; // Flag to check whether reference2 is included in email notification or not
    private String shipNotificationFlag = ""; // Flag which indicates Ship notification
    private String deliveryNotificationFlag = ""; // Flag which indicates Delivery notification
    private String exceptionNotificationFlag = ""; // Flag which indicates Exception notification
    private String formatType = ""; // Type of format(text,html,wireless) of email notification

    //SC_EMail_SJ
    private String salesOrderNumber = "";
    private String customerName = "";
    private String deliveryItemNumbers = "";

    private String supportHazmatShipping = "";
    private String hazmatOp900Format = "";  // holds op900 label format
    
    private String dhlRegionCode ="";

    private String IntlDocSubType="";    // Added by Jagadish
    private Integer clientId;
    private int carrierCodeTemp;
    private static Logger logger = AascLogger.getLogger("AascCarrierConfiguration");
  
    
    public AascCarrierConfigurationDelegate() {
    }
    
    // added below if condition to get Location Details based on client id in Carrier Configuration.

    /** This method is called from action class when Client id is selected in Carrier configuration page. 
     * This method loads Location names based on Client id
     * @param session
     * @param request
     * @return success or error message to action class
     */
    public String locationAction(HttpSession session, HttpServletRequest request) {
        try {
            LinkedList locationList = new LinkedList();
            String clientIdStr1 = request.getParameter("clientId");
            clientId = Integer.parseInt(clientIdStr1);
            AascSetupLocationDAO aascSetupLocationDAO = new AascOracleSetupLocationDAO();
            HashMap locationHashMap = aascSetupLocationDAO.getAllLocationDetails(clientId);     
            locationList = (LinkedList)locationHashMap.get("locationList");
            HashMap locationDetailsMap = new HashMap();
            Iterator locationListIterator = locationList.iterator();
            AascSetupLocationBean aascSetupLocationBean = null ;
            for (int i = 0; i < locationList.size(); i++) {
                aascSetupLocationBean = (AascSetupLocationBean)locationListIterator.next();
                /* Storing the location Id and the location name in a hash map and storing the map in a session
                           for using it as LOV in all the settings pages */
                if("Y".equalsIgnoreCase(aascSetupLocationBean.getLocationStatus()))           
                   locationDetailsMap.put(aascSetupLocationBean.getLocationId(), aascSetupLocationBean.getLocationName());

            }
            session.setAttribute("locationsList", locationList);
            session.setAttribute("locationDetailsMap", locationDetailsMap);
        } catch (Exception e) {
            logger.info("Exception in locationAction"+e.getMessage());
//            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /** This method is called from action class when user select Coarrier Code is selected in Carrier configuration page. 
     * This method is used to get lookup values and details when Coarrier Code is selected like 
     * FedEx, UPS or DHL from the Carrier Code select box.
     * @param aascCarrierConfigurationBean
     * @param session
     * @return success or error message to action class
     */
    public String loadAction(AascCarrierConfigurationBean aascCarrierConfigurationBean,HttpSession session) {
        try {
            int onChangeCarrierCode = aascCarrierConfigurationBean.getCarrierCodeValue();
           //     request.getParameter("carrierCodeValue");
//                
//            logger.info("onChangeCarrierCode ::::::::::::::::::::"+onChangeCarrierCode); 
//          //  logger.info("ClientId"+clientId);
            int onChangeCarrierCodeInt = onChangeCarrierCode;
//            carrierName = (String)request.getParameter("carrierNameStr");
//
//            aascCarrierConfigurationBean.setCarrierCodeValue(onChangeCarrierCode);
//            aascCarrierConfigurationBean.setClientId(clientId);
//            aascCarrierConfigurationBean.setCarrierName(carrierName);
            AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();

            carrierMode = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "CMODE", "");
            session.setAttribute("carrierModeMap", carrierMode);
            
//            logger.info("carrierMode::::::::::::::::::"+carrierMode);

            labelFormats = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "LABEL_FORMAT", "");
            session.setAttribute("labelFormats", labelFormats);
//            logger.info("labelFormats::::::::::::::::::"+labelFormats);
            
            labelStockOrientation = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "LABEL_STOCK_ORIENTATION", "");
//            logger.info("labelStockOrientation::::::::::::::::::"+labelStockOrientation);

            docTabLocation = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "DOC_TAB_LOCATION", "");
//            logger.info("docTabLocation::::::::::::::::::"+docTabLocation);

            session.setAttribute("labelStockOrientation", labelStockOrientation);
            session.setAttribute("docTabLocation", docTabLocation);
            
            //Mahesh
//            intlLabelFormat = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "INTL_LABEL_FORMAT", "");
            paperSize = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "PAPER_SIZE", "");
            intlPrintLayout = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "INTL_PRINT_LAYOUT", "");
            nonDelivery = aascCarrierConfigurationDAO.getCarrierLookUpValues(onChangeCarrierCodeInt, "NON_DEL_OPTION", "");

            
//            session.setAttribute("intlLabelFormat", intlLabelFormat);
            session.setAttribute("paperSize", paperSize);
            session.setAttribute("intlPrintLayout", intlPrintLayout);
            session.setAttribute("nonDelivery", nonDelivery);

            //Mahesh End
            return "success";
        } catch (Exception e) {
            logger.info("Exception in loadAction"+e.getMessage());
//            e.printStackTrace();
            return "error";
        }
    }

    /** This method is called from action class when user clicked on Shipmethod Mapping button in Carrier configuration page. 
     * This method is get required list of values and navigate to Shipmethod Mapping page.
     * @param session
     * @param aascCarrierConfigurationBean
     * @param request
     * @return success or error message to action class
     */
    public String shipMethodAction(HttpSession session, AascCarrierConfigurationBean aascCarrierConfigurationBean,HttpServletRequest request) {
        try {
            LinkedList shipMethodlist=null;
            int carrierCode = 0;
            String carrierMode =aascCarrierConfigurationBean.getCarrierMode();
            if("".equalsIgnoreCase(carrierMode)) {
                carrierMode = request.getParameter("Mode");
            }
            int carrierIDTemp = aascCarrierConfigurationBean.getCarrierId();
            //(String)aascProfileOptionsInfo.getCarrierName();
            String carrierNameTemp = aascCarrierConfigurationBean.getCarrierName();
            String carrierCodeTemp1 = request.getParameter("carrierCodeVal");
            if(!"".equalsIgnoreCase(carrierCodeTemp1) && carrierCodeTemp1 != null){
                carrierCode = Integer.parseInt(carrierCodeTemp1);
                aascCarrierConfigurationBean.setCarrierCodeValue(carrierCode);
            } else 
                carrierCode = aascCarrierConfigurationBean.getCarrierCodeValue();
            int carrierCodeTemp = aascCarrierConfigurationBean.getCarrierCodeValue();
            clientId = aascCarrierConfigurationBean.getClientId();
           // int roleid=aascCarrierConfigurationBean.
            int locationid = aascCarrierConfigurationBean.getLocationId();
            int userid = aascCarrierConfigurationBean.getUserId();
             
             
            session.setAttribute("carrierName", carrierNameTemp);
            session.setAttribute("carrierCode", carrierCodeTemp);
            session.setAttribute("carrierID", carrierIDTemp);
            session.setAttribute("locationid", locationid);           
            session.setAttribute("clientIdN",clientId);
            session.setAttribute("userid",userid);
            session.setAttribute("carrierMode", carrierMode);
            
            
            logger.info("carrierName::" + carrierNameTemp + "\n carrierCode" + 
                        carrierCodeTemp + "\n carrierIDTemp:" + 
                        carrierIDTemp+"\n" + 
                        " clientId:"+clientId+"\n" + 
                        " userId:"+userid);

            AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();
            
            HashMap validationHashMap = aascCarrierConfigurationDAO.getLookUpDetails("SHIPMETHOD_VALIDATION_CODE");
            request.setAttribute("validationHashMap", validationHashMap);

            int carrierId = carrierIDTemp;
            
//            logger.info("carrierCode  "+carrierCode);
            
//request.setAttribute("carrierCode",carrierCode);

          //  HashMap carrierServiceList = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "CARRIER_SERVICE_LEVEL", carrierMode);
            HashMap hmlist = aascCarrierConfigurationDAO.getCarrierShipMethodValues(carrierCode, "CARRIER_SERVICE_LEVEL", carrierMode);
                HashMap carrierServiceList  = (HashMap<String,?>)hmlist.get("carrierHashMap");
                HashMap shipMethodList = (HashMap<String,?>)hmlist.get("shipMethodHashMap");
            
            request.setAttribute("carrierServiceList", carrierServiceList);
            request.setAttribute("shipMethodList", shipMethodList);

            Integer clientIdNum = (Integer)session.getAttribute("client_id");
            if (clientId == null) {
                clientId  =  clientIdNum;
            }    

            AascShipMethodDAO aascShipMethodDAO = new AascOracleShipMethodDAO();
           // AascShipMethodInfo aascShipMethodInfo2 = new AascShipMethodInfo();
//            logger.info("carrierId :::::::::"+carrierId+" &&&& clientId :::::::"+clientId);
            try{
                shipMethodlist  = aascShipMethodDAO.getShipMethodMappingInfo(carrierId,clientId);
//                logger.info("shipMethodlist ::"+shipMethodlist);
            }catch(Exception e){
                logger.info("Exception in shipMethodAction"+e.getMessage());
//                e.printStackTrace();
            }
//            if(shipMethodlist==null){
//                return "error";}
//                else{
            session.setAttribute("shipMethodlist", shipMethodlist);
            return "success";
            //}
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "error";
        }
    }

    /** This method is called from action class when user clicked on Go button in Carrier configuration page. 
     * This method loads all saved details in Carrier Configuration page based on Carrier Code, Client id and Location id.
     * @param aascCarrierConfigurationBean
     * @param session
     * @return success or error message to action class
     */
    public String getCarrierConfigurationDetails(AascCarrierConfigurationBean aascCarrierConfigurationBean,HttpSession session){
       
        AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();
//        LinkedList locationList = new LinkedList();
        try{
            aascCarrierConfigurationBean= aascCarrierConfigurationDAO.getCarrierConfigurationDetails(aascCarrierConfigurationBean);
        }catch(Exception e){
            logger.info("Exception in getCarrierConfigurationDetails"+e.getMessage());
//            e.printStackTrace();
        }
        
        labelFormats = null;
        carrierMode = null;
        int oldcarrierCode = aascCarrierConfigurationBean.getCarrierCodeValue();
        int newCarrierCode = oldcarrierCode;
        
        labelFormats = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "LABEL_FORMAT", "");
//        logger.info("labelFormats::::::::"+labelFormats.toString());

        carrierMode = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "CMODE", ""); // rajesh modified    
//        logger.info("carrierMode::::::::"+carrierMode.toString());

                /*  if (carrierCode == 100
                                            && upsMode.equalsIgnoreCase("ConnectShip")) {

                                        labelFormats = new HashMap();

                                        labelFormats.put("STRING", "STRING");

                                    }*/

        labelStockOrientation = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "LABEL_STOCK_ORIENTATION","");

        docTabLocation = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "DOC_TAB_LOCATION","");
         

    //            logger.info("********1026*********eodLabelFormats**********" + 
    //                               eodLabelFormats);

                // End
//        logger.info("labelStockOrientation ::::::::::::"+labelStockOrientation.toString());
//        logger.info("docTabLocation :::::::::::::::"+docTabLocation);
//       logger.info("carrierModeMap ::::::"+carrierMode);
       
        session.setAttribute("labelFormats", labelFormats);
        session.setAttribute("carrierModeMap", carrierMode);
        session.setAttribute("labelStockOrientation", labelStockOrientation);
        session.setAttribute("docTabLocation", docTabLocation);
        session.setAttribute("aascCarrierConfiguration", aascCarrierConfigurationBean);
        session.setAttribute("shipMethodChk", "Y");
        
        
        //Mahesh
//        intlLabelFormat = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "INTL_LABEL_FORMAT", "");
        paperSize = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "PAPER_SIZE", "");
        intlPrintLayout = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "INTL_PRINT_LAYOUT", "");
        nonDelivery = aascCarrierConfigurationDAO.getCarrierLookUpValues(newCarrierCode, "NON_DEL_OPTION", "");
//        System.out.println("intlLabelFormat-1-"+intlLabelFormat);
        System.out.println("intlPrintLayout-1-"+intlPrintLayout);
        System.out.println("paperSize1--"+paperSize);
        System.out.println("nonDelivery-1-"+nonDelivery);

        
//        session.setAttribute("intlLabelFormat", intlLabelFormat);
        session.setAttribute("paperSize", paperSize);
        session.setAttribute("intlPrintLayout", intlPrintLayout);
        session.setAttribute("nonDelivery", nonDelivery);

        //Mahesh End

        return "success";
    }

    /** This method is called from action class when user clicked on Save button in Carrier configuration page. 
     * This method get data assigned to particular fields and save in database based on client id, location id and carrier code.
     * @param aascCarrierConfigurationBean
     * @param session
     * @param request
     */
    public void saveUpdateAction(AascCarrierConfigurationBean aascCarrierConfigurationBean,HttpSession session, HttpServletRequest request) {
        try {
            carrierName = aascCarrierConfigurationBean.getCarrierName();
            AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();
            int carrierCode = 0;
            if (request.getParameter("carrierCodeValue") != null) {
                carrierCode = Integer.parseInt((String)request.getParameter("carrierCodeValue"));
            } else {
                carrierCode =aascCarrierConfigurationBean.getCarrierCodeValue();
            }
            request.setAttribute("carrierCode",carrierCode);

            labelFormats = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "LABEL_FORMAT","");
            carrierMode = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "CMODE","");
            labelStockOrientation = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "LABEL_STOCK_ORIENTATION","");
            docTabLocation = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "DOC_TAB_LOCATION","");
            
            session.setAttribute("labelFormats", labelFormats);
            session.setAttribute("carrierMode", carrierMode);
            session.setAttribute("labelStockOrientation", labelStockOrientation);
            session.setAttribute("docTabLocation", docTabLocation);


            //Mahesh
//            intlLabelFormat = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "INTL_LABEL_FORMAT", "");
            paperSize = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "PAPER_SIZE", "");
            intlPrintLayout = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "INTL_PRINT_LAYOUT", "");
            nonDelivery = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "NON_DEL_OPTION", "");

            
//            session.setAttribute("intlLabelFormat", intlLabelFormat);
            session.setAttribute("paperSize", paperSize);
            session.setAttribute("intlPrintLayout", intlPrintLayout);
            session.setAttribute("nonDelivery", nonDelivery);

            //Mahesh End
         

            protocol = aascCarrierConfigurationBean.getProtocol(); // getting the jsp value of the protocol from the struts-config.xml file


            port1 = "" + aascCarrierConfigurationBean.getPort(); // getting the jsp value of the port from the struts-config.xml file

            if (!port1.equalsIgnoreCase("")) {
                port = Integer.parseInt(port1);
            }

            serverIpAddress = aascCarrierConfigurationBean.getServerIpAddress(); // getting the jsp value of the serverIpAddress from the struts-config.xml file
            
            userName = aascCarrierConfigurationBean.getUserName(); // getting the jsp value of the userName from the struts-config.xml file

            password = aascCarrierConfigurationBean.getPassword(); // getting the jsp value of the password from the struts-config.xml file

            prefix = aascCarrierConfigurationBean.getPrefix(); // getting the jsp value of the prefix from the struts-config.xml file

            domain = aascCarrierConfigurationBean.getDomain(); // getting the jsp value of the prefix from the struts-config.xml file

            accountNo = aascCarrierConfigurationBean.getAccountNo();
            
            integrationId = aascCarrierConfigurationBean.getIntegrationId();

            pdPort = request.getParameter("pdPort");
            
            stockSymbol = aascCarrierConfigurationBean.getStockSymbol(); // getting the jsp value of the stockSymbol from the struts-config.xml file

            labelStock = aascCarrierConfigurationBean.getLabelStock();

            docTab = aascCarrierConfigurationBean.getDocTab();
            
//            intlLabel =  aascCarrierConfigurationBean.getIntlLabelFormat();
            intlPrintLay = aascCarrierConfigurationBean.getIntlPrintLayout();
            stampsPaperSize = aascCarrierConfigurationBean.getPaperSize();
            nonDeliveryOption = aascCarrierConfigurationBean.getNonDelivery();



            accessLicenseNumber = aascCarrierConfigurationBean.getAccessLicenseNumber();

            meterNumber = aascCarrierConfigurationBean.getMeterNumber();

            nonDiscountedCost = request.getParameter("nonDiscountedCost");
            
            acctNumNegotiatedFlag = request.getParameter("acctNumNegotiatedFlag");

            emailNotificationFlag = request.getParameter("emailNotificationFlag");

            senderEmailAddress = request.getParameter("SenderEmail");
          
            referenceFlag1 = request.getParameter("IsReference1Required");

            referenceFlag2 = request.getParameter("IsReference2Required");

            //SC_EMail_SJ
            salesOrderNumber = request.getParameter("CarrierSalesOrderNumber");

            customerName = request.getParameter("CarrierCustomerName");
            
            deliveryItemNumbers = request.getParameter("deliveryItemNumbers");
            
            shipNotificationFlag = request.getParameter("ShipNotification");

            exceptionNotificationFlag = request.getParameter("ExceptionNotification");

            deliveryNotificationFlag = request.getParameter("DeliveryNotification");

            enableFlag = request.getParameter("enableFlag");
          
            carrierCodeValue = Integer.parseInt(request.getParameter("carrierCodeValue"));

            carrierModeStr = request.getParameter("Mode");

            formatType = request.getParameter("Format");

           // op900LabelFormat = request.getParameter("Op900LabelFormat");

           // logger.info("op900LabelFormat : " + op900LabelFormat);

            supportHazmatShipping = request.getParameter("supportHazmatShipping");
            
            hazmatOp900Format = request.getParameter("Op900LabelFormat");
            
            aascCarrierConfigurationBean.setOp900Format(hazmatOp900Format);

            IntlDocSubType=request.getParameter("IntlDocSubType");  // Added by Jagadish
            
            //Shiva added below code for DHL 
             if(carrierCode==114) {
                 dhlRegionCode =request.getParameter("dhlRegionCode");
             }else{
                 dhlRegionCode="";
             }
            //Shiva code end

            try {
                if (nonDiscountedCost.equals("Y")) {
                    nonDiscountedCost = "Y";
                } else
                {
                    nonDiscountedCost = "N";
                }
            } catch (Exception e) {
                nonDiscountedCost = "N";
            }


            try {
                if (acctNumNegotiatedFlag.equals("Y")) {
                    acctNumNegotiatedFlag = "Y";
                } else
                {
                    acctNumNegotiatedFlag = "N";
                }
            } catch (Exception e) {
                acctNumNegotiatedFlag = "N";
            }

            try {
                if (enableFlag.equals("Y")) {
                    enableFlag = "Y";
                } else
                {
                    enableFlag = "N";
                }
            } catch (Exception e) {
                enableFlag = "N";
            }

         //   session.setAttribute("aascCarrierConfiguration", aascCarrierConfigurationBean);
            aascCarrierConfigurationBean.setNonDiscountedCost(nonDiscountedCost); // setting the nonDiscountedCost to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setAcctNumNegotiatedFlag(acctNumNegotiatedFlag);
            aascCarrierConfigurationBean.setEnableFlag(enableFlag);
            aascCarrierConfigurationBean.setCarrierMode(carrierModeStr);
            aascCarrierConfigurationBean.setCarrierCodeValue(carrierCodeValue);
            try {
                carrierCodeTemp = carrierCodeValue;
            } catch (Exception e) {
               logger.severe("Got exception for Carrier Code "+e.getMessage());
            }
         //   aascCarrierConfigurationBean.setCarrierCode(carrierCodeTemp);
            

            /* if(carrierCodeTemp==100 || carrierCodeTemp==110 || carrierCodeTemp==111) {
                                    String carrierNameTemp1 =  request.getParameter("carrierN");
                                    aascCarrierConfigurationBean.setCarrierName(carrierNameTemp1);
                                }*/
            try {
                if (emailNotificationFlag.equals("Y")) {
                    emailNotificationFlag = "Y";
                } else
                {
                    emailNotificationFlag = "N";
                }
            } catch (Exception e) {
                emailNotificationFlag = "N";
            }

            aascCarrierConfigurationBean.setEmailNotificationFlag(emailNotificationFlag);
            aascCarrierConfigurationBean.setSenderEmailAddress(senderEmailAddress);
            aascCarrierConfigurationBean.setFormatType(formatType);

            try {
                if (referenceFlag1.equals("Y")) {
                    referenceFlag1 = "Y";
                } else {
                    referenceFlag1 = "N";
                }
            } catch (Exception e) {
                referenceFlag1 = "N";
            }

            aascCarrierConfigurationBean.setReferenceFlag1(referenceFlag1);

            try {
                if (referenceFlag2.equals("Y")) {
                    referenceFlag2 = "Y";
                } else {
                    referenceFlag2 = "N";
                }
            } catch (Exception e) {
                referenceFlag2 = "N";
            }

            aascCarrierConfigurationBean.setReferenceFlag2(referenceFlag2);
            
            try {
                if (shipNotificationFlag.equals("Y")) {
                    shipNotificationFlag = "Y";
                } else {
                    shipNotificationFlag = "N";
                }
            } catch (Exception e) {
                shipNotificationFlag = "N";
            }

            aascCarrierConfigurationBean.setShipNotificationFlag(shipNotificationFlag);

            try {
                if (deliveryNotificationFlag.equals("Y")) {
                    deliveryNotificationFlag = "Y";
                } else {
                    deliveryNotificationFlag = "N";
                }
            } catch (Exception e) {
                deliveryNotificationFlag = "N";
            }

            aascCarrierConfigurationBean.setDeliveryNotificationFlag(deliveryNotificationFlag);
            
            try {
                if (exceptionNotificationFlag.equals("Y")) {
                    exceptionNotificationFlag = "Y";
                } else {
                    exceptionNotificationFlag = "N";
                }
            } catch (Exception e) {
                exceptionNotificationFlag = "N";
            }

            aascCarrierConfigurationBean.setExceptionNotificationFlag(exceptionNotificationFlag);

            //SC_EMail_SJ
            try {
                if (salesOrderNumber.equals("Y")) {
                    salesOrderNumber = "Y";
                } else {
                    salesOrderNumber = "N";
                }
            } catch (Exception e) {
                salesOrderNumber = "N";
            }
        //    logger.info("salesOrderNumber----" + salesOrderNumber);
            aascCarrierConfigurationBean.setSalesOrderNumber(salesOrderNumber);

            try {
                if (customerName.equals("Y")) {
                    customerName = "Y";
                } else {
                    customerName = "N";
                }
            } catch (Exception e) {
                customerName = "N";
            }

            aascCarrierConfigurationBean.setCustomerName(customerName);

            try {
                if (deliveryItemNumbers.equals("Y")) {
                    deliveryItemNumbers = "Y";
                } else {
                    deliveryItemNumbers = "N";
                }
            } catch (Exception e) {
                deliveryItemNumbers = "N";
            }

            aascCarrierConfigurationBean.setDeliveryItemNumbers(deliveryItemNumbers);
   //         aascCarrierConfigurationBean.setCarrierId(carrierId); // setting the carrierId to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setProtocol(protocol); // setting the protocol to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setPort(port); // setting the port to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setServerIpAddress(serverIpAddress); // setting the serverIpAddress to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setUserName(userName); // setting the userName to the setter method of AascProfileOptions class
    //            logger.info("In carrierconfiguration action class username is" +
    //                               userName);
            aascCarrierConfigurationBean.setPassword(password); // setting the password to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setPrefix(prefix); // setting the prefix to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setDomain(domain);
            aascCarrierConfigurationBean.setDhlRegionCode(dhlRegionCode);
            aascCarrierConfigurationBean.setAccountNo(accountNo); // setting the accountNo to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setPdPort(pdPort); // setting the pdPort to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setModelSymbol(modelSymbol); // setting the modelSymbol to the setter method of AascProfileOptions class
            aascCarrierConfigurationBean.setStockSymbol(stockSymbol); // setting the stockSymbol to the setter method of AascProfileOptions class    
            aascCarrierConfigurationBean.setAccessLicenseNumber(accessLicenseNumber);
            aascCarrierConfigurationBean.setMeterNumber(meterNumber);
            aascCarrierConfigurationBean.setLabelStock(labelStock);
            aascCarrierConfigurationBean.setDocTab(docTab);
            aascCarrierConfigurationBean.setSupportHazmatShipping(supportHazmatShipping);
            
            aascCarrierConfigurationBean.setIntegrationId(integrationId);
//            aascCarrierConfigurationBean.setIntlLabelFormat(intlLabel);
            aascCarrierConfigurationBean.setIntlPrintLayout(intlPrintLay);
            aascCarrierConfigurationBean.setPaperSize(stampsPaperSize);
            aascCarrierConfigurationBean.setNonDelivery(nonDeliveryOption);
            


            

//            logger.info("Values are set successfully to aascCarrierConfigurationBean bean");
//            logger.info("Calling aascCarrierConfigurationDAO to save Carrier Configuration related data to the database");

            int savePOStatus=0;
            try{
//                logger.info("saveCarrierConfigurationInfo callingggg............");
                savePOStatus=aascCarrierConfigurationDAO.saveCarrierConfigurationInfo(aascCarrierConfigurationBean, userID); // this method saves the carrier Configuration related data to the database
//                logger.info("saveCarrierConfigurationInfo End ............savePOStatus ::"+savePOStatus);
            }catch(Exception ep){ 
                logger.info("Exception in getCarrierConfigurationDetails1"+ep.getMessage());
//                ep.printStackTrace();            
            }
            if (savePOStatus == 182) {
                session.setAttribute("shipMethodChk", "Y");
                key = "aasc182";
            } else if (savePOStatus == -1013) {
                key = "aasc20001";
            }
            else {
                key = "aasc183";
            }

            request.setAttribute("key", key);

//            logger.info("Calling aascCarrierConfigurationDAO to retrieve Carrier Configuration related data from database and setting to bean");

            aascCarrierConfigurationBean= aascCarrierConfigurationDAO.getCarrierConfigurationDetails(aascCarrierConfigurationBean ); // this method retrives the data from the database and sets that values to the setter methods of the aascCarrierConfigurationBean class
            session.setAttribute("aascCarrierConfiguration", aascCarrierConfigurationBean);
       
//            logger.info("Reterived aascCarrierConfigurationBean after saving");
//            logger.info("getRecepientEmail1 :::::::::::"+aascCarrierConfigurationBean.getRecepientEmailAddress1());
//            logger.info("getRecepientEmail2 ::::::::::"+aascCarrierConfigurationBean.getRecepientEmailAddress2());
//            logger.info("getRecepientEmail3 :::::::::::"+aascCarrierConfigurationBean.getRecepientEmailAddress3());
//            logger.info("getRecepientEmail4:::::::::::"+aascCarrierConfigurationBean.getRecepientEmailAddress4());
//            logger.info("getRecepientEmail5 ,,,,,,,,,,"+aascCarrierConfigurationBean.getRecepientEmailAddress5());
//            logger.info("Domain :::::::"+aascCarrierConfigurationBean.getDomain());
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
    }

    /** This method is called from action class when user clicked on Go button in Carrier configuration page. 
     * This method get Account Number details based on location id, client id and carrier code
     * @param aascCarrierConfigurationBean
     * @param session
     * @return success or error message to action class
     */
    public String getAccountNumbers(AascCarrierConfigurationBean  aascCarrierConfigurationBean, HttpSession session) {
        try {
            logger.info("In getAccountNumbers");
            AascAccountNumbersDAO aascAccountNumbersDAO = new AascOracleAccountNumbersInfoDAO();
            AascAccountNumbersBean aascAccountNumbersBean ;
            
            Integer role = (Integer)session.getAttribute("role_id");
//            logger.info("role ::::::::::"+role);
            int roleId = role.intValue();
//            logger.info("roleId :::::::::::::::::"+roleId);
            String queryTimeOut = (String)session.getAttribute("queryTimeOut");
//            logger.info("queryTimeOut::::::::::::::::::"+queryTimeOut);
            //if(invOrgId != 0)
//            logger.info("getCarrierCodeValue : " + aascCarrierConfigurationBean.getCarrierCodeValue());
//            logger.info("UserId :::::::::::::::::::"+aascCarrierConfigurationBean.getUserId());
            
            session.setAttribute("UserId",aascCarrierConfigurationBean.getUserId());
            carrierCodeValue = aascCarrierConfigurationBean.getCarrierCodeValue();
            int carrierCodeInt = carrierCodeValue;
            int locId = aascCarrierConfigurationBean.getLocationId();
            clientId= aascCarrierConfigurationBean.getClientId();
            
//            logger.info("locId ::::::::::"+locId);

//                        if (roleId == 4 ) {
//                         
////                          logger.info("carrierCodeInt : " + carrierCodeInt);
////                            aascAccountNumbersBean = 
////                                    aascAccountNumbersDAO.getAccountNumbersInfo(clientId, 
////                                                                                orgID.intValue(), 
////                                                                                carrierCodeInt, 
////                                                                                Integer.parseInt(queryTimeOut),
////                                                                                clientId);
//                         //   aascAccountNumbersInfo.setInvOrgId(invOrgIdSes.intValue());
//                           // aascAccountNumbersInfo.setOrgId(orgID.intValue());
//
//            } else {
//               
//            logger.info("carrierCodeInt : " + carrierCodeInt);
            
            aascAccountNumbersBean = aascAccountNumbersDAO.getAccountNumbersInfo(carrierCodeInt, clientId,locId,Integer.parseInt(queryTimeOut));
//            
//            logger.info("Error Status ::::"+aascAccountNumbersBean.getErrorStatus());
//            logger.info("Error Message  ::::"+aascAccountNumbersBean.getErrorMessage());
           // }
            aascAccountNumbersBean.setLocationId(locId);
            
            session.setAttribute("locId",locId);
//            logger.info("locId :::::::::::::::::::::"+ locId);
            session.setAttribute("aascAccountNumbersInfo",aascAccountNumbersBean);
//            logger.info("aascAccountNumbersBean : " + aascAccountNumbersBean);
            session.setAttribute("aascCarrierConfiguration", aascCarrierConfigurationBean);
//            logger.info("aascCarrierConfigurationBean : " + aascCarrierConfigurationBean);
            return "success";
        } catch(Exception e) {
            logger.severe("Got Exception getAccountNumbers method "+e.getMessage());
//           e.printStackTrace();
            return "error";
        }
    }
}
