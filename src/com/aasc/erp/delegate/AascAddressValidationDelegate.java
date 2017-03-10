package com.aasc.erp.delegate;
/*
 * @(#)AascAddressValidationDelegate.java     24/02/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 */

import com.aasc.erp.bean.AascAddressValidationBean;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;

import com.aasc.erp.carrier.AascAddressValidation;
import com.aasc.erp.model.AascOracleShipSaveDAO;
import com.aasc.erp.model.AascShipSaveDAO;

import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AascAddressValidationAction class is action class for Address Validation.
 * @version   1
 * @author    Y Pradeep
 * History
 * 
 * 24-Feb-2015  Y Pradeep       Added this file for Address Validation.
 * 10-Mar-2015  Y Pradeep       Addred code to save order header details once after generating order number.
 * 27-May-2015  Suman G         Added code to fix #2936.
 * 07-Dec-2015  Y Pradeep       Modifed if condition to avoid null pointer exception for saveHeaderFlag in commanAcction method.
 * 21-Dec-2015  Y Pradeep       Modifeid cdoe to get webservice username, password and account number for Address Validation from Application.properties file.
 */

public class AascAddressValidationDelegate {
    
    private static Logger logger = AascLogger.getLogger("AascAddressValidationDelegate");
    String orderNumber = "";
    String customerName = "";
    String attentionName = "";
    String address1 = "";
    String address2 = "";
    String city = "";
    String state = "";
    String postalCode = "";
    String countryCode = "";
    String actionType = "";
    AascAddressValidationBean aascAddressValidationBean = null;
    AascShipMethodInfo aascShipMethodInfo;
    AascProfileOptionsBean aascProfileOptionsInfo;
    String fedExKey = "";
    String fedExPassword = "";
    String upsUserName = "";
    String upsPassword = "";
    String accessLicenseNumber = "";
    String meterNumber = "";
    String carrierAccountNumber = "";
    String addressValidationEnable = "";
    String addressValidationUserName = "";
    String addressValidationPassword = "";
    String addressValidationAccNumber = "";
    HashMap addressHashMap = new HashMap();
    List addressList = null;
    String addressType = "";
    String addressClassification = "";
    String descriptionStatus = "" ;

    public AascAddressValidationDelegate() {
    }

    /** This method is for saving order number details for the first time.
     * @param request
     * @param session
     * @return String
     */
    public String commonAction(HttpServletRequest request,HttpSession session){
        
        try{
            int  userId = (Integer)session.getAttribute("user_id"); // string userid
            session.removeAttribute("Error");
            int clientId = (Integer)session.getAttribute("client_id");
//            int locationId = Integer.parseInt(request.getParameter("locationId"));
            orderNumber = request.getParameter("orderNo");
            AascShipmentOrderInfo aascShipmentOrderInfo = null;
            AascShipSaveDAO aascShipSaveDAO=new AascOracleShipSaveDAO();
            String saveHeaderFlag=(String)session.getAttribute("saveHeaderFlag");
            aascShipmentOrderInfo = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
            AascShipmentHeaderInfo headerBean = aascShipmentOrderInfo.getShipmentHeaderInfo();
            orderNumber = headerBean.getOrderNumber();
            headerBean.setAvFlag("Y");
            aascShipmentOrderInfo.setShipmentHeaderInfo(headerBean);
            
            if("Y".equalsIgnoreCase(saveHeaderFlag)){
                logger.info("save header data");
                aascShipSaveDAO.getShipSaveDAO(userId,aascShipmentOrderInfo,clientId,122);
                session.setAttribute("saveHeaderFlag","N");
            }
            session.setAttribute("submitFlag","Y");
            return "success";
        } catch(Exception e){
            logger.severe("Exception in common method of AV delegate class e : "+e.getMessage());
            return "error";
        }
    }

