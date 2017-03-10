package com.aasc.erp.action;

/*

 * @ AascProfileOptionsAction.java        06/08/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.
 
 * All rights reserved.

 */
 
 /**

  * AascProfileOptionsAction class extends Action class.

  * @author Suman Gunda

  * @creation 06/08/2014

  * @History
  * 24-12-2014      Jagadish Jain   Added code required for proper saving and retrieval of profile options. Also added code to control the profile options for different roles. 
  * 10-03-2015      Y Pradeep       Removed getRreferenceValue() method call for removing reference1Value and reference2Value fields.
  * 17/06/2015  Suman G             Added code to fix #2986

  */

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.delegate.AascProfileOptionsDelegate;

import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import com.opensymphony.xwork2.ModelDriven;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class AascProfileOptionsAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ModelDriven{ 

    private static Logger logger = 
        AascLogger.getLogger("AascProfileOptionsAction");
    String aascKey = null; // holds the value of the key 
    
        String aascKeyMsg = null; // holds the value of the key
    protected HttpServletRequest request;
    protected HttpServletResponse response;    
    
    public HttpServletRequest getServletRequest() {
            return request;
    }
     
    public void setServletRequest(HttpServletRequest req){
            this.request = req;
    }
     
    public HttpServletResponse getServletResponse() {
        return response;
    }
    
    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }
    
    AascProfileOptionsBean aascProfileOptionsBean = new AascProfileOptionsBean();
    
    /*
     * Suman Gunda added execute() method for performing action related to Profile Options page
     */
    public String execute()  {
        try{
        logger.info("Entered AascProfileOptionsAction");
        
        String actionType = request.getParameter("actionType");    // gets actionType from request
        
         String roleIdStr = request.getParameter("roleId");    // Added by Jagadish

        logger.info("action type is :::::"+actionType);
        logger.info("IN execute:::::"+aascProfileOptionsBean.getLocationId());
        logger.info("in execute"+request.getParameter("locationId"));
        HttpSession session=request.getSession();
        logger.info("in Session1 "+session.isNew());
        AascProfileOptionsDelegate aascProfileOptionsDelegate=new AascProfileOptionsDelegate();
        logger.info("in Session2 "+session.isNew());
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "error";
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if((roleIdSession != 1 && roleIdSession != 2 && roleIdSession != 3 && roleIdSession != 4)){
            logger.info("in Session "+session.isNew());
            return "error";
        }
        
        String status =  aascProfileOptionsDelegate.commonAction(session, request);
        
        if("SUCCESS".equalsIgnoreCase(status)){
        
        int roleId = Integer.parseInt(roleIdStr);
                        logger.info("roleId in ProfileOptions : "+roleId);
                        
        /*below code added by Jagadish*/
         int selectedClientId = 0;
                         if(roleId == 1 || roleId == 2)
                         {
                             String str = request.getParameter("clientId"); 
                             logger.info("str : "+str);
                             try{
                                 selectedClientId = Integer.parseInt(str);
                             }catch(Exception e)
                             {
                                logger.info(e.getMessage());
                                 selectedClientId = 0;
                             }                   
                         }
                         else
                         {
                             selectedClientId = (Integer)session.getAttribute("client_id");
                         }
                         logger.info("selectedClientId : "+selectedClientId);
                         aascProfileOptionsBean.setClientId(selectedClientId);
        /*Jagadish code ends*/
        
        
         if ("clientDetail".equals(actionType)) 
                         {
                             logger.info("actiontype is clientDetail");
                             String result = 
                                 aascProfileOptionsDelegate.locationAction(selectedClientId,session , request);
                             if ("success".equalsIgnoreCase(result)) {
                                 return "success";
                             } else if ("error".equalsIgnoreCase(result)) {
                                 return "error";
                             }
                         } 
        if("Go".equalsIgnoreCase(actionType)){
            logger.info("Inside go");
            String result =  aascProfileOptionsDelegate.submitGo(session);
            if("success".equalsIgnoreCase(result))
            {
                return "success";
        }
            else{
                   throw new Exception();
                }  
        }
        else if("Save/Update".equalsIgnoreCase(actionType)){
            logger.info("Inside save/update");
            String result = aascProfileOptionsDelegate.saveOrUpdate(session,request,aascProfileOptionsBean);
            if("success".equalsIgnoreCase(result))
                return "success";
        }
     }// close of if("Success")
    }
    catch(Exception e){
        logger.severe("Exception::"+e.getMessage());
                    aascKey = "aasc184";

                    request.setAttribute("key", aascKey);

                    aascKeyMsg = "aasc185";

                    request.setAttribute("key1", aascKeyMsg);
    }
       
        logger.info("Exit from execute method");
                return "success";
    }
    
    public Object getModel() {
        return aascProfileOptionsBean;
    }
}
