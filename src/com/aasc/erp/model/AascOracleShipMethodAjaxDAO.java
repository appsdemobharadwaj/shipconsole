/*
* @(#)AascOracleShipMethodAjaxDAO.java        
* Copyright (c) 2015-2016 Apps Associates Pvt. Ltd.
* All rights reserved.
*/
package com.aasc.erp.model;

import com.aasc.erp.bean.AascCustomerAccountNumbers;
import com.aasc.erp.model.AascOracleDAOFactory;
import com.aasc.erp.model.AascShipMethodAjaxDAO;
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
/**
* AascOracleShipMethodAjaxDAO class retrieves ship method details from database
* and places it in a String when respective methods are called.
* @version - 1
* @author 
* 
* 18/11/2014  Eshwari M  Added this file from Cloud 1.2 version.
* 07/01/2015  Y Pradeep  Merged Sunanda's code : Converted methods into Spring JDBC.
* 19/01/2015  Suman G    Removed unncessary comments from history
* 03/02/2015  Suman G    Added code to fix #2520.
* 11/02/2015  Suman G    Added retrieveDefaultAcNo() for getting TP and FC account numbers.
* 14/04/2015  Y Pradeep  Added nullStrToSpc method to null values while retreving Third Party and Receipent Carrier Account Numbers. Bug #2841.
* 13/07/2015  Y Pradeep  Added code to get label format from carrier configration when shipmethod is changed.
* 20/07/2015  Suman G    Added code for implementing Email Notification.

*/
public class AascOracleShipMethodAjaxDAO implements AascShipMethodAjaxDAO {
    
    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // creating the object for AascPackageInfo
    private String completeDetails = 
        ""; // String used to hold shipment details.
    private static Logger logger = 
        AascLogger.getLogger("AascOracleShipMethodAjaxDAO"); // logger object used for issuing logging requests

    /** default constructor. */
    public AascOracleShipMethodAjaxDAO() {
    }

