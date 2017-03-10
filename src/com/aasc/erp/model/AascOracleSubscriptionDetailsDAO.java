package com.aasc.erp.model;

/*
 * @(#)AascCustomerInfo.java        
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
 
 /**
  * AascOracleSubscriptionDetailsDAO is a class which is used for saving and retreiving subscription details for role 3.
  *
  * @author Suman Gunda
  *
  * @version 1.0
  *
  * @since  25/02/2016
  *
  * History
  *
*/

import com.aasc.erp.bean.AascCustomerBean;
import com.aasc.erp.bean.AascRatesTable;
import com.aasc.erp.bean.AascSubscriptionDetailsBean;

import com.aasc.erp.util.AascLogger;

import com.aasc.erp.util.TripleDES;

import java.math.BigDecimal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import java.util.logging.Logger;

import oracle.jdbc.OracleTypes;



import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class AascOracleSubscriptionDetailsDAO implements AascSubscriptionDetailsDAO{

    public AascOracleSubscriptionDetailsDAO() {
    }
    
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory();
    SimpleJdbcCall simpleJdbcCall;
    private static Logger logger = 
        AascLogger.getLogger("AascOracleSubscriptionDetailsDAO");
    AascSubscriptionDetailsBean aascSubscriptionDetailsBean = null;
    public AascSubscriptionDetailsBean subscriptionDetails(int  clientId){
        logger.info("Entered into subscriptionDetails() ");
//        System.out.println("clientid::::"+clientId);
                try{
            DataSource dataSource = connectionFactory.createDataSource();
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("IP_CLIENT_ID", clientId);
            
            simpleJdbcCall = 
                    new SimpleJdbcCall(dataSource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("GET_SUBSCRIPTION_DETAILS")
                                                  .declareParameters(new SqlOutParameter("op_status",Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("OP_ERROR_MESSAGE", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("AASC_SUBSCRIPTION_DETAILS", OracleTypes.CURSOR));
            
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
//            System.out.println("out::::"+out);
            
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("OP_ERROR_MESSAGE"));
            logger.info("Op Status::::"+opStatus+"::Error message:::::"+errorStatus);
            Iterator it = 
                ((ArrayList)out.get("OP_SUBSCRIPTION_DETAILS")).iterator();
            HashMap<String, ?> map1 = null;
            aascSubscriptionDetailsBean = new AascSubscriptionDetailsBean();
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();
                aascSubscriptionDetailsBean.setClientID(((BigDecimal)map1.get("CLIENT_ID")).intValue());
                aascSubscriptionDetailsBean.setCustomerType((String)map1.get("CUSTOMER_TYPE"));
                aascSubscriptionDetailsBean.setInvoiceType((String)map1.get("INVOICE_TYPE"));
                aascSubscriptionDetailsBean.setMonthlyEstimatedTransactionRange((String)map1.get("TRANSACTION_RANGE"));
                aascSubscriptionDetailsBean.setPricingDuration((String)map1.get("PRICING_DURATION"));
                
                if (map1.get("START_DATE") != null) {
                    String startDate = 
                        nullStrToSpc(((Timestamp)map1.get("START_DATE")).toString());
                    String endDate = 
                        nullStrToSpc(((Timestamp)map1.get("SUBSCRIPTION_END_DATE")).toString());
                    
                
                    aascSubscriptionDetailsBean.setCustomerStartDate(dateConvert(startDate.substring(0, 
                                                                                         startDate.indexOf(' '))).toString());
                    aascSubscriptionDetailsBean.setSubscriptionEndDate((String)dateConvert(endDate.substring(0, 
                                                                                     endDate.indexOf(' '))).toString());
                } else {
                    aascSubscriptionDetailsBean.setCustomerStartDate(null);
                    aascSubscriptionDetailsBean.setSubscriptionEndDate(null);
                }
                
                aascSubscriptionDetailsBean.setCumulativePackageCount(((BigDecimal)map1.get("CUMULATIVE_PKG_COUNT")).intValue());
                aascSubscriptionDetailsBean.setCurrentPackageBalance(((BigDecimal)map1.get("CURRENT_PKG_BALANCE")).intValue());
                aascSubscriptionDetailsBean.setSubscriptionExpiryFlag((String)map1.get("SUBSCRIPTION_EXPIRY_FLAG"));
                
//                System.out.println(aascSubscriptionDetailsBean.getClientID() + "\t" + aascSubscriptionDetailsBean.getCurrentPackageBalance()  + "\t" + aascSubscriptionDetailsBean.getTotalFee());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("End of subscriptionDetails() ");
        return aascSubscriptionDetailsBean;
    }
    
    public int updateTransactionDetails(AascSubscriptionDetailsBean aascSubscriptionDetailsBean){
        logger.info("Entered updateTransactionDetail()");
        int opStatus=0;
        try {

            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
                SqlParameterSource inputparams =    new MapSqlParameterSource().addValue("IP_CLIENT_ID", aascSubscriptionDetailsBean.getClientID())
                                                                                .addValue("ip_customer_type", nullStrToSpc(aascSubscriptionDetailsBean.getCustomerType()))
                                                                                .addValue("ip_invoice_type", nullStrToSpc(aascSubscriptionDetailsBean.getInvoiceType()))
                                                                                .addValue("ip_transaction_range", nullStrToSpc(aascSubscriptionDetailsBean.getMonthlyEstimatedTransactionRange()))
                                                                                .addValue("ip_pricing_duration", nullStrToSpc(aascSubscriptionDetailsBean.getPricingDuration()))
                                                                                .addValue("ip_transaction_count", nullStrToSpc(aascSubscriptionDetailsBean.getTransactionCount()));
                                                                                
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("update_transacction_details")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("opStatus ::::::::" + opStatus + 
                               " && error_status ::::::::::" + error_status);
                                                  
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception::::"+e.getMessage());
        }
        
        logger.info("End of updateTransactionDetail()");
        
        return opStatus;
    }
    
    
    public LinkedList getRateDetails()
    {
        logger.info("Entered getRateDetails()");
        LinkedList rateDetailsList = null;
        
        
        try {
            DataSource datasource = 
                connectionFactory.createDataSource(); // this method returns the datasources object
            simpleJdbcCall = 
                    new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_USER_CONTROL_PKG")
                                                  .withProcedureName("get_rates_table")
                                                  .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                  .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                  .declareParameters(new SqlOutParameter("AASC_RATE_TABLE", OracleTypes.CURSOR));
            Map<String, Object> out = simpleJdbcCall.execute();
            
            
            int opStatus = 
                Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorStatus = String.valueOf(out.get("op_error_status"));
            Iterator it = 
                ((ArrayList)out.get("OP_RATE_TABLE")).iterator();
            
//            System.out.println("rates from :::"+out);
            AascRatesTable aascRatesTable = null;
        
            rateDetailsList = new LinkedList();
            HashMap<String, ?> map1 = null;
            while (it.hasNext()) {
                map1 = (HashMap<String, ?>)it.next();
                aascRatesTable = new AascRatesTable();
                
                
                aascRatesTable.setCustomerType((String)map1.get("CUSTOMER_TYPE"));
                aascRatesTable.setTransactionRange((String)map1.get("TRANSACTION_RANGE"));
                aascRatesTable.setDurationType((String)map1.get("DURATION_TYPE"));
                aascRatesTable.setTotalAmount(((BigDecimal)map1.get("TOTAL_AMOUNT")).toString());
                aascRatesTable.setPricePerPkg(((BigDecimal)map1.get("PRICE_PER_PKG")).toString());
                
               
                rateDetailsList.add(aascRatesTable);
            }
            
        
            logger.info("AASC_ERP_USER_CONTROL_PKG.get_rates_table opStatus:" + 
                        opStatus + "\t errorStatus:" + errorStatus);
        }
        
        catch (Exception e) {
            logger.severe("exception in getting customer details" + 
                          e.getMessage());
//                        e.printStackTrace();
        }
        
        logger.info("Exit from getRateTable()");
//        System.out.println("size:::::"+rateDetailsList.size());
        return rateDetailsList;    
    }
    
    /**
     * This method for converting an Object type into a String type.
     * @param obj
     * @return String
     */
    public String nullStrToSpc(Object obj) {
    
        String spcStr = "";
    
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
    
            return spcStr;
    
        } // closing the if block
        else {
    
            return obj.toString();
    
        } // closing the else block
    
    } // closing the nullStrToSpc method 
    
     /**
     
      * This Method converts String to java.sql.Date type.
     
      * @param date String
     
      * @return java.sql.Date 
     
     */
     private java.sql.Date dateConvert(String date) {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         
     
         try {
     
             java.util.Date date1 = formatter.parse(date);
             java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
             
             return sqlDate;
     
         } catch (ParseException e) {
     
             e.printStackTrace();
         }
         return null;
     }
}
