 /*

   * @PackageDimensionsDelegate.java

   * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

   * All rights reserved.
   *  @History
          17/11/2014  Sunanda.k     added the following code from ShipConsoleCloud version 1.2 
          07/01/2015  Eshwari M     Merged Sunanda's code : Added comments, java doc and aligned the code.
          20/01/2015  K Sunanda     Removed unused variables and unnecessary loggers
          18/02/2015  K Sunanda     Moved all the business logic to Delegate class
          15/04/2015  Eshwari M     Modified code to fix bug # 2582
          14/08/2015  N Srisha      Added code for bug #3407
   */
package com.aasc.erp.delegate;
import com.aasc.erp.bean.AascGetLocInfo;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascPackageDimensionDAO;
import com.aasc.erp.bean.AascPackageDimensionInfo;
import com.aasc.erp.bean.AascSetupLocationBean;
import com.aasc.erp.model.AascGetLocDAO;
import com.aasc.erp.model.AascOracleSetupLocationDAO;
import com.aasc.erp.model.AascSetupLocationDAO;
import com.aasc.erp.util.AascLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

 public class PackageDimensionsDelegate {
     public PackageDimensionsDelegate() {
     }
     String locationStr = "";
     int queryTimeOutInt;
     HttpSession session;
     int userId;
     int type;
     int clientId = 0;
     private static Logger logger =AascLogger.getLogger("PackageDimensionsDelegate");
     int ORACLE = 1;
     int locationInt = 0;
     private AascPackageDimensionDAO aascPackageDimensionDAO;
     AascPackageDimensionInfo aascPackageDimensionInfo;
     int getDimensionStatus = 0;
     String key = null;
     int dimensionCount = 0;
     int dimensionId = 0;
     String dimensionActive = "";
     String dimensionDefault = "";
     int saveDimensionAllStatus = 0;
     int addNewDimensionStatus = 0;
     String statuSMsg = "";
     String actionValue = "";
     AascPackageDimensionInfo aascPackageDimensionInfoAddNewDimension;
     AascPackageDimensionInfo aascPackageDimensionInfoEditDimension;
     int editNewDimensionStatus = 0;
     String editDimStatusMsg = "";

     /**
      * commonAction Method gets clientId and userId from session
                         and sets to object.
      * @param   request                       Object    of class  HttpServletRequest
      * @return  aascSetupLocationBean object
      */
     public String commonAction(HttpServletRequest request, 
                              HttpSession session) {
         try {
             session = request.getSession(true); // getting the Session object
             String userIdstr =session.getAttribute("user_id").toString(); // string userid
             if (userIdstr != null) {
                 userId = Integer.parseInt(userIdstr);
             }
             Integer accessLevelSse = (Integer)session.getAttribute("role_id");
             type = accessLevelSse.intValue();
             String queryTimeOut = (String)session.getAttribute("queryTimeOut");
             queryTimeOutInt = Integer.parseInt(queryTimeOut);
             clientId = (Integer)session.getAttribute("client_id");
             AascDAOFactory aascDAOFactory = 
                 AascDAOFactory.getAascDAOFactory(ORACLE);
             aascPackageDimensionDAO = aascDAOFactory.getPackageDimensionDAO();
             return "success";
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
             return "fail";
         }
     }
     
     
     public String locUnit(HttpSession session){
         AascDAOFactory aascDAOFactory =AascDAOFactory.getAascDAOFactory(ORACLE);
         AascGetLocDAO aascGetLocDAO = aascDAOFactory.getLocationDAO();
         AascGetLocInfo aascGetLocInfo =aascGetLocDAO.getLocationInfo(clientId);
         session.setAttribute("LOCInfo", aascGetLocInfo);
         return "success";
     }

    /**
     * goAction() method for setting actiontype into bean aascPackageDimensionInfo.
     * @param   actiontype        String       the action type of the action class aascPackageDimensionAction.
    */
     public String goAction(HttpServletRequest request, String actiontype,Integer locationInt) {
         try {
             session = request.getSession(true); // getting the Session object
             aascPackageDimensionInfo = aascPackageDimensionDAO.getPackageDimensionInfo(locationInt,type,clientId);
             aascPackageDimensionInfo.setActionType(actiontype);
             dimensionActive=aascPackageDimensionInfo.getDimensionActive();
             dimensionDefault=aascPackageDimensionInfo.getDimensionDefault();
             getDimensionStatus = aascPackageDimensionInfo.getErrorStatus();
             
             if (getDimensionStatus == 506) {
                     key = "aasc506";
                 if(aascPackageDimensionInfo.getPackageDimensionList().size() == 0){ //Srisha added code for bug #3407
                     key = "aasc510";
                 }
                 session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 request.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 
             } else if (getDimensionStatus == 5) {
                 key = "aasc510";
             }else if (getDimensionStatus == -1013) {
                 key = "aasc20001";
             } else {
                 key = "aasc507";
             }
             //request.setAttribute("key", key);
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }
         return key;
     }

     /**
      * saveAction() method for setting the package Dimension Active and Default Status values into bean aascPackageDimensionInfoSave.
      * @param   request                       Object    of class  HttpServletRequest.
      */
     public String saveAction(HttpServletRequest request, int locationId) {
         try {
             session = request.getSession(true); // getting the Session object
             locationInt = locationId ;
             AascPackageDimensionInfo aascPackageDimensionInfoSave = new AascPackageDimensionInfo();
             String dimensionCountStr = request.getParameter("dimensionCount");
             dimensionCount = Integer.parseInt(dimensionCountStr);
             LinkedList packageDimensionInfoList = new LinkedList();
             AascPackageDimensionInfo aascPackageDimensionInfoObj = null ;
             for (int dimLine = 1; dimLine <= dimensionCount; dimLine++) {
                 aascPackageDimensionInfoObj = new AascPackageDimensionInfo();
                 String dimensionIdStr =request.getParameter("dimensionId" + dimLine);
                 dimensionId = Integer.parseInt(dimensionIdStr);
                 dimensionActive =request.getParameter("dimensionActive" + dimLine);
                 if (dimensionActive == null || dimensionActive == "null") {
                     dimensionActive = "N";
                 }
                 dimensionDefault =request.getParameter("dimensionDefault" + dimLine);
                 if (dimensionDefault == null || dimensionDefault == "null") {
                     dimensionDefault = "N";
                 }
                 logger.info(" locationId : "+locationId);
                 
                 aascPackageDimensionInfoObj.setLocationId(locationId);
                 aascPackageDimensionInfoObj.setDimensionId(dimensionId);
                 aascPackageDimensionInfoObj.setDimensionActive(dimensionActive);
                 aascPackageDimensionInfoObj.setDimensionDefault(dimensionDefault);
                 packageDimensionInfoList.add(aascPackageDimensionInfoObj);

             }
             aascPackageDimensionInfoSave.setPackageDimensionList(packageDimensionInfoList);
             saveDimensionAllStatus = aascPackageDimensionDAO.saveAllPackageDimensionInfo(userId,aascPackageDimensionInfoSave,queryTimeOutInt,clientId); // Jagadish added clientId Param
             if (saveDimensionAllStatus == 500) {   
                 aascPackageDimensionInfo =aascPackageDimensionDAO.getPackageDimensionInfo(locationId,type, clientId);
                 aascPackageDimensionInfo.setLocationId(locationId);
                 session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 request.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 key = "aasc500";
             } else if (saveDimensionAllStatus == -1013) {
                 key = "aasc20001";
             } else {
                 key = "aasc501";
             }
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }
         return key;
     }

     /**
      * addNewDimensionAction() method for  setting the   package Dimension values into bean aascPackageDimensionInfoSave.
      * @param   request                       Object    of class  HttpServletRequest.
      * @return  "success" or "fail"           String    whether the addition of new dimensions is success or failure .       
      */
     public String addNewDimensionAction(AascPackageDimensionInfo aascPackageDimensionInfoAddNewDimension,HttpServletRequest request) {
         try {
             session = request.getSession(true); // getting the Session object
             if (locationInt == 0) {
                 locationInt =Integer.parseInt(request.getParameter("locationId"));
             }
             AascPackageDimensionInfo aascPackageDimensionInfoCreate =aascPackageDimensionDAO.getSavePackageDimensionInfo(userId,locationInt,aascPackageDimensionInfoAddNewDimension,queryTimeOutInt,clientId); // clientId param added by Jagadish
             addNewDimensionStatus =aascPackageDimensionInfoCreate.getErrorStatus();
             statuSMsg = aascPackageDimensionInfoCreate.getErrorMessage();
             if (addNewDimensionStatus == 502) {
                 aascPackageDimensionInfo =aascPackageDimensionDAO.getPackageDimensionInfo(locationInt,type, clientId);
                 aascPackageDimensionInfo.setLocationId(locationInt);
                 session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 key = "aasc502";
             } else {
                 if (addNewDimensionStatus == 509) {
                     key = "aasc509";
                     // session.setAttribute("Error",statuSMsg);
                     session.setAttribute("Error", "");

                 } else if (addNewDimensionStatus == 508) {
                     key = "aasc508";
                     session.setAttribute("Error", statuSMsg);
                 } else if (addNewDimensionStatus == -1013) {
                     key = "aasc20001";
                 } else {
                     key = "aasc503";
                 }
                 session.setAttribute("aascPackageDimensionEditInfo",aascPackageDimensionInfoAddNewDimension);
                 actionValue = "ErrorCondition";
                 request.setAttribute("actionValue", actionValue);
                 request.setAttribute("key", key);
                 return "fail";
             }

             request.setAttribute("key", key);
             session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
             //return mapping.findForward("success");
         } catch (Exception e) {
             logger.severe("Exception::"+e.getMessage());
         }
         return "success";
     }

     /**
      * editAction() method for saving the   package Dimension Details .
      * @param   request                       Object    of class  HttpServletRequest .
      * @return  "success" or "fail"           String    whether the edit of dimension details is success or failure  .      
      */
     public String editAction(HttpServletRequest request) {
         try {
             session = request.getSession(true); // getting the Session object
             AascPackageDimensionInfo aascPackageDimensionEditInfo =new AascPackageDimensionInfo();
             String rowNo = request.getParameter("currentRow");
             String dimName = request.getParameter("dimensionName" + rowNo);
             String dimLength = request.getParameter("dimensionLength" + rowNo);
             int len = Integer.parseInt(dimLength);
             String dimWidth = request.getParameter("dimensionWidth" + rowNo);
             int width = Integer.parseInt(dimWidth);
             String dimHeight = request.getParameter("dimensionHeight" + rowNo);
             int ht = Integer.parseInt(dimHeight);
             String dimUnit = request.getParameter("dimensionUnits" + rowNo);
             aascPackageDimensionEditInfo.setDimensionName(dimName);
             aascPackageDimensionEditInfo.setDimensionLength(len);
             aascPackageDimensionEditInfo.setDimensionWidth(width);
             aascPackageDimensionEditInfo.setDimensionHeight(ht);
             aascPackageDimensionEditInfo.setDimensionUnits(dimUnit);
             session.setAttribute("aascPackageDimensionEditInfo",aascPackageDimensionEditInfo);
             return "success";
         } catch (Exception e) {
             e.printStackTrace();
             return "fail";
         }

     }

     /**
      * updateAction() method for Updating  the   package Dimension Details .
      * @param   request                       Object    of class  HttpServletRequest.
      * @param   aascPackageDimensionInfoSave  Object    of class AascPackageDimensionInfo bean.
      * @param   actionType                    String    the action type of the action class aascPackageDimensionAction.
      * @return  "success" or "fail"           String    whether the edit of dimension details is success or failure.        
      */
     public String updateAction(AascPackageDimensionInfo aascPackageDimensionInfoSave, 
                                HttpServletRequest request, String actionType) {

         try {
             logger.info("actionType : "+actionType);
             session = request.getSession(true); // getting the Session object
             String editedDimensionName = aascPackageDimensionInfoSave.getDimensionName();
             String editedpackageDimensionLengthStr = "" + aascPackageDimensionInfoSave.getDimensionLength();
             int length = Integer.parseInt(editedpackageDimensionLengthStr);
             String editedpackageDimensionWidthStr = "" + aascPackageDimensionInfoSave.getDimensionWidth();
             int width = Integer.parseInt(editedpackageDimensionWidthStr);
             String editedpackageDimensionHeightStr = "" + aascPackageDimensionInfoSave.getDimensionHeight();
             int height = Integer.parseInt(editedpackageDimensionHeightStr);
             String editedpackageDimensionUnitsStr = aascPackageDimensionInfoSave.getDimensionUnits();
             aascPackageDimensionInfoEditDimension = new AascPackageDimensionInfo();
             aascPackageDimensionInfoEditDimension.setDimensionName(editedDimensionName);
             aascPackageDimensionInfoEditDimension.setDimensionLength(length);
             aascPackageDimensionInfoEditDimension.setDimensionWidth(width);
             aascPackageDimensionInfoEditDimension.setDimensionHeight(height);
             aascPackageDimensionInfoEditDimension.setDimensionUnits(editedpackageDimensionUnitsStr);
             aascPackageDimensionInfoEditDimension.setActionType(actionType);

             if (locationInt == 0) {
                 locationInt =Integer.parseInt(request.getParameter("location"));
             }

             AascPackageDimensionInfo aascPackageDimEditDimension =aascPackageDimensionDAO.getUpdatePackageDimensionInfo(userId,locationInt, 
                                                                       aascPackageDimensionInfoEditDimension, 
                                                                       queryTimeOutInt, 
                                                                       clientId); // clientId param added by Jagadish
             editNewDimensionStatus =aascPackageDimEditDimension.getErrorStatus();
             editDimStatusMsg = aascPackageDimEditDimension.getErrorMessage();
             if (editNewDimensionStatus == 504) {
                 aascPackageDimensionInfo =aascPackageDimensionDAO.getPackageDimensionInfo(locationInt,type,clientId);
                 aascPackageDimensionInfo.setLocationId(locationInt);
                 session.setAttribute("aascPackageDimensionInfo",aascPackageDimensionInfo);
                 key = "aasc504";
                 request.setAttribute("key", key);
                 return "success";
             } else {


                 if (editNewDimensionStatus == 509) {
                     key = "aasc509";
                     // session.setAttribute("Error",editDimStatusMsg);                                                  
                     session.setAttribute("Error", "");

                 } else if (editNewDimensionStatus == 508) {
                     key = "aasc508";
                     session.setAttribute("Error", editDimStatusMsg);
                 } else {
                     key = "aasc505";
                 }
                 session.setAttribute("aascPackageDimensionEditInfo",aascPackageDimensionInfoEditDimension);
                 actionValue = "ErrorCondition";
                 request.setAttribute("actionValue", actionValue);
                 request.setAttribute("key", key);
                 return "fail";
             }

         } catch (Exception e) {

             key = "aasc505";
             request.setAttribute("key", key);
             logger.severe("Exception::"+e.getMessage());
         }
         return "";
     }
public String clientDetail(int clientId,HttpServletRequest request){
    try{
    session = request.getSession(true); // getting the Session object
    LinkedList locationList = new LinkedList();
    AascSetupLocationDAO aascSetupLocationDAO =new AascOracleSetupLocationDAO();
    HashMap locationHashMap =aascSetupLocationDAO.getAllLocationDetails(clientId);
    locationList = (LinkedList)locationHashMap.get("locationList");
    HashMap locationDetailsMap = new HashMap();
    Iterator locationListIterator = locationList.iterator();
        AascSetupLocationBean aascSetupLocationBean = null ;
    for (int i = 0; i < locationList.size(); i++) {
        aascSetupLocationBean = 
            (AascSetupLocationBean)locationListIterator.next();
        if("Y".equalsIgnoreCase(aascSetupLocationBean.getLocationStatus()))               
           locationDetailsMap.put(aascSetupLocationBean.getLocationId(), aascSetupLocationBean.getLocationName());
    }

    session.setAttribute("locationDetailsMap", 
                         (Object)locationDetailsMap);
    return "success";
    }
    catch (Exception e) {
        e.printStackTrace();
        return "error";
    }
}

 }
