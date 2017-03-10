package com.aasc.erp.model;

/*========================================================================+
@(#)AascProfileOptionsDAO.java 08/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
* Author: Jagadish Jain
+===========================================================================*/

import com.aasc.erp.bean.AascProfileOptionsBean;

import com.aasc.erp.util.AascLogger;

import java.sql.Types;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;


import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.math.BigDecimal;

import java.util.Iterator;

import java.util.logging.Logger;

import oracle.jdbc.OracleTypes;



/**
 * AascProfileOptionsDAO is an interface has abstract method 
       which is implemented by the  AascOracleProfileOptionsDAO class.

 * @author      Jagadish Jain
 * @version 1
 * @since
 * History
 * 
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 24/12/2014  Jagadish Jain   Added all code required to retireve/save profile options.Also added code to retrieve location detials based on client for role 1 and role 2
 * 07/01/2015   Y Pradeep      Modifed code to get connection from datasource in getHazMatPkgGroupLookUpValues() method.
 * 20/01/2015   Suman G        Modified HashMap(?,?) to HashMap (String,?).
 * 29/01/2015   Suman G        Added getHazMatLookUpValues() method for #2533.
 * 20/01/2015   Y Pradeep      Modified code for Address Validationa dn Freight Shopping. Removed Average pallet weight and lable printing check box.
 * 26/02/2015   Suman G        Added code for freight shop.
 * 26/02/2015   Y Pradeep      Modified code to change field names of username, password and account number common for Address Validation and Freight Shopping.
 * 10/03/2015   Y Pradeep      Removed getLookUpDetails() method for removing reference1value and reference2value select fields.
 * 17/03/2015   Y Pradeep      Removed label path field from Profile Options.
 * 25/03/2015   Suman G        Modified class level documentation.
 * 25/03/2015   Suman G        Added code to fix #2706.
 * 03/06/2015   Y Pradeep      Added code to save and get Weighing Scale details in Profile Options page.
 * 13/07/2015   Y Pradeep      Added getPrinterSetupDetails() method to configured printers details and place them in session.
 * 21/12/2015   Y Pradeep      Modified code to set null for webservice username, password and account number for freight shopping and address validation fields.
 * 11/03/2016   Y Pradeep      Removed Printer IP Address related code as it is not required.
 */
public class AascOracleProfileOptionsDAO extends StoredProcedure implements AascProfileOptionsDAO {
    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // for creating connection
    private static Logger logger = 
        AascLogger.getLogger("AascOracleProfileOptionsDAO");
    HashMap clientDetailsLookUpHashMap = null;
    private AascProfileOptionsBean aascProfileOptionsInfo; //declaring the variable for storing the object of the AascProfileOptionInfo class

    private int clientId=0;
    private int locationId=0;
    int opStatus; //holds the value of the opStatus
     HashMap hazMatPkgGrpLookUpHashMap = null;

      /**
       * getCarrierPayMethodValues() method of AascOracleProfileOptionsDAO class has no parameters and returns 
       * ArrayList object.This method gets carrier pay method values for lov in profile options page.
       * @param 
       * @return carrierPayMethodList of type ArrayList
       */

