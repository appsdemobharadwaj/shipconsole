/*
 * @(#)ShipMethodAction.java  05/04/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 */
package com.aasc.erp.delegate;

import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.model.AascCarrierConfigurationDAO;
import com.aasc.erp.model.AascOracleCarrierConfigurationDAO;
import com.aasc.erp.model.AascOracleShipMethodDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @version 1.1
 * @author Vandana Gangisetty
 * @since
 *        10/12/2014 Y Pradeep  Added code to set client id in request while close button is clicked in Shipmethod mapping package.
 *        18/12/2014 Y Pradeep  Modified nullStrToSpc method.
 *        24/12/2014 Y Pradeep  Commited code after Self Audit.
 *        14/12/2014 Y Pradeep  Modified code to save new shipmethods.
 */


/**
 * ShipMethodAction class extends Action class.
 * This class takes the values from the jsp and depending upon 
 * the submit button value of the jsp executes appropriate if 
 * block and saves or retrieves data from the  database.
 **/
public class AascShipMethodDelegate {
    private AascShipMethodDAO aascShipMethodDAO = null; // declaring the variable for storing the object of the AascShipMethodDAO class
    private AascShipMethodInfo aascShipMethodInfo = null; // ship method bean object containing ship method information
    private int index = 0; // used to index alternate ship methods dynamically
    private int clientId = 0;
    LinkedList sessionList = null;
    int userId = 0;

    private static Logger logger = AascLogger.getLogger("ShipMethodAction");


    /** This method is called from action class when delete action is performed for Shipmethod Mapping page.
     * This method delete selected row in ShipMethod Mapping page
     * @param request
     * @param session
     * @return String success or error message to action class
     */
    public String deleteShipMethod(HttpServletRequest request, HttpSession session) {
        LinkedList shipMethodlist=null;
        String deleteStatus="";
        String shipMethodDelSize1 = request.getParameter("shipMethodDelSize");
        
        int shipMethodDelSize = Integer.parseInt(shipMethodDelSize1);
        int locationid= Integer.parseInt(request.getParameter("location"));
        
        int carrierId = Integer.parseInt(request.getParameter("carrierID"));
        
        int client = Integer.parseInt(request.getParameter("client"));
        
        int carrierCodeTemp = Integer.parseInt(request.getParameter("carrierCode"));
        String checked ="", d="";
        
        for(int i=1;i<=shipMethodDelSize;i++){
            checked = request.getParameter("shipMethod"+i);   
            if(checked !=null && "Y".equalsIgnoreCase(checked)){
                String shipmethodIndex = request.getParameter("shipmethodIndex"+i);
                
                int shipmethodIndex1=0;
                if(shipmethodIndex==""||shipmethodIndex==null)
                    shipmethodIndex1 = index;
                else
                    shipmethodIndex1= Integer.parseInt(shipmethodIndex);
                
                
                String userdefinedShipmethod = request.getParameter("userdefinedShipmethod"+i);
                aascShipMethodDAO = new AascOracleShipMethodDAO();
                int status=    aascShipMethodDAO.deleteShipMethod(client,carrierId,locationid,shipmethodIndex1,userdefinedShipmethod);
                if(status==1){
                    deleteStatus="success";
                    d="Ship Methods Deleted Successfully ";
                    request.setAttribute("deleteKey",d);
                }
                else{
                    deleteStatus="error";
                    d=i+" Ship Method deletion failed \n";
                    request.setAttribute("deleteKey",d);
                    break;
                }
                
            }
        }
        
        AascCarrierConfigurationDAO aascCarrierConfigurationDAO= new AascOracleCarrierConfigurationDAO();

        HashMap validationHashMap = aascCarrierConfigurationDAO.getLookUpDetails("SHIPMETHOD_VALIDATION_CODE");
        request.setAttribute("validationHashMap", (Object)validationHashMap);
        
        int carrierCode = carrierCodeTemp;
          String carrierMode=request.getParameter("Mode");

        HashMap carrierServiceList = aascCarrierConfigurationDAO.getCarrierLookUpValues(carrierCode, "CARRIER_SERVICE_LEVEL", carrierMode);     
        request.setAttribute("carrierServiceList", (Object)carrierServiceList);

         AascShipMethodDAO aascShipMethodDAO = new AascOracleShipMethodDAO();
        
        shipMethodlist  = aascShipMethodDAO.getShipMethodMappingInfo(carrierId,client);
        if(shipMethodlist==null){
            return "error";}
        else{
            session.setAttribute("shipMethodlist", (Object)shipMethodlist);
            return "success";
        }
    }

