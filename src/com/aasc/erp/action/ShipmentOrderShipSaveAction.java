/*@(#)ShipmentOrderShipSaveAction.java       6/01/2006
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
/** 
 * @version 
 * @author Eshwari
 * @modified Suman Gunda
 **/
package com.aasc.erp.action;
/*========================================================================================
 Date         Resource      Change history
 ------------------------------------------------------------------------------------------
 28/05/2014  Suman Gunda    Added code for void shipped packages if delivery is not shipped in Adhoc Order.
 14/11/2014  Eshwari M      Added this file from cloud 1.2 version
 18/12/2014  Eshwari M      Modified code to pass Shipfromlocation instated od Location Id for Ship_from_Location col in the database
 18/12/2014  Y Pradeep      Modified code related to nullStrToSpc method
 18/12/2014  Eshwari M      Modified code to set location id in headers table.
 29/12/2014  Eshwari M      Modified code related to companyName and locationId.
 02/01/2015  Y Pradeep      Added code to save ship_to_location_name into shipment headers table.
 19/01/2015  Suman G        Modified for code review fixes.
 19/01/2005  Suman G        Removed voidShipment() and printLabel() methods.
 28/01/2015  Y Pradeep      Modified code as required for International Shipping.
 02/02/2015  Eshwari M      Added upsServiceLevelCode related code for Carrier SLA Report
 05/02/2015  Sunanda K      Added void functionality code
 11/02/2015  Y Pradeep      Modified code to check if condition with carrier name starts with UPS to carrier code is 100.
 12/02/2015  Eshwari M      Removed the DAO calls and moved to delegate class
 12/02/2015  Eshwari M      Modified code to pull correct values after voiding or for new order by getting locationId from session
 16/02/2015  Y Pradeep      Modified code to generate order number on click og Ship button in Shipping page.
 05/03/2015    Sanjay & Khaja Added code for new UI changes.
 10/03/2015  Y Pradeep      Added code to save address line 3, email id for ship to and ship from and residentialFlag.
 05/03/2015  Suman G        Added code for Get Rates functionality.
 12/03/2015  Y Pradeep      Modified below if and else conditions to save shipFlag into database in error case and success case. Bug #2669.
 12/03/2015  Y Pradeep      Modified code to set shipError and errorDesc messages in request instead of session.
 12/03/2015  Suman G        Set key value as 'aasc122' for fix #2615
 13/03/2015  Y Pradeep      Renamed parameter OrderNum to OrderNumber because of issue at ordernumber generation.
 15/03/2015  Y Pradeep      Modified code to hanle class cast exception.
 01/04/2015  Y Pradeep      Modified code to set intlSaveFlag as Y for International Order. Bug #2762.
 06/04/2015  Y Pradeep      Commented code to display values in shipping page after order is void.
 07/04/2015  Y Pradeep      Modified if condition to generate order number if ordernumber is empty. Bug #2808 and #2696.
 22/04/2015  Suman G        Added code to fix #2730.
 05/05/2015  Y Pradeep      Modified code to get carrierName from carrierNameHide hidden field. Bug #2693 and #2901.
 14/05/2015  Y Pradeep      Modified code to get header data from database and set in aascShipmentOrderInfo bean when Void action is performed.
 27/05/2015  Y Pradeep      Modified code to do package level void is done. Bug #2915.
 27/05/2015  Suman G        Added code to fix #2936
 27/05/2015  Y Pradeep      Modified code to display and allow Order Numbers with special characters(Encode and Decode).
 02/06/2015  Suman G        Added code to fix #2954
 02/06/2015  Y Pradeep      Modified code accordingly to for package level voiding for UPS.
 03/06/2015  Suman G        Added code to fix #2955
 12/06/2015  K Sunanda      Modified cod efor bug fix #2968
 17/06/2015  Suman G             Added code to fix #2986
 16/06/2015  Y Pradeep      Modified code to set error and key variables in request instead of session when void button is clicked. Bug #2968.
 13/07/2015  Y Pradeep      Removed unused code and added code to get labelFormat from header tabel.
 17/07/2015  Y Pradeep      Modified code to handle nullpointer and number format exceptions when action is performed in shipping page.
 17/07/2015  Y Pradeep      Added code to save header and package details to db when Get Rates button is clicked. Bug #3180.
 20/07/2015  Suman G        Added code for implement Email Notification.
 20/08/2015  Rakesh K       Removed code added for drop down issue 2895.
 30/10/2015  Shiva          Added code related to DHL integration 
 04/11/2015  Suman G        Added code to fix #3058, 3888.
 05/11/2015  Suman G        Added code for #3920
 05/11/2015  Shiva G        Added code to void the first packge when the second package got errored in multi package shipping for FedEx
 10/11/2015  Mahesh V       Added code for FedEx Recepient Development
 24/11/2015  Shiva G        Modified DHL return status from 152 to 122 to fix the issue #3979
 02/12/2015  Suman G        Added code to edit the ship to fields based on given profile options after error case.
 18/12/2015  Y Pradeep      Modified code to save TimeStamp for shipmentdate field. Bug #4095.
 24/03/2016  Suman G        Commented setting rates to get rates method to remove showing value in text field after ship.
 ========================================================================================*/
 
import com.aasc.erp.carrier.AascStampsShipment;
import com.aasc.erp.carrier.AascShipExecShipment;
import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.bean.AascCarrierProfileOptionsInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascTemplateInfo;
import com.aasc.erp.carrier.AascCarrierShipment;
import com.aasc.erp.carrier.AascDHLShipment;
import com.aasc.erp.carrier.AascFedexShipment;
import com.aasc.erp.carrier.AascFedexVoid;
import com.aasc.erp.carrier.AascUpsShipment;
import com.aasc.erp.delegate.AascFreightShopDelegate;
import com.aasc.erp.delegate.AascShipmentOrderDelegate;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascShipmentOrderInfoDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;

import java.net.URLDecoder;

import java.util.HashMap;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
   ShipmentOrderShipSaveAction class extends Action class.
   This class takes the values from the jsp and calls the model class methods which saves or print or void 
   the order depending upon the condition.
 */
 //ModelDriven<AascShipmentOrderInfo>, Preparable,
