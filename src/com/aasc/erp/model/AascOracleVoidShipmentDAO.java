/*
 * @(#)AascOracleVoidShipmentDAO.java        27/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */

package com.aasc.erp.model;

import com.aasc.erp.util.AascLogger;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


/**
 * AascOracleVoidShipmentDAO voids the shipment.
 * @author  Sunanda
 * @version AascOracleVoidShipmentDAO.java    27/01/2015
 * @since
 *    History
 * 12-02-2015      Eshwari M    Modified code to get correct status from database
 */
public class AascOracleVoidShipmentDAO implements AascVoidShipmentDAO {

    /** this is the variables for connection*/
    ResultSet pkgVoidResultSet = null;
    static Logger logger = AascLogger.getLogger("AascOracleVoidShipmentDAO");
    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory();

    /**
     * default constructor.
     */
    public AascOracleVoidShipmentDAO() {
    }

    /** This method voids the  shipment order.

     * @param orderNumber -int containing order Number

     * @return returnstatus int 

     */
    public int voidShipment(String orderNumber, int clientID, String msg, 
                            int locationID) {
        logger.info("Entered voidShipment(orderNumber)");
        int returnStatus = 0;
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_order_number",orderNumber)
                                           .addValue("ip_location_id",locationID)
                                           .addValue("ip_client_id",clientID);
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("void_shipment")
                                                           .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
                                                           //.declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            returnStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("opStatus" + returnStatus + " && error_status ::" + 
                        error_status);
            
        } catch (Exception e) {
            logger.severe("Error in closing connection" + e);
            e.getMessage();
        }

        logger.info("Exit from voidShipment(orderNumber)");
        return returnStatus;

    }

    /** This method voids the  shipment package.

     * @param orderNumber -int containing order Number

     * @return returnstatus int 

     */

    public int voidShipmentPackage(String orderNumber, int packageID, 
                                   int locationId, int clientID) {
        logger.info("Entered voidShipmentPackage()");
        int returnStatus = 0 ;
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams =new MapSqlParameterSource().addValue("ip_order_number",orderNumber)
                                                                       .addValue("ip_pkg_number",packageID)
                                                                       .addValue("ip_location_id",locationId)
                                                                       .addValue("ip_client_id",clientID);
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("void_package")
                                                           .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));

            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            returnStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("returnStatus" + returnStatus + " && error_status ::" + 
                        error_status);
                        
        } catch (Exception e) {
            logger.severe("Error in closing connection" + e);
            e.getMessage();
        }

        logger.info("Exit from createCustomerLocation()");
        return returnStatus;
    }
    /** This method updates the package shipment cost .

     * @param orderNumber -int containing order Number

     * @return returnstatus int 

     */

    public int updateShipmentCost(String orderNumber, double packageCost, 
                                  int clientID, int locatonId1) {
        logger.info("Entered updateShipmentCost()");
        int returnStatus = 0;

        try {
            DataSource datasource =connectionFactory.createDataSource(); // this method returns the datasources object
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_order_number",orderNumber)
                                                                        .addValue("ip_freight_charges",packageCost)
                                                                        .addValue("ip_client_id",clientID)
                                                                        .addValue("ip_location_id",locatonId1);
            simpleJdbcCall =new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_SHIPMENT_ORDERS_PKG")
                                                           .withProcedureName("UPDATE_SHIPMENT_COST")
                                                           .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR));
                                                           //.declareParameters(new SqlOutParameter("aasc_loc_details", OracleTypes.CURSOR));
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            returnStatus =Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("returnStatus" + returnStatus + " && error_status ::" + 
                        error_status);

            if (returnStatus == 0) {
                returnStatus = 130;
            } else {
                returnStatus = 131;
            }

        } catch (Exception e) {
            logger.severe("Error in closing connection" + e);
            e.getMessage();
        }

        logger.info("Exit from updateShipmentCost()");
        return returnStatus;
    }


}

