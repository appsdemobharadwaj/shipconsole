package com.aasc.erp.delegate;
/*
 * @(#)AascIntlShipDelegate.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.ol
 */

import com.aasc.erp.bean.AascAddressInfo;
import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.bean.AascShipmentOrderInfo;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.bean.AascIntlCommodityInfo;
import com.aasc.erp.bean.AascIntlDataBean;
import com.aasc.erp.bean.AascIntlHeaderInfo;
import com.aasc.erp.bean.AascIntlInfo;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.model.AascIntlShipmentsDAO;

import com.aasc.erp.model.AascOracleShipSaveDAO;
import com.aasc.erp.model.AascShipSaveDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AascIntlShipDelegate class.
 * @author 	Y Pradeep
 * @version 1
 * @since
 * 28/01/2015   Y Pradeep    Added this file for Internationa Shipping.
 * 05/01/2015   Y Pradeep    Added upsInternationalAction() method to load all required data from database onload of UPS International popup page.
 * 11/02/2015   Y Pradeep    Added fedExInternationalAction() and upsIntlAddressAction() methods to load all required data from database onload of UPS and FedEx International popup page.
 * 16/02/2015   Y Pradeep    Modified code to generate order number on click og Ship button in Shipping page.
 * 18/02/2015   Y Pradeep    Added code java doc to all methods.
 * 10/03/2015   Y Pradeep    Addred code to save order header details once after generating order number.
 * 11/03/2015   Y Pradeep    Added if conditions to set Invoice Currency Code and Currency Code as "US Dollar" by default in upsInternationalAction() method.
 * 12/03/2015   Y Pradeep    Modified if condition in commonAction() method to avoide Null Pointer Exception. Bug #2670.
 * 20/03/2015   Y Pradeep    Modified code to get list of Countries and Currency Codes in HashMap instead of LinkedList.
 * 12/06/2015   Y Pradeep    Modified code in fedExInternationalAction() method to avoide exceptions when voiding orders from backend db. Bug #2970.
 * 30/10/2015   Shiva G      Added dhlInternationalAction method and termsOfTrade for DHL
 * 04/11/2015   Mahesh V     Added code for #3884 declaration Statement issue
 */

public class AascIntlShipDelegate{
    static Logger logger = AascLogger.getLogger(" AascIntlShipDelegate "); // logger object
    AascDAOFactory aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // object of AascDAOFactory
    AascIntlHeaderInfo aascIntlHeaderInfo = null;
    AascIntlCommodityInfo aascIntlCommodityInfo = null;
    AascAddressInfo aascAddressInfo = null;
    AascIntlInfo aascIntlInfo = null;
    AascAddressInfo sedAddressInfo = null;
    AascAddressInfo soldToAddressInfo = null;
    AascAddressInfo forwardAgentInfo = null;
    AascAddressInfo intermediateConsigneeInfo = null;
    AascIntlShipmentsDAO aascIntlShipmentsDAO=null;
    AascShipSaveDAO aascShipSaveDAO=new AascOracleShipSaveDAO();
    
    int userId; // to store userID
    int responsibilityId;
    int clientId;
    int locationId;

    String error = ""; // to store error.
    // String key = "";
    int opStatus = 0;
    int opItemStatus = 0;
    int opImpStatus = 0;
    int opBrokerStatus = 0;
    String orderNumber = "";
    int carrierCode;
    int returnStatus;
    
    String saveHeaderFlag="";

    /** This method is used to get common variables data from request and session for feature use.
     * @param request
     * @param session
     * @return String
     */
    public String commonAction(HttpServletRequest request,HttpSession session){
        logger.info("Entered execute method");

        saveHeaderFlag=(String)session.getAttribute("saveHeaderFlag");
        aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();
        aascIntlInfo = new AascIntlInfo();
        aascIntlHeaderInfo = new AascIntlHeaderInfo();
        sedAddressInfo = new AascAddressInfo();
        soldToAddressInfo = new AascAddressInfo();
        aascAddressInfo = new AascAddressInfo();
        forwardAgentInfo = new AascAddressInfo();
        intermediateConsigneeInfo = new AascAddressInfo();
        aascIntlCommodityInfo = new AascIntlCommodityInfo();
        LinkedList commodityList = new LinkedList();

        aascIntlInfo.setIntlHeaderInfo(aascIntlHeaderInfo);
        aascIntlInfo.setIntlCommodityInfo(commodityList);

        carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
        logger.info("carrierCode:::" + carrierCode);
        try {
            if(session.isNew() || !(session.getId().equals(session.getAttribute("SessionId")))) {
                return "error";
            }
            
            userId = (Integer)session.getAttribute("user_id"); // string userid
            logger.info("userIdstr = "+userId);
            
            session.removeAttribute("Error");
                        
            clientId = (Integer)session.getAttribute("client_id");
            logger.info("clientId = " + clientId);
            
            locationId = Integer.parseInt(request.getParameter("locationId"));
            
            String shipmentType = (String)session.getAttribute("shipment");
            logger.info("shipmentType = " + shipmentType);

            AascShipmentOrderInfo aascShipmentOrderInfo = null;
            if ("Shipment".equalsIgnoreCase(shipmentType)) { 
                logger.info("shipmentType is Shipment");
                aascShipmentOrderInfo = (AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo");
                AascShipmentHeaderInfo headerBean = aascShipmentOrderInfo.getShipmentHeaderInfo();
                orderNumber = headerBean.getOrderNumber();
                if("Y".equalsIgnoreCase(saveHeaderFlag)){                       // Modified if condition to avoide null pointer exception. Bug #2670. By Y Pradeep
                    logger.info("save header data");
                    returnStatus = aascShipSaveDAO.getShipSaveDAO(userId,aascShipmentOrderInfo,clientId,122); //added by Jagadish to save header details when intl popup is opened. 122 passed becuase the details should be saved as not-shipped
                    session.setAttribute("saveHeaderFlag","N");
                }
                logger.info("orderNumber in intl delegate = " + orderNumber);
            }
        }
        catch(Exception e){
            logger.severe("Got Exception in commonAction method "+e.getMessage());
//            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /** This method is used to load all required data from database when ShipExec International Shipping page is opened like lookup details, country codes, currency codes, commodity names, importer names and broker names etc.
     * @param request
     * @param session
     * @return aascIntlDataBean
     */

    public AascIntlDataBean shipExecInternationalAction(HttpServletRequest request, HttpSession session){
        AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
        try{
            //ArrayList<String> commItems = null;
            //LinkedList countryList = null;
            //LinkedList currencyCodeList = null;
            HashMap sbUOM = null;
            HashMap exportType = null;
            HashMap rvcCalMthd = null;
            HashMap producer = null;
            HashMap preferenceCriteria = null;
            HashMap UOM = null;
            HashMap TOM = null;
            HashMap reasonExport = null;
            HashMap licExpCode = null;
            HashMap bondCode = null;
            HashMap modeTransport = null;
            AascIntlHeaderInfo intlHeaderBean = null;
            AascIntlInfo IntlInfoBean = null;
            AascAddressInfo soldToAddressBean = null;
            AascAddressInfo sedAddressBean = null;
            AascAddressInfo forwardAgentBean=null;
            AascAddressInfo intermediateConsigneeBean=null;
            AascAddressInfo addressInfoBean = null;
            AascIntlCommodityInfo commInfoBean = null;
            String orderNumber = "";
            String shipStatusFlag = "";
            String shipFlagStr = request.getParameter("shipFlagStr");
            if("".equalsIgnoreCase(shipFlagStr) && shipFlagStr == null){
                shipFlagStr = "N";
            }
            String intlSaveFlag = request.getParameter("intlSaveFlag");
            logger.info("intlSaveFlag = "+intlSaveFlag);
            if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
                intlSaveFlag = "N";
            }
            String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
            session.removeAttribute("intlCommActionFlag");
            if(intlCommActionFlag == null && "".equalsIgnoreCase(intlCommActionFlag)){
                intlCommActionFlag = "N";
            }
            String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
            logger.info("intlSaveActionFlag = "+intlSaveActionFlag);
            session.removeAttribute("intlSaveActionFlag");
            if(intlSaveActionFlag == null && "".equalsIgnoreCase(intlSaveActionFlag)){
                intlSaveActionFlag = "N";
            }
            
            orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
            shipStatusFlag = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();
                    
            if(!"Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    //commItems = aascIntlShipmentsDAO.getIntlCommodityLineItems(clientId, locationId);
                    intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    if("N".equalsIgnoreCase(intlSaveFlag) && ("N".equalsIgnoreCase(intlSaveActionFlag) && "N".equalsIgnoreCase(intlCommActionFlag)))
                    {
                        aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                    }
                }catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }
                    
            if("Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    IntlInfoBean = aascIntlShipmentsDAO.getIntlDetails(orderNumber, clientId, locationId);
                    intlHeaderBean = IntlInfoBean.getIntlHeaderInfo();
                }
                catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }else
            {
                if("Y".equalsIgnoreCase(intlSaveFlag) || "Y".equalsIgnoreCase(intlSaveActionFlag))
                {
                    try
                    {
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    }catch(Exception e){
                        logger.severe("in exception "+e.getMessage());
                    }
                    if(intlHeaderBean!=null){
                        if(intlHeaderBean.getCarrierCode()==carrierCode )
                        {
    //                            intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        else
                        {
//                            aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                            intlHeaderBean=null;
                        }
                    }
                      
                }
            }
            if(intlHeaderBean == null)
            {
                intlHeaderBean = new AascIntlHeaderInfo();
                soldToAddressBean = new AascAddressInfo();
                sedAddressBean = new AascAddressInfo();
                forwardAgentBean = new AascAddressInfo();
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            soldToAddressBean = intlHeaderBean.getSoldToAddressInfo();
            if(soldToAddressBean == null)
            {
                soldToAddressBean = new AascAddressInfo();
            }
                    
            sedAddressBean = intlHeaderBean.getSedAddressInfo();
            if(sedAddressBean == null)
            {
                sedAddressBean = new AascAddressInfo();
            }
                   
            forwardAgentBean = intlHeaderBean.getForwardAgentInfo();
            if(forwardAgentBean == null)
            {
                forwardAgentBean = new AascAddressInfo();
            }
                    
            intermediateConsigneeBean = intlHeaderBean.getIntermediateConsigneeInfo();
            if(intermediateConsigneeBean == null)
            {
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            if(("".equalsIgnoreCase(intlHeaderBean.getOrderNumber()) || intlHeaderBean.getOrderNumber() == null) && !"Y".equalsIgnoreCase(shipFlagStr)){
                intlHeaderBean = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
            }
            commInfoBean = (AascIntlCommodityInfo)session.getAttribute("CommodityLineValue");
            session.removeAttribute("CommodityLineValue");
         
            if(commInfoBean == null){
                commInfoBean = new AascIntlCommodityInfo();
                addressInfoBean = new AascAddressInfo();
            }
             
            addressInfoBean = commInfoBean.getAascAddressInfo();   
            if(addressInfoBean == null)
            {
                addressInfoBean = new AascAddressInfo();
            }
            // Added below if conditions to set Invoice Currency Code and Currency Code as "US Dollar" by default.
            if("".equalsIgnoreCase(intlHeaderBean.getInvoiceCurrencyCode()) || intlHeaderBean.getInvoiceCurrencyCode() == null){
                intlHeaderBean.setInvoiceCurrencyCode("USD");
            }
            if("".equalsIgnoreCase(intlHeaderBean.getIntlCurrencyCode()) || intlHeaderBean.getIntlCurrencyCode() == null){
                intlHeaderBean.setIntlCurrencyCode("USD");
            }
            
            aascIntlDataBean.setIntlInfoBean(IntlInfoBean);
            aascIntlDataBean.setIntlHeaderBean(intlHeaderBean); 
            aascIntlDataBean.setAascCommodityBean(commInfoBean);
            
            AascIntlHeaderInfo checkStatus = (AascIntlHeaderInfo) session.getAttribute("checkStatus");
            AascIntlHeaderInfo checkFormStatus =null;
                  
            logger.info("Commercial invoice::"+intlHeaderBean.getCommercialInv());
            logger.info("SED::"+intlHeaderBean.getShippersExportDecl());
            logger.info("USCO::"+intlHeaderBean.getUsCertOrigin());
            logger.info("NAFTA::"+intlHeaderBean.getNaftaCertOrigin());
            logger.info("InvoiceValue::"+intlHeaderBean.getInvoiceValue());
                        
            if(checkStatus != null){
                checkFormStatus  = checkStatus;
            }
            else
            {
                checkFormStatus=intlHeaderBean;
            }
            
            String commercialInvChecked = "";
            
            if("Y".equalsIgnoreCase(checkFormStatus.getCommercialInv()))
            {
                commercialInvChecked = "true";
            }
            logger.info("Commercial Invoice chk : "+commercialInvChecked);
            aascIntlDataBean.setCommercialInvChecked(commercialInvChecked);
            
            String usCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getUsCertOrigin()))
            {
                usCertOrigin = "true";
            }
            logger.info("US Cer Origin chk : "+usCertOrigin);
            aascIntlDataBean.setUsCertOrigin(usCertOrigin);
            
            String naftaCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getNaftaCertOrigin()))
            {
                naftaCertOrigin = "true";
            }
            logger.info("Nafta Cer Origin chk : "+naftaCertOrigin);
            aascIntlDataBean.setNaftaCertOrigin(naftaCertOrigin);
            
            String shippersExportDecl = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getShippersExportDecl()))
            {
                shippersExportDecl = "true";
            }
            logger.info("SED chk : "+shippersExportDecl);
            aascIntlDataBean.setShippersExportDecl(shippersExportDecl);
            
            Map countryHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCountryLookups());
            Map currencyCodeHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCurrencyCodeLookups());
            sbUOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"SBUOM");
            aascIntlDataBean.setSbUOM(sbUOM);
            exportType = aascIntlShipmentsDAO.getUpsLookUpValues(100,"EXT");
            aascIntlDataBean.setExportType(exportType);
            rvcCalMthd = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RVC");
            aascIntlDataBean.setRvcCalMthd(rvcCalMthd);
            producer = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PROD");
            aascIntlDataBean.setProducer(producer);
            preferenceCriteria = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PREF_CRI");
            aascIntlDataBean.setPreferenceCriteria(preferenceCriteria);
            UOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"UNIT_OF_MEASURE");
            aascIntlDataBean.setUOM(UOM);
            TOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"TOS");
            aascIntlDataBean.setTOM(TOM);
            reasonExport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RFE");
            aascIntlDataBean.setReasonExport(reasonExport);
            licExpCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"LEC");
            aascIntlDataBean.setLicExpCode(licExpCode);
            bondCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"BCODE");
            aascIntlDataBean.setBondCode(bondCode);
            modeTransport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"MOT");
            aascIntlDataBean.setModeTransport(modeTransport);
            /*String countryCode = "";
            String countryName = "";
            AascShipMethodInfo aascCountryDetails = null;
            Iterator countryListItr = countryList.iterator(); 
            HashMap countries = new HashMap();
            while(countryListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)countryListItr.next();
                countryCode = aascCountryDetails.getCountryCode();
                countryName = aascCountryDetails.getCountryName();
                countries.put(countryCode, countryName);
            }*/
            aascIntlDataBean.setCountryList(countryHashMap);
            /*String invoiceCurrencyCodeStr = "";
            String invoiceCurrencyNameStr = "";
            Iterator invoiceCurrencyDetailListItr = currencyCodeList.iterator();
            HashMap currencies = new HashMap();
            while(invoiceCurrencyDetailListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)invoiceCurrencyDetailListItr.next();
                invoiceCurrencyCodeStr = aascCountryDetails.getCurrencyCode();
                invoiceCurrencyNameStr = aascCountryDetails.getCurrencyName();
                currencies.put(invoiceCurrencyCodeStr, invoiceCurrencyNameStr);
            }*/
            aascIntlDataBean.setCurrencyCodeList(currencyCodeHashMap);
            
        } catch(Exception e){
            logger.severe("Got Exception e "+e.getMessage() );
        }
        return aascIntlDataBean;
    }

