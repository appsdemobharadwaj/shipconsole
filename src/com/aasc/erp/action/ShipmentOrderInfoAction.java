/* @(#)ShipmentOrderInfoAction.java       17/11/2014
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */


package com.aasc.erp.action;


/* ========================================================================================
 Date           Resource         Change history
 ------------------------------------------------------------------------------------------
 * 02/05/2006  Eshwari         Created this class from Cloud 1.2 version   
 * 18/12/2014  Y Pradeep       Modified orderNum from int to String
 * 27/12/2014  Eshwari M       Modified code for role 2 shipping
 * 31/12/2014  Suman G         Audited file
 * 12/02/2015  Eshwari M       Removed the DAO calls and moved to delegate class
 * 12/03/2015  Y Pradeep       Renamed orderNumber parameter to orderNumberSearch to get order number from shipment jsp.
 * 16/03/2015  Eshwari M       Modified code to fix bug # 2662
 * 02/04/2015  Y Pradeep       Added intlFlag variable for setting intlSaveFlag to Y.
 * 27/05/2015  Y Pradeep       Modified code to display and allow Order Numbers with special characters(Encode and Decode).
 * 17/06/2015  Suman G             Added code to fix #2986
 * 13/07/2015  Y Pradeep       Removed unused code related to printer.
============================================================================================*/

import com.aasc.erp.bean.AascShipmentOrderInfo;
//import com.aasc.erp.model.AascShipmentOrderInfoDAO;
//import com.aasc.erp.model.AascDAOFactory;
//import com.aasc.erp.model.AascPrinterDAO;
//import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.delegate.AascShipmentOrderDelegate;
//import com.aasc.erp.model.AascTemplateInfoDAO;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import java.net.URLDecoder;

import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * ShipmentOrderInfoAction class extends Action class.
 * This class takes the order number  from the jsp and calls the model class methods which
    retirves the data from the database of that order number through the procedure and sets 
    bean set method.
 *  
 *  @author Eshwari M
 *  
 *  @version 1.0
 */
