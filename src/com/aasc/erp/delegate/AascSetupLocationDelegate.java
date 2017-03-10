package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.model.AascOracleSetupLocationDAO;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascSetupLocationDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
/*========================================================================+
@(#)AascSetupLocationDelegate.java 24/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/


/**
 * AascSetupLocationDelegate class pass data to AascSetupLocationDAO class.
 * @version      1
 * @author       N Srisha
 *  History
 *  17-Dec-2014  Eshwari M   Merged Sunanda code for SC Lite
 *  07-Jan-2014  Eshwari M   Merged Sunanda's code : modified getLocationDetails method to return Hashmap instead od LinkedList and added comments and java.
 *  20-Jan-2014  Sunanda K   Added the correct return type for the method getLocationDetails(),added input param for setCountryDetails() method documentation,
                                removed initcap for method AddOrUpdateNewLocation() and renamed as addOrUpdateNewLocation()
 * 20-Jan-2015  Suman G      Changed AddOrUpdateNewLocation() to addOrUpdateNewLocation().
 **/
public class AascSetupLocationDelegate {
        private static Logger logger = 
        AascLogger.getLogger("AascSetupLocationDelegate");
        int clientId;
        int userId;
        AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
        AascSetupLocationDAO aascSetupLocationDAO = 
        new AascOracleSetupLocationDAO();
        /**
     * commonAction Method gets clientId and userId from session
                        and sets to object.
     * @param  session, aascSetupLocationBean object of class AascSetupLocationBean  
     * @return  aascSetupLocationBean object
     */

    public AascSetupLocationBean commonAction( 
                                              HttpSession session, 
                                              AascSetupLocationBean aascSetupLocationBean) {
        logger.info("Entered commonAction in AascSetupLocationDelegate");

        try {
            int clientId = 
                Integer.parseInt(session.getAttribute("client_id").toString());
            int userId = 
                Integer.parseInt(session.getAttribute("user_id").toString());
            
            aascSetupLocationBean.setClientId(clientId);
            aascSetupLocationBean.setUserId(userId);

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        return aascSetupLocationBean;
    }


    /**

     * AascSetupLocationBean Method saves or updates new Locationdeatils.
     * @param   aascSetupLocationBean    bean        of class AascSetupLocationBean.
     * @return  aascSetupLocationBean    object      returns bean object containing recently added locationDetails.

     */
    public AascSetupLocationBean addOrUpdateNewLocation(AascSetupLocationBean aascSetupLocationBean) {
        logger.info("Entered AddOrUpdateNewLocation in AascSetupLocationDelegate");
        int status =aascSetupLocationDAO.createOrUpdateLocation(aascSetupLocationBean);
        aascSetupLocationBean.setOpStatus(status);
        return aascSetupLocationBean;
    }

    /**
     * setCountryDetails() method for setting country name details into session attribute countryNameHashMap.
     * @param  session   session object 
     * @return String   returns "success" or "fail" of Setting CountryNamevalues
     * 
     */
    public String setCountryDetails(HttpSession session) {
        try {
            LinkedList countryCodelist = 
                aascUserControlDAO.getCountryCodeDetails();
            
            session.setAttribute("countryCodelist", (Object)countryCodelist);
            //logger.info("in setCountryDetails setting countryNameHashMap" + countryCodelist);
            Map countryNameHashMap = 
                new TreeMap((HashMap<String,String>)aascUserControlDAO.getCountryNameDetails());
            
            session.setAttribute("countryNameHashMap", (Object)countryNameHashMap);
            return "success";
        } catch (Exception e) {
            return "failure";
        }
    }


    /**
     * getLocationDetails Method calls aascOracleSetupLocationDAO to get Location details.
     * @param aascSetupLocationBean object of class AascSetupLocationBean  
     * @return   locationHashMap      HashMap   retuns the locationDetails from DB are saved into HashMap 
     */
    public HashMap getLocationDetails(AascSetupLocationBean aascSetupLocationBean) {
        logger.info("Entered getLocationDetails in AascSetupLocationDelegate");

        int clientId = aascSetupLocationBean.getClientId();
        HashMap locationHashMap = null ;
        try {
            locationHashMap = aascSetupLocationDAO.getAllLocationDetails(clientId);     

        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        return locationHashMap;
    }


}
