package com.aasc.erp.model;

import com.aasc.erp.bean.AascCarrierConfigurationBean;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.jdbc.object.StoredProcedure;

/**
 * AascOracleCarrierConfigurationDAO class is DAO factory class class for Carrier Configuration.
 * @version   2
 * @author    Venkateswaramma Malluru
 * @History
 *
 * 19/12/2014   Y Pradeep   Added try, catch to all methods.
 * 24/12/2014   Y Pradeep   Commited code after Self Audit.
 * 31/12/2014   Eshwari M   Modified getCarrierLookUpValues to call get_lookup_value_details procedure from 
 *                          AASC_ERP_SHIP_CONSOLE_PKG instead of AASC_ERP_CARRIER_PKG as it is a duplicate
 * 07/01/2015   Y Pradeep   Removed, commented unused loggers and removed variable declaration inside loop.
 */
 
public class AascOracleCarrierConfigurationDAO extends StoredProcedure implements AascCarrierConfigurationDAO {
    int opStatus = 0;
    String errorStatus = "";
    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // for creating connection


    private static Logger logger = 
        AascLogger.getLogger("AascOracleAccountNumbersInfoDAO");


    public AascOracleCarrierConfigurationDAO() {
    }


    /** This method loads Look Up Details for the provided lookupCode
     * @param lookupCode String
     * @return hm hashmap
     */
    public HashMap getLookUpDetails(String lookupCode) {
        
        logger.info("Entered getLookUpDetails()");
        HashMap hm=new HashMap();
        try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_lookup_type",lookupCode);
                        
                        setQueryTimeout(100);
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                    .withProcedureName("get_lookup_details")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            
//            logger.info("After Execute");
//            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("op_error_status"));   
            Iterator it= ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
//             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                
                hm.put(map1.get("MEANING"),map1.get("LOOKUP_CODE"));
                map1.clear();;
            }
//            logger.info("carier codes values :::"+hm);
                
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("exit from getLookUpDetails()");
        
        return hm;


    } // end of getLookUpDetails()


    /** This method loads Carrier Look Up Values for lookUpCode and carrierCode
     * @param carrierCode int
     * @param lookUpCode String
     * @return hm hashmap
     */
    public HashMap getCarrierLookUpValues(int carrierCode, String lookUpCode, String carrierMode){
        logger.info("Entered getCarrierLookUpValues");
        logger.info("carrierCode ::::::::"+ carrierCode +" && lookUpCode:::::"+lookUpCode +" && carrierMode:::123::"+carrierMode);
        HashMap hm=new HashMap();
        
            try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                                                         .addValue("ip_lookup_type",lookUpCode)
                                                                         .addValue("IP_CARRIER_MODE",carrierMode); // vikas added carrierMOde for getting carrier service levels based on Mode
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                    .withProcedureName("get_lookup_value_details")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            
            
//            logger.info("After Execute");
//            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("op_error_status"));   
            Iterator it= ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
//             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                
                hm.put(map1.get("LOOKUP_VALUE"),map1.get("LOOKUP_VALUE")); //map1.get("MEANING"));
                map1.clear();
            }
