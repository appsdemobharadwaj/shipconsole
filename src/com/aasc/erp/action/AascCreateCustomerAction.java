/*========================================================================+
 @(#)AascCreateCustomerAction.java 25/07/2014
 * Copyright (c)Apps Associates Pvt. Ltd.
 * All rights reserved.
 +===========================================================================*/
package com.aasc.erp.action;

import com.aasc.erp.bean.AascCustomerBean;
import com.aasc.erp.delegate.AascCreateCustomerDelegate;
import com.aasc.erp.util.AascLogger;


import com.opensymphony.xwork2.ModelDriven;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascCreateCustomerAction extends ActionSupport. <br>
 * This class having the functionalities related to customer creation and updation and also for user creation and updation
 * @author      Suman Gunda <br>
 * @version 1 <br>
 * @since
 * 
 * 17/06/2015  Suman G             Added code to fix #2986
 * 21/10/2015  Suman G             Modified code to remove unnecessary fields in the table
 * 24/02/2016  Suman G             Changed bean for new Transaction Management design.
 * 02/03/2016  Suman G             Added code for Make Payment functionality.
 * */
public class

AascCreateCustomerAction implements ServletRequestAware, ServletResponseAware, 
                                    ModelDriven {

    static Logger logger = 
        AascLogger.getLogger(" AascCreateCustomerAction "); // logger object
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

    String actiontype = ""; //  actiontype is used to store actiontype which need to be performed
    AascCustomerBean aascCustomerInfo = new AascCustomerBean();
    String status = "N";

    public String execute() {

        logger.info("Entered  AascCreateCustomerAction");
        actiontype = 
                request.getParameter("actiontype"); // gets actionType from request
//        logger.info("actiontype:: " + actiontype);
        HttpSession session = null;

        try {
            session = request.getSession();
        } catch (Exception e) {
            e.printStackTrace();

        }
        //            logger.info("session ::::::::::::"+session);
        AascCreateCustomerDelegate aascCreateCustomerDelegate = 
            new AascCreateCustomerDelegate();
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if((roleIdSession != 1 && roleIdSession != 2)){
            if((roleIdSession == 3 && !"UpdateTransaction".equalsIgnoreCase(actiontype)) || roleIdSession == 4 || roleIdSession == 5)
            return "sessionError";
        }
        if (actiontype.equalsIgnoreCase("CreateCustomer")) {
            logger.info("actiontype is CREATE");
            aascCreateCustomerDelegate.setCountryDetails(session);
            return "Create";
        } else if (actiontype.equalsIgnoreCase("Cancel")) {
            logger.info("actiontype is CANCEL");

            return "Cancel";
        } else if (actiontype.equalsIgnoreCase("AddNewCustomer")) {
            logger.info("actiontype is AddNewCustomer");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //added below code for handling lincense date.
//            Date licenseEndDateTemp;
            Date licenseStartDateTemp;
            try {

                if (request.getParameter("licenseStartDateText") != null) {
                    licenseStartDateTemp = 
                            dateFormat.parse(request.getParameter("licenseStartDateText"));
                    //  logger.info("licenseStartDateTemp......"+licenseStartDateTemp);
                    java.sql.Date licenseStartDate = 
                        new java.sql.Date(licenseStartDateTemp.getTime());
                    // logger.info("licenseStartDate......"+licenseStartDate);
                    aascCustomerInfo.setCustomerStartDate(licenseStartDate.toString());
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
                //e.printStackTrace();
            }

            String msg = 
                aascCreateCustomerDelegate.createNewCustomer(request, session, 
                                                             aascCustomerInfo);
            // logger.info("---------------status after saving............"+status);
            return msg;
        } else if (actiontype.equalsIgnoreCase("EditCustomer")) {
            try {
                logger.info("actiontype is EditCustomer");


                aascCreateCustomerDelegate.setCountryDetails(session);

//                logger.info("setCountryDetails ::: exit");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Edit";
        } else if (actiontype.equalsIgnoreCase("ViewCustomer")) {
            logger.info("actiontype is View");
            aascCreateCustomerDelegate.setCountryDetails(session);
            return "View";
        } else if (actiontype.equalsIgnoreCase("updateCustomer")) {
            logger.info("actiontype is update Customer");


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //added below code for handling lincense date.
//            Date licenseEndDateTemp;
            Date licenseStartDateTemp;
            try {
                 if (request.getParameter("licenseStartDateText") != null) {
                     licenseStartDateTemp = 
                             dateFormat.parse(request.getParameter("licenseStartDateText"));
                     //  logger.info("licenseStartDateTemp......"+licenseStartDateTemp);
                     java.sql.Date licenseStartDate = 
                         new java.sql.Date(licenseStartDateTemp.getTime());
                     // logger.info("licenseStartDate......"+licenseStartDate);
                     aascCustomerInfo.setCustomerStartDate(licenseStartDate.toString());
                 }
            } catch (Exception e) {
                 logger.severe(e.getMessage());
                 //e.printStackTrace();
            }

            aascCustomerInfo.setClientID(Integer.parseInt(request.getParameter("clientID")));

            String msg = 
                aascCreateCustomerDelegate.updateCustomer(request, session, 
                                                          aascCustomerInfo);

            return msg;

        }
        else if("UpdateTransaction".equalsIgnoreCase(actiontype)){
            logger.info("action type is Update Transaction");
            aascCreateCustomerDelegate.updateTransactionDetails(request,session);
            return "success";
        }
        logger.info("Exit  AascCreateCustomerAction");
        return "success";
    }
    
    

    public Object getModel() {
        return aascCustomerInfo;
    }
}
