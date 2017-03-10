package com.aasc.erp.action;
/*
 * @(#)AascIntlShipAcion.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.ol
 */

import com.aasc.erp.bean.AascAddressInfo;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlDataBean;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.delegate.AascIntlShipDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * AascIntlShipAcion extends Action.
 * @author 	Y Pradeep
 * @version 1
 * @since
 * 28/01/2015   Y Pradeep      Added this file for Internationa Shipping.
 * 05/02/2015   Y Pradeep      Added if condition to for UpsInternational action to load data related to UPS International popup page from database.
 * 11/02/2015   Y Pradeep      Added if condition to for FedExInternational and upsIntlAddressAction action to load data related to UPS and Fedex International popup page from database.
 * 18/02/2015   Y Pradeep      Added code java doc to all methods.
 * 17/04/2015   Suman G        Added session related code to fix #2743.
 * 17/06/2015  Suman G             Added code to fix #2986
 * 27/10/2015   G S Shekar      Added Terms of Trade and DHL international related code for DHL
 */


public class AascIntlShipAction extends ActionSupport implements  ServletRequestAware, ServletResponseAware {

    static Logger logger = AascLogger.getLogger(" AascIntlShipAcion "); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected AascIntlDataBean aascIntlDataBean;

    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory

    int userId; // to store userID

    int responsibilityId;

    String error = ""; // to store error.

    // String key = "";

   
    int opStatus = 0;
    int opItemStatus = 0;
    int opImpStatus = 0;
    int opBrokerStatus = 0;


    AascIntlHeaderInfo aascIntlHeaderInfo = null;
    AascIntlCommodityInfo aascIntlCommodityInfo = null;
    AascAddressInfo aascAddressInfo = null;
    AascIntlInfo aascIntlInfo = null;
    AascAddressInfo sedAddressInfo = null;
    AascAddressInfo soldToAddressInfo = null;
    AascAddressInfo forwardAgentInfo = null;
    AascAddressInfo intermediateConsigneeInfo = null;



