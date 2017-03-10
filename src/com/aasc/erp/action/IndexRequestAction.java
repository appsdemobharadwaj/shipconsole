/*

 * @IndexRequestAction.java        24/07/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

/**

 * IndexRequestAction class extends Action class.

 * @author Suman Gunda

 * @creation 24/07/2014
  
 * @History
  
 * 24/07/2014   N Srisha     Added code for requestType "LocationSetup"

 * 28/11/2014   Eshwari      Merged Sunanda's Pacakage Dimensions code
 * 10/12/2014   Y Praadeep   Added code to get Client details when clicked in Carrier Configuration in index page.
 * 22/12/2014   Eshwari M    Modified code by organizing and removing unwanted code
 * 29/12/2014   Eshwari M    Self Audit
 * 07/01/2015   Eshwari M    Modified code related to Package dimensions for role 2
 * 09/06/2015   Suman G      Added role id to fix #2986
 * 02/03/2016   Suman G      Added code for Make Payment
 */
package

com.aasc.erp.action;

import com.aasc.erp.delegate.AascIndexRequestDelegate;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascOracleDAOFactory;
import com.aasc.erp.model.AascProfileOptionsDAO;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class IndexRequestAction extends ActionSupport implements ServletRequestAware, 
                                                                 ServletResponseAware {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    AascProfileOptionsDAO aascProfileOptionsDAO;
    
    static Logger logger = AascLogger.getLogger("IndexRequestAction");

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

    public IndexRequestAction() {
    }
    /*
     * Suman Gunda added execute() method for performing action based on selection in Index Page
     */

    public String execute() {

        logger.info("In Index Request Action execute()");

        String requestType = request.getParameter("requestType");
        logger.info("requesttype is :" + requestType);

        HttpSession session = request.getSession(true);
        if (session.isNew() || 
            !(session.getId().equals(session.getAttribute("SessionId")))) {
            logger.info("session expired");
            return "error";
        }
        
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        
        String queryTimeOut = "";
        queryTimeOut = getText("queryTimeOut");
        session.setAttribute("queryTimeOut", queryTimeOut);
        AascIndexRequestDelegate aascIndexRequestDelegate = new AascIndexRequestDelegate();
 
        //String status = aascIndexRequestDelegate.commonAction(session,request, requestType); //,queryTimeOutInt,type, requestType);

        if (requestType.equalsIgnoreCase("Logout")) {
            logger.info("In Logout contoller");
            session.invalidate();
            return "login";

        } else if (requestType.equalsIgnoreCase("CreateCustomer")) {
            if(roleIdSession == 1 || roleIdSession == 2){
                logger.info("In Create Customer");
                String message = aascIndexRequestDelegate.getCustomerDetails(session);
                if ("success".equalsIgnoreCase(message)) {
                    return "CreateCustomer";
                } else {
                    return "error";
                }
            }else{
                return "error";
            }
        } 
        
        else if (requestType.equalsIgnoreCase("UploadOrders")) {
            if(roleIdSession == 2 || roleIdSession == 4){
                logger.info("In UploadOrders");
                String message = "success";
                    //aascIndexRequestDelegate.getCustomerDetails(session);
                if ("success".equalsIgnoreCase(message)) {
                    return "UploadOrders";
                } else {
                    return "error";
                }
            }else{
                return "error";
            }
        } 
        else if (requestType.equalsIgnoreCase("LocationSetup")) {
            if(roleIdSession == 2 || roleIdSession == 3){
                logger.info("In Location Setup");
                String message = aascIndexRequestDelegate.commonActionForPSCE(session);
                if ("success".equalsIgnoreCase(message)) {
                    return "LocationSetup";
                } else {
                    return "error";
                }
            }else{
                return "error";
            }
        } else if (requestType.equalsIgnoreCase("CreateUser")) {
            if(roleIdSession == 3){
                logger.info("In Create User");
                String message1 = aascIndexRequestDelegate.getLocationDetails(session);
                String message = aascIndexRequestDelegate.getUserDetails(session);
                if ("success".equalsIgnoreCase(message) && "success".equalsIgnoreCase(message1)) {
                    return "CreateUser";
                } else {
                    return "error";
                }
            }else{
                    return "error";
            }
        }

        else if (requestType.equalsIgnoreCase("CustomerLocationSetup")) {
            if(roleIdSession == 3){
                logger.info("In CustomerLocationSetup ");
                String message = aascIndexRequestDelegate.getCustomerLocationDetails(session);
    
                logger.info("Status of delegate In CustomerLocationSetup :::::::" +message);
                if ("success".equalsIgnoreCase(message)) {
                    return "CustomerLocationSetup";
                } else {
                    return "error";
                }
            }else{
                return "error";
            }
        }

        else if (requestType.equalsIgnoreCase("ProfileOptions")) {
            if(roleIdSession == 1 || roleIdSession ==2 || roleIdSession == 3 || roleIdSession == 4){
                logger.info("Inside profileoptions");
    
                int roleId = ((Integer)session.getAttribute("role_id")).intValue();
                 if(roleId == 1) 
                 { 
                     session.setAttribute("clientId", 0); 
                 }
    
                 //String message=aascIndexRequestDelegate.profileOptionsAction();
                 aascIndexRequestDelegate.carrierPaymethods(session);
                 String message = 
                      aascIndexRequestDelegate.getLocationDetails(session);
                 String message1=aascIndexRequestDelegate.profileOptionsAction(session, request);
                 //String psMessage =aascIndexRequestDelegate.commonActionForPS();
                 
                 if("SUCCESS".equalsIgnoreCase(message) && "SUCCESS".equalsIgnoreCase(message1)){
                            return "ProfileOptions";
                 }
                 else{
                    return "error";
                 }
            }else{
                return "error";
            }
        }

        else if (requestType.equalsIgnoreCase("CarrierConfiguration")) {
            if(roleIdSession == 1 || roleIdSession == 2 || roleIdSession == 3 || roleIdSession == 4){
                logger.info("Inside CarrierConfiguration");
                AascDAOFactory aascDAOFactory = new AascOracleDAOFactory();
                int roleId = ((Integer)session.getAttribute("role_id")).intValue();
                aascProfileOptionsDAO = aascDAOFactory.getAascProfileOptionsDAO();
                if (roleId == 1 || roleId == 2) {
                    HashMap clientDetailsHashMap = aascProfileOptionsDAO.getClientDetailsLookUpValues();
                    session.setAttribute("clientDetailsHashMap", clientDetailsHashMap);
                }
                //  aascIndexRequestDelegate.carrierPaymethods(session);
                String message = aascIndexRequestDelegate.getLocationDetails(session);
                // The above getLocationDetails method call get locationsList and locationDetailsMap and put in Session                        
                logger.info("Revoming aascCarrierConfiguration and aascAccountNumbersInfo From Session");
    
                session.removeAttribute("aascCarrierConfiguration");
                session.removeAttribute("aascAccountNumbersInfo");
                logger.info("getLocationDetails message :::" + message);
    
                String response = aascIndexRequestDelegate.getCarrierNames(session);
                logger.info("carrier name reteriving " + response);
    
                if ("SUCCESS".equalsIgnoreCase(response)) {
                    logger.info("returning ::CarrierConfiguration");
                    return "CarrierConfiguration";
                } else {
                    return "error";
                }
            }else{
                return "error";
            }

        } 
        
        else if (requestType.equalsIgnoreCase("Tracking") ||
            requestType.equalsIgnoreCase("Tracking1") || 
            requestType.equalsIgnoreCase("Reports1")) {
            if(roleIdSession == 2 || roleIdSession == 4 || roleIdSession ==5){
                Integer loc = (Integer)session.getAttribute("location_id");
                request.setAttribute("locationId", loc);
                
                if (requestType.equalsIgnoreCase("Tracking")) {
                    logger.info("inside tracking if block");
                    requestType = "Tracking";
    
                    String message = aascIndexRequestDelegate.getLocationDetails(session);
                    if ("success".equalsIgnoreCase(message)) {
                        return "Tracking";
                    } else {
                        return "error";
                    }
    
                }else if (requestType.equalsIgnoreCase("Tracking1") || requestType.equalsIgnoreCase("Reports1")) {
                    
                    String message = aascIndexRequestDelegate.commonActionForPSCE(session);
                    if ("success".equalsIgnoreCase(message)) {
                      if(requestType.equalsIgnoreCase("Tracking1"))
                        return "Tracking";
                      else if(requestType.equalsIgnoreCase("Reports1"))
                        return "Reports" ;
                    }else {
                        return "error";
                    }
                } 
                else {
                    logger.info("insude return error");
                    return "error";
                }
            }else{
                return "error";
            }
        }
        else if (requestType.equalsIgnoreCase("PackageDimensions")) {
            if(roleIdSession == 2 || roleIdSession == 3 || roleIdSession == 4){
                logger.info("In Package Dimensions contoller");
                String psceMessage = aascIndexRequestDelegate.commonActionForPSCE(session);
                String messagePdsc=aascIndexRequestDelegate.getPackageDimensionDetails(session);
                if(("SUCCESS".equalsIgnoreCase(psceMessage))||("SUCCESS".equalsIgnoreCase(messagePdsc))){
                    return "PackageDimensions";
                }
                else{
                    return "error";
                }
            }else{
                return "error";
            }

        }       
        else if(requestType.equalsIgnoreCase("MakePayment")){
            if(roleIdSession == 3){
                aascIndexRequestDelegate.getPricingDetails(request,session);
                return "MakePayment";
            }
            else{
                return "error";
            }
        }
        
        /*String message = aascIndexRequestDelegate.commonAction(session,request);
        
        if("success".equalsIgnoreCase(message))
        {*/
          if (requestType.equalsIgnoreCase("Shipment") || requestType.equalsIgnoreCase("Shipment1")) 
          {
              if(roleIdSession == 2 || roleIdSession == 4){
            logger.info(" in Shipment controller");
            
            String psceMessage = "" ;
            
            String shipmentMessage = aascIndexRequestDelegate.shipmentAction(session);
            
            logger.info("shipmentMessage : "+shipmentMessage);
            String maMessage = aascIndexRequestDelegate.commonActionForMA(session);
                        
            if(requestType.equalsIgnoreCase("Shipment1"))
            {
                Integer loc = (Integer)session.getAttribute("location_id");
                request.setAttribute("locationId", loc);
                psceMessage = aascIndexRequestDelegate.commonActionForPSCE(session);
            }    
                
            if("SUCCESS".equalsIgnoreCase(shipmentMessage) || "SUCCESS".equalsIgnoreCase(maMessage))
            {
              if(requestType.equalsIgnoreCase("Shipment1")){
                if("SUCCESS".equalsIgnoreCase(psceMessage)) {
                  requestType = "Shipment1";
                  session.setAttribute("ShipmentRequest", "Shipment");
                }
                else{
                  return "error" ; 
                }
              }  
              else if (requestType.equalsIgnoreCase("Shipment"))
              {  
                  String message = aascIndexRequestDelegate.commonAction(session,request);
                  if("SUCCESS".equalsIgnoreCase(message))
                     return "Shipment";
                  else
                     return "error" ;
              }
            }  
            else{
                logger.info("inside Shipment error");
                return "error";
            }
              }else{
                  return "error";
              }
          }
        /*}
         else {
             return "error";
         }
         */ 
        
        logger.info("requestType : " + requestType);
        return requestType;
    }
}
