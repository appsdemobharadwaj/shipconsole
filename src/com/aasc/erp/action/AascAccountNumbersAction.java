package com.aasc.erp.action;
/*
 * @(#)AascAccountNumbersAction.java     28/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.delegate.AascAccountNumbersDelegate;
import com.aasc.erp.bean.AascAccountNumbersBean;
import com.aasc.erp.util.AascLogger;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascAccountNumbersAction class is action class for Account Numbers.
 * @version   1
 * @author    Venkateswaramma Malluru
 * History
 * 
 * 30-Jan-2015  Y Pradeep           Ran self audit and added exception handling.
 * 17-Jun-2015  Suman G             Added code to fix #2986 
 * 15-Jul-2015  Suman G             Added code to remove session error for Role1 User by Padmavathi
 */
public class AascAccountNumbersAction extends ActionSupport implements ModelDriven, 
                                                          ServletRequestAware, 
                                                          ServletResponseAware {

    public Object getModel() {
        return aascAccountNumbersInfo;
    }
    String organizationStr = "";
    String actiontype = "";
    int organizationInt = 0;
    int returnStatus = 0;
    int ORACLE = 1;
    int type;
    int locationId = 0;
    int accountNumberId = 0;
    int carrierCode = 0;
    String accountNumber = "";
    String meterNumber = "";
    String accessLicenseNumber = "";
    String accountNumberActive = "";
    String accountNumberDefault = "";
    String accountNumberNegotiatedFlag = "";
    String accountUserName = "";
    String accountPassword = "";
    String negotiatedRates = "";
    int saveAccountNumberAllStatus = 0;
    int accountNumberCount = 0;
    int addNewAccountNumberStatus = 0;
    String statuSMsg = "";
    int editNewAccountNumberStatus = 0;
    String editAccountNumberStatusMsg = "";
    int getAccountNumberStatus = 0;
    String actionValue = "";
    int userId;
    String key = null;
    
    AascAccountNumbersBean aascAccountNumbersInfo = new AascAccountNumbersBean();
    AascAccountNumbersBean aascAccountNumbersInfoSave = new AascAccountNumbersBean();
    AascAccountNumbersBean aascAccountNumbersInfoAddNewAccountNumber;
    AascAccountNumbersBean aascAccountNumbersInfoEditAccountNumber;
    AascAccountNumbersBean aascAccountNumbersInfoEditObj;
    
    int queryTimeOutInt;
    
    private static Logger logger = 
        AascLogger.getLogger("AascAccountNumbersAction");

    protected HttpServletRequest request;
    protected HttpServletResponse response;

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


    public String execute() {
        logger.info("Entered execute()");
        
        try {
            HttpSession session = 
                request.getSession(true); // getting the Session object

            AascAccountNumbersDelegate aascAccountNumbersDelegate = new AascAccountNumbersDelegate();
            if(session.isNew() || 
                    !(session.getId().equals(session.getAttribute("SessionId")))){
                logger.info("in Session "+session.isNew());
                return "sessionError";
            }
            int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            if((roleIdSession != 1 &&roleIdSession != 2 && roleIdSession != 3 && roleIdSession != 4)){//Removed session error for role1 user 
                return "sessionError";
            
            }
            
            String status = aascAccountNumbersDelegate.commonAction(session, request);
            actiontype = request.getParameter("actiontype");
            logger.info("actiontype = "+actiontype);
            logger.info("status = "+status);
            if ("successDelegate".equalsIgnoreCase(status)) {

                if (actiontype.equalsIgnoreCase("success") || actiontype.equalsIgnoreCase("")) {
                    logger.info("actiontype is : " + actiontype);

                    aascAccountNumbersDelegate.successAction(session, request);

                }
                else if (actiontype.equalsIgnoreCase("Update")) {
                    logger.info("actiontype is " + actiontype);

                    aascAccountNumbersDelegate.updateAction(aascAccountNumbersInfoSave, session, request);

                }
                else if (actiontype.equalsIgnoreCase("Create")) {
                    logger.info("actiontype is CREATE");

                    return "Create";
                }
                else if (actiontype.equalsIgnoreCase("Cancel")) {
                    logger.info("actiontype is CANCEL");

                    return "success";
                }
                else if (actiontype.equalsIgnoreCase("AddNewAccountNumber") || actiontype.equalsIgnoreCase("AllowAddNewAccountNumber")) {
                    logger.info("actiontype is " + actiontype);

                    String result = aascAccountNumbersDelegate.addNewAccountNumberAction(aascAccountNumbersInfo, session, request);
                   
                    if ("success".equalsIgnoreCase(result)) {
                        return "success";
                    } else {
                        return "fail";
                    }                                                        

                }
                else if (actiontype.equalsIgnoreCase("Edit")) {
                    logger.info("actiontype is EDIT");
                    logger.info("here it is" + request.getParameter("accountNumberDefault1"));
                                       
                    return "Edit";
                }
                else if (actiontype.equalsIgnoreCase("Register")) {
                    logger.info("actiontype is :" + actiontype);
                    String accountNumber = 
                        request.getParameter("UpsAccountNumberHidden");

                    request.setAttribute("UpsAccountNumberHidden", 
                                         accountNumber);
                    request.setAttribute("UpsAccountdummy", accountNumber);
                    
                    return "Register";
                }
                else if (actiontype.equalsIgnoreCase("EditSave")) {
                    logger.info("actiontype is " + actiontype);
                    String result = aascAccountNumbersDelegate.editSaveAction(aascAccountNumbersInfo, session, request);
                    if ("success".equalsIgnoreCase(result)) {
                        return "Edit";
                    } else {
                        return "fail";
                    }  

                }
                
            }
            else{
                return "sessionError";
            }

        } catch (Exception e) {
        
            logger.severe("Got Exception = "+e.getMessage());
            return "fail";
        }
        
        logger.info("Exiting execute() in AascAccountNumbersAction");
        request.setAttribute("actionValue", actionValue);
       
        return "success";
    }


}

