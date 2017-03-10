/*
 * @(#)ShipMethodAction.java  05/04/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 */
package com.aasc.erp.action;

import com.aasc.erp.bean.AascCarrierConfigurationBean;
import com.aasc.erp.delegate.AascShipMethodDelegate;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.delegate.AascCarrierConfigurationDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 *
 * @version 1.1
 * @author Vandana Gangisetty
 * @since
 *        10/12/2014 Y Pradeep  Added code to set client id in request while close button is clicked in Shipmethod mapping package.
 *        24/12/2014 Y Pradeep  Commited code after Self Audit.
 *        20/01/2015 Y Pradeep  Removed unused comments and unnecessary loggers.
 *        16/03/2015 Y Pradeep  Modified code to avoid null pointer exception for if conditions.
 *        17/06/2015  Suman G             Added code to fix #2986
 *        24/11/2015 Suman G    Checking the value in submit to fix the issue #3378
 */


/**
 * ShipMethodAction class extends Action class.
 * This class takes the values from the jsp and depending upon 
 * the submit button value of the jsp executes appropriate if 
 * block and saves or retrieves data from the  database.
 **/
public class ShipMethodAction extends ActionSupport implements ModelDriven, 
                                                               ServletRequestAware, 
                                                               ServletResponseAware {
    private String key = ""; // key that contains message to display to the user about the status of the transaction(save or retreival)
    private AascShipMethodInfo aascShipMethodInfo = null; // ship method bean object containing ship method information
    private HttpSession session = null; // http session object to keep the required objects in session
    private String carrierName = ""; // contains carrier name of a praticular ship method    

    /**
     ** This method is executed when request is sent to the controller.
     *@param mapping The ActionMapping used to select this instance.
     *@param form The optional ActionForm bean for this request.
     *@param request The HTTP Request we are processing.
     *@param response The HTTP Response we are processing.
     *@return ActionForward object
     *@throws java.io.IOException If Input/Output exception occured
     *@throws javax.servlet.ServletException occurs when no class found
     **/
    private static Logger logger = AascLogger.getLogger("ShipMethodAction");

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

    /**
     * This is the main action called from the Struts framework.
     */
    public String execute() {
        try {
            logger.info("entered into execute() of ShipMethodAction.java");

            session = request.getSession(); // creating the object of the HttpSession class
             if(session.isNew() || 
                     !(session.getId().equals(session.getAttribute("SessionId")))){
                 logger.info("in Session "+session.isNew());
                 return "error";
             }
             int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
             if(roleIdSession != 2 && roleIdSession != 4 && roleIdSession != 1 && roleIdSession != 3){
              return "error";
             }
             // Checking submit button value to fix #3378
             if(request.getParameter("submit") == null)
                return "error";
//            System.out.println("submit value::::: "+request.getParameter("submit"));
            AascShipMethodDelegate shipMethodDelegate = new AascShipMethodDelegate();
            try {
                if ("GoCarrier".equalsIgnoreCase(request.getParameter("submit"))) {
                    logger.info("submit type is GoCarrier");
                    String result = shipMethodDelegate.goCarrierAction(session, request);
                    if ("success".equalsIgnoreCase(result)) {
                        return "carrier";
                    } else if ("error".equalsIgnoreCase(result)) {
                        return "error";
                    }
                }
            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
                return "success"; // forwarding to jsp page  
            }

            try {
                if ("SaveUpdate".equalsIgnoreCase(request.getParameter("submit"))) {
                    logger.info("submit type is SAVEUPDATE");
                    String result = shipMethodDelegate.saveUpdateAction(session, request);
                    if("success".equalsIgnoreCase(result)){
                        AascCarrierConfigurationDelegate aascCarrierConfigurationDelegate = new AascCarrierConfigurationDelegate();
                        AascCarrierConfigurationBean aascCarrierConfigurationBean=(AascCarrierConfigurationBean)session.getAttribute("aascCarrierConfiguration");
                        String result1 = aascCarrierConfigurationDelegate.shipMethodAction(session,aascCarrierConfigurationBean,request);
                    }    
                    if ("success".equalsIgnoreCase(result)) {
                        return "success";
                    } else if ("error".equalsIgnoreCase(result)) {
                        return "error";
                    }
                } // end of if SaveUpdate
            } catch (Exception e) {
                key = "aasc403"; // key containing message for error in  saving the ship method details
                request.setAttribute("key", key); // setting the key to request object   
                session.setAttribute("carrierNameSelected", carrierName); // placing carreir name in session            
                session.setAttribute("shipMethodInfoBean", aascShipMethodInfo); // placing ship method bean in session   
                logger.severe("Exception::"+e.getMessage());
            } // end of catch


       //     }
        } catch (Exception e) {
            key = "aasc401"; // key containing message for error in retreival
            request.setAttribute("key", key); // placing key in request scope
            logger.severe("key in go button catch block:" + key);
            logger.severe("Exception::"+e.getMessage());
            logger.info("forwarding to jsp");
            return "success"; // forwarding to jsp page  
        }
        logger.info("Exit from ShipMethodAction");
        logger.info("forwarding to jsp");
        return "success"; // forwading to ship method mapping jsp 
    } // end of the method

    /** This method can replace the null values with nullString.
     * @return String that is ""
     * @param obj-object of type Object
     */
    public Object getModel() {
        return aascShipMethodInfo;
    }
} // end of the class