public class ShipmentOrderInfoAction extends ActionSupport implements ServletRequestAware, 
                                                                      ServletResponseAware {

    private int returnStatus; // holds the value of the returnStatus of the methods

    private int clientID;

    private String orderNumber; // holds the value of the orderNumber got from the jsp

    private static Logger logger = 
        AascLogger.getLogger("ShipmentOrderInfoAction"); // this method returns the object of the logger class

    private String errorKey;

    HttpSession session; // holds the value of the session       

    /*AascDAOFactory aascDAOFactory; // holds the object of the AascDAOFactory class

    AascShipmentOrderInfoDAO aascShipmentOrderInfoDAO; // holds the object of the AascShipmentOrderInfoDAO class

    AascPrinterDAO aascPrinterDAO; // holds the object of the AascPrinterDAO class
    
    AascShipMethodDAO aascshipMethosDAO; 
    
    AascTemplateInfoDAO aascTemplateInfoDAO; // holds the object of the AascTemplateInfoDAO class  
    */  // holds the object of the AascShipMethodDAO class      
    
    AascShipmentOrderInfo aascShipmentOrderInfo; // holds the object of the AascShipmentOrderInfo class

    AascShipMethodInfo aascShipMethodInfo; // holds the object of the AascShipMethodInfo class   

    LinkedList shipMethodList; // holds the object of the LinkedList class    for shipMethodList

    
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
     * @return String object 
     **/
    public  String execute()   {

        try {
            logger.info("Entered execute method");

            session = 
                    request.getSession(true); // creating the HttpSession class object

            if (session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))) {

                return "error"; // 06-June-2007 Bhanu added this code for session validation.

            }
             int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
             if(roleIdSession != 2 && roleIdSession != 4){
              return "error";
             }
            Integer locationId = (Integer)session.getAttribute("location_id"); // Gettin location Id from session


            LinkedList sessionList = new LinkedList();

            /*aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // this static method returns the object of the AascDAOFactory class

            aascShipmentOrderInfoDAO = aascDAOFactory.getAascShipmentOrderInfoDAO(); */ // this method returns the onject of the AascShipmentOrderInfoDAO class
           
           if(!"".equalsIgnoreCase(request.getParameter("orderNumberSearch")) && request.getParameter("orderNumberSearch") != null && !(request.getParameter("orderNumberSearch")).startsWith("SC")){
                orderNumber = URLDecoder.decode(request.getParameter("orderNumberSearchHid"),"UTF-8");
            }
            else {
                orderNumber = request.getParameter("orderNumberSearch");
            }
            logger.info("orderNumber in ShipmentOrderInfoAction class = "+orderNumber);
            String orderNum = (String)session.getAttribute("orderNoTmp");

            try {
                session.removeAttribute("orderNoTmp");
            } catch (Exception e) {
                logger.info("Exception : " + e.getMessage());
            }

            clientID = (Integer)session.getAttribute("client_id");
            String clientId = "" + clientID;

            try {
                clientId.substring(0, 1);
            } catch (Exception e) {
                clientID = (Integer)session.getAttribute("client_id");
            }

            //sessionList.add(clientID) ;
            logger.info("Order Number got from the jsp in orderInfoActionclass=" + 
                        orderNumber);
            AascShipmentOrderDelegate aascShipmentOrderDelegate = new AascShipmentOrderDelegate();   
             
            if (orderNumber.equals("")) // checking whether the orderNumber is null or not
            {

                logger.info("order number is null");

            } // end of if block
            else {

                aascShipmentOrderInfo = aascShipmentOrderDelegate.getOrderInfo(orderNumber, new Integer(clientID), session);

                returnStatus = 
                        aascShipmentOrderInfo.getReturnStatus(); // this method returns the opStatus value of the procedure which retrives the header information from the database

                session.setAttribute("AascShipmentOrderInfo", aascShipmentOrderInfo);

                locationId = aascShipmentOrderInfo.getShipmentHeaderInfo().getLocationId();
                
                if("Y".equalsIgnoreCase(aascShipmentOrderInfo.getIntlFlag())){
                    
                    session.setAttribute("intlSaveFlag", "Y");
                    session.setAttribute("intlCustomsFlag", "Y");
                    
                }
                
                logger.info("returnStatus from aascShipmentOrderInfo==" + 
                            returnStatus+"    locationId : "+locationId);
                    
                if (returnStatus == 200) // checking whether the order details are retrieved or not
                {
                    try {

                        sessionList.add(locationId);
                        sessionList.add(clientID) ;
                        
                        aascShipMethodInfo = (AascShipMethodInfo)session.getAttribute("ShipMethodInfo"); 
                        shipMethodList = aascShipMethodInfo.getShipMethodList(); // this method returns the object of the LinkedList which have the ship method names

                        logger.info("Got all required objects for Shipment");

                    } catch (Exception e) {
                        logger.severe("Exception::"+e.getMessage());
                        errorKey = "aasc201";
                    }

                    session.setAttribute("ShipMethodList", 
                                         (Object)shipMethodList); // setting the shipMethodList object to the session 


                    session.setAttribute("AascShipmentOrderInfo", 
                                         aascShipmentOrderInfo); // setting the AascShipmentOrderInfo object to the session


                } // end of inner if block
                else {
                    if (returnStatus == 0) // To fix bug # 2662 By eshwari
                    {
                        errorKey = "aasc201";
                    }    
                    session.removeAttribute("AascShipmentOrderInfo");

                    aascShipmentOrderInfo = new AascShipmentOrderInfo();
//                    logger.info("orderNum before setting...."+orderNum);
                    try{
                    aascShipmentOrderInfo.getShipmentHeaderInfo().setPreviousOrderNumber(orderNum);
                    }catch(Exception e){
                       logger.info("Exception : "+e.getMessage());
                    }
                    session.setAttribute("AascShipmentOrderInfo", 
                                         aascShipmentOrderInfo);

                } // end of inner else block

            } // end of outer else block

            if (returnStatus == 200) {
                errorKey = "aasc200";
            } // end of if block
            else {
                errorKey = "aasc201";
            } // end of else block

        } // end of try block
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            errorKey = "aasc201";
        } // end of catch block

        request.setAttribute("key1", errorKey);
        request.setAttribute("key", errorKey); // To fix bug # 2662 By eshwari

        logger.info("Exit from execute()");

        return "success1";

    } // end of method execute
    
}// class of class ShipmentOrderInfoAction