    /**
     * This method retrives Carrier Code and Carrier Service Level(Connect ship tag) from databse .
     * @param  userShipMethodMeaning  String  Ship Method for which the service levels has to be fetched
     * @param  locationID              int    locaton Id for which the shipmethod service levels has to be fetched
     * @param  clientId                int    clientId 
     * @return a String with Carrier Code, Carrier Service level
     */
    public String retrieveCcCsl(String userShipMethodMeaning,
                                int locationID, int carrierId, int clientId) {
        logger.info("Entered retrieveCcCsl() for getting carier code service level");
        String carrierCode = "";
        try {

            logger.info("userShipMethodMeaning:::" + userShipMethodMeaning);
            logger.info("locationID:::" + locationID);
            logger.info("carrierId:::" + carrierId);
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_user_shipmethod_meaning",userShipMethodMeaning).addValue("ip_location_id",locationID).addValue("ip_carrier_id",carrierId);

            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                           .withProcedureName("get_carrier_code_service_level")
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_carrier_code",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_service_level",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_carrier_mode",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_label_format",Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_error_status")));

            logger.info("aasc_erp_carr_service_comb_pkg.get_carrier_code_service_level opStatus:" + 
                        opStatus);

            if (opStatus == 509) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);

                
                String carrierServiceLevel = "";
                String carrierMode = "";
                String labelFormat = "" ;

                try {
                    carrierCode = (String)out.get("op_carrier_code");
                    carrierServiceLevel = (String)out.get("op_service_level");
                    carrierMode = (String)out.get("op_carrier_mode");
                    labelFormat = (String)out.get("op_label_format");
                } catch (Exception ex) {
                    carrierCode = "";
                    carrierServiceLevel = "";
                    carrierMode = "";
                    labelFormat = "" ;
                }

                try {
                    if (carrierServiceLevel.equals("NULL")) {
                        carrierServiceLevel = "";
                    }
                } catch (Exception e) {
                    carrierServiceLevel = "";
                }

                String temp = 
                    carrierCode + "@" + carrierServiceLevel + "@" + carrierMode + "#" + labelFormat;

                completeDetails = "*" + temp + "*";
                logger.info("completeDetails : " + completeDetails);

                if (carrierCode != "") {
                    String hazmatEnabledFlag = "";
                    String ltlHazmatShipping = "";
                    try {
                        //DataSource  datasource =connectionFactory.createDataSource(); // this method returns the datasources object
                        SqlParameterSource hazmatInputparams =new MapSqlParameterSource()
                                                                .addValue("IP_CARRIER_CODE",carrierCode)
                                                                .addValue("ip_lookup_code","Hazmat_Support")
                                                                .addValue("ip_lookup_value",carrierMode);


                        simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carrier_pkg")
                                                                       .withProcedureName("get_hazmat_lookup_values")
                                                                       .declareParameters(new SqlOutParameter("OP_STATUS",Types.INTEGER))
                                                                       .declareParameters(new SqlOutParameter("OP_ENABLED_FLAG",Types.VARCHAR))
                                                                       .declareParameters(new SqlOutParameter("Op_Error_Status",Types.VARCHAR));


                        out = simpleJdbcCall.execute(hazmatInputparams);
                        hazmatEnabledFlag = (String)out.get("OP_ENABLED_FLAG");

                        logger.info("hazmatEnabledFlag : " + hazmatEnabledFlag);
                        if (hazmatEnabledFlag == null)
                            hazmatEnabledFlag = 
                                    "N"; // Modified by suman from "" to "N"

                    } catch (Exception e) {
                        logger.severe("Got exception in calling Hazamt lookup details database call" + 
                                      e);
                        

                    }

                    completeDetails = 
                            completeDetails + "$" + hazmatEnabledFlag;
                    logger.info("completeDetails : " + completeDetails);

                    try {

                        SqlParameterSource hazmatInputparams =new MapSqlParameterSource()
                        .addValue("IP_CARRIER_CODE",carrierCode)
                        .addValue("IP_CARRIER_ID",carrierId)
                        .addValue("ip_client_id",clientId);

                        simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carrier_pkg")
                                                                      .withProcedureName("GET_LTL_HAZMAT_SUPPORT")
                                                                      .declareParameters(new SqlOutParameter("OP_STATUS",Types.INTEGER))
                                                                      .declareParameters(new SqlOutParameter("OP_LTL_HAZMAT_SUPPORT",Types.VARCHAR))
                                                                      .declareParameters(new SqlOutParameter("Op_Error_Status",Types.VARCHAR));


                        out = simpleJdbcCall.execute(hazmatInputparams);
                        hazmatEnabledFlag = (String)out.get("OP_ENABLED_FLAG");
                        ltlHazmatShipping = 
                                (String)out.get("OP_LTL_HAZMAT_SUPPORT");

                        logger.info("ltlHazmatShipping : " + 
                                    ltlHazmatShipping);
                        if (ltlHazmatShipping == null) {
                            hazmatEnabledFlag = 
                                    "N"; // Modified by suman from "" to "N"
                            ltlHazmatShipping = "No"; //Added by Suman
                        }

                    } catch (Exception e) {
                        logger.severe("Got exception in calling LTL Hazamt Shipping details database call" + 
                                      e);
                        

                    }

                    completeDetails = 
                            completeDetails + "$" + ltlHazmatShipping + 
                            "$"; //return mode;
                    logger.info("completeDetails : " + completeDetails);
                }

            } else {
                logger.info("Error in retreiving data from the database: opStatus " + 
                            opStatus);
                completeDetails = "**";
            }
            
            //========================= Suman Gunda code start
            
//            AascCustAccountNumsDAO aascCustAccountNumsDAO = new AascOracleCustAccountNumsDAO();
//            LinkedList accountNumsList= aascCustAccountNumsDAO.getCustAccountNums(locationID);
//            Iterator it = accountNumsList.iterator();
//            logger.info("Carrier code::::"+carrierCode);
//            AascCustomerAccountNumbers aascCustomerAccountNumbers = new AascCustomerAccountNumbers();
//            while(it.hasNext()){
//                aascCustomerAccountNumbers = (AascCustomerAccountNumbers)it.next();
//                if(aascCustomerAccountNumbers.getCarrierCode() == Integer.parseInt(carrierCode)){
//                    completeDetails = completeDetails + "###" + aascCustomerAccountNumbers.getRecipient() + "#" + aascCustomerAccountNumbers.getThirdParty();
//                }
//            }
            
