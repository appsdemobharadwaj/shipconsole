package com.aasc.erp.action;
/*
 * @(#)AascPrinterSetupAction.java     23/06/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */
import com.aasc.erp.bean.AascPrinterInfo;
import com.aasc.erp.delegate.AascPrinterSetupDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascPrinterSetupAction class is action class for Printer Setup.
 * @version   1
 * @author    Y Pradeep
 * History
 * 
 * 30-Jan-2015  Y Pradeep           Added this file for Printer Setup Popup page.
 */
public class AascPrinterSetupAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    
    private HttpSession session = null; // http session object to keep the required objects in session
    private String key = ""; // key that contains message to display to the user about the status of the transaction(save or retreival)
                                                           
    private static Logger logger = AascLogger.getLogger("AascPrinterSetupAction");

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    LinkedList labelFormatList = null;
    AascPrinterInfo aascPrinterInfo = null;
    
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
        logger.info("entered into execute() of AascPrinterSetupAction.java class");
        try{
            session = request.getSession(); // creating the object of the HttpSession class
             if(session.isNew() || !(session.getId().equals(session.getAttribute("SessionId")))) {
                 return "errorSession";
             }
            int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            if(roleIdSession != 2 && roleIdSession != 4 && roleIdSession != 1 && roleIdSession != 3){
                return "error";
            }
            AascPrinterSetupDelegate printerDelegate = new AascPrinterSetupDelegate();
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            
            String roleIdStr = request.getParameter("roleId");
            int roleId = Integer.parseInt(roleIdStr);
            
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
                        
            if("printerDetails".equalsIgnoreCase(request.getParameter("submit"))) {
                try{
                    logger.info("submit type is printerDetails");
                    aascPrinterInfo = new AascPrinterInfo();
                    labelFormatList = printerDelegate.getPrinterDetails(selectedClientId, locationId);
                    aascPrinterInfo.setLabelFormatList(labelFormatList);
                    setAascPrinterInfo(aascPrinterInfo);
                
                } catch(Exception e) {
                    logger.info("got exception in printerDetails action class :"+e.getMessage());
                    //e.printStackTrace();
                }
                
            } else if ("SaveUpdate".equalsIgnoreCase(request.getParameter("submit"))) {
                logger.info("submit type is SAVEUPDATE");
                try{
                    String result = printerDelegate.saveUpdateAction(session, request);
                    if ("success".equalsIgnoreCase(result)) {
                        aascPrinterInfo = new AascPrinterInfo();
                        labelFormatList = printerDelegate.getPrinterDetails(selectedClientId, locationId);
                        aascPrinterInfo.setLabelFormatList(labelFormatList);
                        setAascPrinterInfo(aascPrinterInfo);
                        return "success";
                    } else if ("error".equalsIgnoreCase(result)) {
                        return "error";
                    }
                } catch(Exception e) {
                    logger.info("got exception in SaveUpdate action class :"+e.getMessage());
                }
            } // end of if SaveUpdate
            return "success";
        } catch(Exception e){
            logger.info("got exception in execute method of action class :"+e.getMessage());
            //e.printStackTrace();
            return "error";
        }
        
    }

    public void setAascPrinterInfo(AascPrinterInfo aascPrinterInfo) {
        this.aascPrinterInfo = aascPrinterInfo;
    }

    public AascPrinterInfo getAascPrinterInfo() {
        return aascPrinterInfo;
    }
}
