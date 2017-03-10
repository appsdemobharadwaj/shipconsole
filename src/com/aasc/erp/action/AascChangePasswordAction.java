/*========================================================================+
 @(#)AascChangePasswordAction.java 04/02/2015
 * Copyright (c)Apps Associates Pvt. Ltd.
 * All rights reserved.
 +===========================================================================*/

package com.aasc.erp.action;


import com.aasc.erp.delegate.AascChangePasswordDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascChangePasswordAction extends ActionSupport. <br>
 * This class having the functionality Change Password and Forgot Password.
 * @author      Suman Gunda <br>
 * @version 1 <br>
 * @since 04/02/2015
 * 
 * */
public class


AascChangePasswordAction extends ActionSupport implements ServletRequestAware, 
                                                          ServletResponseAware {


    static Logger logger = 
        AascLogger.getLogger(" AascChangePasswordAction "); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actiontype = "";
    //    AascUserInfo aascUserInfo=new AascUserInfo();

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

    AascChangePasswordDelegate aascChangePasswordDelegate = 
        new AascChangePasswordDelegate(); //  Object for delegate

    public String execute() {
        String returnValue = "error";
        try {
            HttpSession session=request.getSession();
            if(session.isNew() || 
                    !(session.getId().equals(session.getAttribute("SessionId")))){
                logger.info("in Session "+session.isNew());
                return "sessionError";
            }
            actiontype = request.getParameter("actiontype");

            logger.info("actiontype.............." + actiontype);

            if (actiontype.equalsIgnoreCase("ChangePassword")) {
                logger.info("actiontype is Save");

                int status = 
                    aascChangePasswordDelegate.changePassword(request);

                logger.info("Status ::::::::::::::::::::::" + status);
                if (status == 1 || status == 10) {
                    request.setAttribute("status", status);
                } else {
                    status = 0;
                    request.setAttribute("status", status);

                }
                returnValue = "Success";
            } else if (actiontype.equalsIgnoreCase("forgotPassword")) {
                logger.info("actiontype is forgotPassword");

                int status = 
                    aascChangePasswordDelegate.forgotPassword(request);
                if (status == 1 || status == 10 || status == 20) {
                    request.setAttribute("status", status);
                } else {
                    status = 0;
                    request.setAttribute("status", status);

                }
                returnValue = "success_forgotpwd";
            }


        } catch (Exception e) {
            logger.severe("Exception:" + e.getMessage());
        }
        logger.info("Exit AascChangePasswordAction");
        return returnValue;

    }

}
