package com.aasc.erp.model;

/*
 * @(#)AascAccountNumbersInfoDAO.java     28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.util.AascLogger;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
 * AascAccountNumbersInfoDAO class is DAO factory class class for Account Numbers.
 * @version   1
 * @author    Venkateswaramma Malluru
 * History
 * 
 * 19-Dec-2014  Y Pradeep           Modified nullStrToSpc method.
 * 07-Jan-2015  Y Pradeep           Commented retrieveAccountNumbers() method and connection code which is not in use.
 * 30-Jan-2015  Y Pradeep           Ran self audit, added exception handling and added java doc for all methods.
 * 05-May-2015  Y Pradeep           Added code to save Yes or No instead of true or false in getSaveAccountNumbersInfo method.
 */
public class AascOracleAccountNumbersInfoDAO extends StoredProcedure implements AascAccountNumbersDAO {

    SimpleJdbcCall simpleJdbcCall;
    AascOracleDAOFactory connectionFactory = new AascOracleDAOFactory(); 
    AascAccountNumbersBean aascAccountNumbersInfo = null;
    LinkedList lineLinkedList = null;
    LinkedList lineLinkedListObj = null;
    boolean checkConnection = true;

    int accountNumberId = 0;
    int carrierCode = 0;
    String accountNumber = "";
    String meterNumber = "";
    String accessLicenseNumber = "";
    String accountUserName = "";
    String accountPassword = "";
    String accountNumberActive = "";
    String accountNumberDefault = "";
    String accountNumberNegotiatedFlag = "";
    String negotiatedRates = "";
    String registrationStatus = "";
    int status = 0;

    private static Logger logger = AascLogger.getLogger("AascOracleAccountNumbersInfoDAO");