//            logger.info("carier codes values :::"+hm);
            
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exist from getCarrierLookUpValues");
        
        return hm;
    }

    /** This method get's Carrier Code value and Carrier Name
     * @return hm hashmap
     */
    public HashMap getCarrierCodeValues(){
        logger.info("Entered getCarrierCodeValues");
        HashMap hm=new HashMap();
        
        try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
    
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_CARRIER_PKG")
                                    .withProcedureName("get_carrier_codes")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_carrier_codes", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute();
            
//            logger.info("After Execute");
//            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("op_error_status"));   
            Iterator it= ((ArrayList)out.get("OP_AASC_CARRIER_CODES")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
//             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                
                hm.put(map1.get("CARRIER_CODE"),map1.get("CARRIER_NAME"));
                map1.clear();
            }
//            logger.info("carier codes values :::"+hm.toString());
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exist from getCarrierCodeValues");
        
        return hm;
    }


    /** This method get Carrier Names
     * @return hm hashmap
     */
    public HashMap getCarrierNames(){
        
        logger.info("Entered getCarrierNames");
        HashMap hm=new HashMap();
        
            try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
    
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_CARRIER_PKG")
                                    .withProcedureName("get_carrier_names")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_carrier_names", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute();
            
//            logger.info("After Execute");
//            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("OP_ERROR_STATUS"));   
            Iterator it= ((ArrayList)out.get("OP_CARRIER_NAMES")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
//             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                
                hm.put(map1.get("LOOKUP_CODE"),map1.get("LOOKUP_CODE"));
                map1.clear();
            }
//            logger.info("carier codes :::"+hm);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        } 
        logger.info("Exist from getCarrierNames");
        
        return hm;
    }

    /** This method is for saving Carrier Configuration using aascCarrierConfigurationBean and userId
     * @param aascCarrierConfigurationBean AascCarrierConfigurationBean
     * @param userId int
     * @return opStatus int
     */
    public int saveCarrierConfigurationInfo(AascCarrierConfigurationBean  aascCarrierConfigurationBean, int userId)
    {

      logger.info("Entered saveCarrierConfigurationInfo()");

       /* logger.info("getCustomerName ::::::::::" + 
                    aascCarrierConfigurationBean.getCustomerName());
        logger.info("getLocationId ::::::::::" + 
                    aascCarrierConfigurationBean.getLocationId());
        logger.info(" Carrier Code  :::::::" + 
                    aascCarrierConfigurationBean.getCarrierCodeValue());
        logger.info("getCarrierName :::::::" + 
                    aascCarrierConfigurationBean.getCarrierName());
        logger.info("getPrefix ::::::::::" + 
                    aascCarrierConfigurationBean.getPrefix());
        logger.info("getProtocol :::::::" + 
                    aascCarrierConfigurationBean.getProtocol());
        logger.info("getPort ::::::::::::" + 
                    aascCarrierConfigurationBean.getPort());
        logger.info("getServerIpAddress ::::::::" + 
                    aascCarrierConfigurationBean.getServerIpAddress());
        logger.info("getUserName :::::::::" + 
                    aascCarrierConfigurationBean.getUserName());
        logger.info("getPassword :::::::::::::::" + 
                    aascCarrierConfigurationBean.getPassword());
        logger.info("getCarrierCodeValue:::::::::::" + 
                    aascCarrierConfigurationBean.getCarrierCodeValue());
        logger.info("getLabelStock:::::::::::::::" + 
                    aascCarrierConfigurationBean.getLabelStock());
        logger.info("getDocTab::::::::::" + 
                    aascCarrierConfigurationBean.getDocTab());
        logger.info("getUserId:::::::::" + 
                    aascCarrierConfigurationBean.getUserId());
        logger.info("getEmailNotificationFlag::::::::::::::" + 
                    aascCarrierConfigurationBean.getEmailNotificationFlag());
        logger.info("getSenderEmailAddress:::::" + 
                    aascCarrierConfigurationBean.getSenderEmailAddress());
        logger.info("getReferenceFlag1:::::::::::::" + 
                    aascCarrierConfigurationBean.getReferenceFlag1());
        logger.info("getReferenceFlag2:::::::::" + 
                    aascCarrierConfigurationBean.getReferenceFlag2());
        logger.info("getShipNotificationFlag::::" + 
                    aascCarrierConfigurationBean.getShipNotificationFlag());
        logger.info("getExceptionNotificationFlag::::::::::" + 
                    aascCarrierConfigurationBean.getExceptionNotificationFlag());
        logger.info("getDeliveryNotificationFlag::::::::::" + 
                    aascCarrierConfigurationBean.getDeliveryNotificationFlag());
        logger.info("getFormatType::::::::::::" + 
                    aascCarrierConfigurationBean.getFormatType());
        logger.info("getEnableFlag:::::::::" + 
                    aascCarrierConfigurationBean.getEnableFlag());
        logger.info("getCarrierMode:::::::::::" + 
                    aascCarrierConfigurationBean.getCarrierMode());
        logger.info("getDomain :::::::::" + 
                    aascCarrierConfigurationBean.getDomain());
        logger.info("getSalesOrderNumber :::::::::::::" + 
                    aascCarrierConfigurationBean.getSalesOrderNumber());
        logger.info("getCarrierName:::::::" + 
                    aascCarrierConfigurationBean.getCarrierName());
        logger.info("getDeliveryItemNumbers :::::::" + 
                    aascCarrierConfigurationBean.getDeliveryItemNumbers());
        logger.info("getSupportHazmatShipping ::::::::::" + 
                           aascCarrierConfigurationBean.getSupportHazmatShipping());
        logger.info("getClientId :::::::::" + 
                    aascCarrierConfigurationBean.getClientId());
        logger.info("getPdPort :::::::::" + 
                    aascCarrierConfigurationBean.getPdPort());
        logger.info("op900format :::::::::" + 
                    aascCarrierConfigurationBean.getOp900Format()); */
        try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
    
             simpleJdbcCall = new SimpleJdbcCall(datasource)
                            .withCatalogName("AASC_ERP_CARRIER_PKG")
                            .withProcedureName("save_carrier_configuration")
                            .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                            .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));   
                            
                            
                            // input parameters assignment
                             SqlParameterSource inputparams = 
                             new MapSqlParameterSource().addValue("ip_carrier_id",aascCarrierConfigurationBean.getCarrierId())
                                                                                        .addValue("ip_location_id",aascCarrierConfigurationBean.getLocationId())
                                                                                         .addValue("ip_carrier_name",aascCarrierConfigurationBean.getCarrierName())
                                                                                         .addValue("ip_prefix",aascCarrierConfigurationBean.getPrefix())
                                                                                         .addValue("ip_protocol",aascCarrierConfigurationBean.getProtocol())
                                                                                         .addValue("ip_port",aascCarrierConfigurationBean.getPort())
                                                                                         .addValue("ip_server_ip_address",aascCarrierConfigurationBean.getServerIpAddress())
                                                                                         .addValue("ip_username",aascCarrierConfigurationBean.getUserName())
                                                                                         .addValue("ip_password",aascCarrierConfigurationBean.getPassword())
                                                                                         .addValue("ip_carrier_code",aascCarrierConfigurationBean.getCarrierCodeValue())
                                                                                         .addValue("ip_label_stock_orientation",aascCarrierConfigurationBean.getLabelStock())
                                                                                         .addValue("ip_doc_tab_location",aascCarrierConfigurationBean.getDocTab())
                                                                                         .addValue("ip_user_id",aascCarrierConfigurationBean.getUserId())
                                                                                         .addValue("ip_email_notification_flag",aascCarrierConfigurationBean.getEmailNotificationFlag())
                                                                                         .addValue("ip_sender_email_address",aascCarrierConfigurationBean.getSenderEmailAddress())
                                                                                         .addValue("ip_reference1_flag",aascCarrierConfigurationBean.getReferenceFlag1())
                                                                                         .addValue("ip_reference2_flag",aascCarrierConfigurationBean.getReferenceFlag2())
                                                                                         .addValue("ip_ship_notification_flag",aascCarrierConfigurationBean.getShipNotificationFlag())
                                                                                         .addValue("ip_exception_notification",aascCarrierConfigurationBean.getExceptionNotificationFlag())
                                                                                         .addValue("ip_delivery_notification",aascCarrierConfigurationBean.getDeliveryNotificationFlag())
                                                                                         .addValue("ip_format_type",aascCarrierConfigurationBean.getFormatType())
                                                                                         .addValue("ip_enabled_flag",aascCarrierConfigurationBean.getEnableFlag())
                                                                                         .addValue("ip_carrier_mode",aascCarrierConfigurationBean.getCarrierMode())
                                                                                         .addValue("ip_domain",aascCarrierConfigurationBean.getDomain())
                                                                                         .addValue("ip_SALES_ORDER_NUMBER",aascCarrierConfigurationBean.getSalesOrderNumber())
                                                                                         .addValue("ip_CUSTOMER_NAME",aascCarrierConfigurationBean.getCustomerName())
                                                                                         .addValue("IP_DELIVERY_ITEM_NUMBER",aascCarrierConfigurationBean.getDeliveryItemNumbers())
                                                                                         .addValue("ip_SUPPORT_HAZMAT_SHIPPING",aascCarrierConfigurationBean.getSupportHazmatShipping())
                                                                                         .addValue("ip_RECIPIENT_EMAIL_ADDRESS1",aascCarrierConfigurationBean.getRecepientEmailAddress1())
                                                                                         .addValue("ip_RECIPIENT_EMAIL_ADDRESS2",aascCarrierConfigurationBean.getRecepientEmailAddress2())
                                                                                         .addValue("ip_RECIPIENT_EMAIL_ADDRESS3",aascCarrierConfigurationBean.getRecepientEmailAddress3())
                                                                                         .addValue("ip_RECIPIENT_EMAIL_ADDRESS4",aascCarrierConfigurationBean.getRecepientEmailAddress4())
                                                                                         .addValue("ip_RECIPIENT_EMAIL_ADDRESS5",aascCarrierConfigurationBean.getRecepientEmailAddress5())
                                                                                         .addValue("IP_CLIENT_ID",aascCarrierConfigurationBean.getClientId())
                                                                                         .addValue("ip_printer_port", aascCarrierConfigurationBean.getPdPort())
                                                                                         .addValue("ip_op900_format",aascCarrierConfigurationBean.getOp900Format())
                                                                                         
                                                                                         .addValue("ip_INTEGRATION_ID",aascCarrierConfigurationBean.getIntegrationId())
                                                                                           .addValue("ip_PAPER_SIZE",aascCarrierConfigurationBean.getPaperSize())
                                                                                            .addValue("ip_INTL_PRINT_LAYOUT",aascCarrierConfigurationBean.getIntlPrintLayout())
                                                                                             .addValue("ip_NON_DEL_OPTION",aascCarrierConfigurationBean.getNonDelivery())
                                                                                            .addValue("ip_dhl_region_code",aascCarrierConfigurationBean.getDhlRegionCode());  ;  
            
                            // end of input params assignment
                            
                            
                            
             Map<String,Object> out = simpleJdbcCall.execute(inputparams);
             
