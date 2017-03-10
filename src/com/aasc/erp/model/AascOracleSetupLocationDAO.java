package com.aasc.erp.model;
/*========================================================================+
@(#)AascOracleUserControlDAO.java 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
 ====================================================================================*/


import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;


import java.sql.Types;

import java.util.ArrayList;
import java.util.Formatter;
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
 * AascOracleSetupLocationDAO is a class which interacts with the database and 
          corresponding bean file for saving and retriving Ship From Location Details.
 * @author      N Srisha
 * @version 1.0
 * @since
 * 
 * history:
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 22/12/2014   Eshwari M   Modified code by removing unwanted SOP's and converted SOP's to loggers
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 19/12/2014   Sunanda K   Added exception handling and removed SOPs,replaced with logger statements
 * 07/01/2015   Eshwari M   Merged Sunanda's code : Modified getAllLocationDetails to return hashmap instead of linkedlist.
 * 20/01/2015   Sunanda K   Removed printStackTrace() and replaced with getMessage,added documentation for for the method nullStrToSpc()
 * 20/01/2015   Suman G     Modified unchecked exception of converting from linked list to object and also logger.info
 * 23/03/2015   Sunanda k   Added code for newly craeted fields adress line 3 and email address
 * */
  
  
public class AascOracleSetupLocationDAO extends StoredProcedure implements AascSetupLocationDAO  {
    private static Logger logger =AascLogger.getLogger("AascOracleSetupLocationDAO");
    Formatter formatter = new Formatter();
    SimpleJdbcCall simpleJdbcCall;

    
    CallableStatement saveLocationCallableStatement, getLocationCallableStatement, editLocationCallableStatement; 
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); // creating the object for AascPackageInfo
    Connection connection = null; // establishing the connection
   
     int opStatus;        // for output status
     String errorStatus;  // for error status       
     
      LinkedList locationList;
      
    /**

     * createLocation Method interacts with the database 
                to save and update Location Details.

     * @param aascSetupLocationBean of AascSetupLocationBean class 
     
     * @return integer 

     */
    public int createOrUpdateLocation(AascSetupLocationBean aascSetupLocationBean)    {
    
        logger.info("Entered createLocation()");
    
    
    DataSource datasource = connectionFactory.createDataSource();
    
    SqlParameterSource inputParams =new  MapSqlParameterSource().addValue("ip_client_id",aascSetupLocationBean.getClientId())
                                                                .addValue("IP_USER_ID",aascSetupLocationBean.getUserId())
                                                                .addValue("IP_LOCATION_NAME",aascSetupLocationBean.getLocationName())
                                                                .addValue("ip_contact_name",aascSetupLocationBean.getContactName())
                                                                .addValue("ip_address1",aascSetupLocationBean.getAddressLine1())
                                                                .addValue("ip_address2",aascSetupLocationBean.getAddressLine2())
                                                                .addValue("ip_address3",aascSetupLocationBean.getAddressLine3())
                                                                .addValue("ip_email_id", nullStrToSpc(aascSetupLocationBean.getEmailAddress()))
                                                                .addValue("ip_city",aascSetupLocationBean.getCity())
                                                                .addValue("ip_state",aascSetupLocationBean.getState())
                                                                .addValue("IP_POSTAL_CODE",aascSetupLocationBean.getPostalCode())
                                                                .addValue("ip_country",aascSetupLocationBean.getCountryCode())
                                                                .addValue("IP_PHONE_NUMBER",aascSetupLocationBean.getPhoneNumber())
                                                                .addValue("IP_LOCATION_ID",aascSetupLocationBean.getLocationId())
                                                                .addValue("IP_ACTIVE_FLAG",aascSetupLocationBean.getLocationStatus());
    
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(datasource)
                                .withCatalogName("AASC_ERP_LOCATION_SETUP_PKG")
                                .withProcedureName("save_update_location_details")
                                .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
    
        Map<String,Object> out = simpleJdbcCall.execute(inputParams);
                        
        logger.info("After Execute");
        logger.info("out ::: "+out.toString());
        
        int opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));  
        
        String error_status =  String.valueOf(out.get("op_error_status"));  
        logger.info("opStatus :::"+opStatus+" && error_status"+error_status);
    
       
        logger.info("Exit from createCustomer()");
     return opStatus;
    } // closing the createOrUpdateLocation method
      
    
    /**

     * getAllLocationDetails Method interacts with the database 
                and retrieves all Location Details.
     * @param clientId int 
     * @return LinkedList 
     */
    public HashMap getAllLocationDetails( int clientId ) {
         logger.info("Entered getAllLocationDetails()");           
         logger.info("clientId :::::::"+clientId);
         locationList=new LinkedList();
         HashMap locationsHashMap = new HashMap();
         try{
         DataSource  datasource = connectionFactory.createDataSource(); // this method returns the datasources object
         SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id",clientId);
         simpleJdbcCall = new SimpleJdbcCall(datasource)
                        .withCatalogName("AASC_ERP_LOCATION_SETUP_PKG")
                        .withProcedureName("get_all_location_details")
                        .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                        .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                        .declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));
         
         Map<String,Object> out = simpleJdbcCall.execute(inputparams);
         
         logger.info("After Execute");
         opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
         String errorStatus = String.valueOf(out.get("op_error_status"));
         locationsHashMap.put("opStatus" ,opStatus) ;
         locationsHashMap.put("errorStatus" ,errorStatus);
         Iterator it = ((ArrayList)out.get("OP_LOCATION_DETAILS")).iterator();



         //HashMap hm=new HashMap();
            HashMap<String,?>  map1 = null;
            AascSetupLocationBean aascSetupLocationBean= null ;
     while(it.hasNext()){
             map1 =(HashMap<String,?>)it.next();
      
             aascSetupLocationBean= new AascSetupLocationBean();
             aascSetupLocationBean.setClientId(clientId);
             //                    aascSetupLocationBean.setUserId(userId);
             aascSetupLocationBean.setLocationId(((BigDecimal)map1.get("LOCATION_ID")).intValue());
             aascSetupLocationBean.setLocationName((String)map1.get("LOCATION_NAME"));
             aascSetupLocationBean.setAddressLine1((String)map1.get("ADDRESS1"));
             aascSetupLocationBean.setAddressLine2((String)map1.get("ADDRESS2"));
             aascSetupLocationBean.setAddressLine3((String)map1.get("ADDRESS3"));
             aascSetupLocationBean.setEmailAddress((String)map1.get("EMAIL_ID"));
             aascSetupLocationBean.setCity((String)map1.get("CITY"));
             aascSetupLocationBean.setState((String)map1.get("STATE_PROVIENCE_CODE"));
             aascSetupLocationBean.setPostalCode((String)map1.get("POSTAL_CODE"));
             aascSetupLocationBean.setCountryCode((String)map1.get("COUNTRY_CODE"));
             aascSetupLocationBean.setPhoneNumber((String)map1.get("PHONE_NUMBER"));
             aascSetupLocationBean.setContactName((String)map1.get("CONTACT_NAME"));  
             aascSetupLocationBean.setLocationStatus((String)map1.get("ACTIVE_FLAG"));
             
             locationList.add(aascSetupLocationBean);
         }
         locationsHashMap.put("locationList", (Object)locationList) ;
  

        } catch (Exception e) {
            logger.severe("Error in closing connection" + e.getMessage());
        }
         logger.info("Exit from getAllLocationDetails()");
         return locationsHashMap;

     } // closing the getAllLocationDetails method
   

    /*
    * This method can replace the null values with nullString
    * @return String that is ""
    * @param obj of type Object
    */

      
      public String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }


}