    public ArrayList getCarrierPayMethodValues() {
        logger.info("Entered getCarrierPayMethodValues()");
        ArrayList carrierPayMethodList = new ArrayList();
        
        try{
        
        int opStatus = -1;
        String errorStatus = "";

        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object


        simpleJdbcCall = 
                new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_PROFILE_OPTION_PKG").withProcedureName("get_default_carrier_pay_method")
                .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER)).declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("aasc_pay_methods",  OracleTypes.CURSOR));

      
        Map<String, Object> out = simpleJdbcCall.execute();

        logger.info("After Execute");
        opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
         errorStatus = String.valueOf(out.get("OP_ERROR_STATUS"));
        Iterator it = 
            ((ArrayList)out.get("OP_CARRIER_PAY_METHODS")).iterator();

        HashMap<String, ?> map1=new HashMap<String,String>();
        while (it.hasNext()) {
           map1 = (HashMap<String, ?>)it.next();

            if (!(carrierPayMethodList.contains(map1.get("CARRIERPAYTERM")))) {
                carrierPayMethodList.add(map1.get("CARRIERPAYTERM"));
            }


        }


        
        }
        catch(Exception e){
            logger.severe(e.getMessage());
        }
        

        logger.info("Exit from getCarrierPayMethodValues()");

        return carrierPayMethodList;
    }


    /**
     * saveOrUpdateProfileOptions() method of AascOracleProfileOptionsDAO class takes AascProfileOptionsBean object as parameters
     * and returns an integer value.This method is used to save/update the profile options.
     * @param aascProfileOptionsBean AascProfileOptionsBean 
     * @return opStatus of type int
     */

    public int saveOrUpdateProfileOptions(AascProfileOptionsBean aascProfileOptionsBean) {
        logger.info("Entered saveOrUpdateProfileOptions");
        int opStatus = -1;
        try{
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("IP_LOCATION_ID", String.valueOf(aascProfileOptionsBean.getLocationId()));
        map1.put("IP_CLIENT_ID", String.valueOf(aascProfileOptionsBean.getClientId()));
        map1.put("IP_DEFAULT_CARRIER_PAY_METHOD", aascProfileOptionsBean.getDefaultPayMethod());
        map1.put("IP_ENABLE_SAT_FLAG", aascProfileOptionsBean.getEnableSaturdayFlag());
        map1.put("IP_EDIT_SHIP_TO_ADDRESS", aascProfileOptionsBean.getEditShipToAddress());
        map1.put("IP_EDIT_CUST_NAME", aascProfileOptionsBean.getCustomerNameFlag());
        map1.put("IP_EDIT_ADDRESS_LINES", aascProfileOptionsBean.getAddrLinesFlag());
        map1.put("IP_EDIT_CITY", aascProfileOptionsBean.getCityFlag());
        map1.put("IP_EDIT_STATE", aascProfileOptionsBean.getStateFlag());
        map1.put("IP_EDIT_POSTAL_CODE", aascProfileOptionsBean.getPostalCodeFlag());
        map1.put("IP_EDIT_COUNTRY_CODE", aascProfileOptionsBean.getCountryCodeFlag());
        map1.put("IP_SHIPPER_NAME", aascProfileOptionsBean.getShipperName());
        
        if("false".equalsIgnoreCase(aascProfileOptionsBean.getAddresValidation())){
            aascProfileOptionsBean.setAddresValidation("N");
        } else if("true".equalsIgnoreCase(aascProfileOptionsBean.getAddresValidation())){
            aascProfileOptionsBean.setAddresValidation("Y");
        }
        map1.put("IP_ADDRESS_VALIDATION", aascProfileOptionsBean.getAddresValidation());
        map1.put("IP_WS_USER_NAME", "");//aascProfileOptionsBean.getWsUserName());
        map1.put("IP_WS_PASSWORD", "");//aascProfileOptionsBean.getWsPassword());
        map1.put("IP_WS_ACCOUNT_NUMBER", "");//aascProfileOptionsBean.getWsAccountNumber());

        
        if("false".equalsIgnoreCase(aascProfileOptionsBean.getFreightShopping())){
            aascProfileOptionsBean.setFreightShopping("N");
        } else if("true".equalsIgnoreCase(aascProfileOptionsBean.getFreightShopping())){
            aascProfileOptionsBean.setFreightShopping("Y");
        }
        
        map1.put("IP_FREIGHT_SHOPPING", aascProfileOptionsBean.getFreightShopping());
        
        // vikas added code to set the mask account to Y or N
            if("false".equalsIgnoreCase(aascProfileOptionsBean.getMaskAccount())){
                aascProfileOptionsBean.setMaskAccount("N");
            } else if("true".equalsIgnoreCase(aascProfileOptionsBean.getMaskAccount())){
                aascProfileOptionsBean.setMaskAccount("Y");
            }
        map1.put("IP_MASK_ACCOUNT_NUMBER", aascProfileOptionsBean.getMaskAccount());
        // vikas code ended
        map1.put("IP_REFERENCE1", aascProfileOptionsBean.getReference1());
        map1.put("IP_REFERENCE2", aascProfileOptionsBean.getReference2());
        map1.put("IP_COMPANY_NAME",aascProfileOptionsBean.getCompanyName());
        map1.put("IP_WEIGHING_SCALE_NAME",aascProfileOptionsBean.getWeighingScaleName());
        
        
        if("false".equalsIgnoreCase(aascProfileOptionsBean.getWeighingScale())){
            aascProfileOptionsBean.setWeighingScale("N");
        } else if("true".equalsIgnoreCase(aascProfileOptionsBean.getWeighingScale())){
            aascProfileOptionsBean.setWeighingScale("Y");
        }
        map1.put("IP_WEIGHING_SCALE",aascProfileOptionsBean.getWeighingScale());
        
        SqlParameterSource in = new MapSqlParameterSource().addValues(map1);
        //BeanPropertySqlParameterSource
        //    SqlParameterSource in = new BeanPropertySqlParameterSource(aascProfileOptionsBean);


        simpleJdbcCall = 
                new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_PROFILE_OPTION_PKG")
                                              .withProcedureName("save_profile_option")
                                              .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                                              .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR));

        Map<String, Object> out = simpleJdbcCall.execute(in);
        opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
        String error_status = String.valueOf(out.get("OP_ERROR_STATUS"));
        logger.info("opStatus :::::::" + opStatus + " &&  error_status:::::::" + error_status);
        }
        catch(Exception e){
            logger.severe(e.getMessage());
        }

        return opStatus; // returns the AASC_ERP_AASC_.save_profile_option opstatus value
    } // closing the saveProfileOptionsInfo() method



         /**
          * getProfileOptionsBean() method of AascOracleProfileOptionsDAO class takes LinkedList sessionList object as parameter
          * and returns AascProfileOptionsBean object.This method is retrieve the profile options from db based in clientid and locationid.
          * @param sessionList LinkedList
          * @return aascProfileOptionsBean of type AascProfileOptionsBean
          */
    public AascProfileOptionsBean getProfileOptionsBean(LinkedList sessionList) {
        logger.info("Entered getProfileOptionsBean");
        int opStatus = -1;
        AascProfileOptionsBean aascProfileOptionsBean = 
            new AascProfileOptionsBean(); // creating the object of the AascProfileOptionsInfo class

        try{
        
             Iterator iterator;
                    iterator = sessionList.iterator();
            int index = 0;
                    //
                     while (iterator.hasNext())
                            {
                              if (index == 0)
                              {
                                  Integer locationIdStr = 
                                    (Integer) iterator.next(); // Gettin Inventory Org Id from sessionList LinkedList
                                  if (locationIdStr != null)
                                  {

                                    locationId = locationIdStr.intValue();

                                  }
                                  else
                                  {
                                    locationId = 0;
                                  }
                              }
                              else if (index == 1)
                              {
                                    Integer clientIdStr = 
                                      (Integer) iterator.next(); // Gettin Inventory Org Id from sessionList LinkedList
                                    if (clientIdStr != null)
                                    {

                                      clientId = clientIdStr.intValue();

                                    }
                                    else
                                    {
                                      clientId = 0;
                                    }
                              }
                              index = index + 1;
                            }          
        //
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object


             Map<String, Integer> map1 = new HashMap<String, Integer>();
             
             map1.put("IP_LOCATION_ID", locationId);
             map1.put("IP_CLIENT_ID", clientId);



        SqlParameterSource in = new MapSqlParameterSource().addValues(map1);


        simpleJdbcCall = 
                new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_PROFILE_OPTION_PKG")
                .withProcedureName("get_profile_options")
                .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR))
                .declareParameters(new SqlOutParameter("aasc_get_profile_options",OracleTypes.CURSOR))
                .declareParameters(new SqlOutParameter("aasc_get_loc_profile_options",OracleTypes.CURSOR));

        Map<String, Object> out = simpleJdbcCall.execute(in);
        
        logger.info("After Execute");
        opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
        String error_status = String.valueOf(out.get("OP_ERROR_STATUS"));
        ArrayList str = (ArrayList)out.get("OP_PROFILE_OPTIONS");
        ArrayList strForLocBased = (ArrayList)out.get("OP_LOC_PROFILE_OPTIONS");
        
        Iterator it = str.iterator();
        Iterator itForLocBased = strForLocBased.iterator();

        logger.info("str size : "+str.size());
        
        if(str.size()==0){
            aascProfileOptionsBean=new AascProfileOptionsBean();
            aascProfileOptionsBean.setLocationId(locationId);
            aascProfileOptionsBean.setClientId(clientId);
            
        }
        
        

        HashMap<String, ?> resulthm = new HashMap<String,String>();
        while (it.hasNext()) {
            resulthm = (HashMap<String, ?>)it.next();
        }
        
        HashMap<String, ?> resulthmForLocBased = new HashMap<String,String>();
        while (itForLocBased.hasNext()) {
            resulthmForLocBased = (HashMap<String, ?>)itForLocBased.next();
        }
        if(str.size() != 0) 
        {
            aascProfileOptionsBean.setLocationId(((BigDecimal)resulthm.get("LOCATION_ID")).intValue());
            
            aascProfileOptionsBean.setDefaultPayMethod((String)resulthm.get("DEFAULT_CARRIER_PAY_METHOD"));
            aascProfileOptionsBean.setEnableSaturdayFlag((String)resulthm.get("ENABLE_SAT_FLAG"));
            aascProfileOptionsBean.setEditShipToAddress((String)resulthm.get("EDIT_SHIP_TO_ADDRESS"));
    
            aascProfileOptionsBean.setCustomerNameFlag((String)resulthm.get("EDIT_CUST_NAME"));
            aascProfileOptionsBean.setAddrLinesFlag((String)resulthm.get("EDIT_ADDRESS_LINES"));
            aascProfileOptionsBean.setCityFlag((String)resulthm.get("EDIT_CITY"));
            aascProfileOptionsBean.setStateFlag((String)resulthm.get("EDIT_STATE"));
            aascProfileOptionsBean.setPostalCodeFlag((String)resulthm.get("EDIT_POSTAL_CODE"));
            aascProfileOptionsBean.setCountryCodeFlag((String)resulthm.get("EDIT_COUNTRY_CODE"));

            aascProfileOptionsBean.setReference1((String)resulthm.get("REFERENCE1"));
            aascProfileOptionsBean.setReference2((String)resulthm.get("REFERENCE2"));
            aascProfileOptionsBean.setCompanyName((String)resulthm.get("COMPANY_NAME"));
            aascProfileOptionsBean.setWeighingScaleName((String)resulthm.get("WEIGHING_SCALE_NAME"));
            aascProfileOptionsBean.setWeighingScale((String)resulthm.get("WEIGHING_SCALE"));
            aascProfileOptionsBean.setShipperName((String)resulthm.get("SHIPPER_NAME"));
            aascProfileOptionsBean.setMaskAccount((String)resulthm.get("MASK_ACCOUNT_NUMBER_FLAG"));
        }
        if(strForLocBased.size() != 0){
            aascProfileOptionsBean.setClientId(((BigDecimal)resulthmForLocBased.get("CLIENT_ID")).intValue());
            aascProfileOptionsBean.setAddresValidation((String)resulthmForLocBased.get("ADDRESS_VALIDATION_FLAG"));
            aascProfileOptionsBean.setWsUserName("");//(String)resulthmForLocBased.get("WS_USER_NAME"));
            aascProfileOptionsBean.setWsPassword("");//(String)resulthmForLocBased.get("WS_PASSWORD"));
            aascProfileOptionsBean.setWsAccountNumber("");//(String)resulthmForLocBased.get("WS_ACCOUNT_NUMBER"));
            aascProfileOptionsBean.setFreightShopping((String)resulthmForLocBased.get("FREIGHT_SHOPPING_FLAG"));
//            aascProfileOptionsBean.setMaskAccount((String)resulthmForLocBased.get("MASK_ACCOUNT_NUMBER_FLAG")); // vikas added code to get the mask acoount from DB
        }
        logger.info("opStatus :::::::" + opStatus + 
                           " &&  error_status:::::::" + error_status);
                               
            //Adding Printer Setup Details By Y Pradeep
             HashMap printerHashMap;
             printerHashMap = getPrinterSetupDetails(clientId, locationId);
             aascProfileOptionsBean.setAascPrinterInfoHashMap(printerHashMap);// End of printer code.
        }
        catch(Exception e){
            logger.severe(e.getMessage());
        }
        
        logger.info("End of getProfileOptionsBean");
        return aascProfileOptionsBean;
    }