//             logger.info("After Execute");
//             logger.info("out ::: "+out.toString());
             opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
             errorStatus =  String.valueOf(out.get("op_error_status"));
            logger.info("error status from saveCarrierConfigurationInfo() ::::"+errorStatus);
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }    

        logger.info("Exit from saveCarrierConfigurationInfo()");
        
        return opStatus; // returns the AASC_SHIP_CONSOLE_PKG.save_carrier_configuration procedure's opstatus value

    } // closing the saveCarrierConfigurationInfo method 


    /** This method loads Carrier Configuration details using location id, carrier name and client id
     * @param aascCarrierConfigurationBean AascCarrierConfigurationBean
     * @return aascCarrierConfigurationBean AascCarrierConfigurationBean
     */
    public AascCarrierConfigurationBean getCarrierConfigurationDetails(AascCarrierConfigurationBean aascCarrierConfigurationBean){
        
        logger.info("Entered AascCarrierConfigurationBean");
        try
        {
            logger.info("location id : "+aascCarrierConfigurationBean.getLocationId());
            logger.info("carrier name : "+aascCarrierConfigurationBean.getCarrierName());
            logger.info("client id : "+aascCarrierConfigurationBean.getClientId());
            
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_location_id",aascCarrierConfigurationBean.getLocationId())
                                                                        .addValue("ip_carrier_name",aascCarrierConfigurationBean.getCarrierName())
                                                                        .addValue("ip_client_id",aascCarrierConfigurationBean.getClientId());
    
                       
            simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_CARRIER_PKG")
                                    .withProcedureName("get_carrier_configuration")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_carrier_configuration", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
//            logger.info("After Execute ");
//            logger.info("in out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("op_error_status"));  
            
            Iterator it= ((ArrayList)out.get("OP_CARRIER_CONFIGURATION")).iterator();
//            HashMap hm=new HashMap();
            HashMap<String,?>  map1=null;
            while(it.hasNext()){
               map1 =(HashMap<String,?>)it.next();
            }
//            logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
            aascCarrierConfigurationBean.setCarrierId(((BigDecimal)map1.get("CARRIER_ID")).intValue());
            aascCarrierConfigurationBean.setCarrierName((String)map1.get("CARRIER_NAME"));
            aascCarrierConfigurationBean.setPrefix((String)map1.get("PREFIX"));
            aascCarrierConfigurationBean.setProtocol((String)map1.get("PROTOCOL"));
            aascCarrierConfigurationBean.setIntegrationId((String)map1.get("INTEGRATION_ID"));
           
            if(map1.get("PORT")==null)
                aascCarrierConfigurationBean.setPort(0);
            else 
                aascCarrierConfigurationBean.setPort(((BigDecimal)map1.get("PORT")).intValue());
                
            aascCarrierConfigurationBean.setServerIpAddress((String)map1.get("SERVER_IP_ADDRESS"));
    
            aascCarrierConfigurationBean.setUserName((String)map1.get("USER_NAME"));
            
            if(map1.get("CARRIER_CODE")!=null){
                aascCarrierConfigurationBean.setCarrierCodeValue(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                aascCarrierConfigurationBean.setLabelStock((String)map1.get("LABEL_STOCK_ORIENTATION"));
                aascCarrierConfigurationBean.setDocTab((String)map1.get("DOC_TAB_LOCATION"));
                aascCarrierConfigurationBean.setEmailNotificationFlag((String)map1.get("EMAIL_NOTIFICATION_FLAG"));
                aascCarrierConfigurationBean.setSenderEmailAddress((String)map1.get("SENDER_EMAIL_ADDRESS"));
                aascCarrierConfigurationBean.setReferenceFlag1((String)map1.get("REFERENCE1_FLAG"));
                aascCarrierConfigurationBean.setReferenceFlag2((String)map1.get("REFERENCE2_FLAG"));
                aascCarrierConfigurationBean.setShipNotificationFlag((String)map1.get("SHIP_NOTIFICATION_FLAG"));
                aascCarrierConfigurationBean.setExceptionNotificationFlag((String)map1.get("EXCEPTION_NOTIFICATION"));
                aascCarrierConfigurationBean.setDeliveryNotificationFlag((String)map1.get("DELIVERY_NOTIFICATION"));
                aascCarrierConfigurationBean.setFormatType((String)map1.get("FORMAT_TYPE"));
                aascCarrierConfigurationBean.setEnableFlag((String)map1.get("ENABLED_FLAG"));
                aascCarrierConfigurationBean.setCarrierMode((String)map1.get("CMODE"));
                aascCarrierConfigurationBean.setDomain((String)map1.get("DOMAIN"));
                aascCarrierConfigurationBean.setSalesOrderNumber((String)map1.get("SALES_ORDER_NUMBER"));
                aascCarrierConfigurationBean.setCarrierName((String)map1.get("CARRIER_NAME"));
                aascCarrierConfigurationBean.setDeliveryItemNumbers((String)map1.get("DELIVERY_ITEM_NUMBER"));
                aascCarrierConfigurationBean.setCustomerName((String)map1.get("CUSTOMER_NAME"));
        
                aascCarrierConfigurationBean.setSupportHazmatShipping((String)map1.get("SUPPORT_HAZMAT_SHIPPING"));
                aascCarrierConfigurationBean.setRecepientEmailAddress1((String)map1.get("RECIPIENT_EMAIL_ADDRESS1"));
                aascCarrierConfigurationBean.setRecepientEmailAddress2((String)map1.get("RECIPIENT_EMAIL_ADDRESS2"));
                aascCarrierConfigurationBean.setRecepientEmailAddress3((String)map1.get("RECIPIENT_EMAIL_ADDRESS3"));
                aascCarrierConfigurationBean.setRecepientEmailAddress4((String)map1.get("RECIPIENT_EMAIL_ADDRESS4"));
                aascCarrierConfigurationBean.setRecepientEmailAddress5((String)map1.get("RECIPIENT_EMAIL_ADDRESS5"));
                aascCarrierConfigurationBean.setPdPort((String)map1.get("PRINTER_PORT"));   // added label format by Y Pradeep
                aascCarrierConfigurationBean.setOp900Format((String)map1.get("op900Format"));
                aascCarrierConfigurationBean.setPassword((String)map1.get("carrierpassword"));
                
                //Mahesh
                aascCarrierConfigurationBean.setPaperSize((String)map1.get("PAPER_SIZE"));
                aascCarrierConfigurationBean.setIntlPrintLayout((String)map1.get("INTL_PRINT_LAYOUT"));
                aascCarrierConfigurationBean.setNonDelivery((String)map1.get("NON_DEL_OPTION"));
                aascCarrierConfigurationBean.setDhlRegionCode((String)map1.get("DHL_REGION_CODE"));


                //Mahesh End
//                logger.info("op900 format:::"+aascCarrierConfigurationBean.getOp900Format());
//                logger.info("getDeliveryItemNumbers :::::::::::"+ aascCarrierConfigurationBean.getDeliveryItemNumbers());
                //   hm.put(map1.get("CARRIER_ID"),map1.get("LOOKUP_CODE"));
            }
            else{
                aascCarrierConfigurationBean.setCarrierCodeValue(999);
                logger.info("in null bean retrived :::: setting 999");
            }
       
            
//            logger.info("CarrierCodeValue ::::::::::::::::::::"+aascCarrierConfigurationBean.getCarrierCodeValue()); 
//            logger.info("Carrier Name ::::::::::::::::::::"+aascCarrierConfigurationBean.getCarrierName()); 
//            logger.info("Carrier Id ::::::::::::::::::::"+aascCarrierConfigurationBean.getCarrierId());
    
            return aascCarrierConfigurationBean;
        }
        catch(Exception e)
        {
            logger.info("Exception e : "+e.getMessage());
        }
        logger.info("Exist from AascCarrierConfigurationBean");
        
        return aascCarrierConfigurationBean;
    }

    
    /** This method loads Carrier Look Up Values for lookUpCode and carrierCode
     * @param carrierCode int
     * @param lookUpCode String
     * @return hmlist HashMap
     */
    public HashMap getCarrierShipMethodValues(int carrierCode, String lookUpCode, String carrierMode){
        logger.info("Entered getCarrierShipMethodValues");
        logger.info("carrierCode ::::::::"+ carrierCode +" && lookUpCode:::::"+lookUpCode +" && carrierMode:::123::"+carrierMode);
        HashMap carrierhm=new HashMap();
                HashMap shipMethodhm=new HashMap();
                HashMap hmlist = new HashMap();
        
            try{
            DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
             SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                                                         .addValue("ip_lookup_type",lookUpCode)
                                                                         .addValue("IP_CARRIER_MODE",carrierMode); // vikas added carrierMOde for getting carrier service levels based on Mode
                       
                     simpleJdbcCall = new SimpleJdbcCall(datasource)
                                    .withCatalogName("AASC_ERP_SHIP_CONSOLE_PKG")
                                    .withProcedureName("get_lookup_value_details")
                                    .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                    .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                    .declareParameters(new SqlOutParameter("aasc_get_lookup_details", OracleTypes.CURSOR));
            
            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
            
            
    //            logger.info("After Execute");
    //            logger.info("out ::: "+out.toString());
            opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));          
            errorStatus =  String.valueOf(out.get("op_error_status"));   
            Iterator it= ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            HashMap<String,?>  map1 = null;
            while(it.hasNext()){
                map1 =(HashMap<String,?>)it.next();
             
    //             logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                
                carrierhm.put(map1.get("LOOKUP_VALUE"),map1.get("LOOKUP_VALUE")); //map1.get("MEANING"));
                shipMethodhm.put(map1.get("LOOKUP_VALUE"),map1.get("DESCRIPTION")); //map1.get("MEANING"));
                map1.clear();
            }
                        hmlist.put("carrierHashMap",carrierhm);
                        hmlist.put("shipMethodHashMap",shipMethodhm);
    //            logger.info("carier codes values :::"+carrierhm);
            
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exist from getCarrierShipMethodValues");
        
        return hmlist;
    }
}