    /**
     * This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj of type Object
     */
    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }


    /** This method retrives all account numbers from database based on Carrier Code, clientid, locationid.
     * @param carrierCode
     * @param clientId
     * @param locId
     * @param queryTimeOut
     * @return aascAccountNumbersInfo1 of type AascAccountNumbersBean object
     */
    public AascAccountNumbersBean getAccountNumbersInfo(int carrierCode, 
                                                        int clientId, 
                                                        int locId, 
                                                        int queryTimeOut) {

        lineLinkedList = new LinkedList();
        logger.info("Entered getAccountNumbersInfo()");
        AascAccountNumbersBean aascAccountNumbersInfo1 = new AascAccountNumbersBean();
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            logger.info("queryTimeOut ::::::::::" + queryTimeOut);
            logger.info("carrierCode ::::::::::" + carrierCode);
            logger.info("clientId ::::::::::" + clientId);
            logger.info("locId ::::::::::" + locId);
    
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locId);
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("get_account_number_details")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR))
                                                           .declareParameters(new SqlOutParameter("aasc_account_number_details", OracleTypes.CURSOR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
            setQueryTimeout(queryTimeOut);
    //        logger.info("After Execute");
    //        logger.info("out ::: " + out.toString());
            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String error_status = String.valueOf(out.get("op_error_status"));
            logger.info("opStatus :::" + opStatus + " && " + error_status);
    
            aascAccountNumbersInfo1.setErrorStatus(opStatus);
            aascAccountNumbersInfo1.setErrorMessage(error_status);
            if (opStatus == 606) {
    
                Iterator it = ((ArrayList)out.get("OP_ACCT_NUMBER_DETAILS")).iterator();
                HashMap<String, ?> map1 = null;
                while (it.hasNext()) {
                    map1 = (HashMap<String, ?>)it.next();
                    aascAccountNumbersInfo = new AascAccountNumbersBean();
    
                    aascAccountNumbersInfo.setAccountNumberId(((BigDecimal)map1.get("ACCOUNT_NUMBER_ID")).intValue());
                    aascAccountNumbersInfo.setCarrierCode(((BigDecimal)map1.get("CARRIER_CODE")).intValue());
                    aascAccountNumbersInfo.setAccountNumber((String)map1.get("ACCOUNT_NUMBER"));
                    aascAccountNumbersInfo.setMeterNumber((String)map1.get("METER_NUMBER"));
                    aascAccountNumbersInfo.setAccessLicenseNumber((String)map1.get("ACCESS_LICENSE_NUMBER"));
                    aascAccountNumbersInfo.setAccountNumberDefault((String)map1.get("ACCOUNT_DEFAULT"));
                    aascAccountNumbersInfo.setAccountNumberActive((String)map1.get("ACCOUNT_ACTIVE"));
                    aascAccountNumbersInfo.setAccountNumberNegotiatedFlag((String)map1.get("ACCOUNT_NUMBER_NEGOTIATED_FLAG"));
                    aascAccountNumbersInfo.setNegotiatedRates((String)map1.get("NEGOTIATED_RATES"));
                    aascAccountNumbersInfo.setAccountNumberRegistrationFlag((String)map1.get("REGISTRATION_STATUS"));
                    aascAccountNumbersInfo.setAccountUserName((String)map1.get("ACCOUNT_USER_NAME"));
                    aascAccountNumbersInfo.setAccountPassword((String)map1.get("ACCOUNT_PASSWORD"));
    
                    lineLinkedList.add(aascAccountNumbersInfo);
                }
    
                aascAccountNumbersInfo1.setAccountNumbersList(lineLinkedList);
    
            }
        } catch(Exception e){
            logger.info("Got Exception in getAccountNumbersInfo method = "+e.getMessage());
        }
        logger.info("Exited getAccountNumbersInfo()");
        return aascAccountNumbersInfo1;
    }


    /** This method is called from Acount Number details page to save all Account Numbers.
     * @param userId
     * @param aascAccountNumbersInfo1
     * @param queryTimeOut
     * @return returnStatus of type int
     */
    public int saveAllAcountNumbersInfo(int userId, AascAccountNumbersBean aascAccountNumbersInfo1, int queryTimeOut) {
        
        logger.info("Entered saveAllAcountNumbersInfo");

        int returnStatus = 0;
        int locationId = 0;
        int accountNumberId = 0;
        int carrierCode = 0;
        int clientId = 0;
        String accountNumber = "";
        String meterNumber = "";
        String accountUserName = "";
        String accountPassword = "";
        String accessLicenseNumber = "";
        String accountNumberActive = "";
        String accountNumberDefault = "";
        String accountNumberNegotiatedFlag = "";
        String negotiatedRates = "";
        String registrationStatus = "";
        String actionType = "";
        try{
            LinkedList accountNumbersList1 = aascAccountNumbersInfo1.getAccountNumbersList();
            ListIterator accountNumbersIterator1 = accountNumbersList1.listIterator();
            while (accountNumbersIterator1.hasNext()) {
                AascAccountNumbersBean aascAccountNumbersInfo2 = (AascAccountNumbersBean)accountNumbersIterator1.next();
    
                locationId = aascAccountNumbersInfo2.getLocationId();
                accountNumberId = aascAccountNumbersInfo2.getAccountNumberId();
                carrierCode = aascAccountNumbersInfo2.getCarrierCode();
                accountNumber = aascAccountNumbersInfo2.getAccountNumber().trim();
                
                if (aascAccountNumbersInfo2.getMeterNumber() != null) {
                    meterNumber = aascAccountNumbersInfo2.getMeterNumber().trim();
                } else {
                    meterNumber = aascAccountNumbersInfo2.getMeterNumber(); //
                }
                
                if (aascAccountNumbersInfo2.getAccountUserName() != null) {
                    accountUserName = aascAccountNumbersInfo2.getAccountUserName().trim();
                } else {
                    accountUserName = aascAccountNumbersInfo2.getAccountUserName();
                }
                
                if (aascAccountNumbersInfo2.getAccountPassword() != null) {
                    accountPassword = aascAccountNumbersInfo2.getAccountPassword().trim();
                } else {
                    accountPassword = aascAccountNumbersInfo2.getAccountPassword();
                }
                
                accessLicenseNumber = aascAccountNumbersInfo2.getAccessLicenseNumber();
                accountNumberActive = aascAccountNumbersInfo2.getAccountNumberActive();
                accountNumberDefault = aascAccountNumbersInfo2.getAccountNumberDefault();
                accountNumberNegotiatedFlag = aascAccountNumbersInfo2.getAccountNumberNegotiatedFlag();
                negotiatedRates = aascAccountNumbersInfo2.getNegotiatedRates();
                registrationStatus = aascAccountNumbersInfo2.getAccountNumberRegistrationFlag();
                actionType = aascAccountNumbersInfo2.getActionType();
                clientId = aascAccountNumbersInfo2.getClientId();

                returnStatus = saveEachAccountNumbersInfo(locationId, accountNumberId, 
                                                            carrierCode, accountNumber, 
                                                            meterNumber, accessLicenseNumber, 
                                                            accountNumberDefault, accountNumberActive, 
                                                            accountNumberNegotiatedFlag, negotiatedRates, actionType, 
                                                            userId, registrationStatus, accountUserName, 
                                                            accountPassword, clientId);
    
                aascAccountNumbersInfo2.setErrorStatus(returnStatus);
    
                if (returnStatus != 704) {
                    return returnStatus;
                }
    
            }
        } catch(Exception e){
            logger.info("Got Exception in saveAllAcountNumbersInfo method = "+e.getMessage());
        }
        logger.info("Exited saveAllAcountNumbersInfo");
        return returnStatus;
    }

    /** This method save each account number based on locationid , clientid and userid. This method is called internally from 
                saveAllAcountNumbersInfo() method.
     * @param locationId
     * @param accountNumberId
     * @param carrierCode
     * @param accountNumber
     * @param meterNumber
     * @param accessLicenseNumber
     * @param accountDefault
     * @param accountActive
     * @param accountNumberNegotiatedFlag
     * @param negotiatedRates
     * @param actionType
     * @param userId
     * @param registrationStatus
     * @param userName
     * @param password
     * @param clientId
     * @return returnStatus of type int
     */
    public int saveEachAccountNumbersInfo(int locationId, int accountNumberId, int carrierCode, String accountNumber, 
                                          String meterNumber, String accessLicenseNumber, String accountDefault, String accountActive, 
                                          String accountNumberNegotiatedFlag, String negotiatedRates, String actionType, int userId, 
                                          String registrationStatus, String userName, String password, int clientId) {
        int returnStatus = 0;
        logger.info("Process is in saveEachAccountNumbersInfo method");
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", clientId)
                                                                        .addValue("ip_location_id", locationId)
                                                                        .addValue("account_number_id", accountNumberId)
                                                                        .addValue("ip_carrier_code", carrierCode)
                                                                        .addValue("ip_account_number", accountNumber)
                                                                        .addValue("ip_meter_number", meterNumber)
                                                                        .addValue("ip_license_number", accessLicenseNumber)
                                                                        .addValue("ip_account_default", accountDefault)
                                                                        .addValue("ip_account_active", accountActive)
                                                                        .addValue("ip_negotiated_flag", accountNumberNegotiatedFlag)
                                                                        .addValue("ip_negotiated_rates", negotiatedRates)
                                                                        .addValue("ip_user_id", userId)
                                                                        .addValue("ip_action_type", actionType)
                                                                        .addValue("IP_REGISTRATION_STATUS", registrationStatus)
                                                                        .addValue("IP_ACCOUNT_USER_NAME", userName)
                                                                        .addValue("ip_account_password", password);
    
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("save_account_number")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
    
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
    //        logger.info("After Execute");
    //        logger.info("out ::: " + out.toString());
            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorstatus = String.valueOf(out.get("op_error_status"));
    
            logger.info("opStatus ::" + opStatus + " && error_status::" + errorstatus);
    
            returnStatus = opStatus;
        } catch(Exception e){
            logger.info("Got Exception in saveEachAccountNumbersInfo method = "+e.getMessage());
        }
        logger.info("Exited saveEachAccountNumbersInfo method");
        return returnStatus;
    }

    /** This method save account number into database based on locationId and userid.
     * @param userId
     * @param locationId
     * @param aascAccountNumbersInfoDetail
     * @param queryTimeOut
     * @return aascAccountNumbersInfoDetail of type AascAccountNumbersBean object.
     */
    public AascAccountNumbersBean getSaveAccountNumbersInfo(int userId, 
                                                            int locationId, 
                                                            AascAccountNumbersBean aascAccountNumbersInfoDetail, 
                                                            int queryTimeOut) {
        logger.info("Process is in getSaveAccountNumbersInfo method");

        String negotiatedFlag = ""; 
        String registrationStatus = "";
        try {
            negotiatedFlag = aascAccountNumbersInfoDetail.getAccountNumberNegotiatedFlag();
        } catch (Exception e) {
            negotiatedFlag = "";
        }
        if (negotiatedFlag == "" || negotiatedFlag == null) {
            negotiatedFlag = "N";
        }

        String negotiatedRate = "";
            
        try {
            negotiatedRate = aascAccountNumbersInfoDetail.getNegotiatedRates();
        } catch (Exception e) {
            negotiatedRate = "";
        }
        if (negotiatedRate == "" || negotiatedRate == null) {
            negotiatedRate = "N";
        }
        if("Yes".equalsIgnoreCase(aascAccountNumbersInfoDetail.getAccountNumberRegistrationFlag())){
            registrationStatus = "Yes";
        }else if("No".equalsIgnoreCase(aascAccountNumbersInfoDetail.getAccountNumberRegistrationFlag())){
            registrationStatus = "No";
        }
        try{
            DataSource datasource = connectionFactory.createDataSource(); // this method returns the datasources object
            
            SqlParameterSource inputparams = new MapSqlParameterSource().addValue("ip_client_id", aascAccountNumbersInfoDetail.getClientId())
                                                                        .addValue("ip_location_id", locationId)
                                                                        .addValue("account_number_id", aascAccountNumbersInfoDetail.getAccountNumberId())
                                                                        .addValue("ip_carrier_code", aascAccountNumbersInfoDetail.getCarrierCode())
                                                                        .addValue("ip_account_number", aascAccountNumbersInfoDetail.getAccountNumber())
                                                                        .addValue("ip_meter_number", aascAccountNumbersInfoDetail.getMeterNumber())
                                                                        .addValue("ip_license_number", aascAccountNumbersInfoDetail.getAccessLicenseNumber())
                                                                        .addValue("ip_account_default", aascAccountNumbersInfoDetail.getAccountNumberDefault())
                                                                        .addValue("ip_account_active", aascAccountNumbersInfoDetail.getAccountNumberActive())
                                                                        .addValue("ip_negotiated_flag", negotiatedFlag)
                                                                        .addValue("ip_negotiated_rates", negotiatedRate)
                                                                        .addValue("ip_user_id", userId)
                                                                        .addValue("ip_action_type", aascAccountNumbersInfoDetail.getActionType())
                                                                        .addValue("IP_REGISTRATION_STATUS", registrationStatus)
                                                                        .addValue("IP_ACCOUNT_USER_NAME", aascAccountNumbersInfoDetail.getAccountUserName())
                                                                        .addValue("ip_account_password", aascAccountNumbersInfoDetail.getAccountPassword());
       
    
            simpleJdbcCall = new SimpleJdbcCall(datasource).withCatalogName("AASC_ERP_CARRIER_PKG")
                                                           .withProcedureName("save_account_number")
                                                           .declareParameters(new SqlOutParameter("op_status", Types.INTEGER))
                                                           .declareParameters(new SqlOutParameter("op_error_status", Types.VARCHAR));
    
            Map<String, Object> out = simpleJdbcCall.execute(inputparams);
    
    
    //        logger.info("After Execute");
    //        logger.info("out ::: " + out.toString());
            int opStatus = Integer.parseInt(String.valueOf(out.get("op_status")));
            String errorstatus = String.valueOf(out.get("op_error_status"));
    
            logger.info("opStatus ::::::" + opStatus + " && error_status::" + errorstatus);
    
            aascAccountNumbersInfoDetail.setErrorStatus(opStatus);
            aascAccountNumbersInfoDetail.setErrorMessage(errorstatus);
        } catch(Exception e){
            logger.info("Got Exception in getSaveAccountNumbersInfo method = "+e.getMessage());
        }
        logger.info("Exited getSaveAccountNumbersInfo method");
        return aascAccountNumbersInfoDetail;
    }

}
