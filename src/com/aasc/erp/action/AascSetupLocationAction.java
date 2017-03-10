package com.aasc.erp.action;
/*========================================================================+
@(#)AascSetupLocation.java 24/07/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.delegate.AascSetupLocationDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


 /**
  * AascSetupLocationAction extends ActionSupport. <br>
  * @author      N Srisha <br>
  * @version 1 <br>
  * @since
  * 17-Dec-2014  Eshwari M    Merged sunanda doce for SC lite
  * 07-Jan-2015  Y Pradeep    Merged Sunanda's code : Modified code as getLocationDetails is modified to return Hashmap instead od linkedlist for role 2
  * 20-Jan-2015  Sunanda K    Removed unused object aascSetupLocationDAO and aligned code properly.
  * 20-Jan-2015  Suman G      Removed unnecessary loggers.
  * 20-Jan-2015  Suman G      Changed AddOrUpdateNewLocation() to addOrUpdateNewLocation().
  * 10-Mar-2015  Sunanda k    Removed extends ActionSupport for proper working of action class
  * 17/06/2015  Suman G             Added code to fix #2986
  * */

  
public class AascSetupLocationAction  implements  ServletRequestAware, ServletResponseAware,ModelDriven{
 
    static Logger logger = AascLogger.getLogger(" AascSetupLocation "); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actiontype="";   // stores actiontype 
    int locationId=0;       // Stores LocationId
     int clientId = 0;
    String clientIdStr = "";
    AascSetupLocationDelegate aascSetupLocationDelegate =
                    new AascSetupLocationDelegate();                      // creates object for AascSetupLocationDelegate class
    AascSetupLocationBean aascSetupLocationBean = new AascSetupLocationBean();               // creates object for AascSetupLocationBean
    public HttpServletRequest getServletRequest() {
        return request;
    }
    public void setServletRequest(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }
     /**
     * This is the main action called from the Struts framework.
     * @return ActionForward 
     **/
    public String execute() {
        actiontype = request.getParameter("actiontype");
        logger.info("actiontype.............."+actiontype);
        HttpSession session=request.getSession();
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 3 && roleIdSession != 2){
            return "sessionError";
        }
        
        aascSetupLocationBean = aascSetupLocationDelegate.commonAction(session,aascSetupLocationBean);

        Integer roleId = (Integer)session.getAttribute("role_id");
        logger.info("accessLevelS" + roleId);
        if (roleId == 2) {
            clientIdStr = 
                    request.getParameter("clientIdSelect");
            if (!"".equalsIgnoreCase(clientIdStr)) {
                
                clientId = Integer.parseInt(request.getParameter("clientIdSelect"));
                session.setAttribute("client_id",clientId);
            } else {
                //clientId= Integer.parseInt(request.getParameter("clientIdSelect"));
                clientId = 0;
            }
            
            request.setAttribute("client_id", clientId);
        }
        if (actiontype.equalsIgnoreCase("Create")) {
            logger.info("actiontype is CREATE");
            aascSetupLocationDelegate.setCountryDetails(session);
            session.setAttribute("aascSetupLocationBean","");
            return "Create";
        }
        if (actiontype.equalsIgnoreCase("GO")) {
            logger.info("Inside GO ..." );
            Integer clientId1=(Integer)session.getAttribute("client_id");
            try {
                aascSetupLocationBean.setClientId(clientId1);
                HashMap locationsHashMap = aascSetupLocationDelegate.getLocationDetails(aascSetupLocationBean) ;
                                
                int status = ((Integer)locationsHashMap.get("opStatus")).intValue();
                if(status == 606){
                   LinkedList locationList = (LinkedList)locationsHashMap.get("locationList") ;
                   session.setAttribute("locationsList",(Object)locationList);
                   request.setAttribute("key","aasc606");
                } else {
                    request.setAttribute("key", "aasc607");
                }

            } catch (Exception e) {

                logger.severe("Exception::"+e.getMessage());
            }


            return "success";

        } else if (actiontype.equalsIgnoreCase("Cancel")) {
            logger.info("actiontype is CANCEL");
            return "Cancel";
        } else if (actiontype.equalsIgnoreCase("Edit")) {
            logger.info("actiontype is Edit");
            aascSetupLocationDelegate.setCountryDetails(session);
            
            
            return "Edit";
        } else if (actiontype.equalsIgnoreCase("AddNewLocation")) {
            logger.info("actiontype is AddNewLocation");
            aascSetupLocationBean.setLocationStatus("Y");
            aascSetupLocationBean = aascSetupLocationDelegate.addOrUpdateNewLocation(aascSetupLocationBean);
            int status = aascSetupLocationBean.getOpStatus();
            if(status == 600){
                request.setAttribute("key","aasc602");
                
                HashMap locationsHashMap = aascSetupLocationDelegate.getLocationDetails(aascSetupLocationBean) ;//aascSetupLocationDAO.getAllLocationDetails(clientId1);
                LinkedList locationList = null ;
                int opStatus = ((Integer)locationsHashMap.get("opStatus")).intValue();
                if(opStatus == 606){
                    locationList = (LinkedList)locationsHashMap.get("locationList") ;
                }                
                //LinkedList locationList = aascSetupLocationDelegate.getLocationDetails(aascSetupLocationBean);
                session.setAttribute("locationsList",(Object)locationList);
            }else if (status == 601) {
                request.setAttribute("key","aasc610");
            }else {
                request.setAttribute("key","aasc601");
            }            
            session.setAttribute("aascSetupLocationBean",aascSetupLocationBean);
            return "Create";
        } else if (actiontype.equalsIgnoreCase("editLocationDetails")) {
            logger.info("actiontype in else if .............."+actiontype);
            
            
            aascSetupLocationDelegate.addOrUpdateNewLocation(aascSetupLocationBean);
            int status = aascSetupLocationBean.getOpStatus();
            
            if(status == 604){
                request.setAttribute("key","aasc604");
                try{
                    HashMap locationsHashMap = aascSetupLocationDelegate.getLocationDetails(aascSetupLocationBean) ;//aascSetupLocationDAO.getAllLocationDetails(clientId1);
                    LinkedList locationList = null ;
                    int opStatus = ((Integer)locationsHashMap.get("opStatus")).intValue();
                    if(opStatus == 606){
                        locationList = (LinkedList)locationsHashMap.get("locationList") ;
                    }                   
                    session.setAttribute("locationsList",(Object)locationList);
                }
                catch(Exception e){
                    logger.severe("Exception::"+e.getMessage());
                }
            } else if (status == 605) {
                request.setAttribute("key","aasc610");
            }else {
                request.setAttribute("key","aasc605");
            }            
            session.setAttribute("aascSetupLocationBean",aascSetupLocationBean);
            return "Edit";
        } else {
          logger.info("In Else Block");
          return "success";
        }
    }

    public Object getModel() {
        return aascSetupLocationBean;
    }
}

