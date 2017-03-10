/*
  DESCRIPTION
    AascOracleGetTrackInfo class is used to get the tracking Ids from database and
    concatenating them to a string.
    Version - 1
    History
    11/06/09   Madhavi    Modified logging code
    25/09/13   Eshwari M  Modified code for Role Id 2 Tracking
    20/11/04   Sunanda K  Added from cloud 1.2 version
    17/12/14   Eshwari M  Fixed the issues that came after sunanda converting JDBC calls to Sping JDBC calls
    19/12/14   Y Pradeep  Modified nullStrToSpc method
    31/08/15   Suman G    Added code for Trial User Changes
*/
package com.aasc.erp.model;

import com.aasc.erp.util.AascLogger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import oracle.jdbc.OracleTypes;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class AascOracleGetTrackInfo {
    // private String schemaname = AascProps.getSchemaName();
    private String trackingIds = "@@@";
    private String locationIds = "@@@";
    //  private String orgIds = "";
    private String trackingIdTypes;
    private Date shipmentDate;
    private int locationId = 0;
    private int clientId = 0;
    int status = 0;
    private Connection connection;
    private AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
    private static Logger logger = 
        AascLogger.getLogger("AascOracleGetTrackInfo");

    private ResultSet headerResultSet = null;
    SimpleJdbcCall simpleJdbcCall;

    public AascOracleGetTrackInfo() {
    }

    public String getTrackingInfo(String inputString, String shipmentDate, 
                                  int locationId, int clientId, int userId) {
        logger.info("Entered getTrackingInfo()");
        this.trackingIdTypes = inputString;
        //this.shipmentDate = shipmentDate;
        this.locationId = locationId;
        logger.info("Input String is :" + inputString);
        logger.info("Shipment Date is :" + shipmentDate);
        logger.info("Location Id  is :" + locationId);
        logger.info("userId is :::::"+userId);
        logger.info("clientId : "+clientId);
        
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf1.parse(shipmentDate);
        } catch (ParseException e) {
            date = new java.util.Date() ;
            e.getMessage();
            
        }
        java.sql.Date sqlShipmentDate = new java.sql.Date(date.getTime());  

        try {

            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object


            if (inputString != null || shipmentDate != null || 
                locationId != 0) {
                SqlParameterSource inputparams = 
                    new MapSqlParameterSource().addValue("ip_string", inputString)
                                               .addValue("ip_shipment_date", sqlShipmentDate, Types.DATE)
                                               .addValue("IP_LOCATION_ID", locationId)
                                               .addValue("ip_client_id", clientId)
                                               .addValue("ip_user_Id",userId);

                simpleJdbcCall = 
                        new SimpleJdbcCall(datasource).withCatalogName("aasc_erp_tracking_details_pkg").withProcedureName("tracking_details")
                                                      .declareParameters(new SqlOutParameter("trackdettyp", OracleTypes.CURSOR))
                                                      .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                      .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));

                Map<String, Object> out = simpleJdbcCall.execute(inputparams);


                logger.info("After Execute");
                logger.info("out ::: " + out.toString());
                ArrayList str = (ArrayList)out.get("OP_TRACK");
                logger.info("Array List map values ::::::::::::::::" + 
                            str.toString());
                Iterator it = str.iterator();

                logger.info("str size : " + str.size());

                HashMap<String, ?> resulthm = new HashMap();
                /*while (it.hasNext()) {
                    resulthm = (HashMap<String, ?>)it.next();
                    logger.info("resulthm : "+resulthm);
                }*/
                //headerResultSet = (ResultSet)out.get("OP_TRACK");
                
                if (trackingIdTypes.equalsIgnoreCase("TrackingNumber")) {
                    logger.info("trackingIdTypes is TrackingNumber");
                    while (it.hasNext()) {
                            resulthm = (HashMap<String, ?>)it.next();
                    
                        trackingIds = 
                                trackingIds + resulthm.get("WAYBILL_NUMBER") +  
                                "***";
                    }
                } else if (trackingIdTypes.equalsIgnoreCase("OrderNumber")) {
                    logger.info("trackingIdTypes is OrderNumber");
                    while (it.hasNext()) {
                            resulthm = (HashMap<String, ?>)it.next();
                    
                        trackingIds = 
                                trackingIds + resulthm.get("ORDER_NUMBER") +  
                                "***";
                    }
                }

            }

        } catch (Exception e)

        {
            logger.severe("Got exception in closing database call" + 
                          e.getMessage());

            e.printStackTrace();
        }

        if (trackingIds == null || trackingIds == "") {
            trackingIds = "none";
        }
        logger.info("aasc_erp_tracking_details_pkg.tracking_details  trackingIds" + 
                    trackingIds);
        logger.info("Exit from getTrackingInfo()");
        return trackingIds;
    }


    public String getlocationsInfo(int clientId) {
        logger.info("Entered getlocationsInfo()");
        this.clientId = clientId;
        logger.info("client Id  is :" + clientId);

        try {

            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object

            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_client_id", 
                                                     this.clientId);

            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_LOCATION_SETUP_PKG").withProcedureName("get_all_location_details")
                           .declareParameters(new SqlOutParameter("OP_STATUS", Types.INTEGER))
                           .declareParameters(new SqlOutParameter("OP_ERROR_STATUS",Types.VARCHAR))
                           .declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);


            logger.info("After Execute");
            //logger.info("out ::: " + out.toString());
            status = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));
            String error_status = String.valueOf(out.get("OP_ERROR_STATUS"));
            ArrayList str = (ArrayList)out.get("OP_LOCATION_DETAILS");
            //logger.info("Array List map values ::::::::::::::::" + str.toString());
            Iterator it = str.iterator();

            logger.info("str size : " + str.size());
            
            status = Integer.parseInt(String.valueOf(out.get("OP_STATUS")));

            if (status == 606) 
            {  
                HashMap<String, ?> resulthm = new HashMap();
                while (it.hasNext()) {
                    resulthm = (HashMap<String, ?>)it.next();
                    locationIds = locationIds + String.valueOf(resulthm.get("LOCATION_NAME"))  + 
                                "$";
                        locationIds = 
                                locationIds + String.valueOf(resulthm.get("LOCATION_ID")) + 
                                "***";;
                }
            }
        } catch (Exception e)

        {
            logger.severe("Got exception in closing database call" + 
                          e.getMessage());
        }

        if (locationIds == null || locationIds == "") {
            locationIds = "none";
        }
        logger.info("Exit from getlocationsInfo()");
        return locationIds;

    }


    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

}

