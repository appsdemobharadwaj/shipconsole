/*
   * @PackageDimensionAction.java
   * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.
   * All rights reserved.
   */


package com.aasc.erp.action;


import com.aasc.erp.bean.AascPackageDimensionInfo;
import com.aasc.erp.delegate.PackageDimensionsDelegate;
import com.aasc.erp.util.AascLogger;
import com.opensymphony.xwork2.ModelDriven;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * PackageDimensions Action class is action class for Package Dimensions.
 * @version - 2
 * History.
 * 17/11/2014    Sunanda.k    Added the following code from ShipConsoleCloud version 1.2 
 * 07/01/2015    Eshwari M    Merged Sunanda's code : Modifed getAllLocationDetails calling code for role 2.
 * 15-Jan-2015   Y Pradeep    Merged Sunanda's code on 1.0 release bugs.
 * 20-Jan-2015   K Sunanda    Removed unused variables and unnecessary loggers
 * 18-Feb-2015   K Sunanda    Removed all the business logic and placed in Delegate class
 * 10-03-2015    K Sunanda    Removed the unnecessary ';' at line 245
 * 17/06/2015  Suman G             Added code to fix #2986
 */



public class PackageDimensionsAction implements ModelDriven, ServletRequestAware, 
                                   ServletResponseAware {
    String locationStr = "";
    String actiontype = "";
    int locationInt = 0;
    int clientId = 0;
    int returnStatus = 0;
    int ORACLE = 1;
    int type;
    int locationId = 0;
    int dimensionId = 0;
    String dimensionActive = "";
    String dimensionDefault = "";
    int saveDimensionAllStatus = 0;
    int dimensionCount = 0;
    int addNewDimensionStatus = 0;
    String statuSMsg = "";
    int editNewDimensionStatus = 0;
    String editDimStatusMsg = "";
    int getDimensionStatus = 0;
    String actionValue = "";
    int userId;
    String clientIdStr = "";
    String key = null;

    AascPackageDimensionInfo aascPackageDimensionInfo;
    AascPackageDimensionInfo aascPackageDimensionInfoSave = 
        new AascPackageDimensionInfo();
    AascPackageDimensionInfo aascPackageDimensionInfoAddNewDimension;
    AascPackageDimensionInfo aascPackageDimensionInfoEditDimension;
    int queryTimeOutInt;
    private static Logger logger = 
        AascLogger.getLogger("PackageDimensionsAction");
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

    public PackageDimensionsAction() {
    }

    /**
     * This is the main action called from the Struts framework.
     * @return String 
     **/
    public String execute() {
        logger.info("Entered execute()");
        try {
            HttpSession session = request.getSession(true); // getting the Session object
             if(session.isNew() || 
                     !(session.getId().equals(session.getAttribute("SessionId")))){
                 logger.info("in Session "+session.isNew());
                 return "sessionError";
             }
            int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            
            if(roleIdSession != 2 && roleIdSession != 3 && roleIdSession != 4){
             return "sessionError";
            }
            PackageDimensionsDelegate packageDimensionsDelegate = 
                new PackageDimensionsDelegate();
            try {
                userId =((Integer)session.getAttribute("user_id")).intValue(); // string userid
                Integer roleId = (Integer)session.getAttribute("role_id");
                type = roleId.intValue();
                String queryTimeOut =(String)session.getAttribute("queryTimeOut");
                queryTimeOutInt = Integer.parseInt(queryTimeOut);
                String locationIdStr = request.getParameter("locationId");
                request.setAttribute("locationId", locationIdStr);
                locationStr ="" + aascPackageDimensionInfoSave.getLocationId();
                locationInt = Integer.parseInt(locationStr);
                if (roleId == 2) {
                    clientIdStr = request.getParameter("clientIdSelect");
                    if (!"".equalsIgnoreCase(clientIdStr)) {
                        clientId = (Integer)session.getAttribute("client_id");
                    } else {
                        clientId = 0;
                    }
                    request.setAttribute("client_id", clientId);
                } else {
                    clientId = (Integer)session.getAttribute("client_id");
                }
                actiontype = request.getParameter("actiontype");
                logger.info("actiontype in Action:" + actiontype);

                packageDimensionsDelegate.commonAction(request, session);
            } catch (Exception e) {
                logger.severe("Exception::"+e.getMessage());
            }
            //below code added by Jagadish for packagedimension orgid
            String locationIdStr;
            int locationIdN = 0;
            try {
                locationIdStr = request.getParameter("locationId");
                if (locationIdStr.equalsIgnoreCase("") || 
                    locationIdStr == null) {
                    locationIdStr = "";
                }
            } catch (Exception e) {
                locationIdStr = "";
            }
            if ((locationIdStr.equalsIgnoreCase("") || 
                 locationIdStr == null) && 
                !"locUnit".equalsIgnoreCase(actiontype)) {

                try {
                    String locationIdTemp =(String)session.getAttribute("locationId");
                    if (!"".equals(locationIdTemp) && locationIdTemp != null) {
                        locationIdN = Integer.parseInt(locationIdTemp);
                    }
                } catch (Exception e) {
                    locationIdN = (Integer)session.getAttribute("location_id");
                }
            } else {
                if (!"".equals(locationIdStr) && locationIdStr != null) {
                    locationIdN = new Integer(locationIdStr);
                }
            }
            if ("clientDetail".equals(actiontype)) {
                // request.removeAttribute("locationId");
                request.getAttribute("locationId");
                //   session.removeAttribute("locationIdTemp");
                session.removeAttribute("locationIDs");
                session.getAttribute("locationIDs");
                //session.removeAttribute("aascPackageDimensionInfo");

                String result = 
                    clientDetailAction(session, request, clientIdStr);
                if ("success".equalsIgnoreCase(result)) {
                    return "success";
                } else if ("error".equalsIgnoreCase(result)) {
                    return "error";
                }
            }
            if ("locUnit".equalsIgnoreCase(actiontype)) {
                // session.removeAttribute("aascPackageDimensionInfo");
                logger.info("actionType is locUnit");
                String str = packageDimensionsDelegate.locUnit(session);
                if (str == "succes") {
                    return "success";
                }
            }
            //end 
            if (actiontype.equalsIgnoreCase("GO")) {

                try {
                    String key =packageDimensionsDelegate.goAction(request, actiontype,locationInt);
                    request.setAttribute("key", key);
                    key = (String)request.getAttribute("key");

                } catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());
                }
            } else if (actiontype.equalsIgnoreCase("Save")) {
                try {
                    String sey = packageDimensionsDelegate.saveAction(request, locationIdN);
                    request.setAttribute("key", sey);
                    sey = (String)request.getAttribute("key");

                } catch (Exception e) {

                    logger.severe("Exception::"+e.getMessage());
                }
            }
            //Checking for the action type
            else if (actiontype.equalsIgnoreCase("Create")) {
                session.setAttribute("locationIDs",session.getAttribute("location_id"));

                return "Create";
            } else if (actiontype.equalsIgnoreCase("Cancel")) {
                session.setAttribute("locationIDs",session.getAttribute("location_id"));
                return "success";
            }
            //Checking for the action type
            else if (actiontype.equalsIgnoreCase("AddNewDimension") || 
                     actiontype.equalsIgnoreCase("AllowAddNewDimension")) {
                try {

                    String dimensionName = aascPackageDimensionInfoSave.getDimensionName();
                    String packageDimensionLengthStr ="" + aascPackageDimensionInfoSave.getDimensionLength();
                    int length = Integer.parseInt(packageDimensionLengthStr);
                    String packageDimensionWidthStr ="" + aascPackageDimensionInfoSave.getDimensionWidth();
                    int Width = Integer.parseInt(packageDimensionWidthStr);
                    String packageDimensionHeightStr ="" + aascPackageDimensionInfoSave.getDimensionHeight();
                    int Height = Integer.parseInt(packageDimensionHeightStr);
                    String packageDimensionUnitsStr = aascPackageDimensionInfoSave.getDimensionUnits();
                    aascPackageDimensionInfoAddNewDimension =new AascPackageDimensionInfo();
                    aascPackageDimensionInfoAddNewDimension.setDimensionName(dimensionName);
                    aascPackageDimensionInfoAddNewDimension.setDimensionLength(length);
                    aascPackageDimensionInfoAddNewDimension.setDimensionWidth(Width);
                    aascPackageDimensionInfoAddNewDimension.setDimensionHeight(Height);
                    aascPackageDimensionInfoAddNewDimension.setDimensionUnits(packageDimensionUnitsStr);
                    aascPackageDimensionInfoAddNewDimension.setActionType(actiontype);
                    //calling the save method of packing dimension detail information
                    //below code added by Jagadish
                    locationStr = request.getParameter("locationId");
                    try {
                        locationInt = Integer.parseInt(locationStr);
                    } catch (Exception e) {
                        locationInt = 0;
                    }
                    String sey =packageDimensionsDelegate.addNewDimensionAction(aascPackageDimensionInfoAddNewDimension,request);
                    if (sey == "success")
                    {
                        return "success";
                    }
                } catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());
                }
            } else if (actiontype.equalsIgnoreCase("Edit")) {
                session.setAttribute("locationIDs",session.getAttribute("location_id"));
                AascPackageDimensionInfo aascPackageDimensionEditInfo = new AascPackageDimensionInfo();
                String rowNo = request.getParameter("currentRow");
                String dimName = request.getParameter("dimensionName" + rowNo);
                String dimLength =request.getParameter("dimensionLength" + rowNo);
                int len = Integer.parseInt(dimLength);
                String dimWidth =request.getParameter("dimensionWidth" + rowNo);
                int width = Integer.parseInt(dimWidth);
                String dimHeight =request.getParameter("dimensionHeight" + rowNo);
                int ht = Integer.parseInt(dimHeight);
                String dimUnit =request.getParameter("dimensionUnits" + rowNo);
                aascPackageDimensionEditInfo.setDimensionName(dimName);
                aascPackageDimensionEditInfo.setDimensionLength(len);
                aascPackageDimensionEditInfo.setDimensionWidth(width);
                aascPackageDimensionEditInfo.setDimensionHeight(ht);
                aascPackageDimensionEditInfo.setDimensionUnits(dimUnit);
                session.setAttribute("aascPackageDimensionEditInfo",aascPackageDimensionEditInfo);
                return "Edit";
            } else if (actiontype.equalsIgnoreCase("Update") || 
                       actiontype.equalsIgnoreCase("AllowUpdate")) {
                try {
                    String editedDimensionName = aascPackageDimensionInfoSave.getDimensionName();
                    String editedpackageDimensionLengthStr = "" + aascPackageDimensionInfoSave.getDimensionLength();
                    int length = Integer.parseInt(editedpackageDimensionLengthStr);
                    String editedpackageDimensionWidthStr = "" + aascPackageDimensionInfoSave.getDimensionWidth();
                    int Width = Integer.parseInt(editedpackageDimensionWidthStr);
                    String editedpackageDimensionHeightStr = "" + aascPackageDimensionInfoSave.getDimensionHeight();
                    int Height = Integer.parseInt(editedpackageDimensionHeightStr);
                    String editedpackageDimensionUnitsStr = aascPackageDimensionInfoSave.getDimensionUnits();
                    aascPackageDimensionInfoEditDimension = new AascPackageDimensionInfo();
                    aascPackageDimensionInfoEditDimension.setDimensionName(editedDimensionName);
                    aascPackageDimensionInfoEditDimension.setDimensionLength(length);
                    aascPackageDimensionInfoEditDimension.setDimensionWidth(Width);
                    aascPackageDimensionInfoEditDimension.setDimensionHeight(Height);
                    aascPackageDimensionInfoEditDimension.setDimensionUnits(editedpackageDimensionUnitsStr);
                    aascPackageDimensionInfoEditDimension.setActionType(actiontype);
                    if (locationInt == 0) {
                        locationInt = Integer.parseInt(request.getParameter("locationIdStr"));
                    }
                    String sey = packageDimensionsDelegate.updateAction(aascPackageDimensionInfoEditDimension,request,actiontype);
                    if (sey == "success") {
                        return "success";
                    } else {
                        return "fail";
                    }

                } catch (Exception e) {
                    logger.severe("Exception::"+e.getMessage());
                }
            }
        }
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        }
        logger.info("Exiting execute() in PackageDimensionsAction");
        request.setAttribute("actionValue", actionValue);
        return "success";
    }

    /**
     * This method used for package dimension actions.
     * @param session HttpSession, request HttpServletRequest, clientIdStr String
     * @return String 
     **/
    public String clientDetailAction(HttpSession session, HttpServletRequest request,String clientIdStr) {
        PackageDimensionsDelegate packageDimensionsDelegate = 
            new PackageDimensionsDelegate();
        try {
            clientIdStr = request.getParameter("clientIdSelect");
            clientId = Integer.parseInt(clientIdStr);
            // clientId = (Integer)session.getAttribute("clientId");
            // String clientIdStr = (String)request.getParameter("clientId");
            String selectedClientId = "";
            try {
                selectedClientId = (clientIdStr);
            } catch (Exception e) {
                selectedClientId = "";
            }
            session.setAttribute("clientId", new Integer(selectedClientId));
            session.setAttribute("client_id", new Integer(selectedClientId));
            String key=packageDimensionsDelegate.clientDetail(clientId,request);
            if(key=="success"){
                return "success";
            }
            else{
                return "error";
            }
            }

         catch (Exception e) {
            e.getMessage();
        }
        return "success";
    }


    /**
     * getModel() this is a struts2 implementation method for initializing the form bean variables.
     * @return    Object  Returns the bean object of the form.
     */
    public Object getModel() {
        return aascPackageDimensionInfoSave;
    }
}