/**
* getClientDetailsLookUpValues() method of AascOracleProfileOptionsDAO class has no paramters
* and returns HashMap object.This method is used to get list of all clients from the db.
* @return clientDetailsLookUpHashMap of type HashMap
*/
     public HashMap getClientDetailsLookUpValues()
     {
       logger.info("Entered getClientDetailsLookUpValues()");
       clientDetailsLookUpHashMap = new HashMap();
        
        try{
         //
         DataSource datasource = 
             connectionFactory.createDataSource(); // this method returns the datasources object


         simpleJdbcCall = 
                 new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_ship_console_pkg")
                 .withProcedureName("get_client_details")
                 .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                 .declareParameters(new SqlOutParameter("OP_ERROR_STATUS", Types.VARCHAR))
                 .declareParameters(new SqlOutParameter("aasc_client_details",OracleTypes.CURSOR));

         Map<String, Object> out = simpleJdbcCall.execute();


         logger.info("After Execute");
         opStatus = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
          ArrayList str = (ArrayList)out.get("OP_CLIENTS");
          Iterator it = str.iterator();
          HashMap<String, ?> resulthm = new HashMap<String,String>();
          
          

         if (opStatus == 1)
         {
            
               while (it.hasNext()) {
                   resulthm = (HashMap<String, ?>)it.next();
                   clientDetailsLookUpHashMap.put((BigDecimal)resulthm.get("CLIENT_ID"), (String)resulthm.get("COMPANY_NAME"));
               }
           
             if(aascProfileOptionsInfo != null)
                 aascProfileOptionsInfo.setClientDetailsHashMap(clientDetailsLookUpHashMap);


           } // closing the if block
           


       logger.info("Exit from getClientDetailsLookUpValues()");
        }
        catch(Exception e){
            logger.severe(e.getMessage());
        }
       return clientDetailsLookUpHashMap;

     } // end of getClientDetailsLookUpValues()
     
     
