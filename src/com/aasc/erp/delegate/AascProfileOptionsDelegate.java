package com.aasc.erp.delegate;


import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascOracleProfileOptionsDAO;
import com.aasc.erp.model.AascOracleSetupLocationDAO;
import com.aasc.erp.model.AascProfileOptionsDAO;

import com.aasc.erp.model.AascSetupLocationDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*

 * @ AascProfileOptionsDelegate.java        07/08/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */
 
 /**

  * AascProfileOptionsDelegate class.

  * @author Suman Gunda

  * @creation 07/08/2014
  
  * @History
  * 24-12-2014      Jagadish Jain   Added code required for proper saving and retrieval of profile options. Also added code to control the profile options for different roles. 
  * 07/01/2015      Eshwari M       Merged Sunanda's code of calling getAllLocationDetails() method call as this method modiifed
  * 20/01/2015      Y Pradeep       Modified code for Address Validationa dn Freight Shopping. Removed Average pallet weight and lable printing check box.  
  * 24/01/2015      Y Pradeep       Modified code to save Address Validaion check box and other details.
  * 10/03/2015      Y Pradeep       Removed method getRreferenceValue() to get reference1value and reference2value select filed.
  * 08/04/2015      Y Pradeep       Commented code to display Location LOV enabled for all roles except role 5. Bug #2807.
  * 15/04/2015      Eshwari M       Modified code to fix bug # 2582
  */

public class AascProfileOptionsDelegate {
    
    AascProfileOptionsDAO aascProfileOptionsDAO = new AascOracleProfileOptionsDAO();
    
    private static Logger logger = 
        AascLogger.getLogger("AascProfileOptionsDelegate");
    int ORACLE = 1;
    private int locationIdInt;
    private int clientId;
    LinkedList sessionList = null;
    AascProfileOptionsDAO aascProfileOptionsDao; // declaring the variable for storing the object of the AascProfileOptionDAO class
    AascDAOFactory aascDAOFactory;
        AascDAOFactory aascDaoFactory;
   String key="";     
    
    
    /**
    * commonAction() method of AascProfileOptionsDelegate class has two paramters session and request
    * object and returns a String object with success or failure message.
    * This method is used to put all the common code that is required for different action in ProfileOptions.
    * @param HttpSession session,HttpServletRequest request
    * @return "success" or "error" String
    */
    
    public String commonAction(HttpSession session, 
                                  HttpServletRequest request) {

           try {
               aascDAOFactory = 
                               AascDAOFactory.getAascDAOFactory(ORACLE);
                               
               Integer accessLevelInt = 
                   (Integer)session.getAttribute("role_id");
               int accessLevel = accessLevelInt.intValue();
               String locationIdStr = request.getParameter("locationId");
               request.setAttribute("locationId", locationIdStr);

               String queryTimeOut = (String)session.getAttribute("queryTimeOut");
               if (accessLevel == 4) {
                   locationIdInt = Integer.parseInt(locationIdStr);
//                       (Integer)session.getAttribute("location_id"); // Gettin Inventory Org Id from session


                   clientId = (Integer)session.getAttribute("client_id");


                   sessionList = new LinkedList();
                   sessionList.add(locationIdInt);  
                   sessionList.add(clientId);
                   
                   

               } else {

                   try {
                       locationIdStr = request.getParameter("locationId");
                       if (locationIdStr.equalsIgnoreCase("") || 
                           locationIdStr == null) {
                           locationIdStr = "";
                       }
                   } catch (Exception e) {
                       locationIdStr = "";
                   }

                   if (locationIdStr.equalsIgnoreCase("") || locationIdStr == null) {
                       locationIdInt = 
                               (Integer)session.getAttribute("location_id");
                   } else {
                       locationIdInt = new Integer(locationIdStr);
                   }

                  
                   String clientIdStr;
                   try {
                       clientIdStr = request.getParameter("clientId");
                       if (clientIdStr.equalsIgnoreCase("") || clientIdStr == null) {
                           clientIdStr = "";
                       }
                   } catch (Exception e) {
                       clientIdStr = "";
                   }
                   logger.info("clientIdStr : "+clientIdStr);
                   if (clientIdStr.equalsIgnoreCase("") || clientIdStr == null) {
                   clientId = (Integer)session.getAttribute("client_id");
                   } else {
                       clientId = Integer.parseInt(clientIdStr);
                   }

                   sessionList = new LinkedList();
                   sessionList.add(locationIdInt);
                   sessionList.add(clientId);
               }

                aascDaoFactory = 
                   AascDAOFactory.getAascDAOFactory(1); // this method returns the object of the AascOracleDAOFactory class which is storing in the variable of AascDAOFactory interface

                aascProfileOptionsDao = 
                   aascDaoFactory.getAascProfileOptionsDAO(); // this method returns the object of the AascOracleProfileOptionsDAO class whihc is storing in the AascProfileOptionsDAO interface variable


               logger.info("locationIdInt : "+locationIdInt);
               logger.info("accessLevel : "+accessLevel);
               logger.info("clientId : "+clientId);

               return "success";

           } catch (Exception e) {
               logger.severe("Exception::"+e.getMessage());
               return "error";
           }

       }
    
    
    
