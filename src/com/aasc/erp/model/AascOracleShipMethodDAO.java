/*
  * @(#)AascOracleShipMethodDAO.java        
  * Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
  * All rights reserved.
  */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * AascOracleShipMethodDAO class retrieves ship method details from database 
    and places it into a bean which is inturned placed into LinkedList
    and LinkedList is placed in ship method bean-AascShipMethodInfo by calling its set() method.
 * @version - 1
 * @author 
 * @since  
 *        15/12/2014    Y Pradeep          Modified alls methods from basic JDBC call's to Spring JDBC call's
 *        19/12/2014    Y Pradeep          Modified nullStrToSpc method and added try, catch to all methods.
 *        24/12/2014    Y Pradeep          Commited code after Self Audit.
 *        26/12/2014    Y Pradeep          Added condition to iterate list values in getShipMethodInfo() method.
 *        19/01/2015    Suman G            Removed unncessary comments in history and unnecessary code also.
 *        19/01/2015    Y Pradeep          Removed unnecessary code, commented code and ran self aduit.
 *        25/02/2015    Suman G            Added getFreightShopDetails() method to get carrier codes with user defined meaning.
 *        09/03/2015    Suman G            Removed sop's.
 *        10/03/2015    Y Pradeep          Modified code to display LTL carriers shipmethods if enabled for international shipping.
 *        18/03/2015    Y Pradeep          Modified code to remove duplicate International Ship Methods in Shipping Page.
 *        26/03/2015    Suman G            Added code in getFreightShopDetails() method for getting carrier name
 *        28/04/2015    Eshwari M          Added condition to give alert to user when carrier configuration is not configured.
 *        24/06/2015    Y Pradeep          Added code to get PrinterPort from Carrier Configuring table.
 */
public class AascOracleShipMethodDAO extends StoredProcedure implements AascShipMethodDAO {

    SimpleJdbcCall simpleJdbcCall;
    private LinkedList shipMethodDetailList = new LinkedList(); // linked list containing ship method details-
    private LinkedList shipMethodList = new LinkedList(); // linked list containing ship method names only
    private LinkedList countryDetailList = new LinkedList(); // linked list containing country details
    private LinkedList carrierPayDetails = new LinkedList(); // linked list containing carrier pay details like carrier pay method,code and connectship carrier pay terms
    private LinkedList carrierDetailList = new LinkedList(); // linked list containing carrier details
    private LinkedList carrierConfigList = new LinkedList(); // linked list containing carrier configuration details like port,host 
    private LinkedList dropOffTypeList = new LinkedList();
    private LinkedList packagingList = new LinkedList();
    private LinkedList intlDetailList = new LinkedList();
    private AascShipMethodInfo aascShipMethodBean = null; // ship method details bean object
    private AascShipMethodInfo aascShipMethodInfo = new AascShipMethodInfo(); // aascShipMethodInfo is ship method bean object in which all the linked lists (containing country details ,carrier pay details and shipmethod details) are placed 
    private AascShipMethodInfo aascCarrierInfo = null; // aascCarrierInfo is a ship method bean used to store carrierId and carrierName
    private AascShipMethodInfo aascIntlInfo = null;
    private String statusDesc = "";
    private int opStatus;
    private String errorStatus = "";
    private int locationId = 0; //to store location id
    private int clientId = 0;
    private String intlFlag = "N";
    private static Logger logger = AascLogger.getLogger("AascOracleShipMethodDAO"); // logger object used for issuing logging requests
    private LinkedList shipExecSCSTag = null;
    HashMap validationLookUpHashMap = null;

    LinkedList carrierServiceLookUpList = null;

    String schemaname = "";
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // for creating connection
    
    HashMap<String, ?> map1;

    /** default constructor.*/
    public AascOracleShipMethodDAO() {
    }


    /** This method delete selected Shipmethod row.
     * @param clientId int
     * @param carrierId int
     * @param locationId int
     * @param shipMethodIndex int
     * @param userDefinedShipMethod String
     * @return opStatus int
     */
    public int deleteShipMethod(int clientId, int carrierId, int locationId, 
                                int shipMethodIndex, 
                                String userDefinedShipMethod) {

        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
    
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_carrier_id", carrierId)
                                                                        .addValue("ip_user_shipmethod_meaning", userDefinedShipMethod)
                                                                        .addValue("ip_location_id", locationId)
                                                                        .addValue("ip_ship_method_index", shipMethodIndex);
    
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("delete_shipmethod")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
    
            errorStatus = String.valueOf(out.get("op_error_status"));
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        
        return opStatus;
    }