/**
* getHazMatPkgGroupLookUpValues() method of AascOracleProfileOptionsDAO class has no paramters
* and returns HashMap object.This method is used to get list of all clients from the db.
* @return HashMap
*/
    public HashMap getHazMatPkgGroupLookUpValues() {
    
        logger.info("Entered getHazMatPkgGroupLookUpValues()");
        hazMatPkgGrpLookUpHashMap = new HashMap();
                    
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
             SqlParameterSource inputparams = 
                 new MapSqlParameterSource().addValue("ip_carrier_code", 111)
                                            .addValue("ip_lookup_type","PACK_GRP")
                                            .addValue("IP_CARRIER_MODE","");  // vikas added carrierMOde as null because mode is not necessary here
                                            
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_ship_console_pkg").withProcedureName("get_lookup_value_details").declareParameters(new SqlOutParameter("aasc_get_lookup_details",OracleTypes.CURSOR)).declareParameters(new SqlOutParameter("op_status",Types.INTEGER)).declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            Iterator it = 
                ((ArrayList)out.get("OP_LOOKUP_DETAILS")).iterator();
            
            HashMap<String, ?> map1 = null;
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();
                hazMatPkgGrpLookUpHashMap.put(map1.get("LOOKUP_VALUE"),map1.get("LOOKUP_VALUE"));
            
            }
            
    
            logger.info("AASC_ERP_USER_CONTROL_PKG.get_customer_details opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);
        }
    
        catch (Exception e) {
            logger.severe("exception while closing the countryCodeCallableStatement" + 
                          e);
            
        }
    
        logger.info("Exit from getHazMatPkgGroupLookUpValues()");
        return hazMatPkgGrpLookUpHashMap;
    } // end of getHazMatPkgGroupLookUpValues()
    
     /**
      * This method calls aasc_ship_console_pkg.get_hazmat_material_details procedure from the database
      * which contains 2 input parameters and 3 output parameters with first parameter being input parameter 
      * carrierCode and Second input parameter is lookup code and third parameter is a cursor contining 
      * code and  description and fourth  and fifth parameters are output status and return status. 
      * @param carrierCode.
      * @param hazMatId.
      * @param locationId.
      * @param clientID.
      * @return the hashMap -  hash map containing look up code code and  description/
      */
      public String getHazMatLookUpValues(int carrierCode, String hazMatId, int locationId, int clientID, int userId)
      {

        logger.info("Entered getHazMatLookUpValues()");
        String completeDetails="";
          try
          {
                         DataSource dataSource = connectionFactory.createDataSource(); // this method returns the datasources object
                         SqlParameterSource inputparams = new MapSqlParameterSource()
                                                           .addValue("ip_hazmat_material_id",hazMatId)
                                                           .addValue("ip_carrier_code",carrierCode)
                                                           .addValue("ip_user_id",userId)
                                                           .addValue("ip_client_id",clientID);
            simpleJdbcCall =new SimpleJdbcCall(dataSource).withCatalogName("aasc_erp_ship_console_pkg")
                                 .withProcedureName("get_hazmat_material_details")
                                 .declareParameters(new SqlOutParameter("aasc_hazmat_id_details",OracleTypes.CURSOR))
                                 .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                 .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            

            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));

            String errorStatus = String.valueOf(out.get("op_error_status"));
                         
                    Iterator it = 
                 ((ArrayList)out.get("OP_HAZMAT_MATERIAL_DETAILS")).iterator();
                                 
                   HashMap<String, ?> map1 = null;
                   
        if (carrierCode == 110 || carrierCode == 111){           
             while (it.hasNext()) {
                 map1 = (HashMap<String, ?>)it.next();
                 completeDetails = completeDetails + "*" + 
                                nullStrToSpc((String)map1.get("SHIPPER_CLASS")) + "*" +
                                nullStrToSpc((BigDecimal)map1.get("WEIGHT")) + "*" +
                                nullStrToSpc((String)map1.get("UNITS")) + "*" +
                                nullStrToSpc((String)map1.get("IDENTIFICATION_NUMBER")) + "*" +
                                nullStrToSpc((String)map1.get("DOT_LABELS")) + "*" +
                                nullStrToSpc((String)map1.get("REQUIRED")) + "*" +
                                nullStrToSpc((BigDecimal)map1.get("CARRIER_CODE")) + "*" +
                                nullStrToSpc((String)map1.get("EMERGENCY_CONTACT_NUMBER")) + "*" +
                                nullStrToSpc((String)map1.get("EMERGENCY_CONTACT_NAME")) + "*" +
                                nullStrToSpc((String)map1.get("PACKGING_GROUP")) + "*" +
                                nullStrToSpc((String)map1.get("HAZMAT_ID")) + "*" +             //Shiva modified from BigDecimal to String for issue 3203
                                nullStrToSpc((BigDecimal)map1.get("PACKAGING_COUNT")) + "*" +
                                nullStrToSpc((String)map1.get("PACKAGING_UNITS")) + "*" +
                                nullStrToSpc((String)map1.get("TECHNICAL_NAME")) + "*" ;
             }           
                                 
        }
        else {
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();
                completeDetails = completeDetails + "*" + 
                               nullStrToSpc((String)map1.get("SHIPPER_CLASS")) + "*" +
                               nullStrToSpc((BigDecimal)map1.get("WEIGHT")) + "*" +
                               nullStrToSpc((String)map1.get("UNITS")) + "*" +
                               nullStrToSpc((String)map1.get("IDENTIFICATION_NUMBER")) + "*" +
                               nullStrToSpc((String)map1.get("DOT_LABELS")) + "*" +
                nullStrToSpc((String)map1.get("void_flag")) + "*" +
                nullStrToSpc((BigDecimal)map1.get("CARRIER_CODE")) + "*" +
                               nullStrToSpc((String)map1.get("EMERGENCY_CONTACT_NUMBER")) + "*" +
                               nullStrToSpc((String)map1.get("EMERGENCY_CONTACT_NAME")) + "*" +
                               nullStrToSpc((String)map1.get("PACKGING_GROUP")) + "*" +
                               nullStrToSpc((BigDecimal)map1.get("PACKAGING_COUNT")) + "*" +
                nullStrToSpc((String)map1.get("PACKAGING_UNITS")) + "*" +
                nullStrToSpc((String)map1.get("TECHNICAL_NAME")) + "*" +
                nullStrToSpc((String)map1.get("SIGNATURE_NAME")) + "*" +
                nullStrToSpc((String)map1.get("PACKING_INSTRUCTION")) + "*" +
                nullStrToSpc((String)map1.get("hazmat_material_type")) + "*" +
                nullStrToSpc((String)map1.get("HAZMAT_MATERIAL_ID")) + "*" ;
            }           
            
        }
            logger.info("aasc_erp_ship_console_pkg.get_hazmat_material_details opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);

          } // closing the try block
          catch (Exception e)
          {
            logger.severe("Exception::"+e.getMessage());
          } // closing the catch block


          
        

        logger.info("Exit from getHazMatLookUpValues()");

        return completeDetails;

      } // end of getHazMatLookUpValues()


    HashMap getPrinterSetupDetails(int clientId, int locationId){
    
        HashMap printerSetupDetailsHashMap = new HashMap();
        String printerName = "";
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object

            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId);
           
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_PROFILE_OPTION_PKG")
                                                            .withProcedureName("GET_PRINTER_SETUP")
                                                            .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                            .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("AASC_PRINTER_SETUP_DETAILS", OracleTypes.CURSOR));
           
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
           
            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String  errorStatus = String.valueOf(out.get("op_error_status"));
            ArrayList ar = (ArrayList)out.get("OP_PRINTER_SETUP_DETAILS");
               
            Iterator it = ((ArrayList)out.get("OP_PRINTER_SETUP_DETAILS")).iterator();
            HashMap<String, ?> map1 = null;
            if (opStatus == 1 && !ar.isEmpty()) {
                while (it.hasNext()) {
               
                    map1 = (HashMap<String, ?>)it.next();
                    if("Y".equalsIgnoreCase((String)map1.get("ENABLE_FLAG"))){
                        logger.info("LABEL_FORMAT === "+(String)map1.get("LABEL_FORMAT"));
                        logger.info("PRINTER_NAME === "+(String)map1.get("PRINTER_NAME"));
                        printerName = (String)map1.get("PRINTER_NAME");
                        printerSetupDetailsHashMap.put((String)map1.get("LABEL_FORMAT"), printerName);
                    }
                }
            } else {
                printerSetupDetailsHashMap = null;
            }    
            logger.info("Values are set successfully to aascPrinterInfo bean");
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        }
        logger.info("Exit from getPrinterSetupInfo()");
               
        return printerSetupDetailsHashMap;
           
    }


    /**
     * This method calls by the retriveProfileOptionsInfo() method by passing the resultset.getString(String s)
        
     * as an argument so that if that  resultset.getString(String s) returns  any null value 
        
     * this method returns the value "N" otherwise it returns the same value return by the 
        
     * resultset.getString(String s) method.
        
     * @param obj Object
        
     * @return  string  that is N or obj value.
        
     **/
    public String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    } // closing the nullStrToSpc method 
}
