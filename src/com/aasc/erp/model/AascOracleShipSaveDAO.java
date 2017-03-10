 /*
  * @(#)AascOracleShipSaveDAO.java        
  * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
  * All rights reserved.
  * @author 
  * @version 1
  * @since Modified by Suman G
  */
 /*========================================================================================
 Date        Resource       Change history
 ------------------------------------------------------------------------------------------
 12/12/2014  Suman Gunda    Modified for saving shipped data
 15/12/2014  Y Pradeep      Modified alls methods from basic JDBC call's to Spring JDBC call's
 17/12/2014  Eshwari M      Removed unnecessary Code
 19/12/2014  Y Pradeep      Modified nullStrToSpc method and added try, catch to all methods.
 02/01/2015  Y Pradeep      Added code to save ship_to_location_name into shipment headers table.
 19/01/2015  Suman G        Removed unncessary comments in history and code and removed finally block.
 02/02/2015  Eshwari M      Added code related to transitTime and delivert date for Carrier SLA Report.
10/03/2015   Y Pradeep      Added code to save Address Line 3, emailId for ship to and ship from and resediential flag.
10/03/2015   Sunanda K      Added code to save the New ShipTo address created in Shipping page.
11/05/2015   Y Pradeep      Modified code to save void flag in package table after doing void and again shipping. Bug #2916.
27/05/2015   Suman G        Added code to fix #2936
03/06/2015   Suman G        Added code to fix #2955
09/06/2015   Suman G        Added code to fix #2972    
13/07/2015   Y Pradeep      Removed unused code and added code related to save label format for that order in headers tabel.
30/10/2015   Shiva G        Added additionalInfo for DHL
01/11/2015   Mahesh V       Added code for Stamps.com Integration
10/11/2015   Suman G        Added code to fix #3889
10/11/2015   Mahesh V       Added code for FedEx Recepient Development
18/12/2015   Y Pradeep      Modified code to save TimeStamp for shipmentdate field. Bug #4095.
 ========================================================================================*/


package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;
import javax.servlet.http.HttpSession;

 
 import java.sql.Connection;
 import java.sql.Date;

