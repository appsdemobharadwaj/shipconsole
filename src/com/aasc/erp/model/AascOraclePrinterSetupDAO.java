package com.aasc.erp.model;

/*
 * @(#)AascOraclePrinterSetupDAO.java     23/06/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.bean.AascPrinterInfo;

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
 * AascOraclePrinterSetupDAO class is DAO factory for Printer Setup.
 * @version   1
 * @author    Y Pradeep
 * History
 * 
 * 07/01/2015   Y Pradeep           Added this file for Printer Setup Popup page.
 * 13/07/2015   Y Pradeep           Added Print Server IP Address field and related code.
 * 11/03/2015   Y Pradeep           Removed Printer IP Address related code as it is not required.
 */

public class AascOraclePrinterSetupDAO extends StoredProcedure implements AascPrinterSetupDAO {
    
    private static Logger logger = AascLogger.getLogger("AascOraclePrinterSetupDAO"); // logger object used for issuing logging requests
    SimpleJdbcCall simpleJdbcCall;
    private AascPrinterInfo aascPrinterInfo = new AascPrinterInfo();
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); // for creating connection
    HashMap<String, ?> map1;
    String errorStatus = "";
    int opStatus = 0;    
    
    public List savePrinterSetupInfo(LinkedList printerList) {
    
        logger.info("Entered savePrinterSetupInfo()");
        List opStatusList = new ArrayList();
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
        
            ListIterator lit = printerList.listIterator();
        
            while (lit.hasNext()) {
        
                aascPrinterInfo = (AascPrinterInfo)lit.next();
                simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_PROFILE_OPTION_PKG")
                                                               .withProcedureName("SAVE_PRINTER_SETUP")
                                                               .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                               .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
        
                // input parameters assignment
                SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", aascPrinterInfo.getClientId())
                                                                            .addValue("ip_location_id", aascPrinterInfo.getLocationId())
                                                                            .addValue("ip_enable_flag", aascPrinterInfo.getEnabledFlag())
                                                                            .addValue("ip_lable_format", aascPrinterInfo.getLabelFormat())
                                                                            .addValue("ip_printer_name", aascPrinterInfo.getPrinterName())
                                                                            .addValue("ip_user_id", aascPrinterInfo.getUserid())
                                                                            .addValue("ip_printer_index", aascPrinterInfo.getPrinterIndex());
        
                // end of input params assignment
        
                Map<String, Object> out = simpleJdbcCall.execute(inputparams);
        
                opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
                errorStatus = String.valueOf(out.get("op_error_status"));
        
                aascPrinterInfo.setOpStatus(opStatus);
                aascPrinterInfo.setErrorStatus(errorStatus);
                logger.info(aascPrinterInfo.getPrinterIndex() + ":::opStatus ::::::" + opStatus + "error_status:::::" + errorStatus);
                opStatusList.add(errorStatus);
            }
        } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
        } 
        logger.info("Exit from savePrinterSetupInfo()");

        return opStatusList;
    }

    public LinkedList getPrinterSetupInfo(int clientId, int locationId) {
        
        AascPrinterInfo aascPrinterInfo = null;
        LinkedList printerSetupDetailList = new LinkedList();
        
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
        
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            errorStatus = String.valueOf(out.get("op_error_status"));
            ArrayList ar = (ArrayList)out.get("OP_PRINTER_SETUP_DETAILS");
            Iterator it = ((ArrayList)out.get("OP_PRINTER_SETUP_DETAILS")).iterator();
            
            if (opStatus == 1 && !ar.isEmpty()) {
                while (it.hasNext()) {
            
                    aascPrinterInfo = new AascPrinterInfo();
                    map1 = (HashMap<String, ?>)it.next();
            
                    aascPrinterInfo.setLabelFormat((String)map1.get("LABEL_FORMAT"));
                    aascPrinterInfo.setEnabledFlag((String)map1.get("ENABLE_FLAG"));
                    aascPrinterInfo.setPrinterName((String)map1.get("PRINTER_NAME"));
                    aascPrinterInfo.setPrinterIndex(((BigDecimal)map1.get("PRINTER_INDEX")).intValue());
            
            
                    printerSetupDetailList.add(aascPrinterInfo);
            
                }
            } else {
                printerSetupDetailList = null;
            }    
            
            logger.info("Values are set successfully to aascPrinterInfo bean");
            } catch(Exception e){
            logger.severe("Exception e : "+e.getMessage());
            }
            logger.info("Exit from getPrinterSetupInfo()");
            
        return printerSetupDetailList;
    }
}