//Mahesh Start
    /** This method is used to load all required data from database when Stamps.com International Shipping page is opened like lookup details, country codes, currency codes, commodity names, importer names and broker names etc.
     * @param request
     * @param session
     * @return aascIntlDataBean
     */

    public AascIntlDataBean stampsInternationalAction(HttpServletRequest request, HttpSession session){
        AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
        try{
            //ArrayList<String> commItems = null;
            //LinkedList countryList = null;
            //LinkedList currencyCodeList = null;
            HashMap sbUOM = null;
            HashMap exportType = null;
            HashMap rvcCalMthd = null;
            HashMap producer = null;
            HashMap preferenceCriteria = null;
            HashMap UOM = null;
            HashMap TOM = null;
            HashMap reasonExport = null;
            HashMap licExpCode = null;
            HashMap bondCode = null;
            HashMap modeTransport = null;
            AascIntlHeaderInfo intlHeaderBean = null;
            AascIntlInfo IntlInfoBean = null;
            AascAddressInfo soldToAddressBean = null;
            AascAddressInfo sedAddressBean = null;
            AascAddressInfo forwardAgentBean=null;
            AascAddressInfo intermediateConsigneeBean=null;
            AascAddressInfo addressInfoBean = null;
            AascIntlCommodityInfo commInfoBean = null;
            String orderNumber = "";
            String shipStatusFlag = "";
            String shipFlagStr = request.getParameter("shipFlagStr");
            if("".equalsIgnoreCase(shipFlagStr) && shipFlagStr == null){
                shipFlagStr = "N";
            }
            String intlSaveFlag = request.getParameter("intlSaveFlag");
            logger.info("intlSaveFlag = "+intlSaveFlag);
            if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
                intlSaveFlag = "N";
            }
            String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
            session.removeAttribute("intlCommActionFlag");
            if(intlCommActionFlag == null && "".equalsIgnoreCase(intlCommActionFlag)){
                intlCommActionFlag = "N";
            }
            String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
            logger.info("intlSaveActionFlag = "+intlSaveActionFlag);
            session.removeAttribute("intlSaveActionFlag");
            if(intlSaveActionFlag == null && "".equalsIgnoreCase(intlSaveActionFlag)){
                intlSaveActionFlag = "N";
            }
            
            orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
            shipStatusFlag = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();
                    
            if(!"Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    //commItems = aascIntlShipmentsDAO.getIntlCommodityLineItems(clientId, locationId);
                    intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    if("N".equalsIgnoreCase(intlSaveFlag) && ("N".equalsIgnoreCase(intlSaveActionFlag) && "N".equalsIgnoreCase(intlCommActionFlag)))
                    {
                        aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                    }
                }catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }
                    
            if("Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    IntlInfoBean = aascIntlShipmentsDAO.getIntlDetails(orderNumber, clientId, locationId);
                    intlHeaderBean = IntlInfoBean.getIntlHeaderInfo();
                }
                catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }else
            {
                if("Y".equalsIgnoreCase(intlSaveFlag) || "Y".equalsIgnoreCase(intlSaveActionFlag))
                {
                    try
                    {
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    }catch(Exception e){
                        logger.severe("in exception "+e.getMessage());
                    }
                    if(intlHeaderBean!=null){
                        if(intlHeaderBean.getCarrierCode()==carrierCode )
                        {
    //                            intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        else
                        {
                         //   aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                         //   intlHeaderBean=null;
                        }
                    }
                      
                }
            }
            if(intlHeaderBean == null)
            {
                intlHeaderBean = new AascIntlHeaderInfo();
                soldToAddressBean = new AascAddressInfo();
                sedAddressBean = new AascAddressInfo();
                forwardAgentBean = new AascAddressInfo();
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            soldToAddressBean = intlHeaderBean.getSoldToAddressInfo();
            if(soldToAddressBean == null)
            {
                soldToAddressBean = new AascAddressInfo();
            }
                    
            sedAddressBean = intlHeaderBean.getSedAddressInfo();
            if(sedAddressBean == null)
            {
                sedAddressBean = new AascAddressInfo();
            }
                   
            forwardAgentBean = intlHeaderBean.getForwardAgentInfo();
            if(forwardAgentBean == null)
            {
                forwardAgentBean = new AascAddressInfo();
            }
                    
            intermediateConsigneeBean = intlHeaderBean.getIntermediateConsigneeInfo();
            if(intermediateConsigneeBean == null)
            {
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            if(("".equalsIgnoreCase(intlHeaderBean.getOrderNumber()) || intlHeaderBean.getOrderNumber() == null) && !"Y".equalsIgnoreCase(shipFlagStr)){
                intlHeaderBean = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
            }
            commInfoBean = (AascIntlCommodityInfo)session.getAttribute("CommodityLineValue");
            session.removeAttribute("CommodityLineValue");
         
            if(commInfoBean == null){
                commInfoBean = new AascIntlCommodityInfo();
                addressInfoBean = new AascAddressInfo();
            }
             
            addressInfoBean = commInfoBean.getAascAddressInfo();   
            if(addressInfoBean == null)
            {
                addressInfoBean = new AascAddressInfo();
            }
            // Added below if conditions to set Invoice Currency Code and Currency Code as "US Dollar" by default.
            if("".equalsIgnoreCase(intlHeaderBean.getInvoiceCurrencyCode()) || intlHeaderBean.getInvoiceCurrencyCode() == null){
                intlHeaderBean.setInvoiceCurrencyCode("USD");
            }
            if("".equalsIgnoreCase(intlHeaderBean.getIntlCurrencyCode()) || intlHeaderBean.getIntlCurrencyCode() == null){
                intlHeaderBean.setIntlCurrencyCode("USD");
            }
            
            aascIntlDataBean.setIntlInfoBean(IntlInfoBean);
            aascIntlDataBean.setIntlHeaderBean(intlHeaderBean); 
            aascIntlDataBean.setAascCommodityBean(commInfoBean);
            
            AascIntlHeaderInfo checkStatus = (AascIntlHeaderInfo) session.getAttribute("checkStatus");
            AascIntlHeaderInfo checkFormStatus =null;
                  
            logger.info("Commercial invoice::"+intlHeaderBean.getCommercialInv());
            logger.info("SED::"+intlHeaderBean.getShippersExportDecl());
            logger.info("USCO::"+intlHeaderBean.getUsCertOrigin());
            logger.info("NAFTA::"+intlHeaderBean.getNaftaCertOrigin());
            logger.info("InvoiceValue::"+intlHeaderBean.getInvoiceValue());
                        
            if(checkStatus != null){
                checkFormStatus  = checkStatus;
            }
            else
            {
                checkFormStatus=intlHeaderBean;
            }
            
            String commercialInvChecked = "";
            
            if("Y".equalsIgnoreCase(checkFormStatus.getCommercialInv()))
            {
                commercialInvChecked = "true";
            }
            logger.info("Commercial Invoice chk : "+commercialInvChecked);
            aascIntlDataBean.setCommercialInvChecked(commercialInvChecked);
            
            String usCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getUsCertOrigin()))
            {
                usCertOrigin = "true";
            }
            logger.info("US Cer Origin chk : "+usCertOrigin);
            aascIntlDataBean.setUsCertOrigin(usCertOrigin);
            
            String naftaCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getNaftaCertOrigin()))
            {
                naftaCertOrigin = "true";
            }
            logger.info("Nafta Cer Origin chk : "+naftaCertOrigin);
            aascIntlDataBean.setNaftaCertOrigin(naftaCertOrigin);
            
            String shippersExportDecl = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getShippersExportDecl()))
            {
                shippersExportDecl = "true";
            }
            logger.info("SED chk : "+shippersExportDecl);
            aascIntlDataBean.setShippersExportDecl(shippersExportDecl);
            
            Map countryHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCountryLookups());
            Map currencyCodeHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCurrencyCodeLookups());
            sbUOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"SBUOM");
            aascIntlDataBean.setSbUOM(sbUOM);
            exportType = aascIntlShipmentsDAO.getUpsLookUpValues(100,"EXT");
            aascIntlDataBean.setExportType(exportType);
            rvcCalMthd = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RVC");
            aascIntlDataBean.setRvcCalMthd(rvcCalMthd);
            producer = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PROD");
            aascIntlDataBean.setProducer(producer);
            preferenceCriteria = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PREF_CRI");
            aascIntlDataBean.setPreferenceCriteria(preferenceCriteria);
            UOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"UNIT_OF_MEASURE");
            aascIntlDataBean.setUOM(UOM);
            TOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"TOS");
            aascIntlDataBean.setTOM(TOM);
            reasonExport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RFE");
            aascIntlDataBean.setReasonExport(reasonExport);
            licExpCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"LEC");
            aascIntlDataBean.setLicExpCode(licExpCode);
            bondCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"BCODE");
            aascIntlDataBean.setBondCode(bondCode);
            modeTransport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"MOT");
            aascIntlDataBean.setModeTransport(modeTransport);
            /*String countryCode = "";
            String countryName = "";
            AascShipMethodInfo aascCountryDetails = null;
            Iterator countryListItr = countryList.iterator(); 
            HashMap countries = new HashMap();
            while(countryListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)countryListItr.next();
                countryCode = aascCountryDetails.getCountryCode();
                countryName = aascCountryDetails.getCountryName();
                countries.put(countryCode, countryName);
            }*/
            aascIntlDataBean.setCountryList(countryHashMap);
            /*String invoiceCurrencyCodeStr = "";
            String invoiceCurrencyNameStr = "";
            Iterator invoiceCurrencyDetailListItr = currencyCodeList.iterator();
            HashMap currencies = new HashMap();
            while(invoiceCurrencyDetailListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)invoiceCurrencyDetailListItr.next();
                invoiceCurrencyCodeStr = aascCountryDetails.getCurrencyCode();
                invoiceCurrencyNameStr = aascCountryDetails.getCurrencyName();
                currencies.put(invoiceCurrencyCodeStr, invoiceCurrencyNameStr);
            }*/
            aascIntlDataBean.setCurrencyCodeList(currencyCodeHashMap);
            
        } catch(Exception e){
            logger.severe("Got Exception e "+e.getMessage() );
        }
        return aascIntlDataBean;
    }