import java.sql.Timestamp;
import java.sql.Types;

 import java.util.Iterator;
 import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;



 /**
   AascOracleShipSaveDAO class is used to get the Shipment related header and package information
   from AascShipmentHeaderInfo and AascShipmentPackageInfo class and saving those details to the database.
  */
 public class
  AascOracleShipSaveDAO implements AascShipSaveDAO {

     private int userId; // unique identifier for the user 
     private int returnStatus; // holds the opStatus value  of the procedure
     private String errorStatus; // holds the errorStatus value  of the procedure
     private String customerName; // holds the value of the ship to customer name    
     private String shipToLocationName; // holds the value of the ship to location name    
     private String shipToAddrLine1 = ""; // holds the value of ship to address
     private String shipToAddrLine2 = "";
     private String shipToAddrLine3 = "";
     private String shipToEmailId ="";
     private String address1; // holds the value of ship to address
     private String ShipToAddrLine2;
     private String city; // holds the value of ship to city 
     private String state; // holds the value of ship to state
     private String countryCode; // holds the value of ship to country symbol
     private String postalCode; // holds the value of ship to postal code
     private String shipperName; // holds the value of the shipperName location from where the shipment starts
     private String reference1; // holds the value of the customer purchase order number
     private String reference2; // holds the value of the  order number
     private String additonalInfo;
     private String wayBill; // holds the value of the tracking number  
     private double shipmentCost; // holds the value of the cost for the shipment
     private String shipMethod; // holds the value of the ship method used by the customer to ship the delivery
     private Date shipmentDate; // holds the value of the date(sysdate) on which the delivery needs to be shipped
     private java.sql.Timestamp shipTimeStamp;
     private String carrierPayCode; // holds  the value of the carrier pay code 
     private String carrierAccountNumber; // holds the value carrier account number 
      private String maskCarrierAccountNumber; // holds the value mask carrier account number 
     private String contactName; // holds the value of the contact name related to ship method
     private String phoneNumber; // holds the value of the phone number of the customer
     private int createdBy; // holds the vlaue of the created by number 
     private int carrierId; // holds the value of the carrierId
     private String voidFlag; // holds the value of the voidFlag "Y" or "N" which indicates whether the shipment is void or not
     private String printLabelFlag; // holds the value of the printLabelFlag "Y" or "N" which indicates whether the labels printed or not
     private String manualTrackingFlag; // holds the value of the manualTrackingFlag which inidcates whether the tracking number entered manually or not
     private String shipMethodMeaning; // holds the value of the shipMethodMeaning
     private String saturdayShipFlag; // holds the value of the saturdayShipFlag
     private String tpCompanyName; // holds the value of the third party company name
     private String tpCity; // holds the value of the third party city name
     private String tpState; // holds the value of the third party state name
     private String tpPostalCode; // holds the value of the third party postal code
     private String tpAddress; // holds the value of the third party address
     private String tpCountrySymbol; // holds the value of the third party country name

     private String orderNumber; // holds the value of the order number. sequence number to identify shipment shipment
     private int shipmentId;
     private int packageNumber; // holds the value of the package number.sequence number generated for each package for a particular order
     private String itemNumber; // holds the value of the inventory item Number with respect to item
     private String itemDescription; // holds the description of the item
     private float shippedQuantity; // holds the value of the Quantity that needs to be shipped 
     private String unitOfMeasure; // holds the value of the Unit of measure of the package to be shipped        
     private float weight; // holds the value of the weight of the package
     private String dimensionUnits = ""; // holds the value of the dimensionUnit of package
     private int msn; // holds the value of the msn number
     private int locationId = 0 ; // holds the value of the ship from organisation name
     private String location = "" ;
     private String shipFromAddressLine1 = ""; // holds the value of the ship from address
     private String shipFromAddressLine2 = ""; // holds the value of the ship from address
     private String shipFromAddressLine3 = ""; // holds the value of the ship from address
     private String shipFromEmailId =""; 
     private String shipFromCity = ""; // holds the value of the ship from city name
     private String shipFromState =""; // holds the value of the ship from state name    
     private String shipFromCountry = ""; // holds the value of the ship from country name
     private String dropOfType = ""; // holds the value of the drop of type
     private String packaging = ""; // holds the value of the way the item is pack
     private String shipFromPostalCode = ""; // holds the value of the ship from postal code
     private String shipFromPhoneNumber1 =""; // holds the value of the ship from phone number
     private String shipFromPhoneNumber2 = ""; // holds the value of the ship from phone number
     private String shipFromcompanyName = "";    // holdsd the value for ship from company name
     private String importFlag="N";
     private int opStatus;
     protected HttpServletRequest request;

     private String department = ""; // added by gayaz@007
     private double totalSurcharge; // added by gayaz@007
     private double freightCost; // added by gayaz@007

     private String codFlag = "";
     private float codAmt = 0;
     private double packageDeclaredValue = 0;
     private String signatureOptions = "";
     private String returnShipment = "";
     private double PackageSurcharge = 0;
     private double PackageShipmentCost = 0;
     private double pkgDiscountNetCharge;
     private double pkgListNetCharge;
     private int packageLength;
     private int packageWidth;
     private int packageHeight;

     private String Packpackaging = "";
     private String codCode = "";
     private String codFundsCode = "";
     private String codCurrCode = "";
     private String declaredCurrCode = "";

     private AascShipmentOrderInfo aascShipmentOrderInfo; // holds the object of the AascShipmentOrderInfo class   
     private AascShipmentHeaderInfo aascShipmentHeaderInfo; // holds the object of the AascShipmentHeaderInfo class   
     private AascShipmentPackageInfo aascShipmentPackageInfo; // holds the object of the AascShipmentPackageInfo class
     private LinkedList packageList; // holds the object of the Linked List 
     private AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
     SimpleJdbcCall simpleJdbcCall;
     private Connection connection = null; // holds the object of the oracle connection
     
     private static Logger logger = AascLogger.getLogger("AascOracleShipSaveDAO"); // this is a static method of the AaascLogger class which returns the object of the Logger class

     private String trackingNumber; // holds the value return by getTrackingNumber() method
     private int packageId = 1; // holds the value of the packageId which starts by 1

     boolean checkConnection = true;

     private String dimension; // holds the value return by getDimension() method
     
     private double intlQty; // holds the value return by getIntlQuantity() method
     private String intlUnitUom = ""; // holds the value return by getIntlQUOM() method
     private double intlUnitWeight; // holds the value return by getIntlUnitWeight() method
     private double intlUnitValue; // holds the value return by getIntlUnitValue() method
     private String intlProductDescription = ""; // holds the value return by getIntlProductDescription() method
     
     private double packageShipmentCost;
     private String upsPkging = "";
     private String codType = "";
     private String acctPostalCode = "";
     private String carrierName = "" ;
     private String rtnShipMethod = "";
     private String rtnDesc = "";
     private String largePackage = "";
     private String additionalHandling = "";
     private String rtnDropOfType = "";
     private String rtnPackageList = "";
     private String rtnPayMethod = "";
     private String rtnACNumber = "";
     private String rtnRMA = "";
     private String rtnTrackingNumber = "";
     private double rtnShipmentCost = 0;
     private double rtnDeclaredValue = 0.0;
     private double baseCharge = 0.0;
     private double totalDiscount = 0.0;
     private double rtnBaseCharge = 0.0;
     private double rtnTotalDiscount = 0.0;
     AascShipMethodInfo shipMethodInfo;

     private String halFlag = "";
     private String halPhone = "";
     private String halLine1 = "";
     private String halLine2 = "";
     private String halCity = "";
     private String halState = "";
     private String halZip = "";

     private String HazMatFlag = "";
     private String HazMatType = "";
     private String HazMatClass = "";
     private String HazMatLabel = "";
     private double HazMatCharges = 0.0;

     private double HazMatQuantity = 0.0;
     private String HazMatUnit = "";

     private String HazMatIdentificationNo = "";
     private String HazMatDOTLabelType = "";
     private String HazMatEmergencyContactNo = "";
     private String HazMatEmergencyContactName = "";
     private String HazardousMaterialPkgGroup = "";
     private String HazardousMaterialId = "";

     private double HazmatPkgingCnt = 0.0;
     private String HazmatPkgingUnits = "";
     private String hazmatTechnicalName = "";
     private String hazmatSignatureName = "";
     private String hazmatPackInstructions = "";
     private int dimId = 0;
     private int userId1=0;
     
     private String transitTime=""; // Added this field for Carrier SLA Report enhancement
     private Date deliveryDate; // Added this field for Carrier SLA Report enhancement
      private String residentialFlag = "";
      
     private String avFlag = "";
     private String fsFlag = "";
     
     
     private String dryIceFlag = "";
     private double dryIceWeight = 0.0;
     private String dryIceUnits = "";
    
     private String stampsTxId = "";

     private String labelFormat = "" ;
     
     private String shipFlagFromDB="";
     private String rcPostalCode = "";
     private String rcCompanyName = "";
     /**
      * Default constructor.
      */
     public AascOracleShipSaveDAO() {
     } //closing the constructor 

     /**
      * This method calls saveHeaderInfo() which saves the header information to the database and returns 
      * a headerStatus.If headerStatus is equal to 225 then it calls savePackageInfo() method for saving 
      * package information.If the header information is saved and if package information not saved then 
      * it calls deleteHeaderInfo() method which delete header information.
      * @param userId of type int
      * @param aascShipmentOrderInfo of type AascShipmentOrderInfo
      * @return returnStatus of type int 
      */
     public int getShipSaveDAO(int userId, 
                                    AascShipmentOrderInfo aascShipmentOrderInfo,int clientID, int carrierSuccessStatus) {
         try {
             logger.info("Entered getShipSaveDAO()");
             this.userId = userId; // assigning the userId argument to the instance variable
             this.aascShipmentOrderInfo = aascShipmentOrderInfo; // assignimg aascShipmentOrderInfo to the  instance variable
             aascShipmentHeaderInfo = aascShipmentOrderInfo.getShipmentHeaderInfo(); // calling the getShipmentHeaderInfo method which returns the AaascShipmentHeaderInfo class object
             this.packageList = aascShipmentOrderInfo.getShipmentPackageInfo(); // calling the getShipmentPackageInfo method which returns the getShipmentPackageInfo class objects as a LinkedList
             orderNumber=aascShipmentHeaderInfo.getOrderNumber();
             returnStatus = saveHeaderInfo(clientID, carrierSuccessStatus, userId); // calling the saveHeaderInfo method which returns the headerstatus value as 225
             if (returnStatus == 225) {
                 returnStatus = savePackageInfo(clientID, carrierSuccessStatus); // calling the savePackageInfo method which returns the packageStatus value as 225
                 if (returnStatus == 225) {
                     opStatus=returnStatus;
                     int carrierId = aascShipmentHeaderInfo.getCarrierId();
                     if ((carrierId == 110) || (carrierId == 111)) {
                         opStatus=updateShipmentTotals(clientID);
                     }
                     int shipmentId = aascShipmentHeaderInfo.getShipmentId();
                     if (aascShipmentHeaderInfo.getInternationalFlag().equalsIgnoreCase("Y")) {
                         String DocumentID = aascShipmentHeaderInfo.getDocumentID();
                         opStatus = 
                                 updateIntlShipmentIds(orderNumber, shipmentId, clientID,DocumentID);
                         if (opStatus == 122) {
                             opStatus = 
                                     deleteHeaderInfo(clientID); // calling the deleteHeaderInfo() method
                         } else if (opStatus == 120) {
                         }
                     }


                 } //end of innner if block
                 else {
                     returnStatus = 
                             deleteHeaderInfo(clientID); // calling the deleteHeaderInfo() method  which deletes the header and package information                      
                 } //end of inner else block

             } //end of outer if block
             else {
                 logger.info("header details are not saved");
             } //end of outer else block

         } catch (Exception e) {
             logger.severe("exception in saving shipment details" + 
                           e.getMessage());

         } 
         

         logger.info("Exit from getShipSaveDAO()");
         return returnStatus;
     } // End of saveShipmentOrderDetails() method

     /**
      * Method saveHeaderInfo is used to get the data from AascShipmentHeaderInfo class getxxx() methods and 
      * saves the database through the procedure save_shipment_order_details.
      * @return returnStatus of type int.
      */
     int saveHeaderInfo(int clientID, int carrierSuccessStatus, int userId ) {
        logger.info("Entered saveHeaderInfo");
        customerName = nullStrToSpc(aascShipmentHeaderInfo.getCustomerName());
        logger.info("customer name got from the aascShipmentHeaderInfo= "+customerName);
        shipToLocationName = nullStrToSpc(aascShipmentHeaderInfo.getShipToLocationName());
        shipToAddrLine1 = nullStrToSpc(aascShipmentHeaderInfo.getAddress());
        shipToAddrLine2 = nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine2());
        shipToAddrLine3 = nullStrToSpc(aascShipmentHeaderInfo.getShipToAddrLine3());
        shipToEmailId = nullStrToSpc(aascShipmentHeaderInfo.getShipToEmailId());
        userId1=userId;
        city = nullStrToSpc(aascShipmentHeaderInfo.getCity());
        state = nullStrToSpc(aascShipmentHeaderInfo.getState());
        countryCode = nullStrToSpc(aascShipmentHeaderInfo.getCountrySymbol());
        postalCode = nullStrToSpc(aascShipmentHeaderInfo.getPostalCode());
        shipperName = nullStrToSpc(aascShipmentHeaderInfo.getShipFromContactName());
        reference1 = nullStrToSpc(aascShipmentHeaderInfo.getReference1());
        reference2 = nullStrToSpc(aascShipmentHeaderInfo.getReference2());
        additonalInfo= nullStrToSpc(aascShipmentHeaderInfo.getShipAddInfo());
        wayBill = nullStrToSpc(aascShipmentHeaderInfo.getWayBill());
        shipmentCost = aascShipmentHeaderInfo.getShipmentCost();
        shipMethod = nullStrToSpc(aascShipmentHeaderInfo.getShippingMethod());
        shipmentDate = aascShipmentHeaderInfo.getShipmentDate();
         shipTimeStamp = aascShipmentHeaderInfo.getShipTimeStamp();
         logger.info("shipTimeStamp in dao ==========  "+shipTimeStamp);
        carrierPayCode = nullStrToSpc(aascShipmentHeaderInfo.getCarrierPaymentMethod());
        carrierAccountNumber = nullStrToSpc(aascShipmentHeaderInfo.getCarrierAccountNumber());
         maskCarrierAccountNumber = nullStrToSpc(aascShipmentHeaderInfo.getMaskCarrierAccountNumber()); // vikas added MaskCarrierAccountNumber to get MaskCarrierAccountNumber
        contactName = nullStrToSpc(aascShipmentHeaderInfo.getContactName());
        phoneNumber = nullStrToSpc(aascShipmentHeaderInfo.getPhoneNumber());
        createdBy = aascShipmentHeaderInfo.getCreatedBy();
        carrierId = aascShipmentHeaderInfo.getCarrierId();
        voidFlag = nullStrToSpc(aascShipmentHeaderInfo.getVoidFlag());
        printLabelFlag = nullStrToSpc(aascShipmentHeaderInfo.getPrintLabelFlag());
        manualTrackingFlag = nullStrToSpc(aascShipmentHeaderInfo.getManualTrackingFlag());
        shipMethodMeaning = nullStrToSpc(aascShipmentHeaderInfo.getShipMethodMeaning());
        saturdayShipFlag = nullStrToSpc(aascShipmentHeaderInfo.getSaturdayShipFlag());
        tpCompanyName = nullStrToSpc(aascShipmentHeaderInfo.getTpCompanyName());
        tpCity = nullStrToSpc(aascShipmentHeaderInfo.getTpCity());
        tpState = nullStrToSpc(aascShipmentHeaderInfo.getTpState());
        tpPostalCode = nullStrToSpc(aascShipmentHeaderInfo.getTpPostalCode());
        tpAddress = nullStrToSpc(aascShipmentHeaderInfo.getTpAddress());
         tpCountrySymbol = nullStrToSpc(aascShipmentHeaderInfo.getTpCountrySymbol());        

        locationId = Integer.parseInt(nullStrToSpc(aascShipmentHeaderInfo.getLocationId()));
        location = nullStrToSpc(aascShipmentHeaderInfo.getShipFromLocation());
        shipFromAddressLine1 = nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine1());
        shipFromAddressLine2 = nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine2());
        shipFromAddressLine3 = nullStrToSpc(aascShipmentHeaderInfo.getShipFromAddressLine3());
        shipFromEmailId = nullStrToSpc(aascShipmentHeaderInfo.getShipFromEmailId());
        shipFromCity = nullStrToSpc(aascShipmentHeaderInfo.getShipFromCity());
        shipFromState = nullStrToSpc(aascShipmentHeaderInfo.getShipFromState());
        shipFromCountry = nullStrToSpc(aascShipmentHeaderInfo.getShipFromCountry());
        dropOfType = nullStrToSpc(aascShipmentHeaderInfo.getDropOfType());
        packaging = nullStrToSpc(aascShipmentHeaderInfo.getPackaging());
        shipFromPostalCode = nullStrToSpc(aascShipmentHeaderInfo.getShipFromPostalCode());
        shipFromPhoneNumber1 = nullStrToSpc(aascShipmentHeaderInfo.getShipFromPhoneNumber1());
        shipFromPhoneNumber2 = nullStrToSpc(aascShipmentHeaderInfo.getShipFromPhoneNumber2());
         shipFromcompanyName = nullStrToSpc(aascShipmentHeaderInfo.getShipFromCompanyName());
         importFlag=nullStrToSpc(aascShipmentHeaderInfo.getImportFlag());
         
         rcCompanyName = nullStrToSpc(aascShipmentHeaderInfo.getRecCompanyName());
         rcPostalCode = nullStrToSpc(aascShipmentHeaderInfo.getRecPostalCode());
         

        department = aascShipmentHeaderInfo.getDepartment();
        totalSurcharge = aascShipmentHeaderInfo.getTotalSurcharge();
        freightCost = aascShipmentHeaderInfo.getFreightCost(); 
        orderNumber = aascShipmentHeaderInfo.getOrderNumber(); 
         
        acctPostalCode = aascShipmentHeaderInfo.getAcctPostalCode();
        carrierName = aascShipmentHeaderInfo.getCarrierName();
        
        String carrierServiceLevel = aascShipmentHeaderInfo.getCarrierServiceLevel();
        String upsServiceLevelCode = aascShipmentHeaderInfo.getUpsServiceLevelCode();
        double pkgWeight = aascShipmentHeaderInfo.getPackageWeight() ;
        residentialFlag = aascShipmentHeaderInfo.getResidentialFlag();
        
        avFlag =  aascShipmentHeaderInfo.getAvFlag();
        fsFlag = aascShipmentHeaderInfo.getFsFlag();
         
        labelFormat  = aascShipmentHeaderInfo.getLabelFormat();  
        logger.info("labelFormat : "+labelFormat);
        try{   
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
             
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("save_shipment_order_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_shipment_id",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_ship_flag",Types.VARCHAR));
                                                            
             //input parameters assignment
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_customer_name", customerName)
                                                                        .addValue("ip_ship_to_location_name", shipToLocationName)
                                                                        .addValue("ip_user_id",userId1)
                                                                        .addValue("ip_address", shipToAddrLine1)
                                                                        .addValue("ip_address2", shipToAddrLine2)
                                                                        .addValue("ip_address3", shipToAddrLine3)
                                                                        .addValue("ip_email_id", shipToEmailId)
                                                                        .addValue("ip_city", city)
                                                                        .addValue("ip_state", state)
                                                                        .addValue("ip_country_code", countryCode)
                                                                        .addValue("ip_postal_code", postalCode)
                                                                        .addValue("ip_shipper_name", shipperName)
                                                                        .addValue("ip_reference1", reference1)
                                                                        .addValue("ip_reference2", reference2)
                                                                        .addValue("ip_tracking_number", wayBill)
                                                                        .addValue("ip_shipment_cost", shipmentCost)
                                                                        .addValue("ip_ship_method", shipMethod)
                                                                        .addValue("ip_shipment_date", shipTimeStamp, Types.TIMESTAMP)
                                                                        .addValue("ip_carrier_pay_code", carrierPayCode)
                                                                        .addValue("ip_carrier_account_number", carrierAccountNumber)
                                                                        .addValue("ip_contact_name", contactName)
                                                                        .addValue("ip_phone_number", phoneNumber)
                                                                        .addValue("ip_carrier_id", carrierId)
                                                                        .addValue("ip_void_flag", voidFlag)
                                                                        .addValue("ip_print_label_flag", printLabelFlag)
                                                                        .addValue("ip_manual_tracking_flag", manualTrackingFlag)
                                                                        .addValue("ip_ship_method_meaning", shipMethodMeaning)
                                                                        .addValue("ip_saturday_shipment_flag", saturdayShipFlag)
                                                                        .addValue("ip_tp_company_name", tpCompanyName)
                                                                        .addValue("ip_tp_address", tpAddress)
                                                                        .addValue("ip_tp_city", tpCity)
                                                                        .addValue("ip_tp_state", tpState)
                                                                        .addValue("ip_tp_postal_code", tpPostalCode)
                                                                        .addValue("ip_tp_country_symbol", tpCountrySymbol)
                                                                        .addValue("ip_ship_from_location", location)
                                                                        .addValue("ip_ship_from_address_line1", shipFromAddressLine1)
                                                                        .addValue("ip_ship_from_address_line2", shipFromAddressLine2)
                                                                        .addValue("ip_ship_from_address_line3", shipFromAddressLine3)
                                                                        .addValue("ip_ship_from_town_or_city", shipFromCity)
                                                                        .addValue("ip_ship_from_state", shipFromState)
                                                                        .addValue("ip_ship_from_postal_code", shipFromPostalCode)
                                                                        .addValue("ip_ship_from_country", shipFromCountry)
                                                                        .addValue("ip_ship_from_phone_number1", shipFromPhoneNumber1)
                                                                        .addValue("ip_ship_from_phone_number2", shipFromPhoneNumber2)
                                                                        .addValue("ip_ship_from_email_id", shipFromEmailId)                                                                        
                                                                        .addValue("ip_dropofftype", dropOfType)
                                                                        .addValue("ip_packaging", packaging)
                                                                        .addValue("ip_created_by", createdBy)
                                                                        .addValue("ip_department", department)
                                                                        .addValue("ip_freight_charges", freightCost)
                                                                        .addValue("ip_total_surcharges", totalSurcharge)
                                                                        .addValue("ip_order_number", orderNumber)
                                                                        .addValue("ip_tp_acct_postal_code", acctPostalCode)
                                                                        .addValue("ip_client_id", clientID)
                                                                        .addValue("ip_location_id", locationId) 
                                                                        .addValue("ip_carrier_name", carrierName)
                                                                        .addValue("ip_ship_from_company_name",shipFromcompanyName)
                                                                        .addValue("ip_import_flag",importFlag)
                                                                        .addValue("ip_carrier_service_level", carrierServiceLevel)
                                                                        .addValue("ip_carrier_service_level_code", upsServiceLevelCode)
                                                                        .addValue("ip_package_weight" , pkgWeight)
                                                                        .addValue("ip_residential_flag" , residentialFlag)
                                                                        .addValue("ip_carrier_success_status",carrierSuccessStatus)
                                                                        .addValue("ip_av_flag",avFlag)
                                                                        .addValue("ip_fs_flag",fsFlag)
                                                                        .addValue("ip_label_format",labelFormat)
                                                                        .addValue("ip_ship_add_info",additonalInfo)
                                                                        
                                                                        .addValue("ip_REC_COMPANY_NAME",rcCompanyName)
                                                                        .addValue("ip_REC_POSTAL_CODE",rcPostalCode)
                                                                        .addValue("ip_mask_carrier_account_number", maskCarrierAccountNumber) // vikas added code to save maskaccountnumber into DB
;
                                                                        // end of input params assignment
                                                                          
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus =  String.valueOf(out.get("op_error_status"));
            shipmentId = Integer.parseInt(String.valueOf(out.get("op_shipment_id")));
            shipFlagFromDB = String.valueOf(out.get("op_ship_flag"));
  //          System.out.println("shipFlagFromDB:::::: from header:::::::::::"+shipFlagFromDB);
            aascShipmentHeaderInfo.setShipmentId(shipmentId);
            aascShipmentHeaderInfo.setOrderNumber(orderNumber); // setting the orderNumber to the aascShipmentHeaderInfo.setOrderNumber(orderNumber) method
            
            logger.info("errorStatus : "+errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from saveHeaderInfo");
         
        return opStatus;
     } // End of saveHeaderInfo() method.

     /**
      * Method savePackageInfo is used to get the Package data from AascShipmentPackageInfo class getxxx() methods
      * and saves to the database through the procedure save_shipment_order_details
      * @return returnStatus of type int.
      */
     int savePackageInfo(int clientID,int carrierSuccessStatus) {
        logger.info("Entered savePackageInfo()");
        Iterator packageIterator = packageList.iterator();
        try{ 
            while (packageIterator.hasNext()) {


                aascShipmentPackageInfo = (AascShipmentPackageInfo)packageIterator.next();
                packageNumber = aascShipmentPackageInfo.getPackageNumber();
                itemDescription = aascShipmentPackageInfo.getItemNumber();
                shippedQuantity = aascShipmentPackageInfo.getShippedQuantity();
                unitOfMeasure = aascShipmentPackageInfo.getUom();
                weight = aascShipmentPackageInfo.getWeight();
                trackingNumber = aascShipmentPackageInfo.getTrackingNumber(); // returns the value of trackingNumber 
                dimension = aascShipmentPackageInfo.getDimensions();
                dimensionUnits = aascShipmentPackageInfo.getDimensionUnits();
                msn = aascShipmentPackageInfo.getMsn();
                stampsTxId = aascShipmentPackageInfo.getStampsTaxId();

                codFlag = aascShipmentPackageInfo.getCodFlag();
                if (codFlag ==null || "null".equalsIgnoreCase(codFlag))
                    codFlag = "";
                codAmt = aascShipmentPackageInfo.getCodAmt();
                packageDeclaredValue = aascShipmentPackageInfo.getPackageDeclaredValue();
                signatureOptions = aascShipmentPackageInfo.getSignatureOptions();
                returnShipment = aascShipmentPackageInfo.getReturnShipment();
                PackageSurcharge = aascShipmentPackageInfo.getSurCharges();
                PackageShipmentCost = aascShipmentPackageInfo.getPkgCost();
                pkgDiscountNetCharge = aascShipmentPackageInfo.getPkgDiscountNetCharge();
                pkgListNetCharge = aascShipmentPackageInfo.getPkgListNetCharge();
                packageLength = aascShipmentPackageInfo.getPackageLength();
                packageWidth = aascShipmentPackageInfo.getPackageWidth();
                packageHeight = aascShipmentPackageInfo.getPackageHeight();
                Packpackaging = aascShipmentPackageInfo.getPackaging();
                codCode = aascShipmentPackageInfo.getCodCode();
                codFundsCode = aascShipmentPackageInfo.getCodFundsCode();
                codCurrCode = aascShipmentPackageInfo.getCodCurrCode();
                declaredCurrCode = aascShipmentPackageInfo.getDeclaredCurrCode();

                packageDeclaredValue = aascShipmentPackageInfo.getPackageDeclaredValue();
                signatureOptions = aascShipmentPackageInfo.getSignatureOptions();
                returnShipment = aascShipmentPackageInfo.getReturnShipment();
                packageLength = aascShipmentPackageInfo.getPackageLength();
                packageWidth = aascShipmentPackageInfo.getPackageWidth();
                packageHeight = aascShipmentPackageInfo.getPackageHeight();
                dimId = aascShipmentPackageInfo.getDimensionId();
                packageShipmentCost = aascShipmentPackageInfo.getPkgCost();
                upsPkging = aascShipmentPackageInfo.getPackaging();
                codType = aascShipmentPackageInfo.getCodCode();
                codFundsCode = aascShipmentPackageInfo.getCodFundsCode();
                codCurrCode = aascShipmentPackageInfo.getCodCurrCode();
                declaredCurrCode = aascShipmentPackageInfo.getDeclaredCurrCode();
                rtnShipMethod = aascShipmentPackageInfo.getRtnShipMethod();
                rtnDesc = aascShipmentPackageInfo.getRtnDesc();
                additionalHandling = aascShipmentPackageInfo.getAdditionalHandling();
                largePackage = aascShipmentPackageInfo.getLargePackage();
                rtnDropOfType = aascShipmentPackageInfo.getRtnDropOfType();
                rtnPackageList = aascShipmentPackageInfo.getRtnPackageList();
                rtnPayMethod = aascShipmentPackageInfo.getRtnPayMethod();
                rtnACNumber = aascShipmentPackageInfo.getRtnACNumber();
                rtnRMA = aascShipmentPackageInfo.getRtnRMA();
                rtnTrackingNumber = aascShipmentPackageInfo.getRtnTrackingNumber();
                baseCharge = aascShipmentPackageInfo.getBaseCharge();
                totalDiscount = aascShipmentPackageInfo.getTotalDiscount();
                rtnBaseCharge = aascShipmentPackageInfo.getRtnBaseChrge();
                rtnTotalDiscount = aascShipmentPackageInfo.getRtnTotalDiscount();
                rtnTotalDiscount = 0.0;
                
                dryIceFlag = aascShipmentPackageInfo.getDryIceChk();
                if ("null".equalsIgnoreCase(dryIceFlag) || "N".equalsIgnoreCase(dryIceFlag)){
                    dryIceFlag = "";
                    dryIceWeight = 0.0;
                    dryIceUnits = "";
                }
                else{
                    try{    
                        dryIceWeight = aascShipmentPackageInfo.getDryIceWeight();
                        logger.info("dryIceWeight::::"+dryIceWeight);
                    }catch(Exception e){
                        dryIceWeight = 0.0;
                    }
                    dryIceUnits = aascShipmentPackageInfo.getDryIceUnits();
                    if (dryIceUnits ==null ||"null".equalsIgnoreCase(dryIceUnits))
                        dryIceUnits = "";
                    
                }
                
                try {
                    rtnShipmentCost = aascShipmentPackageInfo.getRtnShipmentCost();
                } catch (Exception e) {
                    rtnShipmentCost = 0;
                }

                try {
                    rtnDeclaredValue = aascShipmentPackageInfo.getRtnDeclaredValue();
                } catch (Exception e) {
                    rtnDeclaredValue = 0;
                }

                halFlag = aascShipmentPackageInfo.getHalFlag();
                if (halFlag == null || "null".equalsIgnoreCase(halFlag))
                    halFlag = "";

                halPhone = aascShipmentPackageInfo.getHalPhone();
                if (halPhone == null || "null".equalsIgnoreCase(halPhone))
                    halPhone = "";

                halLine1 = aascShipmentPackageInfo.getHalLine1();
                if (halLine1 == null || "null".equalsIgnoreCase(halLine1))
                    halLine1 = "";

                halLine2 = aascShipmentPackageInfo.getHalLine2();
                if (halLine2 == null || "null".equalsIgnoreCase(halLine2))
                    halLine2 = "";

                halCity = aascShipmentPackageInfo.getHalCity();
                if (halCity == null || "null".equalsIgnoreCase(halCity))
                    halCity = "";

                halState = aascShipmentPackageInfo.getHalStateOrProvince();
                if (halState == null || "null".equalsIgnoreCase(halState))
                    halState = "";

                halZip = aascShipmentPackageInfo.getHalPostalCode();
                if (halZip == null || "null".equalsIgnoreCase(halZip))
                    halZip = "";

                HazMatFlag = aascShipmentPackageInfo.getHazMatFlag();
                if (HazMatFlag == null || "null".equalsIgnoreCase(HazMatFlag))
                    HazMatFlag = "";

                HazMatType = aascShipmentPackageInfo.getHazMatType();
                if (HazMatType == null || "null".equalsIgnoreCase(HazMatType))
                    HazMatType = "";

                HazMatClass = aascShipmentPackageInfo.getHazMatClass();
                if (HazMatClass == null || "null".equalsIgnoreCase(HazMatClass))
                    HazMatClass = "";

                HazMatCharges = aascShipmentPackageInfo.getHazMatCharges();
                HazMatQuantity = aascShipmentPackageInfo.getHazMatQty();

                HazMatUnit = aascShipmentPackageInfo.getHazMatUnit();
                if (HazMatUnit == null || "null".equalsIgnoreCase(HazMatUnit))
                    HazMatUnit = "";

                HazMatIdentificationNo = aascShipmentPackageInfo.getHazMatIdNo();
                if (HazMatIdentificationNo == null || "null".equalsIgnoreCase(HazMatIdentificationNo))
                    HazMatIdentificationNo = "";

                HazMatDOTLabelType = aascShipmentPackageInfo.getHazMatDOTLabel();
                if (HazMatDOTLabelType == null || "null".equalsIgnoreCase(HazMatDOTLabelType))
                    HazMatDOTLabelType = "";

                HazMatEmergencyContactNo = aascShipmentPackageInfo.getHazMatEmerContactNo();
                if (HazMatEmergencyContactNo == null || "null".equalsIgnoreCase(HazMatEmergencyContactNo))
                    HazMatEmergencyContactNo = "";

                HazMatEmergencyContactName = aascShipmentPackageInfo.getHazMatEmerContactName();
                if (HazMatEmergencyContactName == null || "null".equalsIgnoreCase(HazMatEmergencyContactName))
                    HazMatEmergencyContactName = "";

                HazardousMaterialPkgGroup = aascShipmentPackageInfo.getHazMatPkgGroup();
                if (HazardousMaterialPkgGroup == null || "null".equalsIgnoreCase(HazardousMaterialPkgGroup))
                    HazardousMaterialPkgGroup = "";

                HazardousMaterialId = aascShipmentPackageInfo.getHazMatId();
                if (HazardousMaterialId == null || "null".equalsIgnoreCase(HazardousMaterialId))
                    HazardousMaterialId = "";

                HazmatPkgingCnt = aascShipmentPackageInfo.getHazmatPkgingCnt();
                HazmatPkgingUnits = aascShipmentPackageInfo.getHazmatPkgingUnits();
                hazmatTechnicalName = aascShipmentPackageInfo.getHazmatTechnicalName();
                //Shiva modified below code as formatter was removed
                if(aascShipmentPackageInfo.getHazmatSignatureName()==null)
                hazmatSignatureName = "";
                else
                hazmatSignatureName = aascShipmentPackageInfo.getHazmatSignatureName();
                
                hazmatPackInstructions = aascShipmentPackageInfo.getHazmatPackInstructions();
                
                transitTime = aascShipmentPackageInfo.getTransitTime();
                deliveryDate = aascShipmentPackageInfo.getDeliveryDate();
                
                locationId = aascShipmentHeaderInfo.getLocationId();
                
                DataSource dataSource = connectionFactory.createDataSource();
             
                simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                               .withProcedureName("save_shipment_order_packages")
                                                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                               .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                            
             //input parameters assignment
                SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_shipment_id", shipmentId)
                                                                            .addValue("ip_pkg_number", packageId)
                                                                            .addValue("ip_item_name", itemNumber)
                                                                            .addValue("ip_item_description", itemDescription)
                                                                            .addValue("ip_shipped_qty", shippedQuantity)
                                                                            .addValue("ip_uom", unitOfMeasure)
                                                                            .addValue("ip_weight", weight)
                                                                            .addValue("ip_msn", msn)
                                                                            .addValue("ip_units", dimensionUnits)
                                                                            .addValue("ip_created_by", createdBy)
                                                                            .addValue("ip_cod_option_flag", codFlag)
                                                                            .addValue("ip_cod_amount", codAmt)
                                                                            .addValue("ip_package_declared_value", packageDeclaredValue)
                                                                            .addValue("ip_signature_option", signatureOptions)
                                                                            .addValue("ip_return_shipment_indicator", returnShipment)
                                                                            .addValue("ip_package_surcharges", PackageSurcharge)
                                                                            .addValue("ip_package_shipment_cost", PackageShipmentCost)
                                                                            .addValue("ip_package_length", packageLength)
                                                                            .addValue("ip_package_width", packageWidth)
                                                                            .addValue("ip_package_height", packageHeight)
                                                                            .addValue("ip_packaging", Packpackaging)
                                                                            .addValue("ip_cod_type", codCode)
                                                                            .addValue("ip_cod_funds_code", codFundsCode)
                                                                            .addValue("ip_cod_currency", codCurrCode)
                                                                            .addValue("ip_dec_val_currency", declaredCurrCode)
                                                                            .addValue("ip_package_base_charges", baseCharge)
                                                                            .addValue("ip_tracking_number", trackingNumber)
                                                                            .addValue("ip_dimensions", dimension)
                                                                            .addValue("ip_dimension_units", dimensionUnits)
                                                                            .addValue("ip_intl_qty", intlQty)
                                                                            .addValue("ip_intl_unit_uom", intlUnitUom)
                                                                            .addValue("ip_intl_unit_weight", intlUnitWeight)
                                                                            .addValue("ip_intl_unit_value", intlUnitValue)
                                                                            .addValue("ip_intl_product_desc", intlProductDescription)
                                                                            .addValue("ip_return_ship_method", rtnShipMethod)
                                                                            .addValue("ip_return_dropofftype", rtnDropOfType)
                                                                            .addValue("ip_return_packaging", rtnPackageList)
                                                                            .addValue("ip_return_pay_code", rtnPayMethod)
                                                                            .addValue("ip_return_acct_number", rtnACNumber)
                                                                            .addValue("ip_return_material_auth", rtnRMA)
                                                                            .addValue("ip_return_tracking_number", rtnTrackingNumber)
                                                                            .addValue("ip_return_package_cost", rtnShipmentCost)
                                                                            .addValue("ip_return_declared_value", rtnDeclaredValue)
                                                                            .addValue("ip_return_pkg_base_charges", rtnBaseCharge)
                                                                            .addValue("ip_return_pkg_discount_charges", rtnTotalDiscount)
                                                                            .addValue("ip_hold_at_location_flag", halFlag)
                                                                            .addValue("ip_hold_at_location_phone", halPhone)
                                                                            .addValue("ip_hold_at_location_line1", halLine1)
                                                                            .addValue("ip_hold_at_location_city", halCity)
                                                                            .addValue("ip_hold_at_location_state", halState)
                                                                            .addValue("ip_hold_at_location_post_code", halZip)
                                                                            .addValue("ip_hazmat_flag", HazMatFlag)
                                                                            .addValue("ip_hazmat_type", HazMatType)
                                                                            .addValue("ip_hazmat_class", HazMatClass)
                                                                            .addValue("ip_hazmat_label_type", HazMatLabel)
                                                                            .addValue("ip_hazmat_charges", HazMatCharges)
                                                                            .addValue("ip_dimension_id", dimId)
                                                                            .addValue("ip_hold_at_location_line2", halLine2)
                                                                            .addValue("ip_hazmat_qty", HazMatQuantity)
                                                                            .addValue("ip_hazmat_units", HazMatUnit)
                                                                            .addValue("ip_hazmat_identification_no", HazMatIdentificationNo)
                                                                            .addValue("ip_hazmat_emer_contact_no", HazMatEmergencyContactNo)
                                                                            .addValue("ip_hazmat_emer_contact_name", HazMatEmergencyContactName)
                                                                            .addValue("ip_hazmat_packging_group", HazardousMaterialPkgGroup)
                                                                            .addValue("ip_hazmat_dot_labels", HazMatDOTLabelType)
                                                                            .addValue("ip_hazmat_required", "N")
                                                                            .addValue("ip_hazmat_material_id", HazardousMaterialId)
                                                                            .addValue("ip_pkg_discount_net_charges", pkgDiscountNetCharge)
                                                                            .addValue("ip_pkg_list_net_charges", pkgListNetCharge)
                                                                            .addValue("ip_location_id", locationId)
                                                                            .addValue("ip_hazmat_packaging_count", HazmatPkgingCnt)
                                                                            .addValue("ip_hazmat_packaging_units", HazmatPkgingUnits)
                                                                            .addValue("ip_hazmat_tech_name", hazmatTechnicalName)
                                                                            .addValue("ip_hazmat_signature_name", hazmatSignatureName)
                                                                            .addValue("ip_transit_time", transitTime)
                                                                            .addValue("ip_expected_delivery_date", deliveryDate)
                                                                            .addValue("ip_client_id", clientID)
                                                                            .addValue("ip_carrier_success_status",carrierSuccessStatus)
                                                                            .addValue("ip_void_package_flag", voidFlag)
                                                                            .addValue("IP_DRY_ICE_FLAG", dryIceFlag)
                                                                            .addValue("IP_DRY_ICE_WEIGHT",dryIceWeight)
                                                                            .addValue("IP_DRY_ICE_UNITS", dryIceUnits)
                                                                           .addValue("IP_STAMPS_TAXID", stampsTxId)
                                                                           .addValue("IP_SHIP_FLAG",shipFlagFromDB)
                                                                           .addValue("IP_RETURN_DESCRIPTION",rtnDesc)
                                                                            .addValue("IP_LARGE_PACKAGE",largePackage)
                                                                            .addValue("IP_ADDITIONAL_HANDLING",additionalHandling)
                                                                            .addValue("IP_HAZMAT_PACK_INSTRUCTIONS",hazmatPackInstructions);
             
                Map<String,Object> out = simpleJdbcCall.execute(inputparams);
             
                returnStatus = Integer.parseInt(String.valueOf(out.get("op_status"))); // returns the opStatus value of save_shipment_order_details procedure
                errorStatus = String.valueOf(out.get("op_error_status")); // returns the errorStatus value of save_shipment_order_details procedure
                if(returnStatus != 225){
                    break;
                }
                packageId++; // incrementing the package value
            }//close of while loop
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from savePackageInfo()");
        
        return returnStatus;
     } // End of savePackageInfo() method


     /**
      * Method deleteHeaderInfo is used to delete the header information 
      * if the package information is not saved for a particular order number.
      * Procedure used is: AASC_ERP_SHIPMENT_ORDERS_PKG.delete_shipment_order
      * @return returnStatus of type int.
      */
     int deleteHeaderInfo(int clientID) {
        logger.info("Entered deleteHeaderInfo()");
        try{
            DataSource dataSource = connectionFactory.createDataSource();  // this method returns the datasources object
         
            simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("delete_shipment_order")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                        
         //Input parametes assignment
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number", orderNumber).addValue("ip_client_id", clientID);
         
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            logger.info("After executing");
            logger.info("out == "+out.toString());
         
            returnStatus = Integer.parseInt(String.valueOf(out.get("op_status"))); // this method returns the delete_shipment_order procedure's opStatus value
            errorStatus = String.valueOf(out.get("op_error_status")); // this method returns the delete_shipment_order procedure's errorStatus value
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from deleteHeaderInfo()");
        
        return returnStatus;
     } // End of deleteHeaderInfo() method

     //Added by Sambit on dt 09/06/2008

     /**
      This method calls the  aasc_intl_shipment_pkg.update_intl_shipment_id procedure. 
      This procedure takes the deliveryId and shipmentId as in parameters and 
      updates the shipmentId in aasc_intl_shipments table
      @param orderNumber String
      @param shipmentId int
      @param clientId   int
      @param documentID String 
      @return opStatus int.
      */
     public int updateIntlShipmentIds(String orderNumber, int shipmentId, int clientId, String documentID) {
        logger.info("Entered updateIntlShipmentIds");
        try{
            DataSource dataSource = connectionFactory.createDataSource(); // this method returns the OracleConnection object
         
            simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_INTL_SHIPMENT_PKG")
                                                          .withProcedureName("update_intl_shipment_id")
                                                          .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
         
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                       .addValue("ip_shipment_id", shipmentId)
                                                                       .addValue("ip_document_id", documentID)
                                                                       .addValue("ip_client_id", clientId);
         
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
         
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from updateIntlShipmentIds() method");
        
        return opStatus;
     }


     /**
      * Method updateShipmentTotals is used to get the data from AascShipmentHeaderInfo class getxxx() methods and 
      * saves the database through the procedure save_order_details.
      * @return returnStatus of type int.
      */
     int updateShipmentTotals(int clientID) {
        logger.info("Entered updateShipmentTotals");
        locationId = aascShipmentHeaderInfo.getLocationId();
        logger.info("locationId got from the aascShipmentHeaderInfo=" + 
                     locationId);
        shipmentId = aascShipmentHeaderInfo.getShipmentId() ;
        try{             
            DataSource dataSource = connectionFactory.createDataSource(); // this method returns the OracleConnection object
         
            simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                          .withProcedureName("update_shipment_header_totals")
                                                          .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
         
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_shipment_id", shipmentId)
                                                                       .addValue("ip_location_id", locationId)
                                                                       .addValue("ip_client_id", clientID);
         
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);

            returnStatus = Integer.parseInt(String.valueOf(out.get("op_status"))); // out Parameter returns the opStatus value of update_shipment_header_totals procedure
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS")); // out parameter returns the errorStatus of update_shipment_header_totals procedure
         
            logger.info("AASC_ERP_SHIPMENT_ORDERS_PKG.update_shipment_header_totals opStatus:" + returnStatus + "\t errorStatus:" + errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from updateShipmentTotals()");
        
        return returnStatus;
     } // End of updateShipmentTotals() method.

      /** This method can replace the null values with nullString.
           * @return String that is ""
           * @param obj object of type Object
           */   
      String nullStrToSpc(Object obj) {
          String spcStr = "";

          if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
              return spcStr;
          } else {
              return obj.toString();
          }
      }
          
 }// End of AascOracleShipSaveDAO class
