package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascShipToLocationsInfo;
import com.aasc.erp.model.AascCustAccountNumsDAO;
import com.aasc.erp.model.AascOracleCustAccountNumsDAO;
import com.aasc.erp.model.AascOracleShipToLocationsDAO;
import com.aasc.erp.model.AascOracleUserControlDAO;
import com.aasc.erp.model.AascShipToLocationsDAO;
import com.aasc.erp.model.AascUserControlDAO;
import com.aasc.erp.util.AascLogger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*========================================================================+
@(#)AascShipToLocationDelegate.java 19/01/2015
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
* author Sunanda K
* 10/03/2015  Sunanda K  added code for retaining the saved and edited values on the page by saving the session object(bean)
* 13/05/2015   Y Pradeep   Removed locationId from getAllCustomerLocationDetails(), getShipToCustomersList() method call to get Ship To Locations independent to Ship From Location 
                           and renamed locationId1 to customerLocationid to diffentiate ship from location id and ship to location id.
* 10/06/2015   Suman G      Added code to fix #2962                            
+===========================================================================*/

   
        
public class AascShipToLocationDelegate {
    public AascShipToLocationDelegate() { 
    }
    static Logger logger = 
        AascLogger.getLogger(" AascShipToLocationDelegate "); // logger object
        
    AascShipToLocationsInfo aascShipToLocationsInfo = 
        new AascShipToLocationsInfo();

    
    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
    /**

     * This Method used to set country codes in session.

     * @param session HttpSession 

     * @return  String

     */
    
     AascShipToLocationsDAO aascShipToLocationsDAO = 
         new AascOracleShipToLocationsDAO();
    
    public String setCountryDetails(HttpSession session) {
        try {
            LinkedList countryCodelist = 
                aascUserControlDAO.getCountryCodeDetails();
            session.setAttribute("countryCodelist", (Object)countryCodelist);
            
            Map countryNameHashMap = 
                new TreeMap((HashMap<String,String>)aascUserControlDAO.getCountryNameDetails());
            session.setAttribute("countryNameHashMap", (Object)countryNameHashMap);
            return "success";
        } catch (Exception e) {
            return "failure";
        }
    }
   
    public String createLocation(HttpServletRequest request){
        int clientId =Integer.parseInt((String)request.getParameter("client_Id"));
        
        int locationId =Integer.parseInt((String)request.getParameter("location_Id"));
      
        HttpSession session = request.getSession();
                                                        
        try {
            LinkedList customersList = new LinkedList();
            customersList =aascShipToLocationsDAO.getShipToCustomersList(clientId);
            session.setAttribute("customersList", (Object)customersList);
          
        } catch (Exception e) {
            logger.severe("Error getting shipment customers list from databse");
            session.setAttribute("customersList", (Object)new LinkedList());
        }
        return "Create";
    }
    
    public String editLocation(HttpServletRequest request){
    
        LinkedList countryCodelist = 
            aascUserControlDAO.getCountryCodeDetails();
        
        Map countryNameHashMap = 
            new TreeMap((HashMap<String,String>)aascUserControlDAO.getCountryNameDetails());
        HttpSession session = request.getSession();
        logger.info("actiontype is Edit");
        int customerLocationid = 
            Integer.parseInt((String)request.getParameter("locationIDnum"));
        LinkedList custAccNumList = new LinkedList();

        custAccNumList = null;
        AascCustAccountNumsDAO aascCustAccountNumsDAO = 
            new AascOracleCustAccountNumsDAO();
        try {
            custAccNumList = 
                    aascCustAccountNumsDAO.getCustAccountNums(customerLocationid);
        } catch (Exception e) {
            e.getMessage();
        }
        session.setAttribute("countryCodelist", (Object)countryCodelist);
        session.setAttribute("countryNameHashMap", (Object)countryNameHashMap);
        session.setAttribute("custAccNumList", (Object)custAccNumList);
        return "Edit";
    }
    
    public String addNewLocation(HttpSession session, HttpServletRequest request,AascShipToLocationsInfo aascShipToLocationsInfo){
        int clientId =Integer.parseInt((String)request.getParameter("client_Id"));
        int locationId =Integer.parseInt((String)request.getParameter("location_Id"));
        int userId = Integer.parseInt((String)request.getParameter("user_Id"));
        int status = 
            aascShipToLocationsDAO.createCustomerLocation(aascShipToLocationsInfo);
        if (status == 600 || status == 599) {
            request.setAttribute("key", "aasc600");
            try {
                LinkedList shipToCustomersList = new LinkedList();
                shipToCustomersList =aascShipToLocationsDAO.getAllCustomerLocationDetails(clientId,userId);

                session.setAttribute("shipToCustomersList", 
                                    (Object)shipToCustomersList);
                LinkedList customersList = new LinkedList();
                customersList =aascShipToLocationsDAO.getShipToCustomersList(clientId);
                session.setAttribute("customersList", (Object)customersList);
                session.setAttribute("aascShipToLocationsInfo", 
                                     aascShipToLocationsInfo);

            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
            }

        } else if (status == 10) {
            request.setAttribute("key", "aasc610");
        } else {
            request.setAttribute("key", "aasc603");
        }
        return "success";

    
    }
    
    public String editLocationDetails(HttpSession session, HttpServletRequest request,AascShipToLocationsInfo aascShipToLocationsInfo){
    int customerLocationId = 
        Integer.parseInt((String)request.getParameter("customerLocationId"));
    int clientId =Integer.parseInt((String)request.getParameter("client_Id"));
    
    int userId = Integer.parseInt((String)request.getParameter("user_Id"));
    int locationId =Integer.parseInt((String)request.getParameter("location_Id"));
    aascShipToLocationsInfo.setClientId(clientId);
    aascShipToLocationsInfo.setUserId(userId);
    aascShipToLocationsInfo.setLocationId(locationId);
    
    aascShipToLocationsInfo.setCustomerLocationId(customerLocationId);
    
    int status = 
        aascShipToLocationsDAO.editCusotmerLocation(aascShipToLocationsInfo);
    logger.info("---------------status after saving............" + 
                status);
    if (status == 604) {
        request.setAttribute("key", "aasc604");
        try {
            LinkedList shipToCustomersList = new LinkedList();
            shipToCustomersList = 
                    aascShipToLocationsDAO.getAllCustomerLocationDetails(clientId,userId);
            
            session.setAttribute("shipToCustomersList", 
                                 (Object)shipToCustomersList);
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
       // return "success";
        session.setAttribute("aascShipToLocationsInfo", 
                             aascShipToLocationsInfo);
        return "Edit";
    } else if (status == 605) {
        request.setAttribute("key", "aasc610");
    } else {
        request.setAttribute("key", "aasc605");
    }
    session.setAttribute("aascShipToLocationsInfo", 
                         aascShipToLocationsInfo);
    return "success";

    
  
}
    
}
