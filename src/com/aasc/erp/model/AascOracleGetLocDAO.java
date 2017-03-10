/*
  * @(#)AascOracleGetLocDAO.java        05/09/2007
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 *
 * ====================================================================
 *
 * This class is used to retrive the Location Information
 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascGetLocInfo;
import com.aasc.erp.bean.AascShipToLocationsInfo;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
 * <p>AascOracleGetLocDAO class retrieves Location Information.
 * @author 	Eswhari  <br>
 * @version 2.0 <br>
 * 
 * 19/12/2014   Y Pradeep   Modified nullStrToSpc method
 * 07/01/2015   Y Pradeep   Merged Sunanda's code : Converted the methods into Spring JDBC.
 * */
public class AascOracleGetLocDAO implements AascGetLocDAO {
    // private String schemaname = AascProps.getSchemaName();
    int reportRespID = 0;
    SimpleJdbcCall simpleJdbcCall;


    private Connection connection = null;
    private AascOracleDAOFactory aascOracleDAOFactory = 
        new AascOracleDAOFactory(); // Creating a object of AascOracleDAOFactory class.
    private static Logger logger = 
        AascLogger.getLogger("AascOracleGetLocInfo");


    public AascGetLocInfo getLocationInfo(int clientId) {
        logger.info("Entered getLocationInfo()");
        logger.info("clientId : " + clientId);
        LinkedList locationLinkedList = null;
        AascGetLocInfo aascGetLocInfo = null;
        AascGetLocInfo aascGetLocInfoBean = null;
        aascGetLocInfo = new AascGetLocInfo();

        try {

            DataSource dataSource = aascOracleDAOFactory.createDataSource();
            SqlParameterSource inputparams = 
                new MapSqlParameterSource().addValue("ip_client_id", clientId);
            simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_LOCATION_SETUP_PKG")
                                                           .withProcedureName("get_all_location_details")
                                                           .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status",Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_loc_details",OracleTypes.CURSOR));
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);

            Iterator locationIt = 
                ((ArrayList)out.get("OP_LOCATION_DETAILS")).iterator();
            HashMap<String, ?> map1 = null;
            locationLinkedList = new LinkedList();
            while (locationIt.hasNext()) {
                map1 = (HashMap<String, ?>)locationIt.next();
                //                logger.info("map1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:::::"+map1.toString());
                aascGetLocInfoBean = new AascGetLocInfo();
                int locId = ((BigDecimal)map1.get("location_id")).intValue();
                aascGetLocInfoBean.setlocationId(locId);
                aascGetLocInfoBean.setlocationName(nullStrToSpc(map1.get("LOCATION_NAME")));
                locationLinkedList.add(aascGetLocInfoBean);
            }
            aascGetLocInfo.setlocationDetails(locationLinkedList);
            logger.info("Values are set to aascGetLocInfo bean");
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }

        logger.info("Exit from getLocationInfo()");
        return aascGetLocInfo;

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
