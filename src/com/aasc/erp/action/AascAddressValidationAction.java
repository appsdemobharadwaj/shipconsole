package com.aasc.erp.action;
/*
 * @(#)AascAddressValidationAction.java     24/02/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.delegate.AascAddressValidationDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascAddressValidationAction class is action class for Address Validation.
 * @version   1
 * @author    Y Pradeep
 * History
 * 
 * 24-Feb-2015  Y Pradeep       Added this file for Address Validation.
 * 26-Feb-2015  Y Pradeep       Added code to get parsed reponse details and passing them to jsp page.
 * 26-Feb-2015  Y Pradeep       Added code to save order details after generating order number.
 * 27-Feb-2015  Y Pradeep       Replace printstacktrace with getmessage in error handling.
 * 27-Feb-2015  Y Pradeep       Modified code and  reated delegate class.
 * 16-Mar-2015  Y Pradeep       Modified code accordingly into uppercase in if conditions.
 * 17-Mar-2015  Suman G         Modified session error return values to fix #2743.
 * 17-Jun-2015  Suman G             Added code to fix #2986
 */

public class AascAddressValidationAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private static Logger logger = AascLogger.getLogger("AascAddressValidationAction");
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actionType = "";
    HashMap addressHashMap = new HashMap();
    List addressList = null;
    String addressType = "";
    String addressClassification = "";
    String descriptionStatus = "" ;
    
    public AascAddressValidationAction() {
    }

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
    
    /**
     * This is the main action called from the Struts framework.
     */
    public String execute()  {
        
        try{
            logger.info("entered into AascAddressValidationAction class");
            
            HttpSession session = request.getSession(); // getting the Session object
             if(session.isNew() || 
                     !(session.getId().equals(session.getAttribute("SessionId")))){
                 logger.info("in Session "+session.isNew());
                 return "errorSession";
             }
             int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            if((roleIdSession != 4 && roleIdSession != 2)) {
                return "errorSession";
            }
            AascAddressValidationDelegate aascAddressValidationDelegate = new AascAddressValidationDelegate();
            aascAddressValidationDelegate.commonAction(request, session);
            actionType = request.getParameter("actionType");    // gets actionType from request
            if("validateAddress".equalsIgnoreCase(actionType)){
                
                addressHashMap = aascAddressValidationDelegate.validateAddressAction(request, session);                
                try{
                    addressClassification = addressHashMap.get("addressClassificationStr").toString();
                    addressType = addressHashMap.get("addressTypeStr").toString();
                    addressList = (LinkedList)addressHashMap.get("addressList");
                } catch (Exception e){
                    addressClassification = "";
                    addressType = "";
                    addressList = null;
                }
                
                descriptionStatus = addressHashMap.get("descriptionStatusStr").toString();
                
                if("APARTMENT_NUMBER_NOT_FOUND".equalsIgnoreCase(addressType) || "MODIFIED_TO_ACHIEVE_MATCH".equalsIgnoreCase(addressType) || "AMBIGUIOUS ADDRESS".equalsIgnoreCase(addressType)){
                    setAddressList(addressList);
                } else if("APARTMENT_NUMBER_REQUIRED".equalsIgnoreCase(addressType) || "NO_CHANGES".equalsIgnoreCase(addressType) || "VALID ADDRESS".equalsIgnoreCase(addressType)){
                    addressList = null;
                    setAddressList(addressList);
                }
                request.setAttribute("addressClassification", addressClassification);
                request.setAttribute("addressType", addressType);
                request.setAttribute("descriptionStatus", descriptionStatus);

            }
            
        } catch(Exception e){
            logger.info("Got exception in address validation action class = "+e.getMessage());
        }
        return "success";
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public List getAddressList() {
        return addressList;
    }
}