//Mahesh End


    /** This method is used to load all required data from database when UPS International Shipping page is opened like lookup details, country codes, currency codes, commodity names, importer names and broker names etc.
     * @param request
     * @param session
     * @return aascIntlDataBean
     */
    public AascIntlDataBean upsInternationalAction(HttpServletRequest request, HttpSession session){
        AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
        try{
            //ArrayList<String> commItems = null;
            //LinkedList countryList = null;
            //LinkedList currencyCodeList = null;
            HashMap sbUOM = null;
            HashMap exportType = null;
            HashMap rvcCalMthd = null;
            HashMap producer = null;
            HashMap preferenceCriteria = null;
            HashMap UOM = null;
            HashMap TOM = null;
            HashMap reasonExport = null;
            HashMap licExpCode = null;
            HashMap bondCode = null;
            HashMap modeTransport = null;
            AascIntlHeaderInfo intlHeaderBean = null;
            AascIntlInfo IntlInfoBean = null;
            AascAddressInfo soldToAddressBean = null;
            AascAddressInfo sedAddressBean = null;
            AascAddressInfo forwardAgentBean=null;
            AascAddressInfo intermediateConsigneeBean=null;
            AascAddressInfo addressInfoBean = null;
            AascIntlCommodityInfo commInfoBean = null;
            String orderNumber = "";
            String shipStatusFlag = "";
            String shipFlagStr = request.getParameter("shipFlagStr");
            if("".equalsIgnoreCase(shipFlagStr) && shipFlagStr == null){
                shipFlagStr = "N";
            }
            String intlSaveFlag = request.getParameter("intlSaveFlag");
            logger.info("intlSaveFlag = "+intlSaveFlag);
            if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
                intlSaveFlag = "N";
            }
            String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
            session.removeAttribute("intlCommActionFlag");
            if(intlCommActionFlag == null && "".equalsIgnoreCase(intlCommActionFlag)){
                intlCommActionFlag = "N";
            }
            String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
            logger.info("intlSaveActionFlag = "+intlSaveActionFlag);
            session.removeAttribute("intlSaveActionFlag");
            if(intlSaveActionFlag == null && "".equalsIgnoreCase(intlSaveActionFlag)){
                intlSaveActionFlag = "N";
            }
            
            orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
            shipStatusFlag = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();
                    
            if(!"Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    //commItems = aascIntlShipmentsDAO.getIntlCommodityLineItems(clientId, locationId);
                    intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    if("N".equalsIgnoreCase(intlSaveFlag) && ("N".equalsIgnoreCase(intlSaveActionFlag) && "N".equalsIgnoreCase(intlCommActionFlag)))
                    {
                        aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                    }
                }catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }
                    
            if("Y".equalsIgnoreCase(shipFlagStr))
            {
                try{
                    IntlInfoBean = aascIntlShipmentsDAO.getIntlDetails(orderNumber, clientId, locationId);
                    intlHeaderBean = IntlInfoBean.getIntlHeaderInfo();
                }
                catch(Exception e){
                    logger.severe("Data is not entered in the International Shipments window "+e.getMessage());
                }
            }else
            {
                if("Y".equalsIgnoreCase(intlSaveFlag) || "Y".equalsIgnoreCase(intlSaveActionFlag))
                {
                    try
                    {
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    }catch(Exception e){
                        logger.severe("in exception "+e.getMessage());
                    }
                    if(intlHeaderBean!=null){
                        if(intlHeaderBean.getCarrierCode()==carrierCode )
                        {
//                            intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        else
                        {
//                            aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                            intlHeaderBean=null;
                        }
                    }
                      
                }
            }
            if(intlHeaderBean == null)
            {
                intlHeaderBean = new AascIntlHeaderInfo();
                soldToAddressBean = new AascAddressInfo();
                sedAddressBean = new AascAddressInfo();
                forwardAgentBean = new AascAddressInfo();
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            soldToAddressBean = intlHeaderBean.getSoldToAddressInfo();
            if(soldToAddressBean == null)
            {
                soldToAddressBean = new AascAddressInfo();
            }
                    
            sedAddressBean = intlHeaderBean.getSedAddressInfo();
            if(sedAddressBean == null)
            {
                sedAddressBean = new AascAddressInfo();
            }
                   
            forwardAgentBean = intlHeaderBean.getForwardAgentInfo();
            if(forwardAgentBean == null)
            {
                forwardAgentBean = new AascAddressInfo();
            }
                    
            intermediateConsigneeBean = intlHeaderBean.getIntermediateConsigneeInfo();
            if(intermediateConsigneeBean == null)
            {
                intermediateConsigneeBean = new AascAddressInfo();
            }
                
            if(("".equalsIgnoreCase(intlHeaderBean.getOrderNumber()) || intlHeaderBean.getOrderNumber() == null) && !"Y".equalsIgnoreCase(shipFlagStr)){
                intlHeaderBean = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
            }
            commInfoBean = (AascIntlCommodityInfo)session.getAttribute("CommodityLineValue");
            session.removeAttribute("CommodityLineValue");
         
            if(commInfoBean == null){
                commInfoBean = new AascIntlCommodityInfo();
                addressInfoBean = new AascAddressInfo();
            }
             
            addressInfoBean = commInfoBean.getAascAddressInfo();   
            if(addressInfoBean == null)
            {
                addressInfoBean = new AascAddressInfo();
            }
            // Added below if conditions to set Invoice Currency Code and Currency Code as "US Dollar" by default.
            if("".equalsIgnoreCase(intlHeaderBean.getInvoiceCurrencyCode()) || intlHeaderBean.getInvoiceCurrencyCode() == null){
                intlHeaderBean.setInvoiceCurrencyCode("USD");
            }
            if("".equalsIgnoreCase(intlHeaderBean.getIntlCurrencyCode()) || intlHeaderBean.getIntlCurrencyCode() == null){
                intlHeaderBean.setIntlCurrencyCode("USD");
            }
            
            aascIntlDataBean.setIntlInfoBean(IntlInfoBean);
            aascIntlDataBean.setIntlHeaderBean(intlHeaderBean); 
            aascIntlDataBean.setAascCommodityBean(commInfoBean);
            
            AascIntlHeaderInfo checkStatus = (AascIntlHeaderInfo) session.getAttribute("checkStatus");
            AascIntlHeaderInfo checkFormStatus =null;
                  
            logger.info("Commercial invoice::"+intlHeaderBean.getCommercialInv());
            logger.info("SED::"+intlHeaderBean.getShippersExportDecl());
            logger.info("USCO::"+intlHeaderBean.getUsCertOrigin());
            logger.info("NAFTA::"+intlHeaderBean.getNaftaCertOrigin());
            logger.info("InvoiceValue::"+intlHeaderBean.getInvoiceValue());
                        
            if(checkStatus != null){
                checkFormStatus  = checkStatus;
            }
            else
            {
                checkFormStatus=intlHeaderBean;
            }
            
            String commercialInvChecked = "";
            
            if("Y".equalsIgnoreCase(checkFormStatus.getCommercialInv()))
            {
                commercialInvChecked = "true";
            }
            logger.info("Commercial Invoice chk : "+commercialInvChecked);
            aascIntlDataBean.setCommercialInvChecked(commercialInvChecked);
            
            String usCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getUsCertOrigin()))
            {
                usCertOrigin = "true";
            }
            logger.info("US Cer Origin chk : "+usCertOrigin);
            aascIntlDataBean.setUsCertOrigin(usCertOrigin);
            
            String naftaCertOrigin = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getNaftaCertOrigin()))
            {
                naftaCertOrigin = "true";
            }
            logger.info("Nafta Cer Origin chk : "+naftaCertOrigin);
            aascIntlDataBean.setNaftaCertOrigin(naftaCertOrigin);
            
            String shippersExportDecl = "";
            if("Y".equalsIgnoreCase(checkFormStatus.getShippersExportDecl()))
            {
                shippersExportDecl = "true";
            }
            logger.info("SED chk : "+shippersExportDecl);
            aascIntlDataBean.setShippersExportDecl(shippersExportDecl);
            
            Map countryHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCountryLookups());
            Map currencyCodeHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCurrencyCodeLookups());
            sbUOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"SBUOM");
            aascIntlDataBean.setSbUOM(sbUOM);
            exportType = aascIntlShipmentsDAO.getUpsLookUpValues(100,"EXT");
            aascIntlDataBean.setExportType(exportType);
            rvcCalMthd = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RVC");
            aascIntlDataBean.setRvcCalMthd(rvcCalMthd);
            producer = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PROD");
            aascIntlDataBean.setProducer(producer);
            preferenceCriteria = aascIntlShipmentsDAO.getUpsLookUpValues(100,"PREF_CRI");
            aascIntlDataBean.setPreferenceCriteria(preferenceCriteria);
            UOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"UNIT_OF_MEASURE");
            aascIntlDataBean.setUOM(UOM);
            TOM = aascIntlShipmentsDAO.getUpsLookUpValues(100,"TOS");
            aascIntlDataBean.setTOM(TOM);
            reasonExport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"RFE");
            aascIntlDataBean.setReasonExport(reasonExport);
            licExpCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"LEC");
            aascIntlDataBean.setLicExpCode(licExpCode);
            bondCode = aascIntlShipmentsDAO.getUpsLookUpValues(100,"BCODE");
            aascIntlDataBean.setBondCode(bondCode);
            modeTransport = aascIntlShipmentsDAO.getUpsLookUpValues(100,"MOT");
            aascIntlDataBean.setModeTransport(modeTransport);
            /*String countryCode = "";
            String countryName = "";
            AascShipMethodInfo aascCountryDetails = null;
            Iterator countryListItr = countryList.iterator(); 
            HashMap countries = new HashMap();
            while(countryListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)countryListItr.next();
                countryCode = aascCountryDetails.getCountryCode();
                countryName = aascCountryDetails.getCountryName();
                countries.put(countryCode, countryName);
            }*/
            aascIntlDataBean.setCountryList(countryHashMap);
            /*String invoiceCurrencyCodeStr = "";
            String invoiceCurrencyNameStr = "";
            Iterator invoiceCurrencyDetailListItr = currencyCodeList.iterator();
            HashMap currencies = new HashMap();
            while(invoiceCurrencyDetailListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)invoiceCurrencyDetailListItr.next();
                invoiceCurrencyCodeStr = aascCountryDetails.getCurrencyCode();
                invoiceCurrencyNameStr = aascCountryDetails.getCurrencyName();
                currencies.put(invoiceCurrencyCodeStr, invoiceCurrencyNameStr);
            }*/
            aascIntlDataBean.setCurrencyCodeList(currencyCodeHashMap);
            
        } catch(Exception e){
            logger.severe("Got Exception e "+e.getMessage() );
        }
        return aascIntlDataBean;
    }

    /** This method is used to load all required data from database when UPS International Shipping page is opened like country codes, commodity names, importer names and broker names etc.
     * @param request
     * @param session
     * @return aascIntlDataBean
     */
    public AascIntlDataBean fedExInternationalAction(HttpServletRequest request, HttpSession session){
       AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
       try{
            aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();
            AascIntlInfo IntlInfoBean = null;
            AascIntlCommodityInfo commInfoBean = null;
//            AascIntlHeaderInfo aascIntlHeaderInfo = null;
//            AascIntlCommodityInfo aascIntlCommodityInfo = null;
//            LinkedList cmList = new LinkedList();
//            ArrayList<String> commItems = null;
            HashMap impNames = null;
            HashMap brokerNames = null;
//            LinkedList countryList = null;

            String shipFlagStr = request.getParameter("shipFlagStr");
            if("".equalsIgnoreCase(shipFlagStr) && shipFlagStr == null){
                shipFlagStr = "N";
            }
            
            String intlSaveFlag = request.getParameter("intlSaveFlag");
            if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
                intlSaveFlag = "N";
            }
            
            int  carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
            
            String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
            session.removeAttribute("intlCommActionFlag");
            if("".equalsIgnoreCase(intlCommActionFlag) && intlCommActionFlag == null){
                intlCommActionFlag = "N";
            }
            
            String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
            session.removeAttribute("intlSaveActionFlag");
            if("".equalsIgnoreCase(intlSaveActionFlag) && intlSaveActionFlag == null){
                intlSaveActionFlag = "N";
            }
            
            int clientId = (Integer)session.getAttribute("client_id");
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            logger.info("locationId === "+locationId);
            String accountNumberStr =  (String)session.getAttribute("accountNumber");
            session.removeAttribute("accountNumber");
            String payMethodStr = (String)session.getAttribute("payMethod");
            session.removeAttribute("payMethod");
//            String cCodeStr = (String)session.getAttribute("cCode");
//            session.removeAttribute("cCode");
            String accountNumber = request.getParameter("accountNumber");
            String payMethod = request.getParameter("payMethod");
            String payerType = "";
                        
            if(accountNumber==null){
                accountNumber=accountNumberStr;
            }
                   
            Map countryHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCountryLookups());
            /*String countryCode = "";
            String countryName = "";
            AascShipMethodInfo aascCountryDetails = null;
            Iterator countryListItr = countryList.iterator(); 
            HashMap countries = new HashMap();
            while(countryListItr.hasNext()){
                aascCountryDetails = (AascShipMethodInfo)countryListItr.next();
                countryCode = aascCountryDetails.getCountryCode();
                countryName = aascCountryDetails.getCountryName();
                countries.put(countryCode, countryName);
            }*/
            
            String orderNumber ="";
            String shipStatus = null;
                  
            orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
            shipStatus = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();//Added in 6.71
                   
            AascIntlHeaderInfo intlHeaderBean = null;
            impNames = aascIntlShipmentsDAO.getIntlImporterNames(clientId, locationId);
            brokerNames = aascIntlShipmentsDAO.getIntlBrokerNames(clientId, locationId);
            if(!"Y".equalsIgnoreCase(shipFlagStr))
            {
//                commItems = aascIntlShipmentsDAO.getIntlCommodityLineItems(clientId, locationId);
                if("N".equalsIgnoreCase(intlSaveFlag) && ("N".equalsIgnoreCase(intlSaveActionFlag) && "N".equalsIgnoreCase(intlCommActionFlag)))
                {
                    aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                }
            }
                   
            if("Y".equalsIgnoreCase(shipFlagStr))
            {
                logger.info("In shipFlagStr is Y");
                IntlInfoBean = aascIntlShipmentsDAO.getIntlDetails(orderNumber, clientId, locationId);
                intlHeaderBean = IntlInfoBean.getIntlHeaderInfo();
                logger.info("getDeclarationStmt = "+intlHeaderBean.getDeclarationStmt());
            }else
            {
                logger.info("In shipFlagStr is not Y");
                if("Y".equalsIgnoreCase(intlSaveFlag) || "Y".equalsIgnoreCase(intlSaveActionFlag))
                {   
                    logger.info("Entered if intlHeaderBean!=null");                    
                    try
                    {
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    }catch(Exception e){
                        logger.severe("in exception "+e.getMessage());
                    }
//                    System.out.println("intlheaer bean::::"+intlHeaderBean);
                    if(intlHeaderBean!=null){
//                        System.out.println("intlheaer bean::::"+carrierCode);
//                        System.out.println("intlheaer bean::::     ::::"+intlHeaderBean.getCarrierCode());
                        if(intlHeaderBean.getCarrierCode()==carrierCode)
                        {
    //                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        else
                        {
//                            aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                            intlHeaderBean=null;
                        }
                    }
                }
                else{
//                System.out.println("in else 1091");
                    try{
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
//                        System.out.println("Intl header bean::1094::"+intlHeaderBean);
//                        System.out.println("intlheaer bean::1095::"+carrierCode);
//                        System.out.println("intlheaer bean::::   1096  ::::"+intlHeaderBean.getCarrierCode());
                        if(intlHeaderBean.getCarrierCode()==carrierCode)
                        {
                            intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                       

                    }catch(Exception e)
                    {
                        logger.severe("Exception e = "+e.getMessage());
                    }
                    
                    if(intlHeaderBean == null){
                        intlHeaderBean = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
                    }else{
                    AascIntlHeaderInfo intlHeaderBean2 = null;
                        intlHeaderBean2 = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
//                        System.out.println("intlHeaderBean2::::1108:::"+intlHeaderBean2);
                        intlHeaderBean.setIntlComments(intlHeaderBean2.getIntlComments());
                        intlHeaderBean.setDeclarationStmt(intlHeaderBean2.getDeclarationStmt());
                        intlHeaderBean.setIntlSedNumber(intlHeaderBean2.getIntlSedNumber());
                    }
//                    System.out.println("broker nakeKKKK::::::::::"+intlHeaderBean.getBrokerCity());
                }
            }
            if(intlHeaderBean == null){
                intlHeaderBean = new AascIntlHeaderInfo();
            }
                   
            commInfoBean = (AascIntlCommodityInfo)session.getAttribute("CommodityLineValue");
            session.removeAttribute("CommodityLineValue");
            if(commInfoBean == null)
                commInfoBean = new AascIntlCommodityInfo();
                
            String customsValueStr = nullStrToSpc(commInfoBean.getCustomsValue());
            String quantityValStr = nullStrToSpc(commInfoBean.getQuantity());
            double unitprice = 0.0;
            if(customsValueStr.length() > 0 && quantityValStr.length() > 0){
                double customsValueDouble = Double.parseDouble(commInfoBean.getCustomsValue());
                double quantityDouble = Double.parseDouble(commInfoBean.getQuantity());
                unitprice = customsValueDouble/quantityDouble;
            }
            
            String generateCI = "";
            if("Y".equalsIgnoreCase(intlHeaderBean.getGenerateCI()))
            {
                generateCI = "true";
            }
            logger.info("Commercial Invoice chk : "+generateCI);
            
            if("SENDER".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "PP".equalsIgnoreCase(payMethod) || "SENDER".equalsIgnoreCase(payMethodStr)){
                payerType = "SENDER";
            }
            
            if("RECIPIENT".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "CG".equalsIgnoreCase(payMethod) || "RECIPIENT".equalsIgnoreCase(payMethodStr)){
                payerType = "RECIPIENT";
            }
            
            if("THIRDPARTY".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "TP".equalsIgnoreCase(payMethod) || "THIRDPARTY".equalsIgnoreCase(payMethodStr)){
                payerType = "THIRDPARTY";
            }
            
            aascIntlDataBean.setUnitprice(unitprice);
            aascIntlDataBean.setPayerType(payerType);
            aascIntlDataBean.setGenerateCI(generateCI);
            aascIntlDataBean.setIntlInfoBean(IntlInfoBean);
            aascIntlDataBean.setIntlHeaderBean(intlHeaderBean); 
            aascIntlDataBean.setAascCommodityBean(commInfoBean);
            aascIntlDataBean.setCountryList(countryHashMap);
            aascIntlDataBean.setImpNames(impNames);
            aascIntlDataBean.setBrokerNames(brokerNames);
            
        
        } catch(Exception e){
            logger.severe("Got Exception e "+e.getMessage() );
//            e.printStackTrace();
        }
        
        return aascIntlDataBean;
   }

    /** This method is used to get importer details in shipexec international address page.
     * @return aascIntlDataBean
     */
    public AascIntlDataBean shipExecIntlAddressAction(){
        AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
        try{
             aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();
             HashMap impNames = null;
             impNames = aascIntlShipmentsDAO.getIntlImporterNames(clientId, locationId);
             aascIntlDataBean.setImpNames(impNames);
         
         } catch(Exception e){
             logger.severe("Got Exception e "+e.getMessage() );
    //             e.printStackTrace();
         }
         return aascIntlDataBean;
    }
    
    /** This method is used to get importer details in ups international address page.
     * @return aascIntlDataBean
     */
    public AascIntlDataBean upsIntlAddressAction(){
        AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
        try{
             aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();
             HashMap impNames = null;
             impNames = aascIntlShipmentsDAO.getIntlImporterNames(clientId, locationId);
             aascIntlDataBean.setImpNames(impNames);
         
         } catch(Exception e){
             logger.severe("Got Exception e "+e.getMessage() );
//             e.printStackTrace();
         }
         return aascIntlDataBean;
    }
    
    /** This method is used to load all required data from database when DHL International Shipping page is opened like country codes, commodity names etc.
     * @param request
     * @param session
     * @return aascIntlDataBean
     */
    public AascIntlDataBean dhlInternationalAction(HttpServletRequest request, HttpSession session){
       AascIntlDataBean aascIntlDataBean = new AascIntlDataBean();
       try{
            aascIntlShipmentsDAO = aascDAOFactory.getAascIntlShipmentsDAO();
            AascIntlInfo IntlInfoBean = null;
            AascIntlCommodityInfo commInfoBean = null;
            HashMap impNames = null;
            HashMap brokerNames = null;

            String shipFlagStr = request.getParameter("shipFlagStr");
            if("".equalsIgnoreCase(shipFlagStr) && shipFlagStr == null){
                shipFlagStr = "N";
            }
            
            String intlSaveFlag = request.getParameter("intlSaveFlag");
            if(intlSaveFlag == null && "".equalsIgnoreCase(intlSaveFlag)){
                intlSaveFlag = "N";
            }
            
            int  carrierCode = Integer.parseInt(request.getParameter("carrierCode"));
            
            String intlCommActionFlag = (String)session.getAttribute("intlCommActionFlag");
            session.removeAttribute("intlCommActionFlag");
            if("".equalsIgnoreCase(intlCommActionFlag) && intlCommActionFlag == null){
                intlCommActionFlag = "N";
            }
            
            String intlSaveActionFlag = (String)session.getAttribute("intlSaveActionFlag");
            session.removeAttribute("intlSaveActionFlag");
            if("".equalsIgnoreCase(intlSaveActionFlag) && intlSaveActionFlag == null){
                intlSaveActionFlag = "N";
            }
            
            int clientId = (Integer)session.getAttribute("client_id");
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            logger.info("locationId === "+locationId);
            String accountNumberStr =  (String)session.getAttribute("accountNumber");
            session.removeAttribute("accountNumber");
            String payMethodStr = (String)session.getAttribute("payMethod");
            session.removeAttribute("payMethod");
            String accountNumber = request.getParameter("AccNumber");
            String payMethod = request.getParameter("payMethod");
            String payerType = "";
                        
            if(accountNumber==null){
                accountNumber=accountNumberStr;
            }
            
            //Shiva added below code for DHL Terms of Trade
             String termsOfTrade="";
            try{
                if(carrierCode ==114)
                {
                    termsOfTrade = request.getParameter("TermsOfTrade");
                }
            }catch(Exception e){
                termsOfTrade = "";
            }
            aascIntlHeaderInfo.setTermsOfTrade(termsOfTrade);
            //Shiva code end
                   
            Map countryHashMap = 
                new TreeMap((HashMap<String,String>)aascIntlShipmentsDAO.getCountryLookups());
            String orderNumber ="";
            String shipStatus = null;
                  
            orderNumber = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getOrderNumber();
            shipStatus = (((AascShipmentOrderInfo)session.getAttribute("AascShipmentOrderInfo")).getShipmentHeaderInfo()).getShipFlag();//Added in 6.71
                   
            AascIntlHeaderInfo intlHeaderBean = null;
            if(!"Y".equalsIgnoreCase(shipFlagStr))
            {

                if("N".equalsIgnoreCase(intlSaveFlag) && ("N".equalsIgnoreCase(intlSaveActionFlag) && "N".equalsIgnoreCase(intlCommActionFlag)))
                {
                    aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
                }
            }
                   
            if("Y".equalsIgnoreCase(shipFlagStr))
            {
                logger.info("In shipFlagStr is Y");
                IntlInfoBean = aascIntlShipmentsDAO.getIntlDetails(orderNumber, clientId, locationId);
                intlHeaderBean = IntlInfoBean.getIntlHeaderInfo();
                logger.info("getDeclarationStmt = "+intlHeaderBean.getDeclarationStmt());
            }else
            {
                logger.info("In shipFlagStr is not Y");
                if("Y".equalsIgnoreCase(intlSaveFlag) || "Y".equalsIgnoreCase(intlSaveActionFlag))
                {   
                    logger.info("Entered if intlHeaderBean!=null");                    
                    try
                    {
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                    }catch(Exception e){
                        logger.severe("in exception "+e.getMessage());
                    }
                    if(intlHeaderBean!=null){
                        if(intlHeaderBean.getCarrierCode()==carrierCode)
                        {
    //                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        else
                        {
//                            aascIntlShipmentsDAO.deleteIntlShipments(orderNumber, clientId, locationId);
//                            intlHeaderBean=null;
                        }
                    }
                }
                else{
                    try{
                        intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        if(intlHeaderBean.getCarrierCode()==carrierCode)
                        {
                            intlHeaderBean = aascIntlShipmentsDAO.getIntlHeaderDetails(orderNumber, clientId, locationId);
                        }
                        

                    }catch(Exception e)
                    {
                        logger.severe("Exception e = "+e.getMessage());
                        e.printStackTrace();
                    }
                    intlHeaderBean = aascIntlShipmentsDAO.getCustomIntlHeaderDetails(clientId, locationId);
                }
            }
            if(intlHeaderBean == null){
                intlHeaderBean = new AascIntlHeaderInfo();
            }
                   
            commInfoBean = (AascIntlCommodityInfo)session.getAttribute("CommodityLineValue");
            session.removeAttribute("CommodityLineValue");
            if(commInfoBean == null)
                commInfoBean = new AascIntlCommodityInfo();
                
            String customsValueStr = nullStrToSpc(commInfoBean.getCustomsValue());
            String quantityValStr = nullStrToSpc(commInfoBean.getQuantity());
            double unitprice = 0.0;
            if(customsValueStr.length() > 0 && quantityValStr.length() > 0){
                double customsValueDouble = Double.parseDouble(commInfoBean.getCustomsValue());
                double quantityDouble = Double.parseDouble(commInfoBean.getQuantity());
                unitprice = customsValueDouble/quantityDouble;
            }
            
            String generateCI = "";
            if("Y".equalsIgnoreCase(intlHeaderBean.getGenerateCI()))
            {
                generateCI = "true";
            }
            logger.info("Commercial Invoice chk : "+generateCI);
            
            if("SENDER".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "PP".equalsIgnoreCase(payMethod) || "SENDER".equalsIgnoreCase(payMethodStr)){
                payerType = "SENDER";
            }
            
            if("RECIPIENT".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "CG".equalsIgnoreCase(payMethod) || "RECIPIENT".equalsIgnoreCase(payMethodStr)){
                payerType = "RECIPIENT";
            }
            
            if("THIRDPARTY".equalsIgnoreCase(intlHeaderBean.getIntlPayerType()) || "TP".equalsIgnoreCase(payMethod) || "THIRDPARTY".equalsIgnoreCase(payMethodStr)){
                payerType = "THIRDPARTY";
            }
                        
           
            aascIntlDataBean.setUnitprice(unitprice);
            aascIntlDataBean.setPayerType(payerType);
            aascIntlDataBean.setGenerateCI(generateCI);
            aascIntlDataBean.setIntlInfoBean(IntlInfoBean);
            aascIntlDataBean.setIntlHeaderBean(intlHeaderBean); 
            aascIntlDataBean.setAascCommodityBean(commInfoBean);
            aascIntlDataBean.setCountryList(countryHashMap);

        
        } catch(Exception e){
            logger.severe("Got Exception e "+e.getMessage() );
    //            e.printStackTrace();
        }
        return aascIntlDataBean;
    }
    

    /** This method is called when Save button international page is clicked. This method get all header details from international page and save in the respective database table.
     * @param request
     * @param session
     * @return String
     */
    public String saveAction(HttpServletRequest request,HttpSession session){
        try{
            aascIntlHeaderInfo.setOrderNumber(orderNumber);
            aascIntlHeaderInfo.setShipmentId(0);
            aascIntlHeaderInfo.setVoidFlag("N");

            String intPayerType = "";
            try {
                intPayerType = request.getParameter("payerType").trim();
                logger.info("intPayerType: " + intPayerType);
            } catch (Exception e) {
                intPayerType = "";
            }
            aascIntlHeaderInfo.setIntlPayerType(intPayerType);

            String intAccNumber = "";
            try {
                intAccNumber = request.getParameter("AccNumber").trim();
                logger.info("intAccNumber: " + intAccNumber);
            } catch (Exception e) {
                intAccNumber = "";
            }
            aascIntlHeaderInfo.setIntlAccountNumber(intAccNumber);

            String intcountryCode = "";
            try {
                intcountryCode = request.getParameter("countryCode").trim();
                logger.info("intcountryCode: " + intcountryCode);
            } catch (Exception e) {
                intcountryCode = "";
            }
            aascIntlHeaderInfo.setIntlCountryCode(intcountryCode);

            String intTermsOfSale = "";
            try {
                intTermsOfSale = request.getParameter("TermsOfSale").trim();
                logger.info("intTermsOfSale: " + intTermsOfSale);
            } catch (Exception e) {
                intTermsOfSale = "";
            }
            aascIntlHeaderInfo.setIntlTermsOfSale(intTermsOfSale);

            String intTotalCustomsValue = "";
            try {
                intTotalCustomsValue = request.getParameter("TotalCustomsValue");
                logger.info("intTotalCustomsValue: " + intTotalCustomsValue);
            } catch (Exception e) {
                intTotalCustomsValue = "";
            }
            aascIntlHeaderInfo.setIntlTotalCustomsValue(intTotalCustomsValue);

            //Shiva added below code for DHL Terms of Trade
             String termsOfTrade="";
            try{
                if(carrierCode ==114)
                {
                    termsOfTrade = request.getParameter("TermsOfTrade");
                }
            }catch(Exception e){
                termsOfTrade = "";
            }
            aascIntlHeaderInfo.setTermsOfTrade(termsOfTrade);
            //Shiva code end


            double intlFCharge = 0.0;
            try {
                String intFreightCharge = request.getParameter("FreightCharge").trim();         // For FedEx
                intlFCharge = Double.parseDouble(intFreightCharge);
            } catch (Exception e) {
                try {
                    String intFreightCharge = request.getParameter("FreightCharges").trim();    // For UPS
                    intlFCharge = Double.parseDouble(intFreightCharge);
                    logger.info("intFreightCharge IN CATCH :" + intlFCharge);
                } catch (Exception e1) {
                    intlFCharge = 0.0;
                }
            }
            logger.info("intFreightCharge: " + intlFCharge);
            aascIntlHeaderInfo.setIntlFreightCharge(intlFCharge);
            
            double intlICharge = 0.0;
            try {
                String intInsuranceCharge = request.getParameter("InsuranceCharge").trim();         // For FedEx
                intlICharge = Double.parseDouble(intInsuranceCharge);
            } catch (Exception e) {
                try {
                    String intInsuranceCharge = request.getParameter("InsuranceCharges").trim();    // For UPS
                    intlICharge = Double.parseDouble(intInsuranceCharge);
                    logger.info("intInsuranceCharge in catch: " + intInsuranceCharge);
                } catch (Exception e1) {
                    intlICharge = 0.0;
                }
            }
            logger.info("intInsuranceCharge: " + intlICharge);
            aascIntlHeaderInfo.setIntlInsuranceCharge(intlICharge);

            double intlTMCharge = 0.0;
            try {
                String intTaxesOrMiscellaneousCharge = request.getParameter("TaxesOrMiscellaneousCharge").trim();   // For FedEx
                intlTMCharge = Double.parseDouble(intTaxesOrMiscellaneousCharge);
            } catch (Exception e1) {
                try {
                    String intTaxesOrMiscellaneousCharge = request.getParameter("OtherCharges").trim();             // For UPS
                    intlTMCharge = Double.parseDouble(intTaxesOrMiscellaneousCharge);
                    logger.info("intTaxesOrMiscellaneousCharge in catch: " + intTaxesOrMiscellaneousCharge);
                } catch (Exception e) {
                    intlTMCharge = 0.0;
                }
            }
            logger.info("intTaxesOrMiscellaneousCharge: " + intlTMCharge);
            aascIntlHeaderInfo.setIntlTaxMiscellaneousCharge(intlTMCharge);

            String intPurpose = "";
            try {
                intPurpose = request.getParameter("Purpose").trim();
                logger.info("intPurpose: " + intPurpose);
            } catch (Exception e) {
                intPurpose = "";
            }
            aascIntlHeaderInfo.setIntlPurpose(intPurpose);

            String intSenderTINOrDUNS = "";
            try {
                intSenderTINOrDUNS = request.getParameter("SenderTINOrDUNS").trim();
                logger.info("intSenderTINOrDUNS: " + intSenderTINOrDUNS);
            } catch (Exception e) {
                intSenderTINOrDUNS = "";
            }
            aascIntlHeaderInfo.setIntlSedNumber(intSenderTINOrDUNS);

            String intSenderTINOrDUNSType = "";
            try {
                intSenderTINOrDUNSType = request.getParameter("SenderTINOrDUNSType").trim();
                logger.info("intSenderTINOrDUNSType: " + intSenderTINOrDUNSType);
            } catch (Exception e) {
                intSenderTINOrDUNSType = "";
            }
            aascIntlHeaderInfo.setIntlSedType(intSenderTINOrDUNSType);

            String intExempNo = "";
            try {
                intExempNo = request.getParameter("AESOrFTSRExemptionNumber").trim();
                logger.info("AESOrFTSRExemptionNumber: " + intExempNo);
            } catch (Exception e) {
                intExempNo = "";
            }
            aascIntlHeaderInfo.setIntlExemptionNumber(intExempNo);

            double intlDiscount = 0.0;
            try {
                String intlDiscountStr = request.getParameter("Discount").trim();
                intlDiscount = Double.parseDouble(intlDiscountStr);
            } catch (Exception e) {
                intlDiscount = 0.0;
            }
            aascIntlHeaderInfo.setIntlDiscount(intlDiscount);

            String intlPurchaseOrderNumber = "";
            String intlDeclarationStmt = "";
            String intlShipFromTaxId = "";
            String intlShipToTaxId = "";
            String intlExportDate = "";
            String intlExportingCarrier = "";
            String intlBlanketPeriodBeginDate = "";
            String intlBlanketPeriodEndDate = "";
            String intlCurrencyCode = "";
            String intlInvoiceDate;
            try {
                intlDeclarationStmt = request.getParameter("declarationStmt").trim();
            } catch (Exception e) {
                intlDeclarationStmt = "";
            }
            
            try {
                intlPurchaseOrderNumber = request.getParameter("PuchaseOrderNumber").trim();
            } catch (Exception e) {
                intlPurchaseOrderNumber = "";
            }

            try {
                intlInvoiceDate = request.getParameter("InvoiceDate").trim();
                intlInvoiceDate = dateConverter(intlInvoiceDate);
            } catch (Exception e) {
                intlInvoiceDate = "";
            }
            
            try {
                intlCurrencyCode = request.getParameter("CurrencyCode").trim();
            } catch (Exception e) {
                intlCurrencyCode = "";
            }

            try {
                intlShipToTaxId = request.getParameter("ShipToTaxID").trim();
            } catch (Exception e) {
                intlShipToTaxId = "";
            }
            
            try {
                intlShipFromTaxId = request.getParameter("ShipFromTaxID").trim();
            } catch (Exception e) {
                intlShipFromTaxId = "";
            }
            
            try {
                intlExportDate = request.getParameter("UExportDate").trim();
                intlExportDate = dateConverter(intlExportDate);
            } catch (Exception e) {
                intlExportDate = "";
            }
            
            try {
                intlExportingCarrier = request.getParameter("UExportingCarrier").trim();
            } catch (Exception e) {
                intlExportingCarrier = "";
            }
            
            try {
                intlBlanketPeriodBeginDate = request.getParameter("BlanketPeriodBeginDate").trim();
                intlBlanketPeriodBeginDate = dateConverter(intlBlanketPeriodBeginDate);
            } catch (Exception e) {
                intlBlanketPeriodBeginDate = "";
            }
            
            try {
                intlBlanketPeriodEndDate = request.getParameter("BlanketPeriodEndDate").trim();
                intlBlanketPeriodEndDate = dateConverter(intlBlanketPeriodEndDate);
            } catch (Exception e) {
                intlBlanketPeriodEndDate = "";
            }
            aascIntlHeaderInfo.setIntlShipToTaxId(intlShipToTaxId);
            aascIntlHeaderInfo.setIntlShipFromTaxId(intlShipFromTaxId);
            aascIntlHeaderInfo.setIntlDeclarationStmt(intlDeclarationStmt);
            aascIntlHeaderInfo.setIntlDeclarationStmt(intlDeclarationStmt);
            aascIntlHeaderInfo.setIntlPurchaseOrderNumber(intlPurchaseOrderNumber);
            aascIntlHeaderInfo.setIntlExportCarrier(intlExportingCarrier);
            aascIntlHeaderInfo.setIntlCurrencyCode(intlCurrencyCode);

            aascIntlHeaderInfo.setIntlInvoiceDate(intlInvoiceDate);
            aascIntlHeaderInfo.setIntlExportDate(intlExportDate);
            aascIntlHeaderInfo.setIntlBlanketPeriodBeginDate(intlBlanketPeriodBeginDate);
            aascIntlHeaderInfo.setIntlBlanketPeriodEndDate(intlBlanketPeriodEndDate);

            String sedPointOfOrigin = "";
            String sedModeOfTransport = "";
            String sedExportDate = "";
            String sedExportingCarrier = "";
            String sedInBondCode = "";
            String sedLicenceExceptionCode = "";
            String sedECCN = "";
            String sedLicenceNumber = "";
            String sedLicenceDate = "";
            
            try {
                sedPointOfOrigin = request.getParameter("PointOfOrigin").trim();
            } catch (Exception e) {
                sedPointOfOrigin = "";
            }
           
            try {
                sedModeOfTransport = request.getParameter("ModeOfTransport").trim();
            } catch (Exception e) {
                sedModeOfTransport = "";
            }
            
            try {
                sedExportDate = request.getParameter("SExportDate").trim();
                sedExportDate = dateConverter(sedExportDate);
            } catch (Exception e) {
                sedExportDate = "";
            }
           
            try {
                sedExportingCarrier = request.getParameter("SExportingCarrier").trim();
            } catch (Exception e) {
                sedExportingCarrier = "";
            }
           
            try {
                sedInBondCode = request.getParameter("InBondCode").trim();
            } catch (Exception e) {
                sedInBondCode = "";
            }
           
            try {
                sedLicenceExceptionCode = request.getParameter("ExceptionCodes").trim();
            } catch (Exception e) {
                sedLicenceExceptionCode = "";
            }
           
            try {
                sedECCN = request.getParameter("EccnNumber").trim();
            } catch (Exception e) {
                sedECCN = "";
            }
           
            try {
                sedLicenceNumber = request.getParameter("LicenseNumber").trim();
            } catch (Exception e) {
                sedLicenceNumber = "";
            }
           
            try {
                sedLicenceDate = request.getParameter("LicenseDate").trim();
                sedLicenceDate = dateConverter(sedLicenceDate);
            } catch (Exception e) {
                sedLicenceDate = "";
            }
            aascIntlHeaderInfo.setSedPointOfOrigin(sedPointOfOrigin);
            aascIntlHeaderInfo.setSedModeOfTransport(sedModeOfTransport);
            aascIntlHeaderInfo.setSedExportDate(sedExportDate);
            aascIntlHeaderInfo.setSedExportingCarrier(sedExportingCarrier);
            aascIntlHeaderInfo.setSedInBondCode(sedInBondCode);
            aascIntlHeaderInfo.setSedLicenceExceptionCode(sedLicenceExceptionCode);
            aascIntlHeaderInfo.setSedECCN(sedECCN);
            aascIntlHeaderInfo.setSedLicenceNumber(sedLicenceNumber);
            aascIntlHeaderInfo.setSedLicenceDate(sedLicenceDate);

            String sedEntryNumber = "";
            String sedLoadingPier = "";
            String sedPortOfExport = "";
            String sedPortOfUnloading = "";
            String sedCarrierIdentificationCode = "";
            String sedContainerized = "";
            String sedHazardiousMaterial = "";
            String sedRoutedExportTransaction = "";
            
            try {
                sedLoadingPier = request.getParameter("LoadingPier").trim();
            } catch (Exception e) {
                sedLoadingPier = "";
            }
            
            try {
                sedPortOfExport = request.getParameter("PortOfExport").trim();
            } catch (Exception e) {
                sedPortOfExport = "";
            }
            
            try {
                sedPortOfUnloading = request.getParameter("PortOfUnloading").trim();
            } catch (Exception e) {
                sedPortOfUnloading = "";
            }
            
            try {
                sedCarrierIdentificationCode = request.getParameter("CarrierIdentificationCode").trim();
            } catch (Exception e) {
                sedCarrierIdentificationCode = "";
            }
            
            try {
                sedContainerized = request.getParameter("Containerized").trim();
            } catch (Exception e) {
                sedContainerized = "";
            }
            logger.info("sedContainerized IN action class==" + sedContainerized);

            try {
                sedHazardiousMaterial = request.getParameter("HazardousMaterials").trim();
            } catch (Exception e) {
                sedHazardiousMaterial = "";
            }
            
            try {
                sedRoutedExportTransaction = request.getParameter("RoutedExportTransaction").trim();
            } catch (Exception e) {
                sedRoutedExportTransaction = "";
            }
            
            try {
                sedEntryNumber = request.getParameter("EntryNumber").trim();
                logger.info("sedEntryNumber==" + sedEntryNumber);
            } catch (Exception e) {
                sedEntryNumber = "";
            }
            logger.info("sedEntryNumber in action==" + sedEntryNumber);
            aascIntlHeaderInfo.setSedEntryNumber(sedEntryNumber);
            logger.info("sedEntryNumber in from bean==" + aascIntlHeaderInfo.getSedEntryNumber());
                                                          aascIntlHeaderInfo.setSedLoadingPier(sedLoadingPier);
                                                          aascIntlHeaderInfo.setSedPortOfExport(sedPortOfExport);
                                                          aascIntlHeaderInfo.setSedPortOfUnloading(sedPortOfUnloading);
                                                          aascIntlHeaderInfo.setSedCarrierIdentificationCode(sedCarrierIdentificationCode);
                                                          aascIntlHeaderInfo.setSedContainerized(sedContainerized);
                                                          aascIntlHeaderInfo.setSedHazardiousMaterial(sedHazardiousMaterial);
                                                          aascIntlHeaderInfo.setSedRoutedExportTransaction(sedRoutedExportTransaction);

            String sedPartiesToTran = "";
            try {
                sedPartiesToTran = request.getParameter("PartiestoTransaction").trim();
            } catch (Exception e) {
                sedPartiesToTran = "";
            }
            aascIntlHeaderInfo.setSedPartiesToTran(sedPartiesToTran);

            String sedCompanyName = "";
            String sedAddressLine1 = "";
            String sedCity = "";
            String sedStateProvinceCode = "";
            String sedPostalCode = "";
            String sedCountryCode = "";
            
            try {
                sedCompanyName = request.getParameter("CCompanyName");
            } catch (Exception e) {
                sedCompanyName = "";
            }
            
            try {
                sedAddressLine1 = request.getParameter("CAddressLine1");
            } catch (Exception e) {
                sedAddressLine1 = "";
            }
            
            try {
                sedCity = request.getParameter("CCity");
            } catch (Exception e) {
                sedCity = "";
            }
            
            try {
                sedStateProvinceCode = request.getParameter("CStateProvinceCode");
            } catch (Exception e) {
                sedStateProvinceCode = "";
            }
            
            try {
                sedPostalCode = request.getParameter("CPostalCode");
            } catch (Exception e) {
                sedPostalCode = "";
            }
            
            try {
                sedCountryCode = request.getParameter("CCountryCode");
            } catch (Exception e) {
                sedCountryCode = "";
            }
            sedAddressInfo.setCompanyName(sedCompanyName);
            sedAddressInfo.setAddressLine1(sedAddressLine1);
            sedAddressInfo.setCity(sedCity);
            sedAddressInfo.setStateProvinceCode(sedStateProvinceCode);
            sedAddressInfo.setPostalCode(sedPostalCode);
            sedAddressInfo.setCountryCode(sedCountryCode);

            String fAgentCompanyName = "";
            String fAgentAddressLine1 = "";
            String fAgentCity = "";
            String fAgentStateProvinceCode = "";
            String fAgentPostalCode = "";
            String fAgentCountryCode = "";
            String fTaxId = "";
            
            try {
                fAgentCompanyName = request.getParameter("FCompanyName");
            } catch (Exception e) {
                fAgentCompanyName = "";
            }
            
            try {
                fAgentAddressLine1 = request.getParameter("FAddressLine1");
            } catch (Exception e) {
                fAgentAddressLine1 = "";
            }
            
            try {
                fAgentCity = request.getParameter("FCity");
            } catch (Exception e) {
                fAgentCity = "";
            }
            
            try {
                fAgentStateProvinceCode = request.getParameter("FStateProvinceCode");
            } catch (Exception e) {
                fAgentStateProvinceCode = "";
            }
            
            try {
                fAgentPostalCode = request.getParameter("FPostalCode");
            } catch (Exception e) {
                fAgentPostalCode = "";
            }
            
            try {
                fAgentCountryCode = request.getParameter("FCountryCode");
            } catch (Exception e) {
                fAgentCountryCode = "";
            }
            
            try {
                fTaxId = request.getParameter("FTaxIdNum");
            } catch (Exception e) {
                fTaxId = "";
            }

            forwardAgentInfo.setCompanyName(fAgentCompanyName);
            forwardAgentInfo.setAddressLine1(fAgentAddressLine1);
            forwardAgentInfo.setCity(fAgentCity);
            forwardAgentInfo.setStateProvinceCode(fAgentStateProvinceCode);
            forwardAgentInfo.setPostalCode(fAgentPostalCode);
            forwardAgentInfo.setCountryCode(fAgentCountryCode);
            forwardAgentInfo.setTaxId(fTaxId);

            String iConsigneeCompanyName = "";
            String iConsigneeAddressLine1 = "";
            String iConsigneeCity = "";
            String iConsigneeStateProvinceCode = "";
            String iConsigneePostalCode = "";
            String iConsigneeCountryCode = "";
            try {
                iConsigneeCompanyName = request.getParameter("ICCompanyName");
            } catch (Exception e) {
                iConsigneeCompanyName = "";
            }
            
            try {
                iConsigneeAddressLine1 = request.getParameter("ICAddressLine1");
            } catch (Exception e) {
                iConsigneeAddressLine1 = "";
            }
            
            try {
                iConsigneeCity = request.getParameter("ICCity");
            } catch (Exception e) {
                iConsigneeCity = "";
            }
            
            try {
                iConsigneeStateProvinceCode = request.getParameter("ICStateProvinceCode");
            } catch (Exception e) {
                iConsigneeStateProvinceCode = "";
            }
            
            try {
                iConsigneePostalCode = request.getParameter("ICPostalCode");
            } catch (Exception e) {
                iConsigneePostalCode = "";
            }
            
            try {
                iConsigneeCountryCode = request.getParameter("ICCountryCode");
            } catch (Exception e) {
                iConsigneeCountryCode = "";
            }
            intermediateConsigneeInfo.setCompanyName(iConsigneeCompanyName);
            intermediateConsigneeInfo.setAddressLine1(iConsigneeAddressLine1);
            intermediateConsigneeInfo.setCity(iConsigneeCity);
            intermediateConsigneeInfo.setStateProvinceCode(iConsigneeStateProvinceCode);
            intermediateConsigneeInfo.setPostalCode(iConsigneePostalCode);
            intermediateConsigneeInfo.setCountryCode(iConsigneeCountryCode);

            String commercialInv = "";
            try {
                commercialInv = request.getParameter("CIFlag").trim();
            } catch (Exception e) {
                commercialInv = "";
            }
            aascIntlHeaderInfo.setCommercialInv(commercialInv);

            String usCertOrigin = "";
            try {
                usCertOrigin = request.getParameter("COFlag").trim();
            } catch (Exception e) {
                usCertOrigin = "";
            }
            aascIntlHeaderInfo.setUsCertOrigin(usCertOrigin);

            String naftaCertOrigin = "";
            try {
                naftaCertOrigin = request.getParameter("NAFTAFlag").trim();
            } catch (Exception e) {
                naftaCertOrigin = "";
            }
            aascIntlHeaderInfo.setNaftaCertOrigin(naftaCertOrigin);

            String shippersExportDecl = "";
            try {
                shippersExportDecl = request.getParameter("SEDFlag").trim();
            } catch (Exception e) {
                shippersExportDecl = "";
            }
            aascIntlHeaderInfo.setShippersExportDecl(shippersExportDecl);

            String soldToCompanyName = "";
            String soldToAddressLine1 = "";
            String soldToAddressLine2 = "";
            String soldToCity = "";
            String soldToStateProvinceCode = "";
            String soldToPostalCode = "";
            String soldToCountryCode = "";
            try {
                soldToCompanyName = request.getParameter("CompanyName");
            } catch (Exception e) {
                soldToCompanyName = "";
            }
            
            try {
                soldToAddressLine1 = request.getParameter("AddressLine1");
            } catch (Exception e) {
                soldToAddressLine1 = "";
            }
            
            try {
                soldToAddressLine2 = request.getParameter("AddressLine2");
            } catch (Exception e) {
                soldToAddressLine2 = "";
            }
            
            try {
                soldToCity = request.getParameter("City");
            } catch (Exception e) {
                soldToCity = "";
            }
            
            try {
                soldToStateProvinceCode = request.getParameter("StateProvinceCode");
            } catch (Exception e) {
                    soldToStateProvinceCode = "";
            }
            
            try {
                soldToPostalCode = request.getParameter("PostalCode");
            } catch (Exception e) {
                soldToPostalCode = "";
            }
            
            try {
                soldToCountryCode = request.getParameter("CountryCode");
            } catch (Exception e) {
                soldToCountryCode = "";
            }
            soldToAddressInfo.setCompanyName(soldToCompanyName);
            soldToAddressInfo.setAddressLine1(soldToAddressLine1);
            soldToAddressInfo.setAddressLine2(soldToAddressLine2);
            soldToAddressInfo.setCity(soldToCity);
            soldToAddressInfo.setStateProvinceCode(soldToStateProvinceCode);
            soldToAddressInfo.setPostalCode(soldToPostalCode);
            soldToAddressInfo.setCountryCode(soldToCountryCode);

            String soldToTaxId = "";
            try {
                soldToTaxId = request.getParameter("TaxIdNum").trim();
            } catch (Exception e) {
                soldToTaxId = "";
            }
            logger.info("soldToTaxId :" + soldToTaxId);
            aascIntlHeaderInfo.setSoldToTaxId(soldToTaxId);

            String isShipToSameAsSoldTo = "";
            try {
                isShipToSameAsSoldTo = request.getParameter("ShipTosameSoldTo").trim();
            } catch (Exception e) {
                isShipToSameAsSoldTo = "";
            }
            logger.info("isShipToSameAsSoldTo : " + isShipToSameAsSoldTo);
            aascIntlHeaderInfo.setIsShipToSameAsSoldTo(isShipToSameAsSoldTo);

            String intlComments = "";
            try {
                intlComments = request.getParameter("comments").trim();
            } catch (Exception e) {
                intlComments = "";
            }
            aascIntlHeaderInfo.setIntlComments(intlComments);


            String intlCustomerInvoiceNumber = "";
            try {
                intlCustomerInvoiceNumber = request.getParameter("InvoiceNumber").trim();
            } catch (Exception e) {
                intlCustomerInvoiceNumber = "";
            }
            aascIntlHeaderInfo.setIntlCustomerInvoiceNumber(intlCustomerInvoiceNumber);

            String shipFromPhone = "";
            try {
                shipFromPhone = request.getParameter("ShipFromPhone").trim();
            } catch (Exception e) {
                shipFromPhone = "";
            }
            aascIntlHeaderInfo.setShipFromPhone(shipFromPhone);

            String shipFromAttention = "";
            try {
                shipFromAttention = request.getParameter("ShipFromAttention").trim();
            } catch (Exception e) {
                shipFromAttention = "";
            }
            aascIntlHeaderInfo.setShipFromAttention(shipFromAttention);

            String soldToAttention = "";
            try {
                soldToAttention = request.getParameter("SoldToAttention").trim();
            } catch (Exception e) {
                soldToAttention = "";
            }
            logger.info("soldToAttention :" + soldToAttention);
            aascIntlHeaderInfo.setSoldToAttention(soldToAttention);

            String soldToPhone = "";
            try {
                soldToPhone = request.getParameter("SoldToPhone").trim();
            } catch (Exception e) {
                soldToPhone = "";
            }
            logger.info("soldToPhone :" + soldToPhone);
            aascIntlHeaderInfo.setSoldToPhone(soldToPhone);

            String invCurCd = "";
            try {
                invCurCd = request.getParameter("InvCurCd").trim();
            } catch (Exception e) {
                invCurCd = "";
            }
            aascIntlHeaderInfo.setInvoiceCurrencyCode(invCurCd);

            int invVal = 0;
            try {
                String invValStr = request.getParameter("InvVal").trim();
                invVal = Integer.parseInt(invValStr);
            } catch (Exception e) {
                invVal = 0;
            }
            aascIntlHeaderInfo.setInvoiceValue(invVal);
            aascIntlHeaderInfo.setSedAddressInfo(sedAddressInfo);
            aascIntlHeaderInfo.setSoldToAddressInfo(soldToAddressInfo);
            aascIntlHeaderInfo.setForwardAgentInfo(forwardAgentInfo);
            aascIntlHeaderInfo.setIntermediateConsigneeInfo(intermediateConsigneeInfo);

            String declarationStmt="";
            try {
                declarationStmt = request.getParameter("declarationStmt").trim();
            } catch (Exception e) {
                declarationStmt = "";
            }
            aascIntlHeaderInfo.setDeclarationStmt(declarationStmt);
            logger.info("declarationStmt"+declarationStmt);
                       
            String generateCI="";
            try {
                generateCI = request.getParameter("generateCI").trim();
            } catch (Exception e) {
                generateCI = "N";
            }
            aascIntlHeaderInfo.setGenerateCI(generateCI);
            logger.info("GenerateCI "+generateCI);
                       
            String importerName="";
            try {
                importerName = request.getParameter("importerName").trim();
            } catch (Exception e) {
                importerName = "";
            }
            aascIntlHeaderInfo.setImporterName(importerName);
            logger.info("importerName"+importerName);
                       
            String importerCompName="";
            try {
                importerCompName = request.getParameter("importerCompName").trim();
            } catch (Exception e) {
                importerCompName = "";
            }
            aascIntlHeaderInfo.setImporterCompName(importerCompName);
            logger.info("importerCompName"+importerCompName);
                       
            String importerPhoneNum="";
            try {
                importerPhoneNum = request.getParameter("importerPhoneNum").trim();
            } catch (Exception e) {
                importerPhoneNum = "";
            }
            aascIntlHeaderInfo.setImporterPhoneNum(importerPhoneNum);
            logger.info("importerPhoneNum"+importerPhoneNum);
                       
            String importerAddress1="";
            try {
                importerAddress1 = request.getParameter("importerAddress1").trim();
            } catch (Exception e) {
                importerAddress1 = "";
            }
            aascIntlHeaderInfo.setImporterAddress1(importerAddress1);
            logger.info("importerAddress1"+importerAddress1);
                       
            String importerAddress2="";
            try {
                importerAddress2 = request.getParameter("importerAddress2").trim();
            } catch (Exception e) {
                importerAddress2 = "";
            }
            aascIntlHeaderInfo.setImporterAddress2(importerAddress2);
            logger.info("importerAddress2"+importerAddress2);
                       
            String importerCity="";
            try {
                importerCity = request.getParameter("importerCity").trim();
            } catch (Exception e) {
                importerCity = "";
            }
            aascIntlHeaderInfo.setImporterCity(importerCity);
            logger.info("importerCity"+importerCity);
                       
            String importerState="";
            try {
                importerState = request.getParameter("importerState").trim();
            } catch (Exception e) {
                importerState = "";
            }
            aascIntlHeaderInfo.setImporterState(importerState);
            logger.info("importerState"+importerState);
                       
            String importerPostalCode="";
            try {
                importerPostalCode = request.getParameter("importerPostalCode").trim();
            } catch (Exception e) {
                importerPostalCode = "";
            }
            aascIntlHeaderInfo.setImporterPostalCode(importerPostalCode);
            logger.info("importerPostalCode"+importerPostalCode);
                       
            String importerCountryCode="";
            try {
                importerCountryCode = request.getParameter("importerCountryCode").trim();
            } catch (Exception e) {
                importerCountryCode = "";
            }
            aascIntlHeaderInfo.setImporterCountryCode(importerCountryCode);
            logger.info("importerCountryCode"+importerCountryCode);
                       
            String impIntlSedNumber="";
            try {
                impIntlSedNumber = request.getParameter("ImporterTINOrDUNS").trim();
            } catch (Exception e) {
                impIntlSedNumber = "";
            }
            aascIntlHeaderInfo.setImpIntlSedNumber(impIntlSedNumber);
            logger.info("impIntlSedNumber"+impIntlSedNumber);
                       
            String impIntlSedType="";
            try {
                impIntlSedType = request.getParameter("ImporterTINOrDUNSType").trim();
            } catch (Exception e) {
                impIntlSedType = "";
            }
            aascIntlHeaderInfo.setImpIntlSedType(impIntlSedType);
            logger.info("impIntlSedType"+impIntlSedType);

            String recIntlSedNumber="";
            try {
                recIntlSedNumber = request.getParameter("ReceipentTINOrDUNS").trim();
            } catch (Exception e) {
                recIntlSedNumber = "";
            }
            aascIntlHeaderInfo.setRecIntlSedNumber(recIntlSedNumber);
            logger.info("recIntlSedNumber"+recIntlSedNumber);
                       
            String recIntlSedType="";
            try {
                recIntlSedType = request.getParameter("ReceipentTINOrDUNSType").trim();
            } catch (Exception e) {
                recIntlSedType = "";
            }
            aascIntlHeaderInfo.setRecIntlSedType(recIntlSedType);
            logger.info("recIntlSedType"+recIntlSedType);
                       
            String brokerName="";
            try {
                brokerName = request.getParameter("brokerName").trim();
            } catch (Exception e) {
                brokerName = "";
            }
            aascIntlHeaderInfo.setBrokerName(brokerName);
            logger.info("brokerName"+brokerName);
                       
            String brokerCompName="";
            try {
                brokerCompName = request.getParameter("brokerCompName").trim();
            } catch (Exception e) {
                brokerCompName = "";
            }
            aascIntlHeaderInfo.setBrokerCompName(brokerCompName);
            logger.info("brokerCompName"+brokerCompName);
                       
            String brokerPhoneNum="";
            try {
                brokerPhoneNum = request.getParameter("brokerPhoneNum").trim();
            } catch (Exception e) {
                brokerPhoneNum = "";
            }
            aascIntlHeaderInfo.setBrokerPhoneNum(brokerPhoneNum);
            logger.info("brokerPhoneNum"+brokerPhoneNum);
                       
            String brokerAddress1="";
            try {
                brokerAddress1 = request.getParameter("brokerAddress1").trim();
            } catch (Exception e) {
                brokerAddress1 = "";
            }
            aascIntlHeaderInfo.setBrokerAddress1(brokerAddress1);
            logger.info("brokerAddress1"+brokerAddress1);
                       
            String brokerAddress2="";
            try {
                brokerAddress2 = request.getParameter("brokerAddress2").trim();
            } catch (Exception e) {
                brokerAddress2 = "";
            }
            aascIntlHeaderInfo.setBrokerAddress2(brokerAddress2);
            logger.info("brokerAddress2"+brokerAddress2);
                       
            String brokerCity="";
            try {
                brokerCity = request.getParameter("brokerCity").trim();
            } catch (Exception e) {
                brokerCity = "";
            }
            aascIntlHeaderInfo.setBrokerCity(brokerCity);
            logger.info("brokerCity"+brokerCity);
                       
            String brokerState="";
            try {
                brokerState = request.getParameter("brokerState").trim();
            } catch (Exception e) {
                brokerState = "";
            }
            aascIntlHeaderInfo.setBrokerState(brokerState);
            logger.info("brokerState"+brokerState);
                       
            String brokerPostalCode="";
            try {
                brokerPostalCode = request.getParameter("brokerPostalCode").trim();
            } catch (Exception e) {
                brokerPostalCode = "";
            }
            aascIntlHeaderInfo.setBrokerPostalCode(brokerPostalCode);
            logger.info("brokerPostalCode"+brokerPostalCode);
                       
            String brokerCountryCode="";
            try {
                brokerCountryCode = request.getParameter("brokerCountryCode").trim();
            } catch (Exception e) {
                brokerCountryCode = "";
            }
            aascIntlHeaderInfo.setBrokerCountryCode(brokerCountryCode);
            logger.info("brokerCountryCode"+brokerCountryCode);

            String packingListEnclosed = "false";
            int shippersLoadAndCount = 0;
            String bookingConfirmationNumber = "";
            if(carrierCode == 110 || carrierCode == 111){
                String service = request.getParameter("service");
                if(service.contains("FREIGHT") || service.contains("Freight")){
                    try {
                        String packingListEnclosedStr = request.getParameter("packingListEnclosed").trim();
                        logger.info("packingListEnclosedStr : "+packingListEnclosedStr);        
                        packingListEnclosed = packingListEnclosedStr;
                    } catch (Exception e) {
                        packingListEnclosed = "false";
                        //e.printStackTrace();
                    }
                                                                   
                    try {
                        String shippersLoadAndCountStr = request.getParameter("shippersLoadAndCount").trim();
                        logger.info("shippersLoadAndCountStr :"+shippersLoadAndCountStr);        
                        shippersLoadAndCount = Integer.parseInt(shippersLoadAndCountStr);
                    } catch (Exception e) {
                        shippersLoadAndCount = 0;
                        //e.printStackTrace();
                    }
                                                                   
                    try {
                        String bookingConfirmationNumberStr = request.getParameter("bookingConfirmationNumber").trim();
                        logger.info("bookingConfirmationNumberStr : "+bookingConfirmationNumberStr);        
                        bookingConfirmationNumber = bookingConfirmationNumberStr;
                    } catch (Exception e) {
                        bookingConfirmationNumber = "";
                        //e.printStackTrace();
                    }
                } else{
                    packingListEnclosed = "false";
                    shippersLoadAndCount = 0 ;
                    bookingConfirmationNumber = "";
                }
            }  
            aascIntlHeaderInfo.setPackingListEnclosed(packingListEnclosed);
            aascIntlHeaderInfo.setShippersLoadAndCount(shippersLoadAndCount);
            aascIntlHeaderInfo.setBookingConfirmationNumber(bookingConfirmationNumber);
            
            if(carrierCode == 115){
                String otherDescribe="";
                try {
                    otherDescribe = request.getParameter("otherDescribe").trim();
                } catch (Exception e) {
                    otherDescribe = "";
                }
                aascIntlHeaderInfo.setOtherDescribe(otherDescribe);
                logger.info("otherDescribe at 115"+otherDescribe);
                
                String stampsLicenseNumber="";
                try {
                    stampsLicenseNumber = request.getParameter("stampsLicenseNumber").trim();
                } catch (Exception e) {
                    stampsLicenseNumber = "";
                }
                aascIntlHeaderInfo.setStampsLicenseNumber(stampsLicenseNumber);
                logger.info("stampsLicenseNumber at 115::"+stampsLicenseNumber);
                
                String stampsCertificateNumber="";
                try {
                    stampsCertificateNumber = request.getParameter("stampsCertificateNumber").trim();
                } catch (Exception e) {
                    stampsCertificateNumber = "";
                }
                aascIntlHeaderInfo.setStampsCertificateNumber(stampsCertificateNumber);
                logger.info("stampsCertificateNumber at 115::"+stampsCertificateNumber);
                
                String InvoiceNumber="";
                try {
                    InvoiceNumber = request.getParameter("InvoiceNumber").trim();
                } catch (Exception e) {
                    InvoiceNumber = "";
                }
                aascIntlHeaderInfo.setIntlCustomerInvoiceNumber(InvoiceNumber);
                logger.info("InvoiceNumber at 115::"+InvoiceNumber);
                
                String comments="";
                try {
                    comments = request.getParameter("comments").trim();
                } catch (Exception e) {
                    comments = "";
                }
                aascIntlHeaderInfo.setIntlComments(comments);
                logger.info("comments at 115::"+comments);
                
                String Purpose="";
                try {
                    Purpose = request.getParameter("Purpose").trim();
                } catch (Exception e) {
                    Purpose = "";
                }
                aascIntlHeaderInfo.setIntlPurpose(Purpose);
                logger.info("Purpose at 115::"+Purpose);
                
            }
                       
            opStatus = aascIntlShipmentsDAO.saveIntlShipmentDetails(userId, aascIntlHeaderInfo, carrierCode, clientId, locationId);
            session.setAttribute("intlSaveActionFlag", "Y");
            if (opStatus == 120) {
                logger.info("Successfully Saved International Shipment Values");
                aascIntlInfo.setIntlHeaderInfo(aascIntlHeaderInfo);
                String addOrEditImporter = request.getParameter("addOrEditImporter");
                logger.info("addOrEditImporter : "+addOrEditImporter);
                if("update".equalsIgnoreCase(addOrEditImporter)){
       //SC_UPS Shipping - Passing carrier code to save data by reading it as importer detail for FedEx or soldTo details for UPS
                    opImpStatus = aascIntlShipmentsDAO.addIntlImporterDetail(aascIntlHeaderInfo,carrierCode, clientId, locationId);
                    if(opImpStatus == 120){
                        logger.info("Successfully ADDED Importer IN CUSTOM Table");
                    }else{
                        logger.info("Error in Addition of Importer Detail IN CUSTOM Table ");
                    }
                }
                           
                String addOrEditBroker = request.getParameter("addOrEditBroker");
                if("update".equalsIgnoreCase(addOrEditBroker)){
                    opBrokerStatus = aascIntlShipmentsDAO.addIntlBrokerDetail(aascIntlHeaderInfo,clientId, locationId);
                    if(opBrokerStatus == 120){
                        logger.info("Successfully ADDED Broker IN CUSTOM Table");
                    }else{
                        logger.info("Error in Addition of Broker Detail IN CUSTOM Table");
                    }
                }
                       
                aascIntlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
                aascIntlInfo.setIntlHeaderInfo(aascIntlHeaderInfo);
                session.setAttribute("InternationalShipments", aascIntlInfo);

                session.setAttribute("comments", "Successfully Saved Values");
                session.setAttribute("ACTION", "SAVE");
                session.removeAttribute("checkStatus");
            } else {
                logger.info("Got Error in saving International Shipment Values");
                session.setAttribute("comments", "Error in Saving Values");
            }
        }catch (Exception e) {
            logger.severe("Got Exception In Save action" +e.getMessage());
            return "error";
        }
        
        return "success";
    }

    /** This method is called when Delete button is clicked in international page. This method deletes the selected commodity details.
     * @param request
     * @param session
     * @return String
     */
    public String deleteAction(HttpServletRequest request,HttpSession session){
        try{
            String commodityValueStr = request.getParameter("commodityLine").trim();
            int commodityValue = Integer.parseInt(commodityValueStr);
            logger.info("commodityValue = " + commodityValue);
    
            opStatus = aascIntlShipmentsDAO.deleteIntlCommodityDetails(commodityValue, orderNumber, clientId, locationId);
    
            if (opStatus == 120) {
                logger.info("Successfully Deleted Commodity Item");
                String accountNumber = "";
                try{
                    accountNumber = request.getParameter("AccNumber").trim();
                } catch (Exception e) {
                    accountNumber = "";
                }
                logger.info("accountNumber"+accountNumber);
                session.setAttribute("accountNumber",accountNumber);
               
                String payMethod = "";
                try{
                    payMethod = request.getParameter("payerType").trim();
                } catch (Exception e) {
                    payMethod = "";
                }
                logger.info("payMethod"+payMethod);
                session.setAttribute("payMethod",payMethod);
               
                String cCode = "";
                try{
                    cCode = request.getParameter("countryCode").trim();
                } catch (Exception e) {
                    cCode = "";
                }
                logger.info("cCode"+cCode);
                session.setAttribute("cCode",cCode);
               
                aascIntlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
                session.setAttribute("intlCommActionFlag", "Y");
                session.setAttribute("comments", "Successfully Deleted Values");
                session.setAttribute("ACTION", "DELETE");
            } else {
                logger.info("Got Error in deleting commodity item Values");
               
                session.setAttribute("comments", "Error in Deletion of Values");
            }
        } catch (Exception e) {
            logger.severe("Got Exception In delete Action" +e.getMessage());
            return "error";
        }

        return "success";
    }

    /** This method is called when Edit button is clicked in international page. This method get the selected commodity details and display them for editing purpose.
     * @param request
     * @param session
     * @return String
     */
    public String editAction(HttpServletRequest request,HttpSession session){
        try{
            String commodityValueStr = request.getParameter("commodityLine").trim();
            int commodityValue = Integer.parseInt(commodityValueStr);
            logger.info("commodityValue = " + commodityValue);
            
            String opValue = request.getParameter("opValue");
            logger.info("opValue = " + opValue);
            session.setAttribute("opValue", opValue);

            aascIntlCommodityInfo = aascIntlShipmentsDAO.getIntlCommodityDetails(commodityValue, orderNumber, clientId, locationId);
            logger.info("Successfully Retrieve Commodity IN DATABASE");
           
            String accountNumber = "";
            try{
                accountNumber = request.getParameter("AccNumber").trim();
            } catch (Exception e) {
                accountNumber = "";
            }
            logger.info("accountNumber : "+accountNumber);
            session.setAttribute("accountNumber",accountNumber);
           
            String payMethod = "";
            try{
                payMethod = request.getParameter("payerType").trim();
            } catch (Exception e) {
                payMethod = "";
            }
            logger.info("payMethod : "+payMethod);
            session.setAttribute("payMethod",payMethod);
           
            String cCode = "";
            try{
                cCode = request.getParameter("countryCode").trim();
            } catch (Exception e) {
                cCode = "";
            }
            logger.info("cCode : "+cCode);
            session.setAttribute("cCode",cCode);
           
            session.setAttribute("comments", "Successfully Retrieved Values");
            session.setAttribute("intlCommActionFlag", "Y");
            session.setAttribute("ACTION", "EDIT");
            session.setAttribute("CommodityLineValue", aascIntlCommodityInfo);

            aascIntlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
        } catch(Exception e) {
            logger.severe("Got Exception In edit action" +e.getMessage());
            return "error";
        }
        
        return "success";
    }

    /**  This method is called when Add This Commodity button is clicked in international page. This method save o update the data given at commodity level.
     * @param request
     * @param session
     * @return String
     */
    public String addAction(HttpServletRequest request, HttpSession session){
        try{
            AascIntlInfo intlInfoBean = null;
            LinkedList coList = new LinkedList();
            AascAddressInfo aascAddressInfo = null;
            aascAddressInfo = new AascAddressInfo();
            String selectLenStr = request.getParameter("selectLength");
            int selectLen = Integer.parseInt(selectLenStr) - 2;
            logger.info("Commodity No: " + selectLen);

            intlInfoBean = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
            coList = intlInfoBean.getIntlCommodityInfo();
            ListIterator CoInfoIterator = null;
            int count = 0;
            int commodityId = 0;
            for (int listCount = 1; listCount <= 20; listCount++) {
                count = 0;
                CoInfoIterator = coList.listIterator();
                while (CoInfoIterator.hasNext()) {
                    logger.info("commodityId in loop:" + commodityId);
                    AascIntlCommodityInfo aascIntlCommInfo1 = (AascIntlCommodityInfo)CoInfoIterator.next();
                    commodityId = aascIntlCommInfo1.getCommodityNumber();
                    if (commodityId == listCount) {
                        count = count + 1;
                    }
                }
                logger.info("in for loop:" + listCount + ":Count value:" + count);
                if (count == 0) {
                    selectLen = listCount;
                    break;
                }
            }
            logger.info("CommodityNo: after add-delete " + selectLen);
            String opValue = "";
            try {
                opValue = (String)session.getAttribute("opValue");
                session.removeAttribute("opValue");
                logger.info("opValue = " + opValue);
            } catch (Exception e) {
                opValue = "";
            }

            if (!"".equalsIgnoreCase(opValue) && opValue != null) {
                selectLen = Integer.parseInt(opValue);
            }
            logger.info("After Edit Commodity No: " + selectLen);
            try{
            aascIntlCommodityInfo.setCommodityNumber(selectLen);
            aascIntlCommodityInfo.setOrderNumber(orderNumber);
            aascIntlCommodityInfo.setShipmentId(0);
            aascIntlCommodityInfo.setVoidFlag("N");
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
            AascIntlHeaderInfo chkHeaderInfo = new AascIntlHeaderInfo();


            if(carrierCode == 115){
                String intNumberOfPieces = "0";
                try {
                    String NumberOfPieces = 
                    request.getParameter("Quantity").trim();
                    intNumberOfPieces = NumberOfPieces;
                    logger.info("Quantity at 115: " + intNumberOfPieces);
                } catch (Exception e) {
                    intNumberOfPieces = "0";
                }
                aascIntlCommodityInfo.setQuantity(intNumberOfPieces);
                
                String Description = request.getParameter("Description").trim();
                logger.info("Description at 115: " + Description);
                aascIntlCommodityInfo.setDescription(Description);
                
                String CountryOfManufacture = "";
                try {
                    CountryOfManufacture = request.getParameter("CountryOfManufacture").trim();
                    logger.info("CountryOfManufacture at 115: " + CountryOfManufacture);
                } catch (Exception e) {
                    CountryOfManufacture = "";
                }
                aascIntlCommodityInfo.setCountryOfManufacture(CountryOfManufacture);
                
                String HarmonizedCode = "";
                try {
                    HarmonizedCode = request.getParameter("HarmonizedCode").trim();
                    logger.info("HarmonizedCode at 115: " + HarmonizedCode);
                } catch (Exception e) {
                    HarmonizedCode = "";
                }
                aascIntlCommodityInfo.setHarmonizedCode(HarmonizedCode);
                logger.info("before weight = "+HarmonizedCode);
                
                double weight;
                try {
                    String Weight = request.getParameter("Weight").trim();
                    weight = Double.parseDouble(Weight);
                    logger.info("Weight at 115: " + weight);
                } catch (Exception e) {
                    weight = 0.0;
                }
                aascIntlCommodityInfo.setWeight(weight);
                
                String UnitPrice = "";
                try {
                    UnitPrice = request.getParameter("UnitPrice").trim();
                    logger.info("UnitPrice at 115: " + UnitPrice);
                } catch (Exception e) {
                    UnitPrice = "";
                }
                aascIntlCommodityInfo.setUnitPrice(UnitPrice);
                
                String packageWeightUom = "";
                try {
                    packageWeightUom = request.getParameter("UOM").trim();
                    logger.info("packageWeightUom at 115: " + packageWeightUom);
                } catch (Exception e) {
                    packageWeightUom = "";
                }
                aascIntlCommodityInfo.setPackageWeightUom(packageWeightUom);
                
            }

            else {

            int intNumberOfPieces = 0;
            try {
                String NumberOfPieces = 
                request.getParameter("NumberOfPieces").trim();
                intNumberOfPieces = Integer.parseInt(NumberOfPieces);
                logger.info("NumberOfPieces: " + intNumberOfPieces);
            } catch (Exception e) {
                intNumberOfPieces = 0;
            }
            aascIntlCommodityInfo.setNumberOfPieces(intNumberOfPieces);
            
            String Description = request.getParameter("description").trim();
            logger.info("Description: " + Description);
            aascIntlCommodityInfo.setDescription(Description);
            
            String CountryOfManufacture = "";
            try {
                CountryOfManufacture = request.getParameter("CountryOfManufacture").trim();
                logger.info("CountryOfManufacture: " + CountryOfManufacture);
            } catch (Exception e) {
                CountryOfManufacture = "";
            }
            aascIntlCommodityInfo.setCountryOfManufacture(CountryOfManufacture);
            
            String HarmonizedCode = "";
            try {
                HarmonizedCode = request.getParameter("HarmonizedCode").trim();
                logger.info("HarmonizedCode: " + HarmonizedCode);
            } catch (Exception e) {
                HarmonizedCode = "";
            }
            aascIntlCommodityInfo.setHarmonizedCode(HarmonizedCode);
            logger.info("before weight = "+HarmonizedCode);
            
            double weight;
            try {
                String Weight = request.getParameter("Weight").trim();
                weight = Double.parseDouble(Weight);
                logger.info("Weight: " + weight);
            } catch (Exception e) {
                weight = 0.0;
            }
            aascIntlCommodityInfo.setWeight(weight);

            String Quantity = "";
            try {
                Quantity = request.getParameter("Quantity").trim();
                logger.info("Quantity: " + Quantity);
            } catch (Exception e) {
                Quantity = "";
            }
            aascIntlCommodityInfo.setQuantity(Quantity);

            String QuantityUnits = "";
            try {
                QuantityUnits = request.getParameter("QuantityUnits").trim();
                logger.info("QuantityUnits: " + QuantityUnits);
            } catch (Exception e) {
                QuantityUnits = "";
            }
            aascIntlCommodityInfo.setQuantityUnits(QuantityUnits);

            String UnitPrice = "";
            try {
                UnitPrice = request.getParameter("UnitPrice").trim();
                logger.info("UnitPrice: " + UnitPrice);
            } catch (Exception e) {
                UnitPrice = "";
            }
            aascIntlCommodityInfo.setUnitPrice(UnitPrice);

            String CustomsValue = "";
            try {
                CustomsValue = request.getParameter("CustomsValue").trim();
                logger.info("CustomsValue: " + CustomsValue);
            } catch (Exception e) {
                CustomsValue = "";
            }
                aascIntlCommodityInfo.setCustomsValue(CustomsValue);


            String ExportLicenseNumber = "";
            try {
                ExportLicenseNumber = request.getParameter("ExportLicenseNumber").trim();
                logger.info("ExportLicenseNumber: " + ExportLicenseNumber);
            } catch (Exception e) {
                ExportLicenseNumber = "";
            }
            aascIntlCommodityInfo.setExportLicenseNumber(ExportLicenseNumber);

            String ExportLicenseExpirationDate = "";
            try {
                ExportLicenseExpirationDate = request.getParameter("ExportLicenseExpirationDate").trim();
                logger.info("ExportLicenseExpirationDate: " + ExportLicenseExpirationDate);
                
                ExportLicenseExpirationDate = dateConverter(ExportLicenseExpirationDate);
                logger.info("ExportLicenseExpirationDate: " + ExportLicenseExpirationDate);
            } catch (Exception e) {
                ExportLicenseExpirationDate = "";
            }
            aascIntlCommodityInfo.setExportLicenseExpiryDate(ExportLicenseExpirationDate);

            String netCostPeriodBeginDate = "";
            try {
                netCostPeriodBeginDate = request.getParameter("NetCostPeriodBeginDate").trim();
                logger.info("netCostPeriodBeginDate: " + netCostPeriodBeginDate);
                
                netCostPeriodBeginDate = dateConverter(netCostPeriodBeginDate);
                logger.info("netCostPeriodBeginDate: " + netCostPeriodBeginDate);
            } catch (Exception e) {
                netCostPeriodBeginDate = "";
            }
            aascIntlCommodityInfo.setNetCostPeriodBeginDate(netCostPeriodBeginDate);

            String netCostPeriodEndDate = "";
            try {
                netCostPeriodEndDate = request.getParameter("NetCostPeriodEndDate").trim();
                logger.info("netCostPeriodEndDate: " + netCostPeriodEndDate);
                
                netCostPeriodEndDate = dateConverter(netCostPeriodEndDate);
                logger.info("netCostPeriodEndDate: " + netCostPeriodEndDate);
            } catch (Exception e) {
                netCostPeriodEndDate = "";
            }
            aascIntlCommodityInfo.setNetCostPeriodEndDate(netCostPeriodEndDate);

            String producer = "";
            try {
                producer = request.getParameter("Producer").trim();
                logger.info("producer: " + producer);
            } catch (Exception e) {
                producer = "";
            }
            aascIntlCommodityInfo.setProducer(producer);

            String tariffCode = "";
            try {
                tariffCode = request.getParameter("HarmonizedCode").trim();
                logger.info("tariffCode: " + tariffCode);
            } catch (Exception e) {
                tariffCode = "";
            }
            aascIntlCommodityInfo.setTariffCode(tariffCode);

            String preferenceCriteria = "";
            try {
                preferenceCriteria = request.getParameter("PreferenceCriteria").trim();
                logger.info("preferenceCriteria: " + preferenceCriteria);
            } catch (Exception e) {
                preferenceCriteria = "";
            }
            aascIntlCommodityInfo.setPreferenceCriteria(preferenceCriteria);

            String rvcCalculationMethod = "";
            try {
                rvcCalculationMethod = request.getParameter("RVCCalculationMethod").trim();
                logger.info("rvcCalculationMethod: " + rvcCalculationMethod);
            } catch (Exception e) {
                rvcCalculationMethod = "";
            }
            aascIntlCommodityInfo.setRvcCalculationMethod(rvcCalculationMethod);

            double sedTotalValue = 0.0;
            try {
                String sedTotalValueStr = request.getParameter("SEDTotalValue").trim();
                sedTotalValue = Double.parseDouble(sedTotalValueStr);
                logger.info("sedTotalValue: " + sedTotalValue);
            } catch (Exception e) {
                sedTotalValue = 0.0;
            }
            aascIntlCommodityInfo.setSedTotalValue(sedTotalValue);

            String scheduleBUOM = "";
            try {
                scheduleBUOM = request.getParameter("ScheduleBUnitOfMeasure").trim();
                logger.info("scheduleBUOM: " + scheduleBUOM);
            } catch (Exception e) {
                scheduleBUOM = "";
            }
            aascIntlCommodityInfo.setScheduleBUOM(scheduleBUOM);

            String scheduleBNumber = "";
            try {
                scheduleBNumber = request.getParameter("ScheduleBNumber").trim();
                logger.info("scheduleBNumber: " + scheduleBNumber);
            } catch (Exception e) {
                scheduleBNumber = "";
            }
            aascIntlCommodityInfo.setScheduleBNumber(scheduleBNumber);

            String exportType = "";
            try {
                exportType = request.getParameter("ExportType").trim();
                logger.info("exportType: " + exportType);
            } catch (Exception e) {
                exportType = "";
            }
            aascIntlCommodityInfo.setExportType(exportType);

            String scheduleBQty = "";
            try {
                scheduleBQty = request.getParameter("ScheduleBQuantity").trim();
                logger.info("scheduleBQty: " + scheduleBQty);
            } catch (Exception e) {
                scheduleBQty = "";
            }
            aascIntlCommodityInfo.setScheduleBQty(scheduleBQty);

            String companyName = "";
            try {
                companyName = request.getParameter("PCompanyName").trim();
                logger.info("companyName: " + companyName);
            } catch (Exception e) {
                companyName = "";
            }
            aascAddressInfo.setCompanyName(companyName);

            String addressLine1 = "";
            try {
                addressLine1 = request.getParameter("PAddressLine1").trim();
                logger.info("addressLine1: " + addressLine1);
            } catch (Exception e) {
                addressLine1 = "";
            }
            aascAddressInfo.setAddressLine1(addressLine1);

            String city = "";
            try {
                city = request.getParameter("PCity").trim();
                logger.info("city: " + city);
            } catch (Exception e) {
            city = "";
            }
            aascAddressInfo.setCity(city);

            String stateProvinceCode = "";
            try {
                stateProvinceCode = request.getParameter("PStateProvinceCode").trim();
                logger.info("stateProvinceCode: " + stateProvinceCode);
            } catch (Exception e) {
                stateProvinceCode = "";
            }
            aascAddressInfo.setStateProvinceCode(stateProvinceCode);

            String postalCode = "";
            try {
                postalCode = request.getParameter("PPostalCode").trim();
                logger.info("postalCode: " + postalCode);
            } catch (Exception e) {
                postalCode = "";
            }
            aascAddressInfo.setPostalCode(postalCode);

            String countryCode = "";
            try {
                countryCode = request.getParameter("PCountryCode").trim();
                logger.info("countryCode: " + countryCode);
            } catch (Exception e) {
                countryCode = "";
            }
            aascAddressInfo.setCountryCode(countryCode);
            
            String taxId = "";
            try {
                taxId = request.getParameter("NTaxIdNum").trim();
                logger.info("NTaxIdNum: " + taxId);
            } catch (Exception e) {
                taxId = "";
            }
            aascAddressInfo.setTaxId(taxId);

            String packageWeightUom = "";
            try {
                packageWeightUom = request.getParameter("UOM").trim();
                logger.info("packageWeightUom: " + packageWeightUom);
            } catch (Exception e) {
                packageWeightUom = "";
            }
            aascIntlCommodityInfo.setPackageWeightUom(packageWeightUom);
            aascIntlCommodityInfo.setAascAddressInfo(aascAddressInfo);

//            AascIntlHeaderInfo chkHeaderInfo = new AascIntlHeaderInfo();
            String CI = "";
            try {
                CI = request.getParameter("CIFlag").trim();
                logger.info("CI: " + CI);
            } catch (Exception e) {
                CI = "";
            }
            chkHeaderInfo.setCommercialInv(CI);
            
            String USCO = "";
            try {
                USCO = request.getParameter("COFlag").trim();
                logger.info("USCO: " + USCO);
            } catch (Exception e) {
                USCO = "";
            }
            chkHeaderInfo.setUsCertOrigin(USCO);
            
            String NAFTACO = "";
            try {
                NAFTACO = request.getParameter("NAFTAFlag").trim();
            } catch (Exception e) {
                NAFTACO = "";
            }
            chkHeaderInfo.setNaftaCertOrigin(NAFTACO);
            
            String SED = "";
            try {
                SED = request.getParameter("SEDFlag").trim();
            } catch (Exception e) {
                SED = "";
            }
            chkHeaderInfo.setShippersExportDecl(SED);

            String accountNumber = "";
            try{
                accountNumber = request.getParameter("AccNumber").trim();
            } catch (Exception e) {
                accountNumber = "";
            }
            logger.info("accountNumber: "+accountNumber);
            session.setAttribute("accountNumber",accountNumber);
                       
            String payMethod = "";
            try{
                payMethod = request.getParameter("payerType").trim();
            } catch (Exception e) {
                payMethod = "";
            }
            logger.info("payMethod: "+payMethod);
            session.setAttribute("payMethod",payMethod);
                       
            String cCode = "";
            try{
                cCode = request.getParameter("countryCode").trim();
            } catch (Exception e) {
                cCode = "";
            }
            logger.info("cCode: "+cCode);
            session.setAttribute("cCode",cCode);
            }
            opStatus = aascIntlShipmentsDAO.saveIntlCommodityDetails(userId, aascIntlCommodityInfo, carrierCode, clientId, locationId);
            if (opStatus == 120) {
                logger.info("Successfully ADDED Commodity IN DATABASE");
                aascIntlInfo = aascIntlShipmentsDAO.getIntlCommodityLineDetails(orderNumber, clientId, locationId);
                String addOrEditItem =  request.getParameter("addOrEditItem");  
                logger.info("Add/Update Commodity In Custom Table");
                if("update".equalsIgnoreCase(addOrEditItem)){
                    opItemStatus = aascIntlShipmentsDAO.addIntlCommodityItemDetail(aascIntlCommodityInfo,carrierCode, clientId, locationId);
                    if(opItemStatus == 120){
                        logger.info("Successfully ADDED Commodity IN CUSTOM Table");
                    }else{
                        logger.info("Error in Addition of Commodity Values IN CUSTOM Table");
                    }
                }
                session.setAttribute("intlCommActionFlag", "Y");
                session.setAttribute("checkStatus", chkHeaderInfo);

                if (!"".equalsIgnoreCase(opValue) && opValue != null) {
                    session.setAttribute("comments", "Successfully Updated Commodity Value(s)"); // display message modified by srisha for bug #3335
                } else {
                    session.setAttribute("comments", "Successfully Added Commodity Value(s)");   // display message modified by srisha for bug #3335
                }
                session.setAttribute("ACTION", "ADD");
            } else {
                logger.info("Error in Adding Commodity Values");
                session.setAttribute("comments", "Error in Addition of Commodity Value(s)");   // display message modified by srisha for bug #3335
            }
        }catch (Exception e) {
            logger.severe("Got Exception In addAction " +e.getMessage());
            return "error";
        }

       return "success";
    }
    
    /**
     * This method can convert the yyyy-mm-dd format dates to dd-mon-yyyy format<br>
     * @return String that is "rdate"<br>
     * @param convertDate of type String<br>
     */

    String dateConverter(String convertDate) {
        int len = convertDate.length();
        int index = convertDate.indexOf('-');
        int index1 = convertDate.lastIndexOf('-');
        String rdate = "";
        String mon[] = 
        { "", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", 
          "OCT", "NOV", "DEC" };


        String syear = convertDate.substring(0, index).trim();
        String smon = convertDate.substring(index + 1, index1).trim();
        String sdate = convertDate.substring(index1 + 1, len).trim();
        rdate = sdate + '-' + mon[Integer.parseInt(smon)] + '-' + syear;

        return rdate;
    }
    
    /** This method can replace the null values with nullString.
         * @return String that is ""
         * @param obj of type Object
         */   
    String nullStrToSpc(Object obj) {
        String spcStr = "";
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return spcStr;
        } else {
            return obj.toString();
        }
    }

}


