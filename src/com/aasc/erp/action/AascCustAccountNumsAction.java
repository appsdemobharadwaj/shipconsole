
package com.aasc.erp.action;
/*========================================================================+
@(#)AascCustAccountNumsAction.java 05/08/2014
* Copyright (c)Apps Associates Pvt. Ltd.
* All rights reserved.
+===========================================================================*/

import com.aasc.erp.delegate.AascCustAccountNumsDelegate;
import com.aasc.erp.model.AascCarrierConfigurationDAO;
import com.aasc.erp.model.AascOracleCarrierConfigurationDAO;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascCustAccountNumsAction extends ActionSupport.
 * @author      K Sunanda
 * @version 1.0
 * @since
 * 
 * History:
 * 06/02/2015  Sunanda K    Removed unnecessary loggers and unused variables
 * 17/06/2015  Suman G             Added code to fix #2986
 **/

public class AascCustAccountNumsAction implements ServletRequestAware, ServletResponseAware {
    public AascCustAccountNumsAction() {
    }
    String key = null;
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

    public String execute() {

        HttpSession session = request.getSession();
        String actionType = request.getParameter("actionType"); // gets actionType from request
        String status = "";
            if(session.isNew() || 
                    !(session.getId().equals(session.getAttribute("SessionId")))){
//                logger.info("in Session "+session.isNew());
                return "sessionError";
            }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 3){
            return "sessionError";
        }
        if ("save".equalsIgnoreCase(actionType)) {
            try {
                AascCustAccountNumsDelegate aascCustAccountNumsDelegate = new AascCustAccountNumsDelegate();
                String result = aascCustAccountNumsDelegate.saveCustAccountNumbers(request);
                if (result.equalsIgnoreCase("success")) {
                    status = aascCustAccountNumsDelegate.getCustAccountNums(request,session);
                    AascCarrierConfigurationDAO aascCarrierConfigurationDAO = new AascOracleCarrierConfigurationDAO();
                    HashMap carrierValuesHashMap =aascCarrierConfigurationDAO.getCarrierCodeValues();
                    session.setAttribute("carrierValuesHashMap",(Object)carrierValuesHashMap);
                    if ("success".equalsIgnoreCase(status))
                        key = "aasc700";
                } else {
                    key = "aasc701";
                }

                request.setAttribute("key", key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //if(status.equalsIgnoreCase("success"))
            return "save";
            //else return "save";
        }
        return "success";

    }

}
