package com.aasc.erp.action;

/*

  * @(#)AascShipmentAuditAction.java  10/1/2006

  * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.


History

22-Dec-2014   Eshwari M    Added this file for role 2 Shipping in SC Lite.
17/06/2015  Suman G             Added code to fix #2986

*/

import com.aasc.erp.delegate.AascIndexRequestDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


public class AascShipmentAuditAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

    LinkedList sessionList = null;
    private int userID;
    
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

    public AascShipmentAuditAction() {
    }
    static Logger logger = AascLogger.getLogger("AascShipmentAuditAction");

    
    public String execute()
    {
        logger.info("Entered execute()");
        
        try {
                HttpSession session = request.getSession(); // creating the object of the HttpSession class

            if (session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))) {

                return "error"; // 06-June-2007 Bhanu added this code for session validation.
            }
             int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
             if(roleIdSession != 3 && roleIdSession != 2){
                 return "error";
             }
            userID = ((Integer)session.getAttribute("user_id")).intValue();
            
            //03/09/07(start) 
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            
            int clientId = Integer.parseInt(request.getParameter("clientId"));
             
            session.setAttribute("client_id" , clientId) ;
            session.setAttribute("location_id", locationId);
            
            AascIndexRequestDelegate aascIndexRequestDelegate = new AascIndexRequestDelegate();
            aascIndexRequestDelegate.commonAction(session , request);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("End of execute method");
        return "success";
    } // closing the execute method

}// closing the class 
