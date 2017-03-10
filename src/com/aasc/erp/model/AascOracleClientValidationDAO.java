package com.aasc.erp.model;

import com.aasc.erp.util.AascLogger;


import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * AascOracleClientValidationDAO is a class which interacts with the database and 
             corresponding bean file for retriving Details related with customer validation such as tolerance check, balance check and expiry check.
 * @author 	Jagadish Jain
 * @version 1.0
 * @since
 * 12/08/2015   Jagadish Jain    Wrote the initial code required to make db call to perform client validation(Check for tolerance and expiry)
 * 24/02/2016   Suman G          Changed for the transaction management design.
 * 
 * 
 **/

public class AascOracleClientValidationDAO implements AascClientValidationDAO {
    private static Logger logger = 
        AascLogger.getLogger("AascOracleClientValidationDAO");
    SimpleJdbcCall simpleJdbcCall;
    
    AascOracleDAOFactory connectionFactory = 
        new AascOracleDAOFactory(); // creating the object for AascPackageInfo
    Connection connection = 
        null; // connection object which connects to data base
    CallableStatement callableStatement; 
    private int opStatus;
    HashMap clientValidationHashMap = new HashMap();
    
    public AascOracleClientValidationDAO() {
    }


    public HashMap clientValidation(int clientId, int pkgCount) {
        logger.info("Entered clientValidation() in AascOracleClientValidationDAO");
        DataSource datasource = 
            connectionFactory.createDataSource(); // this method returns the datasources object
        System.out.println("clientId:"+clientId+":"+"pkgCount"+pkgCount);
        
        try {
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CLIENT_ID", clientId)
                                                                        .addValue("IP_NO_OF_PACKAGES", pkgCount);
        
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CLIENT_VALIDATION_PKG")
                                                  .withProcedureName("validate_client")
                                                  .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_msg", Types.VARCHAR))
//                                                  .declareParameters(new SqlOutParameter("op_balance_alert_flag", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("op_tolerance_email_flag", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("op_balance_email_flag", Types.VARCHAR));
        
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
        
            if (out.get("op_status") != null) {
                opStatus = 
                        Integer.parseInt(String.valueOf(out.get("op_status")));
        
                if (opStatus == 100 || opStatus == 101) {
        
                    
//                    String balance_alert_flag = 
//                        String.valueOf(out.get("op_balance_alert_flag"));
                    String  tolerance_email_flag = 
                        String.valueOf(out.get("op_tolerance_email_flag")); 
                    String  balance_email_flag = 
                        String.valueOf(out.get("op_balance_email_flag"));     
                   
                    clientValidationHashMap.put("status", opStatus);
//                    clientValidationHashMap.put("balance_alert_flag", balance_alert_flag);
                    clientValidationHashMap.put("tolerance_email_flag", tolerance_email_flag);
                    clientValidationHashMap.put("balance_email_flag", balance_email_flag);
                    
                    return clientValidationHashMap;
                } else {
                    clientValidationHashMap.put("status", opStatus);
                    
                    return clientValidationHashMap;
                }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            clientValidationHashMap.put("status", "failure");
            return clientValidationHashMap;
        }
        
        clientValidationHashMap.put("status", "failure");
        return clientValidationHashMap;
    }
}