public class ShipmentOrderShipSaveAction extends ActionSupport implements 
                                                                          ServletRequestAware, 
                                                                          ServletResponseAware {
    // declaring the instance variables for storing the header details
    private String manualTrackingFlag = ""; // holds the value of the manualTrackingFlag
    private String wayBill = ""; // holds the value of wayBill number that is  manualTrackingNumber
    private String shipmentDate = ""; // holds the value of the date(sysdate) on which the delivery needs to be shipped
    private double shipmentCost; // holds the typecast value of the shipmentCost
    private String shipmentCost1 =""; // holds the value of shipmentCost get from the jsp
    private String shippingMethod; // holds the value of the shipMethod                                              
    private String orderNumber = ""; // holds the typecasted value of the order number. sequence number to identify shipment
    private int clientID = -1;
    private String orderNumber2 = ""; // holds the value of the orderNumber get from the jsp
    private String orderNumber3; // holds the value of the orderNumber get from the bean class
    private String printer; // holds the name of the  printerName used for printing
    private String reference1; // holds the value of the customer purchase order number
    private String reference2; // holds the value of the  order number
    private String additionalInfo;
    private String contactName; // holds the value of contact name                                                                    
    private String phoneNumber; // holds the value of phone number                                                              
    private String tpCompanyName; // holds the value of third party company name
    private String tpAddress; // holds the value of the third party Address
    private String tpCity; // holds the value of the third party City
    private String tpState; // holds the value of the third party state
    private String tpPostalCode; // holds the value of the third party postal code
    private String tpCountrySymbol; // holds the value of the third party countyr symbol
    private int carrierId; // holds the value of the carrierId
    private String shipperName; // holds the value of the shipper name
    private String customerName; // holds the value of the customer name
    private String shipToLocationName; // holds the value of the ship to location name
    private String carrierPayMethod; // holds the value of the customerPayMethod
    private String carrierACNumber; // holds the value of the carrier account number
     private String maskCarrierACNumber; // holds the masked value of the carrier account number
    private String address; // holds the value of ship to address
    private String shipToAddrLine2 = ""; // holds the value of ship to address line2
    private String shipToAddrLine3 = ""; // holds the value of ship to address line3
    private String shipToEmailId ="";
    private String shipToCity = "";
    private String shipToState = "";
    private String shipToCountry = "";
    private String shipToPhnNumber1 = "";
    private String shipToPhnNumber2 = "";
    private String shipToPostalCode = "";
    private String shiptoContactName = "";
    private String shipToAddrLine1= "";

    

    private String city; // holds the value of ship to city
    private String state; // holds the value of ship to state
    private String postalCode; // holds the value of ship to postal code
    private String countrySymbol; // holds the value of ship to country symbol
    private String shipFromLocation = ""; // holds the value of the ship from organisation
    private String shipFromContactName = ""; // holds the value of the Ship From Contact Name
    private String shipFromAddressLine1 =""; // holds the value of the ship from address
    private String shipFromAddressLine2 =""; // holds the value of the ship from address
    private String shipFromAddressLine3 =""; // holds the value of the ship from address
    private String shipFromEmailId ="";
    private String shipFromCity = ""; // holds the value of the ship from city
    private String shipFromState = ""; // holds the value of the ship from state
    private String shipFromCountry = ""; // holds the value of the ship from country
    private String dropOfType = ""; // holds the value of the dropOfType
    private String packaging = ""; // holds the value of the way the item is pack
    private String shipFromPostalCode = ""; // holds the value of the ship from postalCode
    private String shipFromPhoneNumber1 = ""; // holds the value of the ship from phone number
    private String shipFromPhoneNumber2 = ""; // holds the value of the ship from phone number
    private String print = "N"; // holds the value "Y" indicates labels printed or "N" indicates the labels not printed
    private String carrierName = ""; // holds the vlaue of the carrierName
    private String department = ""; // added by gayaz@007
    private double freightCost; // added by gayaz@007
    // declaring the instance variable for storing the package details
    private String packsCount1 = "";
    private String userIdstr = ""; // holds the number of packages value got from the jsp
    int packsCount; // holds the value of the number of packages
    private String dimensionUnit =  ""; // holds the value of the dimensionUnit of the package
    private String submitButton; // holds the value of the submit button get from the jsp

    private String codFlag = "";    // holds the value of codFlag
    private String codAmt = "";  // holds the value of codAmt
    
    private String Packpackaging = "";   // holds the value of Packpackaging
    private String codFundsCode = "";    // holds the value of codFundsCode
    private String codCurrCode = "";     // holds the value of codCurrCode
    private String declaredCurrCode = ""; // holds the value of declaredCurrCode
    private String upsServiceLevelCode = "";
    private String carrierServiceLevel = "";
    private String delConfirm = ""; // holds the value of delConfirm
     private String additionalHandlingFlag = ""; 
    private String largePackageFlag = ""; 
    private int userId;      // holds the value of userId
    private String shipValue; // holds the value of shipValue
    private int carrierSuccessStatus;
    private String residentialFlag = "N";
    
    private String recCompanyName = "";
    private String recPostalCode = "";
    
    static Logger logger = 
        AascLogger.getLogger("ShipmentOrderShipSaveAction"); // this method returns the object of the logger;
    AascShipMethodInfo aascShipMethodInfo; // holds the object of the AascShipMethodInfo class
    HttpSession session; // holds the object of the HttpSession
    AascShipmentOrderInfo aascShipmentOrderInfo; // holds the object of the AascShipmentOrderInfo class
    AascShipmentHeaderInfo aascShipmentHeaderInfo; // holds the object of the AascShipmentHeaderInfo class
    AascProfileOptionsBean aascProfileOptionsInfo; // holds the object of the AascProfileOptionsInfo class
    AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO; // holds the object of the AascShipmentOrderInfoDAO class
    AascShipmentPackageInfo aascShipmentPackageInfo; // holds the object of the AascShipmentPackageInfo class
    AascCarrierShipment aascCarrierShipment; // holds the object of the AascCarrierShipment class
    AascUpsShipment aascUpsShipment; // holds the object of the aascUpsShipment
    AascFedexShipment aascFedexShipment; // holds the object of the AascFedexShipment class
    AascShipExecShipment aascShipExecShipment; // holds the object of the aascUpsShipment
     AascStampsShipment aascStampsShipment; // holds the object of the aascStampsShipment

     
    String key = ""; // holds the code value the message for this code is written in the AplicationResources.properties class
    String error = ""; // holds the errorStaus value get from the procedures called by the  model class method
    int returnStatus; // holds the value of the opStatus value get from the procedure called by the model class methods
    int returnStatus1; // holds the value of the opStatus value get from the procedure called by the model class methods

    AascIntlInfo aascIntlInfo = null;
    String userIdstr3 = "";
    int userIdint = 0;
    String intlFlag = "";
    String intlSaveFlag  = "";
    int oldPackcount = 0;
    int oldPackcountSaved = 0;
    String voidStatusFlagVal = "";  //  holds the void status flag
    String packageTrackingNumber = "";

    String fedExCarrierMode;    //  holds the fedEx carrier mode
    String fedExKey;            //  holds the fedEx key
    String fedExPassword;       //  holds the fedEx password
    //String intlFlagWS = "";
    String shipToCountryFullName=""; //Shiva added for DHL 
    String shipFromCountryFullName=""; //Shiva added for DHL 
         //Mahesh
     String StampsReturnStatus = "";
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public void setServletRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }
    
    /**
     * This is the main action called from the Struts framework.
     */
    public String execute() {
        logger.info("Entered execute()");
        try {
            LinkedList accountNumbersList = null;
            ListIterator accountNumbersIterator = null;

            AascCarrierProfileOptionsInfo aascCarrierProfileOptionsInfo = new AascCarrierProfileOptionsInfo();
            session = request.getSession(); // creating the object of the  HttpSession
             if(session.isNew() || 
                     !(session.getId().equals(session.getAttribute("SessionId")))){
                 logger.info("in Session "+session.isNew());
                 return "error";
             }
            aascShipmentOrderInfo = new AascShipmentOrderInfo(); // creating the object of the AascShipmentOrderInfo class
            aascShipmentHeaderInfo = new AascShipmentHeaderInfo(); // creating the object of the AascShipmentHeaderInfo class
            aascShipmentOrderInfo.setShipmentHeaderInfo(aascShipmentHeaderInfo); // setting the object of the AascShipmentHeaderInfo class to the aascShipmentOrderInfo.setShipmentHeaderInfo method
            LinkedList packageLinkedList = new LinkedList();  //(LinkedList)aascShipmentOrderInfo.getShipmentPackageInfo();  //new LinkedList();

            submitButton = request.getParameter("submitButton"); // this method returns the value of the submit button
            
            clientID = (Integer)session.getAttribute("client_id");
            
            int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            if(roleIdSession != 2 && roleIdSession != 4){
             return "error";
            }
            if (submitButton.equalsIgnoreCase("New Order")) {
                logger.info(" Inside the New order ");
                
                aascShipmentOrderInfo =  null; // making the aascShipmentOrderInfo object value null
                aascShipmentOrderInfo = new AascShipmentOrderInfo();
                session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);
                session.removeAttribute("error");
                session.removeAttribute("key");
                return "success";
            }
            AascShipmentOrderDelegate aascShipmentOrderDelegate = new AascShipmentOrderDelegate();
            
            shipFromLocation = request.getParameter("shipFromLoc"); // getting the shipFromLoc value
            //Mahesh Added below code to get the field values from request for international Stamps Shipping
            shipFromPostalCode = request.getParameter("shipFromPostalCode");
            shipFromAddressLine1 = request.getParameter("shipFromAddressLine1");
            shipFromAddressLine2 = request.getParameter("shipFromAddressLine2");
            shipFromAddressLine3 = request.getParameter("shipFromAddressLine3");
            shipFromCity = request.getParameter("shipFromCity");
            shipFromState = request.getParameter("shipFromState");
            shipFromCountry = request.getParameter("shipFromCountry");
            shipFromPostalCode = request.getParameter("shipFromPostalCode");
            shipFromContactName = request.getParameter("shipFromContactName");
            shipFromPhoneNumber1 = request.getParameter("shipFromPhoneNumber1");

            String ratesFromFreightShop = request.getParameter("ratesFromFreightShop"); // Added for get rates
                        
            int locationId = 0 ;
            try{
               if(shipFromLocation != null)
               {
                   String str = shipFromLocation.substring(shipFromLocation.lastIndexOf('*')+1);
                   locationId = Integer.parseInt(str);
               }    
            }catch(Exception e)   
            {
                locationId = 0 ; 
               logger.severe("error msg : "+e.getMessage()) ;
            }
            logger.info("locationId : "+locationId);
            aascShipmentHeaderInfo.setShipFromLocation(shipFromLocation); // setting the shipFromLocation get from the jsp to the aascShipmentHeaderInfo.setShipFromOrg method
            
            aascShipmentHeaderInfo.setShipFromAddressLine1(shipFromAddressLine1);
            aascShipmentHeaderInfo.setShipFromAddressLine2(shipFromAddressLine2);
            aascShipmentHeaderInfo.setShipFromAddressLine3(shipFromAddressLine3);
            aascShipmentHeaderInfo.setShipFromAddressCity(shipFromCity);
            aascShipmentHeaderInfo.setShipFromState(shipFromState);
            aascShipmentHeaderInfo.setShipFromCountry(shipFromCountry);
            aascShipmentHeaderInfo.setShipFromCompanyName(shipFromContactName);
            aascShipmentHeaderInfo.setShipFromPhoneNumber1(shipFromPhoneNumber1);
            aascShipmentHeaderInfo.setShipFromAddressPostalCode(shipFromPostalCode);
            request.setAttribute("shipToCityEditFlag",request.getParameter("shipToCityEditFlag"));
            request.setAttribute("shipToStateEditFlag",request.getParameter("shipToStateEditFlag"));
            request.setAttribute("shipToCountryEditFlag",request.getParameter("shipToCountryEditFlag"));
            request.setAttribute("shipToZipEditFlag",request.getParameter("shipToZipEditFlag"));
            aascShipmentHeaderInfo.setLocationId(locationId);
            session.setAttribute("locationId" , locationId);
            AascShipmentOrderInfo aascShipmentOrderInfoSaved = aascShipmentOrderDelegate.getOrderInfo(orderNumber, new Integer(clientID), session) ;
            LinkedList savedPackageList = aascShipmentOrderInfoSaved.getShipmentPackageInfo();
            packageLinkedList = aascShipmentOrderInfo.getShipmentPackageInfo();
            oldPackcount = savedPackageList.size();
            String reqVoidFlagHidden = "";
            // creating the object of the LinkedList class
            aascShipmentOrderInfo.setShipmentPackageInfo(packageLinkedList); // setting the LinkedList object to the aascShipmentOrderInfo.setShipmentPackageInfo method
            aascShipMethodInfo = (AascShipMethodInfo)session.getAttribute("ShipMethodInfo"); //getting the object of the aascShipMethodInfo class from the session
            wayBill = request.getParameter("wayBill"); // getting the wayBill number of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setWayBill(wayBill); // setting the wayBillNumber get from the jsp to the aascShipmentHeaderInfo.setWayBill method
            shipmentCost1 = request.getParameter("shipmentCost"); // getting the shipmentCost  of the jsp through the struts-config.xml
        
         
            if ("".equals(shipmentCost1)) {
                shipmentCost = 0.0;
            } else {
                shipmentCost = Double.parseDouble(shipmentCost1);
            }
            aascShipmentHeaderInfo.setShipmentCost(shipmentCost); // setting the shipmentCost get from the jsp to the aascShipmentHeaderInfo.setShipmentCost method
            
            customerName = request.getParameter("customerName"); // getting the customerName  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setCustomerName(customerName); // setting the customerName get from the jsp to the aascShipmentHeaderInfo.setCustomerName method
            shipToLocationName = request.getParameter("customerLocation"); // getting the customerLocation  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipToLocationName(shipToLocationName); // setting the customerLocation get from the jsp to the aascShipmentHeaderInfo.setCustomerLocation method
            address = request.getParameter("shipToAddress"); // getting the shipToAddress  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setAddress(address); // setting the shipToAddress get from the jsp to the aascShipmentHeaderInfo.setAddress method
            city = request.getParameter("city"); // getting the city  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setCity(city); // setting the city get from the jsp to the aascShipmentHeaderInfo.setCity method
            //shipmentDate = request.getParameter("shipmentDate");
            java.sql.Timestamp shipTimeStamp = null;
            if (request.getParameter("shipmentDate") != null && !"".equalsIgnoreCase(request.getParameter("shipmentDate"))) {
                try {
                    shipTimeStamp = java.sql.Timestamp.valueOf(request.getParameter("shipmentDate"));
                    logger.info("shipTimeStamp in action==" +shipTimeStamp);
                    java.sql.Date shipmentDate =new java.sql.Date(shipTimeStamp.getTime());
                    logger.info("shipmentDate in action==" +shipmentDate);
                    aascShipmentHeaderInfo.setShipmentDate(shipmentDate);
                    aascShipmentHeaderInfo.setShipTimeStamp(shipTimeStamp);
                }catch (Exception e) {
                     logger.info(e.getMessage());
                }
            }
            /*if (shipmentDate != null && !"".equalsIgnoreCase(shipmentDate)) {
                aascShipmentHeaderInfo.setShipmentDate(java.sql.Date.valueOf(shipmentDate)); // setting the shipmentDate get from the jsp to the aascShipmentHeaderInfo.setShipmentDate method
            } else {
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date now = new java.sql.Date(utilDate.getTime());
                aascShipmentHeaderInfo.setShipmentDate(now);
            }*/

            String chkSatShipment = request.getParameter("chkSatShipmentHidden");
            aascShipmentHeaderInfo.setSaturdayShipFlag(chkSatShipment);

            state = request.getParameter("state"); // getting the state  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setState(state); // setting the state name get from the jsp to the aascShipmentHeaderInfo.setState method
            carrierPayMethod = request.getParameter("carrierPayMethodHide"); // getting the carrierPayMethod  of the jsp through the struts-config.xml

            aascShipmentHeaderInfo.setCarrierPaymentMethod(carrierPayMethod); // setting the carrierPayMethod get from the jsp to the aascShipmentHeaderInfo.setCarrierPayMethod method
            
            countrySymbol = request.getParameter("countrySymbol"); // getting the country name  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setCountrySymbol(countrySymbol); // setting the country name get from the jsp to the aascShipmentHeaderInfo.setCountrySymbol method
           
           //  modified carrierAccountNumber to carrierAccountNumberHid  
             String maskCheck = request.getParameter("maskAccountCheck");  
             if  ("Y".equalsIgnoreCase(maskCheck) && (("PREPAID".equalsIgnoreCase(carrierPayMethod)) || ("RECIPIENT".equalsIgnoreCase(carrierPayMethod)) || ("THIRD PARTY BILLING".equalsIgnoreCase(carrierPayMethod)))){
                 carrierACNumber = request.getParameter("carrierAccountNumberHid");
   
             }
             else{
                 carrierACNumber = request.getParameter("carrierAccountNumber");
             }
             maskCarrierACNumber = request.getParameter("carrierAccountNumber");
//            maskCarrierACNumber = request.getParameter("carrierAccountNumber");
//           carrierACNumber = request.getParameter("carrierAccountNumberHid"); // getting the carrierACNumber  of the jsp through the struts-config.xml
            String carrierPayMethodText = "";
            if ("N".equalsIgnoreCase(aascShipmentHeaderInfo.getShipFlag())) {

                carrierPayMethod = request.getParameter("carrierPayMethodHide");
                carrierPayMethodText = aascShipMethodInfo.getCarrierPayCode(carrierPayMethod);
                

            } else {
                carrierPayMethodText = aascShipMethodInfo.getCarrierPayCode(aascShipmentHeaderInfo.getCarrierPaymentMethod());
                
            }
            aascShipmentHeaderInfo.setMaskCarrierAccountNumber(maskCarrierACNumber); // setting the maskCarrierACNumber get from the jsp to the aascShipmentHeaderInfo.setCarrierAccountNumber method
            aascShipmentHeaderInfo.setCarrierAccountNumber(carrierACNumber); // setting the carrierACNumber get from the jsp to the aascShipmentHeaderInfo.setCarrierAccountNumber method
            postalCode = request.getParameter("postalCode"); // getting the zip number  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setPostalCode(postalCode); // setting the zip number get from the jsp to the aascShipmentHeaderInfo.setPostalCode method
            contactName =request.getParameter("contactName"); // getting the contactName  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setContactName(contactName); // setting the contactName get from the jsp to the aascShipmentHeaderInfo.setContactName method
            shipperName = request.getParameter("shipperName"); // getting the shipperName  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipExecShipperName(shipperName); // setting the shipperName get from the jsp to the aascShipmentHeaderInfo.setShipperName method
            //System.out.println("449::"+aascShipmentHeaderInfo.getShipExecShipperName());
            phoneNumber =request.getParameter("phoneNumber"); // getting the phoneNumber  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setPhoneNumber(phoneNumber); // setting the phoneNumber get from the jsp to the aascShipmentHeaderInfo.setPhoneNumber method
            reference1 =request.getParameter("reference1"); // getting the reference1  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setReference1(reference1); // setting the reference1 get from the jsp to the aascShipmentHeaderInfo.setReference1 method
            reference2 =request.getParameter("reference2"); // getting the reference2  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setReference2(reference2); // setting the reference2 get from the jsp to the aascShipmentHeaderInfo.setReference2 method
             additionalInfo = request.getParameter("shipAddInfo");
            aascShipmentHeaderInfo.setShipAddInfo(additionalInfo);
            String testshipMethodAlternate = request.getParameter("shipMethodHide"); // getting the shipMethod  of the jsp through the struts-config.xml
            shippingMethod = aascShipMethodInfo.getShipMethodFromAlt(testshipMethodAlternate);
            aascShipmentHeaderInfo.setShippingMethod(testshipMethodAlternate); // setting the shipMethod get from the jsp to the aascShipmentHeaderInfo.setShippingMethod method
            aascShipmentHeaderInfo.setShipMethodMeaning(shippingMethod); // setting the shippingMethod get from the jsp to the aascShipmentHeaderInfo.setShipMethodMeaning method
            carrierName =request.getParameter("carrierNameHide");//aascShipMethodInfo.getCarrierName(shippingMethod); // this method takes the shipMethdo as an argument and return the carrierName
            aascShipmentHeaderInfo.setCarrierName(carrierName);
            carrierId =aascShipMethodInfo.getCarrierId(shippingMethod); // this method takes the carrierName as a parameter and returns the carrierId
            aascShipMethodInfo.setCarrierId(carrierId); // setting the carrierId to the aascShipMethodInfo.setCarrierId method
            aascShipmentHeaderInfo.setCarrierId(carrierId); // setting the carrierId to the aascShipmentHeaderInfo.setCarrierId
            tpCompanyName = request.getParameter("tpCompanyName"); // getting the third party company name  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpCompanyName(tpCompanyName); // setting the third party company name get from the jsp to the aascShipmentHeaderInfo.setTpCompanyName method
            tpAddress =request.getParameter("tpAddress"); // getting the third party address  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpAddress(tpAddress); // setting the third party address get from the jsp to the aascShipmentHeaderInfo.setTpAddress method
            tpCity =request.getParameter("tpCity"); // getting the third party city name  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpCity(tpCity); // setting the third party city name get from the jsp to the aascShipmentHeaderInfo.setTpCity method
            tpState =request.getParameter("tpState"); // getting the third party state name of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpState(tpState); // setting the third party state name get from the jsp to the aascShipmentHeaderInfo.setTpState method
            tpCountrySymbol =request.getParameter("tpCountrySymbol"); // getting the third party country symbol  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpCountrySymbol(tpCountrySymbol); // setting the third party country symbol get from the jsp to the aascShipmentHeaderInfo.setTpCountrySymbol method
            tpPostalCode = request.getParameter("tpPostalCode"); // getting the third party postal code  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setTpPostalCode(tpPostalCode); // setting the third party postal code get from the jsp to the aascShipmentHeaderInfo.setTpPostalCode method
            aascShipmentHeaderInfo.setShipFromCompanyName(nullStrToSpc(request.getParameter("companyName")));
            shipFromContactName =request.getParameter("shipFromContactName"); // getting the shipFromOrgnization  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromContactName(shipFromContactName); // setting the shipFromOrgnization get from the jsp to the aascShipmentHeaderInfo.setShipFromOrg method
            aascShipmentHeaderInfo.setShipperName(shipFromContactName);
            shipFromAddressLine1 =request.getParameter("shipFromAddressLine1"); // getting the shipFromAddressLine1  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromAddressLine1(shipFromAddressLine1); // setting the shipFromAddressLine1 get from the jsp to the aascShipmentHeaderInfo.setShipFromAddressLine1 method
            shipFromAddressLine2 =request.getParameter("shipFromAddressLine2"); // getting the shipFromAddressLine2  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromAddressLine2(shipFromAddressLine2); // setting the shipFromAddressLine2 get from the jsp to the aascShipmentHeaderInfo.setShipFromAddressLine3 method
            shipFromAddressLine3 =request.getParameter("shipFromAddressLine3"); // getting the shipFromAddressLine3  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromAddressLine3(shipFromAddressLine3); // setting the shipFromAddressLine3 get from the jsp to the aascShipmentHeaderInfo.setShipFromAddressLine3 method
            shipFromEmailId =request.getParameter("shipFromEmail");
            aascShipmentHeaderInfo.setShipFromEmailId(shipFromEmailId);
            
            shipFromCity =request.getParameter("shipFromCity"); // getting the shipFromCity  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromCity(shipFromCity); // setting the shipFromCity get from the jsp to the aascShipmentHeaderInfo.setShipFromCity method
            shipFromState =request.getParameter("shipFromState"); // getting the shipFromState  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromState(shipFromState); // setting the shipFromState get from the jsp to the aascShipmentHeaderInfo.setShipFromState method
            shipFromCountry =request.getParameter("shipFromCountry"); // getting the shipFromCountry  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromCountry(shipFromCountry); // setting the shipFromCountry get from the jsp to the aascShipmentHeaderInfo.setShipFromCountry method
            //Shiva added below code to get country name for DHL
             AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
             LinkedList countryCodelist =
                aascUserControlDAO.getCountryCodeDetails();
             session.setAttribute("countryCodelist", (Object)countryCodelist);
             Map countryNameHashMap =
                new TreeMap((HashMap<?,?>)aascUserControlDAO.getCountryNameDetails());
             
             shipToCountryFullName=getKeyFromValue(countryNameHashMap,countrySymbol);
             
             aascShipmentHeaderInfo.setShipToCountryFullName(shipToCountryFullName);
             shipFromCountryFullName = getKeyFromValue(countryNameHashMap,shipFromCountry);
            aascShipmentHeaderInfo.setShipFromCountryFullName(shipFromCountryFullName);
            
            //Shiva code end
            dropOfType =request.getParameter("dropOftype"); // getting the dropOfType  of the jsp through the struts-config.xml
            //=====================Added By Narasimha Earla====================
            String acctPostalCode = request.getParameter("acctPostalCode"); // (String) shipsaveForm.get("acctPostalCode");
            String countryCodeVal = request.getParameter("countryCodeVal"); // (String) shipsaveForm.get( "countryCodeVal");
            aascShipmentHeaderInfo.setAcctPostalCode(acctPostalCode);
            residentialFlag = request.getParameter("residentialFlag");
            aascShipmentHeaderInfo.setResidentialFlag(residentialFlag);
            
            //Mahesh
            // String rcPostalCode = request.getParameter("acctPostalCode"); // (String) shipsaveForm.get("acctPostalCode");
             String rcCompanyName = request.getParameter("rcCompanyName"); // (String) shipsaveForm.get("acctPostalCode");
             String rcAcctPostalCode = request.getParameter("rcAcctPostalCode");

              aascShipmentHeaderInfo.setRecCompanyName(rcCompanyName);
              aascShipmentHeaderInfo.setRecPostalCode(rcAcctPostalCode);


            //Code added to fix getting Connection mode from Profileoption
            AascShipMethodInfo aascShipMethodInfoUPS = 
                (AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
            int carrierCode = 
                aascShipMethodInfo.getCarrierCode(carrierId);
//            int carrierIdUPS = aascShipMethodInfoUPS.getCarrierIdFromCarrierCode(100);
            int carrierIdUPS = aascShipMethodInfoUPS.getCarrierIdFromCarrierCode(carrierCode);
            String connectionModeUPS = aascShipMethodInfoUPS.getCarrierMode(carrierId);
            try {
                String shipMethodMeaningTemp1 = nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning());
                int carrierIdTemp1 = aascShipMethodInfo.getCarrierId(shipMethodMeaningTemp1);
                int carrierCodeTemp1 = aascShipMethodInfo.getCarrierCode(carrierIdTemp1);

                if (carrierCodeTemp1 == 100 && 
                    connectionModeUPS.equalsIgnoreCase("UPS Direct")) {
                    aascShipmentHeaderInfo.setTpCountrySymbol(countryCodeVal);
                }
               else if (carrierCodeTemp1 == 100 && 
                    connectionModeUPS.equalsIgnoreCase("ShipExec")) {
                    aascShipmentHeaderInfo.setTpCountrySymbol(countryCodeVal);
                }
            } catch (Exception e) {
                logger.severe("got an exception in calling Shipment order action Void loop: " + 
                              e.getMessage());
                              e.printStackTrace();
            }
            //=====================Ended by Narasimha =========================
            aascShipmentHeaderInfo.setDropOfType(dropOfType); // setting the dropOfType get from the jsp to the aascShipmentHeaderInfo.setDropOfType method
            packaging = request.getParameter("packages"); // getting the packaging  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setPackaging(packaging); // setting the packaging get from the jsp to the aascShipmentHeaderInfo.setPackaging method
            shipFromPostalCode = request.getParameter("shipFromPostalCode"); // getting the shipFromPostalCode  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromPostalCode(shipFromPostalCode); // setting the shipFromPostalCode get from the jsp to the aascShipmentHeaderInfo.setShipFromPostalCode method
            shipFromPhoneNumber1 = request.getParameter("shipFromPhoneNumber1"); // getting the shipFromPhoneNumber1  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromPhoneNumber1(shipFromPhoneNumber1); // setting the shipFromPhoneNumber1 get from the jsp to the aascShipmentHeaderInfo.setShipFromPhoneNumber1 method
            shipFromPhoneNumber2 = request.getParameter("shipFromPhoneNumber2"); // getting the shipFromPhoneNumber2  of the jsp through the struts-config.xml
            aascShipmentHeaderInfo.setShipFromPhoneNumber2(shipFromPhoneNumber2); // setting the shipFromPhoneNumber2 get from the jsp to the aascShipmentHeaderInfo.setShipFromPhoneNumber2 method

            if(!"".equalsIgnoreCase(request.getParameter("orderNumber")) && request.getParameter("orderNumber") != null && !(request.getParameter("orderNumber")).startsWith("SC")){
                orderNumber2 = URLDecoder.decode(request.getParameter("orderNumberHid"), "UTF-8"); // getting the orderNum  of the jsp through the struts-config.xml
            } else {
                orderNumber2 = request.getParameter("orderNumber"); // getting the orderNum  of the jsp through the struts-config.xml
            }
            logger.info("orderNumber2 in ShipmentOrderShipSaveAction class = "+orderNumber2 );
            department = request.getParameter("department");
            aascShipmentHeaderInfo.setDepartment(department);
            String totalWeightText = request.getParameter("packageWeight");

            double totalWeight = Double.parseDouble(totalWeightText);

            aascShipmentHeaderInfo.setPackageWeight(totalWeight);
            intlFlag = request.getParameter("intlFlag");
            aascShipmentHeaderInfo.setInternationalFlag(intlFlag);
            userId = (Integer)session.getAttribute("user_id");
            aascShipmentHeaderInfo.setCreatedBy(userId);
            shipToAddrLine2 = request.getParameter("shipToAddrLine2");
            aascShipmentHeaderInfo.setShipToAddrLine2(nullStrToSpc(shipToAddrLine2));
            shipToAddrLine3 = request.getParameter("shipToAddrLine3");
            aascShipmentHeaderInfo.setShipToAddrLine3(nullStrToSpc(shipToAddrLine3));
            shipToEmailId = request.getParameter("shipToEmail");
            aascShipmentHeaderInfo.setShipToEmailId(nullStrToSpc(shipToEmailId));
            
            shipToCity = request.getParameter("city");
            shipToPostalCode = request.getParameter("postalCode");
            shiptoContactName = request.getParameter("contactName");
            shipToPhnNumber1 = request.getParameter("phoneNumber");
            shipToCountry = request.getParameter("countrySymbol");
            shipToState = request.getParameter("state");
            shipToAddrLine1 = request.getParameter("shipToAddress");

            aascShipmentHeaderInfo.setShipToAddrLine1(shipToAddrLine1);
            aascShipmentHeaderInfo.setCity(shipToCity);
            aascShipmentHeaderInfo.setPostalCode(shipToPostalCode);
            aascShipmentHeaderInfo.setState(shipToState);
            aascShipmentHeaderInfo.setCountrySymbol(shipToCountry);
            aascShipmentHeaderInfo.setPhoneNumber(shipToPhnNumber1);
            aascShipmentHeaderInfo.setContactName(shiptoContactName);

            
            
            // Added for Email Notification
            String emailNotificationFlag = request.getParameter("emailNotificationFlag");
            aascShipmentHeaderInfo.setEmailNotificationFlag(emailNotificationFlag);
            String senderEmail = request.getParameter("senderMail");
            aascShipmentHeaderInfo.setSenderMail(senderEmail);
            String reference1Flag = request.getParameter("reference1Flag");
            aascShipmentHeaderInfo.setReference1Flag(reference1Flag);
            String reference2Flag = request.getParameter("reference2Flag");
            aascShipmentHeaderInfo.setReference2Flag(reference2Flag);
            String shipNotificationFlag = request.getParameter("shipNotificationFlag");
            aascShipmentHeaderInfo.setShipNotificationFlag(shipNotificationFlag);
            String exceptionNotification = request.getParameter("exceptionNotification");
            aascShipmentHeaderInfo.setExceptionNotification(exceptionNotification);
            String deliveryNotification = request.getParameter("deliveryNotification");
            aascShipmentHeaderInfo.setDeliveryNotification(deliveryNotification);
            String formatType = request.getParameter("formatType");
            aascShipmentHeaderInfo.setFormatType(formatType);
//            String salesOrderNumber = request.getParameter("salesOrderNumber");
//            aascShipmentHeaderInfo.setSalesOrderNumber(salesOrderNumber);
            String emailCustomerName = request.getParameter("emailCustomerName");
            aascShipmentHeaderInfo.setEmailCustomerName(emailCustomerName);
//            String deliveryItemNumber = request.getParameter("deliveryItemNumber");
//            aascShipmentHeaderInfo.setDeliveryItemNumber(deliveryItemNumber);
            
            try {
                upsServiceLevelCode =request.getParameter("ajaxUpsServiceLevelCode");
                upsServiceLevelCode = upsServiceLevelCode.trim();
            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
                upsServiceLevelCode = "";
                e.printStackTrace();
            }
            aascShipmentHeaderInfo.setupsServiceLevelCode(upsServiceLevelCode);
            try {
                carrierServiceLevel = 
                        request.getParameter("ajaxcarrierservicelevel");
                carrierServiceLevel = carrierServiceLevel.trim();
            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
                carrierServiceLevel = "";
                e.printStackTrace();
            }
            aascShipmentHeaderInfo.setCarrierServiceLevel(carrierServiceLevel);
            try {
                freightCost = Double.parseDouble(request.getParameter("ShipFreightTextBox"));
                aascShipmentHeaderInfo.setFreightCost(freightCost);
                
            } catch (Exception e) {

                logger.severe("Exception::"+e.getMessage());
                aascShipmentHeaderInfo.setFreightCost(0.00);
                e.printStackTrace();
            }
            String avFlag = request.getParameter("avFlag");
            String fsFlag = request.getParameter("fsFlag");
            aascShipmentHeaderInfo.setAvFlag(avFlag);
            aascShipmentHeaderInfo.setFsFlag(fsFlag);
            /*if (orderNumber2.equalsIgnoreCase("")) // checking whether orderNum is null or not
            {
                orderNumber2 = "0";
            } //end of if block*/
            orderNumber = orderNumber2; //Integer.parseInt(orderNumber2);
            aascShipmentHeaderInfo.setOrderNumber(orderNumber2); // setting the orderNumber get from the jsp to the aascShipmentHeaderInfo.orderNumber method
            
            String labelFormat = nullStrToSpc(request.getParameter("labelFormat")) ;
            aascShipmentHeaderInfo.setLabelFormat(labelFormat);
            
            packsCount1 =  request.getParameter("countPackets"); // getting the countPackets  of the jsp through the struts-config.xml
            if (packsCount1 != null && !"".equalsIgnoreCase(packsCount1)) // checking whether orderNum is null or not
            {
                packsCount = Integer.parseInt(packsCount1);
            }

            ListIterator packageIterator =(ListIterator)packageLinkedList.iterator(); // this method returns the object of the ListIterator
            int count = 0;
            int packIndex = 0;
            while (packageIterator.hasNext()) {
                packageIterator.next();
                count++;
            } //closing the while loop 

            for (int packageInfoObj = count; packageInfoObj < packsCount; 
                 packageInfoObj++) {
                aascShipmentPackageInfo = 
                        new AascShipmentPackageInfo(); // creating the object of the AascShipmentPackageInfo class
                packageLinkedList.add(aascShipmentPackageInfo);
            } //closing the for loop
            packageIterator = (ListIterator)packageLinkedList.iterator();
            int pkgCount = packageLinkedList.size();
            for (int packageInfoObj2 = 0; packageInfoObj2 < packsCount; 
                 packageInfoObj2++) {
                packIndex = packIndex + 1;

                aascShipmentPackageInfo =(AascShipmentPackageInfo)packageIterator.next();
                String itemNumber =nullStrToSpc(request.getParameter("lineNo" + packIndex));
                aascShipmentPackageInfo.setItemNumber(itemNumber); // setting the itemNumber get from the jsp to the aascShipmentPackageInfo.setItemNumber method
                String shippedQty = nullStrToSpc(request.getParameter("shippedQty" + packIndex));
                String weight = nullStrToSpc(request.getParameter("weight" + packIndex));
                // TO FIX sc_7 BUG ADDED BY SRAVANTHI
                String msnStr = nullStrToSpc(request.getParameter("msnNo" + packIndex));
                float fshippedQty = 0;

                try {
                    if ("".equalsIgnoreCase(shippedQty)) {
                        fshippedQty = 0;

                    } else {
                        fshippedQty = Float.parseFloat(shippedQty);
                    }
                } catch (Exception e) {
                    fshippedQty = 0;
                    e.printStackTrace();
                }

                aascShipmentPackageInfo.setShippedQuantity(fshippedQty);
                int imsn = 0;
                try {
                    if ("".equalsIgnoreCase(msnStr)) {
                        imsn = 0;
                    } else {
                        imsn = Integer.parseInt(msnStr);
                    }
                } catch (Exception e) {
                    imsn = 0;
                }
                aascShipmentPackageInfo.setMsn(imsn);
                float fweight = 0;

                try {
                    if ("".equalsIgnoreCase(weight)) {
                        fweight = 0;

                    } else {

                        fweight = Float.parseFloat(weight);

                    }
                } catch (Exception e) {
                    fweight = 0;
                }

                aascShipmentPackageInfo.setWeight(fweight);
                //UOM
                String uom = 
                    nullStrToSpc(request.getParameter("uom" + packIndex));

                aascShipmentPackageInfo.setUom(uom);

                //Tracking Number
                String trackingNumber = nullStrToSpc(request.getParameter("trackingNumber" + packIndex));
               // System.out.println("trackingNumber:2:"+trackingNumber);
                aascShipmentPackageInfo.setTrackingNumber(trackingNumber);

//pay method
 String rtnPayMethod1 = nullStrToSpc(request.getParameter("carrierPayMethod" + packIndex));
  //System.out.println("aascShipmentPackageInfo::rtnPayMethod:2:"+rtnPayMethod1);
 aascShipmentPackageInfo.setRtnPayMethod(rtnPayMethod1);
 
                //Dimension
                String dimensions =

                    nullStrToSpc(request.getParameter("dimensions" + 
                                                      packIndex));

                aascShipmentPackageInfo.setDimensions(dimensions);

                String dimensionNameHidden = 
                    nullStrToSpc(request.getParameter("dimensionNameHidden" + 
                                                      packIndex));

                try {
                    dimensionNameHidden.substring(0, 1);
                } catch (Exception e) {
                    dimensionNameHidden = "";
                }

                String dimensionName = 
                    nullStrToSpc((String)request.getParameter("dimensionValueHidden" + 
                                                              packIndex));
                  if("".equalsIgnoreCase(dimensionName)){
                      dimensionName = request.getParameter("dimensionName"+packIndex);
                      
                  }

                try {
                    dimensionName.substring(0, 1);
                } catch (Exception e) {
                    dimensionName = "";
                }

                int firstIndex = 0;
                int secondIndex = 0;
                int thirdIndex = 0;
                int fourthIndex = 0;
                String packageLength = "";
                String packageWidth = "";
                String packageHeight = "";
                String units = "";
                String dimId = "";

                int dimNameLength = dimensionName.length();

                if (dimNameLength > 0) {
                    firstIndex = dimensionName.indexOf("*");
                    secondIndex = 
                            (dimensionName.substring(firstIndex + 1, dimNameLength)).indexOf("*");
                    thirdIndex = 
                            (dimensionName.substring((secondIndex + firstIndex + 
                                                      2), 
                                                     dimNameLength)).indexOf("*");
                    fourthIndex = 
                            (dimensionName.substring((thirdIndex + secondIndex + 
                                                      firstIndex + 3), 
                                                     dimNameLength)).indexOf("*");

                    packageLength = dimensionName.substring(0, firstIndex);
                    packageLength = packageLength.trim();
                    packageWidth = 
                            (dimensionName.substring(firstIndex + 1, dimNameLength)).substring(0, 
                                                                                               (secondIndex));
                    packageWidth = packageWidth.trim();
                    packageHeight = 
                            (dimensionName.substring((secondIndex + firstIndex + 
                                                      2), 
                                                     (secondIndex + firstIndex + 
                                                      thirdIndex + 2)));
                    packageHeight = packageHeight.trim();
                    units = 
                            (dimensionName.substring((thirdIndex + secondIndex + 
                                                      firstIndex + 3), 
                                                     (fourthIndex + 
                                                      thirdIndex + 
                                                      secondIndex + 
                                                      firstIndex + 3)));
                    dimId = 
                            (dimensionName.substring((fourthIndex + thirdIndex + 
                                                      secondIndex + 
                                                      firstIndex + 4), 
                                                     dimNameLength));

                }

                if (packageLength == null || 
                    "".equalsIgnoreCase(packageLength)) {

                    aascShipmentPackageInfo.setPackageLength(0);

                } else {

                    aascShipmentPackageInfo.setPackageLength(Integer.parseInt(packageLength));
                }

                if (packageWidth == null || 
                    "".equalsIgnoreCase(packageWidth)) {

                    aascShipmentPackageInfo.setPackageWidth(0);

                } else {

                    aascShipmentPackageInfo.setPackageWidth(Integer.parseInt(packageWidth));
                }

                if (packageHeight == null || 
                    "".equalsIgnoreCase(packageHeight)) {

                    aascShipmentPackageInfo.setPackageHeight(0);

                } else {

                    aascShipmentPackageInfo.setPackageHeight(Integer.parseInt(packageHeight));
                }

                if (dimId == null || "".equalsIgnoreCase(dimId)) {

                    aascShipmentPackageInfo.setDimensionId(0);

                } else {

                    int dimIdInt = Integer.parseInt(dimId);

                    aascShipmentPackageInfo.setDimensionId(dimIdInt);

                }

                if (units != null) {

                    aascShipmentPackageInfo.setDimensionUnits(units);

                }

                codFlag = 
                        nullStrToSpc(request.getParameter("chCOD" + packIndex));
                aascShipmentPackageInfo.setCodFlag(codFlag);

                codAmt = 
                        nullStrToSpc(request.getParameter("codAmt" + packIndex));

                float codAmount = 0;
                try {
                    if (!"".equals(codAmt) && codAmt != null) {
                        codAmount = Float.parseFloat(codAmt);
                    }
                } catch (Exception e) {
                    codAmount = 0;
                }

                aascShipmentPackageInfo.setCodAmt(codAmount);

                double packageDeclaredValue = 0.00;

                String declaredCurrencyCode = "USD";

                String signatureOption = "";

                String returnShipment = "";

                String packageSurChargesStr = "";
                String packageShipmentCostStr = "";

                double packageSurCharges = 0.0;
                double packageShipmentCost = 0.0;

                String codType = "";

                String packageDeclaredValueStr = 
                    nullStrToSpc(request.getParameter("packageDeclaredValue" + 
                                                      packIndex));

                declaredCurrencyCode = 
                        nullStrToSpc(request.getParameter("declaredCurrCode" + 
                                                          packIndex));

                signatureOption = 
                        nullStrToSpc(request.getParameter("signatureOption" + 
                                                          packIndex));

                

                returnShipment = 
                        nullStrToSpc(request.getParameter("returnShipment" + 
                                                          packIndex));

                packageSurChargesStr = 
                        nullStrToSpc(request.getParameter("PackageSurcharge" + 
                                                          packIndex));

                packageShipmentCostStr = 
                        nullStrToSpc(request.getParameter("PackageShipmentCost" + 
                                                          packIndex));

                if (packageDeclaredValueStr != null) {

                    if (!"".equalsIgnoreCase(packageDeclaredValueStr)) {

                        packageDeclaredValue = 
                                Double.parseDouble(packageDeclaredValueStr);

                    }

                }

                if (packageSurChargesStr != null) {

                    if (!"".equalsIgnoreCase(packageSurChargesStr)) {

                        packageSurCharges = 
                                Double.parseDouble(packageSurChargesStr);

                    }

                }

                if (packageShipmentCostStr != null) {

                    if (!"".equalsIgnoreCase(packageShipmentCostStr)) {

                        packageShipmentCost = 
                                Double.parseDouble(packageShipmentCostStr);

                    }

                }

                aascShipmentPackageInfo.setPackageDeclaredValue(packageDeclaredValue);

                aascShipmentPackageInfo.setSignatureOptions(signatureOption);

                aascShipmentPackageInfo.setReturnShipment(returnShipment);

                aascShipmentPackageInfo.setSurCharges(packageSurCharges);

                aascShipmentPackageInfo.setDeclaredCurrCode(declaredCurrencyCode);

                aascShipmentPackageInfo.setPkgCost(packageShipmentCost);

                
                additionalHandlingFlag = 
                        nullStrToSpc(request.getParameter("AdditionalHandling" + packIndex));
                aascShipmentPackageInfo.setAdditionalHandling(additionalHandlingFlag);

                largePackageFlag = 
                        nullStrToSpc(request.getParameter("LargePackage" + packIndex));
                aascShipmentPackageInfo.setLargePackage(largePackageFlag);

                
                String rtnShipFromCompany = 
                    nullStrToSpc(request.getParameter("rtnShipFromCompany" + 
                                                      packIndex)).trim();
                

                String rtnShipToCompany = 
                    nullStrToSpc(request.getParameter("rtnShipToCompany" + 
                                                      packIndex)).trim();
                

                String rtnShipFromContact = 
                    nullStrToSpc(request.getParameter("rtnShipFromContact" + 
                                                      packIndex)).trim();
                

                String rtnShipToContact = 
                    nullStrToSpc(request.getParameter("rtnShipToContact" + 
                                                      packIndex)).trim();
                

                String rtnShipFromLine1 = 
                    nullStrToSpc(request.getParameter("rtnShipFromLine1" + 
                                                      packIndex)).trim();
                String rtnShipToLine1 = 
                    nullStrToSpc(request.getParameter("rtnShipToLine1" + 
                                                      packIndex)).trim();
                String rtnShipFromLine2 = 
                    nullStrToSpc(request.getParameter("rtnShipFromLine2" + 
                                                      packIndex)).trim();
                String rtnShipToLine2 = 
                    nullStrToSpc(request.getParameter("rtnShipToLine2" + 
                                                      packIndex)).trim();
                String rtnShipFromCity = 
                    nullStrToSpc(request.getParameter("rtnShipFromCity" + 
                                                      packIndex)).trim();
                String rtnShipFromSate = 
                    nullStrToSpc(request.getParameter("rtnShipFromSate" + 
                                                      packIndex)).trim();
                String rtnShipFromZip = 
                    nullStrToSpc(request.getParameter("rtnShipFromZip" + 
                                                      packIndex));
                String rtnShipToCity = 
                    nullStrToSpc(request.getParameter("rtnShipToCity" + 
                                                      packIndex)).trim();
                String rtnShipToState = 
                    nullStrToSpc(request.getParameter("rtnShipToState" + 
                                                      packIndex)).trim();
                String rtnShipToZip = 
                    nullStrToSpc(request.getParameter("rtnShipToZip" + 
                                                      packIndex));
                String rtnCountrySymbol = 
                    nullStrToSpc(request.getParameter("rtnCountrySymbol" + 
                                                      packIndex));
                String rtnShipFromPhone = 
                    nullStrToSpc(request.getParameter("rtnShipFromPhone" + 
                                                      packIndex)).trim();

                
                String rtnShipToPhone = 
                    nullStrToSpc(request.getParameter("rtnShipToPhone" + 
                                                      packIndex)).trim();

                String rtnShipMethod = 
                    nullStrToSpc(request.getParameter("rtnShipMethod" + 
                                                      packIndex));
                String rtnDropOfType = 
                    nullStrToSpc(request.getParameter("rtnDropOfType" + 
                                                      packIndex));
                String rtnPackageList = 
                    nullStrToSpc(request.getParameter("rtnPackageList" + 
                                                      packIndex));
                String rtnPayMethod = 
                    nullStrToSpc(request.getParameter("rtnPayMethod" + 
                                                      packIndex));
                String rtnPayMethodCode = 
                    nullStrToSpc(request.getParameter("rtnPayMethodCode" + 
                                                      packIndex));
                String rtnACNumber = 
                    nullStrToSpc(request.getParameter("rtnACNumber" + 
                                                      packIndex)).trim();
                String rtnRMA = 
                    nullStrToSpc(request.getParameter("rtnRMA" + packIndex)).trim();

                String rtnDeclaredValueStr = 
                    nullStrToSpc(request.getParameter("rtnDeclaredValue" + 
                                                      packIndex)).trim();
                String rtnDesc = 
                    nullStrToSpc(request.getParameter("returnDescription" + 
                                                      packIndex)).trim();
//System.out.println("rtnDesc::rtnDesc::"+rtnDesc);

                double rtnDeclaredValue = 0.0;
                if (rtnDeclaredValueStr != null) {

                    if (!"".equalsIgnoreCase(rtnDeclaredValueStr)) {

                        rtnDeclaredValue = 
                                Double.parseDouble(rtnDeclaredValueStr);

                    }

                }

                if ("PRINTRETURNLABEL".equalsIgnoreCase(returnShipment)) {
                    aascShipmentPackageInfo.setRtnDesc(rtnDesc);
                    aascShipmentPackageInfo.setRtnDeclaredValue(rtnDeclaredValue);
                    aascShipmentPackageInfo.setRtnShipFromCompany(rtnShipFromCompany);
                    aascShipmentPackageInfo.setRtnShipToCompany(rtnShipToCompany);
                    aascShipmentPackageInfo.setRtnShipFromContact(rtnShipFromContact);
                    aascShipmentPackageInfo.setRtnShipToContact(rtnShipToContact);
                    aascShipmentPackageInfo.setRtnShipFromLine1(rtnShipFromLine1);
                    aascShipmentPackageInfo.setRtnShipToLine1(rtnShipToLine1);
                    aascShipmentPackageInfo.setRtnShipFromLine2(rtnShipFromLine2);
                    aascShipmentPackageInfo.setRtnShipToLine2(rtnShipToLine2);
                    aascShipmentPackageInfo.setRtnShipFromCity(rtnShipFromCity);
                    aascShipmentPackageInfo.setRtnShipFromSate(rtnShipFromSate);
                    aascShipmentPackageInfo.setRtnShipFromZip(rtnShipFromZip);
                    aascShipmentPackageInfo.setRtnShipToCity(rtnShipToCity);
                    aascShipmentPackageInfo.setRtnShipToState(rtnShipToState);
                    aascShipmentPackageInfo.setRtnShipToZip(rtnShipToZip);
                    aascShipmentPackageInfo.setRtnCountrySymbol(rtnCountrySymbol);
                    aascShipmentPackageInfo.setRtnShipFromPhone(rtnShipFromPhone);
                    aascShipmentPackageInfo.setRtnShipToPhone(rtnShipToPhone);
                    aascShipmentPackageInfo.setRtnShipMethod(rtnShipMethod);
                    aascShipmentPackageInfo.setRtnDropOfType(rtnDropOfType);
                    aascShipmentPackageInfo.setRtnPackageList(rtnPackageList);
                    aascShipmentPackageInfo.setRtnPayMethod(rtnPayMethod);
                    aascShipmentPackageInfo.setRtnPayMethodCode(rtnPayMethodCode);
                    aascShipmentPackageInfo.setRtnACNumber(rtnACNumber);
                    aascShipmentPackageInfo.setRtnRMA(rtnRMA);
                }

                String hal = 
                    nullStrToSpc(request.getParameter("holdAtLocation" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHalFlag(hal);

                String halPhone = 
                    nullStrToSpc(request.getParameter("halPhone" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHalPhone(halPhone);

                String halCity = 
                    nullStrToSpc(request.getParameter("halCity" + packIndex)).trim();

                aascShipmentPackageInfo.setHalCity(halCity);

                String halLine1 = 
                    nullStrToSpc(request.getParameter("halAddrLine1" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHalLine1(halLine1);

                String halLine2 = 
                    nullStrToSpc(request.getParameter("halAddrLine2" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHalLine2(halLine2);

                String halState = 
                    nullStrToSpc(request.getParameter("halState" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHalStateOrProvince(halState);

                String halZip = 
                    nullStrToSpc(request.getParameter("halZip" + packIndex)).trim();

                aascShipmentPackageInfo.setHalPostalCode(halZip);
                
                //=======================================================
                String dryIce = nullStrToSpc(request.getParameter("chDryIce" + packIndex)).trim();
                aascShipmentPackageInfo.setDryIceChk(dryIce);
                
                String dryIceWeight = nullStrToSpc(request.getParameter("dryIceWeight" + packIndex)).trim();
                if ("".equalsIgnoreCase(dryIceWeight)) {

                    aascShipmentPackageInfo.setDryIceWeight(Double.parseDouble("0.0"));

                } else {

                    aascShipmentPackageInfo.setDryIceWeight(Double.parseDouble(dryIceWeight));
                }
                
                
                String dryIceUnits = nullStrToSpc(request.getParameter("dryIceUnits" + packIndex)).trim();
                aascShipmentPackageInfo.setDryIceUnits(dryIceUnits);
                //========================================================

                String HazMatFlag = 
                    nullStrToSpc(request.getParameter("HazMatFlag" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHazMatFlag(HazMatFlag);

                String HazMatType = 
                    nullStrToSpc(request.getParameter("HazMatType" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHazMatType(HazMatType);

                String HazMatClass = 
                    nullStrToSpc(request.getParameter("HazMatClass" + 
                                                      packIndex)).trim();

                aascShipmentPackageInfo.setHazMatClass(HazMatClass);

                String hazMatCharges = 
                    nullStrToSpc(request.getParameter("HazMatCharges" + 
                                                      packIndex)).trim();
                if ("".equalsIgnoreCase(hazMatCharges)) {
                    aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble("0.0"));
                } else {
                    aascShipmentPackageInfo.setHazMatCharges(Double.parseDouble(hazMatCharges));
                }
                
                String hazMatQuantity = 
                    nullStrToSpc(request.getParameter("HazMatQty" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatQuantity)) {

                    aascShipmentPackageInfo.setHazMatQty(Double.parseDouble("0.0"));

                } else {

                    aascShipmentPackageInfo.setHazMatQty(Double.parseDouble(hazMatQuantity));
                }

                String hazMatUnit = 
                    nullStrToSpc(request.getParameter("HazMatUnit" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatUnit)) {
                    aascShipmentPackageInfo.setHazMatUnit("");
                } else {

                    aascShipmentPackageInfo.setHazMatUnit(hazMatUnit);
                }

                String hazMatIdNo = 
                    nullStrToSpc(request.getParameter("HazMatIdNo" + 
                                                      packIndex)).trim();
                
                if ("".equalsIgnoreCase(hazMatIdNo)) {
                    aascShipmentPackageInfo.setHazMatIdNo("");
                } else {

                    aascShipmentPackageInfo.setHazMatIdNo(hazMatIdNo);
                }

                String hazMatPkgGroup = 
                    nullStrToSpc(request.getParameter("HazMatPkgGroup" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatPkgGroup)) {
                    aascShipmentPackageInfo.setHazMatPkgGroup("");
                } else {

                    aascShipmentPackageInfo.setHazMatPkgGroup(hazMatPkgGroup);
                }

                String hazMatDOTLabel = 
                    nullStrToSpc(request.getParameter("HazMatDOTLabel" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatDOTLabel)) {
                    aascShipmentPackageInfo.setHazMatDOTLabel("");
                } else {

                    aascShipmentPackageInfo.setHazMatDOTLabel(hazMatDOTLabel);
                }

                String hazMatEmerContactNo = 
                    nullStrToSpc(request.getParameter("HazMatEmerContactNo" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatEmerContactNo)) {
                    aascShipmentPackageInfo.setHazMatEmerContactNo("");
                } else {

                    aascShipmentPackageInfo.setHazMatEmerContactNo(hazMatEmerContactNo);
                }

                String hazMatEmerContactName = 
                    nullStrToSpc(request.getParameter("HazMatEmerContactName" + 
                                                      packIndex)).trim();

                if ("".equalsIgnoreCase(hazMatEmerContactName)) {
                    aascShipmentPackageInfo.setHazMatEmerContactName("");
                } else {

                    aascShipmentPackageInfo.setHazMatEmerContactName(hazMatEmerContactName);
                }

                double hazmatPkgingCount = 0.0;
                String HazmatPkgingCnt = 
                    nullStrToSpc(request.getParameter("HazMatPackagingCnt" + 
                                                      packIndex)).trim();
                if (!"".equalsIgnoreCase(HazmatPkgingCnt)) {

                    try {
                        hazmatPkgingCount = 
                                Double.parseDouble(HazmatPkgingCnt);
                        aascShipmentPackageInfo.setHazmatPkgingCnt(hazmatPkgingCount);
                        
                    } catch (Exception e) {
                        aascShipmentPackageInfo.setHazmatPkgingCnt(hazmatPkgingCount);
                    }
                } else {
                    aascShipmentPackageInfo.setHazmatPkgingCnt(hazmatPkgingCount);
                }

                String hazmatPkgingUnits = 
                    nullStrToSpc(request.getParameter("HazMatPackagingUnits" + 
                                                      packIndex)).trim();
                aascShipmentPackageInfo.setHazmatPkgingUnits(hazmatPkgingUnits);
                

                

                // Added on Jul-05-2011
                String hazmatTechnicalName = 
                    nullStrToSpc(request.getParameter("HazMatTechnicalName" + 
                                                      packIndex)).trim();
                aascShipmentPackageInfo.setHazmatTechnicalName(hazmatTechnicalName);
                
                //End on Jul-05-2011

                String hazmatSignatureName = 
                    nullStrToSpc(request.getParameter("HazMatSignatureName" + 
                                                      packIndex)).trim();
                aascShipmentPackageInfo.setHazmatSignatureName(hazmatSignatureName);
                
                String HazMatPackInstructions = 
                    nullStrToSpc(request.getParameter("HazMatPackInstructions" + 
                                                      packIndex)).trim();
                aascShipmentPackageInfo.setHazmatPackInstructions(HazMatPackInstructions);
                

                String hazMatId = 
                    nullStrToSpc(request.getParameter("HazMatId" + 
                                                      packIndex)).trim();


                if ("".equalsIgnoreCase(hazMatId)) {
                    aascShipmentPackageInfo.setHazMatId("");
                } else {

                    aascShipmentPackageInfo.setHazMatId(hazMatId);
                }


                Packpackaging = 
                        nullStrToSpc(request.getParameter("pkging" + packIndex));

                aascShipmentPackageInfo.setPackaging(Packpackaging);

                delConfirm = 
                        nullStrToSpc(request.getParameter("delConfirm" + packIndex));

                codType = 
                        nullStrToSpc(request.getParameter("codType" + packIndex));
                codFundsCode = 
                        nullStrToSpc(request.getParameter("codFundsCode" + 
                                                          packIndex));
                codCurrCode = 
                        nullStrToSpc(request.getParameter("codCurrCode" + packIndex));
                declaredCurrCode = 
                        nullStrToSpc(request.getParameter("declaredCurrCode" + 
                                                          packIndex));

                

                String shipMethodMeaningTemp1 = 
                    nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning());

                int carrierIdTemp = 
                    aascShipMethodInfo.getCarrierId(shipMethodMeaningTemp1);
                int carrierCodeTmp = 
                    aascShipMethodInfo.getCarrierCode(carrierIdTemp);
                if (carrierCodeTmp == 100) {
                    aascShipmentPackageInfo.setSignatureOptions(delConfirm);
                }

                aascShipmentPackageInfo.setCodCode(codType);
                aascShipmentPackageInfo.setCodFundsCode(codFundsCode);

                aascShipmentPackageInfo.setCodCurrCode(codCurrCode);

                aascShipmentPackageInfo.setDeclaredCurrCode(declaredCurrCode);
                aascShipmentPackageInfo.setPackageSequence(String.valueOf(packIndex));
                aascShipmentPackageInfo.setPackageCount(String.valueOf(pkgCount));

                LinkedList packageLinkedListEdit = new LinkedList();
                packageLinkedListEdit.add(aascShipmentPackageInfo);
                aascShipmentOrderInfo.setShipmentPackageInfo(packageLinkedListEdit);

             //   intlFlag = request.getParameter("intlFlag");
                
                intlFlag = request.getParameter("intlFlag");
                logger.info("intlFlag : " + intlFlag);
                
                intlSaveFlag = request.getParameter("intlSaveFlag");
                logger.info("internationalHiddde intlSaveFlag === " + intlSaveFlag);
                logger.info("internationalHiddde === " + intlFlag);                

                if ("Y".equalsIgnoreCase(intlFlag)) {
                    
                    userIdint = ((Integer)session.getAttribute("user_id")).intValue(); // getting UserId from session
                    
                    
                    int clientIdInt = (Integer)session.getAttribute("client_id");
                    String clientIdTmp = "" + clientIdInt;
                    logger.info("orderNumber :::::" + orderNumber);
                    logger.info("locationId :::::" + locationId);

                    try {
                        clientIdTmp.substring(0, 1);
                    } catch (Exception e) {
                        clientIdInt = (Integer)session.getAttribute("clientId");
                    }

                    aascIntlInfo = aascShipmentOrderDelegate.getIntlShipments(orderNumber, clientIdInt, locationId);

                    if (aascIntlInfo == null) {
                        
                    }
                }

            } //end of for loop
            dimensionUnit = 
                    nullStrToSpc(request.getParameter("dimensionUnit")); // getting the dimension value  of the jsp through the struts-config.xml

            if (!dimensionUnit.equalsIgnoreCase("")) // checking whether the dimension value is null or not
            {
                aascShipmentPackageInfo.setDimensionUnits(dimensionUnit); // setting the dimension value get from the jsp to the aascShipmentPackageInfo.setDimension method
            } //closing the if loop
            aascShipmentOrderInfo.setShipmentPackageInfo(packageLinkedList); // setting the packageLinkedList object to the aascShipmentOrderInfo.setShipmentPackageInfo method
            submitButton = 
                    request.getParameter("submitButton"); // this method returns the value of the submit button
            
            aascProfileOptionsInfo = 
                    (AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo"); //getting the object of the AascProfileOptionsInfo class object through the session
            
            
            //String labelPath =(String)session.getAttribute("cloudLabelPath");
            
             
            aascProfileOptionsInfo.setNonDiscountedCost(aascShipMethodInfo.getNonDiscountedCost(aascShipmentHeaderInfo.getCarrierId()));
            aascProfileOptionsInfo.setAcctNumNegotiatedFlag(aascShipMethodInfo.getAcctNegotiatedCertified(aascShipmentHeaderInfo.getCarrierId())); 

            // Testing of ship to customer for the user if the address book of user is User Level.
                
                logger.info("Ship to customer name:::::"+aascShipmentHeaderInfo.getCustomerName());
                logger.info("order number::::"+aascShipmentHeaderInfo.getOrderNumber());
//                AascShipmentOrderDelegate aascShipmentOrderDelegate = new AascShipmentOrderDelegate();
 String addressBookLevel = (String)session.getAttribute("addressBookLevel");
            if("UL".equalsIgnoreCase(addressBookLevel)){
                int result = aascShipmentOrderDelegate.verifyCustomerName(request);
                if(result == 100) {
//                    return "success";
                }
                else{// else block for failure
                    AascShipmentOrderInfo aascShipmentOrderInfo1 = null;
                    aascShipmentOrderInfo1 = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
                    String updatedOrderNumber = aascShipmentOrderInfo1.getShipmentHeaderInfo().getOrderNumber();
                    aascShipmentOrderInfo.getShipmentHeaderInfo().setOrderNumber(updatedOrderNumber);
                    aascShipmentOrderInfo.getShipmentHeaderInfo().setShipFlag("N");
                    
                    if("Y".equalsIgnoreCase(intlSaveFlag)){
                        session.setAttribute("intlSaveFlag", "Y");
                        session.setAttribute("intlCustomsFlag", "Y");
                    }
                    session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);
                    request.setAttribute("key", "aasc153");
                    return "success";
                }
                
            }
            // End of code of testing User Level address book.
            
            
            // Below code added for Get Rates
            if(submitButton.equalsIgnoreCase("Get Rates")){
                AascFreightShopDelegate aascFreightShopDelegate = new AascFreightShopDelegate();
                aascFreightShopDelegate.getFreightShopMethods(request,session);
                AascShipmentOrderInfo aascShipmentOrderInfo1 = null;
                aascShipmentOrderInfo1 = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
                String updatedOrderNumber = aascShipmentOrderInfo1.getShipmentHeaderInfo().getOrderNumber();
                aascShipmentOrderInfo.getShipmentHeaderInfo().setOrderNumber(updatedOrderNumber);
                aascShipmentOrderInfo.getShipmentHeaderInfo().setShipFlag("N");
                
                if("Y".equalsIgnoreCase(intlSaveFlag)){
                    session.setAttribute("intlSaveFlag", "Y");
                    session.setAttribute("intlCustomsFlag", "Y");
                }
                String shipmentRate = (String)request.getAttribute("ratesFromGetRates");
                logger.info("shipmentRate:::"+shipmentRate);
                try{
                    logger.info("ratesFromFreightShop:::"+ratesFromFreightShop);
                    if(ratesFromFreightShop.equalsIgnoreCase(shipmentRate))
                        request.setAttribute("ratesFromGetRates",ratesFromFreightShop);
                    else
                        request.setAttribute("ratesFromGetRates",shipmentRate);
                        
                    aascShipmentOrderInfo.setFsRates((String)request.getAttribute("ratesFromGetRates"));
                    session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);
                }catch(Exception e){
                    logger.severe("Exception ::::"+e.getMessage());
                    e.printStackTrace();
                }
                //Added below code to save header and package details to db when Get Rates button is clicked. Bug #3180. By Y Pradeep.
                try{
                int userIdTmp = ((Integer)session.getAttribute("user_id")).intValue();
                returnStatus = aascShipmentOrderDelegate.saveShipment(userIdTmp,  aascShipmentOrderInfo, clientID, returnStatus) ;
                }catch(Exception e){
                    logger.severe("Exception while saving in get rates ::::"+e.getMessage());
                    e.printStackTrace();
                }
                return "success";
            }
            if (submitButton.equalsIgnoreCase("Void")) //checking whether the submit button value is equal to void 
            {
                //intlFlagWS = nullStrToSpc(request.getParameter("intlFlag"));

                key = "";
                try {
                    AascShipmentOrderInfo aascShipmentOrderSaved = aascShipmentOrderDelegate.getShipmentInfo(orderNumber , clientID)    ;
                    LinkedList savePackageList = aascShipmentOrderSaved.getShipmentPackageInfo();
                    oldPackcount = savePackageList.size();
                    // In Elkay iterated savePackageList but not updated in merging
                    ListIterator packltr1 = (ListIterator)savePackageList.iterator();
                    aascShipMethodInfo = (AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
                    int index1 = 0;
                    session.setAttribute("intlSaveFlag", "N");
                    voidStatusFlagVal = request.getParameter("voidStatusFlag");
                    LinkedList packLinkList = new LinkedList();
                    String voidFlag = "";
                    String rtnPayMethod = "";
                    String packageTrackingNumber1 = "";
                    String voidField = "";
                    for (int pack = 0; pack < oldPackcount; pack++) {
                        index1 = index1 + 1;
                        AascShipmentPackageInfo packbean =(AascShipmentPackageInfo)packltr1.next();
                        try {
                            String shipMethodMeaningTemp = nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning());
                            int carrierIdTemp = aascShipMethodInfo.getCarrierId(shipMethodMeaningTemp);
                            int carrierCodeTemp = aascShipMethodInfo.getCarrierCode(carrierIdTemp);
//                            if (carrierCodeTemp == 100 && connectionModeUPS.equalsIgnoreCase("UPS Direct")) {
                            if(carrierCodeTemp == 100){
                                voidField = "chVoidHidden";
                            } else {
                                voidField = "chVoid";
                            }

                            reqVoidFlagHidden =request.getParameter("chVoidHidden" +index1);
                            try {
                                if (reqVoidFlagHidden.equalsIgnoreCase("") || 
                                    reqVoidFlagHidden == null) {
                                    reqVoidFlagHidden = "N";
                                }
                            } catch (Exception e) {
                                reqVoidFlagHidden = "N";
                            }
                            packbean.setReqVoidFlagHidden(reqVoidFlagHidden);
                            voidFlag =request.getParameter(voidField + index1);
                            String voidFlagTemp =request.getParameter("chVoid" + index1);
                            try {
                                if (voidFlagTemp.equalsIgnoreCase("") || 
                                    voidFlagTemp == null) {
                                    voidFlagTemp = "N";
                                }
                            } catch (Exception e) {
                                voidFlagTemp = "N";
                            }
                            packbean.setTemporaryFlag(voidFlagTemp);

                            try {
                                if (voidFlag.equalsIgnoreCase("") || 
                                    voidFlag == null) {
                                    voidFlag = "N";
                                }
                            } catch (Exception e) {
                                voidFlag = "N";
                            }

                            packbean.setPackageSequence(String.valueOf(index1));
                            packbean.setVoidFlag(voidFlag);
                            packageTrackingNumber1 =packbean.getTrackingNumber();
                            try {
                                if (packageTrackingNumber1.equalsIgnoreCase("") || 
                                    packageTrackingNumber1 == null) {
                                    packageTrackingNumber1 = "";
                                }
                            } catch (Exception e) {
                                packageTrackingNumber1 = "";
                            }
                            packbean.setTrackingNumber(packageTrackingNumber1);
                            
                            rtnPayMethod =packbean.getRtnPayMethod();
                            System.out.println("rtnPayMethod:1545::"+rtnPayMethod);
                            try {
                                if (rtnPayMethod.equalsIgnoreCase("") || 
                                    rtnPayMethod == null) {
                                    rtnPayMethod = "";
                                }
                            } catch (Exception e) {
                                rtnPayMethod = "";
                            }
                            System.out.println("rtnPayMethod:1554::"+rtnPayMethod);
                            packbean.setRtnPayMethod(rtnPayMethod);
                            

                            packLinkList.add(packbean);

                        } catch (Exception e) {
                            logger.severe("got an exception in calling Shipment order action Void loop" + 
                                          e.getMessage());
                        }
                    }

                    aascShipmentOrderInfo.setShipmentPackageInfo(packLinkList);
                    aascShipmentHeaderInfo = 
                            aascShipmentOrderSaved.getShipmentHeaderInfo(); // this method returns the object of the aascShipmentHeaderInfo class
                    aascShipmentHeaderInfo.setVoidType(voidStatusFlagVal);    
                    aascShipmentOrderInfo.setShipmentHeaderInfo(aascShipmentHeaderInfo);
                    aascProfileOptionsInfo.setNonDiscountedCost(aascShipMethodInfo.getNonDiscountedCost(aascShipmentHeaderInfo.getCarrierId()));

                    aascProfileOptionsInfo.setAcctNumNegotiatedFlag(aascShipMethodInfo.getAcctNegotiatedCertified(aascShipmentHeaderInfo.getCarrierId())); 
                    
                    returnStatus = aascShipmentOrderDelegate.voidShipment(aascProfileOptionsInfo, aascShipmentOrderInfo,  aascShipMethodInfo ,session, request ); // calling voidShipment 
                    logger.info("returnStatus in action class : "+returnStatus);
                    orderNumber3 = aascShipmentHeaderInfo.getOrderNumber(); // this method returns  the order number 
                    key = "aasc" + returnStatus;
                    String numOfVoidedPackagesValue = request.getParameter("numOfVoidedPackages");
                    request.setAttribute("numOfVoidedPackagesValue", numOfVoidedPackagesValue);
                    request.setAttribute("key", key);
  error=aascShipmentHeaderInfo.getMainError();
                    if (returnStatus != 130) {
                        request.setAttribute("key", error);
                        request.setAttribute("error",error);
                    } else {
                        aascShipmentOrderInfo = aascShipmentOrderDelegate.getOrderInfo(orderNumber, new Integer(clientID), session);        
                    }

                    voidStatusFlagVal = request.getParameter("voidStatusFlag");         
                    request.setAttribute("voidStatusFlagVal", voidStatusFlagVal);
                    //Commented below code to display values in shipping page after order is void.
                    /*if (voidStatusFlagVal.equalsIgnoreCase("ShipmentVoid") && 
                        returnStatus == 130) {
                        aascShipmentOrderInfo = null; // making the aascShipmentOrderInfo object value null
                        aascShipmentOrderInfo = new AascShipmentOrderInfo();
                    } else {
                        aascShipmentOrderInfo = 
                         aascShipmentOrderDelegate.getOrderInfo(orderNumber, new Integer(clientID), session);
                    }*/
                    session.setAttribute("AascShipmentOrderInfo",  aascShipmentOrderInfo);

                } //closing the try block
                catch (Exception e) {
                    logger.severe("got an exception in calling Shipment Order Ship save action: " + 
                                  e.getMessage());
                    e.printStackTrace();
                    key = "aasc131";
                } //closing the catch block
                request.setAttribute("key", key);
            } //closing the if block
    
            if (submitButton.equalsIgnoreCase("Ship")) {
                if("".equalsIgnoreCase(aascShipmentHeaderInfo.getOrderNumber()) || aascShipmentHeaderInfo.getOrderNumber() == null){    // Modified if condition to generate order number if ordernumber is empty. By Y Pradeep
                    
                    orderNumber = aascShipmentOrderDelegate.generateOrderNumber(clientID);
                    aascShipmentHeaderInfo.setOrderNumber(orderNumber+"");
                }
                if("Y".equalsIgnoreCase(intlSaveFlag)){
                    session.setAttribute("intlSaveFlag", "Y");
                    session.setAttribute("intlCustomsFlag", "Y");
                }
                key = "";
                String shipError = "";
                String warningChk = "";
                String errorDesc = "";
                logger.info(" in the if block of SHIP");

                int index = shippingMethod.indexOf("*");
                shippingMethod = 
                        shippingMethod.substring(index + 1, shippingMethod.length());
                

                try {
                    //Added

                    String shipMethodMeaning = 
                        nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning());

                    

                    
                    int carrierId = 
                        aascShipMethodInfo.getCarrierId(shipMethodMeaning);
                    
                    carrierCode = 
                        aascShipMethodInfo.getCarrierCode(carrierId);
                    

                    fedExCarrierMode = 
                            aascShipMethodInfo.getCarrierMode(carrierId);

                    fedExKey = 
                            aascShipMethodInfo.getCarrierUserName(carrierId);
                    fedExPassword = 
                            aascShipMethodInfo.getCarrierPassword(carrierId);

                    
                    AascAccountNumbersBean aascAccountNumbersInfo = null;
                    
                    String queryTimeOut = (String)session.getAttribute("queryTimeOut");

                    int queryTimeOutInt = Integer.parseInt(queryTimeOut);

                    int clientIDMain = 
                        Integer.parseInt(((Integer)session.getAttribute("client_id")).toString());
                    
                    aascAccountNumbersInfo = aascShipmentOrderDelegate.getAccountNumbersInfo(locationId, carrierCode, clientIDMain, queryTimeOutInt) ;
                                                                        
                    if(carrierCode != 115){
                    if (aascAccountNumbersInfo != null) {
                    try{
                        accountNumbersList = 
                                aascAccountNumbersInfo.getAccountNumbersList();
                        
                        accountNumbersIterator = accountNumbersList.listIterator();
                    }catch(Exception e){
                    e.printStackTrace();
                    }
                        
                    }
try{
                    while (accountNumbersIterator.hasNext()) {

                        AascAccountNumbersBean aascAccountNumbersInfo1 = (AascAccountNumbersBean)accountNumbersIterator.next();

                        String accountNumberMain = 
                            aascAccountNumbersInfo1.getAccountNumber();
                        if (carrierACNumber.equalsIgnoreCase(accountNumberMain)) {

                            String fedexKeyFromPopup = nullStrToSpc(aascAccountNumbersInfo1.getAccountUserName());
                            String fedexPasswordFrompopup =  nullStrToSpc(aascAccountNumbersInfo1.getAccountPassword());
                            if (fedexKeyFromPopup != "" && 
                                fedexPasswordFrompopup != "") {
                                fedExKey = fedexKeyFromPopup;
                                fedExPassword = fedexPasswordFrompopup;
                            }
                        }
                    }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
} 
       // vikas added code for mode shipexec    
     
if(connectionModeUPS.equalsIgnoreCase("ShipExec")){
    logger.info("carrierCode::before1::"+carrierCode);
    if (carrierCode == 100 && wayBill.equalsIgnoreCase("")) {

        logger.info("In ShipExec");
        try {
            warningChk = 
                    request.getParameter("shipmentWarningStatus");
            warningChk.substring(0, 1);
            
        } catch (Exception e) {
            warningChk = "";
        }
        try {
            if ("continue".equalsIgnoreCase(warningChk)) {

                String shipDigest = 
                    (String)session.getAttribute("shipDigest");
                if (!"".equals(shipDigest) && 
                    shipDigest != null) {
                    aascShipmentHeaderInfo.setShipmentDigest(shipDigest);
                }
                session.removeAttribute("shipDigest");
            }
        } catch (Exception e) {
            logger.severe("Exception in Action class while retreiving shipDigest from session==" + 
                        e.getMessage());
        }
        aascShipmentHeaderInfo.setWarningChk(warningChk);

        session.setAttribute("warningChk", warningChk);
        
        try {
//            LinkedList packageListTemp = 
//                aascShipmentOrderInfo.getShipmentPackageInfo();
//            int size = packageListTemp.size();
//            AppsaascLABELINFOTBL appsAasc_LABEL_INFO_TBL =  new AppsaascLABELINFOTBL();
//            AppsaascLABELINFOREC[] appsAasc_LABEL_INFO_REC =  new AppsaascLABELINFOREC[size];
//            appsAasc_LABEL_INFO_TBL.setIp_aasc_label_info_item(appsAasc_LABEL_INFO_REC);
//            aascShipmentOrderInfo.getShipmentHeaderInfo().setAppsAasc_LABEL_INFO_TBL(appsAasc_LABEL_INFO_TBL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        aascShipExecShipment = new AascShipExecShipment();
        
        String cloudLabelPath = 
            (String)session.getAttribute("cloudLabelPath");
            System.out.println("cloudLabelPath:;::"+cloudLabelPath);
            System.out.println("shipperName:123:"+aascProfileOptionsInfo.getShipperName());
        returnStatus = aascShipExecShipment.createShipmentRequest(aascShipmentOrderInfo, 
                                                      aascShipMethodInfo, aascProfileOptionsInfo, 
                                                      aascIntlInfo, cloudLabelPath); 

        if (returnStatus != 150) {

            shipError =aascShipmentHeaderInfo.getErrorChk();
            request.setAttribute("shipError", shipError);           // Setting them to request instead of session.
            errorDesc = aascShipmentHeaderInfo.getError();
            request.setAttribute("errorDesc", errorDesc);           // Setting them to request instead of session
            try {
                String shipDigest =aascShipmentHeaderInfo.getShipmentDigest();
                if (!"".equals(shipDigest) && 
                    shipDigest != null) {
                    session.setAttribute("shipDigest",shipDigest);
                }
            } catch (Exception e) {
                logger.severe("Exception in Action class while setting shipDigest to session==" + 
                            e.getMessage());
            }
            
            request.setAttribute("error", 
                                 aascShipmentHeaderInfo.getMainError()); // setting the error status to the session
            //session.setAttribute("intlSaveFlag", "N");
            session.setAttribute("AascShipmentOrderInfo", 
                                 aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
            returnStatus = 122;
        }
    }
}
else {
// vikas code ended
logger.info("carrierCode::in else::"+carrierCode);
                    if (carrierCode == 100 && wayBill.equalsIgnoreCase("")) //checking whether the ship method is ups or not
                    {

                        if (connectionModeUPS.equalsIgnoreCase("Connectship")) {
                            logger.info(" in the if block of connectship");
                            
                            if (returnStatus != 150) {
                                
                                request.setAttribute("error", 
                                                     error); // setting the error status to the session
//                                session.setAttribute("intlSaveFlag", "N");
                                session.setAttribute("AascShipmentOrderInfo", 
                                                     aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
                            } //closing the if block
                        }
                        
                        else if (connectionModeUPS.equalsIgnoreCase("UPS Direct")) {

                            logger.info("In UPS DIRECT");
                            try {
                                warningChk = 
                                        request.getParameter("shipmentWarningStatus");
                                warningChk.substring(0, 1);
                                
                            } catch (Exception e) {
                                warningChk = "";
                            }
                            try {
                                if ("continue".equalsIgnoreCase(warningChk)) {

                                    String shipDigest = 
                                        (String)session.getAttribute("shipDigest");
                                    if (!"".equals(shipDigest) && 
                                        shipDigest != null) {
                                        aascShipmentHeaderInfo.setShipmentDigest(shipDigest);
                                    }
                                    session.removeAttribute("shipDigest");
                                }
                            } catch (Exception e) {
                                logger.severe("Exception in Action class while retreiving shipDigest from session==" + 
                                            e.getMessage());
                            }
                            aascShipmentHeaderInfo.setWarningChk(warningChk);

                            session.setAttribute("warningChk", warningChk);
                            
                            try {
                                /*LinkedList packageListTemp = 
                                    aascShipmentOrderInfo.getShipmentPackageInfo();
                                int size = packageListTemp.size();
                                AppsaascLABELINFOTBL appsAasc_LABEL_INFO_TBL =  new AppsaascLABELINFOTBL();
                                AppsaascLABELINFOREC[] appsAasc_LABEL_INFO_REC =  new AppsaascLABELINFOREC[size];
                                appsAasc_LABEL_INFO_TBL.setIp_aasc_label_info_item(appsAasc_LABEL_INFO_REC);
                                aascShipmentOrderInfo.getShipmentHeaderInfo().setAppsAasc_LABEL_INFO_TBL(appsAasc_LABEL_INFO_TBL);*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            
                            aascUpsShipment = new AascUpsShipment();
                            
                            String cloudLabelPath = 
                                (String)session.getAttribute("cloudLabelPath");
                            returnStatus = aascUpsShipment.createShipmentRequest(aascShipmentOrderInfo, 
                                                                          aascShipMethodInfo, aascProfileOptionsInfo, 
                                                                          aascIntlInfo, cloudLabelPath); 

                            if (returnStatus != 150) {

                                shipError =aascShipmentHeaderInfo.getErrorChk();
                                request.setAttribute("shipError", shipError);           // Setting them to request instead of session.
                                errorDesc = aascShipmentHeaderInfo.getError();
                                request.setAttribute("errorDesc", errorDesc);           // Setting them to request instead of session
                                try {
                                    String shipDigest =aascShipmentHeaderInfo.getShipmentDigest();
                                    if (!"".equals(shipDigest) && 
                                        shipDigest != null) {
                                        session.setAttribute("shipDigest",shipDigest);
                                    }
                                } catch (Exception e) {
                                    logger.severe("Exception in Action class while setting shipDigest to session==" + 
                                                e.getMessage());
                                }
                                
                                request.setAttribute("error", 
                                                     aascShipmentHeaderInfo.getMainError()); // setting the error status to the session
                                //session.setAttribute("intlSaveFlag", "N");
                                session.setAttribute("AascShipmentOrderInfo", 
                                                     aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
                                returnStatus = 122;
                            }
                        }

                    } //closing the if block 

                    else if ((carrierCode == 110 || carrierCode == 111) && 
                             wayBill.equalsIgnoreCase(""))
                    
                    {
                        logger.info("enter into FDX else if part");
                        aascFedexShipment = new AascFedexShipment(); // creating the object of the   AascFedexShipment class
                        
                        aascProfileOptionsInfo.setNonDiscountedCost(aascShipMethodInfo.getNonDiscountedCost(aascShipmentHeaderInfo.getCarrierId()));
                        String cloudLabelPath = 
                            (String)session.getAttribute("cloudLabelPath");
                        returnStatus = 
                                aascFedexShipment.processShipment(aascShipmentOrderInfo, aascShipMethodInfo, 
                                                                  aascIntlInfo,  aascProfileOptionsInfo, 
                                                                  fedExCarrierMode, fedExKey, 
                                                                  fedExPassword,cloudLabelPath); //calling the aascFedexShipment.processShipment method
                        
                        if (returnStatus == 151) {
                            AascFedexVoid aascFedexVoid = new AascFedexVoid();

                            AascShipmentPackageInfo aascPackageInfo;
                            String error = "";
                            LinkedList aascPackageBean = 
                                aascShipmentOrderInfo.getShipmentPackageInfo();

                            Iterator packageIterator1 = 
                                aascPackageBean.iterator();

                            while (packageIterator1.hasNext()) {

                                aascPackageInfo = 
                                        (AascShipmentPackageInfo)packageIterator1.next();

                                //Shiva added below code to void the first package when the second package not shipped in multiple package shipping 
                                String packageTrackingNumber = "";
                                try{
                                 packageTrackingNumber = 
                                    aascPackageInfo.getTrackingNumber().trim();
                                }catch(Exception e){
                                    packageTrackingNumber="";
                                }
                                    
                                if (!"".equals(packageTrackingNumber)){
                                    
                                    
                                     returnStatus = 
                                             aascFedexVoid.voidShipment(aascShipmentOrderInfo, 
                                                                        aascShipMethodInfo, 
                                                                        packageTrackingNumber, 
                                                                        aascProfileOptionsInfo, 
                                                                        fedExCarrierMode, 
                                                                        fedExKey, 
                                                                        fedExPassword, 
                                                                        cloudLabelPath);
                                }
                                aascPackageInfo.setPkgCost(0.0);
                                aascPackageInfo.setSurCharges(0.0);
                                aascPackageInfo.setTrackingNumber("");
                            }
                            aascShipmentOrderInfo.getShipmentHeaderInfo().setWayBill("");
                            aascShipmentOrderInfo.getShipmentHeaderInfo().setTotalSurcharge(0.0);
                            
                            key = "aasc122";
                            error = aascShipmentHeaderInfo.getMainError();
                            request.setAttribute("error", error);

                        }

                        if (returnStatus != 150) {
                            error = aascFedexShipment.getError(); // this method returns the errorStatus value of the aascFedexShipment class
                            request.setAttribute("error", 
                                                 error); // setting the error status to the session
                            
                            returnStatus = 122;
                            key = "aasc122";
                            session.setAttribute("AascShipmentOrderInfo", 
                                                 aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
                        } //closing the if block
                        if(returnStatus == 150){
                            error = aascShipmentHeaderInfo.getMainError();
                            request.setAttribute("error", error);
                            request.setAttribute("shipError","warning");    //  Setting shipError to warning for showing warnings in UI.
                        }
                    } //closing the elseif block  
                    
                    
                    
                     //Mahesh Start
                     
                     else if (carrierCode == 115 && wayBill.equalsIgnoreCase("")) {

                     logger.info("Entered Stamps.com");
                     try {
                     warningChk =
                        request.getParameter("shipmentWarningStatus");
                     warningChk.substring(0, 1);
                     
                     } catch (Exception e) {
                     warningChk = "";
                     }
                     try {
                     if ("continue".equalsIgnoreCase(warningChk)) {

                     String shipDigest =
                        (String)session.getAttribute("shipDigest");
                     if (!"".equals(shipDigest) &&
                        shipDigest != null) {
                        aascShipmentHeaderInfo.setShipmentDigest(shipDigest);
                     }
                     session.removeAttribute("shipDigest");
                     }
                     } catch (Exception e) {
                     logger.severe("Exception in Action class while retreiving shipDigest from session==" +
                            e.getMessage());
                     }
                     aascShipmentHeaderInfo.setWarningChk(warningChk);

                     session.setAttribute("warningChk", warningChk);
                     
                     try {

                     } catch (Exception e) {
                     e.printStackTrace();
                     }

                     
                     aascStampsShipment = new AascStampsShipment();
                     
                     String cloudLabelPath =
                     (String)session.getAttribute("cloudLabelPath");
                     logger.info("cloudLabelPath inside 115::"+cloudLabelPath);
                     
                     aascShipmentOrderInfo.setShipmentHeaderInfo(aascShipmentHeaderInfo);
                     StampsReturnStatus = aascStampsShipment.processShipment(aascShipmentOrderInfo,
                                                          aascShipMethodInfo,
                                                          aascIntlInfo, aascProfileOptionsInfo, cloudLabelPath); 
                     LinkedList packageListTemp = aascShipmentOrderInfo.getShipmentPackageInfo();
                     Iterator listIterator = packageListTemp.listIterator();

                     int carrierstatus = 0;
                     
                     String subStatus = StampsReturnStatus.substring(0,3);
                     carrierstatus = Integer.parseInt(subStatus);
                     String status = StampsReturnStatus.substring(StampsReturnStatus.indexOf("@@")+2,StampsReturnStatus.length());
                     aascShipmentHeaderInfo.setErrorChk(status);
                     aascShipmentHeaderInfo.setError(status);
                     aascShipmentHeaderInfo.setMainError(status);

                     if("151".equalsIgnoreCase(subStatus)){
                     shipError =aascShipmentHeaderInfo.getErrorChk();
                     request.setAttribute("shipError", shipError);           // Setting them to request instead of session.
                     errorDesc = aascShipmentHeaderInfo.getError();
                     request.setAttribute("errorDesc", errorDesc);           // Setting them to request instead of session
                     error = aascShipmentHeaderInfo.getMainError();
                     request.setAttribute("error", error);
                     try {
                     String shipDigest =aascShipmentHeaderInfo.getShipmentDigest();
                     if (!"".equals(shipDigest) &&
                        shipDigest != null) {
                        session.setAttribute("shipDigest",shipDigest);
                     }
                     } catch (Exception e) {
                     logger.severe("Exception in Action class while setting shipDigest to session==" +
                                e.getMessage());
                     }
                     
                     request.setAttribute("error",
                                     aascShipmentHeaderInfo.getMainError()); // setting the error status to the session
                     
                     session.setAttribute("AascShipmentOrderInfo",
                                     aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
                     returnStatus = 122;
                     key = "aasc122";
                     session.setAttribute("AascShipmentOrderInfo",
                                     aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session

                     }
                     else {
                     aascShipmentHeaderInfo.setManualTrackingFlag("Y"); // setting the manualTrackingFlag to Y if the tracking number is entered manually
                     session.setAttribute("ManualTrackingFlag", "Y");
                     returnStatus = 150;
                     } //closing the else block
                     }
                     
                     //Mahesh End
                //Shiva added below code for DHL
                 else if(carrierCode == 114) {
                     logger.info("------------------> EXECUTING DHL CODE <--------------------:");
                     AascDHLShipment aascDHLShipment = new AascDHLShipment();
                     String cloudLabelPath = (String)session.getAttribute("cloudLabelPath");
                     shipMethodMeaning = aascShipmentHeaderInfo.getShipMethodMeaning();
                     String shipMethodActualName = aascShipMethodInfo.getConnectShipScsTag(shipMethodMeaning);
                     returnStatus = aascDHLShipment.processShipment(aascShipmentOrderInfo,
                                                                    aascShipMethodInfo,
                                                                    aascIntlInfo,
                                                                    aascProfileOptionsInfo,
                                                                    intlFlag,
                                                                    carrierServiceLevel,
                                                                    shipMethodActualName,
                                                                    cloudLabelPath
                                                                    );
                     logger.info("----------carrierstatus id dssa "+returnStatus);
                     if(returnStatus == 151 ) {
                         logger.info("inside if for error setting msg "+aascShipmentHeaderInfo.getMainError());
                         error = aascShipmentHeaderInfo.getMainError();
                         session.setAttribute("Error",error);
                         request.setAttribute("error",error);
                         returnStatus = 122;  //Shiva changed to 122 to fix the issue #3979
                     }
                     
                 }
                
                //Shiva code end
                    else {
                        aascShipmentHeaderInfo.setManualTrackingFlag("Y"); // setting the manualTrackingFlag to Y if the tracking number is entered manually
                        session.setAttribute("ManualTrackingFlag", "Y");
                        returnStatus = 150;
                    } //closing the else block

}
                    if (returnStatus == 150 || returnStatus == 122) {
                        LinkedList userInfo = new LinkedList();
                        userInfo.add(userIdstr);

                        int userID = 0;

                        try {
                            userID = ((Integer)session.getAttribute("user_id")).intValue();
                        } catch (Exception e) {
                            logger.severe("Exception while getting  user id" + 
                                        e.getMessage());
                        }

                        try {
                            //AascPrinterDAO aascPrinterDAOTemp = aascDAOFactory.getAascPrinterDAO();
                            //                             wshPrinterName = 
                            //                                     aascPrinterDAOTemp.getWshPrinterName(locationID, 
                            //                                                                          locationIdint, 
                            //                                                                          userID, 
                            //                                                                          responsibilityID);
                        } catch (Exception e) {
                            logger.severe("Exception while getting wshPrinterName" + 
                                        e.getMessage());
                        }
                        
                        if (carrierCode != 999) {

                            /*SavingLabelreturnStatus = 
                                    aasc_PrintShipLabels_PortClient.getPrint_Ship_labelsResponse(aascShipmentOrderInfo, 
                                                                                                   aascProfileOptionsInfo, 
                                                                                                   userInfo, 
                                                                                                   "PRINT", 
                                                                                                   wsDetails, 
                                                                                                   wshPrinterName); */

                        } else {
                            

                        }
                        
                        int userIdTmp = ((Integer)session.getAttribute("user_id")).intValue();
                        carrierSuccessStatus = returnStatus;
                        // Modified below if and else conditions to save shipFlag into database in error case and success case. Bug #2669. By Y Pradeep
                        if(carrierSuccessStatus == 150){
                            aascShipmentHeaderInfo.setShipFlag("Y"); // setting the ShipFlag "Y" means order is ship save
                            key = "aasc225";
                        }
                        else{
                            aascShipmentHeaderInfo.setShipFlag("N");
                        }
                        returnStatus = aascShipmentOrderDelegate.saveShipment(userIdTmp,  aascShipmentOrderInfo, clientID, returnStatus) ;
                        if (returnStatus == 225) {
                            aascCarrierProfileOptionsInfo.setShipMethodMeaning(aascShipmentHeaderInfo.getShipMethodMeaning());
                            aascCarrierProfileOptionsInfo.setDropOffType(aascShipmentHeaderInfo.getDropOfType());
                            aascCarrierProfileOptionsInfo.setCarrierPaymentTerms(aascShipmentHeaderInfo.getCarrierPaymentMethod());
                            aascCarrierProfileOptionsInfo.setCarrierAccountNum(aascShipmentHeaderInfo.getCarrierAccountNumber());
                            aascCarrierProfileOptionsInfo.setCarrierShipmethodValue(shipValue);
                            //For package options
                            try {
                                LinkedList firstPackage12= aascShipmentOrderInfo.getShipmentPackageInfo();
                                ListIterator firstPackage1= firstPackage12.listIterator();
                                while(firstPackage1.hasNext()) {
                                    AascShipmentPackageInfo firstPackagebean = (AascShipmentPackageInfo)firstPackage1.next();
                                    logger.info("firstPackagebean :: "+firstPackagebean.getReturnShipment());
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                            AascShipmentPackageInfo firstPackage = (AascShipmentPackageInfo)aascShipmentOrderInfo.getShipmentPackageInfo().listIterator().next();
                            aascCarrierProfileOptionsInfo.setSignatureOptions(firstPackage.getSignatureOptions());
                            aascCarrierProfileOptionsInfo.setReturnShipmentFlag(firstPackage.getReturnShipment());
                            aascCarrierProfileOptionsInfo.setCodFlag(firstPackage.getCodFlag());
                            aascCarrierProfileOptionsInfo.setCodFundsCode(firstPackage.getCodFundsCode());
                            aascCarrierProfileOptionsInfo.setCodCurrCode(firstPackage.getCodCurrCode());
                            aascCarrierProfileOptionsInfo.setCodType(firstPackage.getCodCode());
                            aascCarrierProfileOptionsInfo.setHalFlag(firstPackage.getHalFlag());
                            aascCarrierProfileOptionsInfo.setHazmatFlag(firstPackage.getHazMatFlag());
                            aascCarrierProfileOptionsInfo.setDryIceFlag(firstPackage.getDryIceChk());
                            aascCarrierProfileOptionsInfo.setAdditionalHandlingFlag(firstPackage.getAdditionalHandling());
                            aascCarrierProfileOptionsInfo.setLargePackageFlag(firstPackage.getLargePackage());
                            try {
                                //Commented by pavan
                                //String shipMethodMeaning = aascShipmentHeaderInfo.getShipMethodMeaning();
                                // int carrierId = aascShipMethodInfo.getCarrierId(shipMethodMeaning);
                                //int carrierCode = aascShipMethodInfo.getCarrierCode(carrierId);
                                //End
                                if (carrierCode == 100) {
                                    aascCarrierProfileOptionsInfo.setPackaging(firstPackage.getPackaging());
                                } else {
                                    aascCarrierProfileOptionsInfo.setPackaging(aascShipmentHeaderInfo.getPackaging());
                                }
                            } catch (Exception e) {
                                logger.severe("error: " + e.getMessage());
                            }
                            session.setAttribute("CarrierProfileOptionsInfo", aascCarrierProfileOptionsInfo);
                        } //closing the if block of (saveStatus==225)
//                        else {
//                            aascShipmentHeaderInfo.setShipFlag("N");
//                        }
                    } //close for if(carrierstatus==150)
                    else {
                        
                    } //closing the else block
                    request.setAttribute("ratesFromGetRates",ratesFromFreightShop);        // Added for Get Rates
//                     aascShipmentOrderInfo.setFsRates((String)request.getAttribute("ratesFromGetRates"));
                    request.setAttribute("key", key);
                    session.setAttribute("AascShipmentOrderInfo", 
                                         aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session
                } // close of ship save try block
                catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());
                    aascShipmentHeaderInfo.setShipFlag("N");
                    key = "aasc226";
                    request.setAttribute("key", key);
                    e.printStackTrace(); 
                } //closing the catch block
            } // close of ship save if block
            if (submitButton.equalsIgnoreCase("Print Label")) //checking whether the submit button value is equal to printLabel 
            {
               print = "Y";
            } //end of if block
            else {
                print = "";
            } //end of else block


            
            if (print.equalsIgnoreCase("Y")) {
                try {
                    logger.info("inside the loop if print is Y");
                    

                    String wshPrinterName = "";
                    
                    int locationID = 0;
                    int userID = 0;
                    
                    try {
                        locationID =Integer.parseInt(((Integer)session.getAttribute("locationId")).toString());
                    } catch (Exception e) {
                        logger.severe("Exception while getting  location id" + 
                                    e.getMessage());
                    }
                    try {
                        userID =Integer.parseInt((String)session.getAttribute("user_id"));
                    } catch (Exception e) {
                        logger.severe("Exception while getting  user id" +e.getMessage());
                    }
                    String op900PrinterName = "";
                    try {
                    } catch (Exception e) {
                        logger.severe("Exception while getting wshPrinterName" + e);
                    }
                    try {
                       
                    } catch (Exception e) {
                        logger.severe("Exception while getting wshPrinterName" +e.getMessage());
                    }
                    
                    aascShipmentHeaderInfo.setShipFlag("Y");
                    manualTrackingFlag =nullStrToSpc((String)session.getAttribute("ManualTrackingFlag"));
                    if (manualTrackingFlag.equalsIgnoreCase("Y"))
                        aascShipmentHeaderInfo.setManualTrackingFlag("Y");
                    String ciFlag = "";
                    String shipMethodMeaning = 
                        aascShipmentHeaderInfo.getShipMethodMeaning();
                    int carrierId = 
                        aascShipMethodInfo.getCarrierId(shipMethodMeaning);
                    carrierCode = 
                        aascShipMethodInfo.getCarrierCode(carrierId);
                    try {
                        if ("Y".equalsIgnoreCase(intlFlag)) {
                            if (carrierCode == 110 || carrierCode == 111) {
                                ciFlag = 
                                        aascIntlInfo.getIntlHeaderInfo().getGenerateCI();
                            } else if (carrierCode == 100) {
                                ciFlag = 
                                        aascIntlInfo.getIntlHeaderInfo().getCommercialInv();
                            }
                        }
                    } catch (Exception e) {
                           e.printStackTrace();
                    }
                    returnStatus = 
                            aascShipmentOrderDelegate.printlabel(aascShipmentOrderInfo, aascProfileOptionsInfo, 
                                       aascShipMethodInfo, wshPrinterName, 
                                       intlFlag, op900PrinterName, 
                                       ciFlag); // calling the printlabel method
                    
                    if (returnStatus == 
                        125) // checking whether the printStatus is 125 or not
                    {
                        key = "aasc227"; // successful printing.

                        
                        if (intlFlag.equalsIgnoreCase("Y")) {
                            
                            

//                            if (userIdstr3 != null) {
//                                userIdint = Integer.parseInt(userIdstr3);
//                            } else {
//                                
//                            }
                        }
                    } //closing the if block
                    else {
                        key = "aasc126";
                        
                    } //closing the else block
                    request.setAttribute("key", key);
                    session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo); // setting the object of the AascShipmentOrderInfo class to the session
                    return "success";
                } catch (Exception e) {
                    if (key.equalsIgnoreCase("aasc120"))
                        key = "aasc202";
                    else
                        key = "aasc126";

                    logger.severe("Exception::"+e.getMessage());

                    request.setAttribute("key", key);
                    session.setAttribute("AascShipmentOrderInfo", 
                                         aascShipmentOrderInfo); // setting the object of the AascShipmentOrderInfo class to the session
                    return "success";
                }
            } //close of print label
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            key = "aasc122";
            request.setAttribute("key", key);
        } //closing the catch block
        return "success";
    } //closing the execute method

    

   

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
    
//Shiva added below method
 public static String getKeyFromValue(Map hm, Object value) {
     for (Object o : hm.keySet()) {
       if (hm.get(o).equals(value)) {
         return o.toString();
       }
     }
     return "";
   }

}//closing the class ShipmentOrderShipSaveAction