    /** This is the main action class called from the Struts framework .
     * @return String
     */
    public String execute(){
        HttpSession session = request.getSession(); // getting the Session object
        String action = request.getParameter("actionType").trim();
        logger.info("actionType : " + action);
        String upsMode = request.getParameter("upsMode");
        System.out.println("upsMode:::"+upsMode);
        if(session.isNew() || !(session.getId().equals(session.getAttribute("SessionId")))) {
            return "errorSession";
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 2 && roleIdSession != 4){
            return "sessionError";
        }
        AascIntlShipDelegate aascIntlShipDelegate=new AascIntlShipDelegate(); 
        AascIntlDataBean intlDataBean = null ;
        int carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
        logger.info("carrierCode:::" + carrierCode);
        
               
        String result=aascIntlShipDelegate.commonAction(request,session);   
        if("error".equalsIgnoreCase(result)){
            return "error";
        }
        else if("success".equalsIgnoreCase(result)){
            if("UpsInternational".equalsIgnoreCase(action)) {
                logger.info("actionType is UpsInternational"); 
                intlDataBean = aascIntlShipDelegate.upsInternationalAction(request, session);
                setAascIntlDataBean(intlDataBean);
            }
             if("ShipExecInternational".equalsIgnoreCase(action)) {
                 logger.info("actionType is ShipExecInternational"); 
                 intlDataBean = aascIntlShipDelegate.shipExecInternationalAction(request, session);
                 setAascIntlDataBean(intlDataBean);
             }
             if("FedExInternational".equalsIgnoreCase(action)) {
                 logger.info("actionType is FedExInternational"); 
                 intlDataBean = aascIntlShipDelegate.fedExInternationalAction(request, session);
                 setAascIntlDataBean(intlDataBean);
             }
             //Mahesh Start
              if("StampsInternational".equalsIgnoreCase(action)) {
                  logger.info("actionType is FedExInternational"); 
                  intlDataBean = aascIntlShipDelegate.stampsInternationalAction(request, session);
                  setAascIntlDataBean(intlDataBean);
              }
             //Mahesh End
             if("upsIntlAddressAction".equalsIgnoreCase(action)) {
                 logger.info("actionType is upsIntlAddressAction"); 
                 intlDataBean = aascIntlShipDelegate.upsIntlAddressAction();
                 setAascIntlDataBean(intlDataBean);
             }
             if("shipExecIntlAddressAction".equalsIgnoreCase(action)) {
                 logger.info("actionType is shipExecIntlAddressAction"); 
                 intlDataBean = aascIntlShipDelegate.shipExecIntlAddressAction();
                 setAascIntlDataBean(intlDataBean);
             }
             //Shiva added for DHL
              if("DHLInternational".equalsIgnoreCase(action)) {
                  logger.info("actionType is DHLInternational"); 
                  intlDataBean = aascIntlShipDelegate.dhlInternationalAction(request,session);
                  setAascIntlDataBean(intlDataBean);
              }
			//Shiva code end
            if ("SAVE".equalsIgnoreCase(action)) {
                logger.info("actionType is SAVE");
                String status=aascIntlShipDelegate.saveAction(request,session);
                if("error".equalsIgnoreCase(status)){
                    return "error";
                }else if("success".equalsIgnoreCase(status)){
                    if(carrierCode == 100){
                        if(upsMode=="UPS Direct"){
                        intlDataBean = aascIntlShipDelegate.upsInternationalAction(request, session);
                    } 
                    else {
                        intlDataBean = aascIntlShipDelegate.shipExecInternationalAction(request, session);
                    }
                    }
                    else if(carrierCode == 110 || carrierCode == 111){
                        intlDataBean = aascIntlShipDelegate.fedExInternationalAction(request, session);
                    }
                    //Mahesh Start
                    else if(carrierCode == 115){
                        intlDataBean = aascIntlShipDelegate.stampsInternationalAction(request, session);
                    }
                    //Mahesh End
                    else if(carrierCode == 114){ //Shiva added for DHL
                        intlDataBean = aascIntlShipDelegate.dhlInternationalAction(request,session);
                    }
                    setAascIntlDataBean(intlDataBean);
                    return "success";
                }
            }
            if ("DELETE".equalsIgnoreCase(action)) {
                logger.info("actionType is DELETE");
                String status=aascIntlShipDelegate.deleteAction(request,session);
                if("error".equalsIgnoreCase(status)){
                    return "error";
                }else if("success".equalsIgnoreCase(status)){
                    if(carrierCode == 100){
                        if("UPS Direct".equalsIgnoreCase(upsMode)){
                        intlDataBean = aascIntlShipDelegate.upsInternationalAction(request, session);
                    }
                    else{
                        intlDataBean = aascIntlShipDelegate.shipExecInternationalAction(request, session);
                    }
                    }
                    else if(carrierCode == 110 || carrierCode == 111){
                        intlDataBean = aascIntlShipDelegate.fedExInternationalAction(request, session);
                    }
                    //Mahesh Start
                    else if(carrierCode == 115){
                        intlDataBean = aascIntlShipDelegate.stampsInternationalAction(request, session);
                    }
                    //Mahesh End
                    else if(carrierCode == 114){ //Shiva added for DHL
                        intlDataBean = aascIntlShipDelegate.dhlInternationalAction(request,session);
                    }
                    setAascIntlDataBean(intlDataBean);
                    return "success";
                }
            }
            if ("VIEWPRINT".equalsIgnoreCase(action)) {
                logger.info("Entered into action VIEWPRINT");
                return "viewPrint";
            }
            if ("EDIT".equalsIgnoreCase(action)) {
                logger.info("actionType is EDIT");
                String status=aascIntlShipDelegate.editAction(request,session);
                if("error".equalsIgnoreCase(status)){
                    return "error";
                }else if("success".equalsIgnoreCase(status)){
                    if(carrierCode == 100){
                        if(upsMode=="UPS Direct"){
                        intlDataBean = aascIntlShipDelegate.upsInternationalAction(request, session);
                    }
                    else{
                        intlDataBean = aascIntlShipDelegate.shipExecInternationalAction(request, session);
                    }}
                    else if(carrierCode == 110 || carrierCode == 111){
                        intlDataBean = aascIntlShipDelegate.fedExInternationalAction(request, session);
                    }
                    //Mahesh Start
                    else if(carrierCode == 115){
                        intlDataBean = aascIntlShipDelegate.stampsInternationalAction(request, session);
                    }
                    //Mahesh End
                    else if(carrierCode == 114){ //Shiva added for DHL
                        intlDataBean = aascIntlShipDelegate.dhlInternationalAction(request,session);
                    }
                    setAascIntlDataBean(intlDataBean);
                    return "success";
                }
            }
            if ("ADD".equalsIgnoreCase(action)) {
                logger.info("actionType is ADD");
                String status=aascIntlShipDelegate.addAction(request,session);
                if("error".equalsIgnoreCase(status)){
                    return "error";

                }else if("success".equalsIgnoreCase(status)){
                    System.out.println("upsModeupsMode"+upsMode);
                    if(carrierCode == 100){
                         if(upsMode=="UPS Direct"){
                        intlDataBean = aascIntlShipDelegate.upsInternationalAction(request, session);
                    }
                        else{
                            intlDataBean = aascIntlShipDelegate.shipExecInternationalAction(request, session);
                        }
                    }
                        else if(carrierCode == 110 || carrierCode == 111){
                        intlDataBean = aascIntlShipDelegate.fedExInternationalAction(request, session);
                    }
                    //Mahesh Start
                    else if(carrierCode == 115){
                        intlDataBean = aascIntlShipDelegate.stampsInternationalAction(request, session);
                    }
                    //Mahesh End
                    else if(carrierCode == 114){ //Shiva added for DHL
                        intlDataBean = aascIntlShipDelegate.dhlInternationalAction(request,session);
                    }
                    setAascIntlDataBean(intlDataBean);
                    return "success";
                }
            }
            /*
        if(action.equalsIgnoreCase("UPSSAVE"))
        {
          //int deliveryId=0;

         // AascDeliveryInfo aascDeliveryInfo = null;
          aascDeliveryInfo = (AascDeliveryInfo) session.getAttribute("DeliveryInfo");
          logger.info("set aascDeliveryInfo from session ");

             AascHeaderInfo headerBean = aascDeliveryInfo.getHeaderInfo();

             logger.info(

                    " set headerBean from aascDeliveryInfo for delivery "

                            + headerBean.getDeliveryName());

             deliveryId = headerBean.getDeliveryId();
             logger.info(" %%%%%%% deliveryId %%%%%%% "+deliveryId);
              aascIntlHeaderInfo.setDeliveryId(deliveryId);

             aascIntlHeaderInfo.setShipmentId(0);

             aascIntlHeaderInfo.setVoidFlag("N");

               String intlDescription = "";
            try{
               intlDescription = request.getParameter("Description").trim();
               String str = intlDescription.substring(0,1);
               logger.info("$$$$$$$$$$$$$$$$$$$ Descriptionz: $$$$$$$$$$$$$$$$$$$"+intlDescription);
            }catch(Exception e)
            {
              intlDescription = "";
            }
            aascIntlCommodityInfo.setDescription(intlDescription);
        }*/

            //aascIntlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(deliveryId);

            // session.setAttribute("InternationalShipments",aascIntlInfo);


       


        //logger.info("Before Forwarding:" + mapping.findForward("main"));
       
         }
         return "success";
    }

   
    /**
     * This method can replace the null values with nullString<br>
     * @return String that is ""<br>
     * @param obj of type Object<br>
     */

    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
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

    public void setAascIntlDataBean(AascIntlDataBean aascIntlDataBean) {
        this.aascIntlDataBean = aascIntlDataBean;
    }

    public AascIntlDataBean getAascIntlDataBean() {
        return aascIntlDataBean;
    }
}


