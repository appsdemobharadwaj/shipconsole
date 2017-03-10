package com.aasc.erp.delegate;
/*========================================================================+
@(#)AascPrinterSetupDelegate.java 23/06/2015
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

import com.aasc.erp.bean.AascPrinterInfo;
import com.aasc.erp.model.AascOraclePrinterSetupDAO;
import com.aasc.erp.model.AascPrinterSetupDAO;
import com.aasc.erp.util.AascLogger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AascPrinterSetupDelegate class pass data to AascPrinterSetupDAO class.
 * @version 1.0
 * @author Y Pradeep
 * @since
 *        23/06/2015     Y Pradeep          Added this file for Printer Setup Popup page.
 *        13/07/2015     Y Pradeep          Added Print Server IP Address field and related code.
 *        11/03/2016     Y Pradeep          Removed Printer IP Address related code as it is not required.
 */
 

public class AascPrinterSetupDelegate {
    private static Logger logger = AascLogger.getLogger("AascPrinterSetupDelegate");
    
    private AascPrinterInfo aascPrinterInfo = null;
    private AascPrinterSetupDAO aascPrinterSetupDAO = null;
    public String saveUpdateAction(HttpSession session, HttpServletRequest request){
    
        try{
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
            
            int userId = (Integer)session.getAttribute("user_id"); // string userid
            logger.info("userIdstr = "+userId);
            
            String noOfPrinters= request.getParameter("totalPrinters");
            int rows = Integer.parseInt(noOfPrinters);
            
            LinkedList printerList = new LinkedList();
            
            for(int index=1;index<rows+1;index++){
                aascPrinterInfo = new AascPrinterInfo();
                
                String printerIndex = request.getParameter("printerIndex"+index);
                int printerIndex1=0;
                if(printerIndex==""||printerIndex==null)
                    printerIndex1 = index;
                else
                    printerIndex1 = Integer.parseInt(printerIndex);
                
                String labelFormat = request.getParameter("labelFormat"+index);
                String printerName = request.getParameter("printerName"+index);
                String enabledFlag = request.getParameter("enabledFlag"+index);
                String locationId = request.getParameter("locationId");
                
                aascPrinterInfo.setPrinterIndex(printerIndex1);
                aascPrinterInfo.setLabelFormat(labelFormat);
                aascPrinterInfo.setPrinterName(printerName);
                aascPrinterInfo.setEnabledFlag(enabledFlag);
                aascPrinterInfo.setClientId(selectedClientId);
                aascPrinterInfo.setLocationId(Integer.parseInt(locationId));
                aascPrinterInfo.setUserid(userId);
                
                printerList.add(aascPrinterInfo);
            }
            
            String errorlist="";
            List  opStatusList;
            try{
                aascPrinterSetupDAO = new AascOraclePrinterSetupDAO();
                opStatusList = aascPrinterSetupDAO.savePrinterSetupInfo(printerList);
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
                    request.setAttribute("printerKey","Printer details saved successfully");
                    return "success";
                }
                else  {      
                    request.setAttribute("printerKey","Error in saving Printer Details");
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

    public LinkedList getPrinterDetails(int clientId, int locationId){
        logger.info("Inside getPrinterDetails() method");
        LinkedList labelFormatList = null;
        AascPrinterSetupDAO aascPrinterSetupDAO = null;
        try{
            aascPrinterSetupDAO = new AascOraclePrinterSetupDAO();
            labelFormatList = aascPrinterSetupDAO.getPrinterSetupInfo(clientId, locationId);
            
        } catch (Exception e){
            logger.info("got exception in getPrinterDetails() :"+e.getMessage());
            //e.printStackTrace();
        }
        
        return labelFormatList;
    }

}