    /** This method get all the required information and call AascAddressValidation.java class to call Address Validation web services.
     * @param request
     * @param session
     * @return object of type HashMap
     */
    public HashMap validateAddressAction(HttpServletRequest request,HttpSession session){
        try{
            ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
            int carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
            String cloudLabelPath = (String)session.getAttribute("cloudLabelPath");
            aascAddressValidationBean = new AascAddressValidationBean();
            customerName = request.getParameter("customerName"); // getting the customerName  of the jsp through the struts-config.xml
            attentionName = request.getParameter("contactName");
            address1 = request.getParameter("shipToAddress"); // getting the shipToAddress  of the jsp through the struts-config.xml
            address2 = request.getParameter("shipToAddrLine2");
            city = request.getParameter("city"); // getting the city  of the jsp through the struts-config.xml
            state = request.getParameter("state"); // getting the state  of the jsp through the struts-config.xml
            postalCode = request.getParameter("postalCode"); // getting the postalcode  of the jsp through the struts-config.xml
            countryCode = request.getParameter("countrySymbol") ;
            
            aascShipMethodInfo = (AascShipMethodInfo)session.getAttribute("ShipMethodInfo");
            aascProfileOptionsInfo = (AascProfileOptionsBean)session.getAttribute("ProfileOptionsInfo"); //getting the object of the AascProfileOptionsInfo class object through the session
                    
            upsUserName = aascShipMethodInfo.getCarrierCodeUserName(100);
            upsPassword = aascShipMethodInfo.getCarrierCodePassword(100);
            accessLicenseNumber = aascShipMethodInfo.getCodeAccessLicenseNumber(100);
            fedExKey = aascShipMethodInfo.getCarrierCodeUserName(110);
            fedExPassword = aascShipMethodInfo.getCarrierCodePassword(110);
            meterNumber = aascShipMethodInfo.getCodeMeterNumber(110);
            carrierAccountNumber = aascShipMethodInfo.getCarrierCodeAccountNumber(110);
            
            if(fedExKey == null || "".equalsIgnoreCase(fedExKey)){
               fedExKey = aascShipMethodInfo.getCarrierCodeUserName(111);
               fedExPassword = aascShipMethodInfo.getCarrierCodePassword(111);
               meterNumber = aascShipMethodInfo.getCodeMeterNumber(111);
               carrierAccountNumber = aascShipMethodInfo.getCarrierCodeAccountNumber(111);
            }  
            
            addressValidationEnable = aascProfileOptionsInfo.getAddresValidation();
            
            if("Y".equalsIgnoreCase(addressValidationEnable)){
                addressValidationUserName = resourceBundle.getString("WsUserName");//aascProfileOptionsInfo.getWsUserName();
                addressValidationPassword = resourceBundle.getString("WsPassword");//aascProfileOptionsInfo.getWsPassword();
                addressValidationAccNumber = resourceBundle.getString("WsAccountNumber");//aascProfileOptionsInfo.getWsAccountNumber();
            }
            aascAddressValidationBean.setOrderNumber(orderNumber);
            aascAddressValidationBean.setCarrierCode(carrierCode);
            aascAddressValidationBean.setAddressValidationUserName(addressValidationUserName);
            aascAddressValidationBean.setAddressValidationPassword(addressValidationPassword);
            aascAddressValidationBean.setAddressValidationAccNumber(addressValidationAccNumber);
            
            aascAddressValidationBean.setUpsUserName(upsUserName);
            aascAddressValidationBean.setUpsPassword(upsPassword);
            aascAddressValidationBean.setAccessLicenseNumber(accessLicenseNumber);
            aascAddressValidationBean.setFedExKey(fedExKey);
            aascAddressValidationBean.setFedExPassword(fedExPassword);
            aascAddressValidationBean.setCarrierAccountNumber(carrierAccountNumber);
            aascAddressValidationBean.setMeterNumber(meterNumber);
            
            
            aascAddressValidationBean.setCustomerName(customerName);
            aascAddressValidationBean.setAttentionName(attentionName);
            aascAddressValidationBean.setAddress1(address1);
            aascAddressValidationBean.setAddress2(address2);
            aascAddressValidationBean.setCity(city);
            aascAddressValidationBean.setState(state);
            aascAddressValidationBean.setPostalCode(postalCode);
            aascAddressValidationBean.setCountryCode(countryCode);
            
            AascAddressValidation av = new AascAddressValidation();
            
            String addressValidationURL = resourceBundle.getString("addressValidationURL");                
            int addressValidationPort = Integer.parseInt(resourceBundle.getString("addressValidationPort"));
            
            addressHashMap = av.sendAddressValidationRequest(aascAddressValidationBean, addressValidationURL, addressValidationPort, cloudLabelPath);
        
        } catch(Exception e){
            logger.info("Got exception in validationaddressaction method in AV delegate class = "+e.getMessage());
        }
        
        return addressHashMap;
    }
}
