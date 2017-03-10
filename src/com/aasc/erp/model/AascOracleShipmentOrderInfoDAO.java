
/*
* @(#)AascOracleShipmentOrderInfoDAO.java        15/12/2014
* Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
* All rights reserved.
*/
//importing the packages
package com.aasc.erp.model;

import com.aasc.erp.bean.AascPrinterInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


/**
 * AascOracleShipmentOrderInfoDAO class Implements AascShipmentOrderInfoDAO class. 
 * This class gets the Shipment related Header and package information from database and
 * storing into setXxx() method of AascShipmentHeaderInfo and AascShipmentPackageInfo classes
 * and displays this information in the jsp.
 */
public class
/*
 * @author Y Pradeep
 * @version 1.0
 */
/*========================================================================================
Date        Resource       Change history
------------------------------------------------------------------------------------------
15/12/2014  Y Pradeep      Modified alls methods from basic JDBC call's to Spring JDBC call's
17/12/2014  Eshwari        Renamed tracking_number to waybill_number in the headerInfo
19/12/2014  Y Pradeep      Modified nullStrToSpc method, modified all DAO calls into Spring JDBC call in getShipmentOrderInfo method and related methods.
02/01/2015  Y Pradeep      Added code to retrive ship_to_location_name from shipment headers table.
16/01/2015  Y Pradeep      Commented code to get dimension_units in getPackageBean method for bug #2526.
02/02/2015  Eshwari M      Modified code to get the data related to carrier service level and total package weight
12/02/2015  Eshwari M      Modified code to get location id from database at the time of order details retrieval.
09/03/2015  Suman G        Modified setDimensionUnit() to setDimensionUnits() for eliminate duplicate variables.
10/03/2015  Y Pradeep      Added code to get Address Line 3, emailId for ship to and ship from and resediential flag.
12/03/2015  Eshwari M      Modified code to change intVale() to doubleValue() and floatValue() for getting charges and weights in decimal points to resolve bug # 2654
12/03/2015  Suman G        Added carrier name code for view label functionality.
02/04/2015  Y Pradeep      Added intlFlag variable for setting intlSaveFlag to Y.
08/05/2015  Y Pradeep      Added deletePackage method for deleting or clearing package details in database. Bug #2910.
25/05/2015  K Sunanda      Modified code for bug fix #2943
27/05/2015  Suman G        Added code to fix #2936 
02/06/2015  Y Pradeep      Modified code to pull empty package details if package cursor size is equal to 0.
03/06/2015  Suman G        Added code to fix #2955
23/06/2015  Y Pradeep      Added code to get list of enabled printers from Printer Setup table based on Ship from location after Shipping Successfully.
13/07/2015  Y Pradeep      Removed getPrinterSetupDetails method to get printer details from profile options page. Added coded to get labelFormat from header table.
20/07/2015  Y Pradeep      Added if and else coditions to display current date if a voided order is retrieved. Bug #3193.
01/11/2015  Mahesh V       Added code for Stamps.com new fields implemented for Stamps.com Integration.
05/11/2015  Y Pradeep      Added nullStringToSpcace for missed variables while getting header and package details. Bug #3849.
10/11/2015  Mahesh V       Added code for FedEx Recepient Development
18/12/2015  Y Pradeep      Modified code to get TimeStamp for shipmentdate field. Bug #4095.

========================================================================================*/
AascOracleShipmentOrderInfoDAO implements AascShipmentOrderInfoDAO {
 
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
    SimpleJdbcCall simpleJdbcCall;
    private AascShipmentHeaderInfo aascShipmentHeaderInfo; // holds the object of AascShipmentHeaderInfo class.    
    private AascShipmentPackageInfo aascShipmentPackageInfo; // holds the  object of AascShipmentPackageInfo class.    
    private AascShipmentOrderInfo aascShipmentOrderInfo = 
        new AascShipmentOrderInfo(); // Creating object of AascShipmentOrderInfo class.
    private String orderNumber; // holds the object of the order number(sequence number to identify Shipment shipment)
    private LinkedList packageLinkedList = null; // holds the object of the LinkedList class which holds the objects of the AascShipmentPackageInfo class
    private static Logger logger = AascLogger.getLogger("AascOracleShipmentOrderInfoDAO"); // this is a static method of the AaascLogger class which returns the object of the Logger class
    private int returnStatus; // holds the opStatus value return by the procedure                                              
    private String errorStatus; // holds the errorStatus value return by the procedure
    private int userId; // id for the user
    private String defaultPrinter;
    private String order_Number;
    private String intlFlag = "N";

    String shipFromContact = "";
    String shipToAddressLine2 = "";
    Map<String, ?> map1;

    /**
     * Default constructor.
     */
    public AascOracleShipmentOrderInfoDAO() {
    } //end of constructor

    /**
     * This method can replace the null values with nullString.
     * @param  object  Object
     * @return String that is ""
     */
    public String nullStringToSpcace(Object object) {
        String spcStr = "";
        if (object == null || "null".equalsIgnoreCase(object.toString())) {
            return spcStr;
        } //end of if block
        else {
            return object.toString();
        } //end of else block
    } //end of nullStringToSpcace method

    /**
     * getShipmentOrderInfo() method of AascShipmentOrderInfo class has orderNumber as parameter and returns 
     * aascShipmentOrderInfo object.This method gets the Header and package information from the procedure:
     * AASC_Erp_Shipment_ORDERS_PKG.get_Shipment_order_details.This procedure has 2 ref cursors for header and for
     * package information.this method inturn calls the two methods which get the header and package
     * information from the resultset and set to the setter method of the AascShipmentHeaderInfo class and AascShipmentPackageInfo
     * class, and returns the object of the their class. The objects of AascShipmentHeaderInfo and AascShipmentPackageInfo 
     * class are set into setShipmentHeaderInfo() and setShipmentPackageInfo() methods of  AascShipmentOrderInfo class.
     * @param orderNumber of type int
     * @return aascShipmentOrderInfo of type AascShipmentOrderInfo
     */
    public AascShipmentOrderInfo getShipmentOrderInfo(String orderNumber,int clientID) {
        logger.info("Entered getShipmentOrderInfo()");
        this.orderNumber = orderNumber;
        LinkedList packageList = new LinkedList(); // creating the object of the linkedlist
        try {
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the object of the Oracle datasource
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("get_shipment_order_details")
                                                           .declareParameters(new SqlOutParameter("aasc_shipment_orders_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_shipment_orders_packages", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_intl_flag", Types.VARCHAR));
            
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_order_number", orderNumber)
                                                                       .addValue("ip_client_id", clientID);
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
            
            returnStatus = Integer.parseInt(String.valueOf(out.get("op_status"))); // this method returns the opStatus value
            errorStatus = String.valueOf(out.get("op_error_status")); // this method returns the errorStatus value
            intlFlag = String.valueOf(out.get("op_intl_flag"));    // this is to retrieve intl save flag status.
            logger.info("errorStatus : "+errorStatus);

            if (returnStatus == 200) {
                
                Iterator headerDetailsIt = ((ArrayList)out.get("OP_AASC_SHIPMENT_DETAILS")).iterator(); // this method returns the resultSet object which have header information
                Iterator packageDetailsIt = ((ArrayList)out.get("OP_AASC_SHIPMENT_PACKAGES")).iterator(); // this method returns the resultset object which have package information
                AascShipmentHeaderInfo headerBean; // holds the object of AascShipmentHeaderInfo class.     
                headerBean = getHeaderBean(headerDetailsIt); // this method returns the object of the AascShipmentHeaderInfo class.

                if(((ArrayList)out.get("OP_AASC_SHIPMENT_PACKAGES")).size() == 0){ 
                    AascShipmentPackageInfo pkgBean = new AascShipmentPackageInfo(); 
                    packageList.add(pkgBean);
                }
                else
                {
                    packageList = getPackageBean(packageDetailsIt); // this method returns the object of the AascAdhocPackageInfo class.
                }     
                
                aascShipmentOrderInfo.setReturnStatus(returnStatus); // setting the opStatus value of the procedure to the set method of the AascShipmentOrderInfo class
                aascShipmentOrderInfo.setIntlFlag(intlFlag);
                aascShipmentOrderInfo.setShipmentHeaderInfo(headerBean); // settign the object of the AascShipmentHeaderInfo to the  setShipmentHeaderInfo() method of the AascShipmentOrderInfo class
                aascShipmentOrderInfo.setShipmentPackageInfo(packageList); // settign the object of the AascShipmentPackageInfo to the  setShipmentPackageInfo() method of the AascShipmentOrderInfo class        
            } //end of inner if block
            logger.info("AASC_Erp_Shipment_ORDERS_PKG.get_Shipment_order_details opStatus:" + returnStatus + "\t errorStatus:" + errorStatus);
        } //end of getShipmentOrderInfo method try block
        catch (Exception e) {
            logger.info("Exception : "+e.getMessage());
        } //end of getShipmentOrderInfo method catch block
        logger.info("Exit from getShipmentOrderInfo()");
        
        return aascShipmentOrderInfo;
    } // End of getShipmentOrderInfo()

    /**
     * getheaderBean() method sets all the header information from headerResultSet
     * cursor into the setXxx() methods of AascShipmentHeaderInfo class.
     * @return aascShipmentHeaderInfo object of type AascShipmentHeaderInfo class.
     */
    AascShipmentHeaderInfo getHeaderBean(Iterator headerDetailsIt) {
        logger.info("Entered getHeaderBean()");
        aascShipmentHeaderInfo = new AascShipmentHeaderInfo(); // creating the object of the AascShipmentHeaderInfo class
        try {
            logger.info("retriving the header info from the result set");
            while (headerDetailsIt.hasNext()) // Placing all the header information into setXxx() methods.
            {   
                map1 = (Map<String, ?>)headerDetailsIt.next();
                
                aascShipmentHeaderInfo.setOrderNumber(String.valueOf(map1.get("order_number")));
                aascShipmentHeaderInfo.setImportFlag(String.valueOf(map1.get(("import_flag"))));
                aascShipmentHeaderInfo.setCustomerName(nullStringToSpcace(String.valueOf(map1.get("customer_name"))));
                aascShipmentHeaderInfo.setShipToLocationName(nullStringToSpcace(String.valueOf(map1.get("ship_to_location_name"))));
                aascShipmentHeaderInfo.setAddress(nullStringToSpcace(String.valueOf(map1.get("address1"))));
                aascShipmentHeaderInfo.setShipToAddrLine2(nullStringToSpcace(String.valueOf(map1.get("address2"))));
                aascShipmentHeaderInfo.setShipToAddrLine3(nullStringToSpcace(String.valueOf(map1.get("address3"))));
                aascShipmentHeaderInfo.setShipToEmailId(nullStringToSpcace(String.valueOf(map1.get("email_id"))));
                aascShipmentHeaderInfo.setCity(nullStringToSpcace(String.valueOf(map1.get("city"))));
                aascShipmentHeaderInfo.setState(nullStringToSpcace(String.valueOf(map1.get("state"))));
                aascShipmentHeaderInfo.setCountrySymbol(nullStringToSpcace(String.valueOf(map1.get("country_code"))));
                aascShipmentHeaderInfo.setPostalCode(nullStringToSpcace(String.valueOf(map1.get("postal_code"))));
                aascShipmentHeaderInfo.setShipperName(nullStringToSpcace(String.valueOf(map1.get("shipper_name"))));
                aascShipmentHeaderInfo.setReference1(nullStringToSpcace(String.valueOf(map1.get("reference1"))));
                aascShipmentHeaderInfo.setReference2(nullStringToSpcace(String.valueOf(map1.get("reference2"))));
                aascShipmentHeaderInfo.setWayBill(nullStringToSpcace(String.valueOf(map1.get("waybill_number"))));
                aascShipmentHeaderInfo.setShipmentCost(((BigDecimal)map1.get("shipment_cost")).doubleValue());  //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentHeaderInfo.setShippingMethod(nullStringToSpcace(String.valueOf(map1.get("ship_method"))));
                aascShipmentHeaderInfo.setShipAddInfo(nullStringToSpcace(String.valueOf(map1.get("ship_add_info"))));
                try{
                    // Added below if and else coditions to display current date if a voided order is retrieved. Bug #3193. By Y Pradeep.
                    Date shipmentDate = null;
                    Timestamp shipmentDateTimeStamp = null;
                    if("Y".equalsIgnoreCase(String.valueOf(map1.get("SHIP_FLAG")))){
                        shipmentDateTimeStamp = Timestamp.valueOf(String.valueOf(map1.get("shipment_date")));
                        shipmentDate = new Date(shipmentDateTimeStamp.getTime());
                    } else {
                        java.util.Date utilDate = new java.util.Date();
                        shipmentDate = new java.sql.Date(utilDate.getTime()); // date(sysdate) on which the delivery needs to be shipped
                    }
                    aascShipmentHeaderInfo.setShipmentDate(shipmentDate);
                    aascShipmentHeaderInfo.setShipTimeStamp(shipmentDateTimeStamp);
                }catch(Exception e){
                    e.printStackTrace();
                }
                aascShipmentHeaderInfo.setCarrierPaymentMethod(nullStringToSpcace(String.valueOf(map1.get("carrier_pay_code"))));
                aascShipmentHeaderInfo.setCarrierAccountNumber(nullStringToSpcace(String.valueOf(map1.get("carrier_account_number"))));//Sunanda modified code for bug fix #2943
                aascShipmentHeaderInfo.setContactName(nullStringToSpcace(String.valueOf(map1.get("contact_name"))));
                aascShipmentHeaderInfo.setPhoneNumber(nullStringToSpcace(String.valueOf(map1.get("phone_number"))));
                aascShipmentHeaderInfo.setCreatedBy(Integer.parseInt(String.valueOf(map1.get("created_by"))));
                aascShipmentHeaderInfo.setCarrierId(Integer.parseInt(String.valueOf(map1.get("carrier_id"))));
                aascShipmentHeaderInfo.setVoidFlag(nullStringToSpcace(String.valueOf(map1.get("void_flag"))));
                aascShipmentHeaderInfo.setPrintLabelFlag(nullStringToSpcace(String.valueOf(map1.get("print_label_flag"))));
                aascShipmentHeaderInfo.setManualTrackingFlag(nullStringToSpcace(String.valueOf(map1.get("manual_tracking_flag"))));
                aascShipmentHeaderInfo.setShipMethodMeaning(nullStringToSpcace(String.valueOf(map1.get("ship_method_meaning"))));
                aascShipmentHeaderInfo.setSaturdayShipFlag(nullStringToSpcace(String.valueOf(map1.get("saturday_shipment_flag"))));
                aascShipmentHeaderInfo.setTpAddress(nullStringToSpcace(String.valueOf(map1.get("tp_address"))));
                aascShipmentHeaderInfo.setTpCity(nullStringToSpcace(String.valueOf(map1.get("tp_city"))));
                aascShipmentHeaderInfo.setTpCompanyName(nullStringToSpcace(String.valueOf(map1.get("tp_company_name"))));
                aascShipmentHeaderInfo.setTpCountrySymbol(nullStringToSpcace(String.valueOf(map1.get("tp_country_symbol"))));
                aascShipmentHeaderInfo.setTpPostalCode(nullStringToSpcace(String.valueOf(map1.get("tp_postal_code"))));
                aascShipmentHeaderInfo.setTpState(nullStringToSpcace(String.valueOf(map1.get("tp_state"))));
                aascShipmentHeaderInfo.setShipFromLocation(nullStringToSpcace(String.valueOf(map1.get("ship_from_location"))));
                aascShipmentHeaderInfo.setShipFromAddressLine1(nullStringToSpcace(String.valueOf(map1.get("ship_from_address_line1"))));
                aascShipmentHeaderInfo.setShipFromAddressLine2(nullStringToSpcace(String.valueOf(map1.get("ship_from_address_line2"))));
                aascShipmentHeaderInfo.setShipFromAddressLine3(nullStringToSpcace(String.valueOf(map1.get("ship_from_address_line3"))));
                aascShipmentHeaderInfo.setShipFromCity(nullStringToSpcace(String.valueOf(map1.get("ship_from_town_or_city"))));
                aascShipmentHeaderInfo.setShipFromState(nullStringToSpcace(String.valueOf(map1.get("ship_from_state"))));
                aascShipmentHeaderInfo.setShipFromCountry(nullStringToSpcace(String.valueOf(map1.get("ship_from_country"))));
                aascShipmentHeaderInfo.setShipFromPostalCode(nullStringToSpcace(String.valueOf(map1.get("ship_from_postal_code"))));
                aascShipmentHeaderInfo.setShipFromPhoneNumber1(nullStringToSpcace(String.valueOf(map1.get("ship_from_phone_number1"))));
                aascShipmentHeaderInfo.setShipFromPhoneNumber2(nullStringToSpcace(String.valueOf(map1.get("ship_from_phone_number2"))));
                aascShipmentHeaderInfo.setShipFromEmailId(nullStringToSpcace(String.valueOf(map1.get("ship_from_email_id"))));
                aascShipmentHeaderInfo.setDropOfType(nullStringToSpcace(String.valueOf(map1.get("dropofftype"))));
                aascShipmentHeaderInfo.setPackaging(nullStringToSpcace(String.valueOf(map1.get("packaging"))));

                aascShipmentHeaderInfo.setDepartment(nullStringToSpcace(String.valueOf(map1.get("department"))));
                aascShipmentHeaderInfo.setFreightCost(((BigDecimal)map1.get("freight_charges")).doubleValue());   //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentHeaderInfo.setTotalSurcharge(((BigDecimal)map1.get("total_surcharges")).doubleValue());   //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentHeaderInfo.setAcctPostalCode(nullStringToSpcace(String.valueOf(map1.get("tp_acct_postal_code"))));
                aascShipmentHeaderInfo.setShipFromCompanyName(nullStringToSpcace(String.valueOf(map1.get("company_name"))));
                
                aascShipmentHeaderInfo.setShipFlag(nullStringToSpcace(String.valueOf(map1.get("SHIP_FLAG"))));
                aascShipmentHeaderInfo.setCarrierServiceLevel(nullStringToSpcace(String.valueOf(map1.get("carrier_service_level"))));
                aascShipmentHeaderInfo.setUpsServiceLevelCode(nullStringToSpcace(String.valueOf(map1.get("carrier_service_level_code"))));
                aascShipmentHeaderInfo.setPackageWeight(((BigDecimal)map1.get("total_pkg_weight")).doubleValue());   //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentHeaderInfo.setResidentialFlag(nullStringToSpcace(String.valueOf(map1.get("residential_flag"))));
                aascShipmentHeaderInfo.setLocationId(((BigDecimal)map1.get("location_id")).intValue());
                aascShipmentHeaderInfo.setCarrierName(nullStringToSpcace(String.valueOf(map1.get("CARRIER_NAME"))));
                aascShipmentHeaderInfo.setAvFlag(nullStringToSpcace(String.valueOf(map1.get("AV_FLAG"))));
                aascShipmentHeaderInfo.setFsFlag(nullStringToSpcace(String.valueOf(map1.get("FS_FLAG"))));
                logger.info("AVFLAG::::"+aascShipmentHeaderInfo.getAvFlag());
                logger.info("fsFLAG::::"+aascShipmentHeaderInfo.getFsFlag());
                logger.info("carriername::::"+aascShipmentHeaderInfo.getCarrierName());
                aascShipmentHeaderInfo.setLabelFormat(nullStringToSpcace(String.valueOf(map1.get("LABEL_FORMAT"))));
                logger.info("LabelFormat::::"+aascShipmentHeaderInfo.getLabelFormat());
                
                aascShipmentHeaderInfo.setRecCompanyName(nullStringToSpcace(String.valueOf(map1.get("Rec_Company_Name"))));
                aascShipmentHeaderInfo.setRecPostalCode(nullStringToSpcace(String.valueOf(map1.get("REC_POSTAL_CODE"))));

   
                
            } //end of while loop
            logger.info("Values are set to aascShipmentHeaderInfo bean");
            
            //aascShipmentHeaderInfo.setShipFlag("Y");
        } //end of getHeaderBean() method try block
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } //end of getHeaderBean() method catch block
        
        logger.info("Exit from getHeaderBean()");
        return aascShipmentHeaderInfo;
    } // End of getHeaderBean()

    /**
     * getPackageBean() method sets all the package information from packageResultSet
     * cursor into setXxx methods of AascShipmentPackageInfo class.
     * @return packageLinkedList object of type LinkedList.
     */
    LinkedList getPackageBean(Iterator packageDetailsIt) {
        logger.info("Entered getPackageBean()");
        try {
            packageLinkedList = new LinkedList(); // Creating a linked list object.  
            logger.info("retriving the package information from the resultset");
            while (packageDetailsIt.hasNext()) // placing all the package information into setXxx() methods.
            {
                map1 = (Map<String, ?>)packageDetailsIt.next();
                
                aascShipmentPackageInfo = new AascShipmentPackageInfo();
                aascShipmentPackageInfo.setOrderNumber(String.valueOf(map1.get("order_number")));
                aascShipmentPackageInfo.setPackageNumber(Integer.parseInt(String.valueOf(map1.get("pkg_number"))));
                aascShipmentPackageInfo.setItemDescription(nullStringToSpcace(String.valueOf(map1.get("item_name"))));
                aascShipmentPackageInfo.setItemNumber(nullStringToSpcace(String.valueOf(map1.get("item_description"))));
                aascShipmentPackageInfo.setShippedQuantity(((BigDecimal)map1.get("shipped_qty")).floatValue());    //modified() intValue to floatValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setUom(nullStringToSpcace(String.valueOf(map1.get("uom"))));
                aascShipmentPackageInfo.setWeight(((BigDecimal)map1.get("weight")).floatValue());   //modified() intValue to floatValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setMsn(Integer.parseInt(String.valueOf(map1.get("msn"))));
                // aascShipmentPackageInfo.setDimension(packageResultSet.getString("dimensions"));
                aascShipmentPackageInfo.setDimensionUnits(nullStringToSpcace(String.valueOf(map1.get("units"))));
                aascShipmentPackageInfo.setCodFlag(String.valueOf(map1.get("cod_option_flag")));
                aascShipmentPackageInfo.setCodAmt(((BigDecimal)map1.get("cod_amount")).floatValue());  //modified() intValue to floatValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setPackageDeclaredValue(((BigDecimal)map1.get("package_declared_value")).doubleValue());   //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setSignatureOptions(nullStringToSpcace(String.valueOf(map1.get("signature_option"))));
                aascShipmentPackageInfo.setReturnShipment(nullStringToSpcace(String.valueOf(map1.get("return_shipment_indicator"))));
                aascShipmentPackageInfo.setSurCharges(((BigDecimal)map1.get("package_surcharges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setPkgCost(((BigDecimal)map1.get("package_shipment_cost")).doubleValue());     //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setPackageLength(Integer.parseInt(String.valueOf(map1.get("package_length"))));
                aascShipmentPackageInfo.setPackageWidth(Integer.parseInt(String.valueOf(map1.get("package_width"))));
                aascShipmentPackageInfo.setPackageHeight(Integer.parseInt(String.valueOf(map1.get("package_height"))));
                aascShipmentPackageInfo.setPackaging(nullStringToSpcace(String.valueOf(map1.get("packaging"))));
                aascShipmentPackageInfo.setCodCode(nullStringToSpcace(String.valueOf(map1.get("cod_type"))));
                aascShipmentPackageInfo.setCodFundsCode(nullStringToSpcace(String.valueOf(map1.get("cod_funds_code"))));
                aascShipmentPackageInfo.setCodCurrCode(nullStringToSpcace(String.valueOf(map1.get("cod_currency"))));
                aascShipmentPackageInfo.setDeclaredCurrCode(nullStringToSpcace(String.valueOf(map1.get("dec_val_currency"))));
                aascShipmentPackageInfo.setBaseCharge(((BigDecimal)map1.get("package_base_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setTrackingNumber(nullStringToSpcace(String.valueOf(map1.get("tracking_number"))));
                aascShipmentPackageInfo.setDimension(nullStringToSpcace(String.valueOf(map1.get("dimensions"))));
//                aascShipmentPackageInfo.setDimensionUnits(nullStringToSpcace(String.valueOf(map1.get("dimension_units"))));   // commented this line to solve bug #2526 by Y Pradeep
                aascShipmentPackageInfo.setIntlQuantity(((BigDecimal)map1.get("intl_qty")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setIntlQUOM(nullStringToSpcace(String.valueOf(map1.get("intl_unit_uom"))));
                if(map1.get("intl_unit_weight") != null){
                    aascShipmentPackageInfo.setIntlUnitWeight(((BigDecimal)map1.get("intl_unit_weight")).doubleValue());     //modified() intValue to doubleValue() to resolve bug # 2654 
                } else{
                    aascShipmentPackageInfo.setIntlUnitWeight(0.0);
                }
                aascShipmentPackageInfo.setIntlUnitValue(((BigDecimal)map1.get("intl_unit_value")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setIntlProductDescription(nullStringToSpcace(String.valueOf(map1.get("intl_product_desc"))));
                aascShipmentPackageInfo.setRtnShipMethod(nullStringToSpcace(String.valueOf(map1.get("return_ship_method"))));
                aascShipmentPackageInfo.setRtnDropOfType(nullStringToSpcace(String.valueOf(map1.get("return_dropofftype"))));
                aascShipmentPackageInfo.setRtnPackageList(nullStringToSpcace(String.valueOf(map1.get("return_packaging"))));
                aascShipmentPackageInfo.setRtnPayMethod(nullStringToSpcace(String.valueOf(map1.get("return_pay_code"))));
                aascShipmentPackageInfo.setRtnACNumber(nullStringToSpcace(String.valueOf(map1.get("return_acct_number"))));
                aascShipmentPackageInfo.setRtnRMA(nullStringToSpcace(String.valueOf(map1.get("return_material_auth"))));
                aascShipmentPackageInfo.setRtnTrackingNumber(nullStringToSpcace(String.valueOf(map1.get("return_tracking_number"))));
                aascShipmentPackageInfo.setRtnShipmentCost(((BigDecimal)map1.get("return_package_cost")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setRtnDeclaredValue(((BigDecimal)map1.get("return_declared_value")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setRtnBaseChrge(((BigDecimal)map1.get("return_pkg_base_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setRtnTotalDiscount(((BigDecimal)map1.get("return_pkg_discount_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setHalFlag(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_flag"))));
                aascShipmentPackageInfo.setHalPhone(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_phone"))));
                aascShipmentPackageInfo.setHalLine1(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_line1"))));
                aascShipmentPackageInfo.setHalCity(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_city"))));
                aascShipmentPackageInfo.setHalStateOrProvince(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_state"))));
                aascShipmentPackageInfo.setHalPostalCode(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_postal_code"))));
                aascShipmentPackageInfo.setHazMatFlag(nullStringToSpcace(String.valueOf(map1.get("hazmat_flag"))));
                aascShipmentPackageInfo.setHazMatType(nullStringToSpcace(String.valueOf(map1.get("hazmat_type"))));
                aascShipmentPackageInfo.setHazMatClass(nullStringToSpcace(String.valueOf(map1.get("hazmat_class"))));
                aascShipmentPackageInfo.setHazMatLabelType(nullStringToSpcace(String.valueOf(map1.get("hazmat_label_type"))));
                aascShipmentPackageInfo.setHazMatCharges(((BigDecimal)map1.get("hazmat_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setDimensionId(Integer.parseInt(String.valueOf(map1.get("dimension_id"))));
                aascShipmentPackageInfo.setHalLine2(nullStringToSpcace(String.valueOf(map1.get("hold_at_location_line2"))));
                aascShipmentPackageInfo.setHazMatQty(((BigDecimal)map1.get("hazmat_qty")).doubleValue());     //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setHazMatUnit(nullStringToSpcace(String.valueOf(map1.get("hazmat_units"))));
                aascShipmentPackageInfo.setHazMatIdNo(nullStringToSpcace(String.valueOf(map1.get("hazmat_identification_number"))));
                aascShipmentPackageInfo.setHazMatEmerContactNo(nullStringToSpcace(String.valueOf(map1.get("hazmat_emergency_contact_no"))));
                aascShipmentPackageInfo.setHazMatEmerContactName(nullStringToSpcace(String.valueOf(map1.get("hazmat_emergency_contact_name"))));
                aascShipmentPackageInfo.setHazMatPkgGroup(nullStringToSpcace(String.valueOf(map1.get("hazmat_packging_group"))));
                aascShipmentPackageInfo.setHazMatDOTLabel(nullStringToSpcace(String.valueOf(map1.get("hazmat_dot_labels"))));
                aascShipmentPackageInfo.setHazMatReq(nullStringToSpcace(String.valueOf(map1.get("hazmat_required"))));
                aascShipmentPackageInfo.setHazMatId(nullStringToSpcace(String.valueOf(map1.get("hazmat_material_id"))));
                aascShipmentPackageInfo.setPkgDiscountNetCharge(((BigDecimal)map1.get("pkg_discount_net_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setPkgListNetCharge(((BigDecimal)map1.get("pkg_list_net_charges")).doubleValue());    //modified() intValue to doubleValue() to resolve bug # 2654 
                aascShipmentPackageInfo.setVoidFlag(nullStringToSpcace(String.valueOf(map1.get("void_package_flag"))));
                aascShipmentPackageInfo.setCreatedBy(Integer.parseInt(String.valueOf(map1.get("created_by"))));
                aascShipmentPackageInfo.setDryIceChk(nullStringToSpcace(String.valueOf(map1.get("DRY_ICE_FLAG"))));
                aascShipmentPackageInfo.setDryIceWeight(((BigDecimal)(map1.get("DRY_ICE_WEIGHT"))).doubleValue());
                aascShipmentPackageInfo.setDryIceUnits(nullStringToSpcace(String.valueOf(map1.get("DRY_ICE_UNITS"))));
                aascShipmentPackageInfo.setStampsTaxId(nullStringToSpcace(String.valueOf(map1.get("STAMPS_TAXID"))));  //Mahesh
                 aascShipmentPackageInfo.setRtnDesc(nullStringToSpcace(String.valueOf(map1.get("RETURN_DESCRIPTION"))));
                aascShipmentPackageInfo.setLargePackage(nullStringToSpcace(String.valueOf(map1.get("Large_Package"))));
                aascShipmentPackageInfo.setAdditionalHandling(nullStringToSpcace(String.valueOf(map1.get("ADDITIONAL_HANDLING"))));
                aascShipmentPackageInfo.setHazmatPackInstructions(nullStringToSpcace(String.valueOf(map1.get("HAZMAT_PACK_INSTRUCTIONS"))));

                
                try {
                    aascShipmentPackageInfo.setHazmatPkgingCnt(Double.parseDouble(nullStringToSpcace(String.valueOf(map1.get("hazmat_packaging_count")))));
                } catch (Exception e) {
                    aascShipmentPackageInfo.setHazmatPkgingCnt(0.0);
                }

                try {
                    aascShipmentPackageInfo.setHazmatPkgingUnits(nullStringToSpcace(String.valueOf(map1.get("hazmat_packaging_units"))));
                } catch (Exception e) {
                    aascShipmentPackageInfo.setHazmatPkgingUnits("");
                }
                //End on Jun-03-2011


                //Addded on Jul-05-2011
                try {
                    aascShipmentPackageInfo.setHazmatTechnicalName(nullStringToSpcace(String.valueOf(map1.get("hazmat_technical_name"))));
                } catch (Exception e) {
                    aascShipmentPackageInfo.setHazmatTechnicalName("");
                }
                
                try {
                    aascShipmentPackageInfo.setHazmatSignatureName(nullStringToSpcace(String.valueOf(map1.get("hazmat_signature_name"))));
                } catch (Exception e) {
                    aascShipmentPackageInfo.setHazmatSignatureName("");
                }

                //End on Jul-05-2011

                packageLinkedList.add(aascShipmentPackageInfo); // adding all the package information to packageLinkedList.
            } // End of while loop
            logger.info("Values are set to aascShipmentHeaderInfo bean");
        } // End of getPackageBean() try block
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } //end of getPackageBean() method catch block
        logger.info("Exit from getPackageBean()");
        
        return packageLinkedList;
    } // End of getPackageBean() method.


    public String getDefaultPrinter(int userId) {
        logger.info("Entered into getDefaultPrinter() method");
        this.userId = userId;
        logger.info("userId ==" + userId);
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the object of the OracleConnection
                
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_Shipment_ORDERS_PKG")
                                                           .withProcedureName("default_printer")
                                                           .declareParameters(new SqlOutParameter("g_printer_name", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER));
            //input parametes assigment
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_user_id", userId);
                
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
            logger.info("after execution");
            logger.info("out == "+out.toString());
                
            defaultPrinter = String.valueOf(out.get("g_printer_name"));
            errorStatus = String.valueOf(out.get("op_error_status"));
                
            logger.info("AASC_ERP_SHIPMENT_ORDERS_PKG.default_printer() defaultPrinter:" + defaultPrinter + "\t errorStatus:" + errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("End of getDefaultPrinter() method");
        
        return defaultPrinter;
    } //End of default_printer()


    public String getOrderNumber(int clientId) {
        logger.info("Entered into getOrderNumber() method");
        try{
            DataSource datsource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datsource).withCatalogName("AASC_ERP_Shipment_ORDERS_PKG")
                                                          .withProcedureName("get_order_number")
                                                          .declareParameters(new SqlOutParameter("op_order_number", Types.VARCHAR))
                                                          .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                          
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("IP_CLIENT_ID", clientId);
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
            
            order_Number = String.valueOf(out.get("op_order_number")); // returning order number
            errorStatus = String.valueOf(out.get("op_error_status")); // this method returns the errorStatus value
            
            logger.info("AASC_ERP_SHIPMENT_ORDERS_PKG.get_order_number()  order_Number:" + order_Number + "\t errorStatus:" + errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }        
        logger.info("End of getOrderNumber() method");
        return order_Number;
    } //End of getOrderNumber()


    /**
     * Tis method is called from aascAjaxDeletePackages.jsp file to delete or clear all rows of packages when delete or clear button clicked in Package Details section.
     * @param clientId
     * @param orderNumber
     * @param packNumber
     * @return String object
     */
    public String deletePackage(int clientId, String orderNumber, int packNumber){
         
         logger.info("Entered into deletePackage() method");
         try{
             DataSource datsource = connectionFactory.createDataSource();
             
             simpleJdbcCall = new SimpleJdbcCall(datsource).withCatalogName("AASC_ERP_Shipment_ORDERS_PKG")
                                                           .withProcedureName("delete_saved_shipment_packages")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                           
             SqlParameterSource inputparam = new MapSqlParameterSource().addValue("IP_CLIENT_ID", clientId)
                                                                        .addValue("IP_ORDER_NUMBER", orderNumber)
                                                                        .addValue("IP_PACKAGE_NUMBER", packNumber);
             
             Map<String, Object> out = simpleJdbcCall.execute(inputparam);
             
             errorStatus = String.valueOf(out.get("op_error_status")); // this method returns the errorStatus value
             
             logger.info("AASC_ERP_SHIPMENT_ORDERS_PKG.delete_saved_shipment_packages() errorStatus:" + errorStatus);
         } catch(Exception e){
             logger.severe("Exception e : "+e.getMessage());
         }        
         logger.info("End of deletePackage() method");
         return errorStatus;
         
     }

    public int verifyCustomerName(int clientId, int userId, String customerName){
        logger.info("Entered into verifyCustomerName() method");
        try{
            DataSource datsource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datsource).withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                                                          .withProcedureName("VERIFY_CUSTOMER_NAME")
                                                          .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));
                                                          
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("IP_USER_ID", userId)
                                                                       .addValue("IP_CLIENT_ID", clientId)
                                                                       .addValue("IP_CUSTOMER_NAME", customerName);
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
            
            errorStatus = String.valueOf(out.get("OP_ERROR_STATUS")); // this method returns the errorStatus value
            
            returnStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            
            logger.info("AASC_ERP_CUSTOMER_LOCATION_PKG.VERIFY_CUSTOMER_NAME():: return status:::"+returnStatus+":::errorStatus:" + errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }        
        logger.info("End of verifyCustomerName() method");
        return returnStatus;
    }
}// End of class AascOracleShipmentOrderInfoDAO

