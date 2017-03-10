package com.aasc.erp.delegate;
/*========================================================================+
@(#)AascUserControlDelegate.java 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

/**
 * AascUserControlDelegate class pass data to AascUserControlDAO class.
 *              
 * @version   1
 * @author        N Srisha
 * History:
 * 
 *  17-Dec-2014   Eshwari M    Merged Sunanda code for SC Lite.
 *  06-Jan-2015   Y Pradeep    Added code to get version number from userAuthentication method and from ApplicationResources file assigning them to session.
 *  07-Jan-2015   Eshwari M    Modified userAuthentication method to get firstName and lastName of the user logged in and setting in session to display in index page.
 *  21-Apr-2015   Suman G      Added email Address to fix #2754   
 *  31-Aug-2015   Suman G      Added code for trial status flag and display trial period expiry message
 *  04-NOV-2015   Suman G      Added code to send mail to the customer whenever his transaction balance is over
 **/
 
public class AascUserControlDelegate {
    private static Logger logger = 
        AascLogger.getLogger("AascUserControlDelegate");
        
    
     /**
      * userAuthentication Method interacts with the database 
                 and retrieves User Details.
      * @param session HttpSession, userName String and password String 
      * @return HashMap 
      */                 
                  
     public String userAuthentication(HttpSession session, HttpServletRequest request, String userName, String password){
        logger.info("Entered userAuthentication in AascUserControlDelegate");
         AascUserControlDAO aascUserControlDAO = 
                  new AascOracleUserControlDAO();                       // creates object of class AascOracleUserControlDAO                
         HashMap userHashMap =aascUserControlDAO.userAuthentication(userName, password);
         String result = (String)userHashMap.get("status");
         logger.info("result"+result);
                                         
         if ("success".equalsIgnoreCase(result)) {
             ArrayList carrierDetailsList = null;
            logger.info("userHashMap.get(\"role_id\") :: "+userHashMap.get("role_id")); 
              if((Integer)userHashMap.get("role_id")==1 || (Integer)userHashMap.get("role_id")==2){
                   carrierDetailsList= new ArrayList<HashMap>();
                  HashMap<String, String> carrierHM = new  HashMap<String, String>();
                  carrierHM.put("OP_SHIPMENTS_COUNT","3");
                  carrierHM.put("OP_TOTAL_FREIGHT_CHARGES","209");
                  carrierHM.put("OP_CARRIER_CODE","115");
                  carrierHM.put("OP_CARRIER_NAME","Stamps.com");
                  carrierDetailsList.add(carrierHM);
                  carrierHM = new  HashMap<String, String>();
                  logger.info("carrierHM :: "+carrierHM); 
                  carrierHM.put("OP_SHIPMENTS_COUNT","1");
                  carrierHM.put("OP_TOTAL_FREIGHT_CHARGES","214");
                  carrierHM.put("OP_CARRIER_CODE","114");
                  carrierHM.put("OP_CARRIER_NAME","DHL");
                  carrierDetailsList.add(carrierHM);
                  logger.info("carrierHM11 :: "+carrierHM);
                  carrierHM = new  HashMap<String, String>();
                  carrierHM.put("OP_SHIPMENTS_COUNT","4");
                  carrierHM.put("OP_TOTAL_FREIGHT_CHARGES","99");
                  carrierHM.put("OP_CARRIER_CODE","110");
                  carrierHM.put("OP_CARRIER_NAME","FDXE");
                  carrierDetailsList.add(carrierHM);
                  carrierHM = new  HashMap<String, String>();
                  carrierHM.put("OP_SHIPMENTS_COUNT","5");
                  carrierHM.put("OP_TOTAL_FREIGHT_CHARGES","85");
                  carrierHM.put("OP_CARRIER_CODE","100");
                  carrierHM.put("OP_CARRIER_NAME","UPS");
                  carrierDetailsList.add(carrierHM);
                  carrierHM = null;
                  logger.info("carrierDetailsList :: "+carrierDetailsList);
              }
              else{
                   carrierDetailsList= aascUserControlDAO.getMonthlyCarrierDetails((Integer)userHashMap.get("client_id"),(Integer)userHashMap.get("location_id"));
              }
                session.setAttribute("role_id", userHashMap.get("role_id"));
                session.setAttribute("user_id", userHashMap.get("user_id"));
                session.setAttribute("location_id", userHashMap.get("location_id"));
                session.setAttribute("client_id", userHashMap.get("client_id"));
                session.setAttribute("LoginuserName", userName); 
                session.setAttribute("cloudLabelPath",userHashMap.get("cloudLabelPath"));
                session.setAttribute("version",userHashMap.get("version"));
                session.setAttribute("firstName",userHashMap.get("firstName"));
                session.setAttribute("lastName",userHashMap.get("lastName"));
                session.setAttribute("emailAddress",userHashMap.get("emailAddress"));
                session.setAttribute("trialStatus",userHashMap.get("trialStatus"));
                session.setAttribute("clientMailId",userHashMap.get("clientMailId"));
                session.setAttribute("addressBookLevel",userHashMap.get("addressBookLevel"));
                session.setAttribute("carrierMonthlyDetails",carrierDetailsList);
                logger.info("client_id ::"+userHashMap.get("client_id"));
                
                ResourceBundle resources;
                try {
                    resources = ResourceBundle.getBundle("ApplicationResources");
                    String patchVersion = resources.getString("shipConsoleVersion");
                    session.setAttribute("patchVersion", patchVersion);
                } catch (MissingResourceException mre) {
                    logger.info("ApplicationResources.properties not found");
                }
                logger.info("\"cloudLabelPath\",cloudLabelPath::::"+userHashMap.get("cloudLabelPath"));
                logger.info("user_id ::"+userHashMap.get("user_id"));
                return "success";
         }
       else  if ("expired".equalsIgnoreCase(result)) {
           request.setAttribute("userName", userName);
           request.setAttribute("key", "aasc414");
           return "error";
       }
         else {
                request.setAttribute("userName", userName);
                request.setAttribute("key", "aasc405");
                return "error";
         }
           
     }
     
}
