 /*
  * @(#)AascFreightShopAction.java     18/02/2015
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  *
  */
package com.aasc.erp.action;

import com.aasc.erp.delegate.AascFreightShopDelegate;
import com.aasc.erp.util.AascLogger;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * AascFreightShopAction class is action class for FreightShopping.
 * @version   1
 * @author    Suman G
 * History
 * 
 * 27/05/2015   Suman G     Added code to fix #2936
 * 17/06/2015  Suman G             Added code to fix #2986
 */

public class AascFreightShopAction  implements ServletRequestAware, ServletResponseAware {
    
    public AascFreightShopAction() {
    }
    
    static Logger logger = 
        AascLogger.getLogger(" AascFreightShopAction "); // logger object
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

        logger.info("Entered  AascFreightShopAction");
        HttpSession session = null;
        try {
            session = request.getSession();
        } catch (Exception e) {
            logger.severe("Excetpion :"+e.getMessage());

        }
        if(session.isNew() || 
                !(session.getId().equals(session.getAttribute("SessionId")))){
            logger.info("in Session "+session.isNew());
            return "sessionError";
        }
        int roleIdSession = ((Integer)session.getAttribute("role_id")).intValue();
        if(roleIdSession != 2 && roleIdSession != 4){
            return "sessionError";
        }
        AascFreightShopDelegate aascFreightShopDelegate = new AascFreightShopDelegate();
        aascFreightShopDelegate.commonAction(session,"");
        aascFreightShopDelegate.getFreightShopMethods(request, session);
        logger.info("Exit  AascFreightShopAction");
        return "success";
    }
}
