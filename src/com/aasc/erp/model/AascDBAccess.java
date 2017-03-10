/*
  * @(#)AascDBAccess.java
  * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
  * All rights reserved.
  * ====================================================================
  * This class is used to get data from database
  @History
  * 21/11/2011   Eswari M      Modified getWeighingScaleName method to remove hardcodings of vendorId, ProductIds and multiple method definitions for each model.
  * 10/09/2013   Eshwari M     Modified code for role Id 2 Shipping
  * 11/12/2013   Tina Soni     Added code for reading wsurl, wsuser and wspwd from database and call the webservice using those details
  * 12/12/2013   Guru Raj      Modified code to decrypt the webservice password 
  * 17/06/2014   Ravi Teja     Added input parameter user id to the procedure aasc_weighing_scale_name
  * 02/07/2014   Suman Gunda   Added code for getting protocol from data base.
  * 07/01/2015   Y Pradeep     Modified code to get connection from datasource.
  */
  
package com.aasc.erp.model;

import com.aasc.erp.util.AascLogger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import com.aasc.erp.util.TripleDES;
public class AascDBAccess {
    private static Logger logger = AascLogger.getLogger("AascDBAccess");
    String weightClass = "";
    
    //5_7 Merging - Weighing Scale code optimisation enhancement
    String vendorId;
    String productId1;
    String productId2;
    String topology;
    
    Connection connection = null;
    CallableStatement oCallableStatement = null;
    AascOracleDAOFactory aascOracleDAOFactory = null;
    CallableStatement reportRespCallStmt = null;
    CallableStatement upsCallableStatement = null;
    CallableStatement versionCallableStatement = null;
    CallableStatement wSCallableStatement = null;

    int ORACLE = 1;

    private Connection getConnection() {
        try {

            AascDAOFactory aascDAOFactory = 
                AascDAOFactory.getAascDAOFactory(ORACLE);
            aascOracleDAOFactory = new AascOracleDAOFactory();
            connection = aascOracleDAOFactory.createDataSource().getConnection();
        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return connection;
    }


   
    
    public String getMessage(String key) {
        ResourceBundle resources;
        String message = "";
        try {
            resources = 
                    ResourceBundle.getBundle("ApplicationResources");
            logger.info("After the resource bundle");
            message = resources.getString(key);
//            System.out.println("after getting message");
            logger.info("messages from prop files :: " + message);
        } catch (MissingResourceException mre) {
            logger.info("ApplicationResources.properties not found");
        }
        logger.info("inside the DBAccess ----------->"+message);
        return message;
    }
}