    /**
    * submitGo() method of AascProfileOptionsDelegate class has one paramter session 
    * object and returns a String object with success or failure message.
    * This method is used to call AascProfileOptionsDAO to retireve profile options from db and set the profileoptions bean.
    * @param HttpSession session
    * @return "success" or "error" String
    */
    public String submitGo(HttpSession session){
        logger.info("Entered submitGO");
        
        try{
            aascDAOFactory = AascDAOFactory.getAascDAOFactory(ORACLE);
        
                        AascProfileOptionsDAO aascProfileOptionsDAO = 
                            aascDAOFactory.getAascProfileOptionsDAO();
            AascProfileOptionsBean aascProfileOptionsBean =
                            aascProfileOptionsDAO.getProfileOptionsBean(sessionList);
            session.setAttribute("aascProfileOptionsBean",aascProfileOptionsBean);

            
            return "success";
        }
        catch(Exception e){
            logger.info("Exception:"+e.getMessage());
            session.setAttribute("aascProfileOptionsBean","");
            return "error";
        }
    }
    
    /**
    * locationAction() method of AascProfileOptionsDelegate class has two paramters session and request 
    * objects and returns a String object with success or failure message.
    * This method is used to call DAO class which retireve list of all locations based on Client id.
    * @param HttpSession session, HttpServletRequest request
    * @return "success" or "error" String
    */
    public String locationAction(int selectedClientId,HttpSession session, 
                                   HttpServletRequest request){
              try {
                  LinkedList locationList = new LinkedList();
                  
                  clientId = selectedClientId;
                  logger.info("clientId: "+clientId);
                  AascSetupLocationDAO aascSetupLocationDAO = new AascOracleSetupLocationDAO();
                  HashMap locationHashMap = aascSetupLocationDAO.getAllLocationDetails(clientId);     
                  locationList = (LinkedList)locationHashMap.get("locationList");
                  HashMap locationDetailsMap = new HashMap();
                  Iterator locationListIterator = locationList.iterator();
                  AascSetupLocationBean aascSetupLocationBean = null ;
                  for (int i = 0; i < locationList.size(); i++) {
                      aascSetupLocationBean = (AascSetupLocationBean)locationListIterator.next();
                      /* Storing the location Id and the location name in a hash map and storing the map in a session
                                 for using it as LOV in all the settings pages */
                      if("Y".equalsIgnoreCase(aascSetupLocationBean.getLocationStatus()))                      
                          locationDetailsMap.put(aascSetupLocationBean.getLocationId(), aascSetupLocationBean.getLocationName());

                  }
                  session.setAttribute("locationsList", locationList);
                  session.setAttribute("locationDetailsMap", locationDetailsMap);
              } catch (Exception e) {
                  logger.info(e.getMessage());
                  return "error";
              }
              return "success";
        }
        
      
    /**
    * saveOrUpdate() method of AascProfileOptionsDelegate class has three paramters session,request and aascProfileOptionsBean 
    * objects and returns a String object with success or failure message.
    * This method is used to call DAO class save the profile options to db.
    * @param HttpSession session, HttpServletRequest request,AascProfileOptionsBean aascProfileOptionsBean
    * @return "success" or "error" String
    */
    
