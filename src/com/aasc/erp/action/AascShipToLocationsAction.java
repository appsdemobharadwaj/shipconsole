package com.aasc.erp.action;



import com.aasc.erp.bean.AascShipToLocationsInfo;
import com.aasc.erp.delegate.AascShipToLocationDelegate;
import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ModelDriven;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascShipToLocationsAction. <br>
 * This class used to create and edit ship to locations.
 * @author  K Sunanda
 * @version 1.1 <br>    
 * @since
 * History
 * 17-Dec-2014 Eshwari M   Merged Sunanda code for SC Lite
 * 18-Dec-2014 Sunanda K   Removed the term Adhoc
 * 26-Dec-2014 Suman G     Merged Sunanda Code.
 * 07-Jan-2015 Suman G     Merged Sunanda Code.
 * 20-Jan-2015 Sunanda K   Removed printStackTrace() and replaced with getMessage() and removed the business logic and added to delegate file
 * 10-Mar-2015 Sunanda K   Added code to remove the session object when action for new location creation is performed.
 * 17/06/2015  Suman G             Added code to fix #2986
 * */


public class AascShipToLocationsAction implements ServletRequestAware, 
                                                  ServletResponseAware, 
                                                  ModelDriven {
    public AascShipToLocationsAction() {
    }


    static Logger logger = 
        AascLogger.getLogger(" AascShipToLocationsAction "); // logger object
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    String actiontype = "";
    AascShipToLocationsInfo aascShipToLocationsInfo = 
        new AascShipToLocationsInfo();
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

     * This Method used to perform the action for ship to locations.

     * @return  String

     */
     
    public String execute() {
        int clientId =Integer.parseInt((String)request.getParameter("client_Id"));
        int userId = Integer.parseInt((String)request.getParameter("user_Id"));
        int locationId = 
            Integer.parseInt((String)request.getParameter("location_Id"));
        
        HttpSession session = request.getSession();
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
        AascShipToLocationDelegate aascShipToLocationDelegate = 
            new AascShipToLocationDelegate();
        actiontype = request.getParameter("actiontype");
        
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 3 && roleIdSession != 2){
            return "sessionError";
        }
        
        logger.info("actiontype.............." + actiontype);

        if (actiontype.equalsIgnoreCase("Create")) {
            logger.info("actiontype is CREATE");
            aascShipToLocationDelegate.setCountryDetails(session);
            aascShipToLocationDelegate.createLocation(request);
            session.removeAttribute("aascShipToLocationsInfo");
            return "Create";
        }

        else if (actiontype.equalsIgnoreCase("Upload")) {
            logger.info("actiontype is Upload");
            return "Upload";
        }

        else if (actiontype.equalsIgnoreCase("Cancel")) {
            logger.info("actiontype is CANCEL");
            return "Cancel";
        } else if (actiontype.equalsIgnoreCase("Edit")) {
            logger.info("actiontype is Edit");
            aascShipToLocationDelegate.editLocation(request);
            return "Edit";
        } else if (actiontype.equalsIgnoreCase("AddNewLocation")) {
            logger.info("locationName : " + 
                        aascShipToLocationsInfo.getShipToCustLocation());
            aascShipToLocationsInfo.setClientId(clientId);
            aascShipToLocationsInfo.setUserId(userId);

            aascShipToLocationsInfo.setLocationId(locationId);
            logger.info("actiontype is AddNewLocation");
            aascShipToLocationDelegate.addNewLocation(session, request, 
                                                      aascShipToLocationsInfo);
            return "success";
        } else if (actiontype.equalsIgnoreCase("editLocationDetails")) {

            logger.info("actiontype is Edit editLocationDetails");
            int customerLocationId = 
                Integer.parseInt((String)request.getParameter("customerLocationId"));

            aascShipToLocationsInfo.setClientId(clientId);
            aascShipToLocationsInfo.setUserId(userId);
            aascShipToLocationsInfo.setLocationId(locationId);

            aascShipToLocationsInfo.setCustomerLocationId(customerLocationId);

            aascShipToLocationDelegate.editLocationDetails(session, request, 
                                                           aascShipToLocationsInfo);
            return "Edit";
        }
        return "success";
    }


    public Object getModel() {
        return aascShipToLocationsInfo;
    }

}