    /** This method is called from action class when close button is clicked in Shipmethod Mapping page.
     * This method get required values back to Carrier Configuration Page.
     * @param session HttpSession
     * @param request HttpRequest
     * @return String success or error message to action class
     */
    public String goCarrierAction(HttpSession session, HttpServletRequest request) {
        try {
            session.removeAttribute("aascShipMethodInfo2");
            session.removeAttribute("shipMethodInfoBean");
            session.removeAttribute("ShipMethodDAO");
            session.removeAttribute("ShipMethodInfo");

            Integer clientId = (Integer)session.getAttribute("clientIdN");
            request.setAttribute("clientId",clientId);
            session.setAttribute("submitType", "Close");
            return "success";
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "error";
        }
    }

    /** This method is called from action class when Save button is clicked in Shipmethod Mapping page.
     * This method save or update added shipmethods into database.
     * @param session HttpSession
     * @param request HttpRequest
     * @return String success or error message to action class
     */
    public String saveUpdateAction(HttpSession session, HttpServletRequest request) {
        try {
            String noOfShipMethods= request.getParameter("totalShipMethods");
            int rows = Integer.parseInt(noOfShipMethods);
            LinkedList shipmethodlist = new LinkedList();

            for(int index=1;index<rows+1;index++){
                String userdefinedShipmethod = request.getParameter("userdefinedShipmethod"+index);
                String carrierServiceLevel = request.getParameter("carrierServiceLevel"+index);
                String validation = request.getParameter("validation"+index);
                String enabledFlag = request.getParameter("enabledFlag"+index);
                String international = request.getParameter("international"+index);
                String clientid = request.getParameter("client");
                String carrierID = request.getParameter("carrierID");
                String locationid = request.getParameter("location");
                String userid = request.getParameter("user");
                String carriername = request.getParameter("carriername");
                String shipmethodIndex = request.getParameter("shipmethodIndex"+index);
                int shipmethodIndex1=0;
                if(shipmethodIndex==""||shipmethodIndex==null)
                    shipmethodIndex1 = index;
                else
                    shipmethodIndex1= Integer.parseInt(shipmethodIndex);
                  
                aascShipMethodInfo = new AascShipMethodInfo();
          
                aascShipMethodInfo.setUserdefinedShipmethod(userdefinedShipmethod);
                aascShipMethodInfo.setCarrierServiceLevel(carrierServiceLevel);
                aascShipMethodInfo.setValidation(validation);
                aascShipMethodInfo.setEnabledFlag(enabledFlag);
                aascShipMethodInfo.setIntlShipFlag(international);
                aascShipMethodInfo.setShipmethodIndex(shipmethodIndex1);
                aascShipMethodInfo.setClientid(Integer.parseInt(clientid));
                aascShipMethodInfo.setCarrierId(Integer.parseInt(carrierID));
                aascShipMethodInfo.setLocationid(Integer.parseInt(locationid));
                aascShipMethodInfo.setUserid(Integer.parseInt(userid));
                aascShipMethodInfo.setCarrierName(carriername);
                  
                shipmethodlist.add(aascShipMethodInfo);
            }
            String errorlist="";
            List  opStatusList;
            try{
                aascShipMethodDAO = new AascOracleShipMethodDAO();
                opStatusList = aascShipMethodDAO.saveShipMethodInfo(shipmethodlist);
                Iterator it= opStatusList.iterator();          
                while(it.hasNext()){
                    String el=  it.next().toString();
                    if(el.contains("Exception")){
                        errorlist = errorlist + el;
                    }
                }
                session.setAttribute("errorlist",errorlist);
                if("".equalsIgnoreCase(errorlist))
                {   
                    request.setAttribute("deleteKey","Ship Methods saved successfully");
                    return "success";
                }
                else  {      
                    request.setAttribute("deleteKey","Error in saving Ship Methods");
                return "error";
               }
            }catch(Exception e){
                logger.info("Exception in saveUpdateAction"+e.getMessage());
//                e.printStackTrace();
            }

            return "success";
        } catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
            return "error";
        }
    }

    /** This method can replace the null values with nullString
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
    } // end of the method nullStrToSpc(Object obj)    
} // end of the class
