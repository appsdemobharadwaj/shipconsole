package com.aasc.erp.action;
/*========================================================================+
@(#)AascUserControlAction.java 23/07/2014
* Copyright (c)Apps Associates Pvt. Ltd. 
* All rights reserved.   
* 
* 04/11/2015    Suman G     Added code to send transaction related mail to cients.
+===========================================================================*/

import com.aasc.erp.delegate.AascUserControlDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascUserControlAction extends ActionSupport. <br>
 * @author      N Srisha <br>
 * @version 1 <br>
 * @since
 * 
 * */


public class AascUserControlAction extends ActionSupport implements  ServletRequestAware, ServletResponseAware{

    static Logger logger = AascLogger.getLogger(" AascUserControlAction "); // logger object
         protected HttpServletRequest request;
         protected HttpServletResponse response;
         
         String actiontype="";   // stores actiontype 
         
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

    public String execute(){ 
    
        logger.info("Entered  AascUserControlAction"); 
        String userName=request.getParameter("userName");           // gets userName from request
        String password=request.getParameter("password");           // gets password from request
        actiontype = request.getParameter("actiontype");    // gets actionType from request
  
    try{
       HttpSession session  = request.getSession();                 // gets session object      
        if (actiontype.equalsIgnoreCase("login")) {
            AascUserControlDelegate aascUserControlDelegate = 
            new AascUserControlDelegate();                          // creates aascUserControlDelegate object of class AascUserControlDelegate
            String message = aascUserControlDelegate.userAuthentication(session,request,userName, password);
            if("success".equalsIgnoreCase(message))
                request.getSession().setAttribute("SessionId",session.getId());
            return message;
        }
        else if (actiontype.equalsIgnoreCase("Logout")) {
            logger.info("In Logout contoller");
            request.getSession().removeAttribute("role_id");
            request.getSession().removeAttribute("user_id");
            request.getSession().removeAttribute("location_id");
            request.getSession().removeAttribute("client_id");
            request.getSession().removeAttribute("LoginuserName");
            request.getSession().removeAttribute("SessionId");
            request.getSession().removeAttribute("clientMailId");
            request.getSession().invalidate();
            return "login";
        }
        else{
            request.setAttribute("userName",userName);
            request.setAttribute("key","aasc405");
            return "error";
        }
    }
    catch(Exception e){
    return "error";
    }
    
    }
    

}