            //========================= Suman Gunda code end
             
            logger.info("completeDetails in get_carrier_code_service_level()= " + 
                        completeDetails);
            logger.info("Exit from get_carrier_code_service_level()");
            return completeDetails;

        } catch (Exception e1) {
            logger.severe("Exception::"+e1.getMessage());
            logger.info("Exit from get_carrier_code_service_level()");
            return "-1";
        }
    }

    /**
     * This method retrives Drop Off Type.
     * @param carrierCode          int     carrier code for which dropoff type has to fetch
     * @param carrierServiceLevel  String  carrier service level for which dropoff type has to fetch
     * @return a String Drop Off Type
     */
    public String retrieveDot(int carrierCode, String carrierServiceLevel) {
        logger.info("Entered retrieveDot() for getting drop off type");
        try {
            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                                                        .addValue("ip_carrier_service_level",carrierServiceLevel);


            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                           .withProcedureName("get_drop_off_type")
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_get_drop_off_type_details",OracleTypes.CURSOR));


            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_error_status")));
            completeDetails = "";
            logger.info("aasc_erp_carr_service_comb_pkg.get_drop_off_type opStatus:" + 
                        opStatus);
            if (opStatus == 501) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                Iterator it = 
                    ((ArrayList)out.get("OP_DROP_OFF_TYPE_DETAILS")).iterator();

                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    completeDetails = 
                            completeDetails + "*" + (String)map1.get("drop_off_type"); // it.next();
                }

                completeDetails = completeDetails + "*";
            } else {
                logger.info("Error in retreiving data from the database: opStatus " + 
                            opStatus);
                completeDetails = "**";
            }

            logger.info("completeDetails in get_drop_off_type()= " + 
                        completeDetails);
            logger.info("Exit from get_drop_off_type()");
            return completeDetails;
        } catch (Exception e1) {
            logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" + 
                          e1.getMessage());
            logger.info("Exit from get_drop_off_type()");

            return "-1";
        }
    }

    /**
     * This method retrives Packaging.
     * @param carrierCode         carrier code for which packaging has to fetch
     * @param carrierServiceLevel carrier service level for which packaging has to fetch
     * @param dropOffType         Drop Off Type for which packaging has to fetch
     * @return a String Packaging
     */
    public String retrievePackaging(int carrierCode, 
                                    String carrierServiceLevel, 
                                    String dropOffType) {
        logger.info("Entered retrievePackaging()");
        try {

            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode).addValue("ip_carrier_service_level",carrierServiceLevel).addValue("ip_drop_off_type",dropOffType);


            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                        .withProcedureName("get_packaging")
                                                        .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                        .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                        .declareParameters(new SqlOutParameter("aasc_get_packaging_details",OracleTypes.CURSOR));


            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus =Integer.parseInt(String.valueOf(out.get("op_error_status")));
            completeDetails = "";
            logger.info("aasc_erp_carr_service_comb_pkg.get_packaging opStatus:" + 
                        opStatus);
            if (opStatus == 503) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                Iterator it = 
                    ((ArrayList)out.get("OP_PACKAGING_DETAILS")).iterator(); //op_packaging_details

                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    completeDetails = 
                            completeDetails + "*" + (String)map1.get("packaging"); // it.next();
                }

                completeDetails = completeDetails + "*";
            } else {
                logger.info("Error in retreiving data from the database : opStatus " + 
                            opStatus);
                completeDetails = "**";
            }
            logger.info("completeDetails in get_packaging()= " + 
                        completeDetails);
            logger.info("Exit from get_packaging()");
            return completeDetails;
        } catch (Exception e1) {
            logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" + 
                          e1.getMessage());
            logger.info("Exit from get_packaging()");
            return "-1";
        }
    }

    /**
     * This method retrives Carrier Pay Method.
     * @param carrierCode           carrier code for which carrier pay method has to fetch
     * @param carrierServiceLevel   carrier service level for which carrier pay method has to fetch
     * @param dropOffType           Drop Off Type for which carrier pay method has to fetch
     * @param packaging             Packaging for which carrier pay method has to fetch
     * @return a String Carrier Pay Method
     */
    public String retrieveCpt(int carrierCode, String carrierServiceLevel, 
                              String dropOffType, String packaging) {

        logger.info("Entered retrieveCpt() for getting carrier pay terms");
        try {


            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams =new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                                                        .addValue("ip_carrier_service_level",carrierServiceLevel)
                                                                        .addValue("ip_drop_off_type",dropOffType)
                                                                        .addValue("ip_packaging",packaging);


            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                          .withProcedureName("get_carrier_payment_terms")
                                                          .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                          .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                          .declareParameters(new SqlOutParameter("aasc_get_carrier_payment_terms",OracleTypes.CURSOR));


            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_error_status")));
            completeDetails = "";
            logger.info("aasc_erp_carr_service_comb_pkg.get_carrier_payment_terms opStatus:" + 
                        opStatus);
            if (opStatus == 505) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                Iterator it = 
                    ((ArrayList)out.get("OP_CARRIER_PAYMENT_TERMS")).iterator(); //op_carrier_payment_terms

                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    completeDetails = 
                            completeDetails + "*" + (String)map1.get("carrier_payment_terms") + 
                            "@" + 
                            (String)map1.get("pay_term_code"); // it.next();
                }

                completeDetails = completeDetails + "*";

            } else {
                logger.info("Error in retreiving data from the database : opStatus " + 
                            opStatus);
                completeDetails = "**";
            }
            logger.info("completeDetails in get_carrier_payment_terms()= " + 
                        completeDetails);
            logger.info("Exit from get_carrier_payment_terms()");
            return completeDetails;
        } catch (Exception e1) {
            logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" + 
                          e1.getMessage());
            logger.info("Exit from get_carrier_payment_terms()");
            return "-1";
        }
    }

    /**
     * This method retrives Carrier Pay Method.
     * @param carrierCode            carrier code for which service level code has to fetch
     * @param carrierServiceLevel    carrier service level  for which service level code has to fetch
     * @param ajaxOriginRegionCode   region code  for which service level code has to fetch
     * @return a String Ups Service Level Code
     */
    public String retrieveUpsServiceLevelCode(int carrierCode, 
                                              String carrierServiceLevel, 
                                              String ajaxOriginRegionCode) {
        try {
            logger.info("Entered retrieveUpsServiceLevelCode()");

            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams =new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                                                       .addValue("ip_service_level",carrierServiceLevel)
                                                                       .addValue("ip_origin_region_code",ajaxOriginRegionCode);


            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                           .withProcedureName("get_ups_direct_service_code")
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("op_ups_service_level_code",Types.VARCHAR));


            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_error_status")));
            completeDetails = "";
            logger.info("aasc_erp_carr_service_comb_pkg.get_ups_direct_service_code opStatus:" + 
                        opStatus);

            if (opStatus == 507) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                completeDetails = (String)out.get("op_ups_service_level_code");
            } else {
                logger.info("Error in retreiving data from the database : opStatus " + 
                            opStatus);
                completeDetails = "";
            }
            logger.info("completeDetails in retrieveUpsServiceLevelCode()= " + 
                        completeDetails);
            logger.info("Exit from retrieveUpsServiceLevelCode()");

            return completeDetails;
        } catch (Exception e1) {
            logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" + 
                          e1.getMessage());
            logger.info("Exit from retrieveUpsServiceLevelCode()");
            return "-1";
        }

    }

    /**
     * This method retrives Carrier Code Combination Values.
     * @param carrierCode           carrier code for which carrier code combination values has to fetch
     * @param carrierServiceLevel   carrier service level for which carrier code combination values has to fetch
     * @param dropOffType           Drop Off Type for which carrier code combination values has to fetch
     * @param packaging             Packaging for which carrier code combination values has to fetch
     * @param carrierPaymentTerms   Carrier payment terms for which carrier code combination values has to fetch
     * @return a String carrier code combination values
     */
    public String getCarrierCombValidValues(int carrierCode, 
                                            String carrierServiceLevel, 
                                            String dropOffType, 
                                            String packaging, 
                                            String carrierPaymentTerms) {
        logger.info("Entered getCarrierCombValidValues()");
        try {

            logger.info("carrierCode : " + carrierCode);
            logger.info("carrierServiceLevel : " + carrierServiceLevel);
            logger.info("dropOffType : " + dropOffType);
            logger.info("packaging : " + packaging);
            logger.info("carrierPaymentTerms : " + carrierPaymentTerms);
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
                                           .addValue("ip_carrier_service_level",carrierServiceLevel)
                                           .addValue("ip_drop_off_type",dropOffType)
                                           .addValue("ip_packaging",packaging)
                                           .addValue("ip_carrier_payment_terms",carrierPaymentTerms);


            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_carr_service_comb_pkg")
                                                           .withProcedureName("get_carrier_comb_valid_values")
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_carrier_comb_valid_values",OracleTypes.CURSOR));


            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            completeDetails = "";
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_error_status")));
            completeDetails = "";
            logger.info("aasc_erp_carr_service_comb_pkg.get_carrier_comb_valid_values opStatus:" + 
                        opStatus);
            if (opStatus == 505) {
                logger.info("data is retreived successfully from database : opStatus " + 
                            opStatus);
                Iterator it = 
                    ((ArrayList)out.get("op_carrier_comb_valid_values")).iterator();

                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    completeDetails = completeDetails + "*" + (String)map1.get("dim_reqd") + 
                                "*" + (String)map1.get("max_weight") + "*" + 
                                (String)map1.get("max_length") + "*" + 
                                (String)map1.get("min_length") + "*" + 
                                (String)map1.get("max_width") + "*" + 
                                (String)map1.get("min_width") + "*" + 
                                (String)map1.get("max_height") + "*" + 
                                (String)map1.get("min_height");
                }
                completeDetails = completeDetails + "*";
            } else {
                logger.info("Error in retreiving data from the database : opStatus " + 
                            opStatus);
                completeDetails = "**";
            }
            logger.info("completeDetails in get_carrier_comb_valid_values()= " + 
                        completeDetails);
            logger.info("Exit from get_carrier_comb_valid_values()");
            return completeDetails;
        } catch (Exception e1) {
            logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" + 
                          e1.getMessage());
            logger.info("Exit from get_carrier_comb_valid_values()");
            return "-1";
        }
    }

    /**
     * This method retrives Email Details.
     * @param carrierCode   carrier for which eamil details has to fetch
     * @param locationId    location for which eamil details has to fetch
     * @param orderNumber   order number for which eamil details has to fetch
     * @param clientId      clientId fo the user logged in
     * @return a String contianinf email details
     */
    public String getEmailDetails(int carrierCode, int locationId, 
                                  int orderNumber, 
                                  int clientId) { // ClientId Added by suman Gunda
        /*try {
        logger.info("Entered getEmailDetails()");
        logger.info("locationId = " + locationId);
        
        
        DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
        SqlParameterSource inputparams =
        new MapSqlParameterSource().addValue("ip_carrier_code",carrierCode)
        .addValue("ip_location_id",locationId)
        .addValue("ip_origin_region_code",ajaxOriginRegionCode);
        
        
        
        simpleJdbcCall =
        new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_PKG")
        .withProcedureName("get_email_notification_details")
        .declareParameters(new SqlOutParameter("op_error_status",Types.INTEGER))
        .declareParameters(new SqlOutParameter("op_error_message",Types.VARCHAR))
        .declareParameters(new SqlOutParameter("op_ups_service_level_code",Types.VARCHAR));
        
        
        Map<String, Object> out = simpleJdbcCall.execute(inputparams);
        completeDetails = "";
        int opStatus =  Integer.parseInt(String.valueOf(out.get("op_error_status")));
        completeDetails = "";
        logger.info("aasc_erp_carr_service_comb_pkg.get_ups_direct_service_code opStatus:" +
        opStatus);
        
        if (opStatus == 507) {
        logger.info("data is retreived successfully from database : opStatus " +
        opStatus);
        
        
        Iterator it = ((ArrayList)out.get("op_carrier_comb_valid_values")).iterator();
        
        HashMap<String, ?> map1=null;
        for (; it.next(); ) {
        map1 = (HashMap<String, ?>)it.next();
        completeDetails =
        completeDetails =
        completeDetails + "*" + (String)map1.get("dim_reqd") +
        "*" + (String)map1.get("max_weight") + "*" +
        (String)map1.get("max_length") + "*" +
        (String)map1.get("min_length") + "*" +
        (String)map1.get("max_width")+ "*" +
        (String)map1.get("min_width") + "*" +
        (String)map1.get("max_height")+ "*" +
        (String)map1.get("min_height");
        }
        completeDetails = completeDetails + "*";
        callableStatement.setInt(1, carrierCode);
        callableStatement.setInt(2, locationId);
        callableStatement.setInt(3, orderNumber);
        callableStatement.setInt(4, clientId);
        callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
        callableStatement.registerOutParameter(6, Types.INTEGER);
        callableStatement.registerOutParameter(7, Types.VARCHAR);
        callableStatement.execute();
        resultSet = (ResultSet)callableStatement.getObject(5);
        completeDetails = "";
        int opStatus = callableStatement.getInt(6);
        
        logger.info("AASC_SHIPMENT_PKG.get_email_notification_details opStatus:" +
        opStatus);
        
        if (opStatus == 0) {
        logger.info("data is retreived successfully from database : opStatus " +
        opStatus);
        
        while (resultSet.next()) {
        //SC_EMail_SJ
        completeDetails =
        completeDetails + "***" + resultSet.getString(1) +
        "***" + resultSet.getString(2) + "***" +
        resultSet.getString(3) + "***" +
        resultSet.getString(4) + "***" +
        resultSet.getString(5) + "***" +
        resultSet.getString(6) + "***" +
        resultSet.getString(7) + "***" +
        resultSet.getString(8) + "***" +
        resultSet.getString(9) + "***" +
        resultSet.getString(10) + "***" +
        resultSet.getString(11) + "***" +
        resultSet.getString(12) + "***" +
        resultSet.getString(13) + "***" +
        resultSet.getString(14) + "***" +
        resultSet.getString(15) + "***" +
        resultSet.getString(16);
        }
        completeDetails = completeDetails + "***";
        } else {
        logger.info("Error in retreiving data from the database : opStatus " +
        opStatus);
        completeDetails = "**";
        }
        logger.info("completeDetails in get_email_notification_details()= " +
        completeDetails);
        logger.info("Exit from getEmailDetails()");
        return completeDetails;
        } catch (Exception e1) {
        logger.severe("Got exception in getting completeDetails from Ajax,hence returning -1" +
        e1.getMessage());
        logger.info("Exit from getEmailDetails()");
        return "-1";
        } */
        return "-1";
    }
    
    /**
     * This method retrives Default Account Numbers.
     * @param carrierCode           int
     * @param clientId              int
     * @param custLocationId        String
     * @param locationSelected      String
     * @return a String carrier code combination values
     */
    public String retrieveDefaultAcNo(int clientId, String custLocationId, String locationSelected, int carrierCode){
        logger.info("Entered retrieveDefaultAcNo() for getting drop off type");
        completeDetails = "";
        try {
            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CLIENT_ID",clientId)
                                                                        .addValue("IP_CUSTOMER_LOC_ID",custLocationId)
                                                                        .addValue("IP_LOCATION_NAME",locationSelected);


            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CUSTOMER_LOCATION_PKG")
                                                           .withProcedureName("get_default_account_number")
                                                            .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                            .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("aasc_cust_account_nums", OracleTypes.CURSOR));


            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
                    
                    logger.info("After Execute");
                    logger.info("out ::: " + out.toString());
                    int opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
                    String errorStatus =  String.valueOf(out.get("op_error_status"));
                    
                    Iterator it= ((ArrayList)out.get("OP_CUST_ACCOUNT_NUMS")).iterator();
                    
                    
                    while(it.hasNext()){
                    HashMap<String,?>  map1 =(HashMap<String,?>)it.next();
                        if(map1.get("CUSTOMER_LOCATION_ID")!=null){
                    
                            int carriercodeValue = ((BigDecimal)map1.get("CARRIER_CODE")).intValue();
                            if(carrierCode == carriercodeValue){
                                String recipient = nullStrToSpc((String)map1.get("RECEIPENT"));
                                String thirdParty = nullStrToSpc((String)map1.get("THIRDPARTY"));
                                completeDetails = completeDetails+ "###" + recipient + "#" + thirdParty;
                            }
                        
                        }
                            
                        
                    }
                logger.info("opStatus :"+opStatus+" error status :"+errorStatus);
        }catch(Exception e){
            logger.severe("Exception:"+e.getMessage());
            completeDetails = "-1";
        }
        logger.info("complete details:"+completeDetails);
        return completeDetails;
    }
    
    /**
     * This method retrives Email Details.
     * @param carrierCode           int
     * @param clientId              int
     * @param locationId      String
     * @return a String carrier code combination values
     */   
    public String getEmailDetails(int carrierCode,int locationId, int clientId){
        logger.info("Entered retrieveDefaultAcNo() for getting drop off type");
        completeDetails = "";
        try {
            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CARRIER_CODE",carrierCode)
                                                                        .addValue("IP_LOCATION_ID",locationId)
                                                                        .addValue("IP_CLIENT_ID",clientId);


            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARR_SERVICE_COMB_PKG")
                                                           .withProcedureName("get_email_notification_details")
                                                            .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                            .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                            .declareParameters(new SqlOutParameter("aasc_get_email_details", OracleTypes.CURSOR));


            Map<String,Object> out = simpleJdbcCall.execute(inputparams);
                    
                    logger.info("After Execute");
                    logger.info("out ::: " + out.toString());
                    int opStatus=  Integer.parseInt(String.valueOf(out.get("op_status")));
                    String errorStatus =  String.valueOf(out.get("op_error_status"));
                    
                    Iterator it= ((ArrayList)out.get("OP_EMAIL_DETAILS")).iterator();
                
                    HashMap<String, ?> map1 = null;
                    
                    while(it.hasNext()){
                        map1 = (HashMap<String, ?>)it.next();
                        completeDetails = completeDetails + "*" + (String)map1.get("email_notification_flag") + 
                                    "*" + (String)map1.get("sender_email_address") + "*" + 
                                    (String)map1.get("reference1_flag") + "*" + 
                                    (String)map1.get("reference2_flag") + "*" + 
                                    (String)map1.get("ship_notification_flag") + "*" + 
                                    (String)map1.get("exception_notification") + "*" + 
                                    (String)map1.get("delivery_notification") + "*"+
                                    (String)map1.get("format_type") + "*" + 
//                                    (String)map1.get("sales_order_number") + "*" + 
                                    (String)map1.get("customer_name") + "*";// + 
//                                    (String)map1.get("delivery_item_number") ;                 
                        
                    }
                logger.info("opStatus :"+opStatus+" error status :"+errorStatus);
        }catch(Exception e){
            logger.severe("Exception:"+e.getMessage());
            
            completeDetails = "-1";
        }
        logger.info("complete details:"+completeDetails);
        return completeDetails;
    }
    
    public String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }
}