    public String saveOrUpdate(HttpSession session, HttpServletRequest request,AascProfileOptionsBean aascProfileOptionsBean ){
    
    
        logger.info("Inside saveOrUpdate");
        logger.info("pay method:"+aascProfileOptionsBean.getDefaultPayMethod());
        logger.info("enableSatflag:"+aascProfileOptionsBean.getEnableSaturdayFlag());
        logger.info("addvalidation : "+aascProfileOptionsBean.getAddresValidation());
        logger.info("freight shop : "+aascProfileOptionsBean.getFreightShopping());
        logger.info("ShipperName :252::"+aascProfileOptionsBean.getShipperName());
//        String addressValidationStr = request.getParameter("addresValidation");
//        logger.info("addvalidation1234 : "+addressValidationStr);
//        aascProfileOptionsBean.setAddresValidation(addressValidationStr);
        
        if("true".equalsIgnoreCase(aascProfileOptionsBean.getEnableSaturdayFlag()))
            aascProfileOptionsBean.setEnableSaturdayFlag("Y");
        else
            aascProfileOptionsBean.setEnableSaturdayFlag("N");
            
        if("true".equalsIgnoreCase(aascProfileOptionsBean.getEditShipToAddress()))
            aascProfileOptionsBean.setEditShipToAddress("Y");
        else
            aascProfileOptionsBean.setEditShipToAddress("N");
       
            
        logger.info(" ref 1 ::"+aascProfileOptionsBean.getReference1());  
        logger.info(" ref 2 ::"+aascProfileOptionsBean.getReference2());
           
        if("true".equalsIgnoreCase(aascProfileOptionsBean.getReference1()))
            aascProfileOptionsBean.setReference1("Y");
        else
            aascProfileOptionsBean.setReference1("N");
            
        if("true".equalsIgnoreCase(aascProfileOptionsBean.getReference2()))
            aascProfileOptionsBean.setReference2("Y");
        else
            aascProfileOptionsBean.setReference2("N");
                        
        logger.info("ShipperName ::"+aascProfileOptionsBean.getShipperName());
        logger.info("CustomerName ::"+aascProfileOptionsBean.getCustomerNameFlag());
        logger.info("Addr :::"+aascProfileOptionsBean.getAddrLinesFlag());
        logger.info("city ::::"+aascProfileOptionsBean.getCityFlag());
        logger.info("state ::::::"+aascProfileOptionsBean.getStateFlag());
        logger.info("postal ::::::::"+aascProfileOptionsBean.getPostalCodeFlag());
        logger.info("country :::::::::"+aascProfileOptionsBean.getCountryCodeFlag());
        logger.info(" ref 1 ::"+aascProfileOptionsBean.getReference1());
        logger.info(" ref 2::"+aascProfileOptionsBean.getReference2());
        logger.info(" company name::"+aascProfileOptionsBean.getCompanyName());
        logger.info("freight shopping:"+aascProfileOptionsBean.getFreightShopping());
        logger.info("addvalidation:"+aascProfileOptionsBean.getAddresValidation());
        int opStatus=-1;
        try{
        opStatus = aascProfileOptionsDAO.saveOrUpdateProfileOptions(aascProfileOptionsBean);
        logger.info("opStatus in Deligate :::::::"+opStatus);
           
        if(opStatus==1){
            key="aasc180";
        }
        else{
            key="aasc181";
        }
         
        request.setAttribute("key",key);
        }catch(Exception e){
            opStatus=10;
            logger.info(e.getMessage());
            
        }
        
        if(opStatus == 1){
            session.setAttribute("aascProfileOptionsBean",aascProfileOptionsBean);
            return "success";
        }
        else
            return "error";
    }
}