    /** This method retrieves ship method details and country details from database and 
      places into the bean and inturn bean is placed into linked list 
      and linked list is sent to AascShipMethodList class by setting the linked list to this class.
     *@return aascShipMethodInfo AascShipMethodInfo bean object */
    public AascShipMethodInfo getShipMethodInfo(LinkedList sessionList) {
        
        logger.info("Entered getShipMethodInfo()");
        try {

            Iterator iterator;
            iterator = sessionList.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                if (index == 0) {
                    Integer locationIdSes = 
                        (Integer)iterator.next();
                    if (locationIdSes != null) {
                        locationId = locationIdSes.intValue();
                    } else {
                        locationId = 0;
                    }
                } else if (index == 1) {
                    Integer clientIdSed = (Integer)iterator.next(); 
                    clientId = clientIdSed.intValue() ;
                } else {                //added condition by Y Pradeep
                    iterator.next();
                }
                index = index + 1;
            }

            logger.info("locationId:  " + locationId + "  clientId : " + clientId);

            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("get_ship_method_list")
                                                           .declareParameters(new SqlOutParameter("aasc_get_ship_method_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_carrier_pay_terms", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_country_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_carrier_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_shipExec_SCS_tag", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
            
            //input parameters assignment
            SqlParameterSource inputparam = new MapSqlParameterSource().addValue("ip_location_id", locationId)
                                                                       .addValue("ip_client_id", clientId);

            Map<String, Object> out = simpleJdbcCall.execute(inputparam);
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            statusDesc = nullStrToSpc(String.valueOf(out.get("op_error_status")));
            logger.info("AASC_ERP_CARRIER_PKG.get_ship_method_list opStatus:" + opStatus + "\t errorStatus:" + statusDesc);
            
            if (opStatus == 0) {
                
                    logger.info("Error in retreiving data from the database : " + 
                                statusDesc);
                    aascShipMethodInfo.setStatusDesc(statusDesc);
                    aascShipMethodInfo.setOpStatus(opStatus);
                    return aascShipMethodInfo;
            } 
            else if (opStatus == 2) {  // Added this condition to give alert to user when carrier configuration is not configured  by eshwari
                logger.info("Missing Set Up : opStatus " + opStatus);
                aascShipMethodInfo.setOpStatus(opStatus);
            }
            else {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                aascShipMethodInfo.setOpStatus(opStatus);
            }
            shipExecSCSTag = new LinkedList();
            Iterator shipExecIt= ((ArrayList)out.get("OP_SHIPEXEC_SCS_TAG")).iterator();
            while(shipExecIt.hasNext()){
                map1 = (HashMap<String, ?>)shipExecIt.next();
                shipExecSCSTag.add(map1.get("LOOKUP_VALUE")+"$"+map1.get("DESCRIPTION"));
            }
            
           
           
            Iterator shipmethodIt= ((ArrayList)out.get("OP_SHIP_METHOD_DETAILS")).iterator(); // placing ship method details cursor in resultset     

            while (shipmethodIt.hasNext()) {
                 
                 map1 = (HashMap<String, ?>)shipmethodIt.next();
                 intlFlag = (nullStrToSpc(String.valueOf(map1.get("INTERNATIONAL_FLAG"))));
                 if("Y".equalsIgnoreCase(String.valueOf(map1.get("INTERNATIONAL_FLAG")))){
                 
                     aascIntlInfo = new AascShipMethodInfo();
                     
                     aascIntlInfo.setShipMethodMeaning(nullStrToSpc(String.valueOf(map1.get("USER_SHIPMETHOD_MEANING"))));
                     aascIntlInfo.setCarrierId(((BigDecimal)map1.get("CARRIERID")).intValue());
                     aascIntlInfo.setCarrierName(nullStrToSpc(String.valueOf(map1.get("CARRIER_NAME"))));
                     aascIntlInfo.setConnectShipScsTag(nullStrToSpc(String.valueOf(map1.get("CONNECTSHIP_SCS_TAG"))));
                     aascIntlInfo.setShipmentValidationCode(nullStrToSpc(String.valueOf(map1.get("SHIPMETHOD_VALIDATION_CODE"))));
                     aascIntlInfo.setEnabledFlag(nullStrToSpc(String.valueOf(map1.get("ENABLE_FLAG"))));
                     aascIntlInfo.setAlternateShipMethod(nullStrToSpc(String.valueOf(map1.get("USER_SHIPMETHOD_MEANING"))));
                     aascIntlInfo.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                     aascIntlInfo.setIntlShipFlag(nullStrToSpc(String.valueOf(map1.get("INTERNATIONAL_FLAG"))));
                     
                     intlDetailList.add(aascIntlInfo);
                     
                 }
                 
                 aascShipMethodBean = new AascShipMethodInfo(); // ship method bean object
                 
                 aascShipMethodBean.setShipMethodMeaning(nullStrToSpc(String.valueOf(map1.get("USER_SHIPMETHOD_MEANING"))));
                 aascShipMethodBean.setCarrierId(((BigDecimal)map1.get("CARRIERID")).intValue());
                 aascShipMethodBean.setCarrierName(nullStrToSpc(String.valueOf(map1.get("CARRIER_NAME"))));
                 aascShipMethodBean.setConnectShipScsTag(nullStrToSpc(String.valueOf(map1.get("CONNECTSHIP_SCS_TAG"))));
                 aascShipMethodBean.setShipmentValidationCode(nullStrToSpc(String.valueOf(map1.get("SHIPMETHOD_VALIDATION_CODE"))));
                 aascShipMethodBean.setEnabledFlag(nullStrToSpc(String.valueOf(map1.get("ENABLE_FLAG"))));
                 aascShipMethodBean.setAlternateShipMethod(nullStrToSpc(String.valueOf(map1.get("USER_SHIPMETHOD_MEANING"))));
                 aascShipMethodBean.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                 aascShipMethodBean.setIntlShipFlag(nullStrToSpc(String.valueOf(map1.get("INTERNATIONAL_FLAG"))));
                 
                 shipMethodDetailList.add(aascShipMethodBean);
                 
                // Added the below code to include non international LTL ship methods in international ship methods list
                 if (!(aascShipMethodBean.getIntlShipFlag().equalsIgnoreCase("Y")) && aascShipMethodBean.getCarrierCode() == 999) {
                     intlDetailList.add(aascShipMethodBean);
                 }
                 aascCarrierInfo = new AascShipMethodInfo();
                 
                 aascCarrierInfo.setCarrierId(Integer.parseInt(String.valueOf(map1.get("CARRIERID"))));
                 aascCarrierInfo.setCarrierName(nullStrToSpc(String.valueOf(map1.get("CARRIER_NAME"))));
                 carrierDetailList.add(aascCarrierInfo);

                 shipMethodList.add(nullStrToSpc(String.valueOf(map1.get("USER_SHIPMETHOD_MEANING"))));
                  
            }// end of while loop for OP_SHIP_METHOD_DETAILS
            logger.info("after adding shipmethod details to linked list");
             
            Iterator carrierIt= ((ArrayList)out.get("OP_CARRIER_PAY_TERMS")).iterator(); // placing carrier pay terms cursor
            while (carrierIt.hasNext()) {
                  
                 map1 = (HashMap<String, ?>)carrierIt.next();
                  
                 aascShipMethodBean = new AascShipMethodInfo();
                 aascShipMethodBean.setCarrierPayCode(nullStrToSpc(String.valueOf(map1.get("CARRIERPAYCODE"))));
                 aascShipMethodBean.setCarrierPaymentTerms(nullStrToSpc(String.valueOf(map1.get("CARRIERPAYTERM"))));
                 aascShipMethodBean.setCsCarrierPayTerm(nullStrToSpc(String.valueOf(map1.get("CSCARRIERPAYTERM"))));
                 
                 carrierPayDetails.add(aascShipMethodBean);
                  
            }
            logger.info("carrier pay details are added to linked list");
             
            Iterator countryCodeIt= ((ArrayList)out.get("OP_COUNTRY_SYMBOL_DETAILS")).iterator(); // placing COUNTRY SYMBOL DETAILS            
            while (countryCodeIt.hasNext()) {
                  
                 map1 = (HashMap<String, ?>)countryCodeIt.next();
                  
                 aascShipMethodBean = new AascShipMethodInfo();
                 aascShipMethodBean.setCountryCode(nullStrToSpc(String.valueOf(map1.get("COUNTRY_CODE"))));
                 aascShipMethodBean.setCountryName(nullStrToSpc(String.valueOf(map1.get("COUNTRY_NAME"))));
                 countryDetailList.add(aascShipMethodBean);
            }
             
            Iterator carreriConfigIt= ((ArrayList)out.get("OP_CARRIER_CONFIGURATION")).iterator(); // placing CARRIER CONFIGURATION
             while (carreriConfigIt.hasNext()) {
                   
                  map1 = (HashMap<String, ?>)carreriConfigIt.next();
                  
                  aascShipMethodBean = new AascShipMethodInfo();
                  
                 aascShipMethodBean.setCarrierId(((BigDecimal)map1.get("CARRIER_ID")).intValue());
                 aascShipMethodBean.setCarrierPort(nullStrToSpc(String.valueOf(map1.get("PORT"))));
                 aascShipMethodBean.setCarrierServerIPAddress(nullStrToSpc(String.valueOf(map1.get("SERVER_IP_ADDRESS"))));
                 aascShipMethodBean.setCarrierUserName(nullStrToSpc(String.valueOf(map1.get("USER_NAME"))));
                 aascShipMethodBean.setCarrierPassword(nullStrToSpc(String.valueOf(map1.get("CARRIER_PASSWORD"))));
                 aascShipMethodBean.setCarrierPrefix(nullStrToSpc(String.valueOf(map1.get("PREFIX"))));
                 aascShipMethodBean.setCarrierAccountNumber(nullStrToSpc(String.valueOf(map1.get("ACCOUNT_NUMBER"))));
                 aascShipMethodBean.setMeterNumber(nullStrToSpc(String.valueOf(map1.get("METER_NUMBER"))));
                 aascShipMethodBean.setProtocol(nullStrToSpc(String.valueOf(map1.get("PROTOCOL"))));
                 aascShipMethodBean.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                 aascShipMethodBean.setSupportHazmatShipping(nullStrToSpc(String.valueOf(map1.get("SUPPORT_HAZMAT_SHIPPING"))));
                 aascShipMethodBean.setPrinterPort(nullStrToSpc(String.valueOf(map1.get("PRINTER_PORT"))));
                 
                 aascShipMethodBean.setIntegrationId(nullStrToSpc(String.valueOf(map1.get("INTEGRATION_ID"))));
                 aascShipMethodBean.setStampsPaperSize(nullStrToSpc(String.valueOf(map1.get("PAPER_SIZE"))));
                 aascShipMethodBean.setIntlPrintLayOut(nullStrToSpc(String.valueOf(map1.get("INTL_PRINT_LAYOUT"))));
                 aascShipMethodBean.setNonDeliveryOption(nullStrToSpc(String.valueOf(map1.get("NON_DEL_OPTION"))));
                 aascShipMethodBean.setDhlRegionCode(nullStrToSpc(String.valueOf(map1.get("DHL_REGION_CODE"))));

                 carrierConfigList.add(aascShipMethodBean);
                 
                 if ("Y".equalsIgnoreCase(intlFlag)) {
                        
                        aascIntlInfo.setAccessLicenseNumber(nullStrToSpc(String.valueOf(map1.get("ACCESS_LICENSE_NUMBER"))));
                        aascIntlInfo.setLabelStock(nullStrToSpc(String.valueOf(map1.get("LABEL_STOCK_ORIENTATION"))));
                        aascIntlInfo.setDocTab(nullStrToSpc(String.valueOf(map1.get("DOC_TAB_LOCATION"))));
                        
//                        intlDetailList.add(aascIntlInfo);
                }
                
                 aascShipMethodBean.setAccessLicenseNumber(nullStrToSpc(String.valueOf(map1.get("ACCESS_LICENSE_NUMBER"))));
                 aascShipMethodBean.setLabelStock(nullStrToSpc(String.valueOf(map1.get("LABEL_STOCK_ORIENTATION"))));
                 aascShipMethodBean.setDocTab(nullStrToSpc(String.valueOf(map1.get("DOC_TAB_LOCATION"))));
                 aascShipMethodBean.setNonDiscountedCost(String.valueOf(map1.get("NEGOTIATED_RATES")));
                 aascShipMethodBean.setAcctNegotiatedCertified(String.valueOf(map1.get("ACCOUNT_NUMBER_NEGOTIATED_FLAG")));
                 aascShipMethodBean.setCarrierMode(String.valueOf(map1.get("CMODE")));
                 aascShipMethodBean.setDomain(String.valueOf(map1.get("DOMAIN")));
                  
             }
             logger.info("carrier config details are added to linked list");
             logger.info("shipMethodList::"+shipMethodList);
            logger.info("shipMethodDetailList::"+shipMethodDetailList);
             aascShipMethodInfo.setShipMethodList(shipMethodList); // set the linked list contatining ship method names to AascShipMethodInfo
             aascShipMethodInfo.setShipMethodDetailList(shipMethodDetailList); // set the linked list containing ship method details to AascShipMethodIndo
             aascShipMethodInfo.setCountryDetailList(countryDetailList); // set the linked list containing country details to  AascShipMethodInfo
             aascShipMethodInfo.setCarrierDetailList(carrierDetailList); // set the linked list containing carrier details to AascShipMethodInfo
             aascShipMethodInfo.setCarrierPayDetails(carrierPayDetails); // set the linked list containing carrier pay details to  AascShipMethodInfo
             aascShipMethodInfo.setCarrierConfigList(carrierConfigList); // set the linked list containing carrier config details to AascShipMethodInfo
             aascShipMethodInfo.setDropOffTypeList(dropOffTypeList);
             aascShipMethodInfo.setPackagingList(packagingList);
             aascShipMethodInfo.setIntlDetailList(intlDetailList);

        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
            e.printStackTrace();
        } 
        logger.info("Exit from getShipMethodInfo()");
        
        return aascShipMethodInfo;
    } // end of method getShipMethodInfo()


    /** This method save Shipmethod mapping details from shipmethodlist.
     * @param shipmethodlist LinkedList
     * @return opStatusList List
     */
    public List saveShipMethodInfo(LinkedList shipmethodlist) {

        logger.info("Entered saveShipMethodInfo()");
        List opStatusList = new ArrayList();
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
    
            ListIterator lit = shipmethodlist.listIterator();
    
            while (lit.hasNext()) {
    
                aascShipMethodInfo = (AascShipMethodInfo)lit.next();
                simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                               .withProcedureName("save_shipmethod_list")
                                                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                               .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
    
    
                // input parameters assignment
                SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", aascShipMethodInfo.getClientid())
                                                                            .addValue("ip_carrier_id", aascShipMethodInfo.getCarrierId())
                                                                            .addValue("ip_user_shipmethod_meaning", aascShipMethodInfo.getUserdefinedShipmethod())
                                                                            .addValue("ip_enable_flag", aascShipMethodInfo.getEnabledFlag())
                                                                            .addValue("ip_international_flag", aascShipMethodInfo.getIntlShipFlag())
                                                                            .addValue("ip_connectship_scs_tag", aascShipMethodInfo.getCarrierServiceLevel())
                                                                            .addValue("ip_shipmethod_validation_code", aascShipMethodInfo.getValidation())
                                                                            .addValue("ip_user_id", aascShipMethodInfo.getUserid())
                                                                            .addValue("ip_carrier_name", aascShipMethodInfo.getCarrierName())
                                                                            .addValue("ip_location_id", aascShipMethodInfo.getLocationid())
                                                                            .addValue("ip_ship_method_index", aascShipMethodInfo.getShipmethodIndex());
    
                // end of input params assignment
    
    
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
                opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                errorStatus = String.valueOf(out.get("op_error_status"));
    
                aascShipMethodInfo.setOpStatus(opStatus);
                aascShipMethodInfo.setErrorStatus(errorStatus);
                logger.info(aascShipMethodInfo.getShipmethodIndex() + ":::opStatus ::::::" + opStatus + "error_status:::::" + errorStatus);
                opStatusList.add(errorStatus);
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        } 
        logger.info("Exit from saveShipMethodInfo()");

        return opStatusList; // returns the AASC_SHIP_CONSOLE.save_shipmethod_list procedure's opstatus value
    } // closing saveShipMethodInfo method


    /**
     * This method calls AASC_ERP_SHIP_CONSOLE_PKG.get_lookup_value_details procedure from the database
     * which contains 2 input parameters and 3 output parameters with first parameter being input parameter 
     * carrierCode and Second input parameter is lookupCode and third parameter is a cursor containing  
     * lookup code and lookup code description and fourth  and fifth parameters are output status and return status.         
     * @param carrierCode int.
     * @param lookupCode String.
     * @return the hashMap - hash map containing look up codes
     * and their description
     */
    public HashMap getLookUpValues(int carrierCode, String lookupCode, String carrierMode ) {

        logger.info("Entered getLookUpValues()");
     
        HashMap hashMap = new HashMap();
        
        try{
            DataSource datasource = connectionFactory.createDataSource();
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                                           .withProcedureName("get_lookup_value_details")
                                                           .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR))
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
                                                           
            //input parameters assignment
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_lookup_type", lookupCode)
                                                                       .addValue("IP_CARRIER_MODE",carrierMode);  // vikas added carrierMOde for getting carrier service levels based on Mode
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
            
            logger.info("AASC_SHIP_CONSOLE_PKG.get_lookup_value_details opStatus:" +opStatus + "\t errorStatus:" + errorStatus);
            
            if (opStatus == 1) {
            
                try{
                
                    Iterator lookupDetailsIt= ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator(); // placing LOOKUP DETAILS
                    while (lookupDetailsIt.hasNext()) {
                           
                        map1 = (HashMap<String, ?>)lookupDetailsIt.next();
                        
                        hashMap.put(map1.get("MEANING"),map1.get("LOOKUP_VALUE"));      
                          
                    }
                    
                } catch (Exception e) {
                        logger.severe("Exception::"+e.getMessage());
                }
                
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from getLookUpValues()");
        
        return hashMap;

    } // end of getLookUpValues()

    /**
     * This method calls AASC_ERP_SHIP_CONSOLE_PKG.get_lookup_details procedure from the database
     * which contains 1 input parameters and 3 output parameters with first  
     * input parameter is lookup code and second parameter is a cursor contining  
     * look up code and their description and third  and fourth parameters are output 
     * status and return status.            
     * @param lookupCode.
     * @return the hashMap - hash map containing looku up codes
     * and their description
     */
    public HashMap getLookUpDetails(String lookupCode) {
        logger.info("Entered getLookUpDetails()");

        HashMap hashMap = new HashMap();
        
        try {
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object

            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_lookup_type", lookupCode);
            
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                                           .withProcedureName("get_lookup_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
                
            logger.info("AASC_SHIP_CONSOLE_PKG.get_lookup_details opStatus:" + opStatus + "\t errorStatus:" + errorStatus);
                
            Iterator lookupDetailsIt = ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();

            if (opStatus == 1) {
                while (lookupDetailsIt.hasNext()) {
                    map1 = (HashMap<String, ?>)lookupDetailsIt.next();
                    hashMap.put(map1.get("meaning"), map1.get("lookup_code"));
                } // closing the while loop
            } // closing the second if block
         } catch(Exception e){
             logger.severe("Exception e : "+e.getMessage());
         }
        logger.info("Exit from getLookUpDetails()");
        
        return hashMap;

    } // end of getLookUpDetails()


    public LinkedList getShipMethodMappingInfo(int carrierId, int clientId) {
        
        AascShipMethodInfo aascShipMethodInfoBean;
        LinkedList shipMethodDetailList1 = new LinkedList();
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object

            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_id", carrierId)
                                                                        .addValue("ip_client_id", clientId);
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("get_ship_method_mapping")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_ship_method_details", OracleTypes.CURSOR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
            ArrayList ar = (ArrayList)out.get("OP_SHIP_METHOD_DETAILS");

            Iterator it = ((ArrayList)out.get("OP_SHIP_METHOD_DETAILS")).iterator();
    
            if (opStatus == 1 && !ar.isEmpty()) {
                while (it.hasNext()) {
    
                    aascShipMethodInfoBean = new AascShipMethodInfo();
                    map1 = (HashMap<String, ?>)it.next();
    
                    aascShipMethodInfoBean.setCarrierName((String)map1.get("CARRIER_NAME"));
                    aascShipMethodInfoBean.setCarrierId(((BigDecimal)map1.get("CARRIERID")).intValue());
                    aascShipMethodInfoBean.setCarrierServiceLevel((String)map1.get("CONNECTSHIP_SCS_TAG"));
                    aascShipMethodInfoBean.setValidation((String)map1.get("SHIPMETHOD_VALIDATION_CODE"));
                    aascShipMethodInfoBean.setEnabledFlag((String)map1.get("ENABLE_FLAG"));
                    aascShipMethodInfoBean.setUserdefinedShipmethod((String)map1.get("USER_SHIPMETHOD_MEANING"));
                    aascShipMethodInfoBean.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                    aascShipMethodInfoBean.setIntlShipFlag((String)map1.get("INTERNATIONAL_FLAG"));
                    aascShipMethodInfoBean.setShipmethodIndex(((BigDecimal)map1.get("SHIP_METHOD_INDEX")).intValue());
    
    
                    shipMethodDetailList1.add(aascShipMethodInfoBean);
    
                }
            } else {
                shipMethodDetailList1 = null;
            }    
    
            logger.info("Values are set successfully to aascShipMethodInfo bean");
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from getShipMethodInfo()");
        
        return shipMethodDetailList1;
    } // end of method


     /** This to get carrier service level and user defined shipmethod.
      * @return LinkedList  
      * @param clientIdInt int
      * @param locationID int
      * @param intlFlag String
      */
     public LinkedList getFreightShopDetails(int clientIdInt, int locationID, String intlFlag){
        logger.info("Entered getCarrierServiceLevels()");
        LinkedList fsDetails = new LinkedList();
        try{
         DataSource datasource = connectionFactory.createDataSource();
         
         simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                        .withProcedureName("GET_SHIPMETHOD_CODE")
                                                        .declareParameters(new SqlOutParameter("AASC_ERP_SHIPMETHOD_CODES", OracleTypes.CURSOR))
                                                        .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("OP_ERR_MSG", Types.VARCHAR));
         
         //input parameters assignment
         SqlParameterSource inputparam = new MapSqlParameterSource().addValue("IP_INTERNATIONAL_FLAG", intlFlag)
                                                                    .addValue("IP_LOCATION_ID", locationID)
                                                                    .addValue("IP_CLIENT_ID", clientIdInt);

         Map<String, Object> out = simpleJdbcCall.execute(inputparam);
//         System.out.println("out::::"+out);
         opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
         statusDesc = nullStrToSpc(String.valueOf(out.get("OP_ERR_MSG")));
         logger.info("AASC_ERP_CARRIER_PKG.GET_SHIPMETHOD_CODE opStatus:" + opStatus + "\t errorStatus:" + statusDesc);
//         System.out.println("(ArrayList)out.get(\"OP_SHIPMETHOD_CODES\")::::"+((ArrayList)out.get("OP_SHIPMETHOD_CODES")).size());
         Iterator serviceLevels = ((ArrayList)out.get("OP_SHIPMETHOD_CODES")).iterator();
//         System.out.println("servicelves::::"+serviceLevels);
         
            if (opStatus == 100) {
                while (serviceLevels.hasNext()) {
                    map1 = (HashMap<String, ?>)serviceLevels.next();
//                    System.out.println("map1;::::"+map1);
//                    System.out.println("carrier code::::"+(String)map1.get("CARRIER_CODE"));
//                    System.out.println("shipmethod meanign:::::"+(String)map1.get("SHIPMETHOD_MEANING"));
                    fsDetails.add((String)map1.get("CARRIER_NAME"));
                    fsDetails.add((String)map1.get("CARRIER_CODE"));
                    fsDetails.add((String)map1.get("SHIPMETHOD_MEANING"));
                } // closing the while loop
            } // closing the second if block
            
        }catch(Exception e){
        
            logger.severe("Exception :"+e.getMessage());
        }
         logger.info("Exit getCarrierServiceLevels()");
         return fsDetails;
     }


    
     /** This method can replace the null values with nullString
      * @return String that is ""
      * @param obj of type Object
      */
     String nullStrToSpc(Object obj) {
         String spcStr = "";
         if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
             return spcStr;
         } else {
             return obj.toString();
         }
     }

   


}// end of class
