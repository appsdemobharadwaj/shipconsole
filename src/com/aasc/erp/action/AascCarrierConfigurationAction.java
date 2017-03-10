package com.aasc.erp.action;

/**
 * AascCarrierConfigurationAction class is action class for Carrier COnfiguration.
 * @version   2
 * @author    Venkateswaramma Malluru
 * @History
 *
 * 10/12/2014   Y Pradeep   Fixed all issues relaed to UI, Roles, Validations. Addded code for missed fileds for retriving and saving. Arranged code in proper order.
 * 24/12/2014   Y Pradeep   Commited code after Self Audit.
 * 02/01/2015   Y Pradeep   Replaced actionType with submit11 for Go button action.
 * 07/01/2015   Y Pradeep   Removed and commented unused loggers.
 * 15/01/2015   Y Pradeep   Modified code to hanle null pointer exception.
 * 08/04/2015  Y Pradeep    Modified code to display Location LOV enabled for all roles except role 5. Bug #2807.
 * 17/06/2015   Suman G     Added code to fix #2986
 * 04/11/2015   Suman G     Added code to fix #3378
 */
 
import java.util.logging.Logger;
import com.aasc.erp.bean.AascCarrierConfigurationBean;

import com.aasc.erp.delegate.AascCarrierConfigurationDelegate;

import com.aasc.erp.util.AascLogger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class AascCarrierConfigurationAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ModelDriven {
    public AascCarrierConfigurationAction() {
    }
    private static Logger logger = 
        AascLogger.getLogger("AascCarrierConfigurationAction");
    protected HttpServletRequest request;
    protected HttpServletResponse response;    
    
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
    
    public Object getModel() {
        return aascCarrierConfigurationBean;
    }
    
    AascCarrierConfigurationBean aascCarrierConfigurationBean = new AascCarrierConfigurationBean();
    
    /**
     * This is the main action called from the Struts framework.
     */
    public String execute()
    {
    
//        String actionType = request.getParameter("actionType");    // gets actionType from request    // removed by Y Pradeep
        HttpSession session=request.getSession();
        
        AascCarrierConfigurationDelegate aascCarrierConfigurationDelegate=new AascCarrierConfigurationDelegate();
        int clientId;
        int userid;
        try{
            clientId= Integer.parseInt(session.getAttribute("client_id").toString());
         
            userid= Integer.parseInt(session.getAttribute("user_id").toString());
            if(session.isNew() || 
                    !(session.getId().equals(session.getAttribute("SessionId")))){
                logger.info("in Session "+session.isNew());
                return "error";
            }
            int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
            if((roleIdSession != 1 && roleIdSession != 2 && roleIdSession != 3 && roleIdSession != 4)){
                return "error";
            }
        }catch(Exception e){
            return "error";
        }
        aascCarrierConfigurationBean.setClientId(clientId);
        aascCarrierConfigurationBean.setUserId(userid);
        try{
        String clientIdStr1 = request.getParameter("clientIdVal");
        if(!"".equalsIgnoreCase(clientIdStr1)){
            clientId = Integer.parseInt(clientIdStr1);
//            logger.info("clientId: "+clientId);
            request.setAttribute("clientId", clientIdStr1);
            aascCarrierConfigurationBean.setClientId(clientId);
        }
        Integer role = (Integer)session.getAttribute("role_id");
        int roleId = role.intValue();
//        logger.info("roleId :::::::::::::::::"+roleId);
        
        if(roleId == 4){
            String locationIdStr1 = request.getParameter("locationId");

            if(!"".equalsIgnoreCase(locationIdStr1) && locationIdStr1 != null){
                int locationId = Integer.parseInt(locationIdStr1);
                aascCarrierConfigurationBean.setLocationId(locationId);
            }
        }
        }catch(Exception e){
            return "error";
        }
        try{
            if("Go".equals(request.getParameter("submit11"))) {
                logger.info("Inside go");
//                logger.info("location id :::::::"+aascCarrierConfigurationBean.getLocationId());
//                logger.info("carrierName id :::::::"+aascCarrierConfigurationBean.getCarrierName());
                
    
                String result =  aascCarrierConfigurationDelegate.getCarrierConfigurationDetails(aascCarrierConfigurationBean,session);
                String res=  aascCarrierConfigurationDelegate.getAccountNumbers(aascCarrierConfigurationBean,session);
             
                request.setAttribute("carrierCode", aascCarrierConfigurationBean.getCarrierCodeValue());
                if("success".equalsIgnoreCase(result) && "success".equalsIgnoreCase(res))
                    return "success";
            }
        // added below if condition to get Location Details based on client id in Carrier Configuration.
            if ("LocationDetails".equals(request.getParameter("submit11"))) {
                logger.info("actiontype is LocationDetails");
                String result = 
                    aascCarrierConfigurationDelegate.locationAction(session, request);
                if ("success".equalsIgnoreCase(result)) {
                    return "success";
                } else if ("error".equalsIgnoreCase(result)) {
                    return "error";
                }
    
            }
    
            if ("Load".equals(request.getParameter("submit11"))) {
              //  logger.info("actiontype is Load");
                String result = 
                    aascCarrierConfigurationDelegate.loadAction(aascCarrierConfigurationBean, session);
                session.setAttribute("aascCarrierConfiguration",aascCarrierConfigurationBean);
            
                if ("success".equalsIgnoreCase(result)) {
                    return "success";
                } else if ("error".equalsIgnoreCase(result)) {
                    return "error";
                }
    
            }
        
            if ("ShipMethod".equals(request.getParameter("submit11"))) {
    
                logger.info("actiontype is ShipMethod");
                String result = aascCarrierConfigurationDelegate.shipMethodAction(session, aascCarrierConfigurationBean, request);
                if ("success".equalsIgnoreCase(result)) {
                    return "ShipMethod";
                } else if ("error".equalsIgnoreCase(result)) {
                    return "error";
                }
    
            }

            if (("SaveUpdate".equalsIgnoreCase(request.getParameter("submit11")))) // checking whether the submit button vlaue is equal to saveupdate
            {
                logger.info("actiontype is saveUpdate");
                aascCarrierConfigurationDelegate.saveUpdateAction(aascCarrierConfigurationBean, session, request);
                String result = "";
                result = aascCarrierConfigurationDelegate.getAccountNumbers(aascCarrierConfigurationBean, session);
                if ("success".equalsIgnoreCase(result)) {
                    return "success";
                } else if ("error".equalsIgnoreCase(result)) {
                    return "error";
                }
            } // closing the first if else block
        }
        catch(Exception e){
            logger.info("Exceptiuon in Carrier id in Action"+e.getMessage());
//            e.printStackTrace();
        }
        
        return "success";
    }
    
}
